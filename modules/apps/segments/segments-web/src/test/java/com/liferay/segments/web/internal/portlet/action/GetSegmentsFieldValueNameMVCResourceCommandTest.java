/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.field.customizer.SegmentsFieldCustomizer;
import com.liferay.segments.field.customizer.SegmentsFieldCustomizerRegistry;

import java.util.Locale;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author David Arques
 */
@RunWith(MockitoJUnitRunner.class)
public class GetSegmentsFieldValueNameMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws PortalException {
		ReflectionTestUtil.setFieldValue(
			_getSegmentsFieldValueNameMVCResourceCommand,
			"_segmentsFieldCustomizerRegistry",
			_segmentsFieldCustomizerRegistry);
	}

	@Test
	public void testGetFieldValueNameJSONObjectWithExistingSegmentsFieldCustomizer() {
		String entityName = RandomTestUtil.randomString();
		String fieldName = RandomTestUtil.randomString();
		String fieldValue = RandomTestUtil.randomString();
		String fieldValueName = RandomTestUtil.randomString();
		Locale locale = LocaleUtil.getDefault();

		Mockito.doReturn(
			Optional.of(
				_createSegmentsFieldCustomizer(
					fieldValue, fieldValueName, locale))
		).when(
			_segmentsFieldCustomizerRegistry
		).getSegmentsFieldCustomizerOptional(
			entityName, fieldName
		);

		JSONObject jsonObject =
			_getSegmentsFieldValueNameMVCResourceCommand.
				getFieldValueNameJSONObject(
					entityName, fieldName, fieldValue, locale);

		JSONObject expectedJSONObject = JSONUtil.put(
			"fieldValueName", fieldValueName);

		Assert.assertEquals(
			expectedJSONObject.toString(), jsonObject.toString());
	}

	@Test
	public void testGetFieldValueNameJSONObjectWithNonexistingSegmentsFieldCustomizer() {
		String entityName = RandomTestUtil.randomString();
		String fieldName = RandomTestUtil.randomString();

		Mockito.doReturn(
			Optional.empty()
		).when(
			_segmentsFieldCustomizerRegistry
		).getSegmentsFieldCustomizerOptional(
			entityName, fieldName
		);

		JSONObject jsonObject =
			_getSegmentsFieldValueNameMVCResourceCommand.
				getFieldValueNameJSONObject(
					entityName, fieldName, RandomTestUtil.randomString(),
					LocaleUtil.getDefault());

		Assert.assertEquals("{}", jsonObject.toString());
	}

	private SegmentsFieldCustomizer _createSegmentsFieldCustomizer(
		String fieldValue, Object fieldValueName, Locale locale) {

		SegmentsFieldCustomizer segmentsFieldCustomizer = Mockito.mock(
			SegmentsFieldCustomizer.class);

		Mockito.doReturn(
			fieldValueName
		).when(
			segmentsFieldCustomizer
		).getFieldValueName(
			fieldValue, locale
		);

		return segmentsFieldCustomizer;
	}

	private final GetSegmentsFieldValueNameMVCResourceCommand
		_getSegmentsFieldValueNameMVCResourceCommand =
			new GetSegmentsFieldValueNameMVCResourceCommand();

	@Mock
	private SegmentsFieldCustomizerRegistry _segmentsFieldCustomizerRegistry;

}