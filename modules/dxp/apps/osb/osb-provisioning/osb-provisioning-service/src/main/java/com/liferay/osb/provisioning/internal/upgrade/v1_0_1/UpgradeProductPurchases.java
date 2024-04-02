/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.provisioning.internal.upgrade.v1_0_1;

import com.liferay.osb.koroneiki.phloem.rest.client.constants.ExternalLinkDomain;
import com.liferay.osb.koroneiki.phloem.rest.client.constants.ExternalLinkEntityName;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Product;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.osb.provisioning.koroneiki.constants.ProductConstants;
import com.liferay.osb.provisioning.koroneiki.constants.ProductPurchaseConstants;
import com.liferay.osb.provisioning.koroneiki.web.service.AccountWebService;
import com.liferay.osb.provisioning.koroneiki.web.service.ProductPurchaseWebService;
import com.liferay.osb.provisioning.search.FilterQuery;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kyle Bischof
 */
@Component(service = UpgradeProductPurchases.class)
public class UpgradeProductPurchases extends UpgradeProcess {

	public void upgradeOpportunityProductPurchases(
			String salesforceProjectKey, String salesforceOpportunityKey)
		throws Exception {

		Account account = _getAccount(salesforceProjectKey);

		if (account == null) {
			return;
		}

		Set<String> familyAccountKeys = _getFamilyAccountKeys(
			account, salesforceProjectKey);

		FilterQuery filterQuery = new FilterQuery();

		StringBundler sb = new StringBundler(5);

		sb.append(ExternalLinkDomain.SALESFORCE);
		sb.append(StringPool.UNDERLINE);
		sb.append(ExternalLinkEntityName.SALESFORCE_OPPORTUNITY);
		sb.append(StringPool.UNDERLINE);
		sb.append(salesforceOpportunityKey);

		filterQuery.addLambdaEquals(
			true, "externalLinkEntityIds", sb.toString());

		filterQuery.addEquals(
			true, "state", ProductPurchaseConstants.STATE_ACTIVE);

		int totalCount = (int)_productPurchaseWebService.searchCount(
			filterQuery);

		int pages = (int)Math.ceil((double)totalCount / 1000);

		for (int page = 1; page <= pages; page++) {
			List<ProductPurchase> productPurchases =
				_productPurchaseWebService.search(
					filterQuery, page, 1000, StringPool.BLANK);

			for (ProductPurchase productPurchase : productPurchases) {
				String accountKey = productPurchase.getAccountKey();

				if (familyAccountKeys.contains(accountKey)) {
					continue;
				}

				try {
					productPurchase.setStatus(ProductPurchase.Status.CANCELLED);

					_productPurchaseWebService.updateProductPurchase(
						StringPool.BLANK, StringPool.BLANK,
						productPurchase.getKey(), productPurchase);
				}
				catch (Exception exception) {
					_log.error(exception, exception);
				}
			}
		}
	}

	public void upgradeProductPurchases(
			List<String> accountCodes, List<String> productKeys)
		throws Exception {

		for (String accountCode : accountCodes) {
			FilterQuery filterQuery = new FilterQuery();

			filterQuery.addEquals(true, "code", accountCode);

			List<Account> accounts = _accountWebService.search(
				StringPool.BLANK, filterQuery, 1, 1, null);

			if (accounts.isEmpty()) {
				continue;
			}

			for (String productKey : productKeys) {
				try {
					addProductPurchase(accounts.get(0), productKey);
				}
				catch (Exception exception) {
					_log.error(exception, exception);
				}
			}
		}
	}

	protected void addProductPurchase(Account account, String productKey)
		throws Exception {

		ProductPurchase productPurchase = new ProductPurchase();

		productPurchase.setAccountKey(account.getKey());
		productPurchase.setProductKey(productKey);
		productPurchase.setStatus(ProductPurchase.Status.APPROVED);
		productPurchase.setQuantity(1);

		Map<String, String> properties = new HashMap<>();

		properties.put("licenses", StringPool.TRUE);
		properties.put("sizing", String.valueOf(1));
		properties.put("version", String.valueOf(0));

		productPurchase.setProperties(properties);

		Date now = new Date();

		ProductPurchase[] productPurchases = account.getProductPurchases();

		for (ProductPurchase curProductPurchase : productPurchases) {
			if ((curProductPurchase.getEndDate() != null) &&
				now.after(curProductPurchase.getEndDate())) {

				continue;
			}

			Product product = curProductPurchase.getProduct();

			String name = product.getName();

			if (name.equals(ProductConstants.NAME_GOLD) ||
				name.equals(ProductConstants.NAME_PLATINUM)) {

				productPurchase.setEndDate(curProductPurchase.getEndDate());
				productPurchase.setExternalLinks(
					curProductPurchase.getExternalLinks());
				productPurchase.setOriginalEndDate(
					curProductPurchase.getOriginalEndDate());
				productPurchase.setStartDate(curProductPurchase.getStartDate());

				break;
			}
		}

		_productPurchaseWebService.addProductPurchase(
			StringPool.BLANK, StringPool.BLANK, account.getKey(),
			productPurchase);
	}

	@Override
	protected void doUpgrade() throws Exception {
	}

	private Account _getAccount(String salesforceProjectKey) throws Exception {
		List<Account> accounts = _accountWebService.getAccounts(
			ExternalLinkDomain.SALESFORCE,
			ExternalLinkEntityName.SALESFORCE_PROJECT, salesforceProjectKey, 1,
			1);

		if (!accounts.isEmpty()) {
			return accounts.get(0);
		}

		return null;
	}

	private Set<String> _getFamilyAccountKeys(
			Account account, String salesforceProjectKey)
		throws Exception {

		Set<String> familyAccountKeys = new HashSet<>();

		if (Validator.isNotNull(account.getParentAccountKey())) {
			FilterQuery filterQuery = new FilterQuery();

			filterQuery.addEquals(
				true, "parentAccountKey", account.getParentAccountKey());

			List<Account> siblingAccounts = _accountWebService.search(
				StringPool.BLANK, filterQuery, 1, 1000, null);

			for (Account siblingAccount : siblingAccounts) {
				familyAccountKeys.add(siblingAccount.getKey());
			}
		}

		List<Account> relatedAccounts = _accountWebService.getAccounts(
			ExternalLinkDomain.SALESFORCE,
			ExternalLinkEntityName.RELATED_SALESFORCE_PROJECT,
			salesforceProjectKey, 1, 1000);

		for (Account relatedAccount : relatedAccounts) {
			familyAccountKeys.add(relatedAccount.getKey());
		}

		return familyAccountKeys;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeProductPurchases.class);

	@Reference
	private AccountWebService _accountWebService;

	@Reference
	private ProductPurchaseWebService _productPurchaseWebService;

}