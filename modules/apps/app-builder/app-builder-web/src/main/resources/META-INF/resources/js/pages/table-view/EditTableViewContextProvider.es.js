/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useReducer} from 'react';
import {withRouter} from 'react-router-dom';

import {getItem} from '../../utils/client.es';
import EditTableViewContext, {
	UPDATE_DATA_DEFINITION,
	UPDATE_DATA_LIST_VIEW,
	UPDATE_FIELD_TYPES,
	initialState,
	reducer,
} from './EditTableViewContext.es';

export default withRouter(({children, match}) => {
	const [state, dispatch] = useReducer(reducer, initialState);

	const {dataDefinitionId, dataListViewId} = match.params;

	useEffect(() => {
		getItem(
			`/o/data-engine/v2.0/data-definitions/data-definition-fields/field-types`
		).then((fieldTypes) => {
			dispatch({payload: {fieldTypes}, type: UPDATE_FIELD_TYPES});
		});
	}, []);

	useEffect(() => {
		getItem(
			`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}`
		).then((dataDefinition) => {
			dispatch({
				payload: {dataDefinition},
				type: UPDATE_DATA_DEFINITION,
			});
		});
	}, [dataDefinitionId]);

	useEffect(() => {
		if (dataListViewId) {
			getItem(
				`/o/data-engine/v2.0/data-list-views/${dataListViewId}`
			).then((dataListView) => {
				dispatch({
					payload: {dataListView},
					type: UPDATE_DATA_LIST_VIEW,
				});
			});
		}
	}, [dataListViewId]);

	return (
		<EditTableViewContext.Provider value={[state, dispatch]}>
			{children}
		</EditTableViewContext.Provider>
	);
});
