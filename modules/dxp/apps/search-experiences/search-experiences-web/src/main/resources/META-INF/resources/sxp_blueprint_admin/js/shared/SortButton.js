/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import React from 'react';

/**
 * A button to change table sorting and direction. Created for the blueprints
 * and elements view pages to display next to the table headers.
 */
const SortButton = ({active, direction, onClick}) => {
	return (
		<ClayButton
			className="sort-button"
			displayType="unstyled"
			onClick={onClick}
		>
			<ClayIcon
				className={getCN({
					active: active && direction === 'asc',
				})}
				symbol="order-arrow-up"
			/>

			<ClayIcon
				className={getCN({
					active: active && direction === 'desc',
				})}
				symbol="order-arrow-down"
			/>
		</ClayButton>
	);
};

export default SortButton;
