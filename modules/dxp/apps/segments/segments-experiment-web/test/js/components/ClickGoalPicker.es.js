/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {cleanup, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import ClickGoalPicker from '../../../src/main/resources/META-INF/resources/js/components/ClickGoalPicker/ClickGoalPicker.es';
import {segmentsExperiment} from '../fixtures.es';
import renderApp from '../renderApp.es';

describe('Segments Experiments with Click Goal', () => {
	afterEach(cleanup);

	it('Experiment renders when goal value is click', () => {
		const experiment = {
			...segmentsExperiment,
			goal: {
				label: 'Click',
				value: 'click',
			},
		};

		const {getByText} = renderApp({
			initialSegmentsExperiment: segmentsExperiment,
		});

		getByText(experiment.name);
		getByText('review-and-run-test');
	});

	it('User clicks in set element button, the button turns from default to active mode', () => {
		const experiment = {
			...segmentsExperiment,
			goal: {
				label: 'Click',
				value: 'click',
			},
		};

		const {getByText} = render(
			<ClickGoalPicker segmentsExperiment={experiment} />
		);

		const setElementButton = getByText('set-element');
		userEvent.click(setElementButton);

		const clickGoalRoot = document.body.querySelector(
			'div.lfr-segments-experiment-click-goal-root'
		);
		expect(clickGoalRoot).not.toBe(null);
	});

	it('The user can set a click target element in a draft experiment', () => {
		const experiment = {
			...segmentsExperiment,
			goal: {
				label: 'Click',
				value: 'click',
			},
		};

		const {getByText} = render(
			<ClickGoalPicker segmentsExperiment={experiment} />
		);

		expect(experiment.status.label).toBe('Draft');
		getByText(
			'a-clickable-element-on-the-page-must-be-selected-to-be-measured'
		);
		const setElementButton = getByText('set-element');
		expect(setElementButton.attributes['disabled']).toBe(undefined);
	});

	it('The user can edit a selected click target element in a draft experiment', () => {
		const experiment = {
			...segmentsExperiment,
			goal: {
				label: 'Click',
				target: '#myButtonId',
				value: 'click',
			},
		};

		const {getByText} = render(
			<ClickGoalPicker target={experiment.goal.target} />
		);

		expect(experiment.status.label).toBe('Draft');
		const editElement = getByText('edit-element');
		expect(editElement.attributes['disabled']).toBe(undefined);
	});

	it('When the user set element as click target, then the element is set as the click target and the id of the element as a link', () => {
		const experiment = {
			...segmentsExperiment,
			goal: {
				label: 'Click',
				target: '#myButtonId',
				value: 'click',
			},
		};

		const {getByText} = render(
			<ClickGoalPicker target={experiment.goal.target} />
		);

		const targetElement = getByText(experiment.goal.target);
		expect(targetElement.attributes['href'].value).toBe(
			experiment.goal.target
		);
	});

	test.todo(
		'All clickable elements highlighted when the user clicks on set element button'
	);

	test.todo(
		'A tooltip click element to set as click target for your goal appears when the user clicks on set element button'
	);

	test.todo('Selectable as target elements show tooltips on hover');

	test.todo(
		'Cancel selection click target element proccess when clicking out of selection zone'
	);

	test.todo(
		'When hovering over to invalid click target elements, the mouse is displayed in not-allowed mode'
	);

	test.todo('The user can remove a selected click target in a draft element');

	test.todo(
		'The user clicks in the UI reference to the selected click target element, the page scrolls to make it visible'
	);
});
