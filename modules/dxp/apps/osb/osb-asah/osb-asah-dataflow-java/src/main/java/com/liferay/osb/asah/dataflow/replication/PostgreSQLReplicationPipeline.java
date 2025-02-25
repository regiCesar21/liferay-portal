/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.dataflow.replication;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;

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
				"individual", postgreSQLReplicationDataflowPipelineOptions)
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
				"individualactivity",
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
				"identityinterestscore",
				postgreSQLReplicationDataflowPipelineOptions)
		);

		PipelineResult pipelineResult = pipeline.run();

		pipelineResult.waitUntilFinish();
	}

}