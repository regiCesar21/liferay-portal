/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.repository.test;

import com.liferay.osb.asah.backend.model.Metric;
import com.liferay.osb.asah.backend.model.ObjectEntryMetric;
import com.liferay.osb.asah.backend.repository.AssetMetricRepository;
import com.liferay.osb.asah.common.model.TimeRange;
import com.liferay.osb.asah.test.util.annotation.BQSQLResource;

import java.util.Collections;
import java.util.HashSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author Rachael Koestartyo
 */
public class ObjectEntryAssetMetricRepositoryTest
	extends BaseAssetMetricRepositoryTestCase<ObjectEntryMetric> {

	@BQSQLResource(resourcePath = "object_entry_asset_metric.sql")
	@Test
	public void testGetObjectEntryMetricLast30Days() {
		ObjectEntryMetric objectEntryMetric =
			_assetMetricRepository.getObjectEntryMetric(
				1L, "e131fabc", Collections.singleton(29309L),
				new HashSet<>() {
					{
						add("downloadsMetric");
						add("impressionsMetric");
						add("viewsMetric");
					}
				},
				TimeRange.LAST_30_DAYS);

		Assertions.assertEquals("1", objectEntryMetric.getDataSourceId());
		Assertions.assertEquals(
			"e131fabc", objectEntryMetric.getExternalReferenceCode());

		Metric downloadsMetric = objectEntryMetric.getDownloadsMetric();

		Assertions.assertEquals(3.0, downloadsMetric.getValue());
		Assertions.assertEquals(31.0, downloadsMetric.getPreviousValue());

		Metric impressionsMetric = objectEntryMetric.getImpresssionsMetric();

		Assertions.assertEquals(16.0, impressionsMetric.getValue());
		Assertions.assertEquals(17.0, impressionsMetric.getPreviousValue());

		Metric viewsMetric = objectEntryMetric.getViewsMetric();

		Assertions.assertEquals(5.0, viewsMetric.getValue());
		Assertions.assertEquals(60.0, viewsMetric.getPreviousValue());
	}

	@Override
	protected AssetMetricRepository<ObjectEntryMetric>
		getAssetMetricRepository() {

		return _assetMetricRepository;
	}

	@Autowired
	@Qualifier("ObjectEntryAssetMetricRepository")
	private AssetMetricRepository<ObjectEntryMetric> _assetMetricRepository;

}