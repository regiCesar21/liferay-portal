/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from 'frontend-js-react-web';
import React from 'react';

import apiEndpointDefinitions from '../../../../../../dev/apiEndpointDefinitions';
import App from './App.es';
import {StoreProvider} from './components/StoreContext.es';

import 'clay-css/src/scss/atlas.scss';

import '../css/main.scss';

window.Liferay = {
	authToken: 'fakeToken',
};

const fakeData = {
	areaApiUrl: apiEndpointDefinitions.AREA,
	areaId: 'asd',
	id: 'adminPartFinder',
	productApiUrl: apiEndpointDefinitions.PRODUCTS,
	spritemap: '/test-icons.svg',
};

render(
	<StoreProvider>
		<App {...fakeData} />
	</StoreProvider>,
	document.getElementById('root')
);
