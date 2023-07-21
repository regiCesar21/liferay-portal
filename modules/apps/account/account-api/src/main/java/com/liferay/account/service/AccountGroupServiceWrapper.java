/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AccountGroupService}.
 *
 * @author Brian Wing Shun Chan
 * @see AccountGroupService
 * @generated
 */
public class AccountGroupServiceWrapper
	implements AccountGroupService, ServiceWrapper<AccountGroupService> {

	public AccountGroupServiceWrapper(AccountGroupService accountGroupService) {
		_accountGroupService = accountGroupService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _accountGroupService.getOSGiServiceIdentifier();
	}

	@Override
	public AccountGroupService getWrappedService() {
		return _accountGroupService;
	}

	@Override
	public void setWrappedService(AccountGroupService accountGroupService) {
		_accountGroupService = accountGroupService;
	}

	private AccountGroupService _accountGroupService;

}