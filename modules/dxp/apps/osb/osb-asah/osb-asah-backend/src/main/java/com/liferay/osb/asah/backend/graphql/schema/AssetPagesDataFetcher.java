/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.graphql.schema;

import com.liferay.osb.asah.backend.dog.MetricDog;
import com.liferay.osb.asah.backend.dog.MetricTypeDog;
import com.liferay.osb.asah.backend.dog.helper.SearchQueryContext;
import com.liferay.osb.asah.backend.dto.AssetMetricDTO;
import com.liferay.osb.asah.backend.graphql.annotation.GraphQLTypeWiring;
import com.liferay.osb.asah.backend.model.AssetMetric;
import com.liferay.osb.asah.backend.model.AssetType;
import com.liferay.osb.asah.common.model.MetricType;
import com.liferay.osb.asah.common.model.ResultBag;

import graphql.schema.DataFetchingEnvironment;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

/**
 * @author Inácio Nery
 */
@Component
@GraphQLTypeWiring(fieldName = "assetPages", typeName = "QueryType")
public class AssetPagesDataFetcher
	extends BaseDataFetcher<ResultBag<AssetMetricDTO>> {

	@Override
	public ResultBag<AssetMetricDTO> get(
		DataFetchingEnvironment dataFetchingEnvironment,
		SearchQueryContext searchQueryContext) {

		Set<MetricType> metricTypes = _getMetricTypes(
			searchQueryContext.getAssetType(),
			dataFetchingEnvironment.getArgument("selectedMetrics"));

		int size = dataFetchingEnvironment.getArgument("size");
		int start = dataFetchingEnvironment.getArgument("start");

		Page<AssetMetric> assetMetrics = _metricDog.getAppearsOnMetrics(
			metricTypes, PageRequest.of(start / size, size),
			searchQueryContext);

		return new ResultBag<>(
			_getAssetMetricDTO(assetMetrics.getContent(), metricTypes),
			assetMetrics.getTotalElements());
	}

	private List<AssetMetricDTO> _getAssetMetricDTO(
		List<AssetMetric> assetMetrics, Set<MetricType> metricTypes) {

		List<AssetMetricDTO> assetMetricDTOs = new ArrayList<>();

		for (AssetMetric assetMetric : assetMetrics) {
			assetMetricDTOs.add(new AssetMetricDTO(assetMetric, metricTypes));
		}

		return assetMetricDTOs;
	}

	private Set<MetricType> _getMetricTypes(
		AssetType assetType, List<String> selectedMetrics) {

		Set<MetricType> metricTypes = new LinkedHashSet<>();

		for (String selectedMetric : selectedMetrics) {
			metricTypes.add(
				_metricTypeDog.getMetricType(assetType, selectedMetric));
		}

		return metricTypes;
	}

	@Autowired
	private MetricDog _metricDog;

	@Autowired
	private MetricTypeDog _metricTypeDog;

}