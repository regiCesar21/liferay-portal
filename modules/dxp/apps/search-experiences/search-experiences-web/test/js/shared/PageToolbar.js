/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, fireEvent, render} from '@testing-library/react';
import React from 'react';

import PageToolbar from '../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/shared/PageToolbar';

import '@testing-library/jest-dom/extend-expect';

jest.useFakeTimers();

const onChangeTitleAndDescription = jest.fn();
const onSubmit = jest.fn();

function renderPageToolbar(props) {
	return render(
		<PageToolbar
			description=""
			onCancel="/link"
			onChangeTab={jest.fn()}
			onChangeTitleAndDescription={onChangeTitleAndDescription}
			onSubmit={onSubmit}
			tab="query-builder"
			tabs={{
				'query-builder': 'query-builder',
			}}
			title=""
			{...props}
		/>
	);
}

describe('PageToolbar', () => {
	it('renders the page toolbar form', () => {
		const {container} = renderPageToolbar();

		expect(container).not.toBeNull();
	});

	it('renders the title', () => {
		const title = 'Apple';

		const {getByText} = renderPageToolbar({title});

		getByText(title);
	});

	it('calls onChangeTitle when updating title', () => {
		const title = 'Apple';

		const {getByLabelText, getByText} = renderPageToolbar({
			title,
		});

		getByText(title);

		fireEvent.click(getByLabelText('edit-title'));

		act(() => jest.runAllTimers());

		fireEvent.change(getByLabelText('title'), {
			target: {value: 'Banana'},
		});

		fireEvent.click(getByText('done'));

		act(() => jest.runAllTimers());

		expect(onChangeTitleAndDescription).toHaveBeenCalled();
	});

	it('calls onChangeTitle when updating description', () => {
		const title = 'Apple';

		const description = 'A fruit';

		const {getByLabelText, getByText} = renderPageToolbar({
			description,
			title,
		});

		getByText('A fruit');

		fireEvent.click(getByLabelText('edit-description'));

		act(() => jest.runAllTimers());

		fireEvent.change(getByLabelText('description'), {
			target: {value: 'A red fruit'},
		});

		fireEvent.click(getByText('done'));

		act(() => jest.runAllTimers());

		expect(onChangeTitleAndDescription).toHaveBeenCalled();
	});

	it('offers link to cancel', () => {
		const {getByText} = renderPageToolbar();

		expect(getByText('cancel')).toHaveAttribute('href', '/link');
	});

	it('calls onSubmit when clicking on Save', () => {
		const {getByText} = renderPageToolbar();

		fireEvent.click(getByText('save'));

		expect(onSubmit).toHaveBeenCalled();
	});

	it('disables submit button when submitting', () => {
		const {getByText} = renderPageToolbar({isSubmitting: true});

		expect(getByText('save')).toBeDisabled();
	});

	// Disabled, behavior when opening Modal focuses on the modal first to
	// announce that it is open.

	xit('focuses on the title input when clicked on', () => {
		const title = 'Apple';

		const {getByLabelText} = renderPageToolbar({
			title,
		});

		fireEvent.click(getByLabelText('edit-title'));

		act(() => jest.runAllTimers());

		expect(getByLabelText('title')).toHaveFocus();
	});

	// Disabled, behavior when opening Modal focuses on the modal first to
	// announce that it is open.

	xit('focuses on the description input when clicked on', () => {
		const title = 'Apple';

		const {getByLabelText} = renderPageToolbar({
			title,
		});

		fireEvent.click(getByLabelText('edit-description'));

		act(() => jest.runAllTimers());

		expect(getByLabelText('description')).toHaveFocus();
	});
});
