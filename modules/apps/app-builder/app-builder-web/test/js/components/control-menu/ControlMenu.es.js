/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {HashRouter} from 'react-router-dom';

import {AppContext} from '../../../../src/main/resources/META-INF/resources/js/AppContext.es';
import ControlMenu from '../../../../src/main/resources/META-INF/resources/js/components/control-menu/ControlMenu.es';

describe('ControlMenu', () => {
	afterEach(() => {
		cleanup();
		jest.restoreAllMocks();
	});

	it('renders InlineControlMenu for a standalone deployment', () => {
		const context = {
			appDeploymentType: 'standalone',
			controlMenuElementId: 'standalone',
		};

		const props = {
			backURL: '../',
			title: 'title',
		};

		const {baseElement, queryByText} = render(
			<>
				<div id="standalone"></div>
				<HashRouter>
					<AppContext.Provider value={context}>
						<ControlMenu {...props} />
					</AppContext.Provider>
				</HashRouter>
			</>
		);

		const {title} = props;

		expect(queryByText(title)).toBeTruthy();
		expect(document.title).toBe(title);
		expect(
			baseElement.querySelector('.app-builder-control-menu.standalone')
		).toBeTruthy();
	});

	it('renders InlineControlMenu for a widget deployment', () => {
		const context = {
			appDeploymentType: 'widget',
		};

		const props = {
			backURL: 'https://liferay.com/',
			title: 'title',
		};

		const {baseElement, queryByText} = render(
			<HashRouter>
				<AppContext.Provider value={context}>
					<ControlMenu {...props} />
				</AppContext.Provider>
			</HashRouter>
		);

		const {title} = props;

		expect(queryByText(title)).toBeTruthy();
		expect(document.title).toBe(title);
		expect(
			baseElement.querySelector('.app-builder-control-menu.widget')
		).toBeTruthy();
		expect(baseElement.querySelector('a').href).toBe(props.backURL);
	});

	it('renders PortalControlMenu', () => {
		const context = {
			appDeploymentType: 'control-menu',
		};

		const props = {
			backURL: '../../',
			title: 'title',
		};

		const {queryByText} = render(
			<HashRouter>
				<AppContext.Provider value={context}>
					<div className="tools-control-group">
						<div className="control-menu-level-1-heading"></div>
						<div className="sites-control-group">
							<div className="control-menu-nav"></div>
						</div>
					</div>
					<ControlMenu {...props} />
				</AppContext.Provider>
			</HashRouter>
		);

		expect(queryByText(props.title)).toBeTruthy();
	});
});
