/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.data.integration.internal.upgrade;

import com.liferay.commerce.data.integration.internal.upgrade.v1_1_0.CommerceDataIntegrationProcessLogUpgradeProcess;
import com.liferay.commerce.data.integration.internal.upgrade.v2_0_0.CommerceDataIntegrationProcessSystemUpgradeProcess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ethan Bustad
 */
@Component(
	enabled = false, immediate = true, service = UpgradeStepRegistrator.class
)
public class CommerceDataIntegrationUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Commerce data integration upgrade step registrator started");
		}

		registry.register(
			"1.0.0", "1.1.0",
			new CommerceDataIntegrationProcessLogUpgradeProcess());

		registry.register(
			"1.1.0", "2.0.0",
			new CommerceDataIntegrationProcessSystemUpgradeProcess());

		if (_log.isInfoEnabled()) {
			_log.info(
				"Commerce data integration upgrade step registrator finished");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDataIntegrationUpgradeStepRegistrator.class);

}