/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.loop.service.persistence.impl;

import com.liferay.osb.loop.exception.NoSuchLoopJobTitleException;
import com.liferay.osb.loop.model.LoopJobTitle;
import com.liferay.osb.loop.model.impl.LoopJobTitleImpl;
import com.liferay.osb.loop.model.impl.LoopJobTitleModelImpl;
import com.liferay.osb.loop.service.persistence.LoopJobTitlePersistence;
import com.liferay.osb.loop.service.persistence.LoopJobTitleUtil;
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
 * The persistence implementation for the loop job title service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ethan Bustad
 * @generated
 */
public class LoopJobTitlePersistenceImpl
	extends BasePersistenceImpl<LoopJobTitle>
	implements LoopJobTitlePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LoopJobTitleUtil</code> to access the loop job title persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LoopJobTitleImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByName;
	private FinderPath _finderPathCountByName;

	/**
	 * Returns the loop job title where name = &#63; or throws a <code>NoSuchLoopJobTitleException</code> if it could not be found.
	 *
	 * @param name the name
	 * @return the matching loop job title
	 * @throws NoSuchLoopJobTitleException if a matching loop job title could not be found
	 */
	@Override
	public LoopJobTitle findByName(String name)
		throws NoSuchLoopJobTitleException {

		LoopJobTitle loopJobTitle = fetchByName(name);

		if (loopJobTitle == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchLoopJobTitleException(sb.toString());
		}

		return loopJobTitle;
	}

	/**
	 * Returns the loop job title where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @return the matching loop job title, or <code>null</code> if a matching loop job title could not be found
	 */
	@Override
	public LoopJobTitle fetchByName(String name) {
		return fetchByName(name, true);
	}

	/**
	 * Returns the loop job title where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching loop job title, or <code>null</code> if a matching loop job title could not be found
	 */
	@Override
	public LoopJobTitle fetchByName(String name, boolean useFinderCache) {
		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByName, finderArgs, this);
		}

		if (result instanceof LoopJobTitle) {
			LoopJobTitle loopJobTitle = (LoopJobTitle)result;

			if (!Objects.equals(name, loopJobTitle.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_LOOPJOBTITLE_WHERE);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_NAME_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_NAME_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(name);
				}

				List<LoopJobTitle> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByName, finderArgs, list);
					}
				}
				else {
					LoopJobTitle loopJobTitle = list.get(0);

					result = loopJobTitle;

					cacheResult(loopJobTitle);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByName, finderArgs);
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
			return (LoopJobTitle)result;
		}
	}

	/**
	 * Removes the loop job title where name = &#63; from the database.
	 *
	 * @param name the name
	 * @return the loop job title that was removed
	 */
	@Override
	public LoopJobTitle removeByName(String name)
		throws NoSuchLoopJobTitleException {

		LoopJobTitle loopJobTitle = findByName(name);

		return remove(loopJobTitle);
	}

	/**
	 * Returns the number of loop job titles where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching loop job titles
	 */
	@Override
	public int countByName(String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByName;

		Object[] finderArgs = new Object[] {name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_LOOPJOBTITLE_WHERE);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_NAME_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_NAME_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

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

	private static final String _FINDER_COLUMN_NAME_NAME_2 =
		"loopJobTitle.name = ?";

	private static final String _FINDER_COLUMN_NAME_NAME_3 =
		"(loopJobTitle.name IS NULL OR loopJobTitle.name = '')";

	public LoopJobTitlePersistenceImpl() {
		setModelClass(LoopJobTitle.class);
	}

	/**
	 * Caches the loop job title in the entity cache if it is enabled.
	 *
	 * @param loopJobTitle the loop job title
	 */
	@Override
	public void cacheResult(LoopJobTitle loopJobTitle) {
		entityCache.putResult(
			LoopJobTitleModelImpl.ENTITY_CACHE_ENABLED, LoopJobTitleImpl.class,
			loopJobTitle.getPrimaryKey(), loopJobTitle);

		finderCache.putResult(
			_finderPathFetchByName, new Object[] {loopJobTitle.getName()},
			loopJobTitle);

		loopJobTitle.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the loop job titles in the entity cache if it is enabled.
	 *
	 * @param loopJobTitles the loop job titles
	 */
	@Override
	public void cacheResult(List<LoopJobTitle> loopJobTitles) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (loopJobTitles.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (LoopJobTitle loopJobTitle : loopJobTitles) {
			if (entityCache.getResult(
					LoopJobTitleModelImpl.ENTITY_CACHE_ENABLED,
					LoopJobTitleImpl.class, loopJobTitle.getPrimaryKey()) ==
						null) {

				cacheResult(loopJobTitle);
			}
			else {
				loopJobTitle.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all loop job titles.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LoopJobTitleImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the loop job title.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LoopJobTitle loopJobTitle) {
		entityCache.removeResult(
			LoopJobTitleModelImpl.ENTITY_CACHE_ENABLED, LoopJobTitleImpl.class,
			loopJobTitle.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((LoopJobTitleModelImpl)loopJobTitle, true);
	}

	@Override
	public void clearCache(List<LoopJobTitle> loopJobTitles) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LoopJobTitle loopJobTitle : loopJobTitles) {
			entityCache.removeResult(
				LoopJobTitleModelImpl.ENTITY_CACHE_ENABLED,
				LoopJobTitleImpl.class, loopJobTitle.getPrimaryKey());

			clearUniqueFindersCache((LoopJobTitleModelImpl)loopJobTitle, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				LoopJobTitleModelImpl.ENTITY_CACHE_ENABLED,
				LoopJobTitleImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		LoopJobTitleModelImpl loopJobTitleModelImpl) {

		Object[] args = new Object[] {loopJobTitleModelImpl.getName()};

		finderCache.putResult(
			_finderPathCountByName, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByName, args, loopJobTitleModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		LoopJobTitleModelImpl loopJobTitleModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {loopJobTitleModelImpl.getName()};

			finderCache.removeResult(_finderPathCountByName, args);
			finderCache.removeResult(_finderPathFetchByName, args);
		}

		if ((loopJobTitleModelImpl.getColumnBitmask() &
			 _finderPathFetchByName.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				loopJobTitleModelImpl.getOriginalName()
			};

			finderCache.removeResult(_finderPathCountByName, args);
			finderCache.removeResult(_finderPathFetchByName, args);
		}
	}

	/**
	 * Creates a new loop job title with the primary key. Does not add the loop job title to the database.
	 *
	 * @param loopJobTitleId the primary key for the new loop job title
	 * @return the new loop job title
	 */
	@Override
	public LoopJobTitle create(long loopJobTitleId) {
		LoopJobTitle loopJobTitle = new LoopJobTitleImpl();

		loopJobTitle.setNew(true);
		loopJobTitle.setPrimaryKey(loopJobTitleId);

		loopJobTitle.setCompanyId(CompanyThreadLocal.getCompanyId());

		return loopJobTitle;
	}

	/**
	 * Removes the loop job title with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param loopJobTitleId the primary key of the loop job title
	 * @return the loop job title that was removed
	 * @throws NoSuchLoopJobTitleException if a loop job title with the primary key could not be found
	 */
	@Override
	public LoopJobTitle remove(long loopJobTitleId)
		throws NoSuchLoopJobTitleException {

		return remove((Serializable)loopJobTitleId);
	}

	/**
	 * Removes the loop job title with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the loop job title
	 * @return the loop job title that was removed
	 * @throws NoSuchLoopJobTitleException if a loop job title with the primary key could not be found
	 */
	@Override
	public LoopJobTitle remove(Serializable primaryKey)
		throws NoSuchLoopJobTitleException {

		Session session = null;

		try {
			session = openSession();

			LoopJobTitle loopJobTitle = (LoopJobTitle)session.get(
				LoopJobTitleImpl.class, primaryKey);

			if (loopJobTitle == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLoopJobTitleException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(loopJobTitle);
		}
		catch (NoSuchLoopJobTitleException noSuchEntityException) {
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
	protected LoopJobTitle removeImpl(LoopJobTitle loopJobTitle) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(loopJobTitle)) {
				loopJobTitle = (LoopJobTitle)session.get(
					LoopJobTitleImpl.class, loopJobTitle.getPrimaryKeyObj());
			}

			if (loopJobTitle != null) {
				session.delete(loopJobTitle);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (loopJobTitle != null) {
			clearCache(loopJobTitle);
		}

		return loopJobTitle;
	}

	@Override
	public LoopJobTitle updateImpl(LoopJobTitle loopJobTitle) {
		boolean isNew = loopJobTitle.isNew();

		if (!(loopJobTitle instanceof LoopJobTitleModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(loopJobTitle.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					loopJobTitle);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in loopJobTitle proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LoopJobTitle implementation " +
					loopJobTitle.getClass());
		}

		LoopJobTitleModelImpl loopJobTitleModelImpl =
			(LoopJobTitleModelImpl)loopJobTitle;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (loopJobTitle.getCreateDate() == null)) {
			if (serviceContext == null) {
				loopJobTitle.setCreateDate(date);
			}
			else {
				loopJobTitle.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!loopJobTitleModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				loopJobTitle.setModifiedDate(date);
			}
			else {
				loopJobTitle.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(loopJobTitle);

				loopJobTitle.setNew(false);
			}
			else {
				loopJobTitle = (LoopJobTitle)session.merge(loopJobTitle);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LoopJobTitleModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			LoopJobTitleModelImpl.ENTITY_CACHE_ENABLED, LoopJobTitleImpl.class,
			loopJobTitle.getPrimaryKey(), loopJobTitle, false);

		clearUniqueFindersCache(loopJobTitleModelImpl, false);
		cacheUniqueFindersCache(loopJobTitleModelImpl);

		loopJobTitle.resetOriginalValues();

		return loopJobTitle;
	}

	/**
	 * Returns the loop job title with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the loop job title
	 * @return the loop job title
	 * @throws NoSuchLoopJobTitleException if a loop job title with the primary key could not be found
	 */
	@Override
	public LoopJobTitle findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLoopJobTitleException {

		LoopJobTitle loopJobTitle = fetchByPrimaryKey(primaryKey);

		if (loopJobTitle == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLoopJobTitleException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return loopJobTitle;
	}

	/**
	 * Returns the loop job title with the primary key or throws a <code>NoSuchLoopJobTitleException</code> if it could not be found.
	 *
	 * @param loopJobTitleId the primary key of the loop job title
	 * @return the loop job title
	 * @throws NoSuchLoopJobTitleException if a loop job title with the primary key could not be found
	 */
	@Override
	public LoopJobTitle findByPrimaryKey(long loopJobTitleId)
		throws NoSuchLoopJobTitleException {

		return findByPrimaryKey((Serializable)loopJobTitleId);
	}

	/**
	 * Returns the loop job title with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the loop job title
	 * @return the loop job title, or <code>null</code> if a loop job title with the primary key could not be found
	 */
	@Override
	public LoopJobTitle fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			LoopJobTitleModelImpl.ENTITY_CACHE_ENABLED, LoopJobTitleImpl.class,
			primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		LoopJobTitle loopJobTitle = (LoopJobTitle)serializable;

		if (loopJobTitle == null) {
			Session session = null;

			try {
				session = openSession();

				loopJobTitle = (LoopJobTitle)session.get(
					LoopJobTitleImpl.class, primaryKey);

				if (loopJobTitle != null) {
					cacheResult(loopJobTitle);
				}
				else {
					entityCache.putResult(
						LoopJobTitleModelImpl.ENTITY_CACHE_ENABLED,
						LoopJobTitleImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					LoopJobTitleModelImpl.ENTITY_CACHE_ENABLED,
					LoopJobTitleImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return loopJobTitle;
	}

	/**
	 * Returns the loop job title with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param loopJobTitleId the primary key of the loop job title
	 * @return the loop job title, or <code>null</code> if a loop job title with the primary key could not be found
	 */
	@Override
	public LoopJobTitle fetchByPrimaryKey(long loopJobTitleId) {
		return fetchByPrimaryKey((Serializable)loopJobTitleId);
	}

	@Override
	public Map<Serializable, LoopJobTitle> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, LoopJobTitle> map =
			new HashMap<Serializable, LoopJobTitle>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			LoopJobTitle loopJobTitle = fetchByPrimaryKey(primaryKey);

			if (loopJobTitle != null) {
				map.put(primaryKey, loopJobTitle);
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
				LoopJobTitleModelImpl.ENTITY_CACHE_ENABLED,
				LoopJobTitleImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (LoopJobTitle)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_LOOPJOBTITLE_WHERE_PKS_IN);

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

			for (LoopJobTitle loopJobTitle : (List<LoopJobTitle>)query.list()) {
				map.put(loopJobTitle.getPrimaryKeyObj(), loopJobTitle);

				cacheResult(loopJobTitle);

				uncachedPrimaryKeys.remove(loopJobTitle.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					LoopJobTitleModelImpl.ENTITY_CACHE_ENABLED,
					LoopJobTitleImpl.class, primaryKey, nullModel);
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
	 * Returns all the loop job titles.
	 *
	 * @return the loop job titles
	 */
	@Override
	public List<LoopJobTitle> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the loop job titles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopJobTitleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop job titles
	 * @param end the upper bound of the range of loop job titles (not inclusive)
	 * @return the range of loop job titles
	 */
	@Override
	public List<LoopJobTitle> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the loop job titles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopJobTitleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop job titles
	 * @param end the upper bound of the range of loop job titles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of loop job titles
	 */
	@Override
	public List<LoopJobTitle> findAll(
		int start, int end, OrderByComparator<LoopJobTitle> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the loop job titles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopJobTitleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop job titles
	 * @param end the upper bound of the range of loop job titles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of loop job titles
	 */
	@Override
	public List<LoopJobTitle> findAll(
		int start, int end, OrderByComparator<LoopJobTitle> orderByComparator,
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

		List<LoopJobTitle> list = null;

		if (useFinderCache) {
			list = (List<LoopJobTitle>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_LOOPJOBTITLE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_LOOPJOBTITLE;

				sql = sql.concat(LoopJobTitleModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<LoopJobTitle>)QueryUtil.list(
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
	 * Removes all the loop job titles from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LoopJobTitle loopJobTitle : findAll()) {
			remove(loopJobTitle);
		}
	}

	/**
	 * Returns the number of loop job titles.
	 *
	 * @return the number of loop job titles
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_LOOPJOBTITLE);

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
		return LoopJobTitleModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the loop job title persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			LoopJobTitleModelImpl.ENTITY_CACHE_ENABLED,
			LoopJobTitleModelImpl.FINDER_CACHE_ENABLED, LoopJobTitleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			LoopJobTitleModelImpl.ENTITY_CACHE_ENABLED,
			LoopJobTitleModelImpl.FINDER_CACHE_ENABLED, LoopJobTitleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			LoopJobTitleModelImpl.ENTITY_CACHE_ENABLED,
			LoopJobTitleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByName = new FinderPath(
			LoopJobTitleModelImpl.ENTITY_CACHE_ENABLED,
			LoopJobTitleModelImpl.FINDER_CACHE_ENABLED, LoopJobTitleImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByName",
			new String[] {String.class.getName()},
			LoopJobTitleModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByName = new FinderPath(
			LoopJobTitleModelImpl.ENTITY_CACHE_ENABLED,
			LoopJobTitleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByName",
			new String[] {String.class.getName()});

		LoopJobTitleUtil.setPersistence(this);
	}

	public void destroy() {
		LoopJobTitleUtil.setPersistence(null);

		entityCache.removeCache(LoopJobTitleImpl.class.getName());

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

	private static final String _SQL_SELECT_LOOPJOBTITLE =
		"SELECT loopJobTitle FROM LoopJobTitle loopJobTitle";

	private static final String _SQL_SELECT_LOOPJOBTITLE_WHERE_PKS_IN =
		"SELECT loopJobTitle FROM LoopJobTitle loopJobTitle WHERE loopJobTitleId IN (";

	private static final String _SQL_SELECT_LOOPJOBTITLE_WHERE =
		"SELECT loopJobTitle FROM LoopJobTitle loopJobTitle WHERE ";

	private static final String _SQL_COUNT_LOOPJOBTITLE =
		"SELECT COUNT(loopJobTitle) FROM LoopJobTitle loopJobTitle";

	private static final String _SQL_COUNT_LOOPJOBTITLE_WHERE =
		"SELECT COUNT(loopJobTitle) FROM LoopJobTitle loopJobTitle WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "loopJobTitle.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LoopJobTitle exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LoopJobTitle exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LoopJobTitlePersistenceImpl.class);

}