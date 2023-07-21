/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayManagementToolbar from '@clayui/management-toolbar';
import React from 'react';

import ResultsBar from '../../shared/components/results-bar/ResultsBar.es';
import SearchField from '../../shared/components/search-field/SearchField.es';
import TimeRangeFilter from '../filter/TimeRangeFilter.es';

const Header = ({filterKeys, routeParams, totalCount}) => {
	return (
		<>
			<ClayManagementToolbar className="mb-0">
				<ClayManagementToolbar.ItemList>
					<ClayManagementToolbar.Item>
						<strong className="ml-0 mr-0 navbar-text">
							{Liferay.Language.get('filter-by')}
						</strong>
					</ClayManagementToolbar.Item>
				</ClayManagementToolbar.ItemList>

				<SearchField
					disabled={false}
					placeholder={Liferay.Language.get('search-for-step-name')}
				/>

				<ClayManagementToolbar.ItemList>
					<TimeRangeFilter
						buttonClassName="btn-flat btn-sm"
						options={{position: 'right'}}
					/>
				</ClayManagementToolbar.ItemList>
			</ClayManagementToolbar>

			{routeParams.search && (
				<ResultsBar>
					<>
						<ResultsBar.TotalCount
							search={routeParams.search}
							totalCount={totalCount}
						/>

						<ResultsBar.Clear
							filterKeys={filterKeys}
							{...routeParams}
						/>
					</>
				</ResultsBar>
			)}
		</>
	);
};

export {Header};
