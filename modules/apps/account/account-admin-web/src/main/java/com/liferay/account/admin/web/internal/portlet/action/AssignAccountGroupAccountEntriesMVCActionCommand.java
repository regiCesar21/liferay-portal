/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.portlet.action;

import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.account.service.AccountGroupAccountEntryRelLocalService;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.portal.kernel.messaging.proxy.ProxyModeThreadLocal;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Albert Lee
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_GROUPS_ADMIN,
		"mvc.command.name=/account_admin/assign_account_group_account_entries"
	},
	service = MVCActionCommand.class
)
public class AssignAccountGroupAccountEntriesMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long accountGroupId = ParamUtil.getLong(
			actionRequest, "accountGroupId");
		long[] accountEntryIds = ParamUtil.getLongValues(
			actionRequest, "accountEntryIds");

		try (SafeClosable safeClosable =
				ProxyModeThreadLocal.setWithSafeClosable(true)) {

			_accountGroupAccountEntryRelLocalService.
				addAccountGroupAccountEntryRels(
					accountGroupId, accountEntryIds);
		}
	}

	@Reference
	private AccountGroupAccountEntryRelLocalService
		_accountGroupAccountEntryRelLocalService;

}