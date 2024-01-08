/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import Index from '../../../src/main/resources/META-INF/resources/js/index.es';

const props = {
	data: JSON.stringify('{field1: {values: {option1: 1}, type:"radio"}'),
	fields: [
		{
			label: 'Field 1',
			name: 'field1',
			options: {option1: 'Option 1'},
			type: 'radio',
		},
		{label: 'Field 2', name: 'field2', type: 'radio'},
		{label: 'Field 3', name: 'field3', type: 'radio'},
	],
	portletNamespace:
		'_com_liferay_dynamic_data_mapping_form_report_web_portlet_DDMFormReportPortlet_',
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

describe('index', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {asFragment} = render(<Index {...props} />);
		expect(asFragment()).toMatchSnapshot();
	});

	it('renders nothing when there is no data', () => {
		const {asFragment} = render(<Index {...props} data={null} />);
		expect(asFragment()).toMatchSnapshot();
	});
});
