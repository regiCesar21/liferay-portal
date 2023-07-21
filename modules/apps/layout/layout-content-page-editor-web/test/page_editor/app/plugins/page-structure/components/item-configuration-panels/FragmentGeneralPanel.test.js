/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/editableFragmentEntryProcessor';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/freemarkerFragmentEntryProcessor';

import '@testing-library/jest-dom/extend-expect';
import {cleanup, fireEvent, render} from '@testing-library/react';

import {VIEWPORT_SIZES} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/viewportSizes';
import {config} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/index';
import FragmentService from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/services/FragmentService';
import {StoreAPIContextProvider} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/store/index';
import {FragmentGeneralPanel} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/page-structure/components/item-configuration-panels/FragmentGeneralPanel';

jest.mock(
	'../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			availableViewportSizes: {
				desktop: {label: 'Desktop', sizeId: 'desktop'},
				mobile: {label: 'Mobile', sizeId: 'mobile'},
				tablet: {label: 'Tablet', sizeId: 'tablet'},
			},
		},
	})
);

jest.mock(
	'../../../../../../../src/main/resources/META-INF/resources/page_editor/app/services/serviceFetch',
	() => jest.fn(() => Promise.resolve({}))
);

jest.mock(
	'../../../../../../../src/main/resources/META-INF/resources/page_editor/app/services/FragmentService',
	() => ({
		renderFragmentEntryLinkContent: jest.fn(() => Promise.resolve({})),
		updateConfigurationValues: jest.fn(() => Promise.resolve({})),
	})
);

const FRAGMENT_ENTRY_LINK_ID = '1';

const defaultFragmentEntryLink = {
	comments: [],
	configuration: {
		fieldSets: [
			{
				fields: [
					{
						dataType: 'string',
						defaultValue: 'h1',
						description: '',
						label: 'Heading Level',
						name: 'headingLevel',
						type: 'select',
						typeOptions: {
							validValues: [
								{label: 'H1', value: 'h1'},
								{label: 'H2', value: 'h2'},
								{label: 'H3', value: 'h3'},
								{label: 'H4', value: 'h4'},
							],
						},
					},
				],
				label: '',
			},
		],
	},
	defaultConfigurationValues: {
		headingLevel: 'h1',
	},
	editableValues: {
		[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {},
		[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR]: {},
	},
	fragmentEntryLinkId: FRAGMENT_ENTRY_LINK_ID,
	name: 'Heading',
};

const item = {
	children: [],
	config: {
		fragmentEntryLinkId: FRAGMENT_ENTRY_LINK_ID,
	},
	itemId: '1',
	parentId: '',
	type: '',
};

const mockDispatch = jest.fn((a) => {
	if (typeof a === 'function') {
		return a(mockDispatch);
	}
});

const renderGeneralPanel = ({
	segmentsExperienceId,
	fragmentEntryLink = defaultFragmentEntryLink,
}) => {
	const state = {
		fragmentEntryLinks: {[FRAGMENT_ENTRY_LINK_ID]: fragmentEntryLink},
		segmentsExperienceId,
		selectedViewportSize: VIEWPORT_SIZES.desktop,
	};

	return render(
		<StoreAPIContextProvider dispatch={mockDispatch} getState={() => state}>
			<FragmentGeneralPanel item={item} />
		</StoreAPIContextProvider>
	);
};

describe('FragmentGeneralPanel', () => {
	afterEach(() => {
		cleanup();

		FragmentService.updateConfigurationValues.mockClear();
		FragmentService.renderFragmentEntryLinkContent.mockClear();
	});

	it('does not prefix values with segments if we do not have experiences', async () => {
		config.defaultSegmentsExperienceId = null;

		const {getByLabelText} = renderGeneralPanel({
			segmentsExperienceId: null,
		});

		const input = getByLabelText('Heading Level');

		await fireEvent.change(input, {
			target: {value: 'h2'},
		});

		expect(FragmentService.updateConfigurationValues).toBeCalledWith(
			expect.objectContaining({
				configurationValues: {
					[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {},
					[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR]: {
						headingLevel: 'h2',
					},
				},
			})
		);
	});

	it('prefix values with segments when we have experiences', async () => {
		config.defaultSegmentsExperienceId = '2';

		const {getByLabelText} = renderGeneralPanel({
			segmentsExperienceId: '1',
		});

		const input = getByLabelText('Heading Level');

		await fireEvent.change(input, {
			target: {value: 'h2'},
		});

		expect(FragmentService.updateConfigurationValues).toBeCalledWith(
			expect.objectContaining({
				configurationValues: {
					[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {},
					[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR]: {
						headingLevel: 'h2',
					},
				},
			})
		);
	});

	it('prefix values with default experience when segmentsExperience is null', async () => {
		config.defaultSegmentsExperienceId = '2';

		const {getByLabelText} = renderGeneralPanel({
			segmentsExperienceId: null,
		});

		const input = getByLabelText('Heading Level');

		await fireEvent.change(input, {
			target: {value: 'h2'},
		});

		expect(FragmentService.updateConfigurationValues).toBeCalledWith(
			expect.objectContaining({
				configurationValues: {
					[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {},
					[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR]: {
						headingLevel: 'h2',
					},
				},
			})
		);
	});

	it('merges configuration values when a new one is added', async () => {
		config.defaultSegmentsExperienceId = '0';

		const fragmentEntryLink = {
			comments: [],
			configuration: {
				fieldSets: [
					{
						fields: [
							{
								dataType: 'string',
								defaultValue: 'h1',
								description: '',
								label: 'Heading Level',
								name: 'headingLevel',
								type: 'select',
								typeOptions: {
									validValues: [
										{label: 'H1', value: 'h1'},
										{label: 'H2', value: 'h2'},
										{label: 'H3', value: 'h3'},
										{label: 'H4', value: 'h4'},
									],
								},
							},
							{
								dataType: 'string',
								defaultValue: 'default',
								description: '',
								label: 'Another thing',
								name: 'anotherThing',
								type: 'text',
							},
						],
						label: '',
					},
				],
			},
			defaultConfigurationValues: {
				headingLevel: 'h1',
			},
			editableValues: {
				[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {},
				[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR]: {
					anotherThing: 'test',
				},
			},
			fragmentEntryLinkId: FRAGMENT_ENTRY_LINK_ID,
			name: 'Heading',
		};

		const {getByLabelText} = renderGeneralPanel({
			fragmentEntryLink,
			segmentsExperienceId: '0',
		});

		const input = getByLabelText('Heading Level');

		await fireEvent.change(input, {
			target: {value: 'h2'},
		});

		expect(FragmentService.updateConfigurationValues).toBeCalledWith(
			expect.objectContaining({
				configurationValues: {
					[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {},
					[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR]: {
						anotherThing: 'test',
						headingLevel: 'h2',
					},
				},
			})
		);
	});
});
