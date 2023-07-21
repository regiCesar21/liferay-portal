/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import BasicInformation from '../../../src/main/resources/META-INF/resources/js/components/BasicInformation';

import '@testing-library/jest-dom/extend-expect';

describe('BasicInformation', () => {
	afterEach(cleanup);

	it('renders author, publish date and title', () => {
		const testProps = {
			author: {
				authorId: '',
				name: 'John Tester',
				url: '',
			},
			canonicalURL:
				'http://localhost:8080/en/web/guest/-/basic-web-content',
			languageTag: 'en-US',
			publishDate: 'Thu Feb 17 08:17:57 GMT 2020',
			title: 'A testing page',
		};

		const {getByText} = render(
			<BasicInformation
				author={testProps.author}
				canonicalURL={testProps.canonicalURL}
				languageTag={testProps.languageTag}
				publishDate={testProps.publishDate}
				title={testProps.title}
			/>
		);

		expect(getByText(testProps.title)).toBeInTheDocument();

		expect(getByText(testProps.canonicalURL)).toBeInTheDocument();

		const formattedPublishDate = 'February 17, 2020';
		expect(
			getByText('published-on-' + formattedPublishDate)
		).toBeInTheDocument();

		expect(
			getByText('authored-by-' + testProps.author.name)
		).toBeInTheDocument();
	});
});
