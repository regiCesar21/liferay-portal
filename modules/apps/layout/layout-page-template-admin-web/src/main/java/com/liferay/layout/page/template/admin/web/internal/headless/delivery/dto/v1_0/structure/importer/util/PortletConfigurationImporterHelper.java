/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.structure.importer.util;

import com.liferay.layout.page.template.admin.web.internal.importer.PortletConfigurationImporterTracker;
import com.liferay.layout.page.template.importer.PortletConfigurationImporter;
import com.liferay.layout.page.template.importer.PortletPreferencesPortletConfigurationImporter;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = PortletConfigurationImporterHelper.class)
public class PortletConfigurationImporterHelper {

	public void importPortletConfiguration(
			long plid, String portletId, Map<String, Object> widgetConfig)
		throws Exception {

		if (widgetConfig == null) {
			return;
		}

		Layout layout = _layoutLocalService.fetchLayout(plid);

		if (layout == null) {
			return;
		}

		String portletName = PortletIdCodec.decodePortletName(portletId);

		Portlet portlet = _portletLocalService.getPortletById(portletName);

		if (portlet == null) {
			return;
		}

		PortletConfigurationImporter portletConfigurationImporter =
			_portletConfigurationImporterTracker.
				getPortletConfigurationImporter(portletName);

		if (portletConfigurationImporter != null) {
			portletConfigurationImporter.importPortletConfiguration(
				layout.getPlid(), portletId, widgetConfig);
		}
		else {
			_portletPreferencesPortletConfigurationImporter.
				importPortletConfiguration(
					layout.getPlid(), portletId, widgetConfig);
		}
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private PortletConfigurationImporterTracker
		_portletConfigurationImporterTracker;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private PortletPreferencesPortletConfigurationImporter
		_portletPreferencesPortletConfigurationImporter;

}