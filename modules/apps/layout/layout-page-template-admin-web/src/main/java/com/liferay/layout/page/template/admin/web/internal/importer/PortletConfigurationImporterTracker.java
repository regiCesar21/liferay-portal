/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.importer;

import com.liferay.layout.page.template.importer.PortletConfigurationImporter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Jürgen Kappler
 */
@Component(service = PortletConfigurationImporterTracker.class)
public class PortletConfigurationImporterTracker {

	public PortletConfigurationImporter getPortletConfigurationImporter(
		String portletName) {

		return _portletConfigurationImporters.get(portletName);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setPortletConfigurationImporter(
		PortletConfigurationImporter portletConfigurationImporter) {

		_portletConfigurationImporters.put(
			portletConfigurationImporter.getPortletName(),
			portletConfigurationImporter);
	}

	protected void unsetPortletConfigurationImporter(
		PortletConfigurationImporter portletConfigurationImporter) {

		_portletConfigurationImporters.remove(portletConfigurationImporter);
	}

	private final Map<String, PortletConfigurationImporter>
		_portletConfigurationImporters = new ConcurrentHashMap<>();

}