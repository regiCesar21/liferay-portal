/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.upgrade;

import com.liferay.osb.asah.upgrade.v4_6_0.BQIdentityRawUpgradeStep;
import com.liferay.osb.asah.upgrade.v4_8_0.HourlyAssetMetricUpgradeStep;
import com.liferay.osb.asah.upgrade.v4_9_0.PostgreSQLUpgradeStep;

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
			"4.0.26", "4.0.27", _bqIdentityRawUpgradeStep);
		upgradeProcess.addUpgradeSteps(
			"4.0.27", "4.0.28",
			version -> {
			});
		upgradeProcess.addUpgradeSteps(
			"4.0.28", "4.0.29",
			version -> {
			});
		upgradeProcess.addUpgradeSteps(
			"4.0.29", "4.0.30", _hourlyAssetMetricUpgradeStep);

		return upgradeProcess;
	}

	@Autowired
	private BQIdentityRawUpgradeStep _bqIdentityRawUpgradeStep;

	@Autowired
	private HourlyAssetMetricUpgradeStep _hourlyAssetMetricUpgradeStep;

	@Autowired
	private PostgreSQLUpgradeStep _postgreSQLUpgradeStep;

}