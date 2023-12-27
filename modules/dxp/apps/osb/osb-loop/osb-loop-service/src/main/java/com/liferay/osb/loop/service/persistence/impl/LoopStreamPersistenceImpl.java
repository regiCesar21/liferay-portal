/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.loop.service.persistence.impl;

import com.liferay.osb.loop.exception.NoSuchLoopStreamException;
import com.liferay.osb.loop.model.LoopStream;
import com.liferay.osb.loop.model.impl.LoopStreamImpl;
import com.liferay.osb.loop.model.impl.LoopStreamModelImpl;
import com.liferay.osb.loop.service.persistence.LoopStreamPersistence;
import com.liferay.osb.loop.service.persistence.LoopStreamUtil;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the loop stream service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ethan Bustad
 * @generated
 */
public class LoopStreamPersistenceImpl
	extends BasePersistenceImpl<LoopStream> implements LoopStreamPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LoopStreamUtil</code> to access the loop stream persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LoopStreamImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public LoopStreamPersistenceImpl() {
		setModelClass(LoopStream.class);
	}

	/**
	 * Caches the loop stream in the entity cache if it is enabled.
	 *
	 * @param loopStream the loop stream
	 */
	@Override
	public void cacheResult(LoopStream loopStream) {
		entityCache.putResult(
			LoopStreamModelImpl.ENTITY_CACHE_ENABLED, LoopStreamImpl.class,
			loopStream.getPrimaryKey(), loopStream);

		loopStream.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the loop streams in the entity cache if it is enabled.
	 *
	 * @param loopStreams the loop streams
	 */
	@Override
	public void cacheResult(List<LoopStream> loopStreams) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (loopStreams.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (LoopStream loopStream : loopStreams) {
			if (entityCache.getResult(
					LoopStreamModelImpl.ENTITY_CACHE_ENABLED,
					LoopStreamImpl.class, loopStream.getPrimaryKey()) == null) {

				cacheResult(loopStream);
			}
			else {
				loopStream.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all loop streams.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LoopStreamImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the loop stream.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LoopStream loopStream) {
		entityCache.removeResult(
			LoopStreamModelImpl.ENTITY_CACHE_ENABLED, LoopStreamImpl.class,
			loopStream.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<LoopStream> loopStreams) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LoopStream loopStream : loopStreams) {
			entityCache.removeResult(
				LoopStreamModelImpl.ENTITY_CACHE_ENABLED, LoopStreamImpl.class,
				loopStream.getPrimaryKey());
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				LoopStreamModelImpl.ENTITY_CACHE_ENABLED, LoopStreamImpl.class,
				primaryKey);
		}
	}

	/**
	 * Creates a new loop stream with the primary key. Does not add the loop stream to the database.
	 *
	 * @param loopStreamId the primary key for the new loop stream
	 * @return the new loop stream
	 */
	@Override
	public LoopStream create(long loopStreamId) {
		LoopStream loopStream = new LoopStreamImpl();

		loopStream.setNew(true);
		loopStream.setPrimaryKey(loopStreamId);

		return loopStream;
	}

	/**
	 * Removes the loop stream with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param loopStreamId the primary key of the loop stream
	 * @return the loop stream that was removed
	 * @throws NoSuchLoopStreamException if a loop stream with the primary key could not be found
	 */
	@Override
	public LoopStream remove(long loopStreamId)
		throws NoSuchLoopStreamException {

		return remove((Serializable)loopStreamId);
	}

	/**
	 * Removes the loop stream with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the loop stream
	 * @return the loop stream that was removed
	 * @throws NoSuchLoopStreamException if a loop stream with the primary key could not be found
	 */
	@Override
	public LoopStream remove(Serializable primaryKey)
		throws NoSuchLoopStreamException {

		Session session = null;

		try {
			session = openSession();

			LoopStream loopStream = (LoopStream)session.get(
				LoopStreamImpl.class, primaryKey);

			if (loopStream == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLoopStreamException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(loopStream);
		}
		catch (NoSuchLoopStreamException noSuchEntityException) {
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
	protected LoopStream removeImpl(LoopStream loopStream) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(loopStream)) {
				loopStream = (LoopStream)session.get(
					LoopStreamImpl.class, loopStream.getPrimaryKeyObj());
			}

			if (loopStream != null) {
				session.delete(loopStream);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (loopStream != null) {
			clearCache(loopStream);
		}

		return loopStream;
	}

	@Override
	public LoopStream updateImpl(LoopStream loopStream) {
		boolean isNew = loopStream.isNew();

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(loopStream);

				loopStream.setNew(false);
			}
			else {
				loopStream = (LoopStream)session.merge(loopStream);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			LoopStreamModelImpl.ENTITY_CACHE_ENABLED, LoopStreamImpl.class,
			loopStream.getPrimaryKey(), loopStream, false);

		loopStream.resetOriginalValues();

		return loopStream;
	}

	/**
	 * Returns the loop stream with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the loop stream
	 * @return the loop stream
	 * @throws NoSuchLoopStreamException if a loop stream with the primary key could not be found
	 */
	@Override
	public LoopStream findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLoopStreamException {

		LoopStream loopStream = fetchByPrimaryKey(primaryKey);

		if (loopStream == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLoopStreamException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return loopStream;
	}

	/**
	 * Returns the loop stream with the primary key or throws a <code>NoSuchLoopStreamException</code> if it could not be found.
	 *
	 * @param loopStreamId the primary key of the loop stream
	 * @return the loop stream
	 * @throws NoSuchLoopStreamException if a loop stream with the primary key could not be found
	 */
	@Override
	public LoopStream findByPrimaryKey(long loopStreamId)
		throws NoSuchLoopStreamException {

		return findByPrimaryKey((Serializable)loopStreamId);
	}

	/**
	 * Returns the loop stream with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the loop stream
	 * @return the loop stream, or <code>null</code> if a loop stream with the primary key could not be found
	 */
	@Override
	public LoopStream fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			LoopStreamModelImpl.ENTITY_CACHE_ENABLED, LoopStreamImpl.class,
			primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		LoopStream loopStream = (LoopStream)serializable;

		if (loopStream == null) {
			Session session = null;

			try {
				session = openSession();

				loopStream = (LoopStream)session.get(
					LoopStreamImpl.class, primaryKey);

				if (loopStream != null) {
					cacheResult(loopStream);
				}
				else {
					entityCache.putResult(
						LoopStreamModelImpl.ENTITY_CACHE_ENABLED,
						LoopStreamImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					LoopStreamModelImpl.ENTITY_CACHE_ENABLED,
					LoopStreamImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return loopStream;
	}

	/**
	 * Returns the loop stream with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param loopStreamId the primary key of the loop stream
	 * @return the loop stream, or <code>null</code> if a loop stream with the primary key could not be found
	 */
	@Override
	public LoopStream fetchByPrimaryKey(long loopStreamId) {
		return fetchByPrimaryKey((Serializable)loopStreamId);
	}

	@Override
	public Map<Serializable, LoopStream> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, LoopStream> map =
			new HashMap<Serializable, LoopStream>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			LoopStream loopStream = fetchByPrimaryKey(primaryKey);

			if (loopStream != null) {
				map.put(primaryKey, loopStream);
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
				LoopStreamModelImpl.ENTITY_CACHE_ENABLED, LoopStreamImpl.class,
				primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (LoopStream)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_LOOPSTREAM_WHERE_PKS_IN);

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

			for (LoopStream loopStream : (List<LoopStream>)query.list()) {
				map.put(loopStream.getPrimaryKeyObj(), loopStream);

				cacheResult(loopStream);

				uncachedPrimaryKeys.remove(loopStream.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					LoopStreamModelImpl.ENTITY_CACHE_ENABLED,
					LoopStreamImpl.class, primaryKey, nullModel);
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
	 * Returns all the loop streams.
	 *
	 * @return the loop streams
	 */
	@Override
	public List<LoopStream> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the loop streams.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopStreamModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop streams
	 * @param end the upper bound of the range of loop streams (not inclusive)
	 * @return the range of loop streams
	 */
	@Override
	public List<LoopStream> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the loop streams.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopStreamModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop streams
	 * @param end the upper bound of the range of loop streams (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of loop streams
	 */
	@Override
	public List<LoopStream> findAll(
		int start, int end, OrderByComparator<LoopStream> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the loop streams.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopStreamModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop streams
	 * @param end the upper bound of the range of loop streams (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of loop streams
	 */
	@Override
	public List<LoopStream> findAll(
		int start, int end, OrderByComparator<LoopStream> orderByComparator,
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

		List<LoopStream> list = null;

		if (useFinderCache) {
			list = (List<LoopStream>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_LOOPSTREAM);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_LOOPSTREAM;

				sql = sql.concat(LoopStreamModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<LoopStream>)QueryUtil.list(
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
	 * Removes all the loop streams from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LoopStream loopStream : findAll()) {
			remove(loopStream);
		}
	}

	/**
	 * Returns the number of loop streams.
	 *
	 * @return the number of loop streams
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_LOOPSTREAM);

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
		return LoopStreamModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the loop stream persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			LoopStreamModelImpl.ENTITY_CACHE_ENABLED,
			LoopStreamModelImpl.FINDER_CACHE_ENABLED, LoopStreamImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			LoopStreamModelImpl.ENTITY_CACHE_ENABLED,
			LoopStreamModelImpl.FINDER_CACHE_ENABLED, LoopStreamImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			LoopStreamModelImpl.ENTITY_CACHE_ENABLED,
			LoopStreamModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		LoopStreamUtil.setPersistence(this);
	}

	public void destroy() {
		LoopStreamUtil.setPersistence(null);

		entityCache.removeCache(LoopStreamImpl.class.getName());

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

	private static final String _SQL_SELECT_LOOPSTREAM =
		"SELECT loopStream FROM LoopStream loopStream";

	private static final String _SQL_SELECT_LOOPSTREAM_WHERE_PKS_IN =
		"SELECT loopStream FROM LoopStream loopStream WHERE loopStreamId IN (";

	private static final String _SQL_COUNT_LOOPSTREAM =
		"SELECT COUNT(loopStream) FROM LoopStream loopStream";

	private static final String _ORDER_BY_ENTITY_ALIAS = "loopStream.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LoopStream exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		LoopStreamPersistenceImpl.class);

}