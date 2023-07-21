/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {Component} from 'react';

import {bindAll, callApi, setupDataset} from '../utils/utils';
import MembersPane from './MembersPane';
import OrgChart from './OrgChart';

class OrgChartContainer extends Component {
	constructor(props) {
		super(props);

		bindAll(this, 'handleNodeClick', 'setSelection', 'handleInitialLoad');

		const apiParameters = {
			baseURL: props.apiURL,
		};

		this.$didMount = new Promise((resolve) =>
			callApi(apiParameters).then((data) => {
				this.setState(
					() => {
						return {
							rootData: setupDataset(data),
							selectedId: 0,
						};
					},
					() => {
						resolve(true);
					}
				);
			})
		);
	}

	handleInitialLoad() {
		this.setState(() => {
			return {loading_: false};
		});
	}

	handleNodeClick(id) {
		return callApi({
			baseURL: this.props.apiURL,
			id,
		}).then(({organizations}) =>
			organizations.length ? organizations : null
		);
	}

	setSelection(id, colorIdentifier) {
		this.setState(() => {
			return {colorIdentifier, selectedId: id};
		});
	}

	render() {
		const {apiURL, imagesPath, namespace, spritemap} = this.props;

		const {colorIdentifier, rootData, selectedId} = this.state || {};

		return (
			<div className="organization-network">
				{!!rootData && (
					<OrgChart
						data={rootData}
						namespace={namespace}
						onNodeClick={this.setSelection}
						requestChildren={this.handleNodeClick}
						selectedId={selectedId}
					/>
				)}

				{!!selectedId && (
					<MembersPane
						apiURL={apiURL}
						colorIdentifier={colorIdentifier}
						id={selectedId}
						imagesPath={imagesPath}
						spritemap={spritemap}
					/>
				)}
			</div>
		);
	}
}

export default OrgChartContainer;
