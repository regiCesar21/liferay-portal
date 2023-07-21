/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import classNames from 'classnames';
import React from 'react';
import {Link} from 'react-router-dom';

const Button = (props) => {
	const {
		children,
		className,
		forwardRef,
		href,
		symbol,
		tooltip,
		...restProps
	} = props;

	const Button = symbol ? ClayButtonWithIcon : ClayButton;

	let button = (
		<Button
			className={classNames(className)}
			data-title={tooltip}
			ref={forwardRef}
			symbol={symbol}
			{...restProps}
		>
			{children}
		</Button>
	);

	if (href) {
		button = <Link to={href}>{button}</Link>;
	}

	return button;
};

export default React.forwardRef(({children, ...props}, ref) => (
	<Button {...props} forwardRef={ref}>
		{children}
	</Button>
));
