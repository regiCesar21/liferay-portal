/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import DropDownWithSearch from 'app-builder-web/js/pages/apps/DropDownWithSearch.es';
import React from 'react';

export default function SelectDropdown({
	ariaLabelId,
	emptyResultMessage,
	items = [],
	label,
	onSelect,
	selectedValue,
	...otherProps
}) {
	const itemName = selectedValue || label;

	return (
		<>
			<DropDownWithSearch
				isEmpty={items.length === 0}
				label={label}
				trigger={
					<ClayButton
						aria-labelledby={ariaLabelId}
						className="clearfix w-100"
						displayType="secondary"
					>
						<span
							className="float-left text-left text-truncate w90"
							title={itemName}
						>
							{itemName}
						</span>

						<ClayIcon
							className="dropdown-button-asset float-right"
							symbol="caret-bottom"
						/>
					</ClayButton>
				}
				{...otherProps}
			>
				<DropDownWithSearch.Items
					emptyResultMessage={emptyResultMessage}
					items={items}
					onSelect={onSelect}
				/>
			</DropDownWithSearch>
		</>
	);
}
