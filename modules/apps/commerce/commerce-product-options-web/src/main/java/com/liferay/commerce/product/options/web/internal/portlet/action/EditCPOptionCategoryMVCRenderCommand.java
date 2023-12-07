/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.options.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.constants.CPWebKeys;
import com.liferay.commerce.product.exception.NoSuchCPOptionCategoryException;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.options.web.internal.display.context.CPOptionCategoryDisplayContext;
import com.liferay.commerce.product.service.CPOptionCategoryService;
import com.liferay.portal.kernel.exception.PortalException;
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
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_SPECIFICATION_OPTIONS,
		"mvc.command.name=/cp_specification_options/edit_cp_option_category"
	},
	service = MVCRenderCommand.class
)
public class EditCPOptionCategoryMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			CPOptionCategoryDisplayContext cpOptionCategoryDisplayContext =
				new CPOptionCategoryDisplayContext(
					_actionHelper, _portal.getHttpServletRequest(renderRequest),
					_cpOptionCategoryService);

			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				cpOptionCategoryDisplayContext);

			setCPOptionCategoryRequestAttribute(renderRequest);
			_populatePortletDisplay(renderRequest);
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchCPOptionCategoryException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(renderRequest, exception.getClass());

				return "/error.jsp";
			}

			throw new PortletException(exception);
		}

		return "/edit_cp_option_category.jsp";
	}

	protected void setCPOptionCategoryRequestAttribute(
			RenderRequest renderRequest)
		throws PortalException {

		long cpOptionCategoryId = ParamUtil.getLong(
			renderRequest, "cpOptionCategoryId");

		CPOptionCategory cpOptionCategory = null;

		if (cpOptionCategoryId > 0) {
			cpOptionCategory = _cpOptionCategoryService.getCPOptionCategory(
				cpOptionCategoryId);
		}

		renderRequest.setAttribute(
			CPWebKeys.CP_OPTION_CATEGORY, cpOptionCategory);
	}

	private void _populatePortletDisplay(RenderRequest renderRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		portletDisplay.setShowBackIcon(true);

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			renderRequest, CPPortletKeys.CP_SPECIFICATION_OPTIONS,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/cp_specification_options/view_cp_option_categories");

		portletURL.setParameter("toolbarItem", "specification-groups");

		portletDisplay.setURLBack(portletURL.toString());
	}

	@Reference
	private ActionHelper _actionHelper;

	@Reference
	private CPOptionCategoryService _cpOptionCategoryService;

	@Reference
	private Portal _portal;

}