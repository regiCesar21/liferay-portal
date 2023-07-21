/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import className from 'classnames';
import PropTypes from 'prop-types';
import React, {useRef, useState} from 'react';

import Popover from './Popover';

export default function Hint({align, message, position, secondary, title}) {
	const iconRef = useRef();
	const [showTooltip, setShowTooltip] = useState(false);

	const handleMouseEnter = () => {
		setShowTooltip(true);
	};
	const handleMouseLeave = () => {
		setShowTooltip(false);
	};

	const hintClasses = className('p-1', 'small', {
		'text-secondary': secondary,
	});

	return (
		<>
			<span
				className={hintClasses}
				onMouseEnter={handleMouseEnter}
				onMouseLeave={handleMouseLeave}
				ref={iconRef}
			>
				<ClayIcon
					className="mr-1"
					small="true"
					symbol="question-circle"
				/>
			</span>

			{showTooltip && (
				<Popover
					align={align}
					anchor={iconRef.current}
					header={title}
					position={position}
				>
					{message}
				</Popover>
			)}
		</>
	);
}

Hint.proptypes = {
	message: PropTypes.string.isRequired,
	title: PropTypes.string.isRequired,
};
