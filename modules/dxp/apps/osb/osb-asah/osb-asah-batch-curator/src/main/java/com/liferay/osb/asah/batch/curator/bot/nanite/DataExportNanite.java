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
import com.liferay.osb.asah.common.storage.impl.GoogleStorageArchiver;
import com.liferay.osb.asah.common.util.ProjectIdThreadLocal;

import java.io.File;
import java.io.FileOutputStream;

import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
		GoogleStorageArchiver googleStorageArchiver) {

		_bigQueryQueryExecutor = bigQueryQueryExecutor;
		_dataExportTaskDog = dataExportTaskDog;
		_dslContext = dslContext;
		_googleStorageArchiver = googleStorageArchiver;
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

		File tempFile = File.createTempFile(
			String.valueOf(dataExportTask.getId()), ".zip");

		ZipOutputStream zipOutputStream = new ZipOutputStream(
			new FileOutputStream(tempFile));

		if (dataExportTask.getType() == DataExportTask.Type.EVENT) {
			dataExporter = new BigQueryDataExporter(
				_bigQueryQueryExecutor, Collections.emptyList(), dataExportTask,
				"eventDate", _dslContext, Collections.emptyList(), "BQEvent",
				zipOutputStream);
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
				Collections.emptyList(), "BQIdentity", zipOutputStream);
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
				Collections.emptyList(), "BQIndividual", zipOutputStream);
		}
		else if (dataExportTask.getType() == DataExportTask.Type.MEMBERSHIP) {
			dataExporter = new BigQueryDataExporter(
				_bigQueryQueryExecutor, Collections.emptyList(), dataExportTask,
				"createDate", _dslContext, Collections.emptyList(),
				"BQMembership", zipOutputStream);
		}
		else if (dataExportTask.getType() == DataExportTask.Type.PAGE) {
			dataExporter = new BigQueryDataExporter(
				_bigQueryQueryExecutor, Collections.emptyList(), dataExportTask,
				"eventDate", _dslContext, Collections.emptyList(), "PageDaily",
				zipOutputStream);
		}
		else {
			throw new IllegalArgumentException(
				"Invalid data export task type: " + dataExportTask.getType());
		}

		dataExporter.export();

		zipOutputStream.close();

		// Archive

		String bucketName = StringUtils.replace(
			_exportBucketTemplate, "{googleProjectId}", _gcloudProjectId);

		String fileName = dataExportTask.getId() + ".zip";

		_googleStorageArchiver.archiveSync(
			bucketName, null, tempFile, fileName,
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

		File tempFile = File.createTempFile(
			String.valueOf(dataExportTask.getId()), ".zip");

		ZipOutputStream zipOutputStream = new ZipOutputStream(
			new FileOutputStream(tempFile));

		File file = new File("data.json");

		zipOutputStream.putNextEntry(new ZipEntry(file.getName()));

		DataExporter dataExporter = new PostgreSQLDataExporter(
			dataExportTask, "createDate", _dslContext, _jsonFactory,
			zipOutputStream, "segment");

		dataExporter.export();

		zipOutputStream.close();

		// Archive

		String bucketName = StringUtils.replace(
			_exportBucketTemplate, "{googleProjectId}", _gcloudProjectId);

		String fileName = dataExportTask.getId() + ".zip";

		_googleStorageArchiver.archiveSync(
			bucketName, null, tempFile, fileName,
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

	private final GoogleStorageArchiver _googleStorageArchiver;
	private final JsonFactory _jsonFactory = new JsonFactory();

}