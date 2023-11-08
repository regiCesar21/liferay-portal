/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.portlet.action;

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorFieldContextKey;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, service = AddFormInstanceRecordMVCCommandHelper.class
)
public class AddFormInstanceRecordMVCCommandHelper {

	public void updateNonevaluableDDMFormFields(
			Map<String, DDMFormField> ddmFormFieldsMap,
			Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
				ddmFormFieldsPropertyChanges,
			Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap,
			DDMFormLayout ddmFormLayout, Set<Integer> disabledPagesIndexes)
		throws Exception {

		Set<String> nonevaluableFieldNames = new HashSet<>();

		for (Map.Entry<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
				entry : ddmFormFieldsPropertyChanges.entrySet()) {

			if (!MapUtil.getBoolean(entry.getValue(), "readOnly") &&
				MapUtil.getBoolean(entry.getValue(), "visible", true)) {

				continue;
			}

			DDMFormEvaluatorFieldContextKey ddmFormEvaluatorFieldContextKey =
				entry.getKey();

			nonevaluableFieldNames.add(
				ddmFormEvaluatorFieldContextKey.getName());
		}

		for (Integer disabledPagesIndex : disabledPagesIndexes) {
			DDMFormLayoutPage ddmFormLayoutPage =
				ddmFormLayout.getDDMFormLayoutPage(disabledPagesIndex);

			for (DDMFormLayoutRow ddmFormLayoutRow :
					ddmFormLayoutPage.getDDMFormLayoutRows()) {

				for (DDMFormLayoutColumn ddmFormLayoutColumn :
						ddmFormLayoutRow.getDDMFormLayoutColumns()) {

					nonevaluableFieldNames.addAll(
						ddmFormLayoutColumn.getDDMFormFieldNames());
				}
			}
		}

		for (String nonevaluableFieldName : nonevaluableFieldNames) {
			DDMFormField ddmFormField = ddmFormFieldsMap.get(
				nonevaluableFieldName);

			if (ddmFormField == null) {
				continue;
			}

			ddmFormField.setDDMFormFieldValidation(null);
			ddmFormField.setRequired(false);

			for (DDMFormFieldValue ddmFormFieldValue :
					ddmFormFieldValuesMap.get(ddmFormField.getName())) {

				Value value = ddmFormFieldValue.getValue();

				if (value == null) {
					continue;
				}

				if (ddmFormField.isLocalizable()) {
					LocalizedValue localizedValue = new LocalizedValue(
						value.getDefaultLocale());

					for (Locale availableLocale : value.getAvailableLocales()) {
						localizedValue.addString(
							availableLocale, StringPool.BLANK);
					}

					ddmFormFieldValue.setValue(localizedValue);
				}
				else {
					ddmFormFieldValue.setValue(
						new UnlocalizedValue(StringPool.BLANK));
				}
			}
		}
	}

}