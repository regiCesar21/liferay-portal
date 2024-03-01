/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.upgrade.v4_5_0;

import com.liferay.osb.asah.common.bigquery.BigQuerySchemaManager;
import com.liferay.osb.asah.common.date.dog.TimeZoneDog;
import com.liferay.osb.asah.common.util.ProjectIdThreadLocal;
import com.liferay.osb.asah.upgrade.UpgradeStep;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Marcos Martins
 */
@Component
public class BigQuerySchemaUpgrade implements UpgradeStep {

	@Override
	public void upgrade(String version) throws Exception {
		_bigQuerySchemaManager.createOrReplaceView(
			ProjectIdThreadLocal.getProjectId(), "asset",
			_timeZoneDog.getTimeZoneId());

		if (_log.isInfoEnabled()) {
			_log.info("BigQuery has successfully upgraded to schema 4.5.0");
		}
	}

	private static final Log _log = LogFactory.getLog(
		BigQuerySchemaUpgrade.class);

	@Autowired
	private BigQuerySchemaManager _bigQuerySchemaManager;

	@Autowired
	private TimeZoneDog _timeZoneDog;

}