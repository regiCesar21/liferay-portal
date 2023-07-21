/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import {StoreAPIContextProvider} from '../../../../src/main/resources/META-INF/resources/page_editor/app/store/index';
import ItemSelector from '../../../../src/main/resources/META-INF/resources/page_editor/common/components/ItemSelector';
import {openInfoItemSelector} from '../../../../src/main/resources/META-INF/resources/page_editor/core/openInfoItemSelector';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			infoItemSelectorUrl: 'infoItemSelectorUrl',
			portletNamespace: 'portletNamespace',
		},
	})
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/core/openInfoItemSelector',
	() => ({
		openInfoItemSelector: jest.fn(() => {}),
	})
);

function renderItemSelector({mappedInfoItems = [], selectedItemTitle = ''}) {
	const state = {
		mappedInfoItems,
	};

	return render(
		<StoreAPIContextProvider dispatch={() => {}} getState={() => state}>
			<ItemSelector
				label="itemSelectorLabel"
				onItemSelect={() => {}}
				selectedItemTitle={selectedItemTitle}
			/>
		</StoreAPIContextProvider>
	);
}

describe('ItemSelector', () => {
	afterEach(() => {
		cleanup();

		openInfoItemSelector.mockClear();
	});

	it('renders correctly', () => {
		const {getByText} = renderItemSelector({});

		expect(getByText('itemSelectorLabel')).toBeInTheDocument();
	});

	it('shows selected item title correctly when receiving it in props', () => {
		const selectedItemTitle = 'itemTitle';

		const {getByLabelText} = renderItemSelector({
			selectedItemTitle,
		});

		expect(getByLabelText('itemSelectorLabel')).toHaveValue(
			selectedItemTitle
		);
	});

	it('does not show any title when not receiving it in props', () => {
		const {getByLabelText} = renderItemSelector({});

		expect(getByLabelText('itemSelectorLabel')).toBeEmpty();
	});

	it('calls openInfoItemSelector when there are not mapping items and plus button is clicked', () => {
		const {getByLabelText} = renderItemSelector({});

		fireEvent.click(getByLabelText('select-content-button'));

		expect(openInfoItemSelector).toBeCalled();
	});

	it('shows recent items dropdown instead of calling openInfoItemSelector when there are mapping items', () => {
		const mappedInfoItems = [
			{classNameId: '001', classPK: '002', title: 'Mapped Item Title'},
		];

		const {getByLabelText, getByText} = renderItemSelector({
			mappedInfoItems,
		});

		fireEvent.click(getByLabelText('select-content-button'));

		expect(getByText('Mapped Item Title')).toBeInTheDocument();

		expect(openInfoItemSelector).not.toBeCalled();
	});
});
