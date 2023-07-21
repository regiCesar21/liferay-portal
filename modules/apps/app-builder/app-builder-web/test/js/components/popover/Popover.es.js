/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import {Align} from 'metal-position';
import React from 'react';

import Popover from '../../../../src/main/resources/META-INF/resources/js/components/popover/Popover.es';

const getTitle = () => <h1>Title</h1>;
const getContent = () => <p>Content</p>;
const getFooter = () => <span>Footer</span>;

describe('Popover', () => {
	afterEach(() => {
		cleanup();
		jest.restoreAllMocks();
	});

	it('renders popover on top', () => {
		jest.spyOn(Align, 'align').mockImplementation(() => 0);

		const {container, queryByText} = render(
			<Popover
				ref={React.createRef()}
				showArrow
				title={getTitle}
				visible
			/>
		);

		expect(queryByText('Title')).toBeTruthy();

		expect(container.querySelector('div.arrow')).toBeTruthy();
		expect(container.querySelector('.clay-popover-top')).toBeTruthy();
		expect(container.querySelector('.no-content')).toBeTruthy();
		expect(container.querySelector('.popover-body')).toBeTruthy();
	});

	it('renders popover on the right with children', () => {
		jest.spyOn(Align, 'align').mockImplementation(() => 2);

		const {container, queryByText} = render(
			<Popover
				content={getContent}
				footer={getFooter}
				ref={React.createRef()}
				showArrow
				suggestedPosition="right"
				title={getTitle}
				visible
			/>
		);

		expect(queryByText('Title')).toBeTruthy();
		expect(queryByText('Content')).toBeTruthy();
		expect(queryByText('Footer')).toBeTruthy();

		expect(container.querySelector('div.arrow')).toBeTruthy();
		expect(container.querySelector('.clay-popover-right')).toBeTruthy();
		expect(container.querySelector('.no-content')).toBeFalsy();
		expect(container.querySelector('.popover-header')).toBeTruthy();
		expect(container.querySelector('.popover-body')).toBeTruthy();
		expect(container.querySelector('.popover-footer')).toBeTruthy();
	});

	it('renders popover with no ref', () => {
		jest.spyOn(Align, 'align').mockImplementation(() => 0);

		const {container, queryByText} = render(
			<Popover title={getTitle} visible />
		);

		expect(queryByText('Title')).toBeTruthy();

		expect(container.querySelector('div.arrow')).toBeTruthy();
		expect(container.querySelector('.no-content')).toBeTruthy();
		expect(container.querySelector('.hide')).toBeFalsy();
	});
});
