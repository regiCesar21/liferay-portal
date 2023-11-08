/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.portlet.action;

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorFieldContextKey;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Leonardo Barros
 */
@PrepareForTest(ResourceBundleUtil.class)
@RunWith(PowerMockRunner.class)
public class AddFormInstanceRecordMVCCommandHelperTest extends PowerMockito {

	@Test
	public void testDisabledField() throws Exception {
		_updateNonevaluableDDMFormFields(
			HashMapBuilder.<String, Object>put(
				"readOnly", true
			).build(),
			false, RandomTestUtil.randomBoolean(),
			new UnlocalizedValue(_STRING_VALUE));

		_assertDDMFormFields(false, new UnlocalizedValue(StringPool.BLANK));
	}

	@Test
	public void testEnabledField() throws Exception {
		boolean required = RandomTestUtil.randomBoolean();

		_updateNonevaluableDDMFormFields(
			HashMapBuilder.<String, Object>put(
				"readOnly", false
			).build(),
			false, required, new UnlocalizedValue(_STRING_VALUE));

		_assertDDMFormFields(required, new UnlocalizedValue(_STRING_VALUE));
	}

	@Test
	public void testInvisibleAndLocalizableField() throws Exception {
		_updateNonevaluableDDMFormFields(
			HashMapBuilder.<String, Object>put(
				"visible", false
			).build(),
			true, RandomTestUtil.randomBoolean(),
			DDMFormValuesTestUtil.createLocalizedValue(
				"Test", "Teste", LocaleUtil.US));

		Value value = _getFieldValue(_FIELD_NAME);

		Assert.assertEquals(
			StringPool.BLANK, value.getString(LocaleUtil.BRAZIL));
		Assert.assertEquals(StringPool.BLANK, value.getString(LocaleUtil.US));

		value = _getFieldValue(_NESTED_FIELD_NAME);

		Assert.assertEquals(
			StringPool.BLANK, value.getString(LocaleUtil.BRAZIL));
		Assert.assertEquals(StringPool.BLANK, value.getString(LocaleUtil.US));
	}

	@Test
	public void testInvisibleField() throws Exception {
		_updateNonevaluableDDMFormFields(
			HashMapBuilder.<String, Object>put(
				"visible", false
			).build(),
			false, RandomTestUtil.randomBoolean(),
			new UnlocalizedValue(_STRING_VALUE));

		_assertDDMFormFields(false, new UnlocalizedValue(StringPool.BLANK));
	}

	@Test
	public void testInvisibleFieldWithNullValue() throws Exception {
		_updateNonevaluableDDMFormFields(
			HashMapBuilder.<String, Object>put(
				"visible", false
			).build(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomBoolean(),
			null);

		_assertDDMFormFields(false, null);
	}

	@Test
	public void testVisibleField() throws Exception {
		boolean required = RandomTestUtil.randomBoolean();

		_updateNonevaluableDDMFormFields(
			HashMapBuilder.<String, Object>put(
				"visible", true
			).build(),
			false, required, new UnlocalizedValue(_STRING_VALUE));

		_assertDDMFormFields(required, new UnlocalizedValue(_STRING_VALUE));
	}

	private void _assertDDMFormFields(
		boolean expectedRequired, Value expectedValue) {

		Assert.assertEquals(expectedRequired, _ddmFormField.isRequired());
		Assert.assertEquals(expectedValue, _getFieldValue(_FIELD_NAME));
		Assert.assertEquals(expectedValue, _getFieldValue(_NESTED_FIELD_NAME));
	}

	private void _createDDMFormFields(
		DDMForm ddmForm, boolean localizable, boolean required) {

		_ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			_FIELD_NAME, localizable, false, required);

		ddmForm.addDDMFormField(_ddmFormField);

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			RandomTestUtil.randomString(), null, null, null, false, false,
			false);

		ddmFormField.addNestedDDMFormField(
			DDMFormTestUtil.createTextDDMFormField(
				_NESTED_FIELD_NAME, localizable, false, required));

		ddmForm.addDDMFormField(ddmFormField);
	}

	private void _createDDMFormValues(DDMForm ddmForm, Value value) {
		_ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(ddmForm);

		_ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				_FIELD_INSTANCE_ID, _FIELD_NAME, value));

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				RandomTestUtil.randomString(), null);

		ddmFormFieldValue.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				_NESTED_FIELD_INSTANCE_ID, _NESTED_FIELD_NAME, value));

		_ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
	}

	private Value _getFieldValue(String fieldName) {
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			_ddmFormValues.getDDMFormFieldValuesMap(true);

		if (!ddmFormFieldValuesMap.containsKey(fieldName)) {
			return null;
		}

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			fieldName);

		DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

		return ddmFormFieldValue.getValue();
	}

	private void _updateNonevaluableDDMFormFields(
			Map<String, Object> fieldChangesProperties, boolean localizable,
			boolean required, Value value)
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		_createDDMFormFields(ddmForm, localizable, required);

		_createDDMFormValues(ddmForm, value);

		_addFormInstanceRecordMVCCommandHelper.updateNonevaluableDDMFormFields(
			ddmForm.getDDMFormFieldsMap(true),
			HashMapBuilder.put(
				new DDMFormEvaluatorFieldContextKey(
					_FIELD_NAME, _FIELD_INSTANCE_ID),
				fieldChangesProperties
			).put(
				new DDMFormEvaluatorFieldContextKey(
					_NESTED_FIELD_NAME, _NESTED_FIELD_INSTANCE_ID),
				fieldChangesProperties
			).build(),
			_ddmFormValues.getDDMFormFieldValuesMap(true), new DDMFormLayout(),
			Collections.emptySet());
	}

	private static final String _FIELD_INSTANCE_ID =
		RandomTestUtil.randomString();

	private static final String _FIELD_NAME = RandomTestUtil.randomString();

	private static final String _NESTED_FIELD_INSTANCE_ID =
		RandomTestUtil.randomString();

	private static final String _NESTED_FIELD_NAME =
		RandomTestUtil.randomString();

	private static final String _STRING_VALUE = RandomTestUtil.randomString();

	private final AddFormInstanceRecordMVCCommandHelper
		_addFormInstanceRecordMVCCommandHelper =
			new AddFormInstanceRecordMVCCommandHelper();
	private DDMFormField _ddmFormField;
	private DDMFormValues _ddmFormValues;

}