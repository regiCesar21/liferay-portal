/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ADD_MAPPED_INFO_ITEM} from '../actions/types';

export const INITIAL_STATE = [];

export default function mappedInfoItemsReducer(
	mappedInfoItems = INITIAL_STATE,
	action
) {
	switch (action.type) {
		case ADD_MAPPED_INFO_ITEM:
			return action.infoItems;

		default:
			return mappedInfoItems;
	}
}
