/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayManagementToolbar from '@clayui/management-toolbar';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import ActiveViewSelector from './ActiveViewSelector';
import {useAppState} from './Context';
import CreationMenu from './CreationMenu';
import FiltersDropdown from './FiltersDropdown';
import MainSearch from './MainSearch';

function NavBar({creationMenu, showSearch, views}) {
	const {
		state: {filters},
	} = useAppState();
	const [showMobile, setShowMobile] = useState(false);

	return (
		<ClayManagementToolbar className="c-mb-0 justify-content-space-between">
			<ClayManagementToolbar.ItemList>
				{!!filters.length && (
					<ClayManagementToolbar.Item>
						<FiltersDropdown />
					</ClayManagementToolbar.Item>
				)}
			</ClayManagementToolbar.ItemList>

			{showSearch && (
				<>
					<ClayManagementToolbar.Search
						onSubmit={(event) => {
							event.preventDefault();
						}}
						showMobile={showMobile}
					>
						<MainSearch setShowMobile={setShowMobile} />
					</ClayManagementToolbar.Search>
				</>
			)}

			<ClayManagementToolbar.ItemList>
				{showSearch && (
					<ClayManagementToolbar.Item className="navbar-breakpoint-d-none">
						<ClayButton
							className="nav-link nav-link-monospaced"
							displayType="unstyled"
							onClick={() => setShowMobile(true)}
						>
							<ClayIcon symbol="search" />
						</ClayButton>
					</ClayManagementToolbar.Item>
				)}
				{views?.length > 1 && (
					<ClayManagementToolbar.Item>
						<ActiveViewSelector views={views} />
					</ClayManagementToolbar.Item>
				)}
				{creationMenu && (
					<ClayManagementToolbar.Item>
						<CreationMenu {...creationMenu} />
					</ClayManagementToolbar.Item>
				)}
			</ClayManagementToolbar.ItemList>
		</ClayManagementToolbar>
	);
}

NavBar.propTypes = {
	creationMenu: PropTypes.shape({
		primaryItems: PropTypes.array,
		secondaryItems: PropTypes.array,
	}),
	setActiveView: PropTypes.func,
	showSearch: PropTypes.bool,
	views: PropTypes.arrayOf(
		PropTypes.shape({
			label: PropTypes.string.isRequired,
			thumbnail: PropTypes.string.isRequired,
		})
	),
};

NavBar.defaultProps = {
	creationMenu: {
		primaryItems: [],
	},
	showSearch: true,
};

export default NavBar;
