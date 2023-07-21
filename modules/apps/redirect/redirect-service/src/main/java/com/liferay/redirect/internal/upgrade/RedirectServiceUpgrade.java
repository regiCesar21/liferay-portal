/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.internal.upgrade;

import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.redirect.internal.upgrade.v3_0_2.RedirectEntrySourceURLUpgradeProcess;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(service = UpgradeStepRegistrator.class)
public class RedirectServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "2.0.0",
			new com.liferay.redirect.internal.upgrade.v2_0_0.
				UpgradeRedirectNotFoundEntry());

		registry.register(
			"2.0.0", "2.0.1",
			new com.liferay.redirect.internal.upgrade.v2_0_1.
				UpgradeRedirectNotFoundEntry());

		registry.register(
			"2.0.1", "3.0.0",
			new com.liferay.redirect.internal.upgrade.v3_0_0.
				UpgradeRedirectNotFoundEntry());

		registry.register("3.0.0", "3.0.1", new DummyUpgradeProcess());

		registry.register(
			"3.0.1", "3.0.2", new RedirectEntrySourceURLUpgradeProcess());
	}

}