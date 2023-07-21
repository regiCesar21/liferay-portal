/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cancelDebounce, debounce} from 'frontend-js-web';
import {useRef} from 'react';

export function useDebounceCallback(callback, milliseconds) {
	const callbackRef = useRef(debounce(callback, milliseconds));

	return [callbackRef.current, () => cancelDebounce(callbackRef.current)];
}

const MODAL_EXPERIENCE_STATE_KEY = 'modalExperienceState';

/**
 * @typedef {Object} ModalExperienceState
 * @param {string} ModalExperienceState.plid
 * @param {string?} ModalExperienceState.experienceId
 * @param {string} ModalExperienceState.experienceName
 * @param {string} ModalExperienceState.segmentId
 */

/**
 * Stores the `modalExperienceState` so it can be recovered later
 *
 * It also destroys any information previously stored for the same purpose
 *
 * @param {ModalExperienceState} modalExperienceState
 */
export function storeModalExperienceState(modalExperienceState) {
	window.sessionStorage.setItem(
		MODAL_EXPERIENCE_STATE_KEY,
		JSON.stringify(modalExperienceState)
	);
}

/**
 * Rescovers the `modalExperienceState` destroying any information stored for that purpose
 *
 * @return {ModalExperienceState|null}
 */
export function recoverModalExperienceState() {
	const state = window.sessionStorage.getItem(MODAL_EXPERIENCE_STATE_KEY);
	if (state !== null) {
		window.sessionStorage.removeItem(MODAL_EXPERIENCE_STATE_KEY);

		return JSON.parse(state);
	}

	return null;
}
