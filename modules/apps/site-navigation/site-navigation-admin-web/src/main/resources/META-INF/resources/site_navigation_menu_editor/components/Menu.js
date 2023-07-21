/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {useItems} from '../contexts/ItemsContext';
import {MenuItem} from './MenuItem';

export const Menu = () => {
	const items = useItems();

	return (
		<div className="container p-3" role="list">
			{items.map((item) => (
				<MenuItem item={item} key={item.siteNavigationMenuItemId} />
			))}
		</div>
	);
};
