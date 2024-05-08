/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.graphql.schema;

import com.liferay.osb.asah.backend.dto.EventPropertyDTO;
import com.liferay.osb.asah.backend.graphql.annotation.GraphQLTypeWiring;
import com.liferay.osb.asah.common.dog.EventAttributeDefinitionDog;
import com.liferay.osb.asah.common.dog.EventDefinitionDog;
import com.liferay.osb.asah.common.entity.EventAttributeDefinition;
import com.liferay.osb.asah.common.entity.EventDefinition;
import com.liferay.osb.asah.common.model.ResultBag;
import com.liferay.osb.asah.common.model.Sort;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * @author Marcos Martins
 */
@Component
@GraphQLTypeWiring(fieldName = "eventProperties", typeName = "QueryType")
public class EventPropertyBagDataFetcher
	implements DataFetcher<ResultBag<EventPropertyDTO>> {

	@Override
	public ResultBag<EventPropertyDTO> get(
		DataFetchingEnvironment dataFetchingEnvironment) {

		Long eventDefinitionId = null;

		String eventId = dataFetchingEnvironment.getArgument("eventId");

		if (StringUtils.isNotBlank(eventId)) {
			Map<String, EventDefinition> eventDefinitions =
				_eventDefinitionDog.getEventDefinitions(
					Collections.singleton(eventId));

			EventDefinition eventDefinition = eventDefinitions.get(eventId);

			if (eventDefinition != null) {
				eventDefinitionId = eventDefinition.getId();
			}
		}

		Page<EventAttributeDefinition> eventAttributeDefinitionPage =
			_eventAttributeDefinitionDog.getEventAttributeDefinitionPage(
				eventDefinitionId,
				dataFetchingEnvironment.getArgument("keyword"),
				dataFetchingEnvironment.getArgument("page"),
				dataFetchingEnvironment.getArgument("size"),
				Sort.of(dataFetchingEnvironment.getArgument("sort")), null);

		Stream<EventAttributeDefinition> stream =
			eventAttributeDefinitionPage.stream();

		List<EventPropertyDTO> eventPropertyDTOs = stream.map(
			EventPropertyDTO::new
		).collect(
			Collectors.toList()
		);

		return new ResultBag<>(
			eventPropertyDTOs, eventAttributeDefinitionPage.getTotalElements());
	}

	@Autowired
	private EventAttributeDefinitionDog _eventAttributeDefinitionDog;

	@Autowired
	private EventDefinitionDog _eventDefinitionDog;

}