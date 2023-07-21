/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {VIEWPORT_SIZES} from '../config/constants/viewportSizes';

export const CONTENT_CHANGE_PANELS = ['comments', 'contents', 'page-structure'];
export const RESPONSIVE_PANELS = ['comments', 'contents', 'page-structure'];

/**
 * @param {Array<Array<string>>} panels
 */
export default function selectAvailablePanels(panels) {

	/**
	 * @param {{ permissions: import("../../types/ActionKeys").ActionKeysMap, selectedViewportSize: string }} state
	 */
	return function ({permissions, selectedViewportSize}) {
		if (permissions.LOCKED_SEGMENTS_EXPERIMENT || !permissions.UPDATE) {
			return panels
				.map((group) =>
					group.filter((panelId) =>
						CONTENT_CHANGE_PANELS.includes(panelId)
					)
				)
				.filter((group) => group.length);
		}
		else if (selectedViewportSize !== VIEWPORT_SIZES.desktop) {
			return panels
				.map((group) =>
					group.filter((panelId) =>
						RESPONSIVE_PANELS.includes(panelId)
					)
				)
				.filter((group) => group.length);
		}

		return panels;
	};
}
