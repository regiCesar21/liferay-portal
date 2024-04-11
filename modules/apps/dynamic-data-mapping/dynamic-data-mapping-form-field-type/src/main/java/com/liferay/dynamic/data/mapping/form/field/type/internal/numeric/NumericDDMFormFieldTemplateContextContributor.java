/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.numeric;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.internal.util.DDMFormFieldTypeUtil;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=numeric",
	service = {
		DDMFormFieldTemplateContextContributor.class,
		NumericDDMFormFieldTemplateContextContributor.class
	}
)
public class NumericDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Locale locale = ddmFormFieldRenderingContext.getLocale();

		return HashMapBuilder.<String, Object>put(
			"dataType", getDataType(ddmFormField, ddmFormFieldRenderingContext)
		).put(
			"htmlAutocompleteAttribute",
			GetterUtil.getString(
				ddmFormField.getProperty("htmlAutocompleteAttribute"))
		).put(
			"placeholder",
			getValueString(
				(LocalizedValue)ddmFormField.getProperty("placeholder"), locale,
				ddmFormFieldRenderingContext)
		).put(
			"predefinedValue",
			getFormattedValue(
				ddmFormFieldRenderingContext, locale,
				DDMFormFieldTypeUtil.getPropertyValue(
					ddmFormField, ddmFormFieldRenderingContext.getLocale(),
					"predefinedValue"))
		).put(
			"symbols", getSymbolsMap(locale)
		).put(
			"tooltip",
			getValueString(
				(LocalizedValue)ddmFormField.getProperty("tooltip"), locale,
				ddmFormFieldRenderingContext)
		).put(
			"value",
			() -> {
				String value = HtmlUtil.extractText(
					ddmFormFieldRenderingContext.getValue());

				if (Objects.equals(value, "NaN")) {
					return StringPool.BLANK;
				}

				return getFormattedValue(
					ddmFormFieldRenderingContext, locale, value);
			}
		).build();
	}

	protected String getDataType(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Map<String, Object> changedProperties =
			(Map<String, Object>)ddmFormFieldRenderingContext.getProperty(
				"changedProperties");

		if (MapUtil.isNotEmpty(changedProperties)) {
			String dataType = (String)changedProperties.get("dataType");

			if (dataType != null) {
				return dataType;
			}
		}

		return ddmFormField.getDataType();
	}

	protected String getFormattedValue(
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext,
		Locale locale, String value) {

		if (Validator.isNull(value)) {
			return StringPool.BLANK;
		}

		if (GetterUtil.getBoolean(
				ddmFormFieldRenderingContext.getProperty("valueChanged"))) {

			DecimalFormat numberFormat =
				NumericDDMFormFieldUtil.getNumberFormat(locale);

			return numberFormat.format(GetterUtil.getNumber(value));
		}

		return value;
	}

	protected Map<String, String> getSymbolsMap(Locale locale) {
		DecimalFormat formatter = NumericDDMFormFieldUtil.getNumberFormat(
			locale);

		DecimalFormatSymbols decimalFormatSymbols =
			formatter.getDecimalFormatSymbols();

		return HashMapBuilder.put(
			"decimalSymbol",
			String.valueOf(decimalFormatSymbols.getDecimalSeparator())
		).put(
			"thousandsSeparator",
			String.valueOf(decimalFormatSymbols.getGroupingSeparator())
		).build();
	}

	protected String getValueString(
		Value value, Locale locale,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		if (value == null) {
			return StringPool.BLANK;
		}

		return value.getString(locale);
	}

}