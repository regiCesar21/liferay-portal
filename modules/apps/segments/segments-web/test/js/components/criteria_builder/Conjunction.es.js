/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import Conjunction from '../../../../src/main/resources/META-INF/resources/js/components/criteria_builder/Conjunction.es';

const conjunctions = [
	{
		label: 'AND',
		name: 'and',
	},
	{
		label: 'OR',
		name: 'or',
	},
];

describe('Conjunction', () => {
	afterEach(cleanup);

	it('renders with value in editable mode', () => {
		const {
			label: initialConjunctionLabel,
			name: initialConjunctionName,
		} = conjunctions[0];

		const {getAllByText} = render(
			<Conjunction
				conjunctionName={initialConjunctionName}
				editing={true}
				onSelect={() => {}}
				supportedConjunctions={conjunctions}
			/>,
			{
				baseElement: document.body,
			}
		);

		const [selectedConjunction] = getAllByText(initialConjunctionLabel);

		expect(selectedConjunction.tagName).toBe('BUTTON');
	});

	it('renders with value in non-editable mode', () => {
		const {
			label: initialConjunctionLabel,
			name: initialConjunctionName,
		} = conjunctions[0];

		const {getByText} = render(
			<Conjunction
				conjunctionName={initialConjunctionName}
				editing={false}
				onSelect={() => {}}
				supportedConjunctions={conjunctions}
			/>,
			{
				baseElement: document.body,
			}
		);

		const selectedConjunction = getByText(initialConjunctionLabel);

		expect(selectedConjunction.tagName).toBe('DIV');
	});

	it('dropdown opens on click in editable mode', () => {
		const {
			label: initialConjunctionLabel,
			name: initialConjunctionName,
		} = conjunctions[0];

		const {getAllByText} = render(
			<Conjunction
				conjunctionName={initialConjunctionName}
				editing={true}
				onSelect={() => {}}
				supportedConjunctions={conjunctions}
			/>,
			{
				baseElement: document.body,
			}
		);

		const dropdownMenu = document.body.querySelector('.dropdown-menu');
		const [selectedConjunction] = getAllByText(initialConjunctionLabel);

		expect(dropdownMenu.className).not.toContain('show');

		userEvent.click(selectedConjunction);

		expect(dropdownMenu.className).toContain('show');
	});

	it('onSelect prop is triggered on selection with conjuntionName param in editable mode', () => {
		const onSelectMock = jest.fn(() => {});
		const {
			label: initialConjunctionLabel,
			name: initialConjunctionName,
		} = conjunctions[0];

		const {getAllByText, getByText} = render(
			<Conjunction
				conjunctionName={initialConjunctionName}
				editing={true}
				onSelect={onSelectMock}
				supportedConjunctions={conjunctions}
			/>,
			{
				baseElement: document.body,
			}
		);

		const [selectedConjunction] = getAllByText(initialConjunctionLabel);

		userEvent.click(selectedConjunction);

		const conjunctionToSelect = conjunctions[1];

		const orOption = getByText(conjunctionToSelect.label);

		userEvent.click(orOption);

		expect(onSelectMock).toHaveBeenCalledTimes(1);
		expect(onSelectMock).toHaveBeenCalledWith(conjunctionToSelect.name);
	});
});
