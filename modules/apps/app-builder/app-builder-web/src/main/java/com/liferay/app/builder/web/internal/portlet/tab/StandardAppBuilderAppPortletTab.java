/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.web.internal.portlet.tab;

import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.portlet.tab.AppBuilderAppPortletTab;
import com.liferay.app.builder.portlet.tab.AppBuilderAppPortletTabContext;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.kernel.util.HashMapBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true, property = "app.builder.app.tab.name=standard",
	service = AppBuilderAppPortletTab.class
)
public class StandardAppBuilderAppPortletTab
	implements AppBuilderAppPortletTab {

	@Override
	public AppBuilderAppPortletTabContext getAppBuilderAppPortletTabContext(
		AppBuilderApp appBuilderApp, long dataRecordId) {

		AppBuilderAppPortletTabContext appBuilderAppPortletTabContext =
			new AppBuilderAppPortletTabContext();

		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.fetchDDMStructureLayout(
				appBuilderApp.getDdmStructureLayoutId());

		return appBuilderAppPortletTabContext.addDataLayoutProperties(
			appBuilderApp.getDdmStructureLayoutId(),
			HashMapBuilder.<String, Object>put(
				"nameMap", ddmStructureLayout.getNameMap()
			).put(
				"readOnly", false
			).build());
	}

	@Override
	public String getEditEntryPoint() {
		return _npmResolver.resolveModuleName(
			"app-builder-web/js/pages/entry/EditEntry.es");
	}

	@Override
	public String getListEntryPoint() {
		return _npmResolver.resolveModuleName(
			"app-builder-web/js/pages/entry/ListEntries.es");
	}

	@Override
	public String getViewEntryPoint() {
		return _npmResolver.resolveModuleName(
			"app-builder-web/js/pages/entry/ViewEntry.es");
	}

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private NPMResolver _npmResolver;

}