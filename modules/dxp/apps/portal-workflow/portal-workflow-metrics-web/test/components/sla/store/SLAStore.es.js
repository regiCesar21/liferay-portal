/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, renderHook} from '@testing-library/react-hooks';

import {
	START_NODE_KEYS,
	STOP_NODE_KEYS
} from '../../../../src/main/resources/META-INF/resources/js/components/sla/Constants.es';
import {useSLA} from '../../../../src/main/resources/META-INF/resources/js/components/sla/store/SLAStore.es';

test('Should change SLA form values', () => {
	global.fetch.mockResolvedValueOnce({
		json: async () => ({})
	});

	const nodeKeys = [
		{
			compositeId: '123:enter',
			executionType: 'enter',
			id: 123
		},
		{
			compositeId: '321:enter',
			executionType: 'enter',
			id: 321
		},
		{
			compositeId: '1234:leave',
			executionType: 'leave',
			id: 1234
		}
	];

	const {result} = renderHook(() => useSLA('1', '1'));

	const {
		changeNodesKeys,
		changePauseNodes,
		changeValue,
		filterNodeTagIds,
		pauseNodeTagIds
	} = result.current;

	act(() => changeValue('days', 123));
	expect(result.current.sla.days).toBe(123);
	expect(pauseNodeTagIds(nodeKeys, nodeKeys)).toMatchObject([
		'123:enter',
		'321:enter',
		'1234:leave'
	]);
	expect(filterNodeTagIds(nodeKeys, nodeKeys)).toMatchObject([
		'123:enter',
		'321:enter',
		'1234:leave'
	]);

	act(() =>
		changePauseNodes(nodeKeys, () => {})([
			'123:enter',
			'456:enter',
			'321:enter'
		])
	);
	expect(
		result.current.sla.pauseNodeKeys.nodeKeys.map(({id}) => id)
	).toMatchObject([123, 321]);

	act(() =>
		changeNodesKeys(START_NODE_KEYS, nodeKeys, () => {})([
			'123:enter',
			'456:enter',
			'321:enter'
		])
	);
	expect(
		result.current.sla.startNodeKeys.nodeKeys.map(({id}) => id)
	).toMatchObject([123, 321]);

	act(() =>
		changeNodesKeys(STOP_NODE_KEYS, nodeKeys, () => {})([
			'123:enter',
			'456:enter',
			'321:enter'
		])
	);
	expect(
		result.current.sla.stopNodeKeys.nodeKeys.map(({id}) => id)
	).toMatchObject([123, 321]);
});

test('Should test fetch data', () => {
	const data = {
		calendarKey: '123',
		days: null,
		description: '',
		hours: '',
		name: 'test',
		pauseNodeKeys: {nodeKeys: []},
		startNodeKeys: {nodeKeys: []},
		status: undefined,
		stopNodeKeys: {nodeKeys: []}
	};

	global.fetch.mockResolvedValueOnce({
		json: async () => data
	});

	global.fetch.mockResolvedValueOnce({
		json: async () => data
	});

	const {result, waitForNextUpdate} = renderHook(() => useSLA('1', '1'));

	act(() => result.current.fetchSLA('1'));

	return waitForNextUpdate().then(() => {
		expect(result.current.sla).toMatchObject(data);
	});
});

test('Should test fetch data without some parts', () => {
	const data = {
		days: null,
		hours: '',
		name: 'test',
		processId: ''
	};

	global.fetch.mockResolvedValueOnce({
		json: async () => data
	});

	global.fetch.mockResolvedValueOnce({
		json: async () => data
	});

	const {result, waitForNextUpdate} = renderHook(() => useSLA('1', '1'));

	act(() => result.current.fetchSLA('1'));

	return waitForNextUpdate().then(() => {
		expect(result.current.sla).toMatchObject({
			calendarKey: undefined,
			days: null,
			description: '',
			hours: '',
			name: 'test',
			pauseNodeKeys: {
				nodeKeys: []
			},
			startNodeKeys: {
				nodeKeys: []
			},
			status: undefined,
			stopNodeKeys: {
				nodeKeys: []
			}
		});
	});
});

test('Should test initial state', () => {
	const data = {
		calendarKey: undefined,
		days: null,
		description: '',
		hours: '',
		name: undefined,
		pauseNodeKeys: {nodeKeys: []},
		startNodeKeys: {nodeKeys: []},
		status: undefined,
		stopNodeKeys: {nodeKeys: []}
	};

	global.fetch.mockResolvedValueOnce({
		json: async () => ({})
	});

	global.fetch.mockResolvedValueOnce({
		json: async () => data
	});

	const {result, waitForNextUpdate} = renderHook(() => useSLA('1', '1'));

	act(() => result.current.fetchSLA('1'));

	return waitForNextUpdate().then(() => {
		expect(result.current.sla).toMatchObject(data);
	});
});

test('Should test reset', () => {
	const data = {
		calendarKey: undefined,
		days: null,
		description: '',
		hours: '',
		name: undefined,
		pauseNodeKeys: {nodeKeys: []},
		startNodeKeys: {nodeKeys: []},
		status: undefined,
		stopNodeKeys: {nodeKeys: []}
	};

	global.fetch.mockResolvedValueOnce({
		json: async () => ({})
	});

	global.fetch.mockResolvedValueOnce({
		json: async () => data
	});

	const {result, waitForNextUpdate} = renderHook(() => useSLA('1', '1'));

	act(() => result.current.resetNodes());

	return waitForNextUpdate().then(() => {
		expect(result.current.sla).toMatchObject(data);
	});
});

test('Should test save data', () => {
	const data = {
		calendarKey: undefined,
		days: null,
		description: '',
		hours: '',
		name: 'test',
		pauseNodeKeys: {nodeKeys: []},
		startNodeKeys: {nodeKeys: []},
		status: undefined,
		stopNodeKeys: {nodeKeys: []}
	};

	global.fetch.mockResolvedValueOnce({
		json: async () => data
	});

	global.fetch.mockResolvedValueOnce({
		json: async () => data
	});

	const {result, waitForNextUpdate} = renderHook(() => useSLA('1', '1'));

	act(() => result.current.saveSLA('1', null, null));

	return waitForNextUpdate().then(() => {
		expect(result.current.sla).toMatchObject(data);
	});
});

xtest('Should test update data', async () => {
	const data = {
		calendarKey: undefined,
		days: null,
		description: '',
		hours: '',
		name: 'test',
		pauseNodeKeys: {nodeKeys: []},
		startNodeKeys: {nodeKeys: []},
		status: undefined,
		stopNodeKeys: {nodeKeys: []}
	};

	const {result, waitForNextUpdate} = renderHook(() => useSLA('1', '1'));

	await act(async () => {
		await result.current.saveSLA('1', '1', null);
		await waitForNextUpdate();
	});

	expect(result.current.sla).toMatchObject(data);
});
