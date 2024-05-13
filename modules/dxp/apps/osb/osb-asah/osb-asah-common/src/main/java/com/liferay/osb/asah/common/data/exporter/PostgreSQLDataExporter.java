/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.data.exporter;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.liferay.osb.asah.common.entity.DataExportTask;
import com.liferay.osb.asah.common.json.JSONUtil;

import java.io.File;
import java.io.FileOutputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectSelectStep;
import org.jooq.impl.DSL;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * @author Thiago Buarque
 */
public class PostgreSQLDataExporter implements DataExporter {

	public PostgreSQLDataExporter(
			DataExportTask dataExportTask, String dateFieldName,
			DSLContext dslContext, JsonFactory jsonFactory, String tableName)
		throws Exception {

		_dataExportTask = dataExportTask;
		_dateFieldName = dateFieldName;
		_dslContext = dslContext;

		_file = File.createTempFile(
			String.valueOf(dataExportTask.getId()), ".zip");

		ZipOutputStream zipOutputStream = new ZipOutputStream(
			new FileOutputStream(_file));

		File file = new File("data.json");

		zipOutputStream.putNextEntry(new ZipEntry(file.getName()));

		_jsonGenerator = jsonFactory.createGenerator(
			zipOutputStream, JsonEncoding.UTF8);

		_jsonGenerator.enable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);
		_jsonGenerator.setCodec(
			new ObjectMapper() {
				{
					disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

					registerModule(new JavaTimeModule());
					registerModule(new JsonOrgModule());
				}
			});
		_jsonGenerator.setPrettyPrinter(new MinimalPrettyPrinter(""));

		_tableName = tableName;
	}

	@Override
	public File export() throws Exception {
		int page = 0;

		while (true) {
			JSONObject resultPageJSONObject = _getResultPageJSONObject(
				PageRequest.of(page, _PAGE_SIZE));

			JSONArray resultsJSONArray = resultPageJSONObject.getJSONArray(
				"results");

			if (resultsJSONArray.length() == 0) {
				break;
			}

			for (int i = 0; i < resultsJSONArray.length(); i++) {
				_exportResult(resultsJSONArray.getJSONObject(i));
			}

			page++;
		}

		_jsonGenerator.close();

		return _file;
	}

	private void _exportResult(JSONObject resultJSONObject) {
		try {
			_jsonGenerator.writeObject(resultJSONObject);

			_jsonGenerator.writeRaw("\n");
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}
		}
	}

	private List<Condition> _getConditions() {
		List<Condition> conditions = new ArrayList<>();

		if (_dataExportTask.getFromDate() != null) {
			conditions.add(
				DSL.field(
					_dateFieldName
				).greaterOrEqual(
					_dataExportTask.getFromDate()
				));
		}

		if (_dataExportTask.getToDate() != null) {
			conditions.add(
				DSL.field(
					_dateFieldName
				).lessOrEqual(
					_dataExportTask.getToDate()
				));
		}

		return conditions;
	}

	private JSONObject _getResultPageJSONObject(Pageable pageable) {
		SelectSelectStep<Record> selectSelectStep = _dslContext.select();

		Result<Record> records = selectSelectStep.from(
			_tableName
		).where(
			_getConditions()
		).limit(
			pageable.getPageSize()
		).offset(
			pageable.getOffset()
		).fetch();

		return JSONUtil.put(
			"results",
			new JSONArray(
				records.map(record -> new JSONObject(record.intoMap()))));
	}

	private static final int _PAGE_SIZE = 50;

	private static final Log _log = LogFactory.getLog(
		PostgreSQLDataExporter.class);

	private final DataExportTask _dataExportTask;
	private final String _dateFieldName;
	private final DSLContext _dslContext;
	private final File _file;
	private final JsonGenerator _jsonGenerator;
	private final String _tableName;

}