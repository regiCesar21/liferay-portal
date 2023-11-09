/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.provisioning.distributed.messaging.internal.google.pubsub;

import com.liferay.osb.distributed.messaging.google.pubsub.connector.BasePubsubSubscriber;
import com.liferay.osb.distributed.messaging.google.pubsub.connector.ServiceAccountCredentialsProvider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Will Newbury
 */
@Component(
	immediate = true,
	property = {
		"messageFilter=", "namespace=", "projectId=is-sales-uat",
		"subscription=provisioning-support-opportunity-entries",
		"topic=ebenezer-support-opportunity-entries"
	},
	service = ISSalesOpportunityPubsubSubscriber.class
)
public class ISSalesOpportunityPubsubSubscriber extends BasePubsubSubscriber {

	@Override
	protected ServiceAccountCredentialsProvider
			getServiceAccountCredentialsProvider()
		throws Exception {

		return _isSalesServiceAccountCredentialsProvider;
	}

	@Reference
	private ISSalesServiceAccountCredentialsProvider
		_isSalesServiceAccountCredentialsProvider;

}