/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import Legend from '../../../../src/main/resources/META-INF/resources/js/components/chart/Legend.es';

const labels = [
	'label1',
	'label2',
	'label3',
	'label4',
	'label5',
	'label6',
	'label7',
	'label8',
	'label9',
	'label10',
	'label11',
];

const props = {
	activeIndex: null,
	labels: ['label1', 'label2'],
};

describe('Legend', () => {
	afterEach(cleanup);

	it('dims all labels except one that is being hovered', () => {
		const {container} = render(<Legend {...props} activeIndex={0} />);

		expect(container.querySelectorAll('li')[0].className).toBe('');
		expect(container.querySelectorAll('li')[1].className).toBe('dim');
	});

	it('displays showAll button when there are more than 10 items', () => {
		const {queryByText} = render(<Legend {...props} labels={labels} />);

		expect(queryByText('show-all')).toBeTruthy();
	});

	it('displays showLess button when showAll button is clicked', () => {
		const {queryByText} = render(<Legend {...props} labels={labels} />);

		fireEvent.click(queryByText('show-all'));

		expect(queryByText('show-less')).toBeTruthy();
	});
});
