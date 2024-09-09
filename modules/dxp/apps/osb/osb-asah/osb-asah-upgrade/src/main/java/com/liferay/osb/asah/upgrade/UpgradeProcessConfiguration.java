/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.upgrade;

import com.liferay.osb.asah.upgrade.v4_9_0.EventTableUpgradeStep;
import com.liferay.osb.asah.upgrade.v4_9_0.ExportFilesUpgradeStep;
import com.liferay.osb.asah.upgrade.v4_9_0.HourlyAssetMetricUpgradeStep;
import com.liferay.osb.asah.upgrade.v4_9_0.PostgreSQLUpgradeStep;
import com.liferay.osb.asah.upgrade.v4_9_0.StorageFilesUpgradeStep;

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
			"4.0.29", "4.0.30", _eventTableUpgradeStep);
		upgradeProcess.addUpgradeSteps(
			"4.0.30", "4.0.31", _exportFilesUpgradeStep);
		upgradeProcess.addUpgradeSteps(
			"4.0.31", "4.0.32", _hourlyAssetMetricUpgradeStep);
		upgradeProcess.addUpgradeSteps(
			"4.0.32", "4.0.33", _postgreSQLUpgradeStep);
		upgradeProcess.addUpgradeSteps(
			"4.0.33", "4.0.34", _storageFilesUpgradeStep);

		return upgradeProcess;
	}

	@Autowired
	private EventTableUpgradeStep _eventTableUpgradeStep;

	@Autowired
	private ExportFilesUpgradeStep _exportFilesUpgradeStep;

	@Autowired
	private HourlyAssetMetricUpgradeStep _hourlyAssetMetricUpgradeStep;

	@Autowired
	private PostgreSQLUpgradeStep _postgreSQLUpgradeStep;

	@Autowired
	private StorageFilesUpgradeStep _storageFilesUpgradeStep;

}