/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.loop.service.persistence.impl;

import com.liferay.osb.loop.exception.NoSuchLoopTopicException;
import com.liferay.osb.loop.model.LoopTopic;
import com.liferay.osb.loop.model.impl.LoopTopicImpl;
import com.liferay.osb.loop.model.impl.LoopTopicModelImpl;
import com.liferay.osb.loop.service.persistence.LoopTopicPersistence;
import com.liferay.osb.loop.service.persistence.LoopTopicUtil;
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
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the loop topic service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ethan Bustad
 * @generated
 */
public class LoopTopicPersistenceImpl
	extends BasePersistenceImpl<LoopTopic> implements LoopTopicPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LoopTopicUtil</code> to access the loop topic persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LoopTopicImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByC_N;
	private FinderPath _finderPathCountByC_N;

	/**
	 * Returns the loop topic where companyId = &#63; and name = &#63; or throws a <code>NoSuchLoopTopicException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching loop topic
	 * @throws NoSuchLoopTopicException if a matching loop topic could not be found
	 */
	@Override
	public LoopTopic findByC_N(long companyId, String name)
		throws NoSuchLoopTopicException {

		LoopTopic loopTopic = fetchByC_N(companyId, name);

		if (loopTopic == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchLoopTopicException(sb.toString());
		}

		return loopTopic;
	}

	/**
	 * Returns the loop topic where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching loop topic, or <code>null</code> if a matching loop topic could not be found
	 */
	@Override
	public LoopTopic fetchByC_N(long companyId, String name) {
		return fetchByC_N(companyId, name, true);
	}

	/**
	 * Returns the loop topic where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching loop topic, or <code>null</code> if a matching loop topic could not be found
	 */
	@Override
	public LoopTopic fetchByC_N(
		long companyId, String name, boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_N, finderArgs, this);
		}

		if (result instanceof LoopTopic) {
			LoopTopic loopTopic = (LoopTopic)result;

			if ((companyId != loopTopic.getCompanyId()) ||
				!Objects.equals(name, loopTopic.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_LOOPTOPIC_WHERE);

			sb.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindName) {
					queryPos.add(name);
				}

				List<LoopTopic> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_N, finderArgs, list);
					}
				}
				else {
					LoopTopic loopTopic = list.get(0);

					result = loopTopic;

					cacheResult(loopTopic);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(_finderPathFetchByC_N, finderArgs);
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
			return (LoopTopic)result;
		}
	}

	/**
	 * Removes the loop topic where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the loop topic that was removed
	 */
	@Override
	public LoopTopic removeByC_N(long companyId, String name)
		throws NoSuchLoopTopicException {

		LoopTopic loopTopic = findByC_N(companyId, name);

		return remove(loopTopic);
	}

	/**
	 * Returns the number of loop topics where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching loop topics
	 */
	@Override
	public int countByC_N(long companyId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByC_N;

		Object[] finderArgs = new Object[] {companyId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LOOPTOPIC_WHERE);

			sb.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindName) {
					queryPos.add(name);
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

	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 =
		"loopTopic.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_N_NAME_2 =
		"loopTopic.name = ?";

	private static final String _FINDER_COLUMN_C_N_NAME_3 =
		"(loopTopic.name IS NULL OR loopTopic.name = '')";

	public LoopTopicPersistenceImpl() {
		setModelClass(LoopTopic.class);
	}

	/**
	 * Caches the loop topic in the entity cache if it is enabled.
	 *
	 * @param loopTopic the loop topic
	 */
	@Override
	public void cacheResult(LoopTopic loopTopic) {
		entityCache.putResult(
			LoopTopicModelImpl.ENTITY_CACHE_ENABLED, LoopTopicImpl.class,
			loopTopic.getPrimaryKey(), loopTopic);

		finderCache.putResult(
			_finderPathFetchByC_N,
			new Object[] {loopTopic.getCompanyId(), loopTopic.getName()},
			loopTopic);

		loopTopic.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the loop topics in the entity cache if it is enabled.
	 *
	 * @param loopTopics the loop topics
	 */
	@Override
	public void cacheResult(List<LoopTopic> loopTopics) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (loopTopics.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (LoopTopic loopTopic : loopTopics) {
			if (entityCache.getResult(
					LoopTopicModelImpl.ENTITY_CACHE_ENABLED,
					LoopTopicImpl.class, loopTopic.getPrimaryKey()) == null) {

				cacheResult(loopTopic);
			}
			else {
				loopTopic.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all loop topics.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LoopTopicImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the loop topic.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LoopTopic loopTopic) {
		entityCache.removeResult(
			LoopTopicModelImpl.ENTITY_CACHE_ENABLED, LoopTopicImpl.class,
			loopTopic.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((LoopTopicModelImpl)loopTopic, true);
	}

	@Override
	public void clearCache(List<LoopTopic> loopTopics) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LoopTopic loopTopic : loopTopics) {
			entityCache.removeResult(
				LoopTopicModelImpl.ENTITY_CACHE_ENABLED, LoopTopicImpl.class,
				loopTopic.getPrimaryKey());

			clearUniqueFindersCache((LoopTopicModelImpl)loopTopic, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				LoopTopicModelImpl.ENTITY_CACHE_ENABLED, LoopTopicImpl.class,
				primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		LoopTopicModelImpl loopTopicModelImpl) {

		Object[] args = new Object[] {
			loopTopicModelImpl.getCompanyId(), loopTopicModelImpl.getName()
		};

		finderCache.putResult(
			_finderPathCountByC_N, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_N, args, loopTopicModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		LoopTopicModelImpl loopTopicModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				loopTopicModelImpl.getCompanyId(), loopTopicModelImpl.getName()
			};

			finderCache.removeResult(_finderPathCountByC_N, args);
			finderCache.removeResult(_finderPathFetchByC_N, args);
		}

		if ((loopTopicModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_N.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				loopTopicModelImpl.getOriginalCompanyId(),
				loopTopicModelImpl.getOriginalName()
			};

			finderCache.removeResult(_finderPathCountByC_N, args);
			finderCache.removeResult(_finderPathFetchByC_N, args);
		}
	}

	/**
	 * Creates a new loop topic with the primary key. Does not add the loop topic to the database.
	 *
	 * @param loopTopicId the primary key for the new loop topic
	 * @return the new loop topic
	 */
	@Override
	public LoopTopic create(long loopTopicId) {
		LoopTopic loopTopic = new LoopTopicImpl();

		loopTopic.setNew(true);
		loopTopic.setPrimaryKey(loopTopicId);

		loopTopic.setCompanyId(CompanyThreadLocal.getCompanyId());

		return loopTopic;
	}

	/**
	 * Removes the loop topic with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param loopTopicId the primary key of the loop topic
	 * @return the loop topic that was removed
	 * @throws NoSuchLoopTopicException if a loop topic with the primary key could not be found
	 */
	@Override
	public LoopTopic remove(long loopTopicId) throws NoSuchLoopTopicException {
		return remove((Serializable)loopTopicId);
	}

	/**
	 * Removes the loop topic with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the loop topic
	 * @return the loop topic that was removed
	 * @throws NoSuchLoopTopicException if a loop topic with the primary key could not be found
	 */
	@Override
	public LoopTopic remove(Serializable primaryKey)
		throws NoSuchLoopTopicException {

		Session session = null;

		try {
			session = openSession();

			LoopTopic loopTopic = (LoopTopic)session.get(
				LoopTopicImpl.class, primaryKey);

			if (loopTopic == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLoopTopicException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(loopTopic);
		}
		catch (NoSuchLoopTopicException noSuchEntityException) {
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
	protected LoopTopic removeImpl(LoopTopic loopTopic) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(loopTopic)) {
				loopTopic = (LoopTopic)session.get(
					LoopTopicImpl.class, loopTopic.getPrimaryKeyObj());
			}

			if (loopTopic != null) {
				session.delete(loopTopic);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (loopTopic != null) {
			clearCache(loopTopic);
		}

		return loopTopic;
	}

	@Override
	public LoopTopic updateImpl(LoopTopic loopTopic) {
		boolean isNew = loopTopic.isNew();

		if (!(loopTopic instanceof LoopTopicModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(loopTopic.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(loopTopic);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in loopTopic proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LoopTopic implementation " +
					loopTopic.getClass());
		}

		LoopTopicModelImpl loopTopicModelImpl = (LoopTopicModelImpl)loopTopic;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (loopTopic.getCreateDate() == null)) {
			if (serviceContext == null) {
				loopTopic.setCreateDate(date);
			}
			else {
				loopTopic.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!loopTopicModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				loopTopic.setModifiedDate(date);
			}
			else {
				loopTopic.setModifiedDate(serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(loopTopic);

				loopTopic.setNew(false);
			}
			else {
				loopTopic = (LoopTopic)session.merge(loopTopic);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LoopTopicModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			LoopTopicModelImpl.ENTITY_CACHE_ENABLED, LoopTopicImpl.class,
			loopTopic.getPrimaryKey(), loopTopic, false);

		clearUniqueFindersCache(loopTopicModelImpl, false);
		cacheUniqueFindersCache(loopTopicModelImpl);

		loopTopic.resetOriginalValues();

		return loopTopic;
	}

	/**
	 * Returns the loop topic with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the loop topic
	 * @return the loop topic
	 * @throws NoSuchLoopTopicException if a loop topic with the primary key could not be found
	 */
	@Override
	public LoopTopic findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLoopTopicException {

		LoopTopic loopTopic = fetchByPrimaryKey(primaryKey);

		if (loopTopic == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLoopTopicException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return loopTopic;
	}

	/**
	 * Returns the loop topic with the primary key or throws a <code>NoSuchLoopTopicException</code> if it could not be found.
	 *
	 * @param loopTopicId the primary key of the loop topic
	 * @return the loop topic
	 * @throws NoSuchLoopTopicException if a loop topic with the primary key could not be found
	 */
	@Override
	public LoopTopic findByPrimaryKey(long loopTopicId)
		throws NoSuchLoopTopicException {

		return findByPrimaryKey((Serializable)loopTopicId);
	}

	/**
	 * Returns the loop topic with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the loop topic
	 * @return the loop topic, or <code>null</code> if a loop topic with the primary key could not be found
	 */
	@Override
	public LoopTopic fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			LoopTopicModelImpl.ENTITY_CACHE_ENABLED, LoopTopicImpl.class,
			primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		LoopTopic loopTopic = (LoopTopic)serializable;

		if (loopTopic == null) {
			Session session = null;

			try {
				session = openSession();

				loopTopic = (LoopTopic)session.get(
					LoopTopicImpl.class, primaryKey);

				if (loopTopic != null) {
					cacheResult(loopTopic);
				}
				else {
					entityCache.putResult(
						LoopTopicModelImpl.ENTITY_CACHE_ENABLED,
						LoopTopicImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					LoopTopicModelImpl.ENTITY_CACHE_ENABLED,
					LoopTopicImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return loopTopic;
	}

	/**
	 * Returns the loop topic with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param loopTopicId the primary key of the loop topic
	 * @return the loop topic, or <code>null</code> if a loop topic with the primary key could not be found
	 */
	@Override
	public LoopTopic fetchByPrimaryKey(long loopTopicId) {
		return fetchByPrimaryKey((Serializable)loopTopicId);
	}

	@Override
	public Map<Serializable, LoopTopic> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, LoopTopic> map =
			new HashMap<Serializable, LoopTopic>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			LoopTopic loopTopic = fetchByPrimaryKey(primaryKey);

			if (loopTopic != null) {
				map.put(primaryKey, loopTopic);
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
				LoopTopicModelImpl.ENTITY_CACHE_ENABLED, LoopTopicImpl.class,
				primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (LoopTopic)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_LOOPTOPIC_WHERE_PKS_IN);

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

			for (LoopTopic loopTopic : (List<LoopTopic>)query.list()) {
				map.put(loopTopic.getPrimaryKeyObj(), loopTopic);

				cacheResult(loopTopic);

				uncachedPrimaryKeys.remove(loopTopic.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					LoopTopicModelImpl.ENTITY_CACHE_ENABLED,
					LoopTopicImpl.class, primaryKey, nullModel);
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
	 * Returns all the loop topics.
	 *
	 * @return the loop topics
	 */
	@Override
	public List<LoopTopic> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the loop topics.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopTopicModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop topics
	 * @param end the upper bound of the range of loop topics (not inclusive)
	 * @return the range of loop topics
	 */
	@Override
	public List<LoopTopic> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the loop topics.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopTopicModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop topics
	 * @param end the upper bound of the range of loop topics (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of loop topics
	 */
	@Override
	public List<LoopTopic> findAll(
		int start, int end, OrderByComparator<LoopTopic> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the loop topics.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopTopicModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop topics
	 * @param end the upper bound of the range of loop topics (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of loop topics
	 */
	@Override
	public List<LoopTopic> findAll(
		int start, int end, OrderByComparator<LoopTopic> orderByComparator,
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

		List<LoopTopic> list = null;

		if (useFinderCache) {
			list = (List<LoopTopic>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_LOOPTOPIC);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_LOOPTOPIC;

				sql = sql.concat(LoopTopicModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<LoopTopic>)QueryUtil.list(
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
	 * Removes all the loop topics from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LoopTopic loopTopic : findAll()) {
			remove(loopTopic);
		}
	}

	/**
	 * Returns the number of loop topics.
	 *
	 * @return the number of loop topics
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_LOOPTOPIC);

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
		return LoopTopicModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the loop topic persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			LoopTopicModelImpl.ENTITY_CACHE_ENABLED,
			LoopTopicModelImpl.FINDER_CACHE_ENABLED, LoopTopicImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			LoopTopicModelImpl.ENTITY_CACHE_ENABLED,
			LoopTopicModelImpl.FINDER_CACHE_ENABLED, LoopTopicImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			LoopTopicModelImpl.ENTITY_CACHE_ENABLED,
			LoopTopicModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByC_N = new FinderPath(
			LoopTopicModelImpl.ENTITY_CACHE_ENABLED,
			LoopTopicModelImpl.FINDER_CACHE_ENABLED, LoopTopicImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_N",
			new String[] {Long.class.getName(), String.class.getName()},
			LoopTopicModelImpl.COMPANYID_COLUMN_BITMASK |
			LoopTopicModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByC_N = new FinderPath(
			LoopTopicModelImpl.ENTITY_CACHE_ENABLED,
			LoopTopicModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
			new String[] {Long.class.getName(), String.class.getName()});

		LoopTopicUtil.setPersistence(this);
	}

	public void destroy() {
		LoopTopicUtil.setPersistence(null);

		entityCache.removeCache(LoopTopicImpl.class.getName());

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

	private static final String _SQL_SELECT_LOOPTOPIC =
		"SELECT loopTopic FROM LoopTopic loopTopic";

	private static final String _SQL_SELECT_LOOPTOPIC_WHERE_PKS_IN =
		"SELECT loopTopic FROM LoopTopic loopTopic WHERE loopTopicId IN (";

	private static final String _SQL_SELECT_LOOPTOPIC_WHERE =
		"SELECT loopTopic FROM LoopTopic loopTopic WHERE ";

	private static final String _SQL_COUNT_LOOPTOPIC =
		"SELECT COUNT(loopTopic) FROM LoopTopic loopTopic";

	private static final String _SQL_COUNT_LOOPTOPIC_WHERE =
		"SELECT COUNT(loopTopic) FROM LoopTopic loopTopic WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "loopTopic.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LoopTopic exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LoopTopic exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LoopTopicPersistenceImpl.class);

}