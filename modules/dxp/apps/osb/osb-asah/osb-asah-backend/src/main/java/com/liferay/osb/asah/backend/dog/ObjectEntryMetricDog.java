/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.dog;

import com.liferay.osb.asah.backend.dog.helper.SearchQueryContext;
import com.liferay.osb.asah.backend.model.ObjectEntryMetric;
import com.liferay.osb.asah.backend.repository.AssetMetricRepository;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author Rachael Koestartyo
 */
@Component
public class ObjectEntryMetricDog {

	public ObjectEntryMetric getObjectEntryMetric(
		Set<Long> groupIds, SearchQueryContext searchQueryContext,
		Set<String> selectedMetrics) {

		return _assetMetricRepository.getObjectEntryMetric(
			Long.valueOf(searchQueryContext.getDataSourceId()),
			searchQueryContext.getExternalReferenceCode(), groupIds,
			selectedMetrics, searchQueryContext.getTimeRange());
	}

	@Autowired
	@Qualifier("ObjectEntryAssetMetricRepository")
	private AssetMetricRepository<ObjectEntryMetric> _assetMetricRepository;

}