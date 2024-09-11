/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.rest.controller.api.data.source.v1.test;

import com.liferay.osb.asah.backend.OSBAsahBackendSpringTestContext;
import com.liferay.osb.asah.backend.dto.AppearsOnHistogramMetricDTO;
import com.liferay.osb.asah.backend.dto.AssetAppearsOnHistogramMetricDTO;
import com.liferay.osb.asah.backend.dto.AssetHistogramMetricDTO;
import com.liferay.osb.asah.backend.dto.AssetMetricDTO;
import com.liferay.osb.asah.backend.dto.HistogramMetricDTO;
import com.liferay.osb.asah.backend.model.AssetType;
import com.liferay.osb.asah.backend.model.BlogMetricType;
import com.liferay.osb.asah.backend.model.DocumentLibraryMetricType;
import com.liferay.osb.asah.backend.model.Metric;
import com.liferay.osb.asah.backend.rest.controller.api.data.source.v1.AssetMetricRestController;
import com.liferay.osb.asah.common.util.SetUtil;
import com.liferay.osb.asah.test.util.annotation.BQSQLResource;
import com.liferay.osb.asah.test.util.spring.OSBAsahTestExecutionListenersContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Marcos Martins
 */
public class AssetMetricRestControllerTest
	implements OSBAsahBackendSpringTestContext,
			   OSBAsahTestExecutionListenersContext {

	@BQSQLResource(resourcePath = "asset_appears_on_histogram_metric.sql")
	@Test
	public void testGetAssetAppearsOnHistogramMetricDTO1() {
		AssetAppearsOnHistogramMetricDTO assetAppearsOnHistogramMetricDTO =
			_assetMetricRestController.getTopAppearsOnHistogramMetricDTO(
				"e131fabc", "blog", Collections.singleton(1L), "ALL", 7);

		List<AssetAppearsOnHistogramMetricDTO>
			assetAppearsOnHistogramMetricDTOs = new ArrayList(
				assetAppearsOnHistogramMetricDTO.
					getAssetAppearsOnHistogramMetricDTOs());

		Assertions.assertEquals(
			2, assetAppearsOnHistogramMetricDTOs.size(),
			assetAppearsOnHistogramMetricDTOs.toString());

		AssetAppearsOnHistogramMetricDTO childAssetAppearsOnHistogramMetricDTO =
			assetAppearsOnHistogramMetricDTOs.get(0);

		Assertions.assertEquals(
			"commentsMetric",
			childAssetAppearsOnHistogramMetricDTO.getMetricName());

		List<AppearsOnHistogramMetricDTO> appearsOnHistogramMetricDTOs =
			new ArrayList(
				childAssetAppearsOnHistogramMetricDTO.
					getAppearsOnHistogramMetricDTOs());

		Assertions.assertEquals(
			3, appearsOnHistogramMetricDTOs.size(),
			appearsOnHistogramMetricDTOs.toString());

		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(0), 7,
			new double[] {0, 0, 0, 0, 0, 0, 5}, "Page Title 2");
		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(1), 7,
			new double[] {0, 3, 0, 0, 0, 0, 0}, "Page Title 1");
		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(2), 7,
			new double[] {0, 2, 0, 0, 0, 0, 0}, "Page Title 4");

		childAssetAppearsOnHistogramMetricDTO =
			assetAppearsOnHistogramMetricDTOs.get(1);

		Assertions.assertEquals(
			"viewsMetric",
			childAssetAppearsOnHistogramMetricDTO.getMetricName());

		appearsOnHistogramMetricDTOs = new ArrayList(
			childAssetAppearsOnHistogramMetricDTO.
				getAppearsOnHistogramMetricDTOs());

		Assertions.assertEquals(
			3, appearsOnHistogramMetricDTOs.size(),
			appearsOnHistogramMetricDTOs.toString());

		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(0), 7,
			new double[] {0, 0, 0, 0, 0, 10, 0}, "Page Title 3");
		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(1), 7,
			new double[] {0, 5, 0, 0, 0, 0, 0}, "Page Title 1");
		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(2), 7,
			new double[] {0, 4, 0, 0, 0, 0, 0}, "Page Title 4");

		assetAppearsOnHistogramMetricDTO =
			_assetMetricRestController.getTopAppearsOnHistogramMetricDTO(
				"e131fabc", "blog", Collections.singleton(1L), "KNOWN", 7);

		assetAppearsOnHistogramMetricDTOs = new ArrayList(
			assetAppearsOnHistogramMetricDTO.
				getAssetAppearsOnHistogramMetricDTOs());

		Assertions.assertEquals(
			2, assetAppearsOnHistogramMetricDTOs.size(),
			assetAppearsOnHistogramMetricDTOs.toString());

		childAssetAppearsOnHistogramMetricDTO =
			assetAppearsOnHistogramMetricDTOs.get(0);

		Assertions.assertEquals(
			"commentsMetric",
			childAssetAppearsOnHistogramMetricDTO.getMetricName());

		appearsOnHistogramMetricDTOs = new ArrayList(
			childAssetAppearsOnHistogramMetricDTO.
				getAppearsOnHistogramMetricDTOs());

		Assertions.assertEquals(
			3, appearsOnHistogramMetricDTOs.size(),
			appearsOnHistogramMetricDTOs.toString());

		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(0), 7,
			new double[] {0, 0, 0, 0, 0, 0, 5}, "Page Title 2");
		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(1), 7,
			new double[] {0, 2, 0, 0, 0, 0, 0}, "Page Title 4");
		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(2), 7,
			new double[] {0, 0, 0, 0, 0, 1, 0}, "Page Title 3");

		childAssetAppearsOnHistogramMetricDTO =
			assetAppearsOnHistogramMetricDTOs.get(1);

		Assertions.assertEquals(
			"viewsMetric",
			childAssetAppearsOnHistogramMetricDTO.getMetricName());

		appearsOnHistogramMetricDTOs = new ArrayList(
			childAssetAppearsOnHistogramMetricDTO.
				getAppearsOnHistogramMetricDTOs());

		Assertions.assertEquals(
			3, appearsOnHistogramMetricDTOs.size(),
			appearsOnHistogramMetricDTOs.toString());

		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(0), 7,
			new double[] {0, 0, 0, 0, 0, 10, 0}, "Page Title 3");
		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(1), 7,
			new double[] {0, 4, 0, 0, 0, 0, 0}, "Page Title 4");
		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(2), 7,
			new double[] {0, 0, 0, 0, 0, 0, 1}, "Page Title 2");

		assetAppearsOnHistogramMetricDTO =
			_assetMetricRestController.getTopAppearsOnHistogramMetricDTO(
				"e131fabc", "blog", Collections.singleton(1L), "UNKNOWN", 7);

		assetAppearsOnHistogramMetricDTOs = new ArrayList(
			assetAppearsOnHistogramMetricDTO.
				getAssetAppearsOnHistogramMetricDTOs());

		Assertions.assertEquals(
			2, assetAppearsOnHistogramMetricDTOs.size(),
			assetAppearsOnHistogramMetricDTOs.toString());

		childAssetAppearsOnHistogramMetricDTO =
			assetAppearsOnHistogramMetricDTOs.get(0);

		Assertions.assertEquals(
			"commentsMetric",
			childAssetAppearsOnHistogramMetricDTO.getMetricName());

		appearsOnHistogramMetricDTOs = new ArrayList(
			childAssetAppearsOnHistogramMetricDTO.
				getAppearsOnHistogramMetricDTOs());

		Assertions.assertEquals(
			1, appearsOnHistogramMetricDTOs.size(),
			appearsOnHistogramMetricDTOs.toString());

		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(0), 7,
			new double[] {0, 3, 0, 0, 0, 0, 0}, "Page Title 1");

		childAssetAppearsOnHistogramMetricDTO =
			assetAppearsOnHistogramMetricDTOs.get(1);

		Assertions.assertEquals(
			"viewsMetric",
			childAssetAppearsOnHistogramMetricDTO.getMetricName());

		appearsOnHistogramMetricDTOs = new ArrayList(
			childAssetAppearsOnHistogramMetricDTO.
				getAppearsOnHistogramMetricDTOs());

		Assertions.assertEquals(
			1, appearsOnHistogramMetricDTOs.size(),
			appearsOnHistogramMetricDTOs.toString());

		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(0), 7,
			new double[] {0, 5, 0, 0, 0, 0, 0}, "Page Title 1");
	}

	@BQSQLResource(resourcePath = "asset_appears_on_histogram_metric.sql")
	@Test
	public void testGetAssetAppearsOnHistogramMetricDTO2() {
		AssetAppearsOnHistogramMetricDTO assetAppearsOnHistogramMetricDTO =
			_assetMetricRestController.getTopAppearsOnHistogramMetricDTO(
				"egdasdf", "journal", Collections.singleton(1L), "ALL", 7);

		List<AssetAppearsOnHistogramMetricDTO>
			assetAppearsOnHistogramMetricDTOs = new ArrayList(
				assetAppearsOnHistogramMetricDTO.
					getAssetAppearsOnHistogramMetricDTOs());

		Assertions.assertEquals(
			1, assetAppearsOnHistogramMetricDTOs.size(),
			assetAppearsOnHistogramMetricDTOs.toString());

		AssetAppearsOnHistogramMetricDTO childAssetAppearsOnHistogramMetricDTO =
			assetAppearsOnHistogramMetricDTOs.get(0);

		Assertions.assertEquals(
			"viewsMetric",
			childAssetAppearsOnHistogramMetricDTO.getMetricName());

		List<AppearsOnHistogramMetricDTO> appearsOnHistogramMetricDTOs =
			new ArrayList(
				childAssetAppearsOnHistogramMetricDTO.
					getAppearsOnHistogramMetricDTOs());

		Assertions.assertEquals(
			3, appearsOnHistogramMetricDTOs.size(),
			appearsOnHistogramMetricDTOs.toString());

		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(0), 7,
			new double[] {0, 0, 0, 0, 0, 0, 6}, "Page Title 1");
		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(1), 7,
			new double[] {0, 0, 0, 0, 0, 3, 0}, "Page Title 2");
		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(2), 7,
			new double[] {0, 2, 0, 0, 0, 0, 0}, "Page Title 3");

		assetAppearsOnHistogramMetricDTO =
			_assetMetricRestController.getTopAppearsOnHistogramMetricDTO(
				"egdasdf", "journal", Collections.singleton(1L), "KNOWN", 7);

		assetAppearsOnHistogramMetricDTOs = new ArrayList(
			assetAppearsOnHistogramMetricDTO.
				getAssetAppearsOnHistogramMetricDTOs());

		Assertions.assertEquals(
			1, assetAppearsOnHistogramMetricDTOs.size(),
			assetAppearsOnHistogramMetricDTOs.toString());

		childAssetAppearsOnHistogramMetricDTO =
			assetAppearsOnHistogramMetricDTOs.get(0);

		Assertions.assertEquals(
			"viewsMetric",
			childAssetAppearsOnHistogramMetricDTO.getMetricName());

		appearsOnHistogramMetricDTOs = new ArrayList(
			childAssetAppearsOnHistogramMetricDTO.
				getAppearsOnHistogramMetricDTOs());

		Assertions.assertEquals(
			2, appearsOnHistogramMetricDTOs.size(),
			appearsOnHistogramMetricDTOs.toString());

		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(0), 7,
			new double[] {0, 0, 0, 0, 0, 3, 0}, "Page Title 2");
		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(1), 7,
			new double[] {0, 1, 0, 0, 0, 0, 0}, "Page Title 4");

		assetAppearsOnHistogramMetricDTO =
			_assetMetricRestController.getTopAppearsOnHistogramMetricDTO(
				"egdasdf", "journal", Collections.singleton(1L), "UNKNOWN", 7);

		assetAppearsOnHistogramMetricDTOs = new ArrayList(
			assetAppearsOnHistogramMetricDTO.
				getAssetAppearsOnHistogramMetricDTOs());

		Assertions.assertEquals(
			1, assetAppearsOnHistogramMetricDTOs.size(),
			assetAppearsOnHistogramMetricDTOs.toString());

		childAssetAppearsOnHistogramMetricDTO =
			assetAppearsOnHistogramMetricDTOs.get(0);

		Assertions.assertEquals(
			"viewsMetric",
			childAssetAppearsOnHistogramMetricDTO.getMetricName());

		appearsOnHistogramMetricDTOs = new ArrayList(
			childAssetAppearsOnHistogramMetricDTO.
				getAppearsOnHistogramMetricDTOs());

		Assertions.assertEquals(
			2, appearsOnHistogramMetricDTOs.size(),
			appearsOnHistogramMetricDTOs.toString());

		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(0), 7,
			new double[] {0, 0, 0, 0, 0, 0, 6}, "Page Title 1");
		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(1), 7,
			new double[] {0, 2, 0, 0, 0, 0, 0}, "Page Title 3");
	}

	@BQSQLResource(resourcePath = "asset_appears_on_histogram_metric.sql")
	@Test
	public void testGetAssetAppearsOnHistogramMetricDTO3() {
		AssetAppearsOnHistogramMetricDTO assetAppearsOnHistogramMetricDTO =
			_assetMetricRestController.getTopAppearsOnHistogramMetricDTO(
				"zsrwerf", "document", Collections.singleton(1L), "ALL", 7);

		List<AssetAppearsOnHistogramMetricDTO>
			assetAppearsOnHistogramMetricDTOs = new ArrayList(
				assetAppearsOnHistogramMetricDTO.
					getAssetAppearsOnHistogramMetricDTOs());

		Assertions.assertEquals(
			3, assetAppearsOnHistogramMetricDTOs.size(),
			assetAppearsOnHistogramMetricDTOs.toString());

		AssetAppearsOnHistogramMetricDTO childAssetAppearsOnHistogramMetricDTO =
			assetAppearsOnHistogramMetricDTOs.get(0);

		Assertions.assertEquals(
			"commentsMetric",
			childAssetAppearsOnHistogramMetricDTO.getMetricName());

		List<AppearsOnHistogramMetricDTO> appearsOnHistogramMetricDTOs =
			new ArrayList(
				childAssetAppearsOnHistogramMetricDTO.
					getAppearsOnHistogramMetricDTOs());

		Assertions.assertEquals(
			3, appearsOnHistogramMetricDTOs.size(),
			appearsOnHistogramMetricDTOs.toString());

		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(0), 7,
			new double[] {0, 0, 0, 0, 0, 0, 4}, "Page Title 2");
		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(1), 7,
			new double[] {0, 0, 0, 0, 0, 3, 0}, "Page Title 3");
		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(2), 7,
			new double[] {0, 2, 0, 0, 0, 0, 0}, "Page Title 1");

		childAssetAppearsOnHistogramMetricDTO =
			assetAppearsOnHistogramMetricDTOs.get(1);

		Assertions.assertEquals(
			"downloadsMetric",
			childAssetAppearsOnHistogramMetricDTO.getMetricName());

		appearsOnHistogramMetricDTOs = new ArrayList(
			childAssetAppearsOnHistogramMetricDTO.
				getAppearsOnHistogramMetricDTOs());

		Assertions.assertEquals(
			3, appearsOnHistogramMetricDTOs.size(),
			appearsOnHistogramMetricDTOs.toString());

		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(0), 7,
			new double[] {0, 6, 0, 0, 0, 0, 0}, "Page Title 1");
		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(1), 7,
			new double[] {0, 0, 0, 0, 0, 3, 0}, "Page Title 3");
		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(2), 7,
			new double[] {0, 0, 0, 0, 0, 0, 2}, "Page Title 2");

		childAssetAppearsOnHistogramMetricDTO =
			assetAppearsOnHistogramMetricDTOs.get(2);

		Assertions.assertEquals(
			"previewsMetric",
			childAssetAppearsOnHistogramMetricDTO.getMetricName());

		appearsOnHistogramMetricDTOs = new ArrayList(
			childAssetAppearsOnHistogramMetricDTO.
				getAppearsOnHistogramMetricDTOs());

		Assertions.assertEquals(
			3, appearsOnHistogramMetricDTOs.size(),
			appearsOnHistogramMetricDTOs.toString());

		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(0), 7,
			new double[] {0, 0, 0, 0, 0, 7, 0}, "Page Title 3");
		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(1), 7,
			new double[] {0, 0, 0, 0, 0, 0, 6}, "Page Title 2");
		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(2), 7,
			new double[] {0, 2, 0, 0, 0, 0, 0}, "Page Title 4");

		assetAppearsOnHistogramMetricDTO =
			_assetMetricRestController.getTopAppearsOnHistogramMetricDTO(
				"zsrwerf", "document", Collections.singleton(1L), "KNOWN", 7);

		assetAppearsOnHistogramMetricDTOs = new ArrayList(
			assetAppearsOnHistogramMetricDTO.
				getAssetAppearsOnHistogramMetricDTOs());

		Assertions.assertEquals(
			3, assetAppearsOnHistogramMetricDTOs.size(),
			assetAppearsOnHistogramMetricDTOs.toString());

		childAssetAppearsOnHistogramMetricDTO =
			assetAppearsOnHistogramMetricDTOs.get(0);

		Assertions.assertEquals(
			"commentsMetric",
			childAssetAppearsOnHistogramMetricDTO.getMetricName());

		appearsOnHistogramMetricDTOs = new ArrayList(
			childAssetAppearsOnHistogramMetricDTO.
				getAppearsOnHistogramMetricDTOs());

		Assertions.assertEquals(
			2, appearsOnHistogramMetricDTOs.size(),
			appearsOnHistogramMetricDTOs.toString());

		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(0), 7,
			new double[] {0, 2, 0, 0, 0, 0, 0}, "Page Title 1");
		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(1), 7,
			new double[] {0, 1, 0, 0, 0, 0, 0}, "Page Title 4");

		childAssetAppearsOnHistogramMetricDTO =
			assetAppearsOnHistogramMetricDTOs.get(1);

		Assertions.assertEquals(
			"downloadsMetric",
			childAssetAppearsOnHistogramMetricDTO.getMetricName());

		appearsOnHistogramMetricDTOs = new ArrayList(
			childAssetAppearsOnHistogramMetricDTO.
				getAppearsOnHistogramMetricDTOs());

		Assertions.assertEquals(
			2, appearsOnHistogramMetricDTOs.size(),
			appearsOnHistogramMetricDTOs.toString());

		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(0), 7,
			new double[] {0, 6, 0, 0, 0, 0, 0}, "Page Title 1");
		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(1), 7,
			new double[] {0, 1, 0, 0, 0, 0, 0}, "Page Title 4");

		childAssetAppearsOnHistogramMetricDTO =
			assetAppearsOnHistogramMetricDTOs.get(2);

		Assertions.assertEquals(
			"previewsMetric",
			childAssetAppearsOnHistogramMetricDTO.getMetricName());

		appearsOnHistogramMetricDTOs = new ArrayList(
			childAssetAppearsOnHistogramMetricDTO.
				getAppearsOnHistogramMetricDTOs());

		Assertions.assertEquals(
			2, appearsOnHistogramMetricDTOs.size(),
			appearsOnHistogramMetricDTOs.toString());

		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(0), 7,
			new double[] {0, 2, 0, 0, 0, 0, 0}, "Page Title 4");
		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(1), 7,
			new double[] {0, 1, 0, 0, 0, 0, 0}, "Page Title 1");

		assetAppearsOnHistogramMetricDTO =
			_assetMetricRestController.getTopAppearsOnHistogramMetricDTO(
				"zsrwerf", "document", Collections.singleton(1L), "UNKNOWN", 7);

		assetAppearsOnHistogramMetricDTOs = new ArrayList(
			assetAppearsOnHistogramMetricDTO.
				getAssetAppearsOnHistogramMetricDTOs());

		Assertions.assertEquals(
			3, assetAppearsOnHistogramMetricDTOs.size(),
			assetAppearsOnHistogramMetricDTOs.toString());

		childAssetAppearsOnHistogramMetricDTO =
			assetAppearsOnHistogramMetricDTOs.get(0);

		Assertions.assertEquals(
			"commentsMetric",
			childAssetAppearsOnHistogramMetricDTO.getMetricName());

		appearsOnHistogramMetricDTOs = new ArrayList(
			childAssetAppearsOnHistogramMetricDTO.
				getAppearsOnHistogramMetricDTOs());

		Assertions.assertEquals(
			2, appearsOnHistogramMetricDTOs.size(),
			appearsOnHistogramMetricDTOs.toString());

		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(0), 7,
			new double[] {0, 0, 0, 0, 0, 0, 4}, "Page Title 2");
		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(1), 7,
			new double[] {0, 0, 0, 0, 0, 3, 0}, "Page Title 3");

		childAssetAppearsOnHistogramMetricDTO =
			assetAppearsOnHistogramMetricDTOs.get(1);

		Assertions.assertEquals(
			"downloadsMetric",
			childAssetAppearsOnHistogramMetricDTO.getMetricName());

		appearsOnHistogramMetricDTOs = new ArrayList(
			childAssetAppearsOnHistogramMetricDTO.
				getAppearsOnHistogramMetricDTOs());

		Assertions.assertEquals(
			2, appearsOnHistogramMetricDTOs.size(),
			appearsOnHistogramMetricDTOs.toString());

		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(0), 7,
			new double[] {0, 0, 0, 0, 0, 3, 0}, "Page Title 3");
		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(1), 7,
			new double[] {0, 0, 0, 0, 0, 0, 2}, "Page Title 2");

		childAssetAppearsOnHistogramMetricDTO =
			assetAppearsOnHistogramMetricDTOs.get(2);

		Assertions.assertEquals(
			"previewsMetric",
			childAssetAppearsOnHistogramMetricDTO.getMetricName());

		appearsOnHistogramMetricDTOs = new ArrayList(
			childAssetAppearsOnHistogramMetricDTO.
				getAppearsOnHistogramMetricDTOs());

		Assertions.assertEquals(
			2, appearsOnHistogramMetricDTOs.size(),
			appearsOnHistogramMetricDTOs.toString());

		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(0), 7,
			new double[] {0, 0, 0, 0, 0, 7, 0}, "Page Title 3");
		_assertAppearsOnHistogramMetricDTO(
			appearsOnHistogramMetricDTOs.get(1), 7,
			new double[] {0, 0, 0, 0, 0, 0, 6}, "Page Title 2");
	}

	@BQSQLResource(resourcePath = "asset_histogram_metric.sql")
	@Test
	public void testGetAssetHistogramMetricDTO1() {
		AssetHistogramMetricDTO assetHistogramMetricDTO =
			_assetMetricRestController.getAssetHistogramMetricDTO(
				"e131fabc", "blog", Collections.singleton(1L), "ALL", 7);

		Set<AssetHistogramMetricDTO> assetHistogramMetricDTOs =
			assetHistogramMetricDTO.getAssetHistogramMetricDTOs();

		Assertions.assertEquals(2, assetHistogramMetricDTOs.size());

		for (AssetHistogramMetricDTO curAssetHistogramMetricDTO :
				assetHistogramMetricDTOs) {

			Set<HistogramMetricDTO> histogramMetricDTOs =
				curAssetHistogramMetricDTO.getHistogramMetricDTOs();

			Assertions.assertEquals(7, histogramMetricDTOs.size());

			String metricName = curAssetHistogramMetricDTO.getMetricName();

			if (metricName.equals(BlogMetricType.COMMENTS.getName())) {
				Assertions.assertArrayEquals(
					new double[] {0, 0, 0, 0, 0, 1, 0},
					_getActualValues(histogramMetricDTOs));
			}
			else if (metricName.equals(BlogMetricType.VIEWS.getName())) {
				Assertions.assertArrayEquals(
					new double[] {0, 1, 0, 0, 0, 1, 1},
					_getActualValues(histogramMetricDTOs));
			}
		}

		assetHistogramMetricDTO =
			_assetMetricRestController.getAssetHistogramMetricDTO(
				"e131fabc", "blog", Collections.singleton(1L), "KNOWN", 28);

		assetHistogramMetricDTOs =
			assetHistogramMetricDTO.getAssetHistogramMetricDTOs();

		Assertions.assertEquals(2, assetHistogramMetricDTOs.size());

		for (AssetHistogramMetricDTO curAssetHistogramMetricDTO :
				assetHistogramMetricDTOs) {

			Set<HistogramMetricDTO> histogramMetricDTOs =
				curAssetHistogramMetricDTO.getHistogramMetricDTOs();

			Assertions.assertEquals(28, histogramMetricDTOs.size());

			String metricName = curAssetHistogramMetricDTO.getMetricName();

			if (metricName.equals(BlogMetricType.COMMENTS.getName())) {
				Assertions.assertArrayEquals(
					new double[] {
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 1, 0, 0, 0, 0, 0, 1, 0
					},
					_getActualValues(histogramMetricDTOs));
			}
			else if (metricName.equals(BlogMetricType.VIEWS.getName())) {
				Assertions.assertArrayEquals(
					new double[] {
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 2, 0, 1, 0, 0, 0, 1, 1
					},
					_getActualValues(histogramMetricDTOs));
			}
		}

		assetHistogramMetricDTO =
			_assetMetricRestController.getAssetHistogramMetricDTO(
				"e131fabc", "blog", Collections.singleton(1L), "UNKNOWN", 30);

		assetHistogramMetricDTOs =
			assetHistogramMetricDTO.getAssetHistogramMetricDTOs();

		Assertions.assertEquals(2, assetHistogramMetricDTOs.size());

		for (AssetHistogramMetricDTO curAssetHistogramMetricDTO :
				assetHistogramMetricDTOs) {

			Set<HistogramMetricDTO> histogramMetricDTOs =
				curAssetHistogramMetricDTO.getHistogramMetricDTOs();

			Assertions.assertEquals(30, histogramMetricDTOs.size());

			String metricName = curAssetHistogramMetricDTO.getMetricName();

			if (metricName.equals(BlogMetricType.COMMENTS.getName())) {
				Assertions.assertArrayEquals(
					new double[] {
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
					},
					_getActualValues(histogramMetricDTOs));
			}
			else if (metricName.equals(BlogMetricType.VIEWS.getName())) {
				Assertions.assertArrayEquals(
					new double[] {
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1,
						0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0
					},
					_getActualValues(histogramMetricDTOs));
			}
		}
	}

	@BQSQLResource(resourcePath = "asset_histogram_metric.sql")
	@Test
	public void testGetAssetHistogramMetricDTO2() {
		AssetHistogramMetricDTO assetHistogramMetricDTO =
			_assetMetricRestController.getAssetHistogramMetricDTO(
				"zsrwerf", "document", Collections.singleton(1L), "ALL", 7);

		Set<AssetHistogramMetricDTO> assetHistogramMetricDTOs =
			assetHistogramMetricDTO.getAssetHistogramMetricDTOs();

		Assertions.assertEquals(3, assetHistogramMetricDTOs.size());

		for (AssetHistogramMetricDTO curAssetHistogramMetricDTO :
				assetHistogramMetricDTOs) {

			Set<HistogramMetricDTO> histogramMetricDTOs =
				curAssetHistogramMetricDTO.getHistogramMetricDTOs();

			Assertions.assertEquals(7, histogramMetricDTOs.size());

			String metricName = curAssetHistogramMetricDTO.getMetricName();

			if (metricName.equals(
					DocumentLibraryMetricType.COMMENTS.getName())) {

				Assertions.assertArrayEquals(
					new double[] {0, 1, 0, 0, 0, 3, 2},
					_getActualValues(histogramMetricDTOs));
			}
			else if (metricName.equals(
						DocumentLibraryMetricType.DOWNLOADS.getName())) {

				Assertions.assertArrayEquals(
					new double[] {0, 1, 0, 0, 0, 3, 2},
					_getActualValues(histogramMetricDTOs));
			}
			else if (metricName.equals(
						DocumentLibraryMetricType.PREVIEWS.getName())) {

				Assertions.assertArrayEquals(
					new double[] {0, 1, 0, 0, 0, 1, 1},
					_getActualValues(histogramMetricDTOs));
			}
		}

		assetHistogramMetricDTO =
			_assetMetricRestController.getAssetHistogramMetricDTO(
				"zsrwerf", "document", Collections.singleton(1L), "KNOWN", 28);

		assetHistogramMetricDTOs =
			assetHistogramMetricDTO.getAssetHistogramMetricDTOs();

		Assertions.assertEquals(3, assetHistogramMetricDTOs.size());

		for (AssetHistogramMetricDTO curAssetHistogramMetricDTO :
				assetHistogramMetricDTOs) {

			Set<HistogramMetricDTO> histogramMetricDTOs =
				curAssetHistogramMetricDTO.getHistogramMetricDTOs();

			Assertions.assertEquals(28, histogramMetricDTOs.size());

			String metricName = curAssetHistogramMetricDTO.getMetricName();

			if (metricName.equals(
					DocumentLibraryMetricType.COMMENTS.getName())) {

				Assertions.assertArrayEquals(
					new double[] {
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 2, 0, 0,
						0, 2, 0, 1, 0, 0, 0, 0, 0
					},
					_getActualValues(histogramMetricDTOs));
			}
			else if (metricName.equals(
						DocumentLibraryMetricType.DOWNLOADS.getName())) {

				Assertions.assertArrayEquals(
					new double[] {
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 2, 0, 0,
						0, 2, 0, 1, 0, 0, 0, 0, 0
					},
					_getActualValues(histogramMetricDTOs));
			}
			else if (metricName.equals(
						DocumentLibraryMetricType.PREVIEWS.getName())) {

				Assertions.assertArrayEquals(
					new double[] {
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0,
						0, 1, 0, 1, 0, 0, 0, 0, 0
					},
					_getActualValues(histogramMetricDTOs));
			}
		}

		assetHistogramMetricDTO =
			_assetMetricRestController.getAssetHistogramMetricDTO(
				"zsrwerf", "document", Collections.singleton(1L), "UNKNOWN",
				30);

		assetHistogramMetricDTOs =
			assetHistogramMetricDTO.getAssetHistogramMetricDTOs();

		Assertions.assertEquals(3, assetHistogramMetricDTOs.size());

		for (AssetHistogramMetricDTO curAssetHistogramMetricDTO :
				assetHistogramMetricDTOs) {

			Set<HistogramMetricDTO> histogramMetricDTOs =
				curAssetHistogramMetricDTO.getHistogramMetricDTOs();

			Assertions.assertEquals(30, histogramMetricDTOs.size());

			String metricName = curAssetHistogramMetricDTO.getMetricName();

			if (metricName.equals(
					DocumentLibraryMetricType.COMMENTS.getName())) {

				Assertions.assertArrayEquals(
					new double[] {
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 1, 3, 0, 0, 0, 0, 0, 3, 2
					},
					_getActualValues(histogramMetricDTOs));
			}
			else if (metricName.equals(
						DocumentLibraryMetricType.DOWNLOADS.getName())) {

				Assertions.assertArrayEquals(
					new double[] {
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 1, 3, 0, 0, 0, 0, 0, 3, 2
					},
					_getActualValues(histogramMetricDTOs));
			}
			else if (metricName.equals(
						DocumentLibraryMetricType.PREVIEWS.getName())) {

				Assertions.assertArrayEquals(
					new double[] {
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1
					},
					_getActualValues(histogramMetricDTOs));
			}
		}
	}

	@BQSQLResource(resourcePath = "asset_histogram_metric.sql")
	@Test
	public void testGetAssetHistogramMetricDTO3() {
		AssetHistogramMetricDTO assetHistogramMetricDTO =
			_assetMetricRestController.getAssetHistogramMetricDTO(
				"egdasdf", "journal", Collections.singleton(1L), "ALL", 7);

		Set<AssetHistogramMetricDTO> assetHistogramMetricDTOs =
			assetHistogramMetricDTO.getAssetHistogramMetricDTOs();

		Assertions.assertEquals(1, assetHistogramMetricDTOs.size());

		for (AssetHistogramMetricDTO curAssetHistogramMetricDTO :
				assetHistogramMetricDTOs) {

			Set<HistogramMetricDTO> histogramMetricDTOs =
				curAssetHistogramMetricDTO.getHistogramMetricDTOs();

			Assertions.assertEquals(7, histogramMetricDTOs.size());

			Assertions.assertArrayEquals(
				new double[] {0, 1, 0, 0, 0, 1, 1},
				_getActualValues(histogramMetricDTOs));
		}

		assetHistogramMetricDTO =
			_assetMetricRestController.getAssetHistogramMetricDTO(
				"egdasdf", "journal", Collections.singleton(1L), "KNOWN", 28);

		assetHistogramMetricDTOs =
			assetHistogramMetricDTO.getAssetHistogramMetricDTOs();

		Assertions.assertEquals(1, assetHistogramMetricDTOs.size());

		for (AssetHistogramMetricDTO curAssetHistogramMetricDTO :
				assetHistogramMetricDTOs) {

			Set<HistogramMetricDTO> histogramMetricDTOs =
				curAssetHistogramMetricDTO.getHistogramMetricDTOs();

			Assertions.assertEquals(28, histogramMetricDTOs.size());

			Assertions.assertArrayEquals(
				new double[] {
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1,
					1, 0, 0, 0, 0, 0, 1, 0
				},
				_getActualValues(histogramMetricDTOs));
		}

		assetHistogramMetricDTO =
			_assetMetricRestController.getAssetHistogramMetricDTO(
				"egdasdf", "journal", Collections.singleton(1L), "UNKNOWN", 30);

		assetHistogramMetricDTOs =
			assetHistogramMetricDTO.getAssetHistogramMetricDTOs();

		Assertions.assertEquals(1, assetHistogramMetricDTOs.size());

		for (AssetHistogramMetricDTO curAssetHistogramMetricDTO :
				assetHistogramMetricDTOs) {

			Set<HistogramMetricDTO> histogramMetricDTOs =
				curAssetHistogramMetricDTO.getHistogramMetricDTOs();

			Assertions.assertEquals(30, histogramMetricDTOs.size());

			Assertions.assertArrayEquals(
				new double[] {
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0,
					0, 0, 1, 0, 1, 0, 0, 0, 0, 1
				},
				_getActualValues(histogramMetricDTOs));
		}
	}

	@BQSQLResource(resourcePath = "blog_asset_metric_views.sql")
	@Test
	public void testGetAssetMetricDTO() {
		AssetMetricDTO assetMetricDTO =
			_assetMetricRestController.getAssetMetricDTO(
				"e131fabc", "blog", Collections.singleton(1L), "ALL", 30,
				SetUtil.of("viewsMetric"));

		Assertions.assertEquals("e131fabc", assetMetricDTO.getAssetId());
		Assertions.assertEquals(
			AssetType.BLOG.getValue(), assetMetricDTO.getAssetType());

		List<Metric> metrics = new ArrayList<>(
			assetMetricDTO.getSelectedMetrics());

		Assertions.assertEquals(1, metrics.size(), metrics.toString());

		Metric metric = metrics.get(0);

		Assertions.assertEquals("viewsMetric", metric.getName());
		Assertions.assertEquals(9, metric.getValue());

		assetMetricDTO = _assetMetricRestController.getAssetMetricDTO(
			"e131fabc", "blog", Collections.singleton(1L), "KNOWN", 30,
			SetUtil.of("viewsMetric"));

		metrics = new ArrayList<>(assetMetricDTO.getSelectedMetrics());

		Assertions.assertEquals(1, metrics.size(), metrics.toString());

		metric = metrics.get(0);

		Assertions.assertEquals(5, metric.getValue());

		assetMetricDTO = _assetMetricRestController.getAssetMetricDTO(
			"e131fabc", "blog", Collections.singleton(1L), "UNKNOWN", 30,
			SetUtil.of("viewsMetric"));

		metrics = new ArrayList<>(assetMetricDTO.getSelectedMetrics());

		Assertions.assertEquals(1, metrics.size(), metrics.toString());

		metric = metrics.get(0);

		Assertions.assertEquals(4, metric.getValue());
	}

	private void _assertAppearsOnHistogramMetricDTO(
		AppearsOnHistogramMetricDTO appearsOnHistogramMetricDTO,
		int expectedHistogramMetricDTOSize,
		double[] expectedHistogramMetricValues, String expectedPageTitle) {

		Assertions.assertEquals(
			expectedPageTitle, appearsOnHistogramMetricDTO.getPageTitle());

		Set<HistogramMetricDTO> histogramMetricDTOs =
			appearsOnHistogramMetricDTO.getHistogramMetricDTOs();

		Assertions.assertEquals(
			expectedHistogramMetricDTOSize, histogramMetricDTOs.size());

		Assertions.assertArrayEquals(
			expectedHistogramMetricValues,
			_getActualValues(histogramMetricDTOs));
	}

	private double[] _getActualValues(
		Set<HistogramMetricDTO> histogramMetricDTOs) {

		double[] actualValues = new double[histogramMetricDTOs.size()];

		Iterator<HistogramMetricDTO> iterator = histogramMetricDTOs.iterator();

		int i = 0;

		while (iterator.hasNext()) {
			HistogramMetricDTO histogramMetricDTO = iterator.next();

			actualValues[i++] = histogramMetricDTO.getValue();
		}

		return actualValues;
	}

	@Autowired
	private AssetMetricRestController _assetMetricRestController;

}