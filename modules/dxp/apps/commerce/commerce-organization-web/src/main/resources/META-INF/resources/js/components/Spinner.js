/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import PropTypes from 'prop-types';
import React, {Component} from 'react';

class Spinner extends Component {
	render() {
		const {size} = this.props;

		return (
			<div className="spinner-container">
				<div className={getCN('spinner', size)} />
			</div>
		);
	}
}

Spinner.propTypes = {
	size: PropTypes.oneOf(['small']),
};

export default Spinner;
