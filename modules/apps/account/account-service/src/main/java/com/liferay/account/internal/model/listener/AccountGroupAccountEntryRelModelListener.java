/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.model.listener;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountGroupAccountEntryRel;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = ModelListener.class)
public class AccountGroupAccountEntryRelModelListener
	extends BaseModelListener<AccountGroupAccountEntryRel> {

	@Override
	public void onAfterCreate(
			AccountGroupAccountEntryRel accountGroupAccountEntryRel)
		throws ModelListenerException {

		_reindexAccountEntry(accountGroupAccountEntryRel.getAccountEntryId());
	}

	@Override
	public void onAfterRemove(
			AccountGroupAccountEntryRel accountGroupAccountEntryRel)
		throws ModelListenerException {

		_reindexAccountEntry(accountGroupAccountEntryRel.getAccountEntryId());
	}

	private void _reindexAccountEntry(long accountEntryId) {
		try {
			Indexer<AccountEntry> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(AccountEntry.class);

			indexer.reindex(AccountEntry.class.getName(), accountEntryId);
		}
		catch (SearchException searchException) {
			throw new ModelListenerException(searchException);
		}
	}

}