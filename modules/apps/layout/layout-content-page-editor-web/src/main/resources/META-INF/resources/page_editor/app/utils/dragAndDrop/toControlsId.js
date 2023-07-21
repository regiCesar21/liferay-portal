/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getToControlsId} from '../../components/layout-data-items/Collection';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';

/**
 * Translates the given item ID into a collectionId-itemId if the item is
 * inside a collection. Otherwise, returns the plain itemId.
 * @param {{current: object}} layoutDataRef
 * @param {object} item
 * @return {string}
 */
export default function toControlsId(layoutDataRef, item) {
	const baseItem = item;

	const computeControlsId = (layoutDataRef, item) => {
		const parent = layoutDataRef.current.items[item.parentId];

		if (
			item.type === LAYOUT_DATA_ITEM_TYPES.collectionItem &&
			baseItem.collectionItemIndex &&
			parent
		) {
			return getToControlsId(
				parent.itemId,
				baseItem.collectionItemIndex
			)(baseItem.itemId);
		}
		else if (parent) {
			return computeControlsId(layoutDataRef, parent);
		}

		return baseItem.itemId;
	};

	return computeControlsId(layoutDataRef, item);
}
