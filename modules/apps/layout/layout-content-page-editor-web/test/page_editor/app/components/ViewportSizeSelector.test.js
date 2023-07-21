/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {cleanup, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import ViewportSizeSelector from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/ViewportSizeSelector';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/config',
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

const defaultState = {
	selectedViewportSize: 'desktop',
};

const renderComponent = ({onSelect = () => {}, state}) => {
	return render(
		<ViewportSizeSelector
			onSizeSelected={onSelect}
			selectedSize={state.selectedViewportSize}
		/>
	);
};

describe('ViewportSizeSelector', () => {
	afterEach(cleanup);

	it('renders ViewportSizeSelector component', () => {
		const {getByTitle} = renderComponent({state: defaultState});

		expect(getByTitle('Desktop')).toBeInTheDocument();
		expect(getByTitle('Mobile')).toBeInTheDocument();
		expect(getByTitle('Tablet')).toBeInTheDocument();
	});

	it('calls onSizeSelected with sizeId when a size is selected', () => {
		const onSelect = jest.fn();
		const {getByTitle} = renderComponent({onSelect, state: defaultState});
		const button = getByTitle('Mobile');

		userEvent.click(button);

		expect(onSelect).toHaveBeenLastCalledWith('mobile');
	});
});
