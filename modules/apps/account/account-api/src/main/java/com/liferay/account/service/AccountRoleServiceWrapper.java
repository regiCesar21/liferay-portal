/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AccountRoleService}.
 *
 * @author Brian Wing Shun Chan
 * @see AccountRoleService
 * @generated
 */
public class AccountRoleServiceWrapper
	implements AccountRoleService, ServiceWrapper<AccountRoleService> {

	public AccountRoleServiceWrapper(AccountRoleService accountRoleService) {
		_accountRoleService = accountRoleService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _accountRoleService.getOSGiServiceIdentifier();
	}

	@Override
	public AccountRoleService getWrappedService() {
		return _accountRoleService;
	}

	@Override
	public void setWrappedService(AccountRoleService accountRoleService) {
		_accountRoleService = accountRoleService;
	}

	private AccountRoleService _accountRoleService;

}