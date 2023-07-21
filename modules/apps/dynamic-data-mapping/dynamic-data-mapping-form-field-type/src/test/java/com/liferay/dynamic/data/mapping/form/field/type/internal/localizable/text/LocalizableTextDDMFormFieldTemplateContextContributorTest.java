/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.localizable.text;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.language.LanguageImpl;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Gabriel Ibson
 */
@RunWith(PowerMockRunner.class)
public class LocalizableTextDDMFormFieldTemplateContextContributorTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_setUpJSONFactory();
		_setUpLanguage();
	}

	@Test
	public void testGetAvailableLocales() {
		Map<String, Object> parameters = _getParameters();

		JSONArray availableLocalesJSONArray = (JSONArray)parameters.get(
			"availableLocales");

		Assert.assertFalse(availableLocalesJSONArray.length() == 0);
	}

	@Test
	public void testGetNotDefinedPredefinedValue() {
		Map<String, Object> parameters = _getParameters();

		Assert.assertNull(parameters.get("predefinedValue"));
	}

	@Test
	public void testGetPredefinedValue() {
		String expectedString = StringUtil.randomString();

		LocalizedValue predefinedValue = new LocalizedValue(LocaleUtil.US);

		predefinedValue.addString(LocaleUtil.US, expectedString);

		_ddmFormField.setProperty("predefinedValue", predefinedValue);

		Map<String, Object> parameters = _getParameters();

		String actualPredefinedValue = (String)parameters.get(
			"predefinedValue");

		Assert.assertEquals(expectedString, actualPredefinedValue);
	}

	private DDMForm _getDDMForm() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setDefaultLocale(LocaleUtil.US);

		return ddmForm;
	}

	private Map<String, Object> _getParameters() {
		_ddmFormField.setDDMForm(_getDDMForm());

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setLocale(LocaleUtil.US);

		return _localizableTextDDMFormFieldTemplateContextContributor.
			getParameters(_ddmFormField, ddmFormFieldRenderingContext);
	}

	private void _setUpJSONFactory() throws Exception {
		MemberMatcher.field(
			LocalizableTextDDMFormFieldTemplateContextContributor.class,
			"jsonFactory"
		).set(
			_localizableTextDDMFormFieldTemplateContextContributor, _jsonFactory
		);
	}

	private void _setUpLanguage() throws Exception {
		MemberMatcher.field(
			LocalizableTextDDMFormFieldTemplateContextContributor.class,
			"language"
		).set(
			_localizableTextDDMFormFieldTemplateContextContributor, _language
		);
	}

	private final DDMFormField _ddmFormField = new DDMFormField(
		"field", "localizableText");
	private final JSONFactory _jsonFactory = new JSONFactoryImpl();
	private final Language _language = new LanguageImpl();
	private final LocalizableTextDDMFormFieldTemplateContextContributor
		_localizableTextDDMFormFieldTemplateContextContributor =
			new LocalizableTextDDMFormFieldTemplateContextContributor();

}