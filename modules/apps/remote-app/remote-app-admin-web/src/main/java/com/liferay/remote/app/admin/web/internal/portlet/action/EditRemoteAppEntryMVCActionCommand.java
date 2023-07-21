/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.remote.app.admin.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.remote.app.admin.web.internal.RemoteAppPortletRegistrar;
import com.liferay.remote.app.admin.web.internal.constants.RemoteAppAdminPortletKeys;
import com.liferay.remote.app.exception.DuplicateRemoteAppEntryURLException;
import com.liferay.remote.app.exception.NoSuchEntryException;
import com.liferay.remote.app.model.RemoteAppEntry;
import com.liferay.remote.app.service.RemoteAppEntryLocalService;

import java.util.Locale;
import java.util.Map;

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
		"mvc.command.name=/remote_app_admin/edit_remote_app_entry"
	},
	service = MVCActionCommand.class
)
public class EditRemoteAppEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		String url = ParamUtil.getString(actionRequest, "url");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			RemoteAppEntry.class.getName(), actionRequest);

		try {
			if (cmd.equals(Constants.ADD)) {
				RemoteAppEntry remoteAppEntry =
					_remoteAppEntryLocalService.addRemoteAppEntry(
						serviceContext.getUserId(), nameMap, url,
						serviceContext);

				_remoteAppPortletRegistrar.registerPortlet(remoteAppEntry);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				long remoteAppEntryId = ParamUtil.getLong(
					actionRequest, "remoteAppEntryId");

				RemoteAppEntry remoteAppEntry =
					_remoteAppEntryLocalService.updateRemoteAppEntry(
						remoteAppEntryId, nameMap, url, serviceContext);

				_remoteAppPortletRegistrar.unregisterPortlet(remoteAppEntry);

				_remoteAppPortletRegistrar.registerPortlet(remoteAppEntry);
			}

			if (Validator.isNotNull(redirect)) {
				actionResponse.sendRedirect(redirect);
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchEntryException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter(
					"mvcPath", "/admin/error.jsp");
			}
			else if (exception instanceof DuplicateRemoteAppEntryURLException) {
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