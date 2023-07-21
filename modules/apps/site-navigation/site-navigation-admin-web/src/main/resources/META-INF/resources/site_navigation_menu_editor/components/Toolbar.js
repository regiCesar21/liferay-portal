/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import React from 'react';

import {SIDEBAR_PANEL_IDS} from '../constants/sidebarPanelIds';
import {useSetSidebarPanelId} from '../contexts/SidebarPanelIdContext';
import {AddItemDropDown} from './AddItemDropdown';
import {AppLayout} from './AppLayout';

export const Toolbar = () => {
	const setSidebarPanelId = useSetSidebarPanelId();

	const onSettingsButtonClick = () => {
		setSidebarPanelId(SIDEBAR_PANEL_IDS.menuSettings);
	};

	return (
		<>
			<AppLayout.ToolbarItem expand />

			<AppLayout.ToolbarItem>
				<ClayButtonWithIcon
					displayType="unstyled"
					monospaced
					onClick={onSettingsButtonClick}
					small
					symbol="cog"
				/>
			</AppLayout.ToolbarItem>

			<AppLayout.ToolbarItem>
				<AddItemDropDown
					trigger={
						<ClayButtonWithIcon monospaced small symbol="plus" />
					}
				/>
			</AppLayout.ToolbarItem>
		</>
	);
};
