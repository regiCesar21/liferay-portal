/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.internal.upgrade;

import com.liferay.commerce.pricing.internal.upgrade.v1_1_0.CommercePricingClassUpgradeProcess;
import com.liferay.commerce.pricing.internal.upgrade.v2_0_1.CommercePriceModifierUpgradeProcess;
import com.liferay.commerce.pricing.internal.upgrade.v2_1_0.CommercePricingConfigurationUpgradeProcess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false, immediate = true, service = UpgradeStepRegistrator.class
)
public class CommercePricingUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		if (_log.isInfoEnabled()) {
			_log.info("Commerce pricing upgrade step registrator started");
		}

		registry.register(
			"1.0.0", "1.1.0", new CommercePricingClassUpgradeProcess());

		registry.register(
			"1.1.0", "2.0.0",
			new com.liferay.commerce.pricing.internal.upgrade.v2_0_0.
				CommercePricingClassUpgradeProcess(
					_resourceActionLocalService, _resourceLocalService));

		registry.register(
			"2.0.0", "2.0.1", new CommercePriceModifierUpgradeProcess());

		registry.register("2.0.1", "2.0.2", new DummyUpgradeProcess());

		registry.register(
			"2.0.2", "2.1.0",
			new CommercePricingConfigurationUpgradeProcess(
				_configurationProvider));

		if (_log.isInfoEnabled()) {
			_log.info("Commerce pricing upgrade step registrator finished");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePricingUpgradeStepRegistrator.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourceLocalService _resourceLocalService;

}