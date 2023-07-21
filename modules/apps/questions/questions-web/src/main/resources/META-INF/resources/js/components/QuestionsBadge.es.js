/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React from 'react';

export default ({className, symbol, tooltip, value}) => {
	return (
		<ClayTooltipProvider>
			<div
				className={`c-py-2 c-px-3 rounded stretched-link-layer ${className}`}
			>
				<ClayIcon
					data-tooltip-align="top"
					symbol={symbol}
					title={tooltip}
				/>

				<span className="c-ml-2 font-weight-bold small">
					{value || 0}
				</span>
			</div>
		</ClayTooltipProvider>
	);
};
