/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseVerticalCard;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.portal.kernel.dao.search.RowChecker;

import javax.portlet.RenderRequest;

/**
 * @author Alejandro Tardín
 */
public class ItemDescriptorVerticalCard extends BaseVerticalCard {

	public ItemDescriptorVerticalCard(
		ItemSelectorViewDescriptor.ItemDescriptor itemDescriptor,
		RenderRequest renderRequest, RowChecker rowChecker) {

		super(null, renderRequest, rowChecker);

		_itemDescriptor = itemDescriptor;
	}

	@Override
	public String getAspectRatioCssClasses() {
		return "aspect-ratio-item-center-middle " +
			"aspect-ratio-item-vertical-fluid";
	}

	@Override
	public String getElementClasses() {
		return "card-interactive card-interactive-secondary";
	}

	@Override
	public String getIcon() {
		return _itemDescriptor.getIcon();
	}

	@Override
	public String getImageSrc() {
		return _itemDescriptor.getImageURL();
	}

	@Override
	public String getInputValue() {
		return null;
	}

	@Override
	public String getSubtitle() {
		return _itemDescriptor.getSubtitle(themeDisplay.getLocale());
	}

	@Override
	public String getTitle() {
		return _itemDescriptor.getTitle(themeDisplay.getLocale());
	}

	private final ItemSelectorViewDescriptor.ItemDescriptor _itemDescriptor;

}