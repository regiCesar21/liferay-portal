/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useContext, useMemo} from 'react';

import {FilterContext} from '../components/filter/FilterContext.es';
import {useFiltersConstants} from '../components/filter/hooks/useFiltersConstants.es';
import {
	getCapitalizedFilterKey,
	getFilterResults,
	getSelectedItems,
} from '../components/filter/util/filterUtil.es';
import {useRouterParams} from './useRouterParams.es';

const useFilter = ({
	filterKeys = [],
	prefixKeys = [''],
	withoutRouteParams,
}) => {
	const {dispatch, dispatchFilter, filterState, filterValues} = useContext(
		FilterContext
	);

	const {filters} = useRouterParams();
	const {keys, pinnedValues, titles} = useFiltersConstants(filterKeys);

	const prefixedKeys = useMemo(() => {
		const newKeys = [];

		keys.forEach((key) =>
			prefixKeys.forEach((prefix) => {
				newKeys.push(getCapitalizedFilterKey(prefix, key));
			})
		);

		return newKeys;
	}, [keys, prefixKeys]);

	const filterResults = useMemo(
		() => getFilterResults(prefixedKeys, pinnedValues, titles, filterState),
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[filterState, prefixedKeys]
	);

	const hasFilterError = useCallback(
		(filterKey) => {
			const {errors = []} = filterState;

			return errors.includes(filterKey);
		},
		[filterState]
	);

	const filtersError = useMemo(
		() =>
			filterKeys
				.map(hasFilterError)
				.reduce((current, next) => current || next, false),
		[filterKeys, hasFilterError]
	);

	const selectedFilters = useMemo(() => getSelectedItems(filterResults), [
		filterResults,
	]);

	return {
		dispatch,
		dispatchFilter,
		filterState,
		filterValues: withoutRouteParams ? filterValues : filters,
		filtersError,
		hasFilterError,
		prefixedKeys,
		selectedFilters,
	};
};

export {useFilter};
