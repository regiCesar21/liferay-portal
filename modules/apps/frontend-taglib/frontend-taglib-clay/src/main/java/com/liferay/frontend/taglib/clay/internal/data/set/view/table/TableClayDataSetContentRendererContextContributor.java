/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.internal.data.set.view.table;

import com.liferay.frontend.taglib.clay.data.set.ClayDataSetContentRendererContextContributor;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.constants.ClayDataSetConstants;
import com.liferay.frontend.taglib.clay.data.set.view.table.BaseTableClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchema;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaField;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "clay.data.set.content.renderer.name=" + ClayDataSetConstants.TABLE,
	service = ClayDataSetContentRendererContextContributor.class
)
public class TableClayDataSetContentRendererContextContributor
	implements ClayDataSetContentRendererContextContributor {

	@Override
	public Map<String, Object> getContentRendererContext(
		ClayDataSetDisplayView clayDataSetDisplayView, Locale locale) {

		if (clayDataSetDisplayView instanceof BaseTableClayDataSetDisplayView) {
			return _serialize(
				(BaseTableClayDataSetDisplayView)clayDataSetDisplayView,
				locale);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(
		BaseTableClayDataSetDisplayView baseTableClayDataSetDisplayView,
		Locale locale) {

		JSONArray fieldsJSONArray = _jsonFactory.createJSONArray();

		ClayTableSchema clayTableSchema =
			baseTableClayDataSetDisplayView.getClayTableSchema(locale);

		Map<String, ClayTableSchemaField> fieldsMap =
			clayTableSchema.getClayTableSchemaFieldsMap();

		ResourceBundle resourceBundle =
			baseTableClayDataSetDisplayView.getResourceBundle(locale);

		for (ClayTableSchemaField clayTableSchemaField : fieldsMap.values()) {
			String label = LanguageUtil.get(
				resourceBundle, clayTableSchemaField.getLabel());

			if (Validator.isNull(label)) {
				label = StringPool.BLANK;
			}

			JSONObject jsonObject = JSONUtil.put(
				"actionId", clayTableSchemaField.getActionId()
			).put(
				"contentRenderer", clayTableSchemaField.getContentRenderer()
			).put(
				"contentRendererModuleURL",
				clayTableSchemaField.getContentRendererModuleURL()
			).put(
				"expand", clayTableSchemaField.isExpand()
			).put(
				"label", label
			).put(
				"sortable", clayTableSchemaField.isSortable()
			);

			String fieldName = clayTableSchemaField.getFieldName();

			if (fieldName.contains(StringPool.PERIOD)) {
				jsonObject.put(
					"fieldName",
					StringUtil.split(fieldName, StringPool.PERIOD));
			}
			else {
				jsonObject.put("fieldName", fieldName);
			}

			ClayTableSchemaField.SortingOrder sortingOrder =
				clayTableSchemaField.getSortingOrder();

			if (sortingOrder != null) {
				jsonObject.put(
					"sortingOrder",
					StringUtil.toLowerCase(sortingOrder.toString()));
			}

			fieldsJSONArray.put(jsonObject);
		}

		return HashMapBuilder.<String, Object>put(
			"schema", JSONUtil.put("fields", fieldsJSONArray)
		).build();
	}

	@Reference
	private JSONFactory _jsonFactory;

}