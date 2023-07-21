/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.web.internal.display.context;

import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemType;
import com.liferay.portal.kernel.dao.search.SearchContainer;

/**
 * @author Cristina González
 */
public class ContentDashboardItemTypeItemSelectorViewDisplayContext {

	public ContentDashboardItemTypeItemSelectorViewDisplayContext(
		String itemSelectedEventName,
		SearchContainer<? extends ContentDashboardItemType> searchContainer) {

		_itemSelectedEventName = itemSelectedEventName;
		_searchContainer = searchContainer;
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public SearchContainer<? extends ContentDashboardItemType>
		getSearchContainer() {

		return _searchContainer;
	}

	private final String _itemSelectedEventName;
	private final SearchContainer<? extends ContentDashboardItemType>
		_searchContainer;

}