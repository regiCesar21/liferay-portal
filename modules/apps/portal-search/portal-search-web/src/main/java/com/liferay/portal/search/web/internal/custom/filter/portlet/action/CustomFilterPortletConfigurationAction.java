/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.custom.filter.portlet.action;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.internal.custom.filter.constants.CustomFilterPortletKeys;
import com.liferay.portal.search.web.internal.custom.filter.display.context.CustomFilterDisplayBuilder;
import com.liferay.portal.search.web.internal.custom.filter.display.context.CustomFilterDisplayContext;

import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Nazar
 * @author Luan Maoski
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + CustomFilterPortletKeys.CUSTOM_FILTER,
	service = ConfigurationAction.class
)
public class CustomFilterPortletConfigurationAction
	extends DefaultConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest) {
		return "/custom/filter/configuration.jsp";
	}

	@Override
	public void include(
			PortletConfig portletConfig, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ConfigurationDisplayContext configurationDisplayContext =
			new ConfigurationDisplayContext();

		httpServletRequest.setAttribute(
			CustomFilterPortletKeys.CONFIGURATION_DISPLAY_CONTEXT,
			configurationDisplayContext);

		RenderRequest renderRequest =
			(RenderRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			createCustomFilterDisplayContext(renderRequest));

		super.include(portletConfig, httpServletRequest, httpServletResponse);
	}

	protected CustomFilterDisplayContext buildDisplayContext(
			RenderRequest renderRequest)
		throws ConfigurationException {

		return CustomFilterDisplayBuilder.builder(
		).themeDisplay(
			(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).build();
	}

	protected CustomFilterDisplayContext createCustomFilterDisplayContext(
		RenderRequest renderRequest) {

		try {
			return buildDisplayContext(renderRequest);
		}
		catch (ConfigurationException configurationException) {
			throw new RuntimeException(configurationException);
		}
	}

}