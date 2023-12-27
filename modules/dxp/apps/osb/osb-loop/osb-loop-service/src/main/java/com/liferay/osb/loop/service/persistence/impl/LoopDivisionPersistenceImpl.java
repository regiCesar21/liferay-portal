/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.loop.service.persistence.impl;

import com.liferay.osb.loop.exception.NoSuchLoopDivisionException;
import com.liferay.osb.loop.model.LoopDivision;
import com.liferay.osb.loop.model.impl.LoopDivisionImpl;
import com.liferay.osb.loop.model.impl.LoopDivisionModelImpl;
import com.liferay.osb.loop.service.persistence.LoopDivisionPersistence;
import com.liferay.osb.loop.service.persistence.LoopDivisionUtil;
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
import java.util.Set;

/**
 * The persistence implementation for the loop division service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ethan Bustad
 * @generated
 */
public class LoopDivisionPersistenceImpl
	extends BasePersistenceImpl<LoopDivision>
	implements LoopDivisionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LoopDivisionUtil</code> to access the loop division persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LoopDivisionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByOrganizationId;
	private FinderPath _finderPathCountByOrganizationId;

	/**
	 * Returns the loop division where organizationId = &#63; or throws a <code>NoSuchLoopDivisionException</code> if it could not be found.
	 *
	 * @param organizationId the organization ID
	 * @return the matching loop division
	 * @throws NoSuchLoopDivisionException if a matching loop division could not be found
	 */
	@Override
	public LoopDivision findByOrganizationId(long organizationId)
		throws NoSuchLoopDivisionException {

		LoopDivision loopDivision = fetchByOrganizationId(organizationId);

		if (loopDivision == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("organizationId=");
			sb.append(organizationId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchLoopDivisionException(sb.toString());
		}

		return loopDivision;
	}

	/**
	 * Returns the loop division where organizationId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param organizationId the organization ID
	 * @return the matching loop division, or <code>null</code> if a matching loop division could not be found
	 */
	@Override
	public LoopDivision fetchByOrganizationId(long organizationId) {
		return fetchByOrganizationId(organizationId, true);
	}

	/**
	 * Returns the loop division where organizationId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param organizationId the organization ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching loop division, or <code>null</code> if a matching loop division could not be found
	 */
	@Override
	public LoopDivision fetchByOrganizationId(
		long organizationId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {organizationId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByOrganizationId, finderArgs, this);
		}

		if (result instanceof LoopDivision) {
			LoopDivision loopDivision = (LoopDivision)result;

			if (organizationId != loopDivision.getOrganizationId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_LOOPDIVISION_WHERE);

			sb.append(_FINDER_COLUMN_ORGANIZATIONID_ORGANIZATIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(organizationId);

				List<LoopDivision> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByOrganizationId, finderArgs, list);
					}
				}
				else {
					LoopDivision loopDivision = list.get(0);

					result = loopDivision;

					cacheResult(loopDivision);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByOrganizationId, finderArgs);
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
			return (LoopDivision)result;
		}
	}

	/**
	 * Removes the loop division where organizationId = &#63; from the database.
	 *
	 * @param organizationId the organization ID
	 * @return the loop division that was removed
	 */
	@Override
	public LoopDivision removeByOrganizationId(long organizationId)
		throws NoSuchLoopDivisionException {

		LoopDivision loopDivision = findByOrganizationId(organizationId);

		return remove(loopDivision);
	}

	/**
	 * Returns the number of loop divisions where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @return the number of matching loop divisions
	 */
	@Override
	public int countByOrganizationId(long organizationId) {
		FinderPath finderPath = _finderPathCountByOrganizationId;

		Object[] finderArgs = new Object[] {organizationId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_LOOPDIVISION_WHERE);

			sb.append(_FINDER_COLUMN_ORGANIZATIONID_ORGANIZATIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(organizationId);

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

	private static final String _FINDER_COLUMN_ORGANIZATIONID_ORGANIZATIONID_2 =
		"loopDivision.organizationId = ?";

	private FinderPath _finderPathWithPaginationFindByCI_T;
	private FinderPath _finderPathWithoutPaginationFindByCI_T;
	private FinderPath _finderPathCountByCI_T;

	/**
	 * Returns all the loop divisions where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching loop divisions
	 */
	@Override
	public List<LoopDivision> findByCI_T(long companyId, int type) {
		return findByCI_T(
			companyId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the loop divisions where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopDivisionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of loop divisions
	 * @param end the upper bound of the range of loop divisions (not inclusive)
	 * @return the range of matching loop divisions
	 */
	@Override
	public List<LoopDivision> findByCI_T(
		long companyId, int type, int start, int end) {

		return findByCI_T(companyId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the loop divisions where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopDivisionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of loop divisions
	 * @param end the upper bound of the range of loop divisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching loop divisions
	 */
	@Override
	public List<LoopDivision> findByCI_T(
		long companyId, int type, int start, int end,
		OrderByComparator<LoopDivision> orderByComparator) {

		return findByCI_T(companyId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the loop divisions where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopDivisionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of loop divisions
	 * @param end the upper bound of the range of loop divisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching loop divisions
	 */
	@Override
	public List<LoopDivision> findByCI_T(
		long companyId, int type, int start, int end,
		OrderByComparator<LoopDivision> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCI_T;
				finderArgs = new Object[] {companyId, type};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCI_T;
			finderArgs = new Object[] {
				companyId, type, start, end, orderByComparator
			};
		}

		List<LoopDivision> list = null;

		if (useFinderCache) {
			list = (List<LoopDivision>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LoopDivision loopDivision : list) {
					if ((companyId != loopDivision.getCompanyId()) ||
						(type != loopDivision.getType())) {

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

			sb.append(_SQL_SELECT_LOOPDIVISION_WHERE);

			sb.append(_FINDER_COLUMN_CI_T_COMPANYID_2);

			sb.append(_FINDER_COLUMN_CI_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LoopDivisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(type);

				list = (List<LoopDivision>)QueryUtil.list(
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
	 * Returns the first loop division in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching loop division
	 * @throws NoSuchLoopDivisionException if a matching loop division could not be found
	 */
	@Override
	public LoopDivision findByCI_T_First(
			long companyId, int type,
			OrderByComparator<LoopDivision> orderByComparator)
		throws NoSuchLoopDivisionException {

		LoopDivision loopDivision = fetchByCI_T_First(
			companyId, type, orderByComparator);

		if (loopDivision != null) {
			return loopDivision;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchLoopDivisionException(sb.toString());
	}

	/**
	 * Returns the first loop division in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching loop division, or <code>null</code> if a matching loop division could not be found
	 */
	@Override
	public LoopDivision fetchByCI_T_First(
		long companyId, int type,
		OrderByComparator<LoopDivision> orderByComparator) {

		List<LoopDivision> list = findByCI_T(
			companyId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last loop division in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching loop division
	 * @throws NoSuchLoopDivisionException if a matching loop division could not be found
	 */
	@Override
	public LoopDivision findByCI_T_Last(
			long companyId, int type,
			OrderByComparator<LoopDivision> orderByComparator)
		throws NoSuchLoopDivisionException {

		LoopDivision loopDivision = fetchByCI_T_Last(
			companyId, type, orderByComparator);

		if (loopDivision != null) {
			return loopDivision;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchLoopDivisionException(sb.toString());
	}

	/**
	 * Returns the last loop division in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching loop division, or <code>null</code> if a matching loop division could not be found
	 */
	@Override
	public LoopDivision fetchByCI_T_Last(
		long companyId, int type,
		OrderByComparator<LoopDivision> orderByComparator) {

		int count = countByCI_T(companyId, type);

		if (count == 0) {
			return null;
		}

		List<LoopDivision> list = findByCI_T(
			companyId, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the loop divisions before and after the current loop division in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param loopDivisionId the primary key of the current loop division
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next loop division
	 * @throws NoSuchLoopDivisionException if a loop division with the primary key could not be found
	 */
	@Override
	public LoopDivision[] findByCI_T_PrevAndNext(
			long loopDivisionId, long companyId, int type,
			OrderByComparator<LoopDivision> orderByComparator)
		throws NoSuchLoopDivisionException {

		LoopDivision loopDivision = findByPrimaryKey(loopDivisionId);

		Session session = null;

		try {
			session = openSession();

			LoopDivision[] array = new LoopDivisionImpl[3];

			array[0] = getByCI_T_PrevAndNext(
				session, loopDivision, companyId, type, orderByComparator,
				true);

			array[1] = loopDivision;

			array[2] = getByCI_T_PrevAndNext(
				session, loopDivision, companyId, type, orderByComparator,
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

	protected LoopDivision getByCI_T_PrevAndNext(
		Session session, LoopDivision loopDivision, long companyId, int type,
		OrderByComparator<LoopDivision> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_LOOPDIVISION_WHERE);

		sb.append(_FINDER_COLUMN_CI_T_COMPANYID_2);

		sb.append(_FINDER_COLUMN_CI_T_TYPE_2);

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
			sb.append(LoopDivisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(loopDivision)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LoopDivision> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the loop divisions where companyId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	@Override
	public void removeByCI_T(long companyId, int type) {
		for (LoopDivision loopDivision :
				findByCI_T(
					companyId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(loopDivision);
		}
	}

	/**
	 * Returns the number of loop divisions where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching loop divisions
	 */
	@Override
	public int countByCI_T(long companyId, int type) {
		FinderPath finderPath = _finderPathCountByCI_T;

		Object[] finderArgs = new Object[] {companyId, type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LOOPDIVISION_WHERE);

			sb.append(_FINDER_COLUMN_CI_T_COMPANYID_2);

			sb.append(_FINDER_COLUMN_CI_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

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

	private static final String _FINDER_COLUMN_CI_T_COMPANYID_2 =
		"loopDivision.companyId = ? AND ";

	private static final String _FINDER_COLUMN_CI_T_TYPE_2 =
		"loopDivision.type = ?";

	public LoopDivisionPersistenceImpl() {
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

		setModelClass(LoopDivision.class);
	}

	/**
	 * Caches the loop division in the entity cache if it is enabled.
	 *
	 * @param loopDivision the loop division
	 */
	@Override
	public void cacheResult(LoopDivision loopDivision) {
		entityCache.putResult(
			LoopDivisionModelImpl.ENTITY_CACHE_ENABLED, LoopDivisionImpl.class,
			loopDivision.getPrimaryKey(), loopDivision);

		finderCache.putResult(
			_finderPathFetchByOrganizationId,
			new Object[] {loopDivision.getOrganizationId()}, loopDivision);

		loopDivision.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the loop divisions in the entity cache if it is enabled.
	 *
	 * @param loopDivisions the loop divisions
	 */
	@Override
	public void cacheResult(List<LoopDivision> loopDivisions) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (loopDivisions.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (LoopDivision loopDivision : loopDivisions) {
			if (entityCache.getResult(
					LoopDivisionModelImpl.ENTITY_CACHE_ENABLED,
					LoopDivisionImpl.class, loopDivision.getPrimaryKey()) ==
						null) {

				cacheResult(loopDivision);
			}
			else {
				loopDivision.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all loop divisions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LoopDivisionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the loop division.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LoopDivision loopDivision) {
		entityCache.removeResult(
			LoopDivisionModelImpl.ENTITY_CACHE_ENABLED, LoopDivisionImpl.class,
			loopDivision.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((LoopDivisionModelImpl)loopDivision, true);
	}

	@Override
	public void clearCache(List<LoopDivision> loopDivisions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LoopDivision loopDivision : loopDivisions) {
			entityCache.removeResult(
				LoopDivisionModelImpl.ENTITY_CACHE_ENABLED,
				LoopDivisionImpl.class, loopDivision.getPrimaryKey());

			clearUniqueFindersCache((LoopDivisionModelImpl)loopDivision, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				LoopDivisionModelImpl.ENTITY_CACHE_ENABLED,
				LoopDivisionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		LoopDivisionModelImpl loopDivisionModelImpl) {

		Object[] args = new Object[] {
			loopDivisionModelImpl.getOrganizationId()
		};

		finderCache.putResult(
			_finderPathCountByOrganizationId, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByOrganizationId, args, loopDivisionModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		LoopDivisionModelImpl loopDivisionModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				loopDivisionModelImpl.getOrganizationId()
			};

			finderCache.removeResult(_finderPathCountByOrganizationId, args);
			finderCache.removeResult(_finderPathFetchByOrganizationId, args);
		}

		if ((loopDivisionModelImpl.getColumnBitmask() &
			 _finderPathFetchByOrganizationId.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				loopDivisionModelImpl.getOriginalOrganizationId()
			};

			finderCache.removeResult(_finderPathCountByOrganizationId, args);
			finderCache.removeResult(_finderPathFetchByOrganizationId, args);
		}
	}

	/**
	 * Creates a new loop division with the primary key. Does not add the loop division to the database.
	 *
	 * @param loopDivisionId the primary key for the new loop division
	 * @return the new loop division
	 */
	@Override
	public LoopDivision create(long loopDivisionId) {
		LoopDivision loopDivision = new LoopDivisionImpl();

		loopDivision.setNew(true);
		loopDivision.setPrimaryKey(loopDivisionId);

		loopDivision.setCompanyId(CompanyThreadLocal.getCompanyId());

		return loopDivision;
	}

	/**
	 * Removes the loop division with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param loopDivisionId the primary key of the loop division
	 * @return the loop division that was removed
	 * @throws NoSuchLoopDivisionException if a loop division with the primary key could not be found
	 */
	@Override
	public LoopDivision remove(long loopDivisionId)
		throws NoSuchLoopDivisionException {

		return remove((Serializable)loopDivisionId);
	}

	/**
	 * Removes the loop division with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the loop division
	 * @return the loop division that was removed
	 * @throws NoSuchLoopDivisionException if a loop division with the primary key could not be found
	 */
	@Override
	public LoopDivision remove(Serializable primaryKey)
		throws NoSuchLoopDivisionException {

		Session session = null;

		try {
			session = openSession();

			LoopDivision loopDivision = (LoopDivision)session.get(
				LoopDivisionImpl.class, primaryKey);

			if (loopDivision == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLoopDivisionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(loopDivision);
		}
		catch (NoSuchLoopDivisionException noSuchEntityException) {
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
	protected LoopDivision removeImpl(LoopDivision loopDivision) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(loopDivision)) {
				loopDivision = (LoopDivision)session.get(
					LoopDivisionImpl.class, loopDivision.getPrimaryKeyObj());
			}

			if (loopDivision != null) {
				session.delete(loopDivision);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (loopDivision != null) {
			clearCache(loopDivision);
		}

		return loopDivision;
	}

	@Override
	public LoopDivision updateImpl(LoopDivision loopDivision) {
		boolean isNew = loopDivision.isNew();

		if (!(loopDivision instanceof LoopDivisionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(loopDivision.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					loopDivision);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in loopDivision proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LoopDivision implementation " +
					loopDivision.getClass());
		}

		LoopDivisionModelImpl loopDivisionModelImpl =
			(LoopDivisionModelImpl)loopDivision;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (loopDivision.getCreateDate() == null)) {
			if (serviceContext == null) {
				loopDivision.setCreateDate(date);
			}
			else {
				loopDivision.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!loopDivisionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				loopDivision.setModifiedDate(date);
			}
			else {
				loopDivision.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(loopDivision);

				loopDivision.setNew(false);
			}
			else {
				loopDivision = (LoopDivision)session.merge(loopDivision);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LoopDivisionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				loopDivisionModelImpl.getCompanyId(),
				loopDivisionModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByCI_T, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCI_T, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((loopDivisionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCI_T.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					loopDivisionModelImpl.getOriginalCompanyId(),
					loopDivisionModelImpl.getOriginalType()
				};

				finderCache.removeResult(_finderPathCountByCI_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCI_T, args);

				args = new Object[] {
					loopDivisionModelImpl.getCompanyId(),
					loopDivisionModelImpl.getType()
				};

				finderCache.removeResult(_finderPathCountByCI_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCI_T, args);
			}
		}

		entityCache.putResult(
			LoopDivisionModelImpl.ENTITY_CACHE_ENABLED, LoopDivisionImpl.class,
			loopDivision.getPrimaryKey(), loopDivision, false);

		clearUniqueFindersCache(loopDivisionModelImpl, false);
		cacheUniqueFindersCache(loopDivisionModelImpl);

		loopDivision.resetOriginalValues();

		return loopDivision;
	}

	/**
	 * Returns the loop division with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the loop division
	 * @return the loop division
	 * @throws NoSuchLoopDivisionException if a loop division with the primary key could not be found
	 */
	@Override
	public LoopDivision findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLoopDivisionException {

		LoopDivision loopDivision = fetchByPrimaryKey(primaryKey);

		if (loopDivision == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLoopDivisionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return loopDivision;
	}

	/**
	 * Returns the loop division with the primary key or throws a <code>NoSuchLoopDivisionException</code> if it could not be found.
	 *
	 * @param loopDivisionId the primary key of the loop division
	 * @return the loop division
	 * @throws NoSuchLoopDivisionException if a loop division with the primary key could not be found
	 */
	@Override
	public LoopDivision findByPrimaryKey(long loopDivisionId)
		throws NoSuchLoopDivisionException {

		return findByPrimaryKey((Serializable)loopDivisionId);
	}

	/**
	 * Returns the loop division with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the loop division
	 * @return the loop division, or <code>null</code> if a loop division with the primary key could not be found
	 */
	@Override
	public LoopDivision fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			LoopDivisionModelImpl.ENTITY_CACHE_ENABLED, LoopDivisionImpl.class,
			primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		LoopDivision loopDivision = (LoopDivision)serializable;

		if (loopDivision == null) {
			Session session = null;

			try {
				session = openSession();

				loopDivision = (LoopDivision)session.get(
					LoopDivisionImpl.class, primaryKey);

				if (loopDivision != null) {
					cacheResult(loopDivision);
				}
				else {
					entityCache.putResult(
						LoopDivisionModelImpl.ENTITY_CACHE_ENABLED,
						LoopDivisionImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					LoopDivisionModelImpl.ENTITY_CACHE_ENABLED,
					LoopDivisionImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return loopDivision;
	}

	/**
	 * Returns the loop division with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param loopDivisionId the primary key of the loop division
	 * @return the loop division, or <code>null</code> if a loop division with the primary key could not be found
	 */
	@Override
	public LoopDivision fetchByPrimaryKey(long loopDivisionId) {
		return fetchByPrimaryKey((Serializable)loopDivisionId);
	}

	@Override
	public Map<Serializable, LoopDivision> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, LoopDivision> map =
			new HashMap<Serializable, LoopDivision>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			LoopDivision loopDivision = fetchByPrimaryKey(primaryKey);

			if (loopDivision != null) {
				map.put(primaryKey, loopDivision);
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
				LoopDivisionModelImpl.ENTITY_CACHE_ENABLED,
				LoopDivisionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (LoopDivision)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_LOOPDIVISION_WHERE_PKS_IN);

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

			for (LoopDivision loopDivision : (List<LoopDivision>)query.list()) {
				map.put(loopDivision.getPrimaryKeyObj(), loopDivision);

				cacheResult(loopDivision);

				uncachedPrimaryKeys.remove(loopDivision.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					LoopDivisionModelImpl.ENTITY_CACHE_ENABLED,
					LoopDivisionImpl.class, primaryKey, nullModel);
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
	 * Returns all the loop divisions.
	 *
	 * @return the loop divisions
	 */
	@Override
	public List<LoopDivision> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the loop divisions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopDivisionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop divisions
	 * @param end the upper bound of the range of loop divisions (not inclusive)
	 * @return the range of loop divisions
	 */
	@Override
	public List<LoopDivision> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the loop divisions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopDivisionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop divisions
	 * @param end the upper bound of the range of loop divisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of loop divisions
	 */
	@Override
	public List<LoopDivision> findAll(
		int start, int end, OrderByComparator<LoopDivision> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the loop divisions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopDivisionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop divisions
	 * @param end the upper bound of the range of loop divisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of loop divisions
	 */
	@Override
	public List<LoopDivision> findAll(
		int start, int end, OrderByComparator<LoopDivision> orderByComparator,
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

		List<LoopDivision> list = null;

		if (useFinderCache) {
			list = (List<LoopDivision>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_LOOPDIVISION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_LOOPDIVISION;

				sql = sql.concat(LoopDivisionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<LoopDivision>)QueryUtil.list(
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
	 * Removes all the loop divisions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LoopDivision loopDivision : findAll()) {
			remove(loopDivision);
		}
	}

	/**
	 * Returns the number of loop divisions.
	 *
	 * @return the number of loop divisions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_LOOPDIVISION);

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
		return LoopDivisionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the loop division persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			LoopDivisionModelImpl.ENTITY_CACHE_ENABLED,
			LoopDivisionModelImpl.FINDER_CACHE_ENABLED, LoopDivisionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			LoopDivisionModelImpl.ENTITY_CACHE_ENABLED,
			LoopDivisionModelImpl.FINDER_CACHE_ENABLED, LoopDivisionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			LoopDivisionModelImpl.ENTITY_CACHE_ENABLED,
			LoopDivisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByOrganizationId = new FinderPath(
			LoopDivisionModelImpl.ENTITY_CACHE_ENABLED,
			LoopDivisionModelImpl.FINDER_CACHE_ENABLED, LoopDivisionImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByOrganizationId",
			new String[] {Long.class.getName()},
			LoopDivisionModelImpl.ORGANIZATIONID_COLUMN_BITMASK);

		_finderPathCountByOrganizationId = new FinderPath(
			LoopDivisionModelImpl.ENTITY_CACHE_ENABLED,
			LoopDivisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByOrganizationId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByCI_T = new FinderPath(
			LoopDivisionModelImpl.ENTITY_CACHE_ENABLED,
			LoopDivisionModelImpl.FINDER_CACHE_ENABLED, LoopDivisionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCI_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCI_T = new FinderPath(
			LoopDivisionModelImpl.ENTITY_CACHE_ENABLED,
			LoopDivisionModelImpl.FINDER_CACHE_ENABLED, LoopDivisionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCI_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			LoopDivisionModelImpl.COMPANYID_COLUMN_BITMASK |
			LoopDivisionModelImpl.TYPE_COLUMN_BITMASK);

		_finderPathCountByCI_T = new FinderPath(
			LoopDivisionModelImpl.ENTITY_CACHE_ENABLED,
			LoopDivisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCI_T",
			new String[] {Long.class.getName(), Integer.class.getName()});

		LoopDivisionUtil.setPersistence(this);
	}

	public void destroy() {
		LoopDivisionUtil.setPersistence(null);

		entityCache.removeCache(LoopDivisionImpl.class.getName());

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

	private static final String _SQL_SELECT_LOOPDIVISION =
		"SELECT loopDivision FROM LoopDivision loopDivision";

	private static final String _SQL_SELECT_LOOPDIVISION_WHERE_PKS_IN =
		"SELECT loopDivision FROM LoopDivision loopDivision WHERE loopDivisionId IN (";

	private static final String _SQL_SELECT_LOOPDIVISION_WHERE =
		"SELECT loopDivision FROM LoopDivision loopDivision WHERE ";

	private static final String _SQL_COUNT_LOOPDIVISION =
		"SELECT COUNT(loopDivision) FROM LoopDivision loopDivision";

	private static final String _SQL_COUNT_LOOPDIVISION_WHERE =
		"SELECT COUNT(loopDivision) FROM LoopDivision loopDivision WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "loopDivision.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LoopDivision exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LoopDivision exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LoopDivisionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type"});

}