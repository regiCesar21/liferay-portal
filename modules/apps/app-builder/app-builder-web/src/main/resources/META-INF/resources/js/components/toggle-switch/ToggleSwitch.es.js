/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useState} from 'react';

export default ({checked = false, onChange}) => {
	const [isChecked, setChecked] = useState(checked);

	return (
		<ClayTooltipProvider>
			<label className="toggle-switch">
				<input
					checked={isChecked}
					className="toggle-switch-check"
					data-tooltip-align="top"
					data-tooltip-delay="0"
					onChange={() => {
						const newChecked = !isChecked;
						setChecked(newChecked);
						onChange(newChecked);
					}}
					title={
						isChecked
							? Liferay.Language.get('turn-off')
							: Liferay.Language.get('turn-on')
					}
					type="checkbox"
				/>

				<span aria-hidden="true" className="toggle-switch-bar">
					<span className="toggle-switch-handle"></span>
				</span>
			</label>
		</ClayTooltipProvider>
	);
};
