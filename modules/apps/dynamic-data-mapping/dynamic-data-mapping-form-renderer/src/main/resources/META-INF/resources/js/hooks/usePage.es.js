/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext} from 'react';

const PageContext = React.createContext({});

PageContext.displayName = 'PageContext';

export const PageProvider = ({children, value}) => (
	<PageContext.Provider value={value}>{children}</PageContext.Provider>
);

export const usePage = () => {
	return useContext(PageContext);
};
