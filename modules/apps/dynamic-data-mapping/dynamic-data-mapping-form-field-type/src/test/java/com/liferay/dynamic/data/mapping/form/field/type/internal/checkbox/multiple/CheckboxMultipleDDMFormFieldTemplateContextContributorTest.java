/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.checkbox.multiple;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Carolina Barbosa
 */
@RunWith(PowerMockRunner.class)
public class CheckboxMultipleDDMFormFieldTemplateContextContributorTest {

	@Before
	public void setUp() throws Exception {
		_setUpJSONFactory();
	}

	@Test
	public void testGetPredefinedValue() {
		Map<String, Object> parameters =
			_checkboxMultipleDDMFormFieldTemplateContextContributor.
				getParameters(
					_createDDMFormField(),
					_createDDMFormFieldRenderingContext(false));

		Assert.assertEquals(
			new ArrayList<String>() {
				{
					add("Option1");
					add("Option2");
				}
			},
			parameters.get("predefinedValue"));
	}

	@Test
	public void testGetPredefinedValueInViewMode() {
		Map<String, Object> parameters =
			_checkboxMultipleDDMFormFieldTemplateContextContributor.
				getParameters(
					_createDDMFormField(),
					_createDDMFormFieldRenderingContext(true));

		Assert.assertEquals(
			new ArrayList<String>() {
				{
					add("Option1");
					add("Option2");
				}
			},
			parameters.get("predefinedValue"));
	}

	private DDMFormField _createDDMFormField() {
		DDMFormField ddmFormField = new DDMFormField(
			"field", "checkbox_multiple");

		ddmFormField.setProperty(
			"predefinedValue",
			DDMFormValuesTestUtil.createLocalizedValue(
				"[\"Option1\", \"Option2\"]", LocaleUtil.US));

		return ddmFormField;
	}

	private DDMFormFieldRenderingContext _createDDMFormFieldRenderingContext(
		boolean viewMode) {

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setLocale(LocaleUtil.US);
		ddmFormFieldRenderingContext.setViewMode(viewMode);

		return ddmFormFieldRenderingContext;
	}

	private void _setUpJSONFactory() throws Exception {
		PowerMockito.field(
			CheckboxMultipleDDMFormFieldTemplateContextContributor.class,
			"jsonFactory"
		).set(
			_checkboxMultipleDDMFormFieldTemplateContextContributor,
			new JSONFactoryImpl()
		);
	}

	private final CheckboxMultipleDDMFormFieldTemplateContextContributor
		_checkboxMultipleDDMFormFieldTemplateContextContributor =
			new CheckboxMultipleDDMFormFieldTemplateContextContributor();

}