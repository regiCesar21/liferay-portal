/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.provisioning.distributed.messaging.internal.upgrade;

import com.liferay.osb.koroneiki.phloem.rest.client.constants.ExternalLinkDomain;
import com.liferay.osb.koroneiki.phloem.rest.client.constants.ExternalLinkEntityName;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.osb.provisioning.distributed.messaging.internal.subscribing.util.SalesSubscriberUtil;
import com.liferay.osb.provisioning.koroneiki.constants.ProductConstants;
import com.liferay.osb.provisioning.koroneiki.web.service.AccountWebService;
import com.liferay.osb.provisioning.koroneiki.web.service.ProductPurchaseWebService;
import com.liferay.osb.provisioning.search.FilterQuery;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(service = UpgradeAccountGSInvolved.class)
public class UpgradeAccountGSInvolved extends UpgradeProcess {

	public void upgradeAccount(String salesforceProjectKey) throws Exception {
		List<Account> accounts = _accountWebService.getAccounts(
			ExternalLinkDomain.SALESFORCE,
			ExternalLinkEntityName.SALESFORCE_PROJECT, salesforceProjectKey, 1,
			1);

		if (accounts.isEmpty()) {
			_log.error(
				"Unable to find account with Salesforce project key " +
					salesforceProjectKey);

			return;
		}

		Account account = accounts.get(0);

		Map<String, String> properties = account.getProperties();

		properties.put("gsOpportunity", "true");

		_accountWebService.updateAccount(
			StringPool.BLANK, StringPool.BLANK, account.getKey(), account);

		FilterQuery filterQuery = new FilterQuery();

		filterQuery.addEquals(true, "accountKey", account.getKey());
		filterQuery.addContains(
			true, "name",
			ProductConstants.NAME_TECHNICAL_ACCOUNT_MANAGEMENT_SERVICES);

		List<ProductPurchase> productPurchases =
			_productPurchaseWebService.search(
				filterQuery, 1, 1, StringPool.BLANK);

		_salesSubscriberUtil.updateTickets(
			account, !productPurchases.isEmpty(), properties);
	}

	@Override
	protected void doUpgrade() throws Exception {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeAccountGSInvolved.class);

	@Reference
	private AccountWebService _accountWebService;

	@Reference
	private ProductPurchaseWebService _productPurchaseWebService;

	@Reference
	private SalesSubscriberUtil _salesSubscriberUtil;

}