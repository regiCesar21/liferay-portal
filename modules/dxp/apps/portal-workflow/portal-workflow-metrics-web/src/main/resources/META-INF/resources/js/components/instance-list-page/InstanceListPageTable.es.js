/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
import React, {useEffect} from 'react';

import {useIsAdmin} from '../../shared/hooks/useIsAdmin.es';
import {Item} from './InstanceListPageItem.es';

const Table = ({items, totalCount}) => {
	const {fetchData, isAdmin} = useIsAdmin();

	useEffect(() => {
		fetchData();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<ClayTable>
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell headingCell style={{width: '7%'}} />
					<ClayTable.Cell headingCell style={{width: '8%'}}>
						{Liferay.Language.get('id')}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell style={{width: '17%'}}>
						{Liferay.Language.get('item-subject')}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell style={{width: '18%'}}>
						{Liferay.Language.get('process-step')}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell style={{width: '14%'}}>
						{Liferay.Language.get('assignee')}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell style={{width: '17%'}}>
						{Liferay.Language.get('created-by')}
					</ClayTable.Cell>
					<ClayTable.Cell headingCell style={{width: '18%'}}>
						{Liferay.Language.get('creation-date')}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell style={{width: '5%'}} />
				</ClayTable.Row>
			</ClayTable.Head>

			<ClayTable.Body>
				{items.map((item, index) => (
					<Table.Item
						{...item}
						isAdmin={isAdmin}
						key={index}
						totalCount={totalCount}
					/>
				))}
			</ClayTable.Body>
		</ClayTable>
	);
};

Table.Item = Item;

export {Table};
