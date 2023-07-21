/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.redirect.service.RedirectEntryLocalService;
import com.liferay.redirect.web.internal.constants.RedirectPortletKeys;
import com.liferay.redirect.web.internal.constants.RedirectWebKeys;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + RedirectPortletKeys.REDIRECT,
		"mvc.command.name=/redirect/info_panel"
	},
	service = MVCResourceCommand.class
)
public class InfoPanelMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		LongStream longStream = Arrays.stream(
			ParamUtil.getLongValues(resourceRequest, "rowIds"));

		resourceRequest.setAttribute(
			RedirectWebKeys.REDIRECT_ENTRIES,
			longStream.mapToObj(
				_redirectEntryLocalService::fetchRedirectEntry
			).collect(
				Collectors.toList()
			));

		include(resourceRequest, resourceResponse, "/info_panel.jsp");
	}

	@Reference
	private RedirectEntryLocalService _redirectEntryLocalService;

}