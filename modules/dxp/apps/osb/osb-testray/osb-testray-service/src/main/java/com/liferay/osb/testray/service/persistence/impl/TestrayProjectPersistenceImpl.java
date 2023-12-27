/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.testray.service.persistence.impl;

import com.liferay.osb.testray.exception.NoSuchTestrayProjectException;
import com.liferay.osb.testray.model.TestrayProject;
import com.liferay.osb.testray.model.impl.TestrayProjectImpl;
import com.liferay.osb.testray.model.impl.TestrayProjectModelImpl;
import com.liferay.osb.testray.service.persistence.TestrayProjectPersistence;
import com.liferay.osb.testray.service.persistence.TestrayProjectUtil;
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
 * The persistence implementation for the testray project service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ethan Bustad
 * @generated
 */
public class TestrayProjectPersistenceImpl
	extends BasePersistenceImpl<TestrayProject>
	implements TestrayProjectPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>TestrayProjectUtil</code> to access the testray project persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		TestrayProjectImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByGI_N;
	private FinderPath _finderPathCountByGI_N;

	/**
	 * Returns the testray project where groupId = &#63; and name = &#63; or throws a <code>NoSuchTestrayProjectException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching testray project
	 * @throws NoSuchTestrayProjectException if a matching testray project could not be found
	 */
	@Override
	public TestrayProject findByGI_N(long groupId, String name)
		throws NoSuchTestrayProjectException {

		TestrayProject testrayProject = fetchByGI_N(groupId, name);

		if (testrayProject == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchTestrayProjectException(sb.toString());
		}

		return testrayProject;
	}

	/**
	 * Returns the testray project where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching testray project, or <code>null</code> if a matching testray project could not be found
	 */
	@Override
	public TestrayProject fetchByGI_N(long groupId, String name) {
		return fetchByGI_N(groupId, name, true);
	}

	/**
	 * Returns the testray project where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching testray project, or <code>null</code> if a matching testray project could not be found
	 */
	@Override
	public TestrayProject fetchByGI_N(
		long groupId, String name, boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {groupId, name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByGI_N, finderArgs, this);
		}

		if (result instanceof TestrayProject) {
			TestrayProject testrayProject = (TestrayProject)result;

			if ((groupId != testrayProject.getGroupId()) ||
				!Objects.equals(name, testrayProject.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_TESTRAYPROJECT_WHERE);

			sb.append(_FINDER_COLUMN_GI_N_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_GI_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_GI_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindName) {
					queryPos.add(name);
				}

				List<TestrayProject> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByGI_N, finderArgs, list);
					}
				}
				else {
					TestrayProject testrayProject = list.get(0);

					result = testrayProject;

					cacheResult(testrayProject);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByGI_N, finderArgs);
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
			return (TestrayProject)result;
		}
	}

	/**
	 * Removes the testray project where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the testray project that was removed
	 */
	@Override
	public TestrayProject removeByGI_N(long groupId, String name)
		throws NoSuchTestrayProjectException {

		TestrayProject testrayProject = findByGI_N(groupId, name);

		return remove(testrayProject);
	}

	/**
	 * Returns the number of testray projects where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching testray projects
	 */
	@Override
	public int countByGI_N(long groupId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByGI_N;

		Object[] finderArgs = new Object[] {groupId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_TESTRAYPROJECT_WHERE);

			sb.append(_FINDER_COLUMN_GI_N_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_GI_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_GI_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

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

	private static final String _FINDER_COLUMN_GI_N_GROUPID_2 =
		"testrayProject.groupId = ? AND ";

	private static final String _FINDER_COLUMN_GI_N_NAME_2 =
		"testrayProject.name = ?";

	private static final String _FINDER_COLUMN_GI_N_NAME_3 =
		"(testrayProject.name IS NULL OR testrayProject.name = '')";

	public TestrayProjectPersistenceImpl() {
		setModelClass(TestrayProject.class);
	}

	/**
	 * Caches the testray project in the entity cache if it is enabled.
	 *
	 * @param testrayProject the testray project
	 */
	@Override
	public void cacheResult(TestrayProject testrayProject) {
		entityCache.putResult(
			TestrayProjectModelImpl.ENTITY_CACHE_ENABLED,
			TestrayProjectImpl.class, testrayProject.getPrimaryKey(),
			testrayProject);

		finderCache.putResult(
			_finderPathFetchByGI_N,
			new Object[] {
				testrayProject.getGroupId(), testrayProject.getName()
			},
			testrayProject);

		testrayProject.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the testray projects in the entity cache if it is enabled.
	 *
	 * @param testrayProjects the testray projects
	 */
	@Override
	public void cacheResult(List<TestrayProject> testrayProjects) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (testrayProjects.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (TestrayProject testrayProject : testrayProjects) {
			if (entityCache.getResult(
					TestrayProjectModelImpl.ENTITY_CACHE_ENABLED,
					TestrayProjectImpl.class, testrayProject.getPrimaryKey()) ==
						null) {

				cacheResult(testrayProject);
			}
			else {
				testrayProject.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all testray projects.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(TestrayProjectImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the testray project.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(TestrayProject testrayProject) {
		entityCache.removeResult(
			TestrayProjectModelImpl.ENTITY_CACHE_ENABLED,
			TestrayProjectImpl.class, testrayProject.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((TestrayProjectModelImpl)testrayProject, true);
	}

	@Override
	public void clearCache(List<TestrayProject> testrayProjects) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (TestrayProject testrayProject : testrayProjects) {
			entityCache.removeResult(
				TestrayProjectModelImpl.ENTITY_CACHE_ENABLED,
				TestrayProjectImpl.class, testrayProject.getPrimaryKey());

			clearUniqueFindersCache(
				(TestrayProjectModelImpl)testrayProject, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				TestrayProjectModelImpl.ENTITY_CACHE_ENABLED,
				TestrayProjectImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		TestrayProjectModelImpl testrayProjectModelImpl) {

		Object[] args = new Object[] {
			testrayProjectModelImpl.getGroupId(),
			testrayProjectModelImpl.getName()
		};

		finderCache.putResult(
			_finderPathCountByGI_N, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByGI_N, args, testrayProjectModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		TestrayProjectModelImpl testrayProjectModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				testrayProjectModelImpl.getGroupId(),
				testrayProjectModelImpl.getName()
			};

			finderCache.removeResult(_finderPathCountByGI_N, args);
			finderCache.removeResult(_finderPathFetchByGI_N, args);
		}

		if ((testrayProjectModelImpl.getColumnBitmask() &
			 _finderPathFetchByGI_N.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				testrayProjectModelImpl.getOriginalGroupId(),
				testrayProjectModelImpl.getOriginalName()
			};

			finderCache.removeResult(_finderPathCountByGI_N, args);
			finderCache.removeResult(_finderPathFetchByGI_N, args);
		}
	}

	/**
	 * Creates a new testray project with the primary key. Does not add the testray project to the database.
	 *
	 * @param testrayProjectId the primary key for the new testray project
	 * @return the new testray project
	 */
	@Override
	public TestrayProject create(long testrayProjectId) {
		TestrayProject testrayProject = new TestrayProjectImpl();

		testrayProject.setNew(true);
		testrayProject.setPrimaryKey(testrayProjectId);

		testrayProject.setCompanyId(CompanyThreadLocal.getCompanyId());

		return testrayProject;
	}

	/**
	 * Removes the testray project with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param testrayProjectId the primary key of the testray project
	 * @return the testray project that was removed
	 * @throws NoSuchTestrayProjectException if a testray project with the primary key could not be found
	 */
	@Override
	public TestrayProject remove(long testrayProjectId)
		throws NoSuchTestrayProjectException {

		return remove((Serializable)testrayProjectId);
	}

	/**
	 * Removes the testray project with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the testray project
	 * @return the testray project that was removed
	 * @throws NoSuchTestrayProjectException if a testray project with the primary key could not be found
	 */
	@Override
	public TestrayProject remove(Serializable primaryKey)
		throws NoSuchTestrayProjectException {

		Session session = null;

		try {
			session = openSession();

			TestrayProject testrayProject = (TestrayProject)session.get(
				TestrayProjectImpl.class, primaryKey);

			if (testrayProject == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTestrayProjectException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(testrayProject);
		}
		catch (NoSuchTestrayProjectException noSuchEntityException) {
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
	protected TestrayProject removeImpl(TestrayProject testrayProject) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(testrayProject)) {
				testrayProject = (TestrayProject)session.get(
					TestrayProjectImpl.class,
					testrayProject.getPrimaryKeyObj());
			}

			if (testrayProject != null) {
				session.delete(testrayProject);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (testrayProject != null) {
			clearCache(testrayProject);
		}

		return testrayProject;
	}

	@Override
	public TestrayProject updateImpl(TestrayProject testrayProject) {
		boolean isNew = testrayProject.isNew();

		if (!(testrayProject instanceof TestrayProjectModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(testrayProject.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					testrayProject);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in testrayProject proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom TestrayProject implementation " +
					testrayProject.getClass());
		}

		TestrayProjectModelImpl testrayProjectModelImpl =
			(TestrayProjectModelImpl)testrayProject;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (testrayProject.getCreateDate() == null)) {
			if (serviceContext == null) {
				testrayProject.setCreateDate(date);
			}
			else {
				testrayProject.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!testrayProjectModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				testrayProject.setModifiedDate(date);
			}
			else {
				testrayProject.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(testrayProject);

				testrayProject.setNew(false);
			}
			else {
				testrayProject = (TestrayProject)session.merge(testrayProject);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!TestrayProjectModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			TestrayProjectModelImpl.ENTITY_CACHE_ENABLED,
			TestrayProjectImpl.class, testrayProject.getPrimaryKey(),
			testrayProject, false);

		clearUniqueFindersCache(testrayProjectModelImpl, false);
		cacheUniqueFindersCache(testrayProjectModelImpl);

		testrayProject.resetOriginalValues();

		return testrayProject;
	}

	/**
	 * Returns the testray project with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray project
	 * @return the testray project
	 * @throws NoSuchTestrayProjectException if a testray project with the primary key could not be found
	 */
	@Override
	public TestrayProject findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTestrayProjectException {

		TestrayProject testrayProject = fetchByPrimaryKey(primaryKey);

		if (testrayProject == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTestrayProjectException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return testrayProject;
	}

	/**
	 * Returns the testray project with the primary key or throws a <code>NoSuchTestrayProjectException</code> if it could not be found.
	 *
	 * @param testrayProjectId the primary key of the testray project
	 * @return the testray project
	 * @throws NoSuchTestrayProjectException if a testray project with the primary key could not be found
	 */
	@Override
	public TestrayProject findByPrimaryKey(long testrayProjectId)
		throws NoSuchTestrayProjectException {

		return findByPrimaryKey((Serializable)testrayProjectId);
	}

	/**
	 * Returns the testray project with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray project
	 * @return the testray project, or <code>null</code> if a testray project with the primary key could not be found
	 */
	@Override
	public TestrayProject fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			TestrayProjectModelImpl.ENTITY_CACHE_ENABLED,
			TestrayProjectImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		TestrayProject testrayProject = (TestrayProject)serializable;

		if (testrayProject == null) {
			Session session = null;

			try {
				session = openSession();

				testrayProject = (TestrayProject)session.get(
					TestrayProjectImpl.class, primaryKey);

				if (testrayProject != null) {
					cacheResult(testrayProject);
				}
				else {
					entityCache.putResult(
						TestrayProjectModelImpl.ENTITY_CACHE_ENABLED,
						TestrayProjectImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					TestrayProjectModelImpl.ENTITY_CACHE_ENABLED,
					TestrayProjectImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return testrayProject;
	}

	/**
	 * Returns the testray project with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param testrayProjectId the primary key of the testray project
	 * @return the testray project, or <code>null</code> if a testray project with the primary key could not be found
	 */
	@Override
	public TestrayProject fetchByPrimaryKey(long testrayProjectId) {
		return fetchByPrimaryKey((Serializable)testrayProjectId);
	}

	@Override
	public Map<Serializable, TestrayProject> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, TestrayProject> map =
			new HashMap<Serializable, TestrayProject>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			TestrayProject testrayProject = fetchByPrimaryKey(primaryKey);

			if (testrayProject != null) {
				map.put(primaryKey, testrayProject);
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
				TestrayProjectModelImpl.ENTITY_CACHE_ENABLED,
				TestrayProjectImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (TestrayProject)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_TESTRAYPROJECT_WHERE_PKS_IN);

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

			for (TestrayProject testrayProject :
					(List<TestrayProject>)query.list()) {

				map.put(testrayProject.getPrimaryKeyObj(), testrayProject);

				cacheResult(testrayProject);

				uncachedPrimaryKeys.remove(testrayProject.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					TestrayProjectModelImpl.ENTITY_CACHE_ENABLED,
					TestrayProjectImpl.class, primaryKey, nullModel);
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
	 * Returns all the testray projects.
	 *
	 * @return the testray projects
	 */
	@Override
	public List<TestrayProject> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the testray projects.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayProjectModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray projects
	 * @param end the upper bound of the range of testray projects (not inclusive)
	 * @return the range of testray projects
	 */
	@Override
	public List<TestrayProject> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray projects.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayProjectModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray projects
	 * @param end the upper bound of the range of testray projects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray projects
	 */
	@Override
	public List<TestrayProject> findAll(
		int start, int end,
		OrderByComparator<TestrayProject> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the testray projects.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayProjectModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray projects
	 * @param end the upper bound of the range of testray projects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of testray projects
	 */
	@Override
	public List<TestrayProject> findAll(
		int start, int end, OrderByComparator<TestrayProject> orderByComparator,
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

		List<TestrayProject> list = null;

		if (useFinderCache) {
			list = (List<TestrayProject>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_TESTRAYPROJECT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_TESTRAYPROJECT;

				sql = sql.concat(TestrayProjectModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<TestrayProject>)QueryUtil.list(
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
	 * Removes all the testray projects from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (TestrayProject testrayProject : findAll()) {
			remove(testrayProject);
		}
	}

	/**
	 * Returns the number of testray projects.
	 *
	 * @return the number of testray projects
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_TESTRAYPROJECT);

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
		return TestrayProjectModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the testray project persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			TestrayProjectModelImpl.ENTITY_CACHE_ENABLED,
			TestrayProjectModelImpl.FINDER_CACHE_ENABLED,
			TestrayProjectImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			TestrayProjectModelImpl.ENTITY_CACHE_ENABLED,
			TestrayProjectModelImpl.FINDER_CACHE_ENABLED,
			TestrayProjectImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			TestrayProjectModelImpl.ENTITY_CACHE_ENABLED,
			TestrayProjectModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByGI_N = new FinderPath(
			TestrayProjectModelImpl.ENTITY_CACHE_ENABLED,
			TestrayProjectModelImpl.FINDER_CACHE_ENABLED,
			TestrayProjectImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByGI_N",
			new String[] {Long.class.getName(), String.class.getName()},
			TestrayProjectModelImpl.GROUPID_COLUMN_BITMASK |
			TestrayProjectModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByGI_N = new FinderPath(
			TestrayProjectModelImpl.ENTITY_CACHE_ENABLED,
			TestrayProjectModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGI_N",
			new String[] {Long.class.getName(), String.class.getName()});

		TestrayProjectUtil.setPersistence(this);
	}

	public void destroy() {
		TestrayProjectUtil.setPersistence(null);

		entityCache.removeCache(TestrayProjectImpl.class.getName());

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

	private static final String _SQL_SELECT_TESTRAYPROJECT =
		"SELECT testrayProject FROM TestrayProject testrayProject";

	private static final String _SQL_SELECT_TESTRAYPROJECT_WHERE_PKS_IN =
		"SELECT testrayProject FROM TestrayProject testrayProject WHERE testrayProjectId IN (";

	private static final String _SQL_SELECT_TESTRAYPROJECT_WHERE =
		"SELECT testrayProject FROM TestrayProject testrayProject WHERE ";

	private static final String _SQL_COUNT_TESTRAYPROJECT =
		"SELECT COUNT(testrayProject) FROM TestrayProject testrayProject";

	private static final String _SQL_COUNT_TESTRAYPROJECT_WHERE =
		"SELECT COUNT(testrayProject) FROM TestrayProject testrayProject WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "testrayProject.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No TestrayProject exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No TestrayProject exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		TestrayProjectPersistenceImpl.class);

}