/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.remote.app.admin.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.remote.app.admin.web.internal.constants.RemoteAppAdminPortletKeys;
import com.liferay.remote.app.admin.web.internal.constants.RemoteAppAdminWebKeys;
import com.liferay.remote.app.admin.web.internal.display.context.RemoteAppAdminDisplayContext;
import com.liferay.remote.app.service.RemoteAppEntryLocalService;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + RemoteAppAdminPortletKeys.REMOTE_APP_ADMIN,
		"mvc.command.name=/"
	},
	service = MVCRenderCommand.class
)
public class ViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		renderRequest.setAttribute(
			RemoteAppAdminWebKeys.REMOTE_APP_ADMIN_DISPLAY_CONTEXT,
			new RemoteAppAdminDisplayContext(
				renderRequest, renderResponse, _remoteAppEntryLocalService));

		return "/admin/view.jsp";
	}

	@Reference
	private RemoteAppEntryLocalService _remoteAppEntryLocalService;

}