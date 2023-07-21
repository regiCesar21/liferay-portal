/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.web.internal.constants.SegmentsWebKeys;
import com.liferay.segments.web.internal.display.context.SelectOrganizationsDisplayContext;
import com.liferay.segments.web.internal.display.context.SelectOrganizationsManagementToolbarDisplayContext;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SegmentsPortletKeys.SEGMENTS,
		"mvc.command.name=/segments/select_organizations"
	},
	service = MVCRenderCommand.class
)
public class SelectOrganizationsMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			SelectOrganizationsDisplayContext
				selectOrganizationsDisplayContext =
					new SelectOrganizationsDisplayContext(
						_portal.getHttpServletRequest(renderRequest),
						renderRequest, renderResponse,
						_organizationLocalService);

			renderRequest.setAttribute(
				SegmentsWebKeys.
					SEGMENTS_SELECT_ORGANIZATION_MANAGEMENT_TOOLBAL_DISPLAY_CONTEXT,
				new SelectOrganizationsManagementToolbarDisplayContext(
					_portal.getHttpServletRequest(renderRequest),
					_portal.getLiferayPortletRequest(renderRequest),
					_portal.getLiferayPortletResponse(renderResponse),
					selectOrganizationsDisplayContext));
			renderRequest.setAttribute(
				SegmentsWebKeys.SELECT_ORGANIZATIONS_DISPLAY_CONTEXT,
				selectOrganizationsDisplayContext);

			return "/field/select_organizations.jsp";
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private Portal _portal;

}