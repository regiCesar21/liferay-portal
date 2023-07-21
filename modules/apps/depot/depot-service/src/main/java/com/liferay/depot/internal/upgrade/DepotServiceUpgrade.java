/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.internal.upgrade;

import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(service = UpgradeStepRegistrator.class)
public class DepotServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "1.1.0",
			new com.liferay.depot.internal.upgrade.v1_1_0.
				UpgradeDepotEntryGroupRel());

		registry.register(
			"1.1.0", "1.2.0",
			new com.liferay.depot.internal.upgrade.v1_2_0.
				UpgradeDepotEntryGroupRel());

		registry.register(
			"1.2.0", "2.0.0",
			new com.liferay.depot.internal.upgrade.v2_0_0.
				UpgradeDepotEntryGroupRel());
	}

}