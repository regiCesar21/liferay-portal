/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.testray.service.persistence.impl;

import com.liferay.osb.testray.exception.NoSuchTestrayComponentException;
import com.liferay.osb.testray.model.TestrayComponent;
import com.liferay.osb.testray.model.impl.TestrayComponentImpl;
import com.liferay.osb.testray.model.impl.TestrayComponentModelImpl;
import com.liferay.osb.testray.service.persistence.TestrayCasePersistence;
import com.liferay.osb.testray.service.persistence.TestrayComponentPersistence;
import com.liferay.osb.testray.service.persistence.TestrayComponentUtil;
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
 * The persistence implementation for the testray component service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ethan Bustad
 * @generated
 */
public class TestrayComponentPersistenceImpl
	extends BasePersistenceImpl<TestrayComponent>
	implements TestrayComponentPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>TestrayComponentUtil</code> to access the testray component persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		TestrayComponentImpl.class.getName();

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
	 * Returns the testray component where testrayProjectId = &#63; and name = &#63; or throws a <code>NoSuchTestrayComponentException</code> if it could not be found.
	 *
	 * @param testrayProjectId the testray project ID
	 * @param name the name
	 * @return the matching testray component
	 * @throws NoSuchTestrayComponentException if a matching testray component could not be found
	 */
	@Override
	public TestrayComponent findByTPI_N(long testrayProjectId, String name)
		throws NoSuchTestrayComponentException {

		TestrayComponent testrayComponent = fetchByTPI_N(
			testrayProjectId, name);

		if (testrayComponent == null) {
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

			throw new NoSuchTestrayComponentException(sb.toString());
		}

		return testrayComponent;
	}

	/**
	 * Returns the testray component where testrayProjectId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param testrayProjectId the testray project ID
	 * @param name the name
	 * @return the matching testray component, or <code>null</code> if a matching testray component could not be found
	 */
	@Override
	public TestrayComponent fetchByTPI_N(long testrayProjectId, String name) {
		return fetchByTPI_N(testrayProjectId, name, true);
	}

	/**
	 * Returns the testray component where testrayProjectId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param testrayProjectId the testray project ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching testray component, or <code>null</code> if a matching testray component could not be found
	 */
	@Override
	public TestrayComponent fetchByTPI_N(
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

		if (result instanceof TestrayComponent) {
			TestrayComponent testrayComponent = (TestrayComponent)result;

			if ((testrayProjectId != testrayComponent.getTestrayProjectId()) ||
				!Objects.equals(name, testrayComponent.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_TESTRAYCOMPONENT_WHERE);

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

				List<TestrayComponent> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByTPI_N, finderArgs, list);
					}
				}
				else {
					TestrayComponent testrayComponent = list.get(0);

					result = testrayComponent;

					cacheResult(testrayComponent);
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
			return (TestrayComponent)result;
		}
	}

	/**
	 * Removes the testray component where testrayProjectId = &#63; and name = &#63; from the database.
	 *
	 * @param testrayProjectId the testray project ID
	 * @param name the name
	 * @return the testray component that was removed
	 */
	@Override
	public TestrayComponent removeByTPI_N(long testrayProjectId, String name)
		throws NoSuchTestrayComponentException {

		TestrayComponent testrayComponent = findByTPI_N(testrayProjectId, name);

		return remove(testrayComponent);
	}

	/**
	 * Returns the number of testray components where testrayProjectId = &#63; and name = &#63;.
	 *
	 * @param testrayProjectId the testray project ID
	 * @param name the name
	 * @return the number of matching testray components
	 */
	@Override
	public int countByTPI_N(long testrayProjectId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByTPI_N;

		Object[] finderArgs = new Object[] {testrayProjectId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_TESTRAYCOMPONENT_WHERE);

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
		"testrayComponent.testrayProjectId = ? AND ";

	private static final String _FINDER_COLUMN_TPI_N_NAME_2 =
		"testrayComponent.name = ?";

	private static final String _FINDER_COLUMN_TPI_N_NAME_3 =
		"(testrayComponent.name IS NULL OR testrayComponent.name = '')";

	public TestrayComponentPersistenceImpl() {
		setModelClass(TestrayComponent.class);
	}

	/**
	 * Caches the testray component in the entity cache if it is enabled.
	 *
	 * @param testrayComponent the testray component
	 */
	@Override
	public void cacheResult(TestrayComponent testrayComponent) {
		entityCache.putResult(
			TestrayComponentModelImpl.ENTITY_CACHE_ENABLED,
			TestrayComponentImpl.class, testrayComponent.getPrimaryKey(),
			testrayComponent);

		finderCache.putResult(
			_finderPathFetchByTPI_N,
			new Object[] {
				testrayComponent.getTestrayProjectId(),
				testrayComponent.getName()
			},
			testrayComponent);

		testrayComponent.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the testray components in the entity cache if it is enabled.
	 *
	 * @param testrayComponents the testray components
	 */
	@Override
	public void cacheResult(List<TestrayComponent> testrayComponents) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (testrayComponents.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (TestrayComponent testrayComponent : testrayComponents) {
			if (entityCache.getResult(
					TestrayComponentModelImpl.ENTITY_CACHE_ENABLED,
					TestrayComponentImpl.class,
					testrayComponent.getPrimaryKey()) == null) {

				cacheResult(testrayComponent);
			}
			else {
				testrayComponent.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all testray components.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(TestrayComponentImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the testray component.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(TestrayComponent testrayComponent) {
		entityCache.removeResult(
			TestrayComponentModelImpl.ENTITY_CACHE_ENABLED,
			TestrayComponentImpl.class, testrayComponent.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(TestrayComponentModelImpl)testrayComponent, true);
	}

	@Override
	public void clearCache(List<TestrayComponent> testrayComponents) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (TestrayComponent testrayComponent : testrayComponents) {
			entityCache.removeResult(
				TestrayComponentModelImpl.ENTITY_CACHE_ENABLED,
				TestrayComponentImpl.class, testrayComponent.getPrimaryKey());

			clearUniqueFindersCache(
				(TestrayComponentModelImpl)testrayComponent, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				TestrayComponentModelImpl.ENTITY_CACHE_ENABLED,
				TestrayComponentImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		TestrayComponentModelImpl testrayComponentModelImpl) {

		Object[] args = new Object[] {
			testrayComponentModelImpl.getTestrayProjectId(),
			testrayComponentModelImpl.getName()
		};

		finderCache.putResult(
			_finderPathCountByTPI_N, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByTPI_N, args, testrayComponentModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		TestrayComponentModelImpl testrayComponentModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				testrayComponentModelImpl.getTestrayProjectId(),
				testrayComponentModelImpl.getName()
			};

			finderCache.removeResult(_finderPathCountByTPI_N, args);
			finderCache.removeResult(_finderPathFetchByTPI_N, args);
		}

		if ((testrayComponentModelImpl.getColumnBitmask() &
			 _finderPathFetchByTPI_N.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				testrayComponentModelImpl.getOriginalTestrayProjectId(),
				testrayComponentModelImpl.getOriginalName()
			};

			finderCache.removeResult(_finderPathCountByTPI_N, args);
			finderCache.removeResult(_finderPathFetchByTPI_N, args);
		}
	}

	/**
	 * Creates a new testray component with the primary key. Does not add the testray component to the database.
	 *
	 * @param testrayComponentId the primary key for the new testray component
	 * @return the new testray component
	 */
	@Override
	public TestrayComponent create(long testrayComponentId) {
		TestrayComponent testrayComponent = new TestrayComponentImpl();

		testrayComponent.setNew(true);
		testrayComponent.setPrimaryKey(testrayComponentId);

		testrayComponent.setCompanyId(CompanyThreadLocal.getCompanyId());

		return testrayComponent;
	}

	/**
	 * Removes the testray component with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param testrayComponentId the primary key of the testray component
	 * @return the testray component that was removed
	 * @throws NoSuchTestrayComponentException if a testray component with the primary key could not be found
	 */
	@Override
	public TestrayComponent remove(long testrayComponentId)
		throws NoSuchTestrayComponentException {

		return remove((Serializable)testrayComponentId);
	}

	/**
	 * Removes the testray component with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the testray component
	 * @return the testray component that was removed
	 * @throws NoSuchTestrayComponentException if a testray component with the primary key could not be found
	 */
	@Override
	public TestrayComponent remove(Serializable primaryKey)
		throws NoSuchTestrayComponentException {

		Session session = null;

		try {
			session = openSession();

			TestrayComponent testrayComponent = (TestrayComponent)session.get(
				TestrayComponentImpl.class, primaryKey);

			if (testrayComponent == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTestrayComponentException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(testrayComponent);
		}
		catch (NoSuchTestrayComponentException noSuchEntityException) {
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
	protected TestrayComponent removeImpl(TestrayComponent testrayComponent) {
		testrayComponentToTestrayCaseTableMapper.
			deleteLeftPrimaryKeyTableMappings(testrayComponent.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(testrayComponent)) {
				testrayComponent = (TestrayComponent)session.get(
					TestrayComponentImpl.class,
					testrayComponent.getPrimaryKeyObj());
			}

			if (testrayComponent != null) {
				session.delete(testrayComponent);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (testrayComponent != null) {
			clearCache(testrayComponent);
		}

		return testrayComponent;
	}

	@Override
	public TestrayComponent updateImpl(TestrayComponent testrayComponent) {
		boolean isNew = testrayComponent.isNew();

		if (!(testrayComponent instanceof TestrayComponentModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(testrayComponent.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					testrayComponent);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in testrayComponent proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom TestrayComponent implementation " +
					testrayComponent.getClass());
		}

		TestrayComponentModelImpl testrayComponentModelImpl =
			(TestrayComponentModelImpl)testrayComponent;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (testrayComponent.getCreateDate() == null)) {
			if (serviceContext == null) {
				testrayComponent.setCreateDate(date);
			}
			else {
				testrayComponent.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!testrayComponentModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				testrayComponent.setModifiedDate(date);
			}
			else {
				testrayComponent.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(testrayComponent);

				testrayComponent.setNew(false);
			}
			else {
				testrayComponent = (TestrayComponent)session.merge(
					testrayComponent);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!TestrayComponentModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			TestrayComponentModelImpl.ENTITY_CACHE_ENABLED,
			TestrayComponentImpl.class, testrayComponent.getPrimaryKey(),
			testrayComponent, false);

		clearUniqueFindersCache(testrayComponentModelImpl, false);
		cacheUniqueFindersCache(testrayComponentModelImpl);

		testrayComponent.resetOriginalValues();

		return testrayComponent;
	}

	/**
	 * Returns the testray component with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray component
	 * @return the testray component
	 * @throws NoSuchTestrayComponentException if a testray component with the primary key could not be found
	 */
	@Override
	public TestrayComponent findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTestrayComponentException {

		TestrayComponent testrayComponent = fetchByPrimaryKey(primaryKey);

		if (testrayComponent == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTestrayComponentException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return testrayComponent;
	}

	/**
	 * Returns the testray component with the primary key or throws a <code>NoSuchTestrayComponentException</code> if it could not be found.
	 *
	 * @param testrayComponentId the primary key of the testray component
	 * @return the testray component
	 * @throws NoSuchTestrayComponentException if a testray component with the primary key could not be found
	 */
	@Override
	public TestrayComponent findByPrimaryKey(long testrayComponentId)
		throws NoSuchTestrayComponentException {

		return findByPrimaryKey((Serializable)testrayComponentId);
	}

	/**
	 * Returns the testray component with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray component
	 * @return the testray component, or <code>null</code> if a testray component with the primary key could not be found
	 */
	@Override
	public TestrayComponent fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			TestrayComponentModelImpl.ENTITY_CACHE_ENABLED,
			TestrayComponentImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		TestrayComponent testrayComponent = (TestrayComponent)serializable;

		if (testrayComponent == null) {
			Session session = null;

			try {
				session = openSession();

				testrayComponent = (TestrayComponent)session.get(
					TestrayComponentImpl.class, primaryKey);

				if (testrayComponent != null) {
					cacheResult(testrayComponent);
				}
				else {
					entityCache.putResult(
						TestrayComponentModelImpl.ENTITY_CACHE_ENABLED,
						TestrayComponentImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					TestrayComponentModelImpl.ENTITY_CACHE_ENABLED,
					TestrayComponentImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return testrayComponent;
	}

	/**
	 * Returns the testray component with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param testrayComponentId the primary key of the testray component
	 * @return the testray component, or <code>null</code> if a testray component with the primary key could not be found
	 */
	@Override
	public TestrayComponent fetchByPrimaryKey(long testrayComponentId) {
		return fetchByPrimaryKey((Serializable)testrayComponentId);
	}

	@Override
	public Map<Serializable, TestrayComponent> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, TestrayComponent> map =
			new HashMap<Serializable, TestrayComponent>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			TestrayComponent testrayComponent = fetchByPrimaryKey(primaryKey);

			if (testrayComponent != null) {
				map.put(primaryKey, testrayComponent);
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
				TestrayComponentModelImpl.ENTITY_CACHE_ENABLED,
				TestrayComponentImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (TestrayComponent)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_TESTRAYCOMPONENT_WHERE_PKS_IN);

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

			for (TestrayComponent testrayComponent :
					(List<TestrayComponent>)query.list()) {

				map.put(testrayComponent.getPrimaryKeyObj(), testrayComponent);

				cacheResult(testrayComponent);

				uncachedPrimaryKeys.remove(testrayComponent.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					TestrayComponentModelImpl.ENTITY_CACHE_ENABLED,
					TestrayComponentImpl.class, primaryKey, nullModel);
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
	 * Returns all the testray components.
	 *
	 * @return the testray components
	 */
	@Override
	public List<TestrayComponent> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the testray components.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayComponentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray components
	 * @param end the upper bound of the range of testray components (not inclusive)
	 * @return the range of testray components
	 */
	@Override
	public List<TestrayComponent> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray components.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayComponentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray components
	 * @param end the upper bound of the range of testray components (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray components
	 */
	@Override
	public List<TestrayComponent> findAll(
		int start, int end,
		OrderByComparator<TestrayComponent> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the testray components.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayComponentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray components
	 * @param end the upper bound of the range of testray components (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of testray components
	 */
	@Override
	public List<TestrayComponent> findAll(
		int start, int end,
		OrderByComparator<TestrayComponent> orderByComparator,
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

		List<TestrayComponent> list = null;

		if (useFinderCache) {
			list = (List<TestrayComponent>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_TESTRAYCOMPONENT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_TESTRAYCOMPONENT;

				sql = sql.concat(TestrayComponentModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<TestrayComponent>)QueryUtil.list(
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
	 * Removes all the testray components from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (TestrayComponent testrayComponent : findAll()) {
			remove(testrayComponent);
		}
	}

	/**
	 * Returns the number of testray components.
	 *
	 * @return the number of testray components
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_TESTRAYCOMPONENT);

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
	 * Returns the primaryKeys of testray cases associated with the testray component.
	 *
	 * @param pk the primary key of the testray component
	 * @return long[] of the primaryKeys of testray cases associated with the testray component
	 */
	@Override
	public long[] getTestrayCasePrimaryKeys(long pk) {
		long[] pks =
			testrayComponentToTestrayCaseTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the testray cases associated with the testray component.
	 *
	 * @param pk the primary key of the testray component
	 * @return the testray cases associated with the testray component
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayCase> getTestrayCases(
		long pk) {

		return getTestrayCases(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the testray cases associated with the testray component.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayComponentModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray component
	 * @param start the lower bound of the range of testray components
	 * @param end the upper bound of the range of testray components (not inclusive)
	 * @return the range of testray cases associated with the testray component
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayCase> getTestrayCases(
		long pk, int start, int end) {

		return getTestrayCases(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray cases associated with the testray component.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayComponentModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray component
	 * @param start the lower bound of the range of testray components
	 * @param end the upper bound of the range of testray components (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray cases associated with the testray component
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayCase> getTestrayCases(
		long pk, int start, int end,
		OrderByComparator<com.liferay.osb.testray.model.TestrayCase>
			orderByComparator) {

		return testrayComponentToTestrayCaseTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of testray cases associated with the testray component.
	 *
	 * @param pk the primary key of the testray component
	 * @return the number of testray cases associated with the testray component
	 */
	@Override
	public int getTestrayCasesSize(long pk) {
		long[] pks =
			testrayComponentToTestrayCaseTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the testray case is associated with the testray component.
	 *
	 * @param pk the primary key of the testray component
	 * @param testrayCasePK the primary key of the testray case
	 * @return <code>true</code> if the testray case is associated with the testray component; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTestrayCase(long pk, long testrayCasePK) {
		return testrayComponentToTestrayCaseTableMapper.containsTableMapping(
			pk, testrayCasePK);
	}

	/**
	 * Returns <code>true</code> if the testray component has any testray cases associated with it.
	 *
	 * @param pk the primary key of the testray component to check for associations with testray cases
	 * @return <code>true</code> if the testray component has any testray cases associated with it; <code>false</code> otherwise
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
	 * Adds an association between the testray component and the testray case. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray component
	 * @param testrayCasePK the primary key of the testray case
	 */
	@Override
	public void addTestrayCase(long pk, long testrayCasePK) {
		TestrayComponent testrayComponent = fetchByPrimaryKey(pk);

		if (testrayComponent == null) {
			testrayComponentToTestrayCaseTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, testrayCasePK);
		}
		else {
			testrayComponentToTestrayCaseTableMapper.addTableMapping(
				testrayComponent.getCompanyId(), pk, testrayCasePK);
		}
	}

	/**
	 * Adds an association between the testray component and the testray case. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray component
	 * @param testrayCase the testray case
	 */
	@Override
	public void addTestrayCase(
		long pk, com.liferay.osb.testray.model.TestrayCase testrayCase) {

		TestrayComponent testrayComponent = fetchByPrimaryKey(pk);

		if (testrayComponent == null) {
			testrayComponentToTestrayCaseTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				testrayCase.getPrimaryKey());
		}
		else {
			testrayComponentToTestrayCaseTableMapper.addTableMapping(
				testrayComponent.getCompanyId(), pk,
				testrayCase.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the testray component and the testray cases. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray component
	 * @param testrayCasePKs the primary keys of the testray cases
	 */
	@Override
	public void addTestrayCases(long pk, long[] testrayCasePKs) {
		long companyId = 0;

		TestrayComponent testrayComponent = fetchByPrimaryKey(pk);

		if (testrayComponent == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testrayComponent.getCompanyId();
		}

		testrayComponentToTestrayCaseTableMapper.addTableMappings(
			companyId, pk, testrayCasePKs);
	}

	/**
	 * Adds an association between the testray component and the testray cases. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray component
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
	 * Clears all associations between the testray component and its testray cases. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray component to clear the associated testray cases from
	 */
	@Override
	public void clearTestrayCases(long pk) {
		testrayComponentToTestrayCaseTableMapper.
			deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the testray component and the testray case. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray component
	 * @param testrayCasePK the primary key of the testray case
	 */
	@Override
	public void removeTestrayCase(long pk, long testrayCasePK) {
		testrayComponentToTestrayCaseTableMapper.deleteTableMapping(
			pk, testrayCasePK);
	}

	/**
	 * Removes the association between the testray component and the testray case. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray component
	 * @param testrayCase the testray case
	 */
	@Override
	public void removeTestrayCase(
		long pk, com.liferay.osb.testray.model.TestrayCase testrayCase) {

		testrayComponentToTestrayCaseTableMapper.deleteTableMapping(
			pk, testrayCase.getPrimaryKey());
	}

	/**
	 * Removes the association between the testray component and the testray cases. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray component
	 * @param testrayCasePKs the primary keys of the testray cases
	 */
	@Override
	public void removeTestrayCases(long pk, long[] testrayCasePKs) {
		testrayComponentToTestrayCaseTableMapper.deleteTableMappings(
			pk, testrayCasePKs);
	}

	/**
	 * Removes the association between the testray component and the testray cases. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray component
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
	 * Sets the testray cases associated with the testray component, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray component
	 * @param testrayCasePKs the primary keys of the testray cases to be associated with the testray component
	 */
	@Override
	public void setTestrayCases(long pk, long[] testrayCasePKs) {
		Set<Long> newTestrayCasePKsSet = SetUtil.fromArray(testrayCasePKs);
		Set<Long> oldTestrayCasePKsSet = SetUtil.fromArray(
			testrayComponentToTestrayCaseTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeTestrayCasePKsSet = new HashSet<Long>(
			oldTestrayCasePKsSet);

		removeTestrayCasePKsSet.removeAll(newTestrayCasePKsSet);

		testrayComponentToTestrayCaseTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeTestrayCasePKsSet));

		newTestrayCasePKsSet.removeAll(oldTestrayCasePKsSet);

		long companyId = 0;

		TestrayComponent testrayComponent = fetchByPrimaryKey(pk);

		if (testrayComponent == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testrayComponent.getCompanyId();
		}

		testrayComponentToTestrayCaseTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newTestrayCasePKsSet));
	}

	/**
	 * Sets the testray cases associated with the testray component, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray component
	 * @param testrayCases the testray cases to be associated with the testray component
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
		return TestrayComponentModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the testray component persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		testrayComponentToTestrayCaseTableMapper =
			TableMapperFactory.getTableMapper(
				"OSB_TestrayCases_TestrayComponents", "companyId",
				"testrayComponentId", "testrayCaseId", this,
				testrayCasePersistence);

		_finderPathWithPaginationFindAll = new FinderPath(
			TestrayComponentModelImpl.ENTITY_CACHE_ENABLED,
			TestrayComponentModelImpl.FINDER_CACHE_ENABLED,
			TestrayComponentImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			TestrayComponentModelImpl.ENTITY_CACHE_ENABLED,
			TestrayComponentModelImpl.FINDER_CACHE_ENABLED,
			TestrayComponentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			TestrayComponentModelImpl.ENTITY_CACHE_ENABLED,
			TestrayComponentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByTPI_N = new FinderPath(
			TestrayComponentModelImpl.ENTITY_CACHE_ENABLED,
			TestrayComponentModelImpl.FINDER_CACHE_ENABLED,
			TestrayComponentImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByTPI_N",
			new String[] {Long.class.getName(), String.class.getName()},
			TestrayComponentModelImpl.TESTRAYPROJECTID_COLUMN_BITMASK |
			TestrayComponentModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByTPI_N = new FinderPath(
			TestrayComponentModelImpl.ENTITY_CACHE_ENABLED,
			TestrayComponentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTPI_N",
			new String[] {Long.class.getName(), String.class.getName()});

		TestrayComponentUtil.setPersistence(this);
	}

	public void destroy() {
		TestrayComponentUtil.setPersistence(null);

		entityCache.removeCache(TestrayComponentImpl.class.getName());

		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper(
			"OSB_TestrayCases_TestrayComponents");
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
		<TestrayComponent, com.liferay.osb.testray.model.TestrayCase>
			testrayComponentToTestrayCaseTableMapper;

	private static final String _SQL_SELECT_TESTRAYCOMPONENT =
		"SELECT testrayComponent FROM TestrayComponent testrayComponent";

	private static final String _SQL_SELECT_TESTRAYCOMPONENT_WHERE_PKS_IN =
		"SELECT testrayComponent FROM TestrayComponent testrayComponent WHERE testrayComponentId IN (";

	private static final String _SQL_SELECT_TESTRAYCOMPONENT_WHERE =
		"SELECT testrayComponent FROM TestrayComponent testrayComponent WHERE ";

	private static final String _SQL_COUNT_TESTRAYCOMPONENT =
		"SELECT COUNT(testrayComponent) FROM TestrayComponent testrayComponent";

	private static final String _SQL_COUNT_TESTRAYCOMPONENT_WHERE =
		"SELECT COUNT(testrayComponent) FROM TestrayComponent testrayComponent WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "testrayComponent.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No TestrayComponent exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No TestrayComponent exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		TestrayComponentPersistenceImpl.class);

}