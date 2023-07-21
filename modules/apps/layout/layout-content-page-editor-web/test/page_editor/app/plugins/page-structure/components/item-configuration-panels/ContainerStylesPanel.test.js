/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import {VIEWPORT_SIZES} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/viewportSizes';
import {StoreAPIContextProvider} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/store/index';
import updateItemConfig from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateItemConfig';
import {ContainerStylesPanel} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/page-structure/components/item-configuration-panels/ContainerStylesPanel';

const renderComponent = ({
	dispatch = () => {},
	selectedViewportSize = VIEWPORT_SIZES.desktop,
} = {}) =>
	render(
		<StoreAPIContextProvider
			dispatch={dispatch}
			getState={() => ({
				segmentsExperienceId: '0',
				selectedViewportSize,
			})}
		>
			<ContainerStylesPanel
				item={{
					children: [],
					config: {tablet: {styles: {}}},
					itemId: '0',
					parentId: '',
					type: '',
				}}
			/>
		</StoreAPIContextProvider>
	);

jest.mock(
	'../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateItemConfig',
	() => jest.fn()
);

jest.mock(
	'../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			availableViewportSizes: {
				desktop: {label: 'Desktop', sizeId: 'desktop'},
				mobile: {label: 'Mobile', sizeId: 'mobile'},
				tablet: {label: 'Tablet', sizeId: 'tablet'},
			},
			commonStyles: [],
			defaultSegmentsExperienceId: 0,
			marginOptions: [],
			paddingOptions: [],
		},
	})
);

describe('ContainerStylesPanel', () => {
	afterEach(() => {
		cleanup();
		updateItemConfig.mockClear();
	});

	it('renders correctly', () => {
		const {getByLabelText} = renderComponent();

		expect(getByLabelText('container-width')).toBeInTheDocument();
	});

	it('shows custom styles panel', async () => {
		const {getByText} = renderComponent({});

		expect(getByText('custom-styles')).toBeInTheDocument();
	});

	it('calls dispatch method when changing the container width', async () => {
		const {getByLabelText} = renderComponent();

		const containerWidthSelect = getByLabelText('container-width');

		await fireEvent.change(containerWidthSelect, {
			target: {value: 'fixed'},
		});

		expect(updateItemConfig).toBeCalledWith(
			expect.objectContaining({
				itemConfig: {
					widthType: 'fixed',
				},
			})
		);
	});
});
