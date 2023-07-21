/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EVENT_TYPES} from '../actions/eventTypes.es';
import {evaluate, mergePages} from '../util/evaluation.es';
import {PagesVisitor} from '../util/visitors.es';

let REVALIDATE_UPDATES = [];

const getEditedPages = ({editingLanguageId, name, pages, value}) => {
	const pageVisitor = new PagesVisitor(pages);

	return pageVisitor.mapFields(
		(field) => {
			if (field.name === name) {
				return {
					...field,
					localizedValue: {
						...field.localizedValue,
						[editingLanguageId]: value,
					},
					value,
				};
			}

			return field;
		},
		false,
		true
	);
};

let lastEditedPages = [];

export default function fieldChange({
	defaultLanguageId,
	editingLanguageId,
	groupId,
	pages,
	portletNamespace,
	properties,
	rules,
}) {
	return (dispatch) =>

		// We added an event delay to break long tasks into short
		// durations that are done when `fieldChange` is called
		// very often. That's because changing a fieldEdited is a
		// very expensive operation that causes many renderings in
		// the application.

		{
			const {fieldInstance, value} = properties;
			const {evaluable, fieldName} = fieldInstance;

			const editedPages = getEditedPages({
				editingLanguageId,
				name: fieldInstance.name,
				pages,
				value,
			});

			lastEditedPages = editedPages;

			// We want a synchronous update without waiting for an evaluation of
			// the field.

			dispatch({payload: editedPages, type: EVENT_TYPES.UPDATE_PAGES});

			// We triggered a dispatch of FIELD_CHANGE just to propagate the event to
			// the upper layers.

			dispatch({payload: properties, type: EVENT_TYPES.FIELD_CHANGE});

			if (evaluable) {
				evaluate(fieldName, {
					defaultLanguageId,
					editingLanguageId,
					groupId,
					pages: editedPages,
					portletNamespace,
					rules,
				})
					.then((evaluatedPages) => {
						if (REVALIDATE_UPDATES.length > 0) {

							// All non-evaluable operations that were performed after the request
							// was sent are used here to revalidate the new data.

							REVALIDATE_UPDATES.forEach((item) => {
								evaluatedPages = getEditedPages({
									...item,
									pages: evaluatedPages,
								});
							});

							// Redefine the list of updates to avoid leaking memory and avoid
							// more expensive operations in the next interactions

							REVALIDATE_UPDATES = [];
						}

						return evaluatedPages;
					})
					.then((evaluatedPages) => {
						if (fieldInstance.isDisposed()) {
							return;
						}

						const mergedPages = mergePages(
							defaultLanguageId,
							editingLanguageId,
							fieldName,
							evaluatedPages,
							lastEditedPages
						);

						dispatch({
							payload: mergedPages,
							type: EVENT_TYPES.UPDATE_PAGES,
						});
						dispatch({
							payload: mergedPages,
							type: EVENT_TYPES.FIELD_EVALUATED,
						});
					})
					.catch((error) =>
						dispatch({
							payload: error,
							type: EVENT_TYPES.FIELD_EVALUATION_ERROR,
						})
					);
			}
			else {
				REVALIDATE_UPDATES.push({
					editingLanguageId,
					name: fieldInstance.name,
					value,
				});
			}
		};
}
