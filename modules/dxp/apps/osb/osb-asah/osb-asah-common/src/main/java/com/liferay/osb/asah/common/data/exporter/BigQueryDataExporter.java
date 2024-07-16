/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.data.exporter;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import com.liferay.osb.asah.common.entity.DataControlTask;
import com.liferay.osb.asah.common.entity.DataExportTask;
import com.liferay.osb.asah.common.repository.executor.BigQueryQueryExecutor;
import com.liferay.osb.asah.common.util.GetterUtil;
import com.liferay.osb.asah.common.util.IOUtil;
import com.liferay.osb.asah.common.util.ProjectIdThreadLocal;

import java.io.File;
import java.io.FileOutputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SelectSelectStep;
import org.jooq.impl.DSL;

/**
 * @author Marcellus Tavares
 */
public class BigQueryDataExporter implements DataExporter {

	public BigQueryDataExporter(
		BigQueryQueryExecutor bigQueryQueryExecutor,
		DataControlTask dataControlTask, DSLContext dslContext,
		List<String> tableNames) {

		this(bigQueryQueryExecutor, dslContext, tableNames);

		_dataControlTask = dataControlTask;

		_tmpFilePrefix = String.valueOf(dataControlTask.getId());
	}

	public BigQueryDataExporter(
		BigQueryQueryExecutor bigQueryQueryExecutor, List<Condition> conditions,
		DataExportTask dataExportTask, String dateFieldName,
		DSLContext dslContext, String tableName) {

		this(
			bigQueryQueryExecutor, dslContext,
			Collections.singletonList(tableName));

		_conditions = conditions;
		_dataExportTask = dataExportTask;
		_dateFieldName = dateFieldName;

		_tmpFilePrefix = String.valueOf(dataExportTask.getId());
	}

	@Override
	public File export() throws Exception {
		File tmpFile = File.createTempFile(_tmpFilePrefix, ".zip");

		_zipOutputStream = new ZipOutputStream(new FileOutputStream(tmpFile));

		if (_dataControlTask != null) {
			_exportDataControlTask();
		}
		else if (_dataExportTask != null) {
			_exportDataExportTask();
		}

		_zipOutputStream.close();

		return tmpFile;
	}

	private BigQueryDataExporter(
		BigQueryQueryExecutor bigQueryQueryExecutor, DSLContext dslContext,
		List<String> tableNames) {

		_bigQueryQueryExecutor = bigQueryQueryExecutor;
		_dslContext = dslContext;
		_tableNames = tableNames;

		StorageOptions storageOptions = StorageOptions.getDefaultInstance();

		_storage = storageOptions.getService();
	}

	private void _createDataControlZipFile(
			String exportBucket, String exportBucketFolder)
		throws Exception {

		Page<Blob> blobs = _storage.list(
			exportBucket, Storage.BlobListOption.prefix(exportBucketFolder));

		for (Blob blob : blobs.iterateAll()) {
			try {
				String blobName = blob.getName();

				if (!blobName.endsWith(".csv")) {
					continue;
				}

				byte[] bytes = _getBlobContent(blob);

				if (IOUtil.countLines(bytes) <= 1) {
					continue;
				}

				File file = new File(blobName);

				_zipOutputStream.putNextEntry(new ZipEntry(file.getName()));

				_zipOutputStream.write(bytes, 0, bytes.length);
			}
			catch (Exception exception) {
				_log.error(
					String.format(
						"Unable to write blob %s to data control temp file",
						blob.getName()),
					exception);
			}
		}

		_zipOutputStream.closeEntry();
	}

	private void _createDataExportZipFile(
			String exportBucket, String exportBucketFolder)
		throws Exception {

		Page<Blob> blobs = _storage.list(
			exportBucket, Storage.BlobListOption.prefix(exportBucketFolder));

		File file = new File("data.json");

		_zipOutputStream.putNextEntry(new ZipEntry(file.getName()));

		for (Blob blob : blobs.iterateAll()) {
			try {
				String blobName = blob.getName();

				if (!blobName.endsWith(".json")) {
					continue;
				}

				byte[] bytes = _getBlobContent(blob);

				_zipOutputStream.write(bytes, 0, bytes.length);
			}
			catch (Exception exception) {
				_log.error(
					String.format(
						"Unable to write blob %s to data export temporary file",
						blob.getName()),
					exception);
			}
		}

		_zipOutputStream.closeEntry();
	}

	private void _exportDataControlTask() throws Exception {
		String exportBucket = StringUtils.replace(
			_DATA_EXPORTER_BUCKET_TEMPLATE, "{googleProjectId}",
			_bigQueryQueryExecutor.getGoogleProjectId());

		String exportBucketFolder =
			ProjectIdThreadLocal.getProjectId() + "/" +
				_dataControlTask.getId();

		for (String tableName : _tableNames) {
			_runBigQueryDataControlExportJob(
				exportBucket, exportBucketFolder, tableName);
		}

		_createDataControlZipFile(exportBucket, exportBucketFolder);
	}

	private void _exportDataExportTask() throws Exception {
		String exportBucket = StringUtils.replace(
			_DATA_EXPORTER_BUCKET_TEMPLATE, "{googleProjectId}",
			_bigQueryQueryExecutor.getGoogleProjectId());

		String exportBucketFolder =
			ProjectIdThreadLocal.getProjectId() + "/" + _dataExportTask.getId();

		_runDataExportBigQueryExportJob(exportBucket, exportBucketFolder);

		_createDataExportZipFile(exportBucket, exportBucketFolder);
	}

	private byte[] _getBlobContent(Blob blob) throws Exception {
		int retries = 0;

		while (true) {
			try {
				return blob.getContent();
			}
			catch (Exception exception) {
				if (retries++ < _DATA_EXPORTER_MAX_RETRY) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to read blob " + blob.getName() +
								". Retrying in " +
									_DATA_EXPORTER_RETRY_INTERVAL_MILLIS +
										"ms.");
					}

					Thread.sleep(_DATA_EXPORTER_RETRY_INTERVAL_MILLIS);
				}
				else {
					_log.error(
						"Unable to read blob " + blob.getName() + " after " +
							_DATA_EXPORTER_MAX_RETRY + " attempts");

					throw exception;
				}
			}
		}
	}

	private List<Condition> _getConditions() {
		List<Condition> conditions = new ArrayList<>();

		if (_dataExportTask != null) {
			if (_dataExportTask.getFromDate() != null) {
				conditions.add(
					DSL.field(
						_dateFieldName, Date.class
					).greaterOrEqual(
						_dataExportTask.getFromDate()
					));
			}

			if (_dataExportTask.getToDate() != null) {
				conditions.add(
					DSL.field(
						_dateFieldName, Date.class
					).lessOrEqual(
						_dataExportTask.getToDate()
					));
			}
		}

		if (!_conditions.isEmpty()) {
			conditions.addAll(_conditions);
		}

		return conditions;
	}

	private SelectSelectStep<Record> _getDataExportSelectSelectStep() {
		String tableName = _tableNames.get(0);

		if (tableName.equals("BQEvent")) {
			return _dslContext.select(_getFields());
		}

		return _dslContext.select();
	}

	private Field _getEventPropertiesField() {
		return DSL.field(
			String.join(
				"", "COALESCE((SELECT CONCAT('{',",
				"STRING_AGG(CONCAT('\"', CAST(name AS STRING), ",
				"'\":\"', CAST(value AS STRING), '\"')),'}')",
				"FROM UNNEST(properties) AS properties",
				"), '{}') AS eventProperties"));
	}

	private List<Field> _getFields() {
		return Arrays.asList(
			DSL.field("* EXCEPT (properties)"), _getEventPropertiesField());
	}

	private void _runBigQueryDataControlExportJob(
		String exportBucket, String exportBucketFolder, String tableName) {

		String emailAddress = StringUtils.lowerCase(
			_dataControlTask.getEmailAddress());
		String query = null;

		if (tableName.equalsIgnoreCase("BQEvent")) {
			query = _dslContext.select(
				_getFields()
			).from(
				"BQEvent"
			).where(
				DSL.field(
					"emailAddressHashed"
				).eq(
					DigestUtils.sha256Hex(emailAddress)
				)
			).toString();
		}
		else if (tableName.equalsIgnoreCase("BQExpandoValue")) {
			query = _dslContext.select(
				DSL.field("user.emailAddress"), DSL.field("expandoValue.*")
			).from(
				DSL.table(
					"BQExpandoValue"
				).as(
					"expandoValue"
				)
			).innerJoin(
				DSL.table(
					"BQUser"
				).as(
					"user"
				)
			).on(
				DSL.field(
					"expandoValue.classPK"
				).eq(
					DSL.field("CAST(user.dxpUserId AS string)")
				)
			).where(
				DSL.field(
					"user.emailAddress"
				).eq(
					emailAddress
				)
			).toString();
		}
		else if (tableName.equalsIgnoreCase("BQUser")) {
			query = String.join(
				"", "SELECT * EXCEPT(fields), (SELECT '{' || STRING_AGG(",
				"format('\"%s\": \"%s\"', name, value)) || '}' FROM UNNEST(",
				"fields)) AS fields FROM ", "BQUser", " WHERE emailAddress = '",
				emailAddress, "'");
		}

		_bigQueryQueryExecutor.queryExecute(
			String.format(
				_EXPORT_DATA_CSV_QUERY_TEMPLATE, exportBucket,
				exportBucketFolder, tableName, query));
	}

	private void _runDataExportBigQueryExportJob(
		String exportBucket, String exportBucketFolder) {

		SelectSelectStep<Record> selectSelectStep =
			_getDataExportSelectSelectStep();

		_bigQueryQueryExecutor.queryExecute(
			String.format(
				_EXPORT_DATA_JSON_QUERY_TEMPLATE, exportBucket,
				exportBucketFolder,
				selectSelectStep.from(
					_tableNames.get(0)
				).where(
					_getConditions()
				)));
	}

	private static final String _DATA_EXPORTER_BUCKET_TEMPLATE =
		"{googleProjectId}-data-exporter";

	private static final int _DATA_EXPORTER_MAX_RETRY = GetterUtil.getInteger(
		System.getenv("DATA_EXPORTER_MAX_RETRY"), 3);

	private static final int _DATA_EXPORTER_RETRY_INTERVAL_MILLIS =
		GetterUtil.getInteger(
			System.getenv("DATA_EXPORTER_RETRY_INTERVAL_MILLIS"), 1000);

	private static final String _EXPORT_DATA_CSV_QUERY_TEMPLATE =
		"EXPORT DATA OPTIONS(field_delimiter=',', format='CSV', header=true, " +
			"overwrite=true, uri='gs://%s/%s/%s-*.csv') AS %s";

	private static final String _EXPORT_DATA_JSON_QUERY_TEMPLATE =
		"EXPORT DATA OPTIONS(format='JSON', overwrite=true, " +
			"uri='gs://%s/%s/*.json') AS %s";

	private static final Log _log = LogFactory.getLog(
		BigQueryDataExporter.class);

	private final BigQueryQueryExecutor _bigQueryQueryExecutor;
	private List<Condition> _conditions;
	private DataControlTask _dataControlTask;
	private DataExportTask _dataExportTask;
	private String _dateFieldName;
	private final DSLContext _dslContext;
	private final Storage _storage;
	private List<String> _tableNames;
	private String _tmpFilePrefix;
	private ZipOutputStream _zipOutputStream;

}