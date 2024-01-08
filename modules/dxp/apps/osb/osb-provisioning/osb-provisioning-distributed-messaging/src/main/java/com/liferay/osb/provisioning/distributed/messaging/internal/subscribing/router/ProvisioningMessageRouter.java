/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.provisioning.distributed.messaging.internal.subscribing.router;

import com.liferay.osb.distributed.messaging.subscribing.router.BaseMessageRouter;
import com.liferay.osb.distributed.messaging.subscribing.router.MessageRouter;
import com.liferay.osb.provisioning.distributed.messaging.internal.subscribing.ContactMessageSubscriber;
import com.liferay.osb.provisioning.distributed.messaging.internal.subscribing.EntitlementCreateMessageSubscriber;
import com.liferay.osb.provisioning.distributed.messaging.internal.subscribing.EntitlementDeleteMessageSubscriber;
import com.liferay.osb.provisioning.distributed.messaging.internal.subscribing.OktaUsersMessageSubscriber;
import com.liferay.osb.provisioning.distributed.messaging.internal.subscribing.OpportunityMessageSubscriber;
import com.liferay.osb.provisioning.distributed.messaging.internal.subscribing.ProductMessageSubscriber;
import com.liferay.osb.provisioning.distributed.messaging.internal.subscribing.ProductPurchaseMessageSubscriber;
import com.liferay.osb.provisioning.distributed.messaging.internal.subscribing.ProjectMessageSubscriber;
import com.liferay.osb.provisioning.distributed.messaging.internal.subscribing.SalesforceCasesMessageSubscriber;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kyle Bischof
 */
@Component(immediate = true, service = MessageRouter.class)
public class ProvisioningMessageRouter extends BaseMessageRouter {

	@Reference(unbind = "-")
	protected void setContactMessageSubscriber(
		ContactMessageSubscriber contactMessageSubscriber,
		Map<String, Object> properties) {

		addRoute(contactMessageSubscriber, properties);
	}

	@Reference(unbind = "-")
	protected void setEntitlementCreateMessageSubscriber(
		EntitlementCreateMessageSubscriber entitlementCreateMessageSubscriber,
		Map<String, Object> properties) {

		addRoute(entitlementCreateMessageSubscriber, properties);
	}

	@Reference(unbind = "-")
	protected void setEntitlementDeleteMessageSubscriber(
		EntitlementDeleteMessageSubscriber entitlementDeleteMessageSubscriber,
		Map<String, Object> properties) {

		addRoute(entitlementDeleteMessageSubscriber, properties);
	}

	@Reference(unbind = "-")
	protected void setOktaUsersMessageSubscriber(
		OktaUsersMessageSubscriber oktaUsersMessageSubscriber,
		Map<String, Object> properties) {

		addRoute(oktaUsersMessageSubscriber, properties);
	}

	@Reference(unbind = "-")
	protected void setOpportunityMessageSubscriber(
		OpportunityMessageSubscriber opportunityMessageSubscriber,
		Map<String, Object> properties) {

		addRoute(opportunityMessageSubscriber, properties);
	}

	@Reference(unbind = "-")
	protected void setProductMessageSubscriber(
		ProductMessageSubscriber productMessageSubscriber,
		Map<String, Object> properties) {

		addRoute(productMessageSubscriber, properties);
	}

	@Reference(unbind = "-")
	protected void setProductPurchaseMessageSubscriber(
		ProductPurchaseMessageSubscriber productPurchaseMessageSubscriber,
		Map<String, Object> properties) {

		addRoute(productPurchaseMessageSubscriber, properties);
	}

	@Reference(unbind = "-")
	protected void setProjectMessageSubscriber(
		ProjectMessageSubscriber projectMessageSubscriber,
		Map<String, Object> properties) {

		addRoute(projectMessageSubscriber, properties);
	}

	@Reference(unbind = "-")
	protected void setSalesforceCasesMessageSubscriber(
		SalesforceCasesMessageSubscriber salesforceCasesMessageSubscriber,
		Map<String, Object> properties) {

		addRoute(salesforceCasesMessageSubscriber, properties);
	}

}