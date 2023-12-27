/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.testray.service.persistence.impl;

import com.liferay.osb.testray.exception.NoSuchTestrayFactorCategoryException;
import com.liferay.osb.testray.model.TestrayFactorCategory;
import com.liferay.osb.testray.model.impl.TestrayFactorCategoryImpl;
import com.liferay.osb.testray.model.impl.TestrayFactorCategoryModelImpl;
import com.liferay.osb.testray.service.persistence.TestrayFactorCategoryPersistence;
import com.liferay.osb.testray.service.persistence.TestrayFactorCategoryUtil;
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
 * The persistence implementation for the testray factor category service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ethan Bustad
 * @generated
 */
public class TestrayFactorCategoryPersistenceImpl
	extends BasePersistenceImpl<TestrayFactorCategory>
	implements TestrayFactorCategoryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>TestrayFactorCategoryUtil</code> to access the testray factor category persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		TestrayFactorCategoryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByG_N;
	private FinderPath _finderPathCountByG_N;

	/**
	 * Returns the testray factor category where groupId = &#63; and name = &#63; or throws a <code>NoSuchTestrayFactorCategoryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching testray factor category
	 * @throws NoSuchTestrayFactorCategoryException if a matching testray factor category could not be found
	 */
	@Override
	public TestrayFactorCategory findByG_N(long groupId, String name)
		throws NoSuchTestrayFactorCategoryException {

		TestrayFactorCategory testrayFactorCategory = fetchByG_N(groupId, name);

		if (testrayFactorCategory == null) {
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

			throw new NoSuchTestrayFactorCategoryException(sb.toString());
		}

		return testrayFactorCategory;
	}

	/**
	 * Returns the testray factor category where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching testray factor category, or <code>null</code> if a matching testray factor category could not be found
	 */
	@Override
	public TestrayFactorCategory fetchByG_N(long groupId, String name) {
		return fetchByG_N(groupId, name, true);
	}

	/**
	 * Returns the testray factor category where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching testray factor category, or <code>null</code> if a matching testray factor category could not be found
	 */
	@Override
	public TestrayFactorCategory fetchByG_N(
		long groupId, String name, boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {groupId, name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByG_N, finderArgs, this);
		}

		if (result instanceof TestrayFactorCategory) {
			TestrayFactorCategory testrayFactorCategory =
				(TestrayFactorCategory)result;

			if ((groupId != testrayFactorCategory.getGroupId()) ||
				!Objects.equals(name, testrayFactorCategory.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_TESTRAYFACTORCATEGORY_WHERE);

			sb.append(_FINDER_COLUMN_G_N_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_N_NAME_2);
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

				List<TestrayFactorCategory> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByG_N, finderArgs, list);
					}
				}
				else {
					TestrayFactorCategory testrayFactorCategory = list.get(0);

					result = testrayFactorCategory;

					cacheResult(testrayFactorCategory);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(_finderPathFetchByG_N, finderArgs);
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
			return (TestrayFactorCategory)result;
		}
	}

	/**
	 * Removes the testray factor category where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the testray factor category that was removed
	 */
	@Override
	public TestrayFactorCategory removeByG_N(long groupId, String name)
		throws NoSuchTestrayFactorCategoryException {

		TestrayFactorCategory testrayFactorCategory = findByG_N(groupId, name);

		return remove(testrayFactorCategory);
	}

	/**
	 * Returns the number of testray factor categories where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching testray factor categories
	 */
	@Override
	public int countByG_N(long groupId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByG_N;

		Object[] finderArgs = new Object[] {groupId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_TESTRAYFACTORCATEGORY_WHERE);

			sb.append(_FINDER_COLUMN_G_N_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_N_NAME_2);
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

	private static final String _FINDER_COLUMN_G_N_GROUPID_2 =
		"testrayFactorCategory.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_N_NAME_2 =
		"testrayFactorCategory.name = ?";

	private static final String _FINDER_COLUMN_G_N_NAME_3 =
		"(testrayFactorCategory.name IS NULL OR testrayFactorCategory.name = '')";

	public TestrayFactorCategoryPersistenceImpl() {
		setModelClass(TestrayFactorCategory.class);
	}

	/**
	 * Caches the testray factor category in the entity cache if it is enabled.
	 *
	 * @param testrayFactorCategory the testray factor category
	 */
	@Override
	public void cacheResult(TestrayFactorCategory testrayFactorCategory) {
		entityCache.putResult(
			TestrayFactorCategoryModelImpl.ENTITY_CACHE_ENABLED,
			TestrayFactorCategoryImpl.class,
			testrayFactorCategory.getPrimaryKey(), testrayFactorCategory);

		finderCache.putResult(
			_finderPathFetchByG_N,
			new Object[] {
				testrayFactorCategory.getGroupId(),
				testrayFactorCategory.getName()
			},
			testrayFactorCategory);

		testrayFactorCategory.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the testray factor categories in the entity cache if it is enabled.
	 *
	 * @param testrayFactorCategories the testray factor categories
	 */
	@Override
	public void cacheResult(
		List<TestrayFactorCategory> testrayFactorCategories) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (testrayFactorCategories.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (TestrayFactorCategory testrayFactorCategory :
				testrayFactorCategories) {

			if (entityCache.getResult(
					TestrayFactorCategoryModelImpl.ENTITY_CACHE_ENABLED,
					TestrayFactorCategoryImpl.class,
					testrayFactorCategory.getPrimaryKey()) == null) {

				cacheResult(testrayFactorCategory);
			}
			else {
				testrayFactorCategory.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all testray factor categories.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(TestrayFactorCategoryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the testray factor category.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(TestrayFactorCategory testrayFactorCategory) {
		entityCache.removeResult(
			TestrayFactorCategoryModelImpl.ENTITY_CACHE_ENABLED,
			TestrayFactorCategoryImpl.class,
			testrayFactorCategory.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(TestrayFactorCategoryModelImpl)testrayFactorCategory, true);
	}

	@Override
	public void clearCache(
		List<TestrayFactorCategory> testrayFactorCategories) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (TestrayFactorCategory testrayFactorCategory :
				testrayFactorCategories) {

			entityCache.removeResult(
				TestrayFactorCategoryModelImpl.ENTITY_CACHE_ENABLED,
				TestrayFactorCategoryImpl.class,
				testrayFactorCategory.getPrimaryKey());

			clearUniqueFindersCache(
				(TestrayFactorCategoryModelImpl)testrayFactorCategory, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				TestrayFactorCategoryModelImpl.ENTITY_CACHE_ENABLED,
				TestrayFactorCategoryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		TestrayFactorCategoryModelImpl testrayFactorCategoryModelImpl) {

		Object[] args = new Object[] {
			testrayFactorCategoryModelImpl.getGroupId(),
			testrayFactorCategoryModelImpl.getName()
		};

		finderCache.putResult(
			_finderPathCountByG_N, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_N, args, testrayFactorCategoryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		TestrayFactorCategoryModelImpl testrayFactorCategoryModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				testrayFactorCategoryModelImpl.getGroupId(),
				testrayFactorCategoryModelImpl.getName()
			};

			finderCache.removeResult(_finderPathCountByG_N, args);
			finderCache.removeResult(_finderPathFetchByG_N, args);
		}

		if ((testrayFactorCategoryModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_N.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				testrayFactorCategoryModelImpl.getOriginalGroupId(),
				testrayFactorCategoryModelImpl.getOriginalName()
			};

			finderCache.removeResult(_finderPathCountByG_N, args);
			finderCache.removeResult(_finderPathFetchByG_N, args);
		}
	}

	/**
	 * Creates a new testray factor category with the primary key. Does not add the testray factor category to the database.
	 *
	 * @param testrayFactorCategoryId the primary key for the new testray factor category
	 * @return the new testray factor category
	 */
	@Override
	public TestrayFactorCategory create(long testrayFactorCategoryId) {
		TestrayFactorCategory testrayFactorCategory =
			new TestrayFactorCategoryImpl();

		testrayFactorCategory.setNew(true);
		testrayFactorCategory.setPrimaryKey(testrayFactorCategoryId);

		testrayFactorCategory.setCompanyId(CompanyThreadLocal.getCompanyId());

		return testrayFactorCategory;
	}

	/**
	 * Removes the testray factor category with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param testrayFactorCategoryId the primary key of the testray factor category
	 * @return the testray factor category that was removed
	 * @throws NoSuchTestrayFactorCategoryException if a testray factor category with the primary key could not be found
	 */
	@Override
	public TestrayFactorCategory remove(long testrayFactorCategoryId)
		throws NoSuchTestrayFactorCategoryException {

		return remove((Serializable)testrayFactorCategoryId);
	}

	/**
	 * Removes the testray factor category with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the testray factor category
	 * @return the testray factor category that was removed
	 * @throws NoSuchTestrayFactorCategoryException if a testray factor category with the primary key could not be found
	 */
	@Override
	public TestrayFactorCategory remove(Serializable primaryKey)
		throws NoSuchTestrayFactorCategoryException {

		Session session = null;

		try {
			session = openSession();

			TestrayFactorCategory testrayFactorCategory =
				(TestrayFactorCategory)session.get(
					TestrayFactorCategoryImpl.class, primaryKey);

			if (testrayFactorCategory == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTestrayFactorCategoryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(testrayFactorCategory);
		}
		catch (NoSuchTestrayFactorCategoryException noSuchEntityException) {
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
	protected TestrayFactorCategory removeImpl(
		TestrayFactorCategory testrayFactorCategory) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(testrayFactorCategory)) {
				testrayFactorCategory = (TestrayFactorCategory)session.get(
					TestrayFactorCategoryImpl.class,
					testrayFactorCategory.getPrimaryKeyObj());
			}

			if (testrayFactorCategory != null) {
				session.delete(testrayFactorCategory);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (testrayFactorCategory != null) {
			clearCache(testrayFactorCategory);
		}

		return testrayFactorCategory;
	}

	@Override
	public TestrayFactorCategory updateImpl(
		TestrayFactorCategory testrayFactorCategory) {

		boolean isNew = testrayFactorCategory.isNew();

		if (!(testrayFactorCategory instanceof
				TestrayFactorCategoryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(testrayFactorCategory.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					testrayFactorCategory);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in testrayFactorCategory proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom TestrayFactorCategory implementation " +
					testrayFactorCategory.getClass());
		}

		TestrayFactorCategoryModelImpl testrayFactorCategoryModelImpl =
			(TestrayFactorCategoryModelImpl)testrayFactorCategory;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (testrayFactorCategory.getCreateDate() == null)) {
			if (serviceContext == null) {
				testrayFactorCategory.setCreateDate(date);
			}
			else {
				testrayFactorCategory.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!testrayFactorCategoryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				testrayFactorCategory.setModifiedDate(date);
			}
			else {
				testrayFactorCategory.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(testrayFactorCategory);

				testrayFactorCategory.setNew(false);
			}
			else {
				testrayFactorCategory = (TestrayFactorCategory)session.merge(
					testrayFactorCategory);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!TestrayFactorCategoryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			TestrayFactorCategoryModelImpl.ENTITY_CACHE_ENABLED,
			TestrayFactorCategoryImpl.class,
			testrayFactorCategory.getPrimaryKey(), testrayFactorCategory,
			false);

		clearUniqueFindersCache(testrayFactorCategoryModelImpl, false);
		cacheUniqueFindersCache(testrayFactorCategoryModelImpl);

		testrayFactorCategory.resetOriginalValues();

		return testrayFactorCategory;
	}

	/**
	 * Returns the testray factor category with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray factor category
	 * @return the testray factor category
	 * @throws NoSuchTestrayFactorCategoryException if a testray factor category with the primary key could not be found
	 */
	@Override
	public TestrayFactorCategory findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTestrayFactorCategoryException {

		TestrayFactorCategory testrayFactorCategory = fetchByPrimaryKey(
			primaryKey);

		if (testrayFactorCategory == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTestrayFactorCategoryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return testrayFactorCategory;
	}

	/**
	 * Returns the testray factor category with the primary key or throws a <code>NoSuchTestrayFactorCategoryException</code> if it could not be found.
	 *
	 * @param testrayFactorCategoryId the primary key of the testray factor category
	 * @return the testray factor category
	 * @throws NoSuchTestrayFactorCategoryException if a testray factor category with the primary key could not be found
	 */
	@Override
	public TestrayFactorCategory findByPrimaryKey(long testrayFactorCategoryId)
		throws NoSuchTestrayFactorCategoryException {

		return findByPrimaryKey((Serializable)testrayFactorCategoryId);
	}

	/**
	 * Returns the testray factor category with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray factor category
	 * @return the testray factor category, or <code>null</code> if a testray factor category with the primary key could not be found
	 */
	@Override
	public TestrayFactorCategory fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			TestrayFactorCategoryModelImpl.ENTITY_CACHE_ENABLED,
			TestrayFactorCategoryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		TestrayFactorCategory testrayFactorCategory =
			(TestrayFactorCategory)serializable;

		if (testrayFactorCategory == null) {
			Session session = null;

			try {
				session = openSession();

				testrayFactorCategory = (TestrayFactorCategory)session.get(
					TestrayFactorCategoryImpl.class, primaryKey);

				if (testrayFactorCategory != null) {
					cacheResult(testrayFactorCategory);
				}
				else {
					entityCache.putResult(
						TestrayFactorCategoryModelImpl.ENTITY_CACHE_ENABLED,
						TestrayFactorCategoryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					TestrayFactorCategoryModelImpl.ENTITY_CACHE_ENABLED,
					TestrayFactorCategoryImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return testrayFactorCategory;
	}

	/**
	 * Returns the testray factor category with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param testrayFactorCategoryId the primary key of the testray factor category
	 * @return the testray factor category, or <code>null</code> if a testray factor category with the primary key could not be found
	 */
	@Override
	public TestrayFactorCategory fetchByPrimaryKey(
		long testrayFactorCategoryId) {

		return fetchByPrimaryKey((Serializable)testrayFactorCategoryId);
	}

	@Override
	public Map<Serializable, TestrayFactorCategory> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, TestrayFactorCategory> map =
			new HashMap<Serializable, TestrayFactorCategory>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			TestrayFactorCategory testrayFactorCategory = fetchByPrimaryKey(
				primaryKey);

			if (testrayFactorCategory != null) {
				map.put(primaryKey, testrayFactorCategory);
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
				TestrayFactorCategoryModelImpl.ENTITY_CACHE_ENABLED,
				TestrayFactorCategoryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (TestrayFactorCategory)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_TESTRAYFACTORCATEGORY_WHERE_PKS_IN);

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

			for (TestrayFactorCategory testrayFactorCategory :
					(List<TestrayFactorCategory>)query.list()) {

				map.put(
					testrayFactorCategory.getPrimaryKeyObj(),
					testrayFactorCategory);

				cacheResult(testrayFactorCategory);

				uncachedPrimaryKeys.remove(
					testrayFactorCategory.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					TestrayFactorCategoryModelImpl.ENTITY_CACHE_ENABLED,
					TestrayFactorCategoryImpl.class, primaryKey, nullModel);
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
	 * Returns all the testray factor categories.
	 *
	 * @return the testray factor categories
	 */
	@Override
	public List<TestrayFactorCategory> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the testray factor categories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayFactorCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray factor categories
	 * @param end the upper bound of the range of testray factor categories (not inclusive)
	 * @return the range of testray factor categories
	 */
	@Override
	public List<TestrayFactorCategory> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray factor categories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayFactorCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray factor categories
	 * @param end the upper bound of the range of testray factor categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray factor categories
	 */
	@Override
	public List<TestrayFactorCategory> findAll(
		int start, int end,
		OrderByComparator<TestrayFactorCategory> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the testray factor categories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayFactorCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray factor categories
	 * @param end the upper bound of the range of testray factor categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of testray factor categories
	 */
	@Override
	public List<TestrayFactorCategory> findAll(
		int start, int end,
		OrderByComparator<TestrayFactorCategory> orderByComparator,
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

		List<TestrayFactorCategory> list = null;

		if (useFinderCache) {
			list = (List<TestrayFactorCategory>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_TESTRAYFACTORCATEGORY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_TESTRAYFACTORCATEGORY;

				sql = sql.concat(TestrayFactorCategoryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<TestrayFactorCategory>)QueryUtil.list(
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
	 * Removes all the testray factor categories from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (TestrayFactorCategory testrayFactorCategory : findAll()) {
			remove(testrayFactorCategory);
		}
	}

	/**
	 * Returns the number of testray factor categories.
	 *
	 * @return the number of testray factor categories
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
					_SQL_COUNT_TESTRAYFACTORCATEGORY);

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
		return TestrayFactorCategoryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the testray factor category persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			TestrayFactorCategoryModelImpl.ENTITY_CACHE_ENABLED,
			TestrayFactorCategoryModelImpl.FINDER_CACHE_ENABLED,
			TestrayFactorCategoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			TestrayFactorCategoryModelImpl.ENTITY_CACHE_ENABLED,
			TestrayFactorCategoryModelImpl.FINDER_CACHE_ENABLED,
			TestrayFactorCategoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			TestrayFactorCategoryModelImpl.ENTITY_CACHE_ENABLED,
			TestrayFactorCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByG_N = new FinderPath(
			TestrayFactorCategoryModelImpl.ENTITY_CACHE_ENABLED,
			TestrayFactorCategoryModelImpl.FINDER_CACHE_ENABLED,
			TestrayFactorCategoryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_N",
			new String[] {Long.class.getName(), String.class.getName()},
			TestrayFactorCategoryModelImpl.GROUPID_COLUMN_BITMASK |
			TestrayFactorCategoryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByG_N = new FinderPath(
			TestrayFactorCategoryModelImpl.ENTITY_CACHE_ENABLED,
			TestrayFactorCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_N",
			new String[] {Long.class.getName(), String.class.getName()});

		TestrayFactorCategoryUtil.setPersistence(this);
	}

	public void destroy() {
		TestrayFactorCategoryUtil.setPersistence(null);

		entityCache.removeCache(TestrayFactorCategoryImpl.class.getName());

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

	private static final String _SQL_SELECT_TESTRAYFACTORCATEGORY =
		"SELECT testrayFactorCategory FROM TestrayFactorCategory testrayFactorCategory";

	private static final String _SQL_SELECT_TESTRAYFACTORCATEGORY_WHERE_PKS_IN =
		"SELECT testrayFactorCategory FROM TestrayFactorCategory testrayFactorCategory WHERE testrayFactorCategoryId IN (";

	private static final String _SQL_SELECT_TESTRAYFACTORCATEGORY_WHERE =
		"SELECT testrayFactorCategory FROM TestrayFactorCategory testrayFactorCategory WHERE ";

	private static final String _SQL_COUNT_TESTRAYFACTORCATEGORY =
		"SELECT COUNT(testrayFactorCategory) FROM TestrayFactorCategory testrayFactorCategory";

	private static final String _SQL_COUNT_TESTRAYFACTORCATEGORY_WHERE =
		"SELECT COUNT(testrayFactorCategory) FROM TestrayFactorCategory testrayFactorCategory WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"testrayFactorCategory.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No TestrayFactorCategory exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No TestrayFactorCategory exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		TestrayFactorCategoryPersistenceImpl.class);

}