/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.commerce.provisioning.internal.messaging;

import com.liferay.commerce.constants.CommerceDestinationNames;
import com.liferay.osb.commerce.provisioning.internal.OSBCommerceProvisioningPortalInstanceInitializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(
	immediate = true,
	property = "destination.name=" + CommerceDestinationNames.ORDER_STATUS,
	service = MessageListener.class
)
public class CommerceOrderStatusMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		long commerceOrderId = message.getLong("commerceOrderId");

		try {
			_osbCommerceProvisioningPortalInstanceInitializer.
				initializePortalInstance(commerceOrderId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderStatusMessageListener.class);

	@Reference
	private OSBCommerceProvisioningPortalInstanceInitializer
		_osbCommerceProvisioningPortalInstanceInitializer;

}