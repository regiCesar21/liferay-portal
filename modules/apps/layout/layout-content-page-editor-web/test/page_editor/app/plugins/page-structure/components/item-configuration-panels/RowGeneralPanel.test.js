/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import {ResizeContextProvider} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/components/ResizeContext';
import {StoreAPIContextProvider} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/store/index';
import updateItemConfig from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateItemConfig';
import updateRowColumns from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateRowColumns';
import {RowGeneralPanel} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/page-structure/components/item-configuration-panels/RowGeneralPanel';

const ITEM_CONFIG = {
	gutters: true,
	modulesPerRow: 2,
	numberOfColumns: 2,
	verticalAlignment: 'top',
};

const RESIZE_CONTEXT_STATE = {
	customRow: false,
	resizing: false,
	setCustomRow: () => null,
	setResizing: () => null,
	setUpdatedLayoutData: () => null,
	updatedLayoutData: null,
};

const STATE = {
	segmentsExperienceId: '0',
	selectedViewportSize: 'desktop',
};

const renderComponent = ({
	config = ITEM_CONFIG,
	state = STATE,
	contextState = RESIZE_CONTEXT_STATE,
	dispatch = () => {},
}) =>
	render(
		<StoreAPIContextProvider
			dispatch={dispatch}
			getState={() => ({...STATE, ...state})}
		>
			<ResizeContextProvider
				value={{...RESIZE_CONTEXT_STATE, ...contextState}}
			>
				<RowGeneralPanel
					item={{
						children: [],
						config: {...ITEM_CONFIG, ...config},
						itemId: '0',
						parentId: '',
						type: '',
					}}
				/>
			</ResizeContextProvider>
		</StoreAPIContextProvider>
	);

jest.mock(
	'../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			availableViewportSizes: {
				desktop: {label: 'Desktop'},
				landscapeMobile: {label: 'landscapeMobile'},
			},
		},
	})
);

jest.mock(
	'../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateItemConfig',
	() => jest.fn()
);

jest.mock(
	'../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateRowColumns',
	() => jest.fn()
);

describe('RowGeneralPanel', () => {
	afterEach(() => {
		cleanup();
		updateItemConfig.mockClear();
		updateRowColumns.mockClear();
	});

	it('allows changing the number of modules of a grid', async () => {
		const {getByLabelText} = renderComponent({});
		const input = getByLabelText('number-of-modules');

		await fireEvent.change(input, {
			target: {value: '6'},
		});

		expect(updateRowColumns).toHaveBeenCalledWith({
			itemId: '0',
			numberOfColumns: 6,
			segmentsExperienceId: '0',
			viewportSizeId: 'desktop',
		});
	});

	it('allows changing the gutter', async () => {
		const {getByLabelText} = renderComponent({});
		const input = getByLabelText('show-gutter');

		await fireEvent.click(input);

		expect(updateItemConfig).toHaveBeenCalledWith({
			itemConfig: {gutters: false},
			itemId: '0',
			segmentsExperienceId: '0',
		});
	});
});
