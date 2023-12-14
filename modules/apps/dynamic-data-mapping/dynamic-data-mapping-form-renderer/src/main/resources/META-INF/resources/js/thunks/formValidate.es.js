/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EVENT_TYPES} from '../actions/eventTypes.es';
import {evaluate} from '../util/evaluation.es';
import {PagesVisitor} from '../util/visitors.es';

export default function formValidate({
	activePage,
	defaultLanguageId,
	editingLanguageId,
	groupId,
	pages,
	portletNamespace,
	rules,
}) {
	return (dispatch) => {
		const ddmFormSubmitButton = document.getElementById('ddm-form-submit');

		if (ddmFormSubmitButton) {
			ddmFormSubmitButton.disabled = true;
		}

		return evaluate(null, {
			defaultLanguageId,
			editingLanguageId,
			groupId,
			pages,
			portletNamespace,
			rules,
		}).then((evaluatedPages) => {
			let validForm = true;
			const visitor = new PagesVisitor(evaluatedPages);

			visitor.mapFields(
				({valid}) => {
					if (!valid) {
						validForm = false;
					}
				},
				true,
				true
			);

			if (!validForm) {
				if (ddmFormSubmitButton) {
					ddmFormSubmitButton.disabled = false;
				}

				dispatch({
					payload: {
						newPages: evaluatedPages,
						pageIndex: activePage,
					},
					type: EVENT_TYPES.PAGE_VALIDATION_FAILED,
				});
			}

			return Promise.resolve(validForm);
		});
	};
}
