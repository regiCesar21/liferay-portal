/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.metrics.sync.service.persistence.impl;

import com.liferay.osb.customer.metrics.sync.exception.NoSuchSyncStateException;
import com.liferay.osb.customer.metrics.sync.model.SyncState;
import com.liferay.osb.customer.metrics.sync.model.impl.SyncStateImpl;
import com.liferay.osb.customer.metrics.sync.model.impl.SyncStateModelImpl;
import com.liferay.osb.customer.metrics.sync.service.persistence.SyncStatePersistence;
import com.liferay.osb.customer.metrics.sync.service.persistence.SyncStateUtil;
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
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the sync state service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SyncStatePersistenceImpl
	extends BasePersistenceImpl<SyncState> implements SyncStatePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SyncStateUtil</code> to access the sync state persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SyncStateImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByModelName;
	private FinderPath _finderPathCountByModelName;

	/**
	 * Returns the sync state where modelName = &#63; or throws a <code>NoSuchSyncStateException</code> if it could not be found.
	 *
	 * @param modelName the model name
	 * @return the matching sync state
	 * @throws NoSuchSyncStateException if a matching sync state could not be found
	 */
	@Override
	public SyncState findByModelName(String modelName)
		throws NoSuchSyncStateException {

		SyncState syncState = fetchByModelName(modelName);

		if (syncState == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("modelName=");
			sb.append(modelName);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchSyncStateException(sb.toString());
		}

		return syncState;
	}

	/**
	 * Returns the sync state where modelName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param modelName the model name
	 * @return the matching sync state, or <code>null</code> if a matching sync state could not be found
	 */
	@Override
	public SyncState fetchByModelName(String modelName) {
		return fetchByModelName(modelName, true);
	}

	/**
	 * Returns the sync state where modelName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param modelName the model name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching sync state, or <code>null</code> if a matching sync state could not be found
	 */
	@Override
	public SyncState fetchByModelName(
		String modelName, boolean useFinderCache) {

		modelName = Objects.toString(modelName, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {modelName};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByModelName, finderArgs, this);
		}

		if (result instanceof SyncState) {
			SyncState syncState = (SyncState)result;

			if (!Objects.equals(modelName, syncState.getModelName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_SYNCSTATE_WHERE);

			boolean bindModelName = false;

			if (modelName.isEmpty()) {
				sb.append(_FINDER_COLUMN_MODELNAME_MODELNAME_3);
			}
			else {
				bindModelName = true;

				sb.append(_FINDER_COLUMN_MODELNAME_MODELNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindModelName) {
					queryPos.add(modelName);
				}

				List<SyncState> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByModelName, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {modelName};
							}

							_log.warn(
								"SyncStatePersistenceImpl.fetchByModelName(String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					SyncState syncState = list.get(0);

					result = syncState;

					cacheResult(syncState);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByModelName, finderArgs);
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
			return (SyncState)result;
		}
	}

	/**
	 * Removes the sync state where modelName = &#63; from the database.
	 *
	 * @param modelName the model name
	 * @return the sync state that was removed
	 */
	@Override
	public SyncState removeByModelName(String modelName)
		throws NoSuchSyncStateException {

		SyncState syncState = findByModelName(modelName);

		return remove(syncState);
	}

	/**
	 * Returns the number of sync states where modelName = &#63;.
	 *
	 * @param modelName the model name
	 * @return the number of matching sync states
	 */
	@Override
	public int countByModelName(String modelName) {
		modelName = Objects.toString(modelName, "");

		FinderPath finderPath = _finderPathCountByModelName;

		Object[] finderArgs = new Object[] {modelName};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SYNCSTATE_WHERE);

			boolean bindModelName = false;

			if (modelName.isEmpty()) {
				sb.append(_FINDER_COLUMN_MODELNAME_MODELNAME_3);
			}
			else {
				bindModelName = true;

				sb.append(_FINDER_COLUMN_MODELNAME_MODELNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindModelName) {
					queryPos.add(modelName);
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

	private static final String _FINDER_COLUMN_MODELNAME_MODELNAME_2 =
		"syncState.modelName = ?";

	private static final String _FINDER_COLUMN_MODELNAME_MODELNAME_3 =
		"(syncState.modelName IS NULL OR syncState.modelName = '')";

	public SyncStatePersistenceImpl() {
		setModelClass(SyncState.class);
	}

	/**
	 * Caches the sync state in the entity cache if it is enabled.
	 *
	 * @param syncState the sync state
	 */
	@Override
	public void cacheResult(SyncState syncState) {
		entityCache.putResult(
			SyncStateModelImpl.ENTITY_CACHE_ENABLED, SyncStateImpl.class,
			syncState.getPrimaryKey(), syncState);

		finderCache.putResult(
			_finderPathFetchByModelName,
			new Object[] {syncState.getModelName()}, syncState);

		syncState.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the sync states in the entity cache if it is enabled.
	 *
	 * @param syncStates the sync states
	 */
	@Override
	public void cacheResult(List<SyncState> syncStates) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (syncStates.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (SyncState syncState : syncStates) {
			if (entityCache.getResult(
					SyncStateModelImpl.ENTITY_CACHE_ENABLED,
					SyncStateImpl.class, syncState.getPrimaryKey()) == null) {

				cacheResult(syncState);
			}
			else {
				syncState.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all sync states.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SyncStateImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the sync state.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SyncState syncState) {
		entityCache.removeResult(
			SyncStateModelImpl.ENTITY_CACHE_ENABLED, SyncStateImpl.class,
			syncState.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((SyncStateModelImpl)syncState, true);
	}

	@Override
	public void clearCache(List<SyncState> syncStates) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SyncState syncState : syncStates) {
			entityCache.removeResult(
				SyncStateModelImpl.ENTITY_CACHE_ENABLED, SyncStateImpl.class,
				syncState.getPrimaryKey());

			clearUniqueFindersCache((SyncStateModelImpl)syncState, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				SyncStateModelImpl.ENTITY_CACHE_ENABLED, SyncStateImpl.class,
				primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		SyncStateModelImpl syncStateModelImpl) {

		Object[] args = new Object[] {syncStateModelImpl.getModelName()};

		finderCache.putResult(
			_finderPathCountByModelName, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByModelName, args, syncStateModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		SyncStateModelImpl syncStateModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {syncStateModelImpl.getModelName()};

			finderCache.removeResult(_finderPathCountByModelName, args);
			finderCache.removeResult(_finderPathFetchByModelName, args);
		}

		if ((syncStateModelImpl.getColumnBitmask() &
			 _finderPathFetchByModelName.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				syncStateModelImpl.getOriginalModelName()
			};

			finderCache.removeResult(_finderPathCountByModelName, args);
			finderCache.removeResult(_finderPathFetchByModelName, args);
		}
	}

	/**
	 * Creates a new sync state with the primary key. Does not add the sync state to the database.
	 *
	 * @param syncStateId the primary key for the new sync state
	 * @return the new sync state
	 */
	@Override
	public SyncState create(long syncStateId) {
		SyncState syncState = new SyncStateImpl();

		syncState.setNew(true);
		syncState.setPrimaryKey(syncStateId);

		return syncState;
	}

	/**
	 * Removes the sync state with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param syncStateId the primary key of the sync state
	 * @return the sync state that was removed
	 * @throws NoSuchSyncStateException if a sync state with the primary key could not be found
	 */
	@Override
	public SyncState remove(long syncStateId) throws NoSuchSyncStateException {
		return remove((Serializable)syncStateId);
	}

	/**
	 * Removes the sync state with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the sync state
	 * @return the sync state that was removed
	 * @throws NoSuchSyncStateException if a sync state with the primary key could not be found
	 */
	@Override
	public SyncState remove(Serializable primaryKey)
		throws NoSuchSyncStateException {

		Session session = null;

		try {
			session = openSession();

			SyncState syncState = (SyncState)session.get(
				SyncStateImpl.class, primaryKey);

			if (syncState == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSyncStateException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(syncState);
		}
		catch (NoSuchSyncStateException noSuchEntityException) {
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
	protected SyncState removeImpl(SyncState syncState) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(syncState)) {
				syncState = (SyncState)session.get(
					SyncStateImpl.class, syncState.getPrimaryKeyObj());
			}

			if (syncState != null) {
				session.delete(syncState);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (syncState != null) {
			clearCache(syncState);
		}

		return syncState;
	}

	@Override
	public SyncState updateImpl(SyncState syncState) {
		boolean isNew = syncState.isNew();

		if (!(syncState instanceof SyncStateModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(syncState.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(syncState);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in syncState proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SyncState implementation " +
					syncState.getClass());
		}

		SyncStateModelImpl syncStateModelImpl = (SyncStateModelImpl)syncState;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(syncState);

				syncState.setNew(false);
			}
			else {
				syncState = (SyncState)session.merge(syncState);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!SyncStateModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			SyncStateModelImpl.ENTITY_CACHE_ENABLED, SyncStateImpl.class,
			syncState.getPrimaryKey(), syncState, false);

		clearUniqueFindersCache(syncStateModelImpl, false);
		cacheUniqueFindersCache(syncStateModelImpl);

		syncState.resetOriginalValues();

		return syncState;
	}

	/**
	 * Returns the sync state with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the sync state
	 * @return the sync state
	 * @throws NoSuchSyncStateException if a sync state with the primary key could not be found
	 */
	@Override
	public SyncState findByPrimaryKey(Serializable primaryKey)
		throws NoSuchSyncStateException {

		SyncState syncState = fetchByPrimaryKey(primaryKey);

		if (syncState == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchSyncStateException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return syncState;
	}

	/**
	 * Returns the sync state with the primary key or throws a <code>NoSuchSyncStateException</code> if it could not be found.
	 *
	 * @param syncStateId the primary key of the sync state
	 * @return the sync state
	 * @throws NoSuchSyncStateException if a sync state with the primary key could not be found
	 */
	@Override
	public SyncState findByPrimaryKey(long syncStateId)
		throws NoSuchSyncStateException {

		return findByPrimaryKey((Serializable)syncStateId);
	}

	/**
	 * Returns the sync state with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the sync state
	 * @return the sync state, or <code>null</code> if a sync state with the primary key could not be found
	 */
	@Override
	public SyncState fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			SyncStateModelImpl.ENTITY_CACHE_ENABLED, SyncStateImpl.class,
			primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		SyncState syncState = (SyncState)serializable;

		if (syncState == null) {
			Session session = null;

			try {
				session = openSession();

				syncState = (SyncState)session.get(
					SyncStateImpl.class, primaryKey);

				if (syncState != null) {
					cacheResult(syncState);
				}
				else {
					entityCache.putResult(
						SyncStateModelImpl.ENTITY_CACHE_ENABLED,
						SyncStateImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					SyncStateModelImpl.ENTITY_CACHE_ENABLED,
					SyncStateImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return syncState;
	}

	/**
	 * Returns the sync state with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param syncStateId the primary key of the sync state
	 * @return the sync state, or <code>null</code> if a sync state with the primary key could not be found
	 */
	@Override
	public SyncState fetchByPrimaryKey(long syncStateId) {
		return fetchByPrimaryKey((Serializable)syncStateId);
	}

	@Override
	public Map<Serializable, SyncState> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, SyncState> map =
			new HashMap<Serializable, SyncState>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			SyncState syncState = fetchByPrimaryKey(primaryKey);

			if (syncState != null) {
				map.put(primaryKey, syncState);
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
				SyncStateModelImpl.ENTITY_CACHE_ENABLED, SyncStateImpl.class,
				primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (SyncState)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_SYNCSTATE_WHERE_PKS_IN);

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

			for (SyncState syncState : (List<SyncState>)query.list()) {
				map.put(syncState.getPrimaryKeyObj(), syncState);

				cacheResult(syncState);

				uncachedPrimaryKeys.remove(syncState.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					SyncStateModelImpl.ENTITY_CACHE_ENABLED,
					SyncStateImpl.class, primaryKey, nullModel);
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
	 * Returns all the sync states.
	 *
	 * @return the sync states
	 */
	@Override
	public List<SyncState> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sync states.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncStateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sync states
	 * @param end the upper bound of the range of sync states (not inclusive)
	 * @return the range of sync states
	 */
	@Override
	public List<SyncState> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the sync states.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncStateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sync states
	 * @param end the upper bound of the range of sync states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of sync states
	 */
	@Override
	public List<SyncState> findAll(
		int start, int end, OrderByComparator<SyncState> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sync states.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncStateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sync states
	 * @param end the upper bound of the range of sync states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of sync states
	 */
	@Override
	public List<SyncState> findAll(
		int start, int end, OrderByComparator<SyncState> orderByComparator,
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

		List<SyncState> list = null;

		if (useFinderCache) {
			list = (List<SyncState>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_SYNCSTATE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_SYNCSTATE;

				sql = sql.concat(SyncStateModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<SyncState>)QueryUtil.list(
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
	 * Removes all the sync states from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SyncState syncState : findAll()) {
			remove(syncState);
		}
	}

	/**
	 * Returns the number of sync states.
	 *
	 * @return the number of sync states
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_SYNCSTATE);

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
		return SyncStateModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the sync state persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			SyncStateModelImpl.ENTITY_CACHE_ENABLED,
			SyncStateModelImpl.FINDER_CACHE_ENABLED, SyncStateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			SyncStateModelImpl.ENTITY_CACHE_ENABLED,
			SyncStateModelImpl.FINDER_CACHE_ENABLED, SyncStateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			SyncStateModelImpl.ENTITY_CACHE_ENABLED,
			SyncStateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByModelName = new FinderPath(
			SyncStateModelImpl.ENTITY_CACHE_ENABLED,
			SyncStateModelImpl.FINDER_CACHE_ENABLED, SyncStateImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByModelName",
			new String[] {String.class.getName()},
			SyncStateModelImpl.MODELNAME_COLUMN_BITMASK);

		_finderPathCountByModelName = new FinderPath(
			SyncStateModelImpl.ENTITY_CACHE_ENABLED,
			SyncStateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByModelName",
			new String[] {String.class.getName()});

		SyncStateUtil.setPersistence(this);
	}

	public void destroy() {
		SyncStateUtil.setPersistence(null);

		entityCache.removeCache(SyncStateImpl.class.getName());

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

	private static final String _SQL_SELECT_SYNCSTATE =
		"SELECT syncState FROM SyncState syncState";

	private static final String _SQL_SELECT_SYNCSTATE_WHERE_PKS_IN =
		"SELECT syncState FROM SyncState syncState WHERE syncStateId IN (";

	private static final String _SQL_SELECT_SYNCSTATE_WHERE =
		"SELECT syncState FROM SyncState syncState WHERE ";

	private static final String _SQL_COUNT_SYNCSTATE =
		"SELECT COUNT(syncState) FROM SyncState syncState";

	private static final String _SQL_COUNT_SYNCSTATE_WHERE =
		"SELECT COUNT(syncState) FROM SyncState syncState WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "syncState.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SyncState exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SyncState exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SyncStatePersistenceImpl.class);

}