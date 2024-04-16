/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.koroneiki.message.subscriber;

import com.liferay.osb.customer.constants.OSBCustomerConstants;
import com.liferay.osb.customer.koroneiki.constants.EntitlementConstants;
import com.liferay.osb.customer.subscription.util.DXPCloudStatusPageSubscriptionUtil;
import com.liferay.osb.customer.zendesk.constants.ZendeskDestinationNames;
import com.liferay.osb.distributed.messaging.Message;
import com.liferay.osb.distributed.messaging.subscribing.MessageSubscriber;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(
	immediate = true, property = "topic.pattern=koroneiki.entitlement.delete",
	service = EntitlementDeleteMessageSubscriber.class
)
public class EntitlementDeleteMessageSubscriber
	extends BaseMessageSubscriber implements MessageSubscriber {

	@Override
	protected void doReceive(Message message) throws Exception {
		sendMessage(
			ZendeskDestinationNames.ACCOUNT_ENTITLEMENT_SYNC,
			message.getDestinationName(), (String)message.getPayload());

		JSONObject jsonObject = jsonFactory.createJSONObject(
			(String)message.getPayload());

		JSONObject contactJSONObject = jsonObject.getJSONObject("contact");

		if (contactJSONObject == null) {
			return;
		}

		JSONObject entitlementJSONObject = jsonObject.getJSONObject(
			"entitlement");

		String name = entitlementJSONObject.getString("name");

		User user = userLocalService.fetchUserByEmailAddress(
			OSBCustomerConstants.COMPANY_ID,
			contactJSONObject.getString("emailAddress"));

		if (user == null) {
			return;
		}

		if (name.equals(EntitlementConstants.NAME_CUSTOMER_LIFERAY_PAAS)) {
			_dxpCloudStatusPageSubscriptionUtil.unsubscribe(user);
		}

		Organization organization = null;

		if (name.equals(EntitlementConstants.NAME_LIFERAY_EMPLOYEE)) {
			organization = organizationLocalService.fetchOrganization(
				OSBCustomerConstants.ORGANIZATION_LIFERAY_INC_ID);
		}
		else {
			organization = organizationLocalService.fetchOrganization(
				OSBCustomerConstants.COMPANY_ID,
				EntitlementConstants.ORGANIZATION_NAME_PREFIX + name);
		}

		if (organization == null) {
			return;
		}

		userLocalService.unsetOrganizationUsers(
			organization.getOrganizationId(), new long[] {user.getUserId()});
	}

	@Reference
	private DXPCloudStatusPageSubscriptionUtil
		_dxpCloudStatusPageSubscriptionUtil;

}