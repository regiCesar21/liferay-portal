import React from 'react';
import {cleanup, fireEvent, render} from 'react-testing-library';

import times from 'lodash.times';

import Changelog from '../Changelog';

const setup = () => {
	const changelogJSONObj = {
		results: [
			{
				components: ['Frontend Infrastructure > WYSIWYG'],
				description: 'description',
				key: 'LPD-90100',
				release: 'GA',
				summary: 'summary 1',
				url: '/'
			},
			{
				components: ['Accessibility'],
				description: 'description 2',
				key: 'LPD-85155',
				release: 'GA',
				summary: 'summary 2',
				url: '/'
			}
		],
		total: 2
	};

	const changelogJSONObjSize51 = {
		results: times(51, (i) => {
			return {
				components: [`component ${i}`],
				description: `description ${i}`,
				key: `${i}`,
				release: 'GA',
				summary: `summary ${i}`,
				url: '/'
			};
		}),
		total: 51
	};

	const filters = times(10, (i) => {
		return {
			name: `component ${i}`,
			value: `${i}`
		};
	});

	const noResults = {
		results: [],
		total: 0
	};

	const utils = render(
		<Changelog
			description="description"
			endpoint="/"
			filters={filters}
			jsonObject={changelogJSONObj}
		/>
	);

	return {
		changelogJSONObj,
		changelogJSONObjSize51,
		filters,
		noResults,
		...utils
	};
};

afterEach(cleanup);

describe('Changelog', () => {
	it('renders changelog results correctly', () => {
		const {container} = setup();

		expect(container).toMatchSnapshot();
	});

	it('renders changelog with pagination when results exceed 50 items', () => {
		const {changelogJSONObjSize51, filters} = setup();

		const {container} = render(
			<Changelog
				description="description"
				endpoint="/"
				filters={filters}
				jsonObject={changelogJSONObjSize51}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders page one of new results when Release column is sorted', () => {
		const {changelogJSONObjSize51, filters} = setup();

		const {container} = render(
			<Changelog
				description="description"
				endpoint="/"
				filters={filters}
				jsonObject={changelogJSONObjSize51}
			/>
		);

		const sortButton = container.querySelector('.sorting-indicator');

		fireEvent.click(container.querySelector('button[value="2"]'));

		expect(
			container.querySelector('.pagination-current span').innerHTML
		).toBe('2');

		fireEvent.click(sortButton);

		expect(
			container.querySelector('.pagination-current span').innerHTML
		).toBe('1');
	});

	it('renders no results correctly', () => {
		const {filters, noResults} = setup();

		const {container} = render(
			<Changelog
				description="description"
				endpoint="/"
				filters={filters}
				jsonObject={noResults}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders details modal when a ticket summary is clicked', () => {
		const {container, queryByRole, queryByText} = setup();

		fireEvent.click(queryByText('summary 1'));

		expect(queryByRole('dialog')).toBeTruthy();
		expect(queryByText('details')).toBeTruthy();
		expect(container).toMatchSnapshot();
	});

	it('updates details modal when a different ticket summary is clicked', () => {
		const {container, queryByRole, queryByText} = setup();

		fireEvent.click(queryByText('summary 1'));

		expect(queryByRole('dialog')).toBeTruthy();
		expect(queryByText('details')).toBeTruthy();

		fireEvent.click(queryByText('summary 2'));
		expect(container).toMatchSnapshot();
	});

	it('shows seven filter checkboxes and a See More option below the filters if more than seven filter values are available', () => {
		const {container, getByText} = setup();

		expect(
			container.querySelectorAll('.filter-checkbox-container').length
		).toBe(7);
		expect(getByText('see-more')).toBeTruthy();
	});

	it('does not show a See More option below the filters if seven or less filter values are available', () => {
		const {changelogJSONObj} = setup();

		const {container} = render(
			<Changelog
				description="description"
				endpoint="/"
				filters={[
					{
						name: 'component 1',
						value: '1'
					},
					{
						name: 'component 2',
						value: '2'
					}
				]}
				jsonObject={changelogJSONObj}
			/>
		);

		expect(
			container.querySelectorAll('.filter-checkbox-container').length
		).toBe(2);
		expect(container).toMatchSnapshot();
	});

	it('expands the refine by filters if Show More is clicked and displays a Show Less option', () => {
		const {container, getByText} = setup();

		fireEvent.click(getByText('see-more'));

		expect(
			container.querySelectorAll('.filter-checkbox-container').length
		).toBeGreaterThan(7);
		expect(getByText('see-less')).toBeTruthy();
	});

	it('shows a Clear All option when a filter is selected and removes the option when all filters are unselected', () => {
		const {container, queryByText} = setup();

		fireEvent.click(container.querySelector('input[type=checkbox]'));

		expect(queryByText('clear-all')).toBeTruthy();

		fireEvent.click(container.querySelector('input[type=checkbox]'));

		expect(queryByText('clear-all')).toBeFalsy();
	});

	it('clears all filter selections and text input field when Clear All is clicked', () => {
		const {container, getByText} = setup();

		const checkbox = container.querySelector('input[type=checkbox]');
		const textInput = container.querySelector('input[type=text]');

		fireEvent.click(checkbox);
		fireEvent.change(textInput, {
			target: {
				value: 'test'
			}
		});

		fireEvent.click(getByText('clear-all'));

		expect(checkbox.checked).toBeFalsy();
		expect(textInput.value).toBe('');
	});
});
