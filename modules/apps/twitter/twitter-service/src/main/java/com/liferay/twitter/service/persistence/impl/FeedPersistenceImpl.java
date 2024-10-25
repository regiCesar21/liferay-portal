/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.twitter.service.persistence.impl;

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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.twitter.exception.NoSuchFeedException;
import com.liferay.twitter.model.Feed;
import com.liferay.twitter.model.impl.FeedImpl;
import com.liferay.twitter.model.impl.FeedModelImpl;
import com.liferay.twitter.service.persistence.FeedPersistence;
import com.liferay.twitter.service.persistence.FeedUtil;

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
 * The persistence implementation for the feed service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class FeedPersistenceImpl
	extends BasePersistenceImpl<Feed> implements FeedPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>FeedUtil</code> to access the feed persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		FeedImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByU_TSN;
	private FinderPath _finderPathCountByU_TSN;

	/**
	 * Returns the feed where userId = &#63; and twitterScreenName = &#63; or throws a <code>NoSuchFeedException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param twitterScreenName the twitter screen name
	 * @return the matching feed
	 * @throws NoSuchFeedException if a matching feed could not be found
	 */
	@Override
	public Feed findByU_TSN(long userId, String twitterScreenName)
		throws NoSuchFeedException {

		Feed feed = fetchByU_TSN(userId, twitterScreenName);

		if (feed == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("userId=");
			sb.append(userId);

			sb.append(", twitterScreenName=");
			sb.append(twitterScreenName);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchFeedException(sb.toString());
		}

		return feed;
	}

	/**
	 * Returns the feed where userId = &#63; and twitterScreenName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param twitterScreenName the twitter screen name
	 * @return the matching feed, or <code>null</code> if a matching feed could not be found
	 */
	@Override
	public Feed fetchByU_TSN(long userId, String twitterScreenName) {
		return fetchByU_TSN(userId, twitterScreenName, true);
	}

	/**
	 * Returns the feed where userId = &#63; and twitterScreenName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param twitterScreenName the twitter screen name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching feed, or <code>null</code> if a matching feed could not be found
	 */
	@Override
	public Feed fetchByU_TSN(
		long userId, String twitterScreenName, boolean useFinderCache) {

		twitterScreenName = Objects.toString(twitterScreenName, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {userId, twitterScreenName};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByU_TSN, finderArgs, this);
		}

		if (result instanceof Feed) {
			Feed feed = (Feed)result;

			if ((userId != feed.getUserId()) ||
				!Objects.equals(
					twitterScreenName, feed.getTwitterScreenName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_FEED_WHERE);

			sb.append(_FINDER_COLUMN_U_TSN_USERID_2);

			boolean bindTwitterScreenName = false;

			if (twitterScreenName.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_TSN_TWITTERSCREENNAME_3);
			}
			else {
				bindTwitterScreenName = true;

				sb.append(_FINDER_COLUMN_U_TSN_TWITTERSCREENNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindTwitterScreenName) {
					queryPos.add(twitterScreenName);
				}

				List<Feed> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByU_TSN, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									userId, twitterScreenName
								};
							}

							_log.warn(
								"FeedPersistenceImpl.fetchByU_TSN(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					Feed feed = list.get(0);

					result = feed;

					cacheResult(feed);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByU_TSN, finderArgs);
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
			return (Feed)result;
		}
	}

	/**
	 * Removes the feed where userId = &#63; and twitterScreenName = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param twitterScreenName the twitter screen name
	 * @return the feed that was removed
	 */
	@Override
	public Feed removeByU_TSN(long userId, String twitterScreenName)
		throws NoSuchFeedException {

		Feed feed = findByU_TSN(userId, twitterScreenName);

		return remove(feed);
	}

	/**
	 * Returns the number of feeds where userId = &#63; and twitterScreenName = &#63;.
	 *
	 * @param userId the user ID
	 * @param twitterScreenName the twitter screen name
	 * @return the number of matching feeds
	 */
	@Override
	public int countByU_TSN(long userId, String twitterScreenName) {
		twitterScreenName = Objects.toString(twitterScreenName, "");

		FinderPath finderPath = _finderPathCountByU_TSN;

		Object[] finderArgs = new Object[] {userId, twitterScreenName};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FEED_WHERE);

			sb.append(_FINDER_COLUMN_U_TSN_USERID_2);

			boolean bindTwitterScreenName = false;

			if (twitterScreenName.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_TSN_TWITTERSCREENNAME_3);
			}
			else {
				bindTwitterScreenName = true;

				sb.append(_FINDER_COLUMN_U_TSN_TWITTERSCREENNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindTwitterScreenName) {
					queryPos.add(twitterScreenName);
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

	private static final String _FINDER_COLUMN_U_TSN_USERID_2 =
		"feed.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_TSN_TWITTERSCREENNAME_2 =
		"feed.twitterScreenName = ?";

	private static final String _FINDER_COLUMN_U_TSN_TWITTERSCREENNAME_3 =
		"(feed.twitterScreenName IS NULL OR feed.twitterScreenName = '')";

	public FeedPersistenceImpl() {
		setModelClass(Feed.class);
	}

	/**
	 * Caches the feed in the entity cache if it is enabled.
	 *
	 * @param feed the feed
	 */
	@Override
	public void cacheResult(Feed feed) {
		entityCache.putResult(
			FeedModelImpl.ENTITY_CACHE_ENABLED, FeedImpl.class,
			feed.getPrimaryKey(), feed);

		finderCache.putResult(
			_finderPathFetchByU_TSN,
			new Object[] {feed.getUserId(), feed.getTwitterScreenName()}, feed);

		feed.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the feeds in the entity cache if it is enabled.
	 *
	 * @param feeds the feeds
	 */
	@Override
	public void cacheResult(List<Feed> feeds) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (feeds.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (Feed feed : feeds) {
			if (entityCache.getResult(
					FeedModelImpl.ENTITY_CACHE_ENABLED, FeedImpl.class,
					feed.getPrimaryKey()) == null) {

				cacheResult(feed);
			}
			else {
				feed.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all feeds.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FeedImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the feed.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Feed feed) {
		entityCache.removeResult(
			FeedModelImpl.ENTITY_CACHE_ENABLED, FeedImpl.class,
			feed.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((FeedModelImpl)feed, true);
	}

	@Override
	public void clearCache(List<Feed> feeds) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Feed feed : feeds) {
			entityCache.removeResult(
				FeedModelImpl.ENTITY_CACHE_ENABLED, FeedImpl.class,
				feed.getPrimaryKey());

			clearUniqueFindersCache((FeedModelImpl)feed, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				FeedModelImpl.ENTITY_CACHE_ENABLED, FeedImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(FeedModelImpl feedModelImpl) {
		Object[] args = new Object[] {
			feedModelImpl.getUserId(), feedModelImpl.getTwitterScreenName()
		};

		finderCache.putResult(
			_finderPathCountByU_TSN, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByU_TSN, args, feedModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		FeedModelImpl feedModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				feedModelImpl.getUserId(), feedModelImpl.getTwitterScreenName()
			};

			finderCache.removeResult(_finderPathCountByU_TSN, args);
			finderCache.removeResult(_finderPathFetchByU_TSN, args);
		}

		if ((feedModelImpl.getColumnBitmask() &
			 _finderPathFetchByU_TSN.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				feedModelImpl.getOriginalUserId(),
				feedModelImpl.getOriginalTwitterScreenName()
			};

			finderCache.removeResult(_finderPathCountByU_TSN, args);
			finderCache.removeResult(_finderPathFetchByU_TSN, args);
		}
	}

	/**
	 * Creates a new feed with the primary key. Does not add the feed to the database.
	 *
	 * @param feedId the primary key for the new feed
	 * @return the new feed
	 */
	@Override
	public Feed create(long feedId) {
		Feed feed = new FeedImpl();

		feed.setNew(true);
		feed.setPrimaryKey(feedId);

		feed.setCompanyId(CompanyThreadLocal.getCompanyId());

		return feed;
	}

	/**
	 * Removes the feed with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param feedId the primary key of the feed
	 * @return the feed that was removed
	 * @throws NoSuchFeedException if a feed with the primary key could not be found
	 */
	@Override
	public Feed remove(long feedId) throws NoSuchFeedException {
		return remove((Serializable)feedId);
	}

	/**
	 * Removes the feed with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the feed
	 * @return the feed that was removed
	 * @throws NoSuchFeedException if a feed with the primary key could not be found
	 */
	@Override
	public Feed remove(Serializable primaryKey) throws NoSuchFeedException {
		Session session = null;

		try {
			session = openSession();

			Feed feed = (Feed)session.get(FeedImpl.class, primaryKey);

			if (feed == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFeedException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(feed);
		}
		catch (NoSuchFeedException noSuchEntityException) {
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
	protected Feed removeImpl(Feed feed) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(feed)) {
				feed = (Feed)session.get(
					FeedImpl.class, feed.getPrimaryKeyObj());
			}

			if (feed != null) {
				session.delete(feed);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (feed != null) {
			clearCache(feed);
		}

		return feed;
	}

	@Override
	public Feed updateImpl(Feed feed) {
		boolean isNew = feed.isNew();

		if (!(feed instanceof FeedModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(feed.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(feed);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in feed proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom Feed implementation " +
					feed.getClass());
		}

		FeedModelImpl feedModelImpl = (FeedModelImpl)feed;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (feed.getCreateDate() == null)) {
			if (serviceContext == null) {
				feed.setCreateDate(date);
			}
			else {
				feed.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!feedModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				feed.setModifiedDate(date);
			}
			else {
				feed.setModifiedDate(serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(feed);

				feed.setNew(false);
			}
			else {
				feed = (Feed)session.merge(feed);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!FeedModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			FeedModelImpl.ENTITY_CACHE_ENABLED, FeedImpl.class,
			feed.getPrimaryKey(), feed, false);

		clearUniqueFindersCache(feedModelImpl, false);
		cacheUniqueFindersCache(feedModelImpl);

		feed.resetOriginalValues();

		return feed;
	}

	/**
	 * Returns the feed with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the feed
	 * @return the feed
	 * @throws NoSuchFeedException if a feed with the primary key could not be found
	 */
	@Override
	public Feed findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFeedException {

		Feed feed = fetchByPrimaryKey(primaryKey);

		if (feed == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFeedException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return feed;
	}

	/**
	 * Returns the feed with the primary key or throws a <code>NoSuchFeedException</code> if it could not be found.
	 *
	 * @param feedId the primary key of the feed
	 * @return the feed
	 * @throws NoSuchFeedException if a feed with the primary key could not be found
	 */
	@Override
	public Feed findByPrimaryKey(long feedId) throws NoSuchFeedException {
		return findByPrimaryKey((Serializable)feedId);
	}

	/**
	 * Returns the feed with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the feed
	 * @return the feed, or <code>null</code> if a feed with the primary key could not be found
	 */
	@Override
	public Feed fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			FeedModelImpl.ENTITY_CACHE_ENABLED, FeedImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		Feed feed = (Feed)serializable;

		if (feed == null) {
			Session session = null;

			try {
				session = openSession();

				feed = (Feed)session.get(FeedImpl.class, primaryKey);

				if (feed != null) {
					cacheResult(feed);
				}
				else {
					entityCache.putResult(
						FeedModelImpl.ENTITY_CACHE_ENABLED, FeedImpl.class,
						primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					FeedModelImpl.ENTITY_CACHE_ENABLED, FeedImpl.class,
					primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return feed;
	}

	/**
	 * Returns the feed with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param feedId the primary key of the feed
	 * @return the feed, or <code>null</code> if a feed with the primary key could not be found
	 */
	@Override
	public Feed fetchByPrimaryKey(long feedId) {
		return fetchByPrimaryKey((Serializable)feedId);
	}

	@Override
	public Map<Serializable, Feed> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, Feed> map = new HashMap<Serializable, Feed>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			Feed feed = fetchByPrimaryKey(primaryKey);

			if (feed != null) {
				map.put(primaryKey, feed);
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
				FeedModelImpl.ENTITY_CACHE_ENABLED, FeedImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (Feed)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_FEED_WHERE_PKS_IN);

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

			for (Feed feed : (List<Feed>)query.list()) {
				map.put(feed.getPrimaryKeyObj(), feed);

				cacheResult(feed);

				uncachedPrimaryKeys.remove(feed.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					FeedModelImpl.ENTITY_CACHE_ENABLED, FeedImpl.class,
					primaryKey, nullModel);
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
	 * Returns all the feeds.
	 *
	 * @return the feeds
	 */
	@Override
	public List<Feed> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the feeds.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FeedModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of feeds
	 * @param end the upper bound of the range of feeds (not inclusive)
	 * @return the range of feeds
	 */
	@Override
	public List<Feed> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the feeds.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FeedModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of feeds
	 * @param end the upper bound of the range of feeds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of feeds
	 */
	@Override
	public List<Feed> findAll(
		int start, int end, OrderByComparator<Feed> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the feeds.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FeedModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of feeds
	 * @param end the upper bound of the range of feeds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of feeds
	 */
	@Override
	public List<Feed> findAll(
		int start, int end, OrderByComparator<Feed> orderByComparator,
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

		List<Feed> list = null;

		if (useFinderCache) {
			list = (List<Feed>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_FEED);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_FEED;

				sql = sql.concat(FeedModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<Feed>)QueryUtil.list(
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
	 * Removes all the feeds from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Feed feed : findAll()) {
			remove(feed);
		}
	}

	/**
	 * Returns the number of feeds.
	 *
	 * @return the number of feeds
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_FEED);

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
		return FeedModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the feed persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			FeedModelImpl.ENTITY_CACHE_ENABLED,
			FeedModelImpl.FINDER_CACHE_ENABLED, FeedImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FeedModelImpl.ENTITY_CACHE_ENABLED,
			FeedModelImpl.FINDER_CACHE_ENABLED, FeedImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			FeedModelImpl.ENTITY_CACHE_ENABLED,
			FeedModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByU_TSN = new FinderPath(
			FeedModelImpl.ENTITY_CACHE_ENABLED,
			FeedModelImpl.FINDER_CACHE_ENABLED, FeedImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByU_TSN",
			new String[] {Long.class.getName(), String.class.getName()},
			FeedModelImpl.USERID_COLUMN_BITMASK |
			FeedModelImpl.TWITTERSCREENNAME_COLUMN_BITMASK);

		_finderPathCountByU_TSN = new FinderPath(
			FeedModelImpl.ENTITY_CACHE_ENABLED,
			FeedModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_TSN",
			new String[] {Long.class.getName(), String.class.getName()});

		FeedUtil.setPersistence(this);
	}

	public void destroy() {
		FeedUtil.setPersistence(null);

		entityCache.removeCache(FeedImpl.class.getName());

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

	private static final String _SQL_SELECT_FEED = "SELECT feed FROM Feed feed";

	private static final String _SQL_SELECT_FEED_WHERE_PKS_IN =
		"SELECT feed FROM Feed feed WHERE feedId IN (";

	private static final String _SQL_SELECT_FEED_WHERE =
		"SELECT feed FROM Feed feed WHERE ";

	private static final String _SQL_COUNT_FEED =
		"SELECT COUNT(feed) FROM Feed feed";

	private static final String _SQL_COUNT_FEED_WHERE =
		"SELECT COUNT(feed) FROM Feed feed WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "feed.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No Feed exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No Feed exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		FeedPersistenceImpl.class);

}