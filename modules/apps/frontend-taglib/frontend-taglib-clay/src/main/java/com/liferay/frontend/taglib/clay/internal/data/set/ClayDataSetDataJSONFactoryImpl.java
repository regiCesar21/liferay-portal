/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.internal.data.set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.liferay.frontend.taglib.clay.data.set.ClayDataSetActionProvider;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetActionProviderRegistry;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDataJSONFactory;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = ClayDataSetDataJSONFactory.class)
public class ClayDataSetDataJSONFactoryImpl
	implements ClayDataSetDataJSONFactory {

	@Override
	public String create(
			long groupId, String tableName, List<Object> items,
			HttpServletRequest httpServletRequest)
		throws Exception {

		List<ClayDataSetDataRow> clayDataSetDataRows = _getClayTableRows(
			items, tableName, httpServletRequest, groupId);

		return _objectMapper.writeValueAsString(clayDataSetDataRows);
	}

	@Override
	public String create(
			long groupId, String tableName, List<Object> items, int itemsCount,
			HttpServletRequest httpServletRequest)
		throws Exception {

		ClayDataSetResponse clayDataSetResponse = new ClayDataSetResponse(
			_getClayTableRows(items, tableName, httpServletRequest, groupId),
			itemsCount);

		return _objectMapper.writeValueAsString(clayDataSetResponse);
	}

	private List<ClayDataSetDataRow> _getClayTableRows(
			List<Object> items, String tableName,
			HttpServletRequest httpServletRequest, long groupId)
		throws Exception {

		List<ClayDataSetDataRow> clayDataSetDataRows = new ArrayList<>();

		List<ClayDataSetActionProvider> clayDataSetActionProviders =
			_clayDataSetActionProviderRegistry.getClayDataSetActionProviders(
				tableName);

		for (Object item : items) {
			ClayDataSetDataRow clayDataSetDataRow = new ClayDataSetDataRow(
				item);

			if (clayDataSetActionProviders != null) {
				for (ClayDataSetActionProvider clayDataSetActionProvider :
						clayDataSetActionProviders) {

					List<DropdownItem> actionDropdownItems =
						clayDataSetActionProvider.getDropdownItems(
							httpServletRequest, groupId, item);

					if (actionDropdownItems != null) {
						clayDataSetDataRow.addActionDropdownItems(
							actionDropdownItems);
					}
				}
			}

			clayDataSetDataRows.add(clayDataSetDataRow);
		}

		return clayDataSetDataRows;
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
			disable(SerializationFeature.INDENT_OUTPUT);
		}
	};

	@Reference
	private ClayDataSetActionProviderRegistry
		_clayDataSetActionProviderRegistry;

	private class ClayDataSetResponse {

		public ClayDataSetResponse(
			List<ClayDataSetDataRow> clayDataSetRows, int totalCount) {

			_clayDataSetRows = clayDataSetRows;
			_totalCount = totalCount;
		}

		@JsonProperty("items")
		private final List<ClayDataSetDataRow> _clayDataSetRows;

		@JsonProperty("totalCount")
		private final int _totalCount;

	}

}