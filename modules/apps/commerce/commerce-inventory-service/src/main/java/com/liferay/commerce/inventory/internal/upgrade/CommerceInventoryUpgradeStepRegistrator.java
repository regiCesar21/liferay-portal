/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.internal.upgrade;

import com.liferay.commerce.inventory.internal.upgrade.v1_1_0.CommerceInventoryWarehouseItemUpgradeProcess;
import com.liferay.commerce.inventory.internal.upgrade.v2_0_0.CommerceInventoryAuditUpgradeProcess;
import com.liferay.commerce.inventory.internal.upgrade.v2_1_0.MVCCUpgradeProcess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true, service = UpgradeStepRegistrator.class
)
public class CommerceInventoryUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		if (_log.isInfoEnabled()) {
			_log.info("Commerce inventory upgrade step registrator started");
		}

		registry.register(
			"1.0.0", "1.1.0",
			new CommerceInventoryWarehouseItemUpgradeProcess());

		registry.register("1.1.0", "1.2.0", new DummyUpgradeProcess());

		registry.register(
			"1.2.0", "2.0.0", new CommerceInventoryAuditUpgradeProcess());

		registry.register("2.0.0", "2.1.0", new MVCCUpgradeProcess());

		if (_log.isInfoEnabled()) {
			_log.info("Commerce inventory upgrade step registrator finished");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceInventoryUpgradeStepRegistrator.class);

}