/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useContext} from 'react';

import {
	ACTIONS,
	PermissionsContext,
} from '../pages/entry/PermissionsContext.es';

export default function usePermissions() {
	const actionIds = useContext(PermissionsContext);

	return {
		add: actionIds.includes(ACTIONS.ADD_DATA_RECORD),
		delete: actionIds.includes(ACTIONS.DELETE_DATA_RECORD),
		update: actionIds.includes(ACTIONS.UPDATE_DATA_RECORD),
		view: actionIds.includes(ACTIONS.VIEW_DATA_RECORD),
	};
}
