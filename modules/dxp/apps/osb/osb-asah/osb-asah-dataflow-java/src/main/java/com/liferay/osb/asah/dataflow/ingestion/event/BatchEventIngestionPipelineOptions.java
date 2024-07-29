/**
 * SPDX-FileCopyrightText: (c) 2004 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.dataflow.ingestion.event;

import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.Validation;

/**
 * @author Marcellus Tavares
 */
public interface BatchEventIngestionPipelineOptions
	extends DataflowPipelineOptions {

	@Description(
		"Return the input directory name. The name should be in the format of gs://..."
	)
	@Validation.Required
	public String getInputDirectory();

	@Description(
		"Return the output directory for the files. The directory must end with a slash."
	)
	@Validation.Required
	public String getOutputDirectory();

	@Default.Long(5)
	public long getSessionWindowAllowedLateness();

	@Default.Long(30)
	public long getSessionWindowGapDuration();

	public void setInputDirectory(String inputDirectory);

	public void setOutputDirectory(String outputDirectory);

	public void setSessionWindowAllowedLateness(long value);

	public void setSessionWindowGapDuration(long value);

}