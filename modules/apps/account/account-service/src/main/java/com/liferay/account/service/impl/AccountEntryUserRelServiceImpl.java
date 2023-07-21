/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.service.impl;

import com.liferay.account.service.base.AccountEntryUserRelServiceBaseImpl;
import com.liferay.portal.aop.AopService;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 * @see    AccountEntryUserRelServiceBaseImpl
 */
@Component(
	property = {
		"json.web.service.context.name=account",
		"json.web.service.context.path=AccountEntryUserRel"
	},
	service = AopService.class
)
public class AccountEntryUserRelServiceImpl
	extends AccountEntryUserRelServiceBaseImpl {
}