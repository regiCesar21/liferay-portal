/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.testray.service.persistence.impl;

import com.liferay.osb.testray.exception.NoSuchTestrayRunException;
import com.liferay.osb.testray.model.TestrayRun;
import com.liferay.osb.testray.model.impl.TestrayRunImpl;
import com.liferay.osb.testray.model.impl.TestrayRunModelImpl;
import com.liferay.osb.testray.service.persistence.TestrayRunPersistence;
import com.liferay.osb.testray.service.persistence.TestrayRunUtil;
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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
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
 * The persistence implementation for the testray run service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ethan Bustad
 * @generated
 */
public class TestrayRunPersistenceImpl
	extends BasePersistenceImpl<TestrayRun> implements TestrayRunPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>TestrayRunUtil</code> to access the testray run persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		TestrayRunImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByT_E_E;
	private FinderPath _finderPathCountByT_E_E;

	/**
	 * Returns the testray run where testrayBuildId = &#63; and externalReferencePK = &#63; and externalReferenceType = &#63; or throws a <code>NoSuchTestrayRunException</code> if it could not be found.
	 *
	 * @param testrayBuildId the testray build ID
	 * @param externalReferencePK the external reference pk
	 * @param externalReferenceType the external reference type
	 * @return the matching testray run
	 * @throws NoSuchTestrayRunException if a matching testray run could not be found
	 */
	@Override
	public TestrayRun findByT_E_E(
			long testrayBuildId, String externalReferencePK,
			int externalReferenceType)
		throws NoSuchTestrayRunException {

		TestrayRun testrayRun = fetchByT_E_E(
			testrayBuildId, externalReferencePK, externalReferenceType);

		if (testrayRun == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("testrayBuildId=");
			sb.append(testrayBuildId);

			sb.append(", externalReferencePK=");
			sb.append(externalReferencePK);

			sb.append(", externalReferenceType=");
			sb.append(externalReferenceType);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchTestrayRunException(sb.toString());
		}

		return testrayRun;
	}

	/**
	 * Returns the testray run where testrayBuildId = &#63; and externalReferencePK = &#63; and externalReferenceType = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param testrayBuildId the testray build ID
	 * @param externalReferencePK the external reference pk
	 * @param externalReferenceType the external reference type
	 * @return the matching testray run, or <code>null</code> if a matching testray run could not be found
	 */
	@Override
	public TestrayRun fetchByT_E_E(
		long testrayBuildId, String externalReferencePK,
		int externalReferenceType) {

		return fetchByT_E_E(
			testrayBuildId, externalReferencePK, externalReferenceType, true);
	}

	/**
	 * Returns the testray run where testrayBuildId = &#63; and externalReferencePK = &#63; and externalReferenceType = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param testrayBuildId the testray build ID
	 * @param externalReferencePK the external reference pk
	 * @param externalReferenceType the external reference type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching testray run, or <code>null</code> if a matching testray run could not be found
	 */
	@Override
	public TestrayRun fetchByT_E_E(
		long testrayBuildId, String externalReferencePK,
		int externalReferenceType, boolean useFinderCache) {

		externalReferencePK = Objects.toString(externalReferencePK, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				testrayBuildId, externalReferencePK, externalReferenceType
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByT_E_E, finderArgs, this);
		}

		if (result instanceof TestrayRun) {
			TestrayRun testrayRun = (TestrayRun)result;

			if ((testrayBuildId != testrayRun.getTestrayBuildId()) ||
				!Objects.equals(
					externalReferencePK, testrayRun.getExternalReferencePK()) ||
				(externalReferenceType !=
					testrayRun.getExternalReferenceType())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_TESTRAYRUN_WHERE);

			sb.append(_FINDER_COLUMN_T_E_E_TESTRAYBUILDID_2);

			boolean bindExternalReferencePK = false;

			if (externalReferencePK.isEmpty()) {
				sb.append(_FINDER_COLUMN_T_E_E_EXTERNALREFERENCEPK_3);
			}
			else {
				bindExternalReferencePK = true;

				sb.append(_FINDER_COLUMN_T_E_E_EXTERNALREFERENCEPK_2);
			}

			sb.append(_FINDER_COLUMN_T_E_E_EXTERNALREFERENCETYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(testrayBuildId);

				if (bindExternalReferencePK) {
					queryPos.add(externalReferencePK);
				}

				queryPos.add(externalReferenceType);

				List<TestrayRun> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByT_E_E, finderArgs, list);
					}
				}
				else {
					TestrayRun testrayRun = list.get(0);

					result = testrayRun;

					cacheResult(testrayRun);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByT_E_E, finderArgs);
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
			return (TestrayRun)result;
		}
	}

	/**
	 * Removes the testray run where testrayBuildId = &#63; and externalReferencePK = &#63; and externalReferenceType = &#63; from the database.
	 *
	 * @param testrayBuildId the testray build ID
	 * @param externalReferencePK the external reference pk
	 * @param externalReferenceType the external reference type
	 * @return the testray run that was removed
	 */
	@Override
	public TestrayRun removeByT_E_E(
			long testrayBuildId, String externalReferencePK,
			int externalReferenceType)
		throws NoSuchTestrayRunException {

		TestrayRun testrayRun = findByT_E_E(
			testrayBuildId, externalReferencePK, externalReferenceType);

		return remove(testrayRun);
	}

	/**
	 * Returns the number of testray runs where testrayBuildId = &#63; and externalReferencePK = &#63; and externalReferenceType = &#63;.
	 *
	 * @param testrayBuildId the testray build ID
	 * @param externalReferencePK the external reference pk
	 * @param externalReferenceType the external reference type
	 * @return the number of matching testray runs
	 */
	@Override
	public int countByT_E_E(
		long testrayBuildId, String externalReferencePK,
		int externalReferenceType) {

		externalReferencePK = Objects.toString(externalReferencePK, "");

		FinderPath finderPath = _finderPathCountByT_E_E;

		Object[] finderArgs = new Object[] {
			testrayBuildId, externalReferencePK, externalReferenceType
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_TESTRAYRUN_WHERE);

			sb.append(_FINDER_COLUMN_T_E_E_TESTRAYBUILDID_2);

			boolean bindExternalReferencePK = false;

			if (externalReferencePK.isEmpty()) {
				sb.append(_FINDER_COLUMN_T_E_E_EXTERNALREFERENCEPK_3);
			}
			else {
				bindExternalReferencePK = true;

				sb.append(_FINDER_COLUMN_T_E_E_EXTERNALREFERENCEPK_2);
			}

			sb.append(_FINDER_COLUMN_T_E_E_EXTERNALREFERENCETYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(testrayBuildId);

				if (bindExternalReferencePK) {
					queryPos.add(externalReferencePK);
				}

				queryPos.add(externalReferenceType);

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

	private static final String _FINDER_COLUMN_T_E_E_TESTRAYBUILDID_2 =
		"testrayRun.testrayBuildId = ? AND ";

	private static final String _FINDER_COLUMN_T_E_E_EXTERNALREFERENCEPK_2 =
		"testrayRun.externalReferencePK = ? AND ";

	private static final String _FINDER_COLUMN_T_E_E_EXTERNALREFERENCEPK_3 =
		"(testrayRun.externalReferencePK IS NULL OR testrayRun.externalReferencePK = '') AND ";

	private static final String _FINDER_COLUMN_T_E_E_EXTERNALREFERENCETYPE_2 =
		"testrayRun.externalReferenceType = ?";

	public TestrayRunPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("number", "number_");

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
				"_dbColumnNames");

			field.setAccessible(true);

			field.set(this, dbColumnNames);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		setModelClass(TestrayRun.class);
	}

	/**
	 * Caches the testray run in the entity cache if it is enabled.
	 *
	 * @param testrayRun the testray run
	 */
	@Override
	public void cacheResult(TestrayRun testrayRun) {
		entityCache.putResult(
			TestrayRunModelImpl.ENTITY_CACHE_ENABLED, TestrayRunImpl.class,
			testrayRun.getPrimaryKey(), testrayRun);

		finderCache.putResult(
			_finderPathFetchByT_E_E,
			new Object[] {
				testrayRun.getTestrayBuildId(),
				testrayRun.getExternalReferencePK(),
				testrayRun.getExternalReferenceType()
			},
			testrayRun);

		testrayRun.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the testray runs in the entity cache if it is enabled.
	 *
	 * @param testrayRuns the testray runs
	 */
	@Override
	public void cacheResult(List<TestrayRun> testrayRuns) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (testrayRuns.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (TestrayRun testrayRun : testrayRuns) {
			if (entityCache.getResult(
					TestrayRunModelImpl.ENTITY_CACHE_ENABLED,
					TestrayRunImpl.class, testrayRun.getPrimaryKey()) == null) {

				cacheResult(testrayRun);
			}
			else {
				testrayRun.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all testray runs.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(TestrayRunImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the testray run.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(TestrayRun testrayRun) {
		entityCache.removeResult(
			TestrayRunModelImpl.ENTITY_CACHE_ENABLED, TestrayRunImpl.class,
			testrayRun.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((TestrayRunModelImpl)testrayRun, true);
	}

	@Override
	public void clearCache(List<TestrayRun> testrayRuns) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (TestrayRun testrayRun : testrayRuns) {
			entityCache.removeResult(
				TestrayRunModelImpl.ENTITY_CACHE_ENABLED, TestrayRunImpl.class,
				testrayRun.getPrimaryKey());

			clearUniqueFindersCache((TestrayRunModelImpl)testrayRun, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				TestrayRunModelImpl.ENTITY_CACHE_ENABLED, TestrayRunImpl.class,
				primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		TestrayRunModelImpl testrayRunModelImpl) {

		Object[] args = new Object[] {
			testrayRunModelImpl.getTestrayBuildId(),
			testrayRunModelImpl.getExternalReferencePK(),
			testrayRunModelImpl.getExternalReferenceType()
		};

		finderCache.putResult(
			_finderPathCountByT_E_E, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByT_E_E, args, testrayRunModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		TestrayRunModelImpl testrayRunModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				testrayRunModelImpl.getTestrayBuildId(),
				testrayRunModelImpl.getExternalReferencePK(),
				testrayRunModelImpl.getExternalReferenceType()
			};

			finderCache.removeResult(_finderPathCountByT_E_E, args);
			finderCache.removeResult(_finderPathFetchByT_E_E, args);
		}

		if ((testrayRunModelImpl.getColumnBitmask() &
			 _finderPathFetchByT_E_E.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				testrayRunModelImpl.getOriginalTestrayBuildId(),
				testrayRunModelImpl.getOriginalExternalReferencePK(),
				testrayRunModelImpl.getOriginalExternalReferenceType()
			};

			finderCache.removeResult(_finderPathCountByT_E_E, args);
			finderCache.removeResult(_finderPathFetchByT_E_E, args);
		}
	}

	/**
	 * Creates a new testray run with the primary key. Does not add the testray run to the database.
	 *
	 * @param testrayRunId the primary key for the new testray run
	 * @return the new testray run
	 */
	@Override
	public TestrayRun create(long testrayRunId) {
		TestrayRun testrayRun = new TestrayRunImpl();

		testrayRun.setNew(true);
		testrayRun.setPrimaryKey(testrayRunId);

		testrayRun.setCompanyId(CompanyThreadLocal.getCompanyId());

		return testrayRun;
	}

	/**
	 * Removes the testray run with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param testrayRunId the primary key of the testray run
	 * @return the testray run that was removed
	 * @throws NoSuchTestrayRunException if a testray run with the primary key could not be found
	 */
	@Override
	public TestrayRun remove(long testrayRunId)
		throws NoSuchTestrayRunException {

		return remove((Serializable)testrayRunId);
	}

	/**
	 * Removes the testray run with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the testray run
	 * @return the testray run that was removed
	 * @throws NoSuchTestrayRunException if a testray run with the primary key could not be found
	 */
	@Override
	public TestrayRun remove(Serializable primaryKey)
		throws NoSuchTestrayRunException {

		Session session = null;

		try {
			session = openSession();

			TestrayRun testrayRun = (TestrayRun)session.get(
				TestrayRunImpl.class, primaryKey);

			if (testrayRun == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTestrayRunException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(testrayRun);
		}
		catch (NoSuchTestrayRunException noSuchEntityException) {
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
	protected TestrayRun removeImpl(TestrayRun testrayRun) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(testrayRun)) {
				testrayRun = (TestrayRun)session.get(
					TestrayRunImpl.class, testrayRun.getPrimaryKeyObj());
			}

			if (testrayRun != null) {
				session.delete(testrayRun);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (testrayRun != null) {
			clearCache(testrayRun);
		}

		return testrayRun;
	}

	@Override
	public TestrayRun updateImpl(TestrayRun testrayRun) {
		boolean isNew = testrayRun.isNew();

		if (!(testrayRun instanceof TestrayRunModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(testrayRun.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(testrayRun);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in testrayRun proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom TestrayRun implementation " +
					testrayRun.getClass());
		}

		TestrayRunModelImpl testrayRunModelImpl =
			(TestrayRunModelImpl)testrayRun;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (testrayRun.getCreateDate() == null)) {
			if (serviceContext == null) {
				testrayRun.setCreateDate(date);
			}
			else {
				testrayRun.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!testrayRunModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				testrayRun.setModifiedDate(date);
			}
			else {
				testrayRun.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(testrayRun);

				testrayRun.setNew(false);
			}
			else {
				testrayRun = (TestrayRun)session.merge(testrayRun);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!TestrayRunModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			TestrayRunModelImpl.ENTITY_CACHE_ENABLED, TestrayRunImpl.class,
			testrayRun.getPrimaryKey(), testrayRun, false);

		clearUniqueFindersCache(testrayRunModelImpl, false);
		cacheUniqueFindersCache(testrayRunModelImpl);

		testrayRun.resetOriginalValues();

		return testrayRun;
	}

	/**
	 * Returns the testray run with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray run
	 * @return the testray run
	 * @throws NoSuchTestrayRunException if a testray run with the primary key could not be found
	 */
	@Override
	public TestrayRun findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTestrayRunException {

		TestrayRun testrayRun = fetchByPrimaryKey(primaryKey);

		if (testrayRun == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTestrayRunException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return testrayRun;
	}

	/**
	 * Returns the testray run with the primary key or throws a <code>NoSuchTestrayRunException</code> if it could not be found.
	 *
	 * @param testrayRunId the primary key of the testray run
	 * @return the testray run
	 * @throws NoSuchTestrayRunException if a testray run with the primary key could not be found
	 */
	@Override
	public TestrayRun findByPrimaryKey(long testrayRunId)
		throws NoSuchTestrayRunException {

		return findByPrimaryKey((Serializable)testrayRunId);
	}

	/**
	 * Returns the testray run with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray run
	 * @return the testray run, or <code>null</code> if a testray run with the primary key could not be found
	 */
	@Override
	public TestrayRun fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			TestrayRunModelImpl.ENTITY_CACHE_ENABLED, TestrayRunImpl.class,
			primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		TestrayRun testrayRun = (TestrayRun)serializable;

		if (testrayRun == null) {
			Session session = null;

			try {
				session = openSession();

				testrayRun = (TestrayRun)session.get(
					TestrayRunImpl.class, primaryKey);

				if (testrayRun != null) {
					cacheResult(testrayRun);
				}
				else {
					entityCache.putResult(
						TestrayRunModelImpl.ENTITY_CACHE_ENABLED,
						TestrayRunImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					TestrayRunModelImpl.ENTITY_CACHE_ENABLED,
					TestrayRunImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return testrayRun;
	}

	/**
	 * Returns the testray run with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param testrayRunId the primary key of the testray run
	 * @return the testray run, or <code>null</code> if a testray run with the primary key could not be found
	 */
	@Override
	public TestrayRun fetchByPrimaryKey(long testrayRunId) {
		return fetchByPrimaryKey((Serializable)testrayRunId);
	}

	@Override
	public Map<Serializable, TestrayRun> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, TestrayRun> map =
			new HashMap<Serializable, TestrayRun>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			TestrayRun testrayRun = fetchByPrimaryKey(primaryKey);

			if (testrayRun != null) {
				map.put(primaryKey, testrayRun);
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
				TestrayRunModelImpl.ENTITY_CACHE_ENABLED, TestrayRunImpl.class,
				primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (TestrayRun)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_TESTRAYRUN_WHERE_PKS_IN);

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

			for (TestrayRun testrayRun : (List<TestrayRun>)query.list()) {
				map.put(testrayRun.getPrimaryKeyObj(), testrayRun);

				cacheResult(testrayRun);

				uncachedPrimaryKeys.remove(testrayRun.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					TestrayRunModelImpl.ENTITY_CACHE_ENABLED,
					TestrayRunImpl.class, primaryKey, nullModel);
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
	 * Returns all the testray runs.
	 *
	 * @return the testray runs
	 */
	@Override
	public List<TestrayRun> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the testray runs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayRunModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray runs
	 * @param end the upper bound of the range of testray runs (not inclusive)
	 * @return the range of testray runs
	 */
	@Override
	public List<TestrayRun> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray runs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayRunModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray runs
	 * @param end the upper bound of the range of testray runs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray runs
	 */
	@Override
	public List<TestrayRun> findAll(
		int start, int end, OrderByComparator<TestrayRun> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the testray runs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayRunModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray runs
	 * @param end the upper bound of the range of testray runs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of testray runs
	 */
	@Override
	public List<TestrayRun> findAll(
		int start, int end, OrderByComparator<TestrayRun> orderByComparator,
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

		List<TestrayRun> list = null;

		if (useFinderCache) {
			list = (List<TestrayRun>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_TESTRAYRUN);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_TESTRAYRUN;

				sql = sql.concat(TestrayRunModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<TestrayRun>)QueryUtil.list(
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
	 * Removes all the testray runs from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (TestrayRun testrayRun : findAll()) {
			remove(testrayRun);
		}
	}

	/**
	 * Returns the number of testray runs.
	 *
	 * @return the number of testray runs
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_TESTRAYRUN);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return TestrayRunModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the testray run persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			TestrayRunModelImpl.ENTITY_CACHE_ENABLED,
			TestrayRunModelImpl.FINDER_CACHE_ENABLED, TestrayRunImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			TestrayRunModelImpl.ENTITY_CACHE_ENABLED,
			TestrayRunModelImpl.FINDER_CACHE_ENABLED, TestrayRunImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			TestrayRunModelImpl.ENTITY_CACHE_ENABLED,
			TestrayRunModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByT_E_E = new FinderPath(
			TestrayRunModelImpl.ENTITY_CACHE_ENABLED,
			TestrayRunModelImpl.FINDER_CACHE_ENABLED, TestrayRunImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByT_E_E",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			TestrayRunModelImpl.TESTRAYBUILDID_COLUMN_BITMASK |
			TestrayRunModelImpl.EXTERNALREFERENCEPK_COLUMN_BITMASK |
			TestrayRunModelImpl.EXTERNALREFERENCETYPE_COLUMN_BITMASK);

		_finderPathCountByT_E_E = new FinderPath(
			TestrayRunModelImpl.ENTITY_CACHE_ENABLED,
			TestrayRunModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_E_E",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});

		TestrayRunUtil.setPersistence(this);
	}

	public void destroy() {
		TestrayRunUtil.setPersistence(null);

		entityCache.removeCache(TestrayRunImpl.class.getName());

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

	private static final String _SQL_SELECT_TESTRAYRUN =
		"SELECT testrayRun FROM TestrayRun testrayRun";

	private static final String _SQL_SELECT_TESTRAYRUN_WHERE_PKS_IN =
		"SELECT testrayRun FROM TestrayRun testrayRun WHERE testrayRunId IN (";

	private static final String _SQL_SELECT_TESTRAYRUN_WHERE =
		"SELECT testrayRun FROM TestrayRun testrayRun WHERE ";

	private static final String _SQL_COUNT_TESTRAYRUN =
		"SELECT COUNT(testrayRun) FROM TestrayRun testrayRun";

	private static final String _SQL_COUNT_TESTRAYRUN_WHERE =
		"SELECT COUNT(testrayRun) FROM TestrayRun testrayRun WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "testrayRun.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No TestrayRun exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No TestrayRun exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		TestrayRunPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"number"});

}