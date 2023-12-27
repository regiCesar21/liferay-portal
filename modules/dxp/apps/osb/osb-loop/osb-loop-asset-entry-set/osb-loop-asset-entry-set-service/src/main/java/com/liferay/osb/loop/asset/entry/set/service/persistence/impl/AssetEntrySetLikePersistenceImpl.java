/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.loop.asset.entry.set.service.persistence.impl;

import com.liferay.osb.loop.asset.entry.set.exception.NoSuchLikeException;
import com.liferay.osb.loop.asset.entry.set.model.AssetEntrySetLike;
import com.liferay.osb.loop.asset.entry.set.model.impl.AssetEntrySetLikeImpl;
import com.liferay.osb.loop.asset.entry.set.model.impl.AssetEntrySetLikeModelImpl;
import com.liferay.osb.loop.asset.entry.set.service.persistence.AssetEntrySetLikePK;
import com.liferay.osb.loop.asset.entry.set.service.persistence.AssetEntrySetLikePersistence;
import com.liferay.osb.loop.asset.entry.set.service.persistence.AssetEntrySetLikeUtil;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the asset entry set like service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetEntrySetLikePersistenceImpl
	extends BasePersistenceImpl<AssetEntrySetLike>
	implements AssetEntrySetLikePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AssetEntrySetLikeUtil</code> to access the asset entry set like persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AssetEntrySetLikeImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByAssetEntrySetId;
	private FinderPath _finderPathWithoutPaginationFindByAssetEntrySetId;
	private FinderPath _finderPathCountByAssetEntrySetId;

	/**
	 * Returns all the asset entry set likes where assetEntrySetId = &#63;.
	 *
	 * @param assetEntrySetId the asset entry set ID
	 * @return the matching asset entry set likes
	 */
	@Override
	public List<AssetEntrySetLike> findByAssetEntrySetId(long assetEntrySetId) {
		return findByAssetEntrySetId(
			assetEntrySetId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry set likes where assetEntrySetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetLikeModelImpl</code>.
	 * </p>
	 *
	 * @param assetEntrySetId the asset entry set ID
	 * @param start the lower bound of the range of asset entry set likes
	 * @param end the upper bound of the range of asset entry set likes (not inclusive)
	 * @return the range of matching asset entry set likes
	 */
	@Override
	public List<AssetEntrySetLike> findByAssetEntrySetId(
		long assetEntrySetId, int start, int end) {

		return findByAssetEntrySetId(assetEntrySetId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry set likes where assetEntrySetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetLikeModelImpl</code>.
	 * </p>
	 *
	 * @param assetEntrySetId the asset entry set ID
	 * @param start the lower bound of the range of asset entry set likes
	 * @param end the upper bound of the range of asset entry set likes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry set likes
	 */
	@Override
	public List<AssetEntrySetLike> findByAssetEntrySetId(
		long assetEntrySetId, int start, int end,
		OrderByComparator<AssetEntrySetLike> orderByComparator) {

		return findByAssetEntrySetId(
			assetEntrySetId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entry set likes where assetEntrySetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetLikeModelImpl</code>.
	 * </p>
	 *
	 * @param assetEntrySetId the asset entry set ID
	 * @param start the lower bound of the range of asset entry set likes
	 * @param end the upper bound of the range of asset entry set likes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entry set likes
	 */
	@Override
	public List<AssetEntrySetLike> findByAssetEntrySetId(
		long assetEntrySetId, int start, int end,
		OrderByComparator<AssetEntrySetLike> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByAssetEntrySetId;
				finderArgs = new Object[] {assetEntrySetId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByAssetEntrySetId;
			finderArgs = new Object[] {
				assetEntrySetId, start, end, orderByComparator
			};
		}

		List<AssetEntrySetLike> list = null;

		if (useFinderCache) {
			list = (List<AssetEntrySetLike>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntrySetLike assetEntrySetLike : list) {
					if (assetEntrySetId !=
							assetEntrySetLike.getAssetEntrySetId()) {

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

			sb.append(_SQL_SELECT_ASSETENTRYSETLIKE_WHERE);

			sb.append(_FINDER_COLUMN_ASSETENTRYSETID_ASSETENTRYSETID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetEntrySetLikeModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(assetEntrySetId);

				list = (List<AssetEntrySetLike>)QueryUtil.list(
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
	 * Returns the first asset entry set like in the ordered set where assetEntrySetId = &#63;.
	 *
	 * @param assetEntrySetId the asset entry set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry set like
	 * @throws NoSuchLikeException if a matching asset entry set like could not be found
	 */
	@Override
	public AssetEntrySetLike findByAssetEntrySetId_First(
			long assetEntrySetId,
			OrderByComparator<AssetEntrySetLike> orderByComparator)
		throws NoSuchLikeException {

		AssetEntrySetLike assetEntrySetLike = fetchByAssetEntrySetId_First(
			assetEntrySetId, orderByComparator);

		if (assetEntrySetLike != null) {
			return assetEntrySetLike;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("assetEntrySetId=");
		sb.append(assetEntrySetId);

		sb.append("}");

		throw new NoSuchLikeException(sb.toString());
	}

	/**
	 * Returns the first asset entry set like in the ordered set where assetEntrySetId = &#63;.
	 *
	 * @param assetEntrySetId the asset entry set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry set like, or <code>null</code> if a matching asset entry set like could not be found
	 */
	@Override
	public AssetEntrySetLike fetchByAssetEntrySetId_First(
		long assetEntrySetId,
		OrderByComparator<AssetEntrySetLike> orderByComparator) {

		List<AssetEntrySetLike> list = findByAssetEntrySetId(
			assetEntrySetId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry set like in the ordered set where assetEntrySetId = &#63;.
	 *
	 * @param assetEntrySetId the asset entry set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry set like
	 * @throws NoSuchLikeException if a matching asset entry set like could not be found
	 */
	@Override
	public AssetEntrySetLike findByAssetEntrySetId_Last(
			long assetEntrySetId,
			OrderByComparator<AssetEntrySetLike> orderByComparator)
		throws NoSuchLikeException {

		AssetEntrySetLike assetEntrySetLike = fetchByAssetEntrySetId_Last(
			assetEntrySetId, orderByComparator);

		if (assetEntrySetLike != null) {
			return assetEntrySetLike;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("assetEntrySetId=");
		sb.append(assetEntrySetId);

		sb.append("}");

		throw new NoSuchLikeException(sb.toString());
	}

	/**
	 * Returns the last asset entry set like in the ordered set where assetEntrySetId = &#63;.
	 *
	 * @param assetEntrySetId the asset entry set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry set like, or <code>null</code> if a matching asset entry set like could not be found
	 */
	@Override
	public AssetEntrySetLike fetchByAssetEntrySetId_Last(
		long assetEntrySetId,
		OrderByComparator<AssetEntrySetLike> orderByComparator) {

		int count = countByAssetEntrySetId(assetEntrySetId);

		if (count == 0) {
			return null;
		}

		List<AssetEntrySetLike> list = findByAssetEntrySetId(
			assetEntrySetId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entry set likes before and after the current asset entry set like in the ordered set where assetEntrySetId = &#63;.
	 *
	 * @param assetEntrySetLikePK the primary key of the current asset entry set like
	 * @param assetEntrySetId the asset entry set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry set like
	 * @throws NoSuchLikeException if a asset entry set like with the primary key could not be found
	 */
	@Override
	public AssetEntrySetLike[] findByAssetEntrySetId_PrevAndNext(
			AssetEntrySetLikePK assetEntrySetLikePK, long assetEntrySetId,
			OrderByComparator<AssetEntrySetLike> orderByComparator)
		throws NoSuchLikeException {

		AssetEntrySetLike assetEntrySetLike = findByPrimaryKey(
			assetEntrySetLikePK);

		Session session = null;

		try {
			session = openSession();

			AssetEntrySetLike[] array = new AssetEntrySetLikeImpl[3];

			array[0] = getByAssetEntrySetId_PrevAndNext(
				session, assetEntrySetLike, assetEntrySetId, orderByComparator,
				true);

			array[1] = assetEntrySetLike;

			array[2] = getByAssetEntrySetId_PrevAndNext(
				session, assetEntrySetLike, assetEntrySetId, orderByComparator,
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

	protected AssetEntrySetLike getByAssetEntrySetId_PrevAndNext(
		Session session, AssetEntrySetLike assetEntrySetLike,
		long assetEntrySetId,
		OrderByComparator<AssetEntrySetLike> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ASSETENTRYSETLIKE_WHERE);

		sb.append(_FINDER_COLUMN_ASSETENTRYSETID_ASSETENTRYSETID_2);

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
			sb.append(AssetEntrySetLikeModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(assetEntrySetId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetEntrySetLike)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetEntrySetLike> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entry set likes where assetEntrySetId = &#63; from the database.
	 *
	 * @param assetEntrySetId the asset entry set ID
	 */
	@Override
	public void removeByAssetEntrySetId(long assetEntrySetId) {
		for (AssetEntrySetLike assetEntrySetLike :
				findByAssetEntrySetId(
					assetEntrySetId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetEntrySetLike);
		}
	}

	/**
	 * Returns the number of asset entry set likes where assetEntrySetId = &#63;.
	 *
	 * @param assetEntrySetId the asset entry set ID
	 * @return the number of matching asset entry set likes
	 */
	@Override
	public int countByAssetEntrySetId(long assetEntrySetId) {
		FinderPath finderPath = _finderPathCountByAssetEntrySetId;

		Object[] finderArgs = new Object[] {assetEntrySetId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ASSETENTRYSETLIKE_WHERE);

			sb.append(_FINDER_COLUMN_ASSETENTRYSETID_ASSETENTRYSETID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(assetEntrySetId);

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
		_FINDER_COLUMN_ASSETENTRYSETID_ASSETENTRYSETID_2 =
			"assetEntrySetLike.id.assetEntrySetId = ?";

	public AssetEntrySetLikePersistenceImpl() {
		setModelClass(AssetEntrySetLike.class);
	}

	/**
	 * Caches the asset entry set like in the entity cache if it is enabled.
	 *
	 * @param assetEntrySetLike the asset entry set like
	 */
	@Override
	public void cacheResult(AssetEntrySetLike assetEntrySetLike) {
		entityCache.putResult(
			AssetEntrySetLikeModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetLikeImpl.class, assetEntrySetLike.getPrimaryKey(),
			assetEntrySetLike);

		assetEntrySetLike.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the asset entry set likes in the entity cache if it is enabled.
	 *
	 * @param assetEntrySetLikes the asset entry set likes
	 */
	@Override
	public void cacheResult(List<AssetEntrySetLike> assetEntrySetLikes) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (assetEntrySetLikes.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (AssetEntrySetLike assetEntrySetLike : assetEntrySetLikes) {
			if (entityCache.getResult(
					AssetEntrySetLikeModelImpl.ENTITY_CACHE_ENABLED,
					AssetEntrySetLikeImpl.class,
					assetEntrySetLike.getPrimaryKey()) == null) {

				cacheResult(assetEntrySetLike);
			}
			else {
				assetEntrySetLike.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset entry set likes.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AssetEntrySetLikeImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset entry set like.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetEntrySetLike assetEntrySetLike) {
		entityCache.removeResult(
			AssetEntrySetLikeModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetLikeImpl.class, assetEntrySetLike.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<AssetEntrySetLike> assetEntrySetLikes) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetEntrySetLike assetEntrySetLike : assetEntrySetLikes) {
			entityCache.removeResult(
				AssetEntrySetLikeModelImpl.ENTITY_CACHE_ENABLED,
				AssetEntrySetLikeImpl.class, assetEntrySetLike.getPrimaryKey());
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				AssetEntrySetLikeModelImpl.ENTITY_CACHE_ENABLED,
				AssetEntrySetLikeImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new asset entry set like with the primary key. Does not add the asset entry set like to the database.
	 *
	 * @param assetEntrySetLikePK the primary key for the new asset entry set like
	 * @return the new asset entry set like
	 */
	@Override
	public AssetEntrySetLike create(AssetEntrySetLikePK assetEntrySetLikePK) {
		AssetEntrySetLike assetEntrySetLike = new AssetEntrySetLikeImpl();

		assetEntrySetLike.setNew(true);
		assetEntrySetLike.setPrimaryKey(assetEntrySetLikePK);

		return assetEntrySetLike;
	}

	/**
	 * Removes the asset entry set like with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntrySetLikePK the primary key of the asset entry set like
	 * @return the asset entry set like that was removed
	 * @throws NoSuchLikeException if a asset entry set like with the primary key could not be found
	 */
	@Override
	public AssetEntrySetLike remove(AssetEntrySetLikePK assetEntrySetLikePK)
		throws NoSuchLikeException {

		return remove((Serializable)assetEntrySetLikePK);
	}

	/**
	 * Removes the asset entry set like with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset entry set like
	 * @return the asset entry set like that was removed
	 * @throws NoSuchLikeException if a asset entry set like with the primary key could not be found
	 */
	@Override
	public AssetEntrySetLike remove(Serializable primaryKey)
		throws NoSuchLikeException {

		Session session = null;

		try {
			session = openSession();

			AssetEntrySetLike assetEntrySetLike =
				(AssetEntrySetLike)session.get(
					AssetEntrySetLikeImpl.class, primaryKey);

			if (assetEntrySetLike == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLikeException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(assetEntrySetLike);
		}
		catch (NoSuchLikeException noSuchEntityException) {
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
	protected AssetEntrySetLike removeImpl(
		AssetEntrySetLike assetEntrySetLike) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetEntrySetLike)) {
				assetEntrySetLike = (AssetEntrySetLike)session.get(
					AssetEntrySetLikeImpl.class,
					assetEntrySetLike.getPrimaryKeyObj());
			}

			if (assetEntrySetLike != null) {
				session.delete(assetEntrySetLike);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (assetEntrySetLike != null) {
			clearCache(assetEntrySetLike);
		}

		return assetEntrySetLike;
	}

	@Override
	public AssetEntrySetLike updateImpl(AssetEntrySetLike assetEntrySetLike) {
		boolean isNew = assetEntrySetLike.isNew();

		if (!(assetEntrySetLike instanceof AssetEntrySetLikeModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(assetEntrySetLike.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					assetEntrySetLike);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in assetEntrySetLike proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AssetEntrySetLike implementation " +
					assetEntrySetLike.getClass());
		}

		AssetEntrySetLikeModelImpl assetEntrySetLikeModelImpl =
			(AssetEntrySetLikeModelImpl)assetEntrySetLike;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(assetEntrySetLike);

				assetEntrySetLike.setNew(false);
			}
			else {
				assetEntrySetLike = (AssetEntrySetLike)session.merge(
					assetEntrySetLike);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!AssetEntrySetLikeModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				assetEntrySetLikeModelImpl.getAssetEntrySetId()
			};

			finderCache.removeResult(_finderPathCountByAssetEntrySetId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByAssetEntrySetId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((assetEntrySetLikeModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByAssetEntrySetId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					assetEntrySetLikeModelImpl.getOriginalAssetEntrySetId()
				};

				finderCache.removeResult(
					_finderPathCountByAssetEntrySetId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAssetEntrySetId, args);

				args = new Object[] {
					assetEntrySetLikeModelImpl.getAssetEntrySetId()
				};

				finderCache.removeResult(
					_finderPathCountByAssetEntrySetId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAssetEntrySetId, args);
			}
		}

		entityCache.putResult(
			AssetEntrySetLikeModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetLikeImpl.class, assetEntrySetLike.getPrimaryKey(),
			assetEntrySetLike, false);

		assetEntrySetLike.resetOriginalValues();

		return assetEntrySetLike;
	}

	/**
	 * Returns the asset entry set like with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset entry set like
	 * @return the asset entry set like
	 * @throws NoSuchLikeException if a asset entry set like with the primary key could not be found
	 */
	@Override
	public AssetEntrySetLike findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLikeException {

		AssetEntrySetLike assetEntrySetLike = fetchByPrimaryKey(primaryKey);

		if (assetEntrySetLike == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLikeException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return assetEntrySetLike;
	}

	/**
	 * Returns the asset entry set like with the primary key or throws a <code>NoSuchLikeException</code> if it could not be found.
	 *
	 * @param assetEntrySetLikePK the primary key of the asset entry set like
	 * @return the asset entry set like
	 * @throws NoSuchLikeException if a asset entry set like with the primary key could not be found
	 */
	@Override
	public AssetEntrySetLike findByPrimaryKey(
			AssetEntrySetLikePK assetEntrySetLikePK)
		throws NoSuchLikeException {

		return findByPrimaryKey((Serializable)assetEntrySetLikePK);
	}

	/**
	 * Returns the asset entry set like with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset entry set like
	 * @return the asset entry set like, or <code>null</code> if a asset entry set like with the primary key could not be found
	 */
	@Override
	public AssetEntrySetLike fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			AssetEntrySetLikeModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetLikeImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		AssetEntrySetLike assetEntrySetLike = (AssetEntrySetLike)serializable;

		if (assetEntrySetLike == null) {
			Session session = null;

			try {
				session = openSession();

				assetEntrySetLike = (AssetEntrySetLike)session.get(
					AssetEntrySetLikeImpl.class, primaryKey);

				if (assetEntrySetLike != null) {
					cacheResult(assetEntrySetLike);
				}
				else {
					entityCache.putResult(
						AssetEntrySetLikeModelImpl.ENTITY_CACHE_ENABLED,
						AssetEntrySetLikeImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					AssetEntrySetLikeModelImpl.ENTITY_CACHE_ENABLED,
					AssetEntrySetLikeImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return assetEntrySetLike;
	}

	/**
	 * Returns the asset entry set like with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetEntrySetLikePK the primary key of the asset entry set like
	 * @return the asset entry set like, or <code>null</code> if a asset entry set like with the primary key could not be found
	 */
	@Override
	public AssetEntrySetLike fetchByPrimaryKey(
		AssetEntrySetLikePK assetEntrySetLikePK) {

		return fetchByPrimaryKey((Serializable)assetEntrySetLikePK);
	}

	@Override
	public Map<Serializable, AssetEntrySetLike> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AssetEntrySetLike> map =
			new HashMap<Serializable, AssetEntrySetLike>();

		for (Serializable primaryKey : primaryKeys) {
			AssetEntrySetLike assetEntrySetLike = fetchByPrimaryKey(primaryKey);

			if (assetEntrySetLike != null) {
				map.put(primaryKey, assetEntrySetLike);
			}
		}

		return map;
	}

	/**
	 * Returns all the asset entry set likes.
	 *
	 * @return the asset entry set likes
	 */
	@Override
	public List<AssetEntrySetLike> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry set likes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetLikeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry set likes
	 * @param end the upper bound of the range of asset entry set likes (not inclusive)
	 * @return the range of asset entry set likes
	 */
	@Override
	public List<AssetEntrySetLike> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry set likes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetLikeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry set likes
	 * @param end the upper bound of the range of asset entry set likes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset entry set likes
	 */
	@Override
	public List<AssetEntrySetLike> findAll(
		int start, int end,
		OrderByComparator<AssetEntrySetLike> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entry set likes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntrySetLikeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry set likes
	 * @param end the upper bound of the range of asset entry set likes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset entry set likes
	 */
	@Override
	public List<AssetEntrySetLike> findAll(
		int start, int end,
		OrderByComparator<AssetEntrySetLike> orderByComparator,
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

		List<AssetEntrySetLike> list = null;

		if (useFinderCache) {
			list = (List<AssetEntrySetLike>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ASSETENTRYSETLIKE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETENTRYSETLIKE;

				sql = sql.concat(AssetEntrySetLikeModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AssetEntrySetLike>)QueryUtil.list(
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
	 * Removes all the asset entry set likes from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetEntrySetLike assetEntrySetLike : findAll()) {
			remove(assetEntrySetLike);
		}
	}

	/**
	 * Returns the number of asset entry set likes.
	 *
	 * @return the number of asset entry set likes
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_ASSETENTRYSETLIKE);

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
	public Set<String> getCompoundPKColumnNames() {
		return _compoundPKColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AssetEntrySetLikeModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the asset entry set like persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			AssetEntrySetLikeModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetLikeModelImpl.FINDER_CACHE_ENABLED,
			AssetEntrySetLikeImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			AssetEntrySetLikeModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetLikeModelImpl.FINDER_CACHE_ENABLED,
			AssetEntrySetLikeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			AssetEntrySetLikeModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetLikeModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByAssetEntrySetId = new FinderPath(
			AssetEntrySetLikeModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetLikeModelImpl.FINDER_CACHE_ENABLED,
			AssetEntrySetLikeImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByAssetEntrySetId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByAssetEntrySetId = new FinderPath(
			AssetEntrySetLikeModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetLikeModelImpl.FINDER_CACHE_ENABLED,
			AssetEntrySetLikeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAssetEntrySetId",
			new String[] {Long.class.getName()},
			AssetEntrySetLikeModelImpl.ASSETENTRYSETID_COLUMN_BITMASK);

		_finderPathCountByAssetEntrySetId = new FinderPath(
			AssetEntrySetLikeModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntrySetLikeModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAssetEntrySetId",
			new String[] {Long.class.getName()});

		AssetEntrySetLikeUtil.setPersistence(this);
	}

	public void destroy() {
		AssetEntrySetLikeUtil.setPersistence(null);

		entityCache.removeCache(AssetEntrySetLikeImpl.class.getName());

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

	private static final String _SQL_SELECT_ASSETENTRYSETLIKE =
		"SELECT assetEntrySetLike FROM AssetEntrySetLike assetEntrySetLike";

	private static final String _SQL_SELECT_ASSETENTRYSETLIKE_WHERE =
		"SELECT assetEntrySetLike FROM AssetEntrySetLike assetEntrySetLike WHERE ";

	private static final String _SQL_COUNT_ASSETENTRYSETLIKE =
		"SELECT COUNT(assetEntrySetLike) FROM AssetEntrySetLike assetEntrySetLike";

	private static final String _SQL_COUNT_ASSETENTRYSETLIKE_WHERE =
		"SELECT COUNT(assetEntrySetLike) FROM AssetEntrySetLike assetEntrySetLike WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "assetEntrySetLike.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AssetEntrySetLike exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AssetEntrySetLike exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AssetEntrySetLikePersistenceImpl.class);

	private static final Set<String> _compoundPKColumnNames = SetUtil.fromArray(
		new String[] {"assetEntrySetId", "classNameId", "classPK"});

}