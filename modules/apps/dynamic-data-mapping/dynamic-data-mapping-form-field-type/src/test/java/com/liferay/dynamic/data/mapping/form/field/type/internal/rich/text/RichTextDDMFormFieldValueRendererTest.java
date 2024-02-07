/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.rich.text;

import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.HtmlUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Carolina Barbosa
 */
@PrepareForTest(HtmlUtil.class)
@RunWith(PowerMockRunner.class)
public class RichTextDDMFormFieldValueRendererTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_richTextDDMFormFieldValueRenderer, "_html", _html);
	}

	@Test
	public void testRender() {
		String value = RandomTestUtil.randomString();

		Mockito.when(
			_html.extractText(Mockito.anyString())
		).thenReturn(
			value
		);

		PowerMockito.mockStatic(HtmlUtil.class);

		String escapedValue = RandomTestUtil.randomString();

		PowerMockito.when(
			HtmlUtil.escape(value)
		).thenReturn(
			escapedValue
		);

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setValue(
			new UnlocalizedValue(RandomTestUtil.randomString()));

		Assert.assertEquals(
			escapedValue,
			_richTextDDMFormFieldValueRenderer.render(ddmFormFieldValue, null));
	}

	private final Html _html = Mockito.mock(Html.class);
	private final RichTextDDMFormFieldValueRenderer
		_richTextDDMFormFieldValueRenderer =
			new RichTextDDMFormFieldValueRenderer();

}