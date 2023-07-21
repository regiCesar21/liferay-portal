/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import React, {useContext} from 'react';

import {AppContext} from '../../AppContext.es';
import {withLoading} from '../loading/Loading.es';
import SearchContext from '../management-toolbar/SearchContext.es';
import {withEmpty} from './EmptyState.es';
import Table from './Table.es';

const TableWithPagination = ({
	actions,
	columns,
	editMode,
	items,
	noActionsMessage,
	totalCount,
}) => {
	const {deltaValues = [4, 8, 20, 40, 60]} = useContext(AppContext);
	const [{page, pageSize}, dispatch] = useContext(SearchContext);

	const deltas = deltaValues.map((label) => ({label}));

	return (
		<ClayLayout.ContainerFluid>
			<Table
				actions={actions}
				columns={columns}
				editMode={editMode}
				items={items}
				noActionsMessage={noActionsMessage}
			/>

			{totalCount > deltaValues[0] && (
				<div className="taglib-search-iterator-page-iterator-bottom">
					<ClayPaginationBarWithBasicItems
						activeDelta={Number(pageSize)}
						activePage={Number(page)}
						deltas={deltas}
						ellipsisBuffer={3}
						onDeltaChange={(pageSize) =>
							dispatch({pageSize, type: 'CHANGE_PAGE_SIZE'})
						}
						onPageChange={(page) =>
							dispatch({page, type: 'CHANGE_PAGE'})
						}
						totalItems={totalCount}
					/>
				</div>
			)}
		</ClayLayout.ContainerFluid>
	);
};

export default withLoading(withEmpty(TableWithPagination));
