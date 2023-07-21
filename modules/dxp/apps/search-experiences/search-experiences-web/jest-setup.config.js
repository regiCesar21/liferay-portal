/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

window.themeDisplay = {
	getBCP47LanguageId: () => 'en-US',
	getDefaultLanguageId: () => 'en_US',
	getLanguageId: () => 'en_US',
	getPathContext: () => '/',
	getPortalURL: () => 'http://localhost:8080/',
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
		get: (key) => key,
	},
	ThemeDisplay: window.themeDisplay,
};
