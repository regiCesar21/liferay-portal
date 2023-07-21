/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React, {cloneElement, useState} from 'react';

import UpdateDueDateStep from '../../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/modal/update-due-date/UpdateDueDateStep.es';

import '@testing-library/jest-dom/extend-expect';

const ContainerMock = ({children}) => {
	const [value, setValue] = useState('');

	return cloneElement(children, {setValue, value});
};

const wrapperMock = {
	wrapper: ContainerMock,
};

describe('The TimePickerInput component should be render with AM/PM format', () => {
	afterEach(cleanup);

	test('Render with error state and select any option', () => {
		const {getAllByRole, getByPlaceholderText} = render(
			<UpdateDueDateStep.TimePickerInput format="H:mm a" isAmPm />,
			wrapperMock
		);

		const timeInput = getByPlaceholderText('HH:mm am/pm');

		expect(timeInput.parentNode).toHaveClass('has-error');
		expect(timeInput.value).toBe('');

		fireEvent.focus(timeInput);

		const items = getAllByRole('listitem');

		items.forEach((item) => {
			expect(item.innerHTML).toMatch(/[0-9]{1,2}:[0-9]{2}\s(AM|PM)/);
		});

		fireEvent.mouseDown(items[0]);

		expect(timeInput.parentNode).not.toHaveClass('has-error');
		expect(timeInput.value).toBe('12:00 AM');

		fireEvent.focus(timeInput);
		fireEvent.mouseDown(items[1]);

		expect(timeInput.value).toBe('12:30 AM');

		fireEvent.change(timeInput, {target: {value: '14:00'}});

		expect(timeInput.parentNode).toHaveClass('has-error');

		fireEvent.change(timeInput, {target: {value: '2:00 PM'}});

		expect(timeInput.parentNode).not.toHaveClass('has-error');
	});
});

describe('The TimePickerInput component should be render without AM/PM format', () => {
	test('Render with error state and select any option', () => {
		const {getAllByRole, getByPlaceholderText} = render(
			<UpdateDueDateStep.TimePickerInput format="H:mm" />,
			wrapperMock
		);

		const timeInput = getByPlaceholderText('HH:mm');

		expect(timeInput.parentNode).toHaveClass('has-error');
		expect(timeInput.value).toBe('');

		fireEvent.focus(timeInput);

		const items = getAllByRole('listitem');

		items.forEach((item) => {
			expect(item.innerHTML).toMatch(/[0-9]{1,2}:[0-9]{2}/);
		});

		fireEvent.mouseDown(items[0]);

		expect(timeInput.parentNode).not.toHaveClass('has-error');
		expect(timeInput.value).toBe('00:00');

		fireEvent.focus(timeInput);
		fireEvent.mouseDown(items[1]);

		expect(timeInput.value).toBe('00:30');

		fireEvent.change(timeInput, {target: {value: '12:00 AM'}});

		expect(timeInput.parentNode).toHaveClass('has-error');

		fireEvent.change(timeInput, {target: {value: '14:00'}});

		expect(timeInput.parentNode).not.toHaveClass('has-error');

		fireEvent.focus(timeInput);

		let timePickerPopover = document.querySelector('.clay-popover-bottom');

		expect(timePickerPopover).toBeTruthy();

		fireEvent.blur(timeInput);

		timePickerPopover = document.querySelector('.clay-popover-bottom');

		expect(timePickerPopover).toBeFalsy();
	});
});
