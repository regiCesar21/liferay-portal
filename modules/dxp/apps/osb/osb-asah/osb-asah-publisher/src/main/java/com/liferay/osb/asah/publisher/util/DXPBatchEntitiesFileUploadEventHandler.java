/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.publisher.util;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import com.liferay.osb.asah.common.date.DateUtil;
import com.liferay.osb.asah.common.json.JSONUtil;
import com.liferay.osb.asah.common.storage.GoogleStorage;
import com.liferay.osb.asah.common.util.ProjectIdThreadLocal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

	public DXPBatchEntitiesFileUploadEventHandler() {
		_entities.put(
			"com.liferay.analytics.dxp.entity.rest.dto.v1_0.AssetEntity",
			"asset_entity");
		_entities.put(
			"com.liferay.analytics.dxp.entity.rest.dto.v1_0.DXPEntity",
			"entity");
		_entities.put(
			"com.liferay.headless.commerce.machine.learning.dto.v1_0.Order",
			"order");
		_entities.put(
			"com.liferay.headless.commerce.machine.learning.dto.v1_0.Product",
			"product");
	}

	public void receive(
			DXPBatchEntitiesFileUploadEvent dxpBatchEntitiesFileUploadEvent)
		throws Exception {

		if (_environment.acceptsProfiles(Profiles.of("prod"))) {
			String bucketName = StringUtils.replace(
				_dxpBatchEntitiesBucketTemplate, "{googleProjectId}",
				_gcloudProjectId);

			String folderName = String.format(
				"%s/%s/%s", dxpBatchEntitiesFileUploadEvent.getDataSourceId(),
				dxpBatchEntitiesFileUploadEvent.getResourceName(),
				dxpBatchEntitiesFileUploadEvent.getUploadType());

			String fileName = DateUtil.newDateString() + ".zip";

			_googleStorage.archiveSync(
				bucketName, folderName,
				dxpBatchEntitiesFileUploadEvent.getInputStream(), fileName,
				ProjectIdThreadLocal.getProjectId());

			_triggerDAG(
				dxpBatchEntitiesFileUploadEvent.getResourceName(),
				String.format(
					"gs://%s/%s/%s/%s", bucketName,
					ProjectIdThreadLocal.getProjectId(), folderName, fileName));
		}
		else {
			_storeFileSystem(dxpBatchEntitiesFileUploadEvent);
		}
	}

	private String _getValidatedUploadPath(String path) {
		if (!Objects.equals(path, FilenameUtils.normalize(path))) {
			throw new IllegalArgumentException("Invalid storage path");
		}

		return path;
	}

	private void _storeFileSystem(
			DXPBatchEntitiesFileUploadEvent dxpBatchEntitiesFileUploadEvent)
		throws Exception {

		String path = _getValidatedUploadPath(
			String.format(
				"%s/%s/%s/%s/%s/%s.zip", _dxpBatchEntitiesStoragePath,
				ProjectIdThreadLocal.getProjectId(),
				dxpBatchEntitiesFileUploadEvent.getDataSourceId(),
				dxpBatchEntitiesFileUploadEvent.getResourceName(),
				dxpBatchEntitiesFileUploadEvent.getUploadType(),
				DateUtil.newDateString()));

		File targetFile = new File(path);

		FileUtils.createParentDirectories(targetFile);

		try (FileOutputStream fileOutputStream = new FileOutputStream(
				targetFile, true)) {

			IOUtils.copy(
				dxpBatchEntitiesFileUploadEvent.getInputStream(),
				fileOutputStream);
		}
	}

	private void _triggerDAG(String resourceName, String zipFilePath) {
		String entity = _entities.get(resourceName);

		if (entity == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unregistered entity ingestion for resource " +
						resourceName);
			}

			return;
		}

		String dagId = String.format(
			"dxp_%s_ingestion_dataflow_trigger_%s", entity,
			ProjectIdThreadLocal.getProjectId());

		if (_log.isInfoEnabled()) {
			_log.info("Scheduling DAG " + dagId);
		}

		try {
			GoogleCredentials credentials =
				GoogleCredentials.getApplicationDefault();

			credentials = credentials.createScoped(
				"https://www.googleapis.com/auth/cloud-platform");

			NetHttpTransport netHttpTransport = new NetHttpTransport();

			HttpRequestFactory requestFactory =
				netHttpTransport.createRequestFactory(
					new HttpCredentialsAdapter(credentials));

			HttpRequest httpRequest = requestFactory.buildPostRequest(
				new GenericUrl(
					_composerEndpoint + "/api/v1/dags/" + dagId + "/dagRuns"),
				ByteArrayContent.fromString(
					"application/json",
					JSONUtil.put(
						"conf", JSONUtil.put("zipFilePath", zipFilePath)
					).put(
						"logical_date", DateUtil.newDateString()
					).toString()));

			HttpHeaders httpHeaders = httpRequest.getHeaders();

			httpHeaders.setContentType("application/json");

			HttpResponse httpResponse = httpRequest.execute();

			if (httpResponse.getStatusCode() != 200) {
				_log.error(
					String.format(
						"Unexpected error after triggering DAG %s and ZIP " +
							"path %s. Status code: %s",
						dagId, zipFilePath, httpResponse.getStatusCode()));
			}
		}
		catch (IOException ioException) {
			_log.error(
				String.format(
					"Unable to trigger DAG %s and ZIP path %s", dagId,
					zipFilePath),
				ioException);
		}
	}

	private static final Log _log = LogFactory.getLog(
		DXPBatchEntitiesFileUploadEventHandler.class);

	@Value("${osb.asah.composer.endpoint:}")
	private String _composerEndpoint;

	@Value(
		"${osb.asah.dxp.batch.entities.google.bucket:{googleProjectId}-dxp-entities}"
	)
	private String _dxpBatchEntitiesBucketTemplate;

	@Value("${osb.asah.dxp.batch.entities.storage.path:/storage}")
	private String _dxpBatchEntitiesStoragePath;

	private final Map<String, String> _entities = new HashMap<>();

	@Autowired
	private Environment _environment;

	@Value("${osb.asah.gcloud.project.id:liferaycloud-customer-ac}")
	private String _gcloudProjectId;

	@Autowired
	private GoogleStorage _googleStorage;

}