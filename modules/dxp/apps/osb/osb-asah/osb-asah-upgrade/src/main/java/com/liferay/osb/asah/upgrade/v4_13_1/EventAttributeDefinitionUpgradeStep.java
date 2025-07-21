/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.upgrade.v4_13_1;

import com.liferay.osb.asah.upgrade.UpgradeStep;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

/**
 * @author Leslie Wong
 */
@Component
public class EventAttributeDefinitionUpgradeStep implements UpgradeStep {

	@Override
	public void upgrade(String version) throws Exception {
		DatabasePopulatorUtils.execute(
			new ResourceDatabasePopulator(
				new ClassPathResource(
					"v4_13_1/event_attribute_definition_upgrade.sql")),
			_dataSource);

		if (_log.isInfoEnabled()) {
			_log.info(
				"EventAttributeDefinition table successfully upgraded to " +
					"schema 4.13.1");
		}
	}

	private static final Log _log = LogFactory.getLog(
		EventAttributeDefinitionUpgradeStep.class);

	@Autowired
	private DataSource _dataSource;

}