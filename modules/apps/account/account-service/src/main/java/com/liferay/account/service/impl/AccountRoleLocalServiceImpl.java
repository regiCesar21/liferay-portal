/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.service.impl;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.base.AccountRoleLocalServiceBaseImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.account.model.AccountRole",
	service = AopService.class
)
public class AccountRoleLocalServiceImpl
	extends AccountRoleLocalServiceBaseImpl {

	@Override
	public AccountRole addAccountRole(
			long userId, long accountEntryId, String name,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap)
		throws PortalException {

		AccountRole accountRole = createAccountRole(
			counterLocalService.increment());

		accountRole.setAccountEntryId(accountEntryId);

		User user = userLocalService.getUser(userId);

		accountRole.setCompanyId(user.getCompanyId());

		Role role = roleLocalService.addRole(
			userId, AccountRole.class.getName(), accountRole.getAccountRoleId(),
			name, titleMap, descriptionMap, RoleConstants.TYPE_PROVIDER, null,
			null);

		accountRole.setRoleId(role.getRoleId());

		return addAccountRole(accountRole);
	}

	@Override
	public void associateUser(
			long accountEntryId, long accountRoleId, long userId)
		throws PortalException {

		AccountEntry accountEntry = accountEntryPersistence.findByPrimaryKey(
			accountEntryId);

		AccountRole accountRole = getAccountRole(accountRoleId);

		userGroupRoleLocalService.addUserGroupRoles(
			userId, accountEntry.getAccountEntryGroupId(),
			new long[] {accountRole.getRoleId()});
	}

	@Override
	public AccountRole deleteAccountRole(AccountRole accountRole)
		throws PortalException {

		accountRole = super.deleteAccountRole(accountRole);

		Role role = roleLocalService.fetchRole(accountRole.getRoleId());

		if (role != null) {
			userGroupRoleLocalService.deleteUserGroupRolesByRoleId(
				accountRole.getRoleId());

			roleLocalService.deleteRole(accountRole.getRoleId());
		}

		return accountRole;
	}

	@Override
	public AccountRole deleteAccountRole(long accountRoleId)
		throws PortalException {

		return deleteAccountRole(getAccountRole(accountRoleId));
	}

	@Override
	public void deleteAccountRolesByCompanyId(long companyId) {
		if (!CompanyThreadLocal.isDeleteInProcess()) {
			throw new UnsupportedOperationException(
				"Deleting account roles by company must be called when " +
					"deleting a company");
		}

		for (AccountRole accountRole :
				accountRolePersistence.findByCompanyId(companyId)) {

			accountRolePersistence.remove(accountRole);

			userGroupRoleLocalService.deleteUserGroupRolesByRoleId(
				accountRole.getRoleId());
		}
	}

	@Override
	public AccountRole fetchAccountRoleByRoleId(long roleId) {
		return accountRolePersistence.fetchByRoleId(roleId);
	}

	@Override
	public AccountRole getAccountRoleByRoleId(long roleId)
		throws PortalException {

		return accountRolePersistence.findByRoleId(roleId);
	}

	@Override
	public List<AccountRole> getAccountRoles(long accountEntryId, long userId)
		throws PortalException {

		AccountEntry accountEntry = accountEntryPersistence.findByPrimaryKey(
			accountEntryId);

		return TransformUtil.transform(
			userGroupRoleLocalService.getUserGroupRoles(
				userId, accountEntry.getAccountEntryGroupId()),
			userGroupRole -> getAccountRoleByRoleId(userGroupRole.getRoleId()));
	}

	@Override
	public List<AccountRole> getAccountRolesByAccountEntryIds(
		long[] accountEntryIds) {

		return accountRolePersistence.findByAccountEntryId(accountEntryIds);
	}

	@Override
	public BaseModelSearchResult<AccountRole> searchAccountRoles(
		long accountEntryId, String keywords, int start, int end,
		OrderByComparator<?> orderByComparator) {

		return searchAccountRoles(
			new long[] {accountEntryId}, keywords, start, end,
			orderByComparator);
	}

	@Override
	public BaseModelSearchResult<AccountRole> searchAccountRoles(
		long[] accountEntryIds, String keywords, int start, int end,
		OrderByComparator<?> orderByComparator) {

		DynamicQuery roleDynamicQuery = _getRoleDynamicQuery(
			accountEntryIds, keywords, orderByComparator);

		if (roleDynamicQuery == null) {
			return new BaseModelSearchResult<>(
				Collections.<AccountRole>emptyList(), 0);
		}

		List<AccountRole> accountRoles = TransformUtil.transform(
			roleLocalService.<Role>dynamicQuery(roleDynamicQuery, start, end),
			userGroupRole -> getAccountRoleByRoleId(userGroupRole.getRoleId()));

		return new BaseModelSearchResult<>(
			accountRoles,
			(int)roleLocalService.dynamicQueryCount(
				_getRoleDynamicQuery(accountEntryIds, keywords, null)));
	}

	@Override
	public void unassociateUser(
			long accountEntryId, long accountRoleId, long userId)
		throws PortalException {

		AccountEntry accountEntry = accountEntryPersistence.findByPrimaryKey(
			accountEntryId);

		AccountRole accountRole = getAccountRole(accountRoleId);

		userGroupRoleLocalService.deleteUserGroupRoles(
			userId, accountEntry.getAccountEntryGroupId(),
			new long[] {accountRole.getRoleId()});
	}

	private DynamicQuery _getRoleDynamicQuery(
		long[] accountEntryIds, String keywords,
		OrderByComparator<?> orderByComparator) {

		DynamicQuery accountRoleDynamicQuery =
			accountRoleLocalService.dynamicQuery();

		accountRoleDynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"accountEntryId", ListUtil.fromArray(accountEntryIds)));
		accountRoleDynamicQuery.setProjection(
			ProjectionFactoryUtil.property("roleId"));

		List<Long> roleIds = accountRoleLocalService.dynamicQuery(
			accountRoleDynamicQuery);

		if (roleIds.isEmpty()) {
			return null;
		}

		DynamicQuery roleDynamicQuery = roleLocalService.dynamicQuery();

		roleDynamicQuery.add(RestrictionsFactoryUtil.in("roleId", roleIds));

		if (Validator.isNotNull(keywords)) {
			Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

			disjunction.add(
				RestrictionsFactoryUtil.ilike(
					"name", StringUtil.quote(keywords, StringPool.PERCENT)));
			disjunction.add(
				RestrictionsFactoryUtil.ilike(
					"description",
					StringUtil.quote(keywords, StringPool.PERCENT)));

			roleDynamicQuery.add(disjunction);
		}

		OrderFactoryUtil.addOrderByComparator(
			roleDynamicQuery, orderByComparator);

		return roleDynamicQuery;
	}

}