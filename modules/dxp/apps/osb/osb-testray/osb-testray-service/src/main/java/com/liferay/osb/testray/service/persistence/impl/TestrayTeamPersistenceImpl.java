/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.testray.service.persistence.impl;

import com.liferay.osb.testray.exception.NoSuchTestrayTeamException;
import com.liferay.osb.testray.model.TestrayTeam;
import com.liferay.osb.testray.model.impl.TestrayTeamImpl;
import com.liferay.osb.testray.model.impl.TestrayTeamModelImpl;
import com.liferay.osb.testray.service.persistence.TestrayTeamPersistence;
import com.liferay.osb.testray.service.persistence.TestrayTeamUtil;
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
 * The persistence implementation for the testray team service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ethan Bustad
 * @generated
 */
public class TestrayTeamPersistenceImpl
	extends BasePersistenceImpl<TestrayTeam> implements TestrayTeamPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>TestrayTeamUtil</code> to access the testray team persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		TestrayTeamImpl.class.getName();

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
	 * Returns the testray team where testrayProjectId = &#63; and name = &#63; or throws a <code>NoSuchTestrayTeamException</code> if it could not be found.
	 *
	 * @param testrayProjectId the testray project ID
	 * @param name the name
	 * @return the matching testray team
	 * @throws NoSuchTestrayTeamException if a matching testray team could not be found
	 */
	@Override
	public TestrayTeam findByTPI_N(long testrayProjectId, String name)
		throws NoSuchTestrayTeamException {

		TestrayTeam testrayTeam = fetchByTPI_N(testrayProjectId, name);

		if (testrayTeam == null) {
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

			throw new NoSuchTestrayTeamException(sb.toString());
		}

		return testrayTeam;
	}

	/**
	 * Returns the testray team where testrayProjectId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param testrayProjectId the testray project ID
	 * @param name the name
	 * @return the matching testray team, or <code>null</code> if a matching testray team could not be found
	 */
	@Override
	public TestrayTeam fetchByTPI_N(long testrayProjectId, String name) {
		return fetchByTPI_N(testrayProjectId, name, true);
	}

	/**
	 * Returns the testray team where testrayProjectId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param testrayProjectId the testray project ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching testray team, or <code>null</code> if a matching testray team could not be found
	 */
	@Override
	public TestrayTeam fetchByTPI_N(
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

		if (result instanceof TestrayTeam) {
			TestrayTeam testrayTeam = (TestrayTeam)result;

			if ((testrayProjectId != testrayTeam.getTestrayProjectId()) ||
				!Objects.equals(name, testrayTeam.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_TESTRAYTEAM_WHERE);

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

				List<TestrayTeam> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByTPI_N, finderArgs, list);
					}
				}
				else {
					TestrayTeam testrayTeam = list.get(0);

					result = testrayTeam;

					cacheResult(testrayTeam);
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
			return (TestrayTeam)result;
		}
	}

	/**
	 * Removes the testray team where testrayProjectId = &#63; and name = &#63; from the database.
	 *
	 * @param testrayProjectId the testray project ID
	 * @param name the name
	 * @return the testray team that was removed
	 */
	@Override
	public TestrayTeam removeByTPI_N(long testrayProjectId, String name)
		throws NoSuchTestrayTeamException {

		TestrayTeam testrayTeam = findByTPI_N(testrayProjectId, name);

		return remove(testrayTeam);
	}

	/**
	 * Returns the number of testray teams where testrayProjectId = &#63; and name = &#63;.
	 *
	 * @param testrayProjectId the testray project ID
	 * @param name the name
	 * @return the number of matching testray teams
	 */
	@Override
	public int countByTPI_N(long testrayProjectId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByTPI_N;

		Object[] finderArgs = new Object[] {testrayProjectId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_TESTRAYTEAM_WHERE);

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
		"testrayTeam.testrayProjectId = ? AND ";

	private static final String _FINDER_COLUMN_TPI_N_NAME_2 =
		"testrayTeam.name = ?";

	private static final String _FINDER_COLUMN_TPI_N_NAME_3 =
		"(testrayTeam.name IS NULL OR testrayTeam.name = '')";

	public TestrayTeamPersistenceImpl() {
		setModelClass(TestrayTeam.class);
	}

	/**
	 * Caches the testray team in the entity cache if it is enabled.
	 *
	 * @param testrayTeam the testray team
	 */
	@Override
	public void cacheResult(TestrayTeam testrayTeam) {
		entityCache.putResult(
			TestrayTeamModelImpl.ENTITY_CACHE_ENABLED, TestrayTeamImpl.class,
			testrayTeam.getPrimaryKey(), testrayTeam);

		finderCache.putResult(
			_finderPathFetchByTPI_N,
			new Object[] {
				testrayTeam.getTestrayProjectId(), testrayTeam.getName()
			},
			testrayTeam);

		testrayTeam.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the testray teams in the entity cache if it is enabled.
	 *
	 * @param testrayTeams the testray teams
	 */
	@Override
	public void cacheResult(List<TestrayTeam> testrayTeams) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (testrayTeams.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (TestrayTeam testrayTeam : testrayTeams) {
			if (entityCache.getResult(
					TestrayTeamModelImpl.ENTITY_CACHE_ENABLED,
					TestrayTeamImpl.class, testrayTeam.getPrimaryKey()) ==
						null) {

				cacheResult(testrayTeam);
			}
			else {
				testrayTeam.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all testray teams.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(TestrayTeamImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the testray team.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(TestrayTeam testrayTeam) {
		entityCache.removeResult(
			TestrayTeamModelImpl.ENTITY_CACHE_ENABLED, TestrayTeamImpl.class,
			testrayTeam.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((TestrayTeamModelImpl)testrayTeam, true);
	}

	@Override
	public void clearCache(List<TestrayTeam> testrayTeams) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (TestrayTeam testrayTeam : testrayTeams) {
			entityCache.removeResult(
				TestrayTeamModelImpl.ENTITY_CACHE_ENABLED,
				TestrayTeamImpl.class, testrayTeam.getPrimaryKey());

			clearUniqueFindersCache((TestrayTeamModelImpl)testrayTeam, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				TestrayTeamModelImpl.ENTITY_CACHE_ENABLED,
				TestrayTeamImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		TestrayTeamModelImpl testrayTeamModelImpl) {

		Object[] args = new Object[] {
			testrayTeamModelImpl.getTestrayProjectId(),
			testrayTeamModelImpl.getName()
		};

		finderCache.putResult(
			_finderPathCountByTPI_N, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByTPI_N, args, testrayTeamModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		TestrayTeamModelImpl testrayTeamModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				testrayTeamModelImpl.getTestrayProjectId(),
				testrayTeamModelImpl.getName()
			};

			finderCache.removeResult(_finderPathCountByTPI_N, args);
			finderCache.removeResult(_finderPathFetchByTPI_N, args);
		}

		if ((testrayTeamModelImpl.getColumnBitmask() &
			 _finderPathFetchByTPI_N.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				testrayTeamModelImpl.getOriginalTestrayProjectId(),
				testrayTeamModelImpl.getOriginalName()
			};

			finderCache.removeResult(_finderPathCountByTPI_N, args);
			finderCache.removeResult(_finderPathFetchByTPI_N, args);
		}
	}

	/**
	 * Creates a new testray team with the primary key. Does not add the testray team to the database.
	 *
	 * @param testrayTeamId the primary key for the new testray team
	 * @return the new testray team
	 */
	@Override
	public TestrayTeam create(long testrayTeamId) {
		TestrayTeam testrayTeam = new TestrayTeamImpl();

		testrayTeam.setNew(true);
		testrayTeam.setPrimaryKey(testrayTeamId);

		testrayTeam.setCompanyId(CompanyThreadLocal.getCompanyId());

		return testrayTeam;
	}

	/**
	 * Removes the testray team with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param testrayTeamId the primary key of the testray team
	 * @return the testray team that was removed
	 * @throws NoSuchTestrayTeamException if a testray team with the primary key could not be found
	 */
	@Override
	public TestrayTeam remove(long testrayTeamId)
		throws NoSuchTestrayTeamException {

		return remove((Serializable)testrayTeamId);
	}

	/**
	 * Removes the testray team with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the testray team
	 * @return the testray team that was removed
	 * @throws NoSuchTestrayTeamException if a testray team with the primary key could not be found
	 */
	@Override
	public TestrayTeam remove(Serializable primaryKey)
		throws NoSuchTestrayTeamException {

		Session session = null;

		try {
			session = openSession();

			TestrayTeam testrayTeam = (TestrayTeam)session.get(
				TestrayTeamImpl.class, primaryKey);

			if (testrayTeam == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTestrayTeamException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(testrayTeam);
		}
		catch (NoSuchTestrayTeamException noSuchEntityException) {
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
	protected TestrayTeam removeImpl(TestrayTeam testrayTeam) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(testrayTeam)) {
				testrayTeam = (TestrayTeam)session.get(
					TestrayTeamImpl.class, testrayTeam.getPrimaryKeyObj());
			}

			if (testrayTeam != null) {
				session.delete(testrayTeam);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (testrayTeam != null) {
			clearCache(testrayTeam);
		}

		return testrayTeam;
	}

	@Override
	public TestrayTeam updateImpl(TestrayTeam testrayTeam) {
		boolean isNew = testrayTeam.isNew();

		if (!(testrayTeam instanceof TestrayTeamModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(testrayTeam.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(testrayTeam);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in testrayTeam proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom TestrayTeam implementation " +
					testrayTeam.getClass());
		}

		TestrayTeamModelImpl testrayTeamModelImpl =
			(TestrayTeamModelImpl)testrayTeam;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (testrayTeam.getCreateDate() == null)) {
			if (serviceContext == null) {
				testrayTeam.setCreateDate(date);
			}
			else {
				testrayTeam.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!testrayTeamModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				testrayTeam.setModifiedDate(date);
			}
			else {
				testrayTeam.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(testrayTeam);

				testrayTeam.setNew(false);
			}
			else {
				testrayTeam = (TestrayTeam)session.merge(testrayTeam);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!TestrayTeamModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			TestrayTeamModelImpl.ENTITY_CACHE_ENABLED, TestrayTeamImpl.class,
			testrayTeam.getPrimaryKey(), testrayTeam, false);

		clearUniqueFindersCache(testrayTeamModelImpl, false);
		cacheUniqueFindersCache(testrayTeamModelImpl);

		testrayTeam.resetOriginalValues();

		return testrayTeam;
	}

	/**
	 * Returns the testray team with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray team
	 * @return the testray team
	 * @throws NoSuchTestrayTeamException if a testray team with the primary key could not be found
	 */
	@Override
	public TestrayTeam findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTestrayTeamException {

		TestrayTeam testrayTeam = fetchByPrimaryKey(primaryKey);

		if (testrayTeam == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTestrayTeamException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return testrayTeam;
	}

	/**
	 * Returns the testray team with the primary key or throws a <code>NoSuchTestrayTeamException</code> if it could not be found.
	 *
	 * @param testrayTeamId the primary key of the testray team
	 * @return the testray team
	 * @throws NoSuchTestrayTeamException if a testray team with the primary key could not be found
	 */
	@Override
	public TestrayTeam findByPrimaryKey(long testrayTeamId)
		throws NoSuchTestrayTeamException {

		return findByPrimaryKey((Serializable)testrayTeamId);
	}

	/**
	 * Returns the testray team with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray team
	 * @return the testray team, or <code>null</code> if a testray team with the primary key could not be found
	 */
	@Override
	public TestrayTeam fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			TestrayTeamModelImpl.ENTITY_CACHE_ENABLED, TestrayTeamImpl.class,
			primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		TestrayTeam testrayTeam = (TestrayTeam)serializable;

		if (testrayTeam == null) {
			Session session = null;

			try {
				session = openSession();

				testrayTeam = (TestrayTeam)session.get(
					TestrayTeamImpl.class, primaryKey);

				if (testrayTeam != null) {
					cacheResult(testrayTeam);
				}
				else {
					entityCache.putResult(
						TestrayTeamModelImpl.ENTITY_CACHE_ENABLED,
						TestrayTeamImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					TestrayTeamModelImpl.ENTITY_CACHE_ENABLED,
					TestrayTeamImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return testrayTeam;
	}

	/**
	 * Returns the testray team with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param testrayTeamId the primary key of the testray team
	 * @return the testray team, or <code>null</code> if a testray team with the primary key could not be found
	 */
	@Override
	public TestrayTeam fetchByPrimaryKey(long testrayTeamId) {
		return fetchByPrimaryKey((Serializable)testrayTeamId);
	}

	@Override
	public Map<Serializable, TestrayTeam> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, TestrayTeam> map =
			new HashMap<Serializable, TestrayTeam>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			TestrayTeam testrayTeam = fetchByPrimaryKey(primaryKey);

			if (testrayTeam != null) {
				map.put(primaryKey, testrayTeam);
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
				TestrayTeamModelImpl.ENTITY_CACHE_ENABLED,
				TestrayTeamImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (TestrayTeam)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_TESTRAYTEAM_WHERE_PKS_IN);

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

			for (TestrayTeam testrayTeam : (List<TestrayTeam>)query.list()) {
				map.put(testrayTeam.getPrimaryKeyObj(), testrayTeam);

				cacheResult(testrayTeam);

				uncachedPrimaryKeys.remove(testrayTeam.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					TestrayTeamModelImpl.ENTITY_CACHE_ENABLED,
					TestrayTeamImpl.class, primaryKey, nullModel);
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
	 * Returns all the testray teams.
	 *
	 * @return the testray teams
	 */
	@Override
	public List<TestrayTeam> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the testray teams.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayTeamModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray teams
	 * @param end the upper bound of the range of testray teams (not inclusive)
	 * @return the range of testray teams
	 */
	@Override
	public List<TestrayTeam> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray teams.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayTeamModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray teams
	 * @param end the upper bound of the range of testray teams (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray teams
	 */
	@Override
	public List<TestrayTeam> findAll(
		int start, int end, OrderByComparator<TestrayTeam> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the testray teams.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayTeamModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray teams
	 * @param end the upper bound of the range of testray teams (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of testray teams
	 */
	@Override
	public List<TestrayTeam> findAll(
		int start, int end, OrderByComparator<TestrayTeam> orderByComparator,
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

		List<TestrayTeam> list = null;

		if (useFinderCache) {
			list = (List<TestrayTeam>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_TESTRAYTEAM);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_TESTRAYTEAM;

				sql = sql.concat(TestrayTeamModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<TestrayTeam>)QueryUtil.list(
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
	 * Removes all the testray teams from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (TestrayTeam testrayTeam : findAll()) {
			remove(testrayTeam);
		}
	}

	/**
	 * Returns the number of testray teams.
	 *
	 * @return the number of testray teams
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_TESTRAYTEAM);

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
		return TestrayTeamModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the testray team persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			TestrayTeamModelImpl.ENTITY_CACHE_ENABLED,
			TestrayTeamModelImpl.FINDER_CACHE_ENABLED, TestrayTeamImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			TestrayTeamModelImpl.ENTITY_CACHE_ENABLED,
			TestrayTeamModelImpl.FINDER_CACHE_ENABLED, TestrayTeamImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			TestrayTeamModelImpl.ENTITY_CACHE_ENABLED,
			TestrayTeamModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByTPI_N = new FinderPath(
			TestrayTeamModelImpl.ENTITY_CACHE_ENABLED,
			TestrayTeamModelImpl.FINDER_CACHE_ENABLED, TestrayTeamImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByTPI_N",
			new String[] {Long.class.getName(), String.class.getName()},
			TestrayTeamModelImpl.TESTRAYPROJECTID_COLUMN_BITMASK |
			TestrayTeamModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByTPI_N = new FinderPath(
			TestrayTeamModelImpl.ENTITY_CACHE_ENABLED,
			TestrayTeamModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTPI_N",
			new String[] {Long.class.getName(), String.class.getName()});

		TestrayTeamUtil.setPersistence(this);
	}

	public void destroy() {
		TestrayTeamUtil.setPersistence(null);

		entityCache.removeCache(TestrayTeamImpl.class.getName());

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

	private static final String _SQL_SELECT_TESTRAYTEAM =
		"SELECT testrayTeam FROM TestrayTeam testrayTeam";

	private static final String _SQL_SELECT_TESTRAYTEAM_WHERE_PKS_IN =
		"SELECT testrayTeam FROM TestrayTeam testrayTeam WHERE testrayTeamId IN (";

	private static final String _SQL_SELECT_TESTRAYTEAM_WHERE =
		"SELECT testrayTeam FROM TestrayTeam testrayTeam WHERE ";

	private static final String _SQL_COUNT_TESTRAYTEAM =
		"SELECT COUNT(testrayTeam) FROM TestrayTeam testrayTeam";

	private static final String _SQL_COUNT_TESTRAYTEAM_WHERE =
		"SELECT COUNT(testrayTeam) FROM TestrayTeam testrayTeam WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "testrayTeam.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No TestrayTeam exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No TestrayTeam exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		TestrayTeamPersistenceImpl.class);

}