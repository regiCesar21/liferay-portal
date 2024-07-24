/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.rest.controller.functional;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.osb.asah.common.entity.EventAttributeDefinition;
import com.liferay.osb.asah.common.entity.EventDefinition;
import com.liferay.osb.asah.common.entity.EventDefinitionEventAttributeDefinition;
import com.liferay.osb.asah.common.repository.EventAttributeDefinitionRepository;
import com.liferay.osb.asah.common.repository.EventDefinitionRepository;

import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vinicius Lopes
 */
@Profile("dev")
@RequestMapping(
	produces = "application/json", value = "/functional/event-definition"
)
@RestController(
	"com.liferay.osb.asah.backend.rest.controller.functional.EventDefinitionRestController"
)
public class EventDefinitionRestController {

	@PostMapping
	public ResponseEntity postEventDefinition(@RequestBody String json) {
		JSONArray jsonArray = new JSONArray(json);

		jsonArray.forEach(
			eventDefinitionObject -> {
				EventDefinition eventDefinition =
					_eventDefinitionRepository.save(
						_objectMapper.convertValue(
							eventDefinitionObject, EventDefinition.class));

				JSONObject jsonObject = (JSONObject)eventDefinitionObject;

				JSONArray eventAttributeDefinitionJSONArray =
					jsonObject.optJSONArray("eventAttributeDefinitions");

				if (eventAttributeDefinitionJSONArray != null) {
					eventAttributeDefinitionJSONArray.forEach(
						eventAttributeDefinitionObject -> {
							EventAttributeDefinition eventAttributeDefinition =
								_objectMapper.convertValue(
									eventAttributeDefinitionObject,
									EventAttributeDefinition.class);

							eventAttributeDefinition.
								setEventDefinitionEventAttributeDefinitions(
									Collections.singleton(
										new EventDefinitionEventAttributeDefinition(
											eventDefinition.getId(), null)));

							_eventAttributeDefinitionRepository.save(
								eventAttributeDefinition);
						});
				}
			});

		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@Autowired
	private EventAttributeDefinitionRepository
		_eventAttributeDefinitionRepository;

	@Autowired
	private EventDefinitionRepository _eventDefinitionRepository;

	@Autowired
	private ObjectMapper _objectMapper;

}