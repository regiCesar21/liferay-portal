/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.exception.NoSuchOrderException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.order.content.web.internal.display.context.CommerceOrderContentDisplayContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	enabled = false,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT,
		"mvc.command.name=/commerce_open_order_content/edit_commerce_order"
	},
	service = MVCRenderCommand.class
)
public class EditCommerceOrderMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			CommerceOrderContentDisplayContext
				commerceOrderContentDisplayContext =
					(CommerceOrderContentDisplayContext)
						renderRequest.getAttribute(
							WebKeys.PORTLET_DISPLAY_CONTEXT);

			CommerceOrder commerceOrder =
				commerceOrderContentDisplayContext.getCommerceOrder();

			if ((commerceOrder != null) && commerceOrder.isOpen()) {
				CommerceOrder currentCommerceOrder =
					_commerceOrderHttpHelper.getCurrentCommerceOrder(
						_portal.getHttpServletRequest(renderRequest));

				if ((currentCommerceOrder == null) ||
					(commerceOrder.getCommerceOrderId() !=
						currentCommerceOrder.getCommerceOrderId())) {

					_commerceOrderHttpHelper.setCurrentCommerceOrder(
						_portal.getHttpServletRequest(renderRequest),
						commerceOrder);
				}
			}

			return "/pending_orders/edit_order.jsp";
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchOrderException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(renderRequest, exception.getClass());

				return "/error.jsp";
			}

			throw new PortletException(exception);
		}
	}

	@Reference
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;

	@Reference
	private Portal _portal;

}