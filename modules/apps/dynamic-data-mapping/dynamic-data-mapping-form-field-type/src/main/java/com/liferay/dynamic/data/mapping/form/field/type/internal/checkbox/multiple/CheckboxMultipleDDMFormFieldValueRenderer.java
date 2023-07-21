/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.checkbox.multiple;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.util.HtmlUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=checkbox_multiple",
	service = DDMFormFieldValueRenderer.class
)
public class CheckboxMultipleDDMFormFieldValueRenderer
	implements DDMFormFieldValueRenderer {

	@Override
	public String render(DDMFormFieldValue ddmFormFieldValue, Locale locale) {
		return HtmlUtil.escape(
			checkboxMultipleDDMFormFieldValueAccessor.getOptionsLabels(
				ddmFormFieldValue, locale));
	}

	@Reference
	protected CheckboxMultipleDDMFormFieldValueAccessor
		checkboxMultipleDDMFormFieldValueAccessor;

}