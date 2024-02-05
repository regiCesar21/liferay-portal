import React from 'react';
import {render} from 'react-testing-library';

import * as changelogTable from '../ChangelogTable';
import TableResults from '../TableResults';

describe('TableResults', () => {
	const error = {
		error: {
			message: 'Please enter a valid version range.',
			name:
				'class com.liferay.osb.customer.release.tool.web.internal.exception.VersionRangeException'
		}
	};

	const contentObj = {
		results: [
			{
				components: ['Frontend Infrastructure > WYSIWYG'],
				description: 'description',
				key: 'LPD-90100',
				release: 'GA',
				summary: 'IE11 Web Image Resizing',
				url: '/'
			},
			{
				components: ['Accessibility'],
				description: 'description 2',
				key: 'LPD-85155',
				release: 'GA',
				summary: 'Add menu - Heading order invalid',
				url: '/'
			}
		],
		total: 2
	};

	const tab = {
		tabDescription: 'description for the tab',
		tabName: 'changelog'
	};

	it('renders error correctly', () => {
		const {container, getByRole} = render(
			<TableResults jsonObject={error} tab={tab} table={changelogTable} />
		);

		expect(getByRole('alert')).toBeTruthy();
		expect(container).toMatchSnapshot();
	});

	it('renders content correctly', () => {
		const {container, getByRole} = render(
			<TableResults
				jsonObject={contentObj}
				tab={tab}
				table={changelogTable}
			/>
		);

		expect(getByRole('table')).toBeTruthy();
		expect(container).toMatchSnapshot();
	});
});
