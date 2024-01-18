/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.data.provider.internal;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderContext;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderException;
import com.liferay.dynamic.data.mapping.data.provider.configuration.DDMDataProviderConfiguration;
import com.liferay.petra.json.web.service.client.JSONWebServiceClient;
import com.liferay.petra.json.web.service.client.JSONWebServiceClientFactory;
import com.liferay.petra.json.web.service.client.JSONWebServiceException;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import java.net.URI;

import java.nio.charset.StandardCharsets;

import java.security.KeyStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.BOMInputStream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	configurationPid = "com.liferay.dynamic.data.mapping.data.provider.configuration.DDMDataProviderConfiguration",
	immediate = true, property = "ddm.data.provider.type=rest"
)
public class DDMRESTDataProvider implements DDMDataProvider {

	@Override
	public List<KeyValuePair> getData(
			DDMDataProviderContext ddmDataProviderContext)
		throws DDMDataProviderException {

		try {
			try {
				return _getData(ddmDataProviderContext);
			}
			catch (JSONWebServiceException jsonWebServiceException) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						jsonWebServiceException, jsonWebServiceException);
				}
				else if (_log.isWarnEnabled()) {
					_log.warn(
						"The data provider was not able to connect to the " +
							"web service. " + jsonWebServiceException);
				}
			}

			return Collections.emptyList();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			throw new DDMDataProviderException(exception);
		}
	}

	@Override
	public Class<?> getSettings() {
		return DDMRESTDataProviderSettings.class;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ddmDataProviderConfiguration = ConfigurableUtil.createConfigurable(
			DDMDataProviderConfiguration.class, properties);
	}

	private List<KeyValuePair> _getData(
			DDMDataProviderContext ddmDataProviderContext)
		throws Exception {

		DDMRESTDataProviderSettings ddmRESTDataProviderSettings =
			ddmDataProviderContext.getSettingsInstance(
				DDMRESTDataProviderSettings.class);

		String url = ddmRESTDataProviderSettings.url();

		DDMRESTDataProviderResult ddmRESTDataProviderResult = _portalCache.get(
			url);

		if ((ddmRESTDataProviderResult != null) &&
			ddmRESTDataProviderSettings.cacheable()) {

			return ddmRESTDataProviderResult.getKeyValuePairs();
		}

		URI uri = new URI(url);

		JSONWebServiceClient jsonWebServiceClient =
			_jsonWebServiceClientFactory.getInstance(
				HashMapBuilder.<String, Object>put(
					"hostName",
					() -> {
						String host = uri.getHost();

						if (StringUtil.startsWith(host, "www.")) {
							return host.substring(4);
						}

						return host;
					}
				).put(
					"hostPort",
					() -> {
						int port = uri.getPort();

						if (port != -1) {
							return port;
						}

						if (StringUtil.equals(uri.getScheme(), Http.HTTPS)) {
							return Http.HTTPS_PORT;
						}

						return Http.HTTP_PORT;
					}
				).put(
					"keyStore",
					() -> {
						KeyStore keyStore = KeyStore.getInstance(
							KeyStore.getDefaultType());

						keyStore.load(null);

						return keyStore;
					}
				).put(
					"login", ddmRESTDataProviderSettings.username()
				).put(
					"password", ddmRESTDataProviderSettings.password()
				).put(
					"protocol", uri.getScheme()
				).put(
					"trustSelfSignedCertificates",
					_ddmDataProviderConfiguration.trustSelfSignedCertificates()
				).putAll(
					_getProxySettingsMap()
				).build(),
				false);

		String response = jsonWebServiceClient.doGet(url);

		JSONArray jsonArray = _jsonFactory.createJSONArray(
			IOUtils.toString(
				new BOMInputStream(
					new ByteArrayInputStream(response.getBytes())),
				StandardCharsets.UTF_8));

		List<KeyValuePair> results = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			results.add(
				new KeyValuePair(
					jsonObject.getString(ddmRESTDataProviderSettings.key()),
					jsonObject.getString(ddmRESTDataProviderSettings.value())));
		}

		if (ddmRESTDataProviderSettings.cacheable()) {
			_portalCache.put(url, new DDMRESTDataProviderResult(results));
		}

		return results;
	}

	private Map<String, Object> _getProxySettingsMap() {
		Map<String, Object> proxySettingsMap = new HashMap<>();

		try {
			String proxyHost = SystemProperties.get("http.proxyHost");
			String proxyPort = SystemProperties.get("http.proxyPort");

			if (Validator.isNotNull(proxyHost) &&
				Validator.isNotNull(proxyPort)) {

				proxySettingsMap.put("proxyHostName", proxyHost);
				proxySettingsMap.put(
					"proxyHostPort", GetterUtil.getInteger(proxyPort));
			}
		}
		catch (Exception exception) {
			proxySettingsMap.clear();

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get proxy settings from system properties",
					exception);
			}
		}

		return proxySettingsMap;
	}

	@Reference(unbind = "-")
	private void _setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	@Reference(unbind = "-")
	private void _setMultiVMPool(MultiVMPool multiVMPool) {
		_portalCache =
			(PortalCache<String, DDMRESTDataProviderResult>)
				multiVMPool.getPortalCache(DDMRESTDataProvider.class.getName());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMRESTDataProvider.class);

	private volatile DDMDataProviderConfiguration _ddmDataProviderConfiguration;
	private JSONFactory _jsonFactory;

	@Reference
	private JSONWebServiceClientFactory _jsonWebServiceClientFactory;

	private PortalCache<String, DDMRESTDataProviderResult> _portalCache;

	private static class DDMRESTDataProviderResult implements Serializable {

		public DDMRESTDataProviderResult(List<KeyValuePair> keyValuePairs) {
			_keyValuePairs = keyValuePairs;
		}

		public List<KeyValuePair> getKeyValuePairs() {
			return _keyValuePairs;
		}

		private final List<KeyValuePair> _keyValuePairs;

	}

}