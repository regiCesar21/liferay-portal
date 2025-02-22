/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.dataflow.replication;

import java.sql.PreparedStatement;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Marcellus Tavares
 */
public class IndividualActivityReplication {

	public static void extend(
		Pipeline pipeline,
		PostgreSQLReplicationPipelineOptions
			postgreSQLReplicationPipelineOptions) {

		String[] individualActivityColumns = StringUtils.split(
			postgreSQLReplicationPipelineOptions.getIndividualActivityColumns(),
			",");

		pipeline.apply(
			TextIO.read(
			).from(
				postgreSQLReplicationPipelineOptions.
					getIndividualActivityInputDirectory()
			)
		).apply(
			JdbcIO.<String>write(
			).withDataSourceConfiguration(
				JdbcIOUtil.createDataSourceConfiguration(
					postgreSQLReplicationPipelineOptions)
			).withStatement(
				String.format(
					"insert into %s.individualactivity(%s) values(%s)",
					postgreSQLReplicationPipelineOptions.getProjectId(),
					StringUtils.join(individualActivityColumns, ","),
					StringUtils.repeat(
						"?", ",", individualActivityColumns.length))
			).withPreparedStatementSetter(
				new JdbcIO.PreparedStatementSetter<String>() {

					@Override
					public void setParameters(
							String element, PreparedStatement ps)
						throws Exception {

						String[] columnValues = CSVParser.parseLine(element);

						for (int i = 0; i < individualActivityColumns.length;
							 i++) {

							ps.setString(i + 1, columnValues[i]);
						}
					}

				}
			).withBatchSize(
				postgreSQLReplicationPipelineOptions.getBatchSize()
			)
		);
	}

}