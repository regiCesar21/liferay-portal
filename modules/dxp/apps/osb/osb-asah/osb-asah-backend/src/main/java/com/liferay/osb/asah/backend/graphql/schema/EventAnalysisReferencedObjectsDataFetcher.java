/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.graphql.schema;

import com.liferay.osb.asah.backend.dto.EventAnalysisDTO;
import com.liferay.osb.asah.backend.dto.EventAnalysisReferencedObjectDTO;
import com.liferay.osb.asah.backend.graphql.annotation.GraphQLTypeWiring;
import com.liferay.osb.asah.common.dog.EventAttributeDefinitionDog;
import com.liferay.osb.asah.common.dog.EventDefinitionDog;
import com.liferay.osb.asah.common.entity.EventAttributeDefinition;
import com.liferay.osb.asah.common.entity.EventDefinition;
import com.liferay.osb.asah.common.model.AttributeType;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Rachael Koestartyo
 */
@Component
@GraphQLTypeWiring(fieldName = "referencedObjects", typeName = "EventAnalysis")
public class EventAnalysisReferencedObjectsDataFetcher
	implements DataFetcher<EventAnalysisReferencedObjectDTO> {

	@Override
	public EventAnalysisReferencedObjectDTO get(
		DataFetchingEnvironment environment) {

		EventAnalysisDTO eventAnalysisDTO = environment.getSource();

		EventDefinition eventDefinition =
			_eventDefinitionDog.getEventDefinition(
				Long.valueOf(eventAnalysisDTO.getEventDefinitionId()));

		List<EventAnalysisDTO.EventAnalysisBreakdownDTO>
			eventAnalysisBreakdownDTOs =
				eventAnalysisDTO.getEventAnalysisBreakdownDTOs();

		Stream<EventAnalysisDTO.EventAnalysisBreakdownDTO>
			eventAnalysisBreakdownDTOsStream =
				eventAnalysisBreakdownDTOs.stream();

		List<EventAttributeDefinition> eventAttributeDefinitions =
			_eventAttributeDefinitionDog.getEventAttributeDefinitions(
				eventAnalysisBreakdownDTOsStream.filter(
					eventAnalysisBreakdownDTO -> !Objects.equals(
						eventAnalysisBreakdownDTO.getAttributeType(),
						AttributeType.INDIVIDUAL)
				).map(
					EventAnalysisDTO.EventAnalysisBreakdownDTO::getAttributeId
				).map(
					Long::valueOf
				).collect(
					Collectors.toList()
				));

		List<EventAnalysisDTO.EventAnalysisFilterDTO> eventAnalysisFilterDTOs =
			eventAnalysisDTO.getEventAnalysisFilterDTOs();

		Stream<EventAnalysisDTO.EventAnalysisFilterDTO>
			eventAnalysisFilterDTOsStream = eventAnalysisFilterDTOs.stream();

		eventAttributeDefinitions.addAll(
			_eventAttributeDefinitionDog.getEventAttributeDefinitions(
				eventAnalysisFilterDTOsStream.filter(
					eventAnalysisFilterDTO -> !Objects.equals(
						eventAnalysisFilterDTO.getAttributeType(),
						AttributeType.INDIVIDUAL)
				).map(
					EventAnalysisDTO.EventAnalysisFilterDTO::getAttributeId
				).map(
					Long::valueOf
				).collect(
					Collectors.toList()
				)));

		return new EventAnalysisReferencedObjectDTO(
			eventDefinition, eventAttributeDefinitions);
	}

	@Autowired
	private EventAttributeDefinitionDog _eventAttributeDefinitionDog;

	@Autowired
	private EventDefinitionDog _eventDefinitionDog;

}