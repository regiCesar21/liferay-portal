/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.loop.service.persistence.impl;

import com.liferay.osb.loop.exception.NoSuchLoopUserNotificationRecordException;
import com.liferay.osb.loop.model.LoopUserNotificationRecord;
import com.liferay.osb.loop.model.impl.LoopUserNotificationRecordImpl;
import com.liferay.osb.loop.model.impl.LoopUserNotificationRecordModelImpl;
import com.liferay.osb.loop.service.persistence.LoopUserNotificationRecordPersistence;
import com.liferay.osb.loop.service.persistence.LoopUserNotificationRecordUtil;
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
 * The persistence implementation for the loop user notification record service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ethan Bustad
 * @generated
 */
public class LoopUserNotificationRecordPersistenceImpl
	extends BasePersistenceImpl<LoopUserNotificationRecord>
	implements LoopUserNotificationRecordPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LoopUserNotificationRecordUtil</code> to access the loop user notification record persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LoopUserNotificationRecordImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public LoopUserNotificationRecordPersistenceImpl() {
		setModelClass(LoopUserNotificationRecord.class);
	}

	/**
	 * Caches the loop user notification record in the entity cache if it is enabled.
	 *
	 * @param loopUserNotificationRecord the loop user notification record
	 */
	@Override
	public void cacheResult(
		LoopUserNotificationRecord loopUserNotificationRecord) {

		entityCache.putResult(
			LoopUserNotificationRecordModelImpl.ENTITY_CACHE_ENABLED,
			LoopUserNotificationRecordImpl.class,
			loopUserNotificationRecord.getPrimaryKey(),
			loopUserNotificationRecord);

		loopUserNotificationRecord.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the loop user notification records in the entity cache if it is enabled.
	 *
	 * @param loopUserNotificationRecords the loop user notification records
	 */
	@Override
	public void cacheResult(
		List<LoopUserNotificationRecord> loopUserNotificationRecords) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (loopUserNotificationRecords.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (LoopUserNotificationRecord loopUserNotificationRecord :
				loopUserNotificationRecords) {

			if (entityCache.getResult(
					LoopUserNotificationRecordModelImpl.ENTITY_CACHE_ENABLED,
					LoopUserNotificationRecordImpl.class,
					loopUserNotificationRecord.getPrimaryKey()) == null) {

				cacheResult(loopUserNotificationRecord);
			}
			else {
				loopUserNotificationRecord.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all loop user notification records.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LoopUserNotificationRecordImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the loop user notification record.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		LoopUserNotificationRecord loopUserNotificationRecord) {

		entityCache.removeResult(
			LoopUserNotificationRecordModelImpl.ENTITY_CACHE_ENABLED,
			LoopUserNotificationRecordImpl.class,
			loopUserNotificationRecord.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<LoopUserNotificationRecord> loopUserNotificationRecords) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LoopUserNotificationRecord loopUserNotificationRecord :
				loopUserNotificationRecords) {

			entityCache.removeResult(
				LoopUserNotificationRecordModelImpl.ENTITY_CACHE_ENABLED,
				LoopUserNotificationRecordImpl.class,
				loopUserNotificationRecord.getPrimaryKey());
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				LoopUserNotificationRecordModelImpl.ENTITY_CACHE_ENABLED,
				LoopUserNotificationRecordImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new loop user notification record with the primary key. Does not add the loop user notification record to the database.
	 *
	 * @param loopUserNotificationRecordId the primary key for the new loop user notification record
	 * @return the new loop user notification record
	 */
	@Override
	public LoopUserNotificationRecord create(
		long loopUserNotificationRecordId) {

		LoopUserNotificationRecord loopUserNotificationRecord =
			new LoopUserNotificationRecordImpl();

		loopUserNotificationRecord.setNew(true);
		loopUserNotificationRecord.setPrimaryKey(loopUserNotificationRecordId);

		return loopUserNotificationRecord;
	}

	/**
	 * Removes the loop user notification record with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param loopUserNotificationRecordId the primary key of the loop user notification record
	 * @return the loop user notification record that was removed
	 * @throws NoSuchLoopUserNotificationRecordException if a loop user notification record with the primary key could not be found
	 */
	@Override
	public LoopUserNotificationRecord remove(long loopUserNotificationRecordId)
		throws NoSuchLoopUserNotificationRecordException {

		return remove((Serializable)loopUserNotificationRecordId);
	}

	/**
	 * Removes the loop user notification record with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the loop user notification record
	 * @return the loop user notification record that was removed
	 * @throws NoSuchLoopUserNotificationRecordException if a loop user notification record with the primary key could not be found
	 */
	@Override
	public LoopUserNotificationRecord remove(Serializable primaryKey)
		throws NoSuchLoopUserNotificationRecordException {

		Session session = null;

		try {
			session = openSession();

			LoopUserNotificationRecord loopUserNotificationRecord =
				(LoopUserNotificationRecord)session.get(
					LoopUserNotificationRecordImpl.class, primaryKey);

			if (loopUserNotificationRecord == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLoopUserNotificationRecordException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(loopUserNotificationRecord);
		}
		catch (NoSuchLoopUserNotificationRecordException
					noSuchEntityException) {

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
	protected LoopUserNotificationRecord removeImpl(
		LoopUserNotificationRecord loopUserNotificationRecord) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(loopUserNotificationRecord)) {
				loopUserNotificationRecord =
					(LoopUserNotificationRecord)session.get(
						LoopUserNotificationRecordImpl.class,
						loopUserNotificationRecord.getPrimaryKeyObj());
			}

			if (loopUserNotificationRecord != null) {
				session.delete(loopUserNotificationRecord);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (loopUserNotificationRecord != null) {
			clearCache(loopUserNotificationRecord);
		}

		return loopUserNotificationRecord;
	}

	@Override
	public LoopUserNotificationRecord updateImpl(
		LoopUserNotificationRecord loopUserNotificationRecord) {

		boolean isNew = loopUserNotificationRecord.isNew();

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(loopUserNotificationRecord);

				loopUserNotificationRecord.setNew(false);
			}
			else {
				loopUserNotificationRecord =
					(LoopUserNotificationRecord)session.merge(
						loopUserNotificationRecord);
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
			LoopUserNotificationRecordModelImpl.ENTITY_CACHE_ENABLED,
			LoopUserNotificationRecordImpl.class,
			loopUserNotificationRecord.getPrimaryKey(),
			loopUserNotificationRecord, false);

		loopUserNotificationRecord.resetOriginalValues();

		return loopUserNotificationRecord;
	}

	/**
	 * Returns the loop user notification record with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the loop user notification record
	 * @return the loop user notification record
	 * @throws NoSuchLoopUserNotificationRecordException if a loop user notification record with the primary key could not be found
	 */
	@Override
	public LoopUserNotificationRecord findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLoopUserNotificationRecordException {

		LoopUserNotificationRecord loopUserNotificationRecord =
			fetchByPrimaryKey(primaryKey);

		if (loopUserNotificationRecord == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLoopUserNotificationRecordException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return loopUserNotificationRecord;
	}

	/**
	 * Returns the loop user notification record with the primary key or throws a <code>NoSuchLoopUserNotificationRecordException</code> if it could not be found.
	 *
	 * @param loopUserNotificationRecordId the primary key of the loop user notification record
	 * @return the loop user notification record
	 * @throws NoSuchLoopUserNotificationRecordException if a loop user notification record with the primary key could not be found
	 */
	@Override
	public LoopUserNotificationRecord findByPrimaryKey(
			long loopUserNotificationRecordId)
		throws NoSuchLoopUserNotificationRecordException {

		return findByPrimaryKey((Serializable)loopUserNotificationRecordId);
	}

	/**
	 * Returns the loop user notification record with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the loop user notification record
	 * @return the loop user notification record, or <code>null</code> if a loop user notification record with the primary key could not be found
	 */
	@Override
	public LoopUserNotificationRecord fetchByPrimaryKey(
		Serializable primaryKey) {

		Serializable serializable = entityCache.getResult(
			LoopUserNotificationRecordModelImpl.ENTITY_CACHE_ENABLED,
			LoopUserNotificationRecordImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		LoopUserNotificationRecord loopUserNotificationRecord =
			(LoopUserNotificationRecord)serializable;

		if (loopUserNotificationRecord == null) {
			Session session = null;

			try {
				session = openSession();

				loopUserNotificationRecord =
					(LoopUserNotificationRecord)session.get(
						LoopUserNotificationRecordImpl.class, primaryKey);

				if (loopUserNotificationRecord != null) {
					cacheResult(loopUserNotificationRecord);
				}
				else {
					entityCache.putResult(
						LoopUserNotificationRecordModelImpl.
							ENTITY_CACHE_ENABLED,
						LoopUserNotificationRecordImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					LoopUserNotificationRecordModelImpl.ENTITY_CACHE_ENABLED,
					LoopUserNotificationRecordImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return loopUserNotificationRecord;
	}

	/**
	 * Returns the loop user notification record with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param loopUserNotificationRecordId the primary key of the loop user notification record
	 * @return the loop user notification record, or <code>null</code> if a loop user notification record with the primary key could not be found
	 */
	@Override
	public LoopUserNotificationRecord fetchByPrimaryKey(
		long loopUserNotificationRecordId) {

		return fetchByPrimaryKey((Serializable)loopUserNotificationRecordId);
	}

	@Override
	public Map<Serializable, LoopUserNotificationRecord> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, LoopUserNotificationRecord> map =
			new HashMap<Serializable, LoopUserNotificationRecord>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			LoopUserNotificationRecord loopUserNotificationRecord =
				fetchByPrimaryKey(primaryKey);

			if (loopUserNotificationRecord != null) {
				map.put(primaryKey, loopUserNotificationRecord);
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
				LoopUserNotificationRecordModelImpl.ENTITY_CACHE_ENABLED,
				LoopUserNotificationRecordImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(
						primaryKey, (LoopUserNotificationRecord)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_LOOPUSERNOTIFICATIONRECORD_WHERE_PKS_IN);

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

			for (LoopUserNotificationRecord loopUserNotificationRecord :
					(List<LoopUserNotificationRecord>)query.list()) {

				map.put(
					loopUserNotificationRecord.getPrimaryKeyObj(),
					loopUserNotificationRecord);

				cacheResult(loopUserNotificationRecord);

				uncachedPrimaryKeys.remove(
					loopUserNotificationRecord.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					LoopUserNotificationRecordModelImpl.ENTITY_CACHE_ENABLED,
					LoopUserNotificationRecordImpl.class, primaryKey,
					nullModel);
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
	 * Returns all the loop user notification records.
	 *
	 * @return the loop user notification records
	 */
	@Override
	public List<LoopUserNotificationRecord> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the loop user notification records.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopUserNotificationRecordModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop user notification records
	 * @param end the upper bound of the range of loop user notification records (not inclusive)
	 * @return the range of loop user notification records
	 */
	@Override
	public List<LoopUserNotificationRecord> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the loop user notification records.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopUserNotificationRecordModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop user notification records
	 * @param end the upper bound of the range of loop user notification records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of loop user notification records
	 */
	@Override
	public List<LoopUserNotificationRecord> findAll(
		int start, int end,
		OrderByComparator<LoopUserNotificationRecord> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the loop user notification records.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopUserNotificationRecordModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop user notification records
	 * @param end the upper bound of the range of loop user notification records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of loop user notification records
	 */
	@Override
	public List<LoopUserNotificationRecord> findAll(
		int start, int end,
		OrderByComparator<LoopUserNotificationRecord> orderByComparator,
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

		List<LoopUserNotificationRecord> list = null;

		if (useFinderCache) {
			list = (List<LoopUserNotificationRecord>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_LOOPUSERNOTIFICATIONRECORD);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_LOOPUSERNOTIFICATIONRECORD;

				sql = sql.concat(
					LoopUserNotificationRecordModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<LoopUserNotificationRecord>)QueryUtil.list(
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
	 * Removes all the loop user notification records from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LoopUserNotificationRecord loopUserNotificationRecord :
				findAll()) {

			remove(loopUserNotificationRecord);
		}
	}

	/**
	 * Returns the number of loop user notification records.
	 *
	 * @return the number of loop user notification records
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
					_SQL_COUNT_LOOPUSERNOTIFICATIONRECORD);

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
		return LoopUserNotificationRecordModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the loop user notification record persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			LoopUserNotificationRecordModelImpl.ENTITY_CACHE_ENABLED,
			LoopUserNotificationRecordModelImpl.FINDER_CACHE_ENABLED,
			LoopUserNotificationRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			LoopUserNotificationRecordModelImpl.ENTITY_CACHE_ENABLED,
			LoopUserNotificationRecordModelImpl.FINDER_CACHE_ENABLED,
			LoopUserNotificationRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			LoopUserNotificationRecordModelImpl.ENTITY_CACHE_ENABLED,
			LoopUserNotificationRecordModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		LoopUserNotificationRecordUtil.setPersistence(this);
	}

	public void destroy() {
		LoopUserNotificationRecordUtil.setPersistence(null);

		entityCache.removeCache(LoopUserNotificationRecordImpl.class.getName());

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

	private static final String _SQL_SELECT_LOOPUSERNOTIFICATIONRECORD =
		"SELECT loopUserNotificationRecord FROM LoopUserNotificationRecord loopUserNotificationRecord";

	private static final String
		_SQL_SELECT_LOOPUSERNOTIFICATIONRECORD_WHERE_PKS_IN =
			"SELECT loopUserNotificationRecord FROM LoopUserNotificationRecord loopUserNotificationRecord WHERE loopUserNotificationRecordId IN (";

	private static final String _SQL_COUNT_LOOPUSERNOTIFICATIONRECORD =
		"SELECT COUNT(loopUserNotificationRecord) FROM LoopUserNotificationRecord loopUserNotificationRecord";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"loopUserNotificationRecord.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LoopUserNotificationRecord exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		LoopUserNotificationRecordPersistenceImpl.class);

}