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
		File zipTmpFile = _googleStorage.readFile(
			contextJSONObject.getString("bucketName"),
			contextJSONObject.getString("bucketFolder"),
			contextJSONObject.getString("filePrefix"),
			contextJSONObject.getString("fileSuffix"),
			ProjectIdThreadLocal.getProjectId());

		File gzipTmpFile = _convertZipToGzip(
			contextJSONObject.getString("filePrefix"), zipTmpFile);

		_googleStorage.archiveSync(
			contextJSONObject.getString("bucketName"),
			contextJSONObject.getString("bucketFolder"), gzipTmpFile,
			gzipTmpFile.getName(), ProjectIdThreadLocal.getProjectId());

		_composerDAGTrigger.trigger(
			contextJSONObject.getString("resourceName"),
			String.format(
				"gs://%s/%s/%s/%s", contextJSONObject.getString("bucketName"),
				ProjectIdThreadLocal.getProjectId(),
				contextJSONObject.getString("bucketFolder"),
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