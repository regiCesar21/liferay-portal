/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderInputParametersSettings;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderOutputParametersSettings;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.lang.reflect.Method;

import java.util.Collection;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Renato Rego
 */
public class DDMFormFactoryHelperTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetNamesOfDDMDataProviderInputParametersSettings() {
		_ddmFormFactoryHelper = new DDMFormFactoryHelper(
			DDMDataProviderInputParametersSettings.class);

		Assert.assertArrayEquals(
			new String[] {
				"inputParameterLabel", "inputParameterName",
				"inputParameterType", "inputParameterRequired"
			},
			getNames());
	}

	@Test
	public void testGetNamesOfDDMDataProviderOutputParametersSettings() {
		_ddmFormFactoryHelper = new DDMFormFactoryHelper(
			DDMDataProviderOutputParametersSettings.class);

		Assert.assertArrayEquals(
			new String[] {
				"outputParameterId", "outputParameterName",
				"outputParameterPath", "outputParameterType"
			},
			getNames());
	}

	protected String[] getNames() {
		Collection<Method> ddmFormFieldMethods =
			_ddmFormFactoryHelper.getDDMFormFieldMethods();

		Stream<Method> stream = ddmFormFieldMethods.stream();

		return stream.map(
			ddmFormFieldMethod -> ddmFormFieldMethod.getName()
		).toArray(
			String[]::new
		);
	}

	private DDMFormFactoryHelper _ddmFormFactoryHelper;

}