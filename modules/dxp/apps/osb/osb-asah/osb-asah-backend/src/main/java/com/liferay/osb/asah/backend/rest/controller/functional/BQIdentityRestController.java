/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.rest.controller.functional;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.osb.asah.common.entity.BQIdentity;
import com.liferay.osb.asah.common.repository.BQIdentityRepository;

import org.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Marcos Martins
 */
@Profile("dev")
@RequestMapping(produces = "application/json", value = "/functional/identities")
@RestController(
	"com.liferay.osb.asah.backend.rest.controller.functional.BQIdentityRestController"
)
public class BQIdentityRestController {

	@PostMapping
	public ResponseEntity postBQIdentities(@RequestBody String json) {
		JSONArray jsonArray = new JSONArray(json);

		jsonArray.forEach(
			jsonObject -> _bqIdentityRepository.insert(
				_objectMapper.convertValue(jsonObject, BQIdentity.class)));

		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@Autowired
	private BQIdentityRepository _bqIdentityRepository;

	@Autowired
	private ObjectMapper _objectMapper;

}