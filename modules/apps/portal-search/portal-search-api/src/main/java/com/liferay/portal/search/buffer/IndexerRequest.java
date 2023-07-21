/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.buffer;

import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.search.Indexer;

import java.lang.reflect.Method;

/**
 * @author Michael C. Han
 * @deprecated As of Athanasius (7.3.x)
 */
@Deprecated
public class IndexerRequest {

	public IndexerRequest(
		Method method, ClassedModel classedModel, Indexer<?> indexer) {

		throw new UnsupportedOperationException();
	}

	public IndexerRequest(
		Method method, Indexer<?> indexer, String modelClassName,
		Long modelPrimaryKey) {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean equals(Object object) {
		throw new UnsupportedOperationException();
	}

	public void execute() throws Exception {
		throw new UnsupportedOperationException();
	}

	public String getSearchEngineId() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int hashCode() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		throw new UnsupportedOperationException();
	}

}