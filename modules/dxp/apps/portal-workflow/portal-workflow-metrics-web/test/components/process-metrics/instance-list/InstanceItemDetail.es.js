/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render, waitForElement} from '@testing-library/react';
import React from 'react';

import InstanceItemDetail from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/instance-list/InstanceItemDetail.es';
import {InstanceListContext} from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/instance-list/store/InstanceListStore.es';
import {MockRouter} from '../../../mock/MockRouter.es';

describe('The instance item detail should', () => {
	const baseInstance = {
		assetTitle: 'New Post',
		assetType: 'Blog',
		creatorUser: {
			name: 'User 1'
		},
		dateCreated: new Date('2019-01-03'),
		id: 1,
		taskNames: ['Update']
	};

	afterEach(cleanup);

	test('Be rendered with empty data when no instanceId is defined', async () => {
		global.fetch.mockResolvedValueOnce({
			json: async () => ({
				...baseInstance,
				slaStatus: 'Untracked',
				status: 'Completed',
				...[{status: 'Running'}]
			})
		});

		const {getAllByTestId} = render(
			<MockRouter>
				<InstanceListContext.Provider value={{instanceId: null}}>
					<InstanceItemDetail processId="12345" />
				</InstanceListContext.Provider>
			</MockRouter>
		);

		const instanceDetailSpans = await waitForElement(() =>
			getAllByTestId('instanceDetailSpan')
		);

		expect(instanceDetailSpans[0].innerHTML).toBe('');
		expect(instanceDetailSpans[1].innerHTML).toBe('');
		expect(instanceDetailSpans[2].innerHTML).toBe('');
		expect(instanceDetailSpans[3].innerHTML).toBe('');
		expect(instanceDetailSpans[4].innerHTML).toBe('');
	});

	test('Be rendered with "OnTime" and "Completed" statuses', async () => {
		global.fetch.mockResolvedValueOnce({
			json: async () => ({
				...baseInstance,
				slaStatus: 'OnTime',
				status: 'Completed',
				...{
					dateCompletion: new Date('2019-01-07')
				}
			})
		});

		const {getAllByTestId} = render(
			<MockRouter>
				<InstanceListContext.Provider value={{instanceId: 12345}}>
					<InstanceItemDetail processId="12345" />
				</InstanceListContext.Provider>
			</MockRouter>
		);

		const instanceDetailSpans = await waitForElement(() =>
			getAllByTestId('instanceDetailSpan')
		);

		expect(instanceDetailSpans[0].innerHTML).toBe('Completed');
		expect(instanceDetailSpans[1].innerHTML).toBe('User 1');
		expect(instanceDetailSpans[2].innerHTML).toBe('Blog');
		expect(instanceDetailSpans[3].innerHTML).toBe('New Post');
	});

	test('Be rendered with "OnTime" and "Pending" statuses', async () => {
		global.fetch.mockResolvedValueOnce({
			json: async () => ({
				...baseInstance,
				slaStatus: 'Overdue',
				status: 'Pending'
			})
		});

		const {getAllByTestId} = render(
			<MockRouter>
				<InstanceListContext.Provider value={{instanceId: 12345}}>
					<InstanceItemDetail processId="12345" />
				</InstanceListContext.Provider>
			</MockRouter>
		);

		const instanceDetailSpans = await waitForElement(() =>
			getAllByTestId('instanceDetailSpan')
		);

		expect(instanceDetailSpans[0].innerHTML).toBe('Pending');
		expect(instanceDetailSpans[4].innerHTML).toBe('Update');
	});

	test('Be rendered with "Overdue" and "Pending" statuses', async () => {
		global.fetch.mockResolvedValueOnce({
			json: async () => ({
				...baseInstance,
				slaStatus: 'Overdue',
				status: 'Pending'
			})
		});

		const {getAllByTestId} = render(
			<MockRouter>
				<InstanceListContext.Provider value={{instanceId: 12345}}>
					<InstanceItemDetail processId="12345" />
				</InstanceListContext.Provider>
			</MockRouter>
		);

		const instanceDetailSpans = await waitForElement(() =>
			getAllByTestId('instanceDetailSpan')
		);

		expect(instanceDetailSpans[0].innerHTML).toBe('Pending');
	});

	test('Be rendered with "Untracked" and "Paused" statuses', async () => {
		global.fetch.mockResolvedValueOnce({
			json: async () => ({
				...baseInstance,
				slaStatus: 'Untracked',
				status: 'Paused'
			})
		});

		const {getAllByTestId} = render(
			<MockRouter>
				<InstanceListContext.Provider value={{instanceId: 12345}}>
					<InstanceItemDetail processId="12345" />
				</InstanceListContext.Provider>
			</MockRouter>
		);

		const instanceDetailSpans = await waitForElement(() =>
			getAllByTestId('instanceDetailSpan')
		);

		expect(instanceDetailSpans[0].innerHTML).toBe('Paused');
	});

	test('Be rendered with "Untracked", "Completed", and "Running" statuses', async () => {
		global.fetch.mockResolvedValueOnce({
			json: async () => ({
				...baseInstance,
				slaResults: [
					{
						status: 'Running'
					}
				],
				slaStatus: 'Untracked',
				status: 'Completed'
			})
		});

		const {getAllByTestId} = render(
			<MockRouter>
				<InstanceListContext.Provider value={{instanceId: 12345}}>
					<InstanceItemDetail processId="12345" />
				</InstanceListContext.Provider>
			</MockRouter>
		);

		const instanceDetailSpans = await waitForElement(() =>
			getAllByTestId('instanceDetailSpan')
		);

		expect(instanceDetailSpans[0].innerHTML).toBe('Completed');
	});

	test('Be rendered with "Untracked", "Completed", and "Stopped" statuses', async () => {
		global.fetch.mockResolvedValueOnce({
			json: async () => ({
				...baseInstance,
				slaResults: [
					{
						status: 'Stopped'
					}
				],
				slaStatus: 'Untracked',
				status: 'Completed'
			})
		});

		const {getAllByTestId} = render(
			<MockRouter>
				<InstanceListContext.Provider value={{instanceId: 12345}}>
					<InstanceItemDetail processId="12345" />
				</InstanceListContext.Provider>
			</MockRouter>
		);

		const instanceDetailSpans = await waitForElement(() =>
			getAllByTestId('instanceDetailSpan')
		);

		expect(instanceDetailSpans[0].innerHTML).toBe('Completed');
	});
});
