/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.validator;

import com.liferay.layout.page.template.exception.MasterPageValidatorException;
import com.liferay.petra.json.validator.JSONValidator;
import com.liferay.petra.json.validator.JSONValidatorException;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Rubén Pulido
 */
public class MasterPageValidator {

	public static void validateMasterPage(String masterPageJSON)
		throws MasterPageValidatorException {

		if (Validator.isNull(masterPageJSON)) {
			return;
		}

		try {
			JSONValidator.validate(
				masterPageJSON,
				MasterPageValidator.class.getResourceAsStream(
					"dependencies/master_page_json_schema.json"));
		}
		catch (JSONValidatorException jsonValidatorException) {
			throw new MasterPageValidatorException(
				jsonValidatorException.getMessage(), jsonValidatorException);
		}
	}

}