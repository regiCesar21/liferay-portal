/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.provisioning.internal.upgrade.v1_0_1;

import com.liferay.osb.koroneiki.phloem.rest.client.constants.ExternalLinkDomain;
import com.liferay.osb.koroneiki.phloem.rest.client.constants.ExternalLinkEntityName;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ExternalLink;
import com.liferay.osb.provisioning.koroneiki.web.service.AccountWebService;
import com.liferay.osb.provisioning.koroneiki.web.service.ExternalLinkWebService;
import com.liferay.osb.provisioning.search.FilterQuery;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matheus Santos
 */
@Component(service = UpgradeSalesforceExternalLinks.class)
public class UpgradeSalesforceExternalLinks extends UpgradeProcess {

	public void upgradeRelatedSalesforceProjectExternalLinks()
		throws Exception {

		try {
			FilterQuery filterQuery = new FilterQuery();

			filterQuery.addLambdaEquals(
				true, "externalLinkDomains", ExternalLinkDomain.SALESFORCE);

			filterQuery.addLambdaContains(
				true, "externalLinkEntityNames",
				ExternalLinkEntityName.SALESFORCE_PROJECT);

			Map<String, List<String>> salesforceProjectKeyAccountKeys =
				new HashMap<>();

			List<Account> accounts = _accountWebService.search(
				StringPool.BLANK, filterQuery, 1, 10000, StringPool.BLANK);

			for (Account account : accounts) {
				ExternalLink[] externalLinks = account.getExternalLinks();

				for (ExternalLink externalLink : externalLinks) {
					String domain = externalLink.getDomain();
					String entityName = externalLink.getEntityName();

					if (domain.equals(ExternalLinkDomain.SALESFORCE) &&
						entityName.equals(
							ExternalLinkEntityName.SALESFORCE_PROJECT)) {

						String salesforceProjectKey =
							externalLink.getEntityId();

						if (salesforceProjectKeyAccountKeys.containsKey(
								salesforceProjectKey)) {

							List<String> accountKeys =
								salesforceProjectKeyAccountKeys.get(
									salesforceProjectKey);

							accountKeys.add(account.getKey());
						}
						else {
							salesforceProjectKeyAccountKeys.put(
								salesforceProjectKey,
								Arrays.asList(account.getKey()));
						}
					}
				}
			}

			for (Map.Entry<String, List<String>> salesforceProjectKey :
					salesforceProjectKeyAccountKeys.entrySet()) {

				List<String> accountKeys = salesforceProjectKeyAccountKeys.get(
					salesforceProjectKey.getKey());

				if (accountKeys.size() > 1) {
					for (String accountKey : accountKeys) {
						ExternalLink externalLink = new ExternalLink();

						externalLink.setDomain(ExternalLinkDomain.SALESFORCE);
						externalLink.setEntityName(
							ExternalLinkEntityName.RELATED_SALESFORCE_PROJECT);
						externalLink.setEntityId(salesforceProjectKey.getKey());

						_externalLinkWebService.addAccountExternalLink(
							StringPool.BLANK, StringPool.BLANK, accountKey,
							externalLink);
					}
				}
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	public void upgradeSalesforceExternalLinks(
			Map<String, String> accountKeySalesforceKey, String entityName)
		throws Exception {

		try {
			for (Map.Entry<String, String> entry :
					accountKeySalesforceKey.entrySet()) {

				String salesforceKey = entry.getValue();

				if (salesforceKey.length() != 18) {
					_log.error(
						StringBundler.concat(
							"Skipping Salesforce ", entityName, " key ",
							entry.getKey(), " due an invalid value: ",
							salesforceKey));

					continue;
				}

				ExternalLink externalLink = new ExternalLink();

				externalLink.setDomain(ExternalLinkDomain.SALESFORCE);
				externalLink.setEntityName(entityName);
				externalLink.setEntityId(salesforceKey);

				_externalLinkWebService.addAccountExternalLink(
					StringPool.BLANK, StringPool.BLANK, entry.getKey(),
					externalLink);
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	@Override
	protected void doUpgrade() {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeSalesforceExternalLinks.class);

	@Reference
	private AccountWebService _accountWebService;

	@Reference
	private ExternalLinkWebService _externalLinkWebService;

}