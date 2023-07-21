/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.configuration;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Rubén Pulido
 */
@Component(
	configurationPid = "com.liferay.layout.page.template.admin.web.internal.configuration.FFDisplayPageAdminWebConfiguration",
	immediate = true, service = FFDisplayPageAdminWebConfigurationUtil.class
)
public class FFDisplayPageAdminWebConfigurationUtil {

	public static boolean viewUsagesEnabled() {
		return _ffDisplayPageAdminWebConfiguration.viewUsagesEnabled();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ffDisplayPageAdminWebConfiguration =
			ConfigurableUtil.createConfigurable(
				FFDisplayPageAdminWebConfiguration.class, properties);
	}

	private static volatile FFDisplayPageAdminWebConfiguration
		_ffDisplayPageAdminWebConfiguration;

}