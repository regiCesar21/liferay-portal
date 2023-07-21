/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import EmptyAuditBarChart from '../../../src/main/resources/META-INF/resources/js/components/EmptyAuditBarChart';

import '@testing-library/jest-dom/extend-expect';

const LEARN_HOW_URL =
	'https://help.liferay.com/hc/en-us/articles/360028820492-Defining-Categories-for-Content';

describe('EmptyAuditBarChart', () => {
	Liferay.Util.sub.mockImplementation((langKey) => langKey);

	afterEach(cleanup);

	it('renders empty bar chart if there is no content labeled with categories', () => {
		const {getByText} = render(
			<EmptyAuditBarChart learnHowURL={LEARN_HOW_URL} />
		);

		expect(getByText('there-is-no-data')).toBeInTheDocument();
		expect(
			getByText('x-learn-how-x-to-tailor-categories-to-your-needs')
		).toBeInTheDocument();
	});
});
