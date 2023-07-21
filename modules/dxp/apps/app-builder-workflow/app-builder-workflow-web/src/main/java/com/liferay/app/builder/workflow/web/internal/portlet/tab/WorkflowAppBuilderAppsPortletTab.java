/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.workflow.web.internal.portlet.tab;

import com.liferay.app.builder.portlet.tab.AppBuilderAppsPortletTab;
import com.liferay.app.builder.workflow.rest.resource.v1_0.AppWorkflowResource;
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
	immediate = true, property = "app.builder.apps.tabs.name=workflow",
	service = AppBuilderAppsPortletTab.class
)
public class WorkflowAppBuilderAppsPortletTab
	implements AppBuilderAppsPortletTab {

	@Override
	public void deleteApp(long appBuilderAppId, User user) throws Exception {
		AppWorkflowResource.Builder appWorkflowResourceBuilder =
			_appWorkflowResourceFactory.create();

		AppWorkflowResource appWorkflowResource =
			appWorkflowResourceBuilder.user(
				user
			).build();

		appWorkflowResource.deleteAppWorkflow(appBuilderAppId);
	}

	@Override
	public String getEditEntryPoint() {
		return _npmResolver.resolveModuleName(
			"app-builder-workflow-web/js/pages/apps/edit/EditApp.es");
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(
			ResourceBundleUtil.getModuleAndPortalResourceBundle(
				locale, getClass()),
			"workflow-powered");
	}

	@Override
	public String getListEntryPoint() {
		return _npmResolver.resolveModuleName(
			"app-builder-workflow-web/js/pages/apps/ListApps.es");
	}

	@Reference
	private AppWorkflowResource.Factory _appWorkflowResourceFactory;

	@Reference
	private Language _language;

	@Reference
	private NPMResolver _npmResolver;

}