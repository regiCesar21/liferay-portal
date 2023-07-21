/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.commerce.provisioning.web.internal.portlet;

import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.osb.commerce.provisioning.web.internal.constants.OSBCommerceProvisioningPortletKeys;
import com.liferay.osb.commerce.provisioning.web.internal.display.context.CommerceAccountDisplayContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.fragment.entry.processor.portlet.alias=osb-commerce-settings",
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-osb-commerce-settings",
		"com.liferay.portlet.display-category=category.osb-commerce-provisioning",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"javax.portlet.display-name=Settings",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.view-template=/settings/view.jsp",
		"javax.portlet.name=" + OSBCommerceProvisioningPortletKeys.SETTINGS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = {Portlet.class, SettingsPortlet.class}
)
public class SettingsPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		CommerceAccountDisplayContext commerceAccountDisplayContext =
			new CommerceAccountDisplayContext(
				_commerceAccountService, null, null,
				_portal.getHttpServletRequest(renderRequest),
				_userLocalService);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, commerceAccountDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	@Reference
	private CommerceAccountService _commerceAccountService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}