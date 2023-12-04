/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React, {useMemo} from 'react';

import Panel from '../../../shared/components/Panel.es';
import PromisesResolver from '../../../shared/components/promises-resolver/PromisesResolver.es';
import {useFetch} from '../../../shared/hooks/useFetch.es';
import {useFilter} from '../../../shared/hooks/useFilter.es';
import TimeRangeFilter from '../../filter/TimeRangeFilter.es';
import {getTimeRangeParams} from '../../filter/util/timeRangeUtil.es';
import {Body, Footer} from './PerformanceByStepCardBody.es';

const Header = ({disableFilters, prefixKey, totalCount}) => (
	<Panel.HeaderWithOptions
		description={Liferay.Language.get('performance-by-step-description')}
		elementClasses="dashboard-panel-header"
		title={Liferay.Language.get('performance-by-step')}
	>
		<ClayLayout.ContentCol className="m-0 management-bar management-bar-light navbar">
			<ul className="navbar-nav">
				<TimeRangeFilter
					disabled={!totalCount || disableFilters}
					options={{position: 'right'}}
					prefixKey={prefixKey}
				/>
			</ul>
		</ClayLayout.ContentCol>
	</Panel.HeaderWithOptions>
);

const PerformanceByStepCard = ({routeParams}) => {
	const {processId} = routeParams;
	const filterKeys = ['timeRange'];
	const prefixKey = 'step';
	const prefixKeys = [prefixKey];

	const {
		filterValues: {stepDateEnd, stepDateStart, stepTimeRange: [key] = []},
		filtersError,
	} = useFilter({
		filterKeys,
		prefixKeys,
	});

	const timeRange = useMemo(
		() => getTimeRangeParams(stepDateStart, stepDateEnd),
		[stepDateEnd, stepDateStart]
	);

	const {data, fetchData} = useFetch({
		params: {
			completed: true,
			page: 1,
			pageSize: 10,
			sort: 'durationAvg:desc',
			...timeRange,
		},
		url: `/processes/${processId}/nodes/metrics`,
	});

	const promises = useMemo(() => {
		if (timeRange.dateEnd && timeRange.dateStart) {
			return [fetchData()];
		}

		return [new Promise((_, reject) => reject(filtersError))];

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [filtersError, routeParams, timeRange.dateEnd, timeRange.dateStart]);

	return (
		<Panel elementClasses="dashboard-card">
			<PromisesResolver promises={promises}>
				<PerformanceByStepCard.Header
					disableFilters={filtersError}
					prefixKey={prefixKey}
					totalCount={data?.totalCount}
				/>

				<PerformanceByStepCard.Body {...data} />

				{data?.totalCount > 0 && (
					<PerformanceByStepCard.Footer
						processId={processId}
						timeRange={{key, ...timeRange}}
						totalCount={data?.totalCount}
					/>
				)}
			</PromisesResolver>
		</Panel>
	);
};

PerformanceByStepCard.Body = Body;
PerformanceByStepCard.Footer = Footer;
PerformanceByStepCard.Header = Header;

export default PerformanceByStepCard;
