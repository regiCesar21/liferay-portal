/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.service.impl;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.exception.DuplicateAccountGroupAccountEntryRelException;
import com.liferay.account.model.AccountGroupAccountEntryRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.account.service.base.AccountGroupAccountEntryRelLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.account.model.AccountGroupAccountEntryRel",
	service = AopService.class
)
public class AccountGroupAccountEntryRelLocalServiceImpl
	extends AccountGroupAccountEntryRelLocalServiceBaseImpl {

	@Override
	public AccountGroupAccountEntryRel addAccountGroupAccountEntryRel(
			long accountGroupId, long accountEntryId)
		throws PortalException {

		AccountGroupAccountEntryRel accountGroupAccountEntryRel =
			accountGroupAccountEntryRelPersistence.fetchByAGI_AEI(
				accountGroupId, accountEntryId);

		if (accountGroupAccountEntryRel != null) {
			throw new DuplicateAccountGroupAccountEntryRelException();
		}

		_accountGroupLocalService.getAccountGroup(accountGroupId);

		if (accountEntryId != AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT) {
			_accountEntryLocalService.getAccountEntry(accountEntryId);
		}

		accountGroupAccountEntryRel = createAccountGroupAccountEntryRel(
			counterLocalService.increment());

		accountGroupAccountEntryRel.setAccountGroupId(accountGroupId);
		accountGroupAccountEntryRel.setAccountEntryId(accountEntryId);

		return addAccountGroupAccountEntryRel(accountGroupAccountEntryRel);
	}

	@Override
	public void addAccountGroupAccountEntryRels(
			long accountGroupId, long[] accountEntryIds)
		throws PortalException {

		for (long accountEntryId : accountEntryIds) {
			addAccountGroupAccountEntryRel(accountGroupId, accountEntryId);
		}
	}

	@Override
	public void deleteAccountGroupAccountEntryRels(
			long accountGroupId, long[] accountEntryIds)
		throws PortalException {

		for (long accountEntryId : accountEntryIds) {
			accountGroupAccountEntryRelPersistence.removeByAGI_AEI(
				accountGroupId, accountEntryId);
		}
	}

	@Override
	public AccountGroupAccountEntryRel fetchAccountGroupAccountEntryRel(
		long accountGroupId, long accountEntryId) {

		return accountGroupAccountEntryRelPersistence.fetchByAGI_AEI(
			accountGroupId, accountEntryId);
	}

	@Override
	public List<AccountGroupAccountEntryRel>
		getAccountGroupAccountEntryRelsByAccountEntryId(long accountEntryId) {

		return accountGroupAccountEntryRelPersistence.findByAccountEntryId(
			accountEntryId);
	}

	@Override
	public List<AccountGroupAccountEntryRel>
		getAccountGroupAccountEntryRelsByAccountGroupId(long accountGroupId) {

		return accountGroupAccountEntryRelPersistence.findByAccountGroupId(
			accountGroupId);
	}

	@Override
	public long getAccountGroupAccountEntryRelsCountByAccountGroupId(
		long accountGroupId) {

		return accountGroupAccountEntryRelPersistence.countByAccountGroupId(
			accountGroupId);
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountGroupLocalService _accountGroupLocalService;

}