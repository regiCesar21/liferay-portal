/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.upgrade;

import com.liferay.change.tracking.internal.upgrade.v2_2_0.UpgradeCTPreferences;
import com.liferay.change.tracking.internal.upgrade.v2_3_0.UpgradeCompanyId;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class ChangeTrackingServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "1.0.1",
			new com.liferay.change.tracking.internal.upgrade.v1_0_1.
				UpgradeCTCollection());

		registry.register(
			"1.0.1", "2.0.0",
			new com.liferay.change.tracking.internal.upgrade.v2_0_0.
				UpgradeSchema());

		registry.register(
			"2.0.0", "2.1.0",
			new com.liferay.change.tracking.internal.upgrade.v2_1_0.
				UpgradeSchema());

		registry.register("2.1.0", "2.2.0", new UpgradeCTPreferences());

		registry.register("2.2.0", "2.3.0", new UpgradeCompanyId());
	}

}