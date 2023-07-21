/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.upgrade;

import com.liferay.account.internal.upgrade.v1_1_0.UpgradeSchema;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class AccountServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "1.0.1",
			new com.liferay.account.internal.upgrade.v1_0_1.UpgradeRole());

		registry.register(
			"1.0.1", "1.0.2",
			new com.liferay.account.internal.upgrade.v1_0_2.UpgradeRole());

		registry.register(
			"1.0.2", "1.0.3",
			new com.liferay.account.internal.upgrade.v1_0_3.UpgradeRole());

		registry.register(
			"1.0.3", "1.1.0",
			new com.liferay.account.internal.upgrade.v1_1_0.
				UpgradeAccountEntry(),
			new UpgradeSchema());

		registry.register(
			"1.1.0", "1.1.1",
			new com.liferay.account.internal.upgrade.v1_1_1.
				UpgradeAccountEntry());

		registry.register(
			"1.1.1", "1.2.0",
			new com.liferay.account.internal.upgrade.v1_2_0.UpgradeSchema());
	}

}