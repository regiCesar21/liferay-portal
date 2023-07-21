/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.rest.internal.resource.v1_0.util;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class LocalizedValueUtil {

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

	public static Map<String, Object> toStringObjectMap(
		Map<Locale, String> localizedValues) {

		Map<String, Object> stringObjectMap = new HashMap<>();

		for (Map.Entry<Locale, String> entry : localizedValues.entrySet()) {
			stringObjectMap.put(
				String.valueOf(entry.getKey()), entry.getValue());
		}

		return stringObjectMap;
	}

}