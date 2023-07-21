/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import ToggleSwitch from '../../../../src/main/resources/META-INF/resources/js/components/toggle-switch/ToggleSwitch.es';

describe('ToggleSwitch', () => {
	afterEach(() => {
		cleanup();
		jest.restoreAllMocks();
	});

	it('onChange is called when clicked', () => {
		const onChangeCallback = jest.fn();

		const {container} = render(
			<ToggleSwitch onChange={onChangeCallback} />
		);

		const input = container.querySelector('input[type=checkbox]');

		expect(input.checked).toBe(false);
		expect(input.title).toBe('turn-on');

		fireEvent.click(input);

		expect(input.checked).toBe(true);
		expect(input.title).toBe('turn-off');

		fireEvent.click(input);

		expect(input.checked).toBe(false);
		expect(onChangeCallback.mock.calls.length).toBe(2);
		expect(onChangeCallback.mock.calls[0][0]).toBe(true);
		expect(onChangeCallback.mock.calls[1][0]).toBe(false);
	});
});
