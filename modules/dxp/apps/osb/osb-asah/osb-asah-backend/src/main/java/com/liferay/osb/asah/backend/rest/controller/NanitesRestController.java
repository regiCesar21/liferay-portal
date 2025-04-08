/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.rest.controller;

import com.liferay.osb.asah.common.http.NanitesHttp;

import org.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leslie Wong
 */
@Profile("!prod")
@RequestMapping("/nanites")
@RestController
public class NanitesRestController {

	@PostMapping("/run")
	public ResponseEntity<String> run(@RequestBody String json) {
		return _nanitesHttp.run(new JSONArray(json));
	}

	@Autowired
	private NanitesHttp _nanitesHttp;

}