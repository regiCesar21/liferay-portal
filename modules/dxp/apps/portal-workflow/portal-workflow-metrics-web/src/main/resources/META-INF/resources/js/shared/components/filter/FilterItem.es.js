/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getClassName from 'classnames';
import React, {useEffect, useState} from 'react';

const FilterItem = ({
	active = false,
	description,
	dividerAfter,
	hideControl,
	labelPropertyName = 'name',
	multiple,
	name,
	onClick,
	preventClick,
	...otherProps
}) => {
	const [checked, setChecked] = useState(active);

	const classes = {
		control: getClassName(
			'custom-control',
			multiple ? 'custom-checkbox' : 'custom-radio'
		),
		dropdown: getClassName(
			'dropdown-item',
			checked && 'active',
			description && 'with-description',
			hideControl && 'control-hidden'
		),
	};

	useEffect(() => {
		setChecked(active);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [active]);

	const onClickFilter = (event) => {
		onClick(event);

		if (!preventClick) {
			setChecked(!checked);
		}
	};

	return (
		<>
			<div className={classes.dropdown} onClick={onClickFilter}>
				<div className={classes.control}>
					<input
						checked={checked}
						className="custom-control-input"
						type={multiple ? 'checkbox' : 'radio'}
					/>

					<span className="custom-control-label">
						<span className="custom-control-label-text">
							{otherProps[labelPropertyName] || name}
						</span>

						{description && (
							<span className="custom-control-label-text dropdown-item-description">
								{description}
							</span>
						)}
					</span>
				</div>
			</div>

			{dividerAfter && <li className="dropdown-divider" />}
		</>
	);
};

export {FilterItem};
