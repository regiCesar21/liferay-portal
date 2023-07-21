/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.captcha;

import com.liferay.captcha.taglib.servlet.taglib.CaptchaTag;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.template.soy.data.SoyDataFactory;
import com.liferay.portal.template.soy.util.SoyRawData;
import com.liferay.taglib.servlet.PageContextFactoryUtil;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=captcha",
	service = {
		CaptchaDDMFormFieldTemplateContextContributor.class,
		DDMFormFieldTemplateContextContributor.class
	}
)
public class CaptchaDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		String html = StringPool.BLANK;

		try {
			html = renderCaptchaTag(ddmFormField, ddmFormFieldRenderingContext);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		SoyRawData soyRawData = _soyDataFactory.createSoyRawData(html);

		return Collections.singletonMap("html", soyRawData.getValue());
	}

	protected String renderCaptchaTag(
			DDMFormField ddmFormField,
			DDMFormFieldRenderingContext ddmFormFieldRenderingContext)
		throws Exception {

		CaptchaTag captchaTag = new CaptchaTag();

		captchaTag.setUrl(
			GetterUtil.getString(ddmFormField.getProperty("url")));

		HttpServletRequest httpServletRequest =
			ddmFormFieldRenderingContext.getHttpServletRequest();
		HttpServletResponse httpServletResponse =
			ddmFormFieldRenderingContext.getHttpServletResponse();

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PageContext pageContext = PageContextFactoryUtil.create(
			httpServletRequest,
			new PipingServletResponse(httpServletResponse, unsyncStringWriter));

		captchaTag.setPageContext(pageContext);

		captchaTag.runTag();

		return unsyncStringWriter.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CaptchaDDMFormFieldTemplateContextContributor.class);

	@Reference
	private SoyDataFactory _soyDataFactory;

}