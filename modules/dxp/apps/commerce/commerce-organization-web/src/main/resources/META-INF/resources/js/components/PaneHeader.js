/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {Component} from 'react';

import {bindAll} from '../utils/utils';
import PaneOrgInfo from './PaneOrgInfo';
import PaneSearchBar from './PaneSearchBar';
import PaneViewSelector from './PaneViewSelector';

class PaneHeader extends Component {
	constructor(props) {
		super(props);

		this.state = {
			showMenu: false,
		};

		bindAll(this, 'hideMenu', 'showMenu');
	}

	hideMenu() {
		this.setState(() => ({
			showMenu: false,
		}));
	}

	showMenu() {
		this.setState((state) => ({
			showMenu: !state.showMenu,
		}));
	}

	render() {
		const {
			colorIdentifier,
			listBy,
			onLookUp,
			onViewSelected,
			orgName,
			spritemap,
			totalAccounts,
			totalSubOrg,
			totalUsers,
		} = this.props;

		return (
			<div className="pane-header">
				<PaneOrgInfo
					childrenNo={totalSubOrg}
					colorIdentifier={colorIdentifier}
					orgName={orgName}
					showMenu={this.showMenu}
				/>

				<PaneViewSelector
					listBy={listBy}
					onViewSelected={onViewSelected}
					totalAccounts={totalAccounts}
					totalUsers={totalUsers}
				/>

				<PaneSearchBar onLookUp={onLookUp} spritemap={spritemap} />
			</div>
		);
	}
}

export default PaneHeader;
