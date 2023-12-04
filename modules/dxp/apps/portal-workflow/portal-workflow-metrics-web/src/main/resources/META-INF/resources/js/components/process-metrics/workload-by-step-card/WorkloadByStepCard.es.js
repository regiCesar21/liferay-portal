/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React, {useMemo} from 'react';

import Panel from '../../../shared/components/Panel.es';
import PromisesResolver from '../../../shared/components/promises-resolver/PromisesResolver.es';
import {useFetch} from '../../../shared/hooks/useFetch.es';
import {Body} from './WorkloadByStepCardBody.es';

const WorkloadByStepCard = ({processId, routeParams}) => {
	const {data, fetchData} = useFetch({
		params: routeParams,
		url: `/processes/${processId}/nodes/metrics`,
	});

	const promises = useMemo(
		() => [fetchData()],

		// eslint-disable-next-line react-hooks/exhaustive-deps
		[routeParams]
	);

	return (
		<PromisesResolver promises={promises}>
			<Panel>
				<ClayLayout.ContainerFluid className="mt-4">
					<Panel.HeaderWithOptions
						description={Liferay.Language.get(
							'workload-by-step-description'
						)}
						elementClasses="dashboard-panel-header"
						title={Liferay.Language.get('workload-by-step')}
						tooltipPosition="bottom"
					/>

					<WorkloadByStepCard.Body {...data} {...routeParams} />
				</ClayLayout.ContainerFluid>
			</Panel>
		</PromisesResolver>
	);
};

WorkloadByStepCard.Body = Body;

export default WorkloadByStepCard;
