/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const ACTION_UPDATE_VISIBLE_FIELD_NAMES =
	'ACTION_UPDATE_VISIBLE_FIELD_NAMES';

export const updateVisibleFieldNames = (visibleFieldNames) => {
	return {
		type: ACTION_UPDATE_VISIBLE_FIELD_NAMES,
		value: visibleFieldNames,
	};
};
