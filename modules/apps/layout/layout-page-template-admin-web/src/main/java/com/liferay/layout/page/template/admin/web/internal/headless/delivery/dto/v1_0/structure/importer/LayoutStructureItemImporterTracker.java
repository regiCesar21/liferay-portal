/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.structure.importer;

import com.liferay.headless.delivery.dto.v1_0.PageElement;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemImporterTracker.class)
public class LayoutStructureItemImporterTracker {

	public LayoutStructureItemImporter getLayoutStructureItemImporter(
		PageElement.Type type) {

		return _layoutStructureItemImporters.get(type);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setLayoutStructureItemImporter(
		LayoutStructureItemImporter layoutStructureItemImporter) {

		_layoutStructureItemImporters.put(
			layoutStructureItemImporter.getPageElementType(),
			layoutStructureItemImporter);
	}

	protected void unsetLayoutStructureItemImporter(
		LayoutStructureItemImporter layoutStructureItemImporter) {

		_layoutStructureItemImporters.remove(layoutStructureItemImporter);
	}

	private final Map<PageElement.Type, LayoutStructureItemImporter>
		_layoutStructureItemImporters = new ConcurrentHashMap<>();

}