/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.internal.data.set.filter;

import com.liferay.frontend.taglib.clay.data.set.filter.BaseAutocompleteClayDataSetFilter;
import com.liferay.frontend.taglib.clay.data.set.filter.ClayDataSetFilter;
import com.liferay.frontend.taglib.clay.data.set.filter.ClayDataSetFilterContextContributor;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	property = "clay.data.set.filter.type=autocomplete",
	service = ClayDataSetFilterContextContributor.class
)
public class AutocompleteClayDataSetFilterContextContributor
	implements ClayDataSetFilterContextContributor {

	@Override
	public Map<String, Object> getClayDataSetFilterContext(
		ClayDataSetFilter clayDataSetFilter, Locale locale) {

		if (clayDataSetFilter instanceof BaseAutocompleteClayDataSetFilter) {
			return _serialize(
				(BaseAutocompleteClayDataSetFilter)clayDataSetFilter);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(
		BaseAutocompleteClayDataSetFilter baseAutocompleteClayDataSetFilter) {

		return HashMapBuilder.<String, Object>put(
			"apiURL", baseAutocompleteClayDataSetFilter.getAPIURL()
		).put(
			"inputPlaceholder",
			baseAutocompleteClayDataSetFilter.getPlaceholder()
		).put(
			"itemKey", baseAutocompleteClayDataSetFilter.getItemKey()
		).put(
			"itemLabel", baseAutocompleteClayDataSetFilter.getItemLabel()
		).put(
			"selectionType",
			() -> {
				if (baseAutocompleteClayDataSetFilter.isMultipleSelection()) {
					return "multiple";
				}

				return "single";
			}
		).build();
	}

}