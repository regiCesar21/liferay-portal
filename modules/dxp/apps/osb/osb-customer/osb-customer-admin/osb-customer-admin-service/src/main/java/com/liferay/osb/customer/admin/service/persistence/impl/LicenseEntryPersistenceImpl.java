/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.admin.service.persistence.impl;

import com.liferay.osb.customer.admin.exception.NoSuchLicenseEntryException;
import com.liferay.osb.customer.admin.model.LicenseEntry;
import com.liferay.osb.customer.admin.model.impl.LicenseEntryImpl;
import com.liferay.osb.customer.admin.model.impl.LicenseEntryModelImpl;
import com.liferay.osb.customer.admin.service.persistence.LicenseEntryPersistence;
import com.liferay.osb.customer.admin.service.persistence.LicenseEntryUtil;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the license entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LicenseEntryPersistenceImpl
	extends BasePersistenceImpl<LicenseEntry>
	implements LicenseEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LicenseEntryUtil</code> to access the license entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LicenseEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByProductEntryId;
	private FinderPath _finderPathWithoutPaginationFindByProductEntryId;
	private FinderPath _finderPathCountByProductEntryId;

	/**
	 * Returns all the license entries where productEntryId = &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @return the matching license entries
	 */
	@Override
	public List<LicenseEntry> findByProductEntryId(long productEntryId) {
		return findByProductEntryId(
			productEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the license entries where productEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LicenseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param productEntryId the product entry ID
	 * @param start the lower bound of the range of license entries
	 * @param end the upper bound of the range of license entries (not inclusive)
	 * @return the range of matching license entries
	 */
	@Override
	public List<LicenseEntry> findByProductEntryId(
		long productEntryId, int start, int end) {

		return findByProductEntryId(productEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the license entries where productEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LicenseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param productEntryId the product entry ID
	 * @param start the lower bound of the range of license entries
	 * @param end the upper bound of the range of license entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching license entries
	 */
	@Override
	public List<LicenseEntry> findByProductEntryId(
		long productEntryId, int start, int end,
		OrderByComparator<LicenseEntry> orderByComparator) {

		return findByProductEntryId(
			productEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the license entries where productEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LicenseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param productEntryId the product entry ID
	 * @param start the lower bound of the range of license entries
	 * @param end the upper bound of the range of license entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching license entries
	 */
	@Override
	public List<LicenseEntry> findByProductEntryId(
		long productEntryId, int start, int end,
		OrderByComparator<LicenseEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByProductEntryId;
				finderArgs = new Object[] {productEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByProductEntryId;
			finderArgs = new Object[] {
				productEntryId, start, end, orderByComparator
			};
		}

		List<LicenseEntry> list = null;

		if (useFinderCache) {
			list = (List<LicenseEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LicenseEntry licenseEntry : list) {
					if (productEntryId != licenseEntry.getProductEntryId()) {
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

			sb.append(_SQL_SELECT_LICENSEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_PRODUCTENTRYID_PRODUCTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LicenseEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(productEntryId);

				list = (List<LicenseEntry>)QueryUtil.list(
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
	 * Returns the first license entry in the ordered set where productEntryId = &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching license entry
	 * @throws NoSuchLicenseEntryException if a matching license entry could not be found
	 */
	@Override
	public LicenseEntry findByProductEntryId_First(
			long productEntryId,
			OrderByComparator<LicenseEntry> orderByComparator)
		throws NoSuchLicenseEntryException {

		LicenseEntry licenseEntry = fetchByProductEntryId_First(
			productEntryId, orderByComparator);

		if (licenseEntry != null) {
			return licenseEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("productEntryId=");
		sb.append(productEntryId);

		sb.append("}");

		throw new NoSuchLicenseEntryException(sb.toString());
	}

	/**
	 * Returns the first license entry in the ordered set where productEntryId = &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching license entry, or <code>null</code> if a matching license entry could not be found
	 */
	@Override
	public LicenseEntry fetchByProductEntryId_First(
		long productEntryId,
		OrderByComparator<LicenseEntry> orderByComparator) {

		List<LicenseEntry> list = findByProductEntryId(
			productEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last license entry in the ordered set where productEntryId = &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching license entry
	 * @throws NoSuchLicenseEntryException if a matching license entry could not be found
	 */
	@Override
	public LicenseEntry findByProductEntryId_Last(
			long productEntryId,
			OrderByComparator<LicenseEntry> orderByComparator)
		throws NoSuchLicenseEntryException {

		LicenseEntry licenseEntry = fetchByProductEntryId_Last(
			productEntryId, orderByComparator);

		if (licenseEntry != null) {
			return licenseEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("productEntryId=");
		sb.append(productEntryId);

		sb.append("}");

		throw new NoSuchLicenseEntryException(sb.toString());
	}

	/**
	 * Returns the last license entry in the ordered set where productEntryId = &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching license entry, or <code>null</code> if a matching license entry could not be found
	 */
	@Override
	public LicenseEntry fetchByProductEntryId_Last(
		long productEntryId,
		OrderByComparator<LicenseEntry> orderByComparator) {

		int count = countByProductEntryId(productEntryId);

		if (count == 0) {
			return null;
		}

		List<LicenseEntry> list = findByProductEntryId(
			productEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the license entries before and after the current license entry in the ordered set where productEntryId = &#63;.
	 *
	 * @param licenseEntryId the primary key of the current license entry
	 * @param productEntryId the product entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next license entry
	 * @throws NoSuchLicenseEntryException if a license entry with the primary key could not be found
	 */
	@Override
	public LicenseEntry[] findByProductEntryId_PrevAndNext(
			long licenseEntryId, long productEntryId,
			OrderByComparator<LicenseEntry> orderByComparator)
		throws NoSuchLicenseEntryException {

		LicenseEntry licenseEntry = findByPrimaryKey(licenseEntryId);

		Session session = null;

		try {
			session = openSession();

			LicenseEntry[] array = new LicenseEntryImpl[3];

			array[0] = getByProductEntryId_PrevAndNext(
				session, licenseEntry, productEntryId, orderByComparator, true);

			array[1] = licenseEntry;

			array[2] = getByProductEntryId_PrevAndNext(
				session, licenseEntry, productEntryId, orderByComparator,
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

	protected LicenseEntry getByProductEntryId_PrevAndNext(
		Session session, LicenseEntry licenseEntry, long productEntryId,
		OrderByComparator<LicenseEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_LICENSEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_PRODUCTENTRYID_PRODUCTENTRYID_2);

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
			sb.append(LicenseEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(productEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(licenseEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LicenseEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the license entries where productEntryId = &#63; from the database.
	 *
	 * @param productEntryId the product entry ID
	 */
	@Override
	public void removeByProductEntryId(long productEntryId) {
		for (LicenseEntry licenseEntry :
				findByProductEntryId(
					productEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(licenseEntry);
		}
	}

	/**
	 * Returns the number of license entries where productEntryId = &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @return the number of matching license entries
	 */
	@Override
	public int countByProductEntryId(long productEntryId) {
		FinderPath finderPath = _finderPathCountByProductEntryId;

		Object[] finderArgs = new Object[] {productEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_LICENSEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_PRODUCTENTRYID_PRODUCTENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(productEntryId);

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

	private static final String _FINDER_COLUMN_PRODUCTENTRYID_PRODUCTENTRYID_2 =
		"licenseEntry.productEntryId = ?";

	private FinderPath _finderPathFetchByPEI_T;
	private FinderPath _finderPathCountByPEI_T;

	/**
	 * Returns the license entry where productEntryId = &#63; and type = &#63; or throws a <code>NoSuchLicenseEntryException</code> if it could not be found.
	 *
	 * @param productEntryId the product entry ID
	 * @param type the type
	 * @return the matching license entry
	 * @throws NoSuchLicenseEntryException if a matching license entry could not be found
	 */
	@Override
	public LicenseEntry findByPEI_T(long productEntryId, String type)
		throws NoSuchLicenseEntryException {

		LicenseEntry licenseEntry = fetchByPEI_T(productEntryId, type);

		if (licenseEntry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("productEntryId=");
			sb.append(productEntryId);

			sb.append(", type=");
			sb.append(type);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchLicenseEntryException(sb.toString());
		}

		return licenseEntry;
	}

	/**
	 * Returns the license entry where productEntryId = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param productEntryId the product entry ID
	 * @param type the type
	 * @return the matching license entry, or <code>null</code> if a matching license entry could not be found
	 */
	@Override
	public LicenseEntry fetchByPEI_T(long productEntryId, String type) {
		return fetchByPEI_T(productEntryId, type, true);
	}

	/**
	 * Returns the license entry where productEntryId = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param productEntryId the product entry ID
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching license entry, or <code>null</code> if a matching license entry could not be found
	 */
	@Override
	public LicenseEntry fetchByPEI_T(
		long productEntryId, String type, boolean useFinderCache) {

		type = Objects.toString(type, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {productEntryId, type};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByPEI_T, finderArgs, this);
		}

		if (result instanceof LicenseEntry) {
			LicenseEntry licenseEntry = (LicenseEntry)result;

			if ((productEntryId != licenseEntry.getProductEntryId()) ||
				!Objects.equals(type, licenseEntry.getType())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_LICENSEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_PEI_T_PRODUCTENTRYID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_PEI_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_PEI_T_TYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(productEntryId);

				if (bindType) {
					queryPos.add(type);
				}

				List<LicenseEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByPEI_T, finderArgs, list);
					}
				}
				else {
					LicenseEntry licenseEntry = list.get(0);

					result = licenseEntry;

					cacheResult(licenseEntry);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByPEI_T, finderArgs);
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
			return (LicenseEntry)result;
		}
	}

	/**
	 * Removes the license entry where productEntryId = &#63; and type = &#63; from the database.
	 *
	 * @param productEntryId the product entry ID
	 * @param type the type
	 * @return the license entry that was removed
	 */
	@Override
	public LicenseEntry removeByPEI_T(long productEntryId, String type)
		throws NoSuchLicenseEntryException {

		LicenseEntry licenseEntry = findByPEI_T(productEntryId, type);

		return remove(licenseEntry);
	}

	/**
	 * Returns the number of license entries where productEntryId = &#63; and type = &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @param type the type
	 * @return the number of matching license entries
	 */
	@Override
	public int countByPEI_T(long productEntryId, String type) {
		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathCountByPEI_T;

		Object[] finderArgs = new Object[] {productEntryId, type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LICENSEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_PEI_T_PRODUCTENTRYID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_PEI_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_PEI_T_TYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(productEntryId);

				if (bindType) {
					queryPos.add(type);
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

	private static final String _FINDER_COLUMN_PEI_T_PRODUCTENTRYID_2 =
		"licenseEntry.productEntryId = ? AND ";

	private static final String _FINDER_COLUMN_PEI_T_TYPE_2 =
		"licenseEntry.type = ?";

	private static final String _FINDER_COLUMN_PEI_T_TYPE_3 =
		"(licenseEntry.type IS NULL OR licenseEntry.type = '')";

	private FinderPath _finderPathWithPaginationFindByPEI_V;
	private FinderPath _finderPathWithPaginationCountByPEI_V;

	/**
	 * Returns all the license entries where productEntryId = &#63; and versionMin &le; &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @param versionMin the version min
	 * @return the matching license entries
	 */
	@Override
	public List<LicenseEntry> findByPEI_V(long productEntryId, int versionMin) {
		return findByPEI_V(
			productEntryId, versionMin, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the license entries where productEntryId = &#63; and versionMin &le; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LicenseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param productEntryId the product entry ID
	 * @param versionMin the version min
	 * @param start the lower bound of the range of license entries
	 * @param end the upper bound of the range of license entries (not inclusive)
	 * @return the range of matching license entries
	 */
	@Override
	public List<LicenseEntry> findByPEI_V(
		long productEntryId, int versionMin, int start, int end) {

		return findByPEI_V(productEntryId, versionMin, start, end, null);
	}

	/**
	 * Returns an ordered range of all the license entries where productEntryId = &#63; and versionMin &le; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LicenseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param productEntryId the product entry ID
	 * @param versionMin the version min
	 * @param start the lower bound of the range of license entries
	 * @param end the upper bound of the range of license entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching license entries
	 */
	@Override
	public List<LicenseEntry> findByPEI_V(
		long productEntryId, int versionMin, int start, int end,
		OrderByComparator<LicenseEntry> orderByComparator) {

		return findByPEI_V(
			productEntryId, versionMin, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the license entries where productEntryId = &#63; and versionMin &le; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LicenseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param productEntryId the product entry ID
	 * @param versionMin the version min
	 * @param start the lower bound of the range of license entries
	 * @param end the upper bound of the range of license entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching license entries
	 */
	@Override
	public List<LicenseEntry> findByPEI_V(
		long productEntryId, int versionMin, int start, int end,
		OrderByComparator<LicenseEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByPEI_V;
		finderArgs = new Object[] {
			productEntryId, versionMin, start, end, orderByComparator
		};

		List<LicenseEntry> list = null;

		if (useFinderCache) {
			list = (List<LicenseEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LicenseEntry licenseEntry : list) {
					if ((productEntryId != licenseEntry.getProductEntryId()) ||
						(versionMin < licenseEntry.getVersionMin())) {

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

			sb.append(_SQL_SELECT_LICENSEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_PEI_V_PRODUCTENTRYID_2);

			sb.append(_FINDER_COLUMN_PEI_V_VERSIONMIN_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LicenseEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(productEntryId);

				queryPos.add(versionMin);

				list = (List<LicenseEntry>)QueryUtil.list(
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
	 * Returns the first license entry in the ordered set where productEntryId = &#63; and versionMin &le; &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @param versionMin the version min
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching license entry
	 * @throws NoSuchLicenseEntryException if a matching license entry could not be found
	 */
	@Override
	public LicenseEntry findByPEI_V_First(
			long productEntryId, int versionMin,
			OrderByComparator<LicenseEntry> orderByComparator)
		throws NoSuchLicenseEntryException {

		LicenseEntry licenseEntry = fetchByPEI_V_First(
			productEntryId, versionMin, orderByComparator);

		if (licenseEntry != null) {
			return licenseEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("productEntryId=");
		sb.append(productEntryId);

		sb.append(", versionMin<=");
		sb.append(versionMin);

		sb.append("}");

		throw new NoSuchLicenseEntryException(sb.toString());
	}

	/**
	 * Returns the first license entry in the ordered set where productEntryId = &#63; and versionMin &le; &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @param versionMin the version min
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching license entry, or <code>null</code> if a matching license entry could not be found
	 */
	@Override
	public LicenseEntry fetchByPEI_V_First(
		long productEntryId, int versionMin,
		OrderByComparator<LicenseEntry> orderByComparator) {

		List<LicenseEntry> list = findByPEI_V(
			productEntryId, versionMin, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last license entry in the ordered set where productEntryId = &#63; and versionMin &le; &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @param versionMin the version min
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching license entry
	 * @throws NoSuchLicenseEntryException if a matching license entry could not be found
	 */
	@Override
	public LicenseEntry findByPEI_V_Last(
			long productEntryId, int versionMin,
			OrderByComparator<LicenseEntry> orderByComparator)
		throws NoSuchLicenseEntryException {

		LicenseEntry licenseEntry = fetchByPEI_V_Last(
			productEntryId, versionMin, orderByComparator);

		if (licenseEntry != null) {
			return licenseEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("productEntryId=");
		sb.append(productEntryId);

		sb.append(", versionMin<=");
		sb.append(versionMin);

		sb.append("}");

		throw new NoSuchLicenseEntryException(sb.toString());
	}

	/**
	 * Returns the last license entry in the ordered set where productEntryId = &#63; and versionMin &le; &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @param versionMin the version min
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching license entry, or <code>null</code> if a matching license entry could not be found
	 */
	@Override
	public LicenseEntry fetchByPEI_V_Last(
		long productEntryId, int versionMin,
		OrderByComparator<LicenseEntry> orderByComparator) {

		int count = countByPEI_V(productEntryId, versionMin);

		if (count == 0) {
			return null;
		}

		List<LicenseEntry> list = findByPEI_V(
			productEntryId, versionMin, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the license entries before and after the current license entry in the ordered set where productEntryId = &#63; and versionMin &le; &#63;.
	 *
	 * @param licenseEntryId the primary key of the current license entry
	 * @param productEntryId the product entry ID
	 * @param versionMin the version min
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next license entry
	 * @throws NoSuchLicenseEntryException if a license entry with the primary key could not be found
	 */
	@Override
	public LicenseEntry[] findByPEI_V_PrevAndNext(
			long licenseEntryId, long productEntryId, int versionMin,
			OrderByComparator<LicenseEntry> orderByComparator)
		throws NoSuchLicenseEntryException {

		LicenseEntry licenseEntry = findByPrimaryKey(licenseEntryId);

		Session session = null;

		try {
			session = openSession();

			LicenseEntry[] array = new LicenseEntryImpl[3];

			array[0] = getByPEI_V_PrevAndNext(
				session, licenseEntry, productEntryId, versionMin,
				orderByComparator, true);

			array[1] = licenseEntry;

			array[2] = getByPEI_V_PrevAndNext(
				session, licenseEntry, productEntryId, versionMin,
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

	protected LicenseEntry getByPEI_V_PrevAndNext(
		Session session, LicenseEntry licenseEntry, long productEntryId,
		int versionMin, OrderByComparator<LicenseEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_LICENSEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_PEI_V_PRODUCTENTRYID_2);

		sb.append(_FINDER_COLUMN_PEI_V_VERSIONMIN_2);

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
			sb.append(LicenseEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(productEntryId);

		queryPos.add(versionMin);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(licenseEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LicenseEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the license entries where productEntryId = &#63; and versionMin &le; &#63; from the database.
	 *
	 * @param productEntryId the product entry ID
	 * @param versionMin the version min
	 */
	@Override
	public void removeByPEI_V(long productEntryId, int versionMin) {
		for (LicenseEntry licenseEntry :
				findByPEI_V(
					productEntryId, versionMin, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(licenseEntry);
		}
	}

	/**
	 * Returns the number of license entries where productEntryId = &#63; and versionMin &le; &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @param versionMin the version min
	 * @return the number of matching license entries
	 */
	@Override
	public int countByPEI_V(long productEntryId, int versionMin) {
		FinderPath finderPath = _finderPathWithPaginationCountByPEI_V;

		Object[] finderArgs = new Object[] {productEntryId, versionMin};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LICENSEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_PEI_V_PRODUCTENTRYID_2);

			sb.append(_FINDER_COLUMN_PEI_V_VERSIONMIN_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(productEntryId);

				queryPos.add(versionMin);

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

	private static final String _FINDER_COLUMN_PEI_V_PRODUCTENTRYID_2 =
		"licenseEntry.productEntryId = ? AND ";

	private static final String _FINDER_COLUMN_PEI_V_VERSIONMIN_2 =
		"licenseEntry.versionMin <= ?";

	public LicenseEntryPersistenceImpl() {
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

		setModelClass(LicenseEntry.class);
	}

	/**
	 * Caches the license entry in the entity cache if it is enabled.
	 *
	 * @param licenseEntry the license entry
	 */
	@Override
	public void cacheResult(LicenseEntry licenseEntry) {
		entityCache.putResult(
			LicenseEntryModelImpl.ENTITY_CACHE_ENABLED, LicenseEntryImpl.class,
			licenseEntry.getPrimaryKey(), licenseEntry);

		finderCache.putResult(
			_finderPathFetchByPEI_T,
			new Object[] {
				licenseEntry.getProductEntryId(), licenseEntry.getType()
			},
			licenseEntry);

		licenseEntry.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the license entries in the entity cache if it is enabled.
	 *
	 * @param licenseEntries the license entries
	 */
	@Override
	public void cacheResult(List<LicenseEntry> licenseEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (licenseEntries.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (LicenseEntry licenseEntry : licenseEntries) {
			if (entityCache.getResult(
					LicenseEntryModelImpl.ENTITY_CACHE_ENABLED,
					LicenseEntryImpl.class, licenseEntry.getPrimaryKey()) ==
						null) {

				cacheResult(licenseEntry);
			}
			else {
				licenseEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all license entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LicenseEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the license entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LicenseEntry licenseEntry) {
		entityCache.removeResult(
			LicenseEntryModelImpl.ENTITY_CACHE_ENABLED, LicenseEntryImpl.class,
			licenseEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((LicenseEntryModelImpl)licenseEntry, true);
	}

	@Override
	public void clearCache(List<LicenseEntry> licenseEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LicenseEntry licenseEntry : licenseEntries) {
			entityCache.removeResult(
				LicenseEntryModelImpl.ENTITY_CACHE_ENABLED,
				LicenseEntryImpl.class, licenseEntry.getPrimaryKey());

			clearUniqueFindersCache((LicenseEntryModelImpl)licenseEntry, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				LicenseEntryModelImpl.ENTITY_CACHE_ENABLED,
				LicenseEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		LicenseEntryModelImpl licenseEntryModelImpl) {

		Object[] args = new Object[] {
			licenseEntryModelImpl.getProductEntryId(),
			licenseEntryModelImpl.getType()
		};

		finderCache.putResult(
			_finderPathCountByPEI_T, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByPEI_T, args, licenseEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		LicenseEntryModelImpl licenseEntryModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				licenseEntryModelImpl.getProductEntryId(),
				licenseEntryModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByPEI_T, args);
			finderCache.removeResult(_finderPathFetchByPEI_T, args);
		}

		if ((licenseEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByPEI_T.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				licenseEntryModelImpl.getOriginalProductEntryId(),
				licenseEntryModelImpl.getOriginalType()
			};

			finderCache.removeResult(_finderPathCountByPEI_T, args);
			finderCache.removeResult(_finderPathFetchByPEI_T, args);
		}
	}

	/**
	 * Creates a new license entry with the primary key. Does not add the license entry to the database.
	 *
	 * @param licenseEntryId the primary key for the new license entry
	 * @return the new license entry
	 */
	@Override
	public LicenseEntry create(long licenseEntryId) {
		LicenseEntry licenseEntry = new LicenseEntryImpl();

		licenseEntry.setNew(true);
		licenseEntry.setPrimaryKey(licenseEntryId);

		return licenseEntry;
	}

	/**
	 * Removes the license entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param licenseEntryId the primary key of the license entry
	 * @return the license entry that was removed
	 * @throws NoSuchLicenseEntryException if a license entry with the primary key could not be found
	 */
	@Override
	public LicenseEntry remove(long licenseEntryId)
		throws NoSuchLicenseEntryException {

		return remove((Serializable)licenseEntryId);
	}

	/**
	 * Removes the license entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the license entry
	 * @return the license entry that was removed
	 * @throws NoSuchLicenseEntryException if a license entry with the primary key could not be found
	 */
	@Override
	public LicenseEntry remove(Serializable primaryKey)
		throws NoSuchLicenseEntryException {

		Session session = null;

		try {
			session = openSession();

			LicenseEntry licenseEntry = (LicenseEntry)session.get(
				LicenseEntryImpl.class, primaryKey);

			if (licenseEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLicenseEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(licenseEntry);
		}
		catch (NoSuchLicenseEntryException noSuchEntityException) {
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
	protected LicenseEntry removeImpl(LicenseEntry licenseEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(licenseEntry)) {
				licenseEntry = (LicenseEntry)session.get(
					LicenseEntryImpl.class, licenseEntry.getPrimaryKeyObj());
			}

			if (licenseEntry != null) {
				session.delete(licenseEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (licenseEntry != null) {
			clearCache(licenseEntry);
		}

		return licenseEntry;
	}

	@Override
	public LicenseEntry updateImpl(LicenseEntry licenseEntry) {
		boolean isNew = licenseEntry.isNew();

		if (!(licenseEntry instanceof LicenseEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(licenseEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					licenseEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in licenseEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LicenseEntry implementation " +
					licenseEntry.getClass());
		}

		LicenseEntryModelImpl licenseEntryModelImpl =
			(LicenseEntryModelImpl)licenseEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (licenseEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				licenseEntry.setCreateDate(date);
			}
			else {
				licenseEntry.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!licenseEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				licenseEntry.setModifiedDate(date);
			}
			else {
				licenseEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(licenseEntry);

				licenseEntry.setNew(false);
			}
			else {
				licenseEntry = (LicenseEntry)session.merge(licenseEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LicenseEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				licenseEntryModelImpl.getProductEntryId()
			};

			finderCache.removeResult(_finderPathCountByProductEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByProductEntryId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((licenseEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByProductEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					licenseEntryModelImpl.getOriginalProductEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByProductEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByProductEntryId, args);

				args = new Object[] {licenseEntryModelImpl.getProductEntryId()};

				finderCache.removeResult(
					_finderPathCountByProductEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByProductEntryId, args);
			}
		}

		entityCache.putResult(
			LicenseEntryModelImpl.ENTITY_CACHE_ENABLED, LicenseEntryImpl.class,
			licenseEntry.getPrimaryKey(), licenseEntry, false);

		clearUniqueFindersCache(licenseEntryModelImpl, false);
		cacheUniqueFindersCache(licenseEntryModelImpl);

		licenseEntry.resetOriginalValues();

		return licenseEntry;
	}

	/**
	 * Returns the license entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the license entry
	 * @return the license entry
	 * @throws NoSuchLicenseEntryException if a license entry with the primary key could not be found
	 */
	@Override
	public LicenseEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLicenseEntryException {

		LicenseEntry licenseEntry = fetchByPrimaryKey(primaryKey);

		if (licenseEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLicenseEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return licenseEntry;
	}

	/**
	 * Returns the license entry with the primary key or throws a <code>NoSuchLicenseEntryException</code> if it could not be found.
	 *
	 * @param licenseEntryId the primary key of the license entry
	 * @return the license entry
	 * @throws NoSuchLicenseEntryException if a license entry with the primary key could not be found
	 */
	@Override
	public LicenseEntry findByPrimaryKey(long licenseEntryId)
		throws NoSuchLicenseEntryException {

		return findByPrimaryKey((Serializable)licenseEntryId);
	}

	/**
	 * Returns the license entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the license entry
	 * @return the license entry, or <code>null</code> if a license entry with the primary key could not be found
	 */
	@Override
	public LicenseEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			LicenseEntryModelImpl.ENTITY_CACHE_ENABLED, LicenseEntryImpl.class,
			primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		LicenseEntry licenseEntry = (LicenseEntry)serializable;

		if (licenseEntry == null) {
			Session session = null;

			try {
				session = openSession();

				licenseEntry = (LicenseEntry)session.get(
					LicenseEntryImpl.class, primaryKey);

				if (licenseEntry != null) {
					cacheResult(licenseEntry);
				}
				else {
					entityCache.putResult(
						LicenseEntryModelImpl.ENTITY_CACHE_ENABLED,
						LicenseEntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					LicenseEntryModelImpl.ENTITY_CACHE_ENABLED,
					LicenseEntryImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return licenseEntry;
	}

	/**
	 * Returns the license entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param licenseEntryId the primary key of the license entry
	 * @return the license entry, or <code>null</code> if a license entry with the primary key could not be found
	 */
	@Override
	public LicenseEntry fetchByPrimaryKey(long licenseEntryId) {
		return fetchByPrimaryKey((Serializable)licenseEntryId);
	}

	@Override
	public Map<Serializable, LicenseEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, LicenseEntry> map =
			new HashMap<Serializable, LicenseEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			LicenseEntry licenseEntry = fetchByPrimaryKey(primaryKey);

			if (licenseEntry != null) {
				map.put(primaryKey, licenseEntry);
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
				LicenseEntryModelImpl.ENTITY_CACHE_ENABLED,
				LicenseEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (LicenseEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_LICENSEENTRY_WHERE_PKS_IN);

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

			for (LicenseEntry licenseEntry : (List<LicenseEntry>)query.list()) {
				map.put(licenseEntry.getPrimaryKeyObj(), licenseEntry);

				cacheResult(licenseEntry);

				uncachedPrimaryKeys.remove(licenseEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					LicenseEntryModelImpl.ENTITY_CACHE_ENABLED,
					LicenseEntryImpl.class, primaryKey, nullModel);
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
	 * Returns all the license entries.
	 *
	 * @return the license entries
	 */
	@Override
	public List<LicenseEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the license entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LicenseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of license entries
	 * @param end the upper bound of the range of license entries (not inclusive)
	 * @return the range of license entries
	 */
	@Override
	public List<LicenseEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the license entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LicenseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of license entries
	 * @param end the upper bound of the range of license entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of license entries
	 */
	@Override
	public List<LicenseEntry> findAll(
		int start, int end, OrderByComparator<LicenseEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the license entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LicenseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of license entries
	 * @param end the upper bound of the range of license entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of license entries
	 */
	@Override
	public List<LicenseEntry> findAll(
		int start, int end, OrderByComparator<LicenseEntry> orderByComparator,
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

		List<LicenseEntry> list = null;

		if (useFinderCache) {
			list = (List<LicenseEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_LICENSEENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_LICENSEENTRY;

				sql = sql.concat(LicenseEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<LicenseEntry>)QueryUtil.list(
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
	 * Removes all the license entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LicenseEntry licenseEntry : findAll()) {
			remove(licenseEntry);
		}
	}

	/**
	 * Returns the number of license entries.
	 *
	 * @return the number of license entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_LICENSEENTRY);

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
		return LicenseEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the license entry persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			LicenseEntryModelImpl.ENTITY_CACHE_ENABLED,
			LicenseEntryModelImpl.FINDER_CACHE_ENABLED, LicenseEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			LicenseEntryModelImpl.ENTITY_CACHE_ENABLED,
			LicenseEntryModelImpl.FINDER_CACHE_ENABLED, LicenseEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			LicenseEntryModelImpl.ENTITY_CACHE_ENABLED,
			LicenseEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByProductEntryId = new FinderPath(
			LicenseEntryModelImpl.ENTITY_CACHE_ENABLED,
			LicenseEntryModelImpl.FINDER_CACHE_ENABLED, LicenseEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByProductEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByProductEntryId = new FinderPath(
			LicenseEntryModelImpl.ENTITY_CACHE_ENABLED,
			LicenseEntryModelImpl.FINDER_CACHE_ENABLED, LicenseEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByProductEntryId",
			new String[] {Long.class.getName()},
			LicenseEntryModelImpl.PRODUCTENTRYID_COLUMN_BITMASK |
			LicenseEntryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByProductEntryId = new FinderPath(
			LicenseEntryModelImpl.ENTITY_CACHE_ENABLED,
			LicenseEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByProductEntryId",
			new String[] {Long.class.getName()});

		_finderPathFetchByPEI_T = new FinderPath(
			LicenseEntryModelImpl.ENTITY_CACHE_ENABLED,
			LicenseEntryModelImpl.FINDER_CACHE_ENABLED, LicenseEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByPEI_T",
			new String[] {Long.class.getName(), String.class.getName()},
			LicenseEntryModelImpl.PRODUCTENTRYID_COLUMN_BITMASK |
			LicenseEntryModelImpl.TYPE_COLUMN_BITMASK);

		_finderPathCountByPEI_T = new FinderPath(
			LicenseEntryModelImpl.ENTITY_CACHE_ENABLED,
			LicenseEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPEI_T",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByPEI_V = new FinderPath(
			LicenseEntryModelImpl.ENTITY_CACHE_ENABLED,
			LicenseEntryModelImpl.FINDER_CACHE_ENABLED, LicenseEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPEI_V",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByPEI_V = new FinderPath(
			LicenseEntryModelImpl.ENTITY_CACHE_ENABLED,
			LicenseEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByPEI_V",
			new String[] {Long.class.getName(), Integer.class.getName()});

		LicenseEntryUtil.setPersistence(this);
	}

	public void destroy() {
		LicenseEntryUtil.setPersistence(null);

		entityCache.removeCache(LicenseEntryImpl.class.getName());

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

	private static final String _SQL_SELECT_LICENSEENTRY =
		"SELECT licenseEntry FROM LicenseEntry licenseEntry";

	private static final String _SQL_SELECT_LICENSEENTRY_WHERE_PKS_IN =
		"SELECT licenseEntry FROM LicenseEntry licenseEntry WHERE licenseEntryId IN (";

	private static final String _SQL_SELECT_LICENSEENTRY_WHERE =
		"SELECT licenseEntry FROM LicenseEntry licenseEntry WHERE ";

	private static final String _SQL_COUNT_LICENSEENTRY =
		"SELECT COUNT(licenseEntry) FROM LicenseEntry licenseEntry";

	private static final String _SQL_COUNT_LICENSEENTRY_WHERE =
		"SELECT COUNT(licenseEntry) FROM LicenseEntry licenseEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "licenseEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LicenseEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LicenseEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LicenseEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type"});

}