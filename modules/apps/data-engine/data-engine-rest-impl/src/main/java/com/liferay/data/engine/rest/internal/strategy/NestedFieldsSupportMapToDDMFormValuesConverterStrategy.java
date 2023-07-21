/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.rest.internal.strategy;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;
import java.util.Map;

/**
 * @author Rafael Praxedes
 */
public class NestedFieldsSupportMapToDDMFormValuesConverterStrategy
	implements MapToDDMFormValuesConverterStrategy {

	public static NestedFieldsSupportMapToDDMFormValuesConverterStrategy
		getInstance() {

		return _nestedFieldsSupportMapToDDMFormValuesConverterStrategy;
	}

	@Override
	public void setDDMFormFieldValues(
		Map<String, Object> dataRecordValues, DDMForm ddmForm,
		DDMFormValues ddmFormValues, Locale locale) {

		Map<String, DDMFormField> ddmFormFields = ddmForm.getDDMFormFieldsMap(
			true);

		for (Map.Entry<String, Object> entry : dataRecordValues.entrySet()) {
			String[] parts = StringUtil.split(entry.getKey(), "_INSTANCE_");

			ddmFormValues.addDDMFormFieldValue(
				createDDMFormFieldValue(
					ddmFormFields.get(parts[0]), ddmFormFields,
					(Map<String, Object>)entry.getValue(), parts[1], locale));
		}
	}

	protected DDMFormFieldValue createDDMFormFieldValue(
		DDMFormField ddmFormField, Map<String, DDMFormField> ddmFormFields,
		Map<String, Object> fieldInstanceValue, String instanceId,
		Locale locale) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue() {
			{
				setInstanceId(instanceId);
				setName(ddmFormField.getName());
			}
		};

		if (!StringUtil.equals(ddmFormField.getType(), "fieldset")) {
			ddmFormFieldValue.setValue(
				createValue(
					ddmFormField, locale, fieldInstanceValue.get("value")));
		}

		if (ListUtil.isNotEmpty(ddmFormField.getNestedDDMFormFields())) {
			Map<String, Object> nestedValues =
				(Map<String, Object>)fieldInstanceValue.get("nestedValues");

			if (MapUtil.isEmpty(nestedValues)) {
				return ddmFormFieldValue;
			}

			for (Map.Entry<String, Object> entry : nestedValues.entrySet()) {
				String[] parts = StringUtil.split(entry.getKey(), "_INSTANCE_");

				ddmFormFieldValue.addNestedDDMFormFieldValue(
					createDDMFormFieldValue(
						ddmFormFields.get(parts[0]), ddmFormFields,
						(Map<String, Object>)entry.getValue(), parts[1],
						locale));
			}
		}

		return ddmFormFieldValue;
	}

	private NestedFieldsSupportMapToDDMFormValuesConverterStrategy() {
	}

	private static final NestedFieldsSupportMapToDDMFormValuesConverterStrategy
		_nestedFieldsSupportMapToDDMFormValuesConverterStrategy =
			new NestedFieldsSupportMapToDDMFormValuesConverterStrategy();

}