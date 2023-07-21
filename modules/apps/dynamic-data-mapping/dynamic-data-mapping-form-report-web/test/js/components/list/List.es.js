/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import List from '../../../../src/main/resources/META-INF/resources/js/components/list/List.es';

const props = {
	data: ['label1', 'label2', 'label3', 'label4', 'label5'],
	onClick: () => {},
	summary: {},
	totalEntries: 5,
};

describe('List', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {asFragment, container} = render(<List {...props} />);

		const li = container.querySelectorAll('li');

		expect(li.length).toBe(5);

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders color list', () => {
		const colorProps = {
			...props,
			data: ['7F26FF', '2BA676', 'CBCBCB', 'FF21A0', 'FF0D0D'],
			type: 'color',
		};

		const {container} = render(<List {...colorProps} />);

		const colorTextList = container.querySelectorAll('.color-text');

		expect(colorTextList.length).toBe(5);

		colorTextList.forEach((colorText, index) =>
			expect(colorText.textContent).toBe(colorProps.data[index])
		);
	});

	it('shows a button to see all entries when there are more than 5 entries', () => {
		const {container} = render(<List {...props} totalEntries={6} />);

		expect(container.querySelector('button').innerHTML).toBe(
			'see-all-entries'
		);
	});

	it('renders dates according to the language', () => {
		const data = ['12-20-2020'];

		const type = 'date';

		const {getByText} = render(<List {...props} data={data} type={type} />);

		expect(getByText('12/20/2020')).toBeTruthy();
	});
});
