/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.testray.service.persistence.impl;

import com.liferay.osb.testray.exception.NoSuchTestrayProductVersionException;
import com.liferay.osb.testray.model.TestrayProductVersion;
import com.liferay.osb.testray.model.impl.TestrayProductVersionImpl;
import com.liferay.osb.testray.model.impl.TestrayProductVersionModelImpl;
import com.liferay.osb.testray.service.persistence.TestrayProductVersionPersistence;
import com.liferay.osb.testray.service.persistence.TestrayProductVersionUtil;
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
 * The persistence implementation for the testray product version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ethan Bustad
 * @generated
 */
public class TestrayProductVersionPersistenceImpl
	extends BasePersistenceImpl<TestrayProductVersion>
	implements TestrayProductVersionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>TestrayProductVersionUtil</code> to access the testray product version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		TestrayProductVersionImpl.class.getName();

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
	 * Returns the testray product version where testrayProjectId = &#63; and name = &#63; or throws a <code>NoSuchTestrayProductVersionException</code> if it could not be found.
	 *
	 * @param testrayProjectId the testray project ID
	 * @param name the name
	 * @return the matching testray product version
	 * @throws NoSuchTestrayProductVersionException if a matching testray product version could not be found
	 */
	@Override
	public TestrayProductVersion findByT_N(long testrayProjectId, String name)
		throws NoSuchTestrayProductVersionException {

		TestrayProductVersion testrayProductVersion = fetchByT_N(
			testrayProjectId, name);

		if (testrayProductVersion == null) {
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

			throw new NoSuchTestrayProductVersionException(sb.toString());
		}

		return testrayProductVersion;
	}

	/**
	 * Returns the testray product version where testrayProjectId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param testrayProjectId the testray project ID
	 * @param name the name
	 * @return the matching testray product version, or <code>null</code> if a matching testray product version could not be found
	 */
	@Override
	public TestrayProductVersion fetchByT_N(
		long testrayProjectId, String name) {

		return fetchByT_N(testrayProjectId, name, true);
	}

	/**
	 * Returns the testray product version where testrayProjectId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param testrayProjectId the testray project ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching testray product version, or <code>null</code> if a matching testray product version could not be found
	 */
	@Override
	public TestrayProductVersion fetchByT_N(
		long testrayProjectId, String name, boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {testrayProjectId, name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByT_N, finderArgs, this);
		}

		if (result instanceof TestrayProductVersion) {
			TestrayProductVersion testrayProductVersion =
				(TestrayProductVersion)result;

			if ((testrayProjectId !=
					testrayProductVersion.getTestrayProjectId()) ||
				!Objects.equals(name, testrayProductVersion.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_TESTRAYPRODUCTVERSION_WHERE);

			sb.append(_FINDER_COLUMN_T_N_TESTRAYPROJECTID_2);

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

				queryPos.add(testrayProjectId);

				if (bindName) {
					queryPos.add(name);
				}

				List<TestrayProductVersion> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByT_N, finderArgs, list);
					}
				}
				else {
					TestrayProductVersion testrayProductVersion = list.get(0);

					result = testrayProductVersion;

					cacheResult(testrayProductVersion);
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
			return (TestrayProductVersion)result;
		}
	}

	/**
	 * Removes the testray product version where testrayProjectId = &#63; and name = &#63; from the database.
	 *
	 * @param testrayProjectId the testray project ID
	 * @param name the name
	 * @return the testray product version that was removed
	 */
	@Override
	public TestrayProductVersion removeByT_N(long testrayProjectId, String name)
		throws NoSuchTestrayProductVersionException {

		TestrayProductVersion testrayProductVersion = findByT_N(
			testrayProjectId, name);

		return remove(testrayProductVersion);
	}

	/**
	 * Returns the number of testray product versions where testrayProjectId = &#63; and name = &#63;.
	 *
	 * @param testrayProjectId the testray project ID
	 * @param name the name
	 * @return the number of matching testray product versions
	 */
	@Override
	public int countByT_N(long testrayProjectId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByT_N;

		Object[] finderArgs = new Object[] {testrayProjectId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_TESTRAYPRODUCTVERSION_WHERE);

			sb.append(_FINDER_COLUMN_T_N_TESTRAYPROJECTID_2);

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

	private static final String _FINDER_COLUMN_T_N_TESTRAYPROJECTID_2 =
		"testrayProductVersion.testrayProjectId = ? AND ";

	private static final String _FINDER_COLUMN_T_N_NAME_2 =
		"testrayProductVersion.name = ?";

	private static final String _FINDER_COLUMN_T_N_NAME_3 =
		"(testrayProductVersion.name IS NULL OR testrayProductVersion.name = '')";

	public TestrayProductVersionPersistenceImpl() {
		setModelClass(TestrayProductVersion.class);
	}

	/**
	 * Caches the testray product version in the entity cache if it is enabled.
	 *
	 * @param testrayProductVersion the testray product version
	 */
	@Override
	public void cacheResult(TestrayProductVersion testrayProductVersion) {
		entityCache.putResult(
			TestrayProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			TestrayProductVersionImpl.class,
			testrayProductVersion.getPrimaryKey(), testrayProductVersion);

		finderCache.putResult(
			_finderPathFetchByT_N,
			new Object[] {
				testrayProductVersion.getTestrayProjectId(),
				testrayProductVersion.getName()
			},
			testrayProductVersion);

		testrayProductVersion.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the testray product versions in the entity cache if it is enabled.
	 *
	 * @param testrayProductVersions the testray product versions
	 */
	@Override
	public void cacheResult(
		List<TestrayProductVersion> testrayProductVersions) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (testrayProductVersions.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (TestrayProductVersion testrayProductVersion :
				testrayProductVersions) {

			if (entityCache.getResult(
					TestrayProductVersionModelImpl.ENTITY_CACHE_ENABLED,
					TestrayProductVersionImpl.class,
					testrayProductVersion.getPrimaryKey()) == null) {

				cacheResult(testrayProductVersion);
			}
			else {
				testrayProductVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all testray product versions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(TestrayProductVersionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the testray product version.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(TestrayProductVersion testrayProductVersion) {
		entityCache.removeResult(
			TestrayProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			TestrayProductVersionImpl.class,
			testrayProductVersion.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(TestrayProductVersionModelImpl)testrayProductVersion, true);
	}

	@Override
	public void clearCache(List<TestrayProductVersion> testrayProductVersions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (TestrayProductVersion testrayProductVersion :
				testrayProductVersions) {

			entityCache.removeResult(
				TestrayProductVersionModelImpl.ENTITY_CACHE_ENABLED,
				TestrayProductVersionImpl.class,
				testrayProductVersion.getPrimaryKey());

			clearUniqueFindersCache(
				(TestrayProductVersionModelImpl)testrayProductVersion, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				TestrayProductVersionModelImpl.ENTITY_CACHE_ENABLED,
				TestrayProductVersionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		TestrayProductVersionModelImpl testrayProductVersionModelImpl) {

		Object[] args = new Object[] {
			testrayProductVersionModelImpl.getTestrayProjectId(),
			testrayProductVersionModelImpl.getName()
		};

		finderCache.putResult(
			_finderPathCountByT_N, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByT_N, args, testrayProductVersionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		TestrayProductVersionModelImpl testrayProductVersionModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				testrayProductVersionModelImpl.getTestrayProjectId(),
				testrayProductVersionModelImpl.getName()
			};

			finderCache.removeResult(_finderPathCountByT_N, args);
			finderCache.removeResult(_finderPathFetchByT_N, args);
		}

		if ((testrayProductVersionModelImpl.getColumnBitmask() &
			 _finderPathFetchByT_N.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				testrayProductVersionModelImpl.getOriginalTestrayProjectId(),
				testrayProductVersionModelImpl.getOriginalName()
			};

			finderCache.removeResult(_finderPathCountByT_N, args);
			finderCache.removeResult(_finderPathFetchByT_N, args);
		}
	}

	/**
	 * Creates a new testray product version with the primary key. Does not add the testray product version to the database.
	 *
	 * @param testrayProductVersionId the primary key for the new testray product version
	 * @return the new testray product version
	 */
	@Override
	public TestrayProductVersion create(long testrayProductVersionId) {
		TestrayProductVersion testrayProductVersion =
			new TestrayProductVersionImpl();

		testrayProductVersion.setNew(true);
		testrayProductVersion.setPrimaryKey(testrayProductVersionId);

		testrayProductVersion.setCompanyId(CompanyThreadLocal.getCompanyId());

		return testrayProductVersion;
	}

	/**
	 * Removes the testray product version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param testrayProductVersionId the primary key of the testray product version
	 * @return the testray product version that was removed
	 * @throws NoSuchTestrayProductVersionException if a testray product version with the primary key could not be found
	 */
	@Override
	public TestrayProductVersion remove(long testrayProductVersionId)
		throws NoSuchTestrayProductVersionException {

		return remove((Serializable)testrayProductVersionId);
	}

	/**
	 * Removes the testray product version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the testray product version
	 * @return the testray product version that was removed
	 * @throws NoSuchTestrayProductVersionException if a testray product version with the primary key could not be found
	 */
	@Override
	public TestrayProductVersion remove(Serializable primaryKey)
		throws NoSuchTestrayProductVersionException {

		Session session = null;

		try {
			session = openSession();

			TestrayProductVersion testrayProductVersion =
				(TestrayProductVersion)session.get(
					TestrayProductVersionImpl.class, primaryKey);

			if (testrayProductVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTestrayProductVersionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(testrayProductVersion);
		}
		catch (NoSuchTestrayProductVersionException noSuchEntityException) {
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
	protected TestrayProductVersion removeImpl(
		TestrayProductVersion testrayProductVersion) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(testrayProductVersion)) {
				testrayProductVersion = (TestrayProductVersion)session.get(
					TestrayProductVersionImpl.class,
					testrayProductVersion.getPrimaryKeyObj());
			}

			if (testrayProductVersion != null) {
				session.delete(testrayProductVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (testrayProductVersion != null) {
			clearCache(testrayProductVersion);
		}

		return testrayProductVersion;
	}

	@Override
	public TestrayProductVersion updateImpl(
		TestrayProductVersion testrayProductVersion) {

		boolean isNew = testrayProductVersion.isNew();

		if (!(testrayProductVersion instanceof
				TestrayProductVersionModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(testrayProductVersion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					testrayProductVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in testrayProductVersion proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom TestrayProductVersion implementation " +
					testrayProductVersion.getClass());
		}

		TestrayProductVersionModelImpl testrayProductVersionModelImpl =
			(TestrayProductVersionModelImpl)testrayProductVersion;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (testrayProductVersion.getCreateDate() == null)) {
			if (serviceContext == null) {
				testrayProductVersion.setCreateDate(date);
			}
			else {
				testrayProductVersion.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!testrayProductVersionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				testrayProductVersion.setModifiedDate(date);
			}
			else {
				testrayProductVersion.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(testrayProductVersion);

				testrayProductVersion.setNew(false);
			}
			else {
				testrayProductVersion = (TestrayProductVersion)session.merge(
					testrayProductVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!TestrayProductVersionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			TestrayProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			TestrayProductVersionImpl.class,
			testrayProductVersion.getPrimaryKey(), testrayProductVersion,
			false);

		clearUniqueFindersCache(testrayProductVersionModelImpl, false);
		cacheUniqueFindersCache(testrayProductVersionModelImpl);

		testrayProductVersion.resetOriginalValues();

		return testrayProductVersion;
	}

	/**
	 * Returns the testray product version with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray product version
	 * @return the testray product version
	 * @throws NoSuchTestrayProductVersionException if a testray product version with the primary key could not be found
	 */
	@Override
	public TestrayProductVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTestrayProductVersionException {

		TestrayProductVersion testrayProductVersion = fetchByPrimaryKey(
			primaryKey);

		if (testrayProductVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTestrayProductVersionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return testrayProductVersion;
	}

	/**
	 * Returns the testray product version with the primary key or throws a <code>NoSuchTestrayProductVersionException</code> if it could not be found.
	 *
	 * @param testrayProductVersionId the primary key of the testray product version
	 * @return the testray product version
	 * @throws NoSuchTestrayProductVersionException if a testray product version with the primary key could not be found
	 */
	@Override
	public TestrayProductVersion findByPrimaryKey(long testrayProductVersionId)
		throws NoSuchTestrayProductVersionException {

		return findByPrimaryKey((Serializable)testrayProductVersionId);
	}

	/**
	 * Returns the testray product version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray product version
	 * @return the testray product version, or <code>null</code> if a testray product version with the primary key could not be found
	 */
	@Override
	public TestrayProductVersion fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			TestrayProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			TestrayProductVersionImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		TestrayProductVersion testrayProductVersion =
			(TestrayProductVersion)serializable;

		if (testrayProductVersion == null) {
			Session session = null;

			try {
				session = openSession();

				testrayProductVersion = (TestrayProductVersion)session.get(
					TestrayProductVersionImpl.class, primaryKey);

				if (testrayProductVersion != null) {
					cacheResult(testrayProductVersion);
				}
				else {
					entityCache.putResult(
						TestrayProductVersionModelImpl.ENTITY_CACHE_ENABLED,
						TestrayProductVersionImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					TestrayProductVersionModelImpl.ENTITY_CACHE_ENABLED,
					TestrayProductVersionImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return testrayProductVersion;
	}

	/**
	 * Returns the testray product version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param testrayProductVersionId the primary key of the testray product version
	 * @return the testray product version, or <code>null</code> if a testray product version with the primary key could not be found
	 */
	@Override
	public TestrayProductVersion fetchByPrimaryKey(
		long testrayProductVersionId) {

		return fetchByPrimaryKey((Serializable)testrayProductVersionId);
	}

	@Override
	public Map<Serializable, TestrayProductVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, TestrayProductVersion> map =
			new HashMap<Serializable, TestrayProductVersion>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			TestrayProductVersion testrayProductVersion = fetchByPrimaryKey(
				primaryKey);

			if (testrayProductVersion != null) {
				map.put(primaryKey, testrayProductVersion);
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
				TestrayProductVersionModelImpl.ENTITY_CACHE_ENABLED,
				TestrayProductVersionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (TestrayProductVersion)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_TESTRAYPRODUCTVERSION_WHERE_PKS_IN);

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

			for (TestrayProductVersion testrayProductVersion :
					(List<TestrayProductVersion>)query.list()) {

				map.put(
					testrayProductVersion.getPrimaryKeyObj(),
					testrayProductVersion);

				cacheResult(testrayProductVersion);

				uncachedPrimaryKeys.remove(
					testrayProductVersion.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					TestrayProductVersionModelImpl.ENTITY_CACHE_ENABLED,
					TestrayProductVersionImpl.class, primaryKey, nullModel);
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
	 * Returns all the testray product versions.
	 *
	 * @return the testray product versions
	 */
	@Override
	public List<TestrayProductVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the testray product versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayProductVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray product versions
	 * @param end the upper bound of the range of testray product versions (not inclusive)
	 * @return the range of testray product versions
	 */
	@Override
	public List<TestrayProductVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray product versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayProductVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray product versions
	 * @param end the upper bound of the range of testray product versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray product versions
	 */
	@Override
	public List<TestrayProductVersion> findAll(
		int start, int end,
		OrderByComparator<TestrayProductVersion> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the testray product versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayProductVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray product versions
	 * @param end the upper bound of the range of testray product versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of testray product versions
	 */
	@Override
	public List<TestrayProductVersion> findAll(
		int start, int end,
		OrderByComparator<TestrayProductVersion> orderByComparator,
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

		List<TestrayProductVersion> list = null;

		if (useFinderCache) {
			list = (List<TestrayProductVersion>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_TESTRAYPRODUCTVERSION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_TESTRAYPRODUCTVERSION;

				sql = sql.concat(TestrayProductVersionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<TestrayProductVersion>)QueryUtil.list(
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
	 * Removes all the testray product versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (TestrayProductVersion testrayProductVersion : findAll()) {
			remove(testrayProductVersion);
		}
	}

	/**
	 * Returns the number of testray product versions.
	 *
	 * @return the number of testray product versions
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
					_SQL_COUNT_TESTRAYPRODUCTVERSION);

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
		return TestrayProductVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the testray product version persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			TestrayProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			TestrayProductVersionModelImpl.FINDER_CACHE_ENABLED,
			TestrayProductVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			TestrayProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			TestrayProductVersionModelImpl.FINDER_CACHE_ENABLED,
			TestrayProductVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			TestrayProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			TestrayProductVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByT_N = new FinderPath(
			TestrayProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			TestrayProductVersionModelImpl.FINDER_CACHE_ENABLED,
			TestrayProductVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByT_N",
			new String[] {Long.class.getName(), String.class.getName()},
			TestrayProductVersionModelImpl.TESTRAYPROJECTID_COLUMN_BITMASK |
			TestrayProductVersionModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByT_N = new FinderPath(
			TestrayProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			TestrayProductVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_N",
			new String[] {Long.class.getName(), String.class.getName()});

		TestrayProductVersionUtil.setPersistence(this);
	}

	public void destroy() {
		TestrayProductVersionUtil.setPersistence(null);

		entityCache.removeCache(TestrayProductVersionImpl.class.getName());

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

	private static final String _SQL_SELECT_TESTRAYPRODUCTVERSION =
		"SELECT testrayProductVersion FROM TestrayProductVersion testrayProductVersion";

	private static final String _SQL_SELECT_TESTRAYPRODUCTVERSION_WHERE_PKS_IN =
		"SELECT testrayProductVersion FROM TestrayProductVersion testrayProductVersion WHERE testrayProductVersionId IN (";

	private static final String _SQL_SELECT_TESTRAYPRODUCTVERSION_WHERE =
		"SELECT testrayProductVersion FROM TestrayProductVersion testrayProductVersion WHERE ";

	private static final String _SQL_COUNT_TESTRAYPRODUCTVERSION =
		"SELECT COUNT(testrayProductVersion) FROM TestrayProductVersion testrayProductVersion";

	private static final String _SQL_COUNT_TESTRAYPRODUCTVERSION_WHERE =
		"SELECT COUNT(testrayProductVersion) FROM TestrayProductVersion testrayProductVersion WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"testrayProductVersion.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No TestrayProductVersion exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No TestrayProductVersion exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		TestrayProductVersionPersistenceImpl.class);

}