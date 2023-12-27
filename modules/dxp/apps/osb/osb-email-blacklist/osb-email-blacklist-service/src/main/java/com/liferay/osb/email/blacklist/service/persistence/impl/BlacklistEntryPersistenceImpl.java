/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.email.blacklist.service.persistence.impl;

import com.liferay.osb.email.blacklist.exception.NoSuchBlacklistEntryException;
import com.liferay.osb.email.blacklist.model.BlacklistEntry;
import com.liferay.osb.email.blacklist.model.impl.BlacklistEntryImpl;
import com.liferay.osb.email.blacklist.model.impl.BlacklistEntryModelImpl;
import com.liferay.osb.email.blacklist.service.persistence.BlacklistEntryPersistence;
import com.liferay.osb.email.blacklist.service.persistence.BlacklistEntryUtil;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
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
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the blacklist entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Jamie Sammons
 * @generated
 */
public class BlacklistEntryPersistenceImpl
	extends BasePersistenceImpl<BlacklistEntry>
	implements BlacklistEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>BlacklistEntryUtil</code> to access the blacklist entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		BlacklistEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByEmailAddress;
	private FinderPath _finderPathCountByEmailAddress;

	/**
	 * Returns the blacklist entry where emailAddress = &#63; or throws a <code>NoSuchBlacklistEntryException</code> if it could not be found.
	 *
	 * @param emailAddress the email address
	 * @return the matching blacklist entry
	 * @throws NoSuchBlacklistEntryException if a matching blacklist entry could not be found
	 */
	@Override
	public BlacklistEntry findByEmailAddress(String emailAddress)
		throws NoSuchBlacklistEntryException {

		BlacklistEntry blacklistEntry = fetchByEmailAddress(emailAddress);

		if (blacklistEntry == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("emailAddress=");
			sb.append(emailAddress);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchBlacklistEntryException(sb.toString());
		}

		return blacklistEntry;
	}

	/**
	 * Returns the blacklist entry where emailAddress = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param emailAddress the email address
	 * @return the matching blacklist entry, or <code>null</code> if a matching blacklist entry could not be found
	 */
	@Override
	public BlacklistEntry fetchByEmailAddress(String emailAddress) {
		return fetchByEmailAddress(emailAddress, true);
	}

	/**
	 * Returns the blacklist entry where emailAddress = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param emailAddress the email address
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching blacklist entry, or <code>null</code> if a matching blacklist entry could not be found
	 */
	@Override
	public BlacklistEntry fetchByEmailAddress(
		String emailAddress, boolean useFinderCache) {

		emailAddress = Objects.toString(emailAddress, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {emailAddress};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByEmailAddress, finderArgs, this);
		}

		if (result instanceof BlacklistEntry) {
			BlacklistEntry blacklistEntry = (BlacklistEntry)result;

			if (!Objects.equals(
					emailAddress, blacklistEntry.getEmailAddress())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_BLACKLISTENTRY_WHERE);

			boolean bindEmailAddress = false;

			if (emailAddress.isEmpty()) {
				sb.append(_FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_3);
			}
			else {
				bindEmailAddress = true;

				sb.append(_FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindEmailAddress) {
					queryPos.add(emailAddress);
				}

				List<BlacklistEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByEmailAddress, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {emailAddress};
							}

							_log.warn(
								"BlacklistEntryPersistenceImpl.fetchByEmailAddress(String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					BlacklistEntry blacklistEntry = list.get(0);

					result = blacklistEntry;

					cacheResult(blacklistEntry);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByEmailAddress, finderArgs);
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
			return (BlacklistEntry)result;
		}
	}

	/**
	 * Removes the blacklist entry where emailAddress = &#63; from the database.
	 *
	 * @param emailAddress the email address
	 * @return the blacklist entry that was removed
	 */
	@Override
	public BlacklistEntry removeByEmailAddress(String emailAddress)
		throws NoSuchBlacklistEntryException {

		BlacklistEntry blacklistEntry = findByEmailAddress(emailAddress);

		return remove(blacklistEntry);
	}

	/**
	 * Returns the number of blacklist entries where emailAddress = &#63;.
	 *
	 * @param emailAddress the email address
	 * @return the number of matching blacklist entries
	 */
	@Override
	public int countByEmailAddress(String emailAddress) {
		emailAddress = Objects.toString(emailAddress, "");

		FinderPath finderPath = _finderPathCountByEmailAddress;

		Object[] finderArgs = new Object[] {emailAddress};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_BLACKLISTENTRY_WHERE);

			boolean bindEmailAddress = false;

			if (emailAddress.isEmpty()) {
				sb.append(_FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_3);
			}
			else {
				bindEmailAddress = true;

				sb.append(_FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindEmailAddress) {
					queryPos.add(emailAddress);
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

	private static final String _FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_2 =
		"blacklistEntry.emailAddress = ?";

	private static final String _FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_3 =
		"(blacklistEntry.emailAddress IS NULL OR blacklistEntry.emailAddress = '')";

	public BlacklistEntryPersistenceImpl() {
		setModelClass(BlacklistEntry.class);
	}

	/**
	 * Caches the blacklist entry in the entity cache if it is enabled.
	 *
	 * @param blacklistEntry the blacklist entry
	 */
	@Override
	public void cacheResult(BlacklistEntry blacklistEntry) {
		entityCache.putResult(
			BlacklistEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlacklistEntryImpl.class, blacklistEntry.getPrimaryKey(),
			blacklistEntry);

		finderCache.putResult(
			_finderPathFetchByEmailAddress,
			new Object[] {blacklistEntry.getEmailAddress()}, blacklistEntry);

		blacklistEntry.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the blacklist entries in the entity cache if it is enabled.
	 *
	 * @param blacklistEntries the blacklist entries
	 */
	@Override
	public void cacheResult(List<BlacklistEntry> blacklistEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (blacklistEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (BlacklistEntry blacklistEntry : blacklistEntries) {
			if (entityCache.getResult(
					BlacklistEntryModelImpl.ENTITY_CACHE_ENABLED,
					BlacklistEntryImpl.class, blacklistEntry.getPrimaryKey()) ==
						null) {

				cacheResult(blacklistEntry);
			}
			else {
				blacklistEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all blacklist entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(BlacklistEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the blacklist entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(BlacklistEntry blacklistEntry) {
		entityCache.removeResult(
			BlacklistEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlacklistEntryImpl.class, blacklistEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((BlacklistEntryModelImpl)blacklistEntry, true);
	}

	@Override
	public void clearCache(List<BlacklistEntry> blacklistEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (BlacklistEntry blacklistEntry : blacklistEntries) {
			entityCache.removeResult(
				BlacklistEntryModelImpl.ENTITY_CACHE_ENABLED,
				BlacklistEntryImpl.class, blacklistEntry.getPrimaryKey());

			clearUniqueFindersCache(
				(BlacklistEntryModelImpl)blacklistEntry, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				BlacklistEntryModelImpl.ENTITY_CACHE_ENABLED,
				BlacklistEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		BlacklistEntryModelImpl blacklistEntryModelImpl) {

		Object[] args = new Object[] {
			blacklistEntryModelImpl.getEmailAddress()
		};

		finderCache.putResult(
			_finderPathCountByEmailAddress, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByEmailAddress, args, blacklistEntryModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		BlacklistEntryModelImpl blacklistEntryModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				blacklistEntryModelImpl.getEmailAddress()
			};

			finderCache.removeResult(_finderPathCountByEmailAddress, args);
			finderCache.removeResult(_finderPathFetchByEmailAddress, args);
		}

		if ((blacklistEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByEmailAddress.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				blacklistEntryModelImpl.getOriginalEmailAddress()
			};

			finderCache.removeResult(_finderPathCountByEmailAddress, args);
			finderCache.removeResult(_finderPathFetchByEmailAddress, args);
		}
	}

	/**
	 * Creates a new blacklist entry with the primary key. Does not add the blacklist entry to the database.
	 *
	 * @param blacklistEntryId the primary key for the new blacklist entry
	 * @return the new blacklist entry
	 */
	@Override
	public BlacklistEntry create(long blacklistEntryId) {
		BlacklistEntry blacklistEntry = new BlacklistEntryImpl();

		blacklistEntry.setNew(true);
		blacklistEntry.setPrimaryKey(blacklistEntryId);

		return blacklistEntry;
	}

	/**
	 * Removes the blacklist entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param blacklistEntryId the primary key of the blacklist entry
	 * @return the blacklist entry that was removed
	 * @throws NoSuchBlacklistEntryException if a blacklist entry with the primary key could not be found
	 */
	@Override
	public BlacklistEntry remove(long blacklistEntryId)
		throws NoSuchBlacklistEntryException {

		return remove((Serializable)blacklistEntryId);
	}

	/**
	 * Removes the blacklist entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the blacklist entry
	 * @return the blacklist entry that was removed
	 * @throws NoSuchBlacklistEntryException if a blacklist entry with the primary key could not be found
	 */
	@Override
	public BlacklistEntry remove(Serializable primaryKey)
		throws NoSuchBlacklistEntryException {

		Session session = null;

		try {
			session = openSession();

			BlacklistEntry blacklistEntry = (BlacklistEntry)session.get(
				BlacklistEntryImpl.class, primaryKey);

			if (blacklistEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchBlacklistEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(blacklistEntry);
		}
		catch (NoSuchBlacklistEntryException noSuchEntityException) {
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
	protected BlacklistEntry removeImpl(BlacklistEntry blacklistEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(blacklistEntry)) {
				blacklistEntry = (BlacklistEntry)session.get(
					BlacklistEntryImpl.class,
					blacklistEntry.getPrimaryKeyObj());
			}

			if (blacklistEntry != null) {
				session.delete(blacklistEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (blacklistEntry != null) {
			clearCache(blacklistEntry);
		}

		return blacklistEntry;
	}

	@Override
	public BlacklistEntry updateImpl(BlacklistEntry blacklistEntry) {
		boolean isNew = blacklistEntry.isNew();

		if (!(blacklistEntry instanceof BlacklistEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(blacklistEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					blacklistEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in blacklistEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom BlacklistEntry implementation " +
					blacklistEntry.getClass());
		}

		BlacklistEntryModelImpl blacklistEntryModelImpl =
			(BlacklistEntryModelImpl)blacklistEntry;

		if (isNew && (blacklistEntry.getCreateDate() == null)) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Date date = new Date();

			if (serviceContext == null) {
				blacklistEntry.setCreateDate(date);
			}
			else {
				blacklistEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(blacklistEntry);

				blacklistEntry.setNew(false);
			}
			else {
				blacklistEntry = (BlacklistEntry)session.merge(blacklistEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!BlacklistEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			BlacklistEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlacklistEntryImpl.class, blacklistEntry.getPrimaryKey(),
			blacklistEntry, false);

		clearUniqueFindersCache(blacklistEntryModelImpl, false);
		cacheUniqueFindersCache(blacklistEntryModelImpl);

		blacklistEntry.resetOriginalValues();

		return blacklistEntry;
	}

	/**
	 * Returns the blacklist entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the blacklist entry
	 * @return the blacklist entry
	 * @throws NoSuchBlacklistEntryException if a blacklist entry with the primary key could not be found
	 */
	@Override
	public BlacklistEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchBlacklistEntryException {

		BlacklistEntry blacklistEntry = fetchByPrimaryKey(primaryKey);

		if (blacklistEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchBlacklistEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return blacklistEntry;
	}

	/**
	 * Returns the blacklist entry with the primary key or throws a <code>NoSuchBlacklistEntryException</code> if it could not be found.
	 *
	 * @param blacklistEntryId the primary key of the blacklist entry
	 * @return the blacklist entry
	 * @throws NoSuchBlacklistEntryException if a blacklist entry with the primary key could not be found
	 */
	@Override
	public BlacklistEntry findByPrimaryKey(long blacklistEntryId)
		throws NoSuchBlacklistEntryException {

		return findByPrimaryKey((Serializable)blacklistEntryId);
	}

	/**
	 * Returns the blacklist entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the blacklist entry
	 * @return the blacklist entry, or <code>null</code> if a blacklist entry with the primary key could not be found
	 */
	@Override
	public BlacklistEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			BlacklistEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlacklistEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		BlacklistEntry blacklistEntry = (BlacklistEntry)serializable;

		if (blacklistEntry == null) {
			Session session = null;

			try {
				session = openSession();

				blacklistEntry = (BlacklistEntry)session.get(
					BlacklistEntryImpl.class, primaryKey);

				if (blacklistEntry != null) {
					cacheResult(blacklistEntry);
				}
				else {
					entityCache.putResult(
						BlacklistEntryModelImpl.ENTITY_CACHE_ENABLED,
						BlacklistEntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					BlacklistEntryModelImpl.ENTITY_CACHE_ENABLED,
					BlacklistEntryImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return blacklistEntry;
	}

	/**
	 * Returns the blacklist entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param blacklistEntryId the primary key of the blacklist entry
	 * @return the blacklist entry, or <code>null</code> if a blacklist entry with the primary key could not be found
	 */
	@Override
	public BlacklistEntry fetchByPrimaryKey(long blacklistEntryId) {
		return fetchByPrimaryKey((Serializable)blacklistEntryId);
	}

	@Override
	public Map<Serializable, BlacklistEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, BlacklistEntry> map =
			new HashMap<Serializable, BlacklistEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			BlacklistEntry blacklistEntry = fetchByPrimaryKey(primaryKey);

			if (blacklistEntry != null) {
				map.put(primaryKey, blacklistEntry);
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
				BlacklistEntryModelImpl.ENTITY_CACHE_ENABLED,
				BlacklistEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (BlacklistEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_BLACKLISTENTRY_WHERE_PKS_IN);

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

			for (BlacklistEntry blacklistEntry :
					(List<BlacklistEntry>)query.list()) {

				map.put(blacklistEntry.getPrimaryKeyObj(), blacklistEntry);

				cacheResult(blacklistEntry);

				uncachedPrimaryKeys.remove(blacklistEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					BlacklistEntryModelImpl.ENTITY_CACHE_ENABLED,
					BlacklistEntryImpl.class, primaryKey, nullModel);
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
	 * Returns all the blacklist entries.
	 *
	 * @return the blacklist entries
	 */
	@Override
	public List<BlacklistEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blacklist entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlacklistEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of blacklist entries
	 * @param end the upper bound of the range of blacklist entries (not inclusive)
	 * @return the range of blacklist entries
	 */
	@Override
	public List<BlacklistEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the blacklist entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlacklistEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of blacklist entries
	 * @param end the upper bound of the range of blacklist entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of blacklist entries
	 */
	@Override
	public List<BlacklistEntry> findAll(
		int start, int end,
		OrderByComparator<BlacklistEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the blacklist entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlacklistEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of blacklist entries
	 * @param end the upper bound of the range of blacklist entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of blacklist entries
	 */
	@Override
	public List<BlacklistEntry> findAll(
		int start, int end, OrderByComparator<BlacklistEntry> orderByComparator,
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

		List<BlacklistEntry> list = null;

		if (useFinderCache) {
			list = (List<BlacklistEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_BLACKLISTENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_BLACKLISTENTRY;

				sql = sql.concat(BlacklistEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<BlacklistEntry>)QueryUtil.list(
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
	 * Removes all the blacklist entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (BlacklistEntry blacklistEntry : findAll()) {
			remove(blacklistEntry);
		}
	}

	/**
	 * Returns the number of blacklist entries.
	 *
	 * @return the number of blacklist entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_BLACKLISTENTRY);

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
		return BlacklistEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the blacklist entry persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			BlacklistEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlacklistEntryModelImpl.FINDER_CACHE_ENABLED,
			BlacklistEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			BlacklistEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlacklistEntryModelImpl.FINDER_CACHE_ENABLED,
			BlacklistEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			BlacklistEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlacklistEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByEmailAddress = new FinderPath(
			BlacklistEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlacklistEntryModelImpl.FINDER_CACHE_ENABLED,
			BlacklistEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByEmailAddress", new String[] {String.class.getName()},
			BlacklistEntryModelImpl.EMAILADDRESS_COLUMN_BITMASK);

		_finderPathCountByEmailAddress = new FinderPath(
			BlacklistEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlacklistEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByEmailAddress",
			new String[] {String.class.getName()});

		BlacklistEntryUtil.setPersistence(this);
	}

	public void destroy() {
		BlacklistEntryUtil.setPersistence(null);

		entityCache.removeCache(BlacklistEntryImpl.class.getName());

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

	private static final String _SQL_SELECT_BLACKLISTENTRY =
		"SELECT blacklistEntry FROM BlacklistEntry blacklistEntry";

	private static final String _SQL_SELECT_BLACKLISTENTRY_WHERE_PKS_IN =
		"SELECT blacklistEntry FROM BlacklistEntry blacklistEntry WHERE blacklistEntryId IN (";

	private static final String _SQL_SELECT_BLACKLISTENTRY_WHERE =
		"SELECT blacklistEntry FROM BlacklistEntry blacklistEntry WHERE ";

	private static final String _SQL_COUNT_BLACKLISTENTRY =
		"SELECT COUNT(blacklistEntry) FROM BlacklistEntry blacklistEntry";

	private static final String _SQL_COUNT_BLACKLISTENTRY_WHERE =
		"SELECT COUNT(blacklistEntry) FROM BlacklistEntry blacklistEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "blacklistEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No BlacklistEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No BlacklistEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		BlacklistEntryPersistenceImpl.class);

}