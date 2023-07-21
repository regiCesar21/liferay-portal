/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import ClayTabs from '@clayui/tabs';
import React from 'react';

import {EVENT_TYPES} from '../actions/eventTypes.es';
import {useForm} from '../hooks/useForm.es';

export const Tabs = ({activePage, pages}) => {
	const dispatch = useForm();

	return (
		<nav className="component-tbar ddm-form-tabs mb-3 tbar">
			<ClayLayout.ContainerFluid className="pr-0">
				<ClayTabs>
					{pages.map((page, index) => (
						<ClayTabs.Item
							active={index === activePage}
							disabled={!page.enabled}
							key={index}
							onClick={() =>
								dispatch({
									payload: index,
									type: EVENT_TYPES.CHANGE_ACTIVE_PAGE,
								})
							}
						>
							<span className="navbar-text-truncate">
								{page.title}
							</span>
						</ClayTabs.Item>
					))}
				</ClayTabs>
			</ClayLayout.ContainerFluid>
		</nav>
	);
};
