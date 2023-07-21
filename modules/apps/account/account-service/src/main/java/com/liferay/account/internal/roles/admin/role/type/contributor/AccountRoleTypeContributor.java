/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.roles.admin.role.type.contributor;

import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountRole;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.roles.admin.role.type.contributor.RoleTypeContributor;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true, property = "service.ranking:Integer=500",
	service = RoleTypeContributor.class
)
public class AccountRoleTypeContributor implements RoleTypeContributor {

	@Override
	public String getClassName() {
		return AccountRole.class.getName();
	}

	@Override
	public String getIcon() {
		return "briefcase";
	}

	@Override
	public String getName() {
		return "account";
	}

	@Override
	public String[] getSubtypes() {
		return new String[0];
	}

	@Override
	public String getTabTitle(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, AccountRoleTypeContributor.class);

		return _language.get(resourceBundle, "account-roles");
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, AccountRoleTypeContributor.class);

		return _language.get(resourceBundle, "account-role");
	}

	@Override
	public int getType() {
		return RoleConstants.TYPE_ACCOUNT;
	}

	@Override
	public boolean isAllowAssignMembers(Role role) {
		return false;
	}

	@Override
	public boolean isAllowDefinePermissions(Role role) {
		return true;
	}

	@Override
	public boolean isAllowDelete(Role role) {
		if ((role == null) || AccountRoleConstants.isRequiredRole(role)) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isAutomaticallyAssigned(Role role) {
		if (AccountRoleConstants.isImpliedRole(role)) {
			return true;
		}

		return false;
	}

	@Reference
	private Language _language;

}