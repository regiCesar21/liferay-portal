/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sub} from 'dynamic-data-mapping-form-field-type/util/strings.es';

import {EVENT_TYPES} from '../actions/eventTypes.es';
import {PagesVisitor} from '../util/visitors.es';

export default (state, action) => {
	switch (action.type) {
		case EVENT_TYPES.UPDATE_PAGES:
			return {
				pages: action.payload,
			};
		case EVENT_TYPES.CHANGE_ACTIVE_PAGE:
			return {
				activePage: action.payload,
			};
		case EVENT_TYPES.PAGE_VALIDATION_FAILED: {
			const {newPages, pageIndex} = action.payload;
			const visitor = new PagesVisitor(newPages ?? state.pages);
			const fieldTypes = [
				'select-multiple',
				'radio',
				'checkbox',
				'text',
				'hidden',
			];

			let firstInvalidFieldLabel = null;
			let firstInvalidFieldInput = null;
			let firstInvalidFieldName = null;

			const pages = visitor.mapFields(
				(
					field,
					fieldIndex,
					columnIndex,
					rowIndex,
					currentPageIndex
				) => {
					const displayErrors = currentPageIndex === pageIndex;

					if (
						displayErrors &&
						field.errorMessage !== undefined &&
						field.errorMessage !== '' &&
						!field.valid &&
						firstInvalidFieldLabel == null
					) {
						firstInvalidFieldLabel = field.label;
						firstInvalidFieldInput = document.querySelector(
							`[name='${field.name}']`
						);
						firstInvalidFieldName = field.name;
					}

					return {
						...field,
						displayErrors,
					};
				},
				true,
				true
			);

			if (firstInvalidFieldInput) {
				if (!fieldTypes.includes(firstInvalidFieldInput.type)) {
					firstInvalidFieldInput.focus();
				}
				else {
					document
						.getElementsByName(firstInvalidFieldName)[0]
						?.parentElement?.scrollIntoView();
				}
			}

			return {
				forceAriaUpdate: Date.now(),
				invalidFormMessage: sub(
					Liferay.Language.get('this-form-is-invalid-check-field-x'),
					[firstInvalidFieldLabel]
				),
				pages,
			};
		}
		default:
			return state;
	}
};
