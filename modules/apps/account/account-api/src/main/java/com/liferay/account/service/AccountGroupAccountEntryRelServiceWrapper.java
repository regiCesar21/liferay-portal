/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AccountGroupAccountEntryRelService}.
 *
 * @author Brian Wing Shun Chan
 * @see AccountGroupAccountEntryRelService
 * @generated
 */
public class AccountGroupAccountEntryRelServiceWrapper
	implements AccountGroupAccountEntryRelService,
			   ServiceWrapper<AccountGroupAccountEntryRelService> {

	public AccountGroupAccountEntryRelServiceWrapper(
		AccountGroupAccountEntryRelService accountGroupAccountEntryRelService) {

		_accountGroupAccountEntryRelService =
			accountGroupAccountEntryRelService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _accountGroupAccountEntryRelService.getOSGiServiceIdentifier();
	}

	@Override
	public AccountGroupAccountEntryRelService getWrappedService() {
		return _accountGroupAccountEntryRelService;
	}

	@Override
	public void setWrappedService(
		AccountGroupAccountEntryRelService accountGroupAccountEntryRelService) {

		_accountGroupAccountEntryRelService =
			accountGroupAccountEntryRelService;
	}

	private AccountGroupAccountEntryRelService
		_accountGroupAccountEntryRelService;

}