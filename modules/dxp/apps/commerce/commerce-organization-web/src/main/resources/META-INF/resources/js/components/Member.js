/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

function Member(props) {
	const {email: role, imageUrl, name} = props.member;

	return (
		<li className="member" role="button" tabIndex="-1">
			<span
				className="member-picture"
				style={{
					background: `url(/image${imageUrl}) center no-repeat #CCC`,
				}}
			></span>
			<span className="member-data">
				<p className="member-data-name">{name}</p>
				<p>
					<span className="member-data-role">{role}</span>
				</p>
			</span>
		</li>
	);
}

Member.defaultProps = {
	name: '',
	pictureUrl: '',
	role: '',
	tabIndex: 5,
};

Member.propTypes = {
	name: PropTypes.string,
	pictureUrl: PropTypes.string,
	role: PropTypes.string,
	tabIndex: PropTypes.number,
};

export default Member;
