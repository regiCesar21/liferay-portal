/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.portlet.tab;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Rafael Praxedes
 */
public class AppBuilderAppPortletTabContext {

	public AppBuilderAppPortletTabContext addDataLayoutProperties(
		long dataLayoutId, Map<String, Object> properties) {

		_propertiesMap.put(dataLayoutId, properties);

		return this;
	}

	public List<Long> getDataLayoutIds() {
		return new ArrayList<>(_propertiesMap.keySet());
	}

	public String getName(long dataLayoutId, Locale locale) {
		Map<String, Object> properties = _propertiesMap.get(dataLayoutId);

		Map<Locale, String> nameMap =
			(Map<Locale, String>)properties.getOrDefault(
				"nameMap", Collections.emptyMap());

		return nameMap.getOrDefault(locale, StringPool.BLANK);
	}

	public boolean isReadOnly(long dataLayoutId) {
		Map<String, Object> properties = _propertiesMap.get(dataLayoutId);

		return MapUtil.getBoolean(properties, "readOnly");
	}

	private final Map<Long, Map<String, Object>> _propertiesMap =
		new LinkedHashMap<>();

}