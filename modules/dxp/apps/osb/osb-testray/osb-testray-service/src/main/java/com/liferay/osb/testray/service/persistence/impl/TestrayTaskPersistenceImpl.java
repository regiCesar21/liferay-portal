/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.testray.service.persistence.impl;

import com.liferay.osb.testray.exception.NoSuchTestrayTaskException;
import com.liferay.osb.testray.model.TestrayTask;
import com.liferay.osb.testray.model.impl.TestrayTaskImpl;
import com.liferay.osb.testray.model.impl.TestrayTaskModelImpl;
import com.liferay.osb.testray.service.persistence.TestrayCaseTypePersistence;
import com.liferay.osb.testray.service.persistence.TestrayTaskPersistence;
import com.liferay.osb.testray.service.persistence.TestrayTaskUtil;
import com.liferay.portal.kernel.bean.BeanReference;
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

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the testray task service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ethan Bustad
 * @generated
 */
public class TestrayTaskPersistenceImpl
	extends BasePersistenceImpl<TestrayTask> implements TestrayTaskPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>TestrayTaskUtil</code> to access the testray task persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		TestrayTaskImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public TestrayTaskPersistenceImpl() {
		setModelClass(TestrayTask.class);
	}

	/**
	 * Caches the testray task in the entity cache if it is enabled.
	 *
	 * @param testrayTask the testray task
	 */
	@Override
	public void cacheResult(TestrayTask testrayTask) {
		entityCache.putResult(
			TestrayTaskModelImpl.ENTITY_CACHE_ENABLED, TestrayTaskImpl.class,
			testrayTask.getPrimaryKey(), testrayTask);

		testrayTask.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the testray tasks in the entity cache if it is enabled.
	 *
	 * @param testrayTasks the testray tasks
	 */
	@Override
	public void cacheResult(List<TestrayTask> testrayTasks) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (testrayTasks.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (TestrayTask testrayTask : testrayTasks) {
			if (entityCache.getResult(
					TestrayTaskModelImpl.ENTITY_CACHE_ENABLED,
					TestrayTaskImpl.class, testrayTask.getPrimaryKey()) ==
						null) {

				cacheResult(testrayTask);
			}
			else {
				testrayTask.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all testray tasks.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(TestrayTaskImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the testray task.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(TestrayTask testrayTask) {
		entityCache.removeResult(
			TestrayTaskModelImpl.ENTITY_CACHE_ENABLED, TestrayTaskImpl.class,
			testrayTask.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<TestrayTask> testrayTasks) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (TestrayTask testrayTask : testrayTasks) {
			entityCache.removeResult(
				TestrayTaskModelImpl.ENTITY_CACHE_ENABLED,
				TestrayTaskImpl.class, testrayTask.getPrimaryKey());
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				TestrayTaskModelImpl.ENTITY_CACHE_ENABLED,
				TestrayTaskImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new testray task with the primary key. Does not add the testray task to the database.
	 *
	 * @param testrayTaskId the primary key for the new testray task
	 * @return the new testray task
	 */
	@Override
	public TestrayTask create(long testrayTaskId) {
		TestrayTask testrayTask = new TestrayTaskImpl();

		testrayTask.setNew(true);
		testrayTask.setPrimaryKey(testrayTaskId);

		testrayTask.setCompanyId(CompanyThreadLocal.getCompanyId());

		return testrayTask;
	}

	/**
	 * Removes the testray task with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param testrayTaskId the primary key of the testray task
	 * @return the testray task that was removed
	 * @throws NoSuchTestrayTaskException if a testray task with the primary key could not be found
	 */
	@Override
	public TestrayTask remove(long testrayTaskId)
		throws NoSuchTestrayTaskException {

		return remove((Serializable)testrayTaskId);
	}

	/**
	 * Removes the testray task with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the testray task
	 * @return the testray task that was removed
	 * @throws NoSuchTestrayTaskException if a testray task with the primary key could not be found
	 */
	@Override
	public TestrayTask remove(Serializable primaryKey)
		throws NoSuchTestrayTaskException {

		Session session = null;

		try {
			session = openSession();

			TestrayTask testrayTask = (TestrayTask)session.get(
				TestrayTaskImpl.class, primaryKey);

			if (testrayTask == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTestrayTaskException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(testrayTask);
		}
		catch (NoSuchTestrayTaskException noSuchEntityException) {
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
	protected TestrayTask removeImpl(TestrayTask testrayTask) {
		testrayTaskToTestrayCaseTypeTableMapper.
			deleteLeftPrimaryKeyTableMappings(testrayTask.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(testrayTask)) {
				testrayTask = (TestrayTask)session.get(
					TestrayTaskImpl.class, testrayTask.getPrimaryKeyObj());
			}

			if (testrayTask != null) {
				session.delete(testrayTask);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (testrayTask != null) {
			clearCache(testrayTask);
		}

		return testrayTask;
	}

	@Override
	public TestrayTask updateImpl(TestrayTask testrayTask) {
		boolean isNew = testrayTask.isNew();

		if (!(testrayTask instanceof TestrayTaskModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(testrayTask.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(testrayTask);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in testrayTask proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom TestrayTask implementation " +
					testrayTask.getClass());
		}

		TestrayTaskModelImpl testrayTaskModelImpl =
			(TestrayTaskModelImpl)testrayTask;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (testrayTask.getCreateDate() == null)) {
			if (serviceContext == null) {
				testrayTask.setCreateDate(date);
			}
			else {
				testrayTask.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!testrayTaskModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				testrayTask.setModifiedDate(date);
			}
			else {
				testrayTask.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(testrayTask);

				testrayTask.setNew(false);
			}
			else {
				testrayTask = (TestrayTask)session.merge(testrayTask);
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
			TestrayTaskModelImpl.ENTITY_CACHE_ENABLED, TestrayTaskImpl.class,
			testrayTask.getPrimaryKey(), testrayTask, false);

		testrayTask.resetOriginalValues();

		return testrayTask;
	}

	/**
	 * Returns the testray task with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray task
	 * @return the testray task
	 * @throws NoSuchTestrayTaskException if a testray task with the primary key could not be found
	 */
	@Override
	public TestrayTask findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTestrayTaskException {

		TestrayTask testrayTask = fetchByPrimaryKey(primaryKey);

		if (testrayTask == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTestrayTaskException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return testrayTask;
	}

	/**
	 * Returns the testray task with the primary key or throws a <code>NoSuchTestrayTaskException</code> if it could not be found.
	 *
	 * @param testrayTaskId the primary key of the testray task
	 * @return the testray task
	 * @throws NoSuchTestrayTaskException if a testray task with the primary key could not be found
	 */
	@Override
	public TestrayTask findByPrimaryKey(long testrayTaskId)
		throws NoSuchTestrayTaskException {

		return findByPrimaryKey((Serializable)testrayTaskId);
	}

	/**
	 * Returns the testray task with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray task
	 * @return the testray task, or <code>null</code> if a testray task with the primary key could not be found
	 */
	@Override
	public TestrayTask fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			TestrayTaskModelImpl.ENTITY_CACHE_ENABLED, TestrayTaskImpl.class,
			primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		TestrayTask testrayTask = (TestrayTask)serializable;

		if (testrayTask == null) {
			Session session = null;

			try {
				session = openSession();

				testrayTask = (TestrayTask)session.get(
					TestrayTaskImpl.class, primaryKey);

				if (testrayTask != null) {
					cacheResult(testrayTask);
				}
				else {
					entityCache.putResult(
						TestrayTaskModelImpl.ENTITY_CACHE_ENABLED,
						TestrayTaskImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					TestrayTaskModelImpl.ENTITY_CACHE_ENABLED,
					TestrayTaskImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return testrayTask;
	}

	/**
	 * Returns the testray task with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param testrayTaskId the primary key of the testray task
	 * @return the testray task, or <code>null</code> if a testray task with the primary key could not be found
	 */
	@Override
	public TestrayTask fetchByPrimaryKey(long testrayTaskId) {
		return fetchByPrimaryKey((Serializable)testrayTaskId);
	}

	@Override
	public Map<Serializable, TestrayTask> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, TestrayTask> map =
			new HashMap<Serializable, TestrayTask>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			TestrayTask testrayTask = fetchByPrimaryKey(primaryKey);

			if (testrayTask != null) {
				map.put(primaryKey, testrayTask);
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
				TestrayTaskModelImpl.ENTITY_CACHE_ENABLED,
				TestrayTaskImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (TestrayTask)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_TESTRAYTASK_WHERE_PKS_IN);

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

			for (TestrayTask testrayTask : (List<TestrayTask>)query.list()) {
				map.put(testrayTask.getPrimaryKeyObj(), testrayTask);

				cacheResult(testrayTask);

				uncachedPrimaryKeys.remove(testrayTask.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					TestrayTaskModelImpl.ENTITY_CACHE_ENABLED,
					TestrayTaskImpl.class, primaryKey, nullModel);
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
	 * Returns all the testray tasks.
	 *
	 * @return the testray tasks
	 */
	@Override
	public List<TestrayTask> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the testray tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray tasks
	 * @param end the upper bound of the range of testray tasks (not inclusive)
	 * @return the range of testray tasks
	 */
	@Override
	public List<TestrayTask> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray tasks
	 * @param end the upper bound of the range of testray tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray tasks
	 */
	@Override
	public List<TestrayTask> findAll(
		int start, int end, OrderByComparator<TestrayTask> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the testray tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray tasks
	 * @param end the upper bound of the range of testray tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of testray tasks
	 */
	@Override
	public List<TestrayTask> findAll(
		int start, int end, OrderByComparator<TestrayTask> orderByComparator,
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

		List<TestrayTask> list = null;

		if (useFinderCache) {
			list = (List<TestrayTask>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_TESTRAYTASK);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_TESTRAYTASK;

				sql = sql.concat(TestrayTaskModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<TestrayTask>)QueryUtil.list(
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
	 * Removes all the testray tasks from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (TestrayTask testrayTask : findAll()) {
			remove(testrayTask);
		}
	}

	/**
	 * Returns the number of testray tasks.
	 *
	 * @return the number of testray tasks
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_TESTRAYTASK);

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
	 * Returns the primaryKeys of testray case types associated with the testray task.
	 *
	 * @param pk the primary key of the testray task
	 * @return long[] of the primaryKeys of testray case types associated with the testray task
	 */
	@Override
	public long[] getTestrayCaseTypePrimaryKeys(long pk) {
		long[] pks =
			testrayTaskToTestrayCaseTypeTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the testray case types associated with the testray task.
	 *
	 * @param pk the primary key of the testray task
	 * @return the testray case types associated with the testray task
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayCaseType>
		getTestrayCaseTypes(long pk) {

		return getTestrayCaseTypes(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the testray case types associated with the testray task.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayTaskModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray task
	 * @param start the lower bound of the range of testray tasks
	 * @param end the upper bound of the range of testray tasks (not inclusive)
	 * @return the range of testray case types associated with the testray task
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayCaseType>
		getTestrayCaseTypes(long pk, int start, int end) {

		return getTestrayCaseTypes(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray case types associated with the testray task.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayTaskModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray task
	 * @param start the lower bound of the range of testray tasks
	 * @param end the upper bound of the range of testray tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray case types associated with the testray task
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayCaseType>
		getTestrayCaseTypes(
			long pk, int start, int end,
			OrderByComparator<com.liferay.osb.testray.model.TestrayCaseType>
				orderByComparator) {

		return testrayTaskToTestrayCaseTypeTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of testray case types associated with the testray task.
	 *
	 * @param pk the primary key of the testray task
	 * @return the number of testray case types associated with the testray task
	 */
	@Override
	public int getTestrayCaseTypesSize(long pk) {
		long[] pks =
			testrayTaskToTestrayCaseTypeTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the testray case type is associated with the testray task.
	 *
	 * @param pk the primary key of the testray task
	 * @param testrayCaseTypePK the primary key of the testray case type
	 * @return <code>true</code> if the testray case type is associated with the testray task; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTestrayCaseType(long pk, long testrayCaseTypePK) {
		return testrayTaskToTestrayCaseTypeTableMapper.containsTableMapping(
			pk, testrayCaseTypePK);
	}

	/**
	 * Returns <code>true</code> if the testray task has any testray case types associated with it.
	 *
	 * @param pk the primary key of the testray task to check for associations with testray case types
	 * @return <code>true</code> if the testray task has any testray case types associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTestrayCaseTypes(long pk) {
		if (getTestrayCaseTypesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the testray task and the testray case type. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray task
	 * @param testrayCaseTypePK the primary key of the testray case type
	 */
	@Override
	public void addTestrayCaseType(long pk, long testrayCaseTypePK) {
		TestrayTask testrayTask = fetchByPrimaryKey(pk);

		if (testrayTask == null) {
			testrayTaskToTestrayCaseTypeTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, testrayCaseTypePK);
		}
		else {
			testrayTaskToTestrayCaseTypeTableMapper.addTableMapping(
				testrayTask.getCompanyId(), pk, testrayCaseTypePK);
		}
	}

	/**
	 * Adds an association between the testray task and the testray case type. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray task
	 * @param testrayCaseType the testray case type
	 */
	@Override
	public void addTestrayCaseType(
		long pk,
		com.liferay.osb.testray.model.TestrayCaseType testrayCaseType) {

		TestrayTask testrayTask = fetchByPrimaryKey(pk);

		if (testrayTask == null) {
			testrayTaskToTestrayCaseTypeTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				testrayCaseType.getPrimaryKey());
		}
		else {
			testrayTaskToTestrayCaseTypeTableMapper.addTableMapping(
				testrayTask.getCompanyId(), pk,
				testrayCaseType.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the testray task and the testray case types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray task
	 * @param testrayCaseTypePKs the primary keys of the testray case types
	 */
	@Override
	public void addTestrayCaseTypes(long pk, long[] testrayCaseTypePKs) {
		long companyId = 0;

		TestrayTask testrayTask = fetchByPrimaryKey(pk);

		if (testrayTask == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testrayTask.getCompanyId();
		}

		testrayTaskToTestrayCaseTypeTableMapper.addTableMappings(
			companyId, pk, testrayCaseTypePKs);
	}

	/**
	 * Adds an association between the testray task and the testray case types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray task
	 * @param testrayCaseTypes the testray case types
	 */
	@Override
	public void addTestrayCaseTypes(
		long pk,
		List<com.liferay.osb.testray.model.TestrayCaseType> testrayCaseTypes) {

		addTestrayCaseTypes(
			pk,
			ListUtil.toLongArray(
				testrayCaseTypes,
				com.liferay.osb.testray.model.TestrayCaseType.
					TESTRAY_CASE_TYPE_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the testray task and its testray case types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray task to clear the associated testray case types from
	 */
	@Override
	public void clearTestrayCaseTypes(long pk) {
		testrayTaskToTestrayCaseTypeTableMapper.
			deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the testray task and the testray case type. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray task
	 * @param testrayCaseTypePK the primary key of the testray case type
	 */
	@Override
	public void removeTestrayCaseType(long pk, long testrayCaseTypePK) {
		testrayTaskToTestrayCaseTypeTableMapper.deleteTableMapping(
			pk, testrayCaseTypePK);
	}

	/**
	 * Removes the association between the testray task and the testray case type. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray task
	 * @param testrayCaseType the testray case type
	 */
	@Override
	public void removeTestrayCaseType(
		long pk,
		com.liferay.osb.testray.model.TestrayCaseType testrayCaseType) {

		testrayTaskToTestrayCaseTypeTableMapper.deleteTableMapping(
			pk, testrayCaseType.getPrimaryKey());
	}

	/**
	 * Removes the association between the testray task and the testray case types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray task
	 * @param testrayCaseTypePKs the primary keys of the testray case types
	 */
	@Override
	public void removeTestrayCaseTypes(long pk, long[] testrayCaseTypePKs) {
		testrayTaskToTestrayCaseTypeTableMapper.deleteTableMappings(
			pk, testrayCaseTypePKs);
	}

	/**
	 * Removes the association between the testray task and the testray case types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray task
	 * @param testrayCaseTypes the testray case types
	 */
	@Override
	public void removeTestrayCaseTypes(
		long pk,
		List<com.liferay.osb.testray.model.TestrayCaseType> testrayCaseTypes) {

		removeTestrayCaseTypes(
			pk,
			ListUtil.toLongArray(
				testrayCaseTypes,
				com.liferay.osb.testray.model.TestrayCaseType.
					TESTRAY_CASE_TYPE_ID_ACCESSOR));
	}

	/**
	 * Sets the testray case types associated with the testray task, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray task
	 * @param testrayCaseTypePKs the primary keys of the testray case types to be associated with the testray task
	 */
	@Override
	public void setTestrayCaseTypes(long pk, long[] testrayCaseTypePKs) {
		Set<Long> newTestrayCaseTypePKsSet = SetUtil.fromArray(
			testrayCaseTypePKs);
		Set<Long> oldTestrayCaseTypePKsSet = SetUtil.fromArray(
			testrayTaskToTestrayCaseTypeTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeTestrayCaseTypePKsSet = new HashSet<Long>(
			oldTestrayCaseTypePKsSet);

		removeTestrayCaseTypePKsSet.removeAll(newTestrayCaseTypePKsSet);

		testrayTaskToTestrayCaseTypeTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeTestrayCaseTypePKsSet));

		newTestrayCaseTypePKsSet.removeAll(oldTestrayCaseTypePKsSet);

		long companyId = 0;

		TestrayTask testrayTask = fetchByPrimaryKey(pk);

		if (testrayTask == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testrayTask.getCompanyId();
		}

		testrayTaskToTestrayCaseTypeTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newTestrayCaseTypePKsSet));
	}

	/**
	 * Sets the testray case types associated with the testray task, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray task
	 * @param testrayCaseTypes the testray case types to be associated with the testray task
	 */
	@Override
	public void setTestrayCaseTypes(
		long pk,
		List<com.liferay.osb.testray.model.TestrayCaseType> testrayCaseTypes) {

		try {
			long[] testrayCaseTypePKs = new long[testrayCaseTypes.size()];

			for (int i = 0; i < testrayCaseTypes.size(); i++) {
				com.liferay.osb.testray.model.TestrayCaseType testrayCaseType =
					testrayCaseTypes.get(i);

				testrayCaseTypePKs[i] = testrayCaseType.getPrimaryKey();
			}

			setTestrayCaseTypes(pk, testrayCaseTypePKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return TestrayTaskModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the testray task persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		testrayTaskToTestrayCaseTypeTableMapper =
			TableMapperFactory.getTableMapper(
				"OSB_TestrayTasks_TestrayCaseTypes", "companyId",
				"testrayTaskId", "testrayCaseTypeId", this,
				testrayCaseTypePersistence);

		_finderPathWithPaginationFindAll = new FinderPath(
			TestrayTaskModelImpl.ENTITY_CACHE_ENABLED,
			TestrayTaskModelImpl.FINDER_CACHE_ENABLED, TestrayTaskImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			TestrayTaskModelImpl.ENTITY_CACHE_ENABLED,
			TestrayTaskModelImpl.FINDER_CACHE_ENABLED, TestrayTaskImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			TestrayTaskModelImpl.ENTITY_CACHE_ENABLED,
			TestrayTaskModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		TestrayTaskUtil.setPersistence(this);
	}

	public void destroy() {
		TestrayTaskUtil.setPersistence(null);

		entityCache.removeCache(TestrayTaskImpl.class.getName());

		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper(
			"OSB_TestrayTasks_TestrayCaseTypes");
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

	@BeanReference(type = TestrayCaseTypePersistence.class)
	protected TestrayCaseTypePersistence testrayCaseTypePersistence;

	protected TableMapper
		<TestrayTask, com.liferay.osb.testray.model.TestrayCaseType>
			testrayTaskToTestrayCaseTypeTableMapper;

	private static final String _SQL_SELECT_TESTRAYTASK =
		"SELECT testrayTask FROM TestrayTask testrayTask";

	private static final String _SQL_SELECT_TESTRAYTASK_WHERE_PKS_IN =
		"SELECT testrayTask FROM TestrayTask testrayTask WHERE testrayTaskId IN (";

	private static final String _SQL_COUNT_TESTRAYTASK =
		"SELECT COUNT(testrayTask) FROM TestrayTask testrayTask";

	private static final String _ORDER_BY_ENTITY_ALIAS = "testrayTask.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No TestrayTask exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		TestrayTaskPersistenceImpl.class);

}