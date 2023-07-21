/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.security.permission.resource;

import com.liferay.account.model.AccountEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(immediate = true, service = {})
public class AccountEntryPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, AccountEntry accountEntry,
			String actionId)
		throws PortalException {

		return _accountEntryModelResourcePermission.contains(
			permissionChecker, accountEntry, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long accountEntryId,
			String actionId)
		throws PortalException {

		return _accountEntryModelResourcePermission.contains(
			permissionChecker, accountEntryId, actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.account.model.AccountEntry)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<AccountEntry> modelResourcePermission) {

		_accountEntryModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<AccountEntry>
		_accountEntryModelResourcePermission;

}