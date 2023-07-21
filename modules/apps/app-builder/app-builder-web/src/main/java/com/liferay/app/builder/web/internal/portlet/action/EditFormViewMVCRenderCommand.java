/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.web.internal.portlet.action;

import com.liferay.app.builder.constants.AppBuilderPortletKeys;
import com.liferay.app.builder.web.internal.configuration.AppBuilderConfiguration;
import com.liferay.app.builder.web.internal.constants.AppBuilderWebKeys;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;

import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Bruno Basto
 */
@Component(
	configurationPid = "com.liferay.app.builder.web.internal.configuration.AppBuilderConfiguration",
	property = {
		"javax.portlet.name=" + AppBuilderPortletKeys.OBJECTS,
		"mvc.command.name=/app_builder/edit_form_view"
	},
	service = MVCRenderCommand.class
)
public class EditFormViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		renderRequest.setAttribute(
			AppBuilderWebKeys.SHOW_TRANSLATION_MANAGER,
			_appBuilderConfiguration.showTranslationManager());

		return "/edit_form_view.jsp";
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_appBuilderConfiguration = ConfigurableUtil.createConfigurable(
			AppBuilderConfiguration.class, properties);
	}

	private volatile AppBuilderConfiguration _appBuilderConfiguration;

}