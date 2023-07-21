/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.account.admin.web.internal.constants.AccountScreenNavigationEntryConstants;
import com.liferay.account.admin.web.internal.display.AccountEntryDisplay;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = {
		"screen.navigation.category.order:Integer=60",
		"screen.navigation.entry.order:Integer=10"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class AccountEntryRolesScreenNavigationCategory
	extends BaseAccountEntryScreenNavigationEntry
	implements ScreenNavigationCategory {

	@Override
	public String getCategoryKey() {
		return AccountScreenNavigationEntryConstants.CATEGORY_KEY_ROLES;
	}

	@Override
	public String getEntryKey() {
		return AccountScreenNavigationEntryConstants.ENTRY_KEY_ROLES;
	}

	@Override
	public String getJspPath() {
		return "/account_entries_admin/account_entry/view_account_roles.jsp";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "roles");
	}

	@Override
	public boolean isVisible(
		User user, AccountEntryDisplay accountEntryDisplay) {

		if (accountEntryDisplay.getAccountEntryId() > 0) {
			return true;
		}

		return false;
	}

}