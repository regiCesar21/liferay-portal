/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.fixed.internal.upgrade;

import com.liferay.commerce.shipping.engine.fixed.internal.upgrade.v1_1_0.CommerceShippingFixedOptionRelUpgradeProcess;
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
public class CommerceShippingEngineFixedUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Commerce shipping engine fixed upgrade step registrator " +
					"STARTED");
		}

		registry.register(
			"1.0.0", "1.1.0",
			new CommerceShippingFixedOptionRelUpgradeProcess());

		if (_log.isInfoEnabled()) {
			_log.info(
				"Commerce shipping engine fixed upgrade step registrator " +
					"finished");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceShippingEngineFixedUpgradeStepRegistrator.class);

}