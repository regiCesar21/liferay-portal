/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.upgrade.v4_12_0;

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
	public void upgrade(String version) {
		DatabasePopulatorUtils.execute(
			new ResourceDatabasePopulator(
				new ClassPathResource(
					"v4_12_0/upgrade_event_attribute_definition.sql")),
			_dataSource);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Update event attribute definition to add contentLanguageId");
		}
	}

	private static final Log _log = LogFactory.getLog(
		PostgreSQLUpgradeStep.class);

	@Autowired
	private DataSource _dataSource;

}