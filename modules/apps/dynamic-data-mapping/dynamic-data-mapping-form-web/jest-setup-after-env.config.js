/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

Liferay.DDM = {
	FormSettings: {
		restrictedFormURL: 'http://localhost:8080/group/forms/shared/-/form/',
		sharedFormURL: 'http://localhost:8080/web/forms/shared/-/form/',
		spritemap: '/clay/icons.svg',
	},
};

window.themeDisplay = {
	getLanguageId: () => 'en_US',
};

window.Liferay = {
	...(window.Liferay || {}),
	ThemeDisplay: window.themeDisplay,
};

const REGEX_SUB = /\x$/g;

window.Liferay.Util.sub = function (string, data) {
	if (
		arguments.length > 2 ||
		(typeof data !== 'object' && typeof data !== 'function')
	) {
		data = Array.prototype.slice.call(arguments, 1);
	}

	return string.replace(REGEX_SUB, data);
};
