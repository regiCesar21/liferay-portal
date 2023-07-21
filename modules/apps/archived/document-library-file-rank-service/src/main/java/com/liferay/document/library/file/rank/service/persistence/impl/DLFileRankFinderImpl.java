/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.file.rank.service.persistence.impl;

import com.liferay.document.library.file.rank.model.DLFileRank;
import com.liferay.document.library.file.rank.model.impl.DLFileRankImpl;
import com.liferay.document.library.file.rank.service.persistence.DLFileRankFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alexander Chow
 */
@Component(service = DLFileRankFinder.class)
public class DLFileRankFinderImpl
	extends DLFileRankFinderBaseImpl implements DLFileRankFinder {

	public static final String FIND_BY_STALE_RANKS =
		DLFileRankFinder.class.getName() + ".findByStaleRanks";

	public static final String FIND_BY_FOLDER_ID =
		DLFileRankFinder.class.getName() + ".findByFolderId";

	@Override
	public List<Object[]> findByStaleRanks(int count) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_STALE_RANKS);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar("groupId", Type.LONG);
			sqlQuery.addScalar("userId", Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(count);

			return sqlQuery.list(true);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<DLFileRank> findByFolderId(long folderId) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_FOLDER_ID);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity("DLFileRank", DLFileRankImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(folderId);

			return sqlQuery.list(true);
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