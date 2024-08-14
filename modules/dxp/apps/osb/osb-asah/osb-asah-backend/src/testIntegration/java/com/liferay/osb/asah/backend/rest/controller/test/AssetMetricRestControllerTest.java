/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.rest.controller.test;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.osb.asah.backend.OSBAsahBackendSpringTestContext;
import com.liferay.osb.asah.backend.dto.AssetMetricDTO;
import com.liferay.osb.asah.backend.model.AssetType;
import com.liferay.osb.asah.backend.model.Metric;
import com.liferay.osb.asah.backend.rest.controller.AssetMetricRestController;
import com.liferay.osb.asah.common.util.SetUtil;
import com.liferay.osb.asah.test.util.annotation.BQSQLResource;
import com.liferay.osb.asah.test.util.spring.OSBAsahTestExecutionListenersContext;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Marcos Martins
 */
public class AssetMetricRestControllerTest
	implements OSBAsahBackendSpringTestContext,
			   OSBAsahTestExecutionListenersContext {

	@BQSQLResource(resourcePath = "blog_asset_metric_views.sql")
	@Test
	public void testGetAssetMetricDTO() {
		AssetMetricDTO assetMetricDTO =
			_assetMetricRestController.getAssetMetricDTO(
				"e131fabc", "blog", 1L, 30, SetUtil.of("viewsMetric"));

		Assertions.assertEquals("e131fabc", assetMetricDTO.getAssetId());
		Assertions.assertEquals(
			AssetType.BLOG.getValue(), assetMetricDTO.getAssetType());

		List<Metric> metrics = new ArrayList<>(
			assetMetricDTO.getSelectedMetrics());

		Assertions.assertEquals(1, metrics.size(), metrics.toString());

		Metric metric = metrics.get(0);

		Assertions.assertEquals("viewsMetric", metric.getName());
		Assertions.assertEquals(9, metric.getValue());
	}

	@Autowired
	private AssetMetricRestController _assetMetricRestController;

	@Autowired
	private ObjectMapper _objectMapper;

}