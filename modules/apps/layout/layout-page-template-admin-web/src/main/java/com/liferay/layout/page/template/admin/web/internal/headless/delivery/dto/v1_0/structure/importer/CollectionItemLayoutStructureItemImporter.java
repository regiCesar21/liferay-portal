/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.structure.importer;

import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.model.Layout;

import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemImporter.class)
public class CollectionItemLayoutStructureItemImporter
	implements LayoutStructureItemImporter {

	@Override
	public LayoutStructureItem addLayoutStructureItem(
			Layout layout, LayoutStructure layoutStructure,
			PageElement pageElement, String parentItemId, int position,
			Set<String> warningMessages)
		throws Exception {

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getLayoutStructureItem(parentItemId);

		for (String childItemId : layoutStructureItem.getChildrenItemIds()) {
			LayoutStructureItem childLayoutStructureItem =
				layoutStructure.getLayoutStructureItem(childItemId);

			if (Objects.equals(
					childLayoutStructureItem.getItemType(),
					LayoutDataItemTypeConstants.TYPE_COLLECTION_ITEM)) {

				return childLayoutStructureItem;
			}
		}

		return layoutStructure.addCollectionItemLayoutStructureItem(
			parentItemId, position);
	}

	@Override
	public PageElement.Type getPageElementType() {
		return PageElement.Type.COLLECTION_ITEM;
	}

}