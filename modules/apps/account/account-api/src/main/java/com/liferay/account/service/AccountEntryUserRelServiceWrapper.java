/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AccountEntryUserRelService}.
 *
 * @author Brian Wing Shun Chan
 * @see AccountEntryUserRelService
 * @generated
 */
public class AccountEntryUserRelServiceWrapper
	implements AccountEntryUserRelService,
			   ServiceWrapper<AccountEntryUserRelService> {

	public AccountEntryUserRelServiceWrapper(
		AccountEntryUserRelService accountEntryUserRelService) {

		_accountEntryUserRelService = accountEntryUserRelService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _accountEntryUserRelService.getOSGiServiceIdentifier();
	}

	@Override
	public AccountEntryUserRelService getWrappedService() {
		return _accountEntryUserRelService;
	}

	@Override
	public void setWrappedService(
		AccountEntryUserRelService accountEntryUserRelService) {

		_accountEntryUserRelService = accountEntryUserRelService;
	}

	private AccountEntryUserRelService _accountEntryUserRelService;

}