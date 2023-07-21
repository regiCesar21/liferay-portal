/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import MultiStepNav from '../../../../src/main/resources/META-INF/resources/js/components/multi-step-nav/MultiStepNav.es';

describe('MultiStepNav', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<MultiStepNav currentStep={3} steps={['1', '2', '3', '4', '5']} />
		);

		const steps = container.querySelectorAll('.multi-step-item');

		expect(steps.length).toBe(5);

		expect(steps[0].classList).toContain('complete');
		expect(steps[1].classList).toContain('complete');
		expect(steps[2].classList).toContain('complete');
		expect(steps[3].classList).not.toContain('complete');
		expect(steps[4].classList).not.toContain('complete');
		expect(steps[3].textContent).toBe('4');
		expect(steps[4].textContent).toBe('5');
	});
});
