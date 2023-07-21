/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function updateItemParent(items, itemId, newParentId) {
	return items.map((item) =>
		item.siteNavigationMenuItemId === itemId
			? {...item, parentSiteNavigationMenuItemId: newParentId}
			: item
	);
}
