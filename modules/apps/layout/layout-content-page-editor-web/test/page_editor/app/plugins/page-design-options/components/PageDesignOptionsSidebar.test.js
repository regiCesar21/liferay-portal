/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {cleanup, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import LayoutService from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/services/LayoutService';
import {StoreAPIContextProvider} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/store/index';
import changeMasterLayout from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/changeMasterLayout';
import PageDesignOptionsSidebar from '../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/page-design-options/components/PageDesignOptionsSidebar';

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/services/LayoutService',
	() => ({changeStyleBookEntry: jest.fn(() => Promise.resolve())})
);

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/changeMasterLayout',
	() => jest.fn()
);

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/index',
	() => ({
		config: {
			layoutType: '0',
			masterLayouts: [
				{masterLayoutPlid: '0', name: 'Blank'},
				{
					masterLayoutPlid: '15',
					name: 'Pablo Master Layout',
				},
			],
			portletNamespace: 'ContentPageEditorPortlet',
			styleBookEnabled: true,
			styleBooks: [
				{
					name: 'Pablo Style',
					styleBookEntryId: '3',
				},
			],
		},
	})
);

const renderComponent = ({
	hasUpdatePermissions = true,
	lockedExperience = false,
} = {}) => {
	return render(
		<StoreAPIContextProvider
			dispatch={() => Promise.resolve({})}
			getState={() => ({
				masterLayout: {
					masterLayoutPlid: '0',
				},
				permissions: {
					LOCKED_SEGMENTS_EXPERIMENT: lockedExperience,
					UPDATE: hasUpdatePermissions,
				},
			})}
		>
			<PageDesignOptionsSidebar />
		</StoreAPIContextProvider>
	);
};

describe('PageDesignOptionsSidebar', () => {
	afterEach(cleanup);
	changeMasterLayout.mockClear();

	it('has a sidebar panel title', () => {
		const {getByText} = renderComponent();

		expect(getByText('page-design-options')).toBeInTheDocument();
	});

	it('calls changeMasterLayout when a master layout is selected', () => {
		const {getByLabelText} = renderComponent();
		const button = getByLabelText('Pablo Master Layout');

		userEvent.click(button);

		expect(changeMasterLayout).toBeCalledWith(
			expect.objectContaining({masterLayoutPlid: '15'})
		);
	});

	it('calls changeStyleBookEntry when a style is selected', () => {
		const {getByLabelText} = renderComponent();
		const button = getByLabelText('Pablo Style');

		userEvent.click(button);

		expect(LayoutService.changeStyleBookEntry).toHaveBeenCalledTimes(1);
		expect(LayoutService.changeStyleBookEntry).toHaveBeenCalledWith(
			expect.objectContaining({
				styleBookEntryId: '3',
			})
		);
	});
});
