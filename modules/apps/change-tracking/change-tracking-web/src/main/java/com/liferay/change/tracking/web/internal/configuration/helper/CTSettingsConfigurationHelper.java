/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.configuration.helper;

import com.liferay.change.tracking.configuration.CTSettingsConfiguration;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(
	configurationPid = "com.liferay.change.tracking.configuration.CTSettingsConfiguration",
	immediate = true, service = CTSettingsConfigurationHelper.class
)
public class CTSettingsConfigurationHelper {

	public boolean isEnabled(long companyId) {
		CTPreferences ctPreferences =
			_ctPreferencesLocalService.fetchCTPreferences(companyId, 0);

		if (!(ctPreferences == null)) {
			return true;
		}

		return false;
	}

	public boolean isSandboxEnabled(long companyId) {
		CTSettingsConfiguration configuration = _getConfiguration(companyId);

		return configuration.sandboxEnabled();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_defaultCTSettingsConfiguration = ConfigurableUtil.createConfigurable(
			CTSettingsConfiguration.class, properties);
	}

	private CTSettingsConfiguration _getConfiguration(long companyId) {
		try {
			return _configurationProvider.getCompanyConfiguration(
				CTSettingsConfiguration.class, companyId);
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);
		}

		return _defaultCTSettingsConfiguration;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTSettingsConfigurationHelper.class.getName());

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

	private volatile CTSettingsConfiguration _defaultCTSettingsConfiguration;

}