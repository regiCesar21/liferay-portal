/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {formatDuration} from '../../../shared/util/duration.es';
import {
	getFormattedPercentage,
	isValidNumber,
} from '../../../shared/util/util.es';

const Item = ({
	breachedInstanceCount,
	breachedInstancePercentage,
	durationAvg,
	node: {label},
}) => {
	const formattedDuration = formatDuration(durationAvg);
	const formattedPercentage = getFormattedPercentage(
		breachedInstancePercentage,
		100
	);

	return (
		<tr>
			<td className="table-cell-expand">{label}</td>

			<td className="text-right">
				{isValidNumber(breachedInstanceCount)
					? breachedInstanceCount
					: 0}{' '}
				({formattedPercentage})
			</td>

			<td className="text-right">{formattedDuration}</td>
		</tr>
	);
};

const Table = ({items = []}) => (
	<div className="mb-3 table-responsive table-scrollable">
		<table className="table table-autofit table-heading-nowrap table-hover table-list">
			<thead>
				<tr>
					<th style={{width: '60%'}}>
						{Liferay.Language.get('step-name')}
					</th>

					<th className="text-right" style={{width: '20%'}}>
						{Liferay.Language.get('sla-breached-percent')}
					</th>

					<th className="text-right" style={{width: '20%'}}>
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

Table.Item = Item;

export {Table};
