/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, render} from '@testing-library/react';
import React from 'react';

import EditFormViewApp from '../../../../src/main/resources/META-INF/resources/js/pages/form-view/EditFormViewApp.es';
import {FORM_VIEW} from '../../constants.es';

const {EDIT_FORM_VIEW_PROPS, getDataLayoutBuilderProps} = FORM_VIEW;

describe('EditFormViewApp', () => {
	let dataLayoutBuilderProps;

	beforeEach(() => {
		jest.useFakeTimers();

		dataLayoutBuilderProps = getDataLayoutBuilderProps();

		window.Liferay = {
			...window.Liferay,
			componentReady: () =>
				new Promise((resolve) => resolve(dataLayoutBuilderProps)),
		};
	});

	it('renders', async () => {
		const {asFragment} = render(
			<div>
				<div className="tools-control-group">
					<div className="control-menu-level-1-heading" />
				</div>

				<div id={EDIT_FORM_VIEW_PROPS.customObjectSidebarElementId} />

				<EditFormViewApp {...EDIT_FORM_VIEW_PROPS} />
			</div>
		);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(asFragment()).toMatchSnapshot();
	});
});
