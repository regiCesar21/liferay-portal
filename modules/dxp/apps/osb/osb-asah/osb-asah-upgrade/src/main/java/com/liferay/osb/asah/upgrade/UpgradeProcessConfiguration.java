/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.upgrade;

import com.liferay.osb.asah.upgrade.v4_11_1.DataSourcesUpgradeStep;
import com.liferay.osb.asah.upgrade.v4_12_0.PostgreSQLUpgradeStep;

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
			"4.0.41", "4.0.42", _dataSourcesUpgradeStep);
		upgradeProcess.addUpgradeSteps(
			"4.0.42", "4.0.43", _postgreSQLUpgradeStep);

		return upgradeProcess;
	}

	@Autowired
	private DataSourcesUpgradeStep _dataSourcesUpgradeStep;

	@Autowired
	private PostgreSQLUpgradeStep _postgreSQLUpgradeStep;

}