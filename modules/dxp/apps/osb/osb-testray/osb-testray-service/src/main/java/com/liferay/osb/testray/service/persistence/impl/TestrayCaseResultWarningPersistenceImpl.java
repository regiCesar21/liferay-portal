/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.testray.service.persistence.impl;

import com.liferay.osb.testray.exception.NoSuchTestrayCaseResultWarningException;
import com.liferay.osb.testray.model.TestrayCaseResultWarning;
import com.liferay.osb.testray.model.impl.TestrayCaseResultWarningImpl;
import com.liferay.osb.testray.model.impl.TestrayCaseResultWarningModelImpl;
import com.liferay.osb.testray.service.persistence.TestrayCaseResultWarningPersistence;
import com.liferay.osb.testray.service.persistence.TestrayCaseResultWarningUtil;
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
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the testray case result warning service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ethan Bustad
 * @generated
 */
public class TestrayCaseResultWarningPersistenceImpl
	extends BasePersistenceImpl<TestrayCaseResultWarning>
	implements TestrayCaseResultWarningPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>TestrayCaseResultWarningUtil</code> to access the testray case result warning persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		TestrayCaseResultWarningImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByTestrayCaseResultId;
	private FinderPath _finderPathWithoutPaginationFindByTestrayCaseResultId;
	private FinderPath _finderPathCountByTestrayCaseResultId;

	/**
	 * Returns all the testray case result warnings where testrayCaseResultId = &#63;.
	 *
	 * @param testrayCaseResultId the testray case result ID
	 * @return the matching testray case result warnings
	 */
	@Override
	public List<TestrayCaseResultWarning> findByTestrayCaseResultId(
		long testrayCaseResultId) {

		return findByTestrayCaseResultId(
			testrayCaseResultId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the testray case result warnings where testrayCaseResultId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultWarningModelImpl</code>.
	 * </p>
	 *
	 * @param testrayCaseResultId the testray case result ID
	 * @param start the lower bound of the range of testray case result warnings
	 * @param end the upper bound of the range of testray case result warnings (not inclusive)
	 * @return the range of matching testray case result warnings
	 */
	@Override
	public List<TestrayCaseResultWarning> findByTestrayCaseResultId(
		long testrayCaseResultId, int start, int end) {

		return findByTestrayCaseResultId(testrayCaseResultId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray case result warnings where testrayCaseResultId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultWarningModelImpl</code>.
	 * </p>
	 *
	 * @param testrayCaseResultId the testray case result ID
	 * @param start the lower bound of the range of testray case result warnings
	 * @param end the upper bound of the range of testray case result warnings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching testray case result warnings
	 */
	@Override
	public List<TestrayCaseResultWarning> findByTestrayCaseResultId(
		long testrayCaseResultId, int start, int end,
		OrderByComparator<TestrayCaseResultWarning> orderByComparator) {

		return findByTestrayCaseResultId(
			testrayCaseResultId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the testray case result warnings where testrayCaseResultId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultWarningModelImpl</code>.
	 * </p>
	 *
	 * @param testrayCaseResultId the testray case result ID
	 * @param start the lower bound of the range of testray case result warnings
	 * @param end the upper bound of the range of testray case result warnings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching testray case result warnings
	 */
	@Override
	public List<TestrayCaseResultWarning> findByTestrayCaseResultId(
		long testrayCaseResultId, int start, int end,
		OrderByComparator<TestrayCaseResultWarning> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByTestrayCaseResultId;
				finderArgs = new Object[] {testrayCaseResultId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByTestrayCaseResultId;
			finderArgs = new Object[] {
				testrayCaseResultId, start, end, orderByComparator
			};
		}

		List<TestrayCaseResultWarning> list = null;

		if (useFinderCache) {
			list = (List<TestrayCaseResultWarning>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (TestrayCaseResultWarning testrayCaseResultWarning : list) {
					if (testrayCaseResultId !=
							testrayCaseResultWarning.getTestrayCaseResultId()) {

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

			sb.append(_SQL_SELECT_TESTRAYCASERESULTWARNING_WHERE);

			sb.append(_FINDER_COLUMN_TESTRAYCASERESULTID_TESTRAYCASERESULTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(TestrayCaseResultWarningModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(testrayCaseResultId);

				list = (List<TestrayCaseResultWarning>)QueryUtil.list(
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
	 * Returns the first testray case result warning in the ordered set where testrayCaseResultId = &#63;.
	 *
	 * @param testrayCaseResultId the testray case result ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching testray case result warning
	 * @throws NoSuchTestrayCaseResultWarningException if a matching testray case result warning could not be found
	 */
	@Override
	public TestrayCaseResultWarning findByTestrayCaseResultId_First(
			long testrayCaseResultId,
			OrderByComparator<TestrayCaseResultWarning> orderByComparator)
		throws NoSuchTestrayCaseResultWarningException {

		TestrayCaseResultWarning testrayCaseResultWarning =
			fetchByTestrayCaseResultId_First(
				testrayCaseResultId, orderByComparator);

		if (testrayCaseResultWarning != null) {
			return testrayCaseResultWarning;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("testrayCaseResultId=");
		sb.append(testrayCaseResultId);

		sb.append("}");

		throw new NoSuchTestrayCaseResultWarningException(sb.toString());
	}

	/**
	 * Returns the first testray case result warning in the ordered set where testrayCaseResultId = &#63;.
	 *
	 * @param testrayCaseResultId the testray case result ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching testray case result warning, or <code>null</code> if a matching testray case result warning could not be found
	 */
	@Override
	public TestrayCaseResultWarning fetchByTestrayCaseResultId_First(
		long testrayCaseResultId,
		OrderByComparator<TestrayCaseResultWarning> orderByComparator) {

		List<TestrayCaseResultWarning> list = findByTestrayCaseResultId(
			testrayCaseResultId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last testray case result warning in the ordered set where testrayCaseResultId = &#63;.
	 *
	 * @param testrayCaseResultId the testray case result ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching testray case result warning
	 * @throws NoSuchTestrayCaseResultWarningException if a matching testray case result warning could not be found
	 */
	@Override
	public TestrayCaseResultWarning findByTestrayCaseResultId_Last(
			long testrayCaseResultId,
			OrderByComparator<TestrayCaseResultWarning> orderByComparator)
		throws NoSuchTestrayCaseResultWarningException {

		TestrayCaseResultWarning testrayCaseResultWarning =
			fetchByTestrayCaseResultId_Last(
				testrayCaseResultId, orderByComparator);

		if (testrayCaseResultWarning != null) {
			return testrayCaseResultWarning;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("testrayCaseResultId=");
		sb.append(testrayCaseResultId);

		sb.append("}");

		throw new NoSuchTestrayCaseResultWarningException(sb.toString());
	}

	/**
	 * Returns the last testray case result warning in the ordered set where testrayCaseResultId = &#63;.
	 *
	 * @param testrayCaseResultId the testray case result ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching testray case result warning, or <code>null</code> if a matching testray case result warning could not be found
	 */
	@Override
	public TestrayCaseResultWarning fetchByTestrayCaseResultId_Last(
		long testrayCaseResultId,
		OrderByComparator<TestrayCaseResultWarning> orderByComparator) {

		int count = countByTestrayCaseResultId(testrayCaseResultId);

		if (count == 0) {
			return null;
		}

		List<TestrayCaseResultWarning> list = findByTestrayCaseResultId(
			testrayCaseResultId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the testray case result warnings before and after the current testray case result warning in the ordered set where testrayCaseResultId = &#63;.
	 *
	 * @param testrayCaseResultWarningId the primary key of the current testray case result warning
	 * @param testrayCaseResultId the testray case result ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next testray case result warning
	 * @throws NoSuchTestrayCaseResultWarningException if a testray case result warning with the primary key could not be found
	 */
	@Override
	public TestrayCaseResultWarning[] findByTestrayCaseResultId_PrevAndNext(
			long testrayCaseResultWarningId, long testrayCaseResultId,
			OrderByComparator<TestrayCaseResultWarning> orderByComparator)
		throws NoSuchTestrayCaseResultWarningException {

		TestrayCaseResultWarning testrayCaseResultWarning = findByPrimaryKey(
			testrayCaseResultWarningId);

		Session session = null;

		try {
			session = openSession();

			TestrayCaseResultWarning[] array =
				new TestrayCaseResultWarningImpl[3];

			array[0] = getByTestrayCaseResultId_PrevAndNext(
				session, testrayCaseResultWarning, testrayCaseResultId,
				orderByComparator, true);

			array[1] = testrayCaseResultWarning;

			array[2] = getByTestrayCaseResultId_PrevAndNext(
				session, testrayCaseResultWarning, testrayCaseResultId,
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

	protected TestrayCaseResultWarning getByTestrayCaseResultId_PrevAndNext(
		Session session, TestrayCaseResultWarning testrayCaseResultWarning,
		long testrayCaseResultId,
		OrderByComparator<TestrayCaseResultWarning> orderByComparator,
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

		sb.append(_SQL_SELECT_TESTRAYCASERESULTWARNING_WHERE);

		sb.append(_FINDER_COLUMN_TESTRAYCASERESULTID_TESTRAYCASERESULTID_2);

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
			sb.append(TestrayCaseResultWarningModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(testrayCaseResultId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						testrayCaseResultWarning)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<TestrayCaseResultWarning> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the testray case result warnings where testrayCaseResultId = &#63; from the database.
	 *
	 * @param testrayCaseResultId the testray case result ID
	 */
	@Override
	public void removeByTestrayCaseResultId(long testrayCaseResultId) {
		for (TestrayCaseResultWarning testrayCaseResultWarning :
				findByTestrayCaseResultId(
					testrayCaseResultId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(testrayCaseResultWarning);
		}
	}

	/**
	 * Returns the number of testray case result warnings where testrayCaseResultId = &#63;.
	 *
	 * @param testrayCaseResultId the testray case result ID
	 * @return the number of matching testray case result warnings
	 */
	@Override
	public int countByTestrayCaseResultId(long testrayCaseResultId) {
		FinderPath finderPath = _finderPathCountByTestrayCaseResultId;

		Object[] finderArgs = new Object[] {testrayCaseResultId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_TESTRAYCASERESULTWARNING_WHERE);

			sb.append(_FINDER_COLUMN_TESTRAYCASERESULTID_TESTRAYCASERESULTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(testrayCaseResultId);

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
		_FINDER_COLUMN_TESTRAYCASERESULTID_TESTRAYCASERESULTID_2 =
			"testrayCaseResultWarning.testrayCaseResultId = ?";

	public TestrayCaseResultWarningPersistenceImpl() {
		setModelClass(TestrayCaseResultWarning.class);
	}

	/**
	 * Caches the testray case result warning in the entity cache if it is enabled.
	 *
	 * @param testrayCaseResultWarning the testray case result warning
	 */
	@Override
	public void cacheResult(TestrayCaseResultWarning testrayCaseResultWarning) {
		entityCache.putResult(
			TestrayCaseResultWarningModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultWarningImpl.class,
			testrayCaseResultWarning.getPrimaryKey(), testrayCaseResultWarning);

		testrayCaseResultWarning.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the testray case result warnings in the entity cache if it is enabled.
	 *
	 * @param testrayCaseResultWarnings the testray case result warnings
	 */
	@Override
	public void cacheResult(
		List<TestrayCaseResultWarning> testrayCaseResultWarnings) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (testrayCaseResultWarnings.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (TestrayCaseResultWarning testrayCaseResultWarning :
				testrayCaseResultWarnings) {

			if (entityCache.getResult(
					TestrayCaseResultWarningModelImpl.ENTITY_CACHE_ENABLED,
					TestrayCaseResultWarningImpl.class,
					testrayCaseResultWarning.getPrimaryKey()) == null) {

				cacheResult(testrayCaseResultWarning);
			}
			else {
				testrayCaseResultWarning.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all testray case result warnings.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(TestrayCaseResultWarningImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the testray case result warning.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(TestrayCaseResultWarning testrayCaseResultWarning) {
		entityCache.removeResult(
			TestrayCaseResultWarningModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultWarningImpl.class,
			testrayCaseResultWarning.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<TestrayCaseResultWarning> testrayCaseResultWarnings) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (TestrayCaseResultWarning testrayCaseResultWarning :
				testrayCaseResultWarnings) {

			entityCache.removeResult(
				TestrayCaseResultWarningModelImpl.ENTITY_CACHE_ENABLED,
				TestrayCaseResultWarningImpl.class,
				testrayCaseResultWarning.getPrimaryKey());
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				TestrayCaseResultWarningModelImpl.ENTITY_CACHE_ENABLED,
				TestrayCaseResultWarningImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new testray case result warning with the primary key. Does not add the testray case result warning to the database.
	 *
	 * @param testrayCaseResultWarningId the primary key for the new testray case result warning
	 * @return the new testray case result warning
	 */
	@Override
	public TestrayCaseResultWarning create(long testrayCaseResultWarningId) {
		TestrayCaseResultWarning testrayCaseResultWarning =
			new TestrayCaseResultWarningImpl();

		testrayCaseResultWarning.setNew(true);
		testrayCaseResultWarning.setPrimaryKey(testrayCaseResultWarningId);

		testrayCaseResultWarning.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return testrayCaseResultWarning;
	}

	/**
	 * Removes the testray case result warning with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param testrayCaseResultWarningId the primary key of the testray case result warning
	 * @return the testray case result warning that was removed
	 * @throws NoSuchTestrayCaseResultWarningException if a testray case result warning with the primary key could not be found
	 */
	@Override
	public TestrayCaseResultWarning remove(long testrayCaseResultWarningId)
		throws NoSuchTestrayCaseResultWarningException {

		return remove((Serializable)testrayCaseResultWarningId);
	}

	/**
	 * Removes the testray case result warning with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the testray case result warning
	 * @return the testray case result warning that was removed
	 * @throws NoSuchTestrayCaseResultWarningException if a testray case result warning with the primary key could not be found
	 */
	@Override
	public TestrayCaseResultWarning remove(Serializable primaryKey)
		throws NoSuchTestrayCaseResultWarningException {

		Session session = null;

		try {
			session = openSession();

			TestrayCaseResultWarning testrayCaseResultWarning =
				(TestrayCaseResultWarning)session.get(
					TestrayCaseResultWarningImpl.class, primaryKey);

			if (testrayCaseResultWarning == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTestrayCaseResultWarningException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(testrayCaseResultWarning);
		}
		catch (NoSuchTestrayCaseResultWarningException noSuchEntityException) {
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
	protected TestrayCaseResultWarning removeImpl(
		TestrayCaseResultWarning testrayCaseResultWarning) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(testrayCaseResultWarning)) {
				testrayCaseResultWarning =
					(TestrayCaseResultWarning)session.get(
						TestrayCaseResultWarningImpl.class,
						testrayCaseResultWarning.getPrimaryKeyObj());
			}

			if (testrayCaseResultWarning != null) {
				session.delete(testrayCaseResultWarning);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (testrayCaseResultWarning != null) {
			clearCache(testrayCaseResultWarning);
		}

		return testrayCaseResultWarning;
	}

	@Override
	public TestrayCaseResultWarning updateImpl(
		TestrayCaseResultWarning testrayCaseResultWarning) {

		boolean isNew = testrayCaseResultWarning.isNew();

		if (!(testrayCaseResultWarning instanceof
				TestrayCaseResultWarningModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(testrayCaseResultWarning.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					testrayCaseResultWarning);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in testrayCaseResultWarning proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom TestrayCaseResultWarning implementation " +
					testrayCaseResultWarning.getClass());
		}

		TestrayCaseResultWarningModelImpl testrayCaseResultWarningModelImpl =
			(TestrayCaseResultWarningModelImpl)testrayCaseResultWarning;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (testrayCaseResultWarning.getCreateDate() == null)) {
			if (serviceContext == null) {
				testrayCaseResultWarning.setCreateDate(date);
			}
			else {
				testrayCaseResultWarning.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!testrayCaseResultWarningModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				testrayCaseResultWarning.setModifiedDate(date);
			}
			else {
				testrayCaseResultWarning.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(testrayCaseResultWarning);

				testrayCaseResultWarning.setNew(false);
			}
			else {
				testrayCaseResultWarning =
					(TestrayCaseResultWarning)session.merge(
						testrayCaseResultWarning);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!TestrayCaseResultWarningModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				testrayCaseResultWarningModelImpl.getTestrayCaseResultId()
			};

			finderCache.removeResult(
				_finderPathCountByTestrayCaseResultId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByTestrayCaseResultId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((testrayCaseResultWarningModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByTestrayCaseResultId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					testrayCaseResultWarningModelImpl.
						getOriginalTestrayCaseResultId()
				};

				finderCache.removeResult(
					_finderPathCountByTestrayCaseResultId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByTestrayCaseResultId,
					args);

				args = new Object[] {
					testrayCaseResultWarningModelImpl.getTestrayCaseResultId()
				};

				finderCache.removeResult(
					_finderPathCountByTestrayCaseResultId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByTestrayCaseResultId,
					args);
			}
		}

		entityCache.putResult(
			TestrayCaseResultWarningModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultWarningImpl.class,
			testrayCaseResultWarning.getPrimaryKey(), testrayCaseResultWarning,
			false);

		testrayCaseResultWarning.resetOriginalValues();

		return testrayCaseResultWarning;
	}

	/**
	 * Returns the testray case result warning with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray case result warning
	 * @return the testray case result warning
	 * @throws NoSuchTestrayCaseResultWarningException if a testray case result warning with the primary key could not be found
	 */
	@Override
	public TestrayCaseResultWarning findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTestrayCaseResultWarningException {

		TestrayCaseResultWarning testrayCaseResultWarning = fetchByPrimaryKey(
			primaryKey);

		if (testrayCaseResultWarning == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTestrayCaseResultWarningException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return testrayCaseResultWarning;
	}

	/**
	 * Returns the testray case result warning with the primary key or throws a <code>NoSuchTestrayCaseResultWarningException</code> if it could not be found.
	 *
	 * @param testrayCaseResultWarningId the primary key of the testray case result warning
	 * @return the testray case result warning
	 * @throws NoSuchTestrayCaseResultWarningException if a testray case result warning with the primary key could not be found
	 */
	@Override
	public TestrayCaseResultWarning findByPrimaryKey(
			long testrayCaseResultWarningId)
		throws NoSuchTestrayCaseResultWarningException {

		return findByPrimaryKey((Serializable)testrayCaseResultWarningId);
	}

	/**
	 * Returns the testray case result warning with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray case result warning
	 * @return the testray case result warning, or <code>null</code> if a testray case result warning with the primary key could not be found
	 */
	@Override
	public TestrayCaseResultWarning fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			TestrayCaseResultWarningModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultWarningImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		TestrayCaseResultWarning testrayCaseResultWarning =
			(TestrayCaseResultWarning)serializable;

		if (testrayCaseResultWarning == null) {
			Session session = null;

			try {
				session = openSession();

				testrayCaseResultWarning =
					(TestrayCaseResultWarning)session.get(
						TestrayCaseResultWarningImpl.class, primaryKey);

				if (testrayCaseResultWarning != null) {
					cacheResult(testrayCaseResultWarning);
				}
				else {
					entityCache.putResult(
						TestrayCaseResultWarningModelImpl.ENTITY_CACHE_ENABLED,
						TestrayCaseResultWarningImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					TestrayCaseResultWarningModelImpl.ENTITY_CACHE_ENABLED,
					TestrayCaseResultWarningImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return testrayCaseResultWarning;
	}

	/**
	 * Returns the testray case result warning with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param testrayCaseResultWarningId the primary key of the testray case result warning
	 * @return the testray case result warning, or <code>null</code> if a testray case result warning with the primary key could not be found
	 */
	@Override
	public TestrayCaseResultWarning fetchByPrimaryKey(
		long testrayCaseResultWarningId) {

		return fetchByPrimaryKey((Serializable)testrayCaseResultWarningId);
	}

	@Override
	public Map<Serializable, TestrayCaseResultWarning> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, TestrayCaseResultWarning> map =
			new HashMap<Serializable, TestrayCaseResultWarning>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			TestrayCaseResultWarning testrayCaseResultWarning =
				fetchByPrimaryKey(primaryKey);

			if (testrayCaseResultWarning != null) {
				map.put(primaryKey, testrayCaseResultWarning);
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
				TestrayCaseResultWarningModelImpl.ENTITY_CACHE_ENABLED,
				TestrayCaseResultWarningImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (TestrayCaseResultWarning)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_TESTRAYCASERESULTWARNING_WHERE_PKS_IN);

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

			for (TestrayCaseResultWarning testrayCaseResultWarning :
					(List<TestrayCaseResultWarning>)query.list()) {

				map.put(
					testrayCaseResultWarning.getPrimaryKeyObj(),
					testrayCaseResultWarning);

				cacheResult(testrayCaseResultWarning);

				uncachedPrimaryKeys.remove(
					testrayCaseResultWarning.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					TestrayCaseResultWarningModelImpl.ENTITY_CACHE_ENABLED,
					TestrayCaseResultWarningImpl.class, primaryKey, nullModel);
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
	 * Returns all the testray case result warnings.
	 *
	 * @return the testray case result warnings
	 */
	@Override
	public List<TestrayCaseResultWarning> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the testray case result warnings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultWarningModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray case result warnings
	 * @param end the upper bound of the range of testray case result warnings (not inclusive)
	 * @return the range of testray case result warnings
	 */
	@Override
	public List<TestrayCaseResultWarning> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray case result warnings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultWarningModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray case result warnings
	 * @param end the upper bound of the range of testray case result warnings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray case result warnings
	 */
	@Override
	public List<TestrayCaseResultWarning> findAll(
		int start, int end,
		OrderByComparator<TestrayCaseResultWarning> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the testray case result warnings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultWarningModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray case result warnings
	 * @param end the upper bound of the range of testray case result warnings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of testray case result warnings
	 */
	@Override
	public List<TestrayCaseResultWarning> findAll(
		int start, int end,
		OrderByComparator<TestrayCaseResultWarning> orderByComparator,
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

		List<TestrayCaseResultWarning> list = null;

		if (useFinderCache) {
			list = (List<TestrayCaseResultWarning>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_TESTRAYCASERESULTWARNING);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_TESTRAYCASERESULTWARNING;

				sql = sql.concat(
					TestrayCaseResultWarningModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<TestrayCaseResultWarning>)QueryUtil.list(
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
	 * Removes all the testray case result warnings from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (TestrayCaseResultWarning testrayCaseResultWarning : findAll()) {
			remove(testrayCaseResultWarning);
		}
	}

	/**
	 * Returns the number of testray case result warnings.
	 *
	 * @return the number of testray case result warnings
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_TESTRAYCASERESULTWARNING);

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
	protected Map<String, Integer> getTableColumnsMap() {
		return TestrayCaseResultWarningModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the testray case result warning persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			TestrayCaseResultWarningModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultWarningModelImpl.FINDER_CACHE_ENABLED,
			TestrayCaseResultWarningImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			TestrayCaseResultWarningModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultWarningModelImpl.FINDER_CACHE_ENABLED,
			TestrayCaseResultWarningImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			TestrayCaseResultWarningModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultWarningModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByTestrayCaseResultId = new FinderPath(
			TestrayCaseResultWarningModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultWarningModelImpl.FINDER_CACHE_ENABLED,
			TestrayCaseResultWarningImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByTestrayCaseResultId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByTestrayCaseResultId = new FinderPath(
			TestrayCaseResultWarningModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultWarningModelImpl.FINDER_CACHE_ENABLED,
			TestrayCaseResultWarningImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByTestrayCaseResultId", new String[] {Long.class.getName()},
			TestrayCaseResultWarningModelImpl.
				TESTRAYCASERESULTID_COLUMN_BITMASK);

		_finderPathCountByTestrayCaseResultId = new FinderPath(
			TestrayCaseResultWarningModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultWarningModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByTestrayCaseResultId", new String[] {Long.class.getName()});

		TestrayCaseResultWarningUtil.setPersistence(this);
	}

	public void destroy() {
		TestrayCaseResultWarningUtil.setPersistence(null);

		entityCache.removeCache(TestrayCaseResultWarningImpl.class.getName());

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

	private static final String _SQL_SELECT_TESTRAYCASERESULTWARNING =
		"SELECT testrayCaseResultWarning FROM TestrayCaseResultWarning testrayCaseResultWarning";

	private static final String
		_SQL_SELECT_TESTRAYCASERESULTWARNING_WHERE_PKS_IN =
			"SELECT testrayCaseResultWarning FROM TestrayCaseResultWarning testrayCaseResultWarning WHERE testrayCaseResultWarningId IN (";

	private static final String _SQL_SELECT_TESTRAYCASERESULTWARNING_WHERE =
		"SELECT testrayCaseResultWarning FROM TestrayCaseResultWarning testrayCaseResultWarning WHERE ";

	private static final String _SQL_COUNT_TESTRAYCASERESULTWARNING =
		"SELECT COUNT(testrayCaseResultWarning) FROM TestrayCaseResultWarning testrayCaseResultWarning";

	private static final String _SQL_COUNT_TESTRAYCASERESULTWARNING_WHERE =
		"SELECT COUNT(testrayCaseResultWarning) FROM TestrayCaseResultWarning testrayCaseResultWarning WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"testrayCaseResultWarning.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No TestrayCaseResultWarning exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No TestrayCaseResultWarning exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		TestrayCaseResultWarningPersistenceImpl.class);

}