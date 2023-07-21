/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';
import {render} from '@testing-library/react';
import {createMemoryHistory} from 'history';
import {Router} from 'react-router-dom';

import {AppContext} from '../src/main/resources/META-INF/resources/js/AppContext.es';

export const renderComponent = ({
	apolloAddTypename = false,
	apolloMocks = null,
	contextValue = {},
	link,
	ui,
	route = '/',
	history = createMemoryHistory({initialEntries: [route]}),
}) => ({
	...render(
		<Router history={history}>
			<AppContext.Provider value={contextValue}>
				<MockedProvider
					addTypename={apolloAddTypename}
					link={link}
					mocks={apolloMocks}
				>
					{ui}
				</MockedProvider>
			</AppContext.Provider>
		</Router>
	),
	history,
});
