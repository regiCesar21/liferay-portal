/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '../css/main.scss';

import 'clay-css/lib/css/atlas.css';
import {render} from 'frontend-js-react-web';
import React from 'react';

import Container from './Container';

window.Liferay = {
	Language: {
		available: {
			en_US: 'aosidopaisd',
			es_ES: 'aosidopaisd',
		},
	},
};

export default function (id) {
	render(<Container />, document.getElementById(id));
}
