/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ADD_FRAGMENT_ENTRY_LINKS,
	ADD_ITEM,
	DELETE_ITEM,
	DUPLICATE_ITEM,
	MOVE_ITEM,
	UPDATE_COL_SIZE,
	UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION,
	UPDATE_ITEM_CONFIG,
	UPDATE_LAYOUT_DATA,
	UPDATE_ROW_COLUMNS,
} from '../actions/types';

export const INITIAL_STATE = {
	items: {},
};

export default function layoutDataReducer(layoutData = INITIAL_STATE, action) {
	switch (action.type) {
		case UPDATE_COL_SIZE:
		case UPDATE_LAYOUT_DATA:
		case ADD_FRAGMENT_ENTRY_LINKS:
		case ADD_ITEM:
		case DELETE_ITEM:
		case DUPLICATE_ITEM:
		case MOVE_ITEM:
		case UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION:
		case UPDATE_ITEM_CONFIG:
		case UPDATE_ROW_COLUMNS:
			return action.layoutData;

		default:
			return layoutData;
	}
}
