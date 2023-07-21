/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.portlet.action;

import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

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
		"mvc.command.name=/account_admin/edit_account_group"
	},
	service = MVCActionCommand.class
)
public class EditAccountGroupMVCActionCommand extends BaseMVCActionCommand {

	protected AccountGroup addAccountGroup(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		return _accountGroupLocalService.addAccountGroup(
			themeDisplay.getUserId(), name, description);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (cmd.equals(Constants.ADD)) {
			AccountGroup accountGroup = addAccountGroup(actionRequest);

			redirect = _http.setParameter(
				redirect, actionResponse.getNamespace() + "accountGroupId",
				accountGroup.getAccountGroupId());
		}
		else if (cmd.equals(Constants.UPDATE)) {
			updateAccountGroup(actionRequest);
		}

		if (Validator.isNotNull(redirect)) {
			sendRedirect(actionRequest, actionResponse, redirect);
		}
	}

	protected void updateAccountGroup(ActionRequest actionRequest)
		throws Exception {

		long accountGroupId = ParamUtil.getLong(
			actionRequest, "accountGroupId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		_accountGroupLocalService.updateAccountGroup(
			accountGroupId, name, description);
	}

	@Reference
	private AccountGroupLocalService _accountGroupLocalService;

	@Reference
	private Http _http;

}