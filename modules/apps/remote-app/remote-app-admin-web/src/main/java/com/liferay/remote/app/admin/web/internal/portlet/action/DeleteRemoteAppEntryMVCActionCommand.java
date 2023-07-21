/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.remote.app.admin.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.remote.app.admin.web.internal.RemoteAppPortletRegistrar;
import com.liferay.remote.app.admin.web.internal.constants.RemoteAppAdminPortletKeys;
import com.liferay.remote.app.exception.NoSuchEntryException;
import com.liferay.remote.app.service.RemoteAppEntryLocalService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + RemoteAppAdminPortletKeys.REMOTE_APP_ADMIN,
		"mvc.command.name=/remote_app_admin/delete_remote_app_entry"
	},
	service = MVCActionCommand.class
)
public class DeleteRemoteAppEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		long remoteAppEntryId = ParamUtil.getLong(
			actionRequest, "remoteAppEntryId");

		try {
			_remoteAppPortletRegistrar.unregisterPortlet(
				_remoteAppEntryLocalService.getRemoteAppEntry(
					remoteAppEntryId));

			_remoteAppEntryLocalService.deleteRemoteAppEntry(remoteAppEntryId);

			if (Validator.isNotNull(redirect)) {
				actionResponse.sendRedirect(redirect);
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchEntryException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());
			}
			else {
				throw exception;
			}
		}
	}

	@Reference
	private RemoteAppEntryLocalService _remoteAppEntryLocalService;

	@Reference
	private RemoteAppPortletRegistrar _remoteAppPortletRegistrar;

}