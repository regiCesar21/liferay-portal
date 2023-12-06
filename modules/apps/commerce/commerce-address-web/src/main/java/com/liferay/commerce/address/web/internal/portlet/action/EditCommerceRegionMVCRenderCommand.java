/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.address.web.internal.portlet.action;

import com.liferay.commerce.address.web.internal.display.context.CommerceRegionsDisplayContext;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.exception.NoSuchRegionException;
import com.liferay.commerce.service.CommerceRegionService;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_COUNTRY,
		"mvc.command.name=/commerce_country/edit_commerce_region"
	},
	service = MVCRenderCommand.class
)
public class EditCommerceRegionMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			CommerceRegionsDisplayContext commerceRegionsDisplayContext =
				new CommerceRegionsDisplayContext(
					_actionHelper, _commerceRegionService, renderRequest,
					renderResponse);

			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT, commerceRegionsDisplayContext);
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchRegionException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(renderRequest, exception.getClass());

				return "/error.jsp";
			}
		}

		_populatePortletDisplay(renderRequest);

		return "/edit_region.jsp";
	}

	private void _populatePortletDisplay(RenderRequest renderRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		portletDisplay.setShowBackIcon(true);

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			renderRequest, CommercePortletKeys.COMMERCE_COUNTRY,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/commerce_country/edit_commerce_country");
		portletURL.setParameter(
			"countryId",
			String.valueOf(ParamUtil.getLong(renderRequest, "countryId")));
		portletURL.setParameter("screenNavigationCategoryKey", "regions");

		portletDisplay.setURLBack(portletURL.toString());
	}

	@Reference
	private ActionHelper _actionHelper;

	@Reference
	private CommerceRegionService _commerceRegionService;

	@Reference
	private Portal _portal;

}