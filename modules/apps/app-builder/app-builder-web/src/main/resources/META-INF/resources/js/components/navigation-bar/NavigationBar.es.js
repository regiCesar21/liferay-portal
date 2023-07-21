/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayNavigationBar from '@clayui/navigation-bar';
import React from 'react';
import {NavLink, withRouter} from 'react-router-dom';

const {Item} = ClayNavigationBar;

export default withRouter(({match: {url}, tabs}) => {
	const blur = (event) => {
		event.target.blur();
	};

	return (
		<ClayNavigationBar triggerLabel={tabs.find(({active}) => active).label}>
			{tabs.map(({exact, label, path}, index) => (
				<Item key={index}>
					<NavLink
						activeClassName="active"
						className="nav-link"
						exact={exact}
						onClick={blur}
						to={path(url)}
					>
						{label}
					</NavLink>
				</Item>
			))}
		</ClayNavigationBar>
	);
});
