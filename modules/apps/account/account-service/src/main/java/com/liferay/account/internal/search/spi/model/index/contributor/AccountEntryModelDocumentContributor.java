/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.search.spi.model.index.contributor;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryOrganizationRelModel;
import com.liferay.account.model.AccountGroupAccountEntryRel;
import com.liferay.account.retriever.AccountUserRetriever;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.account.service.AccountGroupAccountEntryRelLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.account.model.AccountEntry",
	service = ModelDocumentContributor.class
)
public class AccountEntryModelDocumentContributor
	implements ModelDocumentContributor<AccountEntry> {

	@Override
	public void contribute(Document document, AccountEntry accountEntry) {
		document.addText(Field.DESCRIPTION, accountEntry.getDescription());
		document.addText(Field.NAME, accountEntry.getName());
		document.addKeyword(Field.STATUS, accountEntry.getStatus());
		document.addKeyword(Field.TYPE, accountEntry.getType());
		document.addKeyword(
			"accountGroupIds", _getAccountGroupIds(accountEntry));
		document.addKeyword("accountUserIds", _getAccountUserIds(accountEntry));
		document.addKeyword("domains", _getDomains(accountEntry));
		document.addKeyword(
			"organizationIds", _getOrganizationIds(accountEntry));
		document.addKeyword(
			"parentAccountEntryId", accountEntry.getParentAccountEntryId());
	}

	private long[] _getAccountGroupIds(AccountEntry accountEntry) {
		return ListUtil.toLongArray(
			_accountGroupAccountEntryRelLocalService.
				getAccountGroupAccountEntryRelsByAccountEntryId(
					accountEntry.getAccountEntryId()),
			AccountGroupAccountEntryRel::getAccountGroupId);
	}

	private long[] _getAccountUserIds(AccountEntry accountEntry) {
		return ListUtil.toLongArray(
			_accountUserRetriever.getAccountUsers(
				accountEntry.getAccountEntryId()),
			User.USER_ID_ACCESSOR);
	}

	private String[] _getDomains(AccountEntry accountEntry) {
		return ArrayUtil.toStringArray(
			StringUtil.split(accountEntry.getDomains(), CharPool.COMMA));
	}

	private long[] _getOrganizationIds(AccountEntry accountEntry) {
		return ListUtil.toLongArray(
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRels(
					accountEntry.getAccountEntryId()),
			AccountEntryOrganizationRelModel::getOrganizationId);
	}

	@Reference
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@Reference
	private AccountGroupAccountEntryRelLocalService
		_accountGroupAccountEntryRelLocalService;

	@Reference
	private AccountUserRetriever _accountUserRetriever;

}