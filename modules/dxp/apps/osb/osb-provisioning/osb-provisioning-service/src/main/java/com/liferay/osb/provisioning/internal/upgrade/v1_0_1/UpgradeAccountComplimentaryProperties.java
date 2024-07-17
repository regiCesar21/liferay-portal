/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.provisioning.internal.upgrade.v1_0_1;

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Product;
import com.liferay.osb.provisioning.koroneiki.constants.ProductConstants;
import com.liferay.osb.provisioning.koroneiki.web.service.AccountWebService;
import com.liferay.osb.provisioning.koroneiki.web.service.ProductWebService;
import com.liferay.osb.provisioning.search.FilterQuery;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Karoline Silva
 */
@Component(service = UpgradeAccountComplimentaryProperties.class)
public class UpgradeAccountComplimentaryProperties extends UpgradeProcess {

	public void upgradeComplimentaryProperties() throws Exception {
		String[] subscriptionProductKeys = _getSubscriptionProductKeys();

		FilterQuery filterQuery = new FilterQuery();

		filterQuery.addLambdaEquals(
			false, "activeProductKeys", subscriptionProductKeys, false);
		filterQuery.addLambdaEquals(
			false, "unactivatedProductKeys", subscriptionProductKeys, false);
		filterQuery.addEquals(true, "region", "China", true);
		filterQuery.addEquals(true, "region", "India", true);

		long totalCount = _accountWebService.searchCount(
			StringPool.BLANK, filterQuery);

		int pages = (int)Math.ceil((double)totalCount / 1000);

		for (int page = 1; page <= pages; page++) {
			List<Account> accounts = _accountWebService.search(
				StringPool.BLANK, filterQuery, page, 1000, null);

			for (Account account : accounts) {
				try {
					Map<String, String> properties = account.getProperties();

					properties.put("allowComplimentary", StringPool.TRUE);

					_accountWebService.updateAccount(
						StringPool.BLANK, StringPool.BLANK, account.getKey(),
						account);
				}
				catch (Exception exception) {
					_log.error(
						"Failed to update account: " + account.getKey(),
						exception);
				}
			}
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
	}

	private String[] _getSubscriptionProductKeys() throws Exception {
		String[] productNames = ArrayUtil.append(
			ProductConstants.NAMES_PARTNERSHIP,
			ProductConstants.NAMES_SUBSCRIPTION);

		FilterQuery filterQuery = new FilterQuery();

		filterQuery.addEquals(true, "name", productNames);

		List<Product> products = _productWebService.search(
			StringPool.BLANK, filterQuery, 1, productNames.length, null);

		List<String> subscriptionProductKeys = new ArrayList<>();

		for (Product product : products) {
			subscriptionProductKeys.add(product.getKey());
		}

		return ArrayUtil.toStringArray(subscriptionProductKeys);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeAccountComplimentaryProperties.class);

	@Reference
	private AccountWebService _accountWebService;

	@Reference
	private ProductWebService _productWebService;

}