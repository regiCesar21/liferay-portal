/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {cleanup, render} from '@testing-library/react';
import React from 'react';

import {StoreAPIContextProvider} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/store';
import ItemConfiguration from '../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/page-structure/components/ItemConfiguration';

const renderComponent = () =>
	render(
		<StoreAPIContextProvider>
			<ItemConfiguration />
		</StoreAPIContextProvider>
	);

describe('ItemConfiguration', () => {
	afterEach(cleanup);

	it('renders a warning message if no item is selected', () => {
		const {getByText} = renderComponent();

		expect(
			getByText('select-an-element-of-the-page-to-show-this-panel')
		).toBeInTheDocument();
	});
});
