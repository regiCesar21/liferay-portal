/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.display.context;

import com.liferay.account.admin.web.internal.display.AccountEntryDisplay;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Albert Lee
 */
public class ViewAccountGroupAccountEntriesManagementToolbarDisplayContext
	extends ViewAccountEntriesManagementToolbarDisplayContext {

	public ViewAccountGroupAccountEntriesManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<AccountEntryDisplay> searchContainer) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemList.of(
			() -> {
				DropdownItem dropdownItem = new DropdownItem();

				dropdownItem.putData(
					"action", "removeAccountGroupAccountEntries");
				dropdownItem.setIcon("times-circle");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "remove"));
				dropdownItem.setQuickAction(true);

				return dropdownItem;
			});
	}

	@Override
	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.putData(
					"action", "selectAccountGroupAccountEntries");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "assign-accounts"));
			}
		).build();
	}

	@Override
	public String getDefaultEventHandler() {
		return "ACCOUNT_GROUP_ACCOUNT_ENTRIES_MANAGEMENT_TOOLBAR_" +
			"DEFAULT_EVENT_HANDLER";
	}

}