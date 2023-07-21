/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import ListAppsTabs from '../../../../src/main/resources/META-INF/resources/js/pages/apps/ListAppsTabs.es';
import AppContextProviderWrapper from '../../AppContextProviderWrapper.es';
import RouterWrapper from '../../RouterWrapper.es';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/hooks/useLazy.es',
	() => () => ({module}) => <div>Tab Content: {module}</div>
);

const STANDARD = 'standard';
const WORKFLOW = 'workflow';

const appContext = {
	appsTabs: {
		[STANDARD]: {
			label: STANDARD,
			listEntryPoint: STANDARD,
			scope: STANDARD,
		},
		[WORKFLOW]: {
			label: WORKFLOW,
			listEntryPoint: WORKFLOW,
			scope: WORKFLOW,
		},
	},
	appsTabsKeys: [STANDARD, WORKFLOW],
};

describe('ListAppsTabs', () => {
	afterEach(() => {
		jest.restoreAllMocks();
		cleanup();
	});

	it('renders on default tab', () => {
		const {container, queryByText} = render(
			<RouterWrapper path="/:tab?">
				<ListAppsTabs />
			</RouterWrapper>,
			{
				wrapper: (props) => (
					<AppContextProviderWrapper
						appContext={appContext}
						{...props}
					/>
				),
			}
		);

		const tabsLinks = container.querySelectorAll('a.nav-link');
		const tabContent = queryByText(/tab content:/i);

		expect(tabsLinks[0].classList).toContain('active');
		expect(tabsLinks[0].textContent).toContain(STANDARD);
		expect(tabsLinks[1].textContent).toContain(WORKFLOW);

		expect(tabContent.textContent).toContain(STANDARD);
	});

	it('renders on workflow tab', () => {
		const {container, queryByText} = render(
			<RouterWrapper
				initialEntries={[{pathname: `/${WORKFLOW}`}]}
				path="/:tab?"
			>
				<ListAppsTabs />
			</RouterWrapper>,
			{
				wrapper: (props) => (
					<AppContextProviderWrapper
						appContext={appContext}
						{...props}
					/>
				),
			}
		);

		const tabsLinks = container.querySelectorAll('a.nav-link');
		const tabContent = queryByText(/tab content:/i);

		expect(tabContent.textContent).toContain(WORKFLOW);

		expect(tabsLinks[0].textContent).toContain(STANDARD);
		expect(tabsLinks[1].classList).toContain('active');
		expect(tabsLinks[1].textContent).toContain(WORKFLOW);
	});
});
