/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import {SIDEBAR_PANEL_IDS} from '../constants/sidebarPanelIds';
import {ConstantsProvider} from '../contexts/ConstantsContext';
import {ItemsProvider, useItems} from '../contexts/ItemsContext';
import {SelectedMenuItemIdProvider} from '../contexts/SelectedMenuItemIdContext';
import {SidebarPanelIdProvider} from '../contexts/SidebarPanelIdContext';
import {DragDropProvider} from '../utils/useDragAndDrop';
import {AppLayout} from './AppLayout';
import DragPreview from './DragPreview';
import {EmptyState} from './EmptyState';
import {Menu} from './Menu';
import {MenuItemSettingsPanel} from './MenuItemSettingsPanel';
import {MenuSettingsPanel} from './MenuSettingsPanel';
import {Toolbar} from './Toolbar';

const SIDEBAR_PANELS = [
	{
		component: MenuItemSettingsPanel,
		sidebarPanelId: SIDEBAR_PANEL_IDS.menuItemSettings,
	},
	{
		component: MenuSettingsPanel,
		sidebarPanelId: SIDEBAR_PANEL_IDS.menuSettings,
	},
];

export function App(props) {
	const {siteNavigationMenuItems} = props;

	return (
		<DndProvider backend={HTML5Backend}>
			<ConstantsProvider constants={props}>
				<ItemsProvider initialItems={siteNavigationMenuItems}>
					<DragPreview />
					<DragDropProvider>
						<SelectedMenuItemIdProvider>
							<SidebarPanelIdProvider>
								<AppLayoutWrapper />
							</SidebarPanelIdProvider>
						</SelectedMenuItemIdProvider>
					</DragDropProvider>
				</ItemsProvider>
			</ConstantsProvider>
		</DndProvider>
	);
}

const AppLayoutWrapper = () => (
	<AppLayout
		contentChildren={useItems().length ? <Menu /> : <EmptyState />}
		sidebarPanels={SIDEBAR_PANELS}
		toolbarChildren={<Toolbar />}
	/>
);
