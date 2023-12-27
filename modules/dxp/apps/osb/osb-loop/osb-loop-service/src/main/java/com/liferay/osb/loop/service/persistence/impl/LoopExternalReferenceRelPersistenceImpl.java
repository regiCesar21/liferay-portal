/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.loop.service.persistence.impl;

import com.liferay.osb.loop.exception.NoSuchLoopExternalReferenceRelException;
import com.liferay.osb.loop.model.LoopExternalReferenceRel;
import com.liferay.osb.loop.model.impl.LoopExternalReferenceRelImpl;
import com.liferay.osb.loop.model.impl.LoopExternalReferenceRelModelImpl;
import com.liferay.osb.loop.service.persistence.LoopExternalReferenceRelPersistence;
import com.liferay.osb.loop.service.persistence.LoopExternalReferenceRelUtil;
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
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

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
 * The persistence implementation for the loop external reference rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ethan Bustad
 * @generated
 */
public class LoopExternalReferenceRelPersistenceImpl
	extends BasePersistenceImpl<LoopExternalReferenceRel>
	implements LoopExternalReferenceRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LoopExternalReferenceRelUtil</code> to access the loop external reference rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LoopExternalReferenceRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByERP_ESN;
	private FinderPath _finderPathCountByERP_ESN;

	/**
	 * Returns the loop external reference rel where externalReferenceName = &#63; and externalReferencePK = &#63; or throws a <code>NoSuchLoopExternalReferenceRelException</code> if it could not be found.
	 *
	 * @param externalReferenceName the external reference name
	 * @param externalReferencePK the external reference pk
	 * @return the matching loop external reference rel
	 * @throws NoSuchLoopExternalReferenceRelException if a matching loop external reference rel could not be found
	 */
	@Override
	public LoopExternalReferenceRel findByERP_ESN(
			String externalReferenceName, String externalReferencePK)
		throws NoSuchLoopExternalReferenceRelException {

		LoopExternalReferenceRel loopExternalReferenceRel = fetchByERP_ESN(
			externalReferenceName, externalReferencePK);

		if (loopExternalReferenceRel == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("externalReferenceName=");
			sb.append(externalReferenceName);

			sb.append(", externalReferencePK=");
			sb.append(externalReferencePK);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchLoopExternalReferenceRelException(sb.toString());
		}

		return loopExternalReferenceRel;
	}

	/**
	 * Returns the loop external reference rel where externalReferenceName = &#63; and externalReferencePK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceName the external reference name
	 * @param externalReferencePK the external reference pk
	 * @return the matching loop external reference rel, or <code>null</code> if a matching loop external reference rel could not be found
	 */
	@Override
	public LoopExternalReferenceRel fetchByERP_ESN(
		String externalReferenceName, String externalReferencePK) {

		return fetchByERP_ESN(externalReferenceName, externalReferencePK, true);
	}

	/**
	 * Returns the loop external reference rel where externalReferenceName = &#63; and externalReferencePK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceName the external reference name
	 * @param externalReferencePK the external reference pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching loop external reference rel, or <code>null</code> if a matching loop external reference rel could not be found
	 */
	@Override
	public LoopExternalReferenceRel fetchByERP_ESN(
		String externalReferenceName, String externalReferencePK,
		boolean useFinderCache) {

		externalReferenceName = Objects.toString(externalReferenceName, "");
		externalReferencePK = Objects.toString(externalReferencePK, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				externalReferenceName, externalReferencePK
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByERP_ESN, finderArgs, this);
		}

		if (result instanceof LoopExternalReferenceRel) {
			LoopExternalReferenceRel loopExternalReferenceRel =
				(LoopExternalReferenceRel)result;

			if (!Objects.equals(
					externalReferenceName,
					loopExternalReferenceRel.getExternalReferenceName()) ||
				!Objects.equals(
					externalReferencePK,
					loopExternalReferenceRel.getExternalReferencePK())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_LOOPEXTERNALREFERENCEREL_WHERE);

			boolean bindExternalReferenceName = false;

			if (externalReferenceName.isEmpty()) {
				sb.append(_FINDER_COLUMN_ERP_ESN_EXTERNALREFERENCENAME_3);
			}
			else {
				bindExternalReferenceName = true;

				sb.append(_FINDER_COLUMN_ERP_ESN_EXTERNALREFERENCENAME_2);
			}

			boolean bindExternalReferencePK = false;

			if (externalReferencePK.isEmpty()) {
				sb.append(_FINDER_COLUMN_ERP_ESN_EXTERNALREFERENCEPK_3);
			}
			else {
				bindExternalReferencePK = true;

				sb.append(_FINDER_COLUMN_ERP_ESN_EXTERNALREFERENCEPK_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExternalReferenceName) {
					queryPos.add(externalReferenceName);
				}

				if (bindExternalReferencePK) {
					queryPos.add(externalReferencePK);
				}

				List<LoopExternalReferenceRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByERP_ESN, finderArgs, list);
					}
				}
				else {
					LoopExternalReferenceRel loopExternalReferenceRel =
						list.get(0);

					result = loopExternalReferenceRel;

					cacheResult(loopExternalReferenceRel);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByERP_ESN, finderArgs);
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
			return (LoopExternalReferenceRel)result;
		}
	}

	/**
	 * Removes the loop external reference rel where externalReferenceName = &#63; and externalReferencePK = &#63; from the database.
	 *
	 * @param externalReferenceName the external reference name
	 * @param externalReferencePK the external reference pk
	 * @return the loop external reference rel that was removed
	 */
	@Override
	public LoopExternalReferenceRel removeByERP_ESN(
			String externalReferenceName, String externalReferencePK)
		throws NoSuchLoopExternalReferenceRelException {

		LoopExternalReferenceRel loopExternalReferenceRel = findByERP_ESN(
			externalReferenceName, externalReferencePK);

		return remove(loopExternalReferenceRel);
	}

	/**
	 * Returns the number of loop external reference rels where externalReferenceName = &#63; and externalReferencePK = &#63;.
	 *
	 * @param externalReferenceName the external reference name
	 * @param externalReferencePK the external reference pk
	 * @return the number of matching loop external reference rels
	 */
	@Override
	public int countByERP_ESN(
		String externalReferenceName, String externalReferencePK) {

		externalReferenceName = Objects.toString(externalReferenceName, "");
		externalReferencePK = Objects.toString(externalReferencePK, "");

		FinderPath finderPath = _finderPathCountByERP_ESN;

		Object[] finderArgs = new Object[] {
			externalReferenceName, externalReferencePK
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LOOPEXTERNALREFERENCEREL_WHERE);

			boolean bindExternalReferenceName = false;

			if (externalReferenceName.isEmpty()) {
				sb.append(_FINDER_COLUMN_ERP_ESN_EXTERNALREFERENCENAME_3);
			}
			else {
				bindExternalReferenceName = true;

				sb.append(_FINDER_COLUMN_ERP_ESN_EXTERNALREFERENCENAME_2);
			}

			boolean bindExternalReferencePK = false;

			if (externalReferencePK.isEmpty()) {
				sb.append(_FINDER_COLUMN_ERP_ESN_EXTERNALREFERENCEPK_3);
			}
			else {
				bindExternalReferencePK = true;

				sb.append(_FINDER_COLUMN_ERP_ESN_EXTERNALREFERENCEPK_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExternalReferenceName) {
					queryPos.add(externalReferenceName);
				}

				if (bindExternalReferencePK) {
					queryPos.add(externalReferencePK);
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

	private static final String _FINDER_COLUMN_ERP_ESN_EXTERNALREFERENCENAME_2 =
		"loopExternalReferenceRel.externalReferenceName = ? AND ";

	private static final String _FINDER_COLUMN_ERP_ESN_EXTERNALREFERENCENAME_3 =
		"(loopExternalReferenceRel.externalReferenceName IS NULL OR loopExternalReferenceRel.externalReferenceName = '') AND ";

	private static final String _FINDER_COLUMN_ERP_ESN_EXTERNALREFERENCEPK_2 =
		"loopExternalReferenceRel.externalReferencePK = ?";

	private static final String _FINDER_COLUMN_ERP_ESN_EXTERNALREFERENCEPK_3 =
		"(loopExternalReferenceRel.externalReferencePK IS NULL OR loopExternalReferenceRel.externalReferencePK = '')";

	public LoopExternalReferenceRelPersistenceImpl() {
		setModelClass(LoopExternalReferenceRel.class);
	}

	/**
	 * Caches the loop external reference rel in the entity cache if it is enabled.
	 *
	 * @param loopExternalReferenceRel the loop external reference rel
	 */
	@Override
	public void cacheResult(LoopExternalReferenceRel loopExternalReferenceRel) {
		entityCache.putResult(
			LoopExternalReferenceRelModelImpl.ENTITY_CACHE_ENABLED,
			LoopExternalReferenceRelImpl.class,
			loopExternalReferenceRel.getPrimaryKey(), loopExternalReferenceRel);

		finderCache.putResult(
			_finderPathFetchByERP_ESN,
			new Object[] {
				loopExternalReferenceRel.getExternalReferenceName(),
				loopExternalReferenceRel.getExternalReferencePK()
			},
			loopExternalReferenceRel);

		loopExternalReferenceRel.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the loop external reference rels in the entity cache if it is enabled.
	 *
	 * @param loopExternalReferenceRels the loop external reference rels
	 */
	@Override
	public void cacheResult(
		List<LoopExternalReferenceRel> loopExternalReferenceRels) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (loopExternalReferenceRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (LoopExternalReferenceRel loopExternalReferenceRel :
				loopExternalReferenceRels) {

			if (entityCache.getResult(
					LoopExternalReferenceRelModelImpl.ENTITY_CACHE_ENABLED,
					LoopExternalReferenceRelImpl.class,
					loopExternalReferenceRel.getPrimaryKey()) == null) {

				cacheResult(loopExternalReferenceRel);
			}
			else {
				loopExternalReferenceRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all loop external reference rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LoopExternalReferenceRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the loop external reference rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LoopExternalReferenceRel loopExternalReferenceRel) {
		entityCache.removeResult(
			LoopExternalReferenceRelModelImpl.ENTITY_CACHE_ENABLED,
			LoopExternalReferenceRelImpl.class,
			loopExternalReferenceRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(LoopExternalReferenceRelModelImpl)loopExternalReferenceRel, true);
	}

	@Override
	public void clearCache(
		List<LoopExternalReferenceRel> loopExternalReferenceRels) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LoopExternalReferenceRel loopExternalReferenceRel :
				loopExternalReferenceRels) {

			entityCache.removeResult(
				LoopExternalReferenceRelModelImpl.ENTITY_CACHE_ENABLED,
				LoopExternalReferenceRelImpl.class,
				loopExternalReferenceRel.getPrimaryKey());

			clearUniqueFindersCache(
				(LoopExternalReferenceRelModelImpl)loopExternalReferenceRel,
				true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				LoopExternalReferenceRelModelImpl.ENTITY_CACHE_ENABLED,
				LoopExternalReferenceRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		LoopExternalReferenceRelModelImpl loopExternalReferenceRelModelImpl) {

		Object[] args = new Object[] {
			loopExternalReferenceRelModelImpl.getExternalReferenceName(),
			loopExternalReferenceRelModelImpl.getExternalReferencePK()
		};

		finderCache.putResult(
			_finderPathCountByERP_ESN, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByERP_ESN, args, loopExternalReferenceRelModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		LoopExternalReferenceRelModelImpl loopExternalReferenceRelModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				loopExternalReferenceRelModelImpl.getExternalReferenceName(),
				loopExternalReferenceRelModelImpl.getExternalReferencePK()
			};

			finderCache.removeResult(_finderPathCountByERP_ESN, args);
			finderCache.removeResult(_finderPathFetchByERP_ESN, args);
		}

		if ((loopExternalReferenceRelModelImpl.getColumnBitmask() &
			 _finderPathFetchByERP_ESN.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				loopExternalReferenceRelModelImpl.
					getOriginalExternalReferenceName(),
				loopExternalReferenceRelModelImpl.
					getOriginalExternalReferencePK()
			};

			finderCache.removeResult(_finderPathCountByERP_ESN, args);
			finderCache.removeResult(_finderPathFetchByERP_ESN, args);
		}
	}

	/**
	 * Creates a new loop external reference rel with the primary key. Does not add the loop external reference rel to the database.
	 *
	 * @param loopExternalReferenceRelId the primary key for the new loop external reference rel
	 * @return the new loop external reference rel
	 */
	@Override
	public LoopExternalReferenceRel create(long loopExternalReferenceRelId) {
		LoopExternalReferenceRel loopExternalReferenceRel =
			new LoopExternalReferenceRelImpl();

		loopExternalReferenceRel.setNew(true);
		loopExternalReferenceRel.setPrimaryKey(loopExternalReferenceRelId);

		return loopExternalReferenceRel;
	}

	/**
	 * Removes the loop external reference rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param loopExternalReferenceRelId the primary key of the loop external reference rel
	 * @return the loop external reference rel that was removed
	 * @throws NoSuchLoopExternalReferenceRelException if a loop external reference rel with the primary key could not be found
	 */
	@Override
	public LoopExternalReferenceRel remove(long loopExternalReferenceRelId)
		throws NoSuchLoopExternalReferenceRelException {

		return remove((Serializable)loopExternalReferenceRelId);
	}

	/**
	 * Removes the loop external reference rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the loop external reference rel
	 * @return the loop external reference rel that was removed
	 * @throws NoSuchLoopExternalReferenceRelException if a loop external reference rel with the primary key could not be found
	 */
	@Override
	public LoopExternalReferenceRel remove(Serializable primaryKey)
		throws NoSuchLoopExternalReferenceRelException {

		Session session = null;

		try {
			session = openSession();

			LoopExternalReferenceRel loopExternalReferenceRel =
				(LoopExternalReferenceRel)session.get(
					LoopExternalReferenceRelImpl.class, primaryKey);

			if (loopExternalReferenceRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLoopExternalReferenceRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(loopExternalReferenceRel);
		}
		catch (NoSuchLoopExternalReferenceRelException noSuchEntityException) {
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
	protected LoopExternalReferenceRel removeImpl(
		LoopExternalReferenceRel loopExternalReferenceRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(loopExternalReferenceRel)) {
				loopExternalReferenceRel =
					(LoopExternalReferenceRel)session.get(
						LoopExternalReferenceRelImpl.class,
						loopExternalReferenceRel.getPrimaryKeyObj());
			}

			if (loopExternalReferenceRel != null) {
				session.delete(loopExternalReferenceRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (loopExternalReferenceRel != null) {
			clearCache(loopExternalReferenceRel);
		}

		return loopExternalReferenceRel;
	}

	@Override
	public LoopExternalReferenceRel updateImpl(
		LoopExternalReferenceRel loopExternalReferenceRel) {

		boolean isNew = loopExternalReferenceRel.isNew();

		if (!(loopExternalReferenceRel instanceof
				LoopExternalReferenceRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(loopExternalReferenceRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					loopExternalReferenceRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in loopExternalReferenceRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LoopExternalReferenceRel implementation " +
					loopExternalReferenceRel.getClass());
		}

		LoopExternalReferenceRelModelImpl loopExternalReferenceRelModelImpl =
			(LoopExternalReferenceRelModelImpl)loopExternalReferenceRel;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(loopExternalReferenceRel);

				loopExternalReferenceRel.setNew(false);
			}
			else {
				loopExternalReferenceRel =
					(LoopExternalReferenceRel)session.merge(
						loopExternalReferenceRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LoopExternalReferenceRelModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			LoopExternalReferenceRelModelImpl.ENTITY_CACHE_ENABLED,
			LoopExternalReferenceRelImpl.class,
			loopExternalReferenceRel.getPrimaryKey(), loopExternalReferenceRel,
			false);

		clearUniqueFindersCache(loopExternalReferenceRelModelImpl, false);
		cacheUniqueFindersCache(loopExternalReferenceRelModelImpl);

		loopExternalReferenceRel.resetOriginalValues();

		return loopExternalReferenceRel;
	}

	/**
	 * Returns the loop external reference rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the loop external reference rel
	 * @return the loop external reference rel
	 * @throws NoSuchLoopExternalReferenceRelException if a loop external reference rel with the primary key could not be found
	 */
	@Override
	public LoopExternalReferenceRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLoopExternalReferenceRelException {

		LoopExternalReferenceRel loopExternalReferenceRel = fetchByPrimaryKey(
			primaryKey);

		if (loopExternalReferenceRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLoopExternalReferenceRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return loopExternalReferenceRel;
	}

	/**
	 * Returns the loop external reference rel with the primary key or throws a <code>NoSuchLoopExternalReferenceRelException</code> if it could not be found.
	 *
	 * @param loopExternalReferenceRelId the primary key of the loop external reference rel
	 * @return the loop external reference rel
	 * @throws NoSuchLoopExternalReferenceRelException if a loop external reference rel with the primary key could not be found
	 */
	@Override
	public LoopExternalReferenceRel findByPrimaryKey(
			long loopExternalReferenceRelId)
		throws NoSuchLoopExternalReferenceRelException {

		return findByPrimaryKey((Serializable)loopExternalReferenceRelId);
	}

	/**
	 * Returns the loop external reference rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the loop external reference rel
	 * @return the loop external reference rel, or <code>null</code> if a loop external reference rel with the primary key could not be found
	 */
	@Override
	public LoopExternalReferenceRel fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			LoopExternalReferenceRelModelImpl.ENTITY_CACHE_ENABLED,
			LoopExternalReferenceRelImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		LoopExternalReferenceRel loopExternalReferenceRel =
			(LoopExternalReferenceRel)serializable;

		if (loopExternalReferenceRel == null) {
			Session session = null;

			try {
				session = openSession();

				loopExternalReferenceRel =
					(LoopExternalReferenceRel)session.get(
						LoopExternalReferenceRelImpl.class, primaryKey);

				if (loopExternalReferenceRel != null) {
					cacheResult(loopExternalReferenceRel);
				}
				else {
					entityCache.putResult(
						LoopExternalReferenceRelModelImpl.ENTITY_CACHE_ENABLED,
						LoopExternalReferenceRelImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					LoopExternalReferenceRelModelImpl.ENTITY_CACHE_ENABLED,
					LoopExternalReferenceRelImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return loopExternalReferenceRel;
	}

	/**
	 * Returns the loop external reference rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param loopExternalReferenceRelId the primary key of the loop external reference rel
	 * @return the loop external reference rel, or <code>null</code> if a loop external reference rel with the primary key could not be found
	 */
	@Override
	public LoopExternalReferenceRel fetchByPrimaryKey(
		long loopExternalReferenceRelId) {

		return fetchByPrimaryKey((Serializable)loopExternalReferenceRelId);
	}

	@Override
	public Map<Serializable, LoopExternalReferenceRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, LoopExternalReferenceRel> map =
			new HashMap<Serializable, LoopExternalReferenceRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			LoopExternalReferenceRel loopExternalReferenceRel =
				fetchByPrimaryKey(primaryKey);

			if (loopExternalReferenceRel != null) {
				map.put(primaryKey, loopExternalReferenceRel);
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
				LoopExternalReferenceRelModelImpl.ENTITY_CACHE_ENABLED,
				LoopExternalReferenceRelImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (LoopExternalReferenceRel)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_LOOPEXTERNALREFERENCEREL_WHERE_PKS_IN);

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

			for (LoopExternalReferenceRel loopExternalReferenceRel :
					(List<LoopExternalReferenceRel>)query.list()) {

				map.put(
					loopExternalReferenceRel.getPrimaryKeyObj(),
					loopExternalReferenceRel);

				cacheResult(loopExternalReferenceRel);

				uncachedPrimaryKeys.remove(
					loopExternalReferenceRel.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					LoopExternalReferenceRelModelImpl.ENTITY_CACHE_ENABLED,
					LoopExternalReferenceRelImpl.class, primaryKey, nullModel);
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
	 * Returns all the loop external reference rels.
	 *
	 * @return the loop external reference rels
	 */
	@Override
	public List<LoopExternalReferenceRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the loop external reference rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopExternalReferenceRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop external reference rels
	 * @param end the upper bound of the range of loop external reference rels (not inclusive)
	 * @return the range of loop external reference rels
	 */
	@Override
	public List<LoopExternalReferenceRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the loop external reference rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopExternalReferenceRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop external reference rels
	 * @param end the upper bound of the range of loop external reference rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of loop external reference rels
	 */
	@Override
	public List<LoopExternalReferenceRel> findAll(
		int start, int end,
		OrderByComparator<LoopExternalReferenceRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the loop external reference rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopExternalReferenceRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop external reference rels
	 * @param end the upper bound of the range of loop external reference rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of loop external reference rels
	 */
	@Override
	public List<LoopExternalReferenceRel> findAll(
		int start, int end,
		OrderByComparator<LoopExternalReferenceRel> orderByComparator,
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

		List<LoopExternalReferenceRel> list = null;

		if (useFinderCache) {
			list = (List<LoopExternalReferenceRel>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_LOOPEXTERNALREFERENCEREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_LOOPEXTERNALREFERENCEREL;

				sql = sql.concat(
					LoopExternalReferenceRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<LoopExternalReferenceRel>)QueryUtil.list(
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
	 * Removes all the loop external reference rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LoopExternalReferenceRel loopExternalReferenceRel : findAll()) {
			remove(loopExternalReferenceRel);
		}
	}

	/**
	 * Returns the number of loop external reference rels.
	 *
	 * @return the number of loop external reference rels
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
					_SQL_COUNT_LOOPEXTERNALREFERENCEREL);

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
		return LoopExternalReferenceRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the loop external reference rel persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			LoopExternalReferenceRelModelImpl.ENTITY_CACHE_ENABLED,
			LoopExternalReferenceRelModelImpl.FINDER_CACHE_ENABLED,
			LoopExternalReferenceRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			LoopExternalReferenceRelModelImpl.ENTITY_CACHE_ENABLED,
			LoopExternalReferenceRelModelImpl.FINDER_CACHE_ENABLED,
			LoopExternalReferenceRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			LoopExternalReferenceRelModelImpl.ENTITY_CACHE_ENABLED,
			LoopExternalReferenceRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByERP_ESN = new FinderPath(
			LoopExternalReferenceRelModelImpl.ENTITY_CACHE_ENABLED,
			LoopExternalReferenceRelModelImpl.FINDER_CACHE_ENABLED,
			LoopExternalReferenceRelImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByERP_ESN",
			new String[] {String.class.getName(), String.class.getName()},
			LoopExternalReferenceRelModelImpl.
				EXTERNALREFERENCENAME_COLUMN_BITMASK |
			LoopExternalReferenceRelModelImpl.
				EXTERNALREFERENCEPK_COLUMN_BITMASK);

		_finderPathCountByERP_ESN = new FinderPath(
			LoopExternalReferenceRelModelImpl.ENTITY_CACHE_ENABLED,
			LoopExternalReferenceRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByERP_ESN",
			new String[] {String.class.getName(), String.class.getName()});

		LoopExternalReferenceRelUtil.setPersistence(this);
	}

	public void destroy() {
		LoopExternalReferenceRelUtil.setPersistence(null);

		entityCache.removeCache(LoopExternalReferenceRelImpl.class.getName());

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

	private static final String _SQL_SELECT_LOOPEXTERNALREFERENCEREL =
		"SELECT loopExternalReferenceRel FROM LoopExternalReferenceRel loopExternalReferenceRel";

	private static final String
		_SQL_SELECT_LOOPEXTERNALREFERENCEREL_WHERE_PKS_IN =
			"SELECT loopExternalReferenceRel FROM LoopExternalReferenceRel loopExternalReferenceRel WHERE loopExternalReferenceRelId IN (";

	private static final String _SQL_SELECT_LOOPEXTERNALREFERENCEREL_WHERE =
		"SELECT loopExternalReferenceRel FROM LoopExternalReferenceRel loopExternalReferenceRel WHERE ";

	private static final String _SQL_COUNT_LOOPEXTERNALREFERENCEREL =
		"SELECT COUNT(loopExternalReferenceRel) FROM LoopExternalReferenceRel loopExternalReferenceRel";

	private static final String _SQL_COUNT_LOOPEXTERNALREFERENCEREL_WHERE =
		"SELECT COUNT(loopExternalReferenceRel) FROM LoopExternalReferenceRel loopExternalReferenceRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"loopExternalReferenceRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LoopExternalReferenceRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LoopExternalReferenceRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LoopExternalReferenceRelPersistenceImpl.class);

}