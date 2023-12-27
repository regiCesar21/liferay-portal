/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.loop.service.persistence.impl;

import com.liferay.osb.loop.exception.NoSuchLoopPersonException;
import com.liferay.osb.loop.model.LoopPerson;
import com.liferay.osb.loop.model.impl.LoopPersonImpl;
import com.liferay.osb.loop.model.impl.LoopPersonModelImpl;
import com.liferay.osb.loop.service.persistence.LoopPersonPersistence;
import com.liferay.osb.loop.service.persistence.LoopPersonUtil;
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
import java.util.Set;

/**
 * The persistence implementation for the loop person service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ethan Bustad
 * @generated
 */
public class LoopPersonPersistenceImpl
	extends BasePersistenceImpl<LoopPerson> implements LoopPersonPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LoopPersonUtil</code> to access the loop person persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LoopPersonImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByPersonUserId;
	private FinderPath _finderPathCountByPersonUserId;

	/**
	 * Returns the loop person where personUserId = &#63; or throws a <code>NoSuchLoopPersonException</code> if it could not be found.
	 *
	 * @param personUserId the person user ID
	 * @return the matching loop person
	 * @throws NoSuchLoopPersonException if a matching loop person could not be found
	 */
	@Override
	public LoopPerson findByPersonUserId(long personUserId)
		throws NoSuchLoopPersonException {

		LoopPerson loopPerson = fetchByPersonUserId(personUserId);

		if (loopPerson == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("personUserId=");
			sb.append(personUserId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchLoopPersonException(sb.toString());
		}

		return loopPerson;
	}

	/**
	 * Returns the loop person where personUserId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param personUserId the person user ID
	 * @return the matching loop person, or <code>null</code> if a matching loop person could not be found
	 */
	@Override
	public LoopPerson fetchByPersonUserId(long personUserId) {
		return fetchByPersonUserId(personUserId, true);
	}

	/**
	 * Returns the loop person where personUserId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param personUserId the person user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching loop person, or <code>null</code> if a matching loop person could not be found
	 */
	@Override
	public LoopPerson fetchByPersonUserId(
		long personUserId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {personUserId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByPersonUserId, finderArgs, this);
		}

		if (result instanceof LoopPerson) {
			LoopPerson loopPerson = (LoopPerson)result;

			if (personUserId != loopPerson.getPersonUserId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_LOOPPERSON_WHERE);

			sb.append(_FINDER_COLUMN_PERSONUSERID_PERSONUSERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(personUserId);

				List<LoopPerson> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByPersonUserId, finderArgs, list);
					}
				}
				else {
					LoopPerson loopPerson = list.get(0);

					result = loopPerson;

					cacheResult(loopPerson);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByPersonUserId, finderArgs);
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
			return (LoopPerson)result;
		}
	}

	/**
	 * Removes the loop person where personUserId = &#63; from the database.
	 *
	 * @param personUserId the person user ID
	 * @return the loop person that was removed
	 */
	@Override
	public LoopPerson removeByPersonUserId(long personUserId)
		throws NoSuchLoopPersonException {

		LoopPerson loopPerson = findByPersonUserId(personUserId);

		return remove(loopPerson);
	}

	/**
	 * Returns the number of loop persons where personUserId = &#63;.
	 *
	 * @param personUserId the person user ID
	 * @return the number of matching loop persons
	 */
	@Override
	public int countByPersonUserId(long personUserId) {
		FinderPath finderPath = _finderPathCountByPersonUserId;

		Object[] finderArgs = new Object[] {personUserId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_LOOPPERSON_WHERE);

			sb.append(_FINDER_COLUMN_PERSONUSERID_PERSONUSERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(personUserId);

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

	private static final String _FINDER_COLUMN_PERSONUSERID_PERSONUSERID_2 =
		"loopPerson.personUserId = ?";

	public LoopPersonPersistenceImpl() {
		setModelClass(LoopPerson.class);
	}

	/**
	 * Caches the loop person in the entity cache if it is enabled.
	 *
	 * @param loopPerson the loop person
	 */
	@Override
	public void cacheResult(LoopPerson loopPerson) {
		entityCache.putResult(
			LoopPersonModelImpl.ENTITY_CACHE_ENABLED, LoopPersonImpl.class,
			loopPerson.getPrimaryKey(), loopPerson);

		finderCache.putResult(
			_finderPathFetchByPersonUserId,
			new Object[] {loopPerson.getPersonUserId()}, loopPerson);

		loopPerson.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the loop persons in the entity cache if it is enabled.
	 *
	 * @param loopPersons the loop persons
	 */
	@Override
	public void cacheResult(List<LoopPerson> loopPersons) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (loopPersons.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (LoopPerson loopPerson : loopPersons) {
			if (entityCache.getResult(
					LoopPersonModelImpl.ENTITY_CACHE_ENABLED,
					LoopPersonImpl.class, loopPerson.getPrimaryKey()) == null) {

				cacheResult(loopPerson);
			}
			else {
				loopPerson.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all loop persons.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LoopPersonImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the loop person.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LoopPerson loopPerson) {
		entityCache.removeResult(
			LoopPersonModelImpl.ENTITY_CACHE_ENABLED, LoopPersonImpl.class,
			loopPerson.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((LoopPersonModelImpl)loopPerson, true);
	}

	@Override
	public void clearCache(List<LoopPerson> loopPersons) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LoopPerson loopPerson : loopPersons) {
			entityCache.removeResult(
				LoopPersonModelImpl.ENTITY_CACHE_ENABLED, LoopPersonImpl.class,
				loopPerson.getPrimaryKey());

			clearUniqueFindersCache((LoopPersonModelImpl)loopPerson, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				LoopPersonModelImpl.ENTITY_CACHE_ENABLED, LoopPersonImpl.class,
				primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		LoopPersonModelImpl loopPersonModelImpl) {

		Object[] args = new Object[] {loopPersonModelImpl.getPersonUserId()};

		finderCache.putResult(
			_finderPathCountByPersonUserId, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByPersonUserId, args, loopPersonModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		LoopPersonModelImpl loopPersonModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				loopPersonModelImpl.getPersonUserId()
			};

			finderCache.removeResult(_finderPathCountByPersonUserId, args);
			finderCache.removeResult(_finderPathFetchByPersonUserId, args);
		}

		if ((loopPersonModelImpl.getColumnBitmask() &
			 _finderPathFetchByPersonUserId.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				loopPersonModelImpl.getOriginalPersonUserId()
			};

			finderCache.removeResult(_finderPathCountByPersonUserId, args);
			finderCache.removeResult(_finderPathFetchByPersonUserId, args);
		}
	}

	/**
	 * Creates a new loop person with the primary key. Does not add the loop person to the database.
	 *
	 * @param loopPersonId the primary key for the new loop person
	 * @return the new loop person
	 */
	@Override
	public LoopPerson create(long loopPersonId) {
		LoopPerson loopPerson = new LoopPersonImpl();

		loopPerson.setNew(true);
		loopPerson.setPrimaryKey(loopPersonId);

		loopPerson.setCompanyId(CompanyThreadLocal.getCompanyId());

		return loopPerson;
	}

	/**
	 * Removes the loop person with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param loopPersonId the primary key of the loop person
	 * @return the loop person that was removed
	 * @throws NoSuchLoopPersonException if a loop person with the primary key could not be found
	 */
	@Override
	public LoopPerson remove(long loopPersonId)
		throws NoSuchLoopPersonException {

		return remove((Serializable)loopPersonId);
	}

	/**
	 * Removes the loop person with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the loop person
	 * @return the loop person that was removed
	 * @throws NoSuchLoopPersonException if a loop person with the primary key could not be found
	 */
	@Override
	public LoopPerson remove(Serializable primaryKey)
		throws NoSuchLoopPersonException {

		Session session = null;

		try {
			session = openSession();

			LoopPerson loopPerson = (LoopPerson)session.get(
				LoopPersonImpl.class, primaryKey);

			if (loopPerson == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLoopPersonException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(loopPerson);
		}
		catch (NoSuchLoopPersonException noSuchEntityException) {
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
	protected LoopPerson removeImpl(LoopPerson loopPerson) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(loopPerson)) {
				loopPerson = (LoopPerson)session.get(
					LoopPersonImpl.class, loopPerson.getPrimaryKeyObj());
			}

			if (loopPerson != null) {
				session.delete(loopPerson);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (loopPerson != null) {
			clearCache(loopPerson);
		}

		return loopPerson;
	}

	@Override
	public LoopPerson updateImpl(LoopPerson loopPerson) {
		boolean isNew = loopPerson.isNew();

		if (!(loopPerson instanceof LoopPersonModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(loopPerson.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(loopPerson);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in loopPerson proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LoopPerson implementation " +
					loopPerson.getClass());
		}

		LoopPersonModelImpl loopPersonModelImpl =
			(LoopPersonModelImpl)loopPerson;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (loopPerson.getCreateDate() == null)) {
			if (serviceContext == null) {
				loopPerson.setCreateDate(date);
			}
			else {
				loopPerson.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!loopPersonModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				loopPerson.setModifiedDate(date);
			}
			else {
				loopPerson.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(loopPerson);

				loopPerson.setNew(false);
			}
			else {
				loopPerson = (LoopPerson)session.merge(loopPerson);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LoopPersonModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			LoopPersonModelImpl.ENTITY_CACHE_ENABLED, LoopPersonImpl.class,
			loopPerson.getPrimaryKey(), loopPerson, false);

		clearUniqueFindersCache(loopPersonModelImpl, false);
		cacheUniqueFindersCache(loopPersonModelImpl);

		loopPerson.resetOriginalValues();

		return loopPerson;
	}

	/**
	 * Returns the loop person with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the loop person
	 * @return the loop person
	 * @throws NoSuchLoopPersonException if a loop person with the primary key could not be found
	 */
	@Override
	public LoopPerson findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLoopPersonException {

		LoopPerson loopPerson = fetchByPrimaryKey(primaryKey);

		if (loopPerson == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLoopPersonException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return loopPerson;
	}

	/**
	 * Returns the loop person with the primary key or throws a <code>NoSuchLoopPersonException</code> if it could not be found.
	 *
	 * @param loopPersonId the primary key of the loop person
	 * @return the loop person
	 * @throws NoSuchLoopPersonException if a loop person with the primary key could not be found
	 */
	@Override
	public LoopPerson findByPrimaryKey(long loopPersonId)
		throws NoSuchLoopPersonException {

		return findByPrimaryKey((Serializable)loopPersonId);
	}

	/**
	 * Returns the loop person with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the loop person
	 * @return the loop person, or <code>null</code> if a loop person with the primary key could not be found
	 */
	@Override
	public LoopPerson fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			LoopPersonModelImpl.ENTITY_CACHE_ENABLED, LoopPersonImpl.class,
			primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		LoopPerson loopPerson = (LoopPerson)serializable;

		if (loopPerson == null) {
			Session session = null;

			try {
				session = openSession();

				loopPerson = (LoopPerson)session.get(
					LoopPersonImpl.class, primaryKey);

				if (loopPerson != null) {
					cacheResult(loopPerson);
				}
				else {
					entityCache.putResult(
						LoopPersonModelImpl.ENTITY_CACHE_ENABLED,
						LoopPersonImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					LoopPersonModelImpl.ENTITY_CACHE_ENABLED,
					LoopPersonImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return loopPerson;
	}

	/**
	 * Returns the loop person with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param loopPersonId the primary key of the loop person
	 * @return the loop person, or <code>null</code> if a loop person with the primary key could not be found
	 */
	@Override
	public LoopPerson fetchByPrimaryKey(long loopPersonId) {
		return fetchByPrimaryKey((Serializable)loopPersonId);
	}

	@Override
	public Map<Serializable, LoopPerson> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, LoopPerson> map =
			new HashMap<Serializable, LoopPerson>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			LoopPerson loopPerson = fetchByPrimaryKey(primaryKey);

			if (loopPerson != null) {
				map.put(primaryKey, loopPerson);
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
				LoopPersonModelImpl.ENTITY_CACHE_ENABLED, LoopPersonImpl.class,
				primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (LoopPerson)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_LOOPPERSON_WHERE_PKS_IN);

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

			for (LoopPerson loopPerson : (List<LoopPerson>)query.list()) {
				map.put(loopPerson.getPrimaryKeyObj(), loopPerson);

				cacheResult(loopPerson);

				uncachedPrimaryKeys.remove(loopPerson.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					LoopPersonModelImpl.ENTITY_CACHE_ENABLED,
					LoopPersonImpl.class, primaryKey, nullModel);
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
	 * Returns all the loop persons.
	 *
	 * @return the loop persons
	 */
	@Override
	public List<LoopPerson> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the loop persons.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopPersonModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop persons
	 * @param end the upper bound of the range of loop persons (not inclusive)
	 * @return the range of loop persons
	 */
	@Override
	public List<LoopPerson> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the loop persons.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopPersonModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop persons
	 * @param end the upper bound of the range of loop persons (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of loop persons
	 */
	@Override
	public List<LoopPerson> findAll(
		int start, int end, OrderByComparator<LoopPerson> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the loop persons.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopPersonModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop persons
	 * @param end the upper bound of the range of loop persons (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of loop persons
	 */
	@Override
	public List<LoopPerson> findAll(
		int start, int end, OrderByComparator<LoopPerson> orderByComparator,
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

		List<LoopPerson> list = null;

		if (useFinderCache) {
			list = (List<LoopPerson>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_LOOPPERSON);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_LOOPPERSON;

				sql = sql.concat(LoopPersonModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<LoopPerson>)QueryUtil.list(
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
	 * Removes all the loop persons from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LoopPerson loopPerson : findAll()) {
			remove(loopPerson);
		}
	}

	/**
	 * Returns the number of loop persons.
	 *
	 * @return the number of loop persons
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_LOOPPERSON);

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
		return LoopPersonModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the loop person persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			LoopPersonModelImpl.ENTITY_CACHE_ENABLED,
			LoopPersonModelImpl.FINDER_CACHE_ENABLED, LoopPersonImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			LoopPersonModelImpl.ENTITY_CACHE_ENABLED,
			LoopPersonModelImpl.FINDER_CACHE_ENABLED, LoopPersonImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			LoopPersonModelImpl.ENTITY_CACHE_ENABLED,
			LoopPersonModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByPersonUserId = new FinderPath(
			LoopPersonModelImpl.ENTITY_CACHE_ENABLED,
			LoopPersonModelImpl.FINDER_CACHE_ENABLED, LoopPersonImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByPersonUserId",
			new String[] {Long.class.getName()},
			LoopPersonModelImpl.PERSONUSERID_COLUMN_BITMASK);

		_finderPathCountByPersonUserId = new FinderPath(
			LoopPersonModelImpl.ENTITY_CACHE_ENABLED,
			LoopPersonModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPersonUserId",
			new String[] {Long.class.getName()});

		LoopPersonUtil.setPersistence(this);
	}

	public void destroy() {
		LoopPersonUtil.setPersistence(null);

		entityCache.removeCache(LoopPersonImpl.class.getName());

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

	private static final String _SQL_SELECT_LOOPPERSON =
		"SELECT loopPerson FROM LoopPerson loopPerson";

	private static final String _SQL_SELECT_LOOPPERSON_WHERE_PKS_IN =
		"SELECT loopPerson FROM LoopPerson loopPerson WHERE loopPersonId IN (";

	private static final String _SQL_SELECT_LOOPPERSON_WHERE =
		"SELECT loopPerson FROM LoopPerson loopPerson WHERE ";

	private static final String _SQL_COUNT_LOOPPERSON =
		"SELECT COUNT(loopPerson) FROM LoopPerson loopPerson";

	private static final String _SQL_COUNT_LOOPPERSON_WHERE =
		"SELECT COUNT(loopPerson) FROM LoopPerson loopPerson WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "loopPerson.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LoopPerson exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LoopPerson exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LoopPersonPersistenceImpl.class);

}