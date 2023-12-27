/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.zendesk.documentation.sync.service.persistence.impl;

import com.liferay.osb.customer.zendesk.documentation.sync.exception.NoSuchZendeskCategoryException;
import com.liferay.osb.customer.zendesk.documentation.sync.model.ZendeskCategory;
import com.liferay.osb.customer.zendesk.documentation.sync.model.impl.ZendeskCategoryImpl;
import com.liferay.osb.customer.zendesk.documentation.sync.model.impl.ZendeskCategoryModelImpl;
import com.liferay.osb.customer.zendesk.documentation.sync.service.persistence.ZendeskCategoryPersistence;
import com.liferay.osb.customer.zendesk.documentation.sync.service.persistence.ZendeskCategoryUtil;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the zendesk category service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ZendeskCategoryPersistenceImpl
	extends BasePersistenceImpl<ZendeskCategory>
	implements ZendeskCategoryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ZendeskCategoryUtil</code> to access the zendesk category persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ZendeskCategoryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByDocumentationKey;
	private FinderPath _finderPathCountByDocumentationKey;

	/**
	 * Returns the zendesk category where documentationKey = &#63; or throws a <code>NoSuchZendeskCategoryException</code> if it could not be found.
	 *
	 * @param documentationKey the documentation key
	 * @return the matching zendesk category
	 * @throws NoSuchZendeskCategoryException if a matching zendesk category could not be found
	 */
	@Override
	public ZendeskCategory findByDocumentationKey(String documentationKey)
		throws NoSuchZendeskCategoryException {

		ZendeskCategory zendeskCategory = fetchByDocumentationKey(
			documentationKey);

		if (zendeskCategory == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("documentationKey=");
			sb.append(documentationKey);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchZendeskCategoryException(sb.toString());
		}

		return zendeskCategory;
	}

	/**
	 * Returns the zendesk category where documentationKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param documentationKey the documentation key
	 * @return the matching zendesk category, or <code>null</code> if a matching zendesk category could not be found
	 */
	@Override
	public ZendeskCategory fetchByDocumentationKey(String documentationKey) {
		return fetchByDocumentationKey(documentationKey, true);
	}

	/**
	 * Returns the zendesk category where documentationKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param documentationKey the documentation key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching zendesk category, or <code>null</code> if a matching zendesk category could not be found
	 */
	@Override
	public ZendeskCategory fetchByDocumentationKey(
		String documentationKey, boolean useFinderCache) {

		documentationKey = Objects.toString(documentationKey, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {documentationKey};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByDocumentationKey, finderArgs, this);
		}

		if (result instanceof ZendeskCategory) {
			ZendeskCategory zendeskCategory = (ZendeskCategory)result;

			if (!Objects.equals(
					documentationKey, zendeskCategory.getDocumentationKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_ZENDESKCATEGORY_WHERE);

			boolean bindDocumentationKey = false;

			if (documentationKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_DOCUMENTATIONKEY_DOCUMENTATIONKEY_3);
			}
			else {
				bindDocumentationKey = true;

				sb.append(_FINDER_COLUMN_DOCUMENTATIONKEY_DOCUMENTATIONKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindDocumentationKey) {
					queryPos.add(documentationKey);
				}

				List<ZendeskCategory> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByDocumentationKey, finderArgs,
							list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {documentationKey};
							}

							_log.warn(
								"ZendeskCategoryPersistenceImpl.fetchByDocumentationKey(String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					ZendeskCategory zendeskCategory = list.get(0);

					result = zendeskCategory;

					cacheResult(zendeskCategory);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByDocumentationKey, finderArgs);
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
			return (ZendeskCategory)result;
		}
	}

	/**
	 * Removes the zendesk category where documentationKey = &#63; from the database.
	 *
	 * @param documentationKey the documentation key
	 * @return the zendesk category that was removed
	 */
	@Override
	public ZendeskCategory removeByDocumentationKey(String documentationKey)
		throws NoSuchZendeskCategoryException {

		ZendeskCategory zendeskCategory = findByDocumentationKey(
			documentationKey);

		return remove(zendeskCategory);
	}

	/**
	 * Returns the number of zendesk categories where documentationKey = &#63;.
	 *
	 * @param documentationKey the documentation key
	 * @return the number of matching zendesk categories
	 */
	@Override
	public int countByDocumentationKey(String documentationKey) {
		documentationKey = Objects.toString(documentationKey, "");

		FinderPath finderPath = _finderPathCountByDocumentationKey;

		Object[] finderArgs = new Object[] {documentationKey};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ZENDESKCATEGORY_WHERE);

			boolean bindDocumentationKey = false;

			if (documentationKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_DOCUMENTATIONKEY_DOCUMENTATIONKEY_3);
			}
			else {
				bindDocumentationKey = true;

				sb.append(_FINDER_COLUMN_DOCUMENTATIONKEY_DOCUMENTATIONKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindDocumentationKey) {
					queryPos.add(documentationKey);
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

	private static final String
		_FINDER_COLUMN_DOCUMENTATIONKEY_DOCUMENTATIONKEY_2 =
			"zendeskCategory.documentationKey = ?";

	private static final String
		_FINDER_COLUMN_DOCUMENTATIONKEY_DOCUMENTATIONKEY_3 =
			"(zendeskCategory.documentationKey IS NULL OR zendeskCategory.documentationKey = '')";

	public ZendeskCategoryPersistenceImpl() {
		setModelClass(ZendeskCategory.class);
	}

	/**
	 * Caches the zendesk category in the entity cache if it is enabled.
	 *
	 * @param zendeskCategory the zendesk category
	 */
	@Override
	public void cacheResult(ZendeskCategory zendeskCategory) {
		entityCache.putResult(
			ZendeskCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskCategoryImpl.class, zendeskCategory.getPrimaryKey(),
			zendeskCategory);

		finderCache.putResult(
			_finderPathFetchByDocumentationKey,
			new Object[] {zendeskCategory.getDocumentationKey()},
			zendeskCategory);

		zendeskCategory.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the zendesk categories in the entity cache if it is enabled.
	 *
	 * @param zendeskCategories the zendesk categories
	 */
	@Override
	public void cacheResult(List<ZendeskCategory> zendeskCategories) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (zendeskCategories.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ZendeskCategory zendeskCategory : zendeskCategories) {
			if (entityCache.getResult(
					ZendeskCategoryModelImpl.ENTITY_CACHE_ENABLED,
					ZendeskCategoryImpl.class,
					zendeskCategory.getPrimaryKey()) == null) {

				cacheResult(zendeskCategory);
			}
			else {
				zendeskCategory.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all zendesk categories.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ZendeskCategoryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the zendesk category.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ZendeskCategory zendeskCategory) {
		entityCache.removeResult(
			ZendeskCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskCategoryImpl.class, zendeskCategory.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(ZendeskCategoryModelImpl)zendeskCategory, true);
	}

	@Override
	public void clearCache(List<ZendeskCategory> zendeskCategories) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ZendeskCategory zendeskCategory : zendeskCategories) {
			entityCache.removeResult(
				ZendeskCategoryModelImpl.ENTITY_CACHE_ENABLED,
				ZendeskCategoryImpl.class, zendeskCategory.getPrimaryKey());

			clearUniqueFindersCache(
				(ZendeskCategoryModelImpl)zendeskCategory, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				ZendeskCategoryModelImpl.ENTITY_CACHE_ENABLED,
				ZendeskCategoryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ZendeskCategoryModelImpl zendeskCategoryModelImpl) {

		Object[] args = new Object[] {
			zendeskCategoryModelImpl.getDocumentationKey()
		};

		finderCache.putResult(
			_finderPathCountByDocumentationKey, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByDocumentationKey, args, zendeskCategoryModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		ZendeskCategoryModelImpl zendeskCategoryModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				zendeskCategoryModelImpl.getDocumentationKey()
			};

			finderCache.removeResult(_finderPathCountByDocumentationKey, args);
			finderCache.removeResult(_finderPathFetchByDocumentationKey, args);
		}

		if ((zendeskCategoryModelImpl.getColumnBitmask() &
			 _finderPathFetchByDocumentationKey.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				zendeskCategoryModelImpl.getOriginalDocumentationKey()
			};

			finderCache.removeResult(_finderPathCountByDocumentationKey, args);
			finderCache.removeResult(_finderPathFetchByDocumentationKey, args);
		}
	}

	/**
	 * Creates a new zendesk category with the primary key. Does not add the zendesk category to the database.
	 *
	 * @param zendeskCategoryId the primary key for the new zendesk category
	 * @return the new zendesk category
	 */
	@Override
	public ZendeskCategory create(long zendeskCategoryId) {
		ZendeskCategory zendeskCategory = new ZendeskCategoryImpl();

		zendeskCategory.setNew(true);
		zendeskCategory.setPrimaryKey(zendeskCategoryId);

		return zendeskCategory;
	}

	/**
	 * Removes the zendesk category with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param zendeskCategoryId the primary key of the zendesk category
	 * @return the zendesk category that was removed
	 * @throws NoSuchZendeskCategoryException if a zendesk category with the primary key could not be found
	 */
	@Override
	public ZendeskCategory remove(long zendeskCategoryId)
		throws NoSuchZendeskCategoryException {

		return remove((Serializable)zendeskCategoryId);
	}

	/**
	 * Removes the zendesk category with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the zendesk category
	 * @return the zendesk category that was removed
	 * @throws NoSuchZendeskCategoryException if a zendesk category with the primary key could not be found
	 */
	@Override
	public ZendeskCategory remove(Serializable primaryKey)
		throws NoSuchZendeskCategoryException {

		Session session = null;

		try {
			session = openSession();

			ZendeskCategory zendeskCategory = (ZendeskCategory)session.get(
				ZendeskCategoryImpl.class, primaryKey);

			if (zendeskCategory == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchZendeskCategoryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(zendeskCategory);
		}
		catch (NoSuchZendeskCategoryException noSuchEntityException) {
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
	protected ZendeskCategory removeImpl(ZendeskCategory zendeskCategory) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(zendeskCategory)) {
				zendeskCategory = (ZendeskCategory)session.get(
					ZendeskCategoryImpl.class,
					zendeskCategory.getPrimaryKeyObj());
			}

			if (zendeskCategory != null) {
				session.delete(zendeskCategory);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (zendeskCategory != null) {
			clearCache(zendeskCategory);
		}

		return zendeskCategory;
	}

	@Override
	public ZendeskCategory updateImpl(ZendeskCategory zendeskCategory) {
		boolean isNew = zendeskCategory.isNew();

		if (!(zendeskCategory instanceof ZendeskCategoryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(zendeskCategory.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					zendeskCategory);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in zendeskCategory proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ZendeskCategory implementation " +
					zendeskCategory.getClass());
		}

		ZendeskCategoryModelImpl zendeskCategoryModelImpl =
			(ZendeskCategoryModelImpl)zendeskCategory;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(zendeskCategory);

				zendeskCategory.setNew(false);
			}
			else {
				zendeskCategory = (ZendeskCategory)session.merge(
					zendeskCategory);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!ZendeskCategoryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			ZendeskCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskCategoryImpl.class, zendeskCategory.getPrimaryKey(),
			zendeskCategory, false);

		clearUniqueFindersCache(zendeskCategoryModelImpl, false);
		cacheUniqueFindersCache(zendeskCategoryModelImpl);

		zendeskCategory.resetOriginalValues();

		return zendeskCategory;
	}

	/**
	 * Returns the zendesk category with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the zendesk category
	 * @return the zendesk category
	 * @throws NoSuchZendeskCategoryException if a zendesk category with the primary key could not be found
	 */
	@Override
	public ZendeskCategory findByPrimaryKey(Serializable primaryKey)
		throws NoSuchZendeskCategoryException {

		ZendeskCategory zendeskCategory = fetchByPrimaryKey(primaryKey);

		if (zendeskCategory == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchZendeskCategoryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return zendeskCategory;
	}

	/**
	 * Returns the zendesk category with the primary key or throws a <code>NoSuchZendeskCategoryException</code> if it could not be found.
	 *
	 * @param zendeskCategoryId the primary key of the zendesk category
	 * @return the zendesk category
	 * @throws NoSuchZendeskCategoryException if a zendesk category with the primary key could not be found
	 */
	@Override
	public ZendeskCategory findByPrimaryKey(long zendeskCategoryId)
		throws NoSuchZendeskCategoryException {

		return findByPrimaryKey((Serializable)zendeskCategoryId);
	}

	/**
	 * Returns the zendesk category with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the zendesk category
	 * @return the zendesk category, or <code>null</code> if a zendesk category with the primary key could not be found
	 */
	@Override
	public ZendeskCategory fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			ZendeskCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskCategoryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		ZendeskCategory zendeskCategory = (ZendeskCategory)serializable;

		if (zendeskCategory == null) {
			Session session = null;

			try {
				session = openSession();

				zendeskCategory = (ZendeskCategory)session.get(
					ZendeskCategoryImpl.class, primaryKey);

				if (zendeskCategory != null) {
					cacheResult(zendeskCategory);
				}
				else {
					entityCache.putResult(
						ZendeskCategoryModelImpl.ENTITY_CACHE_ENABLED,
						ZendeskCategoryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					ZendeskCategoryModelImpl.ENTITY_CACHE_ENABLED,
					ZendeskCategoryImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return zendeskCategory;
	}

	/**
	 * Returns the zendesk category with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param zendeskCategoryId the primary key of the zendesk category
	 * @return the zendesk category, or <code>null</code> if a zendesk category with the primary key could not be found
	 */
	@Override
	public ZendeskCategory fetchByPrimaryKey(long zendeskCategoryId) {
		return fetchByPrimaryKey((Serializable)zendeskCategoryId);
	}

	@Override
	public Map<Serializable, ZendeskCategory> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ZendeskCategory> map =
			new HashMap<Serializable, ZendeskCategory>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ZendeskCategory zendeskCategory = fetchByPrimaryKey(primaryKey);

			if (zendeskCategory != null) {
				map.put(primaryKey, zendeskCategory);
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
				ZendeskCategoryModelImpl.ENTITY_CACHE_ENABLED,
				ZendeskCategoryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (ZendeskCategory)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_ZENDESKCATEGORY_WHERE_PKS_IN);

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

			for (ZendeskCategory zendeskCategory :
					(List<ZendeskCategory>)query.list()) {

				map.put(zendeskCategory.getPrimaryKeyObj(), zendeskCategory);

				cacheResult(zendeskCategory);

				uncachedPrimaryKeys.remove(zendeskCategory.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					ZendeskCategoryModelImpl.ENTITY_CACHE_ENABLED,
					ZendeskCategoryImpl.class, primaryKey, nullModel);
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
	 * Returns all the zendesk categories.
	 *
	 * @return the zendesk categories
	 */
	@Override
	public List<ZendeskCategory> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the zendesk categories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ZendeskCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of zendesk categories
	 * @param end the upper bound of the range of zendesk categories (not inclusive)
	 * @return the range of zendesk categories
	 */
	@Override
	public List<ZendeskCategory> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the zendesk categories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ZendeskCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of zendesk categories
	 * @param end the upper bound of the range of zendesk categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of zendesk categories
	 */
	@Override
	public List<ZendeskCategory> findAll(
		int start, int end,
		OrderByComparator<ZendeskCategory> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the zendesk categories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ZendeskCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of zendesk categories
	 * @param end the upper bound of the range of zendesk categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of zendesk categories
	 */
	@Override
	public List<ZendeskCategory> findAll(
		int start, int end,
		OrderByComparator<ZendeskCategory> orderByComparator,
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

		List<ZendeskCategory> list = null;

		if (useFinderCache) {
			list = (List<ZendeskCategory>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ZENDESKCATEGORY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ZENDESKCATEGORY;

				sql = sql.concat(ZendeskCategoryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ZendeskCategory>)QueryUtil.list(
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
	 * Removes all the zendesk categories from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ZendeskCategory zendeskCategory : findAll()) {
			remove(zendeskCategory);
		}
	}

	/**
	 * Returns the number of zendesk categories.
	 *
	 * @return the number of zendesk categories
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_ZENDESKCATEGORY);

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
		return ZendeskCategoryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the zendesk category persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			ZendeskCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskCategoryModelImpl.FINDER_CACHE_ENABLED,
			ZendeskCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			ZendeskCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskCategoryModelImpl.FINDER_CACHE_ENABLED,
			ZendeskCategoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			ZendeskCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByDocumentationKey = new FinderPath(
			ZendeskCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskCategoryModelImpl.FINDER_CACHE_ENABLED,
			ZendeskCategoryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByDocumentationKey", new String[] {String.class.getName()},
			ZendeskCategoryModelImpl.DOCUMENTATIONKEY_COLUMN_BITMASK);

		_finderPathCountByDocumentationKey = new FinderPath(
			ZendeskCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByDocumentationKey", new String[] {String.class.getName()});

		ZendeskCategoryUtil.setPersistence(this);
	}

	public void destroy() {
		ZendeskCategoryUtil.setPersistence(null);

		entityCache.removeCache(ZendeskCategoryImpl.class.getName());

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

	private static final String _SQL_SELECT_ZENDESKCATEGORY =
		"SELECT zendeskCategory FROM ZendeskCategory zendeskCategory";

	private static final String _SQL_SELECT_ZENDESKCATEGORY_WHERE_PKS_IN =
		"SELECT zendeskCategory FROM ZendeskCategory zendeskCategory WHERE zendeskCategoryId IN (";

	private static final String _SQL_SELECT_ZENDESKCATEGORY_WHERE =
		"SELECT zendeskCategory FROM ZendeskCategory zendeskCategory WHERE ";

	private static final String _SQL_COUNT_ZENDESKCATEGORY =
		"SELECT COUNT(zendeskCategory) FROM ZendeskCategory zendeskCategory";

	private static final String _SQL_COUNT_ZENDESKCATEGORY_WHERE =
		"SELECT COUNT(zendeskCategory) FROM ZendeskCategory zendeskCategory WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "zendeskCategory.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ZendeskCategory exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ZendeskCategory exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ZendeskCategoryPersistenceImpl.class);

}