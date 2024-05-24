/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.storage;

import java.io.File;
import java.io.InputStream;

import java.util.Date;

import org.springframework.lang.Nullable;

/**
 * @author Marcellus Tavares
 */
public interface GoogleStorage {

	public void archiveAsync(
		String bucket, String bucketFolder, File file, String fileName,
		String projectId);

	public void archiveSync(
		String bucket, String bucketFolder, File file, String fileName,
		String projectId);

	public void archiveSync(
		String bucket, String bucketFolder, InputStream inputStream,
		String fileName, String projectId);

	public File readFile(
		String bucket, @Nullable String bucketFolder, String filePrefix,
		String fileSuffix, String projectId);

	public File readSparkJobResult(
			String bucket, String bucketFolder, String projectId,
			Date sparkJobResultDateAfter, String sparkJobResultPathPrefix)
		throws Exception;

}