/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.testray.service.persistence.impl;

import com.liferay.osb.testray.exception.NoSuchTestrayRoutineException;
import com.liferay.osb.testray.model.TestrayRoutine;
import com.liferay.osb.testray.model.impl.TestrayRoutineImpl;
import com.liferay.osb.testray.model.impl.TestrayRoutineModelImpl;
import com.liferay.osb.testray.service.persistence.TestrayRoutinePersistence;
import com.liferay.osb.testray.service.persistence.TestrayRoutineUtil;
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
 * The persistence implementation for the testray routine service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ethan Bustad
 * @generated
 */
public class TestrayRoutinePersistenceImpl
	extends BasePersistenceImpl<TestrayRoutine>
	implements TestrayRoutinePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>TestrayRoutineUtil</code> to access the testray routine persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		TestrayRoutineImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public TestrayRoutinePersistenceImpl() {
		setModelClass(TestrayRoutine.class);
	}

	/**
	 * Caches the testray routine in the entity cache if it is enabled.
	 *
	 * @param testrayRoutine the testray routine
	 */
	@Override
	public void cacheResult(TestrayRoutine testrayRoutine) {
		entityCache.putResult(
			TestrayRoutineModelImpl.ENTITY_CACHE_ENABLED,
			TestrayRoutineImpl.class, testrayRoutine.getPrimaryKey(),
			testrayRoutine);

		testrayRoutine.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the testray routines in the entity cache if it is enabled.
	 *
	 * @param testrayRoutines the testray routines
	 */
	@Override
	public void cacheResult(List<TestrayRoutine> testrayRoutines) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (testrayRoutines.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (TestrayRoutine testrayRoutine : testrayRoutines) {
			if (entityCache.getResult(
					TestrayRoutineModelImpl.ENTITY_CACHE_ENABLED,
					TestrayRoutineImpl.class, testrayRoutine.getPrimaryKey()) ==
						null) {

				cacheResult(testrayRoutine);
			}
			else {
				testrayRoutine.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all testray routines.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(TestrayRoutineImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the testray routine.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(TestrayRoutine testrayRoutine) {
		entityCache.removeResult(
			TestrayRoutineModelImpl.ENTITY_CACHE_ENABLED,
			TestrayRoutineImpl.class, testrayRoutine.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<TestrayRoutine> testrayRoutines) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (TestrayRoutine testrayRoutine : testrayRoutines) {
			entityCache.removeResult(
				TestrayRoutineModelImpl.ENTITY_CACHE_ENABLED,
				TestrayRoutineImpl.class, testrayRoutine.getPrimaryKey());
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				TestrayRoutineModelImpl.ENTITY_CACHE_ENABLED,
				TestrayRoutineImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new testray routine with the primary key. Does not add the testray routine to the database.
	 *
	 * @param testrayRoutineId the primary key for the new testray routine
	 * @return the new testray routine
	 */
	@Override
	public TestrayRoutine create(long testrayRoutineId) {
		TestrayRoutine testrayRoutine = new TestrayRoutineImpl();

		testrayRoutine.setNew(true);
		testrayRoutine.setPrimaryKey(testrayRoutineId);

		testrayRoutine.setCompanyId(CompanyThreadLocal.getCompanyId());

		return testrayRoutine;
	}

	/**
	 * Removes the testray routine with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param testrayRoutineId the primary key of the testray routine
	 * @return the testray routine that was removed
	 * @throws NoSuchTestrayRoutineException if a testray routine with the primary key could not be found
	 */
	@Override
	public TestrayRoutine remove(long testrayRoutineId)
		throws NoSuchTestrayRoutineException {

		return remove((Serializable)testrayRoutineId);
	}

	/**
	 * Removes the testray routine with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the testray routine
	 * @return the testray routine that was removed
	 * @throws NoSuchTestrayRoutineException if a testray routine with the primary key could not be found
	 */
	@Override
	public TestrayRoutine remove(Serializable primaryKey)
		throws NoSuchTestrayRoutineException {

		Session session = null;

		try {
			session = openSession();

			TestrayRoutine testrayRoutine = (TestrayRoutine)session.get(
				TestrayRoutineImpl.class, primaryKey);

			if (testrayRoutine == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTestrayRoutineException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(testrayRoutine);
		}
		catch (NoSuchTestrayRoutineException noSuchEntityException) {
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
	protected TestrayRoutine removeImpl(TestrayRoutine testrayRoutine) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(testrayRoutine)) {
				testrayRoutine = (TestrayRoutine)session.get(
					TestrayRoutineImpl.class,
					testrayRoutine.getPrimaryKeyObj());
			}

			if (testrayRoutine != null) {
				session.delete(testrayRoutine);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (testrayRoutine != null) {
			clearCache(testrayRoutine);
		}

		return testrayRoutine;
	}

	@Override
	public TestrayRoutine updateImpl(TestrayRoutine testrayRoutine) {
		boolean isNew = testrayRoutine.isNew();

		if (!(testrayRoutine instanceof TestrayRoutineModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(testrayRoutine.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					testrayRoutine);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in testrayRoutine proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom TestrayRoutine implementation " +
					testrayRoutine.getClass());
		}

		TestrayRoutineModelImpl testrayRoutineModelImpl =
			(TestrayRoutineModelImpl)testrayRoutine;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (testrayRoutine.getCreateDate() == null)) {
			if (serviceContext == null) {
				testrayRoutine.setCreateDate(date);
			}
			else {
				testrayRoutine.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!testrayRoutineModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				testrayRoutine.setModifiedDate(date);
			}
			else {
				testrayRoutine.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(testrayRoutine);

				testrayRoutine.setNew(false);
			}
			else {
				testrayRoutine = (TestrayRoutine)session.merge(testrayRoutine);
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
			TestrayRoutineModelImpl.ENTITY_CACHE_ENABLED,
			TestrayRoutineImpl.class, testrayRoutine.getPrimaryKey(),
			testrayRoutine, false);

		testrayRoutine.resetOriginalValues();

		return testrayRoutine;
	}

	/**
	 * Returns the testray routine with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray routine
	 * @return the testray routine
	 * @throws NoSuchTestrayRoutineException if a testray routine with the primary key could not be found
	 */
	@Override
	public TestrayRoutine findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTestrayRoutineException {

		TestrayRoutine testrayRoutine = fetchByPrimaryKey(primaryKey);

		if (testrayRoutine == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTestrayRoutineException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return testrayRoutine;
	}

	/**
	 * Returns the testray routine with the primary key or throws a <code>NoSuchTestrayRoutineException</code> if it could not be found.
	 *
	 * @param testrayRoutineId the primary key of the testray routine
	 * @return the testray routine
	 * @throws NoSuchTestrayRoutineException if a testray routine with the primary key could not be found
	 */
	@Override
	public TestrayRoutine findByPrimaryKey(long testrayRoutineId)
		throws NoSuchTestrayRoutineException {

		return findByPrimaryKey((Serializable)testrayRoutineId);
	}

	/**
	 * Returns the testray routine with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray routine
	 * @return the testray routine, or <code>null</code> if a testray routine with the primary key could not be found
	 */
	@Override
	public TestrayRoutine fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			TestrayRoutineModelImpl.ENTITY_CACHE_ENABLED,
			TestrayRoutineImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		TestrayRoutine testrayRoutine = (TestrayRoutine)serializable;

		if (testrayRoutine == null) {
			Session session = null;

			try {
				session = openSession();

				testrayRoutine = (TestrayRoutine)session.get(
					TestrayRoutineImpl.class, primaryKey);

				if (testrayRoutine != null) {
					cacheResult(testrayRoutine);
				}
				else {
					entityCache.putResult(
						TestrayRoutineModelImpl.ENTITY_CACHE_ENABLED,
						TestrayRoutineImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					TestrayRoutineModelImpl.ENTITY_CACHE_ENABLED,
					TestrayRoutineImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return testrayRoutine;
	}

	/**
	 * Returns the testray routine with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param testrayRoutineId the primary key of the testray routine
	 * @return the testray routine, or <code>null</code> if a testray routine with the primary key could not be found
	 */
	@Override
	public TestrayRoutine fetchByPrimaryKey(long testrayRoutineId) {
		return fetchByPrimaryKey((Serializable)testrayRoutineId);
	}

	@Override
	public Map<Serializable, TestrayRoutine> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, TestrayRoutine> map =
			new HashMap<Serializable, TestrayRoutine>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			TestrayRoutine testrayRoutine = fetchByPrimaryKey(primaryKey);

			if (testrayRoutine != null) {
				map.put(primaryKey, testrayRoutine);
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
				TestrayRoutineModelImpl.ENTITY_CACHE_ENABLED,
				TestrayRoutineImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (TestrayRoutine)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_TESTRAYROUTINE_WHERE_PKS_IN);

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

			for (TestrayRoutine testrayRoutine :
					(List<TestrayRoutine>)query.list()) {

				map.put(testrayRoutine.getPrimaryKeyObj(), testrayRoutine);

				cacheResult(testrayRoutine);

				uncachedPrimaryKeys.remove(testrayRoutine.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					TestrayRoutineModelImpl.ENTITY_CACHE_ENABLED,
					TestrayRoutineImpl.class, primaryKey, nullModel);
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
	 * Returns all the testray routines.
	 *
	 * @return the testray routines
	 */
	@Override
	public List<TestrayRoutine> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the testray routines.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayRoutineModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray routines
	 * @param end the upper bound of the range of testray routines (not inclusive)
	 * @return the range of testray routines
	 */
	@Override
	public List<TestrayRoutine> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray routines.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayRoutineModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray routines
	 * @param end the upper bound of the range of testray routines (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray routines
	 */
	@Override
	public List<TestrayRoutine> findAll(
		int start, int end,
		OrderByComparator<TestrayRoutine> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the testray routines.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayRoutineModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray routines
	 * @param end the upper bound of the range of testray routines (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of testray routines
	 */
	@Override
	public List<TestrayRoutine> findAll(
		int start, int end, OrderByComparator<TestrayRoutine> orderByComparator,
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

		List<TestrayRoutine> list = null;

		if (useFinderCache) {
			list = (List<TestrayRoutine>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_TESTRAYROUTINE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_TESTRAYROUTINE;

				sql = sql.concat(TestrayRoutineModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<TestrayRoutine>)QueryUtil.list(
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
	 * Removes all the testray routines from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (TestrayRoutine testrayRoutine : findAll()) {
			remove(testrayRoutine);
		}
	}

	/**
	 * Returns the number of testray routines.
	 *
	 * @return the number of testray routines
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_TESTRAYROUTINE);

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
		return TestrayRoutineModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the testray routine persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			TestrayRoutineModelImpl.ENTITY_CACHE_ENABLED,
			TestrayRoutineModelImpl.FINDER_CACHE_ENABLED,
			TestrayRoutineImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			TestrayRoutineModelImpl.ENTITY_CACHE_ENABLED,
			TestrayRoutineModelImpl.FINDER_CACHE_ENABLED,
			TestrayRoutineImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			TestrayRoutineModelImpl.ENTITY_CACHE_ENABLED,
			TestrayRoutineModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		TestrayRoutineUtil.setPersistence(this);
	}

	public void destroy() {
		TestrayRoutineUtil.setPersistence(null);

		entityCache.removeCache(TestrayRoutineImpl.class.getName());

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

	private static final String _SQL_SELECT_TESTRAYROUTINE =
		"SELECT testrayRoutine FROM TestrayRoutine testrayRoutine";

	private static final String _SQL_SELECT_TESTRAYROUTINE_WHERE_PKS_IN =
		"SELECT testrayRoutine FROM TestrayRoutine testrayRoutine WHERE testrayRoutineId IN (";

	private static final String _SQL_COUNT_TESTRAYROUTINE =
		"SELECT COUNT(testrayRoutine) FROM TestrayRoutine testrayRoutine";

	private static final String _ORDER_BY_ENTITY_ALIAS = "testrayRoutine.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No TestrayRoutine exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		TestrayRoutinePersistenceImpl.class);

}