/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipment.web.internal.portlet.action;

import com.liferay.commerce.address.CommerceAddressFormatter;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceRegionService;
import com.liferay.commerce.shipment.web.internal.display.context.CommerceShipmentDisplayContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
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
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_SHIPMENT,
		"mvc.command.name=/commerce_shipment/edit_commerce_shipment"
	},
	service = MVCRenderCommand.class
)
public class EditCommerceShipmentMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		CommerceShipmentDisplayContext commerceShipmentDisplayContext =
			new CommerceShipmentDisplayContext(
				_actionHelper, _portal.getHttpServletRequest(renderRequest),
				_commerceAddressFormatter, _commerceAddressService,
				_commerceChannelService, _commerceCountryService,
				_commerceOrderItemService, _commerceOrderLocalService,
				_commerceRegionService);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, commerceShipmentDisplayContext);

		_populatePortletDisplay(renderRequest);

		return "/edit_commerce_shipment.jsp";
	}

	private void _populatePortletDisplay(RenderRequest renderRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		portletDisplay.setShowBackIcon(true);

		long commerceOrderId = ParamUtil.getLong(
			renderRequest, "commerceOrderId");

		PortletURL portletURL = null;

		if (commerceOrderId > 0) {
			portletURL = _portal.getControlPanelPortletURL(
				renderRequest, CommercePortletKeys.COMMERCE_ORDER,
				PortletRequest.RENDER_PHASE);

			portletURL.setParameter(
				"mvcRenderCommandName", "/commerce_order/edit_commerce_order");

			portletURL.setParameter(
				"commerceOrderId", String.valueOf(commerceOrderId));
			portletURL.setParameter("screenNavigationCategoryKey", "shipments");

			portletDisplay.setURLBack(portletURL.toString());
		}
		else {
			portletURL = _portal.getControlPanelPortletURL(
				renderRequest, CommercePortletKeys.COMMERCE_SHIPMENT,
				PortletRequest.RENDER_PHASE);

			portletDisplay.setURLBack(portletURL.toString());
		}
	}

	@Reference
	private ActionHelper _actionHelper;

	@Reference
	private CommerceAddressFormatter _commerceAddressFormatter;

	@Reference
	private CommerceAddressService _commerceAddressService;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceCountryService _commerceCountryService;

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private CommerceRegionService _commerceRegionService;

	@Reference
	private Portal _portal;

}