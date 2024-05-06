/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const HTML_AUTOCOMPLETE_ATTRIBUTE_TITLE = Liferay.Language.get(
	'html-autocomplete-attribute'
);

const fieldPopoverSettings = {
	htmlAutocompleteAttribute: {
		alignPosition: 'left-top',
		closeOnClickOutside: true,
		content: Liferay.Util.sub(
			Liferay.Language.get(
				'set-the-x-for-this-field-this-informs-the-browser-of-the-type-of-data-it-stores'
			),
			`<a href="https://html.spec.whatwg.org/multipage/form-control-infrastructure.html#autofill" target="_blank">${HTML_AUTOCOMPLETE_ATTRIBUTE_TITLE.toLowerCase().replace(
				'html',
				'HTML'
			)}</a>`
		),
		header: HTML_AUTOCOMPLETE_ATTRIBUTE_TITLE,
	},
};

export default fieldPopoverSettings;
