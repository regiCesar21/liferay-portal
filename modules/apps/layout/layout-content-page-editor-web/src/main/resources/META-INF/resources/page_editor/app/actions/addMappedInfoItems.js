/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ADD_MAPPED_INFO_ITEM} from './types';

/**
 * @param {object} options
 * @param {string} options.className
 * @param {string} options.classNameId
 * @param {string} options.classPK
 * @param {string} options.title
 * @return {object}
 */
export default function addMappedInfoItems(infoItems) {
	return {
		infoItems,
		type: ADD_MAPPED_INFO_ITEM,
	};
}
