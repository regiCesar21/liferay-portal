/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {createContext, useEffect, useState} from 'react';

import {getItem} from '../../utils/client.es';

const PermissionsContext = createContext();

const ACTIONS = {
	ADD_DATA_RECORD: 'ADD_DATA_RECORD',
	DELETE_DATA_RECORD: 'DELETE_DATA_RECORD',
	UPDATE_DATA_RECORD: 'UPDATE_DATA_RECORD',
	VIEW: 'VIEW',
	VIEW_DATA_RECORD: 'VIEW_DATA_RECORD',
};

const PermissionsContextProvider = ({children, dataDefinitionId}) => {
	const [actionIds, setActionIds] = useState([]);

	useEffect(() => {
		getItem(
			`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}/data-record-collection`
		)
			.then(({id: dataRecordCollectionId}) =>
				getItem(
					`/o/data-engine/v2.0/data-record-collections/${dataRecordCollectionId}/permissions/by-current-user`
				)
			)
			.then((actionIds) => setActionIds(actionIds))
			.catch((_) => setActionIds([]));
	}, [dataDefinitionId]);

	return (
		<PermissionsContext.Provider value={actionIds}>
			{children}
		</PermissionsContext.Provider>
	);
};

export {ACTIONS, PermissionsContext, PermissionsContextProvider};
