/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.display;

import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupAccountEntryRelLocalServiceUtil;
import com.liferay.account.service.AccountGroupLocalServiceUtil;
import com.liferay.petra.string.StringPool;

/**
 * @author Albert Lee
 */
public class AccountGroupDisplay {

	public static AccountGroupDisplay of(AccountGroup accountGroup) {
		if (accountGroup != null) {
			return new AccountGroupDisplay(accountGroup);
		}

		return _EMPTY_INSTANCE;
	}

	public static AccountGroupDisplay of(long accountGroupId) {
		return of(
			AccountGroupLocalServiceUtil.fetchAccountGroup(accountGroupId));
	}

	public long getAccountEntriesCount() {
		return _accountEntriesCount;
	}

	public long getAccountGroupId() {
		return _accountGroupId;
	}

	public String getDescription() {
		return _description;
	}

	public String getName() {
		return _name;
	}

	private AccountGroupDisplay() {
		_accountEntriesCount = 0;
		_accountGroupId = 0;
		_description = StringPool.BLANK;
		_name = StringPool.BLANK;
	}

	private AccountGroupDisplay(AccountGroup accountGroup) {
		_accountEntriesCount = _getAccountEntriesCount(accountGroup);
		_accountGroupId = accountGroup.getAccountGroupId();
		_description = accountGroup.getDescription();
		_name = accountGroup.getName();
	}

	private long _getAccountEntriesCount(AccountGroup accountGroup) {
		return AccountGroupAccountEntryRelLocalServiceUtil.
			getAccountGroupAccountEntryRelsCountByAccountGroupId(
				accountGroup.getAccountGroupId());
	}

	private static final AccountGroupDisplay _EMPTY_INSTANCE =
		new AccountGroupDisplay();

	private final long _accountEntriesCount;
	private final long _accountGroupId;
	private final String _description;
	private final String _name;

}