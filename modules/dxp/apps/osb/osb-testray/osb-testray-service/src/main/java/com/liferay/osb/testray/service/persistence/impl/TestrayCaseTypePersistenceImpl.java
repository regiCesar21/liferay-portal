/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.testray.service.persistence.impl;

import com.liferay.osb.testray.exception.NoSuchTestrayCaseTypeException;
import com.liferay.osb.testray.model.TestrayCaseType;
import com.liferay.osb.testray.model.impl.TestrayCaseTypeImpl;
import com.liferay.osb.testray.model.impl.TestrayCaseTypeModelImpl;
import com.liferay.osb.testray.service.persistence.TestrayCaseTypePersistence;
import com.liferay.osb.testray.service.persistence.TestrayCaseTypeUtil;
import com.liferay.osb.testray.service.persistence.TestrayTaskPersistence;
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
 * The persistence implementation for the testray case type service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ethan Bustad
 * @generated
 */
public class TestrayCaseTypePersistenceImpl
	extends BasePersistenceImpl<TestrayCaseType>
	implements TestrayCaseTypePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>TestrayCaseTypeUtil</code> to access the testray case type persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		TestrayCaseTypeImpl.class.getName();

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
	 * Returns the testray case type where groupId = &#63; and name = &#63; or throws a <code>NoSuchTestrayCaseTypeException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching testray case type
	 * @throws NoSuchTestrayCaseTypeException if a matching testray case type could not be found
	 */
	@Override
	public TestrayCaseType findByGI_N(long groupId, String name)
		throws NoSuchTestrayCaseTypeException {

		TestrayCaseType testrayCaseType = fetchByGI_N(groupId, name);

		if (testrayCaseType == null) {
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

			throw new NoSuchTestrayCaseTypeException(sb.toString());
		}

		return testrayCaseType;
	}

	/**
	 * Returns the testray case type where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching testray case type, or <code>null</code> if a matching testray case type could not be found
	 */
	@Override
	public TestrayCaseType fetchByGI_N(long groupId, String name) {
		return fetchByGI_N(groupId, name, true);
	}

	/**
	 * Returns the testray case type where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching testray case type, or <code>null</code> if a matching testray case type could not be found
	 */
	@Override
	public TestrayCaseType fetchByGI_N(
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

		if (result instanceof TestrayCaseType) {
			TestrayCaseType testrayCaseType = (TestrayCaseType)result;

			if ((groupId != testrayCaseType.getGroupId()) ||
				!Objects.equals(name, testrayCaseType.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_TESTRAYCASETYPE_WHERE);

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

				List<TestrayCaseType> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByGI_N, finderArgs, list);
					}
				}
				else {
					TestrayCaseType testrayCaseType = list.get(0);

					result = testrayCaseType;

					cacheResult(testrayCaseType);
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
			return (TestrayCaseType)result;
		}
	}

	/**
	 * Removes the testray case type where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the testray case type that was removed
	 */
	@Override
	public TestrayCaseType removeByGI_N(long groupId, String name)
		throws NoSuchTestrayCaseTypeException {

		TestrayCaseType testrayCaseType = findByGI_N(groupId, name);

		return remove(testrayCaseType);
	}

	/**
	 * Returns the number of testray case types where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching testray case types
	 */
	@Override
	public int countByGI_N(long groupId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByGI_N;

		Object[] finderArgs = new Object[] {groupId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_TESTRAYCASETYPE_WHERE);

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
		"testrayCaseType.groupId = ? AND ";

	private static final String _FINDER_COLUMN_GI_N_NAME_2 =
		"testrayCaseType.name = ?";

	private static final String _FINDER_COLUMN_GI_N_NAME_3 =
		"(testrayCaseType.name IS NULL OR testrayCaseType.name = '')";

	public TestrayCaseTypePersistenceImpl() {
		setModelClass(TestrayCaseType.class);
	}

	/**
	 * Caches the testray case type in the entity cache if it is enabled.
	 *
	 * @param testrayCaseType the testray case type
	 */
	@Override
	public void cacheResult(TestrayCaseType testrayCaseType) {
		entityCache.putResult(
			TestrayCaseTypeModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseTypeImpl.class, testrayCaseType.getPrimaryKey(),
			testrayCaseType);

		finderCache.putResult(
			_finderPathFetchByGI_N,
			new Object[] {
				testrayCaseType.getGroupId(), testrayCaseType.getName()
			},
			testrayCaseType);

		testrayCaseType.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the testray case types in the entity cache if it is enabled.
	 *
	 * @param testrayCaseTypes the testray case types
	 */
	@Override
	public void cacheResult(List<TestrayCaseType> testrayCaseTypes) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (testrayCaseTypes.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (TestrayCaseType testrayCaseType : testrayCaseTypes) {
			if (entityCache.getResult(
					TestrayCaseTypeModelImpl.ENTITY_CACHE_ENABLED,
					TestrayCaseTypeImpl.class,
					testrayCaseType.getPrimaryKey()) == null) {

				cacheResult(testrayCaseType);
			}
			else {
				testrayCaseType.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all testray case types.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(TestrayCaseTypeImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the testray case type.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(TestrayCaseType testrayCaseType) {
		entityCache.removeResult(
			TestrayCaseTypeModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseTypeImpl.class, testrayCaseType.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(TestrayCaseTypeModelImpl)testrayCaseType, true);
	}

	@Override
	public void clearCache(List<TestrayCaseType> testrayCaseTypes) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (TestrayCaseType testrayCaseType : testrayCaseTypes) {
			entityCache.removeResult(
				TestrayCaseTypeModelImpl.ENTITY_CACHE_ENABLED,
				TestrayCaseTypeImpl.class, testrayCaseType.getPrimaryKey());

			clearUniqueFindersCache(
				(TestrayCaseTypeModelImpl)testrayCaseType, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				TestrayCaseTypeModelImpl.ENTITY_CACHE_ENABLED,
				TestrayCaseTypeImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		TestrayCaseTypeModelImpl testrayCaseTypeModelImpl) {

		Object[] args = new Object[] {
			testrayCaseTypeModelImpl.getGroupId(),
			testrayCaseTypeModelImpl.getName()
		};

		finderCache.putResult(
			_finderPathCountByGI_N, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByGI_N, args, testrayCaseTypeModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		TestrayCaseTypeModelImpl testrayCaseTypeModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				testrayCaseTypeModelImpl.getGroupId(),
				testrayCaseTypeModelImpl.getName()
			};

			finderCache.removeResult(_finderPathCountByGI_N, args);
			finderCache.removeResult(_finderPathFetchByGI_N, args);
		}

		if ((testrayCaseTypeModelImpl.getColumnBitmask() &
			 _finderPathFetchByGI_N.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				testrayCaseTypeModelImpl.getOriginalGroupId(),
				testrayCaseTypeModelImpl.getOriginalName()
			};

			finderCache.removeResult(_finderPathCountByGI_N, args);
			finderCache.removeResult(_finderPathFetchByGI_N, args);
		}
	}

	/**
	 * Creates a new testray case type with the primary key. Does not add the testray case type to the database.
	 *
	 * @param testrayCaseTypeId the primary key for the new testray case type
	 * @return the new testray case type
	 */
	@Override
	public TestrayCaseType create(long testrayCaseTypeId) {
		TestrayCaseType testrayCaseType = new TestrayCaseTypeImpl();

		testrayCaseType.setNew(true);
		testrayCaseType.setPrimaryKey(testrayCaseTypeId);

		testrayCaseType.setCompanyId(CompanyThreadLocal.getCompanyId());

		return testrayCaseType;
	}

	/**
	 * Removes the testray case type with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param testrayCaseTypeId the primary key of the testray case type
	 * @return the testray case type that was removed
	 * @throws NoSuchTestrayCaseTypeException if a testray case type with the primary key could not be found
	 */
	@Override
	public TestrayCaseType remove(long testrayCaseTypeId)
		throws NoSuchTestrayCaseTypeException {

		return remove((Serializable)testrayCaseTypeId);
	}

	/**
	 * Removes the testray case type with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the testray case type
	 * @return the testray case type that was removed
	 * @throws NoSuchTestrayCaseTypeException if a testray case type with the primary key could not be found
	 */
	@Override
	public TestrayCaseType remove(Serializable primaryKey)
		throws NoSuchTestrayCaseTypeException {

		Session session = null;

		try {
			session = openSession();

			TestrayCaseType testrayCaseType = (TestrayCaseType)session.get(
				TestrayCaseTypeImpl.class, primaryKey);

			if (testrayCaseType == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTestrayCaseTypeException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(testrayCaseType);
		}
		catch (NoSuchTestrayCaseTypeException noSuchEntityException) {
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
	protected TestrayCaseType removeImpl(TestrayCaseType testrayCaseType) {
		testrayCaseTypeToTestrayTaskTableMapper.
			deleteLeftPrimaryKeyTableMappings(testrayCaseType.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(testrayCaseType)) {
				testrayCaseType = (TestrayCaseType)session.get(
					TestrayCaseTypeImpl.class,
					testrayCaseType.getPrimaryKeyObj());
			}

			if (testrayCaseType != null) {
				session.delete(testrayCaseType);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (testrayCaseType != null) {
			clearCache(testrayCaseType);
		}

		return testrayCaseType;
	}

	@Override
	public TestrayCaseType updateImpl(TestrayCaseType testrayCaseType) {
		boolean isNew = testrayCaseType.isNew();

		if (!(testrayCaseType instanceof TestrayCaseTypeModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(testrayCaseType.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					testrayCaseType);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in testrayCaseType proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom TestrayCaseType implementation " +
					testrayCaseType.getClass());
		}

		TestrayCaseTypeModelImpl testrayCaseTypeModelImpl =
			(TestrayCaseTypeModelImpl)testrayCaseType;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (testrayCaseType.getCreateDate() == null)) {
			if (serviceContext == null) {
				testrayCaseType.setCreateDate(date);
			}
			else {
				testrayCaseType.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!testrayCaseTypeModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				testrayCaseType.setModifiedDate(date);
			}
			else {
				testrayCaseType.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(testrayCaseType);

				testrayCaseType.setNew(false);
			}
			else {
				testrayCaseType = (TestrayCaseType)session.merge(
					testrayCaseType);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!TestrayCaseTypeModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			TestrayCaseTypeModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseTypeImpl.class, testrayCaseType.getPrimaryKey(),
			testrayCaseType, false);

		clearUniqueFindersCache(testrayCaseTypeModelImpl, false);
		cacheUniqueFindersCache(testrayCaseTypeModelImpl);

		testrayCaseType.resetOriginalValues();

		return testrayCaseType;
	}

	/**
	 * Returns the testray case type with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray case type
	 * @return the testray case type
	 * @throws NoSuchTestrayCaseTypeException if a testray case type with the primary key could not be found
	 */
	@Override
	public TestrayCaseType findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTestrayCaseTypeException {

		TestrayCaseType testrayCaseType = fetchByPrimaryKey(primaryKey);

		if (testrayCaseType == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTestrayCaseTypeException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return testrayCaseType;
	}

	/**
	 * Returns the testray case type with the primary key or throws a <code>NoSuchTestrayCaseTypeException</code> if it could not be found.
	 *
	 * @param testrayCaseTypeId the primary key of the testray case type
	 * @return the testray case type
	 * @throws NoSuchTestrayCaseTypeException if a testray case type with the primary key could not be found
	 */
	@Override
	public TestrayCaseType findByPrimaryKey(long testrayCaseTypeId)
		throws NoSuchTestrayCaseTypeException {

		return findByPrimaryKey((Serializable)testrayCaseTypeId);
	}

	/**
	 * Returns the testray case type with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the testray case type
	 * @return the testray case type, or <code>null</code> if a testray case type with the primary key could not be found
	 */
	@Override
	public TestrayCaseType fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			TestrayCaseTypeModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseTypeImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		TestrayCaseType testrayCaseType = (TestrayCaseType)serializable;

		if (testrayCaseType == null) {
			Session session = null;

			try {
				session = openSession();

				testrayCaseType = (TestrayCaseType)session.get(
					TestrayCaseTypeImpl.class, primaryKey);

				if (testrayCaseType != null) {
					cacheResult(testrayCaseType);
				}
				else {
					entityCache.putResult(
						TestrayCaseTypeModelImpl.ENTITY_CACHE_ENABLED,
						TestrayCaseTypeImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					TestrayCaseTypeModelImpl.ENTITY_CACHE_ENABLED,
					TestrayCaseTypeImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return testrayCaseType;
	}

	/**
	 * Returns the testray case type with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param testrayCaseTypeId the primary key of the testray case type
	 * @return the testray case type, or <code>null</code> if a testray case type with the primary key could not be found
	 */
	@Override
	public TestrayCaseType fetchByPrimaryKey(long testrayCaseTypeId) {
		return fetchByPrimaryKey((Serializable)testrayCaseTypeId);
	}

	@Override
	public Map<Serializable, TestrayCaseType> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, TestrayCaseType> map =
			new HashMap<Serializable, TestrayCaseType>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			TestrayCaseType testrayCaseType = fetchByPrimaryKey(primaryKey);

			if (testrayCaseType != null) {
				map.put(primaryKey, testrayCaseType);
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
				TestrayCaseTypeModelImpl.ENTITY_CACHE_ENABLED,
				TestrayCaseTypeImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (TestrayCaseType)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_TESTRAYCASETYPE_WHERE_PKS_IN);

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

			for (TestrayCaseType testrayCaseType :
					(List<TestrayCaseType>)query.list()) {

				map.put(testrayCaseType.getPrimaryKeyObj(), testrayCaseType);

				cacheResult(testrayCaseType);

				uncachedPrimaryKeys.remove(testrayCaseType.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					TestrayCaseTypeModelImpl.ENTITY_CACHE_ENABLED,
					TestrayCaseTypeImpl.class, primaryKey, nullModel);
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
	 * Returns all the testray case types.
	 *
	 * @return the testray case types
	 */
	@Override
	public List<TestrayCaseType> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the testray case types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseTypeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray case types
	 * @param end the upper bound of the range of testray case types (not inclusive)
	 * @return the range of testray case types
	 */
	@Override
	public List<TestrayCaseType> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray case types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseTypeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray case types
	 * @param end the upper bound of the range of testray case types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray case types
	 */
	@Override
	public List<TestrayCaseType> findAll(
		int start, int end,
		OrderByComparator<TestrayCaseType> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the testray case types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseTypeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of testray case types
	 * @param end the upper bound of the range of testray case types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of testray case types
	 */
	@Override
	public List<TestrayCaseType> findAll(
		int start, int end,
		OrderByComparator<TestrayCaseType> orderByComparator,
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

		List<TestrayCaseType> list = null;

		if (useFinderCache) {
			list = (List<TestrayCaseType>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_TESTRAYCASETYPE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_TESTRAYCASETYPE;

				sql = sql.concat(TestrayCaseTypeModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<TestrayCaseType>)QueryUtil.list(
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
	 * Removes all the testray case types from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (TestrayCaseType testrayCaseType : findAll()) {
			remove(testrayCaseType);
		}
	}

	/**
	 * Returns the number of testray case types.
	 *
	 * @return the number of testray case types
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_TESTRAYCASETYPE);

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
	 * Returns the primaryKeys of testray tasks associated with the testray case type.
	 *
	 * @param pk the primary key of the testray case type
	 * @return long[] of the primaryKeys of testray tasks associated with the testray case type
	 */
	@Override
	public long[] getTestrayTaskPrimaryKeys(long pk) {
		long[] pks =
			testrayCaseTypeToTestrayTaskTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the testray tasks associated with the testray case type.
	 *
	 * @param pk the primary key of the testray case type
	 * @return the testray tasks associated with the testray case type
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayTask> getTestrayTasks(
		long pk) {

		return getTestrayTasks(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the testray tasks associated with the testray case type.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseTypeModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray case type
	 * @param start the lower bound of the range of testray case types
	 * @param end the upper bound of the range of testray case types (not inclusive)
	 * @return the range of testray tasks associated with the testray case type
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayTask> getTestrayTasks(
		long pk, int start, int end) {

		return getTestrayTasks(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the testray tasks associated with the testray case type.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TestrayCaseTypeModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the testray case type
	 * @param start the lower bound of the range of testray case types
	 * @param end the upper bound of the range of testray case types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of testray tasks associated with the testray case type
	 */
	@Override
	public List<com.liferay.osb.testray.model.TestrayTask> getTestrayTasks(
		long pk, int start, int end,
		OrderByComparator<com.liferay.osb.testray.model.TestrayTask>
			orderByComparator) {

		return testrayCaseTypeToTestrayTaskTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of testray tasks associated with the testray case type.
	 *
	 * @param pk the primary key of the testray case type
	 * @return the number of testray tasks associated with the testray case type
	 */
	@Override
	public int getTestrayTasksSize(long pk) {
		long[] pks =
			testrayCaseTypeToTestrayTaskTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the testray task is associated with the testray case type.
	 *
	 * @param pk the primary key of the testray case type
	 * @param testrayTaskPK the primary key of the testray task
	 * @return <code>true</code> if the testray task is associated with the testray case type; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTestrayTask(long pk, long testrayTaskPK) {
		return testrayCaseTypeToTestrayTaskTableMapper.containsTableMapping(
			pk, testrayTaskPK);
	}

	/**
	 * Returns <code>true</code> if the testray case type has any testray tasks associated with it.
	 *
	 * @param pk the primary key of the testray case type to check for associations with testray tasks
	 * @return <code>true</code> if the testray case type has any testray tasks associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTestrayTasks(long pk) {
		if (getTestrayTasksSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the testray case type and the testray task. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case type
	 * @param testrayTaskPK the primary key of the testray task
	 */
	@Override
	public void addTestrayTask(long pk, long testrayTaskPK) {
		TestrayCaseType testrayCaseType = fetchByPrimaryKey(pk);

		if (testrayCaseType == null) {
			testrayCaseTypeToTestrayTaskTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, testrayTaskPK);
		}
		else {
			testrayCaseTypeToTestrayTaskTableMapper.addTableMapping(
				testrayCaseType.getCompanyId(), pk, testrayTaskPK);
		}
	}

	/**
	 * Adds an association between the testray case type and the testray task. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case type
	 * @param testrayTask the testray task
	 */
	@Override
	public void addTestrayTask(
		long pk, com.liferay.osb.testray.model.TestrayTask testrayTask) {

		TestrayCaseType testrayCaseType = fetchByPrimaryKey(pk);

		if (testrayCaseType == null) {
			testrayCaseTypeToTestrayTaskTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				testrayTask.getPrimaryKey());
		}
		else {
			testrayCaseTypeToTestrayTaskTableMapper.addTableMapping(
				testrayCaseType.getCompanyId(), pk,
				testrayTask.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the testray case type and the testray tasks. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case type
	 * @param testrayTaskPKs the primary keys of the testray tasks
	 */
	@Override
	public void addTestrayTasks(long pk, long[] testrayTaskPKs) {
		long companyId = 0;

		TestrayCaseType testrayCaseType = fetchByPrimaryKey(pk);

		if (testrayCaseType == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testrayCaseType.getCompanyId();
		}

		testrayCaseTypeToTestrayTaskTableMapper.addTableMappings(
			companyId, pk, testrayTaskPKs);
	}

	/**
	 * Adds an association between the testray case type and the testray tasks. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case type
	 * @param testrayTasks the testray tasks
	 */
	@Override
	public void addTestrayTasks(
		long pk, List<com.liferay.osb.testray.model.TestrayTask> testrayTasks) {

		addTestrayTasks(
			pk,
			ListUtil.toLongArray(
				testrayTasks,
				com.liferay.osb.testray.model.TestrayTask.
					TESTRAY_TASK_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the testray case type and its testray tasks. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case type to clear the associated testray tasks from
	 */
	@Override
	public void clearTestrayTasks(long pk) {
		testrayCaseTypeToTestrayTaskTableMapper.
			deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the testray case type and the testray task. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case type
	 * @param testrayTaskPK the primary key of the testray task
	 */
	@Override
	public void removeTestrayTask(long pk, long testrayTaskPK) {
		testrayCaseTypeToTestrayTaskTableMapper.deleteTableMapping(
			pk, testrayTaskPK);
	}

	/**
	 * Removes the association between the testray case type and the testray task. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case type
	 * @param testrayTask the testray task
	 */
	@Override
	public void removeTestrayTask(
		long pk, com.liferay.osb.testray.model.TestrayTask testrayTask) {

		testrayCaseTypeToTestrayTaskTableMapper.deleteTableMapping(
			pk, testrayTask.getPrimaryKey());
	}

	/**
	 * Removes the association between the testray case type and the testray tasks. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case type
	 * @param testrayTaskPKs the primary keys of the testray tasks
	 */
	@Override
	public void removeTestrayTasks(long pk, long[] testrayTaskPKs) {
		testrayCaseTypeToTestrayTaskTableMapper.deleteTableMappings(
			pk, testrayTaskPKs);
	}

	/**
	 * Removes the association between the testray case type and the testray tasks. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case type
	 * @param testrayTasks the testray tasks
	 */
	@Override
	public void removeTestrayTasks(
		long pk, List<com.liferay.osb.testray.model.TestrayTask> testrayTasks) {

		removeTestrayTasks(
			pk,
			ListUtil.toLongArray(
				testrayTasks,
				com.liferay.osb.testray.model.TestrayTask.
					TESTRAY_TASK_ID_ACCESSOR));
	}

	/**
	 * Sets the testray tasks associated with the testray case type, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case type
	 * @param testrayTaskPKs the primary keys of the testray tasks to be associated with the testray case type
	 */
	@Override
	public void setTestrayTasks(long pk, long[] testrayTaskPKs) {
		Set<Long> newTestrayTaskPKsSet = SetUtil.fromArray(testrayTaskPKs);
		Set<Long> oldTestrayTaskPKsSet = SetUtil.fromArray(
			testrayCaseTypeToTestrayTaskTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeTestrayTaskPKsSet = new HashSet<Long>(
			oldTestrayTaskPKsSet);

		removeTestrayTaskPKsSet.removeAll(newTestrayTaskPKsSet);

		testrayCaseTypeToTestrayTaskTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeTestrayTaskPKsSet));

		newTestrayTaskPKsSet.removeAll(oldTestrayTaskPKsSet);

		long companyId = 0;

		TestrayCaseType testrayCaseType = fetchByPrimaryKey(pk);

		if (testrayCaseType == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = testrayCaseType.getCompanyId();
		}

		testrayCaseTypeToTestrayTaskTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newTestrayTaskPKsSet));
	}

	/**
	 * Sets the testray tasks associated with the testray case type, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the testray case type
	 * @param testrayTasks the testray tasks to be associated with the testray case type
	 */
	@Override
	public void setTestrayTasks(
		long pk, List<com.liferay.osb.testray.model.TestrayTask> testrayTasks) {

		try {
			long[] testrayTaskPKs = new long[testrayTasks.size()];

			for (int i = 0; i < testrayTasks.size(); i++) {
				com.liferay.osb.testray.model.TestrayTask testrayTask =
					testrayTasks.get(i);

				testrayTaskPKs[i] = testrayTask.getPrimaryKey();
			}

			setTestrayTasks(pk, testrayTaskPKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return TestrayCaseTypeModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the testray case type persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		testrayCaseTypeToTestrayTaskTableMapper =
			TableMapperFactory.getTableMapper(
				"OSB_TestrayTasks_TestrayCaseTypes", "companyId",
				"testrayCaseTypeId", "testrayTaskId", this,
				testrayTaskPersistence);

		_finderPathWithPaginationFindAll = new FinderPath(
			TestrayCaseTypeModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseTypeModelImpl.FINDER_CACHE_ENABLED,
			TestrayCaseTypeImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			TestrayCaseTypeModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseTypeModelImpl.FINDER_CACHE_ENABLED,
			TestrayCaseTypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			TestrayCaseTypeModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseTypeModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByGI_N = new FinderPath(
			TestrayCaseTypeModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseTypeModelImpl.FINDER_CACHE_ENABLED,
			TestrayCaseTypeImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByGI_N",
			new String[] {Long.class.getName(), String.class.getName()},
			TestrayCaseTypeModelImpl.GROUPID_COLUMN_BITMASK |
			TestrayCaseTypeModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByGI_N = new FinderPath(
			TestrayCaseTypeModelImpl.ENTITY_CACHE_ENABLED,
			TestrayCaseTypeModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGI_N",
			new String[] {Long.class.getName(), String.class.getName()});

		TestrayCaseTypeUtil.setPersistence(this);
	}

	public void destroy() {
		TestrayCaseTypeUtil.setPersistence(null);

		entityCache.removeCache(TestrayCaseTypeImpl.class.getName());

		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper(
			"OSB_TestrayTasks_TestrayCaseTypes");
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

	@BeanReference(type = TestrayTaskPersistence.class)
	protected TestrayTaskPersistence testrayTaskPersistence;

	protected TableMapper
		<TestrayCaseType, com.liferay.osb.testray.model.TestrayTask>
			testrayCaseTypeToTestrayTaskTableMapper;

	private static final String _SQL_SELECT_TESTRAYCASETYPE =
		"SELECT testrayCaseType FROM TestrayCaseType testrayCaseType";

	private static final String _SQL_SELECT_TESTRAYCASETYPE_WHERE_PKS_IN =
		"SELECT testrayCaseType FROM TestrayCaseType testrayCaseType WHERE testrayCaseTypeId IN (";

	private static final String _SQL_SELECT_TESTRAYCASETYPE_WHERE =
		"SELECT testrayCaseType FROM TestrayCaseType testrayCaseType WHERE ";

	private static final String _SQL_COUNT_TESTRAYCASETYPE =
		"SELECT COUNT(testrayCaseType) FROM TestrayCaseType testrayCaseType";

	private static final String _SQL_COUNT_TESTRAYCASETYPE_WHERE =
		"SELECT COUNT(testrayCaseType) FROM TestrayCaseType testrayCaseType WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "testrayCaseType.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No TestrayCaseType exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No TestrayCaseType exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		TestrayCaseTypePersistenceImpl.class);

}