/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.field.type.util;

import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Leonardo Barros
 */
public class LocalizedValueUtil {

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static Object getLocalizedValue(
		Locale locale, Map<String, Object> localizedValues) {

		if (MapUtil.isEmpty(localizedValues)) {
			return null;
		}

		return localizedValues.get(LocaleUtil.toLanguageId(locale));
	}

	public static <V> JSONObject toJSONObject(Map<String, V> map) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (MapUtil.isEmpty(map)) {
			return jsonObject;
		}

		for (Map.Entry<String, V> entry : map.entrySet()) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}

		return jsonObject;
	}

	public static Map<Locale, String> toLocaleStringMap(
		Map<String, Object> localizedValues) {

		if (MapUtil.isEmpty(localizedValues)) {
			return Collections.emptyMap();
		}

		Map<Locale, String> localeStringMap = new HashMap<>();

		for (Map.Entry<String, Object> entry : localizedValues.entrySet()) {
			localeStringMap.put(
				LocaleUtil.fromLanguageId(entry.getKey()),
				(String)entry.getValue());
		}

		return localeStringMap;
	}

	public static LocalizedValue toLocalizedValue(
		Map<String, Object> localizedValues) {

		if (localizedValues == null) {
			return null;
		}

		LocalizedValue localizedValue = new LocalizedValue();

		for (Map.Entry<String, Object> entry : localizedValues.entrySet()) {
			localizedValue.addString(
				LocaleUtil.fromLanguageId(entry.getKey()),
				GetterUtil.getString(entry.getValue()));
		}

		return localizedValue;
	}

	public static LocalizedValue toLocalizedValue(
		Map<String, Object> localizedValues, Locale locale) {

		if (localizedValues == null) {
			return null;
		}

		LocalizedValue localizedValue = new LocalizedValue();

		for (Map.Entry<String, Object> entry : localizedValues.entrySet()) {
			localizedValue.addString(
				LocaleUtil.fromLanguageId(entry.getKey()),
				GetterUtil.getString(entry.getValue()));

			localizedValue.setDefaultLocale(locale);
		}

		return localizedValue;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static <V> Map<String, V> toLocalizedValues(JSONObject jsonObject) {
		if (jsonObject == null) {
			return Collections.emptyMap();
		}

		Map<String, V> localizedValues = new HashMap<>();

		Iterator<String> iterator = jsonObject.keys();

		while (iterator.hasNext()) {
			String key = iterator.next();

			localizedValues.put(key, (V)jsonObject.get(key));
		}

		return localizedValues;
	}

	public static Map<String, Object> toLocalizedValuesMap(
		LocalizedValue localizedValue) {

		if (localizedValue == null) {
			return Collections.emptyMap();
		}

		Map<Locale, String> values = localizedValue.getValues();

		Set<Map.Entry<Locale, String>> entrySet = values.entrySet();

		Stream<Map.Entry<Locale, String>> stream = entrySet.stream();

		return stream.collect(
			Collectors.toMap(
				entry -> LanguageUtil.getLanguageId(entry.getKey()),
				entry -> {
					String value = entry.getValue();

					if (Validator.isNotNull(value)) {
						try {
							return JSONFactoryUtil.createJSONArray(value);
						}
						catch (JSONException jsonException) {
							if (_log.isDebugEnabled()) {
								_log.debug(jsonException, jsonException);
							}
						}
					}

					return value;
				}));
	}

	public static Map<String, Object> toStringObjectMap(
		Map<Locale, String> localizedValues) {

		Map<String, Object> stringObjectMap = new HashMap<>();

		for (Map.Entry<Locale, String> entry : localizedValues.entrySet()) {
			stringObjectMap.put(
				String.valueOf(entry.getKey()), entry.getValue());
		}

		return stringObjectMap;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LocalizedValueUtil.class);

}