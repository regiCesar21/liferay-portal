/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EVENT_TYPES} from '../actions/eventTypes.es';
import {evaluate} from '../util/evaluation.es';
import {getFormTitle} from '../util/formId.es';

export default function previousPage({
	activePage,
	defaultLanguageId,
	editingLanguageId,
	formId,
	groupId,
	pages,
	portletNamespace,
	rules,
}) {
	const formInstance = document.querySelector(
		`[data-ddmforminstanceid="${formId}"]`
	);
	const title = getFormTitle(formInstance);

	return (dispatch) => {
		evaluate(null, {
			defaultLanguageId,
			editingLanguageId,
			groupId,
			pages,
			portletNamespace,
			rules,
		}).then((evaluatedPages) => {
			let previousActivePageIndex = activePage;

			for (let i = activePage - 1; i > -1; i--) {
				if (evaluatedPages[i].enabled) {
					previousActivePageIndex = i;

					break;
				}
			}

			const activePageUpdated = Math.max(previousActivePageIndex, 0);

			dispatch({
				payload: activePageUpdated,
				type: EVENT_TYPES.CHANGE_ACTIVE_PAGE,
			});

			Liferay.fire('ddmFormPageShow', {
				formId,
				formPageTitle: pages[activePageUpdated].title,
				page: activePageUpdated,
				title,
			});
		});
	};
}
