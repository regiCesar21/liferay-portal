/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {Component} from 'react';

import {getLocalizedText} from '../utils/utils';
import Icon from './Icon';

class PaneSearchBar extends Component {
	constructor(props) {
		super(props);

		this.onSubmit.bind(this);
	}

	onSubmit(e) {
		e.preventDefault();
	}

	render() {
		const {onLookUp, spritemap} = this.props;

		return (
			<div className="pane-search-bar">
				<form name="searchUser" onSubmit={this.onSubmit}>
					<span>
						<input
							autoComplete={'off'}
							name="search-user"
							onChange={onLookUp}
							placeholder={`${getLocalizedText('search')}...`}
							tabIndex="4"
							type="text"
						/>
					</span>
					<span>
						<button tabIndex="5" type="submit">
							<Icon spritemap={spritemap} symbol={'search'} />
						</button>
					</span>
				</form>
			</div>
		);
	}
}

export default PaneSearchBar;
