/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayEmptyState from '@clayui/empty-state';
import React from 'react';
import {
	BarChart,
	CartesianGrid,
	ResponsiveContainer,
	XAxis,
	YAxis,
} from 'recharts';

import {BAR_CHART} from '../utils/constants';

export default function EmptyAuditBarChart({learnHowURL}) {
	const description = learnHowURL && (
		<div
			dangerouslySetInnerHTML={{
				__html: Liferay.Util.sub(
					Liferay.Language.get(
						'x-learn-how-x-to-tailor-categories-to-your-needs'
					),
					`<a href=${learnHowURL} target="_blank">`,
					'</a>'
				),
			}}
		/>
	);

	return (
		<>
			<ClayEmptyState
				className="empty-state text-center"
				description={description}
				title={Liferay.Language.get('there-is-no-data')}
			/>

			<ResponsiveContainer height={BAR_CHART.emptyHeight}>
				<BarChart
					data={[]}
					height={BAR_CHART.emptyHeight}
					width={BAR_CHART.width}
				>
					<CartesianGrid stroke={BAR_CHART.stroke} />
					<XAxis
						axisLine={{
							stroke: BAR_CHART.stroke,
						}}
						tickLine={false}
					/>
					<YAxis
						axisLine={{
							stroke: BAR_CHART.stroke,
						}}
						domain={[0, 40]}
						tickLine={false}
					/>
				</BarChart>
			</ResponsiveContainer>
		</>
	);
}
