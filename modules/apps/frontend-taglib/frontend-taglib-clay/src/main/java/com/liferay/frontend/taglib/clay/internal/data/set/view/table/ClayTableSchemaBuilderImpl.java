/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.internal.data.set.view.table;

import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchema;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilder;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaField;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Marco Leo
 */
public class ClayTableSchemaBuilderImpl implements ClayTableSchemaBuilder {

	public ClayTableSchemaBuilderImpl() {
		_clayTableSchema = new ClayTableSchema();
		_clayTableSchemaFieldsMap = new LinkedHashMap<>();
	}

	@Override
	public void addClayTableSchemaField(
		ClayTableSchemaField clayTableSchemaField) {

		_clayTableSchemaFieldsMap.put(
			clayTableSchemaField.getFieldName(), clayTableSchemaField);
	}

	@Override
	public ClayTableSchemaField addClayTableSchemaField(String fieldName) {
		ClayTableSchemaField clayTableSchemaField = new ClayTableSchemaField();

		clayTableSchemaField.setFieldName(fieldName);

		_clayTableSchemaFieldsMap.put(fieldName, clayTableSchemaField);

		return clayTableSchemaField;
	}

	@Override
	public ClayTableSchemaField addClayTableSchemaField(
		String fieldName, String label) {

		ClayTableSchemaField clayTableSchemaField = addClayTableSchemaField(
			fieldName);

		clayTableSchemaField.setLabel(label);

		return clayTableSchemaField;
	}

	@Override
	public ClayTableSchema build() {
		_clayTableSchema.setClayTableSchemaFieldsMap(_clayTableSchemaFieldsMap);

		return _clayTableSchema;
	}

	@Override
	public void removeClayTableSchemaField(String fieldName) {
		_clayTableSchemaFieldsMap.remove(fieldName);
	}

	@Override
	public void setClayTableSchema(ClayTableSchema clayTableSchema) {
		_clayTableSchema = clayTableSchema;
	}

	private ClayTableSchema _clayTableSchema;
	private final Map<String, ClayTableSchemaField> _clayTableSchemaFieldsMap;

}