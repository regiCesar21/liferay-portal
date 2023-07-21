/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';

const LAYOUT_DATA_ALLOWED_CHILDREN_TYPES = {
	[LAYOUT_DATA_ITEM_TYPES.root]: [
		LAYOUT_DATA_ITEM_TYPES.collection,
		LAYOUT_DATA_ITEM_TYPES.dropZone,
		LAYOUT_DATA_ITEM_TYPES.container,
		LAYOUT_DATA_ITEM_TYPES.row,
		LAYOUT_DATA_ITEM_TYPES.fragment,
	],
	[LAYOUT_DATA_ITEM_TYPES.collection]: [],
	[LAYOUT_DATA_ITEM_TYPES.collectionItem]: [
		LAYOUT_DATA_ITEM_TYPES.container,
		LAYOUT_DATA_ITEM_TYPES.row,
		LAYOUT_DATA_ITEM_TYPES.fragment,
	],
	[LAYOUT_DATA_ITEM_TYPES.dropZone]: [],
	[LAYOUT_DATA_ITEM_TYPES.container]: [
		LAYOUT_DATA_ITEM_TYPES.collection,
		LAYOUT_DATA_ITEM_TYPES.container,
		LAYOUT_DATA_ITEM_TYPES.dropZone,
		LAYOUT_DATA_ITEM_TYPES.row,
		LAYOUT_DATA_ITEM_TYPES.fragment,
	],
	[LAYOUT_DATA_ITEM_TYPES.row]: [LAYOUT_DATA_ITEM_TYPES.column],
	[LAYOUT_DATA_ITEM_TYPES.column]: [
		LAYOUT_DATA_ITEM_TYPES.collection,
		LAYOUT_DATA_ITEM_TYPES.container,
		LAYOUT_DATA_ITEM_TYPES.dropZone,
		LAYOUT_DATA_ITEM_TYPES.row,
		LAYOUT_DATA_ITEM_TYPES.fragment,
	],
	[LAYOUT_DATA_ITEM_TYPES.fragment]: [],
	[LAYOUT_DATA_ITEM_TYPES.fragmentDropZone]: [
		LAYOUT_DATA_ITEM_TYPES.collection,
		LAYOUT_DATA_ITEM_TYPES.dropZone,
		LAYOUT_DATA_ITEM_TYPES.container,
		LAYOUT_DATA_ITEM_TYPES.row,
		LAYOUT_DATA_ITEM_TYPES.fragment,
	],
};

/**
 * Checks if the given child can be nested inside given parent
 * @param {object} child
 * @param {object} parent
 * @param {{current: object}} layoutDataRef
 * @return {boolean}
 */
export default function checkAllowedChild(child, parent, layoutDataRef) {
	const parentIsInsideCollection = (function checkItemInsideCollection(item) {
		if (item.type === LAYOUT_DATA_ITEM_TYPES.collection) {
			return true;
		}
		else if (item.parentId) {
			return checkItemInsideCollection(
				layoutDataRef.current.items[item.parentId]
			);
		}
	})(parent);

	if (
		parentIsInsideCollection &&
		child.type === LAYOUT_DATA_ITEM_TYPES.collection
	) {
		return false;
	}

	return LAYOUT_DATA_ALLOWED_CHILDREN_TYPES[parent.type].includes(child.type);
}
