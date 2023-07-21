/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.configuration.DDMWebConfiguration;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;

/**
 * @author István András Dézsi
 */
public class IsRequiredDescriptionEnabledFunction
	implements DDMExpressionFunction.Function0<Boolean> {

	public static final String NAME = "isRequiredDescriptionEnabled";

	public IsRequiredDescriptionEnabledFunction(
		DDMWebConfiguration ddmWebConfiguration) {

		_ddmWebConfiguration = ddmWebConfiguration;
	}

	@Override
	public Boolean apply() {
		return _ddmWebConfiguration.
			enableSettingTheImageDescriptionAsOptional();
	}

	@Override
	public String getName() {
		return NAME;
	}

	private final DDMWebConfiguration _ddmWebConfiguration;

}