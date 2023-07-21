/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.rich.text;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.internal.util.DDMFormFieldTypeUtil;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.editor.configuration.EditorConfiguration;
import com.liferay.portal.kernel.editor.configuration.EditorConfigurationFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Lancha
 * @author Marko Cikos
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=rich_text",
	service = {
		DDMFormFieldTemplateContextContributor.class,
		RichTextDDMFormFieldTemplateContextContributor.class
	}
)
public class RichTextDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		return HashMapBuilder.<String, Object>put(
			"predefinedValue",
			DDMFormFieldTypeUtil.getPropertyValue(
				ddmFormField, ddmFormFieldRenderingContext.getLocale(),
				"predefinedValue")
		).put(
			"value",
			DDMFormFieldTypeUtil.getPropertyValue(
				ddmFormFieldRenderingContext, "value")
		).putAll(
			_getData(ddmFormFieldRenderingContext, ddmFormField.getType())
		).build();
	}

	private Map<String, Object> _getData(
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext,
		String ddmFormFieldType) {

		HttpServletRequest httpServletRequest =
			ddmFormFieldRenderingContext.getHttpServletRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		EditorConfiguration editorConfiguration =
			EditorConfigurationFactoryUtil.getEditorConfiguration(
				themeDisplay.getPpid(), ddmFormFieldType, "ckeditor_classic",
				HashMapBuilder.<String, Object>put(
					"liferay-ui:input-editor:allowBrowseDocuments", true
				).put(
					"liferay-ui:input-editor:name",
					ddmFormFieldRenderingContext.getName()
				).build(),
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY),
				RequestBackedPortletURLFactoryUtil.create(httpServletRequest));

		return editorConfiguration.getData();
	}

}