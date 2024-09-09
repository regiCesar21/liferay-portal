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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Karoline Silva
 */
@Component(service = UpgradeLXCExternalLinks.class)
public class UpgradeLXCExternalLinks extends UpgradeProcess {

	public void upgradeLXCExternalLinks(Map<String, String> lxcProjectIdMap)
		throws Exception {

		for (Map.Entry<String, String> entry : lxcProjectIdMap.entrySet()) {
			FilterQuery filterQuery = new FilterQuery();

			filterQuery.addEquals(false, "code", entry.getKey());

			List<Account> accounts = _accountWebService.search(
				StringPool.BLANK, filterQuery, 1, 1, StringPool.BLANK);

			if (accounts.isEmpty()) {
				_log.error(
					"Unable to find account with code: " + entry.getKey());

				continue;
			}

			Account account = accounts.get(0);
			boolean hasExternalLink = false;

			for (ExternalLink externalLink : account.getExternalLinks()) {
				String domain = externalLink.getDomain();
				String entityName = externalLink.getEntityName();
				String entityId = externalLink.getEntityId();

				if (domain.equals(ExternalLinkDomain.LXC) &&
					entityName.equals(ExternalLinkEntityName.LXC_PROJECT)) {

					if (!entityId.equals(entry.getValue())) {
						externalLink.setEntityId(entry.getValue());

						_externalLinkWebService.updateExternalLink(
							StringPool.BLANK, StringPool.BLANK,
							externalLink.getKey(), externalLink);
					}

					hasExternalLink = true;

					break;
				}
			}

			if (!hasExternalLink) {
				ExternalLink externalLink = new ExternalLink();

				externalLink.setDomain(ExternalLinkDomain.LXC);
				externalLink.setEntityName(ExternalLinkEntityName.LXC_PROJECT);
				externalLink.setEntityId(entry.getValue());

				_externalLinkWebService.addAccountExternalLink(
					StringPool.BLANK, StringPool.BLANK, account.getKey(),
					externalLink);
			}
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeLXCExternalLinks.class);

	@Reference
	private AccountWebService _accountWebService;

	@Reference
	private ExternalLinkWebService _externalLinkWebService;

}