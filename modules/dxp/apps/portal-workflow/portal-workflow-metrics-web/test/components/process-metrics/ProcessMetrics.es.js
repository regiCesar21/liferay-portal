/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetchMock from 'jest-fetch-mock';
import React from 'react';

import ProcessMetrics from '../../../src/main/resources/META-INF/resources/js/components/process-metrics/ProcessMetrics.es';
import PendingItemsCard from '../../../src/main/resources/META-INF/resources/js/components/process-metrics/process-items/PendingItemsCard.es';
import WorkloadByStepCard from '../../../src/main/resources/META-INF/resources/js/components/process-metrics/workload-by-step/WorkloadByStepCard.es';
import {withParams} from '../../../src/main/resources/META-INF/resources/js/shared/components/router/routerUtil.es';
import {MockRouter as Router} from '../../mock/MockRouter.es';

beforeAll(() => {
	const vbody = document.createElement('div');

	vbody.innerHTML = `
		<div id="workflow">
			<div class="user-control-group">
				<div class="control-menu-icon"></div>
			</div>
		</div>
	`;
	document.body.appendChild(vbody);
});

beforeEach(() => {
	fetchMock.resetMocks(); // Reset mock before each test
});

test('Should render component with completed tab activated', async () => {
	fetchMock.mockRejectOnce(new Error());

	const component = mount(
		<Router initialPath="/metrics/35315">
			<ProcessMetrics processId={35315} />
		</Router>
	);

	await component.update();

	expect(component).toMatchSnapshot();
});

test('Should render component with default tab activated', async () => {
	global.fetch.mockResolvedValueOnce({
		json: async () => ({
			id: 35315,
			onTimeInstanceCount: 1,
			overdueInstanceCount: 0,
			title: 'Single Approver',
			totalCount: 1
		})
	});

	const component = mount(
		<Router initialPath="/metrics/35315">
			<ProcessMetrics processId={35315} />
		</Router>
	);

	await component.update();

	expect(component).toMatchSnapshot();
});

test('Should render component with failure state', async () => {
	fetchMock.mockRejectOnce(new Error());

	const component = mount(
		<Router initialPath="/metrics/35315/completed">
			<ProcessMetrics processId={35315} />
		</Router>
	);

	await component.update();

	expect(component).toMatchSnapshot();
});

test('Should render dashboard route children', () => {
	global.fetch.mockResolvedValueOnce({
		json: async () => ({})
	});

	const component = mount(
		<Router>
			{withParams(
				PendingItemsCard,
				WorkloadByStepCard
			)({
				location: {
					search: ''
				},
				match: {
					params: {
						processId: 35315
					}
				}
			})}
		</Router>
	);

	expect(component).toMatchSnapshot();
});

test('Should render with blocked SLA', () => {
	fetchMock.mockRejectOnce(new Error());

	const component = mount(
		<Router initialPath="/metrics/35315/completed">
			<ProcessMetrics processId="123" />
		</Router>
	);

	const instance = component.find(ProcessMetrics).instance();

	instance.setState({blockedSLACount: 1}, () => {
		expect(component).toMatchSnapshot();
	});
});
