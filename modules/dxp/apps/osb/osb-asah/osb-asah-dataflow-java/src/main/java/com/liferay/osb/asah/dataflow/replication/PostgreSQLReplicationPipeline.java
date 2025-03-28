/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.dataflow.replication;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Marcellus Tavares
 */
public class PostgreSQLReplicationPipeline {

	public static void main(String[] args) {
		run(
			PipelineOptionsFactory.fromArgs(
				args
			).withValidation(
			).as(
				PostgreSQLReplicationDataflowPipelineOptions.class
			));
	}

	public static void run(
		PostgreSQLReplicationDataflowPipelineOptions
			postgreSQLReplicationDataflowPipelineOptions) {

		Pipeline pipeline = Pipeline.create(
			postgreSQLReplicationDataflowPipelineOptions);

		String tempTableSuffix =
			postgreSQLReplicationDataflowPipelineOptions.getTempTableSuffix();

		// Individual

		pipeline.apply(
			TextIO.read(
			).from(
				postgreSQLReplicationDataflowPipelineOptions.
					getIndividualInputDirectory()
			)
		).apply(
			JdbcIOUtil.createJdbcIOWrite(
				postgreSQLReplicationDataflowPipelineOptions.
					getIndividualColumns(),
				postgreSQLReplicationDataflowPipelineOptions.
					getIndividualPrimaryKey(),
				StringUtils.wrap("individual_" + tempTableSuffix, "\""),
				postgreSQLReplicationDataflowPipelineOptions)
		);

		// Individual Activity

		pipeline.apply(
			TextIO.read(
			).from(
				postgreSQLReplicationDataflowPipelineOptions.
					getIndividualActivityInputDirectory()
			)
		).apply(
			JdbcIOUtil.createJdbcIOWrite(
				postgreSQLReplicationDataflowPipelineOptions.
					getIndividualActivityColumns(),
				postgreSQLReplicationDataflowPipelineOptions.
					getIndividualActivityPrimaryKey(),
				StringUtils.wrap("individualactivity_" + tempTableSuffix, "\""),
				postgreSQLReplicationDataflowPipelineOptions)
		);

		// Individual Interest

		pipeline.apply(
			TextIO.read(
			).from(
				postgreSQLReplicationDataflowPipelineOptions.
					getIndividualInterestInputDirectory()
			)
		).apply(
			JdbcIOUtil.createJdbcIOWrite(
				postgreSQLReplicationDataflowPipelineOptions.
					getIndividualInterestColumns(),
				postgreSQLReplicationDataflowPipelineOptions.
					getIndividualInterestPrimaryKey(),
				StringUtils.wrap("individualinterest_" + tempTableSuffix, "\""),
				postgreSQLReplicationDataflowPipelineOptions)
		);

		// Individual Segment

		pipeline.apply(
			TextIO.read(
			).from(
				postgreSQLReplicationDataflowPipelineOptions.
					getIndividualSegmentInputDirectory()
			)
		).apply(
			JdbcIOUtil.createJdbcIOWrite(
				postgreSQLReplicationDataflowPipelineOptions.
					getIndividualSegmentColumns(),
				postgreSQLReplicationDataflowPipelineOptions.
					getIndividualSegmentPrimaryKey(),
				StringUtils.wrap("individualsegment_" + tempTableSuffix, "\""),
				postgreSQLReplicationDataflowPipelineOptions)
		);

		pipeline.run();
	}

}