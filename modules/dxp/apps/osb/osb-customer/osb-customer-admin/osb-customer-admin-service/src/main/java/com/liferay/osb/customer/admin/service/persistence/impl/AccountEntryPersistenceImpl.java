/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.admin.service.persistence.impl;

import com.liferay.osb.customer.admin.exception.NoSuchAccountEntryException;
import com.liferay.osb.customer.admin.model.AccountEntry;
import com.liferay.osb.customer.admin.model.impl.AccountEntryImpl;
import com.liferay.osb.customer.admin.model.impl.AccountEntryModelImpl;
import com.liferay.osb.customer.admin.service.persistence.AccountEntryPersistence;
import com.liferay.osb.customer.admin.service.persistence.AccountEntryUtil;
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
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the account entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AccountEntryPersistenceImpl
	extends BasePersistenceImpl<AccountEntry>
	implements AccountEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AccountEntryUtil</code> to access the account entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AccountEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByKoroneikiAccountKey;
	private FinderPath _finderPathCountByKoroneikiAccountKey;

	/**
	 * Returns the account entry where koroneikiAccountKey = &#63; or throws a <code>NoSuchAccountEntryException</code> if it could not be found.
	 *
	 * @param koroneikiAccountKey the koroneiki account key
	 * @return the matching account entry
	 * @throws NoSuchAccountEntryException if a matching account entry could not be found
	 */
	@Override
	public AccountEntry findByKoroneikiAccountKey(String koroneikiAccountKey)
		throws NoSuchAccountEntryException {

		AccountEntry accountEntry = fetchByKoroneikiAccountKey(
			koroneikiAccountKey);

		if (accountEntry == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("koroneikiAccountKey=");
			sb.append(koroneikiAccountKey);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchAccountEntryException(sb.toString());
		}

		return accountEntry;
	}

	/**
	 * Returns the account entry where koroneikiAccountKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param koroneikiAccountKey the koroneiki account key
	 * @return the matching account entry, or <code>null</code> if a matching account entry could not be found
	 */
	@Override
	public AccountEntry fetchByKoroneikiAccountKey(String koroneikiAccountKey) {
		return fetchByKoroneikiAccountKey(koroneikiAccountKey, true);
	}

	/**
	 * Returns the account entry where koroneikiAccountKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param koroneikiAccountKey the koroneiki account key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching account entry, or <code>null</code> if a matching account entry could not be found
	 */
	@Override
	public AccountEntry fetchByKoroneikiAccountKey(
		String koroneikiAccountKey, boolean useFinderCache) {

		koroneikiAccountKey = Objects.toString(koroneikiAccountKey, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {koroneikiAccountKey};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByKoroneikiAccountKey, finderArgs, this);
		}

		if (result instanceof AccountEntry) {
			AccountEntry accountEntry = (AccountEntry)result;

			if (!Objects.equals(
					koroneikiAccountKey,
					accountEntry.getKoroneikiAccountKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_ACCOUNTENTRY_WHERE);

			boolean bindKoroneikiAccountKey = false;

			if (koroneikiAccountKey.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_KORONEIKIACCOUNTKEY_KORONEIKIACCOUNTKEY_3);
			}
			else {
				bindKoroneikiAccountKey = true;

				sb.append(
					_FINDER_COLUMN_KORONEIKIACCOUNTKEY_KORONEIKIACCOUNTKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindKoroneikiAccountKey) {
					queryPos.add(koroneikiAccountKey);
				}

				List<AccountEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByKoroneikiAccountKey, finderArgs,
							list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {koroneikiAccountKey};
							}

							_log.warn(
								"AccountEntryPersistenceImpl.fetchByKoroneikiAccountKey(String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					AccountEntry accountEntry = list.get(0);

					result = accountEntry;

					cacheResult(accountEntry);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByKoroneikiAccountKey, finderArgs);
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
			return (AccountEntry)result;
		}
	}

	/**
	 * Removes the account entry where koroneikiAccountKey = &#63; from the database.
	 *
	 * @param koroneikiAccountKey the koroneiki account key
	 * @return the account entry that was removed
	 */
	@Override
	public AccountEntry removeByKoroneikiAccountKey(String koroneikiAccountKey)
		throws NoSuchAccountEntryException {

		AccountEntry accountEntry = findByKoroneikiAccountKey(
			koroneikiAccountKey);

		return remove(accountEntry);
	}

	/**
	 * Returns the number of account entries where koroneikiAccountKey = &#63;.
	 *
	 * @param koroneikiAccountKey the koroneiki account key
	 * @return the number of matching account entries
	 */
	@Override
	public int countByKoroneikiAccountKey(String koroneikiAccountKey) {
		koroneikiAccountKey = Objects.toString(koroneikiAccountKey, "");

		FinderPath finderPath = _finderPathCountByKoroneikiAccountKey;

		Object[] finderArgs = new Object[] {koroneikiAccountKey};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ACCOUNTENTRY_WHERE);

			boolean bindKoroneikiAccountKey = false;

			if (koroneikiAccountKey.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_KORONEIKIACCOUNTKEY_KORONEIKIACCOUNTKEY_3);
			}
			else {
				bindKoroneikiAccountKey = true;

				sb.append(
					_FINDER_COLUMN_KORONEIKIACCOUNTKEY_KORONEIKIACCOUNTKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindKoroneikiAccountKey) {
					queryPos.add(koroneikiAccountKey);
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
		_FINDER_COLUMN_KORONEIKIACCOUNTKEY_KORONEIKIACCOUNTKEY_2 =
			"accountEntry.koroneikiAccountKey = ?";

	private static final String
		_FINDER_COLUMN_KORONEIKIACCOUNTKEY_KORONEIKIACCOUNTKEY_3 =
			"(accountEntry.koroneikiAccountKey IS NULL OR accountEntry.koroneikiAccountKey = '')";

	private FinderPath _finderPathWithPaginationFindByDossieraAccountKey;
	private FinderPath _finderPathWithoutPaginationFindByDossieraAccountKey;
	private FinderPath _finderPathCountByDossieraAccountKey;

	/**
	 * Returns all the account entries where dossieraAccountKey = &#63;.
	 *
	 * @param dossieraAccountKey the dossiera account key
	 * @return the matching account entries
	 */
	@Override
	public List<AccountEntry> findByDossieraAccountKey(
		String dossieraAccountKey) {

		return findByDossieraAccountKey(
			dossieraAccountKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the account entries where dossieraAccountKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dossieraAccountKey the dossiera account key
	 * @param start the lower bound of the range of account entries
	 * @param end the upper bound of the range of account entries (not inclusive)
	 * @return the range of matching account entries
	 */
	@Override
	public List<AccountEntry> findByDossieraAccountKey(
		String dossieraAccountKey, int start, int end) {

		return findByDossieraAccountKey(dossieraAccountKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the account entries where dossieraAccountKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dossieraAccountKey the dossiera account key
	 * @param start the lower bound of the range of account entries
	 * @param end the upper bound of the range of account entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account entries
	 */
	@Override
	public List<AccountEntry> findByDossieraAccountKey(
		String dossieraAccountKey, int start, int end,
		OrderByComparator<AccountEntry> orderByComparator) {

		return findByDossieraAccountKey(
			dossieraAccountKey, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the account entries where dossieraAccountKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dossieraAccountKey the dossiera account key
	 * @param start the lower bound of the range of account entries
	 * @param end the upper bound of the range of account entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account entries
	 */
	@Override
	public List<AccountEntry> findByDossieraAccountKey(
		String dossieraAccountKey, int start, int end,
		OrderByComparator<AccountEntry> orderByComparator,
		boolean useFinderCache) {

		dossieraAccountKey = Objects.toString(dossieraAccountKey, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByDossieraAccountKey;
				finderArgs = new Object[] {dossieraAccountKey};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByDossieraAccountKey;
			finderArgs = new Object[] {
				dossieraAccountKey, start, end, orderByComparator
			};
		}

		List<AccountEntry> list = null;

		if (useFinderCache) {
			list = (List<AccountEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AccountEntry accountEntry : list) {
					if (!dossieraAccountKey.equals(
							accountEntry.getDossieraAccountKey())) {

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

			sb.append(_SQL_SELECT_ACCOUNTENTRY_WHERE);

			boolean bindDossieraAccountKey = false;

			if (dossieraAccountKey.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_DOSSIERAACCOUNTKEY_DOSSIERAACCOUNTKEY_3);
			}
			else {
				bindDossieraAccountKey = true;

				sb.append(
					_FINDER_COLUMN_DOSSIERAACCOUNTKEY_DOSSIERAACCOUNTKEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AccountEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindDossieraAccountKey) {
					queryPos.add(dossieraAccountKey);
				}

				list = (List<AccountEntry>)QueryUtil.list(
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
	 * Returns the first account entry in the ordered set where dossieraAccountKey = &#63;.
	 *
	 * @param dossieraAccountKey the dossiera account key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account entry
	 * @throws NoSuchAccountEntryException if a matching account entry could not be found
	 */
	@Override
	public AccountEntry findByDossieraAccountKey_First(
			String dossieraAccountKey,
			OrderByComparator<AccountEntry> orderByComparator)
		throws NoSuchAccountEntryException {

		AccountEntry accountEntry = fetchByDossieraAccountKey_First(
			dossieraAccountKey, orderByComparator);

		if (accountEntry != null) {
			return accountEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("dossieraAccountKey=");
		sb.append(dossieraAccountKey);

		sb.append("}");

		throw new NoSuchAccountEntryException(sb.toString());
	}

	/**
	 * Returns the first account entry in the ordered set where dossieraAccountKey = &#63;.
	 *
	 * @param dossieraAccountKey the dossiera account key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account entry, or <code>null</code> if a matching account entry could not be found
	 */
	@Override
	public AccountEntry fetchByDossieraAccountKey_First(
		String dossieraAccountKey,
		OrderByComparator<AccountEntry> orderByComparator) {

		List<AccountEntry> list = findByDossieraAccountKey(
			dossieraAccountKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last account entry in the ordered set where dossieraAccountKey = &#63;.
	 *
	 * @param dossieraAccountKey the dossiera account key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account entry
	 * @throws NoSuchAccountEntryException if a matching account entry could not be found
	 */
	@Override
	public AccountEntry findByDossieraAccountKey_Last(
			String dossieraAccountKey,
			OrderByComparator<AccountEntry> orderByComparator)
		throws NoSuchAccountEntryException {

		AccountEntry accountEntry = fetchByDossieraAccountKey_Last(
			dossieraAccountKey, orderByComparator);

		if (accountEntry != null) {
			return accountEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("dossieraAccountKey=");
		sb.append(dossieraAccountKey);

		sb.append("}");

		throw new NoSuchAccountEntryException(sb.toString());
	}

	/**
	 * Returns the last account entry in the ordered set where dossieraAccountKey = &#63;.
	 *
	 * @param dossieraAccountKey the dossiera account key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account entry, or <code>null</code> if a matching account entry could not be found
	 */
	@Override
	public AccountEntry fetchByDossieraAccountKey_Last(
		String dossieraAccountKey,
		OrderByComparator<AccountEntry> orderByComparator) {

		int count = countByDossieraAccountKey(dossieraAccountKey);

		if (count == 0) {
			return null;
		}

		List<AccountEntry> list = findByDossieraAccountKey(
			dossieraAccountKey, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the account entries before and after the current account entry in the ordered set where dossieraAccountKey = &#63;.
	 *
	 * @param accountEntryId the primary key of the current account entry
	 * @param dossieraAccountKey the dossiera account key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account entry
	 * @throws NoSuchAccountEntryException if a account entry with the primary key could not be found
	 */
	@Override
	public AccountEntry[] findByDossieraAccountKey_PrevAndNext(
			long accountEntryId, String dossieraAccountKey,
			OrderByComparator<AccountEntry> orderByComparator)
		throws NoSuchAccountEntryException {

		dossieraAccountKey = Objects.toString(dossieraAccountKey, "");

		AccountEntry accountEntry = findByPrimaryKey(accountEntryId);

		Session session = null;

		try {
			session = openSession();

			AccountEntry[] array = new AccountEntryImpl[3];

			array[0] = getByDossieraAccountKey_PrevAndNext(
				session, accountEntry, dossieraAccountKey, orderByComparator,
				true);

			array[1] = accountEntry;

			array[2] = getByDossieraAccountKey_PrevAndNext(
				session, accountEntry, dossieraAccountKey, orderByComparator,
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

	protected AccountEntry getByDossieraAccountKey_PrevAndNext(
		Session session, AccountEntry accountEntry, String dossieraAccountKey,
		OrderByComparator<AccountEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ACCOUNTENTRY_WHERE);

		boolean bindDossieraAccountKey = false;

		if (dossieraAccountKey.isEmpty()) {
			sb.append(_FINDER_COLUMN_DOSSIERAACCOUNTKEY_DOSSIERAACCOUNTKEY_3);
		}
		else {
			bindDossieraAccountKey = true;

			sb.append(_FINDER_COLUMN_DOSSIERAACCOUNTKEY_DOSSIERAACCOUNTKEY_2);
		}

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
			sb.append(AccountEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindDossieraAccountKey) {
			queryPos.add(dossieraAccountKey);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(accountEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AccountEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the account entries where dossieraAccountKey = &#63; from the database.
	 *
	 * @param dossieraAccountKey the dossiera account key
	 */
	@Override
	public void removeByDossieraAccountKey(String dossieraAccountKey) {
		for (AccountEntry accountEntry :
				findByDossieraAccountKey(
					dossieraAccountKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(accountEntry);
		}
	}

	/**
	 * Returns the number of account entries where dossieraAccountKey = &#63;.
	 *
	 * @param dossieraAccountKey the dossiera account key
	 * @return the number of matching account entries
	 */
	@Override
	public int countByDossieraAccountKey(String dossieraAccountKey) {
		dossieraAccountKey = Objects.toString(dossieraAccountKey, "");

		FinderPath finderPath = _finderPathCountByDossieraAccountKey;

		Object[] finderArgs = new Object[] {dossieraAccountKey};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ACCOUNTENTRY_WHERE);

			boolean bindDossieraAccountKey = false;

			if (dossieraAccountKey.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_DOSSIERAACCOUNTKEY_DOSSIERAACCOUNTKEY_3);
			}
			else {
				bindDossieraAccountKey = true;

				sb.append(
					_FINDER_COLUMN_DOSSIERAACCOUNTKEY_DOSSIERAACCOUNTKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindDossieraAccountKey) {
					queryPos.add(dossieraAccountKey);
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
		_FINDER_COLUMN_DOSSIERAACCOUNTKEY_DOSSIERAACCOUNTKEY_2 =
			"accountEntry.dossieraAccountKey = ?";

	private static final String
		_FINDER_COLUMN_DOSSIERAACCOUNTKEY_DOSSIERAACCOUNTKEY_3 =
			"(accountEntry.dossieraAccountKey IS NULL OR accountEntry.dossieraAccountKey = '')";

	private FinderPath _finderPathFetchByCorpProjectUuid;
	private FinderPath _finderPathCountByCorpProjectUuid;

	/**
	 * Returns the account entry where corpProjectUuid = &#63; or throws a <code>NoSuchAccountEntryException</code> if it could not be found.
	 *
	 * @param corpProjectUuid the corp project uuid
	 * @return the matching account entry
	 * @throws NoSuchAccountEntryException if a matching account entry could not be found
	 */
	@Override
	public AccountEntry findByCorpProjectUuid(String corpProjectUuid)
		throws NoSuchAccountEntryException {

		AccountEntry accountEntry = fetchByCorpProjectUuid(corpProjectUuid);

		if (accountEntry == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("corpProjectUuid=");
			sb.append(corpProjectUuid);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchAccountEntryException(sb.toString());
		}

		return accountEntry;
	}

	/**
	 * Returns the account entry where corpProjectUuid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param corpProjectUuid the corp project uuid
	 * @return the matching account entry, or <code>null</code> if a matching account entry could not be found
	 */
	@Override
	public AccountEntry fetchByCorpProjectUuid(String corpProjectUuid) {
		return fetchByCorpProjectUuid(corpProjectUuid, true);
	}

	/**
	 * Returns the account entry where corpProjectUuid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param corpProjectUuid the corp project uuid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching account entry, or <code>null</code> if a matching account entry could not be found
	 */
	@Override
	public AccountEntry fetchByCorpProjectUuid(
		String corpProjectUuid, boolean useFinderCache) {

		corpProjectUuid = Objects.toString(corpProjectUuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {corpProjectUuid};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByCorpProjectUuid, finderArgs, this);
		}

		if (result instanceof AccountEntry) {
			AccountEntry accountEntry = (AccountEntry)result;

			if (!Objects.equals(
					corpProjectUuid, accountEntry.getCorpProjectUuid())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_ACCOUNTENTRY_WHERE);

			boolean bindCorpProjectUuid = false;

			if (corpProjectUuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_CORPPROJECTUUID_CORPPROJECTUUID_3);
			}
			else {
				bindCorpProjectUuid = true;

				sb.append(_FINDER_COLUMN_CORPPROJECTUUID_CORPPROJECTUUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindCorpProjectUuid) {
					queryPos.add(corpProjectUuid);
				}

				List<AccountEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByCorpProjectUuid, finderArgs,
							list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {corpProjectUuid};
							}

							_log.warn(
								"AccountEntryPersistenceImpl.fetchByCorpProjectUuid(String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					AccountEntry accountEntry = list.get(0);

					result = accountEntry;

					cacheResult(accountEntry);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByCorpProjectUuid, finderArgs);
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
			return (AccountEntry)result;
		}
	}

	/**
	 * Removes the account entry where corpProjectUuid = &#63; from the database.
	 *
	 * @param corpProjectUuid the corp project uuid
	 * @return the account entry that was removed
	 */
	@Override
	public AccountEntry removeByCorpProjectUuid(String corpProjectUuid)
		throws NoSuchAccountEntryException {

		AccountEntry accountEntry = findByCorpProjectUuid(corpProjectUuid);

		return remove(accountEntry);
	}

	/**
	 * Returns the number of account entries where corpProjectUuid = &#63;.
	 *
	 * @param corpProjectUuid the corp project uuid
	 * @return the number of matching account entries
	 */
	@Override
	public int countByCorpProjectUuid(String corpProjectUuid) {
		corpProjectUuid = Objects.toString(corpProjectUuid, "");

		FinderPath finderPath = _finderPathCountByCorpProjectUuid;

		Object[] finderArgs = new Object[] {corpProjectUuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ACCOUNTENTRY_WHERE);

			boolean bindCorpProjectUuid = false;

			if (corpProjectUuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_CORPPROJECTUUID_CORPPROJECTUUID_3);
			}
			else {
				bindCorpProjectUuid = true;

				sb.append(_FINDER_COLUMN_CORPPROJECTUUID_CORPPROJECTUUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindCorpProjectUuid) {
					queryPos.add(corpProjectUuid);
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
		_FINDER_COLUMN_CORPPROJECTUUID_CORPPROJECTUUID_2 =
			"accountEntry.corpProjectUuid = ?";

	private static final String
		_FINDER_COLUMN_CORPPROJECTUUID_CORPPROJECTUUID_3 =
			"(accountEntry.corpProjectUuid IS NULL OR accountEntry.corpProjectUuid = '')";

	private FinderPath _finderPathWithPaginationFindByN_C;
	private FinderPath _finderPathWithPaginationCountByN_C;

	/**
	 * Returns all the account entries where name LIKE &#63; and code LIKE &#63;.
	 *
	 * @param name the name
	 * @param code the code
	 * @return the matching account entries
	 */
	@Override
	public List<AccountEntry> findByN_C(String name, String code) {
		return findByN_C(
			name, code, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the account entries where name LIKE &#63; and code LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param code the code
	 * @param start the lower bound of the range of account entries
	 * @param end the upper bound of the range of account entries (not inclusive)
	 * @return the range of matching account entries
	 */
	@Override
	public List<AccountEntry> findByN_C(
		String name, String code, int start, int end) {

		return findByN_C(name, code, start, end, null);
	}

	/**
	 * Returns an ordered range of all the account entries where name LIKE &#63; and code LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param code the code
	 * @param start the lower bound of the range of account entries
	 * @param end the upper bound of the range of account entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account entries
	 */
	@Override
	public List<AccountEntry> findByN_C(
		String name, String code, int start, int end,
		OrderByComparator<AccountEntry> orderByComparator) {

		return findByN_C(name, code, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the account entries where name LIKE &#63; and code LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param code the code
	 * @param start the lower bound of the range of account entries
	 * @param end the upper bound of the range of account entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account entries
	 */
	@Override
	public List<AccountEntry> findByN_C(
		String name, String code, int start, int end,
		OrderByComparator<AccountEntry> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");
		code = Objects.toString(code, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByN_C;
		finderArgs = new Object[] {name, code, start, end, orderByComparator};

		List<AccountEntry> list = null;

		if (useFinderCache) {
			list = (List<AccountEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AccountEntry accountEntry : list) {
					if (!StringUtil.wildcardMatches(
							accountEntry.getName(), name, '_', '%', '\\',
							false) ||
						!StringUtil.wildcardMatches(
							accountEntry.getCode(), code, '_', '%', '\\',
							false)) {

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

			sb.append(_SQL_SELECT_ACCOUNTENTRY_WHERE);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_N_C_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_N_C_NAME_2);
			}

			boolean bindCode = false;

			if (code.isEmpty()) {
				sb.append(_FINDER_COLUMN_N_C_CODE_3);
			}
			else {
				bindCode = true;

				sb.append(_FINDER_COLUMN_N_C_CODE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AccountEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(StringUtil.toLowerCase(name));
				}

				if (bindCode) {
					queryPos.add(StringUtil.toLowerCase(code));
				}

				list = (List<AccountEntry>)QueryUtil.list(
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
	 * Returns the first account entry in the ordered set where name LIKE &#63; and code LIKE &#63;.
	 *
	 * @param name the name
	 * @param code the code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account entry
	 * @throws NoSuchAccountEntryException if a matching account entry could not be found
	 */
	@Override
	public AccountEntry findByN_C_First(
			String name, String code,
			OrderByComparator<AccountEntry> orderByComparator)
		throws NoSuchAccountEntryException {

		AccountEntry accountEntry = fetchByN_C_First(
			name, code, orderByComparator);

		if (accountEntry != null) {
			return accountEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("nameLIKE");
		sb.append(name);

		sb.append(", codeLIKE");
		sb.append(code);

		sb.append("}");

		throw new NoSuchAccountEntryException(sb.toString());
	}

	/**
	 * Returns the first account entry in the ordered set where name LIKE &#63; and code LIKE &#63;.
	 *
	 * @param name the name
	 * @param code the code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account entry, or <code>null</code> if a matching account entry could not be found
	 */
	@Override
	public AccountEntry fetchByN_C_First(
		String name, String code,
		OrderByComparator<AccountEntry> orderByComparator) {

		List<AccountEntry> list = findByN_C(
			name, code, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last account entry in the ordered set where name LIKE &#63; and code LIKE &#63;.
	 *
	 * @param name the name
	 * @param code the code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account entry
	 * @throws NoSuchAccountEntryException if a matching account entry could not be found
	 */
	@Override
	public AccountEntry findByN_C_Last(
			String name, String code,
			OrderByComparator<AccountEntry> orderByComparator)
		throws NoSuchAccountEntryException {

		AccountEntry accountEntry = fetchByN_C_Last(
			name, code, orderByComparator);

		if (accountEntry != null) {
			return accountEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("nameLIKE");
		sb.append(name);

		sb.append(", codeLIKE");
		sb.append(code);

		sb.append("}");

		throw new NoSuchAccountEntryException(sb.toString());
	}

	/**
	 * Returns the last account entry in the ordered set where name LIKE &#63; and code LIKE &#63;.
	 *
	 * @param name the name
	 * @param code the code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account entry, or <code>null</code> if a matching account entry could not be found
	 */
	@Override
	public AccountEntry fetchByN_C_Last(
		String name, String code,
		OrderByComparator<AccountEntry> orderByComparator) {

		int count = countByN_C(name, code);

		if (count == 0) {
			return null;
		}

		List<AccountEntry> list = findByN_C(
			name, code, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the account entries before and after the current account entry in the ordered set where name LIKE &#63; and code LIKE &#63;.
	 *
	 * @param accountEntryId the primary key of the current account entry
	 * @param name the name
	 * @param code the code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account entry
	 * @throws NoSuchAccountEntryException if a account entry with the primary key could not be found
	 */
	@Override
	public AccountEntry[] findByN_C_PrevAndNext(
			long accountEntryId, String name, String code,
			OrderByComparator<AccountEntry> orderByComparator)
		throws NoSuchAccountEntryException {

		name = Objects.toString(name, "");
		code = Objects.toString(code, "");

		AccountEntry accountEntry = findByPrimaryKey(accountEntryId);

		Session session = null;

		try {
			session = openSession();

			AccountEntry[] array = new AccountEntryImpl[3];

			array[0] = getByN_C_PrevAndNext(
				session, accountEntry, name, code, orderByComparator, true);

			array[1] = accountEntry;

			array[2] = getByN_C_PrevAndNext(
				session, accountEntry, name, code, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AccountEntry getByN_C_PrevAndNext(
		Session session, AccountEntry accountEntry, String name, String code,
		OrderByComparator<AccountEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_ACCOUNTENTRY_WHERE);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_N_C_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_N_C_NAME_2);
		}

		boolean bindCode = false;

		if (code.isEmpty()) {
			sb.append(_FINDER_COLUMN_N_C_CODE_3);
		}
		else {
			bindCode = true;

			sb.append(_FINDER_COLUMN_N_C_CODE_2);
		}

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
			sb.append(AccountEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindName) {
			queryPos.add(StringUtil.toLowerCase(name));
		}

		if (bindCode) {
			queryPos.add(StringUtil.toLowerCase(code));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(accountEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AccountEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the account entries where name LIKE &#63; and code LIKE &#63; from the database.
	 *
	 * @param name the name
	 * @param code the code
	 */
	@Override
	public void removeByN_C(String name, String code) {
		for (AccountEntry accountEntry :
				findByN_C(
					name, code, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(accountEntry);
		}
	}

	/**
	 * Returns the number of account entries where name LIKE &#63; and code LIKE &#63;.
	 *
	 * @param name the name
	 * @param code the code
	 * @return the number of matching account entries
	 */
	@Override
	public int countByN_C(String name, String code) {
		name = Objects.toString(name, "");
		code = Objects.toString(code, "");

		FinderPath finderPath = _finderPathWithPaginationCountByN_C;

		Object[] finderArgs = new Object[] {name, code};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ACCOUNTENTRY_WHERE);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_N_C_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_N_C_NAME_2);
			}

			boolean bindCode = false;

			if (code.isEmpty()) {
				sb.append(_FINDER_COLUMN_N_C_CODE_3);
			}
			else {
				bindCode = true;

				sb.append(_FINDER_COLUMN_N_C_CODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(StringUtil.toLowerCase(name));
				}

				if (bindCode) {
					queryPos.add(StringUtil.toLowerCase(code));
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

	private static final String _FINDER_COLUMN_N_C_NAME_2 =
		"lower(accountEntry.name) LIKE ? AND ";

	private static final String _FINDER_COLUMN_N_C_NAME_3 =
		"(accountEntry.name IS NULL OR accountEntry.name LIKE '') AND ";

	private static final String _FINDER_COLUMN_N_C_CODE_2 =
		"lower(accountEntry.code) LIKE ?";

	private static final String _FINDER_COLUMN_N_C_CODE_3 =
		"(accountEntry.code IS NULL OR accountEntry.code LIKE '')";

	public AccountEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("code", "code_");

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

		setModelClass(AccountEntry.class);
	}

	/**
	 * Caches the account entry in the entity cache if it is enabled.
	 *
	 * @param accountEntry the account entry
	 */
	@Override
	public void cacheResult(AccountEntry accountEntry) {
		entityCache.putResult(
			AccountEntryModelImpl.ENTITY_CACHE_ENABLED, AccountEntryImpl.class,
			accountEntry.getPrimaryKey(), accountEntry);

		finderCache.putResult(
			_finderPathFetchByKoroneikiAccountKey,
			new Object[] {accountEntry.getKoroneikiAccountKey()}, accountEntry);

		finderCache.putResult(
			_finderPathFetchByCorpProjectUuid,
			new Object[] {accountEntry.getCorpProjectUuid()}, accountEntry);

		accountEntry.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the account entries in the entity cache if it is enabled.
	 *
	 * @param accountEntries the account entries
	 */
	@Override
	public void cacheResult(List<AccountEntry> accountEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (accountEntries.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (AccountEntry accountEntry : accountEntries) {
			if (entityCache.getResult(
					AccountEntryModelImpl.ENTITY_CACHE_ENABLED,
					AccountEntryImpl.class, accountEntry.getPrimaryKey()) ==
						null) {

				cacheResult(accountEntry);
			}
			else {
				accountEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all account entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AccountEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the account entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AccountEntry accountEntry) {
		entityCache.removeResult(
			AccountEntryModelImpl.ENTITY_CACHE_ENABLED, AccountEntryImpl.class,
			accountEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((AccountEntryModelImpl)accountEntry, true);
	}

	@Override
	public void clearCache(List<AccountEntry> accountEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AccountEntry accountEntry : accountEntries) {
			entityCache.removeResult(
				AccountEntryModelImpl.ENTITY_CACHE_ENABLED,
				AccountEntryImpl.class, accountEntry.getPrimaryKey());

			clearUniqueFindersCache((AccountEntryModelImpl)accountEntry, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				AccountEntryModelImpl.ENTITY_CACHE_ENABLED,
				AccountEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AccountEntryModelImpl accountEntryModelImpl) {

		Object[] args = new Object[] {
			accountEntryModelImpl.getKoroneikiAccountKey()
		};

		finderCache.putResult(
			_finderPathCountByKoroneikiAccountKey, args, Long.valueOf(1),
			false);
		finderCache.putResult(
			_finderPathFetchByKoroneikiAccountKey, args, accountEntryModelImpl,
			false);

		args = new Object[] {accountEntryModelImpl.getCorpProjectUuid()};

		finderCache.putResult(
			_finderPathCountByCorpProjectUuid, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByCorpProjectUuid, args, accountEntryModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		AccountEntryModelImpl accountEntryModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				accountEntryModelImpl.getKoroneikiAccountKey()
			};

			finderCache.removeResult(
				_finderPathCountByKoroneikiAccountKey, args);
			finderCache.removeResult(
				_finderPathFetchByKoroneikiAccountKey, args);
		}

		if ((accountEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByKoroneikiAccountKey.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				accountEntryModelImpl.getOriginalKoroneikiAccountKey()
			};

			finderCache.removeResult(
				_finderPathCountByKoroneikiAccountKey, args);
			finderCache.removeResult(
				_finderPathFetchByKoroneikiAccountKey, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				accountEntryModelImpl.getCorpProjectUuid()
			};

			finderCache.removeResult(_finderPathCountByCorpProjectUuid, args);
			finderCache.removeResult(_finderPathFetchByCorpProjectUuid, args);
		}

		if ((accountEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByCorpProjectUuid.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				accountEntryModelImpl.getOriginalCorpProjectUuid()
			};

			finderCache.removeResult(_finderPathCountByCorpProjectUuid, args);
			finderCache.removeResult(_finderPathFetchByCorpProjectUuid, args);
		}
	}

	/**
	 * Creates a new account entry with the primary key. Does not add the account entry to the database.
	 *
	 * @param accountEntryId the primary key for the new account entry
	 * @return the new account entry
	 */
	@Override
	public AccountEntry create(long accountEntryId) {
		AccountEntry accountEntry = new AccountEntryImpl();

		accountEntry.setNew(true);
		accountEntry.setPrimaryKey(accountEntryId);

		accountEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return accountEntry;
	}

	/**
	 * Removes the account entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountEntryId the primary key of the account entry
	 * @return the account entry that was removed
	 * @throws NoSuchAccountEntryException if a account entry with the primary key could not be found
	 */
	@Override
	public AccountEntry remove(long accountEntryId)
		throws NoSuchAccountEntryException {

		return remove((Serializable)accountEntryId);
	}

	/**
	 * Removes the account entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the account entry
	 * @return the account entry that was removed
	 * @throws NoSuchAccountEntryException if a account entry with the primary key could not be found
	 */
	@Override
	public AccountEntry remove(Serializable primaryKey)
		throws NoSuchAccountEntryException {

		Session session = null;

		try {
			session = openSession();

			AccountEntry accountEntry = (AccountEntry)session.get(
				AccountEntryImpl.class, primaryKey);

			if (accountEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAccountEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(accountEntry);
		}
		catch (NoSuchAccountEntryException noSuchEntityException) {
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
	protected AccountEntry removeImpl(AccountEntry accountEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(accountEntry)) {
				accountEntry = (AccountEntry)session.get(
					AccountEntryImpl.class, accountEntry.getPrimaryKeyObj());
			}

			if (accountEntry != null) {
				session.delete(accountEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (accountEntry != null) {
			clearCache(accountEntry);
		}

		return accountEntry;
	}

	@Override
	public AccountEntry updateImpl(AccountEntry accountEntry) {
		boolean isNew = accountEntry.isNew();

		if (!(accountEntry instanceof AccountEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(accountEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					accountEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in accountEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AccountEntry implementation " +
					accountEntry.getClass());
		}

		AccountEntryModelImpl accountEntryModelImpl =
			(AccountEntryModelImpl)accountEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (accountEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				accountEntry.setCreateDate(date);
			}
			else {
				accountEntry.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!accountEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				accountEntry.setModifiedDate(date);
			}
			else {
				accountEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(accountEntry);

				accountEntry.setNew(false);
			}
			else {
				accountEntry = (AccountEntry)session.merge(accountEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!AccountEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				accountEntryModelImpl.getDossieraAccountKey()
			};

			finderCache.removeResult(
				_finderPathCountByDossieraAccountKey, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByDossieraAccountKey, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((accountEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByDossieraAccountKey.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					accountEntryModelImpl.getOriginalDossieraAccountKey()
				};

				finderCache.removeResult(
					_finderPathCountByDossieraAccountKey, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByDossieraAccountKey, args);

				args = new Object[] {
					accountEntryModelImpl.getDossieraAccountKey()
				};

				finderCache.removeResult(
					_finderPathCountByDossieraAccountKey, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByDossieraAccountKey, args);
			}
		}

		entityCache.putResult(
			AccountEntryModelImpl.ENTITY_CACHE_ENABLED, AccountEntryImpl.class,
			accountEntry.getPrimaryKey(), accountEntry, false);

		clearUniqueFindersCache(accountEntryModelImpl, false);
		cacheUniqueFindersCache(accountEntryModelImpl);

		accountEntry.resetOriginalValues();

		return accountEntry;
	}

	/**
	 * Returns the account entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the account entry
	 * @return the account entry
	 * @throws NoSuchAccountEntryException if a account entry with the primary key could not be found
	 */
	@Override
	public AccountEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAccountEntryException {

		AccountEntry accountEntry = fetchByPrimaryKey(primaryKey);

		if (accountEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAccountEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return accountEntry;
	}

	/**
	 * Returns the account entry with the primary key or throws a <code>NoSuchAccountEntryException</code> if it could not be found.
	 *
	 * @param accountEntryId the primary key of the account entry
	 * @return the account entry
	 * @throws NoSuchAccountEntryException if a account entry with the primary key could not be found
	 */
	@Override
	public AccountEntry findByPrimaryKey(long accountEntryId)
		throws NoSuchAccountEntryException {

		return findByPrimaryKey((Serializable)accountEntryId);
	}

	/**
	 * Returns the account entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the account entry
	 * @return the account entry, or <code>null</code> if a account entry with the primary key could not be found
	 */
	@Override
	public AccountEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			AccountEntryModelImpl.ENTITY_CACHE_ENABLED, AccountEntryImpl.class,
			primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		AccountEntry accountEntry = (AccountEntry)serializable;

		if (accountEntry == null) {
			Session session = null;

			try {
				session = openSession();

				accountEntry = (AccountEntry)session.get(
					AccountEntryImpl.class, primaryKey);

				if (accountEntry != null) {
					cacheResult(accountEntry);
				}
				else {
					entityCache.putResult(
						AccountEntryModelImpl.ENTITY_CACHE_ENABLED,
						AccountEntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					AccountEntryModelImpl.ENTITY_CACHE_ENABLED,
					AccountEntryImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return accountEntry;
	}

	/**
	 * Returns the account entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param accountEntryId the primary key of the account entry
	 * @return the account entry, or <code>null</code> if a account entry with the primary key could not be found
	 */
	@Override
	public AccountEntry fetchByPrimaryKey(long accountEntryId) {
		return fetchByPrimaryKey((Serializable)accountEntryId);
	}

	@Override
	public Map<Serializable, AccountEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AccountEntry> map =
			new HashMap<Serializable, AccountEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AccountEntry accountEntry = fetchByPrimaryKey(primaryKey);

			if (accountEntry != null) {
				map.put(primaryKey, accountEntry);
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
				AccountEntryModelImpl.ENTITY_CACHE_ENABLED,
				AccountEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (AccountEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_ACCOUNTENTRY_WHERE_PKS_IN);

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

			for (AccountEntry accountEntry : (List<AccountEntry>)query.list()) {
				map.put(accountEntry.getPrimaryKeyObj(), accountEntry);

				cacheResult(accountEntry);

				uncachedPrimaryKeys.remove(accountEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					AccountEntryModelImpl.ENTITY_CACHE_ENABLED,
					AccountEntryImpl.class, primaryKey, nullModel);
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
	 * Returns all the account entries.
	 *
	 * @return the account entries
	 */
	@Override
	public List<AccountEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the account entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account entries
	 * @param end the upper bound of the range of account entries (not inclusive)
	 * @return the range of account entries
	 */
	@Override
	public List<AccountEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the account entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account entries
	 * @param end the upper bound of the range of account entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of account entries
	 */
	@Override
	public List<AccountEntry> findAll(
		int start, int end, OrderByComparator<AccountEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the account entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account entries
	 * @param end the upper bound of the range of account entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of account entries
	 */
	@Override
	public List<AccountEntry> findAll(
		int start, int end, OrderByComparator<AccountEntry> orderByComparator,
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

		List<AccountEntry> list = null;

		if (useFinderCache) {
			list = (List<AccountEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ACCOUNTENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ACCOUNTENTRY;

				sql = sql.concat(AccountEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AccountEntry>)QueryUtil.list(
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
	 * Removes all the account entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AccountEntry accountEntry : findAll()) {
			remove(accountEntry);
		}
	}

	/**
	 * Returns the number of account entries.
	 *
	 * @return the number of account entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_ACCOUNTENTRY);

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
		return AccountEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the account entry persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			AccountEntryModelImpl.ENTITY_CACHE_ENABLED,
			AccountEntryModelImpl.FINDER_CACHE_ENABLED, AccountEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			AccountEntryModelImpl.ENTITY_CACHE_ENABLED,
			AccountEntryModelImpl.FINDER_CACHE_ENABLED, AccountEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			AccountEntryModelImpl.ENTITY_CACHE_ENABLED,
			AccountEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByKoroneikiAccountKey = new FinderPath(
			AccountEntryModelImpl.ENTITY_CACHE_ENABLED,
			AccountEntryModelImpl.FINDER_CACHE_ENABLED, AccountEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByKoroneikiAccountKey",
			new String[] {String.class.getName()},
			AccountEntryModelImpl.KORONEIKIACCOUNTKEY_COLUMN_BITMASK);

		_finderPathCountByKoroneikiAccountKey = new FinderPath(
			AccountEntryModelImpl.ENTITY_CACHE_ENABLED,
			AccountEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByKoroneikiAccountKey",
			new String[] {String.class.getName()});

		_finderPathWithPaginationFindByDossieraAccountKey = new FinderPath(
			AccountEntryModelImpl.ENTITY_CACHE_ENABLED,
			AccountEntryModelImpl.FINDER_CACHE_ENABLED, AccountEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByDossieraAccountKey",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByDossieraAccountKey = new FinderPath(
			AccountEntryModelImpl.ENTITY_CACHE_ENABLED,
			AccountEntryModelImpl.FINDER_CACHE_ENABLED, AccountEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByDossieraAccountKey", new String[] {String.class.getName()},
			AccountEntryModelImpl.DOSSIERAACCOUNTKEY_COLUMN_BITMASK);

		_finderPathCountByDossieraAccountKey = new FinderPath(
			AccountEntryModelImpl.ENTITY_CACHE_ENABLED,
			AccountEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByDossieraAccountKey", new String[] {String.class.getName()});

		_finderPathFetchByCorpProjectUuid = new FinderPath(
			AccountEntryModelImpl.ENTITY_CACHE_ENABLED,
			AccountEntryModelImpl.FINDER_CACHE_ENABLED, AccountEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByCorpProjectUuid",
			new String[] {String.class.getName()},
			AccountEntryModelImpl.CORPPROJECTUUID_COLUMN_BITMASK);

		_finderPathCountByCorpProjectUuid = new FinderPath(
			AccountEntryModelImpl.ENTITY_CACHE_ENABLED,
			AccountEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCorpProjectUuid",
			new String[] {String.class.getName()});

		_finderPathWithPaginationFindByN_C = new FinderPath(
			AccountEntryModelImpl.ENTITY_CACHE_ENABLED,
			AccountEntryModelImpl.FINDER_CACHE_ENABLED, AccountEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByN_C",
			new String[] {
				String.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByN_C = new FinderPath(
			AccountEntryModelImpl.ENTITY_CACHE_ENABLED,
			AccountEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByN_C",
			new String[] {String.class.getName(), String.class.getName()});

		AccountEntryUtil.setPersistence(this);
	}

	public void destroy() {
		AccountEntryUtil.setPersistence(null);

		entityCache.removeCache(AccountEntryImpl.class.getName());

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

	private static final String _SQL_SELECT_ACCOUNTENTRY =
		"SELECT accountEntry FROM AccountEntry accountEntry";

	private static final String _SQL_SELECT_ACCOUNTENTRY_WHERE_PKS_IN =
		"SELECT accountEntry FROM AccountEntry accountEntry WHERE accountEntryId IN (";

	private static final String _SQL_SELECT_ACCOUNTENTRY_WHERE =
		"SELECT accountEntry FROM AccountEntry accountEntry WHERE ";

	private static final String _SQL_COUNT_ACCOUNTENTRY =
		"SELECT COUNT(accountEntry) FROM AccountEntry accountEntry";

	private static final String _SQL_COUNT_ACCOUNTENTRY_WHERE =
		"SELECT COUNT(accountEntry) FROM AccountEntry accountEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "accountEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AccountEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AccountEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AccountEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"code"});

}