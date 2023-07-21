/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.validator;

import com.liferay.layout.page.template.exception.DisplayPageTemplateValidatorException;
import com.liferay.petra.json.validator.JSONValidator;
import com.liferay.petra.json.validator.JSONValidatorException;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Rubén Pulido
 */
public class DisplayPageTemplateValidator {

	public static void validateDisplayPageTemplate(
			String displayPageTemplateJSON)
		throws DisplayPageTemplateValidatorException {

		if (Validator.isNull(displayPageTemplateJSON)) {
			return;
		}

		try {
			JSONValidator.validate(
				displayPageTemplateJSON,
				DisplayPageTemplateValidator.class.getResourceAsStream(
					"dependencies/display_page_template_json_schema.json"));
		}
		catch (JSONValidatorException jsonValidatorException) {
			throw new DisplayPageTemplateValidatorException(
				jsonValidatorException.getMessage(), jsonValidatorException);
		}
	}

}