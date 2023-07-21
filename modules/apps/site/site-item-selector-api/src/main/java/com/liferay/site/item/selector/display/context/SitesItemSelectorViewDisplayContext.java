/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.item.selector.display.context;

import com.liferay.item.selector.criteria.group.criterion.GroupItemSelectorCriterion;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portlet.usersadmin.search.GroupSearch;
import com.liferay.site.item.selector.criterion.SiteItemSelectorCriterion;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

/**
 * @author Julio Camarero
 */
public interface SitesItemSelectorViewDisplayContext {

	public String getDisplayStyle();

	public default GroupItemSelectorCriterion getGroupItemSelectorCriterion() {
		return getSiteItemSelectorCriterion();
	}

	public String getGroupName(Group group) throws PortalException;

	public GroupSearch getGroupSearch() throws Exception;

	public String getItemSelectedEventName();

	public PortletRequest getPortletRequest();

	public PortletResponse getPortletResponse();

	public PortletURL getPortletURL() throws PortletException;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getGroupItemSelectorCriterion()}
	 */
	@Deprecated
	public default SiteItemSelectorCriterion getSiteItemSelectorCriterion() {
		return new SiteItemSelectorCriterion();
	}

	public boolean isShowChildSitesLink();

	public boolean isShowSearch();

	public boolean isShowSortFilter();

}