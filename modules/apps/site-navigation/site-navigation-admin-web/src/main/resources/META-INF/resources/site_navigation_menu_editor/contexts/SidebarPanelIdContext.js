/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

const SidebarPanelIdContext = React.createContext(null);
const SetSidebarPanelIdContext = React.createContext(() => {});

export const useSetSidebarPanelId = () => useContext(SetSidebarPanelIdContext);
export const useSidebarPanelId = () => useContext(SidebarPanelIdContext);

export const SidebarPanelIdProvider = ({
	children,
	initialSidebarPanelId = null,
}) => {
	const [sidebarPanelId, setSidebarPanelId] = useState(initialSidebarPanelId);

	return (
		<SetSidebarPanelIdContext.Provider value={setSidebarPanelId}>
			<SidebarPanelIdContext.Provider value={sidebarPanelId}>
				{children}
			</SidebarPanelIdContext.Provider>
		</SetSidebarPanelIdContext.Provider>
	);
};

SidebarPanelIdContext.propTypes = {
	setSidebarPanelId: PropTypes.func,
};
