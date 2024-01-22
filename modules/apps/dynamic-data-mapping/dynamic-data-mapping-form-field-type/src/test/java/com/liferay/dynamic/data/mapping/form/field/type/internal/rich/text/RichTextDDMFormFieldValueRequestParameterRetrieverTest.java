/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.rich.text;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.RegistryUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Carolina Barbosa
 */
@PrepareForTest(SanitizerUtil.class)
@RunWith(PowerMockRunner.class)
public class RichTextDDMFormFieldValueRequestParameterRetrieverTest
	extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		PropsUtil.setProps(Mockito.mock(Props.class));

		RegistryUtil.setRegistry(new BasicRegistryImpl());

		_mockHttpServletRequest = new MockHttpServletRequest();

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.getCompanyId()
		).thenReturn(
			_COMPANY_ID
		);

		Mockito.when(
			themeDisplay.getScopeGroupId()
		).thenReturn(
			_SCOPE_GROUP_ID
		);

		Mockito.when(
			themeDisplay.getUserId()
		).thenReturn(
			_USER_ID
		);

		_mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);
	}

	@Test
	public void testGetRequestParameterValue() throws Exception {
		String parameterValue = RandomTestUtil.randomString();

		_mockHttpServletRequest.addParameter(
			"ddmFormFieldRichText", parameterValue);

		PowerMockito.mockStatic(SanitizerUtil.class);

		String sanitizedParameterValue = RandomTestUtil.randomString();

		PowerMockito.when(
			SanitizerUtil.sanitize(
				_COMPANY_ID, _SCOPE_GROUP_ID, _USER_ID, null, 0,
				ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL, parameterValue,
				null)
		).thenReturn(
			sanitizedParameterValue
		);

		RichTextDDMFormFieldValueRequestParameterRetriever
			richTextDDMFormFieldValueRequestParameterRetriever =
				new RichTextDDMFormFieldValueRequestParameterRetriever();

		Assert.assertEquals(
			sanitizedParameterValue,
			richTextDDMFormFieldValueRequestParameterRetriever.get(
				_mockHttpServletRequest, "ddmFormFieldRichText",
				StringPool.BLANK));
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final long _SCOPE_GROUP_ID = RandomTestUtil.randomLong();

	private static final long _USER_ID = RandomTestUtil.randomLong();

	private MockHttpServletRequest _mockHttpServletRequest =
		new MockHttpServletRequest();

}