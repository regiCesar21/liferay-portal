/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.upgrade.v4_9_0;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.transfermanager.ParallelUploadConfig;
import com.google.cloud.storage.transfermanager.TransferManager;
import com.google.cloud.storage.transfermanager.TransferManagerConfig;
import com.google.cloud.storage.transfermanager.UploadJob;
import com.google.cloud.storage.transfermanager.UploadResult;

import com.liferay.osb.asah.common.dog.DataControlTaskDog;
import com.liferay.osb.asah.common.dog.DataExportTaskDog;
import com.liferay.osb.asah.common.entity.DataControlTask;
import com.liferay.osb.asah.common.entity.DataExportTask;
import com.liferay.osb.asah.common.model.DataControlTaskStatus;
import com.liferay.osb.asah.common.util.ProjectIdThreadLocal;
import com.liferay.osb.asah.upgrade.UpgradeStep;

import java.io.File;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Marcellus Tavares
 */
@Component
public class ExportFilesUpgradeStep implements UpgradeStep {

	public List<Path> listZipFilePathsModifiedLast7Days(
			Set<String> matchingZipFileNames, Path folderPath)
		throws Exception {

		if (!Files.exists(folderPath)) {
			return Collections.emptyList();
		}

		List<Path> exportFilePaths = new ArrayList<>();

		LocalDateTime localDateTime = LocalDateTime.now();

		localDateTime = localDateTime.minusDays(7);

		Instant instant = localDateTime.toInstant(ZoneOffset.UTC);

		DirectoryStream.Filter<Path> filter = path -> {
			File file = path.toFile();

			if (!matchingZipFileNames.contains(file.getName())) {
				return false;
			}

			FileTime fileTime = (FileTime)Files.getAttribute(
				path.toAbsolutePath(), "lastModifiedTime");

			Instant fileTimeInstant = fileTime.toInstant();

			if (fileTimeInstant.isAfter(instant)) {
				return true;
			}

			return false;
		};

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				folderPath, filter)) {

			directoryStream.forEach(exportFilePaths::add);
		}

		return exportFilePaths;
	}

	@Override
	public void upgrade(String version) throws Exception {
		Set<Long> taskIds = new HashSet<>();

		taskIds.addAll(_getCompletedDataControlTasksIds());
		taskIds.addAll(_getCompletedDataExportTasksIds());

		if (taskIds.isEmpty()) {
			if (_log.isInfoEnabled()) {
				_log.info("Skipping upgrade because there are no recent tasks");
			}

			return;
		}

		List<Path> filePaths = listZipFilePathsModifiedLast7Days(
			_toZipFileNames(taskIds), _exportPath);

		if (filePaths.isEmpty()) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Skipping upgrade because there are no recent export " +
						"files");
			}

			return;
		}

		String bucketName = _gcloudProjectId + "-export";

		_backup(bucketName, filePaths);

		if (_log.isInfoEnabled()) {
			_log.info(
				String.format(
					"%s files successfully backed up in the bucket %s",
					filePaths.size(), bucketName));
		}
	}

	private void _backup(String bucketName, List<Path> filePaths)
		throws Exception {

		_createBucketIfMissing(bucketName);

		TransferManagerConfig transferManagerConfig =
			TransferManagerConfig.newBuilder(
			).build();

		TransferManager transferManager = transferManagerConfig.getService();

		ParallelUploadConfig parallelUploadConfig =
			ParallelUploadConfig.newBuilder(
			).setBucketName(
				bucketName
			).setSkipIfExists(
				true
			).setPrefix(
				ProjectIdThreadLocal.getProjectId() + "/"
			).build();

		UploadJob uploadJob = transferManager.uploadFiles(
			filePaths, parallelUploadConfig);

		List<UploadResult> uploadResults = uploadJob.getUploadResults();

		for (UploadResult uploadResult : uploadResults) {
			if (_log.isInfoEnabled()) {
				BlobInfo blobInfo = uploadResult.getInput();

				_log.info(
					String.format(
						"Upload for %s completed with status %",
						blobInfo.getName(), uploadResult.getStatus()));
			}
		}
	}

	private void _createBucketIfMissing(String bucketName) {
		Bucket bucket = _storage.get(bucketName);

		if (bucket != null) {
			return;
		}

		BucketInfo.Builder builder = BucketInfo.newBuilder(bucketName);

		_storage.create(
			builder.setLocation(
				_gcloudRegion
			).setRetentionPeriodDuration(
				Duration.of(30, ChronoUnit.DAYS)
			).build());

		if (_log.isInfoEnabled()) {
			_log.info("Bucket created successfully " + bucketName);
		}
	}

	private Set<Long> _getCompletedDataControlTasksIds() {
		Set<Long> dataControlTaskIds = new HashSet<>();

		List<DataControlTask> dataControlTasks =
			_dataControlTaskDog.getDataControlTasks(
				DataControlTaskStatus.COMPLETED);

		for (DataControlTask dataControlTask : dataControlTasks) {
			dataControlTaskIds.add(dataControlTask.getId());
		}

		return dataControlTaskIds;
	}

	private Set<Long> _getCompletedDataExportTasksIds() {
		Set<Long> dataExportTaskIds = new HashSet<>();

		List<DataExportTask> dataExportTasks =
			_dataExportTaskDog.getDataExportTasks(
				DataExportTask.Status.COMPLETED);

		for (DataExportTask dataExportTask : dataExportTasks) {
			dataExportTaskIds.add(dataExportTask.getId());
		}

		return dataExportTaskIds;
	}

	@PostConstruct
	private void _init() {
		StorageOptions storageOptions = StorageOptions.getDefaultInstance();

		_storage = storageOptions.getService();
	}

	private Set<String> _toZipFileNames(Set<Long> taskIds) {
		Stream<Long> stream = taskIds.stream();

		return stream.map(
			taskId -> taskId + ".zip"
		).collect(
			Collectors.toSet()
		);
	}

	private static final Log _log = LogFactory.getLog(
		ExportFilesUpgradeStep.class);

	@Autowired
	private DataControlTaskDog _dataControlTaskDog;

	@Autowired
	private DataExportTaskDog _dataExportTaskDog;

	private final Path _exportPath = Paths.get("/export");

	@Value("${osb.asah.gcloud.project.id:liferaycloud-customer-ac}")
	private String _gcloudProjectId;

	@Value("${gcloud.compute.region:us-west1}")
	private String _gcloudRegion;

	private Storage _storage;

}