/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.testray.service.persistence.impl;

import com.liferay.osb.testray.exception.NoSuchTestrayArchiveException;
import com.liferay.osb.testray.model.TestrayArchive;
import com.liferay.osb.testray.model.impl.TestrayArchiveImpl;
import com.liferay.osb.testray.model.impl.TestrayArchiveModelImpl;
import com.liferay.osb.testray.service.persistence.TestrayArchivePersistence;
import com.liferay.osb.testray.service.persistence.TestrayArchiveUtil;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
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
import java.util.Set;

/**
 * The persistence implementation for the testray archive service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ethan Bustad
 * @generated
 */
public class TestrayArchivePersistenceImpl
	extends BasePersistenceImpl<TestrayArchive>
	implements TestrayArchivePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>TestrayArchiveUtil</code> to access the testray archive persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		TestrayArchiveImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public TestrayArchivePersistenceImpl() {
		setModelClass(TestrayArchive.class);
	}

	/**
	 * Caches the testray archive in the entity cache if it is enabled.
	 *
	 * @param testrayArchive the testray archive
	 */
	@Override
	public void cacheResult(TestrayArchive testrayArchive) {
		entityCache.putResult(
			TestrayArchiveModelImpl.ENTITY_CACHE_ENABLED,
			TestrayArchiveImpl.class, testrayArchive.getPrimaryKey(),
			testrayArchive);

		testrayArchive.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the testray archives in the entity cache if it is enabled.
	 *
	 * @param testrayArchives the testray archives
	 */
	@Override
	public void cacheResult(List<TestrayArchive> testrayArchives) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (testrayArchives.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (TestrayArchive testrayArchive : testrayArchives) {
			if (entityCache.getResult(
					TestrayArchiveModelImpl.ENTITY_CACHE_ENABLED,
					TestrayArchiveImpl.class, testrayArchive.getPrimaryKey()) ==
						null) {

				cacheResult(testrayArchive);
			}
			else {
				testrayArchive.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all testray archives.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(TestrayArchiveImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the testray archive.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(TestrayArchive testrayArchive) {
		entityCache.removeResult(
			TestrayArchiveModelImpl.ENTITY_CACHE_ENABLED,
			TestrayArchiveImpl.class, testrayArchive.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<TestrayArchive> testrayArchives) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (TestrayArchive testrayArchive : testrayArchives) {
			entityCache.removeResult(
				TestrayArchiveModelImpl.ENTITY_CACHE_ENABLED,
				TestrayArchiveImpl.class, testrayArchive.getPrimaryKey());
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				TestrayArchiveModelImpl.ENTITY_CACHE_ENABLED,
				TestrayArchiveImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new testray archive with the primary key. Does not add the testray archive to the database.
	 *
	 * @param testrayArchiveId the primary key for the new testray archive
	 * @return the new testray archive
	 */
	@Override
	public TestrayArchive create(long testrayArchiveId) {
		TestrayArchive testrayArchive = new TestrayArchiveImpl();

		testrayArchive.setNew(true);
		testrayArchive.setPrimaryKey(testrayArchiveId);

		testrayArchive.setCompanyId(CompanyThreadLocal.getCompanyId());

		return testrayArchive;
	}

	/**
	 * Removes the testray archive with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param testrayArchiveId the primary key of the testray archive
	 * @return the testray archive that was removed
	 * @throws NoSuchTestrayArchiveException if a testray archive with the primary key could not be found
	 */
	@Override
	public TestrayArchive remove(long testrayArchiveId)
		throws NoSuchTestrayArchiveException {

		return remove((Serializable)testrayArchiveId);
	}

	/**
	 * Removes the testray archive with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the testray archive
	 * @return the testray archive that was removed
	 * @throws NoSuchTestrayArchiveException if a testray archive with the primary key could not be found
	 */
	@Override
	public TestrayArchive remove(Serializable primaryKey)
		throws NoSuchTestrayArchiveException {

		Session session = null;

		try {
			session = openSession();

			TestrayArchive testrayArchive = (TestrayArchive)session.get(
				TestrayArchiveImpl.class, primaryKey);

			if (testrayArchive == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTestrayArchiveException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(testrayArchive);
		}
		catch (NoSuchTestrayArchiveException noSuchEntityException) {
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
	protected TestrayArchive removeImpl(TestrayArchive testrayArchive) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(testrayArchive)) {
				testrayArchive = (TestrayArchive)session.get(
					TestrayArchiveImpl.class,
					testrayArchive.getPrimaryKeyObj());
			}

			if (testrayArchive != null) {
				session.delete(testrayArchive);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (testrayArchive != null) {
			clearCache(testrayArchive);
		}

		return testrayArchive;
	}

	@Override
	public TestrayArchive updateImpl(TestrayArchive testrayArchive) {
		boolean isNew = testrayArchive.isNew();

		if (!(testrayArchive instanceof TestrayArchiveModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(testrayArchive.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					testrayArchive);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in testrayArchive proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom TestrayArchive implementation " +
					testrayArchive.getClass());
		}

		TestrayArchiveModelImpl testrayArchiveModelImpl =
			(TestrayArchiveModelImpl)testrayArchive;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (testrayArchive.getCreateDate() == null)) {
			if (serviceContext == null) {
				testrayArchive.setCreateDate(date);
			}
			else {
				testrayArchive.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!testrayArchiveModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				testrayArchive.setModifiedDate(date);
			}
			else {
				testrayArchive.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(testrayArchive);

				testrayArchive.setNew(false);
			}
			else {
				session.evict(testrayArchive);

				session.saveOrUpdate(testrayArchive);
			}

			session.flush();
			session.clear();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			TestrayArchiveModelImpl.ENTITY_CACHE_ENABLED,
			TestrayArchiveImpl.class, testrayArchive.getPrimaryKey(),
			testrayArchive, false);

		testrayArchive.resetOriginalValues();

		return testrayArchive;
	}

	/**
	 * Returns the testray archive with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray archive
	 * @return the testray archive
	 * @throws NoSuchTestrayArchiveException if a testray archive with the primary key could not be found
	 */
	@Override
	public TestrayArchive findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTestrayArchiveException {

		TestrayArchive testrayArchive = fetchByPrimaryKey(primaryKey);

		if (testrayArchive == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTestrayArchiveException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return testrayArchive;
	}

	/**
	 * Returns the testray archive with the primary key or throws a <code>NoSuchTestrayArchiveException</code> if it could not be found.
	 *
	 * @param testrayArchiveId the primary key of the testray archive
	 * @return the testray archive
	 * @throws NoSuchTestrayArchiveException if a testray archive with the primary key could not be found
	 */
	@Override
	public TestrayArchive findByPrimaryKey(long testrayArchiveId)
		throws NoSuchTestrayArchiveException {

		return findByPrimaryKey((Serializable)testrayArchiveId);
	}

	/**
	 * Returns the testray archive with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray archive
	 * @return the testray archive, or <code>null</code> if a testray archive with the primary key could not be found
	 */
	@Override
	public TestrayArchive fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			TestrayArchiveModelImpl.ENTITY_CACHE_ENABLED,
			TestrayArchiveImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		TestrayArchive testrayArchive = (TestrayArchive)serializable;

		if (testrayArchive == null) {
			Session session = null;

			try {
				session = openSession();

				testrayArchive = (TestrayArchive)session.get(
					TestrayArchiveImpl.class, primaryKey);

				if (testrayArchive != null) {
					cacheResult(testrayArchive);
				}
				else {
					entityCache.putResult(
						TestrayArchiveModelImpl.ENTITY_CACHE_ENABLED,
						TestrayArchiveImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					TestrayArchiveModelImpl.ENTITY_CACHE_ENABLED,
					TestrayArchiveImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return testrayArchive;
	}

	/**
	 * Returns the testray archive with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param testrayArchiveId the primary key of the testray archive
	 * @return the testray archive, or <code>null</code> if a testray archive with the primary key could not be found
	 */
	@Override
	public TestrayArchive fetchByPrimaryKey(long testrayArchiveId) {
		return fetchByPrimaryKey((Serializable)testrayArchiveId);
	}

	@Override
	public Map<Serializable, TestrayArchive> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, TestrayArchive> map =
			new HashMap<Serializable, TestrayArchive>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			TestrayArchive testrayArchive = fetchByPrimaryKey(primaryKey);

			if (testrayArchive != null) {
				map.put(primaryKey, testrayArchive);
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
				TestrayArchiveModelImpl.ENTITY_CACHE_ENABLED,
				TestrayArchiveImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (TestrayArchive)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_TESTRAYARCHIVE_WHERE_PKS_IN);

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

			for (TestrayArchive testrayArchive :
					(List<TestrayArchive>)query.list()) {

				map.put(testrayArchive.getPrimaryKeyObj(), testrayArchive);

				cacheResult(testrayArchive);

				uncachedPrimaryKeys.remove(testrayArchive.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					TestrayArchiveModelImpl.ENTITY_CACHE_ENABLED,
					TestrayArchiveImpl.class, primaryKey, nullModel);
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
	 * Returns all the testray archives.
	 *
	 * @return the testray archives
	 */
	@Override
	public List<TestrayArchive> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the testray archives.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayArchiveModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray archives
	 * @param end the upper bound of the range of testray archives (not inclusive)
	 * @return the range of testray archives
	 */
	@Override
	public List<TestrayArchive> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray archives.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayArchiveModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray archives
	 * @param end the upper bound of the range of testray archives (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray archives
	 */
	@Override
	public List<TestrayArchive> findAll(
		int start, int end,
		OrderByComparator<TestrayArchive> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the testray archives.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayArchiveModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray archives
	 * @param end the upper bound of the range of testray archives (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of testray archives
	 */
	@Override
	public List<TestrayArchive> findAll(
		int start, int end, OrderByComparator<TestrayArchive> orderByComparator,
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

		List<TestrayArchive> list = null;

		if (useFinderCache) {
			list = (List<TestrayArchive>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_TESTRAYARCHIVE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_TESTRAYARCHIVE;

				sql = sql.concat(TestrayArchiveModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<TestrayArchive>)QueryUtil.list(
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
	 * Removes all the testray archives from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (TestrayArchive testrayArchive : findAll()) {
			remove(testrayArchive);
		}
	}

	/**
	 * Returns the number of testray archives.
	 *
	 * @return the number of testray archives
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_TESTRAYARCHIVE);

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
		return TestrayArchiveModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the testray archive persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			TestrayArchiveModelImpl.ENTITY_CACHE_ENABLED,
			TestrayArchiveModelImpl.FINDER_CACHE_ENABLED,
			TestrayArchiveImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			TestrayArchiveModelImpl.ENTITY_CACHE_ENABLED,
			TestrayArchiveModelImpl.FINDER_CACHE_ENABLED,
			TestrayArchiveImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			TestrayArchiveModelImpl.ENTITY_CACHE_ENABLED,
			TestrayArchiveModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		TestrayArchiveUtil.setPersistence(this);
	}

	public void destroy() {
		TestrayArchiveUtil.setPersistence(null);

		entityCache.removeCache(TestrayArchiveImpl.class.getName());

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

	private static final String _SQL_SELECT_TESTRAYARCHIVE =
		"SELECT testrayArchive FROM TestrayArchive testrayArchive";

	private static final String _SQL_SELECT_TESTRAYARCHIVE_WHERE_PKS_IN =
		"SELECT testrayArchive FROM TestrayArchive testrayArchive WHERE testrayArchiveId IN (";

	private static final String _SQL_COUNT_TESTRAYARCHIVE =
		"SELECT COUNT(testrayArchive) FROM TestrayArchive testrayArchive";

	private static final String _ORDER_BY_ENTITY_ALIAS = "testrayArchive.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No TestrayArchive exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		TestrayArchivePersistenceImpl.class);

}