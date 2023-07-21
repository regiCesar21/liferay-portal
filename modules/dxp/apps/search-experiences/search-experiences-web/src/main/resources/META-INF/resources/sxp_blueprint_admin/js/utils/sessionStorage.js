/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import isDefined from './functions/is_defined';

const NAMESPACE = 'com.liferay.search.experiences.web_';

/**
 * Keeps track of session ids so none are reused.
 */
export const SESSION_IDS = {
	ADD_SXP_ELEMENT_SIDEBAR: `${NAMESPACE}addSXPElementSidebar`,
	SUCCESS_MESSAGE: `${NAMESPACE}successMessage`,
};

/**
 * Helper function to set the session storage value of
 * SESSION_IDS.ADD_SXP_ELEMENT_SIDEBAR.
 * Toggles the state if `state` is undefined.
 * @param {String} state Either 'open' or 'closed'.
 */
export function setStorageAddSXPElementSidebar(state) {
	if (!isDefined(state)) {
		sessionStorage.setItem(
			SESSION_IDS.ADD_SXP_ELEMENT_SIDEBAR,
			sessionStorage.getItem(SESSION_IDS.ADD_SXP_ELEMENT_SIDEBAR) ===
				'open'
				? 'closed'
				: 'open'
		);
	}
	else {
		sessionStorage.setItem(SESSION_IDS.ADD_SXP_ELEMENT_SIDEBAR, state);
	}

	if (process.env.NODE_ENV === 'development') {
		if (isDefined(state) && state !== 'open' && state !== 'closed') {
			console.warn(
				`Session ID: ${SESSION_IDS.ADD_SXP_ELEMENT_SIDEBAR}`,
				`Parameter value must be 'open' or 'closed'.`,
				`'${state}' was passed in instead.`
			);
		}
	}
}
