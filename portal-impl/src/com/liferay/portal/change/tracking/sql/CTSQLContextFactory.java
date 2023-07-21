/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.change.tracking.sql;

import java.util.List;

/**
 * @author Preston Crary
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
@Deprecated
public interface CTSQLContextFactory {

	public CTSQLContext createCTSQLContext(
		long ctCollectionId, String tableName, String primaryColumnName,
		Class<?> clazz);

	public interface CTSQLContext {

		public List<Long> getExcludePKs();

		public boolean hasAdded();

		public boolean hasModified();

	}

}