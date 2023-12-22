/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, waitForElement} from '@testing-library/react';
import {act, renderHook} from '@testing-library/react-hooks';
import React, {useContext} from 'react';

import {
	TimeRangeContext,
	TimeRangeProvider,
	useTimeRange
} from '../../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/store/TimeRangeStore.es';
import Request from '../../../../../src/main/resources/META-INF/resources/js/shared/components/request/Request.es';
import {MockRouter} from '../../../../mock/MockRouter.es';

const items = [
	{
		dateEnd: new Date('2019-01-31'),
		dateStart: new Date('2019-01-24'),
		defaultTimeRange: true,
		id: 1,
		key: '1',
		name: 'Last 7 Days'
	},
	{
		dateEnd: new Date('2019-01-31'),
		dateStart: new Date('2019-01-01'),
		id: 2,
		key: '2',
		name: 'Last Month'
	}
];

const MockTimeRangeConsumer = () => {
	const {timeRanges} = useContext(TimeRangeContext);

	return timeRanges.map((timeRange, index) => (
		<span data-testId="timeRangeKey" key={index}>
			{timeRange.name}
		</span>
	));
};

const MockAppContext = ({children}) => (
	<MockRouter>
		<Request>{children}</Request>
	</MockRouter>
);

describe('The custom time range name should', () => {
	test('Be "invalid date" if the dates are null', async () => {
		global.fetch.mockResolvedValue({
			json: async () => ({items: []})
		});

		const {result, unmount, waitForNextUpdate} = renderHook(
			() => useTimeRange([]),
			{wrapper: MockAppContext}
		);

		await act(async () => {
			await waitForNextUpdate();
		});

		const customTimeRange = result.current.timeRanges[0];

		expect(customTimeRange.resultName(customTimeRange)).toBe(
			'Invalid date - Invalid date'
		);

		unmount();
	});

	test('Be the formatted dates if the dates are setted', async () => {
		global.fetch.mockResolvedValue({
			json: async () => ({
				items
			})
		});

		const {result, unmount, waitForNextUpdate} = renderHook(
			() => useTimeRange([]),
			{wrapper: MockAppContext}
		);

		await waitForNextUpdate();

		const {setTimeRanges, timeRanges} = result.current;

		const customTimeRange = {
			...timeRanges[0],
			dateEnd: new Date('2019-01-15'),
			dateStart: new Date('2019-01-09')
		};
		timeRanges.shift();

		setTimeRanges([customTimeRange, ...timeRanges]);

		expect(customTimeRange.resultName(customTimeRange)).toBe(
			'Jan 9, 2019 - Jan 15, 2019'
		);

		unmount();
	});
});

describe('The selected time range should', () => {
	test('Be empty when there is no initial key', async () => {
		global.fetch.mockResolvedValue({
			json: async () => ({
				items
			})
		});

		const {result, unmount, waitForNextUpdate} = renderHook(
			() => useTimeRange([]),
			{wrapper: MockAppContext}
		);

		await waitForNextUpdate();

		const selectedTimeRange = result.current.getSelectedTimeRange();

		expect(selectedTimeRange).toBeNull();

		unmount();
	});

	test('Be "Custom Range" when the initial key is "custom"', async () => {
		global.fetch.mockResolvedValue({
			json: async () => ({
				items
			})
		});

		const {result, unmount, waitForNextUpdate} = renderHook(
			() => useTimeRange(['custom']),
			{wrapper: MockAppContext}
		);

		await waitForNextUpdate();

		const selectedTimeRange = result.current.getSelectedTimeRange();

		expect(selectedTimeRange.name).toBe('custom-range');

		unmount();
	});

	test('Be "Last Month" when the initial key is "2"', async () => {
		global.fetch.mockResolvedValue({
			json: async () => ({
				items
			})
		});

		const {result, unmount, waitForNextUpdate} = renderHook(
			() => useTimeRange(['2']),
			{wrapper: MockAppContext}
		);

		await waitForNextUpdate();

		const selectedTimeRange = result.current.getSelectedTimeRange();

		expect(selectedTimeRange.name).toBe('Last Month');

		unmount();
	});
});

describe('The time range store, when receiving "Last 7 Days" and "Last Month" items, should', () => {
	test('Keep the selected time range when the keys are the same', async () => {
		global.fetch.mockResolvedValueOnce({
			json: async () => ({
				items
			})
		});

		const {result, unmount, waitForNextUpdate} = renderHook(
			() => useTimeRange(['1']),
			{wrapper: MockAppContext}
		);

		await waitForNextUpdate();

		const selectedTimeRange = result.current.getSelectedTimeRange();

		expect(selectedTimeRange.key).toBe('1');

		unmount();
	});

	test('Update the selected time range when the keys changed', async () => {
		global.fetch.mockResolvedValueOnce({
			json: async () => ({
				items
			})
		});

		const {rerender, result, unmount, waitForNextUpdate} = renderHook(
			timeRangeKeys => useTimeRange(timeRangeKeys),
			{
				initialProps: ['1'],
				wrapper: MockAppContext
			}
		);

		await waitForNextUpdate();

		const selectedTimeRange1 = result.current.getSelectedTimeRange();

		expect(selectedTimeRange1.key).toBe('1');

		rerender(['2']);

		await waitForNextUpdate();

		const selectedTimeRange2 = result.current.getSelectedTimeRange();

		expect(selectedTimeRange2.key).toBe('2');

		unmount();
	});
});

describe('The time range store, when receiving no items, should', () => {
	test('Have only the "Custom Range" item', async () => {
		global.fetch.mockResolvedValue({
			json: async () => ({items: []})
		});

		const {result, unmount, waitForNextUpdate} = renderHook(
			({timeRangeKeys}) => useTimeRange(timeRangeKeys),
			{
				initialProps: {
					timeRangeKeys: ['custom']
				},
				wrapper: MockAppContext
			}
		);

		await waitForNextUpdate();

		expect(result.current.timeRanges[0].key).toBe('custom');

		unmount();
	});

	test('Return a fallback object of selected item', () => {
		global.fetch.mockResolvedValue({
			json: async () => ({items: []})
		});

		const {result, unmount} = renderHook(
			({timeRangeKeys}) => useTimeRange(timeRangeKeys),
			{
				initialProps: {
					timeRangeKeys: ['1']
				},
				wrapper: MockAppContext
			}
		);

		const selectedTimeRange = result.current.getSelectedTimeRange(['1']);

		expect(selectedTimeRange.key).toBe('1');

		unmount();
	});
});

describe('The time range provider should', () => {
	test('Render "custom-range", "Last 7 Days", and "Last Month" items', async () => {
		global.fetch.mockResolvedValueOnce({
			json: async () => ({
				items
			})
		});

		const {getAllByTestId} = render(
			<MockAppContext>
				<TimeRangeProvider timeRangeKeys={[1, 2]}>
					<MockTimeRangeConsumer />
				</TimeRangeProvider>
			</MockAppContext>
		);

		const timeRangeKeys = await waitForElement(() =>
			getAllByTestId('timeRangeKey')
		);

		expect(timeRangeKeys[0].innerHTML).toBe('custom-range');
		expect(timeRangeKeys[1].innerHTML).toBe('Last 7 Days');
		expect(timeRangeKeys[2].innerHTML).toBe('Last Month');
	});
});
