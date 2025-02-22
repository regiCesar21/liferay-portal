/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.dataflow.replication;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
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
				PostgreSQLReplicationPipelineOptions.class
			));
	}

	public static void run(
		PostgreSQLReplicationPipelineOptions
			postgreSQLReplicationPipelineOptions) {

		Pipeline pipeline = Pipeline.create(
			postgreSQLReplicationPipelineOptions);

		IndividualReplication.extend(
			pipeline, postgreSQLReplicationPipelineOptions);

		IndividualActivityReplication.extend(
			pipeline, postgreSQLReplicationPipelineOptions);

		PipelineResult pipelineResult = pipeline.run();

		pipelineResult.waitUntilFinish();
	}

}