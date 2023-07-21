/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.security.permission.resource;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.permission.OrganizationPermission;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.account.model.AccountEntry",
	service = ModelResourcePermission.class
)
public class AccountEntryModelResourcePermission
	implements ModelResourcePermission<AccountEntry> {

	@Override
	public void check(
			PermissionChecker permissionChecker, AccountEntry accountEntry,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, accountEntry, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, AccountEntry.class.getName(),
				accountEntry.getAccountEntryId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long accountEntryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, accountEntryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, AccountEntry.class.getName(), accountEntryId,
				actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, AccountEntry accountEntry,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker, accountEntry.getAccountEntryId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long accountEntryId,
			String actionId)
		throws PortalException {

		List<AccountEntryOrganizationRel> accountEntryOrganizationRels =
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRels(accountEntryId);

		for (AccountEntryOrganizationRel accountEntryOrganizationRel :
				accountEntryOrganizationRels) {

			Organization organization =
				_organizationLocalService.fetchOrganization(
					accountEntryOrganizationRel.getOrganizationId());

			if (organization == null) {
				continue;
			}

			if (permissionChecker.hasPermission(
					organization.getGroupId(), AccountEntry.class.getName(),
					accountEntryId, actionId)) {

				return true;
			}

			while (!organization.isRoot()) {
				Organization parentOrganization =
					organization.getParentOrganization();

				if (_organizationPermission.contains(
						permissionChecker, parentOrganization,
						AccountActionKeys.MANAGE_SUBORGANIZATIONS_ACCOUNTS) &&
					permissionChecker.hasPermission(
						parentOrganization.getGroupId(),
						AccountEntry.class.getName(), accountEntryId,
						actionId)) {

					return true;
				}

				organization = parentOrganization;
			}
		}

		AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(
			accountEntryId);

		return permissionChecker.hasPermission(
			accountEntry.getAccountEntryGroupId(), AccountEntry.class.getName(),
			accountEntryId, actionId);
	}

	@Override
	public String getModelName() {
		return AccountEntry.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private OrganizationPermission _organizationPermission;

	@Reference(
		target = "(resource.name=" + AccountConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}