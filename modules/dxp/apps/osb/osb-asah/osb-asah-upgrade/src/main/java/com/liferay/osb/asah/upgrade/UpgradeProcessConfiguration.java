/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.upgrade;

import com.liferay.osb.asah.upgrade.v4_5_0.PostgreSQLUpgradeStep;
import com.liferay.osb.asah.upgrade.v4_6_0.BQIdentityRawUpgradeStep;

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
			"4.0.25", "4.0.26", _postgreSQLUpgradeStep);

		upgradeProcess.addUpgradeSteps(
			"4.0.26", "4.0.27", _bqIdentityRawUpgradeStep);

		return upgradeProcess;
	}

	@Autowired
	private BQIdentityRawUpgradeStep _bqIdentityRawUpgradeStep;

	@Autowired
	private PostgreSQLUpgradeStep _postgreSQLUpgradeStep;

}