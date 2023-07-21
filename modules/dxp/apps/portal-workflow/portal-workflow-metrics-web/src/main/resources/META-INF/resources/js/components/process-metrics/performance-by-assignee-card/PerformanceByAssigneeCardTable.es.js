/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import UserAvatar from '../../../shared/components/user-avatar/UserAvatar.es';
import {formatDuration} from '../../../shared/util/duration.es';

const Item = ({assignee: {image, name}, durationTaskAvg, id, taskCount}) => {
	const formattedDuration = formatDuration(durationTaskAvg);

	return (
		<tr>
			<td className="assignee-name border-0">
				<UserAvatar className="mr-3" image={image} />

				<span>{name || id}</span>
			</td>

			<td className="border-0 text-right">
				<span className="task-count-value">{taskCount}</span>
			</td>
			<td className="border-0 text-right">
				<span className="task-count-value">{formattedDuration}</span>
			</td>
		</tr>
	);
};

const Table = ({items}) => {
	return (
		<div className="mb-3 table-responsive table-scrollable">
			<table className="table table-autofit table-heading-nowrap table-hover table-list">
				<thead>
					<tr>
						<th
							className="table-cell-expand table-head-title"
							style={{width: '60%'}}
						>
							{Liferay.Language.get('assignee-name')}
						</th>

						<th
							className="table-head-title text-right"
							style={{width: '20%'}}
						>
							{Liferay.Language.get('completed-tasks')}
						</th>

						<th
							className="table-head-title text-right"
							style={{width: '20%'}}
						>
							{Liferay.Language.get('average-completion-time')}
						</th>
					</tr>
				</thead>
				<tbody>
					{items.map((item, index) => (
						<Table.Item {...item} key={index} />
					))}
				</tbody>
			</table>
		</div>
	);
};

Table.Item = Item;

export {Table};
