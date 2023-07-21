/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.internal.upgrade;

import com.liferay.dispatch.internal.upgrade.v2_0_0.DispatchTriggerUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Beslic
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class DispatchUpgradeStepRegistrator implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "2.0.0", new DispatchTriggerUpgradeProcess());

		registry.register(
			"2.0.0", "2.1.0",
			new com.liferay.dispatch.internal.upgrade.v2_1_0.
				DispatchTriggerUpgradeProcess());

		registry.register(
			"2.1.0", "3.0.0",
			new com.liferay.dispatch.internal.upgrade.v3_0_0.
				DispatchTriggerUpgradeProcess());

		registry.register(
			"3.0.0", "3.1.0",
			new com.liferay.dispatch.internal.upgrade.v3_1_0.
				DispatchTriggerUpgradeProcess());

		registry.register(
			"3.1.0", "3.1.1",
			new com.liferay.dispatch.internal.upgrade.v3_1_1.
				DispatchTriggerModelResourcePermissionUpgradeProcess());

		registry.register(
			"3.1.1", "4.0.0",
			new com.liferay.dispatch.internal.upgrade.v4_0_0.
				DispatchTriggerUpgradeProcess());

		registry.register(
			"4.0.0", "4.0.1",
			new com.liferay.dispatch.internal.upgrade.v4_0_1.
				DispatchTriggerUpgradeProcess());
	}

}