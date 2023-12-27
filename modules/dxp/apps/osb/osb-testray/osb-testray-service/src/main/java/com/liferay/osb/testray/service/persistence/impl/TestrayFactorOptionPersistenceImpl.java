/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.testray.service.persistence.impl;

import com.liferay.osb.testray.exception.NoSuchTestrayFactorOptionException;
import com.liferay.osb.testray.model.TestrayFactorOption;
import com.liferay.osb.testray.model.impl.TestrayFactorOptionImpl;
import com.liferay.osb.testray.model.impl.TestrayFactorOptionModelImpl;
import com.liferay.osb.testray.service.persistence.TestrayFactorOptionPersistence;
import com.liferay.osb.testray.service.persistence.TestrayFactorOptionUtil;
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
 * The persistence implementation for the testray factor option service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ethan Bustad
 * @generated
 */
public class TestrayFactorOptionPersistenceImpl
	extends BasePersistenceImpl<TestrayFactorOption>
	implements TestrayFactorOptionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>TestrayFactorOptionUtil</code> to access the testray factor option persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		TestrayFactorOptionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByT_N;
	private FinderPath _finderPathCountByT_N;

	/**
	 * Returns the testray factor option where testrayFactorCategoryId = &#63; and name = &#63; or throws a <code>NoSuchTestrayFactorOptionException</code> if it could not be found.
	 *
	 * @param testrayFactorCategoryId the testray factor category ID
	 * @param name the name
	 * @return the matching testray factor option
	 * @throws NoSuchTestrayFactorOptionException if a matching testray factor option could not be found
	 */
	@Override
	public TestrayFactorOption findByT_N(
			long testrayFactorCategoryId, String name)
		throws NoSuchTestrayFactorOptionException {

		TestrayFactorOption testrayFactorOption = fetchByT_N(
			testrayFactorCategoryId, name);

		if (testrayFactorOption == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("testrayFactorCategoryId=");
			sb.append(testrayFactorCategoryId);

			sb.append(", name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchTestrayFactorOptionException(sb.toString());
		}

		return testrayFactorOption;
	}

	/**
	 * Returns the testray factor option where testrayFactorCategoryId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param testrayFactorCategoryId the testray factor category ID
	 * @param name the name
	 * @return the matching testray factor option, or <code>null</code> if a matching testray factor option could not be found
	 */
	@Override
	public TestrayFactorOption fetchByT_N(
		long testrayFactorCategoryId, String name) {

		return fetchByT_N(testrayFactorCategoryId, name, true);
	}

	/**
	 * Returns the testray factor option where testrayFactorCategoryId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param testrayFactorCategoryId the testray factor category ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching testray factor option, or <code>null</code> if a matching testray factor option could not be found
	 */
	@Override
	public TestrayFactorOption fetchByT_N(
		long testrayFactorCategoryId, String name, boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {testrayFactorCategoryId, name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByT_N, finderArgs, this);
		}

		if (result instanceof TestrayFactorOption) {
			TestrayFactorOption testrayFactorOption =
				(TestrayFactorOption)result;

			if ((testrayFactorCategoryId !=
					testrayFactorOption.getTestrayFactorCategoryId()) ||
				!Objects.equals(name, testrayFactorOption.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_TESTRAYFACTOROPTION_WHERE);

			sb.append(_FINDER_COLUMN_T_N_TESTRAYFACTORCATEGORYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_T_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_T_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(testrayFactorCategoryId);

				if (bindName) {
					queryPos.add(name);
				}

				List<TestrayFactorOption> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByT_N, finderArgs, list);
					}
				}
				else {
					TestrayFactorOption testrayFactorOption = list.get(0);

					result = testrayFactorOption;

					cacheResult(testrayFactorOption);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(_finderPathFetchByT_N, finderArgs);
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
			return (TestrayFactorOption)result;
		}
	}

	/**
	 * Removes the testray factor option where testrayFactorCategoryId = &#63; and name = &#63; from the database.
	 *
	 * @param testrayFactorCategoryId the testray factor category ID
	 * @param name the name
	 * @return the testray factor option that was removed
	 */
	@Override
	public TestrayFactorOption removeByT_N(
			long testrayFactorCategoryId, String name)
		throws NoSuchTestrayFactorOptionException {

		TestrayFactorOption testrayFactorOption = findByT_N(
			testrayFactorCategoryId, name);

		return remove(testrayFactorOption);
	}

	/**
	 * Returns the number of testray factor options where testrayFactorCategoryId = &#63; and name = &#63;.
	 *
	 * @param testrayFactorCategoryId the testray factor category ID
	 * @param name the name
	 * @return the number of matching testray factor options
	 */
	@Override
	public int countByT_N(long testrayFactorCategoryId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByT_N;

		Object[] finderArgs = new Object[] {testrayFactorCategoryId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_TESTRAYFACTOROPTION_WHERE);

			sb.append(_FINDER_COLUMN_T_N_TESTRAYFACTORCATEGORYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_T_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_T_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(testrayFactorCategoryId);

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

	private static final String _FINDER_COLUMN_T_N_TESTRAYFACTORCATEGORYID_2 =
		"testrayFactorOption.testrayFactorCategoryId = ? AND ";

	private static final String _FINDER_COLUMN_T_N_NAME_2 =
		"testrayFactorOption.name = ?";

	private static final String _FINDER_COLUMN_T_N_NAME_3 =
		"(testrayFactorOption.name IS NULL OR testrayFactorOption.name = '')";

	public TestrayFactorOptionPersistenceImpl() {
		setModelClass(TestrayFactorOption.class);
	}

	/**
	 * Caches the testray factor option in the entity cache if it is enabled.
	 *
	 * @param testrayFactorOption the testray factor option
	 */
	@Override
	public void cacheResult(TestrayFactorOption testrayFactorOption) {
		entityCache.putResult(
			TestrayFactorOptionModelImpl.ENTITY_CACHE_ENABLED,
			TestrayFactorOptionImpl.class, testrayFactorOption.getPrimaryKey(),
			testrayFactorOption);

		finderCache.putResult(
			_finderPathFetchByT_N,
			new Object[] {
				testrayFactorOption.getTestrayFactorCategoryId(),
				testrayFactorOption.getName()
			},
			testrayFactorOption);

		testrayFactorOption.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the testray factor options in the entity cache if it is enabled.
	 *
	 * @param testrayFactorOptions the testray factor options
	 */
	@Override
	public void cacheResult(List<TestrayFactorOption> testrayFactorOptions) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (testrayFactorOptions.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (TestrayFactorOption testrayFactorOption : testrayFactorOptions) {
			if (entityCache.getResult(
					TestrayFactorOptionModelImpl.ENTITY_CACHE_ENABLED,
					TestrayFactorOptionImpl.class,
					testrayFactorOption.getPrimaryKey()) == null) {

				cacheResult(testrayFactorOption);
			}
			else {
				testrayFactorOption.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all testray factor options.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(TestrayFactorOptionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the testray factor option.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(TestrayFactorOption testrayFactorOption) {
		entityCache.removeResult(
			TestrayFactorOptionModelImpl.ENTITY_CACHE_ENABLED,
			TestrayFactorOptionImpl.class, testrayFactorOption.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(TestrayFactorOptionModelImpl)testrayFactorOption, true);
	}

	@Override
	public void clearCache(List<TestrayFactorOption> testrayFactorOptions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (TestrayFactorOption testrayFactorOption : testrayFactorOptions) {
			entityCache.removeResult(
				TestrayFactorOptionModelImpl.ENTITY_CACHE_ENABLED,
				TestrayFactorOptionImpl.class,
				testrayFactorOption.getPrimaryKey());

			clearUniqueFindersCache(
				(TestrayFactorOptionModelImpl)testrayFactorOption, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				TestrayFactorOptionModelImpl.ENTITY_CACHE_ENABLED,
				TestrayFactorOptionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		TestrayFactorOptionModelImpl testrayFactorOptionModelImpl) {

		Object[] args = new Object[] {
			testrayFactorOptionModelImpl.getTestrayFactorCategoryId(),
			testrayFactorOptionModelImpl.getName()
		};

		finderCache.putResult(
			_finderPathCountByT_N, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByT_N, args, testrayFactorOptionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		TestrayFactorOptionModelImpl testrayFactorOptionModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				testrayFactorOptionModelImpl.getTestrayFactorCategoryId(),
				testrayFactorOptionModelImpl.getName()
			};

			finderCache.removeResult(_finderPathCountByT_N, args);
			finderCache.removeResult(_finderPathFetchByT_N, args);
		}

		if ((testrayFactorOptionModelImpl.getColumnBitmask() &
			 _finderPathFetchByT_N.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				testrayFactorOptionModelImpl.
					getOriginalTestrayFactorCategoryId(),
				testrayFactorOptionModelImpl.getOriginalName()
			};

			finderCache.removeResult(_finderPathCountByT_N, args);
			finderCache.removeResult(_finderPathFetchByT_N, args);
		}
	}

	/**
	 * Creates a new testray factor option with the primary key. Does not add the testray factor option to the database.
	 *
	 * @param testrayFactorOptionId the primary key for the new testray factor option
	 * @return the new testray factor option
	 */
	@Override
	public TestrayFactorOption create(long testrayFactorOptionId) {
		TestrayFactorOption testrayFactorOption = new TestrayFactorOptionImpl();

		testrayFactorOption.setNew(true);
		testrayFactorOption.setPrimaryKey(testrayFactorOptionId);

		testrayFactorOption.setCompanyId(CompanyThreadLocal.getCompanyId());

		return testrayFactorOption;
	}

	/**
	 * Removes the testray factor option with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param testrayFactorOptionId the primary key of the testray factor option
	 * @return the testray factor option that was removed
	 * @throws NoSuchTestrayFactorOptionException if a testray factor option with the primary key could not be found
	 */
	@Override
	public TestrayFactorOption remove(long testrayFactorOptionId)
		throws NoSuchTestrayFactorOptionException {

		return remove((Serializable)testrayFactorOptionId);
	}

	/**
	 * Removes the testray factor option with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the testray factor option
	 * @return the testray factor option that was removed
	 * @throws NoSuchTestrayFactorOptionException if a testray factor option with the primary key could not be found
	 */
	@Override
	public TestrayFactorOption remove(Serializable primaryKey)
		throws NoSuchTestrayFactorOptionException {

		Session session = null;

		try {
			session = openSession();

			TestrayFactorOption testrayFactorOption =
				(TestrayFactorOption)session.get(
					TestrayFactorOptionImpl.class, primaryKey);

			if (testrayFactorOption == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTestrayFactorOptionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(testrayFactorOption);
		}
		catch (NoSuchTestrayFactorOptionException noSuchEntityException) {
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
	protected TestrayFactorOption removeImpl(
		TestrayFactorOption testrayFactorOption) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(testrayFactorOption)) {
				testrayFactorOption = (TestrayFactorOption)session.get(
					TestrayFactorOptionImpl.class,
					testrayFactorOption.getPrimaryKeyObj());
			}

			if (testrayFactorOption != null) {
				session.delete(testrayFactorOption);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (testrayFactorOption != null) {
			clearCache(testrayFactorOption);
		}

		return testrayFactorOption;
	}

	@Override
	public TestrayFactorOption updateImpl(
		TestrayFactorOption testrayFactorOption) {

		boolean isNew = testrayFactorOption.isNew();

		if (!(testrayFactorOption instanceof TestrayFactorOptionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(testrayFactorOption.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					testrayFactorOption);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in testrayFactorOption proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom TestrayFactorOption implementation " +
					testrayFactorOption.getClass());
		}

		TestrayFactorOptionModelImpl testrayFactorOptionModelImpl =
			(TestrayFactorOptionModelImpl)testrayFactorOption;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (testrayFactorOption.getCreateDate() == null)) {
			if (serviceContext == null) {
				testrayFactorOption.setCreateDate(date);
			}
			else {
				testrayFactorOption.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!testrayFactorOptionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				testrayFactorOption.setModifiedDate(date);
			}
			else {
				testrayFactorOption.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(testrayFactorOption);

				testrayFactorOption.setNew(false);
			}
			else {
				testrayFactorOption = (TestrayFactorOption)session.merge(
					testrayFactorOption);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!TestrayFactorOptionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			TestrayFactorOptionModelImpl.ENTITY_CACHE_ENABLED,
			TestrayFactorOptionImpl.class, testrayFactorOption.getPrimaryKey(),
			testrayFactorOption, false);

		clearUniqueFindersCache(testrayFactorOptionModelImpl, false);
		cacheUniqueFindersCache(testrayFactorOptionModelImpl);

		testrayFactorOption.resetOriginalValues();

		return testrayFactorOption;
	}

	/**
	 * Returns the testray factor option with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray factor option
	 * @return the testray factor option
	 * @throws NoSuchTestrayFactorOptionException if a testray factor option with the primary key could not be found
	 */
	@Override
	public TestrayFactorOption findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTestrayFactorOptionException {

		TestrayFactorOption testrayFactorOption = fetchByPrimaryKey(primaryKey);

		if (testrayFactorOption == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTestrayFactorOptionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return testrayFactorOption;
	}

	/**
	 * Returns the testray factor option with the primary key or throws a <code>NoSuchTestrayFactorOptionException</code> if it could not be found.
	 *
	 * @param testrayFactorOptionId the primary key of the testray factor option
	 * @return the testray factor option
	 * @throws NoSuchTestrayFactorOptionException if a testray factor option with the primary key could not be found
	 */
	@Override
	public TestrayFactorOption findByPrimaryKey(long testrayFactorOptionId)
		throws NoSuchTestrayFactorOptionException {

		return findByPrimaryKey((Serializable)testrayFactorOptionId);
	}

	/**
	 * Returns the testray factor option with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray factor option
	 * @return the testray factor option, or <code>null</code> if a testray factor option with the primary key could not be found
	 */
	@Override
	public TestrayFactorOption fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			TestrayFactorOptionModelImpl.ENTITY_CACHE_ENABLED,
			TestrayFactorOptionImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		TestrayFactorOption testrayFactorOption =
			(TestrayFactorOption)serializable;

		if (testrayFactorOption == null) {
			Session session = null;

			try {
				session = openSession();

				testrayFactorOption = (TestrayFactorOption)session.get(
					TestrayFactorOptionImpl.class, primaryKey);

				if (testrayFactorOption != null) {
					cacheResult(testrayFactorOption);
				}
				else {
					entityCache.putResult(
						TestrayFactorOptionModelImpl.ENTITY_CACHE_ENABLED,
						TestrayFactorOptionImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					TestrayFactorOptionModelImpl.ENTITY_CACHE_ENABLED,
					TestrayFactorOptionImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return testrayFactorOption;
	}

	/**
	 * Returns the testray factor option with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param testrayFactorOptionId the primary key of the testray factor option
	 * @return the testray factor option, or <code>null</code> if a testray factor option with the primary key could not be found
	 */
	@Override
	public TestrayFactorOption fetchByPrimaryKey(long testrayFactorOptionId) {
		return fetchByPrimaryKey((Serializable)testrayFactorOptionId);
	}

	@Override
	public Map<Serializable, TestrayFactorOption> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, TestrayFactorOption> map =
			new HashMap<Serializable, TestrayFactorOption>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			TestrayFactorOption testrayFactorOption = fetchByPrimaryKey(
				primaryKey);

			if (testrayFactorOption != null) {
				map.put(primaryKey, testrayFactorOption);
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
				TestrayFactorOptionModelImpl.ENTITY_CACHE_ENABLED,
				TestrayFactorOptionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (TestrayFactorOption)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_TESTRAYFACTOROPTION_WHERE_PKS_IN);

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

			for (TestrayFactorOption testrayFactorOption :
					(List<TestrayFactorOption>)query.list()) {

				map.put(
					testrayFactorOption.getPrimaryKeyObj(),
					testrayFactorOption);

				cacheResult(testrayFactorOption);

				uncachedPrimaryKeys.remove(
					testrayFactorOption.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					TestrayFactorOptionModelImpl.ENTITY_CACHE_ENABLED,
					TestrayFactorOptionImpl.class, primaryKey, nullModel);
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
	 * Returns all the testray factor options.
	 *
	 * @return the testray factor options
	 */
	@Override
	public List<TestrayFactorOption> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the testray factor options.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayFactorOptionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray factor options
	 * @param end the upper bound of the range of testray factor options (not inclusive)
	 * @return the range of testray factor options
	 */
	@Override
	public List<TestrayFactorOption> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray factor options.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayFactorOptionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray factor options
	 * @param end the upper bound of the range of testray factor options (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray factor options
	 */
	@Override
	public List<TestrayFactorOption> findAll(
		int start, int end,
		OrderByComparator<TestrayFactorOption> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the testray factor options.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayFactorOptionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray factor options
	 * @param end the upper bound of the range of testray factor options (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of testray factor options
	 */
	@Override
	public List<TestrayFactorOption> findAll(
		int start, int end,
		OrderByComparator<TestrayFactorOption> orderByComparator,
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

		List<TestrayFactorOption> list = null;

		if (useFinderCache) {
			list = (List<TestrayFactorOption>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_TESTRAYFACTOROPTION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_TESTRAYFACTOROPTION;

				sql = sql.concat(TestrayFactorOptionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<TestrayFactorOption>)QueryUtil.list(
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
	 * Removes all the testray factor options from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (TestrayFactorOption testrayFactorOption : findAll()) {
			remove(testrayFactorOption);
		}
	}

	/**
	 * Returns the number of testray factor options.
	 *
	 * @return the number of testray factor options
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
					_SQL_COUNT_TESTRAYFACTOROPTION);

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
		return TestrayFactorOptionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the testray factor option persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			TestrayFactorOptionModelImpl.ENTITY_CACHE_ENABLED,
			TestrayFactorOptionModelImpl.FINDER_CACHE_ENABLED,
			TestrayFactorOptionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			TestrayFactorOptionModelImpl.ENTITY_CACHE_ENABLED,
			TestrayFactorOptionModelImpl.FINDER_CACHE_ENABLED,
			TestrayFactorOptionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			TestrayFactorOptionModelImpl.ENTITY_CACHE_ENABLED,
			TestrayFactorOptionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByT_N = new FinderPath(
			TestrayFactorOptionModelImpl.ENTITY_CACHE_ENABLED,
			TestrayFactorOptionModelImpl.FINDER_CACHE_ENABLED,
			TestrayFactorOptionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByT_N",
			new String[] {Long.class.getName(), String.class.getName()},
			TestrayFactorOptionModelImpl.
				TESTRAYFACTORCATEGORYID_COLUMN_BITMASK |
			TestrayFactorOptionModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByT_N = new FinderPath(
			TestrayFactorOptionModelImpl.ENTITY_CACHE_ENABLED,
			TestrayFactorOptionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_N",
			new String[] {Long.class.getName(), String.class.getName()});

		TestrayFactorOptionUtil.setPersistence(this);
	}

	public void destroy() {
		TestrayFactorOptionUtil.setPersistence(null);

		entityCache.removeCache(TestrayFactorOptionImpl.class.getName());

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

	private static final String _SQL_SELECT_TESTRAYFACTOROPTION =
		"SELECT testrayFactorOption FROM TestrayFactorOption testrayFactorOption";

	private static final String _SQL_SELECT_TESTRAYFACTOROPTION_WHERE_PKS_IN =
		"SELECT testrayFactorOption FROM TestrayFactorOption testrayFactorOption WHERE testrayFactorOptionId IN (";

	private static final String _SQL_SELECT_TESTRAYFACTOROPTION_WHERE =
		"SELECT testrayFactorOption FROM TestrayFactorOption testrayFactorOption WHERE ";

	private static final String _SQL_COUNT_TESTRAYFACTOROPTION =
		"SELECT COUNT(testrayFactorOption) FROM TestrayFactorOption testrayFactorOption";

	private static final String _SQL_COUNT_TESTRAYFACTOROPTION_WHERE =
		"SELECT COUNT(testrayFactorOption) FROM TestrayFactorOption testrayFactorOption WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "testrayFactorOption.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No TestrayFactorOption exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No TestrayFactorOption exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		TestrayFactorOptionPersistenceImpl.class);

}