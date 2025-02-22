/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.dataflow.replication;

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.beam.sdk.io.jdbc.JdbcIO;

/**
 * @author Marcellus Tavares
 */
public class JdbcIOUtil {

	public static JdbcIO.DataSourceConfiguration createDataSourceConfiguration(
		PostgreSQLReplicationPipelineOptions
			postgreSQLReplicationPipelineOptions) {

		return JdbcIO.DataSourceConfiguration.create(
			"org.postgresql.Driver",
			String.format(
				"jdbc:postgresql:///%s",
				postgreSQLReplicationPipelineOptions.getDatabaseName())
		).withConnectionProperties(
			_createDataSourceConnectionPropertiesString(
				postgreSQLReplicationPipelineOptions.
					getCloudSQLConnectionName(),
				postgreSQLReplicationPipelineOptions.getDatabaseUser())
		).withMaxConnections(
			postgreSQLReplicationPipelineOptions.getDatasourceMaxConnections()
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

}