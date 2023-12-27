/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.testray.service.persistence.impl;

import com.liferay.osb.testray.exception.NoSuchTestrayCaseResultException;
import com.liferay.osb.testray.model.TestrayCaseResult;
import com.liferay.osb.testray.model.impl.TestrayCaseResultImpl;
import com.liferay.osb.testray.model.impl.TestrayCaseResultModelImpl;
import com.liferay.osb.testray.service.persistence.TestrayCaseResultPersistence;
import com.liferay.osb.testray.service.persistence.TestrayCaseResultUtil;
import com.liferay.osb.testray.service.persistence.TestrayIssuePersistence;
import com.liferay.osb.testray.service.persistence.TestraySubtaskPersistence;
import com.liferay.portal.kernel.bean.BeanReference;
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
import com.liferay.portal.kernel.service.persistence.impl.TableMapper;
import com.liferay.portal.kernel.service.persistence.impl.TableMapperFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

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
 * The persistence implementation for the testray case result service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ethan Bustad
 * @generated
 */
public class TestrayCaseResultPersistenceImpl
	extends BasePersistenceImpl<TestrayCaseResult>
	implements TestrayCaseResultPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>TestrayCaseResultUtil</code> to access the testray case result persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		TestrayCaseResultImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCreateDate;
	private FinderPath _finderPathWithoutPaginationFindByCreateDate;
	private FinderPath _finderPathCountByCreateDate;

	/**
	 * Returns all the testray case results where createDate = &#63;.
	 *
	 * @param createDate the create date
	 * @return the matching testray case results
	 */
	@Override
	public List<TestrayCaseResult> findByCreateDate(Date createDate) {
		return findByCreateDate(
			createDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the testray case results where createDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultModelImpl</code>.
	 * </p>
	 *
	 * @param createDate the create date
	 * @param start the lower bound of the range of testray case results
	 * @param end the upper bound of the range of testray case results (not inclusive)
	 * @return the range of matching testray case results
	 */
	@Override
	public List<TestrayCaseResult> findByCreateDate(
		Date createDate, int start, int end) {

		return findByCreateDate(createDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray case results where createDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultModelImpl</code>.
	 * </p>
	 *
	 * @param createDate the create date
	 * @param start the lower bound of the range of testray case results
	 * @param end the upper bound of the range of testray case results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching testray case results
	 */
	@Override
	public List<TestrayCaseResult> findByCreateDate(
		Date createDate, int start, int end,
		OrderByComparator<TestrayCaseResult> orderByComparator) {

		return findByCreateDate(
			createDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the testray case results where createDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultModelImpl</code>.
	 * </p>
	 *
	 * @param createDate the create date
	 * @param start the lower bound of the range of testray case results
	 * @param end the upper bound of the range of testray case results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching testray case results
	 */
	@Override
	public List<TestrayCaseResult> findByCreateDate(
		Date createDate, int start, int end,
		OrderByComparator<TestrayCaseResult> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCreateDate;
				finderArgs = new Object[] {_getTime(createDate)};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCreateDate;
			finderArgs = new Object[] {
				_getTime(createDate), start, end, orderByComparator
			};
		}

		List<TestrayCaseResult> list = null;

		if (useFinderCache) {
			list = (List<TestrayCaseResult>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (TestrayCaseResult testrayCaseResult : list) {
					if (!Objects.equals(
							createDate, testrayCaseResult.getCreateDate())) {

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

			sb.append(_SQL_SELECT_TESTRAYCASERESULT_WHERE);

			boolean bindCreateDate = false;

			if (createDate == null) {
				sb.append(_FINDER_COLUMN_CREATEDATE_CREATEDATE_1);
			}
			else {
				bindCreateDate = true;

				sb.append(_FINDER_COLUMN_CREATEDATE_CREATEDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(TestrayCaseResultModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindCreateDate) {
					queryPos.add(new Timestamp(createDate.getTime()));
				}

				list = (List<TestrayCaseResult>)QueryUtil.list(
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
	 * Returns the first testray case result in the ordered set where createDate = &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching testray case result
	 * @throws NoSuchTestrayCaseResultException if a matching testray case result could not be found
	 */
	@Override
	public TestrayCaseResult findByCreateDate_First(
			Date createDate,
			OrderByComparator<TestrayCaseResult> orderByComparator)
		throws NoSuchTestrayCaseResultException {

		TestrayCaseResult testrayCaseResult = fetchByCreateDate_First(
			createDate, orderByComparator);

		if (testrayCaseResult != null) {
			return testrayCaseResult;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("createDate=");
		sb.append(createDate);

		sb.append("}");

		throw new NoSuchTestrayCaseResultException(sb.toString());
	}

	/**
	 * Returns the first testray case result in the ordered set where createDate = &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching testray case result, or <code>null</code> if a matching testray case result could not be found
	 */
	@Override
	public TestrayCaseResult fetchByCreateDate_First(
		Date createDate,
		OrderByComparator<TestrayCaseResult> orderByComparator) {

		List<TestrayCaseResult> list = findByCreateDate(
			createDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last testray case result in the ordered set where createDate = &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching testray case result
	 * @throws NoSuchTestrayCaseResultException if a matching testray case result could not be found
	 */
	@Override
	public TestrayCaseResult findByCreateDate_Last(
			Date createDate,
			OrderByComparator<TestrayCaseResult> orderByComparator)
		throws NoSuchTestrayCaseResultException {

		TestrayCaseResult testrayCaseResult = fetchByCreateDate_Last(
			createDate, orderByComparator);

		if (testrayCaseResult != null) {
			return testrayCaseResult;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("createDate=");
		sb.append(createDate);

		sb.append("}");

		throw new NoSuchTestrayCaseResultException(sb.toString());
	}

	/**
	 * Returns the last testray case result in the ordered set where createDate = &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching testray case result, or <code>null</code> if a matching testray case result could not be found
	 */
	@Override
	public TestrayCaseResult fetchByCreateDate_Last(
		Date createDate,
		OrderByComparator<TestrayCaseResult> orderByComparator) {

		int count = countByCreateDate(createDate);

		if (count == 0) {
			return null;
		}

		List<TestrayCaseResult> list = findByCreateDate(
			createDate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the testray case results before and after the current testray case result in the ordered set where createDate = &#63;.
	 *
	 * @param testrayCaseResultId the primary key of the current testray case result
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next testray case result
	 * @throws NoSuchTestrayCaseResultException if a testray case result with the primary key could not be found
	 */
	@Override
	public TestrayCaseResult[] findByCreateDate_PrevAndNext(
			long testrayCaseResultId, Date createDate,
			OrderByComparator<TestrayCaseResult> orderByComparator)
		throws NoSuchTestrayCaseResultException {

		TestrayCaseResult testrayCaseResult = findByPrimaryKey(
			testrayCaseResultId);

		Session session = null;

		try {
			session = openSession();

			TestrayCaseResult[] array = new TestrayCaseResultImpl[3];

			array[0] = getByCreateDate_PrevAndNext(
				session, testrayCaseResult, createDate, orderByComparator,
				true);

			array[1] = testrayCaseResult;

			array[2] = getByCreateDate_PrevAndNext(
				session, testrayCaseResult, createDate, orderByComparator,
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

	protected TestrayCaseResult getByCreateDate_PrevAndNext(
		Session session, TestrayCaseResult testrayCaseResult, Date createDate,
		OrderByComparator<TestrayCaseResult> orderByComparator,
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

		sb.append(_SQL_SELECT_TESTRAYCASERESULT_WHERE);

		boolean bindCreateDate = false;

		if (createDate == null) {
			sb.append(_FINDER_COLUMN_CREATEDATE_CREATEDATE_1);
		}
		else {
			bindCreateDate = true;

			sb.append(_FINDER_COLUMN_CREATEDATE_CREATEDATE_2);
		}

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
			sb.append(TestrayCaseResultModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindCreateDate) {
			queryPos.add(new Timestamp(createDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						testrayCaseResult)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<TestrayCaseResult> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the testray case results where createDate = &#63; from the database.
	 *
	 * @param createDate the create date
	 */
	@Override
	public void removeByCreateDate(Date createDate) {
		for (TestrayCaseResult testrayCaseResult :
				findByCreateDate(
					createDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(testrayCaseResult);
		}
	}

	/**
	 * Returns the number of testray case results where createDate = &#63;.
	 *
	 * @param createDate the create date
	 * @return the number of matching testray case results
	 */
	@Override
	public int countByCreateDate(Date createDate) {
		FinderPath finderPath = _finderPathCountByCreateDate;

		Object[] finderArgs = new Object[] {_getTime(createDate)};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_TESTRAYCASERESULT_WHERE);

			boolean bindCreateDate = false;

			if (createDate == null) {
				sb.append(_FINDER_COLUMN_CREATEDATE_CREATEDATE_1);
			}
			else {
				bindCreateDate = true;

				sb.append(_FINDER_COLUMN_CREATEDATE_CREATEDATE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindCreateDate) {
					queryPos.add(new Timestamp(createDate.getTime()));
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

	private static final String _FINDER_COLUMN_CREATEDATE_CREATEDATE_1 =
		"testrayCaseResult.createDate IS NULL";

	private static final String _FINDER_COLUMN_CREATEDATE_CREATEDATE_2 =
		"testrayCaseResult.createDate = ?";

	private FinderPath _finderPathWithPaginationFindByTestrayBuildId;
	private FinderPath _finderPathWithoutPaginationFindByTestrayBuildId;
	private FinderPath _finderPathCountByTestrayBuildId;

	/**
	 * Returns all the testray case results where testrayBuildId = &#63;.
	 *
	 * @param testrayBuildId the testray build ID
	 * @return the matching testray case results
	 */
	@Override
	public List<TestrayCaseResult> findByTestrayBuildId(long testrayBuildId) {
		return findByTestrayBuildId(
			testrayBuildId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the testray case results where testrayBuildId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultModelImpl</code>.
	 * </p>
	 *
	 * @param testrayBuildId the testray build ID
	 * @param start the lower bound of the range of testray case results
	 * @param end the upper bound of the range of testray case results (not inclusive)
	 * @return the range of matching testray case results
	 */
	@Override
	public List<TestrayCaseResult> findByTestrayBuildId(
		long testrayBuildId, int start, int end) {

		return findByTestrayBuildId(testrayBuildId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray case results where testrayBuildId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultModelImpl</code>.
	 * </p>
	 *
	 * @param testrayBuildId the testray build ID
	 * @param start the lower bound of the range of testray case results
	 * @param end the upper bound of the range of testray case results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching testray case results
	 */
	@Override
	public List<TestrayCaseResult> findByTestrayBuildId(
		long testrayBuildId, int start, int end,
		OrderByComparator<TestrayCaseResult> orderByComparator) {

		return findByTestrayBuildId(
			testrayBuildId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the testray case results where testrayBuildId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultModelImpl</code>.
	 * </p>
	 *
	 * @param testrayBuildId the testray build ID
	 * @param start the lower bound of the range of testray case results
	 * @param end the upper bound of the range of testray case results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching testray case results
	 */
	@Override
	public List<TestrayCaseResult> findByTestrayBuildId(
		long testrayBuildId, int start, int end,
		OrderByComparator<TestrayCaseResult> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByTestrayBuildId;
				finderArgs = new Object[] {testrayBuildId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByTestrayBuildId;
			finderArgs = new Object[] {
				testrayBuildId, start, end, orderByComparator
			};
		}

		List<TestrayCaseResult> list = null;

		if (useFinderCache) {
			list = (List<TestrayCaseResult>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (TestrayCaseResult testrayCaseResult : list) {
					if (testrayBuildId !=
							testrayCaseResult.getTestrayBuildId()) {

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

			sb.append(_SQL_SELECT_TESTRAYCASERESULT_WHERE);

			sb.append(_FINDER_COLUMN_TESTRAYBUILDID_TESTRAYBUILDID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(TestrayCaseResultModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(testrayBuildId);

				list = (List<TestrayCaseResult>)QueryUtil.list(
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
	 * Returns the first testray case result in the ordered set where testrayBuildId = &#63;.
	 *
	 * @param testrayBuildId the testray build ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching testray case result
	 * @throws NoSuchTestrayCaseResultException if a matching testray case result could not be found
	 */
	@Override
	public TestrayCaseResult findByTestrayBuildId_First(
			long testrayBuildId,
			OrderByComparator<TestrayCaseResult> orderByComparator)
		throws NoSuchTestrayCaseResultException {

		TestrayCaseResult testrayCaseResult = fetchByTestrayBuildId_First(
			testrayBuildId, orderByComparator);

		if (testrayCaseResult != null) {
			return testrayCaseResult;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("testrayBuildId=");
		sb.append(testrayBuildId);

		sb.append("}");

		throw new NoSuchTestrayCaseResultException(sb.toString());
	}

	/**
	 * Returns the first testray case result in the ordered set where testrayBuildId = &#63;.
	 *
	 * @param testrayBuildId the testray build ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching testray case result, or <code>null</code> if a matching testray case result could not be found
	 */
	@Override
	public TestrayCaseResult fetchByTestrayBuildId_First(
		long testrayBuildId,
		OrderByComparator<TestrayCaseResult> orderByComparator) {

		List<TestrayCaseResult> list = findByTestrayBuildId(
			testrayBuildId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last testray case result in the ordered set where testrayBuildId = &#63;.
	 *
	 * @param testrayBuildId the testray build ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching testray case result
	 * @throws NoSuchTestrayCaseResultException if a matching testray case result could not be found
	 */
	@Override
	public TestrayCaseResult findByTestrayBuildId_Last(
			long testrayBuildId,
			OrderByComparator<TestrayCaseResult> orderByComparator)
		throws NoSuchTestrayCaseResultException {

		TestrayCaseResult testrayCaseResult = fetchByTestrayBuildId_Last(
			testrayBuildId, orderByComparator);

		if (testrayCaseResult != null) {
			return testrayCaseResult;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("testrayBuildId=");
		sb.append(testrayBuildId);

		sb.append("}");

		throw new NoSuchTestrayCaseResultException(sb.toString());
	}

	/**
	 * Returns the last testray case result in the ordered set where testrayBuildId = &#63;.
	 *
	 * @param testrayBuildId the testray build ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching testray case result, or <code>null</code> if a matching testray case result could not be found
	 */
	@Override
	public TestrayCaseResult fetchByTestrayBuildId_Last(
		long testrayBuildId,
		OrderByComparator<TestrayCaseResult> orderByComparator) {

		int count = countByTestrayBuildId(testrayBuildId);

		if (count == 0) {
			return null;
		}

		List<TestrayCaseResult> list = findByTestrayBuildId(
			testrayBuildId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the testray case results before and after the current testray case result in the ordered set where testrayBuildId = &#63;.
	 *
	 * @param testrayCaseResultId the primary key of the current testray case result
	 * @param testrayBuildId the testray build ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next testray case result
	 * @throws NoSuchTestrayCaseResultException if a testray case result with the primary key could not be found
	 */
	@Override
	public TestrayCaseResult[] findByTestrayBuildId_PrevAndNext(
			long testrayCaseResultId, long testrayBuildId,
			OrderByComparator<TestrayCaseResult> orderByComparator)
		throws NoSuchTestrayCaseResultException {

		TestrayCaseResult testrayCaseResult = findByPrimaryKey(
			testrayCaseResultId);

		Session session = null;

		try {
			session = openSession();

			TestrayCaseResult[] array = new TestrayCaseResultImpl[3];

			array[0] = getByTestrayBuildId_PrevAndNext(
				session, testrayCaseResult, testrayBuildId, orderByComparator,
				true);

			array[1] = testrayCaseResult;

			array[2] = getByTestrayBuildId_PrevAndNext(
				session, testrayCaseResult, testrayBuildId, orderByComparator,
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

	protected TestrayCaseResult getByTestrayBuildId_PrevAndNext(
		Session session, TestrayCaseResult testrayCaseResult,
		long testrayBuildId,
		OrderByComparator<TestrayCaseResult> orderByComparator,
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

		sb.append(_SQL_SELECT_TESTRAYCASERESULT_WHERE);

		sb.append(_FINDER_COLUMN_TESTRAYBUILDID_TESTRAYBUILDID_2);

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
			sb.append(TestrayCaseResultModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(testrayBuildId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						testrayCaseResult)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<TestrayCaseResult> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the testray case results where testrayBuildId = &#63; from the database.
	 *
	 * @param testrayBuildId the testray build ID
	 */
	@Override
	public void removeByTestrayBuildId(long testrayBuildId) {
		for (TestrayCaseResult testrayCaseResult :
				findByTestrayBuildId(
					testrayBuildId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(testrayCaseResult);
		}
	}

	/**
	 * Returns the number of testray case results where testrayBuildId = &#63;.
	 *
	 * @param testrayBuildId the testray build ID
	 * @return the number of matching testray case results
	 */
	@Override
	public int countByTestrayBuildId(long testrayBuildId) {
		FinderPath finderPath = _finderPathCountByTestrayBuildId;

		Object[] finderArgs = new Object[] {testrayBuildId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_TESTRAYCASERESULT_WHERE);

			sb.append(_FINDER_COLUMN_TESTRAYBUILDID_TESTRAYBUILDID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(testrayBuildId);

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

	private static final String _FINDER_COLUMN_TESTRAYBUILDID_TESTRAYBUILDID_2 =
		"testrayCaseResult.testrayBuildId = ?";

	private FinderPath _finderPathWithPaginationFindByTestrayCaseId;
	private FinderPath _finderPathWithoutPaginationFindByTestrayCaseId;
	private FinderPath _finderPathCountByTestrayCaseId;

	/**
	 * Returns all the testray case results where testrayCaseId = &#63;.
	 *
	 * @param testrayCaseId the testray case ID
	 * @return the matching testray case results
	 */
	@Override
	public List<TestrayCaseResult> findByTestrayCaseId(long testrayCaseId) {
		return findByTestrayCaseId(
			testrayCaseId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the testray case results where testrayCaseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultModelImpl</code>.
	 * </p>
	 *
	 * @param testrayCaseId the testray case ID
	 * @param start the lower bound of the range of testray case results
	 * @param end the upper bound of the range of testray case results (not inclusive)
	 * @return the range of matching testray case results
	 */
	@Override
	public List<TestrayCaseResult> findByTestrayCaseId(
		long testrayCaseId, int start, int end) {

		return findByTestrayCaseId(testrayCaseId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray case results where testrayCaseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultModelImpl</code>.
	 * </p>
	 *
	 * @param testrayCaseId the testray case ID
	 * @param start the lower bound of the range of testray case results
	 * @param end the upper bound of the range of testray case results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching testray case results
	 */
	@Override
	public List<TestrayCaseResult> findByTestrayCaseId(
		long testrayCaseId, int start, int end,
		OrderByComparator<TestrayCaseResult> orderByComparator) {

		return findByTestrayCaseId(
			testrayCaseId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the testray case results where testrayCaseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultModelImpl</code>.
	 * </p>
	 *
	 * @param testrayCaseId the testray case ID
	 * @param start the lower bound of the range of testray case results
	 * @param end the upper bound of the range of testray case results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching testray case results
	 */
	@Override
	public List<TestrayCaseResult> findByTestrayCaseId(
		long testrayCaseId, int start, int end,
		OrderByComparator<TestrayCaseResult> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByTestrayCaseId;
				finderArgs = new Object[] {testrayCaseId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByTestrayCaseId;
			finderArgs = new Object[] {
				testrayCaseId, start, end, orderByComparator
			};
		}

		List<TestrayCaseResult> list = null;

		if (useFinderCache) {
			list = (List<TestrayCaseResult>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (TestrayCaseResult testrayCaseResult : list) {
					if (testrayCaseId != testrayCaseResult.getTestrayCaseId()) {
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

			sb.append(_SQL_SELECT_TESTRAYCASERESULT_WHERE);

			sb.append(_FINDER_COLUMN_TESTRAYCASEID_TESTRAYCASEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(TestrayCaseResultModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(testrayCaseId);

				list = (List<TestrayCaseResult>)QueryUtil.list(
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
	 * Returns the first testray case result in the ordered set where testrayCaseId = &#63;.
	 *
	 * @param testrayCaseId the testray case ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching testray case result
	 * @throws NoSuchTestrayCaseResultException if a matching testray case result could not be found
	 */
	@Override
	public TestrayCaseResult findByTestrayCaseId_First(
			long testrayCaseId,
			OrderByComparator<TestrayCaseResult> orderByComparator)
		throws NoSuchTestrayCaseResultException {

		TestrayCaseResult testrayCaseResult = fetchByTestrayCaseId_First(
			testrayCaseId, orderByComparator);

		if (testrayCaseResult != null) {
			return testrayCaseResult;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("testrayCaseId=");
		sb.append(testrayCaseId);

		sb.append("}");

		throw new NoSuchTestrayCaseResultException(sb.toString());
	}

	/**
	 * Returns the first testray case result in the ordered set where testrayCaseId = &#63;.
	 *
	 * @param testrayCaseId the testray case ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching testray case result, or <code>null</code> if a matching testray case result could not be found
	 */
	@Override
	public TestrayCaseResult fetchByTestrayCaseId_First(
		long testrayCaseId,
		OrderByComparator<TestrayCaseResult> orderByComparator) {

		List<TestrayCaseResult> list = findByTestrayCaseId(
			testrayCaseId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last testray case result in the ordered set where testrayCaseId = &#63;.
	 *
	 * @param testrayCaseId the testray case ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching testray case result
	 * @throws NoSuchTestrayCaseResultException if a matching testray case result could not be found
	 */
	@Override
	public TestrayCaseResult findByTestrayCaseId_Last(
			long testrayCaseId,
			OrderByComparator<TestrayCaseResult> orderByComparator)
		throws NoSuchTestrayCaseResultException {

		TestrayCaseResult testrayCaseResult = fetchByTestrayCaseId_Last(
			testrayCaseId, orderByComparator);

		if (testrayCaseResult != null) {
			return testrayCaseResult;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("testrayCaseId=");
		sb.append(testrayCaseId);

		sb.append("}");

		throw new NoSuchTestrayCaseResultException(sb.toString());
	}

	/**
	 * Returns the last testray case result in the ordered set where testrayCaseId = &#63;.
	 *
	 * @param testrayCaseId the testray case ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching testray case result, or <code>null</code> if a matching testray case result could not be found
	 */
	@Override
	public TestrayCaseResult fetchByTestrayCaseId_Last(
		long testrayCaseId,
		OrderByComparator<TestrayCaseResult> orderByComparator) {

		int count = countByTestrayCaseId(testrayCaseId);

		if (count == 0) {
			return null;
		}

		List<TestrayCaseResult> list = findByTestrayCaseId(
			testrayCaseId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the testray case results before and after the current testray case result in the ordered set where testrayCaseId = &#63;.
	 *
	 * @param testrayCaseResultId the primary key of the current testray case result
	 * @param testrayCaseId the testray case ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next testray case result
	 * @throws NoSuchTestrayCaseResultException if a testray case result with the primary key could not be found
	 */
	@Override
	public TestrayCaseResult[] findByTestrayCaseId_PrevAndNext(
			long testrayCaseResultId, long testrayCaseId,
			OrderByComparator<TestrayCaseResult> orderByComparator)
		throws NoSuchTestrayCaseResultException {

		TestrayCaseResult testrayCaseResult = findByPrimaryKey(
			testrayCaseResultId);

		Session session = null;

		try {
			session = openSession();

			TestrayCaseResult[] array = new TestrayCaseResultImpl[3];

			array[0] = getByTestrayCaseId_PrevAndNext(
				session, testrayCaseResult, testrayCaseId, orderByComparator,
				true);

			array[1] = testrayCaseResult;

			array[2] = getByTestrayCaseId_PrevAndNext(
				session, testrayCaseResult, testrayCaseId, orderByComparator,
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

	protected TestrayCaseResult getByTestrayCaseId_PrevAndNext(
		Session session, TestrayCaseResult testrayCaseResult,
		long testrayCaseId,
		OrderByComparator<TestrayCaseResult> orderByComparator,
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

		sb.append(_SQL_SELECT_TESTRAYCASERESULT_WHERE);

		sb.append(_FINDER_COLUMN_TESTRAYCASEID_TESTRAYCASEID_2);

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
			sb.append(TestrayCaseResultModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(testrayCaseId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						testrayCaseResult)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<TestrayCaseResult> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the testray case results where testrayCaseId = &#63; from the database.
	 *
	 * @param testrayCaseId the testray case ID
	 */
	@Override
	public void removeByTestrayCaseId(long testrayCaseId) {
		for (TestrayCaseResult testrayCaseResult :
				findByTestrayCaseId(
					testrayCaseId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(testrayCaseResult);
		}
	}

	/**
	 * Returns the number of testray case results where testrayCaseId = &#63;.
	 *
	 * @param testrayCaseId the testray case ID
	 * @return the number of matching testray case results
	 */
	@Override
	public int countByTestrayCaseId(long testrayCaseId) {
		FinderPath finderPath = _finderPathCountByTestrayCaseId;

		Object[] finderArgs = new Object[] {testrayCaseId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_TESTRAYCASERESULT_WHERE);

			sb.append(_FINDER_COLUMN_TESTRAYCASEID_TESTRAYCASEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(testrayCaseId);

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

	private static final String _FINDER_COLUMN_TESTRAYCASEID_TESTRAYCASEID_2 =
		"testrayCaseResult.testrayCaseId = ?";

	private FinderPath _finderPathWithPaginationFindByTestrayComponentId;
	private FinderPath _finderPathWithoutPaginationFindByTestrayComponentId;
	private FinderPath _finderPathCountByTestrayComponentId;

	/**
	 * Returns all the testray case results where testrayComponentId = &#63;.
	 *
	 * @param testrayComponentId the testray component ID
	 * @return the matching testray case results
	 */
	@Override
	public List<TestrayCaseResult> findByTestrayComponentId(
		long testrayComponentId) {

		return findByTestrayComponentId(
			testrayComponentId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the testray case results where testrayComponentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultModelImpl</code>.
	 * </p>
	 *
	 * @param testrayComponentId the testray component ID
	 * @param start the lower bound of the range of testray case results
	 * @param end the upper bound of the range of testray case results (not inclusive)
	 * @return the range of matching testray case results
	 */
	@Override
	public List<TestrayCaseResult> findByTestrayComponentId(
		long testrayComponentId, int start, int end) {

		return findByTestrayComponentId(testrayComponentId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray case results where testrayComponentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultModelImpl</code>.
	 * </p>
	 *
	 * @param testrayComponentId the testray component ID
	 * @param start the lower bound of the range of testray case results
	 * @param end the upper bound of the range of testray case results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching testray case results
	 */
	@Override
	public List<TestrayCaseResult> findByTestrayComponentId(
		long testrayComponentId, int start, int end,
		OrderByComparator<TestrayCaseResult> orderByComparator) {

		return findByTestrayComponentId(
			testrayComponentId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the testray case results where testrayComponentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultModelImpl</code>.
	 * </p>
	 *
	 * @param testrayComponentId the testray component ID
	 * @param start the lower bound of the range of testray case results
	 * @param end the upper bound of the range of testray case results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching testray case results
	 */
	@Override
	public List<TestrayCaseResult> findByTestrayComponentId(
		long testrayComponentId, int start, int end,
		OrderByComparator<TestrayCaseResult> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByTestrayComponentId;
				finderArgs = new Object[] {testrayComponentId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByTestrayComponentId;
			finderArgs = new Object[] {
				testrayComponentId, start, end, orderByComparator
			};
		}

		List<TestrayCaseResult> list = null;

		if (useFinderCache) {
			list = (List<TestrayCaseResult>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (TestrayCaseResult testrayCaseResult : list) {
					if (testrayComponentId !=
							testrayCaseResult.getTestrayComponentId()) {

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

			sb.append(_SQL_SELECT_TESTRAYCASERESULT_WHERE);

			sb.append(_FINDER_COLUMN_TESTRAYCOMPONENTID_TESTRAYCOMPONENTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(TestrayCaseResultModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(testrayComponentId);

				list = (List<TestrayCaseResult>)QueryUtil.list(
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
	 * Returns the first testray case result in the ordered set where testrayComponentId = &#63;.
	 *
	 * @param testrayComponentId the testray component ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching testray case result
	 * @throws NoSuchTestrayCaseResultException if a matching testray case result could not be found
	 */
	@Override
	public TestrayCaseResult findByTestrayComponentId_First(
			long testrayComponentId,
			OrderByComparator<TestrayCaseResult> orderByComparator)
		throws NoSuchTestrayCaseResultException {

		TestrayCaseResult testrayCaseResult = fetchByTestrayComponentId_First(
			testrayComponentId, orderByComparator);

		if (testrayCaseResult != null) {
			return testrayCaseResult;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("testrayComponentId=");
		sb.append(testrayComponentId);

		sb.append("}");

		throw new NoSuchTestrayCaseResultException(sb.toString());
	}

	/**
	 * Returns the first testray case result in the ordered set where testrayComponentId = &#63;.
	 *
	 * @param testrayComponentId the testray component ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching testray case result, or <code>null</code> if a matching testray case result could not be found
	 */
	@Override
	public TestrayCaseResult fetchByTestrayComponentId_First(
		long testrayComponentId,
		OrderByComparator<TestrayCaseResult> orderByComparator) {

		List<TestrayCaseResult> list = findByTestrayComponentId(
			testrayComponentId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last testray case result in the ordered set where testrayComponentId = &#63;.
	 *
	 * @param testrayComponentId the testray component ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching testray case result
	 * @throws NoSuchTestrayCaseResultException if a matching testray case result could not be found
	 */
	@Override
	public TestrayCaseResult findByTestrayComponentId_Last(
			long testrayComponentId,
			OrderByComparator<TestrayCaseResult> orderByComparator)
		throws NoSuchTestrayCaseResultException {

		TestrayCaseResult testrayCaseResult = fetchByTestrayComponentId_Last(
			testrayComponentId, orderByComparator);

		if (testrayCaseResult != null) {
			return testrayCaseResult;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("testrayComponentId=");
		sb.append(testrayComponentId);

		sb.append("}");

		throw new NoSuchTestrayCaseResultException(sb.toString());
	}

	/**
	 * Returns the last testray case result in the ordered set where testrayComponentId = &#63;.
	 *
	 * @param testrayComponentId the testray component ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching testray case result, or <code>null</code> if a matching testray case result could not be found
	 */
	@Override
	public TestrayCaseResult fetchByTestrayComponentId_Last(
		long testrayComponentId,
		OrderByComparator<TestrayCaseResult> orderByComparator) {

		int count = countByTestrayComponentId(testrayComponentId);

		if (count == 0) {
			return null;
		}

		List<TestrayCaseResult> list = findByTestrayComponentId(
			testrayComponentId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the testray case results before and after the current testray case result in the ordered set where testrayComponentId = &#63;.
	 *
	 * @param testrayCaseResultId the primary key of the current testray case result
	 * @param testrayComponentId the testray component ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next testray case result
	 * @throws NoSuchTestrayCaseResultException if a testray case result with the primary key could not be found
	 */
	@Override
	public TestrayCaseResult[] findByTestrayComponentId_PrevAndNext(
			long testrayCaseResultId, long testrayComponentId,
			OrderByComparator<TestrayCaseResult> orderByComparator)
		throws NoSuchTestrayCaseResultException {

		TestrayCaseResult testrayCaseResult = findByPrimaryKey(
			testrayCaseResultId);

		Session session = null;

		try {
			session = openSession();

			TestrayCaseResult[] array = new TestrayCaseResultImpl[3];

			array[0] = getByTestrayComponentId_PrevAndNext(
				session, testrayCaseResult, testrayComponentId,
				orderByComparator, true);

			array[1] = testrayCaseResult;

			array[2] = getByTestrayComponentId_PrevAndNext(
				session, testrayCaseResult, testrayComponentId,
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

	protected TestrayCaseResult getByTestrayComponentId_PrevAndNext(
		Session session, TestrayCaseResult testrayCaseResult,
		long testrayComponentId,
		OrderByComparator<TestrayCaseResult> orderByComparator,
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

		sb.append(_SQL_SELECT_TESTRAYCASERESULT_WHERE);

		sb.append(_FINDER_COLUMN_TESTRAYCOMPONENTID_TESTRAYCOMPONENTID_2);

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
			sb.append(TestrayCaseResultModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(testrayComponentId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						testrayCaseResult)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<TestrayCaseResult> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the testray case results where testrayComponentId = &#63; from the database.
	 *
	 * @param testrayComponentId the testray component ID
	 */
	@Override
	public void removeByTestrayComponentId(long testrayComponentId) {
		for (TestrayCaseResult testrayCaseResult :
				findByTestrayComponentId(
					testrayComponentId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(testrayCaseResult);
		}
	}

	/**
	 * Returns the number of testray case results where testrayComponentId = &#63;.
	 *
	 * @param testrayComponentId the testray component ID
	 * @return the number of matching testray case results
	 */
	@Override
	public int countByTestrayComponentId(long testrayComponentId) {
		FinderPath finderPath = _finderPathCountByTestrayComponentId;

		Object[] finderArgs = new Object[] {testrayComponentId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_TESTRAYCASERESULT_WHERE);

			sb.append(_FINDER_COLUMN_TESTRAYCOMPONENTID_TESTRAYCOMPONENTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(testrayComponentId);

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
		_FINDER_COLUMN_TESTRAYCOMPONENTID_TESTRAYCOMPONENTID_2 =
			"testrayCaseResult.testrayComponentId = ?";

	private FinderPath _finderPathWithPaginationFindByTestrayRunId;
	private FinderPath _finderPathWithoutPaginationFindByTestrayRunId;
	private FinderPath _finderPathCountByTestrayRunId;

	/**
	 * Returns all the testray case results where testrayRunId = &#63;.
	 *
	 * @param testrayRunId the testray run ID
	 * @return the matching testray case results
	 */
	@Override
	public List<TestrayCaseResult> findByTestrayRunId(long testrayRunId) {
		return findByTestrayRunId(
			testrayRunId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the testray case results where testrayRunId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultModelImpl</code>.
	 * </p>
	 *
	 * @param testrayRunId the testray run ID
	 * @param start the lower bound of the range of testray case results
	 * @param end the upper bound of the range of testray case results (not inclusive)
	 * @return the range of matching testray case results
	 */
	@Override
	public List<TestrayCaseResult> findByTestrayRunId(
		long testrayRunId, int start, int end) {

		return findByTestrayRunId(testrayRunId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray case results where testrayRunId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultModelImpl</code>.
	 * </p>
	 *
	 * @param testrayRunId the testray run ID
	 * @param start the lower bound of the range of testray case results
	 * @param end the upper bound of the range of testray case results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching testray case results
	 */
	@Override
	public List<TestrayCaseResult> findByTestrayRunId(
		long testrayRunId, int start, int end,
		OrderByComparator<TestrayCaseResult> orderByComparator) {

		return findByTestrayRunId(
			testrayRunId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the testray case results where testrayRunId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultModelImpl</code>.
	 * </p>
	 *
	 * @param testrayRunId the testray run ID
	 * @param start the lower bound of the range of testray case results
	 * @param end the upper bound of the range of testray case results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching testray case results
	 */
	@Override
	public List<TestrayCaseResult> findByTestrayRunId(
		long testrayRunId, int start, int end,
		OrderByComparator<TestrayCaseResult> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByTestrayRunId;
				finderArgs = new Object[] {testrayRunId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByTestrayRunId;
			finderArgs = new Object[] {
				testrayRunId, start, end, orderByComparator
			};
		}

		List<TestrayCaseResult> list = null;

		if (useFinderCache) {
			list = (List<TestrayCaseResult>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (TestrayCaseResult testrayCaseResult : list) {
					if (testrayRunId != testrayCaseResult.getTestrayRunId()) {
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

			sb.append(_SQL_SELECT_TESTRAYCASERESULT_WHERE);

			sb.append(_FINDER_COLUMN_TESTRAYRUNID_TESTRAYRUNID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(TestrayCaseResultModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(testrayRunId);

				list = (List<TestrayCaseResult>)QueryUtil.list(
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
	 * Returns the first testray case result in the ordered set where testrayRunId = &#63;.
	 *
	 * @param testrayRunId the testray run ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching testray case result
	 * @throws NoSuchTestrayCaseResultException if a matching testray case result could not be found
	 */
	@Override
	public TestrayCaseResult findByTestrayRunId_First(
			long testrayRunId,
			OrderByComparator<TestrayCaseResult> orderByComparator)
		throws NoSuchTestrayCaseResultException {

		TestrayCaseResult testrayCaseResult = fetchByTestrayRunId_First(
			testrayRunId, orderByComparator);

		if (testrayCaseResult != null) {
			return testrayCaseResult;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("testrayRunId=");
		sb.append(testrayRunId);

		sb.append("}");

		throw new NoSuchTestrayCaseResultException(sb.toString());
	}

	/**
	 * Returns the first testray case result in the ordered set where testrayRunId = &#63;.
	 *
	 * @param testrayRunId the testray run ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching testray case result, or <code>null</code> if a matching testray case result could not be found
	 */
	@Override
	public TestrayCaseResult fetchByTestrayRunId_First(
		long testrayRunId,
		OrderByComparator<TestrayCaseResult> orderByComparator) {

		List<TestrayCaseResult> list = findByTestrayRunId(
			testrayRunId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last testray case result in the ordered set where testrayRunId = &#63;.
	 *
	 * @param testrayRunId the testray run ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching testray case result
	 * @throws NoSuchTestrayCaseResultException if a matching testray case result could not be found
	 */
	@Override
	public TestrayCaseResult findByTestrayRunId_Last(
			long testrayRunId,
			OrderByComparator<TestrayCaseResult> orderByComparator)
		throws NoSuchTestrayCaseResultException {

		TestrayCaseResult testrayCaseResult = fetchByTestrayRunId_Last(
			testrayRunId, orderByComparator);

		if (testrayCaseResult != null) {
			return testrayCaseResult;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("testrayRunId=");
		sb.append(testrayRunId);

		sb.append("}");

		throw new NoSuchTestrayCaseResultException(sb.toString());
	}

	/**
	 * Returns the last testray case result in the ordered set where testrayRunId = &#63;.
	 *
	 * @param testrayRunId the testray run ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching testray case result, or <code>null</code> if a matching testray case result could not be found
	 */
	@Override
	public TestrayCaseResult fetchByTestrayRunId_Last(
		long testrayRunId,
		OrderByComparator<TestrayCaseResult> orderByComparator) {

		int count = countByTestrayRunId(testrayRunId);

		if (count == 0) {
			return null;
		}

		List<TestrayCaseResult> list = findByTestrayRunId(
			testrayRunId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the testray case results before and after the current testray case result in the ordered set where testrayRunId = &#63;.
	 *
	 * @param testrayCaseResultId the primary key of the current testray case result
	 * @param testrayRunId the testray run ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next testray case result
	 * @throws NoSuchTestrayCaseResultException if a testray case result with the primary key could not be found
	 */
	@Override
	public TestrayCaseResult[] findByTestrayRunId_PrevAndNext(
			long testrayCaseResultId, long testrayRunId,
			OrderByComparator<TestrayCaseResult> orderByComparator)
		throws NoSuchTestrayCaseResultException {

		TestrayCaseResult testrayCaseResult = findByPrimaryKey(
			testrayCaseResultId);

		Session session = null;

		try {
			session = openSession();

			TestrayCaseResult[] array = new TestrayCaseResultImpl[3];

			array[0] = getByTestrayRunId_PrevAndNext(
				session, testrayCaseResult, testrayRunId, orderByComparator,
				true);

			array[1] = testrayCaseResult;

			array[2] = getByTestrayRunId_PrevAndNext(
				session, testrayCaseResult, testrayRunId, orderByComparator,
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

	protected TestrayCaseResult getByTestrayRunId_PrevAndNext(
		Session session, TestrayCaseResult testrayCaseResult, long testrayRunId,
		OrderByComparator<TestrayCaseResult> orderByComparator,
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

		sb.append(_SQL_SELECT_TESTRAYCASERESULT_WHERE);

		sb.append(_FINDER_COLUMN_TESTRAYRUNID_TESTRAYRUNID_2);

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
			sb.append(TestrayCaseResultModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(testrayRunId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						testrayCaseResult)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<TestrayCaseResult> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the testray case results where testrayRunId = &#63; from the database.
	 *
	 * @param testrayRunId the testray run ID
	 */
	@Override
	public void removeByTestrayRunId(long testrayRunId) {
		for (TestrayCaseResult testrayCaseResult :
				findByTestrayRunId(
					testrayRunId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(testrayCaseResult);
		}
	}

	/**
	 * Returns the number of testray case results where testrayRunId = &#63;.
	 *
	 * @param testrayRunId the testray run ID
	 * @return the number of matching testray case results
	 */
	@Override
	public int countByTestrayRunId(long testrayRunId) {
		FinderPath finderPath = _finderPathCountByTestrayRunId;

		Object[] finderArgs = new Object[] {testrayRunId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_TESTRAYCASERESULT_WHERE);

			sb.append(_FINDER_COLUMN_TESTRAYRUNID_TESTRAYRUNID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(testrayRunId);

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

	private static final String _FINDER_COLUMN_TESTRAYRUNID_TESTRAYRUNID_2 =
		"testrayCaseResult.testrayRunId = ?";

	private FinderPath _finderPathFetchByTCaI_TRI;
	private FinderPath _finderPathCountByTCaI_TRI;

	/**
	 * Returns the testray case result where testrayCaseId = &#63; and testrayRunId = &#63; or throws a <code>NoSuchTestrayCaseResultException</code> if it could not be found.
	 *
	 * @param testrayCaseId the testray case ID
	 * @param testrayRunId the testray run ID
	 * @return the matching testray case result
	 * @throws NoSuchTestrayCaseResultException if a matching testray case result could not be found
	 */
	@Override
	public TestrayCaseResult findByTCaI_TRI(
			long testrayCaseId, long testrayRunId)
		throws NoSuchTestrayCaseResultException {

		TestrayCaseResult testrayCaseResult = fetchByTCaI_TRI(
			testrayCaseId, testrayRunId);

		if (testrayCaseResult == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("testrayCaseId=");
			sb.append(testrayCaseId);

			sb.append(", testrayRunId=");
			sb.append(testrayRunId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchTestrayCaseResultException(sb.toString());
		}

		return testrayCaseResult;
	}

	/**
	 * Returns the testray case result where testrayCaseId = &#63; and testrayRunId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param testrayCaseId the testray case ID
	 * @param testrayRunId the testray run ID
	 * @return the matching testray case result, or <code>null</code> if a matching testray case result could not be found
	 */
	@Override
	public TestrayCaseResult fetchByTCaI_TRI(
		long testrayCaseId, long testrayRunId) {

		return fetchByTCaI_TRI(testrayCaseId, testrayRunId, true);
	}

	/**
	 * Returns the testray case result where testrayCaseId = &#63; and testrayRunId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param testrayCaseId the testray case ID
	 * @param testrayRunId the testray run ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching testray case result, or <code>null</code> if a matching testray case result could not be found
	 */
	@Override
	public TestrayCaseResult fetchByTCaI_TRI(
		long testrayCaseId, long testrayRunId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {testrayCaseId, testrayRunId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByTCaI_TRI, finderArgs, this);
		}

		if (result instanceof TestrayCaseResult) {
			TestrayCaseResult testrayCaseResult = (TestrayCaseResult)result;

			if ((testrayCaseId != testrayCaseResult.getTestrayCaseId()) ||
				(testrayRunId != testrayCaseResult.getTestrayRunId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_TESTRAYCASERESULT_WHERE);

			sb.append(_FINDER_COLUMN_TCAI_TRI_TESTRAYCASEID_2);

			sb.append(_FINDER_COLUMN_TCAI_TRI_TESTRAYRUNID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(testrayCaseId);

				queryPos.add(testrayRunId);

				List<TestrayCaseResult> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByTCaI_TRI, finderArgs, list);
					}
				}
				else {
					TestrayCaseResult testrayCaseResult = list.get(0);

					result = testrayCaseResult;

					cacheResult(testrayCaseResult);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByTCaI_TRI, finderArgs);
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
			return (TestrayCaseResult)result;
		}
	}

	/**
	 * Removes the testray case result where testrayCaseId = &#63; and testrayRunId = &#63; from the database.
	 *
	 * @param testrayCaseId the testray case ID
	 * @param testrayRunId the testray run ID
	 * @return the testray case result that was removed
	 */
	@Override
	public TestrayCaseResult removeByTCaI_TRI(
			long testrayCaseId, long testrayRunId)
		throws NoSuchTestrayCaseResultException {

		TestrayCaseResult testrayCaseResult = findByTCaI_TRI(
			testrayCaseId, testrayRunId);

		return remove(testrayCaseResult);
	}

	/**
	 * Returns the number of testray case results where testrayCaseId = &#63; and testrayRunId = &#63;.
	 *
	 * @param testrayCaseId the testray case ID
	 * @param testrayRunId the testray run ID
	 * @return the number of matching testray case results
	 */
	@Override
	public int countByTCaI_TRI(long testrayCaseId, long testrayRunId) {
		FinderPath finderPath = _finderPathCountByTCaI_TRI;

		Object[] finderArgs = new Object[] {testrayCaseId, testrayRunId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_TESTRAYCASERESULT_WHERE);

			sb.append(_FINDER_COLUMN_TCAI_TRI_TESTRAYCASEID_2);

			sb.append(_FINDER_COLUMN_TCAI_TRI_TESTRAYRUNID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(testrayCaseId);

				queryPos.add(testrayRunId);

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

	private static final String _FINDER_COLUMN_TCAI_TRI_TESTRAYCASEID_2 =
		"testrayCaseResult.testrayCaseId = ? AND ";

	private static final String _FINDER_COLUMN_TCAI_TRI_TESTRAYRUNID_2 =
		"testrayCaseResult.testrayRunId = ?";

	private FinderPath _finderPathWithPaginationFindByTCI_TRI;
	private FinderPath _finderPathWithoutPaginationFindByTCI_TRI;
	private FinderPath _finderPathCountByTCI_TRI;

	/**
	 * Returns all the testray case results where testrayComponentId = &#63; and testrayRunId = &#63;.
	 *
	 * @param testrayComponentId the testray component ID
	 * @param testrayRunId the testray run ID
	 * @return the matching testray case results
	 */
	@Override
	public List<TestrayCaseResult> findByTCI_TRI(
		long testrayComponentId, long testrayRunId) {

		return findByTCI_TRI(
			testrayComponentId, testrayRunId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the testray case results where testrayComponentId = &#63; and testrayRunId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultModelImpl</code>.
	 * </p>
	 *
	 * @param testrayComponentId the testray component ID
	 * @param testrayRunId the testray run ID
	 * @param start the lower bound of the range of testray case results
	 * @param end the upper bound of the range of testray case results (not inclusive)
	 * @return the range of matching testray case results
	 */
	@Override
	public List<TestrayCaseResult> findByTCI_TRI(
		long testrayComponentId, long testrayRunId, int start, int end) {

		return findByTCI_TRI(
			testrayComponentId, testrayRunId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray case results where testrayComponentId = &#63; and testrayRunId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultModelImpl</code>.
	 * </p>
	 *
	 * @param testrayComponentId the testray component ID
	 * @param testrayRunId the testray run ID
	 * @param start the lower bound of the range of testray case results
	 * @param end the upper bound of the range of testray case results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching testray case results
	 */
	@Override
	public List<TestrayCaseResult> findByTCI_TRI(
		long testrayComponentId, long testrayRunId, int start, int end,
		OrderByComparator<TestrayCaseResult> orderByComparator) {

		return findByTCI_TRI(
			testrayComponentId, testrayRunId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the testray case results where testrayComponentId = &#63; and testrayRunId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultModelImpl</code>.
	 * </p>
	 *
	 * @param testrayComponentId the testray component ID
	 * @param testrayRunId the testray run ID
	 * @param start the lower bound of the range of testray case results
	 * @param end the upper bound of the range of testray case results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching testray case results
	 */
	@Override
	public List<TestrayCaseResult> findByTCI_TRI(
		long testrayComponentId, long testrayRunId, int start, int end,
		OrderByComparator<TestrayCaseResult> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByTCI_TRI;
				finderArgs = new Object[] {testrayComponentId, testrayRunId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByTCI_TRI;
			finderArgs = new Object[] {
				testrayComponentId, testrayRunId, start, end, orderByComparator
			};
		}

		List<TestrayCaseResult> list = null;

		if (useFinderCache) {
			list = (List<TestrayCaseResult>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (TestrayCaseResult testrayCaseResult : list) {
					if ((testrayComponentId !=
							testrayCaseResult.getTestrayComponentId()) ||
						(testrayRunId != testrayCaseResult.getTestrayRunId())) {

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

			sb.append(_SQL_SELECT_TESTRAYCASERESULT_WHERE);

			sb.append(_FINDER_COLUMN_TCI_TRI_TESTRAYCOMPONENTID_2);

			sb.append(_FINDER_COLUMN_TCI_TRI_TESTRAYRUNID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(TestrayCaseResultModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(testrayComponentId);

				queryPos.add(testrayRunId);

				list = (List<TestrayCaseResult>)QueryUtil.list(
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
	 * Returns the first testray case result in the ordered set where testrayComponentId = &#63; and testrayRunId = &#63;.
	 *
	 * @param testrayComponentId the testray component ID
	 * @param testrayRunId the testray run ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching testray case result
	 * @throws NoSuchTestrayCaseResultException if a matching testray case result could not be found
	 */
	@Override
	public TestrayCaseResult findByTCI_TRI_First(
			long testrayComponentId, long testrayRunId,
			OrderByComparator<TestrayCaseResult> orderByComparator)
		throws NoSuchTestrayCaseResultException {

		TestrayCaseResult testrayCaseResult = fetchByTCI_TRI_First(
			testrayComponentId, testrayRunId, orderByComparator);

		if (testrayCaseResult != null) {
			return testrayCaseResult;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("testrayComponentId=");
		sb.append(testrayComponentId);

		sb.append(", testrayRunId=");
		sb.append(testrayRunId);

		sb.append("}");

		throw new NoSuchTestrayCaseResultException(sb.toString());
	}

	/**
	 * Returns the first testray case result in the ordered set where testrayComponentId = &#63; and testrayRunId = &#63;.
	 *
	 * @param testrayComponentId the testray component ID
	 * @param testrayRunId the testray run ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching testray case result, or <code>null</code> if a matching testray case result could not be found
	 */
	@Override
	public TestrayCaseResult fetchByTCI_TRI_First(
		long testrayComponentId, long testrayRunId,
		OrderByComparator<TestrayCaseResult> orderByComparator) {

		List<TestrayCaseResult> list = findByTCI_TRI(
			testrayComponentId, testrayRunId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last testray case result in the ordered set where testrayComponentId = &#63; and testrayRunId = &#63;.
	 *
	 * @param testrayComponentId the testray component ID
	 * @param testrayRunId the testray run ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching testray case result
	 * @throws NoSuchTestrayCaseResultException if a matching testray case result could not be found
	 */
	@Override
	public TestrayCaseResult findByTCI_TRI_Last(
			long testrayComponentId, long testrayRunId,
			OrderByComparator<TestrayCaseResult> orderByComparator)
		throws NoSuchTestrayCaseResultException {

		TestrayCaseResult testrayCaseResult = fetchByTCI_TRI_Last(
			testrayComponentId, testrayRunId, orderByComparator);

		if (testrayCaseResult != null) {
			return testrayCaseResult;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("testrayComponentId=");
		sb.append(testrayComponentId);

		sb.append(", testrayRunId=");
		sb.append(testrayRunId);

		sb.append("}");

		throw new NoSuchTestrayCaseResultException(sb.toString());
	}

	/**
	 * Returns the last testray case result in the ordered set where testrayComponentId = &#63; and testrayRunId = &#63;.
	 *
	 * @param testrayComponentId the testray component ID
	 * @param testrayRunId the testray run ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching testray case result, or <code>null</code> if a matching testray case result could not be found
	 */
	@Override
	public TestrayCaseResult fetchByTCI_TRI_Last(
		long testrayComponentId, long testrayRunId,
		OrderByComparator<TestrayCaseResult> orderByComparator) {

		int count = countByTCI_TRI(testrayComponentId, testrayRunId);

		if (count == 0) {
			return null;
		}

		List<TestrayCaseResult> list = findByTCI_TRI(
			testrayComponentId, testrayRunId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the testray case results before and after the current testray case result in the ordered set where testrayComponentId = &#63; and testrayRunId = &#63;.
	 *
	 * @param testrayCaseResultId the primary key of the current testray case result
	 * @param testrayComponentId the testray component ID
	 * @param testrayRunId the testray run ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next testray case result
	 * @throws NoSuchTestrayCaseResultException if a testray case result with the primary key could not be found
	 */
	@Override
	public TestrayCaseResult[] findByTCI_TRI_PrevAndNext(
			long testrayCaseResultId, long testrayComponentId,
			long testrayRunId,
			OrderByComparator<TestrayCaseResult> orderByComparator)
		throws NoSuchTestrayCaseResultException {

		TestrayCaseResult testrayCaseResult = findByPrimaryKey(
			testrayCaseResultId);

		Session session = null;

		try {
			session = openSession();

			TestrayCaseResult[] array = new TestrayCaseResultImpl[3];

			array[0] = getByTCI_TRI_PrevAndNext(
				session, testrayCaseResult, testrayComponentId, testrayRunId,
				orderByComparator, true);

			array[1] = testrayCaseResult;

			array[2] = getByTCI_TRI_PrevAndNext(
				session, testrayCaseResult, testrayComponentId, testrayRunId,
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

	protected TestrayCaseResult getByTCI_TRI_PrevAndNext(
		Session session, TestrayCaseResult testrayCaseResult,
		long testrayComponentId, long testrayRunId,
		OrderByComparator<TestrayCaseResult> orderByComparator,
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

		sb.append(_SQL_SELECT_TESTRAYCASERESULT_WHERE);

		sb.append(_FINDER_COLUMN_TCI_TRI_TESTRAYCOMPONENTID_2);

		sb.append(_FINDER_COLUMN_TCI_TRI_TESTRAYRUNID_2);

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
			sb.append(TestrayCaseResultModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(testrayComponentId);

		queryPos.add(testrayRunId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						testrayCaseResult)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<TestrayCaseResult> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the testray case results where testrayComponentId = &#63; and testrayRunId = &#63; from the database.
	 *
	 * @param testrayComponentId the testray component ID
	 * @param testrayRunId the testray run ID
	 */
	@Override
	public void removeByTCI_TRI(long testrayComponentId, long testrayRunId) {
		for (TestrayCaseResult testrayCaseResult :
				findByTCI_TRI(
					testrayComponentId, testrayRunId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(testrayCaseResult);
		}
	}

	/**
	 * Returns the number of testray case results where testrayComponentId = &#63; and testrayRunId = &#63;.
	 *
	 * @param testrayComponentId the testray component ID
	 * @param testrayRunId the testray run ID
	 * @return the number of matching testray case results
	 */
	@Override
	public int countByTCI_TRI(long testrayComponentId, long testrayRunId) {
		FinderPath finderPath = _finderPathCountByTCI_TRI;

		Object[] finderArgs = new Object[] {testrayComponentId, testrayRunId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_TESTRAYCASERESULT_WHERE);

			sb.append(_FINDER_COLUMN_TCI_TRI_TESTRAYCOMPONENTID_2);

			sb.append(_FINDER_COLUMN_TCI_TRI_TESTRAYRUNID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(testrayComponentId);

				queryPos.add(testrayRunId);

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

	private static final String _FINDER_COLUMN_TCI_TRI_TESTRAYCOMPONENTID_2 =
		"testrayCaseResult.testrayComponentId = ? AND ";

	private static final String _FINDER_COLUMN_TCI_TRI_TESTRAYRUNID_2 =
		"testrayCaseResult.testrayRunId = ?";

	public TestrayCaseResultPersistenceImpl() {
		setModelClass(TestrayCaseResult.class);
	}

	/**
	 * Caches the testray case result in the entity cache if it is enabled.
	 *
	 * @param testrayCaseResult the testray case result
	 */
	@Override
	public void cacheResult(TestrayCaseResult testrayCaseResult) {
		entityCache.putResult(
			TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultImpl.class, testrayCaseResult.getPrimaryKey(),
			testrayCaseResult);

		finderCache.putResult(
			_finderPathFetchByTCaI_TRI,
			new Object[] {
				testrayCaseResult.getTestrayCaseId(),
				testrayCaseResult.getTestrayRunId()
			},
			testrayCaseResult);

		testrayCaseResult.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the testray case results in the entity cache if it is enabled.
	 *
	 * @param testrayCaseResults the testray case results
	 */
	@Override
	public void cacheResult(List<TestrayCaseResult> testrayCaseResults) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (testrayCaseResults.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (TestrayCaseResult testrayCaseResult : testrayCaseResults) {
			if (entityCache.getResult(
					TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
					TestrayCaseResultImpl.class,
					testrayCaseResult.getPrimaryKey()) == null) {

				cacheResult(testrayCaseResult);
			}
			else {
				testrayCaseResult.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all testray case results.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(TestrayCaseResultImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the testray case result.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(TestrayCaseResult testrayCaseResult) {
		entityCache.removeResult(
			TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultImpl.class, testrayCaseResult.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(TestrayCaseResultModelImpl)testrayCaseResult, true);
	}

	@Override
	public void clearCache(List<TestrayCaseResult> testrayCaseResults) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (TestrayCaseResult testrayCaseResult : testrayCaseResults) {
			entityCache.removeResult(
				TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
				TestrayCaseResultImpl.class, testrayCaseResult.getPrimaryKey());

			clearUniqueFindersCache(
				(TestrayCaseResultModelImpl)testrayCaseResult, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
				TestrayCaseResultImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		TestrayCaseResultModelImpl testrayCaseResultModelImpl) {

		Object[] args = new Object[] {
			testrayCaseResultModelImpl.getTestrayCaseId(),
			testrayCaseResultModelImpl.getTestrayRunId()
		};

		finderCache.putResult(
			_finderPathCountByTCaI_TRI, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByTCaI_TRI, args, testrayCaseResultModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		TestrayCaseResultModelImpl testrayCaseResultModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				testrayCaseResultModelImpl.getTestrayCaseId(),
				testrayCaseResultModelImpl.getTestrayRunId()
			};

			finderCache.removeResult(_finderPathCountByTCaI_TRI, args);
			finderCache.removeResult(_finderPathFetchByTCaI_TRI, args);
		}

		if ((testrayCaseResultModelImpl.getColumnBitmask() &
			 _finderPathFetchByTCaI_TRI.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				testrayCaseResultModelImpl.getOriginalTestrayCaseId(),
				testrayCaseResultModelImpl.getOriginalTestrayRunId()
			};

			finderCache.removeResult(_finderPathCountByTCaI_TRI, args);
			finderCache.removeResult(_finderPathFetchByTCaI_TRI, args);
		}
	}

	/**
	 * Creates a new testray case result with the primary key. Does not add the testray case result to the database.
	 *
	 * @param testrayCaseResultId the primary key for the new testray case result
	 * @return the new testray case result
	 */
	@Override
	public TestrayCaseResult create(long testrayCaseResultId) {
		TestrayCaseResult testrayCaseResult = new TestrayCaseResultImpl();

		testrayCaseResult.setNew(true);
		testrayCaseResult.setPrimaryKey(testrayCaseResultId);

		testrayCaseResult.setCompanyId(CompanyThreadLocal.getCompanyId());

		return testrayCaseResult;
	}

	/**
	 * Removes the testray case result with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param testrayCaseResultId the primary key of the testray case result
	 * @return the testray case result that was removed
	 * @throws NoSuchTestrayCaseResultException if a testray case result with the primary key could not be found
	 */
	@Override
	public TestrayCaseResult remove(long testrayCaseResultId)
		throws NoSuchTestrayCaseResultException {

		return remove((Serializable)testrayCaseResultId);
	}

	/**
	 * Removes the testray case result with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the testray case result
	 * @return the testray case result that was removed
	 * @throws NoSuchTestrayCaseResultException if a testray case result with the primary key could not be found
	 */
	@Override
	public TestrayCaseResult remove(Serializable primaryKey)
		throws NoSuchTestrayCaseResultException {

		Session session = null;

		try {
			session = openSession();

			TestrayCaseResult testrayCaseResult =
				(TestrayCaseResult)session.get(
					TestrayCaseResultImpl.class, primaryKey);

			if (testrayCaseResult == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTestrayCaseResultException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(testrayCaseResult);
		}
		catch (NoSuchTestrayCaseResultException noSuchEntityException) {
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
	protected TestrayCaseResult removeImpl(
		TestrayCaseResult testrayCaseResult) {

		testrayCaseResultToTestrayIssueTableMapper.
			deleteLeftPrimaryKeyTableMappings(
				testrayCaseResult.getPrimaryKey());

		testrayCaseResultToTestraySubtaskTableMapper.
			deleteLeftPrimaryKeyTableMappings(
				testrayCaseResult.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(testrayCaseResult)) {
				testrayCaseResult = (TestrayCaseResult)session.get(
					TestrayCaseResultImpl.class,
					testrayCaseResult.getPrimaryKeyObj());
			}

			if (testrayCaseResult != null) {
				session.delete(testrayCaseResult);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (testrayCaseResult != null) {
			clearCache(testrayCaseResult);
		}

		return testrayCaseResult;
	}

	@Override
	public TestrayCaseResult updateImpl(TestrayCaseResult testrayCaseResult) {
		boolean isNew = testrayCaseResult.isNew();

		if (!(testrayCaseResult instanceof TestrayCaseResultModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(testrayCaseResult.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					testrayCaseResult);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in testrayCaseResult proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom TestrayCaseResult implementation " +
					testrayCaseResult.getClass());
		}

		TestrayCaseResultModelImpl testrayCaseResultModelImpl =
			(TestrayCaseResultModelImpl)testrayCaseResult;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (testrayCaseResult.getCreateDate() == null)) {
			if (serviceContext == null) {
				testrayCaseResult.setCreateDate(date);
			}
			else {
				testrayCaseResult.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!testrayCaseResultModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				testrayCaseResult.setModifiedDate(date);
			}
			else {
				testrayCaseResult.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(testrayCaseResult);

				testrayCaseResult.setNew(false);
			}
			else {
				testrayCaseResult = (TestrayCaseResult)session.merge(
					testrayCaseResult);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!TestrayCaseResultModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				testrayCaseResultModelImpl.getCreateDate()
			};

			finderCache.removeResult(_finderPathCountByCreateDate, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCreateDate, args);

			args = new Object[] {
				testrayCaseResultModelImpl.getTestrayBuildId()
			};

			finderCache.removeResult(_finderPathCountByTestrayBuildId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByTestrayBuildId, args);

			args = new Object[] {testrayCaseResultModelImpl.getTestrayCaseId()};

			finderCache.removeResult(_finderPathCountByTestrayCaseId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByTestrayCaseId, args);

			args = new Object[] {
				testrayCaseResultModelImpl.getTestrayComponentId()
			};

			finderCache.removeResult(
				_finderPathCountByTestrayComponentId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByTestrayComponentId, args);

			args = new Object[] {testrayCaseResultModelImpl.getTestrayRunId()};

			finderCache.removeResult(_finderPathCountByTestrayRunId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByTestrayRunId, args);

			args = new Object[] {
				testrayCaseResultModelImpl.getTestrayComponentId(),
				testrayCaseResultModelImpl.getTestrayRunId()
			};

			finderCache.removeResult(_finderPathCountByTCI_TRI, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByTCI_TRI, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((testrayCaseResultModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCreateDate.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					testrayCaseResultModelImpl.getOriginalCreateDate()
				};

				finderCache.removeResult(_finderPathCountByCreateDate, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCreateDate, args);

				args = new Object[] {
					testrayCaseResultModelImpl.getCreateDate()
				};

				finderCache.removeResult(_finderPathCountByCreateDate, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCreateDate, args);
			}

			if ((testrayCaseResultModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByTestrayBuildId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					testrayCaseResultModelImpl.getOriginalTestrayBuildId()
				};

				finderCache.removeResult(
					_finderPathCountByTestrayBuildId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByTestrayBuildId, args);

				args = new Object[] {
					testrayCaseResultModelImpl.getTestrayBuildId()
				};

				finderCache.removeResult(
					_finderPathCountByTestrayBuildId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByTestrayBuildId, args);
			}

			if ((testrayCaseResultModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByTestrayCaseId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					testrayCaseResultModelImpl.getOriginalTestrayCaseId()
				};

				finderCache.removeResult(_finderPathCountByTestrayCaseId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByTestrayCaseId, args);

				args = new Object[] {
					testrayCaseResultModelImpl.getTestrayCaseId()
				};

				finderCache.removeResult(_finderPathCountByTestrayCaseId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByTestrayCaseId, args);
			}

			if ((testrayCaseResultModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByTestrayComponentId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					testrayCaseResultModelImpl.getOriginalTestrayComponentId()
				};

				finderCache.removeResult(
					_finderPathCountByTestrayComponentId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByTestrayComponentId, args);

				args = new Object[] {
					testrayCaseResultModelImpl.getTestrayComponentId()
				};

				finderCache.removeResult(
					_finderPathCountByTestrayComponentId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByTestrayComponentId, args);
			}

			if ((testrayCaseResultModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByTestrayRunId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					testrayCaseResultModelImpl.getOriginalTestrayRunId()
				};

				finderCache.removeResult(_finderPathCountByTestrayRunId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByTestrayRunId, args);

				args = new Object[] {
					testrayCaseResultModelImpl.getTestrayRunId()
				};

				finderCache.removeResult(_finderPathCountByTestrayRunId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByTestrayRunId, args);
			}

			if ((testrayCaseResultModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByTCI_TRI.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					testrayCaseResultModelImpl.getOriginalTestrayComponentId(),
					testrayCaseResultModelImpl.getOriginalTestrayRunId()
				};

				finderCache.removeResult(_finderPathCountByTCI_TRI, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByTCI_TRI, args);

				args = new Object[] {
					testrayCaseResultModelImpl.getTestrayComponentId(),
					testrayCaseResultModelImpl.getTestrayRunId()
				};

				finderCache.removeResult(_finderPathCountByTCI_TRI, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByTCI_TRI, args);
			}
		}

		entityCache.putResult(
			TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultImpl.class, testrayCaseResult.getPrimaryKey(),
			testrayCaseResult, false);

		clearUniqueFindersCache(testrayCaseResultModelImpl, false);
		cacheUniqueFindersCache(testrayCaseResultModelImpl);

		testrayCaseResult.resetOriginalValues();

		return testrayCaseResult;
	}

	/**
	 * Returns the testray case result with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray case result
	 * @return the testray case result
	 * @throws NoSuchTestrayCaseResultException if a testray case result with the primary key could not be found
	 */
	@Override
	public TestrayCaseResult findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTestrayCaseResultException {

		TestrayCaseResult testrayCaseResult = fetchByPrimaryKey(primaryKey);

		if (testrayCaseResult == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTestrayCaseResultException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return testrayCaseResult;
	}

	/**
	 * Returns the testray case result with the primary key or throws a <code>NoSuchTestrayCaseResultException</code> if it could not be found.
	 *
	 * @param testrayCaseResultId the primary key of the testray case result
	 * @return the testray case result
	 * @throws NoSuchTestrayCaseResultException if a testray case result with the primary key could not be found
	 */
	@Override
	public TestrayCaseResult findByPrimaryKey(long testrayCaseResultId)
		throws NoSuchTestrayCaseResultException {

		return findByPrimaryKey((Serializable)testrayCaseResultId);
	}

	/**
	 * Returns the testray case result with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray case result
	 * @return the testray case result, or <code>null</code> if a testray case result with the primary key could not be found
	 */
	@Override
	public TestrayCaseResult fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		TestrayCaseResult testrayCaseResult = (TestrayCaseResult)serializable;

		if (testrayCaseResult == null) {
			Session session = null;

			try {
				session = openSession();

				testrayCaseResult = (TestrayCaseResult)session.get(
					TestrayCaseResultImpl.class, primaryKey);

				if (testrayCaseResult != null) {
					cacheResult(testrayCaseResult);
				}
				else {
					entityCache.putResult(
						TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
						TestrayCaseResultImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
					TestrayCaseResultImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return testrayCaseResult;
	}

	/**
	 * Returns the testray case result with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param testrayCaseResultId the primary key of the testray case result
	 * @return the testray case result, or <code>null</code> if a testray case result with the primary key could not be found
	 */
	@Override
	public TestrayCaseResult fetchByPrimaryKey(long testrayCaseResultId) {
		return fetchByPrimaryKey((Serializable)testrayCaseResultId);
	}

	@Override
	public Map<Serializable, TestrayCaseResult> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, TestrayCaseResult> map =
			new HashMap<Serializable, TestrayCaseResult>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			TestrayCaseResult testrayCaseResult = fetchByPrimaryKey(primaryKey);

			if (testrayCaseResult != null) {
				map.put(primaryKey, testrayCaseResult);
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
				TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
				TestrayCaseResultImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (TestrayCaseResult)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_TESTRAYCASERESULT_WHERE_PKS_IN);

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

			for (TestrayCaseResult testrayCaseResult :
					(List<TestrayCaseResult>)query.list()) {

				map.put(
					testrayCaseResult.getPrimaryKeyObj(), testrayCaseResult);

				cacheResult(testrayCaseResult);

				uncachedPrimaryKeys.remove(
					testrayCaseResult.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
					TestrayCaseResultImpl.class, primaryKey, nullModel);
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
	 * Returns all the testray case results.
	 *
	 * @return the testray case results
	 */
	@Override
	public List<TestrayCaseResult> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the testray case results.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray case results
	 * @param end the upper bound of the range of testray case results (not inclusive)
	 * @return the range of testray case results
	 */
	@Override
	public List<TestrayCaseResult> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray case results.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray case results
	 * @param end the upper bound of the range of testray case results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray case results
	 */
	@Override
	public List<TestrayCaseResult> findAll(
		int start, int end,
		OrderByComparator<TestrayCaseResult> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the testray case results.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray case results
	 * @param end the upper bound of the range of testray case results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of testray case results
	 */
	@Override
	public List<TestrayCaseResult> findAll(
		int start, int end,
		OrderByComparator<TestrayCaseResult> orderByComparator,
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

		List<TestrayCaseResult> list = null;

		if (useFinderCache) {
			list = (List<TestrayCaseResult>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_TESTRAYCASERESULT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_TESTRAYCASERESULT;

				sql = sql.concat(TestrayCaseResultModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<TestrayCaseResult>)QueryUtil.list(
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
	 * Removes all the testray case results from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (TestrayCaseResult testrayCaseResult : findAll()) {
			remove(testrayCaseResult);
		}
	}

	/**
	 * Returns the number of testray case results.
	 *
	 * @return the number of testray case results
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_TESTRAYCASERESULT);

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

	/**
	 * Returns the primaryKeys of testray issues associated with the testray case result.
	 *
	 * @param pk the primary key of the testray case result
	 * @return long[] of the primaryKeys of testray issues associated with the testray case result
	 */
	@Override
	public long[] getTestrayIssuePrimaryKeys(long pk) {
		long[] pks =
			testrayCaseResultToTestrayIssueTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the testray issues associated with the testray case result.
	 *
	 * @param pk the primary key of the testray case result
	 * @return the testray issues associated with the testray case result
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayIssue> getTestrayIssues(
		long pk) {

		return getTestrayIssues(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the testray issues associated with the testray case result.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray case result
	 * @param start the lower bound of the range of testray case results
	 * @param end the upper bound of the range of testray case results (not inclusive)
	 * @return the range of testray issues associated with the testray case result
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayIssue> getTestrayIssues(
		long pk, int start, int end) {

		return getTestrayIssues(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray issues associated with the testray case result.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray case result
	 * @param start the lower bound of the range of testray case results
	 * @param end the upper bound of the range of testray case results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray issues associated with the testray case result
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayIssue> getTestrayIssues(
		long pk, int start, int end,
		OrderByComparator<com.liferay.osb.testray.model.TestrayIssue>
			orderByComparator) {

		return testrayCaseResultToTestrayIssueTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of testray issues associated with the testray case result.
	 *
	 * @param pk the primary key of the testray case result
	 * @return the number of testray issues associated with the testray case result
	 */
	@Override
	public int getTestrayIssuesSize(long pk) {
		long[] pks =
			testrayCaseResultToTestrayIssueTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the testray issue is associated with the testray case result.
	 *
	 * @param pk the primary key of the testray case result
	 * @param testrayIssuePK the primary key of the testray issue
	 * @return <code>true</code> if the testray issue is associated with the testray case result; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTestrayIssue(long pk, long testrayIssuePK) {
		return testrayCaseResultToTestrayIssueTableMapper.containsTableMapping(
			pk, testrayIssuePK);
	}

	/**
	 * Returns <code>true</code> if the testray case result has any testray issues associated with it.
	 *
	 * @param pk the primary key of the testray case result to check for associations with testray issues
	 * @return <code>true</code> if the testray case result has any testray issues associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTestrayIssues(long pk) {
		if (getTestrayIssuesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the testray case result and the testray issue. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case result
	 * @param testrayIssuePK the primary key of the testray issue
	 */
	@Override
	public void addTestrayIssue(long pk, long testrayIssuePK) {
		TestrayCaseResult testrayCaseResult = fetchByPrimaryKey(pk);

		if (testrayCaseResult == null) {
			testrayCaseResultToTestrayIssueTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, testrayIssuePK);
		}
		else {
			testrayCaseResultToTestrayIssueTableMapper.addTableMapping(
				testrayCaseResult.getCompanyId(), pk, testrayIssuePK);
		}
	}

	/**
	 * Adds an association between the testray case result and the testray issue. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case result
	 * @param testrayIssue the testray issue
	 */
	@Override
	public void addTestrayIssue(
		long pk, com.liferay.osb.testray.model.TestrayIssue testrayIssue) {

		TestrayCaseResult testrayCaseResult = fetchByPrimaryKey(pk);

		if (testrayCaseResult == null) {
			testrayCaseResultToTestrayIssueTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				testrayIssue.getPrimaryKey());
		}
		else {
			testrayCaseResultToTestrayIssueTableMapper.addTableMapping(
				testrayCaseResult.getCompanyId(), pk,
				testrayIssue.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the testray case result and the testray issues. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case result
	 * @param testrayIssuePKs the primary keys of the testray issues
	 */
	@Override
	public void addTestrayIssues(long pk, long[] testrayIssuePKs) {
		long companyId = 0;

		TestrayCaseResult testrayCaseResult = fetchByPrimaryKey(pk);

		if (testrayCaseResult == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testrayCaseResult.getCompanyId();
		}

		testrayCaseResultToTestrayIssueTableMapper.addTableMappings(
			companyId, pk, testrayIssuePKs);
	}

	/**
	 * Adds an association between the testray case result and the testray issues. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case result
	 * @param testrayIssues the testray issues
	 */
	@Override
	public void addTestrayIssues(
		long pk,
		List<com.liferay.osb.testray.model.TestrayIssue> testrayIssues) {

		addTestrayIssues(
			pk,
			ListUtil.toLongArray(
				testrayIssues,
				com.liferay.osb.testray.model.TestrayIssue.
					TESTRAY_ISSUE_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the testray case result and its testray issues. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case result to clear the associated testray issues from
	 */
	@Override
	public void clearTestrayIssues(long pk) {
		testrayCaseResultToTestrayIssueTableMapper.
			deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the testray case result and the testray issue. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case result
	 * @param testrayIssuePK the primary key of the testray issue
	 */
	@Override
	public void removeTestrayIssue(long pk, long testrayIssuePK) {
		testrayCaseResultToTestrayIssueTableMapper.deleteTableMapping(
			pk, testrayIssuePK);
	}

	/**
	 * Removes the association between the testray case result and the testray issue. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case result
	 * @param testrayIssue the testray issue
	 */
	@Override
	public void removeTestrayIssue(
		long pk, com.liferay.osb.testray.model.TestrayIssue testrayIssue) {

		testrayCaseResultToTestrayIssueTableMapper.deleteTableMapping(
			pk, testrayIssue.getPrimaryKey());
	}

	/**
	 * Removes the association between the testray case result and the testray issues. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case result
	 * @param testrayIssuePKs the primary keys of the testray issues
	 */
	@Override
	public void removeTestrayIssues(long pk, long[] testrayIssuePKs) {
		testrayCaseResultToTestrayIssueTableMapper.deleteTableMappings(
			pk, testrayIssuePKs);
	}

	/**
	 * Removes the association between the testray case result and the testray issues. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case result
	 * @param testrayIssues the testray issues
	 */
	@Override
	public void removeTestrayIssues(
		long pk,
		List<com.liferay.osb.testray.model.TestrayIssue> testrayIssues) {

		removeTestrayIssues(
			pk,
			ListUtil.toLongArray(
				testrayIssues,
				com.liferay.osb.testray.model.TestrayIssue.
					TESTRAY_ISSUE_ID_ACCESSOR));
	}

	/**
	 * Sets the testray issues associated with the testray case result, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case result
	 * @param testrayIssuePKs the primary keys of the testray issues to be associated with the testray case result
	 */
	@Override
	public void setTestrayIssues(long pk, long[] testrayIssuePKs) {
		Set<Long> newTestrayIssuePKsSet = SetUtil.fromArray(testrayIssuePKs);
		Set<Long> oldTestrayIssuePKsSet = SetUtil.fromArray(
			testrayCaseResultToTestrayIssueTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeTestrayIssuePKsSet = new HashSet<Long>(
			oldTestrayIssuePKsSet);

		removeTestrayIssuePKsSet.removeAll(newTestrayIssuePKsSet);

		testrayCaseResultToTestrayIssueTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeTestrayIssuePKsSet));

		newTestrayIssuePKsSet.removeAll(oldTestrayIssuePKsSet);

		long companyId = 0;

		TestrayCaseResult testrayCaseResult = fetchByPrimaryKey(pk);

		if (testrayCaseResult == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testrayCaseResult.getCompanyId();
		}

		testrayCaseResultToTestrayIssueTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newTestrayIssuePKsSet));
	}

	/**
	 * Sets the testray issues associated with the testray case result, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case result
	 * @param testrayIssues the testray issues to be associated with the testray case result
	 */
	@Override
	public void setTestrayIssues(
		long pk,
		List<com.liferay.osb.testray.model.TestrayIssue> testrayIssues) {

		try {
			long[] testrayIssuePKs = new long[testrayIssues.size()];

			for (int i = 0; i < testrayIssues.size(); i++) {
				com.liferay.osb.testray.model.TestrayIssue testrayIssue =
					testrayIssues.get(i);

				testrayIssuePKs[i] = testrayIssue.getPrimaryKey();
			}

			setTestrayIssues(pk, testrayIssuePKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	/**
	 * Returns the primaryKeys of testray subtasks associated with the testray case result.
	 *
	 * @param pk the primary key of the testray case result
	 * @return long[] of the primaryKeys of testray subtasks associated with the testray case result
	 */
	@Override
	public long[] getTestraySubtaskPrimaryKeys(long pk) {
		long[] pks =
			testrayCaseResultToTestraySubtaskTableMapper.getRightPrimaryKeys(
				pk);

		return pks.clone();
	}

	/**
	 * Returns all the testray subtasks associated with the testray case result.
	 *
	 * @param pk the primary key of the testray case result
	 * @return the testray subtasks associated with the testray case result
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestraySubtask>
		getTestraySubtasks(long pk) {

		return getTestraySubtasks(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the testray subtasks associated with the testray case result.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray case result
	 * @param start the lower bound of the range of testray case results
	 * @param end the upper bound of the range of testray case results (not inclusive)
	 * @return the range of testray subtasks associated with the testray case result
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestraySubtask>
		getTestraySubtasks(long pk, int start, int end) {

		return getTestraySubtasks(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray subtasks associated with the testray case result.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseResultModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray case result
	 * @param start the lower bound of the range of testray case results
	 * @param end the upper bound of the range of testray case results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray subtasks associated with the testray case result
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestraySubtask>
		getTestraySubtasks(
			long pk, int start, int end,
			OrderByComparator<com.liferay.osb.testray.model.TestraySubtask>
				orderByComparator) {

		return testrayCaseResultToTestraySubtaskTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of testray subtasks associated with the testray case result.
	 *
	 * @param pk the primary key of the testray case result
	 * @return the number of testray subtasks associated with the testray case result
	 */
	@Override
	public int getTestraySubtasksSize(long pk) {
		long[] pks =
			testrayCaseResultToTestraySubtaskTableMapper.getRightPrimaryKeys(
				pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the testray subtask is associated with the testray case result.
	 *
	 * @param pk the primary key of the testray case result
	 * @param testraySubtaskPK the primary key of the testray subtask
	 * @return <code>true</code> if the testray subtask is associated with the testray case result; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTestraySubtask(long pk, long testraySubtaskPK) {
		return testrayCaseResultToTestraySubtaskTableMapper.
			containsTableMapping(pk, testraySubtaskPK);
	}

	/**
	 * Returns <code>true</code> if the testray case result has any testray subtasks associated with it.
	 *
	 * @param pk the primary key of the testray case result to check for associations with testray subtasks
	 * @return <code>true</code> if the testray case result has any testray subtasks associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTestraySubtasks(long pk) {
		if (getTestraySubtasksSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the testray case result and the testray subtask. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case result
	 * @param testraySubtaskPK the primary key of the testray subtask
	 */
	@Override
	public void addTestraySubtask(long pk, long testraySubtaskPK) {
		TestrayCaseResult testrayCaseResult = fetchByPrimaryKey(pk);

		if (testrayCaseResult == null) {
			testrayCaseResultToTestraySubtaskTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, testraySubtaskPK);
		}
		else {
			testrayCaseResultToTestraySubtaskTableMapper.addTableMapping(
				testrayCaseResult.getCompanyId(), pk, testraySubtaskPK);
		}
	}

	/**
	 * Adds an association between the testray case result and the testray subtask. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case result
	 * @param testraySubtask the testray subtask
	 */
	@Override
	public void addTestraySubtask(
		long pk, com.liferay.osb.testray.model.TestraySubtask testraySubtask) {

		TestrayCaseResult testrayCaseResult = fetchByPrimaryKey(pk);

		if (testrayCaseResult == null) {
			testrayCaseResultToTestraySubtaskTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				testraySubtask.getPrimaryKey());
		}
		else {
			testrayCaseResultToTestraySubtaskTableMapper.addTableMapping(
				testrayCaseResult.getCompanyId(), pk,
				testraySubtask.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the testray case result and the testray subtasks. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case result
	 * @param testraySubtaskPKs the primary keys of the testray subtasks
	 */
	@Override
	public void addTestraySubtasks(long pk, long[] testraySubtaskPKs) {
		long companyId = 0;

		TestrayCaseResult testrayCaseResult = fetchByPrimaryKey(pk);

		if (testrayCaseResult == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testrayCaseResult.getCompanyId();
		}

		testrayCaseResultToTestraySubtaskTableMapper.addTableMappings(
			companyId, pk, testraySubtaskPKs);
	}

	/**
	 * Adds an association between the testray case result and the testray subtasks. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case result
	 * @param testraySubtasks the testray subtasks
	 */
	@Override
	public void addTestraySubtasks(
		long pk,
		List<com.liferay.osb.testray.model.TestraySubtask> testraySubtasks) {

		addTestraySubtasks(
			pk,
			ListUtil.toLongArray(
				testraySubtasks,
				com.liferay.osb.testray.model.TestraySubtask.
					TESTRAY_SUBTASK_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the testray case result and its testray subtasks. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case result to clear the associated testray subtasks from
	 */
	@Override
	public void clearTestraySubtasks(long pk) {
		testrayCaseResultToTestraySubtaskTableMapper.
			deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the testray case result and the testray subtask. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case result
	 * @param testraySubtaskPK the primary key of the testray subtask
	 */
	@Override
	public void removeTestraySubtask(long pk, long testraySubtaskPK) {
		testrayCaseResultToTestraySubtaskTableMapper.deleteTableMapping(
			pk, testraySubtaskPK);
	}

	/**
	 * Removes the association between the testray case result and the testray subtask. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case result
	 * @param testraySubtask the testray subtask
	 */
	@Override
	public void removeTestraySubtask(
		long pk, com.liferay.osb.testray.model.TestraySubtask testraySubtask) {

		testrayCaseResultToTestraySubtaskTableMapper.deleteTableMapping(
			pk, testraySubtask.getPrimaryKey());
	}

	/**
	 * Removes the association between the testray case result and the testray subtasks. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case result
	 * @param testraySubtaskPKs the primary keys of the testray subtasks
	 */
	@Override
	public void removeTestraySubtasks(long pk, long[] testraySubtaskPKs) {
		testrayCaseResultToTestraySubtaskTableMapper.deleteTableMappings(
			pk, testraySubtaskPKs);
	}

	/**
	 * Removes the association between the testray case result and the testray subtasks. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case result
	 * @param testraySubtasks the testray subtasks
	 */
	@Override
	public void removeTestraySubtasks(
		long pk,
		List<com.liferay.osb.testray.model.TestraySubtask> testraySubtasks) {

		removeTestraySubtasks(
			pk,
			ListUtil.toLongArray(
				testraySubtasks,
				com.liferay.osb.testray.model.TestraySubtask.
					TESTRAY_SUBTASK_ID_ACCESSOR));
	}

	/**
	 * Sets the testray subtasks associated with the testray case result, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case result
	 * @param testraySubtaskPKs the primary keys of the testray subtasks to be associated with the testray case result
	 */
	@Override
	public void setTestraySubtasks(long pk, long[] testraySubtaskPKs) {
		Set<Long> newTestraySubtaskPKsSet = SetUtil.fromArray(
			testraySubtaskPKs);
		Set<Long> oldTestraySubtaskPKsSet = SetUtil.fromArray(
			testrayCaseResultToTestraySubtaskTableMapper.getRightPrimaryKeys(
				pk));

		Set<Long> removeTestraySubtaskPKsSet = new HashSet<Long>(
			oldTestraySubtaskPKsSet);

		removeTestraySubtaskPKsSet.removeAll(newTestraySubtaskPKsSet);

		testrayCaseResultToTestraySubtaskTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeTestraySubtaskPKsSet));

		newTestraySubtaskPKsSet.removeAll(oldTestraySubtaskPKsSet);

		long companyId = 0;

		TestrayCaseResult testrayCaseResult = fetchByPrimaryKey(pk);

		if (testrayCaseResult == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testrayCaseResult.getCompanyId();
		}

		testrayCaseResultToTestraySubtaskTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newTestraySubtaskPKsSet));
	}

	/**
	 * Sets the testray subtasks associated with the testray case result, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case result
	 * @param testraySubtasks the testray subtasks to be associated with the testray case result
	 */
	@Override
	public void setTestraySubtasks(
		long pk,
		List<com.liferay.osb.testray.model.TestraySubtask> testraySubtasks) {

		try {
			long[] testraySubtaskPKs = new long[testraySubtasks.size()];

			for (int i = 0; i < testraySubtasks.size(); i++) {
				com.liferay.osb.testray.model.TestraySubtask testraySubtask =
					testraySubtasks.get(i);

				testraySubtaskPKs[i] = testraySubtask.getPrimaryKey();
			}

			setTestraySubtasks(pk, testraySubtaskPKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return TestrayCaseResultModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the testray case result persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		testrayCaseResultToTestrayIssueTableMapper =
			TableMapperFactory.getTableMapper(
				"OSB_TestrayCaseResults_TestrayIssues", "companyId",
				"testrayCaseResultId", "testrayIssueId", this,
				testrayIssuePersistence);

		testrayCaseResultToTestraySubtaskTableMapper =
			TableMapperFactory.getTableMapper(
				"OSB_TestraySubtasks_TestrayCaseResults", "companyId",
				"testrayCaseResultId", "testraySubtaskId", this,
				testraySubtaskPersistence);

		_finderPathWithPaginationFindAll = new FinderPath(
			TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultModelImpl.FINDER_CACHE_ENABLED,
			TestrayCaseResultImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultModelImpl.FINDER_CACHE_ENABLED,
			TestrayCaseResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByCreateDate = new FinderPath(
			TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultModelImpl.FINDER_CACHE_ENABLED,
			TestrayCaseResultImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCreateDate",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCreateDate = new FinderPath(
			TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultModelImpl.FINDER_CACHE_ENABLED,
			TestrayCaseResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCreateDate",
			new String[] {Date.class.getName()},
			TestrayCaseResultModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByCreateDate = new FinderPath(
			TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCreateDate",
			new String[] {Date.class.getName()});

		_finderPathWithPaginationFindByTestrayBuildId = new FinderPath(
			TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultModelImpl.FINDER_CACHE_ENABLED,
			TestrayCaseResultImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByTestrayBuildId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByTestrayBuildId = new FinderPath(
			TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultModelImpl.FINDER_CACHE_ENABLED,
			TestrayCaseResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByTestrayBuildId",
			new String[] {Long.class.getName()},
			TestrayCaseResultModelImpl.TESTRAYBUILDID_COLUMN_BITMASK);

		_finderPathCountByTestrayBuildId = new FinderPath(
			TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTestrayBuildId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByTestrayCaseId = new FinderPath(
			TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultModelImpl.FINDER_CACHE_ENABLED,
			TestrayCaseResultImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByTestrayCaseId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByTestrayCaseId = new FinderPath(
			TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultModelImpl.FINDER_CACHE_ENABLED,
			TestrayCaseResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByTestrayCaseId",
			new String[] {Long.class.getName()},
			TestrayCaseResultModelImpl.TESTRAYCASEID_COLUMN_BITMASK);

		_finderPathCountByTestrayCaseId = new FinderPath(
			TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTestrayCaseId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByTestrayComponentId = new FinderPath(
			TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultModelImpl.FINDER_CACHE_ENABLED,
			TestrayCaseResultImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByTestrayComponentId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByTestrayComponentId = new FinderPath(
			TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultModelImpl.FINDER_CACHE_ENABLED,
			TestrayCaseResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByTestrayComponentId", new String[] {Long.class.getName()},
			TestrayCaseResultModelImpl.TESTRAYCOMPONENTID_COLUMN_BITMASK);

		_finderPathCountByTestrayComponentId = new FinderPath(
			TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByTestrayComponentId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByTestrayRunId = new FinderPath(
			TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultModelImpl.FINDER_CACHE_ENABLED,
			TestrayCaseResultImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByTestrayRunId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByTestrayRunId = new FinderPath(
			TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultModelImpl.FINDER_CACHE_ENABLED,
			TestrayCaseResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByTestrayRunId",
			new String[] {Long.class.getName()},
			TestrayCaseResultModelImpl.TESTRAYRUNID_COLUMN_BITMASK);

		_finderPathCountByTestrayRunId = new FinderPath(
			TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTestrayRunId",
			new String[] {Long.class.getName()});

		_finderPathFetchByTCaI_TRI = new FinderPath(
			TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultModelImpl.FINDER_CACHE_ENABLED,
			TestrayCaseResultImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByTCaI_TRI",
			new String[] {Long.class.getName(), Long.class.getName()},
			TestrayCaseResultModelImpl.TESTRAYCASEID_COLUMN_BITMASK |
			TestrayCaseResultModelImpl.TESTRAYRUNID_COLUMN_BITMASK);

		_finderPathCountByTCaI_TRI = new FinderPath(
			TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTCaI_TRI",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByTCI_TRI = new FinderPath(
			TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultModelImpl.FINDER_CACHE_ENABLED,
			TestrayCaseResultImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByTCI_TRI",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByTCI_TRI = new FinderPath(
			TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultModelImpl.FINDER_CACHE_ENABLED,
			TestrayCaseResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByTCI_TRI",
			new String[] {Long.class.getName(), Long.class.getName()},
			TestrayCaseResultModelImpl.TESTRAYCOMPONENTID_COLUMN_BITMASK |
			TestrayCaseResultModelImpl.TESTRAYRUNID_COLUMN_BITMASK);

		_finderPathCountByTCI_TRI = new FinderPath(
			TestrayCaseResultModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTCI_TRI",
			new String[] {Long.class.getName(), Long.class.getName()});

		TestrayCaseResultUtil.setPersistence(this);
	}

	public void destroy() {
		TestrayCaseResultUtil.setPersistence(null);

		entityCache.removeCache(TestrayCaseResultImpl.class.getName());

		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper(
			"OSB_TestrayCaseResults_TestrayIssues");
		TableMapperFactory.removeTableMapper(
			"OSB_TestraySubtasks_TestrayCaseResults");
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

	@BeanReference(type = TestrayIssuePersistence.class)
	protected TestrayIssuePersistence testrayIssuePersistence;

	protected TableMapper
		<TestrayCaseResult, com.liferay.osb.testray.model.TestrayIssue>
			testrayCaseResultToTestrayIssueTableMapper;

	@BeanReference(type = TestraySubtaskPersistence.class)
	protected TestraySubtaskPersistence testraySubtaskPersistence;

	protected TableMapper
		<TestrayCaseResult, com.liferay.osb.testray.model.TestraySubtask>
			testrayCaseResultToTestraySubtaskTableMapper;

	private static Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_TESTRAYCASERESULT =
		"SELECT testrayCaseResult FROM TestrayCaseResult testrayCaseResult";

	private static final String _SQL_SELECT_TESTRAYCASERESULT_WHERE_PKS_IN =
		"SELECT testrayCaseResult FROM TestrayCaseResult testrayCaseResult WHERE testrayCaseResultId IN (";

	private static final String _SQL_SELECT_TESTRAYCASERESULT_WHERE =
		"SELECT testrayCaseResult FROM TestrayCaseResult testrayCaseResult WHERE ";

	private static final String _SQL_COUNT_TESTRAYCASERESULT =
		"SELECT COUNT(testrayCaseResult) FROM TestrayCaseResult testrayCaseResult";

	private static final String _SQL_COUNT_TESTRAYCASERESULT_WHERE =
		"SELECT COUNT(testrayCaseResult) FROM TestrayCaseResult testrayCaseResult WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "testrayCaseResult.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No TestrayCaseResult exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No TestrayCaseResult exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		TestrayCaseResultPersistenceImpl.class);

}