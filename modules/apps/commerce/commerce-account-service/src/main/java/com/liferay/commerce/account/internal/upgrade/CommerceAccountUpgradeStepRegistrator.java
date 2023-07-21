/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.account.internal.upgrade;

import com.liferay.commerce.account.internal.upgrade.v1_1_0.CommerceAccountUpgradeProcess;
import com.liferay.commerce.account.internal.upgrade.v1_2_0.CommerceAccountGroupCommerceAccountRelUpgradeProcess;
import com.liferay.commerce.account.internal.upgrade.v1_2_0.CommerceAccountGroupRelUpgradeProcess;
import com.liferay.commerce.account.internal.upgrade.v1_2_0.CommerceAccountGroupUpgradeProcess;
import com.liferay.commerce.account.internal.upgrade.v1_3_0.CommerceAccountNameUpgradeProcess;
import com.liferay.commerce.account.internal.upgrade.v1_4_0.CommerceAccountDefaultAddressesUpgradeProcess;
import com.liferay.commerce.account.internal.upgrade.v2_0_0.CommerceAccountGroupSystemUpgradeProcess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true, service = UpgradeStepRegistrator.class
)
public class CommerceAccountUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		if (_log.isInfoEnabled()) {
			_log.info("Commerce account upgrade step registrator started");
		}

		registry.register(
			"1.0.0", "1.1.0", new CommerceAccountUpgradeProcess());

		registry.register(
			"1.1.0", "1.2.0",
			new CommerceAccountGroupCommerceAccountRelUpgradeProcess(),
			new CommerceAccountGroupRelUpgradeProcess(),
			new CommerceAccountGroupUpgradeProcess());

		registry.register(
			"1.2.0", "1.3.0", new CommerceAccountNameUpgradeProcess());

		registry.register(
			"1.3.0", "1.4.0",
			new CommerceAccountDefaultAddressesUpgradeProcess());

		registry.register("1.4.0", "1.5.0", new DummyUpgradeProcess());

		registry.register(
			"1.5.0", "2.0.0", new CommerceAccountGroupSystemUpgradeProcess());

		if (_log.isInfoEnabled()) {
			_log.info("Commerce account upgrade step registrator finished");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceAccountUpgradeStepRegistrator.class);

}