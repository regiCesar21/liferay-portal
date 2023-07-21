/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {createContext} from 'react';

const AppContext = createContext();

const AppContextProvider = ({
	children,
	pathFriendlyURLPublic,
	portletNamespace,
	...restProps
}) => {
	const getStandaloneURL = (appId) =>
		`${Liferay.ThemeDisplay.getPortalURL()}${pathFriendlyURLPublic}/App${appId}`;

	return (
		<AppContext.Provider
			value={{
				getStandaloneURL,
				namespace: portletNamespace,
				...restProps,
			}}
		>
			{children}
		</AppContext.Provider>
	);
};

export {AppContext, AppContextProvider};
