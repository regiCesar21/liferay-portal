/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.web.internal.portlet.tab;

import com.liferay.app.builder.portlet.tab.AppBuilderAppsPortletTab;
import com.liferay.app.builder.rest.resource.v1_0.AppResource;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true, property = "app.builder.apps.tabs.name=standard",
	service = AppBuilderAppsPortletTab.class
)
public class StandardAppBuilderAppsPortletTab
	implements AppBuilderAppsPortletTab {

	@Override
	public void deleteApp(long appBuilderAppId, User user) throws Exception {
		AppResource.Builder appResourceBuilder = _appResourceFactory.create();

		AppResource appResource = appResourceBuilder.user(
			user
		).build();

		appResource.deleteApp(appBuilderAppId);
	}

	@Override
	public String getEditEntryPoint() {
		return _npmResolver.resolveModuleName(
			"app-builder-web/js/pages/apps/edit/EditApp.es");
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(
			ResourceBundleUtil.getModuleAndPortalResourceBundle(
				locale, getClass()),
			"standard");
	}

	@Override
	public String getListEntryPoint() {
		return _npmResolver.resolveModuleName(
			"app-builder-web/js/pages/apps/ListStandardApps.es");
	}

	@Reference
	private AppResource.Factory _appResourceFactory;

	@Reference
	private Language _language;

	@Reference
	private NPMResolver _npmResolver;

}