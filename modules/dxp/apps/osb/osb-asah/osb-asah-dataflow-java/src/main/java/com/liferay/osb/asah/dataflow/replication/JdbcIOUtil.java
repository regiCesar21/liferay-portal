/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.dataflow.replication;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;

import java.sql.PreparedStatement;

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Marcellus Tavares
 */
public class JdbcIOUtil {

	public static JdbcIO.Write<String> createJdbcIOWrite(
		String csvColumns, String tableName,
		PostgreSQLReplicationDataflowPipelineOptions
			postgreSQLReplicationDataflowPipelineOptions) {

		String[] columns = StringUtils.split(csvColumns, ",");

		return JdbcIO.<String>write(
		).withBatchSize(
			postgreSQLReplicationDataflowPipelineOptions.getBatchSize()
		).withDataSourceConfiguration(
			JdbcIO.DataSourceConfiguration.create(
				"org.postgresql.Driver",
				String.format(
					"jdbc:postgresql:///%s",
					postgreSQLReplicationDataflowPipelineOptions.
						getDatabaseName())
			).withConnectionProperties(
				_createDataSourceConnectionPropertiesString(
					postgreSQLReplicationDataflowPipelineOptions.
						getCloudSQLConnectionName(),
					postgreSQLReplicationDataflowPipelineOptions.
						getDatabaseUser())
			).withMaxConnections(
				postgreSQLReplicationDataflowPipelineOptions.
					getDatasourceMaxConnections()
			)
		).withPreparedStatementSetter(
			new DefaultPreparedStatementSetter()
		).withStatement(
			String.format(
				"insert into %s.%s(%s) values(%s)",
				postgreSQLReplicationDataflowPipelineOptions.getProjectId(),
				tableName, StringUtils.join(columns, ","),
				StringUtils.repeat("?", ",", columns.length))
		);
	}

	private static String _createDataSourceConnectionPropertiesString(
		String cloudSQLConnectionName, String databaseUser) {

		Properties properties = new Properties();

		properties.put("cloudSqlInstance", cloudSQLConnectionName);
		properties.put("enableIamAuth", "true");
		properties.put("ipTypes", "PRIVATE");
		properties.put(
			"socketFactory", "com.google.cloud.sql.postgres.SocketFactory");
		properties.put("sslmode", "disable");
		properties.put("stringtype", "unspecified");
		properties.put("user", databaseUser);

		Set<Map.Entry<Object, Object>> entries = properties.entrySet();

		Stream<Map.Entry<Object, Object>> stream = entries.stream();

		return stream.map(
			property -> property.getKey() + "=" + property.getValue()
		).collect(
			Collectors.joining(";")
		);
	}

	private static class DefaultPreparedStatementSetter
		implements JdbcIO.PreparedStatementSetter<String> {

		@Override
		public void setParameters(
				String element, PreparedStatement preparedStatement)
			throws Exception {

			CSVParser csvParser = _buildCSVParser();

			String[] columnValues = csvParser.parseLine(element);

			for (int i = 0; i < columnValues.length; i++) {
				preparedStatement.setString(i + 1, columnValues[i]);
			}
		}

		private CSVParser _buildCSVParser() {
			CSVParserBuilder csvParserBuilder = new CSVParserBuilder();

			csvParserBuilder.withSeparator(';');

			return csvParserBuilder.build();
		}

	}

}