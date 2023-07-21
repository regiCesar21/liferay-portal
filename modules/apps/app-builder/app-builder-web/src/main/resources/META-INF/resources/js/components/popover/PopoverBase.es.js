/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {PropTypes} from 'prop-types';
import React from 'react';

const Header = ({children}) => {
	return <div className="border-0 popover-header">{children}</div>;
};

const Body = ({children}) => {
	return <div className="popover-body">{children}</div>;
};

const Footer = ({children}) => {
	return <div className="popover-footer">{children}</div>;
};

const PopoverBase = ({
	children,
	className,
	forwardRef,
	placement = 'none',
	visible = false,
	...otherProps
}) => {
	return (
		<div
			{...otherProps}
			className={classNames('popover', className, {
				[`clay-popover-${placement}`]: placement,
				hide: !visible,
			})}
			ref={forwardRef}
		>
			{placement !== 'none' && <div className="arrow" />}
			{children}
		</div>
	);
};

PopoverBase.propTypes = {
	placement: PropTypes.oneOf(['bottom', 'left', 'none', 'right', 'top']),
	visible: PropTypes.bool,
};

PopoverBase.Header = Header;
PopoverBase.Body = Body;
PopoverBase.Footer = Footer;

export {PopoverBase};
export default PopoverBase;
