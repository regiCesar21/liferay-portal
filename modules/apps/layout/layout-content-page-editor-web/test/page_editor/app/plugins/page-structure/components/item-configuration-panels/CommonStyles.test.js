/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import {StoreAPIContextProvider} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/store/index';
import updateItemConfig from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateItemConfig';
import {CommonStyles} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/page-structure/components/item-configuration-panels/CommonStyles';

const STATE = {
	segmentsExperienceId: '0',
	selectedViewportSize: 'desktop',
};

const renderComponent = ({
	state = STATE,
	dispatch = () => {},
	itemConfig = {},
	itemType = '',
}) =>
	render(
		<StoreAPIContextProvider
			dispatch={dispatch}
			getState={() => ({...STATE, ...state})}
		>
			<CommonStyles
				commonStylesValues={{}}
				item={{
					children: [],
					config: {
						styles: {},
						tablet: {styles: {}},
						...itemConfig,
					},
					itemId: '0',
					parentId: '',
					type: itemType,
				}}
			/>
		</StoreAPIContextProvider>
	);

jest.mock(
	'../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			availableViewportSizes: {
				desktop: {label: 'Desktop'},
				tablet: {label: 'tablet'},
			},
			commonStyles: [
				{
					label: 'margin',
					styles: [
						{
							dataType: 'string',
							defaultValue: '0',
							dependencies: [],
							displaySize: 'small',
							label: 'margin-left',
							name: 'marginLeft',
							responsive: true,
							type: 'select',
							validValues: [
								{
									label: '0',
									value: '0',
								},
								{
									label: '1',
									value: '1',
								},
							],
						},
						{
							dataType: 'string',
							defaultValue: '0',
							dependencies: [],
							displaySize: 'small',
							label: 'margin-right',
							name: 'marginRight',
							responsive: true,
							type: 'select',
							validValues: [
								{
									label: '0',
									value: '0',
								},
								{
									label: '1',
									value: '1',
								},
							],
						},
					],
				},
			],
			responsiveEnabled: true,
		},
	})
);

jest.mock(
	'../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateItemConfig',
	() => jest.fn()
);

describe('CommonStyles', () => {
	afterEach(() => {
		cleanup();
		updateItemConfig.mockClear();
	});

	it('shows common styles panel', async () => {
		const {getByText} = renderComponent({});

		expect(getByText('common-styles')).toBeInTheDocument();
	});

	it('allows changing common styles for a given viewport', async () => {
		const {getByLabelText} = renderComponent({
			state: {selectedViewportSize: 'tablet'},
		});
		const input = getByLabelText('margin-left');

		await fireEvent.change(input, {
			target: {value: '1'},
		});

		expect(updateItemConfig).toHaveBeenCalledWith({
			itemConfig: {
				tablet: {
					styles: {
						marginLeft: '1',
					},
				},
			},
			itemId: '0',
			segmentsExperienceId: '0',
		});
	});

	it('disables left and right margin selecting fixed width for containers', async () => {
		const {getByLabelText} = renderComponent({
			itemConfig: {widthType: 'fixed'},
			itemType: 'container',
		});

		const marginLeftInput = getByLabelText('margin-left');

		const marginRightInput = getByLabelText('margin-right');

		expect(marginLeftInput.disabled).toBe(true);

		expect(marginRightInput.disabled).toBe(true);
	});
});
