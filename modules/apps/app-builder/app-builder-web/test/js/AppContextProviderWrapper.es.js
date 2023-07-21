/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createMemoryHistory} from 'history';
import React from 'react';
import {HashRouter} from 'react-router-dom';

import {AppContextProvider} from '../../src/main/resources/META-INF/resources/js/AppContext.es';

export default ({
	children,
	appContext = {},
	history = createMemoryHistory(),
}) => {
	return (
		<AppContextProvider {...appContext}>
			<div className="tools-control-group">
				<div className="control-menu-level-1-heading" />
			</div>

			<HashRouter>{React.cloneElement(children, {history})}</HashRouter>
		</AppContextProvider>
	);
};
