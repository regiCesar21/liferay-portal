/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.loop.service.persistence.impl;

import com.liferay.osb.loop.exception.NoSuchLoopTopicAssignmentException;
import com.liferay.osb.loop.model.LoopTopicAssignment;
import com.liferay.osb.loop.model.impl.LoopTopicAssignmentImpl;
import com.liferay.osb.loop.model.impl.LoopTopicAssignmentModelImpl;
import com.liferay.osb.loop.service.persistence.LoopTopicAssignmentPersistence;
import com.liferay.osb.loop.service.persistence.LoopTopicAssignmentUtil;
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
 * The persistence implementation for the loop topic assignment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ethan Bustad
 * @generated
 */
public class LoopTopicAssignmentPersistenceImpl
	extends BasePersistenceImpl<LoopTopicAssignment>
	implements LoopTopicAssignmentPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LoopTopicAssignmentUtil</code> to access the loop topic assignment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LoopTopicAssignmentImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public LoopTopicAssignmentPersistenceImpl() {
		setModelClass(LoopTopicAssignment.class);
	}

	/**
	 * Caches the loop topic assignment in the entity cache if it is enabled.
	 *
	 * @param loopTopicAssignment the loop topic assignment
	 */
	@Override
	public void cacheResult(LoopTopicAssignment loopTopicAssignment) {
		entityCache.putResult(
			LoopTopicAssignmentModelImpl.ENTITY_CACHE_ENABLED,
			LoopTopicAssignmentImpl.class, loopTopicAssignment.getPrimaryKey(),
			loopTopicAssignment);

		loopTopicAssignment.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the loop topic assignments in the entity cache if it is enabled.
	 *
	 * @param loopTopicAssignments the loop topic assignments
	 */
	@Override
	public void cacheResult(List<LoopTopicAssignment> loopTopicAssignments) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (loopTopicAssignments.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (LoopTopicAssignment loopTopicAssignment : loopTopicAssignments) {
			if (entityCache.getResult(
					LoopTopicAssignmentModelImpl.ENTITY_CACHE_ENABLED,
					LoopTopicAssignmentImpl.class,
					loopTopicAssignment.getPrimaryKey()) == null) {

				cacheResult(loopTopicAssignment);
			}
			else {
				loopTopicAssignment.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all loop topic assignments.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LoopTopicAssignmentImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the loop topic assignment.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LoopTopicAssignment loopTopicAssignment) {
		entityCache.removeResult(
			LoopTopicAssignmentModelImpl.ENTITY_CACHE_ENABLED,
			LoopTopicAssignmentImpl.class, loopTopicAssignment.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<LoopTopicAssignment> loopTopicAssignments) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LoopTopicAssignment loopTopicAssignment : loopTopicAssignments) {
			entityCache.removeResult(
				LoopTopicAssignmentModelImpl.ENTITY_CACHE_ENABLED,
				LoopTopicAssignmentImpl.class,
				loopTopicAssignment.getPrimaryKey());
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				LoopTopicAssignmentModelImpl.ENTITY_CACHE_ENABLED,
				LoopTopicAssignmentImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new loop topic assignment with the primary key. Does not add the loop topic assignment to the database.
	 *
	 * @param loopTopicAssignmentId the primary key for the new loop topic assignment
	 * @return the new loop topic assignment
	 */
	@Override
	public LoopTopicAssignment create(long loopTopicAssignmentId) {
		LoopTopicAssignment loopTopicAssignment = new LoopTopicAssignmentImpl();

		loopTopicAssignment.setNew(true);
		loopTopicAssignment.setPrimaryKey(loopTopicAssignmentId);

		return loopTopicAssignment;
	}

	/**
	 * Removes the loop topic assignment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param loopTopicAssignmentId the primary key of the loop topic assignment
	 * @return the loop topic assignment that was removed
	 * @throws NoSuchLoopTopicAssignmentException if a loop topic assignment with the primary key could not be found
	 */
	@Override
	public LoopTopicAssignment remove(long loopTopicAssignmentId)
		throws NoSuchLoopTopicAssignmentException {

		return remove((Serializable)loopTopicAssignmentId);
	}

	/**
	 * Removes the loop topic assignment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the loop topic assignment
	 * @return the loop topic assignment that was removed
	 * @throws NoSuchLoopTopicAssignmentException if a loop topic assignment with the primary key could not be found
	 */
	@Override
	public LoopTopicAssignment remove(Serializable primaryKey)
		throws NoSuchLoopTopicAssignmentException {

		Session session = null;

		try {
			session = openSession();

			LoopTopicAssignment loopTopicAssignment =
				(LoopTopicAssignment)session.get(
					LoopTopicAssignmentImpl.class, primaryKey);

			if (loopTopicAssignment == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLoopTopicAssignmentException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(loopTopicAssignment);
		}
		catch (NoSuchLoopTopicAssignmentException noSuchEntityException) {
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
	protected LoopTopicAssignment removeImpl(
		LoopTopicAssignment loopTopicAssignment) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(loopTopicAssignment)) {
				loopTopicAssignment = (LoopTopicAssignment)session.get(
					LoopTopicAssignmentImpl.class,
					loopTopicAssignment.getPrimaryKeyObj());
			}

			if (loopTopicAssignment != null) {
				session.delete(loopTopicAssignment);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (loopTopicAssignment != null) {
			clearCache(loopTopicAssignment);
		}

		return loopTopicAssignment;
	}

	@Override
	public LoopTopicAssignment updateImpl(
		LoopTopicAssignment loopTopicAssignment) {

		boolean isNew = loopTopicAssignment.isNew();

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(loopTopicAssignment);

				loopTopicAssignment.setNew(false);
			}
			else {
				loopTopicAssignment = (LoopTopicAssignment)session.merge(
					loopTopicAssignment);
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
			LoopTopicAssignmentModelImpl.ENTITY_CACHE_ENABLED,
			LoopTopicAssignmentImpl.class, loopTopicAssignment.getPrimaryKey(),
			loopTopicAssignment, false);

		loopTopicAssignment.resetOriginalValues();

		return loopTopicAssignment;
	}

	/**
	 * Returns the loop topic assignment with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the loop topic assignment
	 * @return the loop topic assignment
	 * @throws NoSuchLoopTopicAssignmentException if a loop topic assignment with the primary key could not be found
	 */
	@Override
	public LoopTopicAssignment findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLoopTopicAssignmentException {

		LoopTopicAssignment loopTopicAssignment = fetchByPrimaryKey(primaryKey);

		if (loopTopicAssignment == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLoopTopicAssignmentException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return loopTopicAssignment;
	}

	/**
	 * Returns the loop topic assignment with the primary key or throws a <code>NoSuchLoopTopicAssignmentException</code> if it could not be found.
	 *
	 * @param loopTopicAssignmentId the primary key of the loop topic assignment
	 * @return the loop topic assignment
	 * @throws NoSuchLoopTopicAssignmentException if a loop topic assignment with the primary key could not be found
	 */
	@Override
	public LoopTopicAssignment findByPrimaryKey(long loopTopicAssignmentId)
		throws NoSuchLoopTopicAssignmentException {

		return findByPrimaryKey((Serializable)loopTopicAssignmentId);
	}

	/**
	 * Returns the loop topic assignment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the loop topic assignment
	 * @return the loop topic assignment, or <code>null</code> if a loop topic assignment with the primary key could not be found
	 */
	@Override
	public LoopTopicAssignment fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			LoopTopicAssignmentModelImpl.ENTITY_CACHE_ENABLED,
			LoopTopicAssignmentImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		LoopTopicAssignment loopTopicAssignment =
			(LoopTopicAssignment)serializable;

		if (loopTopicAssignment == null) {
			Session session = null;

			try {
				session = openSession();

				loopTopicAssignment = (LoopTopicAssignment)session.get(
					LoopTopicAssignmentImpl.class, primaryKey);

				if (loopTopicAssignment != null) {
					cacheResult(loopTopicAssignment);
				}
				else {
					entityCache.putResult(
						LoopTopicAssignmentModelImpl.ENTITY_CACHE_ENABLED,
						LoopTopicAssignmentImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					LoopTopicAssignmentModelImpl.ENTITY_CACHE_ENABLED,
					LoopTopicAssignmentImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return loopTopicAssignment;
	}

	/**
	 * Returns the loop topic assignment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param loopTopicAssignmentId the primary key of the loop topic assignment
	 * @return the loop topic assignment, or <code>null</code> if a loop topic assignment with the primary key could not be found
	 */
	@Override
	public LoopTopicAssignment fetchByPrimaryKey(long loopTopicAssignmentId) {
		return fetchByPrimaryKey((Serializable)loopTopicAssignmentId);
	}

	@Override
	public Map<Serializable, LoopTopicAssignment> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, LoopTopicAssignment> map =
			new HashMap<Serializable, LoopTopicAssignment>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			LoopTopicAssignment loopTopicAssignment = fetchByPrimaryKey(
				primaryKey);

			if (loopTopicAssignment != null) {
				map.put(primaryKey, loopTopicAssignment);
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
				LoopTopicAssignmentModelImpl.ENTITY_CACHE_ENABLED,
				LoopTopicAssignmentImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (LoopTopicAssignment)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_LOOPTOPICASSIGNMENT_WHERE_PKS_IN);

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

			for (LoopTopicAssignment loopTopicAssignment :
					(List<LoopTopicAssignment>)query.list()) {

				map.put(
					loopTopicAssignment.getPrimaryKeyObj(),
					loopTopicAssignment);

				cacheResult(loopTopicAssignment);

				uncachedPrimaryKeys.remove(
					loopTopicAssignment.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					LoopTopicAssignmentModelImpl.ENTITY_CACHE_ENABLED,
					LoopTopicAssignmentImpl.class, primaryKey, nullModel);
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
	 * Returns all the loop topic assignments.
	 *
	 * @return the loop topic assignments
	 */
	@Override
	public List<LoopTopicAssignment> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the loop topic assignments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopTopicAssignmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop topic assignments
	 * @param end the upper bound of the range of loop topic assignments (not inclusive)
	 * @return the range of loop topic assignments
	 */
	@Override
	public List<LoopTopicAssignment> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the loop topic assignments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopTopicAssignmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop topic assignments
	 * @param end the upper bound of the range of loop topic assignments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of loop topic assignments
	 */
	@Override
	public List<LoopTopicAssignment> findAll(
		int start, int end,
		OrderByComparator<LoopTopicAssignment> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the loop topic assignments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopTopicAssignmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop topic assignments
	 * @param end the upper bound of the range of loop topic assignments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of loop topic assignments
	 */
	@Override
	public List<LoopTopicAssignment> findAll(
		int start, int end,
		OrderByComparator<LoopTopicAssignment> orderByComparator,
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

		List<LoopTopicAssignment> list = null;

		if (useFinderCache) {
			list = (List<LoopTopicAssignment>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_LOOPTOPICASSIGNMENT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_LOOPTOPICASSIGNMENT;

				sql = sql.concat(LoopTopicAssignmentModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<LoopTopicAssignment>)QueryUtil.list(
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
	 * Removes all the loop topic assignments from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LoopTopicAssignment loopTopicAssignment : findAll()) {
			remove(loopTopicAssignment);
		}
	}

	/**
	 * Returns the number of loop topic assignments.
	 *
	 * @return the number of loop topic assignments
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
					_SQL_COUNT_LOOPTOPICASSIGNMENT);

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
		return LoopTopicAssignmentModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the loop topic assignment persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			LoopTopicAssignmentModelImpl.ENTITY_CACHE_ENABLED,
			LoopTopicAssignmentModelImpl.FINDER_CACHE_ENABLED,
			LoopTopicAssignmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			LoopTopicAssignmentModelImpl.ENTITY_CACHE_ENABLED,
			LoopTopicAssignmentModelImpl.FINDER_CACHE_ENABLED,
			LoopTopicAssignmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			LoopTopicAssignmentModelImpl.ENTITY_CACHE_ENABLED,
			LoopTopicAssignmentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		LoopTopicAssignmentUtil.setPersistence(this);
	}

	public void destroy() {
		LoopTopicAssignmentUtil.setPersistence(null);

		entityCache.removeCache(LoopTopicAssignmentImpl.class.getName());

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

	private static final String _SQL_SELECT_LOOPTOPICASSIGNMENT =
		"SELECT loopTopicAssignment FROM LoopTopicAssignment loopTopicAssignment";

	private static final String _SQL_SELECT_LOOPTOPICASSIGNMENT_WHERE_PKS_IN =
		"SELECT loopTopicAssignment FROM LoopTopicAssignment loopTopicAssignment WHERE loopTopicAssignmentId IN (";

	private static final String _SQL_COUNT_LOOPTOPICASSIGNMENT =
		"SELECT COUNT(loopTopicAssignment) FROM LoopTopicAssignment loopTopicAssignment";

	private static final String _ORDER_BY_ENTITY_ALIAS = "loopTopicAssignment.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LoopTopicAssignment exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		LoopTopicAssignmentPersistenceImpl.class);

}