/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.commerce.provisioning.web.internal.portlet.action;

import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.service.CommerceSubscriptionEntryLocalService;
import com.liferay.osb.commerce.provisioning.web.internal.constants.OSBCommerceProvisioningPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + OSBCommerceProvisioningPortletKeys.PLAN_MANAGEMENT,
		"mvc.command.name=updatePortalInstanceFeature"
	},
	service = MVCResourceCommand.class
)
public class UpdatePortalInstanceFeatureMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long commerceSubscriptionEntryId = ParamUtil.getLong(
			resourceRequest, "commerceSubscriptionEntryId");
		String featureName = ParamUtil.getString(
			resourceRequest, "featureName");
		boolean active = ParamUtil.getBoolean(resourceRequest, "active");

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			_getCommerceSubscriptionEntry(commerceSubscriptionEntryId);

		UnicodeProperties unicodeProperties =
			commerceSubscriptionEntry.getSubscriptionTypeSettingsProperties();

		if (active) {
			unicodeProperties.put(featureName, String.valueOf(featureName));
		}
		else {
			unicodeProperties.remove(featureName);
		}

		commerceSubscriptionEntry.setSubscriptionTypeSettingsProperties(
			unicodeProperties);

		_commerceSubscriptionEntryLocalService.updateCommerceSubscriptionEntry(
			commerceSubscriptionEntry);
	}

	private CommerceSubscriptionEntry _getCommerceSubscriptionEntry(
		long commerceSubscriptionEntryId) {

		return _commerceSubscriptionEntryLocalService.
			fetchCommerceSubscriptionEntry(commerceSubscriptionEntryId);
	}

	@Reference
	private CommerceSubscriptionEntryLocalService
		_commerceSubscriptionEntryLocalService;

}