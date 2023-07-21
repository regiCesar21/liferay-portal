/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import EmptyState, {
	withEmpty,
} from '../../../../src/main/resources/META-INF/resources/js/components/table/EmptyState.es';

describe('EmptyState', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {queryByText} = render(
			<EmptyState description="description" title="title" />
		);

		expect(queryByText('title')).toBeTruthy();
		expect(queryByText('description')).toBeTruthy();
	});

	describe('withEmpty', () => {
		afterEach(cleanup);

		const Component = () => <div>component</div>;

		it('renders component', () => {
			const {queryByText} = render(withEmpty(Component)({}));

			expect(queryByText('component')).toBeTruthy();
		});

		it('renders default empty', () => {
			const {queryByText} = render(
				withEmpty(Component)({
					isEmpty: true,
					keywords: '',
				})
			);

			expect(queryByText('there-are-no-entries')).toBeTruthy();
		});

		it('renders empty search', () => {
			const {queryByText} = render(
				withEmpty(Component)({
					emptyState: {},
					isEmpty: true,
					keywords: 'text',
				})
			);

			expect(queryByText('no-results-were-found')).toBeTruthy();
			expect(queryByText('there-are-no-results-for-x')).toBeTruthy();
		});

		it('renders empty filtered', () => {
			const {queryByText} = render(
				withEmpty(Component)({
					emptyState: {},
					isEmpty: true,
					isFiltered: true,
					keywords: '',
				})
			);

			expect(queryByText('no-results-were-found')).toBeTruthy();
			expect(
				queryByText('there-are-no-results-with-these-attributes')
			).toBeTruthy();
		});

		it('renders empty search and filtered', () => {
			const {queryByText} = render(
				withEmpty(Component)({
					emptyState: {},
					isEmpty: true,
					isFiltered: true,
					keywords: 'text',
				})
			);

			expect(queryByText('no-results-were-found')).toBeTruthy();
			expect(
				queryByText('there-are-no-results-for-x-with-these-attributes')
			).toBeTruthy();
		});
	});
});
