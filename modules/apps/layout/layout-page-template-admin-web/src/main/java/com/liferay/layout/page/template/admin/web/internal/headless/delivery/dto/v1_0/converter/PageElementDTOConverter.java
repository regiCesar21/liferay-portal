/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.converter;

import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.structure.exporter.LayoutStructureItemExporter;
import com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.structure.exporter.LayoutStructureItemExporterTracker;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.model.Layout;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = PageElementDTOConverter.class)
public class PageElementDTOConverter {

	public PageElement toDTO(
		Layout layout, String layoutStructureItemId, boolean saveInlineContent,
		boolean saveMappingConfiguration, long segmentsExperienceId) {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), layout.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(segmentsExperienceId));

		return _toPageElement(
			layout.getGroupId(), layoutStructure,
			layoutStructure.getLayoutStructureItem(layoutStructureItemId),
			saveInlineContent, saveMappingConfiguration);
	}

	private PageElement _toPageElement(
		long groupId, LayoutStructure layoutStructure,
		LayoutStructureItem layoutStructureItem, boolean saveInlineContent,
		boolean saveMappingConfiguration) {

		List<PageElement> pageElements = new ArrayList<>();

		List<String> childrenItemIds = layoutStructureItem.getChildrenItemIds();

		for (String childItemId : childrenItemIds) {
			LayoutStructureItem childLayoutStructureItem =
				layoutStructure.getLayoutStructureItem(childItemId);

			List<String> grandChildrenItemIds =
				childLayoutStructureItem.getChildrenItemIds();

			if (grandChildrenItemIds.isEmpty()) {
				pageElements.add(
					_toPageElement(
						groupId, childLayoutStructureItem, saveInlineContent,
						saveMappingConfiguration));
			}
			else {
				pageElements.add(
					_toPageElement(
						groupId, layoutStructure, childLayoutStructureItem,
						saveInlineContent, saveMappingConfiguration));
			}
		}

		PageElement pageElement = _toPageElement(
			groupId, layoutStructureItem, saveInlineContent,
			saveMappingConfiguration);

		if (!pageElements.isEmpty()) {
			pageElement.setPageElements(
				pageElements.toArray(new PageElement[0]));
		}

		return pageElement;
	}

	private PageElement _toPageElement(
		long groupId, LayoutStructureItem layoutStructureItem,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		Class<?> clazz = layoutStructureItem.getClass();

		LayoutStructureItemExporter layoutStructureItemExporter =
			_layoutStructureItemExporterTracker.getLayoutStructureItemExporter(
				clazz.getName());

		if (layoutStructureItemExporter == null) {
			return null;
		}

		return layoutStructureItemExporter.getPageElement(
			groupId, layoutStructureItem, saveInlineContent,
			saveMappingConfiguration);
	}

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutStructureItemExporterTracker
		_layoutStructureItemExporterTracker;

}