/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.rest.controller.functional;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.osb.asah.common.dog.AsahTaskDog;
import com.liferay.osb.asah.common.entity.BQSession;
import com.liferay.osb.asah.common.repository.BQSessionRepository;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Marcos Martins
 */
@Profile("!prod")
@RequestMapping(produces = "application/json", value = "/functional/sessions")
@RestController(
	"com.liferay.osb.asah.backend.rest.controller.functional.BQSessionRestController"
)
public class BQSessionRestController {

	@DeleteMapping("/close")
	public ResponseEntity closeBQSessions() {
		_asahTaskDog.scheduleAsahTask(
			"AnalyticsEventsIngestionNanite", new JSONObject());

		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@PostMapping
	public ResponseEntity postBQSessions(@RequestBody String json) {
		JSONArray jsonArray = new JSONArray(json);

		jsonArray.forEach(
			jsonObject -> _bqSessionRepository.insert(
				_objectMapper.convertValue(jsonObject, BQSession.class)));

		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@Autowired
	private AsahTaskDog _asahTaskDog;

	@Autowired
	private BQSessionRepository _bqSessionRepository;

	@Autowired
	private ObjectMapper _objectMapper;

}