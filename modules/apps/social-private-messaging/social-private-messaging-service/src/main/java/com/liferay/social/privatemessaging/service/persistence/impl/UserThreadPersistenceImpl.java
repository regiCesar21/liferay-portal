/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.privatemessaging.service.persistence.impl;

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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.social.privatemessaging.exception.NoSuchUserThreadException;
import com.liferay.social.privatemessaging.model.UserThread;
import com.liferay.social.privatemessaging.model.impl.UserThreadImpl;
import com.liferay.social.privatemessaging.model.impl.UserThreadModelImpl;
import com.liferay.social.privatemessaging.service.persistence.UserThreadPersistence;
import com.liferay.social.privatemessaging.service.persistence.UserThreadUtil;

import java.io.Serializable;

import java.lang.reflect.Field;
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
 * The persistence implementation for the user thread service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UserThreadPersistenceImpl
	extends BasePersistenceImpl<UserThread> implements UserThreadPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>UserThreadUtil</code> to access the user thread persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		UserThreadImpl.class.getName();

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
	 * Returns all the user threads where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching user threads
	 */
	@Override
	public List<UserThread> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user threads where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserThreadModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user threads
	 * @param end the upper bound of the range of user threads (not inclusive)
	 * @return the range of matching user threads
	 */
	@Override
	public List<UserThread> findByUserId(long userId, int start, int end) {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user threads where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserThreadModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user threads
	 * @param end the upper bound of the range of user threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user threads
	 */
	@Override
	public List<UserThread> findByUserId(
		long userId, int start, int end,
		OrderByComparator<UserThread> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user threads where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserThreadModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user threads
	 * @param end the upper bound of the range of user threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user threads
	 */
	@Override
	public List<UserThread> findByUserId(
		long userId, int start, int end,
		OrderByComparator<UserThread> orderByComparator,
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

		List<UserThread> list = null;

		if (useFinderCache) {
			list = (List<UserThread>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserThread userThread : list) {
					if (userId != userThread.getUserId()) {
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

			sb.append(_SQL_SELECT_USERTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				list = (List<UserThread>)QueryUtil.list(
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
	 * Returns the first user thread in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user thread
	 * @throws NoSuchUserThreadException if a matching user thread could not be found
	 */
	@Override
	public UserThread findByUserId_First(
			long userId, OrderByComparator<UserThread> orderByComparator)
		throws NoSuchUserThreadException {

		UserThread userThread = fetchByUserId_First(userId, orderByComparator);

		if (userThread != null) {
			return userThread;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchUserThreadException(sb.toString());
	}

	/**
	 * Returns the first user thread in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user thread, or <code>null</code> if a matching user thread could not be found
	 */
	@Override
	public UserThread fetchByUserId_First(
		long userId, OrderByComparator<UserThread> orderByComparator) {

		List<UserThread> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user thread in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user thread
	 * @throws NoSuchUserThreadException if a matching user thread could not be found
	 */
	@Override
	public UserThread findByUserId_Last(
			long userId, OrderByComparator<UserThread> orderByComparator)
		throws NoSuchUserThreadException {

		UserThread userThread = fetchByUserId_Last(userId, orderByComparator);

		if (userThread != null) {
			return userThread;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchUserThreadException(sb.toString());
	}

	/**
	 * Returns the last user thread in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user thread, or <code>null</code> if a matching user thread could not be found
	 */
	@Override
	public UserThread fetchByUserId_Last(
		long userId, OrderByComparator<UserThread> orderByComparator) {

		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<UserThread> list = findByUserId(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user threads before and after the current user thread in the ordered set where userId = &#63;.
	 *
	 * @param userThreadId the primary key of the current user thread
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user thread
	 * @throws NoSuchUserThreadException if a user thread with the primary key could not be found
	 */
	@Override
	public UserThread[] findByUserId_PrevAndNext(
			long userThreadId, long userId,
			OrderByComparator<UserThread> orderByComparator)
		throws NoSuchUserThreadException {

		UserThread userThread = findByPrimaryKey(userThreadId);

		Session session = null;

		try {
			session = openSession();

			UserThread[] array = new UserThreadImpl[3];

			array[0] = getByUserId_PrevAndNext(
				session, userThread, userId, orderByComparator, true);

			array[1] = userThread;

			array[2] = getByUserId_PrevAndNext(
				session, userThread, userId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserThread getByUserId_PrevAndNext(
		Session session, UserThread userThread, long userId,
		OrderByComparator<UserThread> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_USERTHREAD_WHERE);

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
			sb.append(UserThreadModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(userThread)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<UserThread> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user threads where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (UserThread userThread :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(userThread);
		}
	}

	/**
	 * Returns the number of user threads where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching user threads
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_USERTHREAD_WHERE);

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
		"userThread.userId = ?";

	private FinderPath _finderPathWithPaginationFindByMBThreadId;
	private FinderPath _finderPathWithoutPaginationFindByMBThreadId;
	private FinderPath _finderPathCountByMBThreadId;

	/**
	 * Returns all the user threads where mbThreadId = &#63;.
	 *
	 * @param mbThreadId the mb thread ID
	 * @return the matching user threads
	 */
	@Override
	public List<UserThread> findByMBThreadId(long mbThreadId) {
		return findByMBThreadId(
			mbThreadId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user threads where mbThreadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserThreadModelImpl</code>.
	 * </p>
	 *
	 * @param mbThreadId the mb thread ID
	 * @param start the lower bound of the range of user threads
	 * @param end the upper bound of the range of user threads (not inclusive)
	 * @return the range of matching user threads
	 */
	@Override
	public List<UserThread> findByMBThreadId(
		long mbThreadId, int start, int end) {

		return findByMBThreadId(mbThreadId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user threads where mbThreadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserThreadModelImpl</code>.
	 * </p>
	 *
	 * @param mbThreadId the mb thread ID
	 * @param start the lower bound of the range of user threads
	 * @param end the upper bound of the range of user threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user threads
	 */
	@Override
	public List<UserThread> findByMBThreadId(
		long mbThreadId, int start, int end,
		OrderByComparator<UserThread> orderByComparator) {

		return findByMBThreadId(
			mbThreadId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user threads where mbThreadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserThreadModelImpl</code>.
	 * </p>
	 *
	 * @param mbThreadId the mb thread ID
	 * @param start the lower bound of the range of user threads
	 * @param end the upper bound of the range of user threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user threads
	 */
	@Override
	public List<UserThread> findByMBThreadId(
		long mbThreadId, int start, int end,
		OrderByComparator<UserThread> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByMBThreadId;
				finderArgs = new Object[] {mbThreadId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByMBThreadId;
			finderArgs = new Object[] {
				mbThreadId, start, end, orderByComparator
			};
		}

		List<UserThread> list = null;

		if (useFinderCache) {
			list = (List<UserThread>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserThread userThread : list) {
					if (mbThreadId != userThread.getMbThreadId()) {
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

			sb.append(_SQL_SELECT_USERTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_MBTHREADID_MBTHREADID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(mbThreadId);

				list = (List<UserThread>)QueryUtil.list(
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
	 * Returns the first user thread in the ordered set where mbThreadId = &#63;.
	 *
	 * @param mbThreadId the mb thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user thread
	 * @throws NoSuchUserThreadException if a matching user thread could not be found
	 */
	@Override
	public UserThread findByMBThreadId_First(
			long mbThreadId, OrderByComparator<UserThread> orderByComparator)
		throws NoSuchUserThreadException {

		UserThread userThread = fetchByMBThreadId_First(
			mbThreadId, orderByComparator);

		if (userThread != null) {
			return userThread;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("mbThreadId=");
		sb.append(mbThreadId);

		sb.append("}");

		throw new NoSuchUserThreadException(sb.toString());
	}

	/**
	 * Returns the first user thread in the ordered set where mbThreadId = &#63;.
	 *
	 * @param mbThreadId the mb thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user thread, or <code>null</code> if a matching user thread could not be found
	 */
	@Override
	public UserThread fetchByMBThreadId_First(
		long mbThreadId, OrderByComparator<UserThread> orderByComparator) {

		List<UserThread> list = findByMBThreadId(
			mbThreadId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user thread in the ordered set where mbThreadId = &#63;.
	 *
	 * @param mbThreadId the mb thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user thread
	 * @throws NoSuchUserThreadException if a matching user thread could not be found
	 */
	@Override
	public UserThread findByMBThreadId_Last(
			long mbThreadId, OrderByComparator<UserThread> orderByComparator)
		throws NoSuchUserThreadException {

		UserThread userThread = fetchByMBThreadId_Last(
			mbThreadId, orderByComparator);

		if (userThread != null) {
			return userThread;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("mbThreadId=");
		sb.append(mbThreadId);

		sb.append("}");

		throw new NoSuchUserThreadException(sb.toString());
	}

	/**
	 * Returns the last user thread in the ordered set where mbThreadId = &#63;.
	 *
	 * @param mbThreadId the mb thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user thread, or <code>null</code> if a matching user thread could not be found
	 */
	@Override
	public UserThread fetchByMBThreadId_Last(
		long mbThreadId, OrderByComparator<UserThread> orderByComparator) {

		int count = countByMBThreadId(mbThreadId);

		if (count == 0) {
			return null;
		}

		List<UserThread> list = findByMBThreadId(
			mbThreadId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user threads before and after the current user thread in the ordered set where mbThreadId = &#63;.
	 *
	 * @param userThreadId the primary key of the current user thread
	 * @param mbThreadId the mb thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user thread
	 * @throws NoSuchUserThreadException if a user thread with the primary key could not be found
	 */
	@Override
	public UserThread[] findByMBThreadId_PrevAndNext(
			long userThreadId, long mbThreadId,
			OrderByComparator<UserThread> orderByComparator)
		throws NoSuchUserThreadException {

		UserThread userThread = findByPrimaryKey(userThreadId);

		Session session = null;

		try {
			session = openSession();

			UserThread[] array = new UserThreadImpl[3];

			array[0] = getByMBThreadId_PrevAndNext(
				session, userThread, mbThreadId, orderByComparator, true);

			array[1] = userThread;

			array[2] = getByMBThreadId_PrevAndNext(
				session, userThread, mbThreadId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserThread getByMBThreadId_PrevAndNext(
		Session session, UserThread userThread, long mbThreadId,
		OrderByComparator<UserThread> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_USERTHREAD_WHERE);

		sb.append(_FINDER_COLUMN_MBTHREADID_MBTHREADID_2);

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
			sb.append(UserThreadModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(mbThreadId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(userThread)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<UserThread> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user threads where mbThreadId = &#63; from the database.
	 *
	 * @param mbThreadId the mb thread ID
	 */
	@Override
	public void removeByMBThreadId(long mbThreadId) {
		for (UserThread userThread :
				findByMBThreadId(
					mbThreadId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(userThread);
		}
	}

	/**
	 * Returns the number of user threads where mbThreadId = &#63;.
	 *
	 * @param mbThreadId the mb thread ID
	 * @return the number of matching user threads
	 */
	@Override
	public int countByMBThreadId(long mbThreadId) {
		FinderPath finderPath = _finderPathCountByMBThreadId;

		Object[] finderArgs = new Object[] {mbThreadId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_USERTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_MBTHREADID_MBTHREADID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(mbThreadId);

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

	private static final String _FINDER_COLUMN_MBTHREADID_MBTHREADID_2 =
		"userThread.mbThreadId = ?";

	private FinderPath _finderPathFetchByU_M;
	private FinderPath _finderPathCountByU_M;

	/**
	 * Returns the user thread where userId = &#63; and mbThreadId = &#63; or throws a <code>NoSuchUserThreadException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param mbThreadId the mb thread ID
	 * @return the matching user thread
	 * @throws NoSuchUserThreadException if a matching user thread could not be found
	 */
	@Override
	public UserThread findByU_M(long userId, long mbThreadId)
		throws NoSuchUserThreadException {

		UserThread userThread = fetchByU_M(userId, mbThreadId);

		if (userThread == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("userId=");
			sb.append(userId);

			sb.append(", mbThreadId=");
			sb.append(mbThreadId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchUserThreadException(sb.toString());
		}

		return userThread;
	}

	/**
	 * Returns the user thread where userId = &#63; and mbThreadId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param mbThreadId the mb thread ID
	 * @return the matching user thread, or <code>null</code> if a matching user thread could not be found
	 */
	@Override
	public UserThread fetchByU_M(long userId, long mbThreadId) {
		return fetchByU_M(userId, mbThreadId, true);
	}

	/**
	 * Returns the user thread where userId = &#63; and mbThreadId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param mbThreadId the mb thread ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching user thread, or <code>null</code> if a matching user thread could not be found
	 */
	@Override
	public UserThread fetchByU_M(
		long userId, long mbThreadId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {userId, mbThreadId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByU_M, finderArgs, this);
		}

		if (result instanceof UserThread) {
			UserThread userThread = (UserThread)result;

			if ((userId != userThread.getUserId()) ||
				(mbThreadId != userThread.getMbThreadId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_USERTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_U_M_USERID_2);

			sb.append(_FINDER_COLUMN_U_M_MBTHREADID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(mbThreadId);

				List<UserThread> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByU_M, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {userId, mbThreadId};
							}

							_log.warn(
								"UserThreadPersistenceImpl.fetchByU_M(long, long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					UserThread userThread = list.get(0);

					result = userThread;

					cacheResult(userThread);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(_finderPathFetchByU_M, finderArgs);
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
			return (UserThread)result;
		}
	}

	/**
	 * Removes the user thread where userId = &#63; and mbThreadId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param mbThreadId the mb thread ID
	 * @return the user thread that was removed
	 */
	@Override
	public UserThread removeByU_M(long userId, long mbThreadId)
		throws NoSuchUserThreadException {

		UserThread userThread = findByU_M(userId, mbThreadId);

		return remove(userThread);
	}

	/**
	 * Returns the number of user threads where userId = &#63; and mbThreadId = &#63;.
	 *
	 * @param userId the user ID
	 * @param mbThreadId the mb thread ID
	 * @return the number of matching user threads
	 */
	@Override
	public int countByU_M(long userId, long mbThreadId) {
		FinderPath finderPath = _finderPathCountByU_M;

		Object[] finderArgs = new Object[] {userId, mbThreadId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_USERTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_U_M_USERID_2);

			sb.append(_FINDER_COLUMN_U_M_MBTHREADID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(mbThreadId);

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

	private static final String _FINDER_COLUMN_U_M_USERID_2 =
		"userThread.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_M_MBTHREADID_2 =
		"userThread.mbThreadId = ?";

	private FinderPath _finderPathWithPaginationFindByU_D;
	private FinderPath _finderPathWithoutPaginationFindByU_D;
	private FinderPath _finderPathCountByU_D;

	/**
	 * Returns all the user threads where userId = &#63; and deleted = &#63;.
	 *
	 * @param userId the user ID
	 * @param deleted the deleted
	 * @return the matching user threads
	 */
	@Override
	public List<UserThread> findByU_D(long userId, boolean deleted) {
		return findByU_D(
			userId, deleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user threads where userId = &#63; and deleted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserThreadModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param deleted the deleted
	 * @param start the lower bound of the range of user threads
	 * @param end the upper bound of the range of user threads (not inclusive)
	 * @return the range of matching user threads
	 */
	@Override
	public List<UserThread> findByU_D(
		long userId, boolean deleted, int start, int end) {

		return findByU_D(userId, deleted, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user threads where userId = &#63; and deleted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserThreadModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param deleted the deleted
	 * @param start the lower bound of the range of user threads
	 * @param end the upper bound of the range of user threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user threads
	 */
	@Override
	public List<UserThread> findByU_D(
		long userId, boolean deleted, int start, int end,
		OrderByComparator<UserThread> orderByComparator) {

		return findByU_D(userId, deleted, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user threads where userId = &#63; and deleted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserThreadModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param deleted the deleted
	 * @param start the lower bound of the range of user threads
	 * @param end the upper bound of the range of user threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user threads
	 */
	@Override
	public List<UserThread> findByU_D(
		long userId, boolean deleted, int start, int end,
		OrderByComparator<UserThread> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByU_D;
				finderArgs = new Object[] {userId, deleted};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByU_D;
			finderArgs = new Object[] {
				userId, deleted, start, end, orderByComparator
			};
		}

		List<UserThread> list = null;

		if (useFinderCache) {
			list = (List<UserThread>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserThread userThread : list) {
					if ((userId != userThread.getUserId()) ||
						(deleted != userThread.isDeleted())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_USERTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_U_D_USERID_2);

			sb.append(_FINDER_COLUMN_U_D_DELETED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(deleted);

				list = (List<UserThread>)QueryUtil.list(
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
	 * Returns the first user thread in the ordered set where userId = &#63; and deleted = &#63;.
	 *
	 * @param userId the user ID
	 * @param deleted the deleted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user thread
	 * @throws NoSuchUserThreadException if a matching user thread could not be found
	 */
	@Override
	public UserThread findByU_D_First(
			long userId, boolean deleted,
			OrderByComparator<UserThread> orderByComparator)
		throws NoSuchUserThreadException {

		UserThread userThread = fetchByU_D_First(
			userId, deleted, orderByComparator);

		if (userThread != null) {
			return userThread;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", deleted=");
		sb.append(deleted);

		sb.append("}");

		throw new NoSuchUserThreadException(sb.toString());
	}

	/**
	 * Returns the first user thread in the ordered set where userId = &#63; and deleted = &#63;.
	 *
	 * @param userId the user ID
	 * @param deleted the deleted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user thread, or <code>null</code> if a matching user thread could not be found
	 */
	@Override
	public UserThread fetchByU_D_First(
		long userId, boolean deleted,
		OrderByComparator<UserThread> orderByComparator) {

		List<UserThread> list = findByU_D(
			userId, deleted, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user thread in the ordered set where userId = &#63; and deleted = &#63;.
	 *
	 * @param userId the user ID
	 * @param deleted the deleted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user thread
	 * @throws NoSuchUserThreadException if a matching user thread could not be found
	 */
	@Override
	public UserThread findByU_D_Last(
			long userId, boolean deleted,
			OrderByComparator<UserThread> orderByComparator)
		throws NoSuchUserThreadException {

		UserThread userThread = fetchByU_D_Last(
			userId, deleted, orderByComparator);

		if (userThread != null) {
			return userThread;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", deleted=");
		sb.append(deleted);

		sb.append("}");

		throw new NoSuchUserThreadException(sb.toString());
	}

	/**
	 * Returns the last user thread in the ordered set where userId = &#63; and deleted = &#63;.
	 *
	 * @param userId the user ID
	 * @param deleted the deleted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user thread, or <code>null</code> if a matching user thread could not be found
	 */
	@Override
	public UserThread fetchByU_D_Last(
		long userId, boolean deleted,
		OrderByComparator<UserThread> orderByComparator) {

		int count = countByU_D(userId, deleted);

		if (count == 0) {
			return null;
		}

		List<UserThread> list = findByU_D(
			userId, deleted, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user threads before and after the current user thread in the ordered set where userId = &#63; and deleted = &#63;.
	 *
	 * @param userThreadId the primary key of the current user thread
	 * @param userId the user ID
	 * @param deleted the deleted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user thread
	 * @throws NoSuchUserThreadException if a user thread with the primary key could not be found
	 */
	@Override
	public UserThread[] findByU_D_PrevAndNext(
			long userThreadId, long userId, boolean deleted,
			OrderByComparator<UserThread> orderByComparator)
		throws NoSuchUserThreadException {

		UserThread userThread = findByPrimaryKey(userThreadId);

		Session session = null;

		try {
			session = openSession();

			UserThread[] array = new UserThreadImpl[3];

			array[0] = getByU_D_PrevAndNext(
				session, userThread, userId, deleted, orderByComparator, true);

			array[1] = userThread;

			array[2] = getByU_D_PrevAndNext(
				session, userThread, userId, deleted, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserThread getByU_D_PrevAndNext(
		Session session, UserThread userThread, long userId, boolean deleted,
		OrderByComparator<UserThread> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_USERTHREAD_WHERE);

		sb.append(_FINDER_COLUMN_U_D_USERID_2);

		sb.append(_FINDER_COLUMN_U_D_DELETED_2);

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
			sb.append(UserThreadModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		queryPos.add(deleted);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(userThread)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<UserThread> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user threads where userId = &#63; and deleted = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param deleted the deleted
	 */
	@Override
	public void removeByU_D(long userId, boolean deleted) {
		for (UserThread userThread :
				findByU_D(
					userId, deleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(userThread);
		}
	}

	/**
	 * Returns the number of user threads where userId = &#63; and deleted = &#63;.
	 *
	 * @param userId the user ID
	 * @param deleted the deleted
	 * @return the number of matching user threads
	 */
	@Override
	public int countByU_D(long userId, boolean deleted) {
		FinderPath finderPath = _finderPathCountByU_D;

		Object[] finderArgs = new Object[] {userId, deleted};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_USERTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_U_D_USERID_2);

			sb.append(_FINDER_COLUMN_U_D_DELETED_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(deleted);

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

	private static final String _FINDER_COLUMN_U_D_USERID_2 =
		"userThread.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_D_DELETED_2 =
		"userThread.deleted = ?";

	private FinderPath _finderPathWithPaginationFindByU_R_D;
	private FinderPath _finderPathWithoutPaginationFindByU_R_D;
	private FinderPath _finderPathCountByU_R_D;

	/**
	 * Returns all the user threads where userId = &#63; and read = &#63; and deleted = &#63;.
	 *
	 * @param userId the user ID
	 * @param read the read
	 * @param deleted the deleted
	 * @return the matching user threads
	 */
	@Override
	public List<UserThread> findByU_R_D(
		long userId, boolean read, boolean deleted) {

		return findByU_R_D(
			userId, read, deleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user threads where userId = &#63; and read = &#63; and deleted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserThreadModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param read the read
	 * @param deleted the deleted
	 * @param start the lower bound of the range of user threads
	 * @param end the upper bound of the range of user threads (not inclusive)
	 * @return the range of matching user threads
	 */
	@Override
	public List<UserThread> findByU_R_D(
		long userId, boolean read, boolean deleted, int start, int end) {

		return findByU_R_D(userId, read, deleted, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user threads where userId = &#63; and read = &#63; and deleted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserThreadModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param read the read
	 * @param deleted the deleted
	 * @param start the lower bound of the range of user threads
	 * @param end the upper bound of the range of user threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user threads
	 */
	@Override
	public List<UserThread> findByU_R_D(
		long userId, boolean read, boolean deleted, int start, int end,
		OrderByComparator<UserThread> orderByComparator) {

		return findByU_R_D(
			userId, read, deleted, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user threads where userId = &#63; and read = &#63; and deleted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserThreadModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param read the read
	 * @param deleted the deleted
	 * @param start the lower bound of the range of user threads
	 * @param end the upper bound of the range of user threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user threads
	 */
	@Override
	public List<UserThread> findByU_R_D(
		long userId, boolean read, boolean deleted, int start, int end,
		OrderByComparator<UserThread> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByU_R_D;
				finderArgs = new Object[] {userId, read, deleted};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByU_R_D;
			finderArgs = new Object[] {
				userId, read, deleted, start, end, orderByComparator
			};
		}

		List<UserThread> list = null;

		if (useFinderCache) {
			list = (List<UserThread>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserThread userThread : list) {
					if ((userId != userThread.getUserId()) ||
						(read != userThread.isRead()) ||
						(deleted != userThread.isDeleted())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_USERTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_U_R_D_USERID_2);

			sb.append(_FINDER_COLUMN_U_R_D_READ_2);

			sb.append(_FINDER_COLUMN_U_R_D_DELETED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(read);

				queryPos.add(deleted);

				list = (List<UserThread>)QueryUtil.list(
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
	 * Returns the first user thread in the ordered set where userId = &#63; and read = &#63; and deleted = &#63;.
	 *
	 * @param userId the user ID
	 * @param read the read
	 * @param deleted the deleted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user thread
	 * @throws NoSuchUserThreadException if a matching user thread could not be found
	 */
	@Override
	public UserThread findByU_R_D_First(
			long userId, boolean read, boolean deleted,
			OrderByComparator<UserThread> orderByComparator)
		throws NoSuchUserThreadException {

		UserThread userThread = fetchByU_R_D_First(
			userId, read, deleted, orderByComparator);

		if (userThread != null) {
			return userThread;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", read=");
		sb.append(read);

		sb.append(", deleted=");
		sb.append(deleted);

		sb.append("}");

		throw new NoSuchUserThreadException(sb.toString());
	}

	/**
	 * Returns the first user thread in the ordered set where userId = &#63; and read = &#63; and deleted = &#63;.
	 *
	 * @param userId the user ID
	 * @param read the read
	 * @param deleted the deleted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user thread, or <code>null</code> if a matching user thread could not be found
	 */
	@Override
	public UserThread fetchByU_R_D_First(
		long userId, boolean read, boolean deleted,
		OrderByComparator<UserThread> orderByComparator) {

		List<UserThread> list = findByU_R_D(
			userId, read, deleted, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user thread in the ordered set where userId = &#63; and read = &#63; and deleted = &#63;.
	 *
	 * @param userId the user ID
	 * @param read the read
	 * @param deleted the deleted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user thread
	 * @throws NoSuchUserThreadException if a matching user thread could not be found
	 */
	@Override
	public UserThread findByU_R_D_Last(
			long userId, boolean read, boolean deleted,
			OrderByComparator<UserThread> orderByComparator)
		throws NoSuchUserThreadException {

		UserThread userThread = fetchByU_R_D_Last(
			userId, read, deleted, orderByComparator);

		if (userThread != null) {
			return userThread;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", read=");
		sb.append(read);

		sb.append(", deleted=");
		sb.append(deleted);

		sb.append("}");

		throw new NoSuchUserThreadException(sb.toString());
	}

	/**
	 * Returns the last user thread in the ordered set where userId = &#63; and read = &#63; and deleted = &#63;.
	 *
	 * @param userId the user ID
	 * @param read the read
	 * @param deleted the deleted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user thread, or <code>null</code> if a matching user thread could not be found
	 */
	@Override
	public UserThread fetchByU_R_D_Last(
		long userId, boolean read, boolean deleted,
		OrderByComparator<UserThread> orderByComparator) {

		int count = countByU_R_D(userId, read, deleted);

		if (count == 0) {
			return null;
		}

		List<UserThread> list = findByU_R_D(
			userId, read, deleted, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user threads before and after the current user thread in the ordered set where userId = &#63; and read = &#63; and deleted = &#63;.
	 *
	 * @param userThreadId the primary key of the current user thread
	 * @param userId the user ID
	 * @param read the read
	 * @param deleted the deleted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user thread
	 * @throws NoSuchUserThreadException if a user thread with the primary key could not be found
	 */
	@Override
	public UserThread[] findByU_R_D_PrevAndNext(
			long userThreadId, long userId, boolean read, boolean deleted,
			OrderByComparator<UserThread> orderByComparator)
		throws NoSuchUserThreadException {

		UserThread userThread = findByPrimaryKey(userThreadId);

		Session session = null;

		try {
			session = openSession();

			UserThread[] array = new UserThreadImpl[3];

			array[0] = getByU_R_D_PrevAndNext(
				session, userThread, userId, read, deleted, orderByComparator,
				true);

			array[1] = userThread;

			array[2] = getByU_R_D_PrevAndNext(
				session, userThread, userId, read, deleted, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserThread getByU_R_D_PrevAndNext(
		Session session, UserThread userThread, long userId, boolean read,
		boolean deleted, OrderByComparator<UserThread> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_USERTHREAD_WHERE);

		sb.append(_FINDER_COLUMN_U_R_D_USERID_2);

		sb.append(_FINDER_COLUMN_U_R_D_READ_2);

		sb.append(_FINDER_COLUMN_U_R_D_DELETED_2);

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
			sb.append(UserThreadModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		queryPos.add(read);

		queryPos.add(deleted);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(userThread)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<UserThread> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user threads where userId = &#63; and read = &#63; and deleted = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param read the read
	 * @param deleted the deleted
	 */
	@Override
	public void removeByU_R_D(long userId, boolean read, boolean deleted) {
		for (UserThread userThread :
				findByU_R_D(
					userId, read, deleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(userThread);
		}
	}

	/**
	 * Returns the number of user threads where userId = &#63; and read = &#63; and deleted = &#63;.
	 *
	 * @param userId the user ID
	 * @param read the read
	 * @param deleted the deleted
	 * @return the number of matching user threads
	 */
	@Override
	public int countByU_R_D(long userId, boolean read, boolean deleted) {
		FinderPath finderPath = _finderPathCountByU_R_D;

		Object[] finderArgs = new Object[] {userId, read, deleted};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_USERTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_U_R_D_USERID_2);

			sb.append(_FINDER_COLUMN_U_R_D_READ_2);

			sb.append(_FINDER_COLUMN_U_R_D_DELETED_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(read);

				queryPos.add(deleted);

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

	private static final String _FINDER_COLUMN_U_R_D_USERID_2 =
		"userThread.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_R_D_READ_2 =
		"userThread.read = ? AND ";

	private static final String _FINDER_COLUMN_U_R_D_DELETED_2 =
		"userThread.deleted = ?";

	public UserThreadPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("read", "read_");

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
				"_dbColumnNames");

			field.setAccessible(true);

			field.set(this, dbColumnNames);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		setModelClass(UserThread.class);
	}

	/**
	 * Caches the user thread in the entity cache if it is enabled.
	 *
	 * @param userThread the user thread
	 */
	@Override
	public void cacheResult(UserThread userThread) {
		entityCache.putResult(
			UserThreadModelImpl.ENTITY_CACHE_ENABLED, UserThreadImpl.class,
			userThread.getPrimaryKey(), userThread);

		finderCache.putResult(
			_finderPathFetchByU_M,
			new Object[] {userThread.getUserId(), userThread.getMbThreadId()},
			userThread);

		userThread.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the user threads in the entity cache if it is enabled.
	 *
	 * @param userThreads the user threads
	 */
	@Override
	public void cacheResult(List<UserThread> userThreads) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (userThreads.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (UserThread userThread : userThreads) {
			if (entityCache.getResult(
					UserThreadModelImpl.ENTITY_CACHE_ENABLED,
					UserThreadImpl.class, userThread.getPrimaryKey()) == null) {

				cacheResult(userThread);
			}
			else {
				userThread.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all user threads.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(UserThreadImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the user thread.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(UserThread userThread) {
		entityCache.removeResult(
			UserThreadModelImpl.ENTITY_CACHE_ENABLED, UserThreadImpl.class,
			userThread.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((UserThreadModelImpl)userThread, true);
	}

	@Override
	public void clearCache(List<UserThread> userThreads) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (UserThread userThread : userThreads) {
			entityCache.removeResult(
				UserThreadModelImpl.ENTITY_CACHE_ENABLED, UserThreadImpl.class,
				userThread.getPrimaryKey());

			clearUniqueFindersCache((UserThreadModelImpl)userThread, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				UserThreadModelImpl.ENTITY_CACHE_ENABLED, UserThreadImpl.class,
				primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		UserThreadModelImpl userThreadModelImpl) {

		Object[] args = new Object[] {
			userThreadModelImpl.getUserId(), userThreadModelImpl.getMbThreadId()
		};

		finderCache.putResult(
			_finderPathCountByU_M, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByU_M, args, userThreadModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		UserThreadModelImpl userThreadModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				userThreadModelImpl.getUserId(),
				userThreadModelImpl.getMbThreadId()
			};

			finderCache.removeResult(_finderPathCountByU_M, args);
			finderCache.removeResult(_finderPathFetchByU_M, args);
		}

		if ((userThreadModelImpl.getColumnBitmask() &
			 _finderPathFetchByU_M.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				userThreadModelImpl.getOriginalUserId(),
				userThreadModelImpl.getOriginalMbThreadId()
			};

			finderCache.removeResult(_finderPathCountByU_M, args);
			finderCache.removeResult(_finderPathFetchByU_M, args);
		}
	}

	/**
	 * Creates a new user thread with the primary key. Does not add the user thread to the database.
	 *
	 * @param userThreadId the primary key for the new user thread
	 * @return the new user thread
	 */
	@Override
	public UserThread create(long userThreadId) {
		UserThread userThread = new UserThreadImpl();

		userThread.setNew(true);
		userThread.setPrimaryKey(userThreadId);

		userThread.setCompanyId(CompanyThreadLocal.getCompanyId());

		return userThread;
	}

	/**
	 * Removes the user thread with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userThreadId the primary key of the user thread
	 * @return the user thread that was removed
	 * @throws NoSuchUserThreadException if a user thread with the primary key could not be found
	 */
	@Override
	public UserThread remove(long userThreadId)
		throws NoSuchUserThreadException {

		return remove((Serializable)userThreadId);
	}

	/**
	 * Removes the user thread with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the user thread
	 * @return the user thread that was removed
	 * @throws NoSuchUserThreadException if a user thread with the primary key could not be found
	 */
	@Override
	public UserThread remove(Serializable primaryKey)
		throws NoSuchUserThreadException {

		Session session = null;

		try {
			session = openSession();

			UserThread userThread = (UserThread)session.get(
				UserThreadImpl.class, primaryKey);

			if (userThread == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchUserThreadException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(userThread);
		}
		catch (NoSuchUserThreadException noSuchEntityException) {
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
	protected UserThread removeImpl(UserThread userThread) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(userThread)) {
				userThread = (UserThread)session.get(
					UserThreadImpl.class, userThread.getPrimaryKeyObj());
			}

			if (userThread != null) {
				session.delete(userThread);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (userThread != null) {
			clearCache(userThread);
		}

		return userThread;
	}

	@Override
	public UserThread updateImpl(UserThread userThread) {
		boolean isNew = userThread.isNew();

		if (!(userThread instanceof UserThreadModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(userThread.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(userThread);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in userThread proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom UserThread implementation " +
					userThread.getClass());
		}

		UserThreadModelImpl userThreadModelImpl =
			(UserThreadModelImpl)userThread;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (userThread.getCreateDate() == null)) {
			if (serviceContext == null) {
				userThread.setCreateDate(date);
			}
			else {
				userThread.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!userThreadModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				userThread.setModifiedDate(date);
			}
			else {
				userThread.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(userThread);

				userThread.setNew(false);
			}
			else {
				userThread = (UserThread)session.merge(userThread);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!UserThreadModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {userThreadModelImpl.getUserId()};

			finderCache.removeResult(_finderPathCountByUserId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUserId, args);

			args = new Object[] {userThreadModelImpl.getMbThreadId()};

			finderCache.removeResult(_finderPathCountByMBThreadId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByMBThreadId, args);

			args = new Object[] {
				userThreadModelImpl.getUserId(), userThreadModelImpl.isDeleted()
			};

			finderCache.removeResult(_finderPathCountByU_D, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByU_D, args);

			args = new Object[] {
				userThreadModelImpl.getUserId(), userThreadModelImpl.isRead(),
				userThreadModelImpl.isDeleted()
			};

			finderCache.removeResult(_finderPathCountByU_R_D, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByU_R_D, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((userThreadModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUserId.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					userThreadModelImpl.getOriginalUserId()
				};

				finderCache.removeResult(_finderPathCountByUserId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);

				args = new Object[] {userThreadModelImpl.getUserId()};

				finderCache.removeResult(_finderPathCountByUserId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);
			}

			if ((userThreadModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByMBThreadId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					userThreadModelImpl.getOriginalMbThreadId()
				};

				finderCache.removeResult(_finderPathCountByMBThreadId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByMBThreadId, args);

				args = new Object[] {userThreadModelImpl.getMbThreadId()};

				finderCache.removeResult(_finderPathCountByMBThreadId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByMBThreadId, args);
			}

			if ((userThreadModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU_D.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					userThreadModelImpl.getOriginalUserId(),
					userThreadModelImpl.getOriginalDeleted()
				};

				finderCache.removeResult(_finderPathCountByU_D, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByU_D, args);

				args = new Object[] {
					userThreadModelImpl.getUserId(),
					userThreadModelImpl.isDeleted()
				};

				finderCache.removeResult(_finderPathCountByU_D, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByU_D, args);
			}

			if ((userThreadModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU_R_D.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					userThreadModelImpl.getOriginalUserId(),
					userThreadModelImpl.getOriginalRead(),
					userThreadModelImpl.getOriginalDeleted()
				};

				finderCache.removeResult(_finderPathCountByU_R_D, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByU_R_D, args);

				args = new Object[] {
					userThreadModelImpl.getUserId(),
					userThreadModelImpl.isRead(),
					userThreadModelImpl.isDeleted()
				};

				finderCache.removeResult(_finderPathCountByU_R_D, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByU_R_D, args);
			}
		}

		entityCache.putResult(
			UserThreadModelImpl.ENTITY_CACHE_ENABLED, UserThreadImpl.class,
			userThread.getPrimaryKey(), userThread, false);

		clearUniqueFindersCache(userThreadModelImpl, false);
		cacheUniqueFindersCache(userThreadModelImpl);

		userThread.resetOriginalValues();

		return userThread;
	}

	/**
	 * Returns the user thread with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the user thread
	 * @return the user thread
	 * @throws NoSuchUserThreadException if a user thread with the primary key could not be found
	 */
	@Override
	public UserThread findByPrimaryKey(Serializable primaryKey)
		throws NoSuchUserThreadException {

		UserThread userThread = fetchByPrimaryKey(primaryKey);

		if (userThread == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchUserThreadException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return userThread;
	}

	/**
	 * Returns the user thread with the primary key or throws a <code>NoSuchUserThreadException</code> if it could not be found.
	 *
	 * @param userThreadId the primary key of the user thread
	 * @return the user thread
	 * @throws NoSuchUserThreadException if a user thread with the primary key could not be found
	 */
	@Override
	public UserThread findByPrimaryKey(long userThreadId)
		throws NoSuchUserThreadException {

		return findByPrimaryKey((Serializable)userThreadId);
	}

	/**
	 * Returns the user thread with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the user thread
	 * @return the user thread, or <code>null</code> if a user thread with the primary key could not be found
	 */
	@Override
	public UserThread fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			UserThreadModelImpl.ENTITY_CACHE_ENABLED, UserThreadImpl.class,
			primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		UserThread userThread = (UserThread)serializable;

		if (userThread == null) {
			Session session = null;

			try {
				session = openSession();

				userThread = (UserThread)session.get(
					UserThreadImpl.class, primaryKey);

				if (userThread != null) {
					cacheResult(userThread);
				}
				else {
					entityCache.putResult(
						UserThreadModelImpl.ENTITY_CACHE_ENABLED,
						UserThreadImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					UserThreadModelImpl.ENTITY_CACHE_ENABLED,
					UserThreadImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return userThread;
	}

	/**
	 * Returns the user thread with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param userThreadId the primary key of the user thread
	 * @return the user thread, or <code>null</code> if a user thread with the primary key could not be found
	 */
	@Override
	public UserThread fetchByPrimaryKey(long userThreadId) {
		return fetchByPrimaryKey((Serializable)userThreadId);
	}

	@Override
	public Map<Serializable, UserThread> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, UserThread> map =
			new HashMap<Serializable, UserThread>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			UserThread userThread = fetchByPrimaryKey(primaryKey);

			if (userThread != null) {
				map.put(primaryKey, userThread);
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
				UserThreadModelImpl.ENTITY_CACHE_ENABLED, UserThreadImpl.class,
				primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (UserThread)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_USERTHREAD_WHERE_PKS_IN);

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

			for (UserThread userThread : (List<UserThread>)query.list()) {
				map.put(userThread.getPrimaryKeyObj(), userThread);

				cacheResult(userThread);

				uncachedPrimaryKeys.remove(userThread.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					UserThreadModelImpl.ENTITY_CACHE_ENABLED,
					UserThreadImpl.class, primaryKey, nullModel);
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
	 * Returns all the user threads.
	 *
	 * @return the user threads
	 */
	@Override
	public List<UserThread> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user threads.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserThreadModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user threads
	 * @param end the upper bound of the range of user threads (not inclusive)
	 * @return the range of user threads
	 */
	@Override
	public List<UserThread> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the user threads.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserThreadModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user threads
	 * @param end the upper bound of the range of user threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user threads
	 */
	@Override
	public List<UserThread> findAll(
		int start, int end, OrderByComparator<UserThread> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user threads.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserThreadModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user threads
	 * @param end the upper bound of the range of user threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of user threads
	 */
	@Override
	public List<UserThread> findAll(
		int start, int end, OrderByComparator<UserThread> orderByComparator,
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

		List<UserThread> list = null;

		if (useFinderCache) {
			list = (List<UserThread>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_USERTHREAD);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_USERTHREAD;

				sql = sql.concat(UserThreadModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<UserThread>)QueryUtil.list(
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
	 * Removes all the user threads from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (UserThread userThread : findAll()) {
			remove(userThread);
		}
	}

	/**
	 * Returns the number of user threads.
	 *
	 * @return the number of user threads
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_USERTHREAD);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return UserThreadModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the user thread persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			UserThreadModelImpl.ENTITY_CACHE_ENABLED,
			UserThreadModelImpl.FINDER_CACHE_ENABLED, UserThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			UserThreadModelImpl.ENTITY_CACHE_ENABLED,
			UserThreadModelImpl.FINDER_CACHE_ENABLED, UserThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			UserThreadModelImpl.ENTITY_CACHE_ENABLED,
			UserThreadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUserId = new FinderPath(
			UserThreadModelImpl.ENTITY_CACHE_ENABLED,
			UserThreadModelImpl.FINDER_CACHE_ENABLED, UserThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			UserThreadModelImpl.ENTITY_CACHE_ENABLED,
			UserThreadModelImpl.FINDER_CACHE_ENABLED, UserThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] {Long.class.getName()},
			UserThreadModelImpl.USERID_COLUMN_BITMASK |
			UserThreadModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByUserId = new FinderPath(
			UserThreadModelImpl.ENTITY_CACHE_ENABLED,
			UserThreadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByMBThreadId = new FinderPath(
			UserThreadModelImpl.ENTITY_CACHE_ENABLED,
			UserThreadModelImpl.FINDER_CACHE_ENABLED, UserThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByMBThreadId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByMBThreadId = new FinderPath(
			UserThreadModelImpl.ENTITY_CACHE_ENABLED,
			UserThreadModelImpl.FINDER_CACHE_ENABLED, UserThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByMBThreadId",
			new String[] {Long.class.getName()},
			UserThreadModelImpl.MBTHREADID_COLUMN_BITMASK |
			UserThreadModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByMBThreadId = new FinderPath(
			UserThreadModelImpl.ENTITY_CACHE_ENABLED,
			UserThreadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByMBThreadId",
			new String[] {Long.class.getName()});

		_finderPathFetchByU_M = new FinderPath(
			UserThreadModelImpl.ENTITY_CACHE_ENABLED,
			UserThreadModelImpl.FINDER_CACHE_ENABLED, UserThreadImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByU_M",
			new String[] {Long.class.getName(), Long.class.getName()},
			UserThreadModelImpl.USERID_COLUMN_BITMASK |
			UserThreadModelImpl.MBTHREADID_COLUMN_BITMASK);

		_finderPathCountByU_M = new FinderPath(
			UserThreadModelImpl.ENTITY_CACHE_ENABLED,
			UserThreadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_M",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByU_D = new FinderPath(
			UserThreadModelImpl.ENTITY_CACHE_ENABLED,
			UserThreadModelImpl.FINDER_CACHE_ENABLED, UserThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_D",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU_D = new FinderPath(
			UserThreadModelImpl.ENTITY_CACHE_ENABLED,
			UserThreadModelImpl.FINDER_CACHE_ENABLED, UserThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_D",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			UserThreadModelImpl.USERID_COLUMN_BITMASK |
			UserThreadModelImpl.DELETED_COLUMN_BITMASK |
			UserThreadModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByU_D = new FinderPath(
			UserThreadModelImpl.ENTITY_CACHE_ENABLED,
			UserThreadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_D",
			new String[] {Long.class.getName(), Boolean.class.getName()});

		_finderPathWithPaginationFindByU_R_D = new FinderPath(
			UserThreadModelImpl.ENTITY_CACHE_ENABLED,
			UserThreadModelImpl.FINDER_CACHE_ENABLED, UserThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_R_D",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU_R_D = new FinderPath(
			UserThreadModelImpl.ENTITY_CACHE_ENABLED,
			UserThreadModelImpl.FINDER_CACHE_ENABLED, UserThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_R_D",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			},
			UserThreadModelImpl.USERID_COLUMN_BITMASK |
			UserThreadModelImpl.READ_COLUMN_BITMASK |
			UserThreadModelImpl.DELETED_COLUMN_BITMASK |
			UserThreadModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByU_R_D = new FinderPath(
			UserThreadModelImpl.ENTITY_CACHE_ENABLED,
			UserThreadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_R_D",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			});

		UserThreadUtil.setPersistence(this);
	}

	public void destroy() {
		UserThreadUtil.setPersistence(null);

		entityCache.removeCache(UserThreadImpl.class.getName());

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

	private static final String _SQL_SELECT_USERTHREAD =
		"SELECT userThread FROM UserThread userThread";

	private static final String _SQL_SELECT_USERTHREAD_WHERE_PKS_IN =
		"SELECT userThread FROM UserThread userThread WHERE userThreadId IN (";

	private static final String _SQL_SELECT_USERTHREAD_WHERE =
		"SELECT userThread FROM UserThread userThread WHERE ";

	private static final String _SQL_COUNT_USERTHREAD =
		"SELECT COUNT(userThread) FROM UserThread userThread";

	private static final String _SQL_COUNT_USERTHREAD_WHERE =
		"SELECT COUNT(userThread) FROM UserThread userThread WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "userThread.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No UserThread exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No UserThread exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		UserThreadPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"read"});

}