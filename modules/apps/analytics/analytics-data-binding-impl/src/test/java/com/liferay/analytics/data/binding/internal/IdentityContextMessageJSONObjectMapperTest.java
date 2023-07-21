/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.data.binding.internal;

import com.liferay.analytics.data.binding.JSONObjectMapper;
import com.liferay.analytics.model.IdentityContextMessage;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Eduardo García
 */
public class IdentityContextMessageJSONObjectMapperTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testJSONDeserialization() throws Exception {
		String jsonString = read("identity_context_message.json");

		IdentityContextMessage identityContextMessage = _jsonObjectMapper.map(
			jsonString);

		Map<String, String> identityFields =
			identityContextMessage.getIdentityFields();

		Assert.assertEquals(
			"julio.camarero@liferay.com", identityFields.get("email"));
		Assert.assertEquals("Julio Camarero", identityFields.get("name"));

		Assert.assertEquals("liferay.com", identityContextMessage.getDomain());
		Assert.assertEquals("en-US", identityContextMessage.getLanguage());
		Assert.assertEquals("MacIntel", identityContextMessage.getPlatform());
		Assert.assertEquals("1.0", identityContextMessage.getProtocolVersion());
		Assert.assertEquals("CET", identityContextMessage.getTimezone());
	}

	@Test
	public void testJSONSerialization() throws Exception {
		IdentityContextMessage.Builder identityContextMessageBuilder =
			IdentityContextMessage.builder("DataSourceId");

		identityContextMessageBuilder.domain("liferay.com");
		identityContextMessageBuilder.language("en-US");
		identityContextMessageBuilder.platform("MacIntel");
		identityContextMessageBuilder.protocolVersion("1.0");
		identityContextMessageBuilder.timezone("CET");

		identityContextMessageBuilder.identityFieldsProperty(
			"email", "julio.camarero@liferay.com");
		identityContextMessageBuilder.identityFieldsProperty(
			"name", "Julio Camarero");

		String actualJSON = _jsonObjectMapper.map(
			identityContextMessageBuilder.build());

		String expectedJSON = read("identity_context_message.json");

		JSONAssert.assertEquals(expectedJSON, actualJSON, false);
	}

	protected String read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		URL url = clazz.getResource(fileName);

		byte[] bytes = Files.readAllBytes(Paths.get(url.toURI()));

		return new String(bytes, "UTF-8");
	}

	private final JSONObjectMapper<IdentityContextMessage> _jsonObjectMapper =
		new IdentityContextMessageJSONObjectMapper();

}