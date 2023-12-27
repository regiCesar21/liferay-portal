/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.zendesk.documentation.sync.service.persistence.impl;

import com.liferay.osb.customer.zendesk.documentation.sync.exception.NoSuchZendeskArticleAttachmentException;
import com.liferay.osb.customer.zendesk.documentation.sync.model.ZendeskArticleAttachment;
import com.liferay.osb.customer.zendesk.documentation.sync.model.impl.ZendeskArticleAttachmentImpl;
import com.liferay.osb.customer.zendesk.documentation.sync.model.impl.ZendeskArticleAttachmentModelImpl;
import com.liferay.osb.customer.zendesk.documentation.sync.service.persistence.ZendeskArticleAttachmentPersistence;
import com.liferay.osb.customer.zendesk.documentation.sync.service.persistence.ZendeskArticleAttachmentUtil;
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
 * The persistence implementation for the zendesk article attachment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ZendeskArticleAttachmentPersistenceImpl
	extends BasePersistenceImpl<ZendeskArticleAttachment>
	implements ZendeskArticleAttachmentPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ZendeskArticleAttachmentUtil</code> to access the zendesk article attachment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ZendeskArticleAttachmentImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByZendeskArticleId;
	private FinderPath _finderPathWithoutPaginationFindByZendeskArticleId;
	private FinderPath _finderPathCountByZendeskArticleId;

	/**
	 * Returns all the zendesk article attachments where zendeskArticleId = &#63;.
	 *
	 * @param zendeskArticleId the zendesk article ID
	 * @return the matching zendesk article attachments
	 */
	@Override
	public List<ZendeskArticleAttachment> findByZendeskArticleId(
		long zendeskArticleId) {

		return findByZendeskArticleId(
			zendeskArticleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the zendesk article attachments where zendeskArticleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ZendeskArticleAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param zendeskArticleId the zendesk article ID
	 * @param start the lower bound of the range of zendesk article attachments
	 * @param end the upper bound of the range of zendesk article attachments (not inclusive)
	 * @return the range of matching zendesk article attachments
	 */
	@Override
	public List<ZendeskArticleAttachment> findByZendeskArticleId(
		long zendeskArticleId, int start, int end) {

		return findByZendeskArticleId(zendeskArticleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the zendesk article attachments where zendeskArticleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ZendeskArticleAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param zendeskArticleId the zendesk article ID
	 * @param start the lower bound of the range of zendesk article attachments
	 * @param end the upper bound of the range of zendesk article attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching zendesk article attachments
	 */
	@Override
	public List<ZendeskArticleAttachment> findByZendeskArticleId(
		long zendeskArticleId, int start, int end,
		OrderByComparator<ZendeskArticleAttachment> orderByComparator) {

		return findByZendeskArticleId(
			zendeskArticleId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the zendesk article attachments where zendeskArticleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ZendeskArticleAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param zendeskArticleId the zendesk article ID
	 * @param start the lower bound of the range of zendesk article attachments
	 * @param end the upper bound of the range of zendesk article attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching zendesk article attachments
	 */
	@Override
	public List<ZendeskArticleAttachment> findByZendeskArticleId(
		long zendeskArticleId, int start, int end,
		OrderByComparator<ZendeskArticleAttachment> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByZendeskArticleId;
				finderArgs = new Object[] {zendeskArticleId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByZendeskArticleId;
			finderArgs = new Object[] {
				zendeskArticleId, start, end, orderByComparator
			};
		}

		List<ZendeskArticleAttachment> list = null;

		if (useFinderCache) {
			list = (List<ZendeskArticleAttachment>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ZendeskArticleAttachment zendeskArticleAttachment : list) {
					if (zendeskArticleId !=
							zendeskArticleAttachment.getZendeskArticleId()) {

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

			sb.append(_SQL_SELECT_ZENDESKARTICLEATTACHMENT_WHERE);

			sb.append(_FINDER_COLUMN_ZENDESKARTICLEID_ZENDESKARTICLEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ZendeskArticleAttachmentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(zendeskArticleId);

				list = (List<ZendeskArticleAttachment>)QueryUtil.list(
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
	 * Returns the first zendesk article attachment in the ordered set where zendeskArticleId = &#63;.
	 *
	 * @param zendeskArticleId the zendesk article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching zendesk article attachment
	 * @throws NoSuchZendeskArticleAttachmentException if a matching zendesk article attachment could not be found
	 */
	@Override
	public ZendeskArticleAttachment findByZendeskArticleId_First(
			long zendeskArticleId,
			OrderByComparator<ZendeskArticleAttachment> orderByComparator)
		throws NoSuchZendeskArticleAttachmentException {

		ZendeskArticleAttachment zendeskArticleAttachment =
			fetchByZendeskArticleId_First(zendeskArticleId, orderByComparator);

		if (zendeskArticleAttachment != null) {
			return zendeskArticleAttachment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("zendeskArticleId=");
		sb.append(zendeskArticleId);

		sb.append("}");

		throw new NoSuchZendeskArticleAttachmentException(sb.toString());
	}

	/**
	 * Returns the first zendesk article attachment in the ordered set where zendeskArticleId = &#63;.
	 *
	 * @param zendeskArticleId the zendesk article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching zendesk article attachment, or <code>null</code> if a matching zendesk article attachment could not be found
	 */
	@Override
	public ZendeskArticleAttachment fetchByZendeskArticleId_First(
		long zendeskArticleId,
		OrderByComparator<ZendeskArticleAttachment> orderByComparator) {

		List<ZendeskArticleAttachment> list = findByZendeskArticleId(
			zendeskArticleId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last zendesk article attachment in the ordered set where zendeskArticleId = &#63;.
	 *
	 * @param zendeskArticleId the zendesk article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching zendesk article attachment
	 * @throws NoSuchZendeskArticleAttachmentException if a matching zendesk article attachment could not be found
	 */
	@Override
	public ZendeskArticleAttachment findByZendeskArticleId_Last(
			long zendeskArticleId,
			OrderByComparator<ZendeskArticleAttachment> orderByComparator)
		throws NoSuchZendeskArticleAttachmentException {

		ZendeskArticleAttachment zendeskArticleAttachment =
			fetchByZendeskArticleId_Last(zendeskArticleId, orderByComparator);

		if (zendeskArticleAttachment != null) {
			return zendeskArticleAttachment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("zendeskArticleId=");
		sb.append(zendeskArticleId);

		sb.append("}");

		throw new NoSuchZendeskArticleAttachmentException(sb.toString());
	}

	/**
	 * Returns the last zendesk article attachment in the ordered set where zendeskArticleId = &#63;.
	 *
	 * @param zendeskArticleId the zendesk article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching zendesk article attachment, or <code>null</code> if a matching zendesk article attachment could not be found
	 */
	@Override
	public ZendeskArticleAttachment fetchByZendeskArticleId_Last(
		long zendeskArticleId,
		OrderByComparator<ZendeskArticleAttachment> orderByComparator) {

		int count = countByZendeskArticleId(zendeskArticleId);

		if (count == 0) {
			return null;
		}

		List<ZendeskArticleAttachment> list = findByZendeskArticleId(
			zendeskArticleId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the zendesk article attachments before and after the current zendesk article attachment in the ordered set where zendeskArticleId = &#63;.
	 *
	 * @param zendeskArticleAttachmentId the primary key of the current zendesk article attachment
	 * @param zendeskArticleId the zendesk article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next zendesk article attachment
	 * @throws NoSuchZendeskArticleAttachmentException if a zendesk article attachment with the primary key could not be found
	 */
	@Override
	public ZendeskArticleAttachment[] findByZendeskArticleId_PrevAndNext(
			long zendeskArticleAttachmentId, long zendeskArticleId,
			OrderByComparator<ZendeskArticleAttachment> orderByComparator)
		throws NoSuchZendeskArticleAttachmentException {

		ZendeskArticleAttachment zendeskArticleAttachment = findByPrimaryKey(
			zendeskArticleAttachmentId);

		Session session = null;

		try {
			session = openSession();

			ZendeskArticleAttachment[] array =
				new ZendeskArticleAttachmentImpl[3];

			array[0] = getByZendeskArticleId_PrevAndNext(
				session, zendeskArticleAttachment, zendeskArticleId,
				orderByComparator, true);

			array[1] = zendeskArticleAttachment;

			array[2] = getByZendeskArticleId_PrevAndNext(
				session, zendeskArticleAttachment, zendeskArticleId,
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

	protected ZendeskArticleAttachment getByZendeskArticleId_PrevAndNext(
		Session session, ZendeskArticleAttachment zendeskArticleAttachment,
		long zendeskArticleId,
		OrderByComparator<ZendeskArticleAttachment> orderByComparator,
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

		sb.append(_SQL_SELECT_ZENDESKARTICLEATTACHMENT_WHERE);

		sb.append(_FINDER_COLUMN_ZENDESKARTICLEID_ZENDESKARTICLEID_2);

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
			sb.append(ZendeskArticleAttachmentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(zendeskArticleId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						zendeskArticleAttachment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ZendeskArticleAttachment> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the zendesk article attachments where zendeskArticleId = &#63; from the database.
	 *
	 * @param zendeskArticleId the zendesk article ID
	 */
	@Override
	public void removeByZendeskArticleId(long zendeskArticleId) {
		for (ZendeskArticleAttachment zendeskArticleAttachment :
				findByZendeskArticleId(
					zendeskArticleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(zendeskArticleAttachment);
		}
	}

	/**
	 * Returns the number of zendesk article attachments where zendeskArticleId = &#63;.
	 *
	 * @param zendeskArticleId the zendesk article ID
	 * @return the number of matching zendesk article attachments
	 */
	@Override
	public int countByZendeskArticleId(long zendeskArticleId) {
		FinderPath finderPath = _finderPathCountByZendeskArticleId;

		Object[] finderArgs = new Object[] {zendeskArticleId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ZENDESKARTICLEATTACHMENT_WHERE);

			sb.append(_FINDER_COLUMN_ZENDESKARTICLEID_ZENDESKARTICLEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(zendeskArticleId);

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
		_FINDER_COLUMN_ZENDESKARTICLEID_ZENDESKARTICLEID_2 =
			"zendeskArticleAttachment.zendeskArticleId = ?";

	private FinderPath _finderPathFetchByZAI_FP;
	private FinderPath _finderPathCountByZAI_FP;

	/**
	 * Returns the zendesk article attachment where zendeskArticleId = &#63; and filePath = &#63; or throws a <code>NoSuchZendeskArticleAttachmentException</code> if it could not be found.
	 *
	 * @param zendeskArticleId the zendesk article ID
	 * @param filePath the file path
	 * @return the matching zendesk article attachment
	 * @throws NoSuchZendeskArticleAttachmentException if a matching zendesk article attachment could not be found
	 */
	@Override
	public ZendeskArticleAttachment findByZAI_FP(
			long zendeskArticleId, String filePath)
		throws NoSuchZendeskArticleAttachmentException {

		ZendeskArticleAttachment zendeskArticleAttachment = fetchByZAI_FP(
			zendeskArticleId, filePath);

		if (zendeskArticleAttachment == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("zendeskArticleId=");
			sb.append(zendeskArticleId);

			sb.append(", filePath=");
			sb.append(filePath);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchZendeskArticleAttachmentException(sb.toString());
		}

		return zendeskArticleAttachment;
	}

	/**
	 * Returns the zendesk article attachment where zendeskArticleId = &#63; and filePath = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param zendeskArticleId the zendesk article ID
	 * @param filePath the file path
	 * @return the matching zendesk article attachment, or <code>null</code> if a matching zendesk article attachment could not be found
	 */
	@Override
	public ZendeskArticleAttachment fetchByZAI_FP(
		long zendeskArticleId, String filePath) {

		return fetchByZAI_FP(zendeskArticleId, filePath, true);
	}

	/**
	 * Returns the zendesk article attachment where zendeskArticleId = &#63; and filePath = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param zendeskArticleId the zendesk article ID
	 * @param filePath the file path
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching zendesk article attachment, or <code>null</code> if a matching zendesk article attachment could not be found
	 */
	@Override
	public ZendeskArticleAttachment fetchByZAI_FP(
		long zendeskArticleId, String filePath, boolean useFinderCache) {

		filePath = Objects.toString(filePath, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {zendeskArticleId, filePath};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByZAI_FP, finderArgs, this);
		}

		if (result instanceof ZendeskArticleAttachment) {
			ZendeskArticleAttachment zendeskArticleAttachment =
				(ZendeskArticleAttachment)result;

			if ((zendeskArticleId !=
					zendeskArticleAttachment.getZendeskArticleId()) ||
				!Objects.equals(
					filePath, zendeskArticleAttachment.getFilePath())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_ZENDESKARTICLEATTACHMENT_WHERE);

			sb.append(_FINDER_COLUMN_ZAI_FP_ZENDESKARTICLEID_2);

			boolean bindFilePath = false;

			if (filePath.isEmpty()) {
				sb.append(_FINDER_COLUMN_ZAI_FP_FILEPATH_3);
			}
			else {
				bindFilePath = true;

				sb.append(_FINDER_COLUMN_ZAI_FP_FILEPATH_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(zendeskArticleId);

				if (bindFilePath) {
					queryPos.add(filePath);
				}

				List<ZendeskArticleAttachment> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByZAI_FP, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									zendeskArticleId, filePath
								};
							}

							_log.warn(
								"ZendeskArticleAttachmentPersistenceImpl.fetchByZAI_FP(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					ZendeskArticleAttachment zendeskArticleAttachment =
						list.get(0);

					result = zendeskArticleAttachment;

					cacheResult(zendeskArticleAttachment);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByZAI_FP, finderArgs);
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
			return (ZendeskArticleAttachment)result;
		}
	}

	/**
	 * Removes the zendesk article attachment where zendeskArticleId = &#63; and filePath = &#63; from the database.
	 *
	 * @param zendeskArticleId the zendesk article ID
	 * @param filePath the file path
	 * @return the zendesk article attachment that was removed
	 */
	@Override
	public ZendeskArticleAttachment removeByZAI_FP(
			long zendeskArticleId, String filePath)
		throws NoSuchZendeskArticleAttachmentException {

		ZendeskArticleAttachment zendeskArticleAttachment = findByZAI_FP(
			zendeskArticleId, filePath);

		return remove(zendeskArticleAttachment);
	}

	/**
	 * Returns the number of zendesk article attachments where zendeskArticleId = &#63; and filePath = &#63;.
	 *
	 * @param zendeskArticleId the zendesk article ID
	 * @param filePath the file path
	 * @return the number of matching zendesk article attachments
	 */
	@Override
	public int countByZAI_FP(long zendeskArticleId, String filePath) {
		filePath = Objects.toString(filePath, "");

		FinderPath finderPath = _finderPathCountByZAI_FP;

		Object[] finderArgs = new Object[] {zendeskArticleId, filePath};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ZENDESKARTICLEATTACHMENT_WHERE);

			sb.append(_FINDER_COLUMN_ZAI_FP_ZENDESKARTICLEID_2);

			boolean bindFilePath = false;

			if (filePath.isEmpty()) {
				sb.append(_FINDER_COLUMN_ZAI_FP_FILEPATH_3);
			}
			else {
				bindFilePath = true;

				sb.append(_FINDER_COLUMN_ZAI_FP_FILEPATH_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(zendeskArticleId);

				if (bindFilePath) {
					queryPos.add(filePath);
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

	private static final String _FINDER_COLUMN_ZAI_FP_ZENDESKARTICLEID_2 =
		"zendeskArticleAttachment.zendeskArticleId = ? AND ";

	private static final String _FINDER_COLUMN_ZAI_FP_FILEPATH_2 =
		"zendeskArticleAttachment.filePath = ?";

	private static final String _FINDER_COLUMN_ZAI_FP_FILEPATH_3 =
		"(zendeskArticleAttachment.filePath IS NULL OR zendeskArticleAttachment.filePath = '')";

	public ZendeskArticleAttachmentPersistenceImpl() {
		setModelClass(ZendeskArticleAttachment.class);
	}

	/**
	 * Caches the zendesk article attachment in the entity cache if it is enabled.
	 *
	 * @param zendeskArticleAttachment the zendesk article attachment
	 */
	@Override
	public void cacheResult(ZendeskArticleAttachment zendeskArticleAttachment) {
		entityCache.putResult(
			ZendeskArticleAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleAttachmentImpl.class,
			zendeskArticleAttachment.getPrimaryKey(), zendeskArticleAttachment);

		finderCache.putResult(
			_finderPathFetchByZAI_FP,
			new Object[] {
				zendeskArticleAttachment.getZendeskArticleId(),
				zendeskArticleAttachment.getFilePath()
			},
			zendeskArticleAttachment);

		zendeskArticleAttachment.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the zendesk article attachments in the entity cache if it is enabled.
	 *
	 * @param zendeskArticleAttachments the zendesk article attachments
	 */
	@Override
	public void cacheResult(
		List<ZendeskArticleAttachment> zendeskArticleAttachments) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (zendeskArticleAttachments.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ZendeskArticleAttachment zendeskArticleAttachment :
				zendeskArticleAttachments) {

			if (entityCache.getResult(
					ZendeskArticleAttachmentModelImpl.ENTITY_CACHE_ENABLED,
					ZendeskArticleAttachmentImpl.class,
					zendeskArticleAttachment.getPrimaryKey()) == null) {

				cacheResult(zendeskArticleAttachment);
			}
			else {
				zendeskArticleAttachment.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all zendesk article attachments.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ZendeskArticleAttachmentImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the zendesk article attachment.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ZendeskArticleAttachment zendeskArticleAttachment) {
		entityCache.removeResult(
			ZendeskArticleAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleAttachmentImpl.class,
			zendeskArticleAttachment.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(ZendeskArticleAttachmentModelImpl)zendeskArticleAttachment, true);
	}

	@Override
	public void clearCache(
		List<ZendeskArticleAttachment> zendeskArticleAttachments) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ZendeskArticleAttachment zendeskArticleAttachment :
				zendeskArticleAttachments) {

			entityCache.removeResult(
				ZendeskArticleAttachmentModelImpl.ENTITY_CACHE_ENABLED,
				ZendeskArticleAttachmentImpl.class,
				zendeskArticleAttachment.getPrimaryKey());

			clearUniqueFindersCache(
				(ZendeskArticleAttachmentModelImpl)zendeskArticleAttachment,
				true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				ZendeskArticleAttachmentModelImpl.ENTITY_CACHE_ENABLED,
				ZendeskArticleAttachmentImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ZendeskArticleAttachmentModelImpl zendeskArticleAttachmentModelImpl) {

		Object[] args = new Object[] {
			zendeskArticleAttachmentModelImpl.getZendeskArticleId(),
			zendeskArticleAttachmentModelImpl.getFilePath()
		};

		finderCache.putResult(
			_finderPathCountByZAI_FP, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByZAI_FP, args, zendeskArticleAttachmentModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		ZendeskArticleAttachmentModelImpl zendeskArticleAttachmentModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				zendeskArticleAttachmentModelImpl.getZendeskArticleId(),
				zendeskArticleAttachmentModelImpl.getFilePath()
			};

			finderCache.removeResult(_finderPathCountByZAI_FP, args);
			finderCache.removeResult(_finderPathFetchByZAI_FP, args);
		}

		if ((zendeskArticleAttachmentModelImpl.getColumnBitmask() &
			 _finderPathFetchByZAI_FP.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				zendeskArticleAttachmentModelImpl.getOriginalZendeskArticleId(),
				zendeskArticleAttachmentModelImpl.getOriginalFilePath()
			};

			finderCache.removeResult(_finderPathCountByZAI_FP, args);
			finderCache.removeResult(_finderPathFetchByZAI_FP, args);
		}
	}

	/**
	 * Creates a new zendesk article attachment with the primary key. Does not add the zendesk article attachment to the database.
	 *
	 * @param zendeskArticleAttachmentId the primary key for the new zendesk article attachment
	 * @return the new zendesk article attachment
	 */
	@Override
	public ZendeskArticleAttachment create(long zendeskArticleAttachmentId) {
		ZendeskArticleAttachment zendeskArticleAttachment =
			new ZendeskArticleAttachmentImpl();

		zendeskArticleAttachment.setNew(true);
		zendeskArticleAttachment.setPrimaryKey(zendeskArticleAttachmentId);

		return zendeskArticleAttachment;
	}

	/**
	 * Removes the zendesk article attachment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param zendeskArticleAttachmentId the primary key of the zendesk article attachment
	 * @return the zendesk article attachment that was removed
	 * @throws NoSuchZendeskArticleAttachmentException if a zendesk article attachment with the primary key could not be found
	 */
	@Override
	public ZendeskArticleAttachment remove(long zendeskArticleAttachmentId)
		throws NoSuchZendeskArticleAttachmentException {

		return remove((Serializable)zendeskArticleAttachmentId);
	}

	/**
	 * Removes the zendesk article attachment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the zendesk article attachment
	 * @return the zendesk article attachment that was removed
	 * @throws NoSuchZendeskArticleAttachmentException if a zendesk article attachment with the primary key could not be found
	 */
	@Override
	public ZendeskArticleAttachment remove(Serializable primaryKey)
		throws NoSuchZendeskArticleAttachmentException {

		Session session = null;

		try {
			session = openSession();

			ZendeskArticleAttachment zendeskArticleAttachment =
				(ZendeskArticleAttachment)session.get(
					ZendeskArticleAttachmentImpl.class, primaryKey);

			if (zendeskArticleAttachment == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchZendeskArticleAttachmentException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(zendeskArticleAttachment);
		}
		catch (NoSuchZendeskArticleAttachmentException noSuchEntityException) {
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
	protected ZendeskArticleAttachment removeImpl(
		ZendeskArticleAttachment zendeskArticleAttachment) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(zendeskArticleAttachment)) {
				zendeskArticleAttachment =
					(ZendeskArticleAttachment)session.get(
						ZendeskArticleAttachmentImpl.class,
						zendeskArticleAttachment.getPrimaryKeyObj());
			}

			if (zendeskArticleAttachment != null) {
				session.delete(zendeskArticleAttachment);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (zendeskArticleAttachment != null) {
			clearCache(zendeskArticleAttachment);
		}

		return zendeskArticleAttachment;
	}

	@Override
	public ZendeskArticleAttachment updateImpl(
		ZendeskArticleAttachment zendeskArticleAttachment) {

		boolean isNew = zendeskArticleAttachment.isNew();

		if (!(zendeskArticleAttachment instanceof
				ZendeskArticleAttachmentModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(zendeskArticleAttachment.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					zendeskArticleAttachment);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in zendeskArticleAttachment proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ZendeskArticleAttachment implementation " +
					zendeskArticleAttachment.getClass());
		}

		ZendeskArticleAttachmentModelImpl zendeskArticleAttachmentModelImpl =
			(ZendeskArticleAttachmentModelImpl)zendeskArticleAttachment;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(zendeskArticleAttachment);

				zendeskArticleAttachment.setNew(false);
			}
			else {
				zendeskArticleAttachment =
					(ZendeskArticleAttachment)session.merge(
						zendeskArticleAttachment);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!ZendeskArticleAttachmentModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				zendeskArticleAttachmentModelImpl.getZendeskArticleId()
			};

			finderCache.removeResult(_finderPathCountByZendeskArticleId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByZendeskArticleId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((zendeskArticleAttachmentModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByZendeskArticleId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					zendeskArticleAttachmentModelImpl.
						getOriginalZendeskArticleId()
				};

				finderCache.removeResult(
					_finderPathCountByZendeskArticleId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByZendeskArticleId, args);

				args = new Object[] {
					zendeskArticleAttachmentModelImpl.getZendeskArticleId()
				};

				finderCache.removeResult(
					_finderPathCountByZendeskArticleId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByZendeskArticleId, args);
			}
		}

		entityCache.putResult(
			ZendeskArticleAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleAttachmentImpl.class,
			zendeskArticleAttachment.getPrimaryKey(), zendeskArticleAttachment,
			false);

		clearUniqueFindersCache(zendeskArticleAttachmentModelImpl, false);
		cacheUniqueFindersCache(zendeskArticleAttachmentModelImpl);

		zendeskArticleAttachment.resetOriginalValues();

		return zendeskArticleAttachment;
	}

	/**
	 * Returns the zendesk article attachment with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the zendesk article attachment
	 * @return the zendesk article attachment
	 * @throws NoSuchZendeskArticleAttachmentException if a zendesk article attachment with the primary key could not be found
	 */
	@Override
	public ZendeskArticleAttachment findByPrimaryKey(Serializable primaryKey)
		throws NoSuchZendeskArticleAttachmentException {

		ZendeskArticleAttachment zendeskArticleAttachment = fetchByPrimaryKey(
			primaryKey);

		if (zendeskArticleAttachment == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchZendeskArticleAttachmentException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return zendeskArticleAttachment;
	}

	/**
	 * Returns the zendesk article attachment with the primary key or throws a <code>NoSuchZendeskArticleAttachmentException</code> if it could not be found.
	 *
	 * @param zendeskArticleAttachmentId the primary key of the zendesk article attachment
	 * @return the zendesk article attachment
	 * @throws NoSuchZendeskArticleAttachmentException if a zendesk article attachment with the primary key could not be found
	 */
	@Override
	public ZendeskArticleAttachment findByPrimaryKey(
			long zendeskArticleAttachmentId)
		throws NoSuchZendeskArticleAttachmentException {

		return findByPrimaryKey((Serializable)zendeskArticleAttachmentId);
	}

	/**
	 * Returns the zendesk article attachment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the zendesk article attachment
	 * @return the zendesk article attachment, or <code>null</code> if a zendesk article attachment with the primary key could not be found
	 */
	@Override
	public ZendeskArticleAttachment fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			ZendeskArticleAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleAttachmentImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		ZendeskArticleAttachment zendeskArticleAttachment =
			(ZendeskArticleAttachment)serializable;

		if (zendeskArticleAttachment == null) {
			Session session = null;

			try {
				session = openSession();

				zendeskArticleAttachment =
					(ZendeskArticleAttachment)session.get(
						ZendeskArticleAttachmentImpl.class, primaryKey);

				if (zendeskArticleAttachment != null) {
					cacheResult(zendeskArticleAttachment);
				}
				else {
					entityCache.putResult(
						ZendeskArticleAttachmentModelImpl.ENTITY_CACHE_ENABLED,
						ZendeskArticleAttachmentImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					ZendeskArticleAttachmentModelImpl.ENTITY_CACHE_ENABLED,
					ZendeskArticleAttachmentImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return zendeskArticleAttachment;
	}

	/**
	 * Returns the zendesk article attachment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param zendeskArticleAttachmentId the primary key of the zendesk article attachment
	 * @return the zendesk article attachment, or <code>null</code> if a zendesk article attachment with the primary key could not be found
	 */
	@Override
	public ZendeskArticleAttachment fetchByPrimaryKey(
		long zendeskArticleAttachmentId) {

		return fetchByPrimaryKey((Serializable)zendeskArticleAttachmentId);
	}

	@Override
	public Map<Serializable, ZendeskArticleAttachment> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ZendeskArticleAttachment> map =
			new HashMap<Serializable, ZendeskArticleAttachment>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ZendeskArticleAttachment zendeskArticleAttachment =
				fetchByPrimaryKey(primaryKey);

			if (zendeskArticleAttachment != null) {
				map.put(primaryKey, zendeskArticleAttachment);
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
				ZendeskArticleAttachmentModelImpl.ENTITY_CACHE_ENABLED,
				ZendeskArticleAttachmentImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (ZendeskArticleAttachment)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_ZENDESKARTICLEATTACHMENT_WHERE_PKS_IN);

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

			for (ZendeskArticleAttachment zendeskArticleAttachment :
					(List<ZendeskArticleAttachment>)query.list()) {

				map.put(
					zendeskArticleAttachment.getPrimaryKeyObj(),
					zendeskArticleAttachment);

				cacheResult(zendeskArticleAttachment);

				uncachedPrimaryKeys.remove(
					zendeskArticleAttachment.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					ZendeskArticleAttachmentModelImpl.ENTITY_CACHE_ENABLED,
					ZendeskArticleAttachmentImpl.class, primaryKey, nullModel);
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
	 * Returns all the zendesk article attachments.
	 *
	 * @return the zendesk article attachments
	 */
	@Override
	public List<ZendeskArticleAttachment> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the zendesk article attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ZendeskArticleAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of zendesk article attachments
	 * @param end the upper bound of the range of zendesk article attachments (not inclusive)
	 * @return the range of zendesk article attachments
	 */
	@Override
	public List<ZendeskArticleAttachment> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the zendesk article attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ZendeskArticleAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of zendesk article attachments
	 * @param end the upper bound of the range of zendesk article attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of zendesk article attachments
	 */
	@Override
	public List<ZendeskArticleAttachment> findAll(
		int start, int end,
		OrderByComparator<ZendeskArticleAttachment> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the zendesk article attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ZendeskArticleAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of zendesk article attachments
	 * @param end the upper bound of the range of zendesk article attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of zendesk article attachments
	 */
	@Override
	public List<ZendeskArticleAttachment> findAll(
		int start, int end,
		OrderByComparator<ZendeskArticleAttachment> orderByComparator,
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

		List<ZendeskArticleAttachment> list = null;

		if (useFinderCache) {
			list = (List<ZendeskArticleAttachment>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ZENDESKARTICLEATTACHMENT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ZENDESKARTICLEATTACHMENT;

				sql = sql.concat(
					ZendeskArticleAttachmentModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ZendeskArticleAttachment>)QueryUtil.list(
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
	 * Removes all the zendesk article attachments from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ZendeskArticleAttachment zendeskArticleAttachment : findAll()) {
			remove(zendeskArticleAttachment);
		}
	}

	/**
	 * Returns the number of zendesk article attachments.
	 *
	 * @return the number of zendesk article attachments
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
					_SQL_COUNT_ZENDESKARTICLEATTACHMENT);

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
		return ZendeskArticleAttachmentModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the zendesk article attachment persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			ZendeskArticleAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleAttachmentModelImpl.FINDER_CACHE_ENABLED,
			ZendeskArticleAttachmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			ZendeskArticleAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleAttachmentModelImpl.FINDER_CACHE_ENABLED,
			ZendeskArticleAttachmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			ZendeskArticleAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleAttachmentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByZendeskArticleId = new FinderPath(
			ZendeskArticleAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleAttachmentModelImpl.FINDER_CACHE_ENABLED,
			ZendeskArticleAttachmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByZendeskArticleId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByZendeskArticleId = new FinderPath(
			ZendeskArticleAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleAttachmentModelImpl.FINDER_CACHE_ENABLED,
			ZendeskArticleAttachmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByZendeskArticleId",
			new String[] {Long.class.getName()},
			ZendeskArticleAttachmentModelImpl.ZENDESKARTICLEID_COLUMN_BITMASK);

		_finderPathCountByZendeskArticleId = new FinderPath(
			ZendeskArticleAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleAttachmentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByZendeskArticleId", new String[] {Long.class.getName()});

		_finderPathFetchByZAI_FP = new FinderPath(
			ZendeskArticleAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleAttachmentModelImpl.FINDER_CACHE_ENABLED,
			ZendeskArticleAttachmentImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByZAI_FP",
			new String[] {Long.class.getName(), String.class.getName()},
			ZendeskArticleAttachmentModelImpl.ZENDESKARTICLEID_COLUMN_BITMASK |
			ZendeskArticleAttachmentModelImpl.FILEPATH_COLUMN_BITMASK);

		_finderPathCountByZAI_FP = new FinderPath(
			ZendeskArticleAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleAttachmentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByZAI_FP",
			new String[] {Long.class.getName(), String.class.getName()});

		ZendeskArticleAttachmentUtil.setPersistence(this);
	}

	public void destroy() {
		ZendeskArticleAttachmentUtil.setPersistence(null);

		entityCache.removeCache(ZendeskArticleAttachmentImpl.class.getName());

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

	private static final String _SQL_SELECT_ZENDESKARTICLEATTACHMENT =
		"SELECT zendeskArticleAttachment FROM ZendeskArticleAttachment zendeskArticleAttachment";

	private static final String
		_SQL_SELECT_ZENDESKARTICLEATTACHMENT_WHERE_PKS_IN =
			"SELECT zendeskArticleAttachment FROM ZendeskArticleAttachment zendeskArticleAttachment WHERE zendeskArticleAttachmentId IN (";

	private static final String _SQL_SELECT_ZENDESKARTICLEATTACHMENT_WHERE =
		"SELECT zendeskArticleAttachment FROM ZendeskArticleAttachment zendeskArticleAttachment WHERE ";

	private static final String _SQL_COUNT_ZENDESKARTICLEATTACHMENT =
		"SELECT COUNT(zendeskArticleAttachment) FROM ZendeskArticleAttachment zendeskArticleAttachment";

	private static final String _SQL_COUNT_ZENDESKARTICLEATTACHMENT_WHERE =
		"SELECT COUNT(zendeskArticleAttachment) FROM ZendeskArticleAttachment zendeskArticleAttachment WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"zendeskArticleAttachment.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ZendeskArticleAttachment exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ZendeskArticleAttachment exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ZendeskArticleAttachmentPersistenceImpl.class);

}