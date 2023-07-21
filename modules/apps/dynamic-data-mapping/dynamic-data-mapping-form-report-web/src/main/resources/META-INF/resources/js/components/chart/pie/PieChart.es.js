/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useState} from 'react';
import {
	Cell,
	Pie,
	PieChart,
	ResponsiveContainer,
	Sector,
	Tooltip,
} from 'recharts';

import colors from '../../../utils/colors.es';
import {roundPercentage} from '../../../utils/data.es';
import Legend from '../Legend.es';
import TooltipContent from '../TooltipContent.es';

const RADIAN = Math.PI / 180;

export default ({data, height, totalEntries, width}) => {
	const [activeIndex, setActiveIndex] = useState(null);
	const [isAnimationActive, setAnimationActive] = useState(true);

	const handleOnMouseOut = () => {
		setActiveIndex(null);
	};

	const handleOnMouseOver = (index) => {
		setActiveIndex(index);
	};

	const ActiveShape = ({
		cx,
		cy,
		endAngle,
		innerRadius,
		outerRadius,
		startAngle,
	}) => {
		setAnimationActive(false);

		return (
			<g>
				<Sector
					cx={cx}
					cy={cy}
					endAngle={endAngle}
					fill={colors(activeIndex)}
					innerRadius={innerRadius}
					onMouseOut={handleOnMouseOut}
					outerRadius={outerRadius + 5}
					startAngle={startAngle}
				/>
			</g>
		);
	};

	const Label = ({cx, cy, innerRadius, midAngle, outerRadius, percent}) => {
		const radius = innerRadius + (outerRadius - innerRadius) * 0.5;
		const x = cx + radius * Math.cos(-midAngle * RADIAN);
		const y = cy + radius * Math.sin(-midAngle * RADIAN);

		return (
			<text
				dominantBaseline="central"
				fill="white"
				textAnchor="middle"
				x={x}
				y={y}
			>
				{roundPercentage(percent)}
			</text>
		);
	};

	return (
		<div className="custom-chart-size pie-chart">
			<ResponsiveContainer
				height={height || '99%'}
				width={width || '50%'}
			>
				<PieChart>
					<Pie
						activeIndex={activeIndex}
						activeShape={ActiveShape}
						cx="50%"
						cy="50%"
						data={data}
						dataKey="count"
						innerRadius={80}
						isAnimationActive={isAnimationActive}
						label={Label}
						labelLine={false}
						nameKey="label"
						onMouseOver={(_, index) => handleOnMouseOver(index)}
						outerRadius={135}
						paddingAngle={0}
					>
						{data.map((_, index) => (
							<Cell
								fill={colors(index)}
								fillOpacity={
									activeIndex !== null &&
									activeIndex !== index
										? 0.5
										: 1
								}
								key={index}
							/>
						))}
					</Pie>

					<Tooltip
						content={
							<TooltipContent
								showBullet={true}
								showHeader={false}
								totalEntries={totalEntries}
							/>
						}
					/>
				</PieChart>
			</ResponsiveContainer>

			<Legend
				activeIndex={activeIndex}
				labels={data.map(({label}) => label)}
				onMouseOut={handleOnMouseOut}
				onMouseOver={handleOnMouseOver}
			/>
		</div>
	);
};
