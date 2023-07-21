/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import CardShortcut from './components/card-shortcut/CardShortcut.es';
import CardList from './components/card/CardList.es';
import Sidebar from './components/sidebar/Sidebar.es';
import {SidebarContextProvider} from './components/sidebar/SidebarContext.es';

export default ({
	data,
	fields,
	formReportRecordsFieldValuesURL,
	portletNamespace,
}) => (
	<SidebarContextProvider
		formReportRecordsFieldValuesURL={formReportRecordsFieldValuesURL}
		portletNamespace={portletNamespace}
	>
		<div className="report-cards-area" key="report-cards">
			<CardList data={data} fields={fields} />
		</div>

		<div className="report-cards-shortcut" key="report-cards-shortcut">
			<CardShortcut fields={fields} />
		</div>

		<Sidebar />
	</SidebarContextProvider>
);
