/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.rest.controller.api.data.source.v1;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.osb.asah.common.dog.DataSourceDog;
import com.liferay.osb.asah.common.entity.DataSource;
import com.liferay.osb.asah.common.spring.http.exception.OSBAsahException;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Geyson Silva
 */
@RequestMapping(produces = "application/json", value = "/api/1.0/data-sources")
@RestController(
	"com.liferay.osb.asah.backend.rest.controller.api.data.source.v1.DataSourcesRestController"
)
public class DataSourcesRestController {

	@PostMapping("/{id}/disconnect")
	public String disconnectDataSource(
			@PathVariable Long id, @RequestBody String json)
		throws Exception {

		DataSource dataSource = null;

		if (StringUtils.isNotEmpty(json)) {
			JSONObject jsonObject = new JSONObject(json);

			dataSource = _dataSourceDog.getDataSource(id);

			String url = jsonObject.optString("url", dataSource.getURL());

			if (!Objects.equals(dataSource.getURL(), url)) {
				throw new OSBAsahException(
					HttpStatus.BAD_REQUEST,
					"Unable to disconnect data source " + id + " from " + url);
			}
		}

		dataSource = _dataSourceDog.disconnectDataSource(id);

		_sanitize(dataSource);

		JSONObject dataSourceJSONObject = _objectMapper.convertValue(
			dataSource, JSONObject.class);

		return dataSourceJSONObject.toString();
	}

	@GetMapping("/{id}")
	public String getDataSource(@PathVariable Long id) {
		DataSource dataSource = _dataSourceDog.getDataSource(id);

		_sanitize(dataSource);

		JSONObject dataSourceJSONObject = _objectMapper.convertValue(
			dataSource, JSONObject.class);

		return dataSourceJSONObject.toString();
	}

	@PutMapping("/{id}/details")
	public String updateDataSourceDetails(
			@PathVariable Long id, @RequestBody String json)
		throws Exception {

		Boolean accountsSelected = null;
		Boolean commerceChannelsSelected = null;
		Boolean contactsSelected = null;
		Boolean contentRecommenderMostPopularItemsEnabled = null;
		Boolean contentRecommenderUserPersonalizationEnabled = null;
		Boolean sitesSelected = null;

		JSONObject detailJSONObject = new JSONObject(json);

		if (detailJSONObject.has("accountsSelected")) {
			accountsSelected = detailJSONObject.getBoolean("accountsSelected");
		}

		if (detailJSONObject.has("commerceChannelsSelected")) {
			commerceChannelsSelected = detailJSONObject.getBoolean(
				"commerceChannelsSelected");
		}

		if (detailJSONObject.has("contactsSelected")) {
			contactsSelected = detailJSONObject.getBoolean("contactsSelected");
		}

		if (detailJSONObject.has("contentRecommenderMostPopularItemsEnabled")) {
			contentRecommenderMostPopularItemsEnabled =
				detailJSONObject.getBoolean(
					"contentRecommenderMostPopularItemsEnabled");
		}

		if (detailJSONObject.has(
				"contentRecommenderUserPersonalizationEnabled")) {

			contentRecommenderUserPersonalizationEnabled =
				detailJSONObject.getBoolean(
					"contentRecommenderUserPersonalizationEnabled");
		}

		if (detailJSONObject.has("sitesSelected")) {
			sitesSelected = detailJSONObject.getBoolean("sitesSelected");
		}

		DataSource dataSource = _dataSourceDog.updateDataSourceDetails(
			id, accountsSelected, commerceChannelsSelected, contactsSelected,
			contentRecommenderMostPopularItemsEnabled,
			contentRecommenderUserPersonalizationEnabled, sitesSelected);

		_sanitize(dataSource);

		JSONObject dataSourceJSONObject = _objectMapper.convertValue(
			dataSource, JSONObject.class);

		return dataSourceJSONObject.toString();
	}

	private void _sanitize(DataSource dataSource) {
		dataSource.setFaroBackendSecuritySignature(null);
	}

	@Autowired
	private DataSourceDog _dataSourceDog;

	@Autowired
	private ObjectMapper _objectMapper;

}