/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.batch.curator.bot.nanite;

import com.liferay.osb.asah.common.composer.ComposerDAGTrigger;
import com.liferay.osb.asah.common.storage.GoogleStorage;
import com.liferay.osb.asah.common.util.ProjectIdThreadLocal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

/**
 * @author Marcellus Tavares
 */
@Component
public class DXPBatchEntitiesZipFileHandlerNanite extends BaseNanite {

	@Override
	public boolean isLogRunEnabled() {
		return true;
	}

	@Override
	public void run(JSONObject contextJSONObject) throws Exception {
		String bucketName = contextJSONObject.getString("bucketName");
		String bucketFolder = contextJSONObject.getString("bucketFolder");
		String filePrefix = contextJSONObject.getString("filePrefix");
		String fileSuffix = contextJSONObject.getString("fileSuffix");

		if (_log.isDebugEnabled()) {
			_log.debug(
				String.format(
					"Reading remote file gs://%s/%s/%s/%s.%s", bucketName,
					ProjectIdThreadLocal.getProjectId(), bucketFolder,
					filePrefix, fileSuffix));
		}

		File zipTmpFile = _googleStorage.readFile(
			bucketName, bucketFolder, filePrefix, fileSuffix,
			ProjectIdThreadLocal.getProjectId());

		long start = System.currentTimeMillis();

		File gzipTmpFile = _convertZipToGzip(filePrefix, zipTmpFile);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"File conversion to gzip took " +
					(System.currentTimeMillis() - start) + " ms");
		}

		_googleStorage.archiveSync(
			bucketName, bucketFolder, gzipTmpFile, gzipTmpFile.getName(),
			ProjectIdThreadLocal.getProjectId());

		_composerDAGTrigger.trigger(
			contextJSONObject.getString("resourceName"),
			String.format(
				"gs://%s/%s/%s/%s", bucketName,
				ProjectIdThreadLocal.getProjectId(), bucketFolder,
				gzipTmpFile.getName()));
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	private File _convertZipToGzip(String gzipFilePrefix, File zipTmpFile)
		throws Exception {

		File gzipTmpFile = File.createTempFile(gzipFilePrefix, "gz");

		GZIPOutputStream gzipOutputStream = new GZIPOutputStream(
			new FileOutputStream(gzipTmpFile));

		try (ZipInputStream zipInputStream = new ZipInputStream(
				new FileInputStream(zipTmpFile))) {

			zipInputStream.getNextEntry();

			StreamUtils.copy(zipInputStream, gzipOutputStream);
		}

		gzipOutputStream.close();

		return gzipTmpFile;
	}

	private static final Log _log = LogFactory.getLog(
		DXPBatchEntitiesZipFileHandlerNanite.class);

	@Autowired
	private ComposerDAGTrigger _composerDAGTrigger;

	@Autowired
	private GoogleStorage _googleStorage;

}