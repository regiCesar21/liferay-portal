/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.util;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesMerger;
import com.liferay.portal.kernel.util.GetterUtil;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = DDMFormValuesMerger.class)
public class DDMFormValuesMergerImpl implements DDMFormValuesMerger {

	@Override
	public DDMFormValues merge(
		DDMForm ddmForm, DDMFormValues newDDMFormValues,
		DDMFormValues existingDDMFormValues) {

		List<DDMFormFieldValue> newDDMFormFieldValues = new ArrayList<>(
			newDDMFormValues.getDDMFormFieldValues());

		for (DDMFormFieldValue ddmFormFieldValue :
				newDDMFormValues.getDDMFormFieldValues()) {

			newDDMFormFieldValues.addAll(
				ddmFormFieldValue.getNestedDDMFormFieldValues());
		}

		List<DDMFormFieldValue> mergedDDMFormFieldValues =
			mergeDDMFormFieldValues(
				ddmForm, newDDMFormFieldValues,
				existingDDMFormValues.getDDMFormFieldValues());

		existingDDMFormValues.setDDMFormFieldValues(mergedDDMFormFieldValues);

		return existingDDMFormValues;
	}

	@Override
	public DDMFormValues merge(
		DDMFormValues newDDMFormValues, DDMFormValues existingDDMFormValues) {

		return merge(null, newDDMFormValues, existingDDMFormValues);
	}

	protected DDMFormFieldValue getDDMFormFieldValueByName(
		List<DDMFormFieldValue> ddmFormFieldValues, String name) {

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			if (name.equals(ddmFormFieldValue.getName())) {
				return ddmFormFieldValue;
			}
		}

		return null;
	}

	protected List<DDMFormFieldValue> mergeDDMFormFieldValues(
		DDMForm ddmForm, List<DDMFormFieldValue> newDDMFormFieldValues,
		List<DDMFormFieldValue> existingDDMFormFieldValues) {

		List<DDMFormFieldValue> mergedDDMFormFieldValues = new ArrayList<>(
			existingDDMFormFieldValues);

		for (DDMFormFieldValue newDDMFormFieldValue : newDDMFormFieldValues) {
			DDMFormValues ddmFormValues =
				newDDMFormFieldValue.getDDMFormValues();

			DDMFormFieldValue actualDDMFormFieldValue =
				getDDMFormFieldValueByName(
					existingDDMFormFieldValues, newDDMFormFieldValue.getName());

			if (actualDDMFormFieldValue != null) {
				if (ddmForm == null) {
					ddmForm = ddmFormValues.getDDMForm();
				}

				Map<String, DDMFormField> ddmFormFieldsMap =
					ddmForm.getDDMFormFieldsMap(true);

				Collection<DDMFormField> ddmFormFields =
					ddmFormFieldsMap.values();

				Stream<DDMFormField> stream = ddmFormFields.stream();

				DDMFormField ddmFormField = stream.filter(
					p -> Objects.equals(
						p.getName(), newDDMFormFieldValue.getName())
				).findFirst(
				).orElseGet(
					() -> null
				);

				mergeValue(
					newDDMFormFieldValue.getValue(),
					actualDDMFormFieldValue.getValue(), ddmFormField);

				List<DDMFormFieldValue> mergedNestedDDMFormFieldValues =
					mergeDDMFormFieldValues(
						null,
						newDDMFormFieldValue.getNestedDDMFormFieldValues(),
						actualDDMFormFieldValue.getNestedDDMFormFieldValues());

				newDDMFormFieldValue.setNestedDDMFormFields(
					mergedNestedDDMFormFieldValues);

				existingDDMFormFieldValues.remove(actualDDMFormFieldValue);
				mergedDDMFormFieldValues.remove(actualDDMFormFieldValue);
			}

			mergedDDMFormFieldValues.add(newDDMFormFieldValue);
		}

		return mergedDDMFormFieldValues;
	}

	protected void mergeValue(
		Value newValue, Value existingValue, DDMFormField ddmFormField) {

		if ((newValue == null) || (existingValue == null)) {
			return;
		}

		for (Locale locale : existingValue.getAvailableLocales()) {
			String value = newValue.getString(locale);

			String dataType = ddmFormField.getDataType();

			if (dataType.equals("double")) {
				DecimalFormat numberFormat = _getDecimalFormat(locale);

				newValue.addString(
					locale,
					numberFormat.format(
						GetterUtil.getDouble(
							value, newValue.getDefaultLocale())));
			}

			if (value == null) {
				newValue.addString(locale, existingValue.getString(locale));
			}
		}
	}

	private DecimalFormat _getDecimalFormat(Locale locale) {
		DecimalFormat formatter = _decimalFormattersMap.get(locale);

		if (formatter == null) {
			formatter = (DecimalFormat)DecimalFormat.getInstance(locale);

			formatter.setGroupingUsed(false);
			formatter.setMaximumFractionDigits(Integer.MAX_VALUE);
			formatter.setParseBigDecimal(true);

			_decimalFormattersMap.put(locale, formatter);
		}

		return formatter;
	}

	private static final Map<Locale, DecimalFormat> _decimalFormattersMap =
		new ConcurrentHashMap<>();

}