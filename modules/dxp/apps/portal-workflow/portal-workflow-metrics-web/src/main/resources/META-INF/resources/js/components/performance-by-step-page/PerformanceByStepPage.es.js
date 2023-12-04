/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useMemo} from 'react';

import PromisesResolver from '../../shared/components/promises-resolver/PromisesResolver.es';
import {parse} from '../../shared/components/router/queryString.es';
import {useFetch} from '../../shared/hooks/useFetch.es';
import {useFilter} from '../../shared/hooks/useFilter.es';
import {useProcessTitle} from '../../shared/hooks/useProcessTitle.es';
import {useTimeRangeFetch} from '../filter/hooks/useTimeRangeFetch.es';
import {getTimeRangeParams} from '../filter/util/timeRangeUtil.es';
import {Body} from './PerformanceByStepPageBody.es';
import {Header} from './PerformanceByStepPageHeader.es';

const PerformanceByStepPage = ({query, routeParams}) => {
	useTimeRangeFetch();

	const {processId, ...paginationParams} = routeParams;
	const {search = null} = parse(query);

	useProcessTitle(processId, Liferay.Language.get('performance-by-step'));

	const {
		filterValues: {dateEnd, dateStart},
		prefixedKeys,
	} = useFilter({});

	const timeRange = useMemo(() => getTimeRangeParams(dateStart, dateEnd), [
		dateEnd,
		dateStart,
	]);

	const {data, fetchData} = useFetch({
		params: {
			completed: true,
			key: search,
			...paginationParams,
			...timeRange,
		},
		url: `/processes/${processId}/nodes/metrics`,
	});

	const promises = useMemo(
		() => [fetchData()],

		// eslint-disable-next-line react-hooks/exhaustive-deps
		[routeParams]
	);

	return (
		<PromisesResolver promises={promises}>
			<PerformanceByStepPage.Header
				filterKeys={prefixedKeys}
				routeParams={{...routeParams, search}}
				totalCount={data?.totalCount}
			/>

			<PerformanceByStepPage.Body {...data} filtered={search} />
		</PromisesResolver>
	);
};

PerformanceByStepPage.Body = Body;
PerformanceByStepPage.Header = Header;

export default PerformanceByStepPage;
