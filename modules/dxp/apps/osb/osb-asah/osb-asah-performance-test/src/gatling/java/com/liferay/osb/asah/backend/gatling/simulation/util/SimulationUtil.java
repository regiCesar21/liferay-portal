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

import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author Ivica Cardic
 */
public class SimulationUtil {

	public static Body generateRandomAnalyticsMessageBody() {
		return CoreDsl.StringBody(
			session -> String.format(
				"{\"context\":{\"key\":\"value\"},\"dataSourceId\":1," +
					"\"events\":[{\"applicationId\":\"applicationId\"," +
						"\"eventId\":\"%s\",\"eventDate\":" +
							"\"2024-08-19T13:52:33.123Z\"}],\"id\":%s," +
								"\"userId\":1}",
				_RANDOM.nextLong(), _RANDOM.nextLong()));
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
			_CONFIG.getString("baseUrl")
		).header(
			"OSB-Asah-Project-ID", _CONFIG.getString("osbAsahProjectId")
		).header(
			"OSB-Asah-Faro-Backend-Security-Signature",
			DigestUtils.sha256Hex(
				_CONFIG.getString(
					"osbAsahSecurityToken"
				).concat(
					_CONFIG.getString("baseUrl")
				))
		);
	}

	public static long loadDuring() {
		return _CONFIG.getInt("loadDuring");
	}

	public static int loadRampUsers() {
		return _CONFIG.getInt("loadRampUsers");
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

	public static int spikeDuring() {
		return _CONFIG.getInt("spikeDuring");
	}

	public static int spikeNothingFor() {
		return _CONFIG.getInt("spikeNothingFor");
	}

	public static int spikeRampUsers() {
		return _CONFIG.getInt("spikeRampUsers");
	}

	private static final Config _CONFIG = ConfigFactory.load();

	private static final Random _RANDOM = new Random();

}