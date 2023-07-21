/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.search.dummy.DummyIndexSearcher;
import com.liferay.portal.kernel.search.dummy.DummyIndexWriter;

/**
 * @author Bruno Farache
 * @author Carlos Sierra Andrés
 * @author Marcellus Tavares
 */
public class BaseSearchEngine implements SearchEngine {

	/**
	 * @throws SearchException
	 */
	@Override
	public String backup(long companyId, String backupName)
		throws SearchException {

		return null;
	}

	@Override
	public IndexSearcher getIndexSearcher() {
		return _indexSearcher;
	}

	@Override
	public IndexWriter getIndexWriter() {
		return _indexWriter;
	}

	@Override
	public String getVendor() {
		return _vendor;
	}

	@Override
	public void initialize(long companyId) {
	}

	/**
	 * @throws SearchException
	 */
	@Override
	public void removeBackup(long companyId, String backupName)
		throws SearchException {
	}

	@Override
	public void removeCompany(long companyId) {
	}

	/**
	 * @throws SearchException
	 */
	@Override
	public void restore(long companyId, String backupName)
		throws SearchException {
	}

	public void setIndexSearcher(IndexSearcher indexSearcher) {
		_indexSearcher = indexSearcher;
	}

	public void setIndexWriter(IndexWriter indexWriter) {
		_indexWriter = indexWriter;
	}

	public void setVendor(String vendor) {
		_vendor = vendor;
	}

	private IndexSearcher _indexSearcher = new DummyIndexSearcher();
	private IndexWriter _indexWriter = new DummyIndexWriter();
	private String _vendor;

}