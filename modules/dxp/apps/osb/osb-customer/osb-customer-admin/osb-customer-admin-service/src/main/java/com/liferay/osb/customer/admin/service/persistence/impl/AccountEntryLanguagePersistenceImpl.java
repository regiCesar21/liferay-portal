/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.admin.service.persistence.impl;

import com.liferay.osb.customer.admin.exception.NoSuchAccountEntryLanguageException;
import com.liferay.osb.customer.admin.model.AccountEntryLanguage;
import com.liferay.osb.customer.admin.model.impl.AccountEntryLanguageImpl;
import com.liferay.osb.customer.admin.model.impl.AccountEntryLanguageModelImpl;
import com.liferay.osb.customer.admin.service.persistence.AccountEntryLanguagePersistence;
import com.liferay.osb.customer.admin.service.persistence.AccountEntryLanguageUtil;
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
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the account entry language service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AccountEntryLanguagePersistenceImpl
	extends BasePersistenceImpl<AccountEntryLanguage>
	implements AccountEntryLanguagePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AccountEntryLanguageUtil</code> to access the account entry language persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AccountEntryLanguageImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByAccountEntryId;
	private FinderPath _finderPathWithoutPaginationFindByAccountEntryId;
	private FinderPath _finderPathCountByAccountEntryId;

	/**
	 * Returns all the account entry languages where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the matching account entry languages
	 */
	@Override
	public List<AccountEntryLanguage> findByAccountEntryId(
		long accountEntryId) {

		return findByAccountEntryId(
			accountEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the account entry languages where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryLanguageModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account entry languages
	 * @param end the upper bound of the range of account entry languages (not inclusive)
	 * @return the range of matching account entry languages
	 */
	@Override
	public List<AccountEntryLanguage> findByAccountEntryId(
		long accountEntryId, int start, int end) {

		return findByAccountEntryId(accountEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the account entry languages where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryLanguageModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account entry languages
	 * @param end the upper bound of the range of account entry languages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account entry languages
	 */
	@Override
	public List<AccountEntryLanguage> findByAccountEntryId(
		long accountEntryId, int start, int end,
		OrderByComparator<AccountEntryLanguage> orderByComparator) {

		return findByAccountEntryId(
			accountEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the account entry languages where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryLanguageModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account entry languages
	 * @param end the upper bound of the range of account entry languages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account entry languages
	 */
	@Override
	public List<AccountEntryLanguage> findByAccountEntryId(
		long accountEntryId, int start, int end,
		OrderByComparator<AccountEntryLanguage> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByAccountEntryId;
				finderArgs = new Object[] {accountEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByAccountEntryId;
			finderArgs = new Object[] {
				accountEntryId, start, end, orderByComparator
			};
		}

		List<AccountEntryLanguage> list = null;

		if (useFinderCache) {
			list = (List<AccountEntryLanguage>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AccountEntryLanguage accountEntryLanguage : list) {
					if (accountEntryId !=
							accountEntryLanguage.getAccountEntryId()) {

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

			sb.append(_SQL_SELECT_ACCOUNTENTRYLANGUAGE_WHERE);

			sb.append(_FINDER_COLUMN_ACCOUNTENTRYID_ACCOUNTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AccountEntryLanguageModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEntryId);

				list = (List<AccountEntryLanguage>)QueryUtil.list(
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
	 * Returns the first account entry language in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account entry language
	 * @throws NoSuchAccountEntryLanguageException if a matching account entry language could not be found
	 */
	@Override
	public AccountEntryLanguage findByAccountEntryId_First(
			long accountEntryId,
			OrderByComparator<AccountEntryLanguage> orderByComparator)
		throws NoSuchAccountEntryLanguageException {

		AccountEntryLanguage accountEntryLanguage = fetchByAccountEntryId_First(
			accountEntryId, orderByComparator);

		if (accountEntryLanguage != null) {
			return accountEntryLanguage;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountEntryId=");
		sb.append(accountEntryId);

		sb.append("}");

		throw new NoSuchAccountEntryLanguageException(sb.toString());
	}

	/**
	 * Returns the first account entry language in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account entry language, or <code>null</code> if a matching account entry language could not be found
	 */
	@Override
	public AccountEntryLanguage fetchByAccountEntryId_First(
		long accountEntryId,
		OrderByComparator<AccountEntryLanguage> orderByComparator) {

		List<AccountEntryLanguage> list = findByAccountEntryId(
			accountEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last account entry language in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account entry language
	 * @throws NoSuchAccountEntryLanguageException if a matching account entry language could not be found
	 */
	@Override
	public AccountEntryLanguage findByAccountEntryId_Last(
			long accountEntryId,
			OrderByComparator<AccountEntryLanguage> orderByComparator)
		throws NoSuchAccountEntryLanguageException {

		AccountEntryLanguage accountEntryLanguage = fetchByAccountEntryId_Last(
			accountEntryId, orderByComparator);

		if (accountEntryLanguage != null) {
			return accountEntryLanguage;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountEntryId=");
		sb.append(accountEntryId);

		sb.append("}");

		throw new NoSuchAccountEntryLanguageException(sb.toString());
	}

	/**
	 * Returns the last account entry language in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account entry language, or <code>null</code> if a matching account entry language could not be found
	 */
	@Override
	public AccountEntryLanguage fetchByAccountEntryId_Last(
		long accountEntryId,
		OrderByComparator<AccountEntryLanguage> orderByComparator) {

		int count = countByAccountEntryId(accountEntryId);

		if (count == 0) {
			return null;
		}

		List<AccountEntryLanguage> list = findByAccountEntryId(
			accountEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the account entry languages before and after the current account entry language in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryLanguageId the primary key of the current account entry language
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account entry language
	 * @throws NoSuchAccountEntryLanguageException if a account entry language with the primary key could not be found
	 */
	@Override
	public AccountEntryLanguage[] findByAccountEntryId_PrevAndNext(
			long accountEntryLanguageId, long accountEntryId,
			OrderByComparator<AccountEntryLanguage> orderByComparator)
		throws NoSuchAccountEntryLanguageException {

		AccountEntryLanguage accountEntryLanguage = findByPrimaryKey(
			accountEntryLanguageId);

		Session session = null;

		try {
			session = openSession();

			AccountEntryLanguage[] array = new AccountEntryLanguageImpl[3];

			array[0] = getByAccountEntryId_PrevAndNext(
				session, accountEntryLanguage, accountEntryId,
				orderByComparator, true);

			array[1] = accountEntryLanguage;

			array[2] = getByAccountEntryId_PrevAndNext(
				session, accountEntryLanguage, accountEntryId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AccountEntryLanguage getByAccountEntryId_PrevAndNext(
		Session session, AccountEntryLanguage accountEntryLanguage,
		long accountEntryId,
		OrderByComparator<AccountEntryLanguage> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ACCOUNTENTRYLANGUAGE_WHERE);

		sb.append(_FINDER_COLUMN_ACCOUNTENTRYID_ACCOUNTENTRYID_2);

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
			sb.append(AccountEntryLanguageModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(accountEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						accountEntryLanguage)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AccountEntryLanguage> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the account entry languages where accountEntryId = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 */
	@Override
	public void removeByAccountEntryId(long accountEntryId) {
		for (AccountEntryLanguage accountEntryLanguage :
				findByAccountEntryId(
					accountEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(accountEntryLanguage);
		}
	}

	/**
	 * Returns the number of account entry languages where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the number of matching account entry languages
	 */
	@Override
	public int countByAccountEntryId(long accountEntryId) {
		FinderPath finderPath = _finderPathCountByAccountEntryId;

		Object[] finderArgs = new Object[] {accountEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ACCOUNTENTRYLANGUAGE_WHERE);

			sb.append(_FINDER_COLUMN_ACCOUNTENTRYID_ACCOUNTENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEntryId);

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

	private static final String _FINDER_COLUMN_ACCOUNTENTRYID_ACCOUNTENTRYID_2 =
		"accountEntryLanguage.accountEntryId = ?";

	public AccountEntryLanguagePersistenceImpl() {
		setModelClass(AccountEntryLanguage.class);
	}

	/**
	 * Caches the account entry language in the entity cache if it is enabled.
	 *
	 * @param accountEntryLanguage the account entry language
	 */
	@Override
	public void cacheResult(AccountEntryLanguage accountEntryLanguage) {
		entityCache.putResult(
			AccountEntryLanguageModelImpl.ENTITY_CACHE_ENABLED,
			AccountEntryLanguageImpl.class,
			accountEntryLanguage.getPrimaryKey(), accountEntryLanguage);

		accountEntryLanguage.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the account entry languages in the entity cache if it is enabled.
	 *
	 * @param accountEntryLanguages the account entry languages
	 */
	@Override
	public void cacheResult(List<AccountEntryLanguage> accountEntryLanguages) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (accountEntryLanguages.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (AccountEntryLanguage accountEntryLanguage :
				accountEntryLanguages) {

			if (entityCache.getResult(
					AccountEntryLanguageModelImpl.ENTITY_CACHE_ENABLED,
					AccountEntryLanguageImpl.class,
					accountEntryLanguage.getPrimaryKey()) == null) {

				cacheResult(accountEntryLanguage);
			}
			else {
				accountEntryLanguage.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all account entry languages.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AccountEntryLanguageImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the account entry language.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AccountEntryLanguage accountEntryLanguage) {
		entityCache.removeResult(
			AccountEntryLanguageModelImpl.ENTITY_CACHE_ENABLED,
			AccountEntryLanguageImpl.class,
			accountEntryLanguage.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<AccountEntryLanguage> accountEntryLanguages) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AccountEntryLanguage accountEntryLanguage :
				accountEntryLanguages) {

			entityCache.removeResult(
				AccountEntryLanguageModelImpl.ENTITY_CACHE_ENABLED,
				AccountEntryLanguageImpl.class,
				accountEntryLanguage.getPrimaryKey());
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				AccountEntryLanguageModelImpl.ENTITY_CACHE_ENABLED,
				AccountEntryLanguageImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new account entry language with the primary key. Does not add the account entry language to the database.
	 *
	 * @param accountEntryLanguageId the primary key for the new account entry language
	 * @return the new account entry language
	 */
	@Override
	public AccountEntryLanguage create(long accountEntryLanguageId) {
		AccountEntryLanguage accountEntryLanguage =
			new AccountEntryLanguageImpl();

		accountEntryLanguage.setNew(true);
		accountEntryLanguage.setPrimaryKey(accountEntryLanguageId);

		return accountEntryLanguage;
	}

	/**
	 * Removes the account entry language with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountEntryLanguageId the primary key of the account entry language
	 * @return the account entry language that was removed
	 * @throws NoSuchAccountEntryLanguageException if a account entry language with the primary key could not be found
	 */
	@Override
	public AccountEntryLanguage remove(long accountEntryLanguageId)
		throws NoSuchAccountEntryLanguageException {

		return remove((Serializable)accountEntryLanguageId);
	}

	/**
	 * Removes the account entry language with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the account entry language
	 * @return the account entry language that was removed
	 * @throws NoSuchAccountEntryLanguageException if a account entry language with the primary key could not be found
	 */
	@Override
	public AccountEntryLanguage remove(Serializable primaryKey)
		throws NoSuchAccountEntryLanguageException {

		Session session = null;

		try {
			session = openSession();

			AccountEntryLanguage accountEntryLanguage =
				(AccountEntryLanguage)session.get(
					AccountEntryLanguageImpl.class, primaryKey);

			if (accountEntryLanguage == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAccountEntryLanguageException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(accountEntryLanguage);
		}
		catch (NoSuchAccountEntryLanguageException noSuchEntityException) {
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
	protected AccountEntryLanguage removeImpl(
		AccountEntryLanguage accountEntryLanguage) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(accountEntryLanguage)) {
				accountEntryLanguage = (AccountEntryLanguage)session.get(
					AccountEntryLanguageImpl.class,
					accountEntryLanguage.getPrimaryKeyObj());
			}

			if (accountEntryLanguage != null) {
				session.delete(accountEntryLanguage);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (accountEntryLanguage != null) {
			clearCache(accountEntryLanguage);
		}

		return accountEntryLanguage;
	}

	@Override
	public AccountEntryLanguage updateImpl(
		AccountEntryLanguage accountEntryLanguage) {

		boolean isNew = accountEntryLanguage.isNew();

		if (!(accountEntryLanguage instanceof AccountEntryLanguageModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(accountEntryLanguage.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					accountEntryLanguage);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in accountEntryLanguage proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AccountEntryLanguage implementation " +
					accountEntryLanguage.getClass());
		}

		AccountEntryLanguageModelImpl accountEntryLanguageModelImpl =
			(AccountEntryLanguageModelImpl)accountEntryLanguage;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(accountEntryLanguage);

				accountEntryLanguage.setNew(false);
			}
			else {
				accountEntryLanguage = (AccountEntryLanguage)session.merge(
					accountEntryLanguage);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!AccountEntryLanguageModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				accountEntryLanguageModelImpl.getAccountEntryId()
			};

			finderCache.removeResult(_finderPathCountByAccountEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByAccountEntryId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((accountEntryLanguageModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByAccountEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					accountEntryLanguageModelImpl.getOriginalAccountEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByAccountEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAccountEntryId, args);

				args = new Object[] {
					accountEntryLanguageModelImpl.getAccountEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByAccountEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAccountEntryId, args);
			}
		}

		entityCache.putResult(
			AccountEntryLanguageModelImpl.ENTITY_CACHE_ENABLED,
			AccountEntryLanguageImpl.class,
			accountEntryLanguage.getPrimaryKey(), accountEntryLanguage, false);

		accountEntryLanguage.resetOriginalValues();

		return accountEntryLanguage;
	}

	/**
	 * Returns the account entry language with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the account entry language
	 * @return the account entry language
	 * @throws NoSuchAccountEntryLanguageException if a account entry language with the primary key could not be found
	 */
	@Override
	public AccountEntryLanguage findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAccountEntryLanguageException {

		AccountEntryLanguage accountEntryLanguage = fetchByPrimaryKey(
			primaryKey);

		if (accountEntryLanguage == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAccountEntryLanguageException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return accountEntryLanguage;
	}

	/**
	 * Returns the account entry language with the primary key or throws a <code>NoSuchAccountEntryLanguageException</code> if it could not be found.
	 *
	 * @param accountEntryLanguageId the primary key of the account entry language
	 * @return the account entry language
	 * @throws NoSuchAccountEntryLanguageException if a account entry language with the primary key could not be found
	 */
	@Override
	public AccountEntryLanguage findByPrimaryKey(long accountEntryLanguageId)
		throws NoSuchAccountEntryLanguageException {

		return findByPrimaryKey((Serializable)accountEntryLanguageId);
	}

	/**
	 * Returns the account entry language with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the account entry language
	 * @return the account entry language, or <code>null</code> if a account entry language with the primary key could not be found
	 */
	@Override
	public AccountEntryLanguage fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			AccountEntryLanguageModelImpl.ENTITY_CACHE_ENABLED,
			AccountEntryLanguageImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		AccountEntryLanguage accountEntryLanguage =
			(AccountEntryLanguage)serializable;

		if (accountEntryLanguage == null) {
			Session session = null;

			try {
				session = openSession();

				accountEntryLanguage = (AccountEntryLanguage)session.get(
					AccountEntryLanguageImpl.class, primaryKey);

				if (accountEntryLanguage != null) {
					cacheResult(accountEntryLanguage);
				}
				else {
					entityCache.putResult(
						AccountEntryLanguageModelImpl.ENTITY_CACHE_ENABLED,
						AccountEntryLanguageImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					AccountEntryLanguageModelImpl.ENTITY_CACHE_ENABLED,
					AccountEntryLanguageImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return accountEntryLanguage;
	}

	/**
	 * Returns the account entry language with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param accountEntryLanguageId the primary key of the account entry language
	 * @return the account entry language, or <code>null</code> if a account entry language with the primary key could not be found
	 */
	@Override
	public AccountEntryLanguage fetchByPrimaryKey(long accountEntryLanguageId) {
		return fetchByPrimaryKey((Serializable)accountEntryLanguageId);
	}

	@Override
	public Map<Serializable, AccountEntryLanguage> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AccountEntryLanguage> map =
			new HashMap<Serializable, AccountEntryLanguage>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AccountEntryLanguage accountEntryLanguage = fetchByPrimaryKey(
				primaryKey);

			if (accountEntryLanguage != null) {
				map.put(primaryKey, accountEntryLanguage);
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
				AccountEntryLanguageModelImpl.ENTITY_CACHE_ENABLED,
				AccountEntryLanguageImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (AccountEntryLanguage)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_ACCOUNTENTRYLANGUAGE_WHERE_PKS_IN);

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

			for (AccountEntryLanguage accountEntryLanguage :
					(List<AccountEntryLanguage>)query.list()) {

				map.put(
					accountEntryLanguage.getPrimaryKeyObj(),
					accountEntryLanguage);

				cacheResult(accountEntryLanguage);

				uncachedPrimaryKeys.remove(
					accountEntryLanguage.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					AccountEntryLanguageModelImpl.ENTITY_CACHE_ENABLED,
					AccountEntryLanguageImpl.class, primaryKey, nullModel);
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
	 * Returns all the account entry languages.
	 *
	 * @return the account entry languages
	 */
	@Override
	public List<AccountEntryLanguage> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the account entry languages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryLanguageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account entry languages
	 * @param end the upper bound of the range of account entry languages (not inclusive)
	 * @return the range of account entry languages
	 */
	@Override
	public List<AccountEntryLanguage> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the account entry languages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryLanguageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account entry languages
	 * @param end the upper bound of the range of account entry languages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of account entry languages
	 */
	@Override
	public List<AccountEntryLanguage> findAll(
		int start, int end,
		OrderByComparator<AccountEntryLanguage> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the account entry languages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryLanguageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account entry languages
	 * @param end the upper bound of the range of account entry languages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of account entry languages
	 */
	@Override
	public List<AccountEntryLanguage> findAll(
		int start, int end,
		OrderByComparator<AccountEntryLanguage> orderByComparator,
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

		List<AccountEntryLanguage> list = null;

		if (useFinderCache) {
			list = (List<AccountEntryLanguage>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ACCOUNTENTRYLANGUAGE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ACCOUNTENTRYLANGUAGE;

				sql = sql.concat(AccountEntryLanguageModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AccountEntryLanguage>)QueryUtil.list(
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
	 * Removes all the account entry languages from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AccountEntryLanguage accountEntryLanguage : findAll()) {
			remove(accountEntryLanguage);
		}
	}

	/**
	 * Returns the number of account entry languages.
	 *
	 * @return the number of account entry languages
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_ACCOUNTENTRYLANGUAGE);

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
		return AccountEntryLanguageModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the account entry language persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			AccountEntryLanguageModelImpl.ENTITY_CACHE_ENABLED,
			AccountEntryLanguageModelImpl.FINDER_CACHE_ENABLED,
			AccountEntryLanguageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			AccountEntryLanguageModelImpl.ENTITY_CACHE_ENABLED,
			AccountEntryLanguageModelImpl.FINDER_CACHE_ENABLED,
			AccountEntryLanguageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			AccountEntryLanguageModelImpl.ENTITY_CACHE_ENABLED,
			AccountEntryLanguageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByAccountEntryId = new FinderPath(
			AccountEntryLanguageModelImpl.ENTITY_CACHE_ENABLED,
			AccountEntryLanguageModelImpl.FINDER_CACHE_ENABLED,
			AccountEntryLanguageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAccountEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByAccountEntryId = new FinderPath(
			AccountEntryLanguageModelImpl.ENTITY_CACHE_ENABLED,
			AccountEntryLanguageModelImpl.FINDER_CACHE_ENABLED,
			AccountEntryLanguageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAccountEntryId",
			new String[] {Long.class.getName()},
			AccountEntryLanguageModelImpl.ACCOUNTENTRYID_COLUMN_BITMASK |
			AccountEntryLanguageModelImpl.LANGUAGEID_COLUMN_BITMASK);

		_finderPathCountByAccountEntryId = new FinderPath(
			AccountEntryLanguageModelImpl.ENTITY_CACHE_ENABLED,
			AccountEntryLanguageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAccountEntryId",
			new String[] {Long.class.getName()});

		AccountEntryLanguageUtil.setPersistence(this);
	}

	public void destroy() {
		AccountEntryLanguageUtil.setPersistence(null);

		entityCache.removeCache(AccountEntryLanguageImpl.class.getName());

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

	private static final String _SQL_SELECT_ACCOUNTENTRYLANGUAGE =
		"SELECT accountEntryLanguage FROM AccountEntryLanguage accountEntryLanguage";

	private static final String _SQL_SELECT_ACCOUNTENTRYLANGUAGE_WHERE_PKS_IN =
		"SELECT accountEntryLanguage FROM AccountEntryLanguage accountEntryLanguage WHERE accountEntryLanguageId IN (";

	private static final String _SQL_SELECT_ACCOUNTENTRYLANGUAGE_WHERE =
		"SELECT accountEntryLanguage FROM AccountEntryLanguage accountEntryLanguage WHERE ";

	private static final String _SQL_COUNT_ACCOUNTENTRYLANGUAGE =
		"SELECT COUNT(accountEntryLanguage) FROM AccountEntryLanguage accountEntryLanguage";

	private static final String _SQL_COUNT_ACCOUNTENTRYLANGUAGE_WHERE =
		"SELECT COUNT(accountEntryLanguage) FROM AccountEntryLanguage accountEntryLanguage WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"accountEntryLanguage.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AccountEntryLanguage exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AccountEntryLanguage exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AccountEntryLanguagePersistenceImpl.class);

}