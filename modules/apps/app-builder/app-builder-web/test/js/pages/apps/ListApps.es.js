/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {waitForElementToBeRemoved} from '@testing-library/dom';
import {render} from '@testing-library/react';
import React from 'react';

import ListApps from '../../../../src/main/resources/META-INF/resources/js/pages/apps/ListApps.es';
import * as time from '../../../../src/main/resources/META-INF/resources/js/utils/time.es';
import AppContextProviderWrapper from '../../AppContextProviderWrapper.es';
import {DATA_DEFINITION_RESPONSES, RESPONSES} from '../../constants.es';

const DROPDOWN_VALUES = {
	items: [
		{
			defaultLanguageId: 'en_US',
			id: 37568,
			name: {
				en_US: 'test',
			},
		},
	],
};

const routeProps = {
	match: {
		params: {dataDefinitionId: '123', objectType: 'custom-object'},
	},
};

describe('ListApps', () => {
	beforeEach(() => {
		jest.spyOn(time, 'fromNow').mockImplementation(() => 'months ago');
	});

	afterEach(() => {
		jest.restoreAllMocks();
	});

	it('renders', async () => {
		fetch.mockResponseOnce(JSON.stringify(RESPONSES.ONE_ITEM));
		fetch.mockResponseOnce(
			JSON.stringify(DATA_DEFINITION_RESPONSES.ONE_ITEM)
		);
		fetch.mockResponse(JSON.stringify(DROPDOWN_VALUES));

		const {asFragment} = render(<ListApps {...routeProps} />, {
			wrapper: AppContextProviderWrapper,
		});

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders with 5 apps in the list', async () => {
		fetch.mockResponseOnce(JSON.stringify(RESPONSES.MANY_ITEMS(5)));
		fetch.mockResponseOnce(
			JSON.stringify(DATA_DEFINITION_RESPONSES.ONE_ITEM)
		);
		fetch.mockResponse(JSON.stringify(DROPDOWN_VALUES));

		const {container} = render(<ListApps {...routeProps} />, {
			wrapper: AppContextProviderWrapper,
		});

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(container.querySelector('tbody').children.length).toEqual(5);
	});

	it('renders with empty state', async () => {
		fetch.mockResponseOnce(JSON.stringify(RESPONSES.NO_ITEMS));
		fetch.mockResponseOnce(
			JSON.stringify(DATA_DEFINITION_RESPONSES.ONE_ITEM)
		);
		fetch.mockResponse(JSON.stringify(DROPDOWN_VALUES));

		const {container} = render(<ListApps {...routeProps} />, {
			wrapper: AppContextProviderWrapper,
		});

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(
			container.querySelector('.taglib-empty-result-message-title')
				.textContent
		).toEqual('there-are-no-apps-yet');
	});
});
