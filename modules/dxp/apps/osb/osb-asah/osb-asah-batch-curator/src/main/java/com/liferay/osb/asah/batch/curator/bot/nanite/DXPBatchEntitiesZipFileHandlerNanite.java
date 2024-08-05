/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.batch.curator.bot.nanite;

import com.liferay.osb.asah.common.composer.ComposerDXPIngestionDAGTrigger;
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
		String bucketFolder = contextJSONObject.getString("bucketFolder");
		String bucketName = contextJSONObject.getString("bucketName");
		String filePrefix = contextJSONObject.getString("filePrefix");
		String fileSuffix = contextJSONObject.getString("fileSuffix");

		File zipTmpFile = _readFile(
			bucketName, bucketFolder, filePrefix, fileSuffix);

		File gzipTmpFile = _convertZipToGzip(filePrefix, zipTmpFile);

		_googleStorage.archiveSync(
			bucketName, bucketFolder, gzipTmpFile, gzipTmpFile.getName(),
			ProjectIdThreadLocal.getProjectId());

		_composerDXPIngestionDAGTrigger.trigger(
			contextJSONObject.getString("resourceName"),
			String.format(
				"gs://%s/%s/%s/%s", bucketName,
				ProjectIdThreadLocal.getProjectId(), bucketFolder,
				gzipTmpFile.getName()));

		boolean result = zipTmpFile.delete();

		if (result && _log.isDebugEnabled()) {
			_log.debug("Deleted zip temporary file " + zipTmpFile);
		}
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	private File _convertZipToGzip(String gzipFilePrefix, File zipTmpFile)
		throws Exception {

		long start = System.currentTimeMillis();

		File gzipTmpFile = File.createTempFile(gzipFilePrefix, "gz");

		GZIPOutputStream gzipOutputStream = new GZIPOutputStream(
			new FileOutputStream(gzipTmpFile));

		try (ZipInputStream zipInputStream = new ZipInputStream(
				new FileInputStream(zipTmpFile))) {

			zipInputStream.getNextEntry();

			StreamUtils.copy(zipInputStream, gzipOutputStream);
		}

		gzipOutputStream.close();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"File conversion to gzip took " +
					(System.currentTimeMillis() - start) + " ms");
		}

		return gzipTmpFile;
	}

	private File _readFile(
		String bucket, String bucketFolder, String filePrefix,
		String fileSuffix) {

		if (_log.isDebugEnabled()) {
			_log.debug(
				String.format(
					"Reading file gs://%s/%s/%s/%s.%s", bucket,
					ProjectIdThreadLocal.getProjectId(), bucketFolder,
					filePrefix, fileSuffix));
		}

		return _googleStorage.readFile(
			bucket, bucketFolder, filePrefix, fileSuffix,
			ProjectIdThreadLocal.getProjectId());
	}

	private static final Log _log = LogFactory.getLog(
		DXPBatchEntitiesZipFileHandlerNanite.class);

	@Autowired
	private ComposerDXPIngestionDAGTrigger _composerDXPIngestionDAGTrigger;

	@Autowired
	private GoogleStorage _googleStorage;

}