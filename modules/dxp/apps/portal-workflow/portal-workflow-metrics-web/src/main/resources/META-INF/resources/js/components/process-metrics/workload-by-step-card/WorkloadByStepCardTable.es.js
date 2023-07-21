/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import ListHeadItem from '../../../shared/components/list/ListHeadItem.es';
import {Item} from './WorkloadByStepCardItem.es';

const Table = ({items, processId}) => {
	const onTimeTitle = Liferay.Language.get('on-time');
	const overdueTitle = Liferay.Language.get('overdue');
	const stepNameTitle = Liferay.Language.get('step-name');
	const totalPendingTitle = Liferay.Language.get('total-pending');

	return (
		<div className="table-responsive">
			<table className="show-quick-actions-on-hover table table-autofit table-heading-nowrap table-hover table-list">
				<thead>
					<tr>
						<th className="table-cell-expand table-head-title">
							{stepNameTitle}
						</th>

						<th className="table-head-title text-right">
							<ListHeadItem
								iconColor="danger"
								iconName="exclamation-circle"
								name="overdueInstanceCount"
								title={overdueTitle}
							/>
						</th>

						<th className="table-head-title text-right">
							<ListHeadItem
								iconColor="success"
								iconName="check-circle"
								name="onTimeInstanceCount"
								title={onTimeTitle}
							/>
						</th>

						<th className="table-head-title text-right">
							<ListHeadItem
								name="instanceCount"
								title={totalPendingTitle}
							/>
						</th>
					</tr>
				</thead>

				<tbody>
					{items.map((step, index) => (
						<Table.Item
							{...step}
							key={index}
							processId={processId}
						/>
					))}
				</tbody>
			</table>
		</div>
	);
};

Table.Item = Item;
export {Table};
