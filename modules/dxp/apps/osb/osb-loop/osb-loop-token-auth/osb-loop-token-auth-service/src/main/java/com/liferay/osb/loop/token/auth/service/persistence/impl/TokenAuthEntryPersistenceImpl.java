/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.loop.token.auth.service.persistence.impl;

import com.liferay.osb.loop.token.auth.exception.NoSuchEntryException;
import com.liferay.osb.loop.token.auth.model.TokenAuthEntry;
import com.liferay.osb.loop.token.auth.model.impl.TokenAuthEntryImpl;
import com.liferay.osb.loop.token.auth.model.impl.TokenAuthEntryModelImpl;
import com.liferay.osb.loop.token.auth.service.persistence.TokenAuthEntryPersistence;
import com.liferay.osb.loop.token.auth.service.persistence.TokenAuthEntryUtil;
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
 * The persistence implementation for the token auth entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Bruno Farache
 * @generated
 */
public class TokenAuthEntryPersistenceImpl
	extends BasePersistenceImpl<TokenAuthEntry>
	implements TokenAuthEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>TokenAuthEntryUtil</code> to access the token auth entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		TokenAuthEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUserId;
	private FinderPath _finderPathWithoutPaginationFindByUserId;
	private FinderPath _finderPathCountByUserId;

	/**
	 * Returns all the token auth entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching token auth entries
	 */
	@Override
	public List<TokenAuthEntry> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the token auth entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TokenAuthEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of token auth entries
	 * @param end the upper bound of the range of token auth entries (not inclusive)
	 * @return the range of matching token auth entries
	 */
	@Override
	public List<TokenAuthEntry> findByUserId(long userId, int start, int end) {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the token auth entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TokenAuthEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of token auth entries
	 * @param end the upper bound of the range of token auth entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching token auth entries
	 */
	@Override
	public List<TokenAuthEntry> findByUserId(
		long userId, int start, int end,
		OrderByComparator<TokenAuthEntry> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the token auth entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TokenAuthEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of token auth entries
	 * @param end the upper bound of the range of token auth entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching token auth entries
	 */
	@Override
	public List<TokenAuthEntry> findByUserId(
		long userId, int start, int end,
		OrderByComparator<TokenAuthEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUserId;
				finderArgs = new Object[] {userId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUserId;
			finderArgs = new Object[] {userId, start, end, orderByComparator};
		}

		List<TokenAuthEntry> list = null;

		if (useFinderCache) {
			list = (List<TokenAuthEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (TokenAuthEntry tokenAuthEntry : list) {
					if (userId != tokenAuthEntry.getUserId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_TOKENAUTHENTRY_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(TokenAuthEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				list = (List<TokenAuthEntry>)QueryUtil.list(
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
	 * Returns the first token auth entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching token auth entry
	 * @throws NoSuchEntryException if a matching token auth entry could not be found
	 */
	@Override
	public TokenAuthEntry findByUserId_First(
			long userId, OrderByComparator<TokenAuthEntry> orderByComparator)
		throws NoSuchEntryException {

		TokenAuthEntry tokenAuthEntry = fetchByUserId_First(
			userId, orderByComparator);

		if (tokenAuthEntry != null) {
			return tokenAuthEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first token auth entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching token auth entry, or <code>null</code> if a matching token auth entry could not be found
	 */
	@Override
	public TokenAuthEntry fetchByUserId_First(
		long userId, OrderByComparator<TokenAuthEntry> orderByComparator) {

		List<TokenAuthEntry> list = findByUserId(
			userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last token auth entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching token auth entry
	 * @throws NoSuchEntryException if a matching token auth entry could not be found
	 */
	@Override
	public TokenAuthEntry findByUserId_Last(
			long userId, OrderByComparator<TokenAuthEntry> orderByComparator)
		throws NoSuchEntryException {

		TokenAuthEntry tokenAuthEntry = fetchByUserId_Last(
			userId, orderByComparator);

		if (tokenAuthEntry != null) {
			return tokenAuthEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last token auth entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching token auth entry, or <code>null</code> if a matching token auth entry could not be found
	 */
	@Override
	public TokenAuthEntry fetchByUserId_Last(
		long userId, OrderByComparator<TokenAuthEntry> orderByComparator) {

		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<TokenAuthEntry> list = findByUserId(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the token auth entries before and after the current token auth entry in the ordered set where userId = &#63;.
	 *
	 * @param tokenAuthEntryId the primary key of the current token auth entry
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next token auth entry
	 * @throws NoSuchEntryException if a token auth entry with the primary key could not be found
	 */
	@Override
	public TokenAuthEntry[] findByUserId_PrevAndNext(
			long tokenAuthEntryId, long userId,
			OrderByComparator<TokenAuthEntry> orderByComparator)
		throws NoSuchEntryException {

		TokenAuthEntry tokenAuthEntry = findByPrimaryKey(tokenAuthEntryId);

		Session session = null;

		try {
			session = openSession();

			TokenAuthEntry[] array = new TokenAuthEntryImpl[3];

			array[0] = getByUserId_PrevAndNext(
				session, tokenAuthEntry, userId, orderByComparator, true);

			array[1] = tokenAuthEntry;

			array[2] = getByUserId_PrevAndNext(
				session, tokenAuthEntry, userId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected TokenAuthEntry getByUserId_PrevAndNext(
		Session session, TokenAuthEntry tokenAuthEntry, long userId,
		OrderByComparator<TokenAuthEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_TOKENAUTHENTRY_WHERE);

		sb.append(_FINDER_COLUMN_USERID_USERID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(TokenAuthEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						tokenAuthEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<TokenAuthEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the token auth entries where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (TokenAuthEntry tokenAuthEntry :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(tokenAuthEntry);
		}
	}

	/**
	 * Returns the number of token auth entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching token auth entries
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_TOKENAUTHENTRY_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 =
		"tokenAuthEntry.userId = ?";

	private FinderPath _finderPathFetchByToken;
	private FinderPath _finderPathCountByToken;

	/**
	 * Returns the token auth entry where token = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param token the token
	 * @return the matching token auth entry
	 * @throws NoSuchEntryException if a matching token auth entry could not be found
	 */
	@Override
	public TokenAuthEntry findByToken(String token)
		throws NoSuchEntryException {

		TokenAuthEntry tokenAuthEntry = fetchByToken(token);

		if (tokenAuthEntry == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("token=");
			sb.append(token);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryException(sb.toString());
		}

		return tokenAuthEntry;
	}

	/**
	 * Returns the token auth entry where token = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param token the token
	 * @return the matching token auth entry, or <code>null</code> if a matching token auth entry could not be found
	 */
	@Override
	public TokenAuthEntry fetchByToken(String token) {
		return fetchByToken(token, true);
	}

	/**
	 * Returns the token auth entry where token = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param token the token
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching token auth entry, or <code>null</code> if a matching token auth entry could not be found
	 */
	@Override
	public TokenAuthEntry fetchByToken(String token, boolean useFinderCache) {
		token = Objects.toString(token, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {token};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByToken, finderArgs, this);
		}

		if (result instanceof TokenAuthEntry) {
			TokenAuthEntry tokenAuthEntry = (TokenAuthEntry)result;

			if (!Objects.equals(token, tokenAuthEntry.getToken())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_TOKENAUTHENTRY_WHERE);

			boolean bindToken = false;

			if (token.isEmpty()) {
				sb.append(_FINDER_COLUMN_TOKEN_TOKEN_3);
			}
			else {
				bindToken = true;

				sb.append(_FINDER_COLUMN_TOKEN_TOKEN_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindToken) {
					queryPos.add(token);
				}

				List<TokenAuthEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByToken, finderArgs, list);
					}
				}
				else {
					TokenAuthEntry tokenAuthEntry = list.get(0);

					result = tokenAuthEntry;

					cacheResult(tokenAuthEntry);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByToken, finderArgs);
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
			return (TokenAuthEntry)result;
		}
	}

	/**
	 * Removes the token auth entry where token = &#63; from the database.
	 *
	 * @param token the token
	 * @return the token auth entry that was removed
	 */
	@Override
	public TokenAuthEntry removeByToken(String token)
		throws NoSuchEntryException {

		TokenAuthEntry tokenAuthEntry = findByToken(token);

		return remove(tokenAuthEntry);
	}

	/**
	 * Returns the number of token auth entries where token = &#63;.
	 *
	 * @param token the token
	 * @return the number of matching token auth entries
	 */
	@Override
	public int countByToken(String token) {
		token = Objects.toString(token, "");

		FinderPath finderPath = _finderPathCountByToken;

		Object[] finderArgs = new Object[] {token};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_TOKENAUTHENTRY_WHERE);

			boolean bindToken = false;

			if (token.isEmpty()) {
				sb.append(_FINDER_COLUMN_TOKEN_TOKEN_3);
			}
			else {
				bindToken = true;

				sb.append(_FINDER_COLUMN_TOKEN_TOKEN_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindToken) {
					queryPos.add(token);
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

	private static final String _FINDER_COLUMN_TOKEN_TOKEN_2 =
		"tokenAuthEntry.token = ?";

	private static final String _FINDER_COLUMN_TOKEN_TOKEN_3 =
		"(tokenAuthEntry.token IS NULL OR tokenAuthEntry.token = '')";

	public TokenAuthEntryPersistenceImpl() {
		setModelClass(TokenAuthEntry.class);
	}

	/**
	 * Caches the token auth entry in the entity cache if it is enabled.
	 *
	 * @param tokenAuthEntry the token auth entry
	 */
	@Override
	public void cacheResult(TokenAuthEntry tokenAuthEntry) {
		entityCache.putResult(
			TokenAuthEntryModelImpl.ENTITY_CACHE_ENABLED,
			TokenAuthEntryImpl.class, tokenAuthEntry.getPrimaryKey(),
			tokenAuthEntry);

		finderCache.putResult(
			_finderPathFetchByToken, new Object[] {tokenAuthEntry.getToken()},
			tokenAuthEntry);

		tokenAuthEntry.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the token auth entries in the entity cache if it is enabled.
	 *
	 * @param tokenAuthEntries the token auth entries
	 */
	@Override
	public void cacheResult(List<TokenAuthEntry> tokenAuthEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (tokenAuthEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (TokenAuthEntry tokenAuthEntry : tokenAuthEntries) {
			if (entityCache.getResult(
					TokenAuthEntryModelImpl.ENTITY_CACHE_ENABLED,
					TokenAuthEntryImpl.class, tokenAuthEntry.getPrimaryKey()) ==
						null) {

				cacheResult(tokenAuthEntry);
			}
			else {
				tokenAuthEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all token auth entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(TokenAuthEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the token auth entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(TokenAuthEntry tokenAuthEntry) {
		entityCache.removeResult(
			TokenAuthEntryModelImpl.ENTITY_CACHE_ENABLED,
			TokenAuthEntryImpl.class, tokenAuthEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((TokenAuthEntryModelImpl)tokenAuthEntry, true);
	}

	@Override
	public void clearCache(List<TokenAuthEntry> tokenAuthEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (TokenAuthEntry tokenAuthEntry : tokenAuthEntries) {
			entityCache.removeResult(
				TokenAuthEntryModelImpl.ENTITY_CACHE_ENABLED,
				TokenAuthEntryImpl.class, tokenAuthEntry.getPrimaryKey());

			clearUniqueFindersCache(
				(TokenAuthEntryModelImpl)tokenAuthEntry, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				TokenAuthEntryModelImpl.ENTITY_CACHE_ENABLED,
				TokenAuthEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		TokenAuthEntryModelImpl tokenAuthEntryModelImpl) {

		Object[] args = new Object[] {tokenAuthEntryModelImpl.getToken()};

		finderCache.putResult(
			_finderPathCountByToken, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByToken, args, tokenAuthEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		TokenAuthEntryModelImpl tokenAuthEntryModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {tokenAuthEntryModelImpl.getToken()};

			finderCache.removeResult(_finderPathCountByToken, args);
			finderCache.removeResult(_finderPathFetchByToken, args);
		}

		if ((tokenAuthEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByToken.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				tokenAuthEntryModelImpl.getOriginalToken()
			};

			finderCache.removeResult(_finderPathCountByToken, args);
			finderCache.removeResult(_finderPathFetchByToken, args);
		}
	}

	/**
	 * Creates a new token auth entry with the primary key. Does not add the token auth entry to the database.
	 *
	 * @param tokenAuthEntryId the primary key for the new token auth entry
	 * @return the new token auth entry
	 */
	@Override
	public TokenAuthEntry create(long tokenAuthEntryId) {
		TokenAuthEntry tokenAuthEntry = new TokenAuthEntryImpl();

		tokenAuthEntry.setNew(true);
		tokenAuthEntry.setPrimaryKey(tokenAuthEntryId);

		tokenAuthEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return tokenAuthEntry;
	}

	/**
	 * Removes the token auth entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param tokenAuthEntryId the primary key of the token auth entry
	 * @return the token auth entry that was removed
	 * @throws NoSuchEntryException if a token auth entry with the primary key could not be found
	 */
	@Override
	public TokenAuthEntry remove(long tokenAuthEntryId)
		throws NoSuchEntryException {

		return remove((Serializable)tokenAuthEntryId);
	}

	/**
	 * Removes the token auth entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the token auth entry
	 * @return the token auth entry that was removed
	 * @throws NoSuchEntryException if a token auth entry with the primary key could not be found
	 */
	@Override
	public TokenAuthEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {

		Session session = null;

		try {
			session = openSession();

			TokenAuthEntry tokenAuthEntry = (TokenAuthEntry)session.get(
				TokenAuthEntryImpl.class, primaryKey);

			if (tokenAuthEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(tokenAuthEntry);
		}
		catch (NoSuchEntryException noSuchEntityException) {
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
	protected TokenAuthEntry removeImpl(TokenAuthEntry tokenAuthEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(tokenAuthEntry)) {
				tokenAuthEntry = (TokenAuthEntry)session.get(
					TokenAuthEntryImpl.class,
					tokenAuthEntry.getPrimaryKeyObj());
			}

			if (tokenAuthEntry != null) {
				session.delete(tokenAuthEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (tokenAuthEntry != null) {
			clearCache(tokenAuthEntry);
		}

		return tokenAuthEntry;
	}

	@Override
	public TokenAuthEntry updateImpl(TokenAuthEntry tokenAuthEntry) {
		boolean isNew = tokenAuthEntry.isNew();

		if (!(tokenAuthEntry instanceof TokenAuthEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(tokenAuthEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					tokenAuthEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in tokenAuthEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom TokenAuthEntry implementation " +
					tokenAuthEntry.getClass());
		}

		TokenAuthEntryModelImpl tokenAuthEntryModelImpl =
			(TokenAuthEntryModelImpl)tokenAuthEntry;

		if (isNew && (tokenAuthEntry.getCreateDate() == null)) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Date date = new Date();

			if (serviceContext == null) {
				tokenAuthEntry.setCreateDate(date);
			}
			else {
				tokenAuthEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(tokenAuthEntry);

				tokenAuthEntry.setNew(false);
			}
			else {
				tokenAuthEntry = (TokenAuthEntry)session.merge(tokenAuthEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!TokenAuthEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {tokenAuthEntryModelImpl.getUserId()};

			finderCache.removeResult(_finderPathCountByUserId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUserId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((tokenAuthEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUserId.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					tokenAuthEntryModelImpl.getOriginalUserId()
				};

				finderCache.removeResult(_finderPathCountByUserId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);

				args = new Object[] {tokenAuthEntryModelImpl.getUserId()};

				finderCache.removeResult(_finderPathCountByUserId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);
			}
		}

		entityCache.putResult(
			TokenAuthEntryModelImpl.ENTITY_CACHE_ENABLED,
			TokenAuthEntryImpl.class, tokenAuthEntry.getPrimaryKey(),
			tokenAuthEntry, false);

		clearUniqueFindersCache(tokenAuthEntryModelImpl, false);
		cacheUniqueFindersCache(tokenAuthEntryModelImpl);

		tokenAuthEntry.resetOriginalValues();

		return tokenAuthEntry;
	}

	/**
	 * Returns the token auth entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the token auth entry
	 * @return the token auth entry
	 * @throws NoSuchEntryException if a token auth entry with the primary key could not be found
	 */
	@Override
	public TokenAuthEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		TokenAuthEntry tokenAuthEntry = fetchByPrimaryKey(primaryKey);

		if (tokenAuthEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return tokenAuthEntry;
	}

	/**
	 * Returns the token auth entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param tokenAuthEntryId the primary key of the token auth entry
	 * @return the token auth entry
	 * @throws NoSuchEntryException if a token auth entry with the primary key could not be found
	 */
	@Override
	public TokenAuthEntry findByPrimaryKey(long tokenAuthEntryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)tokenAuthEntryId);
	}

	/**
	 * Returns the token auth entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the token auth entry
	 * @return the token auth entry, or <code>null</code> if a token auth entry with the primary key could not be found
	 */
	@Override
	public TokenAuthEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			TokenAuthEntryModelImpl.ENTITY_CACHE_ENABLED,
			TokenAuthEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		TokenAuthEntry tokenAuthEntry = (TokenAuthEntry)serializable;

		if (tokenAuthEntry == null) {
			Session session = null;

			try {
				session = openSession();

				tokenAuthEntry = (TokenAuthEntry)session.get(
					TokenAuthEntryImpl.class, primaryKey);

				if (tokenAuthEntry != null) {
					cacheResult(tokenAuthEntry);
				}
				else {
					entityCache.putResult(
						TokenAuthEntryModelImpl.ENTITY_CACHE_ENABLED,
						TokenAuthEntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					TokenAuthEntryModelImpl.ENTITY_CACHE_ENABLED,
					TokenAuthEntryImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return tokenAuthEntry;
	}

	/**
	 * Returns the token auth entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param tokenAuthEntryId the primary key of the token auth entry
	 * @return the token auth entry, or <code>null</code> if a token auth entry with the primary key could not be found
	 */
	@Override
	public TokenAuthEntry fetchByPrimaryKey(long tokenAuthEntryId) {
		return fetchByPrimaryKey((Serializable)tokenAuthEntryId);
	}

	@Override
	public Map<Serializable, TokenAuthEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, TokenAuthEntry> map =
			new HashMap<Serializable, TokenAuthEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			TokenAuthEntry tokenAuthEntry = fetchByPrimaryKey(primaryKey);

			if (tokenAuthEntry != null) {
				map.put(primaryKey, tokenAuthEntry);
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
				TokenAuthEntryModelImpl.ENTITY_CACHE_ENABLED,
				TokenAuthEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (TokenAuthEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_TOKENAUTHENTRY_WHERE_PKS_IN);

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

			for (TokenAuthEntry tokenAuthEntry :
					(List<TokenAuthEntry>)query.list()) {

				map.put(tokenAuthEntry.getPrimaryKeyObj(), tokenAuthEntry);

				cacheResult(tokenAuthEntry);

				uncachedPrimaryKeys.remove(tokenAuthEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					TokenAuthEntryModelImpl.ENTITY_CACHE_ENABLED,
					TokenAuthEntryImpl.class, primaryKey, nullModel);
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
	 * Returns all the token auth entries.
	 *
	 * @return the token auth entries
	 */
	@Override
	public List<TokenAuthEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the token auth entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TokenAuthEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of token auth entries
	 * @param end the upper bound of the range of token auth entries (not inclusive)
	 * @return the range of token auth entries
	 */
	@Override
	public List<TokenAuthEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the token auth entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TokenAuthEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of token auth entries
	 * @param end the upper bound of the range of token auth entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of token auth entries
	 */
	@Override
	public List<TokenAuthEntry> findAll(
		int start, int end,
		OrderByComparator<TokenAuthEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the token auth entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TokenAuthEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of token auth entries
	 * @param end the upper bound of the range of token auth entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of token auth entries
	 */
	@Override
	public List<TokenAuthEntry> findAll(
		int start, int end, OrderByComparator<TokenAuthEntry> orderByComparator,
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

		List<TokenAuthEntry> list = null;

		if (useFinderCache) {
			list = (List<TokenAuthEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_TOKENAUTHENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_TOKENAUTHENTRY;

				sql = sql.concat(TokenAuthEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<TokenAuthEntry>)QueryUtil.list(
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
	 * Removes all the token auth entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (TokenAuthEntry tokenAuthEntry : findAll()) {
			remove(tokenAuthEntry);
		}
	}

	/**
	 * Returns the number of token auth entries.
	 *
	 * @return the number of token auth entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_TOKENAUTHENTRY);

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
		return TokenAuthEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the token auth entry persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			TokenAuthEntryModelImpl.ENTITY_CACHE_ENABLED,
			TokenAuthEntryModelImpl.FINDER_CACHE_ENABLED,
			TokenAuthEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			TokenAuthEntryModelImpl.ENTITY_CACHE_ENABLED,
			TokenAuthEntryModelImpl.FINDER_CACHE_ENABLED,
			TokenAuthEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			TokenAuthEntryModelImpl.ENTITY_CACHE_ENABLED,
			TokenAuthEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUserId = new FinderPath(
			TokenAuthEntryModelImpl.ENTITY_CACHE_ENABLED,
			TokenAuthEntryModelImpl.FINDER_CACHE_ENABLED,
			TokenAuthEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			TokenAuthEntryModelImpl.ENTITY_CACHE_ENABLED,
			TokenAuthEntryModelImpl.FINDER_CACHE_ENABLED,
			TokenAuthEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUserId", new String[] {Long.class.getName()},
			TokenAuthEntryModelImpl.USERID_COLUMN_BITMASK);

		_finderPathCountByUserId = new FinderPath(
			TokenAuthEntryModelImpl.ENTITY_CACHE_ENABLED,
			TokenAuthEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName()});

		_finderPathFetchByToken = new FinderPath(
			TokenAuthEntryModelImpl.ENTITY_CACHE_ENABLED,
			TokenAuthEntryModelImpl.FINDER_CACHE_ENABLED,
			TokenAuthEntryImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByToken",
			new String[] {String.class.getName()},
			TokenAuthEntryModelImpl.TOKEN_COLUMN_BITMASK);

		_finderPathCountByToken = new FinderPath(
			TokenAuthEntryModelImpl.ENTITY_CACHE_ENABLED,
			TokenAuthEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByToken",
			new String[] {String.class.getName()});

		TokenAuthEntryUtil.setPersistence(this);
	}

	public void destroy() {
		TokenAuthEntryUtil.setPersistence(null);

		entityCache.removeCache(TokenAuthEntryImpl.class.getName());

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

	private static final String _SQL_SELECT_TOKENAUTHENTRY =
		"SELECT tokenAuthEntry FROM TokenAuthEntry tokenAuthEntry";

	private static final String _SQL_SELECT_TOKENAUTHENTRY_WHERE_PKS_IN =
		"SELECT tokenAuthEntry FROM TokenAuthEntry tokenAuthEntry WHERE tokenAuthEntryId IN (";

	private static final String _SQL_SELECT_TOKENAUTHENTRY_WHERE =
		"SELECT tokenAuthEntry FROM TokenAuthEntry tokenAuthEntry WHERE ";

	private static final String _SQL_COUNT_TOKENAUTHENTRY =
		"SELECT COUNT(tokenAuthEntry) FROM TokenAuthEntry tokenAuthEntry";

	private static final String _SQL_COUNT_TOKENAUTHENTRY_WHERE =
		"SELECT COUNT(tokenAuthEntry) FROM TokenAuthEntry tokenAuthEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "tokenAuthEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No TokenAuthEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No TokenAuthEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		TokenAuthEntryPersistenceImpl.class);

}