/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LAYOUT_DATA_ITEM_TYPE_LABELS} from '../config/constants/layoutDataItemTypeLabels';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';

export default function getLayoutDataItemLabel(item, fragmentEntryLinks) {
	if (
		item.type === LAYOUT_DATA_ITEM_TYPES.fragment &&
		item.config &&
		item.config.fragmentEntryLinkId &&
		fragmentEntryLinks[item.config.fragmentEntryLinkId]
	) {
		return fragmentEntryLinks[item.config.fragmentEntryLinkId].name;
	}

	const itemTypeKey = Object.keys(LAYOUT_DATA_ITEM_TYPES).find(
		(key) => LAYOUT_DATA_ITEM_TYPES[key] === item.type
	);

	if (itemTypeKey in LAYOUT_DATA_ITEM_TYPE_LABELS) {
		return LAYOUT_DATA_ITEM_TYPE_LABELS[itemTypeKey];
	}

	return item.type;
}
