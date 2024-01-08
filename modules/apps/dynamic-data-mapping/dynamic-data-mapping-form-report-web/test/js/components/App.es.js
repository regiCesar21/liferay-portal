/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import App from '../../../src/main/resources/META-INF/resources/js/App.es';

const props = {
	data: {
		field1: {
			type: 'radio',
			values: {
				option1: 2,
				option2: 1,
			},
		},
		field2: {
			type: 'radio',
			values: {
				option1: 2,
				option2: 1,
			},
		},
	},
	fields: [
		{
			label: 'Field 1',
			name: 'field1',
			options: {option1: 'Option 1'},
			type: 'radio',
		},
		{
			label: 'Field 2',
			name: 'field2',
			options: {option2: 'Option 2'},
			type: 'radio',
		},
		{label: 'Field 3', name: 'field3', type: 'radio'},
	],
};

// Mock needed due to a bug in ResponsiveContainer Recharts component
// See https://github.com/recharts/recharts/issues/2268

jest.mock('recharts', () => {
	const OriginalModule = jest.requireActual('recharts');

	return {
		...OriginalModule,
		ResponsiveContainer: ({children, height}) => (
			<OriginalModule.ResponsiveContainer height={height} width={800}>
				{children}
			</OriginalModule.ResponsiveContainer>
		),
	};
});

describe('App', () => {
	afterEach(cleanup);

	it('renders a card for each field', () => {
		const {container} = render(<App {...props} />);

		expect(container.querySelectorAll('.card').length).toBe(
			props.fields.length
		);
	});
});
