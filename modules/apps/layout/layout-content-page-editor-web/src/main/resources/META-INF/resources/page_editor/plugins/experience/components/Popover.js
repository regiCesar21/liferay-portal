/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPopover from '@clayui/popover';
import {Align} from 'metal-position';
import Proptypes from 'prop-types';
import React, {useRef} from 'react';
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

const PopoverComponent = ({anchor, children, ...rest}) => {
	const popRef = useRef(null);

	React.useLayoutEffect(() => {
		Align.align(popRef.current, anchor, Align.Right, false);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<ClayPopover alignPosition="right" ref={popRef} {...rest}>
			{children}
		</ClayPopover>
	);
};

Popover.proptypes = {
	anchor: Proptypes.object,
};

export default Popover;
