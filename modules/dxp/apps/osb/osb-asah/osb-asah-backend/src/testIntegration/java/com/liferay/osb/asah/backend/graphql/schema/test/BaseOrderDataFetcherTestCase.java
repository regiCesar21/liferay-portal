/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.graphql.schema.test;

import com.liferay.osb.asah.backend.OSBAsahBackendSpringTestContext;
import com.liferay.osb.asah.backend.dto.CurrencyValueDTO;
import com.liferay.osb.asah.common.entity.Channel;
import com.liferay.osb.asah.common.entity.ChannelDataSource;
import com.liferay.osb.asah.common.entity.DataSource;
import com.liferay.osb.asah.common.repository.ChannelRepository;
import com.liferay.osb.asah.common.repository.DataSourceRepository;
import com.liferay.osb.asah.test.util.configuration.JDBCTestConfiguration;
import com.liferay.osb.asah.test.util.spring.OSBAsahTestExecutionListenersContext;

import graphql.GraphQL;
import graphql.GraphQLContext;

import graphql.normalized.ExecutableNormalizedField;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingEnvironmentImpl;
import graphql.schema.DataFetchingFieldSelectionSetImpl;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLSchema;
import graphql.schema.GraphQLType;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

/**
 * @author Riccardo Ferrari
 */
@Import(JDBCTestConfiguration.class)
public abstract class BaseOrderDataFetcherTestCase
	implements OSBAsahBackendSpringTestContext,
			   OSBAsahTestExecutionListenersContext {

	@BeforeEach
	public void setUp() {
		DataSource dataSource = new DataSource("Liferay Italy");

		dataSource.setCredentialType("Token Authentication");
		dataSource.setFaroBackendSecuritySignature(
			"faroBackendSecuritySignature");
		dataSource.setId(123L);
		dataSource.setIsNew(Boolean.TRUE);
		dataSource.setProviderType("LIFERAY");
		dataSource.setState("READY");
		dataSource.setStatus("STARTED");
		dataSource.setURL("");

		dataSource = _dataSourceRepository.save(dataSource);

		Channel channel = new Channel("channel");

		channel.setId(11L);
		channel.setIsNew(Boolean.TRUE);

		channel.addChannelDataSource(
			new ChannelDataSource(null, dataSource.getId(), null));

		_channelRepository.save(channel);
	}

	protected void assertCurrencyValueDTO(
		List<CurrencyValueDTO> actualCurrencyValueDTOs, int expectedSize,
		boolean expectedTrend) {

		Assertions.assertNotNull(actualCurrencyValueDTOs);

		Assertions.assertEquals(expectedSize, actualCurrencyValueDTOs.size());

		if (expectedSize == 0) {
			return;
		}

		CurrencyValueDTO currencyValueDTO = actualCurrencyValueDTOs.get(0);

		if (expectedTrend) {
			Assertions.assertNotNull(currencyValueDTO.getTrend());
		}
		else {
			Assertions.assertNull(currencyValueDTO.getTrend());
		}
	}

	protected DataFetchingEnvironment getDataFetchingEnvironment(
		List<String> childFieldNames, String fieldName) {

		DataFetchingEnvironmentImpl.Builder builder =
			DataFetchingEnvironmentImpl.newDataFetchingEnvironment();

		Map<String, Object> arguments = new HashMap<>();

		arguments.put("channelId", "11");
		arguments.put("rangeKey", 7);

		builder.arguments(arguments);

		builder.graphQLContext(GraphQLContext.of(Collections.emptyMap()));

		GraphQLSchema graphQLSchema = _graphQL.getGraphQLSchema();

		GraphQLType graphQLType = graphQLSchema.getType("CurrencyValue");

		Assertions.assertNotNull(graphQLType);

		ExecutableNormalizedField.Builder executableNormalizedFieldBuilder =
			ExecutableNormalizedField.newNormalizedField();

		executableNormalizedFieldBuilder.fieldName(fieldName);

		Stream<String> stream = childFieldNames.stream();

		executableNormalizedFieldBuilder.children(
			stream.map(
				childFieldName -> {
					ExecutableNormalizedField.Builder
						childExecutableNormalizedFieldBuilder =
							ExecutableNormalizedField.newNormalizedField();

					childExecutableNormalizedFieldBuilder.fieldName(
						childFieldName);

					return childExecutableNormalizedFieldBuilder.build();
				}
			).collect(
				Collectors.toList()
			));

		builder.selectionSet(
			DataFetchingFieldSelectionSetImpl.newCollector(
				graphQLSchema, GraphQLList.list(graphQLType),
				executableNormalizedFieldBuilder::build));

		return builder.build();
	}

	@Autowired
	private ChannelRepository _channelRepository;

	@Autowired
	private DataSourceRepository _dataSourceRepository;

	@Autowired
	private GraphQL _graphQL;

}