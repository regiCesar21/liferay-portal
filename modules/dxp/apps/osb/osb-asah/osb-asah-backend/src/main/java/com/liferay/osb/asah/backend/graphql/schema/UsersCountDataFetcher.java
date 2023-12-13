/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.graphql.schema;

import com.liferay.osb.asah.backend.dog.MetricTypeDog;
import com.liferay.osb.asah.backend.dog.UserDog;
import com.liferay.osb.asah.backend.dog.helper.SearchQueryContext;
import com.liferay.osb.asah.backend.graphql.annotation.GraphQLTypeWiring;
import com.liferay.osb.asah.common.model.MetricType;

import graphql.execution.ExecutionStepInfo;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLFieldDefinition;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Marcellus Tavares
 */
@Component
@GraphQLTypeWiring(fieldName = "anonymousUsersCount", typeName = "Metric")
@GraphQLTypeWiring(fieldName = "knownUsersCount", typeName = "Metric")
@GraphQLTypeWiring(
	fieldName = "nonsegmentedKnownUsersCount", typeName = "Metric"
)
@GraphQLTypeWiring(
	fieldName = "segmentedAnonymousUsersCount", typeName = "Metric"
)
@GraphQLTypeWiring(fieldName = "segmentedKnownUsersCount", typeName = "Metric")
public class UsersCountDataFetcher extends BaseDataFetcher<Long> {

	@Override
	public Long get(
		DataFetchingEnvironment dataFetchingEnvironment,
		SearchQueryContext searchQueryContext) {

		ExecutionStepInfo fieldExecutionStepInfo =
			dataFetchingEnvironment.getExecutionStepInfo();

		ExecutionStepInfo parentExecutionStepInfo =
			fieldExecutionStepInfo.getParent();

		GraphQLFieldDefinition parentGraphQLFieldDefinition =
			parentExecutionStepInfo.getFieldDefinition();

		MetricType metricType = _metricTypeDog.getMetricType(
			searchQueryContext.getAssetType(),
			parentGraphQLFieldDefinition.getName());

		GraphQLFieldDefinition graphQLFieldDefinition =
			fieldExecutionStepInfo.getFieldDefinition();

		if (Objects.equals(
				graphQLFieldDefinition.getName(), "anonymousUsersCount")) {

			return _userDog.getAnonymousUsersCount(
				metricType, searchQueryContext);
		}

		if (Objects.equals(
				graphQLFieldDefinition.getName(), "knownUsersCount")) {

			return _userDog.getKnownUsersCount(metricType, searchQueryContext);
		}

		if (Objects.equals(
				graphQLFieldDefinition.getName(),
				"nonsegmentedKnownUsersCount")) {

			return _userDog.getNonsegmentedIndividualsCount(
				metricType, searchQueryContext);
		}

		if (Objects.equals(
				graphQLFieldDefinition.getName(),
				"segmentedAnonymousUsersCount")) {

			return 0L;
		}

		return _userDog.getSegmentedIndividualsCount(
			metricType, searchQueryContext);
	}

	@Autowired
	private MetricTypeDog _metricTypeDog;

	@Autowired
	private UserDog _userDog;

}