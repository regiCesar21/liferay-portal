/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI = () => ({
	use: (key, callback) => callback(key),
});

window.Liferay = {
	...(window.Liferay || {}),
};

window.Liferay.destroyComponent = (componentId) => `${componentId}`;

window.Liferay.PortletKeys = {
	DOCUMENT_LIBRARY: 'DOCUMENT_LIBRARY',
	ITEM_SELECTOR: 'ITEM_SELECTOR',
};

window.Liferay.after = () => ({detach: () => {}});

window.themeDisplay = {
	...window.themeDisplay,
	getDefaultLanguageId: () => 'en_US',
	getLayoutRelativeControlPanelURL: () => 'layoutRelativeControlPanelURL',
	getLayoutRelativeURL: () => 'getLayoutRelativeURL',
	getScopeGroupId: () => 'scopeGroupId',
};

window.util = {
	...window.util,
	escape: (data) => data,
	selectEntity: () => {},
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
	Language: {
		...(window.Liferay.Language || {}),
		available: {
			ar_SA: 'Arabic (Saudi Arabia)',
			ca_ES: 'Catalan (Spain)',
			de_DE: 'German (Germany)',
			en_US: 'English (United States)',
			es_ES: 'Spanish (Spain)',
			fi_FI: 'Finnish (Finland)',
			fr_FR: 'French (France)',
			hu_HU: 'Hungarian (Hungary)',
			ja_JP: 'Japanese (Japan)',
			nl_NL: 'Dutch (Netherlands)',
			pt_BR: 'Portuguese (Brazil)',
			sv_SE: 'Swedish (Sweden)',
			zh_CN: 'Chinese (China)',
		},
		get: (key) => {
			if (languageMap[key]) {
				return languageMap[key];
			}

			return key;
		},
	},
	Util: window.util,
};
