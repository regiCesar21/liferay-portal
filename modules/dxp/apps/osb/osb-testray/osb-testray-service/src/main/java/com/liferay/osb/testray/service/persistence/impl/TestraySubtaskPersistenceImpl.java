/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.testray.service.persistence.impl;

import com.liferay.osb.testray.exception.NoSuchTestraySubtaskException;
import com.liferay.osb.testray.model.TestraySubtask;
import com.liferay.osb.testray.model.impl.TestraySubtaskImpl;
import com.liferay.osb.testray.model.impl.TestraySubtaskModelImpl;
import com.liferay.osb.testray.service.persistence.TestrayCaseResultPersistence;
import com.liferay.osb.testray.service.persistence.TestrayIssuePersistence;
import com.liferay.osb.testray.service.persistence.TestraySubtaskPersistence;
import com.liferay.osb.testray.service.persistence.TestraySubtaskUtil;
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
 * The persistence implementation for the testray subtask service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ethan Bustad
 * @generated
 */
public class TestraySubtaskPersistenceImpl
	extends BasePersistenceImpl<TestraySubtask>
	implements TestraySubtaskPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>TestraySubtaskUtil</code> to access the testray subtask persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		TestraySubtaskImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public TestraySubtaskPersistenceImpl() {
		setModelClass(TestraySubtask.class);
	}

	/**
	 * Caches the testray subtask in the entity cache if it is enabled.
	 *
	 * @param testraySubtask the testray subtask
	 */
	@Override
	public void cacheResult(TestraySubtask testraySubtask) {
		entityCache.putResult(
			TestraySubtaskModelImpl.ENTITY_CACHE_ENABLED,
			TestraySubtaskImpl.class, testraySubtask.getPrimaryKey(),
			testraySubtask);

		testraySubtask.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the testray subtasks in the entity cache if it is enabled.
	 *
	 * @param testraySubtasks the testray subtasks
	 */
	@Override
	public void cacheResult(List<TestraySubtask> testraySubtasks) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (testraySubtasks.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (TestraySubtask testraySubtask : testraySubtasks) {
			if (entityCache.getResult(
					TestraySubtaskModelImpl.ENTITY_CACHE_ENABLED,
					TestraySubtaskImpl.class, testraySubtask.getPrimaryKey()) ==
						null) {

				cacheResult(testraySubtask);
			}
			else {
				testraySubtask.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all testray subtasks.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(TestraySubtaskImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the testray subtask.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(TestraySubtask testraySubtask) {
		entityCache.removeResult(
			TestraySubtaskModelImpl.ENTITY_CACHE_ENABLED,
			TestraySubtaskImpl.class, testraySubtask.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<TestraySubtask> testraySubtasks) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (TestraySubtask testraySubtask : testraySubtasks) {
			entityCache.removeResult(
				TestraySubtaskModelImpl.ENTITY_CACHE_ENABLED,
				TestraySubtaskImpl.class, testraySubtask.getPrimaryKey());
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				TestraySubtaskModelImpl.ENTITY_CACHE_ENABLED,
				TestraySubtaskImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new testray subtask with the primary key. Does not add the testray subtask to the database.
	 *
	 * @param testraySubtaskId the primary key for the new testray subtask
	 * @return the new testray subtask
	 */
	@Override
	public TestraySubtask create(long testraySubtaskId) {
		TestraySubtask testraySubtask = new TestraySubtaskImpl();

		testraySubtask.setNew(true);
		testraySubtask.setPrimaryKey(testraySubtaskId);

		testraySubtask.setCompanyId(CompanyThreadLocal.getCompanyId());

		return testraySubtask;
	}

	/**
	 * Removes the testray subtask with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param testraySubtaskId the primary key of the testray subtask
	 * @return the testray subtask that was removed
	 * @throws NoSuchTestraySubtaskException if a testray subtask with the primary key could not be found
	 */
	@Override
	public TestraySubtask remove(long testraySubtaskId)
		throws NoSuchTestraySubtaskException {

		return remove((Serializable)testraySubtaskId);
	}

	/**
	 * Removes the testray subtask with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the testray subtask
	 * @return the testray subtask that was removed
	 * @throws NoSuchTestraySubtaskException if a testray subtask with the primary key could not be found
	 */
	@Override
	public TestraySubtask remove(Serializable primaryKey)
		throws NoSuchTestraySubtaskException {

		Session session = null;

		try {
			session = openSession();

			TestraySubtask testraySubtask = (TestraySubtask)session.get(
				TestraySubtaskImpl.class, primaryKey);

			if (testraySubtask == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTestraySubtaskException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(testraySubtask);
		}
		catch (NoSuchTestraySubtaskException noSuchEntityException) {
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
	protected TestraySubtask removeImpl(TestraySubtask testraySubtask) {
		testraySubtaskToTestrayCaseResultTableMapper.
			deleteLeftPrimaryKeyTableMappings(testraySubtask.getPrimaryKey());

		testraySubtaskToTestrayIssueTableMapper.
			deleteLeftPrimaryKeyTableMappings(testraySubtask.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(testraySubtask)) {
				testraySubtask = (TestraySubtask)session.get(
					TestraySubtaskImpl.class,
					testraySubtask.getPrimaryKeyObj());
			}

			if (testraySubtask != null) {
				session.delete(testraySubtask);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (testraySubtask != null) {
			clearCache(testraySubtask);
		}

		return testraySubtask;
	}

	@Override
	public TestraySubtask updateImpl(TestraySubtask testraySubtask) {
		boolean isNew = testraySubtask.isNew();

		if (!(testraySubtask instanceof TestraySubtaskModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(testraySubtask.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					testraySubtask);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in testraySubtask proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom TestraySubtask implementation " +
					testraySubtask.getClass());
		}

		TestraySubtaskModelImpl testraySubtaskModelImpl =
			(TestraySubtaskModelImpl)testraySubtask;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (testraySubtask.getCreateDate() == null)) {
			if (serviceContext == null) {
				testraySubtask.setCreateDate(date);
			}
			else {
				testraySubtask.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!testraySubtaskModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				testraySubtask.setModifiedDate(date);
			}
			else {
				testraySubtask.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(testraySubtask);

				testraySubtask.setNew(false);
			}
			else {
				testraySubtask = (TestraySubtask)session.merge(testraySubtask);
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
			TestraySubtaskModelImpl.ENTITY_CACHE_ENABLED,
			TestraySubtaskImpl.class, testraySubtask.getPrimaryKey(),
			testraySubtask, false);

		testraySubtask.resetOriginalValues();

		return testraySubtask;
	}

	/**
	 * Returns the testray subtask with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray subtask
	 * @return the testray subtask
	 * @throws NoSuchTestraySubtaskException if a testray subtask with the primary key could not be found
	 */
	@Override
	public TestraySubtask findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTestraySubtaskException {

		TestraySubtask testraySubtask = fetchByPrimaryKey(primaryKey);

		if (testraySubtask == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTestraySubtaskException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return testraySubtask;
	}

	/**
	 * Returns the testray subtask with the primary key or throws a <code>NoSuchTestraySubtaskException</code> if it could not be found.
	 *
	 * @param testraySubtaskId the primary key of the testray subtask
	 * @return the testray subtask
	 * @throws NoSuchTestraySubtaskException if a testray subtask with the primary key could not be found
	 */
	@Override
	public TestraySubtask findByPrimaryKey(long testraySubtaskId)
		throws NoSuchTestraySubtaskException {

		return findByPrimaryKey((Serializable)testraySubtaskId);
	}

	/**
	 * Returns the testray subtask with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray subtask
	 * @return the testray subtask, or <code>null</code> if a testray subtask with the primary key could not be found
	 */
	@Override
	public TestraySubtask fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			TestraySubtaskModelImpl.ENTITY_CACHE_ENABLED,
			TestraySubtaskImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		TestraySubtask testraySubtask = (TestraySubtask)serializable;

		if (testraySubtask == null) {
			Session session = null;

			try {
				session = openSession();

				testraySubtask = (TestraySubtask)session.get(
					TestraySubtaskImpl.class, primaryKey);

				if (testraySubtask != null) {
					cacheResult(testraySubtask);
				}
				else {
					entityCache.putResult(
						TestraySubtaskModelImpl.ENTITY_CACHE_ENABLED,
						TestraySubtaskImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					TestraySubtaskModelImpl.ENTITY_CACHE_ENABLED,
					TestraySubtaskImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return testraySubtask;
	}

	/**
	 * Returns the testray subtask with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param testraySubtaskId the primary key of the testray subtask
	 * @return the testray subtask, or <code>null</code> if a testray subtask with the primary key could not be found
	 */
	@Override
	public TestraySubtask fetchByPrimaryKey(long testraySubtaskId) {
		return fetchByPrimaryKey((Serializable)testraySubtaskId);
	}

	@Override
	public Map<Serializable, TestraySubtask> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, TestraySubtask> map =
			new HashMap<Serializable, TestraySubtask>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			TestraySubtask testraySubtask = fetchByPrimaryKey(primaryKey);

			if (testraySubtask != null) {
				map.put(primaryKey, testraySubtask);
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
				TestraySubtaskModelImpl.ENTITY_CACHE_ENABLED,
				TestraySubtaskImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (TestraySubtask)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_TESTRAYSUBTASK_WHERE_PKS_IN);

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

			for (TestraySubtask testraySubtask :
					(List<TestraySubtask>)query.list()) {

				map.put(testraySubtask.getPrimaryKeyObj(), testraySubtask);

				cacheResult(testraySubtask);

				uncachedPrimaryKeys.remove(testraySubtask.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					TestraySubtaskModelImpl.ENTITY_CACHE_ENABLED,
					TestraySubtaskImpl.class, primaryKey, nullModel);
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
	 * Returns all the testray subtasks.
	 *
	 * @return the testray subtasks
	 */
	@Override
	public List<TestraySubtask> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the testray subtasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestraySubtaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray subtasks
	 * @param end the upper bound of the range of testray subtasks (not inclusive)
	 * @return the range of testray subtasks
	 */
	@Override
	public List<TestraySubtask> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray subtasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestraySubtaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray subtasks
	 * @param end the upper bound of the range of testray subtasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray subtasks
	 */
	@Override
	public List<TestraySubtask> findAll(
		int start, int end,
		OrderByComparator<TestraySubtask> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the testray subtasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestraySubtaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray subtasks
	 * @param end the upper bound of the range of testray subtasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of testray subtasks
	 */
	@Override
	public List<TestraySubtask> findAll(
		int start, int end, OrderByComparator<TestraySubtask> orderByComparator,
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

		List<TestraySubtask> list = null;

		if (useFinderCache) {
			list = (List<TestraySubtask>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_TESTRAYSUBTASK);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_TESTRAYSUBTASK;

				sql = sql.concat(TestraySubtaskModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<TestraySubtask>)QueryUtil.list(
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
	 * Removes all the testray subtasks from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (TestraySubtask testraySubtask : findAll()) {
			remove(testraySubtask);
		}
	}

	/**
	 * Returns the number of testray subtasks.
	 *
	 * @return the number of testray subtasks
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_TESTRAYSUBTASK);

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
	 * Returns the primaryKeys of testray case results associated with the testray subtask.
	 *
	 * @param pk the primary key of the testray subtask
	 * @return long[] of the primaryKeys of testray case results associated with the testray subtask
	 */
	@Override
	public long[] getTestrayCaseResultPrimaryKeys(long pk) {
		long[] pks =
			testraySubtaskToTestrayCaseResultTableMapper.getRightPrimaryKeys(
				pk);

		return pks.clone();
	}

	/**
	 * Returns all the testray case results associated with the testray subtask.
	 *
	 * @param pk the primary key of the testray subtask
	 * @return the testray case results associated with the testray subtask
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayCaseResult>
		getTestrayCaseResults(long pk) {

		return getTestrayCaseResults(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the testray case results associated with the testray subtask.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestraySubtaskModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray subtask
	 * @param start the lower bound of the range of testray subtasks
	 * @param end the upper bound of the range of testray subtasks (not inclusive)
	 * @return the range of testray case results associated with the testray subtask
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayCaseResult>
		getTestrayCaseResults(long pk, int start, int end) {

		return getTestrayCaseResults(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray case results associated with the testray subtask.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestraySubtaskModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray subtask
	 * @param start the lower bound of the range of testray subtasks
	 * @param end the upper bound of the range of testray subtasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray case results associated with the testray subtask
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayCaseResult>
		getTestrayCaseResults(
			long pk, int start, int end,
			OrderByComparator<com.liferay.osb.testray.model.TestrayCaseResult>
				orderByComparator) {

		return testraySubtaskToTestrayCaseResultTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of testray case results associated with the testray subtask.
	 *
	 * @param pk the primary key of the testray subtask
	 * @return the number of testray case results associated with the testray subtask
	 */
	@Override
	public int getTestrayCaseResultsSize(long pk) {
		long[] pks =
			testraySubtaskToTestrayCaseResultTableMapper.getRightPrimaryKeys(
				pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the testray case result is associated with the testray subtask.
	 *
	 * @param pk the primary key of the testray subtask
	 * @param testrayCaseResultPK the primary key of the testray case result
	 * @return <code>true</code> if the testray case result is associated with the testray subtask; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTestrayCaseResult(
		long pk, long testrayCaseResultPK) {

		return testraySubtaskToTestrayCaseResultTableMapper.
			containsTableMapping(pk, testrayCaseResultPK);
	}

	/**
	 * Returns <code>true</code> if the testray subtask has any testray case results associated with it.
	 *
	 * @param pk the primary key of the testray subtask to check for associations with testray case results
	 * @return <code>true</code> if the testray subtask has any testray case results associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTestrayCaseResults(long pk) {
		if (getTestrayCaseResultsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the testray subtask and the testray case result. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray subtask
	 * @param testrayCaseResultPK the primary key of the testray case result
	 */
	@Override
	public void addTestrayCaseResult(long pk, long testrayCaseResultPK) {
		TestraySubtask testraySubtask = fetchByPrimaryKey(pk);

		if (testraySubtask == null) {
			testraySubtaskToTestrayCaseResultTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, testrayCaseResultPK);
		}
		else {
			testraySubtaskToTestrayCaseResultTableMapper.addTableMapping(
				testraySubtask.getCompanyId(), pk, testrayCaseResultPK);
		}
	}

	/**
	 * Adds an association between the testray subtask and the testray case result. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray subtask
	 * @param testrayCaseResult the testray case result
	 */
	@Override
	public void addTestrayCaseResult(
		long pk,
		com.liferay.osb.testray.model.TestrayCaseResult testrayCaseResult) {

		TestraySubtask testraySubtask = fetchByPrimaryKey(pk);

		if (testraySubtask == null) {
			testraySubtaskToTestrayCaseResultTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				testrayCaseResult.getPrimaryKey());
		}
		else {
			testraySubtaskToTestrayCaseResultTableMapper.addTableMapping(
				testraySubtask.getCompanyId(), pk,
				testrayCaseResult.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the testray subtask and the testray case results. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray subtask
	 * @param testrayCaseResultPKs the primary keys of the testray case results
	 */
	@Override
	public void addTestrayCaseResults(long pk, long[] testrayCaseResultPKs) {
		long companyId = 0;

		TestraySubtask testraySubtask = fetchByPrimaryKey(pk);

		if (testraySubtask == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testraySubtask.getCompanyId();
		}

		testraySubtaskToTestrayCaseResultTableMapper.addTableMappings(
			companyId, pk, testrayCaseResultPKs);
	}

	/**
	 * Adds an association between the testray subtask and the testray case results. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray subtask
	 * @param testrayCaseResults the testray case results
	 */
	@Override
	public void addTestrayCaseResults(
		long pk,
		List<com.liferay.osb.testray.model.TestrayCaseResult>
			testrayCaseResults) {

		addTestrayCaseResults(
			pk,
			ListUtil.toLongArray(
				testrayCaseResults,
				com.liferay.osb.testray.model.TestrayCaseResult.
					TESTRAY_CASE_RESULT_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the testray subtask and its testray case results. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray subtask to clear the associated testray case results from
	 */
	@Override
	public void clearTestrayCaseResults(long pk) {
		testraySubtaskToTestrayCaseResultTableMapper.
			deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the testray subtask and the testray case result. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray subtask
	 * @param testrayCaseResultPK the primary key of the testray case result
	 */
	@Override
	public void removeTestrayCaseResult(long pk, long testrayCaseResultPK) {
		testraySubtaskToTestrayCaseResultTableMapper.deleteTableMapping(
			pk, testrayCaseResultPK);
	}

	/**
	 * Removes the association between the testray subtask and the testray case result. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray subtask
	 * @param testrayCaseResult the testray case result
	 */
	@Override
	public void removeTestrayCaseResult(
		long pk,
		com.liferay.osb.testray.model.TestrayCaseResult testrayCaseResult) {

		testraySubtaskToTestrayCaseResultTableMapper.deleteTableMapping(
			pk, testrayCaseResult.getPrimaryKey());
	}

	/**
	 * Removes the association between the testray subtask and the testray case results. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray subtask
	 * @param testrayCaseResultPKs the primary keys of the testray case results
	 */
	@Override
	public void removeTestrayCaseResults(long pk, long[] testrayCaseResultPKs) {
		testraySubtaskToTestrayCaseResultTableMapper.deleteTableMappings(
			pk, testrayCaseResultPKs);
	}

	/**
	 * Removes the association between the testray subtask and the testray case results. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray subtask
	 * @param testrayCaseResults the testray case results
	 */
	@Override
	public void removeTestrayCaseResults(
		long pk,
		List<com.liferay.osb.testray.model.TestrayCaseResult>
			testrayCaseResults) {

		removeTestrayCaseResults(
			pk,
			ListUtil.toLongArray(
				testrayCaseResults,
				com.liferay.osb.testray.model.TestrayCaseResult.
					TESTRAY_CASE_RESULT_ID_ACCESSOR));
	}

	/**
	 * Sets the testray case results associated with the testray subtask, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray subtask
	 * @param testrayCaseResultPKs the primary keys of the testray case results to be associated with the testray subtask
	 */
	@Override
	public void setTestrayCaseResults(long pk, long[] testrayCaseResultPKs) {
		Set<Long> newTestrayCaseResultPKsSet = SetUtil.fromArray(
			testrayCaseResultPKs);
		Set<Long> oldTestrayCaseResultPKsSet = SetUtil.fromArray(
			testraySubtaskToTestrayCaseResultTableMapper.getRightPrimaryKeys(
				pk));

		Set<Long> removeTestrayCaseResultPKsSet = new HashSet<Long>(
			oldTestrayCaseResultPKsSet);

		removeTestrayCaseResultPKsSet.removeAll(newTestrayCaseResultPKsSet);

		testraySubtaskToTestrayCaseResultTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeTestrayCaseResultPKsSet));

		newTestrayCaseResultPKsSet.removeAll(oldTestrayCaseResultPKsSet);

		long companyId = 0;

		TestraySubtask testraySubtask = fetchByPrimaryKey(pk);

		if (testraySubtask == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testraySubtask.getCompanyId();
		}

		testraySubtaskToTestrayCaseResultTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newTestrayCaseResultPKsSet));
	}

	/**
	 * Sets the testray case results associated with the testray subtask, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray subtask
	 * @param testrayCaseResults the testray case results to be associated with the testray subtask
	 */
	@Override
	public void setTestrayCaseResults(
		long pk,
		List<com.liferay.osb.testray.model.TestrayCaseResult>
			testrayCaseResults) {

		try {
			long[] testrayCaseResultPKs = new long[testrayCaseResults.size()];

			for (int i = 0; i < testrayCaseResults.size(); i++) {
				com.liferay.osb.testray.model.TestrayCaseResult
					testrayCaseResult = testrayCaseResults.get(i);

				testrayCaseResultPKs[i] = testrayCaseResult.getPrimaryKey();
			}

			setTestrayCaseResults(pk, testrayCaseResultPKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	/**
	 * Returns the primaryKeys of testray issues associated with the testray subtask.
	 *
	 * @param pk the primary key of the testray subtask
	 * @return long[] of the primaryKeys of testray issues associated with the testray subtask
	 */
	@Override
	public long[] getTestrayIssuePrimaryKeys(long pk) {
		long[] pks =
			testraySubtaskToTestrayIssueTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the testray issues associated with the testray subtask.
	 *
	 * @param pk the primary key of the testray subtask
	 * @return the testray issues associated with the testray subtask
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayIssue> getTestrayIssues(
		long pk) {

		return getTestrayIssues(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the testray issues associated with the testray subtask.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestraySubtaskModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray subtask
	 * @param start the lower bound of the range of testray subtasks
	 * @param end the upper bound of the range of testray subtasks (not inclusive)
	 * @return the range of testray issues associated with the testray subtask
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayIssue> getTestrayIssues(
		long pk, int start, int end) {

		return getTestrayIssues(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray issues associated with the testray subtask.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestraySubtaskModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray subtask
	 * @param start the lower bound of the range of testray subtasks
	 * @param end the upper bound of the range of testray subtasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray issues associated with the testray subtask
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayIssue> getTestrayIssues(
		long pk, int start, int end,
		OrderByComparator<com.liferay.osb.testray.model.TestrayIssue>
			orderByComparator) {

		return testraySubtaskToTestrayIssueTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of testray issues associated with the testray subtask.
	 *
	 * @param pk the primary key of the testray subtask
	 * @return the number of testray issues associated with the testray subtask
	 */
	@Override
	public int getTestrayIssuesSize(long pk) {
		long[] pks =
			testraySubtaskToTestrayIssueTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the testray issue is associated with the testray subtask.
	 *
	 * @param pk the primary key of the testray subtask
	 * @param testrayIssuePK the primary key of the testray issue
	 * @return <code>true</code> if the testray issue is associated with the testray subtask; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTestrayIssue(long pk, long testrayIssuePK) {
		return testraySubtaskToTestrayIssueTableMapper.containsTableMapping(
			pk, testrayIssuePK);
	}

	/**
	 * Returns <code>true</code> if the testray subtask has any testray issues associated with it.
	 *
	 * @param pk the primary key of the testray subtask to check for associations with testray issues
	 * @return <code>true</code> if the testray subtask has any testray issues associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTestrayIssues(long pk) {
		if (getTestrayIssuesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the testray subtask and the testray issue. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray subtask
	 * @param testrayIssuePK the primary key of the testray issue
	 */
	@Override
	public void addTestrayIssue(long pk, long testrayIssuePK) {
		TestraySubtask testraySubtask = fetchByPrimaryKey(pk);

		if (testraySubtask == null) {
			testraySubtaskToTestrayIssueTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, testrayIssuePK);
		}
		else {
			testraySubtaskToTestrayIssueTableMapper.addTableMapping(
				testraySubtask.getCompanyId(), pk, testrayIssuePK);
		}
	}

	/**
	 * Adds an association between the testray subtask and the testray issue. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray subtask
	 * @param testrayIssue the testray issue
	 */
	@Override
	public void addTestrayIssue(
		long pk, com.liferay.osb.testray.model.TestrayIssue testrayIssue) {

		TestraySubtask testraySubtask = fetchByPrimaryKey(pk);

		if (testraySubtask == null) {
			testraySubtaskToTestrayIssueTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				testrayIssue.getPrimaryKey());
		}
		else {
			testraySubtaskToTestrayIssueTableMapper.addTableMapping(
				testraySubtask.getCompanyId(), pk,
				testrayIssue.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the testray subtask and the testray issues. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray subtask
	 * @param testrayIssuePKs the primary keys of the testray issues
	 */
	@Override
	public void addTestrayIssues(long pk, long[] testrayIssuePKs) {
		long companyId = 0;

		TestraySubtask testraySubtask = fetchByPrimaryKey(pk);

		if (testraySubtask == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testraySubtask.getCompanyId();
		}

		testraySubtaskToTestrayIssueTableMapper.addTableMappings(
			companyId, pk, testrayIssuePKs);
	}

	/**
	 * Adds an association between the testray subtask and the testray issues. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray subtask
	 * @param testrayIssues the testray issues
	 */
	@Override
	public void addTestrayIssues(
		long pk,
		List<com.liferay.osb.testray.model.TestrayIssue> testrayIssues) {

		addTestrayIssues(
			pk,
			ListUtil.toLongArray(
				testrayIssues,
				com.liferay.osb.testray.model.TestrayIssue.
					TESTRAY_ISSUE_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the testray subtask and its testray issues. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray subtask to clear the associated testray issues from
	 */
	@Override
	public void clearTestrayIssues(long pk) {
		testraySubtaskToTestrayIssueTableMapper.
			deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the testray subtask and the testray issue. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray subtask
	 * @param testrayIssuePK the primary key of the testray issue
	 */
	@Override
	public void removeTestrayIssue(long pk, long testrayIssuePK) {
		testraySubtaskToTestrayIssueTableMapper.deleteTableMapping(
			pk, testrayIssuePK);
	}

	/**
	 * Removes the association between the testray subtask and the testray issue. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray subtask
	 * @param testrayIssue the testray issue
	 */
	@Override
	public void removeTestrayIssue(
		long pk, com.liferay.osb.testray.model.TestrayIssue testrayIssue) {

		testraySubtaskToTestrayIssueTableMapper.deleteTableMapping(
			pk, testrayIssue.getPrimaryKey());
	}

	/**
	 * Removes the association between the testray subtask and the testray issues. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray subtask
	 * @param testrayIssuePKs the primary keys of the testray issues
	 */
	@Override
	public void removeTestrayIssues(long pk, long[] testrayIssuePKs) {
		testraySubtaskToTestrayIssueTableMapper.deleteTableMappings(
			pk, testrayIssuePKs);
	}

	/**
	 * Removes the association between the testray subtask and the testray issues. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray subtask
	 * @param testrayIssues the testray issues
	 */
	@Override
	public void removeTestrayIssues(
		long pk,
		List<com.liferay.osb.testray.model.TestrayIssue> testrayIssues) {

		removeTestrayIssues(
			pk,
			ListUtil.toLongArray(
				testrayIssues,
				com.liferay.osb.testray.model.TestrayIssue.
					TESTRAY_ISSUE_ID_ACCESSOR));
	}

	/**
	 * Sets the testray issues associated with the testray subtask, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray subtask
	 * @param testrayIssuePKs the primary keys of the testray issues to be associated with the testray subtask
	 */
	@Override
	public void setTestrayIssues(long pk, long[] testrayIssuePKs) {
		Set<Long> newTestrayIssuePKsSet = SetUtil.fromArray(testrayIssuePKs);
		Set<Long> oldTestrayIssuePKsSet = SetUtil.fromArray(
			testraySubtaskToTestrayIssueTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeTestrayIssuePKsSet = new HashSet<Long>(
			oldTestrayIssuePKsSet);

		removeTestrayIssuePKsSet.removeAll(newTestrayIssuePKsSet);

		testraySubtaskToTestrayIssueTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeTestrayIssuePKsSet));

		newTestrayIssuePKsSet.removeAll(oldTestrayIssuePKsSet);

		long companyId = 0;

		TestraySubtask testraySubtask = fetchByPrimaryKey(pk);

		if (testraySubtask == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testraySubtask.getCompanyId();
		}

		testraySubtaskToTestrayIssueTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newTestrayIssuePKsSet));
	}

	/**
	 * Sets the testray issues associated with the testray subtask, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray subtask
	 * @param testrayIssues the testray issues to be associated with the testray subtask
	 */
	@Override
	public void setTestrayIssues(
		long pk,
		List<com.liferay.osb.testray.model.TestrayIssue> testrayIssues) {

		try {
			long[] testrayIssuePKs = new long[testrayIssues.size()];

			for (int i = 0; i < testrayIssues.size(); i++) {
				com.liferay.osb.testray.model.TestrayIssue testrayIssue =
					testrayIssues.get(i);

				testrayIssuePKs[i] = testrayIssue.getPrimaryKey();
			}

			setTestrayIssues(pk, testrayIssuePKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return TestraySubtaskModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the testray subtask persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		testraySubtaskToTestrayCaseResultTableMapper =
			TableMapperFactory.getTableMapper(
				"OSB_TestraySubtasks_TestrayCaseResults", "companyId",
				"testraySubtaskId", "testrayCaseResultId", this,
				testrayCaseResultPersistence);

		testraySubtaskToTestrayIssueTableMapper =
			TableMapperFactory.getTableMapper(
				"OSB_TestraySubtasks_TestrayIssues", "companyId",
				"testraySubtaskId", "testrayIssueId", this,
				testrayIssuePersistence);

		_finderPathWithPaginationFindAll = new FinderPath(
			TestraySubtaskModelImpl.ENTITY_CACHE_ENABLED,
			TestraySubtaskModelImpl.FINDER_CACHE_ENABLED,
			TestraySubtaskImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			TestraySubtaskModelImpl.ENTITY_CACHE_ENABLED,
			TestraySubtaskModelImpl.FINDER_CACHE_ENABLED,
			TestraySubtaskImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			TestraySubtaskModelImpl.ENTITY_CACHE_ENABLED,
			TestraySubtaskModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		TestraySubtaskUtil.setPersistence(this);
	}

	public void destroy() {
		TestraySubtaskUtil.setPersistence(null);

		entityCache.removeCache(TestraySubtaskImpl.class.getName());

		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper(
			"OSB_TestraySubtasks_TestrayCaseResults");
		TableMapperFactory.removeTableMapper(
			"OSB_TestraySubtasks_TestrayIssues");
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

	@BeanReference(type = TestrayCaseResultPersistence.class)
	protected TestrayCaseResultPersistence testrayCaseResultPersistence;

	protected TableMapper
		<TestraySubtask, com.liferay.osb.testray.model.TestrayCaseResult>
			testraySubtaskToTestrayCaseResultTableMapper;

	@BeanReference(type = TestrayIssuePersistence.class)
	protected TestrayIssuePersistence testrayIssuePersistence;

	protected TableMapper
		<TestraySubtask, com.liferay.osb.testray.model.TestrayIssue>
			testraySubtaskToTestrayIssueTableMapper;

	private static final String _SQL_SELECT_TESTRAYSUBTASK =
		"SELECT testraySubtask FROM TestraySubtask testraySubtask";

	private static final String _SQL_SELECT_TESTRAYSUBTASK_WHERE_PKS_IN =
		"SELECT testraySubtask FROM TestraySubtask testraySubtask WHERE testraySubtaskId IN (";

	private static final String _SQL_COUNT_TESTRAYSUBTASK =
		"SELECT COUNT(testraySubtask) FROM TestraySubtask testraySubtask";

	private static final String _ORDER_BY_ENTITY_ALIAS = "testraySubtask.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No TestraySubtask exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		TestraySubtaskPersistenceImpl.class);

}