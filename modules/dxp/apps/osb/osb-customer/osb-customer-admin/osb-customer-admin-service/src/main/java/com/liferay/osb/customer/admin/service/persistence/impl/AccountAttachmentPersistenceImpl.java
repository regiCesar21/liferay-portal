/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.admin.service.persistence.impl;

import com.liferay.osb.customer.admin.exception.NoSuchAccountAttachmentException;
import com.liferay.osb.customer.admin.model.AccountAttachment;
import com.liferay.osb.customer.admin.model.impl.AccountAttachmentImpl;
import com.liferay.osb.customer.admin.model.impl.AccountAttachmentModelImpl;
import com.liferay.osb.customer.admin.service.persistence.AccountAttachmentPersistence;
import com.liferay.osb.customer.admin.service.persistence.AccountAttachmentUtil;
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
 * The persistence implementation for the account attachment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AccountAttachmentPersistenceImpl
	extends BasePersistenceImpl<AccountAttachment>
	implements AccountAttachmentPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AccountAttachmentUtil</code> to access the account attachment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AccountAttachmentImpl.class.getName();

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
	 * Returns all the account attachments where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the matching account attachments
	 */
	@Override
	public List<AccountAttachment> findByAccountEntryId(long accountEntryId) {
		return findByAccountEntryId(
			accountEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the account attachments where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account attachments
	 * @param end the upper bound of the range of account attachments (not inclusive)
	 * @return the range of matching account attachments
	 */
	@Override
	public List<AccountAttachment> findByAccountEntryId(
		long accountEntryId, int start, int end) {

		return findByAccountEntryId(accountEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the account attachments where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account attachments
	 * @param end the upper bound of the range of account attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account attachments
	 */
	@Override
	public List<AccountAttachment> findByAccountEntryId(
		long accountEntryId, int start, int end,
		OrderByComparator<AccountAttachment> orderByComparator) {

		return findByAccountEntryId(
			accountEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the account attachments where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account attachments
	 * @param end the upper bound of the range of account attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account attachments
	 */
	@Override
	public List<AccountAttachment> findByAccountEntryId(
		long accountEntryId, int start, int end,
		OrderByComparator<AccountAttachment> orderByComparator,
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

		List<AccountAttachment> list = null;

		if (useFinderCache) {
			list = (List<AccountAttachment>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AccountAttachment accountAttachment : list) {
					if (accountEntryId !=
							accountAttachment.getAccountEntryId()) {

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

			sb.append(_SQL_SELECT_ACCOUNTATTACHMENT_WHERE);

			sb.append(_FINDER_COLUMN_ACCOUNTENTRYID_ACCOUNTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AccountAttachmentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEntryId);

				list = (List<AccountAttachment>)QueryUtil.list(
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
	 * Returns the first account attachment in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account attachment
	 * @throws NoSuchAccountAttachmentException if a matching account attachment could not be found
	 */
	@Override
	public AccountAttachment findByAccountEntryId_First(
			long accountEntryId,
			OrderByComparator<AccountAttachment> orderByComparator)
		throws NoSuchAccountAttachmentException {

		AccountAttachment accountAttachment = fetchByAccountEntryId_First(
			accountEntryId, orderByComparator);

		if (accountAttachment != null) {
			return accountAttachment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountEntryId=");
		sb.append(accountEntryId);

		sb.append("}");

		throw new NoSuchAccountAttachmentException(sb.toString());
	}

	/**
	 * Returns the first account attachment in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account attachment, or <code>null</code> if a matching account attachment could not be found
	 */
	@Override
	public AccountAttachment fetchByAccountEntryId_First(
		long accountEntryId,
		OrderByComparator<AccountAttachment> orderByComparator) {

		List<AccountAttachment> list = findByAccountEntryId(
			accountEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last account attachment in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account attachment
	 * @throws NoSuchAccountAttachmentException if a matching account attachment could not be found
	 */
	@Override
	public AccountAttachment findByAccountEntryId_Last(
			long accountEntryId,
			OrderByComparator<AccountAttachment> orderByComparator)
		throws NoSuchAccountAttachmentException {

		AccountAttachment accountAttachment = fetchByAccountEntryId_Last(
			accountEntryId, orderByComparator);

		if (accountAttachment != null) {
			return accountAttachment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountEntryId=");
		sb.append(accountEntryId);

		sb.append("}");

		throw new NoSuchAccountAttachmentException(sb.toString());
	}

	/**
	 * Returns the last account attachment in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account attachment, or <code>null</code> if a matching account attachment could not be found
	 */
	@Override
	public AccountAttachment fetchByAccountEntryId_Last(
		long accountEntryId,
		OrderByComparator<AccountAttachment> orderByComparator) {

		int count = countByAccountEntryId(accountEntryId);

		if (count == 0) {
			return null;
		}

		List<AccountAttachment> list = findByAccountEntryId(
			accountEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the account attachments before and after the current account attachment in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountAttachmentId the primary key of the current account attachment
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account attachment
	 * @throws NoSuchAccountAttachmentException if a account attachment with the primary key could not be found
	 */
	@Override
	public AccountAttachment[] findByAccountEntryId_PrevAndNext(
			long accountAttachmentId, long accountEntryId,
			OrderByComparator<AccountAttachment> orderByComparator)
		throws NoSuchAccountAttachmentException {

		AccountAttachment accountAttachment = findByPrimaryKey(
			accountAttachmentId);

		Session session = null;

		try {
			session = openSession();

			AccountAttachment[] array = new AccountAttachmentImpl[3];

			array[0] = getByAccountEntryId_PrevAndNext(
				session, accountAttachment, accountEntryId, orderByComparator,
				true);

			array[1] = accountAttachment;

			array[2] = getByAccountEntryId_PrevAndNext(
				session, accountAttachment, accountEntryId, orderByComparator,
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

	protected AccountAttachment getByAccountEntryId_PrevAndNext(
		Session session, AccountAttachment accountAttachment,
		long accountEntryId,
		OrderByComparator<AccountAttachment> orderByComparator,
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

		sb.append(_SQL_SELECT_ACCOUNTATTACHMENT_WHERE);

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
			sb.append(AccountAttachmentModelImpl.ORDER_BY_JPQL);
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
						accountAttachment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AccountAttachment> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the account attachments where accountEntryId = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 */
	@Override
	public void removeByAccountEntryId(long accountEntryId) {
		for (AccountAttachment accountAttachment :
				findByAccountEntryId(
					accountEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(accountAttachment);
		}
	}

	/**
	 * Returns the number of account attachments where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the number of matching account attachments
	 */
	@Override
	public int countByAccountEntryId(long accountEntryId) {
		FinderPath finderPath = _finderPathCountByAccountEntryId;

		Object[] finderArgs = new Object[] {accountEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ACCOUNTATTACHMENT_WHERE);

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
		"accountAttachment.accountEntryId = ?";

	private FinderPath _finderPathWithPaginationFindByAEI_API;
	private FinderPath _finderPathWithoutPaginationFindByAEI_API;
	private FinderPath _finderPathCountByAEI_API;

	/**
	 * Returns all the account attachments where accountEntryId = &#63; and accountProjectId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountProjectId the account project ID
	 * @return the matching account attachments
	 */
	@Override
	public List<AccountAttachment> findByAEI_API(
		long accountEntryId, long accountProjectId) {

		return findByAEI_API(
			accountEntryId, accountProjectId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the account attachments where accountEntryId = &#63; and accountProjectId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountProjectId the account project ID
	 * @param start the lower bound of the range of account attachments
	 * @param end the upper bound of the range of account attachments (not inclusive)
	 * @return the range of matching account attachments
	 */
	@Override
	public List<AccountAttachment> findByAEI_API(
		long accountEntryId, long accountProjectId, int start, int end) {

		return findByAEI_API(
			accountEntryId, accountProjectId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the account attachments where accountEntryId = &#63; and accountProjectId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountProjectId the account project ID
	 * @param start the lower bound of the range of account attachments
	 * @param end the upper bound of the range of account attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account attachments
	 */
	@Override
	public List<AccountAttachment> findByAEI_API(
		long accountEntryId, long accountProjectId, int start, int end,
		OrderByComparator<AccountAttachment> orderByComparator) {

		return findByAEI_API(
			accountEntryId, accountProjectId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the account attachments where accountEntryId = &#63; and accountProjectId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountProjectId the account project ID
	 * @param start the lower bound of the range of account attachments
	 * @param end the upper bound of the range of account attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account attachments
	 */
	@Override
	public List<AccountAttachment> findByAEI_API(
		long accountEntryId, long accountProjectId, int start, int end,
		OrderByComparator<AccountAttachment> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByAEI_API;
				finderArgs = new Object[] {accountEntryId, accountProjectId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByAEI_API;
			finderArgs = new Object[] {
				accountEntryId, accountProjectId, start, end, orderByComparator
			};
		}

		List<AccountAttachment> list = null;

		if (useFinderCache) {
			list = (List<AccountAttachment>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AccountAttachment accountAttachment : list) {
					if ((accountEntryId !=
							accountAttachment.getAccountEntryId()) ||
						(accountProjectId !=
							accountAttachment.getAccountProjectId())) {

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

			sb.append(_SQL_SELECT_ACCOUNTATTACHMENT_WHERE);

			sb.append(_FINDER_COLUMN_AEI_API_ACCOUNTENTRYID_2);

			sb.append(_FINDER_COLUMN_AEI_API_ACCOUNTPROJECTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AccountAttachmentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEntryId);

				queryPos.add(accountProjectId);

				list = (List<AccountAttachment>)QueryUtil.list(
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
	 * Returns the first account attachment in the ordered set where accountEntryId = &#63; and accountProjectId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountProjectId the account project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account attachment
	 * @throws NoSuchAccountAttachmentException if a matching account attachment could not be found
	 */
	@Override
	public AccountAttachment findByAEI_API_First(
			long accountEntryId, long accountProjectId,
			OrderByComparator<AccountAttachment> orderByComparator)
		throws NoSuchAccountAttachmentException {

		AccountAttachment accountAttachment = fetchByAEI_API_First(
			accountEntryId, accountProjectId, orderByComparator);

		if (accountAttachment != null) {
			return accountAttachment;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountEntryId=");
		sb.append(accountEntryId);

		sb.append(", accountProjectId=");
		sb.append(accountProjectId);

		sb.append("}");

		throw new NoSuchAccountAttachmentException(sb.toString());
	}

	/**
	 * Returns the first account attachment in the ordered set where accountEntryId = &#63; and accountProjectId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountProjectId the account project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account attachment, or <code>null</code> if a matching account attachment could not be found
	 */
	@Override
	public AccountAttachment fetchByAEI_API_First(
		long accountEntryId, long accountProjectId,
		OrderByComparator<AccountAttachment> orderByComparator) {

		List<AccountAttachment> list = findByAEI_API(
			accountEntryId, accountProjectId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last account attachment in the ordered set where accountEntryId = &#63; and accountProjectId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountProjectId the account project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account attachment
	 * @throws NoSuchAccountAttachmentException if a matching account attachment could not be found
	 */
	@Override
	public AccountAttachment findByAEI_API_Last(
			long accountEntryId, long accountProjectId,
			OrderByComparator<AccountAttachment> orderByComparator)
		throws NoSuchAccountAttachmentException {

		AccountAttachment accountAttachment = fetchByAEI_API_Last(
			accountEntryId, accountProjectId, orderByComparator);

		if (accountAttachment != null) {
			return accountAttachment;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountEntryId=");
		sb.append(accountEntryId);

		sb.append(", accountProjectId=");
		sb.append(accountProjectId);

		sb.append("}");

		throw new NoSuchAccountAttachmentException(sb.toString());
	}

	/**
	 * Returns the last account attachment in the ordered set where accountEntryId = &#63; and accountProjectId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountProjectId the account project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account attachment, or <code>null</code> if a matching account attachment could not be found
	 */
	@Override
	public AccountAttachment fetchByAEI_API_Last(
		long accountEntryId, long accountProjectId,
		OrderByComparator<AccountAttachment> orderByComparator) {

		int count = countByAEI_API(accountEntryId, accountProjectId);

		if (count == 0) {
			return null;
		}

		List<AccountAttachment> list = findByAEI_API(
			accountEntryId, accountProjectId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the account attachments before and after the current account attachment in the ordered set where accountEntryId = &#63; and accountProjectId = &#63;.
	 *
	 * @param accountAttachmentId the primary key of the current account attachment
	 * @param accountEntryId the account entry ID
	 * @param accountProjectId the account project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account attachment
	 * @throws NoSuchAccountAttachmentException if a account attachment with the primary key could not be found
	 */
	@Override
	public AccountAttachment[] findByAEI_API_PrevAndNext(
			long accountAttachmentId, long accountEntryId,
			long accountProjectId,
			OrderByComparator<AccountAttachment> orderByComparator)
		throws NoSuchAccountAttachmentException {

		AccountAttachment accountAttachment = findByPrimaryKey(
			accountAttachmentId);

		Session session = null;

		try {
			session = openSession();

			AccountAttachment[] array = new AccountAttachmentImpl[3];

			array[0] = getByAEI_API_PrevAndNext(
				session, accountAttachment, accountEntryId, accountProjectId,
				orderByComparator, true);

			array[1] = accountAttachment;

			array[2] = getByAEI_API_PrevAndNext(
				session, accountAttachment, accountEntryId, accountProjectId,
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

	protected AccountAttachment getByAEI_API_PrevAndNext(
		Session session, AccountAttachment accountAttachment,
		long accountEntryId, long accountProjectId,
		OrderByComparator<AccountAttachment> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_ACCOUNTATTACHMENT_WHERE);

		sb.append(_FINDER_COLUMN_AEI_API_ACCOUNTENTRYID_2);

		sb.append(_FINDER_COLUMN_AEI_API_ACCOUNTPROJECTID_2);

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
			sb.append(AccountAttachmentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(accountEntryId);

		queryPos.add(accountProjectId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						accountAttachment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AccountAttachment> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the account attachments where accountEntryId = &#63; and accountProjectId = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountProjectId the account project ID
	 */
	@Override
	public void removeByAEI_API(long accountEntryId, long accountProjectId) {
		for (AccountAttachment accountAttachment :
				findByAEI_API(
					accountEntryId, accountProjectId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(accountAttachment);
		}
	}

	/**
	 * Returns the number of account attachments where accountEntryId = &#63; and accountProjectId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountProjectId the account project ID
	 * @return the number of matching account attachments
	 */
	@Override
	public int countByAEI_API(long accountEntryId, long accountProjectId) {
		FinderPath finderPath = _finderPathCountByAEI_API;

		Object[] finderArgs = new Object[] {accountEntryId, accountProjectId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ACCOUNTATTACHMENT_WHERE);

			sb.append(_FINDER_COLUMN_AEI_API_ACCOUNTENTRYID_2);

			sb.append(_FINDER_COLUMN_AEI_API_ACCOUNTPROJECTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEntryId);

				queryPos.add(accountProjectId);

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

	private static final String _FINDER_COLUMN_AEI_API_ACCOUNTENTRYID_2 =
		"accountAttachment.accountEntryId = ? AND ";

	private static final String _FINDER_COLUMN_AEI_API_ACCOUNTPROJECTID_2 =
		"accountAttachment.accountProjectId = ?";

	private FinderPath _finderPathWithPaginationFindByAEI_API_T;
	private FinderPath _finderPathWithoutPaginationFindByAEI_API_T;
	private FinderPath _finderPathCountByAEI_API_T;

	/**
	 * Returns all the account attachments where accountEntryId = &#63; and accountProjectId = &#63; and type = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountProjectId the account project ID
	 * @param type the type
	 * @return the matching account attachments
	 */
	@Override
	public List<AccountAttachment> findByAEI_API_T(
		long accountEntryId, long accountProjectId, int type) {

		return findByAEI_API_T(
			accountEntryId, accountProjectId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the account attachments where accountEntryId = &#63; and accountProjectId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountProjectId the account project ID
	 * @param type the type
	 * @param start the lower bound of the range of account attachments
	 * @param end the upper bound of the range of account attachments (not inclusive)
	 * @return the range of matching account attachments
	 */
	@Override
	public List<AccountAttachment> findByAEI_API_T(
		long accountEntryId, long accountProjectId, int type, int start,
		int end) {

		return findByAEI_API_T(
			accountEntryId, accountProjectId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the account attachments where accountEntryId = &#63; and accountProjectId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountProjectId the account project ID
	 * @param type the type
	 * @param start the lower bound of the range of account attachments
	 * @param end the upper bound of the range of account attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account attachments
	 */
	@Override
	public List<AccountAttachment> findByAEI_API_T(
		long accountEntryId, long accountProjectId, int type, int start,
		int end, OrderByComparator<AccountAttachment> orderByComparator) {

		return findByAEI_API_T(
			accountEntryId, accountProjectId, type, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the account attachments where accountEntryId = &#63; and accountProjectId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountProjectId the account project ID
	 * @param type the type
	 * @param start the lower bound of the range of account attachments
	 * @param end the upper bound of the range of account attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account attachments
	 */
	@Override
	public List<AccountAttachment> findByAEI_API_T(
		long accountEntryId, long accountProjectId, int type, int start,
		int end, OrderByComparator<AccountAttachment> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByAEI_API_T;
				finderArgs = new Object[] {
					accountEntryId, accountProjectId, type
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByAEI_API_T;
			finderArgs = new Object[] {
				accountEntryId, accountProjectId, type, start, end,
				orderByComparator
			};
		}

		List<AccountAttachment> list = null;

		if (useFinderCache) {
			list = (List<AccountAttachment>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AccountAttachment accountAttachment : list) {
					if ((accountEntryId !=
							accountAttachment.getAccountEntryId()) ||
						(accountProjectId !=
							accountAttachment.getAccountProjectId()) ||
						(type != accountAttachment.getType())) {

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

			sb.append(_SQL_SELECT_ACCOUNTATTACHMENT_WHERE);

			sb.append(_FINDER_COLUMN_AEI_API_T_ACCOUNTENTRYID_2);

			sb.append(_FINDER_COLUMN_AEI_API_T_ACCOUNTPROJECTID_2);

			sb.append(_FINDER_COLUMN_AEI_API_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AccountAttachmentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEntryId);

				queryPos.add(accountProjectId);

				queryPos.add(type);

				list = (List<AccountAttachment>)QueryUtil.list(
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
	 * Returns the first account attachment in the ordered set where accountEntryId = &#63; and accountProjectId = &#63; and type = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountProjectId the account project ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account attachment
	 * @throws NoSuchAccountAttachmentException if a matching account attachment could not be found
	 */
	@Override
	public AccountAttachment findByAEI_API_T_First(
			long accountEntryId, long accountProjectId, int type,
			OrderByComparator<AccountAttachment> orderByComparator)
		throws NoSuchAccountAttachmentException {

		AccountAttachment accountAttachment = fetchByAEI_API_T_First(
			accountEntryId, accountProjectId, type, orderByComparator);

		if (accountAttachment != null) {
			return accountAttachment;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountEntryId=");
		sb.append(accountEntryId);

		sb.append(", accountProjectId=");
		sb.append(accountProjectId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchAccountAttachmentException(sb.toString());
	}

	/**
	 * Returns the first account attachment in the ordered set where accountEntryId = &#63; and accountProjectId = &#63; and type = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountProjectId the account project ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account attachment, or <code>null</code> if a matching account attachment could not be found
	 */
	@Override
	public AccountAttachment fetchByAEI_API_T_First(
		long accountEntryId, long accountProjectId, int type,
		OrderByComparator<AccountAttachment> orderByComparator) {

		List<AccountAttachment> list = findByAEI_API_T(
			accountEntryId, accountProjectId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last account attachment in the ordered set where accountEntryId = &#63; and accountProjectId = &#63; and type = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountProjectId the account project ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account attachment
	 * @throws NoSuchAccountAttachmentException if a matching account attachment could not be found
	 */
	@Override
	public AccountAttachment findByAEI_API_T_Last(
			long accountEntryId, long accountProjectId, int type,
			OrderByComparator<AccountAttachment> orderByComparator)
		throws NoSuchAccountAttachmentException {

		AccountAttachment accountAttachment = fetchByAEI_API_T_Last(
			accountEntryId, accountProjectId, type, orderByComparator);

		if (accountAttachment != null) {
			return accountAttachment;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountEntryId=");
		sb.append(accountEntryId);

		sb.append(", accountProjectId=");
		sb.append(accountProjectId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchAccountAttachmentException(sb.toString());
	}

	/**
	 * Returns the last account attachment in the ordered set where accountEntryId = &#63; and accountProjectId = &#63; and type = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountProjectId the account project ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account attachment, or <code>null</code> if a matching account attachment could not be found
	 */
	@Override
	public AccountAttachment fetchByAEI_API_T_Last(
		long accountEntryId, long accountProjectId, int type,
		OrderByComparator<AccountAttachment> orderByComparator) {

		int count = countByAEI_API_T(accountEntryId, accountProjectId, type);

		if (count == 0) {
			return null;
		}

		List<AccountAttachment> list = findByAEI_API_T(
			accountEntryId, accountProjectId, type, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the account attachments before and after the current account attachment in the ordered set where accountEntryId = &#63; and accountProjectId = &#63; and type = &#63;.
	 *
	 * @param accountAttachmentId the primary key of the current account attachment
	 * @param accountEntryId the account entry ID
	 * @param accountProjectId the account project ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account attachment
	 * @throws NoSuchAccountAttachmentException if a account attachment with the primary key could not be found
	 */
	@Override
	public AccountAttachment[] findByAEI_API_T_PrevAndNext(
			long accountAttachmentId, long accountEntryId,
			long accountProjectId, int type,
			OrderByComparator<AccountAttachment> orderByComparator)
		throws NoSuchAccountAttachmentException {

		AccountAttachment accountAttachment = findByPrimaryKey(
			accountAttachmentId);

		Session session = null;

		try {
			session = openSession();

			AccountAttachment[] array = new AccountAttachmentImpl[3];

			array[0] = getByAEI_API_T_PrevAndNext(
				session, accountAttachment, accountEntryId, accountProjectId,
				type, orderByComparator, true);

			array[1] = accountAttachment;

			array[2] = getByAEI_API_T_PrevAndNext(
				session, accountAttachment, accountEntryId, accountProjectId,
				type, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AccountAttachment getByAEI_API_T_PrevAndNext(
		Session session, AccountAttachment accountAttachment,
		long accountEntryId, long accountProjectId, int type,
		OrderByComparator<AccountAttachment> orderByComparator,
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

		sb.append(_SQL_SELECT_ACCOUNTATTACHMENT_WHERE);

		sb.append(_FINDER_COLUMN_AEI_API_T_ACCOUNTENTRYID_2);

		sb.append(_FINDER_COLUMN_AEI_API_T_ACCOUNTPROJECTID_2);

		sb.append(_FINDER_COLUMN_AEI_API_T_TYPE_2);

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
			sb.append(AccountAttachmentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(accountEntryId);

		queryPos.add(accountProjectId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						accountAttachment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AccountAttachment> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the account attachments where accountEntryId = &#63; and accountProjectId = &#63; and type = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountProjectId the account project ID
	 * @param type the type
	 */
	@Override
	public void removeByAEI_API_T(
		long accountEntryId, long accountProjectId, int type) {

		for (AccountAttachment accountAttachment :
				findByAEI_API_T(
					accountEntryId, accountProjectId, type, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(accountAttachment);
		}
	}

	/**
	 * Returns the number of account attachments where accountEntryId = &#63; and accountProjectId = &#63; and type = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountProjectId the account project ID
	 * @param type the type
	 * @return the number of matching account attachments
	 */
	@Override
	public int countByAEI_API_T(
		long accountEntryId, long accountProjectId, int type) {

		FinderPath finderPath = _finderPathCountByAEI_API_T;

		Object[] finderArgs = new Object[] {
			accountEntryId, accountProjectId, type
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_ACCOUNTATTACHMENT_WHERE);

			sb.append(_FINDER_COLUMN_AEI_API_T_ACCOUNTENTRYID_2);

			sb.append(_FINDER_COLUMN_AEI_API_T_ACCOUNTPROJECTID_2);

			sb.append(_FINDER_COLUMN_AEI_API_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEntryId);

				queryPos.add(accountProjectId);

				queryPos.add(type);

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

	private static final String _FINDER_COLUMN_AEI_API_T_ACCOUNTENTRYID_2 =
		"accountAttachment.accountEntryId = ? AND ";

	private static final String _FINDER_COLUMN_AEI_API_T_ACCOUNTPROJECTID_2 =
		"accountAttachment.accountProjectId = ? AND ";

	private static final String _FINDER_COLUMN_AEI_API_T_TYPE_2 =
		"accountAttachment.type = ?";

	private FinderPath _finderPathFetchByAEI_API_FN_T;
	private FinderPath _finderPathCountByAEI_API_FN_T;

	/**
	 * Returns the account attachment where accountEntryId = &#63; and accountProjectId = &#63; and fileName = &#63; and type = &#63; or throws a <code>NoSuchAccountAttachmentException</code> if it could not be found.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountProjectId the account project ID
	 * @param fileName the file name
	 * @param type the type
	 * @return the matching account attachment
	 * @throws NoSuchAccountAttachmentException if a matching account attachment could not be found
	 */
	@Override
	public AccountAttachment findByAEI_API_FN_T(
			long accountEntryId, long accountProjectId, String fileName,
			int type)
		throws NoSuchAccountAttachmentException {

		AccountAttachment accountAttachment = fetchByAEI_API_FN_T(
			accountEntryId, accountProjectId, fileName, type);

		if (accountAttachment == null) {
			StringBundler sb = new StringBundler(10);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("accountEntryId=");
			sb.append(accountEntryId);

			sb.append(", accountProjectId=");
			sb.append(accountProjectId);

			sb.append(", fileName=");
			sb.append(fileName);

			sb.append(", type=");
			sb.append(type);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchAccountAttachmentException(sb.toString());
		}

		return accountAttachment;
	}

	/**
	 * Returns the account attachment where accountEntryId = &#63; and accountProjectId = &#63; and fileName = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountProjectId the account project ID
	 * @param fileName the file name
	 * @param type the type
	 * @return the matching account attachment, or <code>null</code> if a matching account attachment could not be found
	 */
	@Override
	public AccountAttachment fetchByAEI_API_FN_T(
		long accountEntryId, long accountProjectId, String fileName, int type) {

		return fetchByAEI_API_FN_T(
			accountEntryId, accountProjectId, fileName, type, true);
	}

	/**
	 * Returns the account attachment where accountEntryId = &#63; and accountProjectId = &#63; and fileName = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountProjectId the account project ID
	 * @param fileName the file name
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching account attachment, or <code>null</code> if a matching account attachment could not be found
	 */
	@Override
	public AccountAttachment fetchByAEI_API_FN_T(
		long accountEntryId, long accountProjectId, String fileName, int type,
		boolean useFinderCache) {

		fileName = Objects.toString(fileName, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				accountEntryId, accountProjectId, fileName, type
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByAEI_API_FN_T, finderArgs, this);
		}

		if (result instanceof AccountAttachment) {
			AccountAttachment accountAttachment = (AccountAttachment)result;

			if ((accountEntryId != accountAttachment.getAccountEntryId()) ||
				(accountProjectId != accountAttachment.getAccountProjectId()) ||
				!Objects.equals(fileName, accountAttachment.getFileName()) ||
				(type != accountAttachment.getType())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_SELECT_ACCOUNTATTACHMENT_WHERE);

			sb.append(_FINDER_COLUMN_AEI_API_FN_T_ACCOUNTENTRYID_2);

			sb.append(_FINDER_COLUMN_AEI_API_FN_T_ACCOUNTPROJECTID_2);

			boolean bindFileName = false;

			if (fileName.isEmpty()) {
				sb.append(_FINDER_COLUMN_AEI_API_FN_T_FILENAME_3);
			}
			else {
				bindFileName = true;

				sb.append(_FINDER_COLUMN_AEI_API_FN_T_FILENAME_2);
			}

			sb.append(_FINDER_COLUMN_AEI_API_FN_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEntryId);

				queryPos.add(accountProjectId);

				if (bindFileName) {
					queryPos.add(fileName);
				}

				queryPos.add(type);

				List<AccountAttachment> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByAEI_API_FN_T, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									accountEntryId, accountProjectId, fileName,
									type
								};
							}

							_log.warn(
								"AccountAttachmentPersistenceImpl.fetchByAEI_API_FN_T(long, long, String, int, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					AccountAttachment accountAttachment = list.get(0);

					result = accountAttachment;

					cacheResult(accountAttachment);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByAEI_API_FN_T, finderArgs);
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
			return (AccountAttachment)result;
		}
	}

	/**
	 * Removes the account attachment where accountEntryId = &#63; and accountProjectId = &#63; and fileName = &#63; and type = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountProjectId the account project ID
	 * @param fileName the file name
	 * @param type the type
	 * @return the account attachment that was removed
	 */
	@Override
	public AccountAttachment removeByAEI_API_FN_T(
			long accountEntryId, long accountProjectId, String fileName,
			int type)
		throws NoSuchAccountAttachmentException {

		AccountAttachment accountAttachment = findByAEI_API_FN_T(
			accountEntryId, accountProjectId, fileName, type);

		return remove(accountAttachment);
	}

	/**
	 * Returns the number of account attachments where accountEntryId = &#63; and accountProjectId = &#63; and fileName = &#63; and type = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountProjectId the account project ID
	 * @param fileName the file name
	 * @param type the type
	 * @return the number of matching account attachments
	 */
	@Override
	public int countByAEI_API_FN_T(
		long accountEntryId, long accountProjectId, String fileName, int type) {

		fileName = Objects.toString(fileName, "");

		FinderPath finderPath = _finderPathCountByAEI_API_FN_T;

		Object[] finderArgs = new Object[] {
			accountEntryId, accountProjectId, fileName, type
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_ACCOUNTATTACHMENT_WHERE);

			sb.append(_FINDER_COLUMN_AEI_API_FN_T_ACCOUNTENTRYID_2);

			sb.append(_FINDER_COLUMN_AEI_API_FN_T_ACCOUNTPROJECTID_2);

			boolean bindFileName = false;

			if (fileName.isEmpty()) {
				sb.append(_FINDER_COLUMN_AEI_API_FN_T_FILENAME_3);
			}
			else {
				bindFileName = true;

				sb.append(_FINDER_COLUMN_AEI_API_FN_T_FILENAME_2);
			}

			sb.append(_FINDER_COLUMN_AEI_API_FN_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEntryId);

				queryPos.add(accountProjectId);

				if (bindFileName) {
					queryPos.add(fileName);
				}

				queryPos.add(type);

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

	private static final String _FINDER_COLUMN_AEI_API_FN_T_ACCOUNTENTRYID_2 =
		"accountAttachment.accountEntryId = ? AND ";

	private static final String _FINDER_COLUMN_AEI_API_FN_T_ACCOUNTPROJECTID_2 =
		"accountAttachment.accountProjectId = ? AND ";

	private static final String _FINDER_COLUMN_AEI_API_FN_T_FILENAME_2 =
		"accountAttachment.fileName = ? AND ";

	private static final String _FINDER_COLUMN_AEI_API_FN_T_FILENAME_3 =
		"(accountAttachment.fileName IS NULL OR accountAttachment.fileName = '') AND ";

	private static final String _FINDER_COLUMN_AEI_API_FN_T_TYPE_2 =
		"accountAttachment.type = ?";

	public AccountAttachmentPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");

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

		setModelClass(AccountAttachment.class);
	}

	/**
	 * Caches the account attachment in the entity cache if it is enabled.
	 *
	 * @param accountAttachment the account attachment
	 */
	@Override
	public void cacheResult(AccountAttachment accountAttachment) {
		entityCache.putResult(
			AccountAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountAttachmentImpl.class, accountAttachment.getPrimaryKey(),
			accountAttachment);

		finderCache.putResult(
			_finderPathFetchByAEI_API_FN_T,
			new Object[] {
				accountAttachment.getAccountEntryId(),
				accountAttachment.getAccountProjectId(),
				accountAttachment.getFileName(), accountAttachment.getType()
			},
			accountAttachment);

		accountAttachment.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the account attachments in the entity cache if it is enabled.
	 *
	 * @param accountAttachments the account attachments
	 */
	@Override
	public void cacheResult(List<AccountAttachment> accountAttachments) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (accountAttachments.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (AccountAttachment accountAttachment : accountAttachments) {
			if (entityCache.getResult(
					AccountAttachmentModelImpl.ENTITY_CACHE_ENABLED,
					AccountAttachmentImpl.class,
					accountAttachment.getPrimaryKey()) == null) {

				cacheResult(accountAttachment);
			}
			else {
				accountAttachment.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all account attachments.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AccountAttachmentImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the account attachment.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AccountAttachment accountAttachment) {
		entityCache.removeResult(
			AccountAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountAttachmentImpl.class, accountAttachment.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(AccountAttachmentModelImpl)accountAttachment, true);
	}

	@Override
	public void clearCache(List<AccountAttachment> accountAttachments) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AccountAttachment accountAttachment : accountAttachments) {
			entityCache.removeResult(
				AccountAttachmentModelImpl.ENTITY_CACHE_ENABLED,
				AccountAttachmentImpl.class, accountAttachment.getPrimaryKey());

			clearUniqueFindersCache(
				(AccountAttachmentModelImpl)accountAttachment, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				AccountAttachmentModelImpl.ENTITY_CACHE_ENABLED,
				AccountAttachmentImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AccountAttachmentModelImpl accountAttachmentModelImpl) {

		Object[] args = new Object[] {
			accountAttachmentModelImpl.getAccountEntryId(),
			accountAttachmentModelImpl.getAccountProjectId(),
			accountAttachmentModelImpl.getFileName(),
			accountAttachmentModelImpl.getType()
		};

		finderCache.putResult(
			_finderPathCountByAEI_API_FN_T, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByAEI_API_FN_T, args, accountAttachmentModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		AccountAttachmentModelImpl accountAttachmentModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				accountAttachmentModelImpl.getAccountEntryId(),
				accountAttachmentModelImpl.getAccountProjectId(),
				accountAttachmentModelImpl.getFileName(),
				accountAttachmentModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByAEI_API_FN_T, args);
			finderCache.removeResult(_finderPathFetchByAEI_API_FN_T, args);
		}

		if ((accountAttachmentModelImpl.getColumnBitmask() &
			 _finderPathFetchByAEI_API_FN_T.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				accountAttachmentModelImpl.getOriginalAccountEntryId(),
				accountAttachmentModelImpl.getOriginalAccountProjectId(),
				accountAttachmentModelImpl.getOriginalFileName(),
				accountAttachmentModelImpl.getOriginalType()
			};

			finderCache.removeResult(_finderPathCountByAEI_API_FN_T, args);
			finderCache.removeResult(_finderPathFetchByAEI_API_FN_T, args);
		}
	}

	/**
	 * Creates a new account attachment with the primary key. Does not add the account attachment to the database.
	 *
	 * @param accountAttachmentId the primary key for the new account attachment
	 * @return the new account attachment
	 */
	@Override
	public AccountAttachment create(long accountAttachmentId) {
		AccountAttachment accountAttachment = new AccountAttachmentImpl();

		accountAttachment.setNew(true);
		accountAttachment.setPrimaryKey(accountAttachmentId);

		return accountAttachment;
	}

	/**
	 * Removes the account attachment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountAttachmentId the primary key of the account attachment
	 * @return the account attachment that was removed
	 * @throws NoSuchAccountAttachmentException if a account attachment with the primary key could not be found
	 */
	@Override
	public AccountAttachment remove(long accountAttachmentId)
		throws NoSuchAccountAttachmentException {

		return remove((Serializable)accountAttachmentId);
	}

	/**
	 * Removes the account attachment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the account attachment
	 * @return the account attachment that was removed
	 * @throws NoSuchAccountAttachmentException if a account attachment with the primary key could not be found
	 */
	@Override
	public AccountAttachment remove(Serializable primaryKey)
		throws NoSuchAccountAttachmentException {

		Session session = null;

		try {
			session = openSession();

			AccountAttachment accountAttachment =
				(AccountAttachment)session.get(
					AccountAttachmentImpl.class, primaryKey);

			if (accountAttachment == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAccountAttachmentException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(accountAttachment);
		}
		catch (NoSuchAccountAttachmentException noSuchEntityException) {
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
	protected AccountAttachment removeImpl(
		AccountAttachment accountAttachment) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(accountAttachment)) {
				accountAttachment = (AccountAttachment)session.get(
					AccountAttachmentImpl.class,
					accountAttachment.getPrimaryKeyObj());
			}

			if (accountAttachment != null) {
				session.delete(accountAttachment);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (accountAttachment != null) {
			clearCache(accountAttachment);
		}

		return accountAttachment;
	}

	@Override
	public AccountAttachment updateImpl(AccountAttachment accountAttachment) {
		boolean isNew = accountAttachment.isNew();

		if (!(accountAttachment instanceof AccountAttachmentModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(accountAttachment.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					accountAttachment);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in accountAttachment proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AccountAttachment implementation " +
					accountAttachment.getClass());
		}

		AccountAttachmentModelImpl accountAttachmentModelImpl =
			(AccountAttachmentModelImpl)accountAttachment;

		if (isNew && (accountAttachment.getCreateDate() == null)) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Date date = new Date();

			if (serviceContext == null) {
				accountAttachment.setCreateDate(date);
			}
			else {
				accountAttachment.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(accountAttachment);

				accountAttachment.setNew(false);
			}
			else {
				accountAttachment = (AccountAttachment)session.merge(
					accountAttachment);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!AccountAttachmentModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				accountAttachmentModelImpl.getAccountEntryId()
			};

			finderCache.removeResult(_finderPathCountByAccountEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByAccountEntryId, args);

			args = new Object[] {
				accountAttachmentModelImpl.getAccountEntryId(),
				accountAttachmentModelImpl.getAccountProjectId()
			};

			finderCache.removeResult(_finderPathCountByAEI_API, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByAEI_API, args);

			args = new Object[] {
				accountAttachmentModelImpl.getAccountEntryId(),
				accountAttachmentModelImpl.getAccountProjectId(),
				accountAttachmentModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByAEI_API_T, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByAEI_API_T, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((accountAttachmentModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByAccountEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					accountAttachmentModelImpl.getOriginalAccountEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByAccountEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAccountEntryId, args);

				args = new Object[] {
					accountAttachmentModelImpl.getAccountEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByAccountEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAccountEntryId, args);
			}

			if ((accountAttachmentModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByAEI_API.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					accountAttachmentModelImpl.getOriginalAccountEntryId(),
					accountAttachmentModelImpl.getOriginalAccountProjectId()
				};

				finderCache.removeResult(_finderPathCountByAEI_API, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAEI_API, args);

				args = new Object[] {
					accountAttachmentModelImpl.getAccountEntryId(),
					accountAttachmentModelImpl.getAccountProjectId()
				};

				finderCache.removeResult(_finderPathCountByAEI_API, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAEI_API, args);
			}

			if ((accountAttachmentModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByAEI_API_T.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					accountAttachmentModelImpl.getOriginalAccountEntryId(),
					accountAttachmentModelImpl.getOriginalAccountProjectId(),
					accountAttachmentModelImpl.getOriginalType()
				};

				finderCache.removeResult(_finderPathCountByAEI_API_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAEI_API_T, args);

				args = new Object[] {
					accountAttachmentModelImpl.getAccountEntryId(),
					accountAttachmentModelImpl.getAccountProjectId(),
					accountAttachmentModelImpl.getType()
				};

				finderCache.removeResult(_finderPathCountByAEI_API_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAEI_API_T, args);
			}
		}

		entityCache.putResult(
			AccountAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountAttachmentImpl.class, accountAttachment.getPrimaryKey(),
			accountAttachment, false);

		clearUniqueFindersCache(accountAttachmentModelImpl, false);
		cacheUniqueFindersCache(accountAttachmentModelImpl);

		accountAttachment.resetOriginalValues();

		return accountAttachment;
	}

	/**
	 * Returns the account attachment with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the account attachment
	 * @return the account attachment
	 * @throws NoSuchAccountAttachmentException if a account attachment with the primary key could not be found
	 */
	@Override
	public AccountAttachment findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAccountAttachmentException {

		AccountAttachment accountAttachment = fetchByPrimaryKey(primaryKey);

		if (accountAttachment == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAccountAttachmentException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return accountAttachment;
	}

	/**
	 * Returns the account attachment with the primary key or throws a <code>NoSuchAccountAttachmentException</code> if it could not be found.
	 *
	 * @param accountAttachmentId the primary key of the account attachment
	 * @return the account attachment
	 * @throws NoSuchAccountAttachmentException if a account attachment with the primary key could not be found
	 */
	@Override
	public AccountAttachment findByPrimaryKey(long accountAttachmentId)
		throws NoSuchAccountAttachmentException {

		return findByPrimaryKey((Serializable)accountAttachmentId);
	}

	/**
	 * Returns the account attachment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the account attachment
	 * @return the account attachment, or <code>null</code> if a account attachment with the primary key could not be found
	 */
	@Override
	public AccountAttachment fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			AccountAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountAttachmentImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		AccountAttachment accountAttachment = (AccountAttachment)serializable;

		if (accountAttachment == null) {
			Session session = null;

			try {
				session = openSession();

				accountAttachment = (AccountAttachment)session.get(
					AccountAttachmentImpl.class, primaryKey);

				if (accountAttachment != null) {
					cacheResult(accountAttachment);
				}
				else {
					entityCache.putResult(
						AccountAttachmentModelImpl.ENTITY_CACHE_ENABLED,
						AccountAttachmentImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					AccountAttachmentModelImpl.ENTITY_CACHE_ENABLED,
					AccountAttachmentImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return accountAttachment;
	}

	/**
	 * Returns the account attachment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param accountAttachmentId the primary key of the account attachment
	 * @return the account attachment, or <code>null</code> if a account attachment with the primary key could not be found
	 */
	@Override
	public AccountAttachment fetchByPrimaryKey(long accountAttachmentId) {
		return fetchByPrimaryKey((Serializable)accountAttachmentId);
	}

	@Override
	public Map<Serializable, AccountAttachment> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AccountAttachment> map =
			new HashMap<Serializable, AccountAttachment>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AccountAttachment accountAttachment = fetchByPrimaryKey(primaryKey);

			if (accountAttachment != null) {
				map.put(primaryKey, accountAttachment);
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
				AccountAttachmentModelImpl.ENTITY_CACHE_ENABLED,
				AccountAttachmentImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (AccountAttachment)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_ACCOUNTATTACHMENT_WHERE_PKS_IN);

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

			for (AccountAttachment accountAttachment :
					(List<AccountAttachment>)query.list()) {

				map.put(
					accountAttachment.getPrimaryKeyObj(), accountAttachment);

				cacheResult(accountAttachment);

				uncachedPrimaryKeys.remove(
					accountAttachment.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					AccountAttachmentModelImpl.ENTITY_CACHE_ENABLED,
					AccountAttachmentImpl.class, primaryKey, nullModel);
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
	 * Returns all the account attachments.
	 *
	 * @return the account attachments
	 */
	@Override
	public List<AccountAttachment> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the account attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account attachments
	 * @param end the upper bound of the range of account attachments (not inclusive)
	 * @return the range of account attachments
	 */
	@Override
	public List<AccountAttachment> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the account attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account attachments
	 * @param end the upper bound of the range of account attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of account attachments
	 */
	@Override
	public List<AccountAttachment> findAll(
		int start, int end,
		OrderByComparator<AccountAttachment> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the account attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account attachments
	 * @param end the upper bound of the range of account attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of account attachments
	 */
	@Override
	public List<AccountAttachment> findAll(
		int start, int end,
		OrderByComparator<AccountAttachment> orderByComparator,
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

		List<AccountAttachment> list = null;

		if (useFinderCache) {
			list = (List<AccountAttachment>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ACCOUNTATTACHMENT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ACCOUNTATTACHMENT;

				sql = sql.concat(AccountAttachmentModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AccountAttachment>)QueryUtil.list(
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
	 * Removes all the account attachments from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AccountAttachment accountAttachment : findAll()) {
			remove(accountAttachment);
		}
	}

	/**
	 * Returns the number of account attachments.
	 *
	 * @return the number of account attachments
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_ACCOUNTATTACHMENT);

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
		return AccountAttachmentModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the account attachment persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			AccountAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountAttachmentModelImpl.FINDER_CACHE_ENABLED,
			AccountAttachmentImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			AccountAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountAttachmentModelImpl.FINDER_CACHE_ENABLED,
			AccountAttachmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			AccountAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountAttachmentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByAccountEntryId = new FinderPath(
			AccountAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountAttachmentModelImpl.FINDER_CACHE_ENABLED,
			AccountAttachmentImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByAccountEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByAccountEntryId = new FinderPath(
			AccountAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountAttachmentModelImpl.FINDER_CACHE_ENABLED,
			AccountAttachmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAccountEntryId",
			new String[] {Long.class.getName()},
			AccountAttachmentModelImpl.ACCOUNTENTRYID_COLUMN_BITMASK |
			AccountAttachmentModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByAccountEntryId = new FinderPath(
			AccountAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountAttachmentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAccountEntryId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByAEI_API = new FinderPath(
			AccountAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountAttachmentModelImpl.FINDER_CACHE_ENABLED,
			AccountAttachmentImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByAEI_API",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByAEI_API = new FinderPath(
			AccountAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountAttachmentModelImpl.FINDER_CACHE_ENABLED,
			AccountAttachmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAEI_API",
			new String[] {Long.class.getName(), Long.class.getName()},
			AccountAttachmentModelImpl.ACCOUNTENTRYID_COLUMN_BITMASK |
			AccountAttachmentModelImpl.ACCOUNTPROJECTID_COLUMN_BITMASK |
			AccountAttachmentModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByAEI_API = new FinderPath(
			AccountAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountAttachmentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAEI_API",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByAEI_API_T = new FinderPath(
			AccountAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountAttachmentModelImpl.FINDER_CACHE_ENABLED,
			AccountAttachmentImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByAEI_API_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByAEI_API_T = new FinderPath(
			AccountAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountAttachmentModelImpl.FINDER_CACHE_ENABLED,
			AccountAttachmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAEI_API_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			AccountAttachmentModelImpl.ACCOUNTENTRYID_COLUMN_BITMASK |
			AccountAttachmentModelImpl.ACCOUNTPROJECTID_COLUMN_BITMASK |
			AccountAttachmentModelImpl.TYPE_COLUMN_BITMASK |
			AccountAttachmentModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByAEI_API_T = new FinderPath(
			AccountAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountAttachmentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAEI_API_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathFetchByAEI_API_FN_T = new FinderPath(
			AccountAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountAttachmentModelImpl.FINDER_CACHE_ENABLED,
			AccountAttachmentImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByAEI_API_FN_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName()
			},
			AccountAttachmentModelImpl.ACCOUNTENTRYID_COLUMN_BITMASK |
			AccountAttachmentModelImpl.ACCOUNTPROJECTID_COLUMN_BITMASK |
			AccountAttachmentModelImpl.FILENAME_COLUMN_BITMASK |
			AccountAttachmentModelImpl.TYPE_COLUMN_BITMASK);

		_finderPathCountByAEI_API_FN_T = new FinderPath(
			AccountAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountAttachmentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAEI_API_FN_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName()
			});

		AccountAttachmentUtil.setPersistence(this);
	}

	public void destroy() {
		AccountAttachmentUtil.setPersistence(null);

		entityCache.removeCache(AccountAttachmentImpl.class.getName());

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

	private static final String _SQL_SELECT_ACCOUNTATTACHMENT =
		"SELECT accountAttachment FROM AccountAttachment accountAttachment";

	private static final String _SQL_SELECT_ACCOUNTATTACHMENT_WHERE_PKS_IN =
		"SELECT accountAttachment FROM AccountAttachment accountAttachment WHERE accountAttachmentId IN (";

	private static final String _SQL_SELECT_ACCOUNTATTACHMENT_WHERE =
		"SELECT accountAttachment FROM AccountAttachment accountAttachment WHERE ";

	private static final String _SQL_COUNT_ACCOUNTATTACHMENT =
		"SELECT COUNT(accountAttachment) FROM AccountAttachment accountAttachment";

	private static final String _SQL_COUNT_ACCOUNTATTACHMENT_WHERE =
		"SELECT COUNT(accountAttachment) FROM AccountAttachment accountAttachment WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "accountAttachment.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AccountAttachment exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AccountAttachment exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AccountAttachmentPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type"});

}