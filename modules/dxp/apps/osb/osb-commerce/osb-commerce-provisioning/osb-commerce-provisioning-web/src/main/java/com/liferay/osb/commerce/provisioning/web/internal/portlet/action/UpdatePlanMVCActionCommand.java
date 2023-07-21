/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.commerce.provisioning.web.internal.portlet.action;

import com.liferay.commerce.service.CommerceSubscriptionEntryService;
import com.liferay.osb.commerce.provisioning.web.internal.constants.OSBCommerceProvisioningPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gianmarco Brunialti Masera
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + OSBCommerceProvisioningPortletKeys.PLAN_MANAGEMENT,
		"mvc.command.name=updatePlan"
	},
	service = {}
)
public class UpdatePlanMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = GetterUtil.getString(actionRequest);

		if (cmd.equals(Constants.DELETE)) {
			long commerceSubscriptionEntryId = ParamUtil.getLong(
				actionRequest, "commerceSubscriptionEntryId");

			_cancelPlanSubscription(actionRequest, commerceSubscriptionEntryId);
		}
	}

	private void _cancelPlanSubscription(
			ActionRequest actionRequest, long commerceSubscriptionEntryId)
		throws Exception {

		long[] deleteCommerceSubscriptionEntryIds = null;

		if (commerceSubscriptionEntryId > 0) {
			deleteCommerceSubscriptionEntryIds = new long[] {
				commerceSubscriptionEntryId
			};
		}
		else {
			deleteCommerceSubscriptionEntryIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceSubscriptionEntryIds"),
				0L);
		}

		for (long deleteCommerceSubscriptionEntryId :
				deleteCommerceSubscriptionEntryIds) {

			_commerceSubscriptionEntryService.deleteCommerceSubscriptionEntry(
				deleteCommerceSubscriptionEntryId);
		}
	}

	@Reference
	private CommerceSubscriptionEntryService _commerceSubscriptionEntryService;

}