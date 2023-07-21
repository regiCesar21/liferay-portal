/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const ACTION_UPDATE_VIEW_COMPONENT = 'ACTION_UPDATE_VIEW_COMPONENT';

export const updateViewComponent = (name, component) => {
	return {
		type: ACTION_UPDATE_VIEW_COMPONENT,
		value: {component, name},
	};
};
