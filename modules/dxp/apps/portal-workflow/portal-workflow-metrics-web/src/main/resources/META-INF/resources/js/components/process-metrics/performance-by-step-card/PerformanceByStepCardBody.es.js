/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React, {useContext} from 'react';

import Panel from '../../../shared/components/Panel.es';
import ContentView from '../../../shared/components/content-view/ContentView.es';
import ReloadButton from '../../../shared/components/list/ReloadButton.es';
import ChildLink from '../../../shared/components/router/ChildLink.es';
import {AppContext} from '../../AppContext.es';
import {Table} from './PerformanceByStepCardTable.es';

const Body = ({items, totalCount}) => {
	const statesProps = {
		emptyProps: {
			className: 'mt-5 py-8',
			hideAnimation: true,
			messageClassName: 'small',
		},
		errorProps: {
			actionButton: <ReloadButton />,
			className: 'mt-4 py-8',
			hideAnimation: true,
			message: Liferay.Language.get(
				'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
			),
			messageClassName: 'small',
		},
		loadingProps: {className: 'mt-4 py-8'},
	};

	return (
		<Panel.Body>
			<ContentView {...statesProps}>
				{totalCount > 0 && <Body.Table items={items} />}
			</ContentView>
		</Panel.Body>
	);
};

const Footer = ({processId, timeRange, totalCount}) => {
	const {defaultDelta} = useContext(AppContext);
	const filters = {};

	const {dateEnd, dateStart, key} = timeRange;
	if (dateEnd && dateStart && key) {
		filters.dateEnd = dateEnd;
		filters.dateStart = dateStart;
		filters.timeRange = [key];
	}

	const viewAllStepsQuery = {filters};
	const viewAllStepsUrl = `/performance/step/${processId}/${defaultDelta}/1/breachedInstancePercentage:desc`;

	return (
		<Panel.Footer elementClasses="fixed-bottom">
			<div className="mb-1 text-right">
				<ChildLink
					className="border-0 btn btn-secondary btn-sm"
					query={viewAllStepsQuery}
					to={viewAllStepsUrl}
				>
					<span className="mr-2">
						{`${Liferay.Language.get(
							'view-all-steps'
						)} (${totalCount})`}
					</span>

					<ClayIcon symbol="caret-right-l" />
				</ChildLink>
			</div>
		</Panel.Footer>
	);
};

Body.Table = Table;

export {Body, Footer};
