/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ACTIONS,
	PermissionsContext,
} from 'app-builder-web/js/pages/entry/PermissionsContext.es';
import React from 'react';

const defaultActionIds = [
	ACTIONS.ADD_DATA_RECORD,
	ACTIONS.DELETE_DATA_RECORD,
	ACTIONS.UPDATE_DATA_RECORD,
	ACTIONS.VIEW,
	ACTIONS.VIEW_DATA_RECORD,
];

export default function PermissionsContextProviderWrapper({
	children,
	actionIds = defaultActionIds,
}) {
	return (
		<PermissionsContext.Provider value={actionIds}>
			{children}
		</PermissionsContext.Provider>
	);
}
