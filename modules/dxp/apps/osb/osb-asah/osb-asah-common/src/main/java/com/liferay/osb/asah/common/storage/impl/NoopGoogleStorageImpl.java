/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.storage.impl;

import com.liferay.osb.asah.common.spring.annotation.ConditionalOnGoogleApplicationCredentials;
import com.liferay.osb.asah.common.storage.GoogleStorage;

import java.io.File;
import java.io.InputStream;

import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * @author Marcellus Tavares
 */
@Component
@ConditionalOnGoogleApplicationCredentials(matchIfMissing = true)
public class NoopGoogleStorageImpl implements GoogleStorage {

	@Override
	public void archiveAsync(
		String bucket, String bucketFolder, File file, String fileName,
		String projectId) {
	}

	@Override
	public void archiveSync(
		String bucket, String bucketFolder, File file, String fileName,
		String projectId) {
	}

	@Override
	public void archiveSync(
		String bucket, String bucketFolder, InputStream inputStream,
		String fileName, String projectId) {
	}

	@Override
	public File readFile(
		String bucket, String bucketFolder, String filePrefix,
		String fileSuffix, String projectId) {

		return null;
	}

	@Override
	public File readSparkJobResult(
			String bucket, String bucketFolder, String projectId,
			Date sparkJobResultDateAfter, String sparkJobResultPathPrefix)
		throws Exception {

		return null;
	}

}