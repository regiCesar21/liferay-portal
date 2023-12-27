/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.testray.service.persistence.impl;

import com.liferay.osb.testray.exception.NoSuchTestraySuiteException;
import com.liferay.osb.testray.model.TestraySuite;
import com.liferay.osb.testray.model.impl.TestraySuiteImpl;
import com.liferay.osb.testray.model.impl.TestraySuiteModelImpl;
import com.liferay.osb.testray.service.persistence.TestrayCasePersistence;
import com.liferay.osb.testray.service.persistence.TestraySuitePersistence;
import com.liferay.osb.testray.service.persistence.TestraySuiteUtil;
import com.liferay.portal.kernel.bean.BeanReference;
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
import java.util.Set;

/**
 * The persistence implementation for the testray suite service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ethan Bustad
 * @generated
 */
public class TestraySuitePersistenceImpl
	extends BasePersistenceImpl<TestraySuite>
	implements TestraySuitePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>TestraySuiteUtil</code> to access the testray suite persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		TestraySuiteImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public TestraySuitePersistenceImpl() {
		setModelClass(TestraySuite.class);
	}

	/**
	 * Caches the testray suite in the entity cache if it is enabled.
	 *
	 * @param testraySuite the testray suite
	 */
	@Override
	public void cacheResult(TestraySuite testraySuite) {
		entityCache.putResult(
			TestraySuiteModelImpl.ENTITY_CACHE_ENABLED, TestraySuiteImpl.class,
			testraySuite.getPrimaryKey(), testraySuite);

		testraySuite.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the testray suites in the entity cache if it is enabled.
	 *
	 * @param testraySuites the testray suites
	 */
	@Override
	public void cacheResult(List<TestraySuite> testraySuites) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (testraySuites.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (TestraySuite testraySuite : testraySuites) {
			if (entityCache.getResult(
					TestraySuiteModelImpl.ENTITY_CACHE_ENABLED,
					TestraySuiteImpl.class, testraySuite.getPrimaryKey()) ==
						null) {

				cacheResult(testraySuite);
			}
			else {
				testraySuite.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all testray suites.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(TestraySuiteImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the testray suite.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(TestraySuite testraySuite) {
		entityCache.removeResult(
			TestraySuiteModelImpl.ENTITY_CACHE_ENABLED, TestraySuiteImpl.class,
			testraySuite.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<TestraySuite> testraySuites) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (TestraySuite testraySuite : testraySuites) {
			entityCache.removeResult(
				TestraySuiteModelImpl.ENTITY_CACHE_ENABLED,
				TestraySuiteImpl.class, testraySuite.getPrimaryKey());
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				TestraySuiteModelImpl.ENTITY_CACHE_ENABLED,
				TestraySuiteImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new testray suite with the primary key. Does not add the testray suite to the database.
	 *
	 * @param testraySuiteId the primary key for the new testray suite
	 * @return the new testray suite
	 */
	@Override
	public TestraySuite create(long testraySuiteId) {
		TestraySuite testraySuite = new TestraySuiteImpl();

		testraySuite.setNew(true);
		testraySuite.setPrimaryKey(testraySuiteId);

		testraySuite.setCompanyId(CompanyThreadLocal.getCompanyId());

		return testraySuite;
	}

	/**
	 * Removes the testray suite with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param testraySuiteId the primary key of the testray suite
	 * @return the testray suite that was removed
	 * @throws NoSuchTestraySuiteException if a testray suite with the primary key could not be found
	 */
	@Override
	public TestraySuite remove(long testraySuiteId)
		throws NoSuchTestraySuiteException {

		return remove((Serializable)testraySuiteId);
	}

	/**
	 * Removes the testray suite with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the testray suite
	 * @return the testray suite that was removed
	 * @throws NoSuchTestraySuiteException if a testray suite with the primary key could not be found
	 */
	@Override
	public TestraySuite remove(Serializable primaryKey)
		throws NoSuchTestraySuiteException {

		Session session = null;

		try {
			session = openSession();

			TestraySuite testraySuite = (TestraySuite)session.get(
				TestraySuiteImpl.class, primaryKey);

			if (testraySuite == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTestraySuiteException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(testraySuite);
		}
		catch (NoSuchTestraySuiteException noSuchEntityException) {
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
	protected TestraySuite removeImpl(TestraySuite testraySuite) {
		testraySuiteToTestrayCaseTableMapper.deleteLeftPrimaryKeyTableMappings(
			testraySuite.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(testraySuite)) {
				testraySuite = (TestraySuite)session.get(
					TestraySuiteImpl.class, testraySuite.getPrimaryKeyObj());
			}

			if (testraySuite != null) {
				session.delete(testraySuite);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (testraySuite != null) {
			clearCache(testraySuite);
		}

		return testraySuite;
	}

	@Override
	public TestraySuite updateImpl(TestraySuite testraySuite) {
		boolean isNew = testraySuite.isNew();

		if (!(testraySuite instanceof TestraySuiteModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(testraySuite.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					testraySuite);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in testraySuite proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom TestraySuite implementation " +
					testraySuite.getClass());
		}

		TestraySuiteModelImpl testraySuiteModelImpl =
			(TestraySuiteModelImpl)testraySuite;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (testraySuite.getCreateDate() == null)) {
			if (serviceContext == null) {
				testraySuite.setCreateDate(date);
			}
			else {
				testraySuite.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!testraySuiteModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				testraySuite.setModifiedDate(date);
			}
			else {
				testraySuite.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(testraySuite);

				testraySuite.setNew(false);
			}
			else {
				testraySuite = (TestraySuite)session.merge(testraySuite);
			}
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
			TestraySuiteModelImpl.ENTITY_CACHE_ENABLED, TestraySuiteImpl.class,
			testraySuite.getPrimaryKey(), testraySuite, false);

		testraySuite.resetOriginalValues();

		return testraySuite;
	}

	/**
	 * Returns the testray suite with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray suite
	 * @return the testray suite
	 * @throws NoSuchTestraySuiteException if a testray suite with the primary key could not be found
	 */
	@Override
	public TestraySuite findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTestraySuiteException {

		TestraySuite testraySuite = fetchByPrimaryKey(primaryKey);

		if (testraySuite == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTestraySuiteException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return testraySuite;
	}

	/**
	 * Returns the testray suite with the primary key or throws a <code>NoSuchTestraySuiteException</code> if it could not be found.
	 *
	 * @param testraySuiteId the primary key of the testray suite
	 * @return the testray suite
	 * @throws NoSuchTestraySuiteException if a testray suite with the primary key could not be found
	 */
	@Override
	public TestraySuite findByPrimaryKey(long testraySuiteId)
		throws NoSuchTestraySuiteException {

		return findByPrimaryKey((Serializable)testraySuiteId);
	}

	/**
	 * Returns the testray suite with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray suite
	 * @return the testray suite, or <code>null</code> if a testray suite with the primary key could not be found
	 */
	@Override
	public TestraySuite fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			TestraySuiteModelImpl.ENTITY_CACHE_ENABLED, TestraySuiteImpl.class,
			primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		TestraySuite testraySuite = (TestraySuite)serializable;

		if (testraySuite == null) {
			Session session = null;

			try {
				session = openSession();

				testraySuite = (TestraySuite)session.get(
					TestraySuiteImpl.class, primaryKey);

				if (testraySuite != null) {
					cacheResult(testraySuite);
				}
				else {
					entityCache.putResult(
						TestraySuiteModelImpl.ENTITY_CACHE_ENABLED,
						TestraySuiteImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					TestraySuiteModelImpl.ENTITY_CACHE_ENABLED,
					TestraySuiteImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return testraySuite;
	}

	/**
	 * Returns the testray suite with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param testraySuiteId the primary key of the testray suite
	 * @return the testray suite, or <code>null</code> if a testray suite with the primary key could not be found
	 */
	@Override
	public TestraySuite fetchByPrimaryKey(long testraySuiteId) {
		return fetchByPrimaryKey((Serializable)testraySuiteId);
	}

	@Override
	public Map<Serializable, TestraySuite> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, TestraySuite> map =
			new HashMap<Serializable, TestraySuite>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			TestraySuite testraySuite = fetchByPrimaryKey(primaryKey);

			if (testraySuite != null) {
				map.put(primaryKey, testraySuite);
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
				TestraySuiteModelImpl.ENTITY_CACHE_ENABLED,
				TestraySuiteImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (TestraySuite)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_TESTRAYSUITE_WHERE_PKS_IN);

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

			for (TestraySuite testraySuite : (List<TestraySuite>)query.list()) {
				map.put(testraySuite.getPrimaryKeyObj(), testraySuite);

				cacheResult(testraySuite);

				uncachedPrimaryKeys.remove(testraySuite.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					TestraySuiteModelImpl.ENTITY_CACHE_ENABLED,
					TestraySuiteImpl.class, primaryKey, nullModel);
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
	 * Returns all the testray suites.
	 *
	 * @return the testray suites
	 */
	@Override
	public List<TestraySuite> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the testray suites.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestraySuiteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray suites
	 * @param end the upper bound of the range of testray suites (not inclusive)
	 * @return the range of testray suites
	 */
	@Override
	public List<TestraySuite> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray suites.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestraySuiteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray suites
	 * @param end the upper bound of the range of testray suites (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray suites
	 */
	@Override
	public List<TestraySuite> findAll(
		int start, int end, OrderByComparator<TestraySuite> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the testray suites.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestraySuiteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray suites
	 * @param end the upper bound of the range of testray suites (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of testray suites
	 */
	@Override
	public List<TestraySuite> findAll(
		int start, int end, OrderByComparator<TestraySuite> orderByComparator,
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

		List<TestraySuite> list = null;

		if (useFinderCache) {
			list = (List<TestraySuite>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_TESTRAYSUITE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_TESTRAYSUITE;

				sql = sql.concat(TestraySuiteModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<TestraySuite>)QueryUtil.list(
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
	 * Removes all the testray suites from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (TestraySuite testraySuite : findAll()) {
			remove(testraySuite);
		}
	}

	/**
	 * Returns the number of testray suites.
	 *
	 * @return the number of testray suites
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_TESTRAYSUITE);

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
	 * Returns the primaryKeys of testray cases associated with the testray suite.
	 *
	 * @param pk the primary key of the testray suite
	 * @return long[] of the primaryKeys of testray cases associated with the testray suite
	 */
	@Override
	public long[] getTestrayCasePrimaryKeys(long pk) {
		long[] pks = testraySuiteToTestrayCaseTableMapper.getRightPrimaryKeys(
			pk);

		return pks.clone();
	}

	/**
	 * Returns all the testray cases associated with the testray suite.
	 *
	 * @param pk the primary key of the testray suite
	 * @return the testray cases associated with the testray suite
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayCase> getTestrayCases(
		long pk) {

		return getTestrayCases(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the testray cases associated with the testray suite.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestraySuiteModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray suite
	 * @param start the lower bound of the range of testray suites
	 * @param end the upper bound of the range of testray suites (not inclusive)
	 * @return the range of testray cases associated with the testray suite
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayCase> getTestrayCases(
		long pk, int start, int end) {

		return getTestrayCases(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray cases associated with the testray suite.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestraySuiteModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray suite
	 * @param start the lower bound of the range of testray suites
	 * @param end the upper bound of the range of testray suites (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray cases associated with the testray suite
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayCase> getTestrayCases(
		long pk, int start, int end,
		OrderByComparator<com.liferay.osb.testray.model.TestrayCase>
			orderByComparator) {

		return testraySuiteToTestrayCaseTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of testray cases associated with the testray suite.
	 *
	 * @param pk the primary key of the testray suite
	 * @return the number of testray cases associated with the testray suite
	 */
	@Override
	public int getTestrayCasesSize(long pk) {
		long[] pks = testraySuiteToTestrayCaseTableMapper.getRightPrimaryKeys(
			pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the testray case is associated with the testray suite.
	 *
	 * @param pk the primary key of the testray suite
	 * @param testrayCasePK the primary key of the testray case
	 * @return <code>true</code> if the testray case is associated with the testray suite; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTestrayCase(long pk, long testrayCasePK) {
		return testraySuiteToTestrayCaseTableMapper.containsTableMapping(
			pk, testrayCasePK);
	}

	/**
	 * Returns <code>true</code> if the testray suite has any testray cases associated with it.
	 *
	 * @param pk the primary key of the testray suite to check for associations with testray cases
	 * @return <code>true</code> if the testray suite has any testray cases associated with it; <code>false</code> otherwise
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
	 * Adds an association between the testray suite and the testray case. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray suite
	 * @param testrayCasePK the primary key of the testray case
	 */
	@Override
	public void addTestrayCase(long pk, long testrayCasePK) {
		TestraySuite testraySuite = fetchByPrimaryKey(pk);

		if (testraySuite == null) {
			testraySuiteToTestrayCaseTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, testrayCasePK);
		}
		else {
			testraySuiteToTestrayCaseTableMapper.addTableMapping(
				testraySuite.getCompanyId(), pk, testrayCasePK);
		}
	}

	/**
	 * Adds an association between the testray suite and the testray case. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray suite
	 * @param testrayCase the testray case
	 */
	@Override
	public void addTestrayCase(
		long pk, com.liferay.osb.testray.model.TestrayCase testrayCase) {

		TestraySuite testraySuite = fetchByPrimaryKey(pk);

		if (testraySuite == null) {
			testraySuiteToTestrayCaseTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				testrayCase.getPrimaryKey());
		}
		else {
			testraySuiteToTestrayCaseTableMapper.addTableMapping(
				testraySuite.getCompanyId(), pk, testrayCase.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the testray suite and the testray cases. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray suite
	 * @param testrayCasePKs the primary keys of the testray cases
	 */
	@Override
	public void addTestrayCases(long pk, long[] testrayCasePKs) {
		long companyId = 0;

		TestraySuite testraySuite = fetchByPrimaryKey(pk);

		if (testraySuite == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testraySuite.getCompanyId();
		}

		testraySuiteToTestrayCaseTableMapper.addTableMappings(
			companyId, pk, testrayCasePKs);
	}

	/**
	 * Adds an association between the testray suite and the testray cases. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray suite
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
	 * Clears all associations between the testray suite and its testray cases. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray suite to clear the associated testray cases from
	 */
	@Override
	public void clearTestrayCases(long pk) {
		testraySuiteToTestrayCaseTableMapper.deleteLeftPrimaryKeyTableMappings(
			pk);
	}

	/**
	 * Removes the association between the testray suite and the testray case. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray suite
	 * @param testrayCasePK the primary key of the testray case
	 */
	@Override
	public void removeTestrayCase(long pk, long testrayCasePK) {
		testraySuiteToTestrayCaseTableMapper.deleteTableMapping(
			pk, testrayCasePK);
	}

	/**
	 * Removes the association between the testray suite and the testray case. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray suite
	 * @param testrayCase the testray case
	 */
	@Override
	public void removeTestrayCase(
		long pk, com.liferay.osb.testray.model.TestrayCase testrayCase) {

		testraySuiteToTestrayCaseTableMapper.deleteTableMapping(
			pk, testrayCase.getPrimaryKey());
	}

	/**
	 * Removes the association between the testray suite and the testray cases. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray suite
	 * @param testrayCasePKs the primary keys of the testray cases
	 */
	@Override
	public void removeTestrayCases(long pk, long[] testrayCasePKs) {
		testraySuiteToTestrayCaseTableMapper.deleteTableMappings(
			pk, testrayCasePKs);
	}

	/**
	 * Removes the association between the testray suite and the testray cases. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray suite
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
	 * Sets the testray cases associated with the testray suite, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray suite
	 * @param testrayCasePKs the primary keys of the testray cases to be associated with the testray suite
	 */
	@Override
	public void setTestrayCases(long pk, long[] testrayCasePKs) {
		Set<Long> newTestrayCasePKsSet = SetUtil.fromArray(testrayCasePKs);
		Set<Long> oldTestrayCasePKsSet = SetUtil.fromArray(
			testraySuiteToTestrayCaseTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeTestrayCasePKsSet = new HashSet<Long>(
			oldTestrayCasePKsSet);

		removeTestrayCasePKsSet.removeAll(newTestrayCasePKsSet);

		testraySuiteToTestrayCaseTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeTestrayCasePKsSet));

		newTestrayCasePKsSet.removeAll(oldTestrayCasePKsSet);

		long companyId = 0;

		TestraySuite testraySuite = fetchByPrimaryKey(pk);

		if (testraySuite == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testraySuite.getCompanyId();
		}

		testraySuiteToTestrayCaseTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newTestrayCasePKsSet));
	}

	/**
	 * Sets the testray cases associated with the testray suite, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray suite
	 * @param testrayCases the testray cases to be associated with the testray suite
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
		return TestraySuiteModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the testray suite persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		testraySuiteToTestrayCaseTableMapper =
			TableMapperFactory.getTableMapper(
				"OSB_TestraySuites_TestrayCases", "companyId", "testraySuiteId",
				"testrayCaseId", this, testrayCasePersistence);

		_finderPathWithPaginationFindAll = new FinderPath(
			TestraySuiteModelImpl.ENTITY_CACHE_ENABLED,
			TestraySuiteModelImpl.FINDER_CACHE_ENABLED, TestraySuiteImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			TestraySuiteModelImpl.ENTITY_CACHE_ENABLED,
			TestraySuiteModelImpl.FINDER_CACHE_ENABLED, TestraySuiteImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			TestraySuiteModelImpl.ENTITY_CACHE_ENABLED,
			TestraySuiteModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		TestraySuiteUtil.setPersistence(this);
	}

	public void destroy() {
		TestraySuiteUtil.setPersistence(null);

		entityCache.removeCache(TestraySuiteImpl.class.getName());

		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

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

	@BeanReference(type = TestrayCasePersistence.class)
	protected TestrayCasePersistence testrayCasePersistence;

	protected TableMapper
		<TestraySuite, com.liferay.osb.testray.model.TestrayCase>
			testraySuiteToTestrayCaseTableMapper;

	private static final String _SQL_SELECT_TESTRAYSUITE =
		"SELECT testraySuite FROM TestraySuite testraySuite";

	private static final String _SQL_SELECT_TESTRAYSUITE_WHERE_PKS_IN =
		"SELECT testraySuite FROM TestraySuite testraySuite WHERE testraySuiteId IN (";

	private static final String _SQL_COUNT_TESTRAYSUITE =
		"SELECT COUNT(testraySuite) FROM TestraySuite testraySuite";

	private static final String _ORDER_BY_ENTITY_ALIAS = "testraySuite.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No TestraySuite exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		TestraySuitePersistenceImpl.class);

}