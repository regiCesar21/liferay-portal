/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

window.AlloyEditor = {
	...window.AlloyEditor,
	Selections: [
		{
			buttons: ['linkEdit'],
			name: 'link',
		},
		{
			buttons: [
				'styles',
				'bold',
				'italic',
				'underline',
				'link',
				'twitter',
			],
			name: 'text',
		},
	],
};

window.AUI = () => ({
	...window.AUI,
	use: (...modules) => {
		const callback = modules[modules.length - 1];

		callback({
			LiferayAlloyEditor: () => ({
				render: () => ({
					destroy: () => {},
					getHTML: () => 'test',
					getNativeEditor: () => ({
						on: () => true,
						setData: () => false,
					}),
				}),
			}),
			one: () => ({
				innerHTML: () => {},
			}),
		});
	},
});

window.Liferay.PortletKeys = {
	DOCUMENT_LIBRARY: 'DOCUMENT_LIBRARY',
	ITEM_SELECTOR: 'ITEM_SELECTOR',
};

window.themeDisplay = {
	...window.themeDisplay,
	getDefaultLanguageId: () => 'en_US',
	getLayoutRelativeControlPanelURL: () => 'layoutRelativeControlPanelURL',
	getLayoutRelativeURL: () => 'getLayoutRelativeURL',
	getScopeGroupId: () => 'scopeGroupId',
	isSignedIn: () => true,
};

const sub = function (string, data) {
	if (
		arguments.length > 2 ||
		(typeof data !== 'object' && typeof data !== 'function')
	) {
		data = Array.prototype.slice.call(arguments, 1);
	}

	const REGEX_SUB = /\x$/g;

	return string.replace(REGEX_SUB, data);
};

window.util = {
	...window.util,
	escape: (data) => data,
	selectEntity: () => {},
	sub,
};

const languageMap = {
	'days-abbreviation': 'd',
	'decimal-delimiter': '.',
	'hours-abbreviation': 'h',
	'minutes-abbreviation': 'min',
	'mmm-dd': 'MMM DD',
	'mmm-dd-hh-mm': 'MMM DD, HH:mm',
	'mmm-dd-hh-mm-a': 'MMM DD, hh:mm A',
	'mmm-dd-lt': 'MMM DD, LT',
	'mmm-dd-yyyy': 'MMM DD, YYYY',
	'mmm-dd-yyyy-lt': 'MMM DD, YYYY, LT',
	'thousand-abbreviation': 'K',
};

window.Liferay = {
	...(window.Liferay || {}),
	AUI: {
		getDateFormat: () => '%m/%d/%Y',
	},
	Language: {
		get: (key) => {
			if (languageMap[key]) {
				return languageMap[key];
			}

			return key;
		},
	},
	ThemeDisplay: window.themeDisplay,
	Util: window.util,
};
