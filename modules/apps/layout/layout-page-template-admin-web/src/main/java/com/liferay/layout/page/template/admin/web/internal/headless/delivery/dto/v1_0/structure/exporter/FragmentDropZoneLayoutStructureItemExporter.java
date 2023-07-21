/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.structure.exporter;

import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.dto.v1_0.PageFragmentDropZoneDefinition;
import com.liferay.layout.util.structure.FragmentDropZoneLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureItem;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(service = LayoutStructureItemExporter.class)
public class FragmentDropZoneLayoutStructureItemExporter
	implements LayoutStructureItemExporter {

	@Override
	public String getClassName() {
		return FragmentDropZoneLayoutStructureItem.class.getName();
	}

	@Override
	public PageElement getPageElement(
		long groupId, LayoutStructureItem layoutStructureItem,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		FragmentDropZoneLayoutStructureItem
			fragmentDropZoneLayoutStructureItem =
				(FragmentDropZoneLayoutStructureItem)layoutStructureItem;

		return new PageElement() {
			{
				definition = new PageFragmentDropZoneDefinition() {
					{
						fragmentDropZoneId =
							fragmentDropZoneLayoutStructureItem.
								getFragmentDropZoneId();
					}
				};

				type = Type.FRAGMENT_DROP_ZONE;
			}
		};
	}

}