/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class ItemSelectorViewDescriptorRendererManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public ItemSelectorViewDescriptorRendererManagementToolbarDisplayContext(
		ItemSelectorViewDescriptorRendererDisplayContext
			itemSelectorViewDescriptorRendererDisplayContext,
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<?> searchContainer) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);

		_itemSelectorViewDescriptorRendererDisplayContext =
			itemSelectorViewDescriptorRendererDisplayContext;

		_itemSelectorViewDescriptor =
			itemSelectorViewDescriptorRendererDisplayContext.
				getItemSelectorViewDescriptor();
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	@Override
	public String[] getOrderByKeys() {
		return _itemSelectorViewDescriptor.getOrderByKeys();
	}

	@Override
	public String getSearchActionURL() {
		return String.valueOf(getPortletURL());
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}

	@Override
	public Boolean isShowSearch() {
		return _itemSelectorViewDescriptor.isShowSearch();
	}

	@Override
	protected String getDefaultDisplayStyle() {
		return _itemSelectorViewDescriptor.getDefaultDisplayStyle();
	}

	@Override
	protected String getDisplayStyle() {
		return _itemSelectorViewDescriptorRendererDisplayContext.
			getDisplayStyle();
	}

	@Override
	protected String[] getDisplayViews() {
		return new String[] {"descriptive", "icon", "list"};
	}

	private final ItemSelectorViewDescriptor<Object>
		_itemSelectorViewDescriptor;
	private final ItemSelectorViewDescriptorRendererDisplayContext
		_itemSelectorViewDescriptorRendererDisplayContext;

}