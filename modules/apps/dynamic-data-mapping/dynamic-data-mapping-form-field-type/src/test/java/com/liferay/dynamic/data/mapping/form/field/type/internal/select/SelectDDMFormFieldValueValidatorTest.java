/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.select;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueValidationException;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.portal.kernel.util.LocaleUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Marcellus Tavares
 */
@RunWith(PowerMockRunner.class)
public class SelectDDMFormFieldValueValidatorTest {

	@Test
	public void testValidate() throws Exception {
		DDMFormField ddmFormField = new DDMFormField("option", "select");

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel("A", LocaleUtil.US, "Option A");

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		_selectDDMFormFieldValueValidator.validate(ddmFormField, null);
	}

	@Test(expected = DDMFormFieldValueValidationException.class)
	public void testValidateWithEmptyOptionsValues() throws Exception {
		DDMFormField ddmFormField = new DDMFormField("option", "select");

		ddmFormField.setDDMFormFieldOptions(new DDMFormFieldOptions());

		_selectDDMFormFieldValueValidator.validate(ddmFormField, null);
	}

	@Test(expected = DDMFormFieldValueValidationException.class)
	public void testValidateWithNullDDMFormFieldOptions() throws Exception {
		DDMFormField ddmFormField = new DDMFormField("option", "select");

		ddmFormField.setDDMFormFieldOptions(null);

		_selectDDMFormFieldValueValidator.validate(ddmFormField, null);
	}

	private final SelectDDMFormFieldValueValidator
		_selectDDMFormFieldValueValidator =
			new SelectDDMFormFieldValueValidator();

}