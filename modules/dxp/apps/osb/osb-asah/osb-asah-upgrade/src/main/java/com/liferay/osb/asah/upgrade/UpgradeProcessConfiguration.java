/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.upgrade;

import com.liferay.osb.asah.upgrade.v4_11_0.EventPropertyUpgradeStep;
import com.liferay.osb.asah.upgrade.v4_11_0.PostgreSQLUpgradeStep;
import com.liferay.osb.asah.upgrade.v4_11_1.DataSourcesUpgradeStep;

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
			"4.0.39", "4.0.40", _postgreSQLUpgradeStep);
		upgradeProcess.addUpgradeSteps(
			"4.0.40", "4.0.41", _eventPropertyUpgradeStep);
		upgradeProcess.addUpgradeSteps(
			"4.0.41", "4.0.42", _dataSourcesUpgradeStep);

		return upgradeProcess;
	}

	@Autowired
	private DataSourcesUpgradeStep _dataSourcesUpgradeStep;

	@Autowired
	private EventPropertyUpgradeStep _eventPropertyUpgradeStep;

	@Autowired
	private PostgreSQLUpgradeStep _postgreSQLUpgradeStep;

}