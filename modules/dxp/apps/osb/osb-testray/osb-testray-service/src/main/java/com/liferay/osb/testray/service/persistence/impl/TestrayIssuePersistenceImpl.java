/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.testray.service.persistence.impl;

import com.liferay.osb.testray.exception.NoSuchTestrayIssueException;
import com.liferay.osb.testray.model.TestrayIssue;
import com.liferay.osb.testray.model.impl.TestrayIssueImpl;
import com.liferay.osb.testray.model.impl.TestrayIssueModelImpl;
import com.liferay.osb.testray.service.persistence.TestrayCaseResultPersistence;
import com.liferay.osb.testray.service.persistence.TestrayIssuePersistence;
import com.liferay.osb.testray.service.persistence.TestrayIssueUtil;
import com.liferay.osb.testray.service.persistence.TestraySubtaskPersistence;
import com.liferay.portal.kernel.bean.BeanReference;
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
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the testray issue service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ethan Bustad
 * @generated
 */
public class TestrayIssuePersistenceImpl
	extends BasePersistenceImpl<TestrayIssue>
	implements TestrayIssuePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>TestrayIssueUtil</code> to access the testray issue persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		TestrayIssueImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByName;
	private FinderPath _finderPathCountByName;

	/**
	 * Returns the testray issue where name = &#63; or throws a <code>NoSuchTestrayIssueException</code> if it could not be found.
	 *
	 * @param name the name
	 * @return the matching testray issue
	 * @throws NoSuchTestrayIssueException if a matching testray issue could not be found
	 */
	@Override
	public TestrayIssue findByName(String name)
		throws NoSuchTestrayIssueException {

		TestrayIssue testrayIssue = fetchByName(name);

		if (testrayIssue == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchTestrayIssueException(sb.toString());
		}

		return testrayIssue;
	}

	/**
	 * Returns the testray issue where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @return the matching testray issue, or <code>null</code> if a matching testray issue could not be found
	 */
	@Override
	public TestrayIssue fetchByName(String name) {
		return fetchByName(name, true);
	}

	/**
	 * Returns the testray issue where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching testray issue, or <code>null</code> if a matching testray issue could not be found
	 */
	@Override
	public TestrayIssue fetchByName(String name, boolean useFinderCache) {
		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByName, finderArgs, this);
		}

		if (result instanceof TestrayIssue) {
			TestrayIssue testrayIssue = (TestrayIssue)result;

			if (!Objects.equals(name, testrayIssue.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_TESTRAYISSUE_WHERE);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_NAME_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_NAME_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(name);
				}

				List<TestrayIssue> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByName, finderArgs, list);
					}
				}
				else {
					TestrayIssue testrayIssue = list.get(0);

					result = testrayIssue;

					cacheResult(testrayIssue);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByName, finderArgs);
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
			return (TestrayIssue)result;
		}
	}

	/**
	 * Removes the testray issue where name = &#63; from the database.
	 *
	 * @param name the name
	 * @return the testray issue that was removed
	 */
	@Override
	public TestrayIssue removeByName(String name)
		throws NoSuchTestrayIssueException {

		TestrayIssue testrayIssue = findByName(name);

		return remove(testrayIssue);
	}

	/**
	 * Returns the number of testray issues where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching testray issues
	 */
	@Override
	public int countByName(String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByName;

		Object[] finderArgs = new Object[] {name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_TESTRAYISSUE_WHERE);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_NAME_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_NAME_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

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

	private static final String _FINDER_COLUMN_NAME_NAME_2 =
		"testrayIssue.name = ?";

	private static final String _FINDER_COLUMN_NAME_NAME_3 =
		"(testrayIssue.name IS NULL OR testrayIssue.name = '')";

	public TestrayIssuePersistenceImpl() {
		setModelClass(TestrayIssue.class);
	}

	/**
	 * Caches the testray issue in the entity cache if it is enabled.
	 *
	 * @param testrayIssue the testray issue
	 */
	@Override
	public void cacheResult(TestrayIssue testrayIssue) {
		entityCache.putResult(
			TestrayIssueModelImpl.ENTITY_CACHE_ENABLED, TestrayIssueImpl.class,
			testrayIssue.getPrimaryKey(), testrayIssue);

		finderCache.putResult(
			_finderPathFetchByName, new Object[] {testrayIssue.getName()},
			testrayIssue);

		testrayIssue.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the testray issues in the entity cache if it is enabled.
	 *
	 * @param testrayIssues the testray issues
	 */
	@Override
	public void cacheResult(List<TestrayIssue> testrayIssues) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (testrayIssues.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (TestrayIssue testrayIssue : testrayIssues) {
			if (entityCache.getResult(
					TestrayIssueModelImpl.ENTITY_CACHE_ENABLED,
					TestrayIssueImpl.class, testrayIssue.getPrimaryKey()) ==
						null) {

				cacheResult(testrayIssue);
			}
			else {
				testrayIssue.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all testray issues.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(TestrayIssueImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the testray issue.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(TestrayIssue testrayIssue) {
		entityCache.removeResult(
			TestrayIssueModelImpl.ENTITY_CACHE_ENABLED, TestrayIssueImpl.class,
			testrayIssue.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((TestrayIssueModelImpl)testrayIssue, true);
	}

	@Override
	public void clearCache(List<TestrayIssue> testrayIssues) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (TestrayIssue testrayIssue : testrayIssues) {
			entityCache.removeResult(
				TestrayIssueModelImpl.ENTITY_CACHE_ENABLED,
				TestrayIssueImpl.class, testrayIssue.getPrimaryKey());

			clearUniqueFindersCache((TestrayIssueModelImpl)testrayIssue, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				TestrayIssueModelImpl.ENTITY_CACHE_ENABLED,
				TestrayIssueImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		TestrayIssueModelImpl testrayIssueModelImpl) {

		Object[] args = new Object[] {testrayIssueModelImpl.getName()};

		finderCache.putResult(
			_finderPathCountByName, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByName, args, testrayIssueModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		TestrayIssueModelImpl testrayIssueModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {testrayIssueModelImpl.getName()};

			finderCache.removeResult(_finderPathCountByName, args);
			finderCache.removeResult(_finderPathFetchByName, args);
		}

		if ((testrayIssueModelImpl.getColumnBitmask() &
			 _finderPathFetchByName.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				testrayIssueModelImpl.getOriginalName()
			};

			finderCache.removeResult(_finderPathCountByName, args);
			finderCache.removeResult(_finderPathFetchByName, args);
		}
	}

	/**
	 * Creates a new testray issue with the primary key. Does not add the testray issue to the database.
	 *
	 * @param testrayIssueId the primary key for the new testray issue
	 * @return the new testray issue
	 */
	@Override
	public TestrayIssue create(long testrayIssueId) {
		TestrayIssue testrayIssue = new TestrayIssueImpl();

		testrayIssue.setNew(true);
		testrayIssue.setPrimaryKey(testrayIssueId);

		testrayIssue.setCompanyId(CompanyThreadLocal.getCompanyId());

		return testrayIssue;
	}

	/**
	 * Removes the testray issue with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param testrayIssueId the primary key of the testray issue
	 * @return the testray issue that was removed
	 * @throws NoSuchTestrayIssueException if a testray issue with the primary key could not be found
	 */
	@Override
	public TestrayIssue remove(long testrayIssueId)
		throws NoSuchTestrayIssueException {

		return remove((Serializable)testrayIssueId);
	}

	/**
	 * Removes the testray issue with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the testray issue
	 * @return the testray issue that was removed
	 * @throws NoSuchTestrayIssueException if a testray issue with the primary key could not be found
	 */
	@Override
	public TestrayIssue remove(Serializable primaryKey)
		throws NoSuchTestrayIssueException {

		Session session = null;

		try {
			session = openSession();

			TestrayIssue testrayIssue = (TestrayIssue)session.get(
				TestrayIssueImpl.class, primaryKey);

			if (testrayIssue == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTestrayIssueException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(testrayIssue);
		}
		catch (NoSuchTestrayIssueException noSuchEntityException) {
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
	protected TestrayIssue removeImpl(TestrayIssue testrayIssue) {
		testrayIssueToTestrayCaseResultTableMapper.
			deleteLeftPrimaryKeyTableMappings(testrayIssue.getPrimaryKey());

		testrayIssueToTestraySubtaskTableMapper.
			deleteLeftPrimaryKeyTableMappings(testrayIssue.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(testrayIssue)) {
				testrayIssue = (TestrayIssue)session.get(
					TestrayIssueImpl.class, testrayIssue.getPrimaryKeyObj());
			}

			if (testrayIssue != null) {
				session.delete(testrayIssue);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (testrayIssue != null) {
			clearCache(testrayIssue);
		}

		return testrayIssue;
	}

	@Override
	public TestrayIssue updateImpl(TestrayIssue testrayIssue) {
		boolean isNew = testrayIssue.isNew();

		if (!(testrayIssue instanceof TestrayIssueModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(testrayIssue.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					testrayIssue);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in testrayIssue proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom TestrayIssue implementation " +
					testrayIssue.getClass());
		}

		TestrayIssueModelImpl testrayIssueModelImpl =
			(TestrayIssueModelImpl)testrayIssue;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (testrayIssue.getCreateDate() == null)) {
			if (serviceContext == null) {
				testrayIssue.setCreateDate(date);
			}
			else {
				testrayIssue.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!testrayIssueModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				testrayIssue.setModifiedDate(date);
			}
			else {
				testrayIssue.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(testrayIssue);

				testrayIssue.setNew(false);
			}
			else {
				testrayIssue = (TestrayIssue)session.merge(testrayIssue);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!TestrayIssueModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			TestrayIssueModelImpl.ENTITY_CACHE_ENABLED, TestrayIssueImpl.class,
			testrayIssue.getPrimaryKey(), testrayIssue, false);

		clearUniqueFindersCache(testrayIssueModelImpl, false);
		cacheUniqueFindersCache(testrayIssueModelImpl);

		testrayIssue.resetOriginalValues();

		return testrayIssue;
	}

	/**
	 * Returns the testray issue with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray issue
	 * @return the testray issue
	 * @throws NoSuchTestrayIssueException if a testray issue with the primary key could not be found
	 */
	@Override
	public TestrayIssue findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTestrayIssueException {

		TestrayIssue testrayIssue = fetchByPrimaryKey(primaryKey);

		if (testrayIssue == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTestrayIssueException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return testrayIssue;
	}

	/**
	 * Returns the testray issue with the primary key or throws a <code>NoSuchTestrayIssueException</code> if it could not be found.
	 *
	 * @param testrayIssueId the primary key of the testray issue
	 * @return the testray issue
	 * @throws NoSuchTestrayIssueException if a testray issue with the primary key could not be found
	 */
	@Override
	public TestrayIssue findByPrimaryKey(long testrayIssueId)
		throws NoSuchTestrayIssueException {

		return findByPrimaryKey((Serializable)testrayIssueId);
	}

	/**
	 * Returns the testray issue with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray issue
	 * @return the testray issue, or <code>null</code> if a testray issue with the primary key could not be found
	 */
	@Override
	public TestrayIssue fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			TestrayIssueModelImpl.ENTITY_CACHE_ENABLED, TestrayIssueImpl.class,
			primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		TestrayIssue testrayIssue = (TestrayIssue)serializable;

		if (testrayIssue == null) {
			Session session = null;

			try {
				session = openSession();

				testrayIssue = (TestrayIssue)session.get(
					TestrayIssueImpl.class, primaryKey);

				if (testrayIssue != null) {
					cacheResult(testrayIssue);
				}
				else {
					entityCache.putResult(
						TestrayIssueModelImpl.ENTITY_CACHE_ENABLED,
						TestrayIssueImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					TestrayIssueModelImpl.ENTITY_CACHE_ENABLED,
					TestrayIssueImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return testrayIssue;
	}

	/**
	 * Returns the testray issue with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param testrayIssueId the primary key of the testray issue
	 * @return the testray issue, or <code>null</code> if a testray issue with the primary key could not be found
	 */
	@Override
	public TestrayIssue fetchByPrimaryKey(long testrayIssueId) {
		return fetchByPrimaryKey((Serializable)testrayIssueId);
	}

	@Override
	public Map<Serializable, TestrayIssue> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, TestrayIssue> map =
			new HashMap<Serializable, TestrayIssue>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			TestrayIssue testrayIssue = fetchByPrimaryKey(primaryKey);

			if (testrayIssue != null) {
				map.put(primaryKey, testrayIssue);
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
				TestrayIssueModelImpl.ENTITY_CACHE_ENABLED,
				TestrayIssueImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (TestrayIssue)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_TESTRAYISSUE_WHERE_PKS_IN);

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

			for (TestrayIssue testrayIssue : (List<TestrayIssue>)query.list()) {
				map.put(testrayIssue.getPrimaryKeyObj(), testrayIssue);

				cacheResult(testrayIssue);

				uncachedPrimaryKeys.remove(testrayIssue.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					TestrayIssueModelImpl.ENTITY_CACHE_ENABLED,
					TestrayIssueImpl.class, primaryKey, nullModel);
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
	 * Returns all the testray issues.
	 *
	 * @return the testray issues
	 */
	@Override
	public List<TestrayIssue> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the testray issues.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayIssueModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray issues
	 * @param end the upper bound of the range of testray issues (not inclusive)
	 * @return the range of testray issues
	 */
	@Override
	public List<TestrayIssue> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray issues.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayIssueModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray issues
	 * @param end the upper bound of the range of testray issues (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray issues
	 */
	@Override
	public List<TestrayIssue> findAll(
		int start, int end, OrderByComparator<TestrayIssue> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the testray issues.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayIssueModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray issues
	 * @param end the upper bound of the range of testray issues (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of testray issues
	 */
	@Override
	public List<TestrayIssue> findAll(
		int start, int end, OrderByComparator<TestrayIssue> orderByComparator,
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

		List<TestrayIssue> list = null;

		if (useFinderCache) {
			list = (List<TestrayIssue>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_TESTRAYISSUE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_TESTRAYISSUE;

				sql = sql.concat(TestrayIssueModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<TestrayIssue>)QueryUtil.list(
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
	 * Removes all the testray issues from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (TestrayIssue testrayIssue : findAll()) {
			remove(testrayIssue);
		}
	}

	/**
	 * Returns the number of testray issues.
	 *
	 * @return the number of testray issues
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_TESTRAYISSUE);

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
	 * Returns the primaryKeys of testray case results associated with the testray issue.
	 *
	 * @param pk the primary key of the testray issue
	 * @return long[] of the primaryKeys of testray case results associated with the testray issue
	 */
	@Override
	public long[] getTestrayCaseResultPrimaryKeys(long pk) {
		long[] pks =
			testrayIssueToTestrayCaseResultTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the testray case results associated with the testray issue.
	 *
	 * @param pk the primary key of the testray issue
	 * @return the testray case results associated with the testray issue
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayCaseResult>
		getTestrayCaseResults(long pk) {

		return getTestrayCaseResults(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the testray case results associated with the testray issue.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayIssueModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray issue
	 * @param start the lower bound of the range of testray issues
	 * @param end the upper bound of the range of testray issues (not inclusive)
	 * @return the range of testray case results associated with the testray issue
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayCaseResult>
		getTestrayCaseResults(long pk, int start, int end) {

		return getTestrayCaseResults(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray case results associated with the testray issue.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayIssueModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray issue
	 * @param start the lower bound of the range of testray issues
	 * @param end the upper bound of the range of testray issues (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray case results associated with the testray issue
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayCaseResult>
		getTestrayCaseResults(
			long pk, int start, int end,
			OrderByComparator<com.liferay.osb.testray.model.TestrayCaseResult>
				orderByComparator) {

		return testrayIssueToTestrayCaseResultTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of testray case results associated with the testray issue.
	 *
	 * @param pk the primary key of the testray issue
	 * @return the number of testray case results associated with the testray issue
	 */
	@Override
	public int getTestrayCaseResultsSize(long pk) {
		long[] pks =
			testrayIssueToTestrayCaseResultTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the testray case result is associated with the testray issue.
	 *
	 * @param pk the primary key of the testray issue
	 * @param testrayCaseResultPK the primary key of the testray case result
	 * @return <code>true</code> if the testray case result is associated with the testray issue; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTestrayCaseResult(
		long pk, long testrayCaseResultPK) {

		return testrayIssueToTestrayCaseResultTableMapper.containsTableMapping(
			pk, testrayCaseResultPK);
	}

	/**
	 * Returns <code>true</code> if the testray issue has any testray case results associated with it.
	 *
	 * @param pk the primary key of the testray issue to check for associations with testray case results
	 * @return <code>true</code> if the testray issue has any testray case results associated with it; <code>false</code> otherwise
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
	 * Adds an association between the testray issue and the testray case result. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray issue
	 * @param testrayCaseResultPK the primary key of the testray case result
	 */
	@Override
	public void addTestrayCaseResult(long pk, long testrayCaseResultPK) {
		TestrayIssue testrayIssue = fetchByPrimaryKey(pk);

		if (testrayIssue == null) {
			testrayIssueToTestrayCaseResultTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, testrayCaseResultPK);
		}
		else {
			testrayIssueToTestrayCaseResultTableMapper.addTableMapping(
				testrayIssue.getCompanyId(), pk, testrayCaseResultPK);
		}
	}

	/**
	 * Adds an association between the testray issue and the testray case result. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray issue
	 * @param testrayCaseResult the testray case result
	 */
	@Override
	public void addTestrayCaseResult(
		long pk,
		com.liferay.osb.testray.model.TestrayCaseResult testrayCaseResult) {

		TestrayIssue testrayIssue = fetchByPrimaryKey(pk);

		if (testrayIssue == null) {
			testrayIssueToTestrayCaseResultTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				testrayCaseResult.getPrimaryKey());
		}
		else {
			testrayIssueToTestrayCaseResultTableMapper.addTableMapping(
				testrayIssue.getCompanyId(), pk,
				testrayCaseResult.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the testray issue and the testray case results. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray issue
	 * @param testrayCaseResultPKs the primary keys of the testray case results
	 */
	@Override
	public void addTestrayCaseResults(long pk, long[] testrayCaseResultPKs) {
		long companyId = 0;

		TestrayIssue testrayIssue = fetchByPrimaryKey(pk);

		if (testrayIssue == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testrayIssue.getCompanyId();
		}

		testrayIssueToTestrayCaseResultTableMapper.addTableMappings(
			companyId, pk, testrayCaseResultPKs);
	}

	/**
	 * Adds an association between the testray issue and the testray case results. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray issue
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
	 * Clears all associations between the testray issue and its testray case results. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray issue to clear the associated testray case results from
	 */
	@Override
	public void clearTestrayCaseResults(long pk) {
		testrayIssueToTestrayCaseResultTableMapper.
			deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the testray issue and the testray case result. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray issue
	 * @param testrayCaseResultPK the primary key of the testray case result
	 */
	@Override
	public void removeTestrayCaseResult(long pk, long testrayCaseResultPK) {
		testrayIssueToTestrayCaseResultTableMapper.deleteTableMapping(
			pk, testrayCaseResultPK);
	}

	/**
	 * Removes the association between the testray issue and the testray case result. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray issue
	 * @param testrayCaseResult the testray case result
	 */
	@Override
	public void removeTestrayCaseResult(
		long pk,
		com.liferay.osb.testray.model.TestrayCaseResult testrayCaseResult) {

		testrayIssueToTestrayCaseResultTableMapper.deleteTableMapping(
			pk, testrayCaseResult.getPrimaryKey());
	}

	/**
	 * Removes the association between the testray issue and the testray case results. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray issue
	 * @param testrayCaseResultPKs the primary keys of the testray case results
	 */
	@Override
	public void removeTestrayCaseResults(long pk, long[] testrayCaseResultPKs) {
		testrayIssueToTestrayCaseResultTableMapper.deleteTableMappings(
			pk, testrayCaseResultPKs);
	}

	/**
	 * Removes the association between the testray issue and the testray case results. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray issue
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
	 * Sets the testray case results associated with the testray issue, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray issue
	 * @param testrayCaseResultPKs the primary keys of the testray case results to be associated with the testray issue
	 */
	@Override
	public void setTestrayCaseResults(long pk, long[] testrayCaseResultPKs) {
		Set<Long> newTestrayCaseResultPKsSet = SetUtil.fromArray(
			testrayCaseResultPKs);
		Set<Long> oldTestrayCaseResultPKsSet = SetUtil.fromArray(
			testrayIssueToTestrayCaseResultTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeTestrayCaseResultPKsSet = new HashSet<Long>(
			oldTestrayCaseResultPKsSet);

		removeTestrayCaseResultPKsSet.removeAll(newTestrayCaseResultPKsSet);

		testrayIssueToTestrayCaseResultTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeTestrayCaseResultPKsSet));

		newTestrayCaseResultPKsSet.removeAll(oldTestrayCaseResultPKsSet);

		long companyId = 0;

		TestrayIssue testrayIssue = fetchByPrimaryKey(pk);

		if (testrayIssue == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testrayIssue.getCompanyId();
		}

		testrayIssueToTestrayCaseResultTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newTestrayCaseResultPKsSet));
	}

	/**
	 * Sets the testray case results associated with the testray issue, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray issue
	 * @param testrayCaseResults the testray case results to be associated with the testray issue
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
	 * Returns the primaryKeys of testray subtasks associated with the testray issue.
	 *
	 * @param pk the primary key of the testray issue
	 * @return long[] of the primaryKeys of testray subtasks associated with the testray issue
	 */
	@Override
	public long[] getTestraySubtaskPrimaryKeys(long pk) {
		long[] pks =
			testrayIssueToTestraySubtaskTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the testray subtasks associated with the testray issue.
	 *
	 * @param pk the primary key of the testray issue
	 * @return the testray subtasks associated with the testray issue
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestraySubtask>
		getTestraySubtasks(long pk) {

		return getTestraySubtasks(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the testray subtasks associated with the testray issue.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayIssueModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray issue
	 * @param start the lower bound of the range of testray issues
	 * @param end the upper bound of the range of testray issues (not inclusive)
	 * @return the range of testray subtasks associated with the testray issue
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestraySubtask>
		getTestraySubtasks(long pk, int start, int end) {

		return getTestraySubtasks(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray subtasks associated with the testray issue.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayIssueModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray issue
	 * @param start the lower bound of the range of testray issues
	 * @param end the upper bound of the range of testray issues (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray subtasks associated with the testray issue
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestraySubtask>
		getTestraySubtasks(
			long pk, int start, int end,
			OrderByComparator<com.liferay.osb.testray.model.TestraySubtask>
				orderByComparator) {

		return testrayIssueToTestraySubtaskTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of testray subtasks associated with the testray issue.
	 *
	 * @param pk the primary key of the testray issue
	 * @return the number of testray subtasks associated with the testray issue
	 */
	@Override
	public int getTestraySubtasksSize(long pk) {
		long[] pks =
			testrayIssueToTestraySubtaskTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the testray subtask is associated with the testray issue.
	 *
	 * @param pk the primary key of the testray issue
	 * @param testraySubtaskPK the primary key of the testray subtask
	 * @return <code>true</code> if the testray subtask is associated with the testray issue; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTestraySubtask(long pk, long testraySubtaskPK) {
		return testrayIssueToTestraySubtaskTableMapper.containsTableMapping(
			pk, testraySubtaskPK);
	}

	/**
	 * Returns <code>true</code> if the testray issue has any testray subtasks associated with it.
	 *
	 * @param pk the primary key of the testray issue to check for associations with testray subtasks
	 * @return <code>true</code> if the testray issue has any testray subtasks associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTestraySubtasks(long pk) {
		if (getTestraySubtasksSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the testray issue and the testray subtask. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray issue
	 * @param testraySubtaskPK the primary key of the testray subtask
	 */
	@Override
	public void addTestraySubtask(long pk, long testraySubtaskPK) {
		TestrayIssue testrayIssue = fetchByPrimaryKey(pk);

		if (testrayIssue == null) {
			testrayIssueToTestraySubtaskTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, testraySubtaskPK);
		}
		else {
			testrayIssueToTestraySubtaskTableMapper.addTableMapping(
				testrayIssue.getCompanyId(), pk, testraySubtaskPK);
		}
	}

	/**
	 * Adds an association between the testray issue and the testray subtask. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray issue
	 * @param testraySubtask the testray subtask
	 */
	@Override
	public void addTestraySubtask(
		long pk, com.liferay.osb.testray.model.TestraySubtask testraySubtask) {

		TestrayIssue testrayIssue = fetchByPrimaryKey(pk);

		if (testrayIssue == null) {
			testrayIssueToTestraySubtaskTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				testraySubtask.getPrimaryKey());
		}
		else {
			testrayIssueToTestraySubtaskTableMapper.addTableMapping(
				testrayIssue.getCompanyId(), pk,
				testraySubtask.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the testray issue and the testray subtasks. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray issue
	 * @param testraySubtaskPKs the primary keys of the testray subtasks
	 */
	@Override
	public void addTestraySubtasks(long pk, long[] testraySubtaskPKs) {
		long companyId = 0;

		TestrayIssue testrayIssue = fetchByPrimaryKey(pk);

		if (testrayIssue == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testrayIssue.getCompanyId();
		}

		testrayIssueToTestraySubtaskTableMapper.addTableMappings(
			companyId, pk, testraySubtaskPKs);
	}

	/**
	 * Adds an association between the testray issue and the testray subtasks. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray issue
	 * @param testraySubtasks the testray subtasks
	 */
	@Override
	public void addTestraySubtasks(
		long pk,
		List<com.liferay.osb.testray.model.TestraySubtask> testraySubtasks) {

		addTestraySubtasks(
			pk,
			ListUtil.toLongArray(
				testraySubtasks,
				com.liferay.osb.testray.model.TestraySubtask.
					TESTRAY_SUBTASK_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the testray issue and its testray subtasks. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray issue to clear the associated testray subtasks from
	 */
	@Override
	public void clearTestraySubtasks(long pk) {
		testrayIssueToTestraySubtaskTableMapper.
			deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the testray issue and the testray subtask. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray issue
	 * @param testraySubtaskPK the primary key of the testray subtask
	 */
	@Override
	public void removeTestraySubtask(long pk, long testraySubtaskPK) {
		testrayIssueToTestraySubtaskTableMapper.deleteTableMapping(
			pk, testraySubtaskPK);
	}

	/**
	 * Removes the association between the testray issue and the testray subtask. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray issue
	 * @param testraySubtask the testray subtask
	 */
	@Override
	public void removeTestraySubtask(
		long pk, com.liferay.osb.testray.model.TestraySubtask testraySubtask) {

		testrayIssueToTestraySubtaskTableMapper.deleteTableMapping(
			pk, testraySubtask.getPrimaryKey());
	}

	/**
	 * Removes the association between the testray issue and the testray subtasks. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray issue
	 * @param testraySubtaskPKs the primary keys of the testray subtasks
	 */
	@Override
	public void removeTestraySubtasks(long pk, long[] testraySubtaskPKs) {
		testrayIssueToTestraySubtaskTableMapper.deleteTableMappings(
			pk, testraySubtaskPKs);
	}

	/**
	 * Removes the association between the testray issue and the testray subtasks. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray issue
	 * @param testraySubtasks the testray subtasks
	 */
	@Override
	public void removeTestraySubtasks(
		long pk,
		List<com.liferay.osb.testray.model.TestraySubtask> testraySubtasks) {

		removeTestraySubtasks(
			pk,
			ListUtil.toLongArray(
				testraySubtasks,
				com.liferay.osb.testray.model.TestraySubtask.
					TESTRAY_SUBTASK_ID_ACCESSOR));
	}

	/**
	 * Sets the testray subtasks associated with the testray issue, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray issue
	 * @param testraySubtaskPKs the primary keys of the testray subtasks to be associated with the testray issue
	 */
	@Override
	public void setTestraySubtasks(long pk, long[] testraySubtaskPKs) {
		Set<Long> newTestraySubtaskPKsSet = SetUtil.fromArray(
			testraySubtaskPKs);
		Set<Long> oldTestraySubtaskPKsSet = SetUtil.fromArray(
			testrayIssueToTestraySubtaskTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeTestraySubtaskPKsSet = new HashSet<Long>(
			oldTestraySubtaskPKsSet);

		removeTestraySubtaskPKsSet.removeAll(newTestraySubtaskPKsSet);

		testrayIssueToTestraySubtaskTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeTestraySubtaskPKsSet));

		newTestraySubtaskPKsSet.removeAll(oldTestraySubtaskPKsSet);

		long companyId = 0;

		TestrayIssue testrayIssue = fetchByPrimaryKey(pk);

		if (testrayIssue == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testrayIssue.getCompanyId();
		}

		testrayIssueToTestraySubtaskTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newTestraySubtaskPKsSet));
	}

	/**
	 * Sets the testray subtasks associated with the testray issue, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray issue
	 * @param testraySubtasks the testray subtasks to be associated with the testray issue
	 */
	@Override
	public void setTestraySubtasks(
		long pk,
		List<com.liferay.osb.testray.model.TestraySubtask> testraySubtasks) {

		try {
			long[] testraySubtaskPKs = new long[testraySubtasks.size()];

			for (int i = 0; i < testraySubtasks.size(); i++) {
				com.liferay.osb.testray.model.TestraySubtask testraySubtask =
					testraySubtasks.get(i);

				testraySubtaskPKs[i] = testraySubtask.getPrimaryKey();
			}

			setTestraySubtasks(pk, testraySubtaskPKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return TestrayIssueModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the testray issue persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		testrayIssueToTestrayCaseResultTableMapper =
			TableMapperFactory.getTableMapper(
				"OSB_TestrayCaseResults_TestrayIssues", "companyId",
				"testrayIssueId", "testrayCaseResultId", this,
				testrayCaseResultPersistence);

		testrayIssueToTestraySubtaskTableMapper =
			TableMapperFactory.getTableMapper(
				"OSB_TestraySubtasks_TestrayIssues", "companyId",
				"testrayIssueId", "testraySubtaskId", this,
				testraySubtaskPersistence);

		_finderPathWithPaginationFindAll = new FinderPath(
			TestrayIssueModelImpl.ENTITY_CACHE_ENABLED,
			TestrayIssueModelImpl.FINDER_CACHE_ENABLED, TestrayIssueImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			TestrayIssueModelImpl.ENTITY_CACHE_ENABLED,
			TestrayIssueModelImpl.FINDER_CACHE_ENABLED, TestrayIssueImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			TestrayIssueModelImpl.ENTITY_CACHE_ENABLED,
			TestrayIssueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByName = new FinderPath(
			TestrayIssueModelImpl.ENTITY_CACHE_ENABLED,
			TestrayIssueModelImpl.FINDER_CACHE_ENABLED, TestrayIssueImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByName",
			new String[] {String.class.getName()},
			TestrayIssueModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByName = new FinderPath(
			TestrayIssueModelImpl.ENTITY_CACHE_ENABLED,
			TestrayIssueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByName",
			new String[] {String.class.getName()});

		TestrayIssueUtil.setPersistence(this);
	}

	public void destroy() {
		TestrayIssueUtil.setPersistence(null);

		entityCache.removeCache(TestrayIssueImpl.class.getName());

		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper(
			"OSB_TestrayCaseResults_TestrayIssues");
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
		<TestrayIssue, com.liferay.osb.testray.model.TestrayCaseResult>
			testrayIssueToTestrayCaseResultTableMapper;

	@BeanReference(type = TestraySubtaskPersistence.class)
	protected TestraySubtaskPersistence testraySubtaskPersistence;

	protected TableMapper
		<TestrayIssue, com.liferay.osb.testray.model.TestraySubtask>
			testrayIssueToTestraySubtaskTableMapper;

	private static final String _SQL_SELECT_TESTRAYISSUE =
		"SELECT testrayIssue FROM TestrayIssue testrayIssue";

	private static final String _SQL_SELECT_TESTRAYISSUE_WHERE_PKS_IN =
		"SELECT testrayIssue FROM TestrayIssue testrayIssue WHERE testrayIssueId IN (";

	private static final String _SQL_SELECT_TESTRAYISSUE_WHERE =
		"SELECT testrayIssue FROM TestrayIssue testrayIssue WHERE ";

	private static final String _SQL_COUNT_TESTRAYISSUE =
		"SELECT COUNT(testrayIssue) FROM TestrayIssue testrayIssue";

	private static final String _SQL_COUNT_TESTRAYISSUE_WHERE =
		"SELECT COUNT(testrayIssue) FROM TestrayIssue testrayIssue WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "testrayIssue.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No TestrayIssue exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No TestrayIssue exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		TestrayIssuePersistenceImpl.class);

}