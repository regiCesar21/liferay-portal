/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.structure.exporter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemExporterTracker.class)
public class LayoutStructureItemExporterTracker {

	public LayoutStructureItemExporter getLayoutStructureItemExporter(
		String className) {

		return _layoutStructureItemExporters.get(className);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setLayoutStructureItemExporter(
		LayoutStructureItemExporter layoutStructureItemExporter) {

		_layoutStructureItemExporters.put(
			layoutStructureItemExporter.getClassName(),
			layoutStructureItemExporter);
	}

	protected void unsetLayoutStructureItemExporter(
		LayoutStructureItemExporter layoutStructureItemExporter) {

		_layoutStructureItemExporters.remove(layoutStructureItemExporter);
	}

	private final Map<String, LayoutStructureItemExporter>
		_layoutStructureItemExporters = new ConcurrentHashMap<>();

}