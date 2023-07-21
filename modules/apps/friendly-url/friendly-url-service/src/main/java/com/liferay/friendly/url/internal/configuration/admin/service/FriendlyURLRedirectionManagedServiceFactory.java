/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.internal.configuration.admin.service;

import com.liferay.friendly.url.internal.configuration.FriendlyURLRedirectionConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Constants;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Adolfo Pérez
 */
@Component(
	configurationPid = "com.liferay.friendly.url.internal.configuration.FriendlyURLRedirectionConfiguration",
	immediate = true,
	property = Constants.SERVICE_PID + "=com.liferay.friendly.url.internal.configuration.FriendlyURLRedirectionConfiguration.scoped",
	service = {
		FriendlyURLRedirectionManagedServiceFactory.class,
		ManagedServiceFactory.class
	}
)
public class FriendlyURLRedirectionManagedServiceFactory
	implements ManagedServiceFactory {

	@Override
	public void deleted(String pid) {
		_unmapPid(pid);
	}

	public FriendlyURLRedirectionConfiguration
		getCompanyFriendlyURLConfiguration(long companyId) {

		return _companyConfigurationBeans.getOrDefault(
			companyId, _systemFriendlyURLRedirectionConfiguration);
	}

	@Override
	public String getName() {
		return FriendlyURLRedirectionConfiguration.class.getName() + ".scoped";
	}

	@Override
	public void updated(String pid, Dictionary<String, ?> dictionary)
		throws ConfigurationException {

		_unmapPid(pid);

		long companyId = GetterUtil.getLong(
			dictionary.get("companyId"), CompanyConstants.SYSTEM);

		if (companyId != CompanyConstants.SYSTEM) {
			_companyConfigurationBeans.put(
				companyId,
				ConfigurableUtil.createConfigurable(
					FriendlyURLRedirectionConfiguration.class, dictionary));
			_companyIds.put(pid, companyId);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_systemFriendlyURLRedirectionConfiguration =
			ConfigurableUtil.createConfigurable(
				FriendlyURLRedirectionConfiguration.class, properties);
	}

	private void _unmapPid(String pid) {
		if (_companyIds.containsKey(pid)) {
			long companyId = _companyIds.remove(pid);

			_companyConfigurationBeans.remove(companyId);
		}
	}

	private final Map<Long, FriendlyURLRedirectionConfiguration>
		_companyConfigurationBeans = new ConcurrentHashMap<>();
	private final Map<String, Long> _companyIds = new ConcurrentHashMap<>();
	private volatile FriendlyURLRedirectionConfiguration
		_systemFriendlyURLRedirectionConfiguration;

}