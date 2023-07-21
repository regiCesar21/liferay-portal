/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayManagementToolbar from '@clayui/management-toolbar';
import React from 'react';

import filterConstants from '../../shared/components/filter/util/filterConstants.es';
import ResultsBar from '../../shared/components/results-bar/ResultsBar.es';
import SearchField from '../../shared/components/search-field/SearchField.es';
import ProcessStepFilter from '../filter/ProcessStepFilter.es';
import RoleFilter from '../filter/RoleFilter.es';
import TimeRangeFilter from '../filter/TimeRangeFilter.es';

const Header = ({filterKeys, routeParams, selectedFilters, totalCount}) => {
	const showFiltersResult = routeParams.search || selectedFilters.length > 0;

	return (
		<>
			<ClayManagementToolbar className="mb-0">
				<ClayManagementToolbar.ItemList>
					<ClayManagementToolbar.Item>
						<strong className="ml-0 mr-0 navbar-text">
							{Liferay.Language.get('filter-by')}
						</strong>
					</ClayManagementToolbar.Item>

					<RoleFilter
						completed={true}
						filterKey={filterConstants.roles.key}
						processId={routeParams.processId}
					/>

					<ProcessStepFilter
						filterKey={filterConstants.processStep.key}
						processId={routeParams.processId}
					/>
				</ClayManagementToolbar.ItemList>

				<SearchField
					disabled={false}
					placeholder={Liferay.Language.get(
						'search-for-assignee-name'
					)}
				/>
				<ClayManagementToolbar.ItemList>
					<TimeRangeFilter
						buttonClassName="btn-flat btn-sm"
						options={{position: 'right'}}
					/>
				</ClayManagementToolbar.ItemList>
			</ClayManagementToolbar>

			{showFiltersResult && (
				<ResultsBar>
					<ResultsBar.TotalCount
						search={routeParams.search}
						totalCount={totalCount}
					/>

					<ResultsBar.FilterItems
						filters={selectedFilters}
						{...routeParams}
					/>

					<ResultsBar.Clear
						filterKeys={filterKeys}
						filters={selectedFilters}
						{...routeParams}
					/>
				</ResultsBar>
			)}
		</>
	);
};

export {Header};
