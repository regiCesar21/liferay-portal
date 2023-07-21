/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import toDataArray, {sumTotalEntries, toArray} from '../../utils/data.es';
import fieldTypes from '../../utils/fieldTypes.es';
import MultiBarChart from '../chart/bar/MultiBarChart.es';
import SimpleBarChart from '../chart/bar/SimpleBarChart.es';
import PieChart from '../chart/pie/PieChart.es';
import EmptyState from '../empty-state/EmptyState.es';
import List from '../list/List.es';
import Card from './Card.es';

const chartFactory = ({field, structure, summary, totalEntries, values}) => {
	const {options, type} = field;

	switch (type) {
		case 'checkbox_multiple':
			return (
				<SimpleBarChart
					data={toDataArray(options, values)}
					totalEntries={totalEntries}
				/>
			);

		case 'numeric': {
			if (Array.isArray(values)) {
				return (
					<List
						data={toArray(values)}
						field={field}
						summary={summary}
						totalEntries={totalEntries}
					/>
				);
			}
			else {
				return '';
			}
		}

		case 'grid': {
			return (
				<MultiBarChart
					data={values}
					field={field}
					structure={structure}
					totalEntries={totalEntries}
				/>
			);
		}

		case 'radio':
		case 'select':
			return (
				<PieChart
					data={toDataArray(options, values)}
					totalEntries={totalEntries}
				/>
			);
		case 'color':
		case 'date':
		case 'text': {
			if (Array.isArray(values)) {
				return (
					<List
						data={toArray(values)}
						field={field}
						totalEntries={totalEntries}
						type={type}
					/>
				);
			}
			else {
				return '';
			}
		}

		default:
			return null;
	}
};

export default ({data, fields}) => {
	let hasCards = false;

	const cards = fields.map((field, index) => {
		const {values = {}, structure = {}, summary = {}, totalEntries} =
			data[field.name] || {};

		const sumTotalValues = sumTotalEntries(values);

		field = {
			...field,
			...fieldTypes[field.type],
		};

		const chartContent = {
			field,
			structure,
			summary,
			totalEntries: sumTotalValues,
			values,
		};

		const chart = chartFactory(chartContent);

		if (chart === null) {
			return null;
		}
		else {
			hasCards = true;
		}

		return (
			<Card
				field={field}
				index={index}
				key={index}
				summary={summary}
				totalEntries={totalEntries ? totalEntries : sumTotalValues}
			>
				{chart}
			</Card>
		);
	});

	if (!hasCards) {
		return <EmptyState />;
	}

	return cards;
};
