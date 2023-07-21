/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.service.persistence.impl;

import com.liferay.asset.service.persistence.AssetEntryUsageFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;

import java.util.Iterator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author     Eudaldo Alonso
 * @deprecated As of Mueller (7.2.x), replaced by {@link
 *             com.liferay.layout.service.persistence.impl.LayoutClassedModelUsageFinderImpl}
 */
@Component(service = AssetEntryUsageFinder.class)
@Deprecated
public class AssetEntryUsageFinderImpl
	extends AssetEntryUsageFinderBaseImpl implements AssetEntryUsageFinder {

	public static final String COUNT_BY_ASSET_ENTRY_ID =
		AssetEntryUsageFinder.class.getName() + ".countByAssetEntryId";

	@Override
	public int countByAssetEntryId(long assetEntryId) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_ASSET_ENTRY_ID);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(assetEntryId);

			Iterator<Long> iterator = sqlQuery.iterate();

			if (iterator.hasNext()) {
				Long count = iterator.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Reference
	private CustomSQL _customSQL;

}