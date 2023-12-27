/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.admin.service.persistence.impl;

import com.liferay.osb.customer.admin.exception.NoSuchAccountEnvironmentAttachmentException;
import com.liferay.osb.customer.admin.model.AccountEnvironmentAttachment;
import com.liferay.osb.customer.admin.model.impl.AccountEnvironmentAttachmentImpl;
import com.liferay.osb.customer.admin.model.impl.AccountEnvironmentAttachmentModelImpl;
import com.liferay.osb.customer.admin.service.persistence.AccountEnvironmentAttachmentPersistence;
import com.liferay.osb.customer.admin.service.persistence.AccountEnvironmentAttachmentUtil;
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
 * The persistence implementation for the account environment attachment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AccountEnvironmentAttachmentPersistenceImpl
	extends BasePersistenceImpl<AccountEnvironmentAttachment>
	implements AccountEnvironmentAttachmentPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AccountEnvironmentAttachmentUtil</code> to access the account environment attachment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AccountEnvironmentAttachmentImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByAccountEnvironmentId;
	private FinderPath _finderPathWithoutPaginationFindByAccountEnvironmentId;
	private FinderPath _finderPathCountByAccountEnvironmentId;

	/**
	 * Returns all the account environment attachments where accountEnvironmentId = &#63;.
	 *
	 * @param accountEnvironmentId the account environment ID
	 * @return the matching account environment attachments
	 */
	@Override
	public List<AccountEnvironmentAttachment> findByAccountEnvironmentId(
		long accountEnvironmentId) {

		return findByAccountEnvironmentId(
			accountEnvironmentId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the account environment attachments where accountEnvironmentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEnvironmentAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param accountEnvironmentId the account environment ID
	 * @param start the lower bound of the range of account environment attachments
	 * @param end the upper bound of the range of account environment attachments (not inclusive)
	 * @return the range of matching account environment attachments
	 */
	@Override
	public List<AccountEnvironmentAttachment> findByAccountEnvironmentId(
		long accountEnvironmentId, int start, int end) {

		return findByAccountEnvironmentId(
			accountEnvironmentId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the account environment attachments where accountEnvironmentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEnvironmentAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param accountEnvironmentId the account environment ID
	 * @param start the lower bound of the range of account environment attachments
	 * @param end the upper bound of the range of account environment attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account environment attachments
	 */
	@Override
	public List<AccountEnvironmentAttachment> findByAccountEnvironmentId(
		long accountEnvironmentId, int start, int end,
		OrderByComparator<AccountEnvironmentAttachment> orderByComparator) {

		return findByAccountEnvironmentId(
			accountEnvironmentId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the account environment attachments where accountEnvironmentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEnvironmentAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param accountEnvironmentId the account environment ID
	 * @param start the lower bound of the range of account environment attachments
	 * @param end the upper bound of the range of account environment attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account environment attachments
	 */
	@Override
	public List<AccountEnvironmentAttachment> findByAccountEnvironmentId(
		long accountEnvironmentId, int start, int end,
		OrderByComparator<AccountEnvironmentAttachment> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByAccountEnvironmentId;
				finderArgs = new Object[] {accountEnvironmentId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByAccountEnvironmentId;
			finderArgs = new Object[] {
				accountEnvironmentId, start, end, orderByComparator
			};
		}

		List<AccountEnvironmentAttachment> list = null;

		if (useFinderCache) {
			list = (List<AccountEnvironmentAttachment>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AccountEnvironmentAttachment accountEnvironmentAttachment :
						list) {

					if (accountEnvironmentId !=
							accountEnvironmentAttachment.
								getAccountEnvironmentId()) {

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

			sb.append(_SQL_SELECT_ACCOUNTENVIRONMENTATTACHMENT_WHERE);

			sb.append(
				_FINDER_COLUMN_ACCOUNTENVIRONMENTID_ACCOUNTENVIRONMENTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AccountEnvironmentAttachmentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEnvironmentId);

				list = (List<AccountEnvironmentAttachment>)QueryUtil.list(
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
	 * Returns the first account environment attachment in the ordered set where accountEnvironmentId = &#63;.
	 *
	 * @param accountEnvironmentId the account environment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account environment attachment
	 * @throws NoSuchAccountEnvironmentAttachmentException if a matching account environment attachment could not be found
	 */
	@Override
	public AccountEnvironmentAttachment findByAccountEnvironmentId_First(
			long accountEnvironmentId,
			OrderByComparator<AccountEnvironmentAttachment> orderByComparator)
		throws NoSuchAccountEnvironmentAttachmentException {

		AccountEnvironmentAttachment accountEnvironmentAttachment =
			fetchByAccountEnvironmentId_First(
				accountEnvironmentId, orderByComparator);

		if (accountEnvironmentAttachment != null) {
			return accountEnvironmentAttachment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountEnvironmentId=");
		sb.append(accountEnvironmentId);

		sb.append("}");

		throw new NoSuchAccountEnvironmentAttachmentException(sb.toString());
	}

	/**
	 * Returns the first account environment attachment in the ordered set where accountEnvironmentId = &#63;.
	 *
	 * @param accountEnvironmentId the account environment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account environment attachment, or <code>null</code> if a matching account environment attachment could not be found
	 */
	@Override
	public AccountEnvironmentAttachment fetchByAccountEnvironmentId_First(
		long accountEnvironmentId,
		OrderByComparator<AccountEnvironmentAttachment> orderByComparator) {

		List<AccountEnvironmentAttachment> list = findByAccountEnvironmentId(
			accountEnvironmentId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last account environment attachment in the ordered set where accountEnvironmentId = &#63;.
	 *
	 * @param accountEnvironmentId the account environment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account environment attachment
	 * @throws NoSuchAccountEnvironmentAttachmentException if a matching account environment attachment could not be found
	 */
	@Override
	public AccountEnvironmentAttachment findByAccountEnvironmentId_Last(
			long accountEnvironmentId,
			OrderByComparator<AccountEnvironmentAttachment> orderByComparator)
		throws NoSuchAccountEnvironmentAttachmentException {

		AccountEnvironmentAttachment accountEnvironmentAttachment =
			fetchByAccountEnvironmentId_Last(
				accountEnvironmentId, orderByComparator);

		if (accountEnvironmentAttachment != null) {
			return accountEnvironmentAttachment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountEnvironmentId=");
		sb.append(accountEnvironmentId);

		sb.append("}");

		throw new NoSuchAccountEnvironmentAttachmentException(sb.toString());
	}

	/**
	 * Returns the last account environment attachment in the ordered set where accountEnvironmentId = &#63;.
	 *
	 * @param accountEnvironmentId the account environment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account environment attachment, or <code>null</code> if a matching account environment attachment could not be found
	 */
	@Override
	public AccountEnvironmentAttachment fetchByAccountEnvironmentId_Last(
		long accountEnvironmentId,
		OrderByComparator<AccountEnvironmentAttachment> orderByComparator) {

		int count = countByAccountEnvironmentId(accountEnvironmentId);

		if (count == 0) {
			return null;
		}

		List<AccountEnvironmentAttachment> list = findByAccountEnvironmentId(
			accountEnvironmentId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the account environment attachments before and after the current account environment attachment in the ordered set where accountEnvironmentId = &#63;.
	 *
	 * @param accountEnvironmentAttachmentId the primary key of the current account environment attachment
	 * @param accountEnvironmentId the account environment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account environment attachment
	 * @throws NoSuchAccountEnvironmentAttachmentException if a account environment attachment with the primary key could not be found
	 */
	@Override
	public AccountEnvironmentAttachment[]
			findByAccountEnvironmentId_PrevAndNext(
				long accountEnvironmentAttachmentId, long accountEnvironmentId,
				OrderByComparator<AccountEnvironmentAttachment>
					orderByComparator)
		throws NoSuchAccountEnvironmentAttachmentException {

		AccountEnvironmentAttachment accountEnvironmentAttachment =
			findByPrimaryKey(accountEnvironmentAttachmentId);

		Session session = null;

		try {
			session = openSession();

			AccountEnvironmentAttachment[] array =
				new AccountEnvironmentAttachmentImpl[3];

			array[0] = getByAccountEnvironmentId_PrevAndNext(
				session, accountEnvironmentAttachment, accountEnvironmentId,
				orderByComparator, true);

			array[1] = accountEnvironmentAttachment;

			array[2] = getByAccountEnvironmentId_PrevAndNext(
				session, accountEnvironmentAttachment, accountEnvironmentId,
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

	protected AccountEnvironmentAttachment
		getByAccountEnvironmentId_PrevAndNext(
			Session session,
			AccountEnvironmentAttachment accountEnvironmentAttachment,
			long accountEnvironmentId,
			OrderByComparator<AccountEnvironmentAttachment> orderByComparator,
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

		sb.append(_SQL_SELECT_ACCOUNTENVIRONMENTATTACHMENT_WHERE);

		sb.append(_FINDER_COLUMN_ACCOUNTENVIRONMENTID_ACCOUNTENVIRONMENTID_2);

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
			sb.append(AccountEnvironmentAttachmentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(accountEnvironmentId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						accountEnvironmentAttachment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AccountEnvironmentAttachment> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the account environment attachments where accountEnvironmentId = &#63; from the database.
	 *
	 * @param accountEnvironmentId the account environment ID
	 */
	@Override
	public void removeByAccountEnvironmentId(long accountEnvironmentId) {
		for (AccountEnvironmentAttachment accountEnvironmentAttachment :
				findByAccountEnvironmentId(
					accountEnvironmentId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(accountEnvironmentAttachment);
		}
	}

	/**
	 * Returns the number of account environment attachments where accountEnvironmentId = &#63;.
	 *
	 * @param accountEnvironmentId the account environment ID
	 * @return the number of matching account environment attachments
	 */
	@Override
	public int countByAccountEnvironmentId(long accountEnvironmentId) {
		FinderPath finderPath = _finderPathCountByAccountEnvironmentId;

		Object[] finderArgs = new Object[] {accountEnvironmentId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ACCOUNTENVIRONMENTATTACHMENT_WHERE);

			sb.append(
				_FINDER_COLUMN_ACCOUNTENVIRONMENTID_ACCOUNTENVIRONMENTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEnvironmentId);

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
		_FINDER_COLUMN_ACCOUNTENVIRONMENTID_ACCOUNTENVIRONMENTID_2 =
			"accountEnvironmentAttachment.accountEnvironmentId = ?";

	private FinderPath _finderPathFetchByAEI_FN;
	private FinderPath _finderPathCountByAEI_FN;

	/**
	 * Returns the account environment attachment where accountEnvironmentId = &#63; and fileName = &#63; or throws a <code>NoSuchAccountEnvironmentAttachmentException</code> if it could not be found.
	 *
	 * @param accountEnvironmentId the account environment ID
	 * @param fileName the file name
	 * @return the matching account environment attachment
	 * @throws NoSuchAccountEnvironmentAttachmentException if a matching account environment attachment could not be found
	 */
	@Override
	public AccountEnvironmentAttachment findByAEI_FN(
			long accountEnvironmentId, String fileName)
		throws NoSuchAccountEnvironmentAttachmentException {

		AccountEnvironmentAttachment accountEnvironmentAttachment =
			fetchByAEI_FN(accountEnvironmentId, fileName);

		if (accountEnvironmentAttachment == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("accountEnvironmentId=");
			sb.append(accountEnvironmentId);

			sb.append(", fileName=");
			sb.append(fileName);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchAccountEnvironmentAttachmentException(
				sb.toString());
		}

		return accountEnvironmentAttachment;
	}

	/**
	 * Returns the account environment attachment where accountEnvironmentId = &#63; and fileName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param accountEnvironmentId the account environment ID
	 * @param fileName the file name
	 * @return the matching account environment attachment, or <code>null</code> if a matching account environment attachment could not be found
	 */
	@Override
	public AccountEnvironmentAttachment fetchByAEI_FN(
		long accountEnvironmentId, String fileName) {

		return fetchByAEI_FN(accountEnvironmentId, fileName, true);
	}

	/**
	 * Returns the account environment attachment where accountEnvironmentId = &#63; and fileName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param accountEnvironmentId the account environment ID
	 * @param fileName the file name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching account environment attachment, or <code>null</code> if a matching account environment attachment could not be found
	 */
	@Override
	public AccountEnvironmentAttachment fetchByAEI_FN(
		long accountEnvironmentId, String fileName, boolean useFinderCache) {

		fileName = Objects.toString(fileName, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {accountEnvironmentId, fileName};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByAEI_FN, finderArgs, this);
		}

		if (result instanceof AccountEnvironmentAttachment) {
			AccountEnvironmentAttachment accountEnvironmentAttachment =
				(AccountEnvironmentAttachment)result;

			if ((accountEnvironmentId !=
					accountEnvironmentAttachment.getAccountEnvironmentId()) ||
				!Objects.equals(
					fileName, accountEnvironmentAttachment.getFileName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_ACCOUNTENVIRONMENTATTACHMENT_WHERE);

			sb.append(_FINDER_COLUMN_AEI_FN_ACCOUNTENVIRONMENTID_2);

			boolean bindFileName = false;

			if (fileName.isEmpty()) {
				sb.append(_FINDER_COLUMN_AEI_FN_FILENAME_3);
			}
			else {
				bindFileName = true;

				sb.append(_FINDER_COLUMN_AEI_FN_FILENAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEnvironmentId);

				if (bindFileName) {
					queryPos.add(fileName);
				}

				List<AccountEnvironmentAttachment> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByAEI_FN, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									accountEnvironmentId, fileName
								};
							}

							_log.warn(
								"AccountEnvironmentAttachmentPersistenceImpl.fetchByAEI_FN(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					AccountEnvironmentAttachment accountEnvironmentAttachment =
						list.get(0);

					result = accountEnvironmentAttachment;

					cacheResult(accountEnvironmentAttachment);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByAEI_FN, finderArgs);
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
			return (AccountEnvironmentAttachment)result;
		}
	}

	/**
	 * Removes the account environment attachment where accountEnvironmentId = &#63; and fileName = &#63; from the database.
	 *
	 * @param accountEnvironmentId the account environment ID
	 * @param fileName the file name
	 * @return the account environment attachment that was removed
	 */
	@Override
	public AccountEnvironmentAttachment removeByAEI_FN(
			long accountEnvironmentId, String fileName)
		throws NoSuchAccountEnvironmentAttachmentException {

		AccountEnvironmentAttachment accountEnvironmentAttachment =
			findByAEI_FN(accountEnvironmentId, fileName);

		return remove(accountEnvironmentAttachment);
	}

	/**
	 * Returns the number of account environment attachments where accountEnvironmentId = &#63; and fileName = &#63;.
	 *
	 * @param accountEnvironmentId the account environment ID
	 * @param fileName the file name
	 * @return the number of matching account environment attachments
	 */
	@Override
	public int countByAEI_FN(long accountEnvironmentId, String fileName) {
		fileName = Objects.toString(fileName, "");

		FinderPath finderPath = _finderPathCountByAEI_FN;

		Object[] finderArgs = new Object[] {accountEnvironmentId, fileName};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ACCOUNTENVIRONMENTATTACHMENT_WHERE);

			sb.append(_FINDER_COLUMN_AEI_FN_ACCOUNTENVIRONMENTID_2);

			boolean bindFileName = false;

			if (fileName.isEmpty()) {
				sb.append(_FINDER_COLUMN_AEI_FN_FILENAME_3);
			}
			else {
				bindFileName = true;

				sb.append(_FINDER_COLUMN_AEI_FN_FILENAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEnvironmentId);

				if (bindFileName) {
					queryPos.add(fileName);
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

	private static final String _FINDER_COLUMN_AEI_FN_ACCOUNTENVIRONMENTID_2 =
		"accountEnvironmentAttachment.accountEnvironmentId = ? AND ";

	private static final String _FINDER_COLUMN_AEI_FN_FILENAME_2 =
		"accountEnvironmentAttachment.fileName = ?";

	private static final String _FINDER_COLUMN_AEI_FN_FILENAME_3 =
		"(accountEnvironmentAttachment.fileName IS NULL OR accountEnvironmentAttachment.fileName = '')";

	private FinderPath _finderPathFetchByAEI_T;
	private FinderPath _finderPathCountByAEI_T;

	/**
	 * Returns the account environment attachment where accountEnvironmentId = &#63; and type = &#63; or throws a <code>NoSuchAccountEnvironmentAttachmentException</code> if it could not be found.
	 *
	 * @param accountEnvironmentId the account environment ID
	 * @param type the type
	 * @return the matching account environment attachment
	 * @throws NoSuchAccountEnvironmentAttachmentException if a matching account environment attachment could not be found
	 */
	@Override
	public AccountEnvironmentAttachment findByAEI_T(
			long accountEnvironmentId, int type)
		throws NoSuchAccountEnvironmentAttachmentException {

		AccountEnvironmentAttachment accountEnvironmentAttachment =
			fetchByAEI_T(accountEnvironmentId, type);

		if (accountEnvironmentAttachment == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("accountEnvironmentId=");
			sb.append(accountEnvironmentId);

			sb.append(", type=");
			sb.append(type);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchAccountEnvironmentAttachmentException(
				sb.toString());
		}

		return accountEnvironmentAttachment;
	}

	/**
	 * Returns the account environment attachment where accountEnvironmentId = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param accountEnvironmentId the account environment ID
	 * @param type the type
	 * @return the matching account environment attachment, or <code>null</code> if a matching account environment attachment could not be found
	 */
	@Override
	public AccountEnvironmentAttachment fetchByAEI_T(
		long accountEnvironmentId, int type) {

		return fetchByAEI_T(accountEnvironmentId, type, true);
	}

	/**
	 * Returns the account environment attachment where accountEnvironmentId = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param accountEnvironmentId the account environment ID
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching account environment attachment, or <code>null</code> if a matching account environment attachment could not be found
	 */
	@Override
	public AccountEnvironmentAttachment fetchByAEI_T(
		long accountEnvironmentId, int type, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {accountEnvironmentId, type};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByAEI_T, finderArgs, this);
		}

		if (result instanceof AccountEnvironmentAttachment) {
			AccountEnvironmentAttachment accountEnvironmentAttachment =
				(AccountEnvironmentAttachment)result;

			if ((accountEnvironmentId !=
					accountEnvironmentAttachment.getAccountEnvironmentId()) ||
				(type != accountEnvironmentAttachment.getType())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_ACCOUNTENVIRONMENTATTACHMENT_WHERE);

			sb.append(_FINDER_COLUMN_AEI_T_ACCOUNTENVIRONMENTID_2);

			sb.append(_FINDER_COLUMN_AEI_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEnvironmentId);

				queryPos.add(type);

				List<AccountEnvironmentAttachment> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByAEI_T, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									accountEnvironmentId, type
								};
							}

							_log.warn(
								"AccountEnvironmentAttachmentPersistenceImpl.fetchByAEI_T(long, int, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					AccountEnvironmentAttachment accountEnvironmentAttachment =
						list.get(0);

					result = accountEnvironmentAttachment;

					cacheResult(accountEnvironmentAttachment);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByAEI_T, finderArgs);
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
			return (AccountEnvironmentAttachment)result;
		}
	}

	/**
	 * Removes the account environment attachment where accountEnvironmentId = &#63; and type = &#63; from the database.
	 *
	 * @param accountEnvironmentId the account environment ID
	 * @param type the type
	 * @return the account environment attachment that was removed
	 */
	@Override
	public AccountEnvironmentAttachment removeByAEI_T(
			long accountEnvironmentId, int type)
		throws NoSuchAccountEnvironmentAttachmentException {

		AccountEnvironmentAttachment accountEnvironmentAttachment = findByAEI_T(
			accountEnvironmentId, type);

		return remove(accountEnvironmentAttachment);
	}

	/**
	 * Returns the number of account environment attachments where accountEnvironmentId = &#63; and type = &#63;.
	 *
	 * @param accountEnvironmentId the account environment ID
	 * @param type the type
	 * @return the number of matching account environment attachments
	 */
	@Override
	public int countByAEI_T(long accountEnvironmentId, int type) {
		FinderPath finderPath = _finderPathCountByAEI_T;

		Object[] finderArgs = new Object[] {accountEnvironmentId, type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ACCOUNTENVIRONMENTATTACHMENT_WHERE);

			sb.append(_FINDER_COLUMN_AEI_T_ACCOUNTENVIRONMENTID_2);

			sb.append(_FINDER_COLUMN_AEI_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEnvironmentId);

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

	private static final String _FINDER_COLUMN_AEI_T_ACCOUNTENVIRONMENTID_2 =
		"accountEnvironmentAttachment.accountEnvironmentId = ? AND ";

	private static final String _FINDER_COLUMN_AEI_T_TYPE_2 =
		"accountEnvironmentAttachment.type = ?";

	public AccountEnvironmentAttachmentPersistenceImpl() {
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

		setModelClass(AccountEnvironmentAttachment.class);
	}

	/**
	 * Caches the account environment attachment in the entity cache if it is enabled.
	 *
	 * @param accountEnvironmentAttachment the account environment attachment
	 */
	@Override
	public void cacheResult(
		AccountEnvironmentAttachment accountEnvironmentAttachment) {

		entityCache.putResult(
			AccountEnvironmentAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountEnvironmentAttachmentImpl.class,
			accountEnvironmentAttachment.getPrimaryKey(),
			accountEnvironmentAttachment);

		finderCache.putResult(
			_finderPathFetchByAEI_FN,
			new Object[] {
				accountEnvironmentAttachment.getAccountEnvironmentId(),
				accountEnvironmentAttachment.getFileName()
			},
			accountEnvironmentAttachment);

		finderCache.putResult(
			_finderPathFetchByAEI_T,
			new Object[] {
				accountEnvironmentAttachment.getAccountEnvironmentId(),
				accountEnvironmentAttachment.getType()
			},
			accountEnvironmentAttachment);

		accountEnvironmentAttachment.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the account environment attachments in the entity cache if it is enabled.
	 *
	 * @param accountEnvironmentAttachments the account environment attachments
	 */
	@Override
	public void cacheResult(
		List<AccountEnvironmentAttachment> accountEnvironmentAttachments) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (accountEnvironmentAttachments.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (AccountEnvironmentAttachment accountEnvironmentAttachment :
				accountEnvironmentAttachments) {

			if (entityCache.getResult(
					AccountEnvironmentAttachmentModelImpl.ENTITY_CACHE_ENABLED,
					AccountEnvironmentAttachmentImpl.class,
					accountEnvironmentAttachment.getPrimaryKey()) == null) {

				cacheResult(accountEnvironmentAttachment);
			}
			else {
				accountEnvironmentAttachment.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all account environment attachments.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AccountEnvironmentAttachmentImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the account environment attachment.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		AccountEnvironmentAttachment accountEnvironmentAttachment) {

		entityCache.removeResult(
			AccountEnvironmentAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountEnvironmentAttachmentImpl.class,
			accountEnvironmentAttachment.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(AccountEnvironmentAttachmentModelImpl)accountEnvironmentAttachment,
			true);
	}

	@Override
	public void clearCache(
		List<AccountEnvironmentAttachment> accountEnvironmentAttachments) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AccountEnvironmentAttachment accountEnvironmentAttachment :
				accountEnvironmentAttachments) {

			entityCache.removeResult(
				AccountEnvironmentAttachmentModelImpl.ENTITY_CACHE_ENABLED,
				AccountEnvironmentAttachmentImpl.class,
				accountEnvironmentAttachment.getPrimaryKey());

			clearUniqueFindersCache(
				(AccountEnvironmentAttachmentModelImpl)
					accountEnvironmentAttachment,
				true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				AccountEnvironmentAttachmentModelImpl.ENTITY_CACHE_ENABLED,
				AccountEnvironmentAttachmentImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AccountEnvironmentAttachmentModelImpl
			accountEnvironmentAttachmentModelImpl) {

		Object[] args = new Object[] {
			accountEnvironmentAttachmentModelImpl.getAccountEnvironmentId(),
			accountEnvironmentAttachmentModelImpl.getFileName()
		};

		finderCache.putResult(
			_finderPathCountByAEI_FN, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByAEI_FN, args,
			accountEnvironmentAttachmentModelImpl, false);

		args = new Object[] {
			accountEnvironmentAttachmentModelImpl.getAccountEnvironmentId(),
			accountEnvironmentAttachmentModelImpl.getType()
		};

		finderCache.putResult(
			_finderPathCountByAEI_T, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByAEI_T, args,
			accountEnvironmentAttachmentModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		AccountEnvironmentAttachmentModelImpl
			accountEnvironmentAttachmentModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				accountEnvironmentAttachmentModelImpl.getAccountEnvironmentId(),
				accountEnvironmentAttachmentModelImpl.getFileName()
			};

			finderCache.removeResult(_finderPathCountByAEI_FN, args);
			finderCache.removeResult(_finderPathFetchByAEI_FN, args);
		}

		if ((accountEnvironmentAttachmentModelImpl.getColumnBitmask() &
			 _finderPathFetchByAEI_FN.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				accountEnvironmentAttachmentModelImpl.
					getOriginalAccountEnvironmentId(),
				accountEnvironmentAttachmentModelImpl.getOriginalFileName()
			};

			finderCache.removeResult(_finderPathCountByAEI_FN, args);
			finderCache.removeResult(_finderPathFetchByAEI_FN, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				accountEnvironmentAttachmentModelImpl.getAccountEnvironmentId(),
				accountEnvironmentAttachmentModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByAEI_T, args);
			finderCache.removeResult(_finderPathFetchByAEI_T, args);
		}

		if ((accountEnvironmentAttachmentModelImpl.getColumnBitmask() &
			 _finderPathFetchByAEI_T.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				accountEnvironmentAttachmentModelImpl.
					getOriginalAccountEnvironmentId(),
				accountEnvironmentAttachmentModelImpl.getOriginalType()
			};

			finderCache.removeResult(_finderPathCountByAEI_T, args);
			finderCache.removeResult(_finderPathFetchByAEI_T, args);
		}
	}

	/**
	 * Creates a new account environment attachment with the primary key. Does not add the account environment attachment to the database.
	 *
	 * @param accountEnvironmentAttachmentId the primary key for the new account environment attachment
	 * @return the new account environment attachment
	 */
	@Override
	public AccountEnvironmentAttachment create(
		long accountEnvironmentAttachmentId) {

		AccountEnvironmentAttachment accountEnvironmentAttachment =
			new AccountEnvironmentAttachmentImpl();

		accountEnvironmentAttachment.setNew(true);
		accountEnvironmentAttachment.setPrimaryKey(
			accountEnvironmentAttachmentId);

		return accountEnvironmentAttachment;
	}

	/**
	 * Removes the account environment attachment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountEnvironmentAttachmentId the primary key of the account environment attachment
	 * @return the account environment attachment that was removed
	 * @throws NoSuchAccountEnvironmentAttachmentException if a account environment attachment with the primary key could not be found
	 */
	@Override
	public AccountEnvironmentAttachment remove(
			long accountEnvironmentAttachmentId)
		throws NoSuchAccountEnvironmentAttachmentException {

		return remove((Serializable)accountEnvironmentAttachmentId);
	}

	/**
	 * Removes the account environment attachment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the account environment attachment
	 * @return the account environment attachment that was removed
	 * @throws NoSuchAccountEnvironmentAttachmentException if a account environment attachment with the primary key could not be found
	 */
	@Override
	public AccountEnvironmentAttachment remove(Serializable primaryKey)
		throws NoSuchAccountEnvironmentAttachmentException {

		Session session = null;

		try {
			session = openSession();

			AccountEnvironmentAttachment accountEnvironmentAttachment =
				(AccountEnvironmentAttachment)session.get(
					AccountEnvironmentAttachmentImpl.class, primaryKey);

			if (accountEnvironmentAttachment == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAccountEnvironmentAttachmentException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(accountEnvironmentAttachment);
		}
		catch (NoSuchAccountEnvironmentAttachmentException
					noSuchEntityException) {

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
	protected AccountEnvironmentAttachment removeImpl(
		AccountEnvironmentAttachment accountEnvironmentAttachment) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(accountEnvironmentAttachment)) {
				accountEnvironmentAttachment =
					(AccountEnvironmentAttachment)session.get(
						AccountEnvironmentAttachmentImpl.class,
						accountEnvironmentAttachment.getPrimaryKeyObj());
			}

			if (accountEnvironmentAttachment != null) {
				session.delete(accountEnvironmentAttachment);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (accountEnvironmentAttachment != null) {
			clearCache(accountEnvironmentAttachment);
		}

		return accountEnvironmentAttachment;
	}

	@Override
	public AccountEnvironmentAttachment updateImpl(
		AccountEnvironmentAttachment accountEnvironmentAttachment) {

		boolean isNew = accountEnvironmentAttachment.isNew();

		if (!(accountEnvironmentAttachment instanceof
				AccountEnvironmentAttachmentModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					accountEnvironmentAttachment.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					accountEnvironmentAttachment);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in accountEnvironmentAttachment proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AccountEnvironmentAttachment implementation " +
					accountEnvironmentAttachment.getClass());
		}

		AccountEnvironmentAttachmentModelImpl
			accountEnvironmentAttachmentModelImpl =
				(AccountEnvironmentAttachmentModelImpl)
					accountEnvironmentAttachment;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (accountEnvironmentAttachment.getCreateDate() == null)) {
			if (serviceContext == null) {
				accountEnvironmentAttachment.setCreateDate(date);
			}
			else {
				accountEnvironmentAttachment.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!accountEnvironmentAttachmentModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				accountEnvironmentAttachment.setModifiedDate(date);
			}
			else {
				accountEnvironmentAttachment.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(accountEnvironmentAttachment);

				accountEnvironmentAttachment.setNew(false);
			}
			else {
				accountEnvironmentAttachment =
					(AccountEnvironmentAttachment)session.merge(
						accountEnvironmentAttachment);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!AccountEnvironmentAttachmentModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				accountEnvironmentAttachmentModelImpl.getAccountEnvironmentId()
			};

			finderCache.removeResult(
				_finderPathCountByAccountEnvironmentId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByAccountEnvironmentId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((accountEnvironmentAttachmentModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByAccountEnvironmentId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					accountEnvironmentAttachmentModelImpl.
						getOriginalAccountEnvironmentId()
				};

				finderCache.removeResult(
					_finderPathCountByAccountEnvironmentId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAccountEnvironmentId,
					args);

				args = new Object[] {
					accountEnvironmentAttachmentModelImpl.
						getAccountEnvironmentId()
				};

				finderCache.removeResult(
					_finderPathCountByAccountEnvironmentId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAccountEnvironmentId,
					args);
			}
		}

		entityCache.putResult(
			AccountEnvironmentAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountEnvironmentAttachmentImpl.class,
			accountEnvironmentAttachment.getPrimaryKey(),
			accountEnvironmentAttachment, false);

		clearUniqueFindersCache(accountEnvironmentAttachmentModelImpl, false);
		cacheUniqueFindersCache(accountEnvironmentAttachmentModelImpl);

		accountEnvironmentAttachment.resetOriginalValues();

		return accountEnvironmentAttachment;
	}

	/**
	 * Returns the account environment attachment with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the account environment attachment
	 * @return the account environment attachment
	 * @throws NoSuchAccountEnvironmentAttachmentException if a account environment attachment with the primary key could not be found
	 */
	@Override
	public AccountEnvironmentAttachment findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchAccountEnvironmentAttachmentException {

		AccountEnvironmentAttachment accountEnvironmentAttachment =
			fetchByPrimaryKey(primaryKey);

		if (accountEnvironmentAttachment == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAccountEnvironmentAttachmentException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return accountEnvironmentAttachment;
	}

	/**
	 * Returns the account environment attachment with the primary key or throws a <code>NoSuchAccountEnvironmentAttachmentException</code> if it could not be found.
	 *
	 * @param accountEnvironmentAttachmentId the primary key of the account environment attachment
	 * @return the account environment attachment
	 * @throws NoSuchAccountEnvironmentAttachmentException if a account environment attachment with the primary key could not be found
	 */
	@Override
	public AccountEnvironmentAttachment findByPrimaryKey(
			long accountEnvironmentAttachmentId)
		throws NoSuchAccountEnvironmentAttachmentException {

		return findByPrimaryKey((Serializable)accountEnvironmentAttachmentId);
	}

	/**
	 * Returns the account environment attachment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the account environment attachment
	 * @return the account environment attachment, or <code>null</code> if a account environment attachment with the primary key could not be found
	 */
	@Override
	public AccountEnvironmentAttachment fetchByPrimaryKey(
		Serializable primaryKey) {

		Serializable serializable = entityCache.getResult(
			AccountEnvironmentAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountEnvironmentAttachmentImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		AccountEnvironmentAttachment accountEnvironmentAttachment =
			(AccountEnvironmentAttachment)serializable;

		if (accountEnvironmentAttachment == null) {
			Session session = null;

			try {
				session = openSession();

				accountEnvironmentAttachment =
					(AccountEnvironmentAttachment)session.get(
						AccountEnvironmentAttachmentImpl.class, primaryKey);

				if (accountEnvironmentAttachment != null) {
					cacheResult(accountEnvironmentAttachment);
				}
				else {
					entityCache.putResult(
						AccountEnvironmentAttachmentModelImpl.
							ENTITY_CACHE_ENABLED,
						AccountEnvironmentAttachmentImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					AccountEnvironmentAttachmentModelImpl.ENTITY_CACHE_ENABLED,
					AccountEnvironmentAttachmentImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return accountEnvironmentAttachment;
	}

	/**
	 * Returns the account environment attachment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param accountEnvironmentAttachmentId the primary key of the account environment attachment
	 * @return the account environment attachment, or <code>null</code> if a account environment attachment with the primary key could not be found
	 */
	@Override
	public AccountEnvironmentAttachment fetchByPrimaryKey(
		long accountEnvironmentAttachmentId) {

		return fetchByPrimaryKey((Serializable)accountEnvironmentAttachmentId);
	}

	@Override
	public Map<Serializable, AccountEnvironmentAttachment> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AccountEnvironmentAttachment> map =
			new HashMap<Serializable, AccountEnvironmentAttachment>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AccountEnvironmentAttachment accountEnvironmentAttachment =
				fetchByPrimaryKey(primaryKey);

			if (accountEnvironmentAttachment != null) {
				map.put(primaryKey, accountEnvironmentAttachment);
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
				AccountEnvironmentAttachmentModelImpl.ENTITY_CACHE_ENABLED,
				AccountEnvironmentAttachmentImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(
						primaryKey, (AccountEnvironmentAttachment)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_ACCOUNTENVIRONMENTATTACHMENT_WHERE_PKS_IN);

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

			for (AccountEnvironmentAttachment accountEnvironmentAttachment :
					(List<AccountEnvironmentAttachment>)query.list()) {

				map.put(
					accountEnvironmentAttachment.getPrimaryKeyObj(),
					accountEnvironmentAttachment);

				cacheResult(accountEnvironmentAttachment);

				uncachedPrimaryKeys.remove(
					accountEnvironmentAttachment.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					AccountEnvironmentAttachmentModelImpl.ENTITY_CACHE_ENABLED,
					AccountEnvironmentAttachmentImpl.class, primaryKey,
					nullModel);
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
	 * Returns all the account environment attachments.
	 *
	 * @return the account environment attachments
	 */
	@Override
	public List<AccountEnvironmentAttachment> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the account environment attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEnvironmentAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account environment attachments
	 * @param end the upper bound of the range of account environment attachments (not inclusive)
	 * @return the range of account environment attachments
	 */
	@Override
	public List<AccountEnvironmentAttachment> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the account environment attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEnvironmentAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account environment attachments
	 * @param end the upper bound of the range of account environment attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of account environment attachments
	 */
	@Override
	public List<AccountEnvironmentAttachment> findAll(
		int start, int end,
		OrderByComparator<AccountEnvironmentAttachment> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the account environment attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEnvironmentAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account environment attachments
	 * @param end the upper bound of the range of account environment attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of account environment attachments
	 */
	@Override
	public List<AccountEnvironmentAttachment> findAll(
		int start, int end,
		OrderByComparator<AccountEnvironmentAttachment> orderByComparator,
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

		List<AccountEnvironmentAttachment> list = null;

		if (useFinderCache) {
			list = (List<AccountEnvironmentAttachment>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ACCOUNTENVIRONMENTATTACHMENT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ACCOUNTENVIRONMENTATTACHMENT;

				sql = sql.concat(
					AccountEnvironmentAttachmentModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AccountEnvironmentAttachment>)QueryUtil.list(
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
	 * Removes all the account environment attachments from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AccountEnvironmentAttachment accountEnvironmentAttachment :
				findAll()) {

			remove(accountEnvironmentAttachment);
		}
	}

	/**
	 * Returns the number of account environment attachments.
	 *
	 * @return the number of account environment attachments
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
					_SQL_COUNT_ACCOUNTENVIRONMENTATTACHMENT);

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
		return AccountEnvironmentAttachmentModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the account environment attachment persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			AccountEnvironmentAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountEnvironmentAttachmentModelImpl.FINDER_CACHE_ENABLED,
			AccountEnvironmentAttachmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			AccountEnvironmentAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountEnvironmentAttachmentModelImpl.FINDER_CACHE_ENABLED,
			AccountEnvironmentAttachmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			AccountEnvironmentAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountEnvironmentAttachmentModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByAccountEnvironmentId = new FinderPath(
			AccountEnvironmentAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountEnvironmentAttachmentModelImpl.FINDER_CACHE_ENABLED,
			AccountEnvironmentAttachmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByAccountEnvironmentId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByAccountEnvironmentId = new FinderPath(
			AccountEnvironmentAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountEnvironmentAttachmentModelImpl.FINDER_CACHE_ENABLED,
			AccountEnvironmentAttachmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByAccountEnvironmentId", new String[] {Long.class.getName()},
			AccountEnvironmentAttachmentModelImpl.
				ACCOUNTENVIRONMENTID_COLUMN_BITMASK);

		_finderPathCountByAccountEnvironmentId = new FinderPath(
			AccountEnvironmentAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountEnvironmentAttachmentModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAccountEnvironmentId", new String[] {Long.class.getName()});

		_finderPathFetchByAEI_FN = new FinderPath(
			AccountEnvironmentAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountEnvironmentAttachmentModelImpl.FINDER_CACHE_ENABLED,
			AccountEnvironmentAttachmentImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByAEI_FN",
			new String[] {Long.class.getName(), String.class.getName()},
			AccountEnvironmentAttachmentModelImpl.
				ACCOUNTENVIRONMENTID_COLUMN_BITMASK |
			AccountEnvironmentAttachmentModelImpl.FILENAME_COLUMN_BITMASK);

		_finderPathCountByAEI_FN = new FinderPath(
			AccountEnvironmentAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountEnvironmentAttachmentModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAEI_FN",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathFetchByAEI_T = new FinderPath(
			AccountEnvironmentAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountEnvironmentAttachmentModelImpl.FINDER_CACHE_ENABLED,
			AccountEnvironmentAttachmentImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByAEI_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			AccountEnvironmentAttachmentModelImpl.
				ACCOUNTENVIRONMENTID_COLUMN_BITMASK |
			AccountEnvironmentAttachmentModelImpl.TYPE_COLUMN_BITMASK);

		_finderPathCountByAEI_T = new FinderPath(
			AccountEnvironmentAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			AccountEnvironmentAttachmentModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAEI_T",
			new String[] {Long.class.getName(), Integer.class.getName()});

		AccountEnvironmentAttachmentUtil.setPersistence(this);
	}

	public void destroy() {
		AccountEnvironmentAttachmentUtil.setPersistence(null);

		entityCache.removeCache(
			AccountEnvironmentAttachmentImpl.class.getName());

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

	private static final String _SQL_SELECT_ACCOUNTENVIRONMENTATTACHMENT =
		"SELECT accountEnvironmentAttachment FROM AccountEnvironmentAttachment accountEnvironmentAttachment";

	private static final String
		_SQL_SELECT_ACCOUNTENVIRONMENTATTACHMENT_WHERE_PKS_IN =
			"SELECT accountEnvironmentAttachment FROM AccountEnvironmentAttachment accountEnvironmentAttachment WHERE accountEnvironmentAttachmentId IN (";

	private static final String _SQL_SELECT_ACCOUNTENVIRONMENTATTACHMENT_WHERE =
		"SELECT accountEnvironmentAttachment FROM AccountEnvironmentAttachment accountEnvironmentAttachment WHERE ";

	private static final String _SQL_COUNT_ACCOUNTENVIRONMENTATTACHMENT =
		"SELECT COUNT(accountEnvironmentAttachment) FROM AccountEnvironmentAttachment accountEnvironmentAttachment";

	private static final String _SQL_COUNT_ACCOUNTENVIRONMENTATTACHMENT_WHERE =
		"SELECT COUNT(accountEnvironmentAttachment) FROM AccountEnvironmentAttachment accountEnvironmentAttachment WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"accountEnvironmentAttachment.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AccountEnvironmentAttachment exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AccountEnvironmentAttachment exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AccountEnvironmentAttachmentPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type"});

}