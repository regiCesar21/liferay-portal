/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from 'frontend-js-react-web';
import React from 'react';

import App from './App.es';
import {StoreProvider} from './components/StoreContext.es';

/* eslint-disable no-unused-vars */
let instance = null;

function BOMAdmin(props) {
	return (
		<StoreProvider>
			<App
				ref={(component) => {
					instance = component;
				}}
				{...props}
			/>
		</StoreProvider>
	);
}

export default function (componentId, id, props) {
	const portletFrame = window.document.getElementById(id);

	render(BOMAdmin, props, portletFrame);
}
