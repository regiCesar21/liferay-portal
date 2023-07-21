/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.internal.upgrade;

import com.liferay.layout.seo.internal.upgrade.v2_0_0.UpgradeSEOEntry;
import com.liferay.layout.seo.internal.upgrade.v2_1_0.UpgradeSchema;
import com.liferay.portal.kernel.upgrade.UpgradeCTModel;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Cristina González
 */
@Component(service = UpgradeStepRegistrator.class)
public class LayoutSEOServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("1.0.0", "2.0.0", new UpgradeSEOEntry());

		registry.register("2.0.0", "2.1.0", new UpgradeSchema());

		registry.register(
			"2.1.0", "2.2.0",
			new UpgradeCTModel("LayoutSEOEntry", "LayoutSEOSite"));
	}

}