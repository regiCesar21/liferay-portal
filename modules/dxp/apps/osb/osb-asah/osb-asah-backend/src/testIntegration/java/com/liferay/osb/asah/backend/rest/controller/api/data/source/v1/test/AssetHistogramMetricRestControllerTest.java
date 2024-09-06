/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.rest.controller.api.data.source.v1.test;

import com.liferay.osb.asah.backend.OSBAsahBackendSpringTestContext;
import com.liferay.osb.asah.backend.dto.AssetHistogramMetricDTO;
import com.liferay.osb.asah.backend.dto.HistogramMetricDTO;
import com.liferay.osb.asah.backend.model.BlogMetricType;
import com.liferay.osb.asah.backend.model.DocumentLibraryMetricType;
import com.liferay.osb.asah.backend.rest.controller.api.data.source.v1.AssetHistogramMetricRestController;
import com.liferay.osb.asah.test.util.annotation.BQSQLResource;
import com.liferay.osb.asah.test.util.spring.OSBAsahTestExecutionListenersContext;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Rachael Koestartyo
 */
public class AssetHistogramMetricRestControllerTest
	implements OSBAsahBackendSpringTestContext,
			   OSBAsahTestExecutionListenersContext {

	@BQSQLResource(resourcePath = "asset_histogram_metric.sql")
	@Test
	public void testGetAssetHistogramMetricDTO1() {
		AssetHistogramMetricDTO assetHistogramMetricDTO =
			_assetHistogramMetricRestController.getAssetHistogramMetricDTO(
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
			_assetHistogramMetricRestController.getAssetHistogramMetricDTO(
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
			_assetHistogramMetricRestController.getAssetHistogramMetricDTO(
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
			_assetHistogramMetricRestController.getAssetHistogramMetricDTO(
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
			_assetHistogramMetricRestController.getAssetHistogramMetricDTO(
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
			_assetHistogramMetricRestController.getAssetHistogramMetricDTO(
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
			_assetHistogramMetricRestController.getAssetHistogramMetricDTO(
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
			_assetHistogramMetricRestController.getAssetHistogramMetricDTO(
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
			_assetHistogramMetricRestController.getAssetHistogramMetricDTO(
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
	private AssetHistogramMetricRestController
		_assetHistogramMetricRestController;

}