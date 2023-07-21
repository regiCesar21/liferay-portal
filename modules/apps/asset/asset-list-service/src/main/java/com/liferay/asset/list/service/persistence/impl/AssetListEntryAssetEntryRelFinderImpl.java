/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.service.persistence.impl;

import com.liferay.asset.list.service.persistence.AssetListEntryAssetEntryRelFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier de Arcos
 */
@Component(service = AssetListEntryAssetEntryRelFinder.class)
public class AssetListEntryAssetEntryRelFinderImpl
	extends AssetListEntryAssetEntryRelFinderBaseImpl
	implements AssetListEntryAssetEntryRelFinder {

	public static final String COUNT_BY_A_S =
		AssetListEntryAssetEntryRelFinder.class.getName() + ".countByA_S";

	@Override
	public int countByA_S(
		long assetListEntryId, long segmentsEntryId, boolean visible) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_A_S);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(assetListEntryId);
			queryPos.add(segmentsEntryId);
			queryPos.add(visible);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
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