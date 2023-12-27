/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.loop.asset.sharing.service.persistence.impl;

import com.liferay.osb.loop.asset.sharing.exception.NoSuchEntryException;
import com.liferay.osb.loop.asset.sharing.model.AssetSharingEntry;
import com.liferay.osb.loop.asset.sharing.model.impl.AssetSharingEntryImpl;
import com.liferay.osb.loop.asset.sharing.model.impl.AssetSharingEntryModelImpl;
import com.liferay.osb.loop.asset.sharing.service.persistence.AssetSharingEntryPK;
import com.liferay.osb.loop.asset.sharing.service.persistence.AssetSharingEntryPersistence;
import com.liferay.osb.loop.asset.sharing.service.persistence.AssetSharingEntryUtil;
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
 * The persistence implementation for the asset sharing entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetSharingEntryPersistenceImpl
	extends BasePersistenceImpl<AssetSharingEntry>
	implements AssetSharingEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AssetSharingEntryUtil</code> to access the asset sharing entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AssetSharingEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the asset sharing entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching asset sharing entries
	 */
	@Override
	public List<AssetSharingEntry> findByC_C(long classNameId, long classPK) {
		return findByC_C(
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset sharing entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetSharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of asset sharing entries
	 * @param end the upper bound of the range of asset sharing entries (not inclusive)
	 * @return the range of matching asset sharing entries
	 */
	@Override
	public List<AssetSharingEntry> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset sharing entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetSharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of asset sharing entries
	 * @param end the upper bound of the range of asset sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset sharing entries
	 */
	@Override
	public List<AssetSharingEntry> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<AssetSharingEntry> orderByComparator) {

		return findByC_C(
			classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset sharing entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetSharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of asset sharing entries
	 * @param end the upper bound of the range of asset sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset sharing entries
	 */
	@Override
	public List<AssetSharingEntry> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<AssetSharingEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {classNameId, classPK};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				classNameId, classPK, start, end, orderByComparator
			};
		}

		List<AssetSharingEntry> list = null;

		if (useFinderCache) {
			list = (List<AssetSharingEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetSharingEntry assetSharingEntry : list) {
					if ((classNameId != assetSharingEntry.getClassNameId()) ||
						(classPK != assetSharingEntry.getClassPK())) {

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

			sb.append(_SQL_SELECT_ASSETSHARINGENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetSharingEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<AssetSharingEntry>)QueryUtil.list(
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
	 * Returns the first asset sharing entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset sharing entry
	 * @throws NoSuchEntryException if a matching asset sharing entry could not be found
	 */
	@Override
	public AssetSharingEntry findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<AssetSharingEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetSharingEntry assetSharingEntry = fetchByC_C_First(
			classNameId, classPK, orderByComparator);

		if (assetSharingEntry != null) {
			return assetSharingEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first asset sharing entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset sharing entry, or <code>null</code> if a matching asset sharing entry could not be found
	 */
	@Override
	public AssetSharingEntry fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<AssetSharingEntry> orderByComparator) {

		List<AssetSharingEntry> list = findByC_C(
			classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset sharing entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset sharing entry
	 * @throws NoSuchEntryException if a matching asset sharing entry could not be found
	 */
	@Override
	public AssetSharingEntry findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<AssetSharingEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetSharingEntry assetSharingEntry = fetchByC_C_Last(
			classNameId, classPK, orderByComparator);

		if (assetSharingEntry != null) {
			return assetSharingEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last asset sharing entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset sharing entry, or <code>null</code> if a matching asset sharing entry could not be found
	 */
	@Override
	public AssetSharingEntry fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<AssetSharingEntry> orderByComparator) {

		int count = countByC_C(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<AssetSharingEntry> list = findByC_C(
			classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset sharing entries before and after the current asset sharing entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param assetSharingEntryPK the primary key of the current asset sharing entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset sharing entry
	 * @throws NoSuchEntryException if a asset sharing entry with the primary key could not be found
	 */
	@Override
	public AssetSharingEntry[] findByC_C_PrevAndNext(
			AssetSharingEntryPK assetSharingEntryPK, long classNameId,
			long classPK,
			OrderByComparator<AssetSharingEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetSharingEntry assetSharingEntry = findByPrimaryKey(
			assetSharingEntryPK);

		Session session = null;

		try {
			session = openSession();

			AssetSharingEntry[] array = new AssetSharingEntryImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, assetSharingEntry, classNameId, classPK,
				orderByComparator, true);

			array[1] = assetSharingEntry;

			array[2] = getByC_C_PrevAndNext(
				session, assetSharingEntry, classNameId, classPK,
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

	protected AssetSharingEntry getByC_C_PrevAndNext(
		Session session, AssetSharingEntry assetSharingEntry, long classNameId,
		long classPK, OrderByComparator<AssetSharingEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETSHARINGENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

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
			sb.append(AssetSharingEntryModelImpl.ORDER_BY_JPQL);
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
						assetSharingEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetSharingEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset sharing entries where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		for (AssetSharingEntry assetSharingEntry :
				findByC_C(
					classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetSharingEntry);
		}
	}

	/**
	 * Returns the number of asset sharing entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching asset sharing entries
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {classNameId, classPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ASSETSHARINGENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"assetSharingEntry.id.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"assetSharingEntry.id.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByS_S;
	private FinderPath _finderPathWithoutPaginationFindByS_S;
	private FinderPath _finderPathCountByS_S;

	/**
	 * Returns all the asset sharing entries where sharedToClassNameId = &#63; and sharedToClassPK = &#63;.
	 *
	 * @param sharedToClassNameId the shared to class name ID
	 * @param sharedToClassPK the shared to class pk
	 * @return the matching asset sharing entries
	 */
	@Override
	public List<AssetSharingEntry> findByS_S(
		long sharedToClassNameId, long sharedToClassPK) {

		return findByS_S(
			sharedToClassNameId, sharedToClassPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset sharing entries where sharedToClassNameId = &#63; and sharedToClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetSharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sharedToClassNameId the shared to class name ID
	 * @param sharedToClassPK the shared to class pk
	 * @param start the lower bound of the range of asset sharing entries
	 * @param end the upper bound of the range of asset sharing entries (not inclusive)
	 * @return the range of matching asset sharing entries
	 */
	@Override
	public List<AssetSharingEntry> findByS_S(
		long sharedToClassNameId, long sharedToClassPK, int start, int end) {

		return findByS_S(
			sharedToClassNameId, sharedToClassPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset sharing entries where sharedToClassNameId = &#63; and sharedToClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetSharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sharedToClassNameId the shared to class name ID
	 * @param sharedToClassPK the shared to class pk
	 * @param start the lower bound of the range of asset sharing entries
	 * @param end the upper bound of the range of asset sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset sharing entries
	 */
	@Override
	public List<AssetSharingEntry> findByS_S(
		long sharedToClassNameId, long sharedToClassPK, int start, int end,
		OrderByComparator<AssetSharingEntry> orderByComparator) {

		return findByS_S(
			sharedToClassNameId, sharedToClassPK, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the asset sharing entries where sharedToClassNameId = &#63; and sharedToClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetSharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sharedToClassNameId the shared to class name ID
	 * @param sharedToClassPK the shared to class pk
	 * @param start the lower bound of the range of asset sharing entries
	 * @param end the upper bound of the range of asset sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset sharing entries
	 */
	@Override
	public List<AssetSharingEntry> findByS_S(
		long sharedToClassNameId, long sharedToClassPK, int start, int end,
		OrderByComparator<AssetSharingEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByS_S;
				finderArgs = new Object[] {
					sharedToClassNameId, sharedToClassPK
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByS_S;
			finderArgs = new Object[] {
				sharedToClassNameId, sharedToClassPK, start, end,
				orderByComparator
			};
		}

		List<AssetSharingEntry> list = null;

		if (useFinderCache) {
			list = (List<AssetSharingEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetSharingEntry assetSharingEntry : list) {
					if ((sharedToClassNameId !=
							assetSharingEntry.getSharedToClassNameId()) ||
						(sharedToClassPK !=
							assetSharingEntry.getSharedToClassPK())) {

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

			sb.append(_SQL_SELECT_ASSETSHARINGENTRY_WHERE);

			sb.append(_FINDER_COLUMN_S_S_SHAREDTOCLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_S_S_SHAREDTOCLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetSharingEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(sharedToClassNameId);

				queryPos.add(sharedToClassPK);

				list = (List<AssetSharingEntry>)QueryUtil.list(
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
	 * Returns the first asset sharing entry in the ordered set where sharedToClassNameId = &#63; and sharedToClassPK = &#63;.
	 *
	 * @param sharedToClassNameId the shared to class name ID
	 * @param sharedToClassPK the shared to class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset sharing entry
	 * @throws NoSuchEntryException if a matching asset sharing entry could not be found
	 */
	@Override
	public AssetSharingEntry findByS_S_First(
			long sharedToClassNameId, long sharedToClassPK,
			OrderByComparator<AssetSharingEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetSharingEntry assetSharingEntry = fetchByS_S_First(
			sharedToClassNameId, sharedToClassPK, orderByComparator);

		if (assetSharingEntry != null) {
			return assetSharingEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("sharedToClassNameId=");
		sb.append(sharedToClassNameId);

		sb.append(", sharedToClassPK=");
		sb.append(sharedToClassPK);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first asset sharing entry in the ordered set where sharedToClassNameId = &#63; and sharedToClassPK = &#63;.
	 *
	 * @param sharedToClassNameId the shared to class name ID
	 * @param sharedToClassPK the shared to class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset sharing entry, or <code>null</code> if a matching asset sharing entry could not be found
	 */
	@Override
	public AssetSharingEntry fetchByS_S_First(
		long sharedToClassNameId, long sharedToClassPK,
		OrderByComparator<AssetSharingEntry> orderByComparator) {

		List<AssetSharingEntry> list = findByS_S(
			sharedToClassNameId, sharedToClassPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset sharing entry in the ordered set where sharedToClassNameId = &#63; and sharedToClassPK = &#63;.
	 *
	 * @param sharedToClassNameId the shared to class name ID
	 * @param sharedToClassPK the shared to class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset sharing entry
	 * @throws NoSuchEntryException if a matching asset sharing entry could not be found
	 */
	@Override
	public AssetSharingEntry findByS_S_Last(
			long sharedToClassNameId, long sharedToClassPK,
			OrderByComparator<AssetSharingEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetSharingEntry assetSharingEntry = fetchByS_S_Last(
			sharedToClassNameId, sharedToClassPK, orderByComparator);

		if (assetSharingEntry != null) {
			return assetSharingEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("sharedToClassNameId=");
		sb.append(sharedToClassNameId);

		sb.append(", sharedToClassPK=");
		sb.append(sharedToClassPK);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last asset sharing entry in the ordered set where sharedToClassNameId = &#63; and sharedToClassPK = &#63;.
	 *
	 * @param sharedToClassNameId the shared to class name ID
	 * @param sharedToClassPK the shared to class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset sharing entry, or <code>null</code> if a matching asset sharing entry could not be found
	 */
	@Override
	public AssetSharingEntry fetchByS_S_Last(
		long sharedToClassNameId, long sharedToClassPK,
		OrderByComparator<AssetSharingEntry> orderByComparator) {

		int count = countByS_S(sharedToClassNameId, sharedToClassPK);

		if (count == 0) {
			return null;
		}

		List<AssetSharingEntry> list = findByS_S(
			sharedToClassNameId, sharedToClassPK, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset sharing entries before and after the current asset sharing entry in the ordered set where sharedToClassNameId = &#63; and sharedToClassPK = &#63;.
	 *
	 * @param assetSharingEntryPK the primary key of the current asset sharing entry
	 * @param sharedToClassNameId the shared to class name ID
	 * @param sharedToClassPK the shared to class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset sharing entry
	 * @throws NoSuchEntryException if a asset sharing entry with the primary key could not be found
	 */
	@Override
	public AssetSharingEntry[] findByS_S_PrevAndNext(
			AssetSharingEntryPK assetSharingEntryPK, long sharedToClassNameId,
			long sharedToClassPK,
			OrderByComparator<AssetSharingEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetSharingEntry assetSharingEntry = findByPrimaryKey(
			assetSharingEntryPK);

		Session session = null;

		try {
			session = openSession();

			AssetSharingEntry[] array = new AssetSharingEntryImpl[3];

			array[0] = getByS_S_PrevAndNext(
				session, assetSharingEntry, sharedToClassNameId,
				sharedToClassPK, orderByComparator, true);

			array[1] = assetSharingEntry;

			array[2] = getByS_S_PrevAndNext(
				session, assetSharingEntry, sharedToClassNameId,
				sharedToClassPK, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetSharingEntry getByS_S_PrevAndNext(
		Session session, AssetSharingEntry assetSharingEntry,
		long sharedToClassNameId, long sharedToClassPK,
		OrderByComparator<AssetSharingEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETSHARINGENTRY_WHERE);

		sb.append(_FINDER_COLUMN_S_S_SHAREDTOCLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_S_S_SHAREDTOCLASSPK_2);

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
			sb.append(AssetSharingEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(sharedToClassNameId);

		queryPos.add(sharedToClassPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetSharingEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetSharingEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset sharing entries where sharedToClassNameId = &#63; and sharedToClassPK = &#63; from the database.
	 *
	 * @param sharedToClassNameId the shared to class name ID
	 * @param sharedToClassPK the shared to class pk
	 */
	@Override
	public void removeByS_S(long sharedToClassNameId, long sharedToClassPK) {
		for (AssetSharingEntry assetSharingEntry :
				findByS_S(
					sharedToClassNameId, sharedToClassPK, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(assetSharingEntry);
		}
	}

	/**
	 * Returns the number of asset sharing entries where sharedToClassNameId = &#63; and sharedToClassPK = &#63;.
	 *
	 * @param sharedToClassNameId the shared to class name ID
	 * @param sharedToClassPK the shared to class pk
	 * @return the number of matching asset sharing entries
	 */
	@Override
	public int countByS_S(long sharedToClassNameId, long sharedToClassPK) {
		FinderPath finderPath = _finderPathCountByS_S;

		Object[] finderArgs = new Object[] {
			sharedToClassNameId, sharedToClassPK
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ASSETSHARINGENTRY_WHERE);

			sb.append(_FINDER_COLUMN_S_S_SHAREDTOCLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_S_S_SHAREDTOCLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(sharedToClassNameId);

				queryPos.add(sharedToClassPK);

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

	private static final String _FINDER_COLUMN_S_S_SHAREDTOCLASSNAMEID_2 =
		"assetSharingEntry.id.sharedToClassNameId = ? AND ";

	private static final String _FINDER_COLUMN_S_S_SHAREDTOCLASSPK_2 =
		"assetSharingEntry.id.sharedToClassPK = ?";

	private FinderPath _finderPathWithPaginationFindByC_C_S;
	private FinderPath _finderPathWithoutPaginationFindByC_C_S;
	private FinderPath _finderPathCountByC_C_S;

	/**
	 * Returns all the asset sharing entries where classNameId = &#63; and classPK = &#63; and sharedToClassNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sharedToClassNameId the shared to class name ID
	 * @return the matching asset sharing entries
	 */
	@Override
	public List<AssetSharingEntry> findByC_C_S(
		long classNameId, long classPK, long sharedToClassNameId) {

		return findByC_C_S(
			classNameId, classPK, sharedToClassNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset sharing entries where classNameId = &#63; and classPK = &#63; and sharedToClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetSharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sharedToClassNameId the shared to class name ID
	 * @param start the lower bound of the range of asset sharing entries
	 * @param end the upper bound of the range of asset sharing entries (not inclusive)
	 * @return the range of matching asset sharing entries
	 */
	@Override
	public List<AssetSharingEntry> findByC_C_S(
		long classNameId, long classPK, long sharedToClassNameId, int start,
		int end) {

		return findByC_C_S(
			classNameId, classPK, sharedToClassNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset sharing entries where classNameId = &#63; and classPK = &#63; and sharedToClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetSharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sharedToClassNameId the shared to class name ID
	 * @param start the lower bound of the range of asset sharing entries
	 * @param end the upper bound of the range of asset sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset sharing entries
	 */
	@Override
	public List<AssetSharingEntry> findByC_C_S(
		long classNameId, long classPK, long sharedToClassNameId, int start,
		int end, OrderByComparator<AssetSharingEntry> orderByComparator) {

		return findByC_C_S(
			classNameId, classPK, sharedToClassNameId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset sharing entries where classNameId = &#63; and classPK = &#63; and sharedToClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetSharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sharedToClassNameId the shared to class name ID
	 * @param start the lower bound of the range of asset sharing entries
	 * @param end the upper bound of the range of asset sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset sharing entries
	 */
	@Override
	public List<AssetSharingEntry> findByC_C_S(
		long classNameId, long classPK, long sharedToClassNameId, int start,
		int end, OrderByComparator<AssetSharingEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C_S;
				finderArgs = new Object[] {
					classNameId, classPK, sharedToClassNameId
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C_S;
			finderArgs = new Object[] {
				classNameId, classPK, sharedToClassNameId, start, end,
				orderByComparator
			};
		}

		List<AssetSharingEntry> list = null;

		if (useFinderCache) {
			list = (List<AssetSharingEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetSharingEntry assetSharingEntry : list) {
					if ((classNameId != assetSharingEntry.getClassNameId()) ||
						(classPK != assetSharingEntry.getClassPK()) ||
						(sharedToClassNameId !=
							assetSharingEntry.getSharedToClassNameId())) {

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

			sb.append(_SQL_SELECT_ASSETSHARINGENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_S_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_S_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_S_SHAREDTOCLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetSharingEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(sharedToClassNameId);

				list = (List<AssetSharingEntry>)QueryUtil.list(
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
	 * Returns the first asset sharing entry in the ordered set where classNameId = &#63; and classPK = &#63; and sharedToClassNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sharedToClassNameId the shared to class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset sharing entry
	 * @throws NoSuchEntryException if a matching asset sharing entry could not be found
	 */
	@Override
	public AssetSharingEntry findByC_C_S_First(
			long classNameId, long classPK, long sharedToClassNameId,
			OrderByComparator<AssetSharingEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetSharingEntry assetSharingEntry = fetchByC_C_S_First(
			classNameId, classPK, sharedToClassNameId, orderByComparator);

		if (assetSharingEntry != null) {
			return assetSharingEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", sharedToClassNameId=");
		sb.append(sharedToClassNameId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first asset sharing entry in the ordered set where classNameId = &#63; and classPK = &#63; and sharedToClassNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sharedToClassNameId the shared to class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset sharing entry, or <code>null</code> if a matching asset sharing entry could not be found
	 */
	@Override
	public AssetSharingEntry fetchByC_C_S_First(
		long classNameId, long classPK, long sharedToClassNameId,
		OrderByComparator<AssetSharingEntry> orderByComparator) {

		List<AssetSharingEntry> list = findByC_C_S(
			classNameId, classPK, sharedToClassNameId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset sharing entry in the ordered set where classNameId = &#63; and classPK = &#63; and sharedToClassNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sharedToClassNameId the shared to class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset sharing entry
	 * @throws NoSuchEntryException if a matching asset sharing entry could not be found
	 */
	@Override
	public AssetSharingEntry findByC_C_S_Last(
			long classNameId, long classPK, long sharedToClassNameId,
			OrderByComparator<AssetSharingEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetSharingEntry assetSharingEntry = fetchByC_C_S_Last(
			classNameId, classPK, sharedToClassNameId, orderByComparator);

		if (assetSharingEntry != null) {
			return assetSharingEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", sharedToClassNameId=");
		sb.append(sharedToClassNameId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last asset sharing entry in the ordered set where classNameId = &#63; and classPK = &#63; and sharedToClassNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sharedToClassNameId the shared to class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset sharing entry, or <code>null</code> if a matching asset sharing entry could not be found
	 */
	@Override
	public AssetSharingEntry fetchByC_C_S_Last(
		long classNameId, long classPK, long sharedToClassNameId,
		OrderByComparator<AssetSharingEntry> orderByComparator) {

		int count = countByC_C_S(classNameId, classPK, sharedToClassNameId);

		if (count == 0) {
			return null;
		}

		List<AssetSharingEntry> list = findByC_C_S(
			classNameId, classPK, sharedToClassNameId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset sharing entries before and after the current asset sharing entry in the ordered set where classNameId = &#63; and classPK = &#63; and sharedToClassNameId = &#63;.
	 *
	 * @param assetSharingEntryPK the primary key of the current asset sharing entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sharedToClassNameId the shared to class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset sharing entry
	 * @throws NoSuchEntryException if a asset sharing entry with the primary key could not be found
	 */
	@Override
	public AssetSharingEntry[] findByC_C_S_PrevAndNext(
			AssetSharingEntryPK assetSharingEntryPK, long classNameId,
			long classPK, long sharedToClassNameId,
			OrderByComparator<AssetSharingEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetSharingEntry assetSharingEntry = findByPrimaryKey(
			assetSharingEntryPK);

		Session session = null;

		try {
			session = openSession();

			AssetSharingEntry[] array = new AssetSharingEntryImpl[3];

			array[0] = getByC_C_S_PrevAndNext(
				session, assetSharingEntry, classNameId, classPK,
				sharedToClassNameId, orderByComparator, true);

			array[1] = assetSharingEntry;

			array[2] = getByC_C_S_PrevAndNext(
				session, assetSharingEntry, classNameId, classPK,
				sharedToClassNameId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetSharingEntry getByC_C_S_PrevAndNext(
		Session session, AssetSharingEntry assetSharingEntry, long classNameId,
		long classPK, long sharedToClassNameId,
		OrderByComparator<AssetSharingEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETSHARINGENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_C_S_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_S_CLASSPK_2);

		sb.append(_FINDER_COLUMN_C_C_S_SHAREDTOCLASSNAMEID_2);

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
			sb.append(AssetSharingEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		queryPos.add(sharedToClassNameId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetSharingEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetSharingEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset sharing entries where classNameId = &#63; and classPK = &#63; and sharedToClassNameId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sharedToClassNameId the shared to class name ID
	 */
	@Override
	public void removeByC_C_S(
		long classNameId, long classPK, long sharedToClassNameId) {

		for (AssetSharingEntry assetSharingEntry :
				findByC_C_S(
					classNameId, classPK, sharedToClassNameId,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetSharingEntry);
		}
	}

	/**
	 * Returns the number of asset sharing entries where classNameId = &#63; and classPK = &#63; and sharedToClassNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sharedToClassNameId the shared to class name ID
	 * @return the number of matching asset sharing entries
	 */
	@Override
	public int countByC_C_S(
		long classNameId, long classPK, long sharedToClassNameId) {

		FinderPath finderPath = _finderPathCountByC_C_S;

		Object[] finderArgs = new Object[] {
			classNameId, classPK, sharedToClassNameId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_ASSETSHARINGENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_S_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_S_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_S_SHAREDTOCLASSNAMEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(sharedToClassNameId);

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

	private static final String _FINDER_COLUMN_C_C_S_CLASSNAMEID_2 =
		"assetSharingEntry.id.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_S_CLASSPK_2 =
		"assetSharingEntry.id.classPK = ? AND ";

	private static final String _FINDER_COLUMN_C_C_S_SHAREDTOCLASSNAMEID_2 =
		"assetSharingEntry.id.sharedToClassNameId = ?";

	private FinderPath _finderPathWithPaginationFindByC_S_S;
	private FinderPath _finderPathWithoutPaginationFindByC_S_S;
	private FinderPath _finderPathCountByC_S_S;

	/**
	 * Returns all the asset sharing entries where classNameId = &#63; and sharedToClassNameId = &#63; and sharedToClassPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param sharedToClassNameId the shared to class name ID
	 * @param sharedToClassPK the shared to class pk
	 * @return the matching asset sharing entries
	 */
	@Override
	public List<AssetSharingEntry> findByC_S_S(
		long classNameId, long sharedToClassNameId, long sharedToClassPK) {

		return findByC_S_S(
			classNameId, sharedToClassNameId, sharedToClassPK,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset sharing entries where classNameId = &#63; and sharedToClassNameId = &#63; and sharedToClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetSharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param sharedToClassNameId the shared to class name ID
	 * @param sharedToClassPK the shared to class pk
	 * @param start the lower bound of the range of asset sharing entries
	 * @param end the upper bound of the range of asset sharing entries (not inclusive)
	 * @return the range of matching asset sharing entries
	 */
	@Override
	public List<AssetSharingEntry> findByC_S_S(
		long classNameId, long sharedToClassNameId, long sharedToClassPK,
		int start, int end) {

		return findByC_S_S(
			classNameId, sharedToClassNameId, sharedToClassPK, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the asset sharing entries where classNameId = &#63; and sharedToClassNameId = &#63; and sharedToClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetSharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param sharedToClassNameId the shared to class name ID
	 * @param sharedToClassPK the shared to class pk
	 * @param start the lower bound of the range of asset sharing entries
	 * @param end the upper bound of the range of asset sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset sharing entries
	 */
	@Override
	public List<AssetSharingEntry> findByC_S_S(
		long classNameId, long sharedToClassNameId, long sharedToClassPK,
		int start, int end,
		OrderByComparator<AssetSharingEntry> orderByComparator) {

		return findByC_S_S(
			classNameId, sharedToClassNameId, sharedToClassPK, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset sharing entries where classNameId = &#63; and sharedToClassNameId = &#63; and sharedToClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetSharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param sharedToClassNameId the shared to class name ID
	 * @param sharedToClassPK the shared to class pk
	 * @param start the lower bound of the range of asset sharing entries
	 * @param end the upper bound of the range of asset sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset sharing entries
	 */
	@Override
	public List<AssetSharingEntry> findByC_S_S(
		long classNameId, long sharedToClassNameId, long sharedToClassPK,
		int start, int end,
		OrderByComparator<AssetSharingEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_S_S;
				finderArgs = new Object[] {
					classNameId, sharedToClassNameId, sharedToClassPK
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_S_S;
			finderArgs = new Object[] {
				classNameId, sharedToClassNameId, sharedToClassPK, start, end,
				orderByComparator
			};
		}

		List<AssetSharingEntry> list = null;

		if (useFinderCache) {
			list = (List<AssetSharingEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetSharingEntry assetSharingEntry : list) {
					if ((classNameId != assetSharingEntry.getClassNameId()) ||
						(sharedToClassNameId !=
							assetSharingEntry.getSharedToClassNameId()) ||
						(sharedToClassPK !=
							assetSharingEntry.getSharedToClassPK())) {

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

			sb.append(_SQL_SELECT_ASSETSHARINGENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_S_S_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_S_S_SHAREDTOCLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_S_S_SHAREDTOCLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetSharingEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(sharedToClassNameId);

				queryPos.add(sharedToClassPK);

				list = (List<AssetSharingEntry>)QueryUtil.list(
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
	 * Returns the first asset sharing entry in the ordered set where classNameId = &#63; and sharedToClassNameId = &#63; and sharedToClassPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param sharedToClassNameId the shared to class name ID
	 * @param sharedToClassPK the shared to class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset sharing entry
	 * @throws NoSuchEntryException if a matching asset sharing entry could not be found
	 */
	@Override
	public AssetSharingEntry findByC_S_S_First(
			long classNameId, long sharedToClassNameId, long sharedToClassPK,
			OrderByComparator<AssetSharingEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetSharingEntry assetSharingEntry = fetchByC_S_S_First(
			classNameId, sharedToClassNameId, sharedToClassPK,
			orderByComparator);

		if (assetSharingEntry != null) {
			return assetSharingEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", sharedToClassNameId=");
		sb.append(sharedToClassNameId);

		sb.append(", sharedToClassPK=");
		sb.append(sharedToClassPK);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first asset sharing entry in the ordered set where classNameId = &#63; and sharedToClassNameId = &#63; and sharedToClassPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param sharedToClassNameId the shared to class name ID
	 * @param sharedToClassPK the shared to class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset sharing entry, or <code>null</code> if a matching asset sharing entry could not be found
	 */
	@Override
	public AssetSharingEntry fetchByC_S_S_First(
		long classNameId, long sharedToClassNameId, long sharedToClassPK,
		OrderByComparator<AssetSharingEntry> orderByComparator) {

		List<AssetSharingEntry> list = findByC_S_S(
			classNameId, sharedToClassNameId, sharedToClassPK, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset sharing entry in the ordered set where classNameId = &#63; and sharedToClassNameId = &#63; and sharedToClassPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param sharedToClassNameId the shared to class name ID
	 * @param sharedToClassPK the shared to class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset sharing entry
	 * @throws NoSuchEntryException if a matching asset sharing entry could not be found
	 */
	@Override
	public AssetSharingEntry findByC_S_S_Last(
			long classNameId, long sharedToClassNameId, long sharedToClassPK,
			OrderByComparator<AssetSharingEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetSharingEntry assetSharingEntry = fetchByC_S_S_Last(
			classNameId, sharedToClassNameId, sharedToClassPK,
			orderByComparator);

		if (assetSharingEntry != null) {
			return assetSharingEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", sharedToClassNameId=");
		sb.append(sharedToClassNameId);

		sb.append(", sharedToClassPK=");
		sb.append(sharedToClassPK);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last asset sharing entry in the ordered set where classNameId = &#63; and sharedToClassNameId = &#63; and sharedToClassPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param sharedToClassNameId the shared to class name ID
	 * @param sharedToClassPK the shared to class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset sharing entry, or <code>null</code> if a matching asset sharing entry could not be found
	 */
	@Override
	public AssetSharingEntry fetchByC_S_S_Last(
		long classNameId, long sharedToClassNameId, long sharedToClassPK,
		OrderByComparator<AssetSharingEntry> orderByComparator) {

		int count = countByC_S_S(
			classNameId, sharedToClassNameId, sharedToClassPK);

		if (count == 0) {
			return null;
		}

		List<AssetSharingEntry> list = findByC_S_S(
			classNameId, sharedToClassNameId, sharedToClassPK, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset sharing entries before and after the current asset sharing entry in the ordered set where classNameId = &#63; and sharedToClassNameId = &#63; and sharedToClassPK = &#63;.
	 *
	 * @param assetSharingEntryPK the primary key of the current asset sharing entry
	 * @param classNameId the class name ID
	 * @param sharedToClassNameId the shared to class name ID
	 * @param sharedToClassPK the shared to class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset sharing entry
	 * @throws NoSuchEntryException if a asset sharing entry with the primary key could not be found
	 */
	@Override
	public AssetSharingEntry[] findByC_S_S_PrevAndNext(
			AssetSharingEntryPK assetSharingEntryPK, long classNameId,
			long sharedToClassNameId, long sharedToClassPK,
			OrderByComparator<AssetSharingEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetSharingEntry assetSharingEntry = findByPrimaryKey(
			assetSharingEntryPK);

		Session session = null;

		try {
			session = openSession();

			AssetSharingEntry[] array = new AssetSharingEntryImpl[3];

			array[0] = getByC_S_S_PrevAndNext(
				session, assetSharingEntry, classNameId, sharedToClassNameId,
				sharedToClassPK, orderByComparator, true);

			array[1] = assetSharingEntry;

			array[2] = getByC_S_S_PrevAndNext(
				session, assetSharingEntry, classNameId, sharedToClassNameId,
				sharedToClassPK, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetSharingEntry getByC_S_S_PrevAndNext(
		Session session, AssetSharingEntry assetSharingEntry, long classNameId,
		long sharedToClassNameId, long sharedToClassPK,
		OrderByComparator<AssetSharingEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETSHARINGENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_S_S_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_S_S_SHAREDTOCLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_S_S_SHAREDTOCLASSPK_2);

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
			sb.append(AssetSharingEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(sharedToClassNameId);

		queryPos.add(sharedToClassPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetSharingEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetSharingEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset sharing entries where classNameId = &#63; and sharedToClassNameId = &#63; and sharedToClassPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param sharedToClassNameId the shared to class name ID
	 * @param sharedToClassPK the shared to class pk
	 */
	@Override
	public void removeByC_S_S(
		long classNameId, long sharedToClassNameId, long sharedToClassPK) {

		for (AssetSharingEntry assetSharingEntry :
				findByC_S_S(
					classNameId, sharedToClassNameId, sharedToClassPK,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetSharingEntry);
		}
	}

	/**
	 * Returns the number of asset sharing entries where classNameId = &#63; and sharedToClassNameId = &#63; and sharedToClassPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param sharedToClassNameId the shared to class name ID
	 * @param sharedToClassPK the shared to class pk
	 * @return the number of matching asset sharing entries
	 */
	@Override
	public int countByC_S_S(
		long classNameId, long sharedToClassNameId, long sharedToClassPK) {

		FinderPath finderPath = _finderPathCountByC_S_S;

		Object[] finderArgs = new Object[] {
			classNameId, sharedToClassNameId, sharedToClassPK
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_ASSETSHARINGENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_S_S_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_S_S_SHAREDTOCLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_S_S_SHAREDTOCLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(sharedToClassNameId);

				queryPos.add(sharedToClassPK);

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

	private static final String _FINDER_COLUMN_C_S_S_CLASSNAMEID_2 =
		"assetSharingEntry.id.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_S_S_SHAREDTOCLASSNAMEID_2 =
		"assetSharingEntry.id.sharedToClassNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_S_S_SHAREDTOCLASSPK_2 =
		"assetSharingEntry.id.sharedToClassPK = ?";

	public AssetSharingEntryPersistenceImpl() {
		setModelClass(AssetSharingEntry.class);
	}

	/**
	 * Caches the asset sharing entry in the entity cache if it is enabled.
	 *
	 * @param assetSharingEntry the asset sharing entry
	 */
	@Override
	public void cacheResult(AssetSharingEntry assetSharingEntry) {
		entityCache.putResult(
			AssetSharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetSharingEntryImpl.class, assetSharingEntry.getPrimaryKey(),
			assetSharingEntry);

		assetSharingEntry.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the asset sharing entries in the entity cache if it is enabled.
	 *
	 * @param assetSharingEntries the asset sharing entries
	 */
	@Override
	public void cacheResult(List<AssetSharingEntry> assetSharingEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (assetSharingEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (AssetSharingEntry assetSharingEntry : assetSharingEntries) {
			if (entityCache.getResult(
					AssetSharingEntryModelImpl.ENTITY_CACHE_ENABLED,
					AssetSharingEntryImpl.class,
					assetSharingEntry.getPrimaryKey()) == null) {

				cacheResult(assetSharingEntry);
			}
			else {
				assetSharingEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset sharing entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AssetSharingEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset sharing entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetSharingEntry assetSharingEntry) {
		entityCache.removeResult(
			AssetSharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetSharingEntryImpl.class, assetSharingEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<AssetSharingEntry> assetSharingEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetSharingEntry assetSharingEntry : assetSharingEntries) {
			entityCache.removeResult(
				AssetSharingEntryModelImpl.ENTITY_CACHE_ENABLED,
				AssetSharingEntryImpl.class, assetSharingEntry.getPrimaryKey());
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				AssetSharingEntryModelImpl.ENTITY_CACHE_ENABLED,
				AssetSharingEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new asset sharing entry with the primary key. Does not add the asset sharing entry to the database.
	 *
	 * @param assetSharingEntryPK the primary key for the new asset sharing entry
	 * @return the new asset sharing entry
	 */
	@Override
	public AssetSharingEntry create(AssetSharingEntryPK assetSharingEntryPK) {
		AssetSharingEntry assetSharingEntry = new AssetSharingEntryImpl();

		assetSharingEntry.setNew(true);
		assetSharingEntry.setPrimaryKey(assetSharingEntryPK);

		return assetSharingEntry;
	}

	/**
	 * Removes the asset sharing entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetSharingEntryPK the primary key of the asset sharing entry
	 * @return the asset sharing entry that was removed
	 * @throws NoSuchEntryException if a asset sharing entry with the primary key could not be found
	 */
	@Override
	public AssetSharingEntry remove(AssetSharingEntryPK assetSharingEntryPK)
		throws NoSuchEntryException {

		return remove((Serializable)assetSharingEntryPK);
	}

	/**
	 * Removes the asset sharing entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset sharing entry
	 * @return the asset sharing entry that was removed
	 * @throws NoSuchEntryException if a asset sharing entry with the primary key could not be found
	 */
	@Override
	public AssetSharingEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {

		Session session = null;

		try {
			session = openSession();

			AssetSharingEntry assetSharingEntry =
				(AssetSharingEntry)session.get(
					AssetSharingEntryImpl.class, primaryKey);

			if (assetSharingEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(assetSharingEntry);
		}
		catch (NoSuchEntryException noSuchEntityException) {
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
	protected AssetSharingEntry removeImpl(
		AssetSharingEntry assetSharingEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetSharingEntry)) {
				assetSharingEntry = (AssetSharingEntry)session.get(
					AssetSharingEntryImpl.class,
					assetSharingEntry.getPrimaryKeyObj());
			}

			if (assetSharingEntry != null) {
				session.delete(assetSharingEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (assetSharingEntry != null) {
			clearCache(assetSharingEntry);
		}

		return assetSharingEntry;
	}

	@Override
	public AssetSharingEntry updateImpl(AssetSharingEntry assetSharingEntry) {
		boolean isNew = assetSharingEntry.isNew();

		if (!(assetSharingEntry instanceof AssetSharingEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(assetSharingEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					assetSharingEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in assetSharingEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AssetSharingEntry implementation " +
					assetSharingEntry.getClass());
		}

		AssetSharingEntryModelImpl assetSharingEntryModelImpl =
			(AssetSharingEntryModelImpl)assetSharingEntry;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(assetSharingEntry);

				assetSharingEntry.setNew(false);
			}
			else {
				assetSharingEntry = (AssetSharingEntry)session.merge(
					assetSharingEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!AssetSharingEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				assetSharingEntryModelImpl.getClassNameId(),
				assetSharingEntryModelImpl.getClassPK()
			};

			finderCache.removeResult(_finderPathCountByC_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_C, args);

			args = new Object[] {
				assetSharingEntryModelImpl.getSharedToClassNameId(),
				assetSharingEntryModelImpl.getSharedToClassPK()
			};

			finderCache.removeResult(_finderPathCountByS_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByS_S, args);

			args = new Object[] {
				assetSharingEntryModelImpl.getClassNameId(),
				assetSharingEntryModelImpl.getClassPK(),
				assetSharingEntryModelImpl.getSharedToClassNameId()
			};

			finderCache.removeResult(_finderPathCountByC_C_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_C_S, args);

			args = new Object[] {
				assetSharingEntryModelImpl.getClassNameId(),
				assetSharingEntryModelImpl.getSharedToClassNameId(),
				assetSharingEntryModelImpl.getSharedToClassPK()
			};

			finderCache.removeResult(_finderPathCountByC_S_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_S_S, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((assetSharingEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetSharingEntryModelImpl.getOriginalClassNameId(),
					assetSharingEntryModelImpl.getOriginalClassPK()
				};

				finderCache.removeResult(_finderPathCountByC_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);

				args = new Object[] {
					assetSharingEntryModelImpl.getClassNameId(),
					assetSharingEntryModelImpl.getClassPK()
				};

				finderCache.removeResult(_finderPathCountByC_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);
			}

			if ((assetSharingEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByS_S.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetSharingEntryModelImpl.getOriginalSharedToClassNameId(),
					assetSharingEntryModelImpl.getOriginalSharedToClassPK()
				};

				finderCache.removeResult(_finderPathCountByS_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByS_S, args);

				args = new Object[] {
					assetSharingEntryModelImpl.getSharedToClassNameId(),
					assetSharingEntryModelImpl.getSharedToClassPK()
				};

				finderCache.removeResult(_finderPathCountByS_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByS_S, args);
			}

			if ((assetSharingEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_C_S.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetSharingEntryModelImpl.getOriginalClassNameId(),
					assetSharingEntryModelImpl.getOriginalClassPK(),
					assetSharingEntryModelImpl.getOriginalSharedToClassNameId()
				};

				finderCache.removeResult(_finderPathCountByC_C_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_C_S, args);

				args = new Object[] {
					assetSharingEntryModelImpl.getClassNameId(),
					assetSharingEntryModelImpl.getClassPK(),
					assetSharingEntryModelImpl.getSharedToClassNameId()
				};

				finderCache.removeResult(_finderPathCountByC_C_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_C_S, args);
			}

			if ((assetSharingEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_S_S.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetSharingEntryModelImpl.getOriginalClassNameId(),
					assetSharingEntryModelImpl.getOriginalSharedToClassNameId(),
					assetSharingEntryModelImpl.getOriginalSharedToClassPK()
				};

				finderCache.removeResult(_finderPathCountByC_S_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_S_S, args);

				args = new Object[] {
					assetSharingEntryModelImpl.getClassNameId(),
					assetSharingEntryModelImpl.getSharedToClassNameId(),
					assetSharingEntryModelImpl.getSharedToClassPK()
				};

				finderCache.removeResult(_finderPathCountByC_S_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_S_S, args);
			}
		}

		entityCache.putResult(
			AssetSharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetSharingEntryImpl.class, assetSharingEntry.getPrimaryKey(),
			assetSharingEntry, false);

		assetSharingEntry.resetOriginalValues();

		return assetSharingEntry;
	}

	/**
	 * Returns the asset sharing entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset sharing entry
	 * @return the asset sharing entry
	 * @throws NoSuchEntryException if a asset sharing entry with the primary key could not be found
	 */
	@Override
	public AssetSharingEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		AssetSharingEntry assetSharingEntry = fetchByPrimaryKey(primaryKey);

		if (assetSharingEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return assetSharingEntry;
	}

	/**
	 * Returns the asset sharing entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param assetSharingEntryPK the primary key of the asset sharing entry
	 * @return the asset sharing entry
	 * @throws NoSuchEntryException if a asset sharing entry with the primary key could not be found
	 */
	@Override
	public AssetSharingEntry findByPrimaryKey(
			AssetSharingEntryPK assetSharingEntryPK)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)assetSharingEntryPK);
	}

	/**
	 * Returns the asset sharing entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset sharing entry
	 * @return the asset sharing entry, or <code>null</code> if a asset sharing entry with the primary key could not be found
	 */
	@Override
	public AssetSharingEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			AssetSharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetSharingEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		AssetSharingEntry assetSharingEntry = (AssetSharingEntry)serializable;

		if (assetSharingEntry == null) {
			Session session = null;

			try {
				session = openSession();

				assetSharingEntry = (AssetSharingEntry)session.get(
					AssetSharingEntryImpl.class, primaryKey);

				if (assetSharingEntry != null) {
					cacheResult(assetSharingEntry);
				}
				else {
					entityCache.putResult(
						AssetSharingEntryModelImpl.ENTITY_CACHE_ENABLED,
						AssetSharingEntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					AssetSharingEntryModelImpl.ENTITY_CACHE_ENABLED,
					AssetSharingEntryImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return assetSharingEntry;
	}

	/**
	 * Returns the asset sharing entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetSharingEntryPK the primary key of the asset sharing entry
	 * @return the asset sharing entry, or <code>null</code> if a asset sharing entry with the primary key could not be found
	 */
	@Override
	public AssetSharingEntry fetchByPrimaryKey(
		AssetSharingEntryPK assetSharingEntryPK) {

		return fetchByPrimaryKey((Serializable)assetSharingEntryPK);
	}

	@Override
	public Map<Serializable, AssetSharingEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AssetSharingEntry> map =
			new HashMap<Serializable, AssetSharingEntry>();

		for (Serializable primaryKey : primaryKeys) {
			AssetSharingEntry assetSharingEntry = fetchByPrimaryKey(primaryKey);

			if (assetSharingEntry != null) {
				map.put(primaryKey, assetSharingEntry);
			}
		}

		return map;
	}

	/**
	 * Returns all the asset sharing entries.
	 *
	 * @return the asset sharing entries
	 */
	@Override
	public List<AssetSharingEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset sharing entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetSharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset sharing entries
	 * @param end the upper bound of the range of asset sharing entries (not inclusive)
	 * @return the range of asset sharing entries
	 */
	@Override
	public List<AssetSharingEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset sharing entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetSharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset sharing entries
	 * @param end the upper bound of the range of asset sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset sharing entries
	 */
	@Override
	public List<AssetSharingEntry> findAll(
		int start, int end,
		OrderByComparator<AssetSharingEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset sharing entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetSharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset sharing entries
	 * @param end the upper bound of the range of asset sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset sharing entries
	 */
	@Override
	public List<AssetSharingEntry> findAll(
		int start, int end,
		OrderByComparator<AssetSharingEntry> orderByComparator,
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

		List<AssetSharingEntry> list = null;

		if (useFinderCache) {
			list = (List<AssetSharingEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ASSETSHARINGENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETSHARINGENTRY;

				sql = sql.concat(AssetSharingEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AssetSharingEntry>)QueryUtil.list(
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
	 * Removes all the asset sharing entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetSharingEntry assetSharingEntry : findAll()) {
			remove(assetSharingEntry);
		}
	}

	/**
	 * Returns the number of asset sharing entries.
	 *
	 * @return the number of asset sharing entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_ASSETSHARINGENTRY);

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
		return AssetSharingEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the asset sharing entry persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			AssetSharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetSharingEntryModelImpl.FINDER_CACHE_ENABLED,
			AssetSharingEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			AssetSharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetSharingEntryModelImpl.FINDER_CACHE_ENABLED,
			AssetSharingEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			AssetSharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetSharingEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByC_C = new FinderPath(
			AssetSharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetSharingEntryModelImpl.FINDER_CACHE_ENABLED,
			AssetSharingEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			AssetSharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetSharingEntryModelImpl.FINDER_CACHE_ENABLED,
			AssetSharingEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			AssetSharingEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			AssetSharingEntryModelImpl.CLASSPK_COLUMN_BITMASK);

		_finderPathCountByC_C = new FinderPath(
			AssetSharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetSharingEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByS_S = new FinderPath(
			AssetSharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetSharingEntryModelImpl.FINDER_CACHE_ENABLED,
			AssetSharingEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByS_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByS_S = new FinderPath(
			AssetSharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetSharingEntryModelImpl.FINDER_CACHE_ENABLED,
			AssetSharingEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByS_S",
			new String[] {Long.class.getName(), Long.class.getName()},
			AssetSharingEntryModelImpl.SHAREDTOCLASSNAMEID_COLUMN_BITMASK |
			AssetSharingEntryModelImpl.SHAREDTOCLASSPK_COLUMN_BITMASK);

		_finderPathCountByS_S = new FinderPath(
			AssetSharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetSharingEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByS_S",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByC_C_S = new FinderPath(
			AssetSharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetSharingEntryModelImpl.FINDER_CACHE_ENABLED,
			AssetSharingEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_C_S = new FinderPath(
			AssetSharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetSharingEntryModelImpl.FINDER_CACHE_ENABLED,
			AssetSharingEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			AssetSharingEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			AssetSharingEntryModelImpl.CLASSPK_COLUMN_BITMASK |
			AssetSharingEntryModelImpl.SHAREDTOCLASSNAMEID_COLUMN_BITMASK);

		_finderPathCountByC_C_S = new FinderPath(
			AssetSharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetSharingEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathWithPaginationFindByC_S_S = new FinderPath(
			AssetSharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetSharingEntryModelImpl.FINDER_CACHE_ENABLED,
			AssetSharingEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_S_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_S_S = new FinderPath(
			AssetSharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetSharingEntryModelImpl.FINDER_CACHE_ENABLED,
			AssetSharingEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_S_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			AssetSharingEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			AssetSharingEntryModelImpl.SHAREDTOCLASSNAMEID_COLUMN_BITMASK |
			AssetSharingEntryModelImpl.SHAREDTOCLASSPK_COLUMN_BITMASK);

		_finderPathCountByC_S_S = new FinderPath(
			AssetSharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetSharingEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		AssetSharingEntryUtil.setPersistence(this);
	}

	public void destroy() {
		AssetSharingEntryUtil.setPersistence(null);

		entityCache.removeCache(AssetSharingEntryImpl.class.getName());

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

	private static final String _SQL_SELECT_ASSETSHARINGENTRY =
		"SELECT assetSharingEntry FROM AssetSharingEntry assetSharingEntry";

	private static final String _SQL_SELECT_ASSETSHARINGENTRY_WHERE =
		"SELECT assetSharingEntry FROM AssetSharingEntry assetSharingEntry WHERE ";

	private static final String _SQL_COUNT_ASSETSHARINGENTRY =
		"SELECT COUNT(assetSharingEntry) FROM AssetSharingEntry assetSharingEntry";

	private static final String _SQL_COUNT_ASSETSHARINGENTRY_WHERE =
		"SELECT COUNT(assetSharingEntry) FROM AssetSharingEntry assetSharingEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "assetSharingEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AssetSharingEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AssetSharingEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AssetSharingEntryPersistenceImpl.class);

	private static final Set<String> _compoundPKColumnNames = SetUtil.fromArray(
		new String[] {
			"classNameId", "classPK", "sharedToClassNameId", "sharedToClassPK"
		});

}