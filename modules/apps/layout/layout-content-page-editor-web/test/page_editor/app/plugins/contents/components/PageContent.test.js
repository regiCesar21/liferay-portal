/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import {StoreContextProvider} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/store';
import PageContent from '../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/contents/components/PageContent';

const renderPageContent = (props) =>
	render(
		<StoreContextProvider initialState={[{}, {}]}>
			<PageContent
				actions={{
					editURL: 'editURL',
					permissionsURL: 'permissionsURL',
					viewUsagesURL: 'viewUsagesURL',
				}}
				name="Web Content Article"
				status={{
					hasApprovedVersion: true,
				}}
				title="Test Web Content"
				usagesCount={1}
				{...props}
			></PageContent>
		</StoreContextProvider>
	);

describe('PageContent', () => {
	afterEach(cleanup);

	it('shows properly the name of the content', () => {
		const {getByText} = renderPageContent();

		expect(getByText('Test Web Content')).toBeInTheDocument();
	});

	it('shows properly the content type', () => {
		const {getByText} = renderPageContent();

		expect(getByText('Web Content Article')).toBeInTheDocument();
	});

	it('shows properly the number of usages', () => {
		const {getByText} = renderPageContent();

		expect(getByText('used-in-1-page')).toBeInTheDocument();
	});

	it('shows Edit action in dropdown menu if receives an Edit URL', () => {
		const {getByText} = renderPageContent();

		fireEvent.click(getByText('open-actions-menu'));

		expect(getByText('edit')).toBeInTheDocument();
	});

	it('shows Permissions action in dropdown menu if receives a Permissions URL', () => {
		const {getByText} = renderPageContent();

		fireEvent.click(getByText('open-actions-menu'));

		expect(getByText('permissions')).toBeInTheDocument();
	});

	it('shows View Usages action in dropdown menu if receives a View Usages URL', () => {
		const {getByText} = renderPageContent({
			status: {
				hasApprovedVersion: false,
			},
		});

		fireEvent.click(getByText('open-actions-menu'));

		expect(getByText('view-usages')).toBeInTheDocument();
	});
});
