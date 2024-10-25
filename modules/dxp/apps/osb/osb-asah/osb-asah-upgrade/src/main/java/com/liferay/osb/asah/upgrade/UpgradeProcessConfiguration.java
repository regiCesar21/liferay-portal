/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.upgrade;

import com.liferay.osb.asah.upgrade.v4_10_0.AssetHourlyViewsUpgradeStep;
import com.liferay.osb.asah.upgrade.v4_10_0.AssetMetricUpgradeStep;
import com.liferay.osb.asah.upgrade.v4_10_0.EventUpgradeStep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Marcellus Tavares
 */
@Configuration
public class UpgradeProcessConfiguration {

	@Bean
	public UpgradeProcess upgradeProcess() {
		UpgradeProcess upgradeProcess = new UpgradeProcess();

		upgradeProcess.addUpgradeSteps(
			"4.0.36", "4.0.37", _assetHourlyViewsUpgradeStep);
		upgradeProcess.addUpgradeSteps(
			"4.0.37", "4.0.38", _assetMetricUpgradeStep);
		upgradeProcess.addUpgradeSteps("4.0.38", "4.0.39", _eventUpgradeStep);

		return upgradeProcess;
	}

	@Autowired
	private AssetHourlyViewsUpgradeStep _assetHourlyViewsUpgradeStep;

	@Autowired
	private AssetMetricUpgradeStep _assetMetricUpgradeStep;

	@Autowired
	private EventUpgradeStep _eventUpgradeStep;

}