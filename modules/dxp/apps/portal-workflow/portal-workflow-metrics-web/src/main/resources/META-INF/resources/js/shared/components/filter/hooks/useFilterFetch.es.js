/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useContext, useEffect} from 'react';

import {AppContext} from '../../../../components/AppContext.es';
import {FilterContext} from '../FilterContext.es';
import {
	buildFilterItems,
	getCapitalizedFilterKey,
	mergeItemsArray,
} from '../util/filterUtil.es';
import {useFilterState} from './useFilterState.es';

const useFilterFetch = ({
	filterKey,
	labelPropertyName = 'label',
	prefixKey,
	requestBody: data = {},
	propertyKey,
	requestMethod: method = 'get',
	requestParams: params = {},
	requestUrl: url,
	staticData,
	staticItems,
	withoutRouteParams,
}) => {
	const {client} = useContext(AppContext);
	const {dispatchFilterError} = useContext(FilterContext);
	const {items, selectedItems, selectedKeys, setItems} = useFilterState(
		getCapitalizedFilterKey(prefixKey, filterKey),
		withoutRouteParams
	);

	const parseResponse = ({data = {}}) => {
		data.items.sort((current, next) =>
			current[labelPropertyName]?.localeCompare(next[labelPropertyName])
		);

		const mergedItems = mergeItemsArray(staticItems, data.items);

		const mappedItems = buildFilterItems({
			items: mergedItems,
			propertyKey,
			selectedKeys,
		});

		setItems(mappedItems);
	};

	useEffect(
		() => {
			dispatchFilterError(filterKey, true);

			if (staticData) {
				parseResponse({data: {items: staticData}});
			}
			else {
				client
					.request({data, method, params, url})
					.then(parseResponse)
					.catch(() => dispatchFilterError(filterKey));
			}
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[]
	);

	return {items, selectedItems};
};

export {useFilterFetch};
