/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render, wait} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import TotalCount from '../../../src/main/resources/META-INF/resources/js/components/TotalCount';

import '@testing-library/jest-dom/extend-expect';

describe('TotalCount', () => {
	afterEach(cleanup);

	it('renders text, help text and total count number', async () => {
		const mockDataProvider = jest.fn(() => {
			return Promise.resolve(9999);
		});

		const testProps = {
			dataProvider: mockDataProvider,
			label: 'Total Views',
			popoverHeader: 'Total Views',
			popoverMessage:
				'This number refers to the total number of views since the content was published.',
		};

		const {getByRole, getByText} = render(
			<TotalCount
				dataProvider={testProps.dataProvider}
				label={testProps.label}
				popoverHeader={testProps.popoverHeader}
				popoverMessage={testProps.popoverMessage}
			/>
		);

		await wait(() => expect(mockDataProvider).toHaveBeenCalled());

		expect(getByText('9,999')).toBeInTheDocument();

		const label = getByText(testProps.label);
		expect(label).toBeInTheDocument();

		const helpTextIcon = getByRole('presentation');

		userEvent.click(helpTextIcon);

		getByText(
			'This number refers to the total number of views since the content was published.'
		);

		expect(mockDataProvider).toHaveBeenCalledTimes(1);
	});

	it('renders a dash instead of total count number when there is an error', async () => {
		const mockDataProvider = jest.fn(() => {
			return Promise.reject('-');
		});

		const testProps = {
			dataProvider: mockDataProvider,
			label: 'Total Views',
			popoverHeader: 'Total Views',
			popoverMessage:
				'This number refers to the total number of views since the content was published.',
		};

		const {getByText} = render(
			<TotalCount
				dataProvider={testProps.dataProvider}
				label={testProps.label}
				popoverHeader={testProps.popoverHeader}
				popoverMessage={testProps.popoverMessage}
			/>
		);

		await wait(() => expect(mockDataProvider).toHaveBeenCalled());

		expect(getByText('-')).toBeInTheDocument();

		expect(mockDataProvider).toHaveBeenCalledTimes(1);
	});
});
