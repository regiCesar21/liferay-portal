/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.composer;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import com.liferay.osb.asah.common.configuration.GoogleCloudConfiguration;
import com.liferay.osb.asah.common.date.DateUtil;
import com.liferay.osb.asah.common.json.JSONUtil;
import com.liferay.osb.asah.common.util.ProjectIdThreadLocal;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Marcellus Tavares
 */
@Component
public class ComposerDXPIngestionDAGTrigger {

	public ComposerDXPIngestionDAGTrigger() {
		_entities.put(
			"com.liferay.analytics.dxp.entity.rest.dto.v1_0.AssetEntity",
			"asset");
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

	public void trigger(
		String dataSourceId, String resourceName, String uploadFileBucketFolder,
		String uploadFileBucketName, String uploadFileContentEncoding,
		String uploadDate, String uploadType) {

		String entity = _entities.get(resourceName);

		if (entity == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unregistered entity ingestion for resource " +
						resourceName);
			}

			return;
		}

		String dagId = _getDAGId(entity);

		if (_log.isInfoEnabled()) {
			_log.info("Scheduling DAG " + dagId);
		}

		String uploadFilePath = String.format(
			"gs://%s/%s/%s/%s.%s", uploadFileBucketName,
			ProjectIdThreadLocal.getProjectId(), uploadFileBucketFolder,
			uploadDate,
			StringUtils.replace(uploadFileContentEncoding, "gzip", "gz"));

		try {
			GoogleCredentials credentials =
				GoogleCredentials.getApplicationDefault();

			credentials = credentials.createScoped(
				"https://www.googleapis.com/auth/cloud-platform");

			NetHttpTransport netHttpTransport = new NetHttpTransport();

			HttpRequestFactory httpRequestFactory =
				netHttpTransport.createRequestFactory(
					new HttpCredentialsAdapter(credentials));

			HttpRequest httpRequest = httpRequestFactory.buildPostRequest(
				new GenericUrl(
					_googleCloudConfiguration.getComposerEndpoint() +
						"/api/v1/dags/" + dagId + "/dagRuns"),
				ByteArrayContent.fromString(
					"application/json",
					JSONUtil.put(
						"conf",
						JSONUtil.put(
							"bucketFolder", uploadFileBucketFolder
						).put(
							"bucketName", uploadFileBucketName
						).put(
							"dataSourceId", dataSourceId
						).put(
							"uploadDate", uploadDate
						).put(
							"uploadType", uploadType
						).put(
							"zipFilePath", uploadFilePath
						)
					).put(
						"logical_date", DateUtil.newDateString()
					).toString()));

			HttpHeaders httpHeaders = httpRequest.getHeaders();

			httpHeaders.setContentType("application/json");

			HttpResponse httpResponse = httpRequest.execute();

			if (httpResponse.getStatusCode() != 200) {
				_log.error(
					String.format(
						"Unexpected error after triggering DAG %s and file " +
							"path %s. Status code: %s",
						dagId, uploadFilePath, httpResponse.getStatusCode()));
			}
		}
		catch (IOException ioException) {
			_log.error(
				String.format(
					"Unable to trigger DAG %s and file path %s", dagId,
					uploadFilePath),
				ioException);
		}
	}

	private String _getDAGId(String entity) {
		return String.format(
			"dxp_%s_ingestion_%s", entity, ProjectIdThreadLocal.getProjectId());
	}

	private static final Log _log = LogFactory.getLog(
		ComposerDXPIngestionDAGTrigger.class);

	private final Map<String, String> _entities = new HashMap<>();

	@Autowired
	private GoogleCloudConfiguration _googleCloudConfiguration;

}