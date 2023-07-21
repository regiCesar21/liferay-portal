/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getDescendantsCount from './getDescendantsCount';
import updateItemParent from './updateItemParent';

export default function moveItem(items, itemId, parentId, newIndex, direction) {
	const itemIndex = items.findIndex(
		(item) => item.siteNavigationMenuItemId === itemId
	);

	const newItems = updateItemParent(items, itemId, parentId);

	const movedItems = newItems.filter(
		(item, index) =>
			index >= itemIndex &&
			index <= itemIndex + getDescendantsCount(items, itemId)
	);

	return newItems.reduce((acc, item, index) => {
		if (index === newIndex) {
			return direction === 'up'
				? [...acc, ...movedItems, item]
				: [...acc, item, ...movedItems];
		}
		if (
			movedItems.find(
				(movedItem) =>
					movedItem.siteNavigationMenuItemId ===
					item.siteNavigationMenuItemId
			)
		) {
			return acc;
		}
		else {
			return [...acc, item];
		}
	}, []);
}
