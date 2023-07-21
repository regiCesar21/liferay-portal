/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.field.type;

import com.liferay.info.field.InfoField;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Optional;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alejandro Tardín
 */
public class TextInfoFieldTypeTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testMultilineAttributeCanBeSetToFalse() {
		InfoField<TextInfoFieldType> infoField = InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).namespace(
			StringPool.BLANK
		).name(
			"test-field"
		).attribute(
			TextInfoFieldType.MULTILINE, false
		).build();

		Optional<Boolean> attributeOptional = infoField.getAttributeOptional(
			TextInfoFieldType.MULTILINE);

		Assert.assertFalse(attributeOptional.get());
	}

	@Test
	public void testMultilineAttributeCanBeSetToTrue() {
		InfoField<TextInfoFieldType> infoField = InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).namespace(
			StringPool.BLANK
		).name(
			"test-field"
		).attribute(
			TextInfoFieldType.MULTILINE, true
		).build();

		Optional<Boolean> attributeOptional = infoField.getAttributeOptional(
			TextInfoFieldType.MULTILINE);

		Assert.assertTrue(attributeOptional.get());
	}

	@Test
	public void testMultilineAttributeIsEmptyByDefault() {
		InfoField<TextInfoFieldType> infoField = InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).namespace(
			StringPool.BLANK
		).name(
			"test-field"
		).build();

		Optional<Boolean> attributeOptional = infoField.getAttributeOptional(
			TextInfoFieldType.MULTILINE);

		Assert.assertFalse(attributeOptional.isPresent());
	}

}