/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.zendesk.synchronizer;

import com.liferay.osb.customer.admin.model.AccountEntry;
import com.liferay.osb.customer.admin.service.AccountEntryLocalService;
import com.liferay.osb.customer.koroneiki.constants.TeamRoleConstants;
import com.liferay.osb.customer.koroneiki.web.service.AccountWebService;
import com.liferay.osb.customer.koroneiki.web.service.TeamRoleWebService;
import com.liferay.osb.customer.zendesk.util.ZendeskMapperUtil;
import com.liferay.osb.customer.zendesk.web.service.ZendeskOrganizationMembershipWebService;
import com.liferay.osb.customer.zendesk.web.service.ZendeskUserWebService;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Team;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.TeamRole;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kyle Bischof
 */
@Component(immediate = true, service = TeamSynchronizer.class)
public class TeamSynchronizer {

	public void add(Team team, User user) throws Exception {
		if (!ZendeskSynchronizerThreadLocal.isEnabled()) {
			return;
		}

		long[] zendeskOrganizationIds = getZendeskOrganizationIds(team);

		if (ArrayUtil.isNotEmpty(zendeskOrganizationIds)) {
			long zendeskUserId = _userSynchronizer.update(user, null);

			addOrganizationMemberships(zendeskUserId, zendeskOrganizationIds);
		}
	}

	public void remove(Team team, User user) throws Exception {
		if (!ZendeskSynchronizerThreadLocal.isEnabled()) {
			return;
		}

		long[] zendeskOrganizationIds = getZendeskOrganizationIds(team);
		long zendeskUserId = _zendeskMapperUtil.fetchZendeskUserId(
			user.getUserId());

		if (ArrayUtil.isNotEmpty(zendeskOrganizationIds) &&
			(zendeskUserId > 0)) {

			removeOrganizationMemberships(
				zendeskUserId, zendeskOrganizationIds);

			_userSynchronizer.update(user, null);
		}
	}

	protected void addOrganizationMemberships(
			long zendeskUserId, long[] zendeskOrganizationIds)
		throws Exception {

		if (ArrayUtil.isEmpty(zendeskOrganizationIds)) {
			return;
		}

		_zendeskOrganizationMembershipWebService.createOrganizationMemberships(
			zendeskUserId, zendeskOrganizationIds);

		for (long zendeskOrganizationId : zendeskOrganizationIds) {
			_asyncZendeskUserWebService.
				createZendeskUserOrganizationSubscription(
					zendeskUserId, zendeskOrganizationId);
		}
	}

	protected long[] getZendeskOrganizationIds(Team team) throws Exception {
		TeamRole teamRole = _teamRoleWebService.fetchTeamRole(
			TeamRole.Type.ACCOUNT.toString(),
			TeamRoleConstants.NAME_FIRST_LINE_SUPPORT);

		if (teamRole == null) {
			return new long[0];
		}

		List<Long> zendeskOrganizationIds = new ArrayList<>();

		StringBundler sb = new StringBundler(5);

		sb.append("assignedTeamKeyTeamRoleKeys/any(s:s eq '");
		sb.append(team.getKey());
		sb.append(StringPool.UNDERLINE);
		sb.append(teamRole.getKey());
		sb.append("')");

		List<Account> accounts = _accountWebService.search(
			StringPool.BLANK, sb.toString(), 1, 1000, StringPool.BLANK);

		for (Account account : accounts) {
			AccountEntry accountEntry =
				_accountEntryLocalService.fetchKoroneikiAccountEntry(
					account.getKey());

			if ((accountEntry == null) ||
				!accountEntry.isActiveTicketSupport()) {

				continue;
			}

			long zendeskOrganizationId =
				_zendeskMapperUtil.fetchZendeskOrganizationId(
					accountEntry.getAccountEntryId());

			if (zendeskOrganizationId != 0) {
				zendeskOrganizationIds.add(zendeskOrganizationId);
			}
		}

		return ArrayUtil.toLongArray(zendeskOrganizationIds);
	}

	protected void removeOrganizationMemberships(
			long zendeskUserId, long[] zendeskOrganizationIds)
		throws Exception {

		if (ArrayUtil.isEmpty(zendeskOrganizationIds)) {
			return;
		}

		_zendeskOrganizationMembershipWebService.deleteOrganizationMemberships(
			zendeskUserId, zendeskOrganizationIds);
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountWebService _accountWebService;

	@Reference(target = "(async=true)")
	private ZendeskUserWebService _asyncZendeskUserWebService;

	@Reference
	private TeamRoleWebService _teamRoleWebService;

	@Reference
	private UserSynchronizer _userSynchronizer;

	@Reference
	private ZendeskMapperUtil _zendeskMapperUtil;

	@Reference(target = "(async=true)")
	private ZendeskOrganizationMembershipWebService
		_zendeskOrganizationMembershipWebService;

}