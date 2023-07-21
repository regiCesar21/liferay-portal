/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.ip.address.internal.settings.definition;

import com.liferay.multi.factor.authentication.ip.address.internal.configuration.MFAIPAddressConfiguration;
import com.liferay.portal.kernel.settings.definition.ConfigurationBeanDeclaration;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marta Medio
 */
@Component(
	property = "mfa.visibility.configuration.pid=com.liferay.multi.factor.authentication.ip.address.internal.configuration.MFAIPAddressConfiguration",
	service = ConfigurationBeanDeclaration.class
)
public class MFAIPAddressCompanyConfigurationBeanDeclaration
	implements ConfigurationBeanDeclaration {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return MFAIPAddressConfiguration.class;
	}

}