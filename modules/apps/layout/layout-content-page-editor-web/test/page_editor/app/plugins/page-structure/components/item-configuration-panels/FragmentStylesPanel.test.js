/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/editableFragmentEntryProcessor';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/freemarkerFragmentEntryProcessor';
import {VIEWPORT_SIZES} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/viewportSizes';
import {StoreAPIContextProvider} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/store/index';
import updateItemConfig from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateItemConfig';
import {FragmentStylesPanel} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/page-structure/components/item-configuration-panels/FragmentStylesPanel';

const FRAGMENT_ENTRY_LINK_ID = '1';

const fragmentEntryLinkWithStyles = {
	comments: [],
	configuration: {
		fieldSets: [
			{
				configurationRole: 'style',
				fields: [
					{
						dataType: 'string',
						defaultValue: '1',
						description: '',
						label: 'Custom Style',
						name: 'customStyle',
						type: 'select',
						typeOptions: {
							validValues: [
								{label: '0', value: '0'},
								{label: '1', value: '1'},
							],
						},
					},
				],
				label: '',
			},
		],
	},
	defaultConfigurationValues: {
		customStyle: '1',
	},
	editableValues: {
		[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {},
		[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR]: {},
	},
	fragmentEntryLinkId: FRAGMENT_ENTRY_LINK_ID,
	name: 'Fragment',
};

const renderComponent = ({
	dispatch = () => {},
	fragmentEntryLink = {},
	selectedViewportSize = VIEWPORT_SIZES.desktop,
} = {}) =>
	render(
		<StoreAPIContextProvider
			dispatch={dispatch}
			getState={() => ({
				fragmentEntryLinks: {
					[FRAGMENT_ENTRY_LINK_ID]: fragmentEntryLink,
				},
				segmentsExperienceId: '0',
				selectedViewportSize,
			})}
		>
			<FragmentStylesPanel
				item={{
					children: [],
					config: {
						fragmentEntryLinkId: FRAGMENT_ENTRY_LINK_ID,
						tablet: {styles: {}},
					},
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
			commonStyles: [
				{
					label: 'margin',
					styles: [
						{
							dataType: 'string',
							defaultValue: '0',
							dependencies: [],
							displaySize: 'small',
							label: 'margin-top',
							name: 'marginTop',
							responsive: true,
							responsiveTemplate: 'mt{viewport}{value}',
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
			defaultSegmentsExperienceId: 0,
			marginOptions: [],
			paddingOptions: [],
		},
	})
);

describe('FragmentStylesPanel', () => {
	afterEach(() => {
		cleanup();
		updateItemConfig.mockClear();
	});

	it('does not show custom styles panel when there are no custom styles', async () => {
		const {queryByText} = renderComponent({});

		expect(queryByText('custom-styles')).not.toBeInTheDocument();
	});

	it('shows custom styles panel when there is at least one custom style', async () => {
		const {getByText} = renderComponent({
			fragmentEntryLink: fragmentEntryLinkWithStyles,
		});

		expect(getByText('custom-styles')).toBeInTheDocument();
	});

	it('allows changing custom styles for a given viewport', async () => {
		const {getByLabelText} = renderComponent({
			selectedViewportSize: VIEWPORT_SIZES.tablet,
		});
		const input = getByLabelText('margin-top');

		await fireEvent.change(input, {
			target: {value: '1'},
		});

		expect(updateItemConfig).toHaveBeenCalledWith({
			itemConfig: {
				tablet: {
					styles: {
						marginTop: '1',
					},
				},
			},
			itemId: '0',
			segmentsExperienceId: '0',
		});
	});
});
