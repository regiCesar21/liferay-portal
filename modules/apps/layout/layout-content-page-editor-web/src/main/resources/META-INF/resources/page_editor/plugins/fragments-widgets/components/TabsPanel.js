/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTabs from '@clayui/tabs';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {useId} from '../../../app/utils/useId';
import TabCollection from './TabCollection';

const INITIAL_EXPANDED_ITEM_COLLECTIONS = 3;

export default function TabsPanel({tabs}) {
	const [activeTabId, setActiveTabId] = useState(0);
	const tabIdNamespace = useId();

	const getTabId = (tabId) => `${tabIdNamespace}tab${tabId}`;
	const getTabPanelId = (tabId) => `${tabIdNamespace}tabPanel${tabId}`;

	return (
		<>
			<ClayTabs
				className="page-editor__sidebar__fragments-widgets-panel__tabs"
				modern
			>
				{tabs.map((tab, index) => (
					<ClayTabs.Item
						active={activeTabId === index}
						innerProps={{
							'aria-controls': getTabPanelId(index),
							id: getTabId(index),
						}}
						key={index}
						onClick={() => setActiveTabId(index)}
					>
						{tab.label}
					</ClayTabs.Item>
				))}
			</ClayTabs>

			<ClayTabs.Content
				activeIndex={activeTabId}
				className="page-editor__sidebar__fragments-widgets-panel__tab-content"
				fade
			>
				{tabs.map((tab, index) => (
					<ClayTabs.TabPane
						aria-labelledby={getTabId(index)}
						id={getTabPanelId(index)}
						key={index}
					>
						<ul className="list-unstyled">
							{tab.collections.map((collection, index) => (
								<TabCollection
									collection={collection}
									key={index}
									open={
										index <
										INITIAL_EXPANDED_ITEM_COLLECTIONS
									}
								/>
							))}
						</ul>
					</ClayTabs.TabPane>
				))}
			</ClayTabs.Content>
		</>
	);
}

TabsPanel.propTypes = {
	tabs: PropTypes.arrayOf(
		PropTypes.shape({
			collections: PropTypes.arrayOf(PropTypes.shape({})),
			id: PropTypes.string,
			label: PropTypes.string,
		})
	),
};
