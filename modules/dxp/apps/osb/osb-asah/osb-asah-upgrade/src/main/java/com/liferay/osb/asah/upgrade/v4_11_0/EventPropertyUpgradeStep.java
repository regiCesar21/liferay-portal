/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.upgrade.v4_11_0;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.QueryJobConfiguration;

import com.liferay.osb.asah.common.bigquery.BigQuerySchemaManager;
import com.liferay.osb.asah.common.date.DateUtil;
import com.liferay.osb.asah.common.util.ProjectIdThreadLocal;
import com.liferay.osb.asah.upgrade.UpgradeStep;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Marcellus Tavares
 */
@Component
public class EventPropertyUpgradeStep implements UpgradeStep {

	@Override
	public void upgrade(String version) throws Exception {
		_createEventPropertySnapshotTable();

		_bigQuerySchemaManager.dropTable(
			ProjectIdThreadLocal.getProjectId(), "eventproperty");
	}

	private void _createEventPropertySnapshotTable() throws Exception {
		BigQueryOptions bigQueryOptions = _bigQuery.getOptions();

		Date date = DateUtil.newDate();

		Date expirationDate = DateUtil.addDays(date, 30);

		String snapshotTimestampString = DateUtil.toUTCString(
			date, "yyyy-MM-dd'T'HH_mm_ss_SSS'Z'");

		String query = StringUtils.replaceEach(
			_CREATE_SNAPSHOT_TABLE_TPL,
			new String[] {
				"${AC_PROJECT_ID}", "${GCP_PROJECT_ID}",
				"${SNAPSHOT_EXPIRATION_TIMESTAMP}", "${SNAPSHOT_TIMESTAMP}"
			},
			new String[] {
				ProjectIdThreadLocal.getProjectId(),
				bigQueryOptions.getProjectId(),
				DateUtil.toUTCString(expirationDate), snapshotTimestampString
			});

		QueryJobConfiguration queryJobConfiguration =
			QueryJobConfiguration.newBuilder(
				query
			).build();

		_bigQuery.query(queryJobConfiguration);

		if (_log.isInfoEnabled()) {
			_log.info(
				String.format(
					"Snapshot table %s.eventproperty-%s created successfully",
					ProjectIdThreadLocal.getProjectId(),
					snapshotTimestampString));
		}
	}

	private static final String _CREATE_SNAPSHOT_TABLE_TPL =
		"CREATE SNAPSHOT TABLE `${GCP_PROJECT_ID}.${AC_PROJECT_ID}." +
			"eventproperty-${SNAPSHOT_TIMESTAMP}` CLONE `${GCP_PROJECT_ID}." +
				"${AC_PROJECT_ID}.eventproperty` OPTIONS (" +
					"expiration_timestamp = TIMESTAMP " +
						"'${SNAPSHOT_EXPIRATION_TIMESTAMP}');";

	private static final Log _log = LogFactory.getLog(
		EventPropertyUpgradeStep.class);

	@Autowired
	private BigQuery _bigQuery;

	@Autowired
	private BigQuerySchemaManager _bigQuerySchemaManager;

}