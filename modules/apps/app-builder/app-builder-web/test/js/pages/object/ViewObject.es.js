/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import ViewObject from '../../../../src/main/resources/META-INF/resources/js/pages/object/ViewObject.es';
import AppContextProviderWrapper from '../../AppContextProviderWrapper.es';

import '@testing-library/jest-dom/extend-expect';

describe('ViewObject', () => {
	const RESPONSE = {
		name: {
			en_US: 'Custom Object',
		},
	};

	beforeEach(() => {
		jest.useFakeTimers();
	});

	afterEach(() => {
		cleanup();
		jest.clearAllTimers();
		jest.restoreAllMocks();
	});

	afterAll(() => {
		jest.useRealTimers();
	});

	it('renders', async () => {
		fetch.mockResponseOnce(JSON.stringify(RESPONSE));

		const {asFragment} = render(
			<ViewObject
				match={{
					params: {
						dataDefinitionId: 1,
					},
				}}
			/>,
			{wrapper: AppContextProviderWrapper}
		);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(asFragment()).toMatchSnapshot();
	});

	it('clicks on tabs and checks if they are active', async () => {
		fetch.mockResponse(JSON.stringify(RESPONSE));

		const {queryByText} = render(
			<ViewObject
				match={{
					params: {
						dataDefinitionId: 1,
					},
				}}
			/>,
			{wrapper: AppContextProviderWrapper}
		);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(queryByText(RESPONSE.name.en_US)).toBeTruthy();

		const tableViewsTab = queryByText('table-views');
		fireEvent.click(tableViewsTab);
		expect(tableViewsTab.classList.contains('active')).toBeTruthy();
	});
});
