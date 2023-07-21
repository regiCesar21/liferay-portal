/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React, {useContext, useMemo} from 'react';

import Panel from '../../../shared/components/Panel.es';
import ContentView from '../../../shared/components/content-view/ContentView.es';
import ReloadButton from '../../../shared/components/list/ReloadButton.es';
import PromisesResolver from '../../../shared/components/promises-resolver/PromisesResolver.es';
import ChildLink from '../../../shared/components/router/ChildLink.es';
import {AppContext} from '../../AppContext.es';
import {Table} from './PerformanceByAssigneeCardTable.es';

const Body = ({filtered, items, totalCount}) => {
	const statesProps = useMemo(
		() => ({
			emptyProps: {
				className: 'mt-5 py-8',
				filtered,
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
		}),
		[filtered]
	);

	return (
		<Panel.Body>
			<ContentView {...statesProps}>
				{totalCount > 0 && <Body.Table items={items} />}
			</ContentView>
		</Panel.Body>
	);
};

const Footer = ({processId, processStep, timeRange, totalCount}) => {
	const {defaultDelta} = useContext(AppContext);
	const filters = {};
	const {dateEnd, dateStart, key} = timeRange;

	if (dateEnd && dateStart && key) {
		filters.dateEnd = dateEnd;
		filters.dateStart = dateStart;
		filters.timeRange = [key];
	}

	if (processStep && processStep !== 'allSteps') {
		filters.taskNames = [processStep];
	}

	const viewAllAssigneesQuery = {filters};
	const viewAllAssigneesUrl = `/performance/assignee/${processId}/${defaultDelta}/1/durationTaskAvg:desc/`;

	return (
		<PromisesResolver.Resolved>
			{totalCount > 0 ? (
				<Panel.Footer elementClasses="fixed-bottom">
					<div className="mb-1 text-right">
						<ChildLink
							className="border-0 btn btn-secondary btn-sm"
							query={viewAllAssigneesQuery}
							to={viewAllAssigneesUrl}
						>
							<span className="mr-2">
								{`${Liferay.Language.get(
									'view-all-assignees'
								)} (${totalCount})`}
							</span>

							<ClayIcon symbol="caret-right-l" />
						</ChildLink>
					</div>
				</Panel.Footer>
			) : null}
		</PromisesResolver.Resolved>
	);
};

Body.Table = Table;

export {Body, Footer};
