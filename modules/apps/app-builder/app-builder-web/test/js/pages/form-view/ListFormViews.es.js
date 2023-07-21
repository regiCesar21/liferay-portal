/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {waitForElementToBeRemoved} from '@testing-library/dom';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {createMemoryHistory} from 'history';
import React from 'react';

import ListFormViews from '../../../../src/main/resources/META-INF/resources/js/pages/form-view/ListFormViews.es';
import * as time from '../../../../src/main/resources/META-INF/resources/js/utils/time.es';
import AppContextProviderWrapper from '../../AppContextProviderWrapper.es';
import {DATA_DEFINITION_RESPONSES, RESPONSES} from '../../constants.es';

const basePortletURL = 'localhost';
const portletURL = '/edit_form_view';

describe('ListFormViews', () => {
	let spyFromNow;
	let PortletURL;
	let navigate;

	beforeEach(() => {
		navigate = jest.fn();
		PortletURL = {
			createRenderURL: jest.fn().mockReturnValue(portletURL),
		};

		window.Liferay = {
			...window.Liferay,
			Util: {
				...window.Liferay.Util,
				PortletURL,
				navigate,
			},
		};

		spyFromNow = jest
			.spyOn(time, 'fromNow')
			.mockImplementation(() => 'months ago');
	});

	afterEach(() => {
		cleanup();

		jest.restoreAllMocks();
	});

	it('renders', async () => {
		fetch
			.mockResponseOnce(JSON.stringify(RESPONSES.ONE_ITEM))
			.mockResponseOnce(
				JSON.stringify(DATA_DEFINITION_RESPONSES.ONE_ITEM)
			);

		const {asFragment} = render(
			<AppContextProviderWrapper appContext={{basePortletURL}}>
				<ListFormViews
					match={{
						params: {
							dataDefinitionId: 1,
						},
					}}
				/>
			</AppContextProviderWrapper>
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders with empty state', async () => {
		fetch
			.mockResponseOnce(JSON.stringify(RESPONSES.NO_ITEMS))
			.mockResponseOnce(
				JSON.stringify(DATA_DEFINITION_RESPONSES.ONE_ITEM)
			);

		const {container, queryByText} = render(
			<AppContextProviderWrapper appContext={{basePortletURL}}>
				<ListFormViews
					match={{
						params: {
							dataDefinitionId: 1,
						},
					}}
				/>
			</AppContextProviderWrapper>
		);

		await waitForElementToBeRemoved(() =>
			container.querySelector('span.loading-animation')
		);

		expect(
			queryByText(
				'create-one-or-more-forms-to-display-the-data-held-in-your-data-object'
			)
		).toBeTruthy();

		expect(queryByText('there-are-no-form-views-yet')).toBeTruthy();

		const buttonNewFormView = queryByText('new-form-view');

		expect(buttonNewFormView).toBeTruthy();

		fireEvent.click(buttonNewFormView);

		expect(navigate.mock.calls.length).toBe(1);
		expect(navigate.mock.calls[0][0]).toBe(portletURL);

		expect(PortletURL.createRenderURL.mock.calls[0][0]).toEqual(
			basePortletURL
		);

		expect(fetch.mock.calls.length).toBe(2);
	});

	it('renders with data and click on actions', async () => {
		fetch
			.mockResponseOnce(JSON.stringify(RESPONSES.ONE_ITEM))
			.mockResponseOnce(
				JSON.stringify(DATA_DEFINITION_RESPONSES.ONE_ITEM)
			);

		const history = createMemoryHistory();

		const {baseElement, container} = render(
			<AppContextProviderWrapper
				appContext={{basePortletURL}}
				history={history}
			>
				<ListFormViews
					match={{
						params: {
							dataDefinitionId: 1,
						},
					}}
				/>
			</AppContextProviderWrapper>
		);

		expect(history.length).toBe(1);
		expect(history.location.pathname).toBe('/');

		await waitForElementToBeRemoved(() =>
			container.querySelector('span.loading-animation')
		);

		expect(spyFromNow).toHaveBeenCalled();

		const dropDownMenu = baseElement.querySelectorAll('.dropdown-menu');
		const actions = dropDownMenu[1].querySelectorAll('.dropdown-item');

		expect(actions.length).toBe(2);
		expect(history.length).toBe(1);
		expect(history.location.pathname).toBe('/');

		const [edit] = actions;

		fireEvent.click(edit);

		expect(navigate.mock.calls.length).toBe(1);
		expect(navigate.mock.calls[0][0]).toBe(portletURL);

		const buttonNewFormViewPlus = container.querySelector(
			'[data-title="new-form-view"]'
		);

		fireEvent.click(buttonNewFormViewPlus);

		expect(navigate.mock.calls.length).toBe(2);
		expect(navigate.mock.calls[1][0]).toBe(portletURL);
	});
});
