/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DataDefinitionUtils} from 'data-engine-taglib';
import {useEffect, useState} from 'react';

import {getItem} from '../utils/client.es';
import {errorToast} from '../utils/toast.es';

export default function useDataListView(
	dataListViewId,
	dataDefinitionId,
	withPermission
) {
	const [state, setState] = useState({
		columns: [],
		dataDefinition: null,
		dataListView: {
			fieldNames: [],
		},
		isLoading: true,
	});

	useEffect(() => {
		if (withPermission) {
			Promise.all([
				getItem(
					`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}`
				),
				getItem(
					`/o/data-engine/v2.0/data-list-views/${dataListViewId}`
				),
			])
				.then(([dataDefinition, dataListView]) => {
					setState((prevState) => ({
						...prevState,
						columns: dataListView.fieldNames.map((column) => {
							const {
								label: value,
							} = DataDefinitionUtils.getDataDefinitionField(
								dataDefinition,
								column
							);

							return {
								key: 'dataRecordValues/' + column,
								sortable: true,
								value,
							};
						}),
						dataDefinition: {
							...prevState.dataDefinition,
							...dataDefinition,
						},
						dataListView: {
							...prevState.dataListView,
							...dataListView,
						},
						isLoading: false,
					}));
				})
				.catch(() => {
					setState((prevState) => ({
						...prevState,
						isLoading: false,
					}));

					errorToast();
				});
		}
	}, [dataDefinitionId, dataListViewId, withPermission]);

	return state;
}
