/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import formatLocaleWithUnderscores from '../language/format_locale_with_underscores';
import renameKeys from '../language/rename_keys';
import replaceTemplateVariable from './replace_template_variable';

/**
 * Function that provides the element JSON, with title, description, and elementDefinition.
 * The elementDefinition's configuration is updated to have its variables replaced with
 * values from uiConfigurationValues.
 *
 * @param {object} sxpElement SXP Element with title, description, elementDefinition
 * @param {object=} uiConfigurationValues Values that will replace the keys in uiConfiguration
 * @return {object}
 */
export default function getSXPElementJSON(sxpElement, uiConfigurationValues) {
	const {description_i18n, elementDefinition, title_i18n} = sxpElement;

	const {category, configuration, icon} = elementDefinition;

	return {
		description_i18n: renameKeys(
			description_i18n,
			formatLocaleWithUnderscores
		),
		elementDefinition: {
			category,
			configuration: uiConfigurationValues
				? replaceTemplateVariable({
						sxpElement,
						uiConfigurationValues,
				  })
				: configuration,
			icon,
		},
		title_i18n: renameKeys(title_i18n, formatLocaleWithUnderscores),
	};
}
