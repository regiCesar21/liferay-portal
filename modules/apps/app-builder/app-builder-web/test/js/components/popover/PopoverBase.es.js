/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import {PopoverBase} from '../../../../src/main/resources/META-INF/resources/js/components/popover/PopoverBase.es';

describe('PopoverBase', () => {
	afterEach(() => {
		cleanup();
	});

	it('renders correct placement className', () => {
		const {container, queryByText} = render(
			<PopoverBase placement="bottom" visible>
				<PopoverBase.Header>
					<h1>Header</h1>
				</PopoverBase.Header>
				<PopoverBase.Body>
					<p>Body</p>
				</PopoverBase.Body>
				<PopoverBase.Footer>
					<span>Footer</span>
				</PopoverBase.Footer>
			</PopoverBase>
		);

		expect(container.querySelector('div.arrow')).toBeTruthy();
		expect(container.querySelector('.hide')).toBeFalsy();
		expect(container.querySelector('.clay-popover-bottom')).toBeTruthy();
		expect(queryByText('Header')).toBeTruthy();
		expect(queryByText('Body')).toBeTruthy();
		expect(queryByText('Footer')).toBeTruthy();
	});

	it('renders with placement as none', () => {
		const {container, queryByText} = render(
			<PopoverBase>
				<PopoverBase.Header>
					<h1>Header</h1>
				</PopoverBase.Header>
				<PopoverBase.Body>
					<p>Body</p>
				</PopoverBase.Body>
				<PopoverBase.Footer>
					<span>Footer</span>
				</PopoverBase.Footer>
			</PopoverBase>
		);

		expect(container.querySelector('div.arrow')).toBeFalsy();
		expect(container.querySelector('.hide')).toBeTruthy();
		expect(container.querySelector('.clay-popover-none')).toBeTruthy();
		expect(queryByText('Header')).toBeTruthy();
		expect(queryByText('Body')).toBeTruthy();
		expect(queryByText('Footer')).toBeTruthy();
	});
});
