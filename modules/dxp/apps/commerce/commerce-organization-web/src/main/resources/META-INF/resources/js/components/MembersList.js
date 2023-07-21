/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {Component} from 'react';

import Member from './Member';
import NoMembers from './NoMembers';

class MembersList extends Component {
	render() {
		const {imagesPath, isLoading, members, spritemap} = this.props;

		return (
			<div className="pane-members-list">
				{
					<ul>
						{!isLoading &&
							!!members.length &&
							members.map((member, index) => {
								return (
									<Member
										imagesPath={imagesPath}
										key={index}
										member={member}
									/>
								);
							})}
					</ul>
				}

				{!isLoading && !members.length && (
					<NoMembers spritemap={spritemap} />
				)}
			</div>
		);
	}
}

export default MembersList;
