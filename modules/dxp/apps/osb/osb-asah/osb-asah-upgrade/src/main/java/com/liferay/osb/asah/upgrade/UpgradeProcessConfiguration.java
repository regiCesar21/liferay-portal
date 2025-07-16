/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.upgrade;

import com.liferay.osb.asah.upgrade.v4_12_0.DataControlTaskUpgradeStep;
import com.liferay.osb.asah.upgrade.v4_12_0.DataReplicationTablePrimaryKeyUpgradeStep;
import com.liferay.osb.asah.upgrade.v4_12_0.PostgreSQLUpgradeStep;
import com.liferay.osb.asah.upgrade.v4_13_0.BigQuerySchemaUpgradeStep;
import com.liferay.osb.asah.upgrade.v4_13_0.EventDefinitionUpgradeStep;
import com.liferay.osb.asah.upgrade.v4_13_1.EventUpgradeStep;
import com.liferay.osb.asah.upgrade.v4_13_2.EventGroupIdUpgradeStep;
import com.liferay.osb.asah.upgrade.v4_13_2.ObjectEntryUpgradeStep;

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
			"4.0.43", "4.0.44", _postgreSQLUpgradeStep);
		upgradeProcess.addUpgradeSteps(
			"4.0.44", "4.0.45", _dataControlTaskUpgradeStep);
		upgradeProcess.addUpgradeSteps(
			"4.0.45", "4.0.46", _dataReplicationTablePrimaryKeyUpgradeStep);
		upgradeProcess.addUpgradeSteps(
			"4.0.46", "4.0.47", _bigQuerySchemaUpgradeStep);
		upgradeProcess.addUpgradeSteps(
			"4.0.47", "4.0.48", _eventDefinitionUpgradeStep);
		upgradeProcess.addUpgradeSteps("4.0.48", "4.0.49", _eventUpgradeStep);
		upgradeProcess.addUpgradeSteps(
			"4.0.49", "4.0.50", _eventGroupIdUpgradeStep,
			_objectEntryUpgradeStep);

		return upgradeProcess;
	}

	@Autowired
	private BigQuerySchemaUpgradeStep _bigQuerySchemaUpgradeStep;

	@Autowired
	private DataControlTaskUpgradeStep _dataControlTaskUpgradeStep;

	@Autowired
	private DataReplicationTablePrimaryKeyUpgradeStep
		_dataReplicationTablePrimaryKeyUpgradeStep;

	@Autowired
	private EventDefinitionUpgradeStep _eventDefinitionUpgradeStep;

	@Autowired
	private EventGroupIdUpgradeStep _eventGroupIdUpgradeStep;

	@Autowired
	private EventUpgradeStep _eventUpgradeStep;

	@Autowired
	private ObjectEntryUpgradeStep _objectEntryUpgradeStep;

	@Autowired
	private PostgreSQLUpgradeStep _postgreSQLUpgradeStep;

}