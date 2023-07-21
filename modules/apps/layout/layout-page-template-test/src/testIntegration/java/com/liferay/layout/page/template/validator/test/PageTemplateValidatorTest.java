/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.validator.test;

import com.liferay.layout.page.template.exception.PageTemplateValidatorException;
import com.liferay.layout.page.template.validator.PageTemplateValidator;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.util.FileImpl;

import org.hamcrest.core.StringStartsWith;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Rubén Pulido
 */
public class PageTemplateValidatorTest {

	@Before
	public void setUp() {
		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());
	}

	@Test
	public void testValidatePageTemplateInvalidExtraProperties()
		throws Exception {

		expectedException.expect(PageTemplateValidatorException.class);
		expectedException.expectMessage(
			new StringStartsWith("extraneous key [extra] is not permitted"));

		PageTemplateValidator.validatePageTemplate(
			_read("page_template_invalid_extra_properties.json"));
	}

	@Test
	public void testValidatePageTemplateInvalidMissingName() throws Exception {
		expectedException.expect(PageTemplateValidatorException.class);
		expectedException.expectMessage(
			new StringStartsWith("required key [name] not found"));

		PageTemplateValidator.validatePageTemplate(
			_read("page_template_invalid_missing_name.json"));
	}

	@Test
	public void testValidatePageTemplateValidRequired() throws Exception {
		PageTemplateValidator.validatePageTemplate(
			_read("page_template_valid_required.json"));
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

}