/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.buffer;

import java.util.Collection;

/**
 * @author Michael C. Han
 * @deprecated As of Athanasius (7.3.x)
 */
@Deprecated
public class IndexerRequestBuffer {

	public static IndexerRequestBuffer create() {
		throw new UnsupportedOperationException();
	}

	public static IndexerRequestBuffer get() {
		throw new UnsupportedOperationException();
	}

	public static IndexerRequestBuffer remove() {
		throw new UnsupportedOperationException();
	}

	public void add(
		IndexerRequest indexerRequest,
		IndexerRequestBufferOverflowHandler indexerRequestBufferOverflowHandler,
		int maxBufferSize) {

		throw new UnsupportedOperationException();
	}

	public void clear() {
		throw new UnsupportedOperationException();
	}

	public Collection<IndexerRequest> getIndexerRequests() {
		throw new UnsupportedOperationException();
	}

	public boolean isEmpty() {
		throw new UnsupportedOperationException();
	}

	public void remove(IndexerRequest indexerRequest) {
		throw new UnsupportedOperationException();
	}

	public int size() {
		throw new UnsupportedOperationException();
	}

}