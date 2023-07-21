/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

const DataSetDisplayContext = React.createContext({
	actionParameterName: null,
	formId: null,
	formRef: null,
	id: null,
	loadData: () => {},
	modalId: null,
	namespace: null,
	openModal: () => {},
	openSidePanel: () => {},
	selectItems: () => {},
	selectable: false,
	selectedItemsValue: [],
	sidePanelId: null,
	sorting: [],
	updateSorting: () => {},
});

export default DataSetDisplayContext;
