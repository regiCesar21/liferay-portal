/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.loop.asset.entry.set.service.persistence.impl;

import com.liferay.osb.loop.asset.entry.set.exception.NoSuchAssetEntrySetException;
import com.liferay.osb.loop.asset.entry.set.model.AssetEntrySet;
import com.liferay.osb.loop.asset.entry.set.model.impl.AssetEntrySetImpl;
import com.liferay.osb.loop.asset.entry.set.model.impl.AssetEntrySetModelImpl;
import com.liferay.osb.loop.asset.entry.set.service.persistence.AssetEntrySetPersistence;
import com.liferay.osb.loop.asset.entry.set.service.persistence.AssetEntrySetUtil;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the asset entry set service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetEntrySetPersistenceImpl
	extends BasePersistenceImpl<AssetEntrySet>
	implements AssetEntrySetPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AssetEntrySetUtil</code> to access the asset entry set persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AssetEntrySetImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByParentAssetEntrySetId;
	private FinderPath _finderPathWithoutPaginationFindByParentAssetEntrySetId;
	private FinderPath _finderPathCountByParentAssetEntrySetId;

	/**
	 * Returns all the asset entry sets where parentAssetEntrySetId = &#63;.
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @return the matching asset entry sets
	 */
	@Override
	public List<AssetEntrySet> findByParentAssetEntrySetId(
		long parentAssetEntrySetId) {

		return findByParentAssetEntrySetId(
			parentAssetEntrySetId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry sets where parentAssetEntrySetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @return the range of matching asset entry sets
	 */
	@Override
	public List<AssetEntrySet> findByParentAssetEntrySetId(
		long parentAssetEntrySetId, int start, int end) {

		return findByParentAssetEntrySetId(
			parentAssetEntrySetId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry sets where parentAssetEntrySetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry sets
	 */
	@Override
	public List<AssetEntrySet> findByParentAssetEntrySetId(
		long parentAssetEntrySetId, int start, int end,
		OrderByComparator<AssetEntrySet> orderByComparator) {

		return findByParentAssetEntrySetId(
			parentAssetEntrySetId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entry sets where parentAssetEntrySetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entry sets
	 */
	@Override
	public List<AssetEntrySet> findByParentAssetEntrySetId(
		long parentAssetEntrySetId, int start, int end,
		OrderByComparator<AssetEntrySet> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByParentAssetEntrySetId;
				finderArgs = new Object[] {parentAssetEntrySetId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByParentAssetEntrySetId;
			finderArgs = new Object[] {
				parentAssetEntrySetId, start, end, orderByComparator
			};
		}

		List<AssetEntrySet> list = null;

		if (useFinderCache) {
			list = (List<AssetEntrySet>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntrySet assetEntrySet : list) {
					if (parentAssetEntrySetId !=
							assetEntrySet.getParentAssetEntrySetId()) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_ASSETENTRYSET_WHERE);

			sb.append(
				_FINDER_COLUMN_PARENTASSETENTRYSETID_PARENTASSETENTRYSETID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(parentAssetEntrySetId);

				list = (List<AssetEntrySet>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset entry set in the ordered set where parentAssetEntrySetId = &#63;.
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry set
	 * @throws NoSuchAssetEntrySetException if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet findByParentAssetEntrySetId_First(
			long parentAssetEntrySetId,
			OrderByComparator<AssetEntrySet> orderByComparator)
		throws NoSuchAssetEntrySetException {

		AssetEntrySet assetEntrySet = fetchByParentAssetEntrySetId_First(
			parentAssetEntrySetId, orderByComparator);

		if (assetEntrySet != null) {
			return assetEntrySet;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("parentAssetEntrySetId=");
		sb.append(parentAssetEntrySetId);

		sb.append("}");

		throw new NoSuchAssetEntrySetException(sb.toString());
	}

	/**
	 * Returns the first asset entry set in the ordered set where parentAssetEntrySetId = &#63;.
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry set, or <code>null</code> if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet fetchByParentAssetEntrySetId_First(
		long parentAssetEntrySetId,
		OrderByComparator<AssetEntrySet> orderByComparator) {

		List<AssetEntrySet> list = findByParentAssetEntrySetId(
			parentAssetEntrySetId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry set in the ordered set where parentAssetEntrySetId = &#63;.
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry set
	 * @throws NoSuchAssetEntrySetException if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet findByParentAssetEntrySetId_Last(
			long parentAssetEntrySetId,
			OrderByComparator<AssetEntrySet> orderByComparator)
		throws NoSuchAssetEntrySetException {

		AssetEntrySet assetEntrySet = fetchByParentAssetEntrySetId_Last(
			parentAssetEntrySetId, orderByComparator);

		if (assetEntrySet != null) {
			return assetEntrySet;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("parentAssetEntrySetId=");
		sb.append(parentAssetEntrySetId);

		sb.append("}");

		throw new NoSuchAssetEntrySetException(sb.toString());
	}

	/**
	 * Returns the last asset entry set in the ordered set where parentAssetEntrySetId = &#63;.
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry set, or <code>null</code> if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet fetchByParentAssetEntrySetId_Last(
		long parentAssetEntrySetId,
		OrderByComparator<AssetEntrySet> orderByComparator) {

		int count = countByParentAssetEntrySetId(parentAssetEntrySetId);

		if (count == 0) {
			return null;
		}

		List<AssetEntrySet> list = findByParentAssetEntrySetId(
			parentAssetEntrySetId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entry sets before and after the current asset entry set in the ordered set where parentAssetEntrySetId = &#63;.
	 *
	 * @param assetEntrySetId the primary key of the current asset entry set
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry set
	 * @throws NoSuchAssetEntrySetException if a asset entry set with the primary key could not be found
	 */
	@Override
	public AssetEntrySet[] findByParentAssetEntrySetId_PrevAndNext(
			long assetEntrySetId, long parentAssetEntrySetId,
			OrderByComparator<AssetEntrySet> orderByComparator)
		throws NoSuchAssetEntrySetException {

		AssetEntrySet assetEntrySet = findByPrimaryKey(assetEntrySetId);

		Session session = null;

		try {
			session = openSession();

			AssetEntrySet[] array = new AssetEntrySetImpl[3];

			array[0] = getByParentAssetEntrySetId_PrevAndNext(
				session, assetEntrySet, parentAssetEntrySetId,
				orderByComparator, true);

			array[1] = assetEntrySet;

			array[2] = getByParentAssetEntrySetId_PrevAndNext(
				session, assetEntrySet, parentAssetEntrySetId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntrySet getByParentAssetEntrySetId_PrevAndNext(
		Session session, AssetEntrySet assetEntrySet,
		long parentAssetEntrySetId,
		OrderByComparator<AssetEntrySet> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ASSETENTRYSET_WHERE);

		sb.append(_FINDER_COLUMN_PARENTASSETENTRYSETID_PARENTASSETENTRYSETID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(AssetEntrySetModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(parentAssetEntrySetId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetEntrySet)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetEntrySet> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset entry sets that the user has permission to view where parentAssetEntrySetId = &#63;.
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @return the matching asset entry sets that the user has permission to view
	 */
	@Override
	public List<AssetEntrySet> filterFindByParentAssetEntrySetId(
		long parentAssetEntrySetId) {

		return filterFindByParentAssetEntrySetId(
			parentAssetEntrySetId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry sets that the user has permission to view where parentAssetEntrySetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @return the range of matching asset entry sets that the user has permission to view
	 */
	@Override
	public List<AssetEntrySet> filterFindByParentAssetEntrySetId(
		long parentAssetEntrySetId, int start, int end) {

		return filterFindByParentAssetEntrySetId(
			parentAssetEntrySetId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry sets that the user has permissions to view where parentAssetEntrySetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry sets that the user has permission to view
	 */
	@Override
	public List<AssetEntrySet> filterFindByParentAssetEntrySetId(
		long parentAssetEntrySetId, int start, int end,
		OrderByComparator<AssetEntrySet> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByParentAssetEntrySetId(
				parentAssetEntrySetId, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETENTRYSET_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETENTRYSET_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_PARENTASSETENTRYSETID_PARENTASSETENTRYSETID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETENTRYSET_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetEntrySet.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, AssetEntrySetImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, AssetEntrySetImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(parentAssetEntrySetId);

			return (List<AssetEntrySet>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the asset entry sets before and after the current asset entry set in the ordered set of asset entry sets that the user has permission to view where parentAssetEntrySetId = &#63;.
	 *
	 * @param assetEntrySetId the primary key of the current asset entry set
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry set
	 * @throws NoSuchAssetEntrySetException if a asset entry set with the primary key could not be found
	 */
	@Override
	public AssetEntrySet[] filterFindByParentAssetEntrySetId_PrevAndNext(
			long assetEntrySetId, long parentAssetEntrySetId,
			OrderByComparator<AssetEntrySet> orderByComparator)
		throws NoSuchAssetEntrySetException {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByParentAssetEntrySetId_PrevAndNext(
				assetEntrySetId, parentAssetEntrySetId, orderByComparator);
		}

		AssetEntrySet assetEntrySet = findByPrimaryKey(assetEntrySetId);

		Session session = null;

		try {
			session = openSession();

			AssetEntrySet[] array = new AssetEntrySetImpl[3];

			array[0] = filterGetByParentAssetEntrySetId_PrevAndNext(
				session, assetEntrySet, parentAssetEntrySetId,
				orderByComparator, true);

			array[1] = assetEntrySet;

			array[2] = filterGetByParentAssetEntrySetId_PrevAndNext(
				session, assetEntrySet, parentAssetEntrySetId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntrySet filterGetByParentAssetEntrySetId_PrevAndNext(
		Session session, AssetEntrySet assetEntrySet,
		long parentAssetEntrySetId,
		OrderByComparator<AssetEntrySet> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETENTRYSET_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETENTRYSET_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_PARENTASSETENTRYSETID_PARENTASSETENTRYSETID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETENTRYSET_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetEntrySet.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, AssetEntrySetImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, AssetEntrySetImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(parentAssetEntrySetId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetEntrySet)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetEntrySet> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entry sets where parentAssetEntrySetId = &#63; from the database.
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 */
	@Override
	public void removeByParentAssetEntrySetId(long parentAssetEntrySetId) {
		for (AssetEntrySet assetEntrySet :
				findByParentAssetEntrySetId(
					parentAssetEntrySetId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetEntrySet);
		}
	}

	/**
	 * Returns the number of asset entry sets where parentAssetEntrySetId = &#63;.
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @return the number of matching asset entry sets
	 */
	@Override
	public int countByParentAssetEntrySetId(long parentAssetEntrySetId) {
		FinderPath finderPath = _finderPathCountByParentAssetEntrySetId;

		Object[] finderArgs = new Object[] {parentAssetEntrySetId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ASSETENTRYSET_WHERE);

			sb.append(
				_FINDER_COLUMN_PARENTASSETENTRYSETID_PARENTASSETENTRYSETID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(parentAssetEntrySetId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset entry sets that the user has permission to view where parentAssetEntrySetId = &#63;.
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @return the number of matching asset entry sets that the user has permission to view
	 */
	@Override
	public int filterCountByParentAssetEntrySetId(long parentAssetEntrySetId) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByParentAssetEntrySetId(parentAssetEntrySetId);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_ASSETENTRYSET_WHERE);

		sb.append(_FINDER_COLUMN_PARENTASSETENTRYSETID_PARENTASSETENTRYSETID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetEntrySet.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(parentAssetEntrySetId);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String
		_FINDER_COLUMN_PARENTASSETENTRYSETID_PARENTASSETENTRYSETID_2 =
			"assetEntrySet.parentAssetEntrySetId = ?";

	private FinderPath _finderPathWithPaginationFindByGtCT_PAESI;
	private FinderPath _finderPathWithPaginationCountByGtCT_PAESI;

	/**
	 * Returns all the asset entry sets where createTime &gt; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @return the matching asset entry sets
	 */
	@Override
	public List<AssetEntrySet> findByGtCT_PAESI(
		long createTime, long parentAssetEntrySetId) {

		return findByGtCT_PAESI(
			createTime, parentAssetEntrySetId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry sets where createTime &gt; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @return the range of matching asset entry sets
	 */
	@Override
	public List<AssetEntrySet> findByGtCT_PAESI(
		long createTime, long parentAssetEntrySetId, int start, int end) {

		return findByGtCT_PAESI(
			createTime, parentAssetEntrySetId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry sets where createTime &gt; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry sets
	 */
	@Override
	public List<AssetEntrySet> findByGtCT_PAESI(
		long createTime, long parentAssetEntrySetId, int start, int end,
		OrderByComparator<AssetEntrySet> orderByComparator) {

		return findByGtCT_PAESI(
			createTime, parentAssetEntrySetId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the asset entry sets where createTime &gt; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entry sets
	 */
	@Override
	public List<AssetEntrySet> findByGtCT_PAESI(
		long createTime, long parentAssetEntrySetId, int start, int end,
		OrderByComparator<AssetEntrySet> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByGtCT_PAESI;
		finderArgs = new Object[] {
			createTime, parentAssetEntrySetId, start, end, orderByComparator
		};

		List<AssetEntrySet> list = null;

		if (useFinderCache) {
			list = (List<AssetEntrySet>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntrySet assetEntrySet : list) {
					if ((createTime >= assetEntrySet.getCreateTime()) ||
						(parentAssetEntrySetId !=
							assetEntrySet.getParentAssetEntrySetId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_ASSETENTRYSET_WHERE);

			sb.append(_FINDER_COLUMN_GTCT_PAESI_CREATETIME_2);

			sb.append(_FINDER_COLUMN_GTCT_PAESI_PARENTASSETENTRYSETID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(createTime);

				queryPos.add(parentAssetEntrySetId);

				list = (List<AssetEntrySet>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset entry set in the ordered set where createTime &gt; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry set
	 * @throws NoSuchAssetEntrySetException if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet findByGtCT_PAESI_First(
			long createTime, long parentAssetEntrySetId,
			OrderByComparator<AssetEntrySet> orderByComparator)
		throws NoSuchAssetEntrySetException {

		AssetEntrySet assetEntrySet = fetchByGtCT_PAESI_First(
			createTime, parentAssetEntrySetId, orderByComparator);

		if (assetEntrySet != null) {
			return assetEntrySet;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("createTime>");
		sb.append(createTime);

		sb.append(", parentAssetEntrySetId=");
		sb.append(parentAssetEntrySetId);

		sb.append("}");

		throw new NoSuchAssetEntrySetException(sb.toString());
	}

	/**
	 * Returns the first asset entry set in the ordered set where createTime &gt; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry set, or <code>null</code> if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet fetchByGtCT_PAESI_First(
		long createTime, long parentAssetEntrySetId,
		OrderByComparator<AssetEntrySet> orderByComparator) {

		List<AssetEntrySet> list = findByGtCT_PAESI(
			createTime, parentAssetEntrySetId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry set in the ordered set where createTime &gt; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry set
	 * @throws NoSuchAssetEntrySetException if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet findByGtCT_PAESI_Last(
			long createTime, long parentAssetEntrySetId,
			OrderByComparator<AssetEntrySet> orderByComparator)
		throws NoSuchAssetEntrySetException {

		AssetEntrySet assetEntrySet = fetchByGtCT_PAESI_Last(
			createTime, parentAssetEntrySetId, orderByComparator);

		if (assetEntrySet != null) {
			return assetEntrySet;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("createTime>");
		sb.append(createTime);

		sb.append(", parentAssetEntrySetId=");
		sb.append(parentAssetEntrySetId);

		sb.append("}");

		throw new NoSuchAssetEntrySetException(sb.toString());
	}

	/**
	 * Returns the last asset entry set in the ordered set where createTime &gt; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry set, or <code>null</code> if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet fetchByGtCT_PAESI_Last(
		long createTime, long parentAssetEntrySetId,
		OrderByComparator<AssetEntrySet> orderByComparator) {

		int count = countByGtCT_PAESI(createTime, parentAssetEntrySetId);

		if (count == 0) {
			return null;
		}

		List<AssetEntrySet> list = findByGtCT_PAESI(
			createTime, parentAssetEntrySetId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entry sets before and after the current asset entry set in the ordered set where createTime &gt; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * @param assetEntrySetId the primary key of the current asset entry set
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry set
	 * @throws NoSuchAssetEntrySetException if a asset entry set with the primary key could not be found
	 */
	@Override
	public AssetEntrySet[] findByGtCT_PAESI_PrevAndNext(
			long assetEntrySetId, long createTime, long parentAssetEntrySetId,
			OrderByComparator<AssetEntrySet> orderByComparator)
		throws NoSuchAssetEntrySetException {

		AssetEntrySet assetEntrySet = findByPrimaryKey(assetEntrySetId);

		Session session = null;

		try {
			session = openSession();

			AssetEntrySet[] array = new AssetEntrySetImpl[3];

			array[0] = getByGtCT_PAESI_PrevAndNext(
				session, assetEntrySet, createTime, parentAssetEntrySetId,
				orderByComparator, true);

			array[1] = assetEntrySet;

			array[2] = getByGtCT_PAESI_PrevAndNext(
				session, assetEntrySet, createTime, parentAssetEntrySetId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntrySet getByGtCT_PAESI_PrevAndNext(
		Session session, AssetEntrySet assetEntrySet, long createTime,
		long parentAssetEntrySetId,
		OrderByComparator<AssetEntrySet> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_ASSETENTRYSET_WHERE);

		sb.append(_FINDER_COLUMN_GTCT_PAESI_CREATETIME_2);

		sb.append(_FINDER_COLUMN_GTCT_PAESI_PARENTASSETENTRYSETID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(AssetEntrySetModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(createTime);

		queryPos.add(parentAssetEntrySetId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetEntrySet)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetEntrySet> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset entry sets that the user has permission to view where createTime &gt; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @return the matching asset entry sets that the user has permission to view
	 */
	@Override
	public List<AssetEntrySet> filterFindByGtCT_PAESI(
		long createTime, long parentAssetEntrySetId) {

		return filterFindByGtCT_PAESI(
			createTime, parentAssetEntrySetId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry sets that the user has permission to view where createTime &gt; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @return the range of matching asset entry sets that the user has permission to view
	 */
	@Override
	public List<AssetEntrySet> filterFindByGtCT_PAESI(
		long createTime, long parentAssetEntrySetId, int start, int end) {

		return filterFindByGtCT_PAESI(
			createTime, parentAssetEntrySetId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry sets that the user has permissions to view where createTime &gt; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry sets that the user has permission to view
	 */
	@Override
	public List<AssetEntrySet> filterFindByGtCT_PAESI(
		long createTime, long parentAssetEntrySetId, int start, int end,
		OrderByComparator<AssetEntrySet> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByGtCT_PAESI(
				createTime, parentAssetEntrySetId, start, end,
				orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETENTRYSET_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETENTRYSET_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_GTCT_PAESI_CREATETIME_2);

		sb.append(_FINDER_COLUMN_GTCT_PAESI_PARENTASSETENTRYSETID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETENTRYSET_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetEntrySet.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, AssetEntrySetImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, AssetEntrySetImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(createTime);

			queryPos.add(parentAssetEntrySetId);

			return (List<AssetEntrySet>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the asset entry sets before and after the current asset entry set in the ordered set of asset entry sets that the user has permission to view where createTime &gt; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * @param assetEntrySetId the primary key of the current asset entry set
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry set
	 * @throws NoSuchAssetEntrySetException if a asset entry set with the primary key could not be found
	 */
	@Override
	public AssetEntrySet[] filterFindByGtCT_PAESI_PrevAndNext(
			long assetEntrySetId, long createTime, long parentAssetEntrySetId,
			OrderByComparator<AssetEntrySet> orderByComparator)
		throws NoSuchAssetEntrySetException {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByGtCT_PAESI_PrevAndNext(
				assetEntrySetId, createTime, parentAssetEntrySetId,
				orderByComparator);
		}

		AssetEntrySet assetEntrySet = findByPrimaryKey(assetEntrySetId);

		Session session = null;

		try {
			session = openSession();

			AssetEntrySet[] array = new AssetEntrySetImpl[3];

			array[0] = filterGetByGtCT_PAESI_PrevAndNext(
				session, assetEntrySet, createTime, parentAssetEntrySetId,
				orderByComparator, true);

			array[1] = assetEntrySet;

			array[2] = filterGetByGtCT_PAESI_PrevAndNext(
				session, assetEntrySet, createTime, parentAssetEntrySetId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntrySet filterGetByGtCT_PAESI_PrevAndNext(
		Session session, AssetEntrySet assetEntrySet, long createTime,
		long parentAssetEntrySetId,
		OrderByComparator<AssetEntrySet> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETENTRYSET_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETENTRYSET_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_GTCT_PAESI_CREATETIME_2);

		sb.append(_FINDER_COLUMN_GTCT_PAESI_PARENTASSETENTRYSETID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETENTRYSET_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetEntrySet.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, AssetEntrySetImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, AssetEntrySetImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(createTime);

		queryPos.add(parentAssetEntrySetId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetEntrySet)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetEntrySet> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entry sets where createTime &gt; &#63; and parentAssetEntrySetId = &#63; from the database.
	 *
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 */
	@Override
	public void removeByGtCT_PAESI(
		long createTime, long parentAssetEntrySetId) {

		for (AssetEntrySet assetEntrySet :
				findByGtCT_PAESI(
					createTime, parentAssetEntrySetId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(assetEntrySet);
		}
	}

	/**
	 * Returns the number of asset entry sets where createTime &gt; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @return the number of matching asset entry sets
	 */
	@Override
	public int countByGtCT_PAESI(long createTime, long parentAssetEntrySetId) {
		FinderPath finderPath = _finderPathWithPaginationCountByGtCT_PAESI;

		Object[] finderArgs = new Object[] {createTime, parentAssetEntrySetId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ASSETENTRYSET_WHERE);

			sb.append(_FINDER_COLUMN_GTCT_PAESI_CREATETIME_2);

			sb.append(_FINDER_COLUMN_GTCT_PAESI_PARENTASSETENTRYSETID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(createTime);

				queryPos.add(parentAssetEntrySetId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset entry sets that the user has permission to view where createTime &gt; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @return the number of matching asset entry sets that the user has permission to view
	 */
	@Override
	public int filterCountByGtCT_PAESI(
		long createTime, long parentAssetEntrySetId) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByGtCT_PAESI(createTime, parentAssetEntrySetId);
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_ASSETENTRYSET_WHERE);

		sb.append(_FINDER_COLUMN_GTCT_PAESI_CREATETIME_2);

		sb.append(_FINDER_COLUMN_GTCT_PAESI_PARENTASSETENTRYSETID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetEntrySet.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(createTime);

			queryPos.add(parentAssetEntrySetId);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_GTCT_PAESI_CREATETIME_2 =
		"assetEntrySet.createTime > ? AND ";

	private static final String
		_FINDER_COLUMN_GTCT_PAESI_PARENTASSETENTRYSETID_2 =
			"assetEntrySet.parentAssetEntrySetId = ?";

	private FinderPath _finderPathWithPaginationFindByLtCT_PAESI;
	private FinderPath _finderPathWithPaginationCountByLtCT_PAESI;

	/**
	 * Returns all the asset entry sets where createTime &le; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @return the matching asset entry sets
	 */
	@Override
	public List<AssetEntrySet> findByLtCT_PAESI(
		long createTime, long parentAssetEntrySetId) {

		return findByLtCT_PAESI(
			createTime, parentAssetEntrySetId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry sets where createTime &le; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @return the range of matching asset entry sets
	 */
	@Override
	public List<AssetEntrySet> findByLtCT_PAESI(
		long createTime, long parentAssetEntrySetId, int start, int end) {

		return findByLtCT_PAESI(
			createTime, parentAssetEntrySetId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry sets where createTime &le; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry sets
	 */
	@Override
	public List<AssetEntrySet> findByLtCT_PAESI(
		long createTime, long parentAssetEntrySetId, int start, int end,
		OrderByComparator<AssetEntrySet> orderByComparator) {

		return findByLtCT_PAESI(
			createTime, parentAssetEntrySetId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the asset entry sets where createTime &le; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entry sets
	 */
	@Override
	public List<AssetEntrySet> findByLtCT_PAESI(
		long createTime, long parentAssetEntrySetId, int start, int end,
		OrderByComparator<AssetEntrySet> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByLtCT_PAESI;
		finderArgs = new Object[] {
			createTime, parentAssetEntrySetId, start, end, orderByComparator
		};

		List<AssetEntrySet> list = null;

		if (useFinderCache) {
			list = (List<AssetEntrySet>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntrySet assetEntrySet : list) {
					if ((createTime < assetEntrySet.getCreateTime()) ||
						(parentAssetEntrySetId !=
							assetEntrySet.getParentAssetEntrySetId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_ASSETENTRYSET_WHERE);

			sb.append(_FINDER_COLUMN_LTCT_PAESI_CREATETIME_2);

			sb.append(_FINDER_COLUMN_LTCT_PAESI_PARENTASSETENTRYSETID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(createTime);

				queryPos.add(parentAssetEntrySetId);

				list = (List<AssetEntrySet>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset entry set in the ordered set where createTime &le; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry set
	 * @throws NoSuchAssetEntrySetException if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet findByLtCT_PAESI_First(
			long createTime, long parentAssetEntrySetId,
			OrderByComparator<AssetEntrySet> orderByComparator)
		throws NoSuchAssetEntrySetException {

		AssetEntrySet assetEntrySet = fetchByLtCT_PAESI_First(
			createTime, parentAssetEntrySetId, orderByComparator);

		if (assetEntrySet != null) {
			return assetEntrySet;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("createTime<=");
		sb.append(createTime);

		sb.append(", parentAssetEntrySetId=");
		sb.append(parentAssetEntrySetId);

		sb.append("}");

		throw new NoSuchAssetEntrySetException(sb.toString());
	}

	/**
	 * Returns the first asset entry set in the ordered set where createTime &le; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry set, or <code>null</code> if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet fetchByLtCT_PAESI_First(
		long createTime, long parentAssetEntrySetId,
		OrderByComparator<AssetEntrySet> orderByComparator) {

		List<AssetEntrySet> list = findByLtCT_PAESI(
			createTime, parentAssetEntrySetId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry set in the ordered set where createTime &le; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry set
	 * @throws NoSuchAssetEntrySetException if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet findByLtCT_PAESI_Last(
			long createTime, long parentAssetEntrySetId,
			OrderByComparator<AssetEntrySet> orderByComparator)
		throws NoSuchAssetEntrySetException {

		AssetEntrySet assetEntrySet = fetchByLtCT_PAESI_Last(
			createTime, parentAssetEntrySetId, orderByComparator);

		if (assetEntrySet != null) {
			return assetEntrySet;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("createTime<=");
		sb.append(createTime);

		sb.append(", parentAssetEntrySetId=");
		sb.append(parentAssetEntrySetId);

		sb.append("}");

		throw new NoSuchAssetEntrySetException(sb.toString());
	}

	/**
	 * Returns the last asset entry set in the ordered set where createTime &le; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry set, or <code>null</code> if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet fetchByLtCT_PAESI_Last(
		long createTime, long parentAssetEntrySetId,
		OrderByComparator<AssetEntrySet> orderByComparator) {

		int count = countByLtCT_PAESI(createTime, parentAssetEntrySetId);

		if (count == 0) {
			return null;
		}

		List<AssetEntrySet> list = findByLtCT_PAESI(
			createTime, parentAssetEntrySetId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entry sets before and after the current asset entry set in the ordered set where createTime &le; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * @param assetEntrySetId the primary key of the current asset entry set
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry set
	 * @throws NoSuchAssetEntrySetException if a asset entry set with the primary key could not be found
	 */
	@Override
	public AssetEntrySet[] findByLtCT_PAESI_PrevAndNext(
			long assetEntrySetId, long createTime, long parentAssetEntrySetId,
			OrderByComparator<AssetEntrySet> orderByComparator)
		throws NoSuchAssetEntrySetException {

		AssetEntrySet assetEntrySet = findByPrimaryKey(assetEntrySetId);

		Session session = null;

		try {
			session = openSession();

			AssetEntrySet[] array = new AssetEntrySetImpl[3];

			array[0] = getByLtCT_PAESI_PrevAndNext(
				session, assetEntrySet, createTime, parentAssetEntrySetId,
				orderByComparator, true);

			array[1] = assetEntrySet;

			array[2] = getByLtCT_PAESI_PrevAndNext(
				session, assetEntrySet, createTime, parentAssetEntrySetId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntrySet getByLtCT_PAESI_PrevAndNext(
		Session session, AssetEntrySet assetEntrySet, long createTime,
		long parentAssetEntrySetId,
		OrderByComparator<AssetEntrySet> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_ASSETENTRYSET_WHERE);

		sb.append(_FINDER_COLUMN_LTCT_PAESI_CREATETIME_2);

		sb.append(_FINDER_COLUMN_LTCT_PAESI_PARENTASSETENTRYSETID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(AssetEntrySetModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(createTime);

		queryPos.add(parentAssetEntrySetId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetEntrySet)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetEntrySet> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset entry sets that the user has permission to view where createTime &le; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @return the matching asset entry sets that the user has permission to view
	 */
	@Override
	public List<AssetEntrySet> filterFindByLtCT_PAESI(
		long createTime, long parentAssetEntrySetId) {

		return filterFindByLtCT_PAESI(
			createTime, parentAssetEntrySetId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry sets that the user has permission to view where createTime &le; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @return the range of matching asset entry sets that the user has permission to view
	 */
	@Override
	public List<AssetEntrySet> filterFindByLtCT_PAESI(
		long createTime, long parentAssetEntrySetId, int start, int end) {

		return filterFindByLtCT_PAESI(
			createTime, parentAssetEntrySetId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry sets that the user has permissions to view where createTime &le; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry sets that the user has permission to view
	 */
	@Override
	public List<AssetEntrySet> filterFindByLtCT_PAESI(
		long createTime, long parentAssetEntrySetId, int start, int end,
		OrderByComparator<AssetEntrySet> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByLtCT_PAESI(
				createTime, parentAssetEntrySetId, start, end,
				orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETENTRYSET_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETENTRYSET_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_LTCT_PAESI_CREATETIME_2);

		sb.append(_FINDER_COLUMN_LTCT_PAESI_PARENTASSETENTRYSETID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETENTRYSET_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetEntrySet.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, AssetEntrySetImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, AssetEntrySetImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(createTime);

			queryPos.add(parentAssetEntrySetId);

			return (List<AssetEntrySet>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the asset entry sets before and after the current asset entry set in the ordered set of asset entry sets that the user has permission to view where createTime &le; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * @param assetEntrySetId the primary key of the current asset entry set
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry set
	 * @throws NoSuchAssetEntrySetException if a asset entry set with the primary key could not be found
	 */
	@Override
	public AssetEntrySet[] filterFindByLtCT_PAESI_PrevAndNext(
			long assetEntrySetId, long createTime, long parentAssetEntrySetId,
			OrderByComparator<AssetEntrySet> orderByComparator)
		throws NoSuchAssetEntrySetException {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByLtCT_PAESI_PrevAndNext(
				assetEntrySetId, createTime, parentAssetEntrySetId,
				orderByComparator);
		}

		AssetEntrySet assetEntrySet = findByPrimaryKey(assetEntrySetId);

		Session session = null;

		try {
			session = openSession();

			AssetEntrySet[] array = new AssetEntrySetImpl[3];

			array[0] = filterGetByLtCT_PAESI_PrevAndNext(
				session, assetEntrySet, createTime, parentAssetEntrySetId,
				orderByComparator, true);

			array[1] = assetEntrySet;

			array[2] = filterGetByLtCT_PAESI_PrevAndNext(
				session, assetEntrySet, createTime, parentAssetEntrySetId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntrySet filterGetByLtCT_PAESI_PrevAndNext(
		Session session, AssetEntrySet assetEntrySet, long createTime,
		long parentAssetEntrySetId,
		OrderByComparator<AssetEntrySet> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETENTRYSET_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETENTRYSET_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_LTCT_PAESI_CREATETIME_2);

		sb.append(_FINDER_COLUMN_LTCT_PAESI_PARENTASSETENTRYSETID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETENTRYSET_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetEntrySet.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, AssetEntrySetImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, AssetEntrySetImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(createTime);

		queryPos.add(parentAssetEntrySetId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetEntrySet)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetEntrySet> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entry sets where createTime &le; &#63; and parentAssetEntrySetId = &#63; from the database.
	 *
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 */
	@Override
	public void removeByLtCT_PAESI(
		long createTime, long parentAssetEntrySetId) {

		for (AssetEntrySet assetEntrySet :
				findByLtCT_PAESI(
					createTime, parentAssetEntrySetId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(assetEntrySet);
		}
	}

	/**
	 * Returns the number of asset entry sets where createTime &le; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @return the number of matching asset entry sets
	 */
	@Override
	public int countByLtCT_PAESI(long createTime, long parentAssetEntrySetId) {
		FinderPath finderPath = _finderPathWithPaginationCountByLtCT_PAESI;

		Object[] finderArgs = new Object[] {createTime, parentAssetEntrySetId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ASSETENTRYSET_WHERE);

			sb.append(_FINDER_COLUMN_LTCT_PAESI_CREATETIME_2);

			sb.append(_FINDER_COLUMN_LTCT_PAESI_PARENTASSETENTRYSETID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(createTime);

				queryPos.add(parentAssetEntrySetId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset entry sets that the user has permission to view where createTime &le; &#63; and parentAssetEntrySetId = &#63;.
	 *
	 * @param createTime the create time
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @return the number of matching asset entry sets that the user has permission to view
	 */
	@Override
	public int filterCountByLtCT_PAESI(
		long createTime, long parentAssetEntrySetId) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByLtCT_PAESI(createTime, parentAssetEntrySetId);
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_ASSETENTRYSET_WHERE);

		sb.append(_FINDER_COLUMN_LTCT_PAESI_CREATETIME_2);

		sb.append(_FINDER_COLUMN_LTCT_PAESI_PARENTASSETENTRYSETID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetEntrySet.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(createTime);

			queryPos.add(parentAssetEntrySetId);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_LTCT_PAESI_CREATETIME_2 =
		"assetEntrySet.createTime <= ? AND ";

	private static final String
		_FINDER_COLUMN_LTCT_PAESI_PARENTASSETENTRYSETID_2 =
			"assetEntrySet.parentAssetEntrySetId = ?";

	private FinderPath _finderPathWithPaginationFindByPAESI_CCNI;
	private FinderPath _finderPathWithoutPaginationFindByPAESI_CCNI;
	private FinderPath _finderPathCountByPAESI_CCNI;

	/**
	 * Returns all the asset entry sets where parentAssetEntrySetId = &#63; and creatorClassNameId = &#63;.
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param creatorClassNameId the creator class name ID
	 * @return the matching asset entry sets
	 */
	@Override
	public List<AssetEntrySet> findByPAESI_CCNI(
		long parentAssetEntrySetId, long creatorClassNameId) {

		return findByPAESI_CCNI(
			parentAssetEntrySetId, creatorClassNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry sets where parentAssetEntrySetId = &#63; and creatorClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param creatorClassNameId the creator class name ID
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @return the range of matching asset entry sets
	 */
	@Override
	public List<AssetEntrySet> findByPAESI_CCNI(
		long parentAssetEntrySetId, long creatorClassNameId, int start,
		int end) {

		return findByPAESI_CCNI(
			parentAssetEntrySetId, creatorClassNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry sets where parentAssetEntrySetId = &#63; and creatorClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param creatorClassNameId the creator class name ID
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry sets
	 */
	@Override
	public List<AssetEntrySet> findByPAESI_CCNI(
		long parentAssetEntrySetId, long creatorClassNameId, int start, int end,
		OrderByComparator<AssetEntrySet> orderByComparator) {

		return findByPAESI_CCNI(
			parentAssetEntrySetId, creatorClassNameId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entry sets where parentAssetEntrySetId = &#63; and creatorClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param creatorClassNameId the creator class name ID
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entry sets
	 */
	@Override
	public List<AssetEntrySet> findByPAESI_CCNI(
		long parentAssetEntrySetId, long creatorClassNameId, int start, int end,
		OrderByComparator<AssetEntrySet> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByPAESI_CCNI;
				finderArgs = new Object[] {
					parentAssetEntrySetId, creatorClassNameId
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByPAESI_CCNI;
			finderArgs = new Object[] {
				parentAssetEntrySetId, creatorClassNameId, start, end,
				orderByComparator
			};
		}

		List<AssetEntrySet> list = null;

		if (useFinderCache) {
			list = (List<AssetEntrySet>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntrySet assetEntrySet : list) {
					if ((parentAssetEntrySetId !=
							assetEntrySet.getParentAssetEntrySetId()) ||
						(creatorClassNameId !=
							assetEntrySet.getCreatorClassNameId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_ASSETENTRYSET_WHERE);

			sb.append(_FINDER_COLUMN_PAESI_CCNI_PARENTASSETENTRYSETID_2);

			sb.append(_FINDER_COLUMN_PAESI_CCNI_CREATORCLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(parentAssetEntrySetId);

				queryPos.add(creatorClassNameId);

				list = (List<AssetEntrySet>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset entry set in the ordered set where parentAssetEntrySetId = &#63; and creatorClassNameId = &#63;.
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param creatorClassNameId the creator class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry set
	 * @throws NoSuchAssetEntrySetException if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet findByPAESI_CCNI_First(
			long parentAssetEntrySetId, long creatorClassNameId,
			OrderByComparator<AssetEntrySet> orderByComparator)
		throws NoSuchAssetEntrySetException {

		AssetEntrySet assetEntrySet = fetchByPAESI_CCNI_First(
			parentAssetEntrySetId, creatorClassNameId, orderByComparator);

		if (assetEntrySet != null) {
			return assetEntrySet;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("parentAssetEntrySetId=");
		sb.append(parentAssetEntrySetId);

		sb.append(", creatorClassNameId=");
		sb.append(creatorClassNameId);

		sb.append("}");

		throw new NoSuchAssetEntrySetException(sb.toString());
	}

	/**
	 * Returns the first asset entry set in the ordered set where parentAssetEntrySetId = &#63; and creatorClassNameId = &#63;.
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param creatorClassNameId the creator class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry set, or <code>null</code> if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet fetchByPAESI_CCNI_First(
		long parentAssetEntrySetId, long creatorClassNameId,
		OrderByComparator<AssetEntrySet> orderByComparator) {

		List<AssetEntrySet> list = findByPAESI_CCNI(
			parentAssetEntrySetId, creatorClassNameId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry set in the ordered set where parentAssetEntrySetId = &#63; and creatorClassNameId = &#63;.
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param creatorClassNameId the creator class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry set
	 * @throws NoSuchAssetEntrySetException if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet findByPAESI_CCNI_Last(
			long parentAssetEntrySetId, long creatorClassNameId,
			OrderByComparator<AssetEntrySet> orderByComparator)
		throws NoSuchAssetEntrySetException {

		AssetEntrySet assetEntrySet = fetchByPAESI_CCNI_Last(
			parentAssetEntrySetId, creatorClassNameId, orderByComparator);

		if (assetEntrySet != null) {
			return assetEntrySet;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("parentAssetEntrySetId=");
		sb.append(parentAssetEntrySetId);

		sb.append(", creatorClassNameId=");
		sb.append(creatorClassNameId);

		sb.append("}");

		throw new NoSuchAssetEntrySetException(sb.toString());
	}

	/**
	 * Returns the last asset entry set in the ordered set where parentAssetEntrySetId = &#63; and creatorClassNameId = &#63;.
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param creatorClassNameId the creator class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry set, or <code>null</code> if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet fetchByPAESI_CCNI_Last(
		long parentAssetEntrySetId, long creatorClassNameId,
		OrderByComparator<AssetEntrySet> orderByComparator) {

		int count = countByPAESI_CCNI(
			parentAssetEntrySetId, creatorClassNameId);

		if (count == 0) {
			return null;
		}

		List<AssetEntrySet> list = findByPAESI_CCNI(
			parentAssetEntrySetId, creatorClassNameId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entry sets before and after the current asset entry set in the ordered set where parentAssetEntrySetId = &#63; and creatorClassNameId = &#63;.
	 *
	 * @param assetEntrySetId the primary key of the current asset entry set
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param creatorClassNameId the creator class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry set
	 * @throws NoSuchAssetEntrySetException if a asset entry set with the primary key could not be found
	 */
	@Override
	public AssetEntrySet[] findByPAESI_CCNI_PrevAndNext(
			long assetEntrySetId, long parentAssetEntrySetId,
			long creatorClassNameId,
			OrderByComparator<AssetEntrySet> orderByComparator)
		throws NoSuchAssetEntrySetException {

		AssetEntrySet assetEntrySet = findByPrimaryKey(assetEntrySetId);

		Session session = null;

		try {
			session = openSession();

			AssetEntrySet[] array = new AssetEntrySetImpl[3];

			array[0] = getByPAESI_CCNI_PrevAndNext(
				session, assetEntrySet, parentAssetEntrySetId,
				creatorClassNameId, orderByComparator, true);

			array[1] = assetEntrySet;

			array[2] = getByPAESI_CCNI_PrevAndNext(
				session, assetEntrySet, parentAssetEntrySetId,
				creatorClassNameId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntrySet getByPAESI_CCNI_PrevAndNext(
		Session session, AssetEntrySet assetEntrySet,
		long parentAssetEntrySetId, long creatorClassNameId,
		OrderByComparator<AssetEntrySet> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_ASSETENTRYSET_WHERE);

		sb.append(_FINDER_COLUMN_PAESI_CCNI_PARENTASSETENTRYSETID_2);

		sb.append(_FINDER_COLUMN_PAESI_CCNI_CREATORCLASSNAMEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(AssetEntrySetModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(parentAssetEntrySetId);

		queryPos.add(creatorClassNameId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetEntrySet)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetEntrySet> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset entry sets that the user has permission to view where parentAssetEntrySetId = &#63; and creatorClassNameId = &#63;.
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param creatorClassNameId the creator class name ID
	 * @return the matching asset entry sets that the user has permission to view
	 */
	@Override
	public List<AssetEntrySet> filterFindByPAESI_CCNI(
		long parentAssetEntrySetId, long creatorClassNameId) {

		return filterFindByPAESI_CCNI(
			parentAssetEntrySetId, creatorClassNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry sets that the user has permission to view where parentAssetEntrySetId = &#63; and creatorClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param creatorClassNameId the creator class name ID
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @return the range of matching asset entry sets that the user has permission to view
	 */
	@Override
	public List<AssetEntrySet> filterFindByPAESI_CCNI(
		long parentAssetEntrySetId, long creatorClassNameId, int start,
		int end) {

		return filterFindByPAESI_CCNI(
			parentAssetEntrySetId, creatorClassNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry sets that the user has permissions to view where parentAssetEntrySetId = &#63; and creatorClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param creatorClassNameId the creator class name ID
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry sets that the user has permission to view
	 */
	@Override
	public List<AssetEntrySet> filterFindByPAESI_CCNI(
		long parentAssetEntrySetId, long creatorClassNameId, int start, int end,
		OrderByComparator<AssetEntrySet> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByPAESI_CCNI(
				parentAssetEntrySetId, creatorClassNameId, start, end,
				orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETENTRYSET_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETENTRYSET_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_PAESI_CCNI_PARENTASSETENTRYSETID_2);

		sb.append(_FINDER_COLUMN_PAESI_CCNI_CREATORCLASSNAMEID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETENTRYSET_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetEntrySet.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, AssetEntrySetImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, AssetEntrySetImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(parentAssetEntrySetId);

			queryPos.add(creatorClassNameId);

			return (List<AssetEntrySet>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the asset entry sets before and after the current asset entry set in the ordered set of asset entry sets that the user has permission to view where parentAssetEntrySetId = &#63; and creatorClassNameId = &#63;.
	 *
	 * @param assetEntrySetId the primary key of the current asset entry set
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param creatorClassNameId the creator class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry set
	 * @throws NoSuchAssetEntrySetException if a asset entry set with the primary key could not be found
	 */
	@Override
	public AssetEntrySet[] filterFindByPAESI_CCNI_PrevAndNext(
			long assetEntrySetId, long parentAssetEntrySetId,
			long creatorClassNameId,
			OrderByComparator<AssetEntrySet> orderByComparator)
		throws NoSuchAssetEntrySetException {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByPAESI_CCNI_PrevAndNext(
				assetEntrySetId, parentAssetEntrySetId, creatorClassNameId,
				orderByComparator);
		}

		AssetEntrySet assetEntrySet = findByPrimaryKey(assetEntrySetId);

		Session session = null;

		try {
			session = openSession();

			AssetEntrySet[] array = new AssetEntrySetImpl[3];

			array[0] = filterGetByPAESI_CCNI_PrevAndNext(
				session, assetEntrySet, parentAssetEntrySetId,
				creatorClassNameId, orderByComparator, true);

			array[1] = assetEntrySet;

			array[2] = filterGetByPAESI_CCNI_PrevAndNext(
				session, assetEntrySet, parentAssetEntrySetId,
				creatorClassNameId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntrySet filterGetByPAESI_CCNI_PrevAndNext(
		Session session, AssetEntrySet assetEntrySet,
		long parentAssetEntrySetId, long creatorClassNameId,
		OrderByComparator<AssetEntrySet> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETENTRYSET_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETENTRYSET_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_PAESI_CCNI_PARENTASSETENTRYSETID_2);

		sb.append(_FINDER_COLUMN_PAESI_CCNI_CREATORCLASSNAMEID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETENTRYSET_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetEntrySet.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, AssetEntrySetImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, AssetEntrySetImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(parentAssetEntrySetId);

		queryPos.add(creatorClassNameId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetEntrySet)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetEntrySet> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entry sets where parentAssetEntrySetId = &#63; and creatorClassNameId = &#63; from the database.
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param creatorClassNameId the creator class name ID
	 */
	@Override
	public void removeByPAESI_CCNI(
		long parentAssetEntrySetId, long creatorClassNameId) {

		for (AssetEntrySet assetEntrySet :
				findByPAESI_CCNI(
					parentAssetEntrySetId, creatorClassNameId,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetEntrySet);
		}
	}

	/**
	 * Returns the number of asset entry sets where parentAssetEntrySetId = &#63; and creatorClassNameId = &#63;.
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param creatorClassNameId the creator class name ID
	 * @return the number of matching asset entry sets
	 */
	@Override
	public int countByPAESI_CCNI(
		long parentAssetEntrySetId, long creatorClassNameId) {

		FinderPath finderPath = _finderPathCountByPAESI_CCNI;

		Object[] finderArgs = new Object[] {
			parentAssetEntrySetId, creatorClassNameId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ASSETENTRYSET_WHERE);

			sb.append(_FINDER_COLUMN_PAESI_CCNI_PARENTASSETENTRYSETID_2);

			sb.append(_FINDER_COLUMN_PAESI_CCNI_CREATORCLASSNAMEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(parentAssetEntrySetId);

				queryPos.add(creatorClassNameId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset entry sets that the user has permission to view where parentAssetEntrySetId = &#63; and creatorClassNameId = &#63;.
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param creatorClassNameId the creator class name ID
	 * @return the number of matching asset entry sets that the user has permission to view
	 */
	@Override
	public int filterCountByPAESI_CCNI(
		long parentAssetEntrySetId, long creatorClassNameId) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByPAESI_CCNI(parentAssetEntrySetId, creatorClassNameId);
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_ASSETENTRYSET_WHERE);

		sb.append(_FINDER_COLUMN_PAESI_CCNI_PARENTASSETENTRYSETID_2);

		sb.append(_FINDER_COLUMN_PAESI_CCNI_CREATORCLASSNAMEID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetEntrySet.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(parentAssetEntrySetId);

			queryPos.add(creatorClassNameId);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String
		_FINDER_COLUMN_PAESI_CCNI_PARENTASSETENTRYSETID_2 =
			"assetEntrySet.parentAssetEntrySetId = ? AND ";

	private static final String _FINDER_COLUMN_PAESI_CCNI_CREATORCLASSNAMEID_2 =
		"assetEntrySet.creatorClassNameId = ?";

	private FinderPath _finderPathWithPaginationFindByCNI_CPK;
	private FinderPath _finderPathWithoutPaginationFindByCNI_CPK;
	private FinderPath _finderPathCountByCNI_CPK;

	/**
	 * Returns all the asset entry sets where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching asset entry sets
	 */
	@Override
	public List<AssetEntrySet> findByCNI_CPK(long classNameId, long classPK) {
		return findByCNI_CPK(
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry sets where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @return the range of matching asset entry sets
	 */
	@Override
	public List<AssetEntrySet> findByCNI_CPK(
		long classNameId, long classPK, int start, int end) {

		return findByCNI_CPK(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry sets where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry sets
	 */
	@Override
	public List<AssetEntrySet> findByCNI_CPK(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<AssetEntrySet> orderByComparator) {

		return findByCNI_CPK(
			classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entry sets where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entry sets
	 */
	@Override
	public List<AssetEntrySet> findByCNI_CPK(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<AssetEntrySet> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCNI_CPK;
				finderArgs = new Object[] {classNameId, classPK};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCNI_CPK;
			finderArgs = new Object[] {
				classNameId, classPK, start, end, orderByComparator
			};
		}

		List<AssetEntrySet> list = null;

		if (useFinderCache) {
			list = (List<AssetEntrySet>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntrySet assetEntrySet : list) {
					if ((classNameId != assetEntrySet.getClassNameId()) ||
						(classPK != assetEntrySet.getClassPK())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_ASSETENTRYSET_WHERE);

			sb.append(_FINDER_COLUMN_CNI_CPK_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_CNI_CPK_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<AssetEntrySet>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset entry set in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry set
	 * @throws NoSuchAssetEntrySetException if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet findByCNI_CPK_First(
			long classNameId, long classPK,
			OrderByComparator<AssetEntrySet> orderByComparator)
		throws NoSuchAssetEntrySetException {

		AssetEntrySet assetEntrySet = fetchByCNI_CPK_First(
			classNameId, classPK, orderByComparator);

		if (assetEntrySet != null) {
			return assetEntrySet;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchAssetEntrySetException(sb.toString());
	}

	/**
	 * Returns the first asset entry set in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry set, or <code>null</code> if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet fetchByCNI_CPK_First(
		long classNameId, long classPK,
		OrderByComparator<AssetEntrySet> orderByComparator) {

		List<AssetEntrySet> list = findByCNI_CPK(
			classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry set in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry set
	 * @throws NoSuchAssetEntrySetException if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet findByCNI_CPK_Last(
			long classNameId, long classPK,
			OrderByComparator<AssetEntrySet> orderByComparator)
		throws NoSuchAssetEntrySetException {

		AssetEntrySet assetEntrySet = fetchByCNI_CPK_Last(
			classNameId, classPK, orderByComparator);

		if (assetEntrySet != null) {
			return assetEntrySet;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchAssetEntrySetException(sb.toString());
	}

	/**
	 * Returns the last asset entry set in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry set, or <code>null</code> if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet fetchByCNI_CPK_Last(
		long classNameId, long classPK,
		OrderByComparator<AssetEntrySet> orderByComparator) {

		int count = countByCNI_CPK(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<AssetEntrySet> list = findByCNI_CPK(
			classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entry sets before and after the current asset entry set in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param assetEntrySetId the primary key of the current asset entry set
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry set
	 * @throws NoSuchAssetEntrySetException if a asset entry set with the primary key could not be found
	 */
	@Override
	public AssetEntrySet[] findByCNI_CPK_PrevAndNext(
			long assetEntrySetId, long classNameId, long classPK,
			OrderByComparator<AssetEntrySet> orderByComparator)
		throws NoSuchAssetEntrySetException {

		AssetEntrySet assetEntrySet = findByPrimaryKey(assetEntrySetId);

		Session session = null;

		try {
			session = openSession();

			AssetEntrySet[] array = new AssetEntrySetImpl[3];

			array[0] = getByCNI_CPK_PrevAndNext(
				session, assetEntrySet, classNameId, classPK, orderByComparator,
				true);

			array[1] = assetEntrySet;

			array[2] = getByCNI_CPK_PrevAndNext(
				session, assetEntrySet, classNameId, classPK, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntrySet getByCNI_CPK_PrevAndNext(
		Session session, AssetEntrySet assetEntrySet, long classNameId,
		long classPK, OrderByComparator<AssetEntrySet> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_ASSETENTRYSET_WHERE);

		sb.append(_FINDER_COLUMN_CNI_CPK_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_CNI_CPK_CLASSPK_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(AssetEntrySetModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetEntrySet)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetEntrySet> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset entry sets that the user has permission to view where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching asset entry sets that the user has permission to view
	 */
	@Override
	public List<AssetEntrySet> filterFindByCNI_CPK(
		long classNameId, long classPK) {

		return filterFindByCNI_CPK(
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry sets that the user has permission to view where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @return the range of matching asset entry sets that the user has permission to view
	 */
	@Override
	public List<AssetEntrySet> filterFindByCNI_CPK(
		long classNameId, long classPK, int start, int end) {

		return filterFindByCNI_CPK(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry sets that the user has permissions to view where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry sets that the user has permission to view
	 */
	@Override
	public List<AssetEntrySet> filterFindByCNI_CPK(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<AssetEntrySet> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByCNI_CPK(
				classNameId, classPK, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETENTRYSET_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETENTRYSET_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_CNI_CPK_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_CNI_CPK_CLASSPK_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETENTRYSET_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetEntrySet.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, AssetEntrySetImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, AssetEntrySetImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(classNameId);

			queryPos.add(classPK);

			return (List<AssetEntrySet>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the asset entry sets before and after the current asset entry set in the ordered set of asset entry sets that the user has permission to view where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param assetEntrySetId the primary key of the current asset entry set
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry set
	 * @throws NoSuchAssetEntrySetException if a asset entry set with the primary key could not be found
	 */
	@Override
	public AssetEntrySet[] filterFindByCNI_CPK_PrevAndNext(
			long assetEntrySetId, long classNameId, long classPK,
			OrderByComparator<AssetEntrySet> orderByComparator)
		throws NoSuchAssetEntrySetException {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByCNI_CPK_PrevAndNext(
				assetEntrySetId, classNameId, classPK, orderByComparator);
		}

		AssetEntrySet assetEntrySet = findByPrimaryKey(assetEntrySetId);

		Session session = null;

		try {
			session = openSession();

			AssetEntrySet[] array = new AssetEntrySetImpl[3];

			array[0] = filterGetByCNI_CPK_PrevAndNext(
				session, assetEntrySet, classNameId, classPK, orderByComparator,
				true);

			array[1] = assetEntrySet;

			array[2] = filterGetByCNI_CPK_PrevAndNext(
				session, assetEntrySet, classNameId, classPK, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntrySet filterGetByCNI_CPK_PrevAndNext(
		Session session, AssetEntrySet assetEntrySet, long classNameId,
		long classPK, OrderByComparator<AssetEntrySet> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETENTRYSET_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETENTRYSET_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_CNI_CPK_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_CNI_CPK_CLASSPK_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETENTRYSET_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetEntrySet.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, AssetEntrySetImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, AssetEntrySetImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetEntrySet)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetEntrySet> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entry sets where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByCNI_CPK(long classNameId, long classPK) {
		for (AssetEntrySet assetEntrySet :
				findByCNI_CPK(
					classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetEntrySet);
		}
	}

	/**
	 * Returns the number of asset entry sets where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching asset entry sets
	 */
	@Override
	public int countByCNI_CPK(long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByCNI_CPK;

		Object[] finderArgs = new Object[] {classNameId, classPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ASSETENTRYSET_WHERE);

			sb.append(_FINDER_COLUMN_CNI_CPK_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_CNI_CPK_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset entry sets that the user has permission to view where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching asset entry sets that the user has permission to view
	 */
	@Override
	public int filterCountByCNI_CPK(long classNameId, long classPK) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByCNI_CPK(classNameId, classPK);
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_ASSETENTRYSET_WHERE);

		sb.append(_FINDER_COLUMN_CNI_CPK_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_CNI_CPK_CLASSPK_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetEntrySet.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(classNameId);

			queryPos.add(classPK);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_CNI_CPK_CLASSNAMEID_2 =
		"assetEntrySet.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_CNI_CPK_CLASSPK_2 =
		"assetEntrySet.classPK = ?";

	private FinderPath _finderPathFetchByPAESI_CCNI_CCPK;
	private FinderPath _finderPathCountByPAESI_CCNI_CCPK;

	/**
	 * Returns the asset entry set where parentAssetEntrySetId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; or throws a <code>NoSuchAssetEntrySetException</code> if it could not be found.
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @return the matching asset entry set
	 * @throws NoSuchAssetEntrySetException if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet findByPAESI_CCNI_CCPK(
			long parentAssetEntrySetId, long creatorClassNameId,
			long creatorClassPK)
		throws NoSuchAssetEntrySetException {

		AssetEntrySet assetEntrySet = fetchByPAESI_CCNI_CCPK(
			parentAssetEntrySetId, creatorClassNameId, creatorClassPK);

		if (assetEntrySet == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("parentAssetEntrySetId=");
			sb.append(parentAssetEntrySetId);

			sb.append(", creatorClassNameId=");
			sb.append(creatorClassNameId);

			sb.append(", creatorClassPK=");
			sb.append(creatorClassPK);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchAssetEntrySetException(sb.toString());
		}

		return assetEntrySet;
	}

	/**
	 * Returns the asset entry set where parentAssetEntrySetId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @return the matching asset entry set, or <code>null</code> if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet fetchByPAESI_CCNI_CCPK(
		long parentAssetEntrySetId, long creatorClassNameId,
		long creatorClassPK) {

		return fetchByPAESI_CCNI_CCPK(
			parentAssetEntrySetId, creatorClassNameId, creatorClassPK, true);
	}

	/**
	 * Returns the asset entry set where parentAssetEntrySetId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset entry set, or <code>null</code> if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet fetchByPAESI_CCNI_CCPK(
		long parentAssetEntrySetId, long creatorClassNameId,
		long creatorClassPK, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				parentAssetEntrySetId, creatorClassNameId, creatorClassPK
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByPAESI_CCNI_CCPK, finderArgs, this);
		}

		if (result instanceof AssetEntrySet) {
			AssetEntrySet assetEntrySet = (AssetEntrySet)result;

			if ((parentAssetEntrySetId !=
					assetEntrySet.getParentAssetEntrySetId()) ||
				(creatorClassNameId != assetEntrySet.getCreatorClassNameId()) ||
				(creatorClassPK != assetEntrySet.getCreatorClassPK())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_ASSETENTRYSET_WHERE);

			sb.append(_FINDER_COLUMN_PAESI_CCNI_CCPK_PARENTASSETENTRYSETID_2);

			sb.append(_FINDER_COLUMN_PAESI_CCNI_CCPK_CREATORCLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_PAESI_CCNI_CCPK_CREATORCLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(parentAssetEntrySetId);

				queryPos.add(creatorClassNameId);

				queryPos.add(creatorClassPK);

				List<AssetEntrySet> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByPAESI_CCNI_CCPK, finderArgs,
							list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									parentAssetEntrySetId, creatorClassNameId,
									creatorClassPK
								};
							}

							_log.warn(
								"AssetEntrySetPersistenceImpl.fetchByPAESI_CCNI_CCPK(long, long, long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					AssetEntrySet assetEntrySet = list.get(0);

					result = assetEntrySet;

					cacheResult(assetEntrySet);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByPAESI_CCNI_CCPK, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (AssetEntrySet)result;
		}
	}

	/**
	 * Removes the asset entry set where parentAssetEntrySetId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; from the database.
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @return the asset entry set that was removed
	 */
	@Override
	public AssetEntrySet removeByPAESI_CCNI_CCPK(
			long parentAssetEntrySetId, long creatorClassNameId,
			long creatorClassPK)
		throws NoSuchAssetEntrySetException {

		AssetEntrySet assetEntrySet = findByPAESI_CCNI_CCPK(
			parentAssetEntrySetId, creatorClassNameId, creatorClassPK);

		return remove(assetEntrySet);
	}

	/**
	 * Returns the number of asset entry sets where parentAssetEntrySetId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param parentAssetEntrySetId the parent asset entry set ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @return the number of matching asset entry sets
	 */
	@Override
	public int countByPAESI_CCNI_CCPK(
		long parentAssetEntrySetId, long creatorClassNameId,
		long creatorClassPK) {

		FinderPath finderPath = _finderPathCountByPAESI_CCNI_CCPK;

		Object[] finderArgs = new Object[] {
			parentAssetEntrySetId, creatorClassNameId, creatorClassPK
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_ASSETENTRYSET_WHERE);

			sb.append(_FINDER_COLUMN_PAESI_CCNI_CCPK_PARENTASSETENTRYSETID_2);

			sb.append(_FINDER_COLUMN_PAESI_CCNI_CCPK_CREATORCLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_PAESI_CCNI_CCPK_CREATORCLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(parentAssetEntrySetId);

				queryPos.add(creatorClassNameId);

				queryPos.add(creatorClassPK);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_PAESI_CCNI_CCPK_PARENTASSETENTRYSETID_2 =
			"assetEntrySet.parentAssetEntrySetId = ? AND ";

	private static final String
		_FINDER_COLUMN_PAESI_CCNI_CCPK_CREATORCLASSNAMEID_2 =
			"assetEntrySet.creatorClassNameId = ? AND ";

	private static final String
		_FINDER_COLUMN_PAESI_CCNI_CCPK_CREATORCLASSPK_2 =
			"assetEntrySet.creatorClassPK = ?";

	private FinderPath _finderPathFetchByCNI_CPK_Title;
	private FinderPath _finderPathCountByCNI_CPK_Title;

	/**
	 * Returns the asset entry set where classNameId = &#63; and classPK = &#63; and title = &#63; or throws a <code>NoSuchAssetEntrySetException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param title the title
	 * @return the matching asset entry set
	 * @throws NoSuchAssetEntrySetException if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet findByCNI_CPK_Title(
			long classNameId, long classPK, String title)
		throws NoSuchAssetEntrySetException {

		AssetEntrySet assetEntrySet = fetchByCNI_CPK_Title(
			classNameId, classPK, title);

		if (assetEntrySet == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append(", title=");
			sb.append(title);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchAssetEntrySetException(sb.toString());
		}

		return assetEntrySet;
	}

	/**
	 * Returns the asset entry set where classNameId = &#63; and classPK = &#63; and title = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param title the title
	 * @return the matching asset entry set, or <code>null</code> if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet fetchByCNI_CPK_Title(
		long classNameId, long classPK, String title) {

		return fetchByCNI_CPK_Title(classNameId, classPK, title, true);
	}

	/**
	 * Returns the asset entry set where classNameId = &#63; and classPK = &#63; and title = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param title the title
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset entry set, or <code>null</code> if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet fetchByCNI_CPK_Title(
		long classNameId, long classPK, String title, boolean useFinderCache) {

		title = Objects.toString(title, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {classNameId, classPK, title};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByCNI_CPK_Title, finderArgs, this);
		}

		if (result instanceof AssetEntrySet) {
			AssetEntrySet assetEntrySet = (AssetEntrySet)result;

			if ((classNameId != assetEntrySet.getClassNameId()) ||
				(classPK != assetEntrySet.getClassPK()) ||
				!Objects.equals(title, assetEntrySet.getTitle())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_ASSETENTRYSET_WHERE);

			sb.append(_FINDER_COLUMN_CNI_CPK_TITLE_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_CNI_CPK_TITLE_CLASSPK_2);

			boolean bindTitle = false;

			if (title.isEmpty()) {
				sb.append(_FINDER_COLUMN_CNI_CPK_TITLE_TITLE_3);
			}
			else {
				bindTitle = true;

				sb.append(_FINDER_COLUMN_CNI_CPK_TITLE_TITLE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				if (bindTitle) {
					queryPos.add(title);
				}

				List<AssetEntrySet> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByCNI_CPK_Title, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									classNameId, classPK, title
								};
							}

							_log.warn(
								"AssetEntrySetPersistenceImpl.fetchByCNI_CPK_Title(long, long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					AssetEntrySet assetEntrySet = list.get(0);

					result = assetEntrySet;

					cacheResult(assetEntrySet);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByCNI_CPK_Title, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (AssetEntrySet)result;
		}
	}

	/**
	 * Removes the asset entry set where classNameId = &#63; and classPK = &#63; and title = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param title the title
	 * @return the asset entry set that was removed
	 */
	@Override
	public AssetEntrySet removeByCNI_CPK_Title(
			long classNameId, long classPK, String title)
		throws NoSuchAssetEntrySetException {

		AssetEntrySet assetEntrySet = findByCNI_CPK_Title(
			classNameId, classPK, title);

		return remove(assetEntrySet);
	}

	/**
	 * Returns the number of asset entry sets where classNameId = &#63; and classPK = &#63; and title = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param title the title
	 * @return the number of matching asset entry sets
	 */
	@Override
	public int countByCNI_CPK_Title(
		long classNameId, long classPK, String title) {

		title = Objects.toString(title, "");

		FinderPath finderPath = _finderPathCountByCNI_CPK_Title;

		Object[] finderArgs = new Object[] {classNameId, classPK, title};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_ASSETENTRYSET_WHERE);

			sb.append(_FINDER_COLUMN_CNI_CPK_TITLE_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_CNI_CPK_TITLE_CLASSPK_2);

			boolean bindTitle = false;

			if (title.isEmpty()) {
				sb.append(_FINDER_COLUMN_CNI_CPK_TITLE_TITLE_3);
			}
			else {
				bindTitle = true;

				sb.append(_FINDER_COLUMN_CNI_CPK_TITLE_TITLE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				if (bindTitle) {
					queryPos.add(title);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_CNI_CPK_TITLE_CLASSNAMEID_2 =
		"assetEntrySet.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_CNI_CPK_TITLE_CLASSPK_2 =
		"assetEntrySet.classPK = ? AND ";

	private static final String _FINDER_COLUMN_CNI_CPK_TITLE_TITLE_2 =
		"assetEntrySet.title = ?";

	private static final String _FINDER_COLUMN_CNI_CPK_TITLE_TITLE_3 =
		"(assetEntrySet.title IS NULL OR assetEntrySet.title = '')";

	private FinderPath _finderPathWithPaginationFindByCNI_CPK_Type;
	private FinderPath _finderPathWithoutPaginationFindByCNI_CPK_Type;
	private FinderPath _finderPathCountByCNI_CPK_Type;

	/**
	 * Returns all the asset entry sets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the matching asset entry sets
	 */
	@Override
	public List<AssetEntrySet> findByCNI_CPK_Type(
		long classNameId, long classPK, int type) {

		return findByCNI_CPK_Type(
			classNameId, classPK, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the asset entry sets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @return the range of matching asset entry sets
	 */
	@Override
	public List<AssetEntrySet> findByCNI_CPK_Type(
		long classNameId, long classPK, int type, int start, int end) {

		return findByCNI_CPK_Type(classNameId, classPK, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry sets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry sets
	 */
	@Override
	public List<AssetEntrySet> findByCNI_CPK_Type(
		long classNameId, long classPK, int type, int start, int end,
		OrderByComparator<AssetEntrySet> orderByComparator) {

		return findByCNI_CPK_Type(
			classNameId, classPK, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entry sets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entry sets
	 */
	@Override
	public List<AssetEntrySet> findByCNI_CPK_Type(
		long classNameId, long classPK, int type, int start, int end,
		OrderByComparator<AssetEntrySet> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCNI_CPK_Type;
				finderArgs = new Object[] {classNameId, classPK, type};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCNI_CPK_Type;
			finderArgs = new Object[] {
				classNameId, classPK, type, start, end, orderByComparator
			};
		}

		List<AssetEntrySet> list = null;

		if (useFinderCache) {
			list = (List<AssetEntrySet>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntrySet assetEntrySet : list) {
					if ((classNameId != assetEntrySet.getClassNameId()) ||
						(classPK != assetEntrySet.getClassPK()) ||
						(type != assetEntrySet.getType())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_ASSETENTRYSET_WHERE);

			sb.append(_FINDER_COLUMN_CNI_CPK_TYPE_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_CNI_CPK_TYPE_CLASSPK_2);

			sb.append(_FINDER_COLUMN_CNI_CPK_TYPE_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(type);

				list = (List<AssetEntrySet>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset entry set in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry set
	 * @throws NoSuchAssetEntrySetException if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet findByCNI_CPK_Type_First(
			long classNameId, long classPK, int type,
			OrderByComparator<AssetEntrySet> orderByComparator)
		throws NoSuchAssetEntrySetException {

		AssetEntrySet assetEntrySet = fetchByCNI_CPK_Type_First(
			classNameId, classPK, type, orderByComparator);

		if (assetEntrySet != null) {
			return assetEntrySet;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchAssetEntrySetException(sb.toString());
	}

	/**
	 * Returns the first asset entry set in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry set, or <code>null</code> if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet fetchByCNI_CPK_Type_First(
		long classNameId, long classPK, int type,
		OrderByComparator<AssetEntrySet> orderByComparator) {

		List<AssetEntrySet> list = findByCNI_CPK_Type(
			classNameId, classPK, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry set in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry set
	 * @throws NoSuchAssetEntrySetException if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet findByCNI_CPK_Type_Last(
			long classNameId, long classPK, int type,
			OrderByComparator<AssetEntrySet> orderByComparator)
		throws NoSuchAssetEntrySetException {

		AssetEntrySet assetEntrySet = fetchByCNI_CPK_Type_Last(
			classNameId, classPK, type, orderByComparator);

		if (assetEntrySet != null) {
			return assetEntrySet;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchAssetEntrySetException(sb.toString());
	}

	/**
	 * Returns the last asset entry set in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry set, or <code>null</code> if a matching asset entry set could not be found
	 */
	@Override
	public AssetEntrySet fetchByCNI_CPK_Type_Last(
		long classNameId, long classPK, int type,
		OrderByComparator<AssetEntrySet> orderByComparator) {

		int count = countByCNI_CPK_Type(classNameId, classPK, type);

		if (count == 0) {
			return null;
		}

		List<AssetEntrySet> list = findByCNI_CPK_Type(
			classNameId, classPK, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entry sets before and after the current asset entry set in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param assetEntrySetId the primary key of the current asset entry set
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry set
	 * @throws NoSuchAssetEntrySetException if a asset entry set with the primary key could not be found
	 */
	@Override
	public AssetEntrySet[] findByCNI_CPK_Type_PrevAndNext(
			long assetEntrySetId, long classNameId, long classPK, int type,
			OrderByComparator<AssetEntrySet> orderByComparator)
		throws NoSuchAssetEntrySetException {

		AssetEntrySet assetEntrySet = findByPrimaryKey(assetEntrySetId);

		Session session = null;

		try {
			session = openSession();

			AssetEntrySet[] array = new AssetEntrySetImpl[3];

			array[0] = getByCNI_CPK_Type_PrevAndNext(
				session, assetEntrySet, classNameId, classPK, type,
				orderByComparator, true);

			array[1] = assetEntrySet;

			array[2] = getByCNI_CPK_Type_PrevAndNext(
				session, assetEntrySet, classNameId, classPK, type,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntrySet getByCNI_CPK_Type_PrevAndNext(
		Session session, AssetEntrySet assetEntrySet, long classNameId,
		long classPK, int type,
		OrderByComparator<AssetEntrySet> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_ASSETENTRYSET_WHERE);

		sb.append(_FINDER_COLUMN_CNI_CPK_TYPE_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_CNI_CPK_TYPE_CLASSPK_2);

		sb.append(_FINDER_COLUMN_CNI_CPK_TYPE_TYPE_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(AssetEntrySetModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetEntrySet)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetEntrySet> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset entry sets that the user has permission to view where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the matching asset entry sets that the user has permission to view
	 */
	@Override
	public List<AssetEntrySet> filterFindByCNI_CPK_Type(
		long classNameId, long classPK, int type) {

		return filterFindByCNI_CPK_Type(
			classNameId, classPK, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the asset entry sets that the user has permission to view where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @return the range of matching asset entry sets that the user has permission to view
	 */
	@Override
	public List<AssetEntrySet> filterFindByCNI_CPK_Type(
		long classNameId, long classPK, int type, int start, int end) {

		return filterFindByCNI_CPK_Type(
			classNameId, classPK, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry sets that the user has permissions to view where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry sets that the user has permission to view
	 */
	@Override
	public List<AssetEntrySet> filterFindByCNI_CPK_Type(
		long classNameId, long classPK, int type, int start, int end,
		OrderByComparator<AssetEntrySet> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByCNI_CPK_Type(
				classNameId, classPK, type, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETENTRYSET_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETENTRYSET_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_CNI_CPK_TYPE_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_CNI_CPK_TYPE_CLASSPK_2);

		sb.append(_FINDER_COLUMN_CNI_CPK_TYPE_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETENTRYSET_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetEntrySet.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, AssetEntrySetImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, AssetEntrySetImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(classNameId);

			queryPos.add(classPK);

			queryPos.add(type);

			return (List<AssetEntrySet>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the asset entry sets before and after the current asset entry set in the ordered set of asset entry sets that the user has permission to view where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param assetEntrySetId the primary key of the current asset entry set
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry set
	 * @throws NoSuchAssetEntrySetException if a asset entry set with the primary key could not be found
	 */
	@Override
	public AssetEntrySet[] filterFindByCNI_CPK_Type_PrevAndNext(
			long assetEntrySetId, long classNameId, long classPK, int type,
			OrderByComparator<AssetEntrySet> orderByComparator)
		throws NoSuchAssetEntrySetException {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByCNI_CPK_Type_PrevAndNext(
				assetEntrySetId, classNameId, classPK, type, orderByComparator);
		}

		AssetEntrySet assetEntrySet = findByPrimaryKey(assetEntrySetId);

		Session session = null;

		try {
			session = openSession();

			AssetEntrySet[] array = new AssetEntrySetImpl[3];

			array[0] = filterGetByCNI_CPK_Type_PrevAndNext(
				session, assetEntrySet, classNameId, classPK, type,
				orderByComparator, true);

			array[1] = assetEntrySet;

			array[2] = filterGetByCNI_CPK_Type_PrevAndNext(
				session, assetEntrySet, classNameId, classPK, type,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntrySet filterGetByCNI_CPK_Type_PrevAndNext(
		Session session, AssetEntrySet assetEntrySet, long classNameId,
		long classPK, int type,
		OrderByComparator<AssetEntrySet> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETENTRYSET_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETENTRYSET_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_CNI_CPK_TYPE_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_CNI_CPK_TYPE_CLASSPK_2);

		sb.append(_FINDER_COLUMN_CNI_CPK_TYPE_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETENTRYSET_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetEntrySetModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetEntrySet.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, AssetEntrySetImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, AssetEntrySetImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetEntrySet)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetEntrySet> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entry sets where classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 */
	@Override
	public void removeByCNI_CPK_Type(long classNameId, long classPK, int type) {
		for (AssetEntrySet assetEntrySet :
				findByCNI_CPK_Type(
					classNameId, classPK, type, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(assetEntrySet);
		}
	}

	/**
	 * Returns the number of asset entry sets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the number of matching asset entry sets
	 */
	@Override
	public int countByCNI_CPK_Type(long classNameId, long classPK, int type) {
		FinderPath finderPath = _finderPathCountByCNI_CPK_Type;

		Object[] finderArgs = new Object[] {classNameId, classPK, type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_ASSETENTRYSET_WHERE);

			sb.append(_FINDER_COLUMN_CNI_CPK_TYPE_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_CNI_CPK_TYPE_CLASSPK_2);

			sb.append(_FINDER_COLUMN_CNI_CPK_TYPE_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(type);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset entry sets that the user has permission to view where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the number of matching asset entry sets that the user has permission to view
	 */
	@Override
	public int filterCountByCNI_CPK_Type(
		long classNameId, long classPK, int type) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByCNI_CPK_Type(classNameId, classPK, type);
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_ASSETENTRYSET_WHERE);

		sb.append(_FINDER_COLUMN_CNI_CPK_TYPE_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_CNI_CPK_TYPE_CLASSPK_2);

		sb.append(_FINDER_COLUMN_CNI_CPK_TYPE_TYPE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetEntrySet.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(classNameId);

			queryPos.add(classPK);

			queryPos.add(type);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_CNI_CPK_TYPE_CLASSNAMEID_2 =
		"assetEntrySet.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_CNI_CPK_TYPE_CLASSPK_2 =
		"assetEntrySet.classPK = ? AND ";

	private static final String _FINDER_COLUMN_CNI_CPK_TYPE_TYPE_2 =
		"assetEntrySet.type = ?";

	private static final String _FINDER_COLUMN_CNI_CPK_TYPE_TYPE_2_SQL =
		"assetEntrySet.type_ = ?";

	public AssetEntrySetPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
				"_dbColumnNames");

			field.setAccessible(true);

			field.set(this, dbColumnNames);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		setModelClass(AssetEntrySet.class);
	}

	/**
	 * Caches the asset entry set in the entity cache if it is enabled.
	 *
	 * @param assetEntrySet the asset entry set
	 */
	@Override
	public void cacheResult(AssetEntrySet assetEntrySet) {
		entityCache.putResult(
			AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetImpl.class, assetEntrySet.getPrimaryKey(),
			assetEntrySet);

		finderCache.putResult(
			_finderPathFetchByPAESI_CCNI_CCPK,
			new Object[] {
				assetEntrySet.getParentAssetEntrySetId(),
				assetEntrySet.getCreatorClassNameId(),
				assetEntrySet.getCreatorClassPK()
			},
			assetEntrySet);

		finderCache.putResult(
			_finderPathFetchByCNI_CPK_Title,
			new Object[] {
				assetEntrySet.getClassNameId(), assetEntrySet.getClassPK(),
				assetEntrySet.getTitle()
			},
			assetEntrySet);

		assetEntrySet.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the asset entry sets in the entity cache if it is enabled.
	 *
	 * @param assetEntrySets the asset entry sets
	 */
	@Override
	public void cacheResult(List<AssetEntrySet> assetEntrySets) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (assetEntrySets.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (AssetEntrySet assetEntrySet : assetEntrySets) {
			if (entityCache.getResult(
					AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
					AssetEntrySetImpl.class, assetEntrySet.getPrimaryKey()) ==
						null) {

				cacheResult(assetEntrySet);
			}
			else {
				assetEntrySet.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset entry sets.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AssetEntrySetImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset entry set.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetEntrySet assetEntrySet) {
		entityCache.removeResult(
			AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetImpl.class, assetEntrySet.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((AssetEntrySetModelImpl)assetEntrySet, true);
	}

	@Override
	public void clearCache(List<AssetEntrySet> assetEntrySets) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetEntrySet assetEntrySet : assetEntrySets) {
			entityCache.removeResult(
				AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
				AssetEntrySetImpl.class, assetEntrySet.getPrimaryKey());

			clearUniqueFindersCache(
				(AssetEntrySetModelImpl)assetEntrySet, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
				AssetEntrySetImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AssetEntrySetModelImpl assetEntrySetModelImpl) {

		Object[] args = new Object[] {
			assetEntrySetModelImpl.getParentAssetEntrySetId(),
			assetEntrySetModelImpl.getCreatorClassNameId(),
			assetEntrySetModelImpl.getCreatorClassPK()
		};

		finderCache.putResult(
			_finderPathCountByPAESI_CCNI_CCPK, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByPAESI_CCNI_CCPK, args, assetEntrySetModelImpl,
			false);

		args = new Object[] {
			assetEntrySetModelImpl.getClassNameId(),
			assetEntrySetModelImpl.getClassPK(),
			assetEntrySetModelImpl.getTitle()
		};

		finderCache.putResult(
			_finderPathCountByCNI_CPK_Title, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByCNI_CPK_Title, args, assetEntrySetModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		AssetEntrySetModelImpl assetEntrySetModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				assetEntrySetModelImpl.getParentAssetEntrySetId(),
				assetEntrySetModelImpl.getCreatorClassNameId(),
				assetEntrySetModelImpl.getCreatorClassPK()
			};

			finderCache.removeResult(_finderPathCountByPAESI_CCNI_CCPK, args);
			finderCache.removeResult(_finderPathFetchByPAESI_CCNI_CCPK, args);
		}

		if ((assetEntrySetModelImpl.getColumnBitmask() &
			 _finderPathFetchByPAESI_CCNI_CCPK.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				assetEntrySetModelImpl.getOriginalParentAssetEntrySetId(),
				assetEntrySetModelImpl.getOriginalCreatorClassNameId(),
				assetEntrySetModelImpl.getOriginalCreatorClassPK()
			};

			finderCache.removeResult(_finderPathCountByPAESI_CCNI_CCPK, args);
			finderCache.removeResult(_finderPathFetchByPAESI_CCNI_CCPK, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				assetEntrySetModelImpl.getClassNameId(),
				assetEntrySetModelImpl.getClassPK(),
				assetEntrySetModelImpl.getTitle()
			};

			finderCache.removeResult(_finderPathCountByCNI_CPK_Title, args);
			finderCache.removeResult(_finderPathFetchByCNI_CPK_Title, args);
		}

		if ((assetEntrySetModelImpl.getColumnBitmask() &
			 _finderPathFetchByCNI_CPK_Title.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				assetEntrySetModelImpl.getOriginalClassNameId(),
				assetEntrySetModelImpl.getOriginalClassPK(),
				assetEntrySetModelImpl.getOriginalTitle()
			};

			finderCache.removeResult(_finderPathCountByCNI_CPK_Title, args);
			finderCache.removeResult(_finderPathFetchByCNI_CPK_Title, args);
		}
	}

	/**
	 * Creates a new asset entry set with the primary key. Does not add the asset entry set to the database.
	 *
	 * @param assetEntrySetId the primary key for the new asset entry set
	 * @return the new asset entry set
	 */
	@Override
	public AssetEntrySet create(long assetEntrySetId) {
		AssetEntrySet assetEntrySet = new AssetEntrySetImpl();

		assetEntrySet.setNew(true);
		assetEntrySet.setPrimaryKey(assetEntrySetId);

		assetEntrySet.setCompanyId(CompanyThreadLocal.getCompanyId());

		return assetEntrySet;
	}

	/**
	 * Removes the asset entry set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntrySetId the primary key of the asset entry set
	 * @return the asset entry set that was removed
	 * @throws NoSuchAssetEntrySetException if a asset entry set with the primary key could not be found
	 */
	@Override
	public AssetEntrySet remove(long assetEntrySetId)
		throws NoSuchAssetEntrySetException {

		return remove((Serializable)assetEntrySetId);
	}

	/**
	 * Removes the asset entry set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset entry set
	 * @return the asset entry set that was removed
	 * @throws NoSuchAssetEntrySetException if a asset entry set with the primary key could not be found
	 */
	@Override
	public AssetEntrySet remove(Serializable primaryKey)
		throws NoSuchAssetEntrySetException {

		Session session = null;

		try {
			session = openSession();

			AssetEntrySet assetEntrySet = (AssetEntrySet)session.get(
				AssetEntrySetImpl.class, primaryKey);

			if (assetEntrySet == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAssetEntrySetException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(assetEntrySet);
		}
		catch (NoSuchAssetEntrySetException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected AssetEntrySet removeImpl(AssetEntrySet assetEntrySet) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetEntrySet)) {
				assetEntrySet = (AssetEntrySet)session.get(
					AssetEntrySetImpl.class, assetEntrySet.getPrimaryKeyObj());
			}

			if (assetEntrySet != null) {
				session.delete(assetEntrySet);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (assetEntrySet != null) {
			clearCache(assetEntrySet);
		}

		return assetEntrySet;
	}

	@Override
	public AssetEntrySet updateImpl(AssetEntrySet assetEntrySet) {
		boolean isNew = assetEntrySet.isNew();

		if (!(assetEntrySet instanceof AssetEntrySetModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(assetEntrySet.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					assetEntrySet);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in assetEntrySet proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AssetEntrySet implementation " +
					assetEntrySet.getClass());
		}

		AssetEntrySetModelImpl assetEntrySetModelImpl =
			(AssetEntrySetModelImpl)assetEntrySet;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(assetEntrySet);

				assetEntrySet.setNew(false);
			}
			else {
				assetEntrySet = (AssetEntrySet)session.merge(assetEntrySet);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!AssetEntrySetModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				assetEntrySetModelImpl.getParentAssetEntrySetId()
			};

			finderCache.removeResult(
				_finderPathCountByParentAssetEntrySetId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByParentAssetEntrySetId, args);

			args = new Object[] {
				assetEntrySetModelImpl.getParentAssetEntrySetId(),
				assetEntrySetModelImpl.getCreatorClassNameId()
			};

			finderCache.removeResult(_finderPathCountByPAESI_CCNI, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByPAESI_CCNI, args);

			args = new Object[] {
				assetEntrySetModelImpl.getClassNameId(),
				assetEntrySetModelImpl.getClassPK()
			};

			finderCache.removeResult(_finderPathCountByCNI_CPK, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCNI_CPK, args);

			args = new Object[] {
				assetEntrySetModelImpl.getClassNameId(),
				assetEntrySetModelImpl.getClassPK(),
				assetEntrySetModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByCNI_CPK_Type, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCNI_CPK_Type, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((assetEntrySetModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByParentAssetEntrySetId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					assetEntrySetModelImpl.getOriginalParentAssetEntrySetId()
				};

				finderCache.removeResult(
					_finderPathCountByParentAssetEntrySetId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByParentAssetEntrySetId,
					args);

				args = new Object[] {
					assetEntrySetModelImpl.getParentAssetEntrySetId()
				};

				finderCache.removeResult(
					_finderPathCountByParentAssetEntrySetId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByParentAssetEntrySetId,
					args);
			}

			if ((assetEntrySetModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByPAESI_CCNI.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					assetEntrySetModelImpl.getOriginalParentAssetEntrySetId(),
					assetEntrySetModelImpl.getOriginalCreatorClassNameId()
				};

				finderCache.removeResult(_finderPathCountByPAESI_CCNI, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByPAESI_CCNI, args);

				args = new Object[] {
					assetEntrySetModelImpl.getParentAssetEntrySetId(),
					assetEntrySetModelImpl.getCreatorClassNameId()
				};

				finderCache.removeResult(_finderPathCountByPAESI_CCNI, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByPAESI_CCNI, args);
			}

			if ((assetEntrySetModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCNI_CPK.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					assetEntrySetModelImpl.getOriginalClassNameId(),
					assetEntrySetModelImpl.getOriginalClassPK()
				};

				finderCache.removeResult(_finderPathCountByCNI_CPK, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCNI_CPK, args);

				args = new Object[] {
					assetEntrySetModelImpl.getClassNameId(),
					assetEntrySetModelImpl.getClassPK()
				};

				finderCache.removeResult(_finderPathCountByCNI_CPK, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCNI_CPK, args);
			}

			if ((assetEntrySetModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCNI_CPK_Type.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					assetEntrySetModelImpl.getOriginalClassNameId(),
					assetEntrySetModelImpl.getOriginalClassPK(),
					assetEntrySetModelImpl.getOriginalType()
				};

				finderCache.removeResult(_finderPathCountByCNI_CPK_Type, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCNI_CPK_Type, args);

				args = new Object[] {
					assetEntrySetModelImpl.getClassNameId(),
					assetEntrySetModelImpl.getClassPK(),
					assetEntrySetModelImpl.getType()
				};

				finderCache.removeResult(_finderPathCountByCNI_CPK_Type, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCNI_CPK_Type, args);
			}
		}

		entityCache.putResult(
			AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetImpl.class, assetEntrySet.getPrimaryKey(),
			assetEntrySet, false);

		clearUniqueFindersCache(assetEntrySetModelImpl, false);
		cacheUniqueFindersCache(assetEntrySetModelImpl);

		assetEntrySet.resetOriginalValues();

		return assetEntrySet;
	}

	/**
	 * Returns the asset entry set with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset entry set
	 * @return the asset entry set
	 * @throws NoSuchAssetEntrySetException if a asset entry set with the primary key could not be found
	 */
	@Override
	public AssetEntrySet findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAssetEntrySetException {

		AssetEntrySet assetEntrySet = fetchByPrimaryKey(primaryKey);

		if (assetEntrySet == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAssetEntrySetException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return assetEntrySet;
	}

	/**
	 * Returns the asset entry set with the primary key or throws a <code>NoSuchAssetEntrySetException</code> if it could not be found.
	 *
	 * @param assetEntrySetId the primary key of the asset entry set
	 * @return the asset entry set
	 * @throws NoSuchAssetEntrySetException if a asset entry set with the primary key could not be found
	 */
	@Override
	public AssetEntrySet findByPrimaryKey(long assetEntrySetId)
		throws NoSuchAssetEntrySetException {

		return findByPrimaryKey((Serializable)assetEntrySetId);
	}

	/**
	 * Returns the asset entry set with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset entry set
	 * @return the asset entry set, or <code>null</code> if a asset entry set with the primary key could not be found
	 */
	@Override
	public AssetEntrySet fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		AssetEntrySet assetEntrySet = (AssetEntrySet)serializable;

		if (assetEntrySet == null) {
			Session session = null;

			try {
				session = openSession();

				assetEntrySet = (AssetEntrySet)session.get(
					AssetEntrySetImpl.class, primaryKey);

				if (assetEntrySet != null) {
					cacheResult(assetEntrySet);
				}
				else {
					entityCache.putResult(
						AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
						AssetEntrySetImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
					AssetEntrySetImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return assetEntrySet;
	}

	/**
	 * Returns the asset entry set with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetEntrySetId the primary key of the asset entry set
	 * @return the asset entry set, or <code>null</code> if a asset entry set with the primary key could not be found
	 */
	@Override
	public AssetEntrySet fetchByPrimaryKey(long assetEntrySetId) {
		return fetchByPrimaryKey((Serializable)assetEntrySetId);
	}

	@Override
	public Map<Serializable, AssetEntrySet> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AssetEntrySet> map =
			new HashMap<Serializable, AssetEntrySet>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AssetEntrySet assetEntrySet = fetchByPrimaryKey(primaryKey);

			if (assetEntrySet != null) {
				map.put(primaryKey, assetEntrySet);
			}

			return map;
		}

		if ((_databaseInMaxParameters > 0) &&
			(primaryKeys.size() > _databaseInMaxParameters)) {

			Iterator<Serializable> iterator = primaryKeys.iterator();

			while (iterator.hasNext()) {
				Set<Serializable> page = new HashSet<>();

				for (int i = 0;
					 (i < _databaseInMaxParameters) && iterator.hasNext();
					 i++) {

					page.add(iterator.next());
				}

				map.putAll(fetchByPrimaryKeys(page));
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(
				AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
				AssetEntrySetImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (AssetEntrySet)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_ASSETENTRYSET_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			sb.append((long)primaryKey);

			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			for (AssetEntrySet assetEntrySet :
					(List<AssetEntrySet>)query.list()) {

				map.put(assetEntrySet.getPrimaryKeyObj(), assetEntrySet);

				cacheResult(assetEntrySet);

				uncachedPrimaryKeys.remove(assetEntrySet.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
					AssetEntrySetImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the asset entry sets.
	 *
	 * @return the asset entry sets
	 */
	@Override
	public List<AssetEntrySet> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @return the range of asset entry sets
	 */
	@Override
	public List<AssetEntrySet> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset entry sets
	 */
	@Override
	public List<AssetEntrySet> findAll(
		int start, int end,
		OrderByComparator<AssetEntrySet> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entry sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry sets
	 * @param end the upper bound of the range of asset entry sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset entry sets
	 */
	@Override
	public List<AssetEntrySet> findAll(
		int start, int end, OrderByComparator<AssetEntrySet> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<AssetEntrySet> list = null;

		if (useFinderCache) {
			list = (List<AssetEntrySet>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ASSETENTRYSET);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETENTRYSET;

				sql = sql.concat(AssetEntrySetModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AssetEntrySet>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the asset entry sets from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetEntrySet assetEntrySet : findAll()) {
			remove(assetEntrySet);
		}
	}

	/**
	 * Returns the number of asset entry sets.
	 *
	 * @return the number of asset entry sets
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_ASSETENTRYSET);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AssetEntrySetModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the asset entry set persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetModelImpl.FINDER_CACHE_ENABLED,
			AssetEntrySetImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetModelImpl.FINDER_CACHE_ENABLED,
			AssetEntrySetImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByParentAssetEntrySetId = new FinderPath(
			AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetModelImpl.FINDER_CACHE_ENABLED,
			AssetEntrySetImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByParentAssetEntrySetId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByParentAssetEntrySetId =
			new FinderPath(
				AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
				AssetEntrySetModelImpl.FINDER_CACHE_ENABLED,
				AssetEntrySetImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByParentAssetEntrySetId",
				new String[] {Long.class.getName()},
				AssetEntrySetModelImpl.PARENTASSETENTRYSETID_COLUMN_BITMASK |
				AssetEntrySetModelImpl.CREATETIME_COLUMN_BITMASK);

		_finderPathCountByParentAssetEntrySetId = new FinderPath(
			AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByParentAssetEntrySetId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByGtCT_PAESI = new FinderPath(
			AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetModelImpl.FINDER_CACHE_ENABLED,
			AssetEntrySetImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGtCT_PAESI",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByGtCT_PAESI = new FinderPath(
			AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByGtCT_PAESI",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByLtCT_PAESI = new FinderPath(
			AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetModelImpl.FINDER_CACHE_ENABLED,
			AssetEntrySetImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByLtCT_PAESI",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByLtCT_PAESI = new FinderPath(
			AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtCT_PAESI",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByPAESI_CCNI = new FinderPath(
			AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetModelImpl.FINDER_CACHE_ENABLED,
			AssetEntrySetImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByPAESI_CCNI",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByPAESI_CCNI = new FinderPath(
			AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetModelImpl.FINDER_CACHE_ENABLED,
			AssetEntrySetImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByPAESI_CCNI",
			new String[] {Long.class.getName(), Long.class.getName()},
			AssetEntrySetModelImpl.PARENTASSETENTRYSETID_COLUMN_BITMASK |
			AssetEntrySetModelImpl.CREATORCLASSNAMEID_COLUMN_BITMASK |
			AssetEntrySetModelImpl.CREATETIME_COLUMN_BITMASK);

		_finderPathCountByPAESI_CCNI = new FinderPath(
			AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPAESI_CCNI",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByCNI_CPK = new FinderPath(
			AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetModelImpl.FINDER_CACHE_ENABLED,
			AssetEntrySetImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCNI_CPK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCNI_CPK = new FinderPath(
			AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetModelImpl.FINDER_CACHE_ENABLED,
			AssetEntrySetImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCNI_CPK",
			new String[] {Long.class.getName(), Long.class.getName()},
			AssetEntrySetModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			AssetEntrySetModelImpl.CLASSPK_COLUMN_BITMASK |
			AssetEntrySetModelImpl.CREATETIME_COLUMN_BITMASK);

		_finderPathCountByCNI_CPK = new FinderPath(
			AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCNI_CPK",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathFetchByPAESI_CCNI_CCPK = new FinderPath(
			AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetModelImpl.FINDER_CACHE_ENABLED,
			AssetEntrySetImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByPAESI_CCNI_CCPK",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			AssetEntrySetModelImpl.PARENTASSETENTRYSETID_COLUMN_BITMASK |
			AssetEntrySetModelImpl.CREATORCLASSNAMEID_COLUMN_BITMASK |
			AssetEntrySetModelImpl.CREATORCLASSPK_COLUMN_BITMASK);

		_finderPathCountByPAESI_CCNI_CCPK = new FinderPath(
			AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPAESI_CCNI_CCPK",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathFetchByCNI_CPK_Title = new FinderPath(
			AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetModelImpl.FINDER_CACHE_ENABLED,
			AssetEntrySetImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByCNI_CPK_Title",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			AssetEntrySetModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			AssetEntrySetModelImpl.CLASSPK_COLUMN_BITMASK |
			AssetEntrySetModelImpl.TITLE_COLUMN_BITMASK);

		_finderPathCountByCNI_CPK_Title = new FinderPath(
			AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCNI_CPK_Title",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

		_finderPathWithPaginationFindByCNI_CPK_Type = new FinderPath(
			AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetModelImpl.FINDER_CACHE_ENABLED,
			AssetEntrySetImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCNI_CPK_Type",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCNI_CPK_Type = new FinderPath(
			AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetModelImpl.FINDER_CACHE_ENABLED,
			AssetEntrySetImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCNI_CPK_Type",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			AssetEntrySetModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			AssetEntrySetModelImpl.CLASSPK_COLUMN_BITMASK |
			AssetEntrySetModelImpl.TYPE_COLUMN_BITMASK |
			AssetEntrySetModelImpl.CREATETIME_COLUMN_BITMASK);

		_finderPathCountByCNI_CPK_Type = new FinderPath(
			AssetEntrySetModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCNI_CPK_Type",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		AssetEntrySetUtil.setPersistence(this);
	}

	public void destroy() {
		AssetEntrySetUtil.setPersistence(null);

		entityCache.removeCache(AssetEntrySetImpl.class.getName());

		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);

		DBType dbType = DBManagerUtil.getDBType(sessionFactory.getDialect());

		_databaseInMaxParameters = GetterUtil.getInteger(
			PropsUtil.get(
				"database.in.max.parameters", new Filter(dbType.getName())));
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_ASSETENTRYSET =
		"SELECT assetEntrySet FROM AssetEntrySet assetEntrySet";

	private static final String _SQL_SELECT_ASSETENTRYSET_WHERE_PKS_IN =
		"SELECT assetEntrySet FROM AssetEntrySet assetEntrySet WHERE assetEntrySetId IN (";

	private static final String _SQL_SELECT_ASSETENTRYSET_WHERE =
		"SELECT assetEntrySet FROM AssetEntrySet assetEntrySet WHERE ";

	private static final String _SQL_COUNT_ASSETENTRYSET =
		"SELECT COUNT(assetEntrySet) FROM AssetEntrySet assetEntrySet";

	private static final String _SQL_COUNT_ASSETENTRYSET_WHERE =
		"SELECT COUNT(assetEntrySet) FROM AssetEntrySet assetEntrySet WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"assetEntrySet.assetEntrySetId";

	private static final String _FILTER_SQL_SELECT_ASSETENTRYSET_WHERE =
		"SELECT DISTINCT {assetEntrySet.*} FROM AssetEntrySet assetEntrySet WHERE ";

	private static final String
		_FILTER_SQL_SELECT_ASSETENTRYSET_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {AssetEntrySet.*} FROM (SELECT DISTINCT assetEntrySet.assetEntrySetId FROM AssetEntrySet assetEntrySet WHERE ";

	private static final String
		_FILTER_SQL_SELECT_ASSETENTRYSET_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN AssetEntrySet ON TEMP_TABLE.assetEntrySetId = AssetEntrySet.assetEntrySetId";

	private static final String _FILTER_SQL_COUNT_ASSETENTRYSET_WHERE =
		"SELECT COUNT(DISTINCT assetEntrySet.assetEntrySetId) AS COUNT_VALUE FROM AssetEntrySet assetEntrySet WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "assetEntrySet";

	private static final String _FILTER_ENTITY_TABLE = "AssetEntrySet";

	private static final String _ORDER_BY_ENTITY_ALIAS = "assetEntrySet.";

	private static final String _ORDER_BY_ENTITY_TABLE = "AssetEntrySet.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AssetEntrySet exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AssetEntrySet exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AssetEntrySetPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type"});

}