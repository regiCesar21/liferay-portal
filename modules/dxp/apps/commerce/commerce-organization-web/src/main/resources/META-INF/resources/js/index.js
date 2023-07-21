/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from 'frontend-js-react-web';

import OrgChartContainer from './components/OrgChartContainer';

export default function (id, props) {
	const portletFrame = window.document.getElementById(id);

	render(OrgChartContainer, props, portletFrame);
}
