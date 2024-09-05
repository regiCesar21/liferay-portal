/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.publisher.rest.controller;

import com.liferay.osb.asah.common.antivirus.ClamAVScanner;
import com.liferay.osb.asah.common.configuration.GoogleCloudConfiguration;
import com.liferay.osb.asah.common.constants.HeaderConstants;
import com.liferay.osb.asah.common.dog.DataSourceDog;
import com.liferay.osb.asah.common.entity.DataSource;
import com.liferay.osb.asah.common.spring.http.exception.OSBAsahException;
import com.liferay.osb.asah.common.storage.GoogleStorage;
import com.liferay.osb.asah.common.util.ProjectIdThreadLocal;
import com.liferay.osb.asah.publisher.util.DXPBatchEntitiesFileUploadEvent;
import com.liferay.osb.asah.publisher.util.DXPBatchEntitiesFileUploadEventHandler;

import java.io.File;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Riccardo Ferrari
 */
@CrossOrigin
@RequestMapping("/dxp-batch-entities")
@RestController
public class DXPBatchEntitiesRestController {

	@GetMapping(produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> get(
		@RequestHeader(value = HeaderConstants.DATA_SOURCE_ID) String
			dataSourceId,
		@RequestParam("resourceName") String resourceName,
		@RequestHeader(required = false, value = "If-Modified-Since") String
			ifModifiedSince) {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Received download request for resource: " + resourceName);
		}

		File file = _readSparkJobResult(
			dataSourceId, _parseDate(ifModifiedSince), resourceName);

		if (file == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.ok();

		bodyBuilder.headers(
			new HttpHeaders() {
				{
					add(
						HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=" + resourceName + ".zip");
					add(
						HttpHeaders.CONTENT_LENGTH,
						String.valueOf(file.length()));
				}
			});

		return bodyBuilder.body(new FileSystemResource(file));
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> post(
			@RequestHeader(
				required = false, value = HttpHeaders.CONTENT_ENCODING
			)
			String contentEncoding,
			@RequestHeader(value = HeaderConstants.DATA_SOURCE_ID) String
				dataSourceId,
			@RequestPart(value = "file") List<MultipartFile> multipartFiles,
			@RequestPart(required = false, value = "uploadType") String
				uploadType)
		throws Exception {

		DataSource dataSource = _dataSourceDog.getDataSource(
			Long.parseLong(dataSourceId));

		for (MultipartFile multipartFile : multipartFiles) {
			String name = multipartFile.getOriginalFilename();

			_validateDataSourceConfiguration(dataSource, name);

			if (_log.isDebugEnabled()) {
				_log.debug("Received upload request " + name);
			}

			if (_isFileUploadEmpty(contentEncoding, multipartFile.getSize())) {
				if (_log.isDebugEnabled()) {
					_log.debug("Skipping empty uploaded file  " + name);
				}

				continue;
			}

			if (_clamAVScanner != null) {
				_clamAVScanner.scan(multipartFile.getInputStream());
			}

			_dxpBatchEntitiesFileUploadHandler.receive(
				new DXPBatchEntitiesFileUploadEvent(
					contentEncoding, dataSourceId,
					multipartFile.getInputStream(), name, uploadType));

			if (_log.isInfoEnabled()) {
				_log.info(
					String.format(
						"Uploaded resource %s (%s B) from data source ID %s " +
							"saved successfully",
						name, multipartFile.getSize(), dataSourceId));
			}
		}

		return ResponseEntity.ok(Collections.emptyList());
	}

	private String _getValidatedFileName(String fileName) {
		if (!Objects.equals(fileName, FilenameUtils.getName(fileName))) {
			throw new IllegalArgumentException("Invalid file name");
		}

		return fileName;
	}

	private boolean _isFileUploadEmpty(String contentEncoding, long fileSize) {
		if (StringUtils.equals(contentEncoding, "gzip") &&
			(fileSize <= _EMPTY_GZIP_FILE_LENGTH)) {

			return true;
		}

		if (StringUtils.equals(contentEncoding, "zip") &&
			(fileSize <= _EMPTY_ZIP_FILE_LENGTH)) {

			return true;
		}

		return false;
	}

	private Date _parseDate(String dateString) {
		try {
			if (dateString == null) {
				return null;
			}

			Instant instant = Instant.from(
				_dateTimeFormatter.parse(dateString));

			ZonedDateTime zonedDateTime = instant.atZone(ZoneOffset.UTC);

			Date date = Date.from(zonedDateTime.toInstant());

			if (_log.isDebugEnabled()) {
				_log.debug("Resource modified date: " + date);
			}

			return date;
		}
		catch (DateTimeParseException dateTimeParseException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to parse last modified date",
					dateTimeParseException);
			}

			return null;
		}
	}

	private File _readSparkJobResult(
		String dataSourceId, Date sparkJobResultDateAfter,
		String sparkJobResultPathPrefix) {

		try {
			String bucketFolder = _getValidatedFileName(dataSourceId);

			return _googleStorage.readSparkJobResult(
				_googleCloudConfiguration.getDXPEntitiesBucketName(),
				bucketFolder, ProjectIdThreadLocal.getProjectId(),
				sparkJobResultDateAfter, sparkJobResultPathPrefix);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return null;
	}

	private void _validateDataSourceConfiguration(
		DataSource dataSource, String name) {

		if (Objects.equals(
				name,
				"com.liferay.analytics.dxp.entity.rest.dto.v1_0.DXPEntity") &&
			!(BooleanUtils.toBoolean(dataSource.getAccountsSelected()) ||
			  BooleanUtils.toBoolean(dataSource.getContactsSelected()))) {

			throw new OSBAsahException(
				HttpStatus.BAD_REQUEST,
				"Contacts synchronization is not enabled");
		}
	}

	private static final long _EMPTY_GZIP_FILE_LENGTH = 20;

	private static final long _EMPTY_ZIP_FILE_LENGTH = 140;

	private static final Log _log = LogFactory.getLog(
		DXPBatchEntitiesRestController.class);

	private static final DateTimeFormatter _dateTimeFormatter =
		DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss zzz");

	@Autowired(required = false)
	private ClamAVScanner _clamAVScanner;

	@Autowired
	private DataSourceDog _dataSourceDog;

	@Autowired
	private DXPBatchEntitiesFileUploadEventHandler
		_dxpBatchEntitiesFileUploadHandler;

	@Autowired
	private GoogleCloudConfiguration _googleCloudConfiguration;

	@Autowired
	private GoogleStorage _googleStorage;

}