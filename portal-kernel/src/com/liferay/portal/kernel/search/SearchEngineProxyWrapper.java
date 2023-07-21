/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

/**
 * @author Michael C. Han
 */
public class SearchEngineProxyWrapper implements SearchEngine {

	public SearchEngineProxyWrapper(
		SearchEngine searchEngine, IndexSearcher indexSearcher,
		IndexWriter indexWriter) {

		_searchEngine = searchEngine;
		_indexSearcher = indexSearcher;
		_indexWriter = indexWriter;
	}

	@Override
	public String backup(long companyId, String backupName)
		throws SearchException {

		return _searchEngine.backup(companyId, backupName);
	}

	@Override
	public IndexSearcher getIndexSearcher() {
		return _indexSearcher;
	}

	@Override
	public IndexWriter getIndexWriter() {
		return _indexWriter;
	}

	public SearchEngine getSearchEngine() {
		return _searchEngine;
	}

	@Override
	public String getVendor() {
		return _searchEngine.getVendor();
	}

	@Override
	public void initialize(long companyId) {
		_searchEngine.initialize(companyId);
	}

	@Override
	public void removeBackup(long companyId, String backupName)
		throws SearchException {

		_searchEngine.removeBackup(companyId, backupName);
	}

	@Override
	public void removeCompany(long companyId) {
		_searchEngine.removeCompany(companyId);
	}

	@Override
	public void restore(long companyId, String backupName)
		throws SearchException {

		_searchEngine.restore(companyId, backupName);
	}

	private final IndexSearcher _indexSearcher;
	private final IndexWriter _indexWriter;
	private final SearchEngine _searchEngine;

}