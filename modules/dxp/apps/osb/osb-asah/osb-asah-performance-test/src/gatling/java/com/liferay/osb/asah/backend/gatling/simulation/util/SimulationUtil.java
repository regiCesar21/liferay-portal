/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.gatling.simulation.util;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import io.gatling.javaapi.core.Body;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.util.Collections;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Ivica Cardic
 */
public class SimulationUtil {

	public static Body generateRandomAnalyticsMessageBody() {
		JSONObject bodyJSONObject = new JSONObject();

		JSONObject contextJSONObject = new JSONObject();

		contextJSONObject.put("key", "value");

		bodyJSONObject.put(
			"context", contextJSONObject
		).put(
			"dataSourceId", 1
		);

		JSONObject eventJSONObject = new JSONObject();

		eventJSONObject.put(
			"applicationId", "applicationId"
		).put(
			"eventDate", "2024-08-19T13:52:33.123Z"
		).put(
			"eventId", _RANDOM.nextLong()
		);

		bodyJSONObject.put(
			"events", new JSONArray(Collections.singleton(eventJSONObject))
		).put(
			"id", _RANDOM.nextLong()
		).put(
			"userId", 1
		);

		return CoreDsl.StringBody(bodyJSONObject.toString());
	}

	public static ChainBuilder get(String name, String path) {
		return CoreDsl.exec(
			CoreDsl.exec(
				HttpDsl.http(
					name
				).get(
					path
				).check(
					HttpDsl.status(
					).shouldBe(
						200
					)
				)));
	}

	public static HttpProtocolBuilder httpProtocol() {
		return HttpDsl.http.baseUrl(
			_CONFIG.getString("gatling.baseUrl")
		).header(
			"OSB-Asah-Project-ID", _CONFIG.getString("osb.asah.projectId")
		).header(
			"OSB-Asah-Faro-Backend-Security-Signature",
			DigestUtils.sha256Hex(
				_CONFIG.getString(
					"osb.asah.security.token"
				).concat(
					_CONFIG.getString("gatling.baseUrl")
				))
		);
	}

	public static int loadConstantUsersPerSec() {
		return _CONFIG.getInt("gatling.load.constantUsersPerSec");
	}

	public static long loadDuring() {
		return _CONFIG.getInt("gatling.load.during");
	}

	public static int loadRequestsPerSec() {
		return _CONFIG.getInt("gatling.load.requestsPerSec");
	}

	public static ChainBuilder post(Body body, String name, String path) {
		return CoreDsl.exec(
			CoreDsl.exec(
				HttpDsl.http(
					name
				).post(
					path
				).header(
					"Content-Type", "application/json"
				).body(
					body
				).check(
					HttpDsl.status(
					).shouldBe(
						200
					)
				)));
	}

	public static int spikeConstantUsersPerSec() {
		return _CONFIG.getInt("gatling.spike.constantUsersPerSec");
	}

	public static int spikeDuring() {
		return _CONFIG.getInt("gatling.spike.during");
	}

	public static int spikeNothingFor() {
		return _CONFIG.getInt("gatling.spike.nothingFor");
	}

	public static int spikeRequestsPerSec() {
		return _CONFIG.getInt("gatling.spike.requestsPerSec");
	}

	private static final Config _CONFIG = ConfigFactory.load();

	private static final Random _RANDOM = new Random();

}