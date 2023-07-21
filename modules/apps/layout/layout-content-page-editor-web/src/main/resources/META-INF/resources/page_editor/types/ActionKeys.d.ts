/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type ACTION_KEYS =
	| 'EDIT_SEGMENTS_ENTRY'
	| 'LOCKED_SEGMENTS_EXPERIMENT'
	| 'SWITCH_EDIT_MODE'
	| 'UPDATE'
	| 'UPDATE_LAYOUT_CONTENT';

export type ActionKeysMap = {
	[key in ACTION_KEYS]: boolean;
};
