/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.upgrade.v4_9_0;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.Field;
import com.google.cloud.bigquery.LegacySQLTypeName;
import com.google.cloud.bigquery.Schema;
import com.google.cloud.bigquery.StandardTableDefinition;
import com.google.cloud.bigquery.Table;
import com.google.cloud.bigquery.TableDefinition;

import com.liferay.osb.asah.common.util.ProjectIdThreadLocal;
import com.liferay.osb.asah.common.util.SetUtil;
import com.liferay.osb.asah.upgrade.UpgradeStep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Marcellus Tavares
 */
@Component
public class EventTableUpgradeStep implements UpgradeStep {

	@Override
	public void upgrade(String version) throws Exception {
		_updateTableFields(
			Arrays.asList(
				Field.newBuilder(
					"properties", LegacySQLTypeName.RECORD,
					Field.newBuilder(
						"name", LegacySQLTypeName.STRING
					).setMode(
						Field.Mode.REQUIRED
					).build(),
					Field.newBuilder(
						"value", LegacySQLTypeName.STRING
					).setMode(
						Field.Mode.REQUIRED
					).build()
				).setMode(
					Field.Mode.REPEATED
				).build()),
			"event");

		if (_log.isInfoEnabled()) {
			_log.info(
				"Properties column added to the Event table successfully");
		}
	}

	private List<Field> _getTableFields(Table table) {
		TableDefinition definition = table.getDefinition();

		Schema currentSchema = definition.getSchema();

		return new ArrayList<>(currentSchema.getFields());
	}

	private void _updateTableFields(List<Field> newFields, String tableName) {
		List<Field> fields = new ArrayList<>();

		fields.addAll(newFields);

		Set<String> newFieldNames = SetUtil.map(newFields, Field::getName);

		Table table = _bigQuery.getTable(
			ProjectIdThreadLocal.getProjectId(), tableName);

		for (Field field : _getTableFields(table)) {
			if (newFieldNames.contains(field.getName())) {
				continue;
			}

			fields.add(field);
		}

		Table.Builder builder = table.toBuilder();

		builder = builder.setDefinition(
			StandardTableDefinition.of(Schema.of(fields)));

		table = builder.build();

		table.update();
	}

	private static final Log _log = LogFactory.getLog(
		EventTableUpgradeStep.class);

	@Autowired
	private BigQuery _bigQuery;

}