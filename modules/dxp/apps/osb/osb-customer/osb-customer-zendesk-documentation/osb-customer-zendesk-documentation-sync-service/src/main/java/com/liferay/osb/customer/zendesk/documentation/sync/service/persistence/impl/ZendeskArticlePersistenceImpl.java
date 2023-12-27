/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.zendesk.documentation.sync.service.persistence.impl;

import com.liferay.osb.customer.zendesk.documentation.sync.exception.NoSuchZendeskArticleException;
import com.liferay.osb.customer.zendesk.documentation.sync.model.ZendeskArticle;
import com.liferay.osb.customer.zendesk.documentation.sync.model.impl.ZendeskArticleImpl;
import com.liferay.osb.customer.zendesk.documentation.sync.model.impl.ZendeskArticleModelImpl;
import com.liferay.osb.customer.zendesk.documentation.sync.service.persistence.ZendeskArticlePersistence;
import com.liferay.osb.customer.zendesk.documentation.sync.service.persistence.ZendeskArticleUtil;
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
 * The persistence implementation for the zendesk article service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ZendeskArticlePersistenceImpl
	extends BasePersistenceImpl<ZendeskArticle>
	implements ZendeskArticlePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ZendeskArticleUtil</code> to access the zendesk article persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ZendeskArticleImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByZendeskCategoryId;
	private FinderPath _finderPathWithoutPaginationFindByZendeskCategoryId;
	private FinderPath _finderPathCountByZendeskCategoryId;

	/**
	 * Returns all the zendesk articles where zendeskCategoryId = &#63;.
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 * @return the matching zendesk articles
	 */
	@Override
	public List<ZendeskArticle> findByZendeskCategoryId(
		long zendeskCategoryId) {

		return findByZendeskCategoryId(
			zendeskCategoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the zendesk articles where zendeskCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ZendeskArticleModelImpl</code>.
	 * </p>
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 * @param start the lower bound of the range of zendesk articles
	 * @param end the upper bound of the range of zendesk articles (not inclusive)
	 * @return the range of matching zendesk articles
	 */
	@Override
	public List<ZendeskArticle> findByZendeskCategoryId(
		long zendeskCategoryId, int start, int end) {

		return findByZendeskCategoryId(zendeskCategoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the zendesk articles where zendeskCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ZendeskArticleModelImpl</code>.
	 * </p>
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 * @param start the lower bound of the range of zendesk articles
	 * @param end the upper bound of the range of zendesk articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching zendesk articles
	 */
	@Override
	public List<ZendeskArticle> findByZendeskCategoryId(
		long zendeskCategoryId, int start, int end,
		OrderByComparator<ZendeskArticle> orderByComparator) {

		return findByZendeskCategoryId(
			zendeskCategoryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the zendesk articles where zendeskCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ZendeskArticleModelImpl</code>.
	 * </p>
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 * @param start the lower bound of the range of zendesk articles
	 * @param end the upper bound of the range of zendesk articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching zendesk articles
	 */
	@Override
	public List<ZendeskArticle> findByZendeskCategoryId(
		long zendeskCategoryId, int start, int end,
		OrderByComparator<ZendeskArticle> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByZendeskCategoryId;
				finderArgs = new Object[] {zendeskCategoryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByZendeskCategoryId;
			finderArgs = new Object[] {
				zendeskCategoryId, start, end, orderByComparator
			};
		}

		List<ZendeskArticle> list = null;

		if (useFinderCache) {
			list = (List<ZendeskArticle>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ZendeskArticle zendeskArticle : list) {
					if (zendeskCategoryId !=
							zendeskArticle.getZendeskCategoryId()) {

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

			sb.append(_SQL_SELECT_ZENDESKARTICLE_WHERE);

			sb.append(_FINDER_COLUMN_ZENDESKCATEGORYID_ZENDESKCATEGORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ZendeskArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(zendeskCategoryId);

				list = (List<ZendeskArticle>)QueryUtil.list(
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
	 * Returns the first zendesk article in the ordered set where zendeskCategoryId = &#63;.
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching zendesk article
	 * @throws NoSuchZendeskArticleException if a matching zendesk article could not be found
	 */
	@Override
	public ZendeskArticle findByZendeskCategoryId_First(
			long zendeskCategoryId,
			OrderByComparator<ZendeskArticle> orderByComparator)
		throws NoSuchZendeskArticleException {

		ZendeskArticle zendeskArticle = fetchByZendeskCategoryId_First(
			zendeskCategoryId, orderByComparator);

		if (zendeskArticle != null) {
			return zendeskArticle;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("zendeskCategoryId=");
		sb.append(zendeskCategoryId);

		sb.append("}");

		throw new NoSuchZendeskArticleException(sb.toString());
	}

	/**
	 * Returns the first zendesk article in the ordered set where zendeskCategoryId = &#63;.
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching zendesk article, or <code>null</code> if a matching zendesk article could not be found
	 */
	@Override
	public ZendeskArticle fetchByZendeskCategoryId_First(
		long zendeskCategoryId,
		OrderByComparator<ZendeskArticle> orderByComparator) {

		List<ZendeskArticle> list = findByZendeskCategoryId(
			zendeskCategoryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last zendesk article in the ordered set where zendeskCategoryId = &#63;.
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching zendesk article
	 * @throws NoSuchZendeskArticleException if a matching zendesk article could not be found
	 */
	@Override
	public ZendeskArticle findByZendeskCategoryId_Last(
			long zendeskCategoryId,
			OrderByComparator<ZendeskArticle> orderByComparator)
		throws NoSuchZendeskArticleException {

		ZendeskArticle zendeskArticle = fetchByZendeskCategoryId_Last(
			zendeskCategoryId, orderByComparator);

		if (zendeskArticle != null) {
			return zendeskArticle;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("zendeskCategoryId=");
		sb.append(zendeskCategoryId);

		sb.append("}");

		throw new NoSuchZendeskArticleException(sb.toString());
	}

	/**
	 * Returns the last zendesk article in the ordered set where zendeskCategoryId = &#63;.
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching zendesk article, or <code>null</code> if a matching zendesk article could not be found
	 */
	@Override
	public ZendeskArticle fetchByZendeskCategoryId_Last(
		long zendeskCategoryId,
		OrderByComparator<ZendeskArticle> orderByComparator) {

		int count = countByZendeskCategoryId(zendeskCategoryId);

		if (count == 0) {
			return null;
		}

		List<ZendeskArticle> list = findByZendeskCategoryId(
			zendeskCategoryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the zendesk articles before and after the current zendesk article in the ordered set where zendeskCategoryId = &#63;.
	 *
	 * @param zendeskArticleId the primary key of the current zendesk article
	 * @param zendeskCategoryId the zendesk category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next zendesk article
	 * @throws NoSuchZendeskArticleException if a zendesk article with the primary key could not be found
	 */
	@Override
	public ZendeskArticle[] findByZendeskCategoryId_PrevAndNext(
			long zendeskArticleId, long zendeskCategoryId,
			OrderByComparator<ZendeskArticle> orderByComparator)
		throws NoSuchZendeskArticleException {

		ZendeskArticle zendeskArticle = findByPrimaryKey(zendeskArticleId);

		Session session = null;

		try {
			session = openSession();

			ZendeskArticle[] array = new ZendeskArticleImpl[3];

			array[0] = getByZendeskCategoryId_PrevAndNext(
				session, zendeskArticle, zendeskCategoryId, orderByComparator,
				true);

			array[1] = zendeskArticle;

			array[2] = getByZendeskCategoryId_PrevAndNext(
				session, zendeskArticle, zendeskCategoryId, orderByComparator,
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

	protected ZendeskArticle getByZendeskCategoryId_PrevAndNext(
		Session session, ZendeskArticle zendeskArticle, long zendeskCategoryId,
		OrderByComparator<ZendeskArticle> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ZENDESKARTICLE_WHERE);

		sb.append(_FINDER_COLUMN_ZENDESKCATEGORYID_ZENDESKCATEGORYID_2);

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
			sb.append(ZendeskArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(zendeskCategoryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						zendeskArticle)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ZendeskArticle> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the zendesk articles where zendeskCategoryId = &#63; from the database.
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 */
	@Override
	public void removeByZendeskCategoryId(long zendeskCategoryId) {
		for (ZendeskArticle zendeskArticle :
				findByZendeskCategoryId(
					zendeskCategoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(zendeskArticle);
		}
	}

	/**
	 * Returns the number of zendesk articles where zendeskCategoryId = &#63;.
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 * @return the number of matching zendesk articles
	 */
	@Override
	public int countByZendeskCategoryId(long zendeskCategoryId) {
		FinderPath finderPath = _finderPathCountByZendeskCategoryId;

		Object[] finderArgs = new Object[] {zendeskCategoryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ZENDESKARTICLE_WHERE);

			sb.append(_FINDER_COLUMN_ZENDESKCATEGORYID_ZENDESKCATEGORYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(zendeskCategoryId);

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
		_FINDER_COLUMN_ZENDESKCATEGORYID_ZENDESKCATEGORYID_2 =
			"zendeskArticle.zendeskCategoryId = ?";

	private FinderPath _finderPathWithPaginationFindByZendeskSectionId;
	private FinderPath _finderPathWithoutPaginationFindByZendeskSectionId;
	private FinderPath _finderPathCountByZendeskSectionId;

	/**
	 * Returns all the zendesk articles where zendeskSectionId = &#63;.
	 *
	 * @param zendeskSectionId the zendesk section ID
	 * @return the matching zendesk articles
	 */
	@Override
	public List<ZendeskArticle> findByZendeskSectionId(long zendeskSectionId) {
		return findByZendeskSectionId(
			zendeskSectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the zendesk articles where zendeskSectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ZendeskArticleModelImpl</code>.
	 * </p>
	 *
	 * @param zendeskSectionId the zendesk section ID
	 * @param start the lower bound of the range of zendesk articles
	 * @param end the upper bound of the range of zendesk articles (not inclusive)
	 * @return the range of matching zendesk articles
	 */
	@Override
	public List<ZendeskArticle> findByZendeskSectionId(
		long zendeskSectionId, int start, int end) {

		return findByZendeskSectionId(zendeskSectionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the zendesk articles where zendeskSectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ZendeskArticleModelImpl</code>.
	 * </p>
	 *
	 * @param zendeskSectionId the zendesk section ID
	 * @param start the lower bound of the range of zendesk articles
	 * @param end the upper bound of the range of zendesk articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching zendesk articles
	 */
	@Override
	public List<ZendeskArticle> findByZendeskSectionId(
		long zendeskSectionId, int start, int end,
		OrderByComparator<ZendeskArticle> orderByComparator) {

		return findByZendeskSectionId(
			zendeskSectionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the zendesk articles where zendeskSectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ZendeskArticleModelImpl</code>.
	 * </p>
	 *
	 * @param zendeskSectionId the zendesk section ID
	 * @param start the lower bound of the range of zendesk articles
	 * @param end the upper bound of the range of zendesk articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching zendesk articles
	 */
	@Override
	public List<ZendeskArticle> findByZendeskSectionId(
		long zendeskSectionId, int start, int end,
		OrderByComparator<ZendeskArticle> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByZendeskSectionId;
				finderArgs = new Object[] {zendeskSectionId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByZendeskSectionId;
			finderArgs = new Object[] {
				zendeskSectionId, start, end, orderByComparator
			};
		}

		List<ZendeskArticle> list = null;

		if (useFinderCache) {
			list = (List<ZendeskArticle>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ZendeskArticle zendeskArticle : list) {
					if (zendeskSectionId !=
							zendeskArticle.getZendeskSectionId()) {

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

			sb.append(_SQL_SELECT_ZENDESKARTICLE_WHERE);

			sb.append(_FINDER_COLUMN_ZENDESKSECTIONID_ZENDESKSECTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ZendeskArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(zendeskSectionId);

				list = (List<ZendeskArticle>)QueryUtil.list(
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
	 * Returns the first zendesk article in the ordered set where zendeskSectionId = &#63;.
	 *
	 * @param zendeskSectionId the zendesk section ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching zendesk article
	 * @throws NoSuchZendeskArticleException if a matching zendesk article could not be found
	 */
	@Override
	public ZendeskArticle findByZendeskSectionId_First(
			long zendeskSectionId,
			OrderByComparator<ZendeskArticle> orderByComparator)
		throws NoSuchZendeskArticleException {

		ZendeskArticle zendeskArticle = fetchByZendeskSectionId_First(
			zendeskSectionId, orderByComparator);

		if (zendeskArticle != null) {
			return zendeskArticle;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("zendeskSectionId=");
		sb.append(zendeskSectionId);

		sb.append("}");

		throw new NoSuchZendeskArticleException(sb.toString());
	}

	/**
	 * Returns the first zendesk article in the ordered set where zendeskSectionId = &#63;.
	 *
	 * @param zendeskSectionId the zendesk section ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching zendesk article, or <code>null</code> if a matching zendesk article could not be found
	 */
	@Override
	public ZendeskArticle fetchByZendeskSectionId_First(
		long zendeskSectionId,
		OrderByComparator<ZendeskArticle> orderByComparator) {

		List<ZendeskArticle> list = findByZendeskSectionId(
			zendeskSectionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last zendesk article in the ordered set where zendeskSectionId = &#63;.
	 *
	 * @param zendeskSectionId the zendesk section ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching zendesk article
	 * @throws NoSuchZendeskArticleException if a matching zendesk article could not be found
	 */
	@Override
	public ZendeskArticle findByZendeskSectionId_Last(
			long zendeskSectionId,
			OrderByComparator<ZendeskArticle> orderByComparator)
		throws NoSuchZendeskArticleException {

		ZendeskArticle zendeskArticle = fetchByZendeskSectionId_Last(
			zendeskSectionId, orderByComparator);

		if (zendeskArticle != null) {
			return zendeskArticle;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("zendeskSectionId=");
		sb.append(zendeskSectionId);

		sb.append("}");

		throw new NoSuchZendeskArticleException(sb.toString());
	}

	/**
	 * Returns the last zendesk article in the ordered set where zendeskSectionId = &#63;.
	 *
	 * @param zendeskSectionId the zendesk section ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching zendesk article, or <code>null</code> if a matching zendesk article could not be found
	 */
	@Override
	public ZendeskArticle fetchByZendeskSectionId_Last(
		long zendeskSectionId,
		OrderByComparator<ZendeskArticle> orderByComparator) {

		int count = countByZendeskSectionId(zendeskSectionId);

		if (count == 0) {
			return null;
		}

		List<ZendeskArticle> list = findByZendeskSectionId(
			zendeskSectionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the zendesk articles before and after the current zendesk article in the ordered set where zendeskSectionId = &#63;.
	 *
	 * @param zendeskArticleId the primary key of the current zendesk article
	 * @param zendeskSectionId the zendesk section ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next zendesk article
	 * @throws NoSuchZendeskArticleException if a zendesk article with the primary key could not be found
	 */
	@Override
	public ZendeskArticle[] findByZendeskSectionId_PrevAndNext(
			long zendeskArticleId, long zendeskSectionId,
			OrderByComparator<ZendeskArticle> orderByComparator)
		throws NoSuchZendeskArticleException {

		ZendeskArticle zendeskArticle = findByPrimaryKey(zendeskArticleId);

		Session session = null;

		try {
			session = openSession();

			ZendeskArticle[] array = new ZendeskArticleImpl[3];

			array[0] = getByZendeskSectionId_PrevAndNext(
				session, zendeskArticle, zendeskSectionId, orderByComparator,
				true);

			array[1] = zendeskArticle;

			array[2] = getByZendeskSectionId_PrevAndNext(
				session, zendeskArticle, zendeskSectionId, orderByComparator,
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

	protected ZendeskArticle getByZendeskSectionId_PrevAndNext(
		Session session, ZendeskArticle zendeskArticle, long zendeskSectionId,
		OrderByComparator<ZendeskArticle> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ZENDESKARTICLE_WHERE);

		sb.append(_FINDER_COLUMN_ZENDESKSECTIONID_ZENDESKSECTIONID_2);

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
			sb.append(ZendeskArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(zendeskSectionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						zendeskArticle)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ZendeskArticle> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the zendesk articles where zendeskSectionId = &#63; from the database.
	 *
	 * @param zendeskSectionId the zendesk section ID
	 */
	@Override
	public void removeByZendeskSectionId(long zendeskSectionId) {
		for (ZendeskArticle zendeskArticle :
				findByZendeskSectionId(
					zendeskSectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(zendeskArticle);
		}
	}

	/**
	 * Returns the number of zendesk articles where zendeskSectionId = &#63;.
	 *
	 * @param zendeskSectionId the zendesk section ID
	 * @return the number of matching zendesk articles
	 */
	@Override
	public int countByZendeskSectionId(long zendeskSectionId) {
		FinderPath finderPath = _finderPathCountByZendeskSectionId;

		Object[] finderArgs = new Object[] {zendeskSectionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ZENDESKARTICLE_WHERE);

			sb.append(_FINDER_COLUMN_ZENDESKSECTIONID_ZENDESKSECTIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(zendeskSectionId);

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
		_FINDER_COLUMN_ZENDESKSECTIONID_ZENDESKSECTIONID_2 =
			"zendeskArticle.zendeskSectionId = ?";

	private FinderPath _finderPathFetchByDocumentationOriginalURL;
	private FinderPath _finderPathCountByDocumentationOriginalURL;

	/**
	 * Returns the zendesk article where documentationOriginalURL = &#63; or throws a <code>NoSuchZendeskArticleException</code> if it could not be found.
	 *
	 * @param documentationOriginalURL the documentation original url
	 * @return the matching zendesk article
	 * @throws NoSuchZendeskArticleException if a matching zendesk article could not be found
	 */
	@Override
	public ZendeskArticle findByDocumentationOriginalURL(
			String documentationOriginalURL)
		throws NoSuchZendeskArticleException {

		ZendeskArticle zendeskArticle = fetchByDocumentationOriginalURL(
			documentationOriginalURL);

		if (zendeskArticle == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("documentationOriginalURL=");
			sb.append(documentationOriginalURL);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchZendeskArticleException(sb.toString());
		}

		return zendeskArticle;
	}

	/**
	 * Returns the zendesk article where documentationOriginalURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param documentationOriginalURL the documentation original url
	 * @return the matching zendesk article, or <code>null</code> if a matching zendesk article could not be found
	 */
	@Override
	public ZendeskArticle fetchByDocumentationOriginalURL(
		String documentationOriginalURL) {

		return fetchByDocumentationOriginalURL(documentationOriginalURL, true);
	}

	/**
	 * Returns the zendesk article where documentationOriginalURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param documentationOriginalURL the documentation original url
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching zendesk article, or <code>null</code> if a matching zendesk article could not be found
	 */
	@Override
	public ZendeskArticle fetchByDocumentationOriginalURL(
		String documentationOriginalURL, boolean useFinderCache) {

		documentationOriginalURL = Objects.toString(
			documentationOriginalURL, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {documentationOriginalURL};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByDocumentationOriginalURL, finderArgs, this);
		}

		if (result instanceof ZendeskArticle) {
			ZendeskArticle zendeskArticle = (ZendeskArticle)result;

			if (!Objects.equals(
					documentationOriginalURL,
					zendeskArticle.getDocumentationOriginalURL())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_ZENDESKARTICLE_WHERE);

			boolean bindDocumentationOriginalURL = false;

			if (documentationOriginalURL.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_DOCUMENTATIONORIGINALURL_DOCUMENTATIONORIGINALURL_3);
			}
			else {
				bindDocumentationOriginalURL = true;

				sb.append(
					_FINDER_COLUMN_DOCUMENTATIONORIGINALURL_DOCUMENTATIONORIGINALURL_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindDocumentationOriginalURL) {
					queryPos.add(documentationOriginalURL);
				}

				List<ZendeskArticle> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByDocumentationOriginalURL,
							finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									documentationOriginalURL
								};
							}

							_log.warn(
								"ZendeskArticlePersistenceImpl.fetchByDocumentationOriginalURL(String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					ZendeskArticle zendeskArticle = list.get(0);

					result = zendeskArticle;

					cacheResult(zendeskArticle);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByDocumentationOriginalURL, finderArgs);
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
			return (ZendeskArticle)result;
		}
	}

	/**
	 * Removes the zendesk article where documentationOriginalURL = &#63; from the database.
	 *
	 * @param documentationOriginalURL the documentation original url
	 * @return the zendesk article that was removed
	 */
	@Override
	public ZendeskArticle removeByDocumentationOriginalURL(
			String documentationOriginalURL)
		throws NoSuchZendeskArticleException {

		ZendeskArticle zendeskArticle = findByDocumentationOriginalURL(
			documentationOriginalURL);

		return remove(zendeskArticle);
	}

	/**
	 * Returns the number of zendesk articles where documentationOriginalURL = &#63;.
	 *
	 * @param documentationOriginalURL the documentation original url
	 * @return the number of matching zendesk articles
	 */
	@Override
	public int countByDocumentationOriginalURL(
		String documentationOriginalURL) {

		documentationOriginalURL = Objects.toString(
			documentationOriginalURL, "");

		FinderPath finderPath = _finderPathCountByDocumentationOriginalURL;

		Object[] finderArgs = new Object[] {documentationOriginalURL};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ZENDESKARTICLE_WHERE);

			boolean bindDocumentationOriginalURL = false;

			if (documentationOriginalURL.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_DOCUMENTATIONORIGINALURL_DOCUMENTATIONORIGINALURL_3);
			}
			else {
				bindDocumentationOriginalURL = true;

				sb.append(
					_FINDER_COLUMN_DOCUMENTATIONORIGINALURL_DOCUMENTATIONORIGINALURL_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindDocumentationOriginalURL) {
					queryPos.add(documentationOriginalURL);
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
		_FINDER_COLUMN_DOCUMENTATIONORIGINALURL_DOCUMENTATIONORIGINALURL_2 =
			"zendeskArticle.documentationOriginalURL = ?";

	private static final String
		_FINDER_COLUMN_DOCUMENTATIONORIGINALURL_DOCUMENTATIONORIGINALURL_3 =
			"(zendeskArticle.documentationOriginalURL IS NULL OR zendeskArticle.documentationOriginalURL = '')";

	private FinderPath _finderPathFetchByZCI_DK;
	private FinderPath _finderPathCountByZCI_DK;

	/**
	 * Returns the zendesk article where zendeskCategoryId = &#63; and documentationKey = &#63; or throws a <code>NoSuchZendeskArticleException</code> if it could not be found.
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 * @param documentationKey the documentation key
	 * @return the matching zendesk article
	 * @throws NoSuchZendeskArticleException if a matching zendesk article could not be found
	 */
	@Override
	public ZendeskArticle findByZCI_DK(
			long zendeskCategoryId, String documentationKey)
		throws NoSuchZendeskArticleException {

		ZendeskArticle zendeskArticle = fetchByZCI_DK(
			zendeskCategoryId, documentationKey);

		if (zendeskArticle == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("zendeskCategoryId=");
			sb.append(zendeskCategoryId);

			sb.append(", documentationKey=");
			sb.append(documentationKey);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchZendeskArticleException(sb.toString());
		}

		return zendeskArticle;
	}

	/**
	 * Returns the zendesk article where zendeskCategoryId = &#63; and documentationKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 * @param documentationKey the documentation key
	 * @return the matching zendesk article, or <code>null</code> if a matching zendesk article could not be found
	 */
	@Override
	public ZendeskArticle fetchByZCI_DK(
		long zendeskCategoryId, String documentationKey) {

		return fetchByZCI_DK(zendeskCategoryId, documentationKey, true);
	}

	/**
	 * Returns the zendesk article where zendeskCategoryId = &#63; and documentationKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 * @param documentationKey the documentation key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching zendesk article, or <code>null</code> if a matching zendesk article could not be found
	 */
	@Override
	public ZendeskArticle fetchByZCI_DK(
		long zendeskCategoryId, String documentationKey,
		boolean useFinderCache) {

		documentationKey = Objects.toString(documentationKey, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {zendeskCategoryId, documentationKey};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByZCI_DK, finderArgs, this);
		}

		if (result instanceof ZendeskArticle) {
			ZendeskArticle zendeskArticle = (ZendeskArticle)result;

			if ((zendeskCategoryId != zendeskArticle.getZendeskCategoryId()) ||
				!Objects.equals(
					documentationKey, zendeskArticle.getDocumentationKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_ZENDESKARTICLE_WHERE);

			sb.append(_FINDER_COLUMN_ZCI_DK_ZENDESKCATEGORYID_2);

			boolean bindDocumentationKey = false;

			if (documentationKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_ZCI_DK_DOCUMENTATIONKEY_3);
			}
			else {
				bindDocumentationKey = true;

				sb.append(_FINDER_COLUMN_ZCI_DK_DOCUMENTATIONKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(zendeskCategoryId);

				if (bindDocumentationKey) {
					queryPos.add(documentationKey);
				}

				List<ZendeskArticle> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByZCI_DK, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									zendeskCategoryId, documentationKey
								};
							}

							_log.warn(
								"ZendeskArticlePersistenceImpl.fetchByZCI_DK(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					ZendeskArticle zendeskArticle = list.get(0);

					result = zendeskArticle;

					cacheResult(zendeskArticle);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByZCI_DK, finderArgs);
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
			return (ZendeskArticle)result;
		}
	}

	/**
	 * Removes the zendesk article where zendeskCategoryId = &#63; and documentationKey = &#63; from the database.
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 * @param documentationKey the documentation key
	 * @return the zendesk article that was removed
	 */
	@Override
	public ZendeskArticle removeByZCI_DK(
			long zendeskCategoryId, String documentationKey)
		throws NoSuchZendeskArticleException {

		ZendeskArticle zendeskArticle = findByZCI_DK(
			zendeskCategoryId, documentationKey);

		return remove(zendeskArticle);
	}

	/**
	 * Returns the number of zendesk articles where zendeskCategoryId = &#63; and documentationKey = &#63;.
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 * @param documentationKey the documentation key
	 * @return the number of matching zendesk articles
	 */
	@Override
	public int countByZCI_DK(long zendeskCategoryId, String documentationKey) {
		documentationKey = Objects.toString(documentationKey, "");

		FinderPath finderPath = _finderPathCountByZCI_DK;

		Object[] finderArgs = new Object[] {
			zendeskCategoryId, documentationKey
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ZENDESKARTICLE_WHERE);

			sb.append(_FINDER_COLUMN_ZCI_DK_ZENDESKCATEGORYID_2);

			boolean bindDocumentationKey = false;

			if (documentationKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_ZCI_DK_DOCUMENTATIONKEY_3);
			}
			else {
				bindDocumentationKey = true;

				sb.append(_FINDER_COLUMN_ZCI_DK_DOCUMENTATIONKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(zendeskCategoryId);

				if (bindDocumentationKey) {
					queryPos.add(documentationKey);
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

	private static final String _FINDER_COLUMN_ZCI_DK_ZENDESKCATEGORYID_2 =
		"zendeskArticle.zendeskCategoryId = ? AND ";

	private static final String _FINDER_COLUMN_ZCI_DK_DOCUMENTATIONKEY_2 =
		"zendeskArticle.documentationKey = ?";

	private static final String _FINDER_COLUMN_ZCI_DK_DOCUMENTATIONKEY_3 =
		"(zendeskArticle.documentationKey IS NULL OR zendeskArticle.documentationKey = '')";

	public ZendeskArticlePersistenceImpl() {
		setModelClass(ZendeskArticle.class);
	}

	/**
	 * Caches the zendesk article in the entity cache if it is enabled.
	 *
	 * @param zendeskArticle the zendesk article
	 */
	@Override
	public void cacheResult(ZendeskArticle zendeskArticle) {
		entityCache.putResult(
			ZendeskArticleModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleImpl.class, zendeskArticle.getPrimaryKey(),
			zendeskArticle);

		finderCache.putResult(
			_finderPathFetchByDocumentationOriginalURL,
			new Object[] {zendeskArticle.getDocumentationOriginalURL()},
			zendeskArticle);

		finderCache.putResult(
			_finderPathFetchByZCI_DK,
			new Object[] {
				zendeskArticle.getZendeskCategoryId(),
				zendeskArticle.getDocumentationKey()
			},
			zendeskArticle);

		zendeskArticle.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the zendesk articles in the entity cache if it is enabled.
	 *
	 * @param zendeskArticles the zendesk articles
	 */
	@Override
	public void cacheResult(List<ZendeskArticle> zendeskArticles) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (zendeskArticles.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ZendeskArticle zendeskArticle : zendeskArticles) {
			if (entityCache.getResult(
					ZendeskArticleModelImpl.ENTITY_CACHE_ENABLED,
					ZendeskArticleImpl.class, zendeskArticle.getPrimaryKey()) ==
						null) {

				cacheResult(zendeskArticle);
			}
			else {
				zendeskArticle.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all zendesk articles.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ZendeskArticleImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the zendesk article.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ZendeskArticle zendeskArticle) {
		entityCache.removeResult(
			ZendeskArticleModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleImpl.class, zendeskArticle.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((ZendeskArticleModelImpl)zendeskArticle, true);
	}

	@Override
	public void clearCache(List<ZendeskArticle> zendeskArticles) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ZendeskArticle zendeskArticle : zendeskArticles) {
			entityCache.removeResult(
				ZendeskArticleModelImpl.ENTITY_CACHE_ENABLED,
				ZendeskArticleImpl.class, zendeskArticle.getPrimaryKey());

			clearUniqueFindersCache(
				(ZendeskArticleModelImpl)zendeskArticle, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				ZendeskArticleModelImpl.ENTITY_CACHE_ENABLED,
				ZendeskArticleImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ZendeskArticleModelImpl zendeskArticleModelImpl) {

		Object[] args = new Object[] {
			zendeskArticleModelImpl.getDocumentationOriginalURL()
		};

		finderCache.putResult(
			_finderPathCountByDocumentationOriginalURL, args, Long.valueOf(1),
			false);
		finderCache.putResult(
			_finderPathFetchByDocumentationOriginalURL, args,
			zendeskArticleModelImpl, false);

		args = new Object[] {
			zendeskArticleModelImpl.getZendeskCategoryId(),
			zendeskArticleModelImpl.getDocumentationKey()
		};

		finderCache.putResult(
			_finderPathCountByZCI_DK, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByZCI_DK, args, zendeskArticleModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		ZendeskArticleModelImpl zendeskArticleModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				zendeskArticleModelImpl.getDocumentationOriginalURL()
			};

			finderCache.removeResult(
				_finderPathCountByDocumentationOriginalURL, args);
			finderCache.removeResult(
				_finderPathFetchByDocumentationOriginalURL, args);
		}

		if ((zendeskArticleModelImpl.getColumnBitmask() &
			 _finderPathFetchByDocumentationOriginalURL.getColumnBitmask()) !=
				 0) {

			Object[] args = new Object[] {
				zendeskArticleModelImpl.getOriginalDocumentationOriginalURL()
			};

			finderCache.removeResult(
				_finderPathCountByDocumentationOriginalURL, args);
			finderCache.removeResult(
				_finderPathFetchByDocumentationOriginalURL, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				zendeskArticleModelImpl.getZendeskCategoryId(),
				zendeskArticleModelImpl.getDocumentationKey()
			};

			finderCache.removeResult(_finderPathCountByZCI_DK, args);
			finderCache.removeResult(_finderPathFetchByZCI_DK, args);
		}

		if ((zendeskArticleModelImpl.getColumnBitmask() &
			 _finderPathFetchByZCI_DK.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				zendeskArticleModelImpl.getOriginalZendeskCategoryId(),
				zendeskArticleModelImpl.getOriginalDocumentationKey()
			};

			finderCache.removeResult(_finderPathCountByZCI_DK, args);
			finderCache.removeResult(_finderPathFetchByZCI_DK, args);
		}
	}

	/**
	 * Creates a new zendesk article with the primary key. Does not add the zendesk article to the database.
	 *
	 * @param zendeskArticleId the primary key for the new zendesk article
	 * @return the new zendesk article
	 */
	@Override
	public ZendeskArticle create(long zendeskArticleId) {
		ZendeskArticle zendeskArticle = new ZendeskArticleImpl();

		zendeskArticle.setNew(true);
		zendeskArticle.setPrimaryKey(zendeskArticleId);

		return zendeskArticle;
	}

	/**
	 * Removes the zendesk article with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param zendeskArticleId the primary key of the zendesk article
	 * @return the zendesk article that was removed
	 * @throws NoSuchZendeskArticleException if a zendesk article with the primary key could not be found
	 */
	@Override
	public ZendeskArticle remove(long zendeskArticleId)
		throws NoSuchZendeskArticleException {

		return remove((Serializable)zendeskArticleId);
	}

	/**
	 * Removes the zendesk article with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the zendesk article
	 * @return the zendesk article that was removed
	 * @throws NoSuchZendeskArticleException if a zendesk article with the primary key could not be found
	 */
	@Override
	public ZendeskArticle remove(Serializable primaryKey)
		throws NoSuchZendeskArticleException {

		Session session = null;

		try {
			session = openSession();

			ZendeskArticle zendeskArticle = (ZendeskArticle)session.get(
				ZendeskArticleImpl.class, primaryKey);

			if (zendeskArticle == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchZendeskArticleException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(zendeskArticle);
		}
		catch (NoSuchZendeskArticleException noSuchEntityException) {
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
	protected ZendeskArticle removeImpl(ZendeskArticle zendeskArticle) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(zendeskArticle)) {
				zendeskArticle = (ZendeskArticle)session.get(
					ZendeskArticleImpl.class,
					zendeskArticle.getPrimaryKeyObj());
			}

			if (zendeskArticle != null) {
				session.delete(zendeskArticle);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (zendeskArticle != null) {
			clearCache(zendeskArticle);
		}

		return zendeskArticle;
	}

	@Override
	public ZendeskArticle updateImpl(ZendeskArticle zendeskArticle) {
		boolean isNew = zendeskArticle.isNew();

		if (!(zendeskArticle instanceof ZendeskArticleModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(zendeskArticle.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					zendeskArticle);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in zendeskArticle proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ZendeskArticle implementation " +
					zendeskArticle.getClass());
		}

		ZendeskArticleModelImpl zendeskArticleModelImpl =
			(ZendeskArticleModelImpl)zendeskArticle;

		if (!zendeskArticleModelImpl.hasSetModifiedDate()) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Date date = new Date();

			if (serviceContext == null) {
				zendeskArticle.setModifiedDate(date);
			}
			else {
				zendeskArticle.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(zendeskArticle);

				zendeskArticle.setNew(false);
			}
			else {
				zendeskArticle = (ZendeskArticle)session.merge(zendeskArticle);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!ZendeskArticleModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				zendeskArticleModelImpl.getZendeskCategoryId()
			};

			finderCache.removeResult(_finderPathCountByZendeskCategoryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByZendeskCategoryId, args);

			args = new Object[] {zendeskArticleModelImpl.getZendeskSectionId()};

			finderCache.removeResult(_finderPathCountByZendeskSectionId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByZendeskSectionId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((zendeskArticleModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByZendeskCategoryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					zendeskArticleModelImpl.getOriginalZendeskCategoryId()
				};

				finderCache.removeResult(
					_finderPathCountByZendeskCategoryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByZendeskCategoryId, args);

				args = new Object[] {
					zendeskArticleModelImpl.getZendeskCategoryId()
				};

				finderCache.removeResult(
					_finderPathCountByZendeskCategoryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByZendeskCategoryId, args);
			}

			if ((zendeskArticleModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByZendeskSectionId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					zendeskArticleModelImpl.getOriginalZendeskSectionId()
				};

				finderCache.removeResult(
					_finderPathCountByZendeskSectionId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByZendeskSectionId, args);

				args = new Object[] {
					zendeskArticleModelImpl.getZendeskSectionId()
				};

				finderCache.removeResult(
					_finderPathCountByZendeskSectionId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByZendeskSectionId, args);
			}
		}

		entityCache.putResult(
			ZendeskArticleModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleImpl.class, zendeskArticle.getPrimaryKey(),
			zendeskArticle, false);

		clearUniqueFindersCache(zendeskArticleModelImpl, false);
		cacheUniqueFindersCache(zendeskArticleModelImpl);

		zendeskArticle.resetOriginalValues();

		return zendeskArticle;
	}

	/**
	 * Returns the zendesk article with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the zendesk article
	 * @return the zendesk article
	 * @throws NoSuchZendeskArticleException if a zendesk article with the primary key could not be found
	 */
	@Override
	public ZendeskArticle findByPrimaryKey(Serializable primaryKey)
		throws NoSuchZendeskArticleException {

		ZendeskArticle zendeskArticle = fetchByPrimaryKey(primaryKey);

		if (zendeskArticle == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchZendeskArticleException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return zendeskArticle;
	}

	/**
	 * Returns the zendesk article with the primary key or throws a <code>NoSuchZendeskArticleException</code> if it could not be found.
	 *
	 * @param zendeskArticleId the primary key of the zendesk article
	 * @return the zendesk article
	 * @throws NoSuchZendeskArticleException if a zendesk article with the primary key could not be found
	 */
	@Override
	public ZendeskArticle findByPrimaryKey(long zendeskArticleId)
		throws NoSuchZendeskArticleException {

		return findByPrimaryKey((Serializable)zendeskArticleId);
	}

	/**
	 * Returns the zendesk article with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the zendesk article
	 * @return the zendesk article, or <code>null</code> if a zendesk article with the primary key could not be found
	 */
	@Override
	public ZendeskArticle fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			ZendeskArticleModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		ZendeskArticle zendeskArticle = (ZendeskArticle)serializable;

		if (zendeskArticle == null) {
			Session session = null;

			try {
				session = openSession();

				zendeskArticle = (ZendeskArticle)session.get(
					ZendeskArticleImpl.class, primaryKey);

				if (zendeskArticle != null) {
					cacheResult(zendeskArticle);
				}
				else {
					entityCache.putResult(
						ZendeskArticleModelImpl.ENTITY_CACHE_ENABLED,
						ZendeskArticleImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					ZendeskArticleModelImpl.ENTITY_CACHE_ENABLED,
					ZendeskArticleImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return zendeskArticle;
	}

	/**
	 * Returns the zendesk article with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param zendeskArticleId the primary key of the zendesk article
	 * @return the zendesk article, or <code>null</code> if a zendesk article with the primary key could not be found
	 */
	@Override
	public ZendeskArticle fetchByPrimaryKey(long zendeskArticleId) {
		return fetchByPrimaryKey((Serializable)zendeskArticleId);
	}

	@Override
	public Map<Serializable, ZendeskArticle> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ZendeskArticle> map =
			new HashMap<Serializable, ZendeskArticle>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ZendeskArticle zendeskArticle = fetchByPrimaryKey(primaryKey);

			if (zendeskArticle != null) {
				map.put(primaryKey, zendeskArticle);
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
				ZendeskArticleModelImpl.ENTITY_CACHE_ENABLED,
				ZendeskArticleImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (ZendeskArticle)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_ZENDESKARTICLE_WHERE_PKS_IN);

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

			for (ZendeskArticle zendeskArticle :
					(List<ZendeskArticle>)query.list()) {

				map.put(zendeskArticle.getPrimaryKeyObj(), zendeskArticle);

				cacheResult(zendeskArticle);

				uncachedPrimaryKeys.remove(zendeskArticle.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					ZendeskArticleModelImpl.ENTITY_CACHE_ENABLED,
					ZendeskArticleImpl.class, primaryKey, nullModel);
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
	 * Returns all the zendesk articles.
	 *
	 * @return the zendesk articles
	 */
	@Override
	public List<ZendeskArticle> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the zendesk articles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ZendeskArticleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of zendesk articles
	 * @param end the upper bound of the range of zendesk articles (not inclusive)
	 * @return the range of zendesk articles
	 */
	@Override
	public List<ZendeskArticle> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the zendesk articles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ZendeskArticleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of zendesk articles
	 * @param end the upper bound of the range of zendesk articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of zendesk articles
	 */
	@Override
	public List<ZendeskArticle> findAll(
		int start, int end,
		OrderByComparator<ZendeskArticle> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the zendesk articles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ZendeskArticleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of zendesk articles
	 * @param end the upper bound of the range of zendesk articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of zendesk articles
	 */
	@Override
	public List<ZendeskArticle> findAll(
		int start, int end, OrderByComparator<ZendeskArticle> orderByComparator,
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

		List<ZendeskArticle> list = null;

		if (useFinderCache) {
			list = (List<ZendeskArticle>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ZENDESKARTICLE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ZENDESKARTICLE;

				sql = sql.concat(ZendeskArticleModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ZendeskArticle>)QueryUtil.list(
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
	 * Removes all the zendesk articles from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ZendeskArticle zendeskArticle : findAll()) {
			remove(zendeskArticle);
		}
	}

	/**
	 * Returns the number of zendesk articles.
	 *
	 * @return the number of zendesk articles
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_ZENDESKARTICLE);

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
		return ZendeskArticleModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the zendesk article persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			ZendeskArticleModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleModelImpl.FINDER_CACHE_ENABLED,
			ZendeskArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			ZendeskArticleModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleModelImpl.FINDER_CACHE_ENABLED,
			ZendeskArticleImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			ZendeskArticleModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByZendeskCategoryId = new FinderPath(
			ZendeskArticleModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleModelImpl.FINDER_CACHE_ENABLED,
			ZendeskArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByZendeskCategoryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByZendeskCategoryId = new FinderPath(
			ZendeskArticleModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleModelImpl.FINDER_CACHE_ENABLED,
			ZendeskArticleImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByZendeskCategoryId", new String[] {Long.class.getName()},
			ZendeskArticleModelImpl.ZENDESKCATEGORYID_COLUMN_BITMASK);

		_finderPathCountByZendeskCategoryId = new FinderPath(
			ZendeskArticleModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByZendeskCategoryId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByZendeskSectionId = new FinderPath(
			ZendeskArticleModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleModelImpl.FINDER_CACHE_ENABLED,
			ZendeskArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByZendeskSectionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByZendeskSectionId = new FinderPath(
			ZendeskArticleModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleModelImpl.FINDER_CACHE_ENABLED,
			ZendeskArticleImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByZendeskSectionId", new String[] {Long.class.getName()},
			ZendeskArticleModelImpl.ZENDESKSECTIONID_COLUMN_BITMASK);

		_finderPathCountByZendeskSectionId = new FinderPath(
			ZendeskArticleModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByZendeskSectionId", new String[] {Long.class.getName()});

		_finderPathFetchByDocumentationOriginalURL = new FinderPath(
			ZendeskArticleModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleModelImpl.FINDER_CACHE_ENABLED,
			ZendeskArticleImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByDocumentationOriginalURL",
			new String[] {String.class.getName()},
			ZendeskArticleModelImpl.DOCUMENTATIONORIGINALURL_COLUMN_BITMASK);

		_finderPathCountByDocumentationOriginalURL = new FinderPath(
			ZendeskArticleModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByDocumentationOriginalURL",
			new String[] {String.class.getName()});

		_finderPathFetchByZCI_DK = new FinderPath(
			ZendeskArticleModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleModelImpl.FINDER_CACHE_ENABLED,
			ZendeskArticleImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByZCI_DK",
			new String[] {Long.class.getName(), String.class.getName()},
			ZendeskArticleModelImpl.ZENDESKCATEGORYID_COLUMN_BITMASK |
			ZendeskArticleModelImpl.DOCUMENTATIONKEY_COLUMN_BITMASK);

		_finderPathCountByZCI_DK = new FinderPath(
			ZendeskArticleModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByZCI_DK",
			new String[] {Long.class.getName(), String.class.getName()});

		ZendeskArticleUtil.setPersistence(this);
	}

	public void destroy() {
		ZendeskArticleUtil.setPersistence(null);

		entityCache.removeCache(ZendeskArticleImpl.class.getName());

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

	private static final String _SQL_SELECT_ZENDESKARTICLE =
		"SELECT zendeskArticle FROM ZendeskArticle zendeskArticle";

	private static final String _SQL_SELECT_ZENDESKARTICLE_WHERE_PKS_IN =
		"SELECT zendeskArticle FROM ZendeskArticle zendeskArticle WHERE zendeskArticleId IN (";

	private static final String _SQL_SELECT_ZENDESKARTICLE_WHERE =
		"SELECT zendeskArticle FROM ZendeskArticle zendeskArticle WHERE ";

	private static final String _SQL_COUNT_ZENDESKARTICLE =
		"SELECT COUNT(zendeskArticle) FROM ZendeskArticle zendeskArticle";

	private static final String _SQL_COUNT_ZENDESKARTICLE_WHERE =
		"SELECT COUNT(zendeskArticle) FROM ZendeskArticle zendeskArticle WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "zendeskArticle.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ZendeskArticle exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ZendeskArticle exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ZendeskArticlePersistenceImpl.class);

}