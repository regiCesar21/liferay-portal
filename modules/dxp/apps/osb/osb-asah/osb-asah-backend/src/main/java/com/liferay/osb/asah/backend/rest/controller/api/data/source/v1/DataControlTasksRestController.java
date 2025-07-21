/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.rest.controller.api.data.source.v1;

import com.liferay.osb.asah.common.dog.DataControlTaskDog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Marcos Martins
 */
@RequestMapping(
	produces = "application/json", value = "/api/1.0/data-control-tasks"
)
@RestController(
	"com.liferay.osb.asah.backend.rest.controller.api.data.source.v1.DataControlTasksRestController"
)
public class DataControlTasksRestController {

	@PostMapping
	public ResponseEntity createDataControlTask(@RequestBody String json) {
		JSONObject jsonObject = new JSONObject(json);

		Set<String> emailAddresses = new HashSet<>();

		JSONArray emailAddressesJSONArray = jsonObject.getJSONArray(
			"emailAddresses");

		emailAddressesJSONArray.forEach(
			emailAddress -> emailAddresses.add(String.valueOf(emailAddress)));

		List<String> types = new ArrayList<>();

		JSONArray typesJSONArray = jsonObject.getJSONArray("types");

		typesJSONArray.forEach(type -> types.add(String.valueOf(type)));

		_dataControlTaskDog.addDataControlTasks(
			emailAddresses, null, "0", types, 0L, "");

		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@Autowired
	private DataControlTaskDog _dataControlTaskDog;

}