/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {Component} from 'react';

import {LIST_BY} from '../utils/constants';

const {ACCOUNTS, USERS} = LIST_BY;

function isSelected(listBy, callee) {
	return listBy === callee ? 'selected-pane' : '';
}

function Tab(props) {
	const {listBy, onViewSelected, totalMembers, viewName} = props;

	return (
		<span
			className={
				!totalMembers ? 'disabled' : isSelected(listBy, viewName)
			}
			onClick={!totalMembers ? null : onViewSelected.bind(this, viewName)}
			role="button"
			tabIndex="-1"
		>
			{`${viewName} (${totalMembers})`}
		</span>
	);
}

class PaneViewSelector extends Component {
	render() {
		const {listBy, onViewSelected, totalAccounts, totalUsers} = this.props;

		return (
			<div className="pane-list-selector">
				{[USERS, ACCOUNTS].map((viewName, i) => {
					const totalMembers =
						viewName === USERS ? totalUsers : totalAccounts;

					return (
						<Tab
							key={i}
							listBy={listBy}
							onViewSelected={onViewSelected}
							totalMembers={totalMembers}
							viewName={viewName}
						/>
					);
				})}
			</div>
		);
	}
}

export default PaneViewSelector;
