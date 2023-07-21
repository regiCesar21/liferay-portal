/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

export default function SidebarPanelContent({padded = true, ...props}) {
	return (
		<div
			{...props}
			className={classNames({
				[props.className]: !!props.className,
				'px-3': padded,
			})}
		/>
	);
}

SidebarPanelContent.propTypes = {
	padded: PropTypes.bool,
};
