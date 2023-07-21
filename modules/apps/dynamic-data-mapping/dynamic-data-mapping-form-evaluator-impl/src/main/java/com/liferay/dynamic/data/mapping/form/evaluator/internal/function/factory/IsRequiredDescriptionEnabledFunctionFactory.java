/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function.factory;

import com.liferay.dynamic.data.mapping.configuration.DDMWebConfiguration;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionFactory;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.function.IsRequiredDescriptionEnabledFunction;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author István András Dézsi
 */
@Component(
	configurationPid = "com.liferay.dynamic.data.mapping.configuration.DDMWebConfiguration",
	property = "name=" + IsRequiredDescriptionEnabledFunction.NAME,
	service = DDMExpressionFunctionFactory.class
)
public class IsRequiredDescriptionEnabledFunctionFactory
	implements DDMExpressionFunctionFactory {

	@Override
	public DDMExpressionFunction create() {
		return new IsRequiredDescriptionEnabledFunction(_ddmWebConfiguration);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ddmWebConfiguration = ConfigurableUtil.createConfigurable(
			DDMWebConfiguration.class, properties);
	}

	private volatile DDMWebConfiguration _ddmWebConfiguration;

}