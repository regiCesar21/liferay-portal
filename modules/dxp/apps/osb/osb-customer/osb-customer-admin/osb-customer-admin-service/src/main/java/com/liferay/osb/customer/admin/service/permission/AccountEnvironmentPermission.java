/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.admin.service.permission;

import com.liferay.osb.customer.admin.model.AccountEntry;
import com.liferay.osb.customer.admin.service.AccountEntryLocalService;
import com.liferay.osb.customer.constants.OSBActionKeys;
import com.liferay.osb.customer.constants.OSBCustomerConstants;
import com.liferay.osb.customer.koroneiki.constants.ContactRoleConstants;
import com.liferay.osb.customer.koroneiki.constants.TeamRoleConstants;
import com.liferay.osb.customer.koroneiki.web.service.ContactRoleWebService;
import com.liferay.osb.customer.koroneiki.web.service.TeamWebService;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ContactRole;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Team;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.TeamRole;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lin Cui
 */
@Component(immediate = true, service = {})
public class AccountEnvironmentPermission {

	public static void check(
			PermissionChecker permissionChecker, AccountEntry accountEntry,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, accountEntry, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long accountEntryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, accountEntryId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, AccountEntry accountEntry,
			String actionId)
		throws PortalException {

		if (permissionChecker.isOmniadmin() ||
			_roleLocalService.hasUserRole(
				permissionChecker.getUserId(),
				OSBCustomerConstants.ROLE_OSB_ADMINISTRATOR_ID)) {

			return true;
		}

		if (_organizationLocalService.hasUserOrganization(
				permissionChecker.getUserId(),
				OSBCustomerConstants.ORGANIZATION_DATA_ACCESS_EU_ID) ||
			_organizationLocalService.hasUserOrganization(
				permissionChecker.getUserId(),
				OSBCustomerConstants.ORGANIZATION_DATA_ACCESS_US_ID) ||
			_organizationLocalService.hasUserOrganization(
				permissionChecker.getUserId(),
				OSBCustomerConstants.
					ORGANIZATION_DIVISION_SUBSCRIPTION_SERVICES_ID)) {

			return true;
		}

		try {
			User user = permissionChecker.getUser();

			List<ContactRole> contactRoles =
				_contactRoleWebService.getAccountContactRoles(
					accountEntry.getKoroneikiAccountKey(), user.getUuid(), 1,
					1000);

			for (ContactRole contactRole : contactRoles) {
				String name = contactRole.getName();

				if (name.equals(
						ContactRoleConstants.NAME_SUPPORT_ADMINISTRATOR) ||
					name.equals(ContactRoleConstants.NAME_SUPPORT_REQUESTER)) {

					return true;
				}
			}

			List<ContactRole> partnerContactRoles = getPartnerContactRoles(
				accountEntry.getKoroneikiAccountKey(), user.getUuid());

			for (ContactRole contactRole : partnerContactRoles) {
				String name = contactRole.getName();

				if (name.equals(ContactRoleConstants.NAME_PARTNER_MANAGER)) {
					return true;
				}
			}

			if (actionId.equals(OSBActionKeys.DELETE)) {
				return false;
			}

			for (ContactRole contactRole : partnerContactRoles) {
				String name = contactRole.getName();

				if (name.equals(
						ContactRoleConstants.NAME_PARTNER_MARKETING_USER) ||
					name.equals(ContactRoleConstants.NAME_PARTNER_MEMBER) ||
					name.equals(ContactRoleConstants.NAME_PARTNER_SALES_USER) ||
					name.equals(
						ContactRoleConstants.NAME_PARTNER_TECHNICAL_USER)) {

					return true;
				}
			}

			if (actionId.equals(OSBActionKeys.ADD_ACCOUNT_ENVIRONMENT) ||
				actionId.equals(OSBActionKeys.UPDATE)) {

				return false;
			}

			if (contactRoles.isEmpty()) {
				return true;
			}
		}
		catch (Exception e) {
			throw new PortalException(e);
		}

		return false;
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long accountEntryId,
			String actionId)
		throws PortalException {

		AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(
			accountEntryId);

		return contains(permissionChecker, accountEntry, actionId);
	}

	protected static List<ContactRole> getPartnerContactRoles(
			String koroneikiAccountKey, String userUuid)
		throws Exception {

		List<Team> teams = _teamWebService.getAssignedTeams(
			koroneikiAccountKey);

		for (Team team : teams) {
			TeamRole[] teamRoles = team.getTeamRoles();

			if (teamRoles == null) {
				continue;
			}

			for (TeamRole teamRole : teamRoles) {
				String name = teamRole.getName();

				if (name.equals(TeamRoleConstants.NAME_FIRST_LINE_SUPPORT)) {
					return _contactRoleWebService.getTeamContactRoles(
						team.getKey(), userUuid, 1, 100);
				}
			}
		}

		return Collections.emptyList();
	}

	@Reference(unbind = "-")
	protected void setAccountEntryLocalService(
		AccountEntryLocalService accountEntryLocalService) {

		_accountEntryLocalService = accountEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setContactRoleWebService(
		ContactRoleWebService contactRoleWebService) {

		_contactRoleWebService = contactRoleWebService;
	}

	@Reference(unbind = "-")
	protected void setOrganizationLocalService(
		OrganizationLocalService organizationLocalService) {

		_organizationLocalService = organizationLocalService;
	}

	@Reference(unbind = "-")
	protected void setRoleLocalService(RoleLocalService roleLocalService) {
		_roleLocalService = roleLocalService;
	}

	@Reference(unbind = "-")
	protected void setTeamWebService(TeamWebService teamWebService) {
		_teamWebService = teamWebService;
	}

	private static AccountEntryLocalService _accountEntryLocalService;
	private static ContactRoleWebService _contactRoleWebService;
	private static OrganizationLocalService _organizationLocalService;
	private static RoleLocalService _roleLocalService;
	private static TeamWebService _teamWebService;

}