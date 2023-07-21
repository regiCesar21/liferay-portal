/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.web.internal.display.context;

import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemType;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class
	ContentDashboardItemTypeItemSelectorViewManagementToolbarDisplayContext
		extends SearchContainerManagementToolbarDisplayContext {

	public ContentDashboardItemTypeItemSelectorViewManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<? extends ContentDashboardItemType> searchContainer) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return String.valueOf(clearResultsURL);
	}

	@Override
	public String[] getOrderByKeys() {
		return new String[] {"title", "modified-date"};
	}

	@Override
	public String getSearchActionURL() {
		return String.valueOf(getPortletURL());
	}

	@Override
	public String getSearchContainerId() {
		return "contentDashboardItemTypes";
	}

	@Override
	public Boolean isSelectable() {
		return true;
	}

	@Override
	public Boolean isShowSearch() {
		return true;
	}

}