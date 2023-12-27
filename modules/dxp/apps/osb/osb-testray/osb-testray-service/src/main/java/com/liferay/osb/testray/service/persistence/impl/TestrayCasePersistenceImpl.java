/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.testray.service.persistence.impl;

import com.liferay.osb.testray.exception.NoSuchTestrayCaseException;
import com.liferay.osb.testray.model.TestrayCase;
import com.liferay.osb.testray.model.impl.TestrayCaseImpl;
import com.liferay.osb.testray.model.impl.TestrayCaseModelImpl;
import com.liferay.osb.testray.service.persistence.TestrayBuildPersistence;
import com.liferay.osb.testray.service.persistence.TestrayCasePersistence;
import com.liferay.osb.testray.service.persistence.TestrayCaseUtil;
import com.liferay.osb.testray.service.persistence.TestrayComponentPersistence;
import com.liferay.osb.testray.service.persistence.TestrayRequirementPersistence;
import com.liferay.osb.testray.service.persistence.TestraySuitePersistence;
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
 * The persistence implementation for the testray case service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ethan Bustad
 * @generated
 */
public class TestrayCasePersistenceImpl
	extends BasePersistenceImpl<TestrayCase> implements TestrayCasePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>TestrayCaseUtil</code> to access the testray case persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		TestrayCaseImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByTPI_N;
	private FinderPath _finderPathCountByTPI_N;

	/**
	 * Returns the testray case where testrayProjectId = &#63; and name = &#63; or throws a <code>NoSuchTestrayCaseException</code> if it could not be found.
	 *
	 * @param testrayProjectId the testray project ID
	 * @param name the name
	 * @return the matching testray case
	 * @throws NoSuchTestrayCaseException if a matching testray case could not be found
	 */
	@Override
	public TestrayCase findByTPI_N(long testrayProjectId, String name)
		throws NoSuchTestrayCaseException {

		TestrayCase testrayCase = fetchByTPI_N(testrayProjectId, name);

		if (testrayCase == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("testrayProjectId=");
			sb.append(testrayProjectId);

			sb.append(", name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchTestrayCaseException(sb.toString());
		}

		return testrayCase;
	}

	/**
	 * Returns the testray case where testrayProjectId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param testrayProjectId the testray project ID
	 * @param name the name
	 * @return the matching testray case, or <code>null</code> if a matching testray case could not be found
	 */
	@Override
	public TestrayCase fetchByTPI_N(long testrayProjectId, String name) {
		return fetchByTPI_N(testrayProjectId, name, true);
	}

	/**
	 * Returns the testray case where testrayProjectId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param testrayProjectId the testray project ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching testray case, or <code>null</code> if a matching testray case could not be found
	 */
	@Override
	public TestrayCase fetchByTPI_N(
		long testrayProjectId, String name, boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {testrayProjectId, name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByTPI_N, finderArgs, this);
		}

		if (result instanceof TestrayCase) {
			TestrayCase testrayCase = (TestrayCase)result;

			if ((testrayProjectId != testrayCase.getTestrayProjectId()) ||
				!Objects.equals(name, testrayCase.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_TESTRAYCASE_WHERE);

			sb.append(_FINDER_COLUMN_TPI_N_TESTRAYPROJECTID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_TPI_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_TPI_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(testrayProjectId);

				if (bindName) {
					queryPos.add(name);
				}

				List<TestrayCase> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByTPI_N, finderArgs, list);
					}
				}
				else {
					TestrayCase testrayCase = list.get(0);

					result = testrayCase;

					cacheResult(testrayCase);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByTPI_N, finderArgs);
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
			return (TestrayCase)result;
		}
	}

	/**
	 * Removes the testray case where testrayProjectId = &#63; and name = &#63; from the database.
	 *
	 * @param testrayProjectId the testray project ID
	 * @param name the name
	 * @return the testray case that was removed
	 */
	@Override
	public TestrayCase removeByTPI_N(long testrayProjectId, String name)
		throws NoSuchTestrayCaseException {

		TestrayCase testrayCase = findByTPI_N(testrayProjectId, name);

		return remove(testrayCase);
	}

	/**
	 * Returns the number of testray cases where testrayProjectId = &#63; and name = &#63;.
	 *
	 * @param testrayProjectId the testray project ID
	 * @param name the name
	 * @return the number of matching testray cases
	 */
	@Override
	public int countByTPI_N(long testrayProjectId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByTPI_N;

		Object[] finderArgs = new Object[] {testrayProjectId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_TESTRAYCASE_WHERE);

			sb.append(_FINDER_COLUMN_TPI_N_TESTRAYPROJECTID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_TPI_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_TPI_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(testrayProjectId);

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

	private static final String _FINDER_COLUMN_TPI_N_TESTRAYPROJECTID_2 =
		"testrayCase.testrayProjectId = ? AND ";

	private static final String _FINDER_COLUMN_TPI_N_NAME_2 =
		"testrayCase.name = ?";

	private static final String _FINDER_COLUMN_TPI_N_NAME_3 =
		"(testrayCase.name IS NULL OR testrayCase.name = '')";

	public TestrayCasePersistenceImpl() {
		setModelClass(TestrayCase.class);
	}

	/**
	 * Caches the testray case in the entity cache if it is enabled.
	 *
	 * @param testrayCase the testray case
	 */
	@Override
	public void cacheResult(TestrayCase testrayCase) {
		entityCache.putResult(
			TestrayCaseModelImpl.ENTITY_CACHE_ENABLED, TestrayCaseImpl.class,
			testrayCase.getPrimaryKey(), testrayCase);

		finderCache.putResult(
			_finderPathFetchByTPI_N,
			new Object[] {
				testrayCase.getTestrayProjectId(), testrayCase.getName()
			},
			testrayCase);

		testrayCase.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the testray cases in the entity cache if it is enabled.
	 *
	 * @param testrayCases the testray cases
	 */
	@Override
	public void cacheResult(List<TestrayCase> testrayCases) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (testrayCases.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (TestrayCase testrayCase : testrayCases) {
			if (entityCache.getResult(
					TestrayCaseModelImpl.ENTITY_CACHE_ENABLED,
					TestrayCaseImpl.class, testrayCase.getPrimaryKey()) ==
						null) {

				cacheResult(testrayCase);
			}
			else {
				testrayCase.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all testray cases.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(TestrayCaseImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the testray case.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(TestrayCase testrayCase) {
		entityCache.removeResult(
			TestrayCaseModelImpl.ENTITY_CACHE_ENABLED, TestrayCaseImpl.class,
			testrayCase.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((TestrayCaseModelImpl)testrayCase, true);
	}

	@Override
	public void clearCache(List<TestrayCase> testrayCases) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (TestrayCase testrayCase : testrayCases) {
			entityCache.removeResult(
				TestrayCaseModelImpl.ENTITY_CACHE_ENABLED,
				TestrayCaseImpl.class, testrayCase.getPrimaryKey());

			clearUniqueFindersCache((TestrayCaseModelImpl)testrayCase, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				TestrayCaseModelImpl.ENTITY_CACHE_ENABLED,
				TestrayCaseImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		TestrayCaseModelImpl testrayCaseModelImpl) {

		Object[] args = new Object[] {
			testrayCaseModelImpl.getTestrayProjectId(),
			testrayCaseModelImpl.getName()
		};

		finderCache.putResult(
			_finderPathCountByTPI_N, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByTPI_N, args, testrayCaseModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		TestrayCaseModelImpl testrayCaseModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				testrayCaseModelImpl.getTestrayProjectId(),
				testrayCaseModelImpl.getName()
			};

			finderCache.removeResult(_finderPathCountByTPI_N, args);
			finderCache.removeResult(_finderPathFetchByTPI_N, args);
		}

		if ((testrayCaseModelImpl.getColumnBitmask() &
			 _finderPathFetchByTPI_N.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				testrayCaseModelImpl.getOriginalTestrayProjectId(),
				testrayCaseModelImpl.getOriginalName()
			};

			finderCache.removeResult(_finderPathCountByTPI_N, args);
			finderCache.removeResult(_finderPathFetchByTPI_N, args);
		}
	}

	/**
	 * Creates a new testray case with the primary key. Does not add the testray case to the database.
	 *
	 * @param testrayCaseId the primary key for the new testray case
	 * @return the new testray case
	 */
	@Override
	public TestrayCase create(long testrayCaseId) {
		TestrayCase testrayCase = new TestrayCaseImpl();

		testrayCase.setNew(true);
		testrayCase.setPrimaryKey(testrayCaseId);

		testrayCase.setCompanyId(CompanyThreadLocal.getCompanyId());

		return testrayCase;
	}

	/**
	 * Removes the testray case with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param testrayCaseId the primary key of the testray case
	 * @return the testray case that was removed
	 * @throws NoSuchTestrayCaseException if a testray case with the primary key could not be found
	 */
	@Override
	public TestrayCase remove(long testrayCaseId)
		throws NoSuchTestrayCaseException {

		return remove((Serializable)testrayCaseId);
	}

	/**
	 * Removes the testray case with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the testray case
	 * @return the testray case that was removed
	 * @throws NoSuchTestrayCaseException if a testray case with the primary key could not be found
	 */
	@Override
	public TestrayCase remove(Serializable primaryKey)
		throws NoSuchTestrayCaseException {

		Session session = null;

		try {
			session = openSession();

			TestrayCase testrayCase = (TestrayCase)session.get(
				TestrayCaseImpl.class, primaryKey);

			if (testrayCase == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTestrayCaseException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(testrayCase);
		}
		catch (NoSuchTestrayCaseException noSuchEntityException) {
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
	protected TestrayCase removeImpl(TestrayCase testrayCase) {
		testrayCaseToTestrayBuildTableMapper.deleteLeftPrimaryKeyTableMappings(
			testrayCase.getPrimaryKey());

		testrayCaseToTestrayComponentTableMapper.
			deleteLeftPrimaryKeyTableMappings(testrayCase.getPrimaryKey());

		testrayCaseToTestrayRequirementTableMapper.
			deleteLeftPrimaryKeyTableMappings(testrayCase.getPrimaryKey());

		testrayCaseToTestraySuiteTableMapper.deleteLeftPrimaryKeyTableMappings(
			testrayCase.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(testrayCase)) {
				testrayCase = (TestrayCase)session.get(
					TestrayCaseImpl.class, testrayCase.getPrimaryKeyObj());
			}

			if (testrayCase != null) {
				session.delete(testrayCase);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (testrayCase != null) {
			clearCache(testrayCase);
		}

		return testrayCase;
	}

	@Override
	public TestrayCase updateImpl(TestrayCase testrayCase) {
		boolean isNew = testrayCase.isNew();

		if (!(testrayCase instanceof TestrayCaseModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(testrayCase.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(testrayCase);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in testrayCase proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom TestrayCase implementation " +
					testrayCase.getClass());
		}

		TestrayCaseModelImpl testrayCaseModelImpl =
			(TestrayCaseModelImpl)testrayCase;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (testrayCase.getCreateDate() == null)) {
			if (serviceContext == null) {
				testrayCase.setCreateDate(date);
			}
			else {
				testrayCase.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!testrayCaseModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				testrayCase.setModifiedDate(date);
			}
			else {
				testrayCase.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(testrayCase);

				testrayCase.setNew(false);
			}
			else {
				testrayCase = (TestrayCase)session.merge(testrayCase);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!TestrayCaseModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			TestrayCaseModelImpl.ENTITY_CACHE_ENABLED, TestrayCaseImpl.class,
			testrayCase.getPrimaryKey(), testrayCase, false);

		clearUniqueFindersCache(testrayCaseModelImpl, false);
		cacheUniqueFindersCache(testrayCaseModelImpl);

		testrayCase.resetOriginalValues();

		return testrayCase;
	}

	/**
	 * Returns the testray case with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray case
	 * @return the testray case
	 * @throws NoSuchTestrayCaseException if a testray case with the primary key could not be found
	 */
	@Override
	public TestrayCase findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTestrayCaseException {

		TestrayCase testrayCase = fetchByPrimaryKey(primaryKey);

		if (testrayCase == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTestrayCaseException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return testrayCase;
	}

	/**
	 * Returns the testray case with the primary key or throws a <code>NoSuchTestrayCaseException</code> if it could not be found.
	 *
	 * @param testrayCaseId the primary key of the testray case
	 * @return the testray case
	 * @throws NoSuchTestrayCaseException if a testray case with the primary key could not be found
	 */
	@Override
	public TestrayCase findByPrimaryKey(long testrayCaseId)
		throws NoSuchTestrayCaseException {

		return findByPrimaryKey((Serializable)testrayCaseId);
	}

	/**
	 * Returns the testray case with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray case
	 * @return the testray case, or <code>null</code> if a testray case with the primary key could not be found
	 */
	@Override
	public TestrayCase fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			TestrayCaseModelImpl.ENTITY_CACHE_ENABLED, TestrayCaseImpl.class,
			primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		TestrayCase testrayCase = (TestrayCase)serializable;

		if (testrayCase == null) {
			Session session = null;

			try {
				session = openSession();

				testrayCase = (TestrayCase)session.get(
					TestrayCaseImpl.class, primaryKey);

				if (testrayCase != null) {
					cacheResult(testrayCase);
				}
				else {
					entityCache.putResult(
						TestrayCaseModelImpl.ENTITY_CACHE_ENABLED,
						TestrayCaseImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					TestrayCaseModelImpl.ENTITY_CACHE_ENABLED,
					TestrayCaseImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return testrayCase;
	}

	/**
	 * Returns the testray case with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param testrayCaseId the primary key of the testray case
	 * @return the testray case, or <code>null</code> if a testray case with the primary key could not be found
	 */
	@Override
	public TestrayCase fetchByPrimaryKey(long testrayCaseId) {
		return fetchByPrimaryKey((Serializable)testrayCaseId);
	}

	@Override
	public Map<Serializable, TestrayCase> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, TestrayCase> map =
			new HashMap<Serializable, TestrayCase>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			TestrayCase testrayCase = fetchByPrimaryKey(primaryKey);

			if (testrayCase != null) {
				map.put(primaryKey, testrayCase);
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
				TestrayCaseModelImpl.ENTITY_CACHE_ENABLED,
				TestrayCaseImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (TestrayCase)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_TESTRAYCASE_WHERE_PKS_IN);

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

			for (TestrayCase testrayCase : (List<TestrayCase>)query.list()) {
				map.put(testrayCase.getPrimaryKeyObj(), testrayCase);

				cacheResult(testrayCase);

				uncachedPrimaryKeys.remove(testrayCase.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					TestrayCaseModelImpl.ENTITY_CACHE_ENABLED,
					TestrayCaseImpl.class, primaryKey, nullModel);
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
	 * Returns all the testray cases.
	 *
	 * @return the testray cases
	 */
	@Override
	public List<TestrayCase> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the testray cases.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray cases
	 * @param end the upper bound of the range of testray cases (not inclusive)
	 * @return the range of testray cases
	 */
	@Override
	public List<TestrayCase> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray cases.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray cases
	 * @param end the upper bound of the range of testray cases (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray cases
	 */
	@Override
	public List<TestrayCase> findAll(
		int start, int end, OrderByComparator<TestrayCase> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the testray cases.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray cases
	 * @param end the upper bound of the range of testray cases (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of testray cases
	 */
	@Override
	public List<TestrayCase> findAll(
		int start, int end, OrderByComparator<TestrayCase> orderByComparator,
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

		List<TestrayCase> list = null;

		if (useFinderCache) {
			list = (List<TestrayCase>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_TESTRAYCASE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_TESTRAYCASE;

				sql = sql.concat(TestrayCaseModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<TestrayCase>)QueryUtil.list(
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
	 * Removes all the testray cases from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (TestrayCase testrayCase : findAll()) {
			remove(testrayCase);
		}
	}

	/**
	 * Returns the number of testray cases.
	 *
	 * @return the number of testray cases
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_TESTRAYCASE);

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
	 * Returns the primaryKeys of testray builds associated with the testray case.
	 *
	 * @param pk the primary key of the testray case
	 * @return long[] of the primaryKeys of testray builds associated with the testray case
	 */
	@Override
	public long[] getTestrayBuildPrimaryKeys(long pk) {
		long[] pks = testrayCaseToTestrayBuildTableMapper.getRightPrimaryKeys(
			pk);

		return pks.clone();
	}

	/**
	 * Returns all the testray builds associated with the testray case.
	 *
	 * @param pk the primary key of the testray case
	 * @return the testray builds associated with the testray case
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayBuild> getTestrayBuilds(
		long pk) {

		return getTestrayBuilds(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the testray builds associated with the testray case.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray case
	 * @param start the lower bound of the range of testray cases
	 * @param end the upper bound of the range of testray cases (not inclusive)
	 * @return the range of testray builds associated with the testray case
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayBuild> getTestrayBuilds(
		long pk, int start, int end) {

		return getTestrayBuilds(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray builds associated with the testray case.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray case
	 * @param start the lower bound of the range of testray cases
	 * @param end the upper bound of the range of testray cases (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray builds associated with the testray case
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayBuild> getTestrayBuilds(
		long pk, int start, int end,
		OrderByComparator<com.liferay.osb.testray.model.TestrayBuild>
			orderByComparator) {

		return testrayCaseToTestrayBuildTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of testray builds associated with the testray case.
	 *
	 * @param pk the primary key of the testray case
	 * @return the number of testray builds associated with the testray case
	 */
	@Override
	public int getTestrayBuildsSize(long pk) {
		long[] pks = testrayCaseToTestrayBuildTableMapper.getRightPrimaryKeys(
			pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the testray build is associated with the testray case.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayBuildPK the primary key of the testray build
	 * @return <code>true</code> if the testray build is associated with the testray case; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTestrayBuild(long pk, long testrayBuildPK) {
		return testrayCaseToTestrayBuildTableMapper.containsTableMapping(
			pk, testrayBuildPK);
	}

	/**
	 * Returns <code>true</code> if the testray case has any testray builds associated with it.
	 *
	 * @param pk the primary key of the testray case to check for associations with testray builds
	 * @return <code>true</code> if the testray case has any testray builds associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTestrayBuilds(long pk) {
		if (getTestrayBuildsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the testray case and the testray build. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayBuildPK the primary key of the testray build
	 */
	@Override
	public void addTestrayBuild(long pk, long testrayBuildPK) {
		TestrayCase testrayCase = fetchByPrimaryKey(pk);

		if (testrayCase == null) {
			testrayCaseToTestrayBuildTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, testrayBuildPK);
		}
		else {
			testrayCaseToTestrayBuildTableMapper.addTableMapping(
				testrayCase.getCompanyId(), pk, testrayBuildPK);
		}
	}

	/**
	 * Adds an association between the testray case and the testray build. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayBuild the testray build
	 */
	@Override
	public void addTestrayBuild(
		long pk, com.liferay.osb.testray.model.TestrayBuild testrayBuild) {

		TestrayCase testrayCase = fetchByPrimaryKey(pk);

		if (testrayCase == null) {
			testrayCaseToTestrayBuildTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				testrayBuild.getPrimaryKey());
		}
		else {
			testrayCaseToTestrayBuildTableMapper.addTableMapping(
				testrayCase.getCompanyId(), pk, testrayBuild.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the testray case and the testray builds. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayBuildPKs the primary keys of the testray builds
	 */
	@Override
	public void addTestrayBuilds(long pk, long[] testrayBuildPKs) {
		long companyId = 0;

		TestrayCase testrayCase = fetchByPrimaryKey(pk);

		if (testrayCase == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testrayCase.getCompanyId();
		}

		testrayCaseToTestrayBuildTableMapper.addTableMappings(
			companyId, pk, testrayBuildPKs);
	}

	/**
	 * Adds an association between the testray case and the testray builds. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayBuilds the testray builds
	 */
	@Override
	public void addTestrayBuilds(
		long pk,
		List<com.liferay.osb.testray.model.TestrayBuild> testrayBuilds) {

		addTestrayBuilds(
			pk,
			ListUtil.toLongArray(
				testrayBuilds,
				com.liferay.osb.testray.model.TestrayBuild.
					TESTRAY_BUILD_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the testray case and its testray builds. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case to clear the associated testray builds from
	 */
	@Override
	public void clearTestrayBuilds(long pk) {
		testrayCaseToTestrayBuildTableMapper.deleteLeftPrimaryKeyTableMappings(
			pk);
	}

	/**
	 * Removes the association between the testray case and the testray build. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayBuildPK the primary key of the testray build
	 */
	@Override
	public void removeTestrayBuild(long pk, long testrayBuildPK) {
		testrayCaseToTestrayBuildTableMapper.deleteTableMapping(
			pk, testrayBuildPK);
	}

	/**
	 * Removes the association between the testray case and the testray build. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayBuild the testray build
	 */
	@Override
	public void removeTestrayBuild(
		long pk, com.liferay.osb.testray.model.TestrayBuild testrayBuild) {

		testrayCaseToTestrayBuildTableMapper.deleteTableMapping(
			pk, testrayBuild.getPrimaryKey());
	}

	/**
	 * Removes the association between the testray case and the testray builds. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayBuildPKs the primary keys of the testray builds
	 */
	@Override
	public void removeTestrayBuilds(long pk, long[] testrayBuildPKs) {
		testrayCaseToTestrayBuildTableMapper.deleteTableMappings(
			pk, testrayBuildPKs);
	}

	/**
	 * Removes the association between the testray case and the testray builds. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayBuilds the testray builds
	 */
	@Override
	public void removeTestrayBuilds(
		long pk,
		List<com.liferay.osb.testray.model.TestrayBuild> testrayBuilds) {

		removeTestrayBuilds(
			pk,
			ListUtil.toLongArray(
				testrayBuilds,
				com.liferay.osb.testray.model.TestrayBuild.
					TESTRAY_BUILD_ID_ACCESSOR));
	}

	/**
	 * Sets the testray builds associated with the testray case, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayBuildPKs the primary keys of the testray builds to be associated with the testray case
	 */
	@Override
	public void setTestrayBuilds(long pk, long[] testrayBuildPKs) {
		Set<Long> newTestrayBuildPKsSet = SetUtil.fromArray(testrayBuildPKs);
		Set<Long> oldTestrayBuildPKsSet = SetUtil.fromArray(
			testrayCaseToTestrayBuildTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeTestrayBuildPKsSet = new HashSet<Long>(
			oldTestrayBuildPKsSet);

		removeTestrayBuildPKsSet.removeAll(newTestrayBuildPKsSet);

		testrayCaseToTestrayBuildTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeTestrayBuildPKsSet));

		newTestrayBuildPKsSet.removeAll(oldTestrayBuildPKsSet);

		long companyId = 0;

		TestrayCase testrayCase = fetchByPrimaryKey(pk);

		if (testrayCase == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testrayCase.getCompanyId();
		}

		testrayCaseToTestrayBuildTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newTestrayBuildPKsSet));
	}

	/**
	 * Sets the testray builds associated with the testray case, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayBuilds the testray builds to be associated with the testray case
	 */
	@Override
	public void setTestrayBuilds(
		long pk,
		List<com.liferay.osb.testray.model.TestrayBuild> testrayBuilds) {

		try {
			long[] testrayBuildPKs = new long[testrayBuilds.size()];

			for (int i = 0; i < testrayBuilds.size(); i++) {
				com.liferay.osb.testray.model.TestrayBuild testrayBuild =
					testrayBuilds.get(i);

				testrayBuildPKs[i] = testrayBuild.getPrimaryKey();
			}

			setTestrayBuilds(pk, testrayBuildPKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	/**
	 * Returns the primaryKeys of testray components associated with the testray case.
	 *
	 * @param pk the primary key of the testray case
	 * @return long[] of the primaryKeys of testray components associated with the testray case
	 */
	@Override
	public long[] getTestrayComponentPrimaryKeys(long pk) {
		long[] pks =
			testrayCaseToTestrayComponentTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the testray components associated with the testray case.
	 *
	 * @param pk the primary key of the testray case
	 * @return the testray components associated with the testray case
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayComponent>
		getTestrayComponents(long pk) {

		return getTestrayComponents(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the testray components associated with the testray case.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray case
	 * @param start the lower bound of the range of testray cases
	 * @param end the upper bound of the range of testray cases (not inclusive)
	 * @return the range of testray components associated with the testray case
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayComponent>
		getTestrayComponents(long pk, int start, int end) {

		return getTestrayComponents(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray components associated with the testray case.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray case
	 * @param start the lower bound of the range of testray cases
	 * @param end the upper bound of the range of testray cases (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray components associated with the testray case
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayComponent>
		getTestrayComponents(
			long pk, int start, int end,
			OrderByComparator<com.liferay.osb.testray.model.TestrayComponent>
				orderByComparator) {

		return testrayCaseToTestrayComponentTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of testray components associated with the testray case.
	 *
	 * @param pk the primary key of the testray case
	 * @return the number of testray components associated with the testray case
	 */
	@Override
	public int getTestrayComponentsSize(long pk) {
		long[] pks =
			testrayCaseToTestrayComponentTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the testray component is associated with the testray case.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayComponentPK the primary key of the testray component
	 * @return <code>true</code> if the testray component is associated with the testray case; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTestrayComponent(long pk, long testrayComponentPK) {
		return testrayCaseToTestrayComponentTableMapper.containsTableMapping(
			pk, testrayComponentPK);
	}

	/**
	 * Returns <code>true</code> if the testray case has any testray components associated with it.
	 *
	 * @param pk the primary key of the testray case to check for associations with testray components
	 * @return <code>true</code> if the testray case has any testray components associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTestrayComponents(long pk) {
		if (getTestrayComponentsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the testray case and the testray component. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayComponentPK the primary key of the testray component
	 */
	@Override
	public void addTestrayComponent(long pk, long testrayComponentPK) {
		TestrayCase testrayCase = fetchByPrimaryKey(pk);

		if (testrayCase == null) {
			testrayCaseToTestrayComponentTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, testrayComponentPK);
		}
		else {
			testrayCaseToTestrayComponentTableMapper.addTableMapping(
				testrayCase.getCompanyId(), pk, testrayComponentPK);
		}
	}

	/**
	 * Adds an association between the testray case and the testray component. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayComponent the testray component
	 */
	@Override
	public void addTestrayComponent(
		long pk,
		com.liferay.osb.testray.model.TestrayComponent testrayComponent) {

		TestrayCase testrayCase = fetchByPrimaryKey(pk);

		if (testrayCase == null) {
			testrayCaseToTestrayComponentTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				testrayComponent.getPrimaryKey());
		}
		else {
			testrayCaseToTestrayComponentTableMapper.addTableMapping(
				testrayCase.getCompanyId(), pk,
				testrayComponent.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the testray case and the testray components. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayComponentPKs the primary keys of the testray components
	 */
	@Override
	public void addTestrayComponents(long pk, long[] testrayComponentPKs) {
		long companyId = 0;

		TestrayCase testrayCase = fetchByPrimaryKey(pk);

		if (testrayCase == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testrayCase.getCompanyId();
		}

		testrayCaseToTestrayComponentTableMapper.addTableMappings(
			companyId, pk, testrayComponentPKs);
	}

	/**
	 * Adds an association between the testray case and the testray components. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayComponents the testray components
	 */
	@Override
	public void addTestrayComponents(
		long pk,
		List<com.liferay.osb.testray.model.TestrayComponent>
			testrayComponents) {

		addTestrayComponents(
			pk,
			ListUtil.toLongArray(
				testrayComponents,
				com.liferay.osb.testray.model.TestrayComponent.
					TESTRAY_COMPONENT_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the testray case and its testray components. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case to clear the associated testray components from
	 */
	@Override
	public void clearTestrayComponents(long pk) {
		testrayCaseToTestrayComponentTableMapper.
			deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the testray case and the testray component. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayComponentPK the primary key of the testray component
	 */
	@Override
	public void removeTestrayComponent(long pk, long testrayComponentPK) {
		testrayCaseToTestrayComponentTableMapper.deleteTableMapping(
			pk, testrayComponentPK);
	}

	/**
	 * Removes the association between the testray case and the testray component. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayComponent the testray component
	 */
	@Override
	public void removeTestrayComponent(
		long pk,
		com.liferay.osb.testray.model.TestrayComponent testrayComponent) {

		testrayCaseToTestrayComponentTableMapper.deleteTableMapping(
			pk, testrayComponent.getPrimaryKey());
	}

	/**
	 * Removes the association between the testray case and the testray components. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayComponentPKs the primary keys of the testray components
	 */
	@Override
	public void removeTestrayComponents(long pk, long[] testrayComponentPKs) {
		testrayCaseToTestrayComponentTableMapper.deleteTableMappings(
			pk, testrayComponentPKs);
	}

	/**
	 * Removes the association between the testray case and the testray components. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayComponents the testray components
	 */
	@Override
	public void removeTestrayComponents(
		long pk,
		List<com.liferay.osb.testray.model.TestrayComponent>
			testrayComponents) {

		removeTestrayComponents(
			pk,
			ListUtil.toLongArray(
				testrayComponents,
				com.liferay.osb.testray.model.TestrayComponent.
					TESTRAY_COMPONENT_ID_ACCESSOR));
	}

	/**
	 * Sets the testray components associated with the testray case, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayComponentPKs the primary keys of the testray components to be associated with the testray case
	 */
	@Override
	public void setTestrayComponents(long pk, long[] testrayComponentPKs) {
		Set<Long> newTestrayComponentPKsSet = SetUtil.fromArray(
			testrayComponentPKs);
		Set<Long> oldTestrayComponentPKsSet = SetUtil.fromArray(
			testrayCaseToTestrayComponentTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeTestrayComponentPKsSet = new HashSet<Long>(
			oldTestrayComponentPKsSet);

		removeTestrayComponentPKsSet.removeAll(newTestrayComponentPKsSet);

		testrayCaseToTestrayComponentTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeTestrayComponentPKsSet));

		newTestrayComponentPKsSet.removeAll(oldTestrayComponentPKsSet);

		long companyId = 0;

		TestrayCase testrayCase = fetchByPrimaryKey(pk);

		if (testrayCase == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testrayCase.getCompanyId();
		}

		testrayCaseToTestrayComponentTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newTestrayComponentPKsSet));
	}

	/**
	 * Sets the testray components associated with the testray case, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayComponents the testray components to be associated with the testray case
	 */
	@Override
	public void setTestrayComponents(
		long pk,
		List<com.liferay.osb.testray.model.TestrayComponent>
			testrayComponents) {

		try {
			long[] testrayComponentPKs = new long[testrayComponents.size()];

			for (int i = 0; i < testrayComponents.size(); i++) {
				com.liferay.osb.testray.model.TestrayComponent
					testrayComponent = testrayComponents.get(i);

				testrayComponentPKs[i] = testrayComponent.getPrimaryKey();
			}

			setTestrayComponents(pk, testrayComponentPKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	/**
	 * Returns the primaryKeys of testray requirements associated with the testray case.
	 *
	 * @param pk the primary key of the testray case
	 * @return long[] of the primaryKeys of testray requirements associated with the testray case
	 */
	@Override
	public long[] getTestrayRequirementPrimaryKeys(long pk) {
		long[] pks =
			testrayCaseToTestrayRequirementTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the testray requirements associated with the testray case.
	 *
	 * @param pk the primary key of the testray case
	 * @return the testray requirements associated with the testray case
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayRequirement>
		getTestrayRequirements(long pk) {

		return getTestrayRequirements(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the testray requirements associated with the testray case.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray case
	 * @param start the lower bound of the range of testray cases
	 * @param end the upper bound of the range of testray cases (not inclusive)
	 * @return the range of testray requirements associated with the testray case
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayRequirement>
		getTestrayRequirements(long pk, int start, int end) {

		return getTestrayRequirements(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray requirements associated with the testray case.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray case
	 * @param start the lower bound of the range of testray cases
	 * @param end the upper bound of the range of testray cases (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray requirements associated with the testray case
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayRequirement>
		getTestrayRequirements(
			long pk, int start, int end,
			OrderByComparator<com.liferay.osb.testray.model.TestrayRequirement>
				orderByComparator) {

		return testrayCaseToTestrayRequirementTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of testray requirements associated with the testray case.
	 *
	 * @param pk the primary key of the testray case
	 * @return the number of testray requirements associated with the testray case
	 */
	@Override
	public int getTestrayRequirementsSize(long pk) {
		long[] pks =
			testrayCaseToTestrayRequirementTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the testray requirement is associated with the testray case.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayRequirementPK the primary key of the testray requirement
	 * @return <code>true</code> if the testray requirement is associated with the testray case; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTestrayRequirement(
		long pk, long testrayRequirementPK) {

		return testrayCaseToTestrayRequirementTableMapper.containsTableMapping(
			pk, testrayRequirementPK);
	}

	/**
	 * Returns <code>true</code> if the testray case has any testray requirements associated with it.
	 *
	 * @param pk the primary key of the testray case to check for associations with testray requirements
	 * @return <code>true</code> if the testray case has any testray requirements associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTestrayRequirements(long pk) {
		if (getTestrayRequirementsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the testray case and the testray requirement. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayRequirementPK the primary key of the testray requirement
	 */
	@Override
	public void addTestrayRequirement(long pk, long testrayRequirementPK) {
		TestrayCase testrayCase = fetchByPrimaryKey(pk);

		if (testrayCase == null) {
			testrayCaseToTestrayRequirementTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, testrayRequirementPK);
		}
		else {
			testrayCaseToTestrayRequirementTableMapper.addTableMapping(
				testrayCase.getCompanyId(), pk, testrayRequirementPK);
		}
	}

	/**
	 * Adds an association between the testray case and the testray requirement. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayRequirement the testray requirement
	 */
	@Override
	public void addTestrayRequirement(
		long pk,
		com.liferay.osb.testray.model.TestrayRequirement testrayRequirement) {

		TestrayCase testrayCase = fetchByPrimaryKey(pk);

		if (testrayCase == null) {
			testrayCaseToTestrayRequirementTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				testrayRequirement.getPrimaryKey());
		}
		else {
			testrayCaseToTestrayRequirementTableMapper.addTableMapping(
				testrayCase.getCompanyId(), pk,
				testrayRequirement.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the testray case and the testray requirements. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayRequirementPKs the primary keys of the testray requirements
	 */
	@Override
	public void addTestrayRequirements(long pk, long[] testrayRequirementPKs) {
		long companyId = 0;

		TestrayCase testrayCase = fetchByPrimaryKey(pk);

		if (testrayCase == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testrayCase.getCompanyId();
		}

		testrayCaseToTestrayRequirementTableMapper.addTableMappings(
			companyId, pk, testrayRequirementPKs);
	}

	/**
	 * Adds an association between the testray case and the testray requirements. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayRequirements the testray requirements
	 */
	@Override
	public void addTestrayRequirements(
		long pk,
		List<com.liferay.osb.testray.model.TestrayRequirement>
			testrayRequirements) {

		addTestrayRequirements(
			pk,
			ListUtil.toLongArray(
				testrayRequirements,
				com.liferay.osb.testray.model.TestrayRequirement.
					TESTRAY_REQUIREMENT_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the testray case and its testray requirements. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case to clear the associated testray requirements from
	 */
	@Override
	public void clearTestrayRequirements(long pk) {
		testrayCaseToTestrayRequirementTableMapper.
			deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the testray case and the testray requirement. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayRequirementPK the primary key of the testray requirement
	 */
	@Override
	public void removeTestrayRequirement(long pk, long testrayRequirementPK) {
		testrayCaseToTestrayRequirementTableMapper.deleteTableMapping(
			pk, testrayRequirementPK);
	}

	/**
	 * Removes the association between the testray case and the testray requirement. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayRequirement the testray requirement
	 */
	@Override
	public void removeTestrayRequirement(
		long pk,
		com.liferay.osb.testray.model.TestrayRequirement testrayRequirement) {

		testrayCaseToTestrayRequirementTableMapper.deleteTableMapping(
			pk, testrayRequirement.getPrimaryKey());
	}

	/**
	 * Removes the association between the testray case and the testray requirements. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayRequirementPKs the primary keys of the testray requirements
	 */
	@Override
	public void removeTestrayRequirements(
		long pk, long[] testrayRequirementPKs) {

		testrayCaseToTestrayRequirementTableMapper.deleteTableMappings(
			pk, testrayRequirementPKs);
	}

	/**
	 * Removes the association between the testray case and the testray requirements. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayRequirements the testray requirements
	 */
	@Override
	public void removeTestrayRequirements(
		long pk,
		List<com.liferay.osb.testray.model.TestrayRequirement>
			testrayRequirements) {

		removeTestrayRequirements(
			pk,
			ListUtil.toLongArray(
				testrayRequirements,
				com.liferay.osb.testray.model.TestrayRequirement.
					TESTRAY_REQUIREMENT_ID_ACCESSOR));
	}

	/**
	 * Sets the testray requirements associated with the testray case, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayRequirementPKs the primary keys of the testray requirements to be associated with the testray case
	 */
	@Override
	public void setTestrayRequirements(long pk, long[] testrayRequirementPKs) {
		Set<Long> newTestrayRequirementPKsSet = SetUtil.fromArray(
			testrayRequirementPKs);
		Set<Long> oldTestrayRequirementPKsSet = SetUtil.fromArray(
			testrayCaseToTestrayRequirementTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeTestrayRequirementPKsSet = new HashSet<Long>(
			oldTestrayRequirementPKsSet);

		removeTestrayRequirementPKsSet.removeAll(newTestrayRequirementPKsSet);

		testrayCaseToTestrayRequirementTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeTestrayRequirementPKsSet));

		newTestrayRequirementPKsSet.removeAll(oldTestrayRequirementPKsSet);

		long companyId = 0;

		TestrayCase testrayCase = fetchByPrimaryKey(pk);

		if (testrayCase == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testrayCase.getCompanyId();
		}

		testrayCaseToTestrayRequirementTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newTestrayRequirementPKsSet));
	}

	/**
	 * Sets the testray requirements associated with the testray case, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testrayRequirements the testray requirements to be associated with the testray case
	 */
	@Override
	public void setTestrayRequirements(
		long pk,
		List<com.liferay.osb.testray.model.TestrayRequirement>
			testrayRequirements) {

		try {
			long[] testrayRequirementPKs = new long[testrayRequirements.size()];

			for (int i = 0; i < testrayRequirements.size(); i++) {
				com.liferay.osb.testray.model.TestrayRequirement
					testrayRequirement = testrayRequirements.get(i);

				testrayRequirementPKs[i] = testrayRequirement.getPrimaryKey();
			}

			setTestrayRequirements(pk, testrayRequirementPKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	/**
	 * Returns the primaryKeys of testray suites associated with the testray case.
	 *
	 * @param pk the primary key of the testray case
	 * @return long[] of the primaryKeys of testray suites associated with the testray case
	 */
	@Override
	public long[] getTestraySuitePrimaryKeys(long pk) {
		long[] pks = testrayCaseToTestraySuiteTableMapper.getRightPrimaryKeys(
			pk);

		return pks.clone();
	}

	/**
	 * Returns all the testray suites associated with the testray case.
	 *
	 * @param pk the primary key of the testray case
	 * @return the testray suites associated with the testray case
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestraySuite> getTestraySuites(
		long pk) {

		return getTestraySuites(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the testray suites associated with the testray case.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray case
	 * @param start the lower bound of the range of testray cases
	 * @param end the upper bound of the range of testray cases (not inclusive)
	 * @return the range of testray suites associated with the testray case
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestraySuite> getTestraySuites(
		long pk, int start, int end) {

		return getTestraySuites(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray suites associated with the testray case.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray case
	 * @param start the lower bound of the range of testray cases
	 * @param end the upper bound of the range of testray cases (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray suites associated with the testray case
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestraySuite> getTestraySuites(
		long pk, int start, int end,
		OrderByComparator<com.liferay.osb.testray.model.TestraySuite>
			orderByComparator) {

		return testrayCaseToTestraySuiteTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of testray suites associated with the testray case.
	 *
	 * @param pk the primary key of the testray case
	 * @return the number of testray suites associated with the testray case
	 */
	@Override
	public int getTestraySuitesSize(long pk) {
		long[] pks = testrayCaseToTestraySuiteTableMapper.getRightPrimaryKeys(
			pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the testray suite is associated with the testray case.
	 *
	 * @param pk the primary key of the testray case
	 * @param testraySuitePK the primary key of the testray suite
	 * @return <code>true</code> if the testray suite is associated with the testray case; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTestraySuite(long pk, long testraySuitePK) {
		return testrayCaseToTestraySuiteTableMapper.containsTableMapping(
			pk, testraySuitePK);
	}

	/**
	 * Returns <code>true</code> if the testray case has any testray suites associated with it.
	 *
	 * @param pk the primary key of the testray case to check for associations with testray suites
	 * @return <code>true</code> if the testray case has any testray suites associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTestraySuites(long pk) {
		if (getTestraySuitesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the testray case and the testray suite. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testraySuitePK the primary key of the testray suite
	 */
	@Override
	public void addTestraySuite(long pk, long testraySuitePK) {
		TestrayCase testrayCase = fetchByPrimaryKey(pk);

		if (testrayCase == null) {
			testrayCaseToTestraySuiteTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, testraySuitePK);
		}
		else {
			testrayCaseToTestraySuiteTableMapper.addTableMapping(
				testrayCase.getCompanyId(), pk, testraySuitePK);
		}
	}

	/**
	 * Adds an association between the testray case and the testray suite. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testraySuite the testray suite
	 */
	@Override
	public void addTestraySuite(
		long pk, com.liferay.osb.testray.model.TestraySuite testraySuite) {

		TestrayCase testrayCase = fetchByPrimaryKey(pk);

		if (testrayCase == null) {
			testrayCaseToTestraySuiteTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				testraySuite.getPrimaryKey());
		}
		else {
			testrayCaseToTestraySuiteTableMapper.addTableMapping(
				testrayCase.getCompanyId(), pk, testraySuite.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the testray case and the testray suites. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testraySuitePKs the primary keys of the testray suites
	 */
	@Override
	public void addTestraySuites(long pk, long[] testraySuitePKs) {
		long companyId = 0;

		TestrayCase testrayCase = fetchByPrimaryKey(pk);

		if (testrayCase == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testrayCase.getCompanyId();
		}

		testrayCaseToTestraySuiteTableMapper.addTableMappings(
			companyId, pk, testraySuitePKs);
	}

	/**
	 * Adds an association between the testray case and the testray suites. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testraySuites the testray suites
	 */
	@Override
	public void addTestraySuites(
		long pk,
		List<com.liferay.osb.testray.model.TestraySuite> testraySuites) {

		addTestraySuites(
			pk,
			ListUtil.toLongArray(
				testraySuites,
				com.liferay.osb.testray.model.TestraySuite.
					TESTRAY_SUITE_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the testray case and its testray suites. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case to clear the associated testray suites from
	 */
	@Override
	public void clearTestraySuites(long pk) {
		testrayCaseToTestraySuiteTableMapper.deleteLeftPrimaryKeyTableMappings(
			pk);
	}

	/**
	 * Removes the association between the testray case and the testray suite. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testraySuitePK the primary key of the testray suite
	 */
	@Override
	public void removeTestraySuite(long pk, long testraySuitePK) {
		testrayCaseToTestraySuiteTableMapper.deleteTableMapping(
			pk, testraySuitePK);
	}

	/**
	 * Removes the association between the testray case and the testray suite. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testraySuite the testray suite
	 */
	@Override
	public void removeTestraySuite(
		long pk, com.liferay.osb.testray.model.TestraySuite testraySuite) {

		testrayCaseToTestraySuiteTableMapper.deleteTableMapping(
			pk, testraySuite.getPrimaryKey());
	}

	/**
	 * Removes the association between the testray case and the testray suites. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testraySuitePKs the primary keys of the testray suites
	 */
	@Override
	public void removeTestraySuites(long pk, long[] testraySuitePKs) {
		testrayCaseToTestraySuiteTableMapper.deleteTableMappings(
			pk, testraySuitePKs);
	}

	/**
	 * Removes the association between the testray case and the testray suites. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testraySuites the testray suites
	 */
	@Override
	public void removeTestraySuites(
		long pk,
		List<com.liferay.osb.testray.model.TestraySuite> testraySuites) {

		removeTestraySuites(
			pk,
			ListUtil.toLongArray(
				testraySuites,
				com.liferay.osb.testray.model.TestraySuite.
					TESTRAY_SUITE_ID_ACCESSOR));
	}

	/**
	 * Sets the testray suites associated with the testray case, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testraySuitePKs the primary keys of the testray suites to be associated with the testray case
	 */
	@Override
	public void setTestraySuites(long pk, long[] testraySuitePKs) {
		Set<Long> newTestraySuitePKsSet = SetUtil.fromArray(testraySuitePKs);
		Set<Long> oldTestraySuitePKsSet = SetUtil.fromArray(
			testrayCaseToTestraySuiteTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeTestraySuitePKsSet = new HashSet<Long>(
			oldTestraySuitePKsSet);

		removeTestraySuitePKsSet.removeAll(newTestraySuitePKsSet);

		testrayCaseToTestraySuiteTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeTestraySuitePKsSet));

		newTestraySuitePKsSet.removeAll(oldTestraySuitePKsSet);

		long companyId = 0;

		TestrayCase testrayCase = fetchByPrimaryKey(pk);

		if (testrayCase == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testrayCase.getCompanyId();
		}

		testrayCaseToTestraySuiteTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newTestraySuitePKsSet));
	}

	/**
	 * Sets the testray suites associated with the testray case, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case
	 * @param testraySuites the testray suites to be associated with the testray case
	 */
	@Override
	public void setTestraySuites(
		long pk,
		List<com.liferay.osb.testray.model.TestraySuite> testraySuites) {

		try {
			long[] testraySuitePKs = new long[testraySuites.size()];

			for (int i = 0; i < testraySuites.size(); i++) {
				com.liferay.osb.testray.model.TestraySuite testraySuite =
					testraySuites.get(i);

				testraySuitePKs[i] = testraySuite.getPrimaryKey();
			}

			setTestraySuites(pk, testraySuitePKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return TestrayCaseModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the testray case persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		testrayCaseToTestrayBuildTableMapper =
			TableMapperFactory.getTableMapper(
				"OSB_TestrayBuilds_TestrayCases", "companyId", "testrayCaseId",
				"testrayBuildId", this, testrayBuildPersistence);

		testrayCaseToTestrayComponentTableMapper =
			TableMapperFactory.getTableMapper(
				"OSB_TestrayCases_TestrayComponents", "companyId",
				"testrayCaseId", "testrayComponentId", this,
				testrayComponentPersistence);

		testrayCaseToTestrayRequirementTableMapper =
			TableMapperFactory.getTableMapper(
				"OSB_TestrayRequirements_TestrayCases", "companyId",
				"testrayCaseId", "testrayRequirementId", this,
				testrayRequirementPersistence);

		testrayCaseToTestraySuiteTableMapper =
			TableMapperFactory.getTableMapper(
				"OSB_TestraySuites_TestrayCases", "companyId", "testrayCaseId",
				"testraySuiteId", this, testraySuitePersistence);

		_finderPathWithPaginationFindAll = new FinderPath(
			TestrayCaseModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseModelImpl.FINDER_CACHE_ENABLED, TestrayCaseImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			TestrayCaseModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseModelImpl.FINDER_CACHE_ENABLED, TestrayCaseImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			TestrayCaseModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByTPI_N = new FinderPath(
			TestrayCaseModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseModelImpl.FINDER_CACHE_ENABLED, TestrayCaseImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByTPI_N",
			new String[] {Long.class.getName(), String.class.getName()},
			TestrayCaseModelImpl.TESTRAYPROJECTID_COLUMN_BITMASK |
			TestrayCaseModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByTPI_N = new FinderPath(
			TestrayCaseModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTPI_N",
			new String[] {Long.class.getName(), String.class.getName()});

		TestrayCaseUtil.setPersistence(this);
	}

	public void destroy() {
		TestrayCaseUtil.setPersistence(null);

		entityCache.removeCache(TestrayCaseImpl.class.getName());

		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper("OSB_TestrayBuilds_TestrayCases");
		TableMapperFactory.removeTableMapper(
			"OSB_TestrayCases_TestrayComponents");
		TableMapperFactory.removeTableMapper(
			"OSB_TestrayRequirements_TestrayCases");
		TableMapperFactory.removeTableMapper("OSB_TestraySuites_TestrayCases");
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

	@BeanReference(type = TestrayBuildPersistence.class)
	protected TestrayBuildPersistence testrayBuildPersistence;

	protected TableMapper
		<TestrayCase, com.liferay.osb.testray.model.TestrayBuild>
			testrayCaseToTestrayBuildTableMapper;

	@BeanReference(type = TestrayComponentPersistence.class)
	protected TestrayComponentPersistence testrayComponentPersistence;

	protected TableMapper
		<TestrayCase, com.liferay.osb.testray.model.TestrayComponent>
			testrayCaseToTestrayComponentTableMapper;

	@BeanReference(type = TestrayRequirementPersistence.class)
	protected TestrayRequirementPersistence testrayRequirementPersistence;

	protected TableMapper
		<TestrayCase, com.liferay.osb.testray.model.TestrayRequirement>
			testrayCaseToTestrayRequirementTableMapper;

	@BeanReference(type = TestraySuitePersistence.class)
	protected TestraySuitePersistence testraySuitePersistence;

	protected TableMapper
		<TestrayCase, com.liferay.osb.testray.model.TestraySuite>
			testrayCaseToTestraySuiteTableMapper;

	private static final String _SQL_SELECT_TESTRAYCASE =
		"SELECT testrayCase FROM TestrayCase testrayCase";

	private static final String _SQL_SELECT_TESTRAYCASE_WHERE_PKS_IN =
		"SELECT testrayCase FROM TestrayCase testrayCase WHERE testrayCaseId IN (";

	private static final String _SQL_SELECT_TESTRAYCASE_WHERE =
		"SELECT testrayCase FROM TestrayCase testrayCase WHERE ";

	private static final String _SQL_COUNT_TESTRAYCASE =
		"SELECT COUNT(testrayCase) FROM TestrayCase testrayCase";

	private static final String _SQL_COUNT_TESTRAYCASE_WHERE =
		"SELECT COUNT(testrayCase) FROM TestrayCase testrayCase WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "testrayCase.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No TestrayCase exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No TestrayCase exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		TestrayCasePersistenceImpl.class);

}