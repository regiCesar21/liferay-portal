/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPopover from '@clayui/popover';
import {Align} from 'metal-position';
import Proptypes from 'prop-types';
import React, {useLayoutEffect, useRef} from 'react';
import ReactDOM from 'react-dom';

/**
 * Tailored implementation of a ClayPopover for Experiences
 *
 * It is triggered on hover, thus it does not need to re-calculate on window resize,
 * scroll or any other event
 */
const Popover = (props) => {
	return ReactDOM.createPortal(
		<PopoverComponent {...props} />,
		document.body
	);
};

const PopoverComponent = ({
	anchor,
	align = Align.Top,
	children,
	position = 'top',
	...rest
}) => {
	const popRef = useRef(null);

	useLayoutEffect(() => {
		Align.align(popRef.current, anchor, align, false);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<ClayPopover alignPosition={position} ref={popRef} {...rest}>
			{children}
		</ClayPopover>
	);
};

Popover.proptypes = {
	anchor: Proptypes.instanceOf(Element),
};

export default Popover;
