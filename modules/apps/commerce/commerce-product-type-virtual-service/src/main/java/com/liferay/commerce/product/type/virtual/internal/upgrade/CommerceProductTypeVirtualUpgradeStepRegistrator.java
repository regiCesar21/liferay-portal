/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.internal.upgrade;

import com.liferay.commerce.product.type.virtual.internal.upgrade.v1_1_0.CPDefinitionVirtualSettingUpgradeProcess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true, service = UpgradeStepRegistrator.class
)
public class CommerceProductTypeVirtualUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Commerce product type virtual upgrade step registrator " +
					"STARTED");
		}

		registry.register(
			"1.0.0", "1.1.0", new CPDefinitionVirtualSettingUpgradeProcess());

		registry.register(
			"1.1.0", "1.1.1",
			new com.liferay.commerce.product.type.virtual.internal.upgrade.
				v1_1_1.CPDefinitionVirtualSettingUpgradeProcess());

		if (_log.isInfoEnabled()) {
			_log.info(
				"Commerce product type virtual upgrade step registrator " +
					"FINISHED");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceProductTypeVirtualUpgradeStepRegistrator.class);

}