/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.testray.service.persistence.impl;

import com.liferay.osb.testray.exception.NoSuchTestrayBuildException;
import com.liferay.osb.testray.model.TestrayBuild;
import com.liferay.osb.testray.model.impl.TestrayBuildImpl;
import com.liferay.osb.testray.model.impl.TestrayBuildModelImpl;
import com.liferay.osb.testray.service.persistence.TestrayBuildPersistence;
import com.liferay.osb.testray.service.persistence.TestrayBuildUtil;
import com.liferay.osb.testray.service.persistence.TestrayCasePersistence;
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
 * The persistence implementation for the testray build service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ethan Bustad
 * @generated
 */
public class TestrayBuildPersistenceImpl
	extends BasePersistenceImpl<TestrayBuild>
	implements TestrayBuildPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>TestrayBuildUtil</code> to access the testray build persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		TestrayBuildImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByTRI_N;
	private FinderPath _finderPathCountByTRI_N;

	/**
	 * Returns the testray build where testrayRoutineId = &#63; and name = &#63; or throws a <code>NoSuchTestrayBuildException</code> if it could not be found.
	 *
	 * @param testrayRoutineId the testray routine ID
	 * @param name the name
	 * @return the matching testray build
	 * @throws NoSuchTestrayBuildException if a matching testray build could not be found
	 */
	@Override
	public TestrayBuild findByTRI_N(long testrayRoutineId, String name)
		throws NoSuchTestrayBuildException {

		TestrayBuild testrayBuild = fetchByTRI_N(testrayRoutineId, name);

		if (testrayBuild == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("testrayRoutineId=");
			sb.append(testrayRoutineId);

			sb.append(", name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchTestrayBuildException(sb.toString());
		}

		return testrayBuild;
	}

	/**
	 * Returns the testray build where testrayRoutineId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param testrayRoutineId the testray routine ID
	 * @param name the name
	 * @return the matching testray build, or <code>null</code> if a matching testray build could not be found
	 */
	@Override
	public TestrayBuild fetchByTRI_N(long testrayRoutineId, String name) {
		return fetchByTRI_N(testrayRoutineId, name, true);
	}

	/**
	 * Returns the testray build where testrayRoutineId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param testrayRoutineId the testray routine ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching testray build, or <code>null</code> if a matching testray build could not be found
	 */
	@Override
	public TestrayBuild fetchByTRI_N(
		long testrayRoutineId, String name, boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {testrayRoutineId, name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByTRI_N, finderArgs, this);
		}

		if (result instanceof TestrayBuild) {
			TestrayBuild testrayBuild = (TestrayBuild)result;

			if ((testrayRoutineId != testrayBuild.getTestrayRoutineId()) ||
				!Objects.equals(name, testrayBuild.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_TESTRAYBUILD_WHERE);

			sb.append(_FINDER_COLUMN_TRI_N_TESTRAYROUTINEID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_TRI_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_TRI_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(testrayRoutineId);

				if (bindName) {
					queryPos.add(name);
				}

				List<TestrayBuild> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByTRI_N, finderArgs, list);
					}
				}
				else {
					TestrayBuild testrayBuild = list.get(0);

					result = testrayBuild;

					cacheResult(testrayBuild);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByTRI_N, finderArgs);
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
			return (TestrayBuild)result;
		}
	}

	/**
	 * Removes the testray build where testrayRoutineId = &#63; and name = &#63; from the database.
	 *
	 * @param testrayRoutineId the testray routine ID
	 * @param name the name
	 * @return the testray build that was removed
	 */
	@Override
	public TestrayBuild removeByTRI_N(long testrayRoutineId, String name)
		throws NoSuchTestrayBuildException {

		TestrayBuild testrayBuild = findByTRI_N(testrayRoutineId, name);

		return remove(testrayBuild);
	}

	/**
	 * Returns the number of testray builds where testrayRoutineId = &#63; and name = &#63;.
	 *
	 * @param testrayRoutineId the testray routine ID
	 * @param name the name
	 * @return the number of matching testray builds
	 */
	@Override
	public int countByTRI_N(long testrayRoutineId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByTRI_N;

		Object[] finderArgs = new Object[] {testrayRoutineId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_TESTRAYBUILD_WHERE);

			sb.append(_FINDER_COLUMN_TRI_N_TESTRAYROUTINEID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_TRI_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_TRI_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(testrayRoutineId);

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

	private static final String _FINDER_COLUMN_TRI_N_TESTRAYROUTINEID_2 =
		"testrayBuild.testrayRoutineId = ? AND ";

	private static final String _FINDER_COLUMN_TRI_N_NAME_2 =
		"testrayBuild.name = ?";

	private static final String _FINDER_COLUMN_TRI_N_NAME_3 =
		"(testrayBuild.name IS NULL OR testrayBuild.name = '')";

	public TestrayBuildPersistenceImpl() {
		setModelClass(TestrayBuild.class);
	}

	/**
	 * Caches the testray build in the entity cache if it is enabled.
	 *
	 * @param testrayBuild the testray build
	 */
	@Override
	public void cacheResult(TestrayBuild testrayBuild) {
		entityCache.putResult(
			TestrayBuildModelImpl.ENTITY_CACHE_ENABLED, TestrayBuildImpl.class,
			testrayBuild.getPrimaryKey(), testrayBuild);

		finderCache.putResult(
			_finderPathFetchByTRI_N,
			new Object[] {
				testrayBuild.getTestrayRoutineId(), testrayBuild.getName()
			},
			testrayBuild);

		testrayBuild.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the testray builds in the entity cache if it is enabled.
	 *
	 * @param testrayBuilds the testray builds
	 */
	@Override
	public void cacheResult(List<TestrayBuild> testrayBuilds) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (testrayBuilds.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (TestrayBuild testrayBuild : testrayBuilds) {
			if (entityCache.getResult(
					TestrayBuildModelImpl.ENTITY_CACHE_ENABLED,
					TestrayBuildImpl.class, testrayBuild.getPrimaryKey()) ==
						null) {

				cacheResult(testrayBuild);
			}
			else {
				testrayBuild.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all testray builds.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(TestrayBuildImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the testray build.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(TestrayBuild testrayBuild) {
		entityCache.removeResult(
			TestrayBuildModelImpl.ENTITY_CACHE_ENABLED, TestrayBuildImpl.class,
			testrayBuild.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((TestrayBuildModelImpl)testrayBuild, true);
	}

	@Override
	public void clearCache(List<TestrayBuild> testrayBuilds) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (TestrayBuild testrayBuild : testrayBuilds) {
			entityCache.removeResult(
				TestrayBuildModelImpl.ENTITY_CACHE_ENABLED,
				TestrayBuildImpl.class, testrayBuild.getPrimaryKey());

			clearUniqueFindersCache((TestrayBuildModelImpl)testrayBuild, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				TestrayBuildModelImpl.ENTITY_CACHE_ENABLED,
				TestrayBuildImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		TestrayBuildModelImpl testrayBuildModelImpl) {

		Object[] args = new Object[] {
			testrayBuildModelImpl.getTestrayRoutineId(),
			testrayBuildModelImpl.getName()
		};

		finderCache.putResult(
			_finderPathCountByTRI_N, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByTRI_N, args, testrayBuildModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		TestrayBuildModelImpl testrayBuildModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				testrayBuildModelImpl.getTestrayRoutineId(),
				testrayBuildModelImpl.getName()
			};

			finderCache.removeResult(_finderPathCountByTRI_N, args);
			finderCache.removeResult(_finderPathFetchByTRI_N, args);
		}

		if ((testrayBuildModelImpl.getColumnBitmask() &
			 _finderPathFetchByTRI_N.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				testrayBuildModelImpl.getOriginalTestrayRoutineId(),
				testrayBuildModelImpl.getOriginalName()
			};

			finderCache.removeResult(_finderPathCountByTRI_N, args);
			finderCache.removeResult(_finderPathFetchByTRI_N, args);
		}
	}

	/**
	 * Creates a new testray build with the primary key. Does not add the testray build to the database.
	 *
	 * @param testrayBuildId the primary key for the new testray build
	 * @return the new testray build
	 */
	@Override
	public TestrayBuild create(long testrayBuildId) {
		TestrayBuild testrayBuild = new TestrayBuildImpl();

		testrayBuild.setNew(true);
		testrayBuild.setPrimaryKey(testrayBuildId);

		testrayBuild.setCompanyId(CompanyThreadLocal.getCompanyId());

		return testrayBuild;
	}

	/**
	 * Removes the testray build with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param testrayBuildId the primary key of the testray build
	 * @return the testray build that was removed
	 * @throws NoSuchTestrayBuildException if a testray build with the primary key could not be found
	 */
	@Override
	public TestrayBuild remove(long testrayBuildId)
		throws NoSuchTestrayBuildException {

		return remove((Serializable)testrayBuildId);
	}

	/**
	 * Removes the testray build with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the testray build
	 * @return the testray build that was removed
	 * @throws NoSuchTestrayBuildException if a testray build with the primary key could not be found
	 */
	@Override
	public TestrayBuild remove(Serializable primaryKey)
		throws NoSuchTestrayBuildException {

		Session session = null;

		try {
			session = openSession();

			TestrayBuild testrayBuild = (TestrayBuild)session.get(
				TestrayBuildImpl.class, primaryKey);

			if (testrayBuild == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTestrayBuildException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(testrayBuild);
		}
		catch (NoSuchTestrayBuildException noSuchEntityException) {
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
	protected TestrayBuild removeImpl(TestrayBuild testrayBuild) {
		testrayBuildToTestrayCaseTableMapper.deleteLeftPrimaryKeyTableMappings(
			testrayBuild.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(testrayBuild)) {
				testrayBuild = (TestrayBuild)session.get(
					TestrayBuildImpl.class, testrayBuild.getPrimaryKeyObj());
			}

			if (testrayBuild != null) {
				session.delete(testrayBuild);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (testrayBuild != null) {
			clearCache(testrayBuild);
		}

		return testrayBuild;
	}

	@Override
	public TestrayBuild updateImpl(TestrayBuild testrayBuild) {
		boolean isNew = testrayBuild.isNew();

		if (!(testrayBuild instanceof TestrayBuildModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(testrayBuild.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					testrayBuild);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in testrayBuild proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom TestrayBuild implementation " +
					testrayBuild.getClass());
		}

		TestrayBuildModelImpl testrayBuildModelImpl =
			(TestrayBuildModelImpl)testrayBuild;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (testrayBuild.getCreateDate() == null)) {
			if (serviceContext == null) {
				testrayBuild.setCreateDate(date);
			}
			else {
				testrayBuild.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!testrayBuildModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				testrayBuild.setModifiedDate(date);
			}
			else {
				testrayBuild.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(testrayBuild);

				testrayBuild.setNew(false);
			}
			else {
				testrayBuild = (TestrayBuild)session.merge(testrayBuild);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!TestrayBuildModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			TestrayBuildModelImpl.ENTITY_CACHE_ENABLED, TestrayBuildImpl.class,
			testrayBuild.getPrimaryKey(), testrayBuild, false);

		clearUniqueFindersCache(testrayBuildModelImpl, false);
		cacheUniqueFindersCache(testrayBuildModelImpl);

		testrayBuild.resetOriginalValues();

		return testrayBuild;
	}

	/**
	 * Returns the testray build with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray build
	 * @return the testray build
	 * @throws NoSuchTestrayBuildException if a testray build with the primary key could not be found
	 */
	@Override
	public TestrayBuild findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTestrayBuildException {

		TestrayBuild testrayBuild = fetchByPrimaryKey(primaryKey);

		if (testrayBuild == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTestrayBuildException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return testrayBuild;
	}

	/**
	 * Returns the testray build with the primary key or throws a <code>NoSuchTestrayBuildException</code> if it could not be found.
	 *
	 * @param testrayBuildId the primary key of the testray build
	 * @return the testray build
	 * @throws NoSuchTestrayBuildException if a testray build with the primary key could not be found
	 */
	@Override
	public TestrayBuild findByPrimaryKey(long testrayBuildId)
		throws NoSuchTestrayBuildException {

		return findByPrimaryKey((Serializable)testrayBuildId);
	}

	/**
	 * Returns the testray build with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray build
	 * @return the testray build, or <code>null</code> if a testray build with the primary key could not be found
	 */
	@Override
	public TestrayBuild fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			TestrayBuildModelImpl.ENTITY_CACHE_ENABLED, TestrayBuildImpl.class,
			primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		TestrayBuild testrayBuild = (TestrayBuild)serializable;

		if (testrayBuild == null) {
			Session session = null;

			try {
				session = openSession();

				testrayBuild = (TestrayBuild)session.get(
					TestrayBuildImpl.class, primaryKey);

				if (testrayBuild != null) {
					cacheResult(testrayBuild);
				}
				else {
					entityCache.putResult(
						TestrayBuildModelImpl.ENTITY_CACHE_ENABLED,
						TestrayBuildImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					TestrayBuildModelImpl.ENTITY_CACHE_ENABLED,
					TestrayBuildImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return testrayBuild;
	}

	/**
	 * Returns the testray build with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param testrayBuildId the primary key of the testray build
	 * @return the testray build, or <code>null</code> if a testray build with the primary key could not be found
	 */
	@Override
	public TestrayBuild fetchByPrimaryKey(long testrayBuildId) {
		return fetchByPrimaryKey((Serializable)testrayBuildId);
	}

	@Override
	public Map<Serializable, TestrayBuild> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, TestrayBuild> map =
			new HashMap<Serializable, TestrayBuild>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			TestrayBuild testrayBuild = fetchByPrimaryKey(primaryKey);

			if (testrayBuild != null) {
				map.put(primaryKey, testrayBuild);
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
				TestrayBuildModelImpl.ENTITY_CACHE_ENABLED,
				TestrayBuildImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (TestrayBuild)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_TESTRAYBUILD_WHERE_PKS_IN);

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

			for (TestrayBuild testrayBuild : (List<TestrayBuild>)query.list()) {
				map.put(testrayBuild.getPrimaryKeyObj(), testrayBuild);

				cacheResult(testrayBuild);

				uncachedPrimaryKeys.remove(testrayBuild.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					TestrayBuildModelImpl.ENTITY_CACHE_ENABLED,
					TestrayBuildImpl.class, primaryKey, nullModel);
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
	 * Returns all the testray builds.
	 *
	 * @return the testray builds
	 */
	@Override
	public List<TestrayBuild> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the testray builds.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayBuildModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray builds
	 * @param end the upper bound of the range of testray builds (not inclusive)
	 * @return the range of testray builds
	 */
	@Override
	public List<TestrayBuild> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray builds.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayBuildModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray builds
	 * @param end the upper bound of the range of testray builds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray builds
	 */
	@Override
	public List<TestrayBuild> findAll(
		int start, int end, OrderByComparator<TestrayBuild> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the testray builds.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayBuildModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray builds
	 * @param end the upper bound of the range of testray builds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of testray builds
	 */
	@Override
	public List<TestrayBuild> findAll(
		int start, int end, OrderByComparator<TestrayBuild> orderByComparator,
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

		List<TestrayBuild> list = null;

		if (useFinderCache) {
			list = (List<TestrayBuild>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_TESTRAYBUILD);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_TESTRAYBUILD;

				sql = sql.concat(TestrayBuildModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<TestrayBuild>)QueryUtil.list(
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
	 * Removes all the testray builds from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (TestrayBuild testrayBuild : findAll()) {
			remove(testrayBuild);
		}
	}

	/**
	 * Returns the number of testray builds.
	 *
	 * @return the number of testray builds
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_TESTRAYBUILD);

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
	 * Returns the primaryKeys of testray cases associated with the testray build.
	 *
	 * @param pk the primary key of the testray build
	 * @return long[] of the primaryKeys of testray cases associated with the testray build
	 */
	@Override
	public long[] getTestrayCasePrimaryKeys(long pk) {
		long[] pks = testrayBuildToTestrayCaseTableMapper.getRightPrimaryKeys(
			pk);

		return pks.clone();
	}

	/**
	 * Returns all the testray cases associated with the testray build.
	 *
	 * @param pk the primary key of the testray build
	 * @return the testray cases associated with the testray build
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayCase> getTestrayCases(
		long pk) {

		return getTestrayCases(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the testray cases associated with the testray build.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayBuildModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray build
	 * @param start the lower bound of the range of testray builds
	 * @param end the upper bound of the range of testray builds (not inclusive)
	 * @return the range of testray cases associated with the testray build
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayCase> getTestrayCases(
		long pk, int start, int end) {

		return getTestrayCases(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray cases associated with the testray build.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayBuildModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray build
	 * @param start the lower bound of the range of testray builds
	 * @param end the upper bound of the range of testray builds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray cases associated with the testray build
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayCase> getTestrayCases(
		long pk, int start, int end,
		OrderByComparator<com.liferay.osb.testray.model.TestrayCase>
			orderByComparator) {

		return testrayBuildToTestrayCaseTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of testray cases associated with the testray build.
	 *
	 * @param pk the primary key of the testray build
	 * @return the number of testray cases associated with the testray build
	 */
	@Override
	public int getTestrayCasesSize(long pk) {
		long[] pks = testrayBuildToTestrayCaseTableMapper.getRightPrimaryKeys(
			pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the testray case is associated with the testray build.
	 *
	 * @param pk the primary key of the testray build
	 * @param testrayCasePK the primary key of the testray case
	 * @return <code>true</code> if the testray case is associated with the testray build; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTestrayCase(long pk, long testrayCasePK) {
		return testrayBuildToTestrayCaseTableMapper.containsTableMapping(
			pk, testrayCasePK);
	}

	/**
	 * Returns <code>true</code> if the testray build has any testray cases associated with it.
	 *
	 * @param pk the primary key of the testray build to check for associations with testray cases
	 * @return <code>true</code> if the testray build has any testray cases associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTestrayCases(long pk) {
		if (getTestrayCasesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the testray build and the testray case. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray build
	 * @param testrayCasePK the primary key of the testray case
	 */
	@Override
	public void addTestrayCase(long pk, long testrayCasePK) {
		TestrayBuild testrayBuild = fetchByPrimaryKey(pk);

		if (testrayBuild == null) {
			testrayBuildToTestrayCaseTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, testrayCasePK);
		}
		else {
			testrayBuildToTestrayCaseTableMapper.addTableMapping(
				testrayBuild.getCompanyId(), pk, testrayCasePK);
		}
	}

	/**
	 * Adds an association between the testray build and the testray case. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray build
	 * @param testrayCase the testray case
	 */
	@Override
	public void addTestrayCase(
		long pk, com.liferay.osb.testray.model.TestrayCase testrayCase) {

		TestrayBuild testrayBuild = fetchByPrimaryKey(pk);

		if (testrayBuild == null) {
			testrayBuildToTestrayCaseTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				testrayCase.getPrimaryKey());
		}
		else {
			testrayBuildToTestrayCaseTableMapper.addTableMapping(
				testrayBuild.getCompanyId(), pk, testrayCase.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the testray build and the testray cases. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray build
	 * @param testrayCasePKs the primary keys of the testray cases
	 */
	@Override
	public void addTestrayCases(long pk, long[] testrayCasePKs) {
		long companyId = 0;

		TestrayBuild testrayBuild = fetchByPrimaryKey(pk);

		if (testrayBuild == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testrayBuild.getCompanyId();
		}

		testrayBuildToTestrayCaseTableMapper.addTableMappings(
			companyId, pk, testrayCasePKs);
	}

	/**
	 * Adds an association between the testray build and the testray cases. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray build
	 * @param testrayCases the testray cases
	 */
	@Override
	public void addTestrayCases(
		long pk, List<com.liferay.osb.testray.model.TestrayCase> testrayCases) {

		addTestrayCases(
			pk,
			ListUtil.toLongArray(
				testrayCases,
				com.liferay.osb.testray.model.TestrayCase.
					TESTRAY_CASE_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the testray build and its testray cases. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray build to clear the associated testray cases from
	 */
	@Override
	public void clearTestrayCases(long pk) {
		testrayBuildToTestrayCaseTableMapper.deleteLeftPrimaryKeyTableMappings(
			pk);
	}

	/**
	 * Removes the association between the testray build and the testray case. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray build
	 * @param testrayCasePK the primary key of the testray case
	 */
	@Override
	public void removeTestrayCase(long pk, long testrayCasePK) {
		testrayBuildToTestrayCaseTableMapper.deleteTableMapping(
			pk, testrayCasePK);
	}

	/**
	 * Removes the association between the testray build and the testray case. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray build
	 * @param testrayCase the testray case
	 */
	@Override
	public void removeTestrayCase(
		long pk, com.liferay.osb.testray.model.TestrayCase testrayCase) {

		testrayBuildToTestrayCaseTableMapper.deleteTableMapping(
			pk, testrayCase.getPrimaryKey());
	}

	/**
	 * Removes the association between the testray build and the testray cases. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray build
	 * @param testrayCasePKs the primary keys of the testray cases
	 */
	@Override
	public void removeTestrayCases(long pk, long[] testrayCasePKs) {
		testrayBuildToTestrayCaseTableMapper.deleteTableMappings(
			pk, testrayCasePKs);
	}

	/**
	 * Removes the association between the testray build and the testray cases. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray build
	 * @param testrayCases the testray cases
	 */
	@Override
	public void removeTestrayCases(
		long pk, List<com.liferay.osb.testray.model.TestrayCase> testrayCases) {

		removeTestrayCases(
			pk,
			ListUtil.toLongArray(
				testrayCases,
				com.liferay.osb.testray.model.TestrayCase.
					TESTRAY_CASE_ID_ACCESSOR));
	}

	/**
	 * Sets the testray cases associated with the testray build, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray build
	 * @param testrayCasePKs the primary keys of the testray cases to be associated with the testray build
	 */
	@Override
	public void setTestrayCases(long pk, long[] testrayCasePKs) {
		Set<Long> newTestrayCasePKsSet = SetUtil.fromArray(testrayCasePKs);
		Set<Long> oldTestrayCasePKsSet = SetUtil.fromArray(
			testrayBuildToTestrayCaseTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeTestrayCasePKsSet = new HashSet<Long>(
			oldTestrayCasePKsSet);

		removeTestrayCasePKsSet.removeAll(newTestrayCasePKsSet);

		testrayBuildToTestrayCaseTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeTestrayCasePKsSet));

		newTestrayCasePKsSet.removeAll(oldTestrayCasePKsSet);

		long companyId = 0;

		TestrayBuild testrayBuild = fetchByPrimaryKey(pk);

		if (testrayBuild == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testrayBuild.getCompanyId();
		}

		testrayBuildToTestrayCaseTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newTestrayCasePKsSet));
	}

	/**
	 * Sets the testray cases associated with the testray build, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray build
	 * @param testrayCases the testray cases to be associated with the testray build
	 */
	@Override
	public void setTestrayCases(
		long pk, List<com.liferay.osb.testray.model.TestrayCase> testrayCases) {

		try {
			long[] testrayCasePKs = new long[testrayCases.size()];

			for (int i = 0; i < testrayCases.size(); i++) {
				com.liferay.osb.testray.model.TestrayCase testrayCase =
					testrayCases.get(i);

				testrayCasePKs[i] = testrayCase.getPrimaryKey();
			}

			setTestrayCases(pk, testrayCasePKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return TestrayBuildModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the testray build persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		testrayBuildToTestrayCaseTableMapper =
			TableMapperFactory.getTableMapper(
				"OSB_TestrayBuilds_TestrayCases", "companyId", "testrayBuildId",
				"testrayCaseId", this, testrayCasePersistence);

		_finderPathWithPaginationFindAll = new FinderPath(
			TestrayBuildModelImpl.ENTITY_CACHE_ENABLED,
			TestrayBuildModelImpl.FINDER_CACHE_ENABLED, TestrayBuildImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			TestrayBuildModelImpl.ENTITY_CACHE_ENABLED,
			TestrayBuildModelImpl.FINDER_CACHE_ENABLED, TestrayBuildImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			TestrayBuildModelImpl.ENTITY_CACHE_ENABLED,
			TestrayBuildModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByTRI_N = new FinderPath(
			TestrayBuildModelImpl.ENTITY_CACHE_ENABLED,
			TestrayBuildModelImpl.FINDER_CACHE_ENABLED, TestrayBuildImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByTRI_N",
			new String[] {Long.class.getName(), String.class.getName()},
			TestrayBuildModelImpl.TESTRAYROUTINEID_COLUMN_BITMASK |
			TestrayBuildModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByTRI_N = new FinderPath(
			TestrayBuildModelImpl.ENTITY_CACHE_ENABLED,
			TestrayBuildModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTRI_N",
			new String[] {Long.class.getName(), String.class.getName()});

		TestrayBuildUtil.setPersistence(this);
	}

	public void destroy() {
		TestrayBuildUtil.setPersistence(null);

		entityCache.removeCache(TestrayBuildImpl.class.getName());

		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper("OSB_TestrayBuilds_TestrayCases");
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

	@BeanReference(type = TestrayCasePersistence.class)
	protected TestrayCasePersistence testrayCasePersistence;

	protected TableMapper
		<TestrayBuild, com.liferay.osb.testray.model.TestrayCase>
			testrayBuildToTestrayCaseTableMapper;

	private static final String _SQL_SELECT_TESTRAYBUILD =
		"SELECT testrayBuild FROM TestrayBuild testrayBuild";

	private static final String _SQL_SELECT_TESTRAYBUILD_WHERE_PKS_IN =
		"SELECT testrayBuild FROM TestrayBuild testrayBuild WHERE testrayBuildId IN (";

	private static final String _SQL_SELECT_TESTRAYBUILD_WHERE =
		"SELECT testrayBuild FROM TestrayBuild testrayBuild WHERE ";

	private static final String _SQL_COUNT_TESTRAYBUILD =
		"SELECT COUNT(testrayBuild) FROM TestrayBuild testrayBuild";

	private static final String _SQL_COUNT_TESTRAYBUILD_WHERE =
		"SELECT COUNT(testrayBuild) FROM TestrayBuild testrayBuild WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "testrayBuild.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No TestrayBuild exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No TestrayBuild exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		TestrayBuildPersistenceImpl.class);

}