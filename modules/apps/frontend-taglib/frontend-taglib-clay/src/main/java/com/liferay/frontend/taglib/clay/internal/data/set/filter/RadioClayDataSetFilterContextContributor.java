/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.internal.data.set.filter;

import com.liferay.frontend.taglib.clay.data.set.filter.BaseRadioClayDataSetFilter;
import com.liferay.frontend.taglib.clay.data.set.filter.ClayDataSetFilter;
import com.liferay.frontend.taglib.clay.data.set.filter.ClayDataSetFilterContextContributor;
import com.liferay.frontend.taglib.clay.data.set.filter.RadioClayDataSetFilterItem;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "clay.data.set.filter.type=radio",
	service = ClayDataSetFilterContextContributor.class
)
public class RadioClayDataSetFilterContextContributor
	implements ClayDataSetFilterContextContributor {

	@Override
	public Map<String, Object> getClayDataSetFilterContext(
		ClayDataSetFilter clayDataSetFilter, Locale locale) {

		if (clayDataSetFilter instanceof BaseRadioClayDataSetFilter) {
			return _serialize(
				(BaseRadioClayDataSetFilter)clayDataSetFilter, locale);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(
		BaseRadioClayDataSetFilter baseRadioClayDataSetFilter, Locale locale) {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		List<RadioClayDataSetFilterItem> radioClayDataSetFilterItems =
			baseRadioClayDataSetFilter.getRadioClayDataSetFilterItems(locale);

		for (RadioClayDataSetFilterItem radioClayDataSetFilterItem :
				radioClayDataSetFilterItems) {

			jsonArray.put(
				JSONUtil.put(
					"label",
					LanguageUtil.get(
						resourceBundle, radioClayDataSetFilterItem.getLabel())
				).put(
					"value", radioClayDataSetFilterItem.getValue()
				));
		}

		return HashMapBuilder.<String, Object>put(
			"items", jsonArray
		).build();
	}

	@Reference
	private JSONFactory _jsonFactory;

}