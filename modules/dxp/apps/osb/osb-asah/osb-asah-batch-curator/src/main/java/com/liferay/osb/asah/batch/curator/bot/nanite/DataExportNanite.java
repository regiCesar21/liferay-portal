/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.batch.curator.bot.nanite;

import com.fasterxml.jackson.core.JsonFactory;

import com.liferay.osb.asah.common.data.exporter.BigQueryDataExporter;
import com.liferay.osb.asah.common.data.exporter.DataExporter;
import com.liferay.osb.asah.common.data.exporter.PostgreSQLDataExporter;
import com.liferay.osb.asah.common.dog.DataExportTaskDog;
import com.liferay.osb.asah.common.entity.DataExportTask;
import com.liferay.osb.asah.common.repository.executor.BigQueryQueryExecutor;
import com.liferay.osb.asah.common.storage.GoogleStorage;
import com.liferay.osb.asah.common.util.ProjectIdThreadLocal;

import java.io.File;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Marcellus Tavares
 */
@Component
public class DataExportNanite extends BaseNanite {

	@Autowired
	public DataExportNanite(
		BigQueryQueryExecutor bigQueryQueryExecutor,
		DataExportTaskDog dataExportTaskDog, DSLContext dslContext,
		GoogleStorage googleStorage) {

		_bigQueryQueryExecutor = bigQueryQueryExecutor;
		_dataExportTaskDog = dataExportTaskDog;
		_dslContext = dslContext;
		_googleStorage = googleStorage;
	}

	@Override
	public boolean isLogRunEnabled() {
		return true;
	}

	@Override
	public void run(JSONObject contextJSONObject) throws Exception {
		List<DataExportTask> dataExportTasks =
			_dataExportTaskDog.getDataExportTasks(
				DataExportTask.Status.PENDING);

		dataExportTasks.forEach(this::_runDataExportTask);
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	private void _runBigQueryDataExportTask(DataExportTask dataExportTask)
		throws Exception {

		DataExporter dataExporter = null;

		if (dataExportTask.getType() == DataExportTask.Type.EVENT) {
			dataExporter = new BigQueryDataExporter(
				_bigQueryQueryExecutor, Collections.emptyList(), dataExportTask,
				"eventDate", _dslContext, Collections.emptyList(), "BQEvent");
		}
		else if (dataExportTask.getType() == DataExportTask.Type.IDENTITY) {
			Condition condition = DSL.field(
				"individualId"
			).notIn(
				_dslContext.select(
					DSL.field("TO_HEX(SHA256(emailAddress))")
				).from(
					"Suppression"
				)
			);

			dataExporter = new BigQueryDataExporter(
				_bigQueryQueryExecutor, Collections.singletonList(condition),
				dataExportTask, "createDate", _dslContext,
				Collections.emptyList(), "BQIdentity");
		}
		else if (dataExportTask.getType() == DataExportTask.Type.INDIVIDUAL) {
			Condition condition = DSL.or(
				DSL.field(
					"suppressed"
				).isNull(),
				DSL.field(
					"suppressed", Boolean.class
				).eq(
					false
				));

			dataExporter = new BigQueryDataExporter(
				_bigQueryQueryExecutor, Collections.singletonList(condition),
				dataExportTask, "createDate", _dslContext,
				Collections.emptyList(), "BQIndividual");
		}
		else if (dataExportTask.getType() == DataExportTask.Type.MEMBERSHIP) {
			dataExporter = new BigQueryDataExporter(
				_bigQueryQueryExecutor, Collections.emptyList(), dataExportTask,
				"createDate", _dslContext, Collections.emptyList(),
				"BQMembership");
		}
		else if (dataExportTask.getType() == DataExportTask.Type.PAGE) {
			dataExporter = new BigQueryDataExporter(
				_bigQueryQueryExecutor, Collections.emptyList(), dataExportTask,
				"eventDate", _dslContext, Collections.emptyList(), "PageDaily");
		}
		else {
			throw new IllegalArgumentException(
				"Invalid data export task type: " + dataExportTask.getType());
		}

		File tmpFile = dataExporter.export();

		String bucketName = StringUtils.replace(
			_exportBucketTemplate, "{googleProjectId}", _gcloudProjectId);

		_googleStorage.archiveSync(
			bucketName, null, tmpFile, tmpFile.getName(),
			ProjectIdThreadLocal.getProjectId());
	}

	private void _runDataExportTask(DataExportTask dataExportTask) {
		_dataExportTaskDog.updateDataExportTask(
			dataExportTask.getId(), DataExportTask.Status.RUNNING);

		try {
			if (dataExportTask.getType() == DataExportTask.Type.SEGMENT) {
				_runSegmentDataExportTask(dataExportTask);
			}
			else {
				_runBigQueryDataExportTask(dataExportTask);
			}

			_dataExportTaskDog.updateDataExportTask(
				dataExportTask.getId(), DataExportTask.Status.COMPLETED);
		}
		catch (Exception exception) {
			_log.error(
				"Unable to run data export on task " + dataExportTask.getId(),
				exception);

			_dataExportTaskDog.updateDataExportTask(
				dataExportTask.getId(), DataExportTask.Status.ERROR);
		}
	}

	private void _runSegmentDataExportTask(DataExportTask dataExportTask)
		throws Exception {

		DataExporter dataExporter = new PostgreSQLDataExporter(
			dataExportTask, "createDate", _dslContext, _jsonFactory, "segment");

		File tmpFile = dataExporter.export();

		String bucketName = StringUtils.replace(
			_exportBucketTemplate, "{googleProjectId}", _gcloudProjectId);

		_googleStorage.archiveSync(
			bucketName, null, tmpFile, tmpFile.getName(),
			ProjectIdThreadLocal.getProjectId());
	}

	private static final Log _log = LogFactory.getLog(DataExportNanite.class);

	private final BigQueryQueryExecutor _bigQueryQueryExecutor;
	private final DataExportTaskDog _dataExportTaskDog;
	private final DSLContext _dslContext;

	@Value("${osb.asah.export.google.bucket:{googleProjectId}-export}")
	private String _exportBucketTemplate;

	@Value("${osb.asah.gcloud.project.id:liferaycloud-customer-ac}")
	private String _gcloudProjectId;

	private final GoogleStorage _googleStorage;
	private final JsonFactory _jsonFactory = new JsonFactory();

}