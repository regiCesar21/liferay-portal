/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import React from 'react';

const {Divider, Item} = ClayDropDown;

export default ({action: {action, name}, item, setActive}) => {
	if (name === 'divider') {
		return <Divider />;
	}

	return (
		<Item
			onClick={(event) => {
				event.preventDefault();
				setActive(false);

				if (action) {
					action(item);
				}
			}}
		>
			{typeof name === 'function' ? name(item) : name}
		</Item>
	);
};
