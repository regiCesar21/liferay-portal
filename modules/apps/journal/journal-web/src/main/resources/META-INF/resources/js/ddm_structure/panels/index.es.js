/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import BasicInfoPanel from './BasicInfoPanel.es';
import {Component} from './PluginContext.es';

/**
 * Entry-point for "Properties" (sidebar pane) functionality.
 */
export default class {
	constructor({app, panel}) {
		this.Component = Component(app);
		this.title = panel.label;
		this.url = panel.url;
	}

	renderSidebar() {
		const {Component} = this;

		return (
			<Component>
				<BasicInfoPanel url={this.url} />
			</Component>
		);
	}
}
