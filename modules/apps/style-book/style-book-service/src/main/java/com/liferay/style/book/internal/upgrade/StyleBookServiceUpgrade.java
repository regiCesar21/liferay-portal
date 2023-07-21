/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.internal.upgrade;

import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.UpgradeCTModel;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.style.book.internal.upgrade.v1_1_0.UpgradeStyleBookEntry;
import com.liferay.style.book.internal.upgrade.v1_2_0.UpgradeStyleBookEntryVersion;
import com.liferay.style.book.internal.upgrade.v1_2_0.util.UpgradeMVCCVersion;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	service = {StyleBookServiceUpgrade.class, UpgradeStepRegistrator.class}
)
public class StyleBookServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("0.0.1", "1.0.0", new DummyUpgradeStep());

		registry.register("1.0.0", "1.1.0", new UpgradeStyleBookEntry());

		registry.register(
			"1.1.0", "1.2.0", new UpgradeMVCCVersion(),
			new UpgradeStyleBookEntryVersion());

		registry.register(
			"1.2.0", "1.3.0", new UpgradeCTModel("StyleBookEntry"));

		registry.register(
			"1.3.0", "1.4.0", new UpgradeCTModel("StyleBookEntryVersion"));

		registry.register("1.4.0", "1.4.1", new UpgradeMVCCVersion());
	}

}