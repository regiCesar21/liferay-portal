/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.commerce.provisioning.web.internal.portlet;

import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.commerce.service.CommerceSubscriptionEntryLocalService;
import com.liferay.osb.commerce.provisioning.util.OSBCommercePortalInstance;
import com.liferay.osb.commerce.provisioning.web.internal.constants.OSBCommerceProvisioningPortletKeys;
import com.liferay.osb.commerce.provisioning.web.internal.portlet.display.context.TrialRegistrationDisplayContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
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
 * @author Gianmarco Brunialti Masera
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.fragment.entry.processor.portlet.alias=osb-commerce-trial-registration",
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-osb-commerce-trial-registration",
		"com.liferay.portlet.display-category=category.osb-commerce-provisioning",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"javax.portlet.display-name=Trial Registration",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.view-template=/trial_registration/view.jsp",
		"javax.portlet.name=" + OSBCommerceProvisioningPortletKeys.TRIAL_REGISTRATION,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = {Portlet.class, TrialRegistrationPortlet.class}
)
public class TrialRegistrationPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		TrialRegistrationDisplayContext trialRegistrationDisplayContext =
			new TrialRegistrationDisplayContext(
				_commerceCountryService, _commerceSubscriptionEntryLocalService,
				_osbCommercePortalInstance);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, trialRegistrationDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	@Reference
	private CommerceCountryService _commerceCountryService;

	@Reference
	private CommerceSubscriptionEntryLocalService
		_commerceSubscriptionEntryLocalService;

	@Reference
	private OSBCommercePortalInstance _osbCommercePortalInstance;

}