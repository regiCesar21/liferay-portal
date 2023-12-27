/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.ticket.service.persistence.impl;

import com.liferay.osb.customer.ticket.exception.NoSuchTicketAttachmentException;
import com.liferay.osb.customer.ticket.model.TicketAttachment;
import com.liferay.osb.customer.ticket.model.impl.TicketAttachmentImpl;
import com.liferay.osb.customer.ticket.model.impl.TicketAttachmentModelImpl;
import com.liferay.osb.customer.ticket.service.persistence.TicketAttachmentPersistence;
import com.liferay.osb.customer.ticket.service.persistence.TicketAttachmentUtil;
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
import com.liferay.portal.kernel.util.ArrayUtil;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the ticket attachment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class TicketAttachmentPersistenceImpl
	extends BasePersistenceImpl<TicketAttachment>
	implements TicketAttachmentPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>TicketAttachmentUtil</code> to access the ticket attachment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		TicketAttachmentImpl.class.getName();

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
	 * Returns all the ticket attachments where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the matching ticket attachments
	 */
	@Override
	public List<TicketAttachment> findByAccountEntryId(long accountEntryId) {
		return findByAccountEntryId(
			accountEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ticket attachments where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TicketAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of ticket attachments
	 * @param end the upper bound of the range of ticket attachments (not inclusive)
	 * @return the range of matching ticket attachments
	 */
	@Override
	public List<TicketAttachment> findByAccountEntryId(
		long accountEntryId, int start, int end) {

		return findByAccountEntryId(accountEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ticket attachments where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TicketAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of ticket attachments
	 * @param end the upper bound of the range of ticket attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ticket attachments
	 */
	@Override
	public List<TicketAttachment> findByAccountEntryId(
		long accountEntryId, int start, int end,
		OrderByComparator<TicketAttachment> orderByComparator) {

		return findByAccountEntryId(
			accountEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ticket attachments where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TicketAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of ticket attachments
	 * @param end the upper bound of the range of ticket attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ticket attachments
	 */
	@Override
	public List<TicketAttachment> findByAccountEntryId(
		long accountEntryId, int start, int end,
		OrderByComparator<TicketAttachment> orderByComparator,
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

		List<TicketAttachment> list = null;

		if (useFinderCache) {
			list = (List<TicketAttachment>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (TicketAttachment ticketAttachment : list) {
					if (accountEntryId !=
							ticketAttachment.getAccountEntryId()) {

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

			sb.append(_SQL_SELECT_TICKETATTACHMENT_WHERE);

			sb.append(_FINDER_COLUMN_ACCOUNTENTRYID_ACCOUNTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(TicketAttachmentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEntryId);

				list = (List<TicketAttachment>)QueryUtil.list(
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
	 * Returns the first ticket attachment in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ticket attachment
	 * @throws NoSuchTicketAttachmentException if a matching ticket attachment could not be found
	 */
	@Override
	public TicketAttachment findByAccountEntryId_First(
			long accountEntryId,
			OrderByComparator<TicketAttachment> orderByComparator)
		throws NoSuchTicketAttachmentException {

		TicketAttachment ticketAttachment = fetchByAccountEntryId_First(
			accountEntryId, orderByComparator);

		if (ticketAttachment != null) {
			return ticketAttachment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountEntryId=");
		sb.append(accountEntryId);

		sb.append("}");

		throw new NoSuchTicketAttachmentException(sb.toString());
	}

	/**
	 * Returns the first ticket attachment in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ticket attachment, or <code>null</code> if a matching ticket attachment could not be found
	 */
	@Override
	public TicketAttachment fetchByAccountEntryId_First(
		long accountEntryId,
		OrderByComparator<TicketAttachment> orderByComparator) {

		List<TicketAttachment> list = findByAccountEntryId(
			accountEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ticket attachment in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ticket attachment
	 * @throws NoSuchTicketAttachmentException if a matching ticket attachment could not be found
	 */
	@Override
	public TicketAttachment findByAccountEntryId_Last(
			long accountEntryId,
			OrderByComparator<TicketAttachment> orderByComparator)
		throws NoSuchTicketAttachmentException {

		TicketAttachment ticketAttachment = fetchByAccountEntryId_Last(
			accountEntryId, orderByComparator);

		if (ticketAttachment != null) {
			return ticketAttachment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountEntryId=");
		sb.append(accountEntryId);

		sb.append("}");

		throw new NoSuchTicketAttachmentException(sb.toString());
	}

	/**
	 * Returns the last ticket attachment in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ticket attachment, or <code>null</code> if a matching ticket attachment could not be found
	 */
	@Override
	public TicketAttachment fetchByAccountEntryId_Last(
		long accountEntryId,
		OrderByComparator<TicketAttachment> orderByComparator) {

		int count = countByAccountEntryId(accountEntryId);

		if (count == 0) {
			return null;
		}

		List<TicketAttachment> list = findByAccountEntryId(
			accountEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ticket attachments before and after the current ticket attachment in the ordered set where accountEntryId = &#63;.
	 *
	 * @param ticketAttachmentId the primary key of the current ticket attachment
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ticket attachment
	 * @throws NoSuchTicketAttachmentException if a ticket attachment with the primary key could not be found
	 */
	@Override
	public TicketAttachment[] findByAccountEntryId_PrevAndNext(
			long ticketAttachmentId, long accountEntryId,
			OrderByComparator<TicketAttachment> orderByComparator)
		throws NoSuchTicketAttachmentException {

		TicketAttachment ticketAttachment = findByPrimaryKey(
			ticketAttachmentId);

		Session session = null;

		try {
			session = openSession();

			TicketAttachment[] array = new TicketAttachmentImpl[3];

			array[0] = getByAccountEntryId_PrevAndNext(
				session, ticketAttachment, accountEntryId, orderByComparator,
				true);

			array[1] = ticketAttachment;

			array[2] = getByAccountEntryId_PrevAndNext(
				session, ticketAttachment, accountEntryId, orderByComparator,
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

	protected TicketAttachment getByAccountEntryId_PrevAndNext(
		Session session, TicketAttachment ticketAttachment, long accountEntryId,
		OrderByComparator<TicketAttachment> orderByComparator,
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

		sb.append(_SQL_SELECT_TICKETATTACHMENT_WHERE);

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
			sb.append(TicketAttachmentModelImpl.ORDER_BY_JPQL);
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
						ticketAttachment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<TicketAttachment> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ticket attachments where accountEntryId = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 */
	@Override
	public void removeByAccountEntryId(long accountEntryId) {
		for (TicketAttachment ticketAttachment :
				findByAccountEntryId(
					accountEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ticketAttachment);
		}
	}

	/**
	 * Returns the number of ticket attachments where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the number of matching ticket attachments
	 */
	@Override
	public int countByAccountEntryId(long accountEntryId) {
		FinderPath finderPath = _finderPathCountByAccountEntryId;

		Object[] finderArgs = new Object[] {accountEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_TICKETATTACHMENT_WHERE);

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
		"ticketAttachment.accountEntryId = ?";

	private FinderPath _finderPathWithPaginationFindByZendeskTicketId;
	private FinderPath _finderPathWithoutPaginationFindByZendeskTicketId;
	private FinderPath _finderPathCountByZendeskTicketId;

	/**
	 * Returns all the ticket attachments where zendeskTicketId = &#63;.
	 *
	 * @param zendeskTicketId the zendesk ticket ID
	 * @return the matching ticket attachments
	 */
	@Override
	public List<TicketAttachment> findByZendeskTicketId(long zendeskTicketId) {
		return findByZendeskTicketId(
			zendeskTicketId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ticket attachments where zendeskTicketId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TicketAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param zendeskTicketId the zendesk ticket ID
	 * @param start the lower bound of the range of ticket attachments
	 * @param end the upper bound of the range of ticket attachments (not inclusive)
	 * @return the range of matching ticket attachments
	 */
	@Override
	public List<TicketAttachment> findByZendeskTicketId(
		long zendeskTicketId, int start, int end) {

		return findByZendeskTicketId(zendeskTicketId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ticket attachments where zendeskTicketId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TicketAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param zendeskTicketId the zendesk ticket ID
	 * @param start the lower bound of the range of ticket attachments
	 * @param end the upper bound of the range of ticket attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ticket attachments
	 */
	@Override
	public List<TicketAttachment> findByZendeskTicketId(
		long zendeskTicketId, int start, int end,
		OrderByComparator<TicketAttachment> orderByComparator) {

		return findByZendeskTicketId(
			zendeskTicketId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ticket attachments where zendeskTicketId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TicketAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param zendeskTicketId the zendesk ticket ID
	 * @param start the lower bound of the range of ticket attachments
	 * @param end the upper bound of the range of ticket attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ticket attachments
	 */
	@Override
	public List<TicketAttachment> findByZendeskTicketId(
		long zendeskTicketId, int start, int end,
		OrderByComparator<TicketAttachment> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByZendeskTicketId;
				finderArgs = new Object[] {zendeskTicketId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByZendeskTicketId;
			finderArgs = new Object[] {
				zendeskTicketId, start, end, orderByComparator
			};
		}

		List<TicketAttachment> list = null;

		if (useFinderCache) {
			list = (List<TicketAttachment>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (TicketAttachment ticketAttachment : list) {
					if (zendeskTicketId !=
							ticketAttachment.getZendeskTicketId()) {

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

			sb.append(_SQL_SELECT_TICKETATTACHMENT_WHERE);

			sb.append(_FINDER_COLUMN_ZENDESKTICKETID_ZENDESKTICKETID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(TicketAttachmentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(zendeskTicketId);

				list = (List<TicketAttachment>)QueryUtil.list(
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
	 * Returns the first ticket attachment in the ordered set where zendeskTicketId = &#63;.
	 *
	 * @param zendeskTicketId the zendesk ticket ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ticket attachment
	 * @throws NoSuchTicketAttachmentException if a matching ticket attachment could not be found
	 */
	@Override
	public TicketAttachment findByZendeskTicketId_First(
			long zendeskTicketId,
			OrderByComparator<TicketAttachment> orderByComparator)
		throws NoSuchTicketAttachmentException {

		TicketAttachment ticketAttachment = fetchByZendeskTicketId_First(
			zendeskTicketId, orderByComparator);

		if (ticketAttachment != null) {
			return ticketAttachment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("zendeskTicketId=");
		sb.append(zendeskTicketId);

		sb.append("}");

		throw new NoSuchTicketAttachmentException(sb.toString());
	}

	/**
	 * Returns the first ticket attachment in the ordered set where zendeskTicketId = &#63;.
	 *
	 * @param zendeskTicketId the zendesk ticket ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ticket attachment, or <code>null</code> if a matching ticket attachment could not be found
	 */
	@Override
	public TicketAttachment fetchByZendeskTicketId_First(
		long zendeskTicketId,
		OrderByComparator<TicketAttachment> orderByComparator) {

		List<TicketAttachment> list = findByZendeskTicketId(
			zendeskTicketId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ticket attachment in the ordered set where zendeskTicketId = &#63;.
	 *
	 * @param zendeskTicketId the zendesk ticket ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ticket attachment
	 * @throws NoSuchTicketAttachmentException if a matching ticket attachment could not be found
	 */
	@Override
	public TicketAttachment findByZendeskTicketId_Last(
			long zendeskTicketId,
			OrderByComparator<TicketAttachment> orderByComparator)
		throws NoSuchTicketAttachmentException {

		TicketAttachment ticketAttachment = fetchByZendeskTicketId_Last(
			zendeskTicketId, orderByComparator);

		if (ticketAttachment != null) {
			return ticketAttachment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("zendeskTicketId=");
		sb.append(zendeskTicketId);

		sb.append("}");

		throw new NoSuchTicketAttachmentException(sb.toString());
	}

	/**
	 * Returns the last ticket attachment in the ordered set where zendeskTicketId = &#63;.
	 *
	 * @param zendeskTicketId the zendesk ticket ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ticket attachment, or <code>null</code> if a matching ticket attachment could not be found
	 */
	@Override
	public TicketAttachment fetchByZendeskTicketId_Last(
		long zendeskTicketId,
		OrderByComparator<TicketAttachment> orderByComparator) {

		int count = countByZendeskTicketId(zendeskTicketId);

		if (count == 0) {
			return null;
		}

		List<TicketAttachment> list = findByZendeskTicketId(
			zendeskTicketId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ticket attachments before and after the current ticket attachment in the ordered set where zendeskTicketId = &#63;.
	 *
	 * @param ticketAttachmentId the primary key of the current ticket attachment
	 * @param zendeskTicketId the zendesk ticket ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ticket attachment
	 * @throws NoSuchTicketAttachmentException if a ticket attachment with the primary key could not be found
	 */
	@Override
	public TicketAttachment[] findByZendeskTicketId_PrevAndNext(
			long ticketAttachmentId, long zendeskTicketId,
			OrderByComparator<TicketAttachment> orderByComparator)
		throws NoSuchTicketAttachmentException {

		TicketAttachment ticketAttachment = findByPrimaryKey(
			ticketAttachmentId);

		Session session = null;

		try {
			session = openSession();

			TicketAttachment[] array = new TicketAttachmentImpl[3];

			array[0] = getByZendeskTicketId_PrevAndNext(
				session, ticketAttachment, zendeskTicketId, orderByComparator,
				true);

			array[1] = ticketAttachment;

			array[2] = getByZendeskTicketId_PrevAndNext(
				session, ticketAttachment, zendeskTicketId, orderByComparator,
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

	protected TicketAttachment getByZendeskTicketId_PrevAndNext(
		Session session, TicketAttachment ticketAttachment,
		long zendeskTicketId,
		OrderByComparator<TicketAttachment> orderByComparator,
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

		sb.append(_SQL_SELECT_TICKETATTACHMENT_WHERE);

		sb.append(_FINDER_COLUMN_ZENDESKTICKETID_ZENDESKTICKETID_2);

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
			sb.append(TicketAttachmentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(zendeskTicketId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ticketAttachment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<TicketAttachment> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ticket attachments where zendeskTicketId = &#63; from the database.
	 *
	 * @param zendeskTicketId the zendesk ticket ID
	 */
	@Override
	public void removeByZendeskTicketId(long zendeskTicketId) {
		for (TicketAttachment ticketAttachment :
				findByZendeskTicketId(
					zendeskTicketId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ticketAttachment);
		}
	}

	/**
	 * Returns the number of ticket attachments where zendeskTicketId = &#63;.
	 *
	 * @param zendeskTicketId the zendesk ticket ID
	 * @return the number of matching ticket attachments
	 */
	@Override
	public int countByZendeskTicketId(long zendeskTicketId) {
		FinderPath finderPath = _finderPathCountByZendeskTicketId;

		Object[] finderArgs = new Object[] {zendeskTicketId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_TICKETATTACHMENT_WHERE);

			sb.append(_FINDER_COLUMN_ZENDESKTICKETID_ZENDESKTICKETID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(zendeskTicketId);

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
		_FINDER_COLUMN_ZENDESKTICKETID_ZENDESKTICKETID_2 =
			"ticketAttachment.zendeskTicketId = ?";

	private FinderPath _finderPathWithPaginationFindByZTI_T;
	private FinderPath _finderPathWithoutPaginationFindByZTI_T;
	private FinderPath _finderPathCountByZTI_T;
	private FinderPath _finderPathWithPaginationCountByZTI_T;

	/**
	 * Returns all the ticket attachments where zendeskTicketId = &#63; and type = &#63;.
	 *
	 * @param zendeskTicketId the zendesk ticket ID
	 * @param type the type
	 * @return the matching ticket attachments
	 */
	@Override
	public List<TicketAttachment> findByZTI_T(long zendeskTicketId, int type) {
		return findByZTI_T(
			zendeskTicketId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ticket attachments where zendeskTicketId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TicketAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param zendeskTicketId the zendesk ticket ID
	 * @param type the type
	 * @param start the lower bound of the range of ticket attachments
	 * @param end the upper bound of the range of ticket attachments (not inclusive)
	 * @return the range of matching ticket attachments
	 */
	@Override
	public List<TicketAttachment> findByZTI_T(
		long zendeskTicketId, int type, int start, int end) {

		return findByZTI_T(zendeskTicketId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ticket attachments where zendeskTicketId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TicketAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param zendeskTicketId the zendesk ticket ID
	 * @param type the type
	 * @param start the lower bound of the range of ticket attachments
	 * @param end the upper bound of the range of ticket attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ticket attachments
	 */
	@Override
	public List<TicketAttachment> findByZTI_T(
		long zendeskTicketId, int type, int start, int end,
		OrderByComparator<TicketAttachment> orderByComparator) {

		return findByZTI_T(
			zendeskTicketId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ticket attachments where zendeskTicketId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TicketAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param zendeskTicketId the zendesk ticket ID
	 * @param type the type
	 * @param start the lower bound of the range of ticket attachments
	 * @param end the upper bound of the range of ticket attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ticket attachments
	 */
	@Override
	public List<TicketAttachment> findByZTI_T(
		long zendeskTicketId, int type, int start, int end,
		OrderByComparator<TicketAttachment> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByZTI_T;
				finderArgs = new Object[] {zendeskTicketId, type};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByZTI_T;
			finderArgs = new Object[] {
				zendeskTicketId, type, start, end, orderByComparator
			};
		}

		List<TicketAttachment> list = null;

		if (useFinderCache) {
			list = (List<TicketAttachment>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (TicketAttachment ticketAttachment : list) {
					if ((zendeskTicketId !=
							ticketAttachment.getZendeskTicketId()) ||
						(type != ticketAttachment.getType())) {

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

			sb.append(_SQL_SELECT_TICKETATTACHMENT_WHERE);

			sb.append(_FINDER_COLUMN_ZTI_T_ZENDESKTICKETID_2);

			sb.append(_FINDER_COLUMN_ZTI_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(TicketAttachmentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(zendeskTicketId);

				queryPos.add(type);

				list = (List<TicketAttachment>)QueryUtil.list(
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
	 * Returns the first ticket attachment in the ordered set where zendeskTicketId = &#63; and type = &#63;.
	 *
	 * @param zendeskTicketId the zendesk ticket ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ticket attachment
	 * @throws NoSuchTicketAttachmentException if a matching ticket attachment could not be found
	 */
	@Override
	public TicketAttachment findByZTI_T_First(
			long zendeskTicketId, int type,
			OrderByComparator<TicketAttachment> orderByComparator)
		throws NoSuchTicketAttachmentException {

		TicketAttachment ticketAttachment = fetchByZTI_T_First(
			zendeskTicketId, type, orderByComparator);

		if (ticketAttachment != null) {
			return ticketAttachment;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("zendeskTicketId=");
		sb.append(zendeskTicketId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchTicketAttachmentException(sb.toString());
	}

	/**
	 * Returns the first ticket attachment in the ordered set where zendeskTicketId = &#63; and type = &#63;.
	 *
	 * @param zendeskTicketId the zendesk ticket ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ticket attachment, or <code>null</code> if a matching ticket attachment could not be found
	 */
	@Override
	public TicketAttachment fetchByZTI_T_First(
		long zendeskTicketId, int type,
		OrderByComparator<TicketAttachment> orderByComparator) {

		List<TicketAttachment> list = findByZTI_T(
			zendeskTicketId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ticket attachment in the ordered set where zendeskTicketId = &#63; and type = &#63;.
	 *
	 * @param zendeskTicketId the zendesk ticket ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ticket attachment
	 * @throws NoSuchTicketAttachmentException if a matching ticket attachment could not be found
	 */
	@Override
	public TicketAttachment findByZTI_T_Last(
			long zendeskTicketId, int type,
			OrderByComparator<TicketAttachment> orderByComparator)
		throws NoSuchTicketAttachmentException {

		TicketAttachment ticketAttachment = fetchByZTI_T_Last(
			zendeskTicketId, type, orderByComparator);

		if (ticketAttachment != null) {
			return ticketAttachment;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("zendeskTicketId=");
		sb.append(zendeskTicketId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchTicketAttachmentException(sb.toString());
	}

	/**
	 * Returns the last ticket attachment in the ordered set where zendeskTicketId = &#63; and type = &#63;.
	 *
	 * @param zendeskTicketId the zendesk ticket ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ticket attachment, or <code>null</code> if a matching ticket attachment could not be found
	 */
	@Override
	public TicketAttachment fetchByZTI_T_Last(
		long zendeskTicketId, int type,
		OrderByComparator<TicketAttachment> orderByComparator) {

		int count = countByZTI_T(zendeskTicketId, type);

		if (count == 0) {
			return null;
		}

		List<TicketAttachment> list = findByZTI_T(
			zendeskTicketId, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ticket attachments before and after the current ticket attachment in the ordered set where zendeskTicketId = &#63; and type = &#63;.
	 *
	 * @param ticketAttachmentId the primary key of the current ticket attachment
	 * @param zendeskTicketId the zendesk ticket ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ticket attachment
	 * @throws NoSuchTicketAttachmentException if a ticket attachment with the primary key could not be found
	 */
	@Override
	public TicketAttachment[] findByZTI_T_PrevAndNext(
			long ticketAttachmentId, long zendeskTicketId, int type,
			OrderByComparator<TicketAttachment> orderByComparator)
		throws NoSuchTicketAttachmentException {

		TicketAttachment ticketAttachment = findByPrimaryKey(
			ticketAttachmentId);

		Session session = null;

		try {
			session = openSession();

			TicketAttachment[] array = new TicketAttachmentImpl[3];

			array[0] = getByZTI_T_PrevAndNext(
				session, ticketAttachment, zendeskTicketId, type,
				orderByComparator, true);

			array[1] = ticketAttachment;

			array[2] = getByZTI_T_PrevAndNext(
				session, ticketAttachment, zendeskTicketId, type,
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

	protected TicketAttachment getByZTI_T_PrevAndNext(
		Session session, TicketAttachment ticketAttachment,
		long zendeskTicketId, int type,
		OrderByComparator<TicketAttachment> orderByComparator,
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

		sb.append(_SQL_SELECT_TICKETATTACHMENT_WHERE);

		sb.append(_FINDER_COLUMN_ZTI_T_ZENDESKTICKETID_2);

		sb.append(_FINDER_COLUMN_ZTI_T_TYPE_2);

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
			sb.append(TicketAttachmentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(zendeskTicketId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ticketAttachment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<TicketAttachment> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the ticket attachments where zendeskTicketId = &#63; and type = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TicketAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param zendeskTicketId the zendesk ticket ID
	 * @param types the types
	 * @return the matching ticket attachments
	 */
	@Override
	public List<TicketAttachment> findByZTI_T(
		long zendeskTicketId, int[] types) {

		return findByZTI_T(
			zendeskTicketId, types, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ticket attachments where zendeskTicketId = &#63; and type = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TicketAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param zendeskTicketId the zendesk ticket ID
	 * @param types the types
	 * @param start the lower bound of the range of ticket attachments
	 * @param end the upper bound of the range of ticket attachments (not inclusive)
	 * @return the range of matching ticket attachments
	 */
	@Override
	public List<TicketAttachment> findByZTI_T(
		long zendeskTicketId, int[] types, int start, int end) {

		return findByZTI_T(zendeskTicketId, types, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ticket attachments where zendeskTicketId = &#63; and type = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TicketAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param zendeskTicketId the zendesk ticket ID
	 * @param types the types
	 * @param start the lower bound of the range of ticket attachments
	 * @param end the upper bound of the range of ticket attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ticket attachments
	 */
	@Override
	public List<TicketAttachment> findByZTI_T(
		long zendeskTicketId, int[] types, int start, int end,
		OrderByComparator<TicketAttachment> orderByComparator) {

		return findByZTI_T(
			zendeskTicketId, types, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ticket attachments where zendeskTicketId = &#63; and type = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TicketAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param zendeskTicketId the zendesk ticket ID
	 * @param types the types
	 * @param start the lower bound of the range of ticket attachments
	 * @param end the upper bound of the range of ticket attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ticket attachments
	 */
	@Override
	public List<TicketAttachment> findByZTI_T(
		long zendeskTicketId, int[] types, int start, int end,
		OrderByComparator<TicketAttachment> orderByComparator,
		boolean useFinderCache) {

		if (types == null) {
			types = new int[0];
		}
		else if (types.length > 1) {
			types = ArrayUtil.unique(types);

			Arrays.sort(types);
		}

		if (types.length == 1) {
			return findByZTI_T(
				zendeskTicketId, types[0], start, end, orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					zendeskTicketId, StringUtil.merge(types)
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				zendeskTicketId, StringUtil.merge(types), start, end,
				orderByComparator
			};
		}

		List<TicketAttachment> list = null;

		if (useFinderCache) {
			list = (List<TicketAttachment>)finderCache.getResult(
				_finderPathWithPaginationFindByZTI_T, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (TicketAttachment ticketAttachment : list) {
					if ((zendeskTicketId !=
							ticketAttachment.getZendeskTicketId()) ||
						!ArrayUtil.contains(
							types, ticketAttachment.getType())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_TICKETATTACHMENT_WHERE);

			sb.append(_FINDER_COLUMN_ZTI_T_ZENDESKTICKETID_2);

			if (types.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_ZTI_T_TYPE_7);

				sb.append(StringUtil.merge(types));

				sb.append(")");

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(TicketAttachmentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(zendeskTicketId);

				list = (List<TicketAttachment>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByZTI_T, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathWithPaginationFindByZTI_T, finderArgs);
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
	 * Removes all the ticket attachments where zendeskTicketId = &#63; and type = &#63; from the database.
	 *
	 * @param zendeskTicketId the zendesk ticket ID
	 * @param type the type
	 */
	@Override
	public void removeByZTI_T(long zendeskTicketId, int type) {
		for (TicketAttachment ticketAttachment :
				findByZTI_T(
					zendeskTicketId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ticketAttachment);
		}
	}

	/**
	 * Returns the number of ticket attachments where zendeskTicketId = &#63; and type = &#63;.
	 *
	 * @param zendeskTicketId the zendesk ticket ID
	 * @param type the type
	 * @return the number of matching ticket attachments
	 */
	@Override
	public int countByZTI_T(long zendeskTicketId, int type) {
		FinderPath finderPath = _finderPathCountByZTI_T;

		Object[] finderArgs = new Object[] {zendeskTicketId, type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_TICKETATTACHMENT_WHERE);

			sb.append(_FINDER_COLUMN_ZTI_T_ZENDESKTICKETID_2);

			sb.append(_FINDER_COLUMN_ZTI_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(zendeskTicketId);

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

	/**
	 * Returns the number of ticket attachments where zendeskTicketId = &#63; and type = any &#63;.
	 *
	 * @param zendeskTicketId the zendesk ticket ID
	 * @param types the types
	 * @return the number of matching ticket attachments
	 */
	@Override
	public int countByZTI_T(long zendeskTicketId, int[] types) {
		if (types == null) {
			types = new int[0];
		}
		else if (types.length > 1) {
			types = ArrayUtil.unique(types);

			Arrays.sort(types);
		}

		Object[] finderArgs = new Object[] {
			zendeskTicketId, StringUtil.merge(types)
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByZTI_T, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_TICKETATTACHMENT_WHERE);

			sb.append(_FINDER_COLUMN_ZTI_T_ZENDESKTICKETID_2);

			if (types.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_ZTI_T_TYPE_7);

				sb.append(StringUtil.merge(types));

				sb.append(")");

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(zendeskTicketId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByZTI_T, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(
					_finderPathWithPaginationCountByZTI_T, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_ZTI_T_ZENDESKTICKETID_2 =
		"ticketAttachment.zendeskTicketId = ? AND ";

	private static final String _FINDER_COLUMN_ZTI_T_TYPE_2 =
		"ticketAttachment.type = ?";

	private static final String _FINDER_COLUMN_ZTI_T_TYPE_7 =
		"ticketAttachment.type IN (";

	public TicketAttachmentPersistenceImpl() {
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

		setModelClass(TicketAttachment.class);
	}

	/**
	 * Caches the ticket attachment in the entity cache if it is enabled.
	 *
	 * @param ticketAttachment the ticket attachment
	 */
	@Override
	public void cacheResult(TicketAttachment ticketAttachment) {
		entityCache.putResult(
			TicketAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			TicketAttachmentImpl.class, ticketAttachment.getPrimaryKey(),
			ticketAttachment);

		ticketAttachment.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the ticket attachments in the entity cache if it is enabled.
	 *
	 * @param ticketAttachments the ticket attachments
	 */
	@Override
	public void cacheResult(List<TicketAttachment> ticketAttachments) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (ticketAttachments.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (TicketAttachment ticketAttachment : ticketAttachments) {
			if (entityCache.getResult(
					TicketAttachmentModelImpl.ENTITY_CACHE_ENABLED,
					TicketAttachmentImpl.class,
					ticketAttachment.getPrimaryKey()) == null) {

				cacheResult(ticketAttachment);
			}
			else {
				ticketAttachment.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all ticket attachments.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(TicketAttachmentImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the ticket attachment.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(TicketAttachment ticketAttachment) {
		entityCache.removeResult(
			TicketAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			TicketAttachmentImpl.class, ticketAttachment.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<TicketAttachment> ticketAttachments) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (TicketAttachment ticketAttachment : ticketAttachments) {
			entityCache.removeResult(
				TicketAttachmentModelImpl.ENTITY_CACHE_ENABLED,
				TicketAttachmentImpl.class, ticketAttachment.getPrimaryKey());
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				TicketAttachmentModelImpl.ENTITY_CACHE_ENABLED,
				TicketAttachmentImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new ticket attachment with the primary key. Does not add the ticket attachment to the database.
	 *
	 * @param ticketAttachmentId the primary key for the new ticket attachment
	 * @return the new ticket attachment
	 */
	@Override
	public TicketAttachment create(long ticketAttachmentId) {
		TicketAttachment ticketAttachment = new TicketAttachmentImpl();

		ticketAttachment.setNew(true);
		ticketAttachment.setPrimaryKey(ticketAttachmentId);

		return ticketAttachment;
	}

	/**
	 * Removes the ticket attachment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ticketAttachmentId the primary key of the ticket attachment
	 * @return the ticket attachment that was removed
	 * @throws NoSuchTicketAttachmentException if a ticket attachment with the primary key could not be found
	 */
	@Override
	public TicketAttachment remove(long ticketAttachmentId)
		throws NoSuchTicketAttachmentException {

		return remove((Serializable)ticketAttachmentId);
	}

	/**
	 * Removes the ticket attachment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ticket attachment
	 * @return the ticket attachment that was removed
	 * @throws NoSuchTicketAttachmentException if a ticket attachment with the primary key could not be found
	 */
	@Override
	public TicketAttachment remove(Serializable primaryKey)
		throws NoSuchTicketAttachmentException {

		Session session = null;

		try {
			session = openSession();

			TicketAttachment ticketAttachment = (TicketAttachment)session.get(
				TicketAttachmentImpl.class, primaryKey);

			if (ticketAttachment == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTicketAttachmentException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ticketAttachment);
		}
		catch (NoSuchTicketAttachmentException noSuchEntityException) {
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
	protected TicketAttachment removeImpl(TicketAttachment ticketAttachment) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ticketAttachment)) {
				ticketAttachment = (TicketAttachment)session.get(
					TicketAttachmentImpl.class,
					ticketAttachment.getPrimaryKeyObj());
			}

			if (ticketAttachment != null) {
				session.delete(ticketAttachment);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ticketAttachment != null) {
			clearCache(ticketAttachment);
		}

		return ticketAttachment;
	}

	@Override
	public TicketAttachment updateImpl(TicketAttachment ticketAttachment) {
		boolean isNew = ticketAttachment.isNew();

		if (!(ticketAttachment instanceof TicketAttachmentModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ticketAttachment.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					ticketAttachment);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ticketAttachment proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom TicketAttachment implementation " +
					ticketAttachment.getClass());
		}

		TicketAttachmentModelImpl ticketAttachmentModelImpl =
			(TicketAttachmentModelImpl)ticketAttachment;

		if (isNew && (ticketAttachment.getCreateDate() == null)) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Date date = new Date();

			if (serviceContext == null) {
				ticketAttachment.setCreateDate(date);
			}
			else {
				ticketAttachment.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(ticketAttachment);

				ticketAttachment.setNew(false);
			}
			else {
				ticketAttachment = (TicketAttachment)session.merge(
					ticketAttachment);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!TicketAttachmentModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				ticketAttachmentModelImpl.getAccountEntryId()
			};

			finderCache.removeResult(_finderPathCountByAccountEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByAccountEntryId, args);

			args = new Object[] {
				ticketAttachmentModelImpl.getZendeskTicketId()
			};

			finderCache.removeResult(_finderPathCountByZendeskTicketId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByZendeskTicketId, args);

			args = new Object[] {
				ticketAttachmentModelImpl.getZendeskTicketId(),
				ticketAttachmentModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByZTI_T, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByZTI_T, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((ticketAttachmentModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByAccountEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					ticketAttachmentModelImpl.getOriginalAccountEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByAccountEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAccountEntryId, args);

				args = new Object[] {
					ticketAttachmentModelImpl.getAccountEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByAccountEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAccountEntryId, args);
			}

			if ((ticketAttachmentModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByZendeskTicketId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					ticketAttachmentModelImpl.getOriginalZendeskTicketId()
				};

				finderCache.removeResult(
					_finderPathCountByZendeskTicketId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByZendeskTicketId, args);

				args = new Object[] {
					ticketAttachmentModelImpl.getZendeskTicketId()
				};

				finderCache.removeResult(
					_finderPathCountByZendeskTicketId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByZendeskTicketId, args);
			}

			if ((ticketAttachmentModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByZTI_T.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					ticketAttachmentModelImpl.getOriginalZendeskTicketId(),
					ticketAttachmentModelImpl.getOriginalType()
				};

				finderCache.removeResult(_finderPathCountByZTI_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByZTI_T, args);

				args = new Object[] {
					ticketAttachmentModelImpl.getZendeskTicketId(),
					ticketAttachmentModelImpl.getType()
				};

				finderCache.removeResult(_finderPathCountByZTI_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByZTI_T, args);
			}
		}

		entityCache.putResult(
			TicketAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			TicketAttachmentImpl.class, ticketAttachment.getPrimaryKey(),
			ticketAttachment, false);

		ticketAttachment.resetOriginalValues();

		return ticketAttachment;
	}

	/**
	 * Returns the ticket attachment with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ticket attachment
	 * @return the ticket attachment
	 * @throws NoSuchTicketAttachmentException if a ticket attachment with the primary key could not be found
	 */
	@Override
	public TicketAttachment findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTicketAttachmentException {

		TicketAttachment ticketAttachment = fetchByPrimaryKey(primaryKey);

		if (ticketAttachment == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTicketAttachmentException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ticketAttachment;
	}

	/**
	 * Returns the ticket attachment with the primary key or throws a <code>NoSuchTicketAttachmentException</code> if it could not be found.
	 *
	 * @param ticketAttachmentId the primary key of the ticket attachment
	 * @return the ticket attachment
	 * @throws NoSuchTicketAttachmentException if a ticket attachment with the primary key could not be found
	 */
	@Override
	public TicketAttachment findByPrimaryKey(long ticketAttachmentId)
		throws NoSuchTicketAttachmentException {

		return findByPrimaryKey((Serializable)ticketAttachmentId);
	}

	/**
	 * Returns the ticket attachment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ticket attachment
	 * @return the ticket attachment, or <code>null</code> if a ticket attachment with the primary key could not be found
	 */
	@Override
	public TicketAttachment fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			TicketAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			TicketAttachmentImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		TicketAttachment ticketAttachment = (TicketAttachment)serializable;

		if (ticketAttachment == null) {
			Session session = null;

			try {
				session = openSession();

				ticketAttachment = (TicketAttachment)session.get(
					TicketAttachmentImpl.class, primaryKey);

				if (ticketAttachment != null) {
					cacheResult(ticketAttachment);
				}
				else {
					entityCache.putResult(
						TicketAttachmentModelImpl.ENTITY_CACHE_ENABLED,
						TicketAttachmentImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					TicketAttachmentModelImpl.ENTITY_CACHE_ENABLED,
					TicketAttachmentImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return ticketAttachment;
	}

	/**
	 * Returns the ticket attachment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ticketAttachmentId the primary key of the ticket attachment
	 * @return the ticket attachment, or <code>null</code> if a ticket attachment with the primary key could not be found
	 */
	@Override
	public TicketAttachment fetchByPrimaryKey(long ticketAttachmentId) {
		return fetchByPrimaryKey((Serializable)ticketAttachmentId);
	}

	@Override
	public Map<Serializable, TicketAttachment> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, TicketAttachment> map =
			new HashMap<Serializable, TicketAttachment>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			TicketAttachment ticketAttachment = fetchByPrimaryKey(primaryKey);

			if (ticketAttachment != null) {
				map.put(primaryKey, ticketAttachment);
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
				TicketAttachmentModelImpl.ENTITY_CACHE_ENABLED,
				TicketAttachmentImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (TicketAttachment)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_TICKETATTACHMENT_WHERE_PKS_IN);

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

			for (TicketAttachment ticketAttachment :
					(List<TicketAttachment>)query.list()) {

				map.put(ticketAttachment.getPrimaryKeyObj(), ticketAttachment);

				cacheResult(ticketAttachment);

				uncachedPrimaryKeys.remove(ticketAttachment.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					TicketAttachmentModelImpl.ENTITY_CACHE_ENABLED,
					TicketAttachmentImpl.class, primaryKey, nullModel);
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
	 * Returns all the ticket attachments.
	 *
	 * @return the ticket attachments
	 */
	@Override
	public List<TicketAttachment> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ticket attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TicketAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ticket attachments
	 * @param end the upper bound of the range of ticket attachments (not inclusive)
	 * @return the range of ticket attachments
	 */
	@Override
	public List<TicketAttachment> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ticket attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TicketAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ticket attachments
	 * @param end the upper bound of the range of ticket attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ticket attachments
	 */
	@Override
	public List<TicketAttachment> findAll(
		int start, int end,
		OrderByComparator<TicketAttachment> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ticket attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TicketAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ticket attachments
	 * @param end the upper bound of the range of ticket attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ticket attachments
	 */
	@Override
	public List<TicketAttachment> findAll(
		int start, int end,
		OrderByComparator<TicketAttachment> orderByComparator,
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

		List<TicketAttachment> list = null;

		if (useFinderCache) {
			list = (List<TicketAttachment>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_TICKETATTACHMENT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_TICKETATTACHMENT;

				sql = sql.concat(TicketAttachmentModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<TicketAttachment>)QueryUtil.list(
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
	 * Removes all the ticket attachments from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (TicketAttachment ticketAttachment : findAll()) {
			remove(ticketAttachment);
		}
	}

	/**
	 * Returns the number of ticket attachments.
	 *
	 * @return the number of ticket attachments
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_TICKETATTACHMENT);

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
		return TicketAttachmentModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the ticket attachment persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			TicketAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			TicketAttachmentModelImpl.FINDER_CACHE_ENABLED,
			TicketAttachmentImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			TicketAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			TicketAttachmentModelImpl.FINDER_CACHE_ENABLED,
			TicketAttachmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			TicketAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			TicketAttachmentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByAccountEntryId = new FinderPath(
			TicketAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			TicketAttachmentModelImpl.FINDER_CACHE_ENABLED,
			TicketAttachmentImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByAccountEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByAccountEntryId = new FinderPath(
			TicketAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			TicketAttachmentModelImpl.FINDER_CACHE_ENABLED,
			TicketAttachmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAccountEntryId",
			new String[] {Long.class.getName()},
			TicketAttachmentModelImpl.ACCOUNTENTRYID_COLUMN_BITMASK |
			TicketAttachmentModelImpl.ZENDESKTICKETID_COLUMN_BITMASK |
			TicketAttachmentModelImpl.FILENAME_COLUMN_BITMASK);

		_finderPathCountByAccountEntryId = new FinderPath(
			TicketAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			TicketAttachmentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAccountEntryId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByZendeskTicketId = new FinderPath(
			TicketAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			TicketAttachmentModelImpl.FINDER_CACHE_ENABLED,
			TicketAttachmentImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByZendeskTicketId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByZendeskTicketId = new FinderPath(
			TicketAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			TicketAttachmentModelImpl.FINDER_CACHE_ENABLED,
			TicketAttachmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByZendeskTicketId",
			new String[] {Long.class.getName()},
			TicketAttachmentModelImpl.ZENDESKTICKETID_COLUMN_BITMASK |
			TicketAttachmentModelImpl.FILENAME_COLUMN_BITMASK);

		_finderPathCountByZendeskTicketId = new FinderPath(
			TicketAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			TicketAttachmentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByZendeskTicketId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByZTI_T = new FinderPath(
			TicketAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			TicketAttachmentModelImpl.FINDER_CACHE_ENABLED,
			TicketAttachmentImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByZTI_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByZTI_T = new FinderPath(
			TicketAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			TicketAttachmentModelImpl.FINDER_CACHE_ENABLED,
			TicketAttachmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByZTI_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			TicketAttachmentModelImpl.ZENDESKTICKETID_COLUMN_BITMASK |
			TicketAttachmentModelImpl.TYPE_COLUMN_BITMASK |
			TicketAttachmentModelImpl.FILENAME_COLUMN_BITMASK);

		_finderPathCountByZTI_T = new FinderPath(
			TicketAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			TicketAttachmentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByZTI_T",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationCountByZTI_T = new FinderPath(
			TicketAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			TicketAttachmentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByZTI_T",
			new String[] {Long.class.getName(), Integer.class.getName()});

		TicketAttachmentUtil.setPersistence(this);
	}

	public void destroy() {
		TicketAttachmentUtil.setPersistence(null);

		entityCache.removeCache(TicketAttachmentImpl.class.getName());

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

	private static final String _SQL_SELECT_TICKETATTACHMENT =
		"SELECT ticketAttachment FROM TicketAttachment ticketAttachment";

	private static final String _SQL_SELECT_TICKETATTACHMENT_WHERE_PKS_IN =
		"SELECT ticketAttachment FROM TicketAttachment ticketAttachment WHERE ticketAttachmentId IN (";

	private static final String _SQL_SELECT_TICKETATTACHMENT_WHERE =
		"SELECT ticketAttachment FROM TicketAttachment ticketAttachment WHERE ";

	private static final String _SQL_COUNT_TICKETATTACHMENT =
		"SELECT COUNT(ticketAttachment) FROM TicketAttachment ticketAttachment";

	private static final String _SQL_COUNT_TICKETATTACHMENT_WHERE =
		"SELECT COUNT(ticketAttachment) FROM TicketAttachment ticketAttachment WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ticketAttachment.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No TicketAttachment exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No TicketAttachment exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		TicketAttachmentPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type"});

}