/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.service.RedirectEntryService;
import com.liferay.redirect.web.internal.constants.RedirectPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"javax.portlet.name=" + RedirectPortletKeys.REDIRECT,
		"mvc.command.name=/redirect/edit_redirect_entry"
	},
	service = MVCRenderCommand.class
)
public class EditRedirectEntryMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			renderRequest.setAttribute(
				RedirectEntry.class.getName(),
				_redirectEntryService.fetchRedirectEntry(
					ParamUtil.getLong(renderRequest, "redirectEntryId")));
		}
		catch (PortalException portalException) {
			throw new PortletException(portalException);
		}

		return "/edit_redirect_entry.jsp";
	}

	@Reference
	private RedirectEntryService _redirectEntryService;

}