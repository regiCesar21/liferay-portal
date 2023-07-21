/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.localizable.text;

import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Gabriel Ibson
 */
@RunWith(PowerMockRunner.class)
public class LocalizableTextDDMFormFieldValueAccessorTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		_setUpLocalizableTextDDMFormFieldValueAccessor();
	}

	@Test
	public void testEmpty() {
		Assert.assertTrue(
			_localizableTextDDMFormFieldValueAccessor.isEmpty(
				DDMFormValuesTestUtil.createDDMFormFieldValue(
					"localizableText", new UnlocalizedValue("{}")),
				LocaleUtil.US));
	}

	@Test
	public void testMalformedJson() {
		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"localizableText", new UnlocalizedValue("{"));

		JSONObject valueJSONObject =
			_localizableTextDDMFormFieldValueAccessor.getValue(
				ddmFormFieldValue, LocaleUtil.US);

		Assert.assertTrue(valueJSONObject.length() == 0);
	}

	@Test
	public void testNotEmpty() {
		StringBundler sb = new StringBundler(2);

		sb.append("{\"title\":\"Welcome to Liferay Forms!\",");
		sb.append("\"type\":\"document\"}");

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"localizableText", new UnlocalizedValue(sb.toString()));

		Assert.assertFalse(
			_localizableTextDDMFormFieldValueAccessor.isEmpty(
				ddmFormFieldValue, LocaleUtil.US));
	}

	private void _setUpLocalizableTextDDMFormFieldValueAccessor()
		throws Exception {

		_localizableTextDDMFormFieldValueAccessor =
			new LocalizableTextDDMFormFieldValueAccessor();

		field(
			LocalizableTextDDMFormFieldValueAccessor.class, "jsonFactory"
		).set(
			_localizableTextDDMFormFieldValueAccessor, _jsonFactory
		);
	}

	private final JSONFactory _jsonFactory = new JSONFactoryImpl();
	private LocalizableTextDDMFormFieldValueAccessor
		_localizableTextDDMFormFieldValueAccessor;

}