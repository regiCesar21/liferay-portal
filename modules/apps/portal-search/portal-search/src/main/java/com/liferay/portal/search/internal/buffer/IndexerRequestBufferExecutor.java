/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.buffer;

/**
 * @author André de Oliveira
 * @author Michael C. Han
 */
public interface IndexerRequestBufferExecutor {

	public void execute(IndexerRequestBuffer indexerRequestBuffer);

	public void execute(IndexerRequestBuffer indexerRequestBuffer, int count);

}