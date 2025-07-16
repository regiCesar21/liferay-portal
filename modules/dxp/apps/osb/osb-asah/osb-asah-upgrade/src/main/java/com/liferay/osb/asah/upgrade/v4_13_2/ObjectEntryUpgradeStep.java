/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.upgrade.v4_13_2;

import com.liferay.osb.asah.common.bigquery.BigQuerySchemaManager;
import com.liferay.osb.asah.common.util.ProjectIdThreadLocal;
import com.liferay.osb.asah.upgrade.UpgradeStep;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Rachael Koestartyo
 */
@Component
public class ObjectEntryUpgradeStep implements UpgradeStep {

	@Override
	public void upgrade(String version) throws Exception {
		_bigQuerySchemaManager.createFunction(
			"objectEntryHourly", ProjectIdThreadLocal.getProjectId());

		_bigQuerySchemaManager.createOrReplaceView(
			ProjectIdThreadLocal.getProjectId(), "objectentryhourly");
		_bigQuerySchemaManager.createTable(
			ProjectIdThreadLocal.getProjectId(), "objectentrydaily");

		if (_log.isInfoEnabled()) {
			_log.info("BigQuery has successfully upgraded to schema 4.13.2");
		}
	}

	private static final Log _log = LogFactory.getLog(
		ObjectEntryUpgradeStep.class);

	@Autowired
	private BigQuerySchemaManager _bigQuerySchemaManager;

}