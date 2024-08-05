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

	public void trigger(String resourceName, String zipFilePath) {
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
					_googleCloudConfiguration.getComposerEndpoint() +
						"/api/v1/dags/" + dagId + "/dagRuns"),
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
		ComposerDXPIngestionDAGTrigger.class);

	private final Map<String, String> _entities = new HashMap<>();

	@Autowired
	private GoogleCloudConfiguration _googleCloudConfiguration;

}