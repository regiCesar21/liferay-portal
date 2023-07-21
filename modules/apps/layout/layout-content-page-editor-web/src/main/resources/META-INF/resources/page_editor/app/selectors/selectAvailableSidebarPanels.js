/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {VIEWPORT_SIZES} from '../config/constants/viewportSizes';
import {
	CONTENT_CHANGE_PANELS,
	RESPONSIVE_PANELS,
} from './selectAvailablePanels';

/**
 * @param {{ [panelId: string]: object }} sidebarPanels
 */
export default function selectAvailableSidebarPanels(sidebarPanels) {

	/**
	 * @param {{ permissions: import("../../types/ActionKeys").ActionKeysMap, selectedViewportSize: string }} state
	 */
	return function ({permissions, selectedViewportSize}) {
		const availableSidebarPanels = {};

		if (permissions.LOCKED_SEGMENTS_EXPERIMENT || !permissions.UPDATE) {
			CONTENT_CHANGE_PANELS.forEach((panelId) => {
				availableSidebarPanels[panelId] = sidebarPanels[panelId];
			});

			return availableSidebarPanels;
		}
		else if (selectedViewportSize !== VIEWPORT_SIZES.desktop) {
			RESPONSIVE_PANELS.forEach((panelId) => {
				availableSidebarPanels[panelId] = sidebarPanels[panelId];
			});

			return availableSidebarPanels;
		}

		return sidebarPanels;
	};
}
