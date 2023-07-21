/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {cleanup, render, waitForElement} from '@testing-library/react';
import React from 'react';

import Container from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/layout-data-items/Container';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {VIEWPORT_SIZES} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/viewportSizes';
import {StoreAPIContextProvider} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/store';

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			frontendTokens: {},
		},
	})
);

const renderContainer = (config) => {
	return render(
		<StoreAPIContextProvider
			getState={() => ({
				languageId: 'en',
				selectedViewportSize: VIEWPORT_SIZES.desktop,
			})}
		>
			<Container
				data={{}}
				item={{
					children: [],
					config: {...config, styles: {}},
					itemId: 'containerId',
					type: LAYOUT_DATA_ITEM_TYPES.container,
				}}
				withinTopper={false}
			>
				Container
			</Container>
		</StoreAPIContextProvider>
	);
};

describe('Container', () => {
	afterEach(cleanup);

	it('wraps the container inside a link if configuration is specified', async () => {
		const link = renderContainer({
			link: {
				href: 'https://sandro.vero.victor.com',
				target: '_blank',
			},
		}).getByRole('link');

		await waitForElement(() => link);

		expect(link.href).toBe('https://sandro.vero.victor.com/');
		expect(link.target).toBe('_blank');
		expect(link.textContent).toBe('Container');
	});
});
