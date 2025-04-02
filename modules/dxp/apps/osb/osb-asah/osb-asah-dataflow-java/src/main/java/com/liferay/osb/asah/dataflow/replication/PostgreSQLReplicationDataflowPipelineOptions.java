/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.dataflow.replication;

import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.Validation;

/**
 * @author Marcellus Tavares
 */
public interface PostgreSQLReplicationDataflowPipelineOptions
	extends DataflowPipelineOptions {

	@Default.Integer(500)
	public Integer getBatchSize();

	@Validation.Required
	public String getCloudSQLConnectionName();

	@Default.String("osbasah")
	public String getDatabaseName();

	@Validation.Required
	public String getDatabaseUser();

	@Default.Integer(3)
	public Integer getDatasourceMaxConnections();

	@Description(
		"Return the comma-separated list of column names corresponding to " +
			"the fields in the CSV files"
	)
	@Validation.Required
	public String getIndividualActivityColumns();

	@Validation.Required
	public String getIndividualActivityInputDirectory();

	@Description(
		"Return the comma-separated list of column names used as primary key"
	)
	@Validation.Required
	public String getIndividualActivityPrimaryKey();

	@Default.String("individualactivity")
	public String getIndividualActivityTargetTable();

	@Description(
		"Return the comma-separated list of column names corresponding to " +
			"the fields in the CSV files"
	)
	@Validation.Required
	public String getIndividualColumns();

	@Validation.Required
	public String getIndividualInputDirectory();

	@Description(
		"Return the comma-separated list of column names corresponding to " +
			"the fields in the CSV files"
	)
	@Validation.Required
	public String getIndividualInterestColumns();

	@Validation.Required
	public String getIndividualInterestInputDirectory();

	@Description(
		"Return the comma-separated list of column names used as primary key"
	)
	@Validation.Required
	public String getIndividualInterestPrimaryKey();

	@Default.String("individualinterest")
	public String getIndividualInterestTargetTable();

	@Description(
		"Return the comma-separated list of column names used as primary key"
	)
	@Validation.Required
	public String getIndividualPrimaryKey();

	@Description(
		"Return the comma-separated list of column names corresponding to " +
			"the fields in the CSV files"
	)
	@Validation.Required
	public String getIndividualSegmentColumns();

	@Validation.Required
	public String getIndividualSegmentInputDirectory();

	@Description(
		"Return the comma-separated list of column names used as primary key"
	)
	@Validation.Required
	public String getIndividualSegmentPrimaryKey();

	@Default.String("individualsegment")
	public String getIndividualSegmentTargetTable();

	@Default.String("individual")
	public String getIndividualTargetTable();

	@Description("Return the Analytics Cloud project ID")
	@Validation.Required
	public String getProjectId();

	public void setBatchSize(Integer batchSize);

	public void setCloudSQLConnectionName(String cloudSQLConnectionName);

	public void setDatabaseName(String databaseName);

	public void setDatabaseUser(String databaseUser);

	public void setDatasourceMaxConnections(Integer maxConnections);

	public void setIndividualActivityColumns(String individualActivityColumns);

	public void setIndividualActivityInputDirectory(
		String individualActivityInputDirectory);

	public void setIndividualActivityPrimaryKey(
		String individualActivityPrimaryKey);

	public void setIndividualActivityTargetTable(
		String individualActivityTargetTable);

	public void setIndividualColumns(String individualColumns);

	public void setIndividualInputDirectory(String individualInputDirectory);

	public void setIndividualInterestColumns(String individualInterestColumns);

	public void setIndividualInterestInputDirectory(
		String individualInterestInputDirectory);

	public void setIndividualInterestPrimaryKey(
		String individualInterestPrimaryKey);

	public void setIndividualInterestTargetTable(
		String individualInterestTargetTable);

	public void setIndividualPrimaryKey(String individualPrimaryKey);

	public void setIndividualSegmentColumns(String individualSegmentColumns);

	public void setIndividualSegmentInputDirectory(
		String individualSegmentInputDirectory);

	public void setIndividualSegmentPrimaryKey(
		String individualSegmentPrimaryKey);

	public void setIndividualSegmentTargetTable(
		String individualSegmentTargetTable);

	public void setIndividualTargetTable(String individualTargetTable);

	public void setProjectId(String projectId);

}