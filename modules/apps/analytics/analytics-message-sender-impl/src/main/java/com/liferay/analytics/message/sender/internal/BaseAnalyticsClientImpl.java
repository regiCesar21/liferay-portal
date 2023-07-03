/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.analytics.message.sender.internal;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationRegistry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.UnicodeProperties;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
public abstract class BaseAnalyticsClientImpl {

	protected CloseableHttpClient getCloseableHttpClient() {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		httpClientBuilder.useSystemProperties();

		return httpClientBuilder.build();
	}

	protected boolean isEnabled(long companyId) {
		if (!analyticsConfigurationRegistry.isActive()) {
			if (_log.isDebugEnabled()) {
				_log.debug("Analytics configuration tracker not active");
			}

			return false;
		}

		AnalyticsConfiguration analyticsConfiguration =
			analyticsConfigurationRegistry.getAnalyticsConfiguration(companyId);

		if (analyticsConfiguration.liferayAnalyticsEndpointURL() == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Analytics endpoint URL null");
			}

			return false;
		}

		return true;
	}

	protected void processInvalidTokenMessage(
		long companyId, boolean disconnected, String message) {

		if (message.equals("INVALID_TOKEN") || disconnected) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Disconnecting data source for company ", companyId,
						" because of an invalid token"));
			}

			_disconnectDataSource(companyId);
		}
	}

	@Reference
	protected AnalyticsConfigurationRegistry analyticsConfigurationRegistry;

	@Reference
	protected CompanyLocalService companyLocalService;

	@Reference
	protected ConfigurationProvider configurationProvider;

	private void _disconnectDataSource(long companyId) {
		UnicodeProperties unicodeProperties = new UnicodeProperties(true);

		unicodeProperties.setProperty("liferayAnalyticsConnectionType", "");
		unicodeProperties.setProperty("liferayAnalyticsDataSourceId", "");
		unicodeProperties.setProperty("liferayAnalyticsEndpointURL", "");
		unicodeProperties.setProperty(
			"liferayAnalyticsFaroBackendSecuritySignature", "");
		unicodeProperties.setProperty("liferayAnalyticsFaroBackendURL", "");
		unicodeProperties.setProperty("liferayAnalyticsGroupIds", "");
		unicodeProperties.setProperty("liferayAnalyticsProjectId", "");
		unicodeProperties.setProperty("liferayAnalyticsURL", "");

		try {
			companyLocalService.updatePreferences(companyId, unicodeProperties);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to remove analytics preferences for company " +
						companyId,
					exception);
			}
		}

		try {
			configurationProvider.deleteCompanyConfiguration(
				AnalyticsConfiguration.class, companyId);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to remove analytics configuration for company " +
						companyId,
					exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseAnalyticsClientImpl.class);

}