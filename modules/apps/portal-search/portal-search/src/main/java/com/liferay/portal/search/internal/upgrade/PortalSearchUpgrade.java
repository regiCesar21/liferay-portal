/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.upgrade;

import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.search.internal.upgrade.v1_0_1.ReindexConfigurationUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Hugo Huijser
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class PortalSearchUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("0.0.0", "0.0.2", new DummyUpgradeStep());

		registry.register(
			"0.0.1", "0.0.2",
			new ReindexConfigurationUpgradeProcess(
				_configurationAdmin, _prefsProps));
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private PrefsProps _prefsProps;

}