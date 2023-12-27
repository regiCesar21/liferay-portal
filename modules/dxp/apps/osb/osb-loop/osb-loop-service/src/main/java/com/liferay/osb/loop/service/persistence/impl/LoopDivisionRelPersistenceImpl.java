/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.loop.service.persistence.impl;

import com.liferay.osb.loop.exception.NoSuchLoopDivisionRelException;
import com.liferay.osb.loop.model.LoopDivisionRel;
import com.liferay.osb.loop.model.impl.LoopDivisionRelImpl;
import com.liferay.osb.loop.model.impl.LoopDivisionRelModelImpl;
import com.liferay.osb.loop.service.persistence.LoopDivisionRelPersistence;
import com.liferay.osb.loop.service.persistence.LoopDivisionRelUtil;
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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the loop division rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ethan Bustad
 * @generated
 */
public class LoopDivisionRelPersistenceImpl
	extends BasePersistenceImpl<LoopDivisionRel>
	implements LoopDivisionRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LoopDivisionRelUtil</code> to access the loop division rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LoopDivisionRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByCLDI_LPI;
	private FinderPath _finderPathCountByCLDI_LPI;

	/**
	 * Returns the loop division rel where childLoopDivisionId = &#63; and loopPersonId = &#63; or throws a <code>NoSuchLoopDivisionRelException</code> if it could not be found.
	 *
	 * @param childLoopDivisionId the child loop division ID
	 * @param loopPersonId the loop person ID
	 * @return the matching loop division rel
	 * @throws NoSuchLoopDivisionRelException if a matching loop division rel could not be found
	 */
	@Override
	public LoopDivisionRel findByCLDI_LPI(
			long childLoopDivisionId, long loopPersonId)
		throws NoSuchLoopDivisionRelException {

		LoopDivisionRel loopDivisionRel = fetchByCLDI_LPI(
			childLoopDivisionId, loopPersonId);

		if (loopDivisionRel == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("childLoopDivisionId=");
			sb.append(childLoopDivisionId);

			sb.append(", loopPersonId=");
			sb.append(loopPersonId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchLoopDivisionRelException(sb.toString());
		}

		return loopDivisionRel;
	}

	/**
	 * Returns the loop division rel where childLoopDivisionId = &#63; and loopPersonId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param childLoopDivisionId the child loop division ID
	 * @param loopPersonId the loop person ID
	 * @return the matching loop division rel, or <code>null</code> if a matching loop division rel could not be found
	 */
	@Override
	public LoopDivisionRel fetchByCLDI_LPI(
		long childLoopDivisionId, long loopPersonId) {

		return fetchByCLDI_LPI(childLoopDivisionId, loopPersonId, true);
	}

	/**
	 * Returns the loop division rel where childLoopDivisionId = &#63; and loopPersonId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param childLoopDivisionId the child loop division ID
	 * @param loopPersonId the loop person ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching loop division rel, or <code>null</code> if a matching loop division rel could not be found
	 */
	@Override
	public LoopDivisionRel fetchByCLDI_LPI(
		long childLoopDivisionId, long loopPersonId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {childLoopDivisionId, loopPersonId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByCLDI_LPI, finderArgs, this);
		}

		if (result instanceof LoopDivisionRel) {
			LoopDivisionRel loopDivisionRel = (LoopDivisionRel)result;

			if ((childLoopDivisionId !=
					loopDivisionRel.getChildLoopDivisionId()) ||
				(loopPersonId != loopDivisionRel.getLoopPersonId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_LOOPDIVISIONREL_WHERE);

			sb.append(_FINDER_COLUMN_CLDI_LPI_CHILDLOOPDIVISIONID_2);

			sb.append(_FINDER_COLUMN_CLDI_LPI_LOOPPERSONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(childLoopDivisionId);

				queryPos.add(loopPersonId);

				List<LoopDivisionRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByCLDI_LPI, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									childLoopDivisionId, loopPersonId
								};
							}

							_log.warn(
								"LoopDivisionRelPersistenceImpl.fetchByCLDI_LPI(long, long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					LoopDivisionRel loopDivisionRel = list.get(0);

					result = loopDivisionRel;

					cacheResult(loopDivisionRel);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByCLDI_LPI, finderArgs);
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
			return (LoopDivisionRel)result;
		}
	}

	/**
	 * Removes the loop division rel where childLoopDivisionId = &#63; and loopPersonId = &#63; from the database.
	 *
	 * @param childLoopDivisionId the child loop division ID
	 * @param loopPersonId the loop person ID
	 * @return the loop division rel that was removed
	 */
	@Override
	public LoopDivisionRel removeByCLDI_LPI(
			long childLoopDivisionId, long loopPersonId)
		throws NoSuchLoopDivisionRelException {

		LoopDivisionRel loopDivisionRel = findByCLDI_LPI(
			childLoopDivisionId, loopPersonId);

		return remove(loopDivisionRel);
	}

	/**
	 * Returns the number of loop division rels where childLoopDivisionId = &#63; and loopPersonId = &#63;.
	 *
	 * @param childLoopDivisionId the child loop division ID
	 * @param loopPersonId the loop person ID
	 * @return the number of matching loop division rels
	 */
	@Override
	public int countByCLDI_LPI(long childLoopDivisionId, long loopPersonId) {
		FinderPath finderPath = _finderPathCountByCLDI_LPI;

		Object[] finderArgs = new Object[] {childLoopDivisionId, loopPersonId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LOOPDIVISIONREL_WHERE);

			sb.append(_FINDER_COLUMN_CLDI_LPI_CHILDLOOPDIVISIONID_2);

			sb.append(_FINDER_COLUMN_CLDI_LPI_LOOPPERSONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(childLoopDivisionId);

				queryPos.add(loopPersonId);

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

	private static final String _FINDER_COLUMN_CLDI_LPI_CHILDLOOPDIVISIONID_2 =
		"loopDivisionRel.childLoopDivisionId = ? AND ";

	private static final String _FINDER_COLUMN_CLDI_LPI_LOOPPERSONID_2 =
		"loopDivisionRel.loopPersonId = ?";

	private FinderPath _finderPathFetchByLPI_PLDI;
	private FinderPath _finderPathCountByLPI_PLDI;

	/**
	 * Returns the loop division rel where loopPersonId = &#63; and parentLoopDivisionId = &#63; or throws a <code>NoSuchLoopDivisionRelException</code> if it could not be found.
	 *
	 * @param loopPersonId the loop person ID
	 * @param parentLoopDivisionId the parent loop division ID
	 * @return the matching loop division rel
	 * @throws NoSuchLoopDivisionRelException if a matching loop division rel could not be found
	 */
	@Override
	public LoopDivisionRel findByLPI_PLDI(
			long loopPersonId, long parentLoopDivisionId)
		throws NoSuchLoopDivisionRelException {

		LoopDivisionRel loopDivisionRel = fetchByLPI_PLDI(
			loopPersonId, parentLoopDivisionId);

		if (loopDivisionRel == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("loopPersonId=");
			sb.append(loopPersonId);

			sb.append(", parentLoopDivisionId=");
			sb.append(parentLoopDivisionId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchLoopDivisionRelException(sb.toString());
		}

		return loopDivisionRel;
	}

	/**
	 * Returns the loop division rel where loopPersonId = &#63; and parentLoopDivisionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param loopPersonId the loop person ID
	 * @param parentLoopDivisionId the parent loop division ID
	 * @return the matching loop division rel, or <code>null</code> if a matching loop division rel could not be found
	 */
	@Override
	public LoopDivisionRel fetchByLPI_PLDI(
		long loopPersonId, long parentLoopDivisionId) {

		return fetchByLPI_PLDI(loopPersonId, parentLoopDivisionId, true);
	}

	/**
	 * Returns the loop division rel where loopPersonId = &#63; and parentLoopDivisionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param loopPersonId the loop person ID
	 * @param parentLoopDivisionId the parent loop division ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching loop division rel, or <code>null</code> if a matching loop division rel could not be found
	 */
	@Override
	public LoopDivisionRel fetchByLPI_PLDI(
		long loopPersonId, long parentLoopDivisionId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {loopPersonId, parentLoopDivisionId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByLPI_PLDI, finderArgs, this);
		}

		if (result instanceof LoopDivisionRel) {
			LoopDivisionRel loopDivisionRel = (LoopDivisionRel)result;

			if ((loopPersonId != loopDivisionRel.getLoopPersonId()) ||
				(parentLoopDivisionId !=
					loopDivisionRel.getParentLoopDivisionId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_LOOPDIVISIONREL_WHERE);

			sb.append(_FINDER_COLUMN_LPI_PLDI_LOOPPERSONID_2);

			sb.append(_FINDER_COLUMN_LPI_PLDI_PARENTLOOPDIVISIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(loopPersonId);

				queryPos.add(parentLoopDivisionId);

				List<LoopDivisionRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByLPI_PLDI, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									loopPersonId, parentLoopDivisionId
								};
							}

							_log.warn(
								"LoopDivisionRelPersistenceImpl.fetchByLPI_PLDI(long, long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					LoopDivisionRel loopDivisionRel = list.get(0);

					result = loopDivisionRel;

					cacheResult(loopDivisionRel);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByLPI_PLDI, finderArgs);
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
			return (LoopDivisionRel)result;
		}
	}

	/**
	 * Removes the loop division rel where loopPersonId = &#63; and parentLoopDivisionId = &#63; from the database.
	 *
	 * @param loopPersonId the loop person ID
	 * @param parentLoopDivisionId the parent loop division ID
	 * @return the loop division rel that was removed
	 */
	@Override
	public LoopDivisionRel removeByLPI_PLDI(
			long loopPersonId, long parentLoopDivisionId)
		throws NoSuchLoopDivisionRelException {

		LoopDivisionRel loopDivisionRel = findByLPI_PLDI(
			loopPersonId, parentLoopDivisionId);

		return remove(loopDivisionRel);
	}

	/**
	 * Returns the number of loop division rels where loopPersonId = &#63; and parentLoopDivisionId = &#63;.
	 *
	 * @param loopPersonId the loop person ID
	 * @param parentLoopDivisionId the parent loop division ID
	 * @return the number of matching loop division rels
	 */
	@Override
	public int countByLPI_PLDI(long loopPersonId, long parentLoopDivisionId) {
		FinderPath finderPath = _finderPathCountByLPI_PLDI;

		Object[] finderArgs = new Object[] {loopPersonId, parentLoopDivisionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LOOPDIVISIONREL_WHERE);

			sb.append(_FINDER_COLUMN_LPI_PLDI_LOOPPERSONID_2);

			sb.append(_FINDER_COLUMN_LPI_PLDI_PARENTLOOPDIVISIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(loopPersonId);

				queryPos.add(parentLoopDivisionId);

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

	private static final String _FINDER_COLUMN_LPI_PLDI_LOOPPERSONID_2 =
		"loopDivisionRel.loopPersonId = ? AND ";

	private static final String _FINDER_COLUMN_LPI_PLDI_PARENTLOOPDIVISIONID_2 =
		"loopDivisionRel.parentLoopDivisionId = ?";

	private FinderPath _finderPathFetchByCLDI_LPI_PLDI;
	private FinderPath _finderPathCountByCLDI_LPI_PLDI;

	/**
	 * Returns the loop division rel where childLoopDivisionId = &#63; and loopPersonId = &#63; and parentLoopDivisionId = &#63; or throws a <code>NoSuchLoopDivisionRelException</code> if it could not be found.
	 *
	 * @param childLoopDivisionId the child loop division ID
	 * @param loopPersonId the loop person ID
	 * @param parentLoopDivisionId the parent loop division ID
	 * @return the matching loop division rel
	 * @throws NoSuchLoopDivisionRelException if a matching loop division rel could not be found
	 */
	@Override
	public LoopDivisionRel findByCLDI_LPI_PLDI(
			long childLoopDivisionId, long loopPersonId,
			long parentLoopDivisionId)
		throws NoSuchLoopDivisionRelException {

		LoopDivisionRel loopDivisionRel = fetchByCLDI_LPI_PLDI(
			childLoopDivisionId, loopPersonId, parentLoopDivisionId);

		if (loopDivisionRel == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("childLoopDivisionId=");
			sb.append(childLoopDivisionId);

			sb.append(", loopPersonId=");
			sb.append(loopPersonId);

			sb.append(", parentLoopDivisionId=");
			sb.append(parentLoopDivisionId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchLoopDivisionRelException(sb.toString());
		}

		return loopDivisionRel;
	}

	/**
	 * Returns the loop division rel where childLoopDivisionId = &#63; and loopPersonId = &#63; and parentLoopDivisionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param childLoopDivisionId the child loop division ID
	 * @param loopPersonId the loop person ID
	 * @param parentLoopDivisionId the parent loop division ID
	 * @return the matching loop division rel, or <code>null</code> if a matching loop division rel could not be found
	 */
	@Override
	public LoopDivisionRel fetchByCLDI_LPI_PLDI(
		long childLoopDivisionId, long loopPersonId,
		long parentLoopDivisionId) {

		return fetchByCLDI_LPI_PLDI(
			childLoopDivisionId, loopPersonId, parentLoopDivisionId, true);
	}

	/**
	 * Returns the loop division rel where childLoopDivisionId = &#63; and loopPersonId = &#63; and parentLoopDivisionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param childLoopDivisionId the child loop division ID
	 * @param loopPersonId the loop person ID
	 * @param parentLoopDivisionId the parent loop division ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching loop division rel, or <code>null</code> if a matching loop division rel could not be found
	 */
	@Override
	public LoopDivisionRel fetchByCLDI_LPI_PLDI(
		long childLoopDivisionId, long loopPersonId, long parentLoopDivisionId,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				childLoopDivisionId, loopPersonId, parentLoopDivisionId
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByCLDI_LPI_PLDI, finderArgs, this);
		}

		if (result instanceof LoopDivisionRel) {
			LoopDivisionRel loopDivisionRel = (LoopDivisionRel)result;

			if ((childLoopDivisionId !=
					loopDivisionRel.getChildLoopDivisionId()) ||
				(loopPersonId != loopDivisionRel.getLoopPersonId()) ||
				(parentLoopDivisionId !=
					loopDivisionRel.getParentLoopDivisionId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_LOOPDIVISIONREL_WHERE);

			sb.append(_FINDER_COLUMN_CLDI_LPI_PLDI_CHILDLOOPDIVISIONID_2);

			sb.append(_FINDER_COLUMN_CLDI_LPI_PLDI_LOOPPERSONID_2);

			sb.append(_FINDER_COLUMN_CLDI_LPI_PLDI_PARENTLOOPDIVISIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(childLoopDivisionId);

				queryPos.add(loopPersonId);

				queryPos.add(parentLoopDivisionId);

				List<LoopDivisionRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByCLDI_LPI_PLDI, finderArgs, list);
					}
				}
				else {
					LoopDivisionRel loopDivisionRel = list.get(0);

					result = loopDivisionRel;

					cacheResult(loopDivisionRel);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByCLDI_LPI_PLDI, finderArgs);
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
			return (LoopDivisionRel)result;
		}
	}

	/**
	 * Removes the loop division rel where childLoopDivisionId = &#63; and loopPersonId = &#63; and parentLoopDivisionId = &#63; from the database.
	 *
	 * @param childLoopDivisionId the child loop division ID
	 * @param loopPersonId the loop person ID
	 * @param parentLoopDivisionId the parent loop division ID
	 * @return the loop division rel that was removed
	 */
	@Override
	public LoopDivisionRel removeByCLDI_LPI_PLDI(
			long childLoopDivisionId, long loopPersonId,
			long parentLoopDivisionId)
		throws NoSuchLoopDivisionRelException {

		LoopDivisionRel loopDivisionRel = findByCLDI_LPI_PLDI(
			childLoopDivisionId, loopPersonId, parentLoopDivisionId);

		return remove(loopDivisionRel);
	}

	/**
	 * Returns the number of loop division rels where childLoopDivisionId = &#63; and loopPersonId = &#63; and parentLoopDivisionId = &#63;.
	 *
	 * @param childLoopDivisionId the child loop division ID
	 * @param loopPersonId the loop person ID
	 * @param parentLoopDivisionId the parent loop division ID
	 * @return the number of matching loop division rels
	 */
	@Override
	public int countByCLDI_LPI_PLDI(
		long childLoopDivisionId, long loopPersonId,
		long parentLoopDivisionId) {

		FinderPath finderPath = _finderPathCountByCLDI_LPI_PLDI;

		Object[] finderArgs = new Object[] {
			childLoopDivisionId, loopPersonId, parentLoopDivisionId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_LOOPDIVISIONREL_WHERE);

			sb.append(_FINDER_COLUMN_CLDI_LPI_PLDI_CHILDLOOPDIVISIONID_2);

			sb.append(_FINDER_COLUMN_CLDI_LPI_PLDI_LOOPPERSONID_2);

			sb.append(_FINDER_COLUMN_CLDI_LPI_PLDI_PARENTLOOPDIVISIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(childLoopDivisionId);

				queryPos.add(loopPersonId);

				queryPos.add(parentLoopDivisionId);

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
		_FINDER_COLUMN_CLDI_LPI_PLDI_CHILDLOOPDIVISIONID_2 =
			"loopDivisionRel.childLoopDivisionId = ? AND ";

	private static final String _FINDER_COLUMN_CLDI_LPI_PLDI_LOOPPERSONID_2 =
		"loopDivisionRel.loopPersonId = ? AND ";

	private static final String
		_FINDER_COLUMN_CLDI_LPI_PLDI_PARENTLOOPDIVISIONID_2 =
			"loopDivisionRel.parentLoopDivisionId = ?";

	public LoopDivisionRelPersistenceImpl() {
		setModelClass(LoopDivisionRel.class);
	}

	/**
	 * Caches the loop division rel in the entity cache if it is enabled.
	 *
	 * @param loopDivisionRel the loop division rel
	 */
	@Override
	public void cacheResult(LoopDivisionRel loopDivisionRel) {
		entityCache.putResult(
			LoopDivisionRelModelImpl.ENTITY_CACHE_ENABLED,
			LoopDivisionRelImpl.class, loopDivisionRel.getPrimaryKey(),
			loopDivisionRel);

		finderCache.putResult(
			_finderPathFetchByCLDI_LPI,
			new Object[] {
				loopDivisionRel.getChildLoopDivisionId(),
				loopDivisionRel.getLoopPersonId()
			},
			loopDivisionRel);

		finderCache.putResult(
			_finderPathFetchByLPI_PLDI,
			new Object[] {
				loopDivisionRel.getLoopPersonId(),
				loopDivisionRel.getParentLoopDivisionId()
			},
			loopDivisionRel);

		finderCache.putResult(
			_finderPathFetchByCLDI_LPI_PLDI,
			new Object[] {
				loopDivisionRel.getChildLoopDivisionId(),
				loopDivisionRel.getLoopPersonId(),
				loopDivisionRel.getParentLoopDivisionId()
			},
			loopDivisionRel);

		loopDivisionRel.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the loop division rels in the entity cache if it is enabled.
	 *
	 * @param loopDivisionRels the loop division rels
	 */
	@Override
	public void cacheResult(List<LoopDivisionRel> loopDivisionRels) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (loopDivisionRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (LoopDivisionRel loopDivisionRel : loopDivisionRels) {
			if (entityCache.getResult(
					LoopDivisionRelModelImpl.ENTITY_CACHE_ENABLED,
					LoopDivisionRelImpl.class,
					loopDivisionRel.getPrimaryKey()) == null) {

				cacheResult(loopDivisionRel);
			}
			else {
				loopDivisionRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all loop division rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LoopDivisionRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the loop division rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LoopDivisionRel loopDivisionRel) {
		entityCache.removeResult(
			LoopDivisionRelModelImpl.ENTITY_CACHE_ENABLED,
			LoopDivisionRelImpl.class, loopDivisionRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(LoopDivisionRelModelImpl)loopDivisionRel, true);
	}

	@Override
	public void clearCache(List<LoopDivisionRel> loopDivisionRels) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LoopDivisionRel loopDivisionRel : loopDivisionRels) {
			entityCache.removeResult(
				LoopDivisionRelModelImpl.ENTITY_CACHE_ENABLED,
				LoopDivisionRelImpl.class, loopDivisionRel.getPrimaryKey());

			clearUniqueFindersCache(
				(LoopDivisionRelModelImpl)loopDivisionRel, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				LoopDivisionRelModelImpl.ENTITY_CACHE_ENABLED,
				LoopDivisionRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		LoopDivisionRelModelImpl loopDivisionRelModelImpl) {

		Object[] args = new Object[] {
			loopDivisionRelModelImpl.getChildLoopDivisionId(),
			loopDivisionRelModelImpl.getLoopPersonId()
		};

		finderCache.putResult(
			_finderPathCountByCLDI_LPI, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByCLDI_LPI, args, loopDivisionRelModelImpl, false);

		args = new Object[] {
			loopDivisionRelModelImpl.getLoopPersonId(),
			loopDivisionRelModelImpl.getParentLoopDivisionId()
		};

		finderCache.putResult(
			_finderPathCountByLPI_PLDI, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByLPI_PLDI, args, loopDivisionRelModelImpl, false);

		args = new Object[] {
			loopDivisionRelModelImpl.getChildLoopDivisionId(),
			loopDivisionRelModelImpl.getLoopPersonId(),
			loopDivisionRelModelImpl.getParentLoopDivisionId()
		};

		finderCache.putResult(
			_finderPathCountByCLDI_LPI_PLDI, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByCLDI_LPI_PLDI, args, loopDivisionRelModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		LoopDivisionRelModelImpl loopDivisionRelModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				loopDivisionRelModelImpl.getChildLoopDivisionId(),
				loopDivisionRelModelImpl.getLoopPersonId()
			};

			finderCache.removeResult(_finderPathCountByCLDI_LPI, args);
			finderCache.removeResult(_finderPathFetchByCLDI_LPI, args);
		}

		if ((loopDivisionRelModelImpl.getColumnBitmask() &
			 _finderPathFetchByCLDI_LPI.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				loopDivisionRelModelImpl.getOriginalChildLoopDivisionId(),
				loopDivisionRelModelImpl.getOriginalLoopPersonId()
			};

			finderCache.removeResult(_finderPathCountByCLDI_LPI, args);
			finderCache.removeResult(_finderPathFetchByCLDI_LPI, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				loopDivisionRelModelImpl.getLoopPersonId(),
				loopDivisionRelModelImpl.getParentLoopDivisionId()
			};

			finderCache.removeResult(_finderPathCountByLPI_PLDI, args);
			finderCache.removeResult(_finderPathFetchByLPI_PLDI, args);
		}

		if ((loopDivisionRelModelImpl.getColumnBitmask() &
			 _finderPathFetchByLPI_PLDI.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				loopDivisionRelModelImpl.getOriginalLoopPersonId(),
				loopDivisionRelModelImpl.getOriginalParentLoopDivisionId()
			};

			finderCache.removeResult(_finderPathCountByLPI_PLDI, args);
			finderCache.removeResult(_finderPathFetchByLPI_PLDI, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				loopDivisionRelModelImpl.getChildLoopDivisionId(),
				loopDivisionRelModelImpl.getLoopPersonId(),
				loopDivisionRelModelImpl.getParentLoopDivisionId()
			};

			finderCache.removeResult(_finderPathCountByCLDI_LPI_PLDI, args);
			finderCache.removeResult(_finderPathFetchByCLDI_LPI_PLDI, args);
		}

		if ((loopDivisionRelModelImpl.getColumnBitmask() &
			 _finderPathFetchByCLDI_LPI_PLDI.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				loopDivisionRelModelImpl.getOriginalChildLoopDivisionId(),
				loopDivisionRelModelImpl.getOriginalLoopPersonId(),
				loopDivisionRelModelImpl.getOriginalParentLoopDivisionId()
			};

			finderCache.removeResult(_finderPathCountByCLDI_LPI_PLDI, args);
			finderCache.removeResult(_finderPathFetchByCLDI_LPI_PLDI, args);
		}
	}

	/**
	 * Creates a new loop division rel with the primary key. Does not add the loop division rel to the database.
	 *
	 * @param loopDivisionRelId the primary key for the new loop division rel
	 * @return the new loop division rel
	 */
	@Override
	public LoopDivisionRel create(long loopDivisionRelId) {
		LoopDivisionRel loopDivisionRel = new LoopDivisionRelImpl();

		loopDivisionRel.setNew(true);
		loopDivisionRel.setPrimaryKey(loopDivisionRelId);

		return loopDivisionRel;
	}

	/**
	 * Removes the loop division rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param loopDivisionRelId the primary key of the loop division rel
	 * @return the loop division rel that was removed
	 * @throws NoSuchLoopDivisionRelException if a loop division rel with the primary key could not be found
	 */
	@Override
	public LoopDivisionRel remove(long loopDivisionRelId)
		throws NoSuchLoopDivisionRelException {

		return remove((Serializable)loopDivisionRelId);
	}

	/**
	 * Removes the loop division rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the loop division rel
	 * @return the loop division rel that was removed
	 * @throws NoSuchLoopDivisionRelException if a loop division rel with the primary key could not be found
	 */
	@Override
	public LoopDivisionRel remove(Serializable primaryKey)
		throws NoSuchLoopDivisionRelException {

		Session session = null;

		try {
			session = openSession();

			LoopDivisionRel loopDivisionRel = (LoopDivisionRel)session.get(
				LoopDivisionRelImpl.class, primaryKey);

			if (loopDivisionRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLoopDivisionRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(loopDivisionRel);
		}
		catch (NoSuchLoopDivisionRelException noSuchEntityException) {
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
	protected LoopDivisionRel removeImpl(LoopDivisionRel loopDivisionRel) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(loopDivisionRel)) {
				loopDivisionRel = (LoopDivisionRel)session.get(
					LoopDivisionRelImpl.class,
					loopDivisionRel.getPrimaryKeyObj());
			}

			if (loopDivisionRel != null) {
				session.delete(loopDivisionRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (loopDivisionRel != null) {
			clearCache(loopDivisionRel);
		}

		return loopDivisionRel;
	}

	@Override
	public LoopDivisionRel updateImpl(LoopDivisionRel loopDivisionRel) {
		boolean isNew = loopDivisionRel.isNew();

		if (!(loopDivisionRel instanceof LoopDivisionRelModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(loopDivisionRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					loopDivisionRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in loopDivisionRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LoopDivisionRel implementation " +
					loopDivisionRel.getClass());
		}

		LoopDivisionRelModelImpl loopDivisionRelModelImpl =
			(LoopDivisionRelModelImpl)loopDivisionRel;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(loopDivisionRel);

				loopDivisionRel.setNew(false);
			}
			else {
				loopDivisionRel = (LoopDivisionRel)session.merge(
					loopDivisionRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LoopDivisionRelModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			LoopDivisionRelModelImpl.ENTITY_CACHE_ENABLED,
			LoopDivisionRelImpl.class, loopDivisionRel.getPrimaryKey(),
			loopDivisionRel, false);

		clearUniqueFindersCache(loopDivisionRelModelImpl, false);
		cacheUniqueFindersCache(loopDivisionRelModelImpl);

		loopDivisionRel.resetOriginalValues();

		return loopDivisionRel;
	}

	/**
	 * Returns the loop division rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the loop division rel
	 * @return the loop division rel
	 * @throws NoSuchLoopDivisionRelException if a loop division rel with the primary key could not be found
	 */
	@Override
	public LoopDivisionRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLoopDivisionRelException {

		LoopDivisionRel loopDivisionRel = fetchByPrimaryKey(primaryKey);

		if (loopDivisionRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLoopDivisionRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return loopDivisionRel;
	}

	/**
	 * Returns the loop division rel with the primary key or throws a <code>NoSuchLoopDivisionRelException</code> if it could not be found.
	 *
	 * @param loopDivisionRelId the primary key of the loop division rel
	 * @return the loop division rel
	 * @throws NoSuchLoopDivisionRelException if a loop division rel with the primary key could not be found
	 */
	@Override
	public LoopDivisionRel findByPrimaryKey(long loopDivisionRelId)
		throws NoSuchLoopDivisionRelException {

		return findByPrimaryKey((Serializable)loopDivisionRelId);
	}

	/**
	 * Returns the loop division rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the loop division rel
	 * @return the loop division rel, or <code>null</code> if a loop division rel with the primary key could not be found
	 */
	@Override
	public LoopDivisionRel fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			LoopDivisionRelModelImpl.ENTITY_CACHE_ENABLED,
			LoopDivisionRelImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		LoopDivisionRel loopDivisionRel = (LoopDivisionRel)serializable;

		if (loopDivisionRel == null) {
			Session session = null;

			try {
				session = openSession();

				loopDivisionRel = (LoopDivisionRel)session.get(
					LoopDivisionRelImpl.class, primaryKey);

				if (loopDivisionRel != null) {
					cacheResult(loopDivisionRel);
				}
				else {
					entityCache.putResult(
						LoopDivisionRelModelImpl.ENTITY_CACHE_ENABLED,
						LoopDivisionRelImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					LoopDivisionRelModelImpl.ENTITY_CACHE_ENABLED,
					LoopDivisionRelImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return loopDivisionRel;
	}

	/**
	 * Returns the loop division rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param loopDivisionRelId the primary key of the loop division rel
	 * @return the loop division rel, or <code>null</code> if a loop division rel with the primary key could not be found
	 */
	@Override
	public LoopDivisionRel fetchByPrimaryKey(long loopDivisionRelId) {
		return fetchByPrimaryKey((Serializable)loopDivisionRelId);
	}

	@Override
	public Map<Serializable, LoopDivisionRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, LoopDivisionRel> map =
			new HashMap<Serializable, LoopDivisionRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			LoopDivisionRel loopDivisionRel = fetchByPrimaryKey(primaryKey);

			if (loopDivisionRel != null) {
				map.put(primaryKey, loopDivisionRel);
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
				LoopDivisionRelModelImpl.ENTITY_CACHE_ENABLED,
				LoopDivisionRelImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (LoopDivisionRel)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_LOOPDIVISIONREL_WHERE_PKS_IN);

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

			for (LoopDivisionRel loopDivisionRel :
					(List<LoopDivisionRel>)query.list()) {

				map.put(loopDivisionRel.getPrimaryKeyObj(), loopDivisionRel);

				cacheResult(loopDivisionRel);

				uncachedPrimaryKeys.remove(loopDivisionRel.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					LoopDivisionRelModelImpl.ENTITY_CACHE_ENABLED,
					LoopDivisionRelImpl.class, primaryKey, nullModel);
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
	 * Returns all the loop division rels.
	 *
	 * @return the loop division rels
	 */
	@Override
	public List<LoopDivisionRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the loop division rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopDivisionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop division rels
	 * @param end the upper bound of the range of loop division rels (not inclusive)
	 * @return the range of loop division rels
	 */
	@Override
	public List<LoopDivisionRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the loop division rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopDivisionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop division rels
	 * @param end the upper bound of the range of loop division rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of loop division rels
	 */
	@Override
	public List<LoopDivisionRel> findAll(
		int start, int end,
		OrderByComparator<LoopDivisionRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the loop division rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopDivisionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop division rels
	 * @param end the upper bound of the range of loop division rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of loop division rels
	 */
	@Override
	public List<LoopDivisionRel> findAll(
		int start, int end,
		OrderByComparator<LoopDivisionRel> orderByComparator,
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

		List<LoopDivisionRel> list = null;

		if (useFinderCache) {
			list = (List<LoopDivisionRel>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_LOOPDIVISIONREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_LOOPDIVISIONREL;

				sql = sql.concat(LoopDivisionRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<LoopDivisionRel>)QueryUtil.list(
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
	 * Removes all the loop division rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LoopDivisionRel loopDivisionRel : findAll()) {
			remove(loopDivisionRel);
		}
	}

	/**
	 * Returns the number of loop division rels.
	 *
	 * @return the number of loop division rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_LOOPDIVISIONREL);

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
		return LoopDivisionRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the loop division rel persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			LoopDivisionRelModelImpl.ENTITY_CACHE_ENABLED,
			LoopDivisionRelModelImpl.FINDER_CACHE_ENABLED,
			LoopDivisionRelImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			LoopDivisionRelModelImpl.ENTITY_CACHE_ENABLED,
			LoopDivisionRelModelImpl.FINDER_CACHE_ENABLED,
			LoopDivisionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			LoopDivisionRelModelImpl.ENTITY_CACHE_ENABLED,
			LoopDivisionRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByCLDI_LPI = new FinderPath(
			LoopDivisionRelModelImpl.ENTITY_CACHE_ENABLED,
			LoopDivisionRelModelImpl.FINDER_CACHE_ENABLED,
			LoopDivisionRelImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByCLDI_LPI",
			new String[] {Long.class.getName(), Long.class.getName()},
			LoopDivisionRelModelImpl.CHILDLOOPDIVISIONID_COLUMN_BITMASK |
			LoopDivisionRelModelImpl.LOOPPERSONID_COLUMN_BITMASK);

		_finderPathCountByCLDI_LPI = new FinderPath(
			LoopDivisionRelModelImpl.ENTITY_CACHE_ENABLED,
			LoopDivisionRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCLDI_LPI",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathFetchByLPI_PLDI = new FinderPath(
			LoopDivisionRelModelImpl.ENTITY_CACHE_ENABLED,
			LoopDivisionRelModelImpl.FINDER_CACHE_ENABLED,
			LoopDivisionRelImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByLPI_PLDI",
			new String[] {Long.class.getName(), Long.class.getName()},
			LoopDivisionRelModelImpl.LOOPPERSONID_COLUMN_BITMASK |
			LoopDivisionRelModelImpl.PARENTLOOPDIVISIONID_COLUMN_BITMASK);

		_finderPathCountByLPI_PLDI = new FinderPath(
			LoopDivisionRelModelImpl.ENTITY_CACHE_ENABLED,
			LoopDivisionRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByLPI_PLDI",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathFetchByCLDI_LPI_PLDI = new FinderPath(
			LoopDivisionRelModelImpl.ENTITY_CACHE_ENABLED,
			LoopDivisionRelModelImpl.FINDER_CACHE_ENABLED,
			LoopDivisionRelImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByCLDI_LPI_PLDI",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			LoopDivisionRelModelImpl.CHILDLOOPDIVISIONID_COLUMN_BITMASK |
			LoopDivisionRelModelImpl.LOOPPERSONID_COLUMN_BITMASK |
			LoopDivisionRelModelImpl.PARENTLOOPDIVISIONID_COLUMN_BITMASK);

		_finderPathCountByCLDI_LPI_PLDI = new FinderPath(
			LoopDivisionRelModelImpl.ENTITY_CACHE_ENABLED,
			LoopDivisionRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCLDI_LPI_PLDI",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		LoopDivisionRelUtil.setPersistence(this);
	}

	public void destroy() {
		LoopDivisionRelUtil.setPersistence(null);

		entityCache.removeCache(LoopDivisionRelImpl.class.getName());

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

	private static final String _SQL_SELECT_LOOPDIVISIONREL =
		"SELECT loopDivisionRel FROM LoopDivisionRel loopDivisionRel";

	private static final String _SQL_SELECT_LOOPDIVISIONREL_WHERE_PKS_IN =
		"SELECT loopDivisionRel FROM LoopDivisionRel loopDivisionRel WHERE loopDivisionRelId IN (";

	private static final String _SQL_SELECT_LOOPDIVISIONREL_WHERE =
		"SELECT loopDivisionRel FROM LoopDivisionRel loopDivisionRel WHERE ";

	private static final String _SQL_COUNT_LOOPDIVISIONREL =
		"SELECT COUNT(loopDivisionRel) FROM LoopDivisionRel loopDivisionRel";

	private static final String _SQL_COUNT_LOOPDIVISIONREL_WHERE =
		"SELECT COUNT(loopDivisionRel) FROM LoopDivisionRel loopDivisionRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "loopDivisionRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LoopDivisionRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LoopDivisionRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LoopDivisionRelPersistenceImpl.class);

}