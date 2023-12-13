/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.graphql.schema;

import com.liferay.osb.asah.backend.dog.MetricDog;
import com.liferay.osb.asah.backend.dog.MetricTypeDog;
import com.liferay.osb.asah.backend.dog.SiteMetricDog;
import com.liferay.osb.asah.backend.dog.helper.SearchQueryContext;
import com.liferay.osb.asah.backend.graphql.annotation.GraphQLTypeWiring;
import com.liferay.osb.asah.backend.model.AssetType;
import com.liferay.osb.asah.backend.model.Metric;
import com.liferay.osb.asah.common.model.MetricType;

import graphql.execution.ExecutionStepInfo;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLFieldDefinition;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Inácio Nery
 */
@Component
@GraphQLTypeWiring(fieldName = "browser", typeName = "Metric")
public class BrowserDataFetcher extends BaseDataFetcher<List<Metric>> {

	@Override
	public List<Metric> get(
		DataFetchingEnvironment dataFetchingEnvironment,
		SearchQueryContext searchQueryContext) {

		ExecutionStepInfo fieldExecutionStepInfo =
			dataFetchingEnvironment.getExecutionStepInfo();

		ExecutionStepInfo parentExecutionStepInfo =
			fieldExecutionStepInfo.getParent();

		GraphQLFieldDefinition graphQLFieldDefinition =
			parentExecutionStepInfo.getFieldDefinition();

		MetricType metricType = _metricTypeDog.getMetricType(
			searchQueryContext.getAssetType(),
			graphQLFieldDefinition.getName());

		if (searchQueryContext.getAssetType() == AssetType.SITE) {
			return _siteMetricDog.getBrowserMetrics(
				metricType, searchQueryContext);
		}

		return _metricDog.getBrowserMetrics(metricType, searchQueryContext);
	}

	@Autowired
	private MetricDog _metricDog;

	@Autowired
	private MetricTypeDog _metricTypeDog;

	@Autowired
	private SiteMetricDog _siteMetricDog;

}