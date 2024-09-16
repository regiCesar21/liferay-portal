/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.publisher.util;

import com.liferay.osb.asah.common.composer.ComposerDXPIngestionDAGTrigger;
import com.liferay.osb.asah.common.configuration.GoogleCloudConfiguration;
import com.liferay.osb.asah.common.date.DateUtil;
import com.liferay.osb.asah.common.dog.AsahTaskDog;
import com.liferay.osb.asah.common.json.JSONUtil;
import com.liferay.osb.asah.common.storage.GoogleStorage;
import com.liferay.osb.asah.common.util.ProjectIdThreadLocal;

import java.io.File;
import java.io.FileOutputStream;

import java.util.Objects;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Component;

/**
 * @author Marcellus Tavares
 */
@Component
public class DXPBatchEntitiesFileUploadEventHandler {

	public void receive(
			DXPBatchEntitiesFileUploadEvent dxpBatchEntitiesFileUploadEvent)
		throws Exception {

		String uploadDateString = DateUtil.newDateString();

		String fileSuffix = StringUtils.replace(
			dxpBatchEntitiesFileUploadEvent.getContentEncoding(), "gzip", "gz");

		String fileName = uploadDateString + "." + fileSuffix;

		if (_environment.acceptsProfiles(Profiles.of("prod"))) {
			String bucketName =
				_googleCloudConfiguration.getDXPEntitiesBucketName();

			String folderName = String.format(
				"%s/%s/%s", dxpBatchEntitiesFileUploadEvent.getDataSourceId(),
				dxpBatchEntitiesFileUploadEvent.getResourceName(),
				dxpBatchEntitiesFileUploadEvent.getUploadType());

			_googleStorage.archiveSync(
				bucketName, folderName,
				dxpBatchEntitiesFileUploadEvent.getInputStream(), fileName,
				ProjectIdThreadLocal.getProjectId());

			if (Objects.equals(
					dxpBatchEntitiesFileUploadEvent.getContentEncoding(),
					"zip")) {

				_asahTaskDog.scheduleAsahTask(
					"DXPBatchEntitiesZipFileHandlerNanite",
					JSONUtil.put(
						"bucketFolder", folderName
					).put(
						"bucketName", bucketName
					).put(
						"dataSourceId",
						dxpBatchEntitiesFileUploadEvent.getDataSourceId()
					).put(
						"resourceName",
						dxpBatchEntitiesFileUploadEvent.getResourceName()
					).put(
						"uploadDate", uploadDateString
					).put(
						"uploadType",
						dxpBatchEntitiesFileUploadEvent.getUploadType()
					));
			}
			else {
				_composerDXPIngestionDAGTrigger.trigger(
					dxpBatchEntitiesFileUploadEvent.getDataSourceId(),
					dxpBatchEntitiesFileUploadEvent.getResourceName(),
					folderName, bucketName,
					dxpBatchEntitiesFileUploadEvent.getContentEncoding(),
					uploadDateString,
					dxpBatchEntitiesFileUploadEvent.getUploadType());
			}
		}
		else {
			_storeFileSystem(dxpBatchEntitiesFileUploadEvent, fileName);
		}
	}

	private String _getValidatedUploadPath(String path) {
		if (!Objects.equals(path, FilenameUtils.normalize(path))) {
			throw new IllegalArgumentException("Invalid storage path");
		}

		return path;
	}

	private void _storeFileSystem(
			DXPBatchEntitiesFileUploadEvent dxpBatchEntitiesFileUploadEvent,
			String uploadFileName)
		throws Exception {

		String path = _getValidatedUploadPath(
			String.format(
				"%s/%s/%s/%s/%s/%s", _dxpBatchEntitiesStoragePath,
				ProjectIdThreadLocal.getProjectId(),
				dxpBatchEntitiesFileUploadEvent.getDataSourceId(),
				dxpBatchEntitiesFileUploadEvent.getResourceName(),
				dxpBatchEntitiesFileUploadEvent.getUploadType(),
				uploadFileName));

		File targetFile = new File(path);

		FileUtils.createParentDirectories(targetFile);

		try (FileOutputStream fileOutputStream = new FileOutputStream(
				targetFile, true)) {

			IOUtils.copy(
				dxpBatchEntitiesFileUploadEvent.getInputStream(),
				fileOutputStream);
		}
	}

	@Autowired
	private AsahTaskDog _asahTaskDog;

	@Autowired
	private ComposerDXPIngestionDAGTrigger _composerDXPIngestionDAGTrigger;

	@Value("${osb.asah.dxp.batch.entities.storage.path:/storage}")
	private String _dxpBatchEntitiesStoragePath;

	@Autowired
	private Environment _environment;

	@Autowired
	private GoogleCloudConfiguration _googleCloudConfiguration;

	@Autowired
	private GoogleStorage _googleStorage;

}