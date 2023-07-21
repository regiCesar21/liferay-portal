/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.data.binding.internal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.analytics.data.binding.JSONObjectMapper;
import com.liferay.analytics.model.IdentityContextMessage;

import java.io.IOException;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = "model=com.liferay.analytics.model.IdentityContextMessage",
	service = JSONObjectMapper.class
)
public class IdentityContextMessageJSONObjectMapper
	implements JSONObjectMapper<IdentityContextMessage> {

	@Override
	public String map(IdentityContextMessage identityContextMessage)
		throws IOException {

		return ObjectMapperHolder._objectMapper.writeValueAsString(
			identityContextMessage);
	}

	@Override
	public IdentityContextMessage map(String jsonString) throws IOException {
		return ObjectMapperHolder._objectMapper.readValue(
			jsonString, IdentityContextMessage.class);
	}

	private static final class IdentityContextMessageMixIn {

		@JsonProperty("browserPluginDetails")
		private String _browserPluginDetails;

		@JsonProperty("canvasFingerPrint")
		private String _canvasFingerPrint;

		@JsonProperty("cookiesEnabled")
		private boolean _cookiesEnabled;

		@JsonIgnore
		private String _dataSourceId;

		@JsonProperty("dataSourceIdentifier")
		private String _dataSourceIdentifier;

		@JsonProperty("dataSourceIndividualIdentifier")
		private String _dataSourceIndividualIdentifier;

		@JsonProperty("domain")
		private String _domain;

		@JsonProperty("httpAcceptHeaders")
		private String _httpAcceptHeaders;

		@JsonProperty("identity")
		private Map<String, String> _identityFields;

		@JsonProperty("language")
		private String _language;

		@JsonProperty("platform")
		private String _platform;

		@JsonProperty("protocolVersion")
		private String _protocolVersion;

		@JsonProperty("screenSizeAndColorDepth")
		private String _screenSizeAndColorDepth;

		@JsonProperty("systemFonts")
		private String _systemFonts;

		@JsonProperty("timezone")
		private String _timezone;

		@JsonProperty("touchSupport")
		private boolean _touchSupport;

		@JsonProperty("userAgent")
		private String _userAgent;

		@JsonProperty("userId")
		private String _userId;

		@JsonProperty("webGLFingerPrint")
		private String _webGLFingerPrint;

	}

	private static class ObjectMapperHolder {

		private static final ObjectMapper _objectMapper = new ObjectMapper() {
			{
				addMixIn(
					IdentityContextMessage.class,
					IdentityContextMessageMixIn.class);
				configure(
					DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			}
		};

	}

}