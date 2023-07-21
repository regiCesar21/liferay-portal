/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.search.bar.portlet.action;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.constants.SearchBarPortletKeys;
import com.liferay.portal.search.web.internal.search.bar.portlet.SearchBarPortletDisplayContext;
import com.liferay.portal.search.web.internal.search.bar.portlet.SearchBarPrecedenceHelper;
import com.liferay.portal.search.web.internal.search.bar.portlet.configuration.SearchBarPortletInstanceConfiguration;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + SearchBarPortletKeys.SEARCH_BAR,
	service = ConfigurationAction.class
)
public class SearchBarConfigurationAction extends DefaultConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SearchBarPortletDisplayContext searchBarPortletDisplayContext =
			new SearchBarPortletDisplayContext();

		SearchBarPortletInstanceConfiguration
			searchBarPortletInstanceConfiguration =
				getSearchBarPortletInstanceConfiguration(
					themeDisplay.getPortletDisplay());

		long displayStyleGroupId =
			searchBarPortletInstanceConfiguration.displayStyleGroupId();

		if (displayStyleGroupId <= 0) {
			displayStyleGroupId = themeDisplay.getScopeGroupId();
		}

		searchBarPortletDisplayContext.setDisplayStyleGroupId(
			displayStyleGroupId);
		searchBarPortletDisplayContext.setDisplayWarningIgnoredConfiguration(
			searchBarPrecedenceHelper.isDisplayWarningIgnoredConfiguration(
				themeDisplay, true));
		searchBarPortletDisplayContext.setSearchBarPortletInstanceConfiguration(
			searchBarPortletInstanceConfiguration);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, searchBarPortletDisplayContext);

		return "/search/bar/configuration.jsp";
	}

	protected SearchBarPortletInstanceConfiguration
		getSearchBarPortletInstanceConfiguration(
			PortletDisplay portletDisplay) {

		try {
			return portletDisplay.getPortletInstanceConfiguration(
				SearchBarPortletInstanceConfiguration.class);
		}
		catch (ConfigurationException configurationException) {
			throw new RuntimeException(configurationException);
		}
	}

	@Reference
	protected SearchBarPrecedenceHelper searchBarPrecedenceHelper;

}