/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.zendesk.documentation.sync.service.persistence.impl;

import com.liferay.osb.customer.zendesk.documentation.sync.exception.NoSuchZendeskSectionException;
import com.liferay.osb.customer.zendesk.documentation.sync.model.ZendeskSection;
import com.liferay.osb.customer.zendesk.documentation.sync.model.impl.ZendeskSectionImpl;
import com.liferay.osb.customer.zendesk.documentation.sync.model.impl.ZendeskSectionModelImpl;
import com.liferay.osb.customer.zendesk.documentation.sync.service.persistence.ZendeskSectionPersistence;
import com.liferay.osb.customer.zendesk.documentation.sync.service.persistence.ZendeskSectionUtil;
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
 * The persistence implementation for the zendesk section service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ZendeskSectionPersistenceImpl
	extends BasePersistenceImpl<ZendeskSection>
	implements ZendeskSectionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ZendeskSectionUtil</code> to access the zendesk section persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ZendeskSectionImpl.class.getName();

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
	 * Returns all the zendesk sections where zendeskCategoryId = &#63;.
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 * @return the matching zendesk sections
	 */
	@Override
	public List<ZendeskSection> findByZendeskCategoryId(
		long zendeskCategoryId) {

		return findByZendeskCategoryId(
			zendeskCategoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the zendesk sections where zendeskCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ZendeskSectionModelImpl</code>.
	 * </p>
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 * @param start the lower bound of the range of zendesk sections
	 * @param end the upper bound of the range of zendesk sections (not inclusive)
	 * @return the range of matching zendesk sections
	 */
	@Override
	public List<ZendeskSection> findByZendeskCategoryId(
		long zendeskCategoryId, int start, int end) {

		return findByZendeskCategoryId(zendeskCategoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the zendesk sections where zendeskCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ZendeskSectionModelImpl</code>.
	 * </p>
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 * @param start the lower bound of the range of zendesk sections
	 * @param end the upper bound of the range of zendesk sections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching zendesk sections
	 */
	@Override
	public List<ZendeskSection> findByZendeskCategoryId(
		long zendeskCategoryId, int start, int end,
		OrderByComparator<ZendeskSection> orderByComparator) {

		return findByZendeskCategoryId(
			zendeskCategoryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the zendesk sections where zendeskCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ZendeskSectionModelImpl</code>.
	 * </p>
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 * @param start the lower bound of the range of zendesk sections
	 * @param end the upper bound of the range of zendesk sections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching zendesk sections
	 */
	@Override
	public List<ZendeskSection> findByZendeskCategoryId(
		long zendeskCategoryId, int start, int end,
		OrderByComparator<ZendeskSection> orderByComparator,
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

		List<ZendeskSection> list = null;

		if (useFinderCache) {
			list = (List<ZendeskSection>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ZendeskSection zendeskSection : list) {
					if (zendeskCategoryId !=
							zendeskSection.getZendeskCategoryId()) {

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

			sb.append(_SQL_SELECT_ZENDESKSECTION_WHERE);

			sb.append(_FINDER_COLUMN_ZENDESKCATEGORYID_ZENDESKCATEGORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ZendeskSectionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(zendeskCategoryId);

				list = (List<ZendeskSection>)QueryUtil.list(
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
	 * Returns the first zendesk section in the ordered set where zendeskCategoryId = &#63;.
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching zendesk section
	 * @throws NoSuchZendeskSectionException if a matching zendesk section could not be found
	 */
	@Override
	public ZendeskSection findByZendeskCategoryId_First(
			long zendeskCategoryId,
			OrderByComparator<ZendeskSection> orderByComparator)
		throws NoSuchZendeskSectionException {

		ZendeskSection zendeskSection = fetchByZendeskCategoryId_First(
			zendeskCategoryId, orderByComparator);

		if (zendeskSection != null) {
			return zendeskSection;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("zendeskCategoryId=");
		sb.append(zendeskCategoryId);

		sb.append("}");

		throw new NoSuchZendeskSectionException(sb.toString());
	}

	/**
	 * Returns the first zendesk section in the ordered set where zendeskCategoryId = &#63;.
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching zendesk section, or <code>null</code> if a matching zendesk section could not be found
	 */
	@Override
	public ZendeskSection fetchByZendeskCategoryId_First(
		long zendeskCategoryId,
		OrderByComparator<ZendeskSection> orderByComparator) {

		List<ZendeskSection> list = findByZendeskCategoryId(
			zendeskCategoryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last zendesk section in the ordered set where zendeskCategoryId = &#63;.
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching zendesk section
	 * @throws NoSuchZendeskSectionException if a matching zendesk section could not be found
	 */
	@Override
	public ZendeskSection findByZendeskCategoryId_Last(
			long zendeskCategoryId,
			OrderByComparator<ZendeskSection> orderByComparator)
		throws NoSuchZendeskSectionException {

		ZendeskSection zendeskSection = fetchByZendeskCategoryId_Last(
			zendeskCategoryId, orderByComparator);

		if (zendeskSection != null) {
			return zendeskSection;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("zendeskCategoryId=");
		sb.append(zendeskCategoryId);

		sb.append("}");

		throw new NoSuchZendeskSectionException(sb.toString());
	}

	/**
	 * Returns the last zendesk section in the ordered set where zendeskCategoryId = &#63;.
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching zendesk section, or <code>null</code> if a matching zendesk section could not be found
	 */
	@Override
	public ZendeskSection fetchByZendeskCategoryId_Last(
		long zendeskCategoryId,
		OrderByComparator<ZendeskSection> orderByComparator) {

		int count = countByZendeskCategoryId(zendeskCategoryId);

		if (count == 0) {
			return null;
		}

		List<ZendeskSection> list = findByZendeskCategoryId(
			zendeskCategoryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the zendesk sections before and after the current zendesk section in the ordered set where zendeskCategoryId = &#63;.
	 *
	 * @param zendeskSectionId the primary key of the current zendesk section
	 * @param zendeskCategoryId the zendesk category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next zendesk section
	 * @throws NoSuchZendeskSectionException if a zendesk section with the primary key could not be found
	 */
	@Override
	public ZendeskSection[] findByZendeskCategoryId_PrevAndNext(
			long zendeskSectionId, long zendeskCategoryId,
			OrderByComparator<ZendeskSection> orderByComparator)
		throws NoSuchZendeskSectionException {

		ZendeskSection zendeskSection = findByPrimaryKey(zendeskSectionId);

		Session session = null;

		try {
			session = openSession();

			ZendeskSection[] array = new ZendeskSectionImpl[3];

			array[0] = getByZendeskCategoryId_PrevAndNext(
				session, zendeskSection, zendeskCategoryId, orderByComparator,
				true);

			array[1] = zendeskSection;

			array[2] = getByZendeskCategoryId_PrevAndNext(
				session, zendeskSection, zendeskCategoryId, orderByComparator,
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

	protected ZendeskSection getByZendeskCategoryId_PrevAndNext(
		Session session, ZendeskSection zendeskSection, long zendeskCategoryId,
		OrderByComparator<ZendeskSection> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ZENDESKSECTION_WHERE);

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
			sb.append(ZendeskSectionModelImpl.ORDER_BY_JPQL);
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
						zendeskSection)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ZendeskSection> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the zendesk sections where zendeskCategoryId = &#63; from the database.
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 */
	@Override
	public void removeByZendeskCategoryId(long zendeskCategoryId) {
		for (ZendeskSection zendeskSection :
				findByZendeskCategoryId(
					zendeskCategoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(zendeskSection);
		}
	}

	/**
	 * Returns the number of zendesk sections where zendeskCategoryId = &#63;.
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 * @return the number of matching zendesk sections
	 */
	@Override
	public int countByZendeskCategoryId(long zendeskCategoryId) {
		FinderPath finderPath = _finderPathCountByZendeskCategoryId;

		Object[] finderArgs = new Object[] {zendeskCategoryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ZENDESKSECTION_WHERE);

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
			"zendeskSection.zendeskCategoryId = ?";

	private FinderPath _finderPathFetchByZCI_DK;
	private FinderPath _finderPathCountByZCI_DK;

	/**
	 * Returns the zendesk section where zendeskCategoryId = &#63; and documentationKey = &#63; or throws a <code>NoSuchZendeskSectionException</code> if it could not be found.
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 * @param documentationKey the documentation key
	 * @return the matching zendesk section
	 * @throws NoSuchZendeskSectionException if a matching zendesk section could not be found
	 */
	@Override
	public ZendeskSection findByZCI_DK(
			long zendeskCategoryId, String documentationKey)
		throws NoSuchZendeskSectionException {

		ZendeskSection zendeskSection = fetchByZCI_DK(
			zendeskCategoryId, documentationKey);

		if (zendeskSection == null) {
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

			throw new NoSuchZendeskSectionException(sb.toString());
		}

		return zendeskSection;
	}

	/**
	 * Returns the zendesk section where zendeskCategoryId = &#63; and documentationKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 * @param documentationKey the documentation key
	 * @return the matching zendesk section, or <code>null</code> if a matching zendesk section could not be found
	 */
	@Override
	public ZendeskSection fetchByZCI_DK(
		long zendeskCategoryId, String documentationKey) {

		return fetchByZCI_DK(zendeskCategoryId, documentationKey, true);
	}

	/**
	 * Returns the zendesk section where zendeskCategoryId = &#63; and documentationKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 * @param documentationKey the documentation key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching zendesk section, or <code>null</code> if a matching zendesk section could not be found
	 */
	@Override
	public ZendeskSection fetchByZCI_DK(
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

		if (result instanceof ZendeskSection) {
			ZendeskSection zendeskSection = (ZendeskSection)result;

			if ((zendeskCategoryId != zendeskSection.getZendeskCategoryId()) ||
				!Objects.equals(
					documentationKey, zendeskSection.getDocumentationKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_ZENDESKSECTION_WHERE);

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

				List<ZendeskSection> list = query.list();

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
								"ZendeskSectionPersistenceImpl.fetchByZCI_DK(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					ZendeskSection zendeskSection = list.get(0);

					result = zendeskSection;

					cacheResult(zendeskSection);
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
			return (ZendeskSection)result;
		}
	}

	/**
	 * Removes the zendesk section where zendeskCategoryId = &#63; and documentationKey = &#63; from the database.
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 * @param documentationKey the documentation key
	 * @return the zendesk section that was removed
	 */
	@Override
	public ZendeskSection removeByZCI_DK(
			long zendeskCategoryId, String documentationKey)
		throws NoSuchZendeskSectionException {

		ZendeskSection zendeskSection = findByZCI_DK(
			zendeskCategoryId, documentationKey);

		return remove(zendeskSection);
	}

	/**
	 * Returns the number of zendesk sections where zendeskCategoryId = &#63; and documentationKey = &#63;.
	 *
	 * @param zendeskCategoryId the zendesk category ID
	 * @param documentationKey the documentation key
	 * @return the number of matching zendesk sections
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

			sb.append(_SQL_COUNT_ZENDESKSECTION_WHERE);

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
		"zendeskSection.zendeskCategoryId = ? AND ";

	private static final String _FINDER_COLUMN_ZCI_DK_DOCUMENTATIONKEY_2 =
		"zendeskSection.documentationKey = ?";

	private static final String _FINDER_COLUMN_ZCI_DK_DOCUMENTATIONKEY_3 =
		"(zendeskSection.documentationKey IS NULL OR zendeskSection.documentationKey = '')";

	public ZendeskSectionPersistenceImpl() {
		setModelClass(ZendeskSection.class);
	}

	/**
	 * Caches the zendesk section in the entity cache if it is enabled.
	 *
	 * @param zendeskSection the zendesk section
	 */
	@Override
	public void cacheResult(ZendeskSection zendeskSection) {
		entityCache.putResult(
			ZendeskSectionModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskSectionImpl.class, zendeskSection.getPrimaryKey(),
			zendeskSection);

		finderCache.putResult(
			_finderPathFetchByZCI_DK,
			new Object[] {
				zendeskSection.getZendeskCategoryId(),
				zendeskSection.getDocumentationKey()
			},
			zendeskSection);

		zendeskSection.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the zendesk sections in the entity cache if it is enabled.
	 *
	 * @param zendeskSections the zendesk sections
	 */
	@Override
	public void cacheResult(List<ZendeskSection> zendeskSections) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (zendeskSections.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ZendeskSection zendeskSection : zendeskSections) {
			if (entityCache.getResult(
					ZendeskSectionModelImpl.ENTITY_CACHE_ENABLED,
					ZendeskSectionImpl.class, zendeskSection.getPrimaryKey()) ==
						null) {

				cacheResult(zendeskSection);
			}
			else {
				zendeskSection.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all zendesk sections.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ZendeskSectionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the zendesk section.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ZendeskSection zendeskSection) {
		entityCache.removeResult(
			ZendeskSectionModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskSectionImpl.class, zendeskSection.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((ZendeskSectionModelImpl)zendeskSection, true);
	}

	@Override
	public void clearCache(List<ZendeskSection> zendeskSections) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ZendeskSection zendeskSection : zendeskSections) {
			entityCache.removeResult(
				ZendeskSectionModelImpl.ENTITY_CACHE_ENABLED,
				ZendeskSectionImpl.class, zendeskSection.getPrimaryKey());

			clearUniqueFindersCache(
				(ZendeskSectionModelImpl)zendeskSection, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				ZendeskSectionModelImpl.ENTITY_CACHE_ENABLED,
				ZendeskSectionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ZendeskSectionModelImpl zendeskSectionModelImpl) {

		Object[] args = new Object[] {
			zendeskSectionModelImpl.getZendeskCategoryId(),
			zendeskSectionModelImpl.getDocumentationKey()
		};

		finderCache.putResult(
			_finderPathCountByZCI_DK, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByZCI_DK, args, zendeskSectionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		ZendeskSectionModelImpl zendeskSectionModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				zendeskSectionModelImpl.getZendeskCategoryId(),
				zendeskSectionModelImpl.getDocumentationKey()
			};

			finderCache.removeResult(_finderPathCountByZCI_DK, args);
			finderCache.removeResult(_finderPathFetchByZCI_DK, args);
		}

		if ((zendeskSectionModelImpl.getColumnBitmask() &
			 _finderPathFetchByZCI_DK.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				zendeskSectionModelImpl.getOriginalZendeskCategoryId(),
				zendeskSectionModelImpl.getOriginalDocumentationKey()
			};

			finderCache.removeResult(_finderPathCountByZCI_DK, args);
			finderCache.removeResult(_finderPathFetchByZCI_DK, args);
		}
	}

	/**
	 * Creates a new zendesk section with the primary key. Does not add the zendesk section to the database.
	 *
	 * @param zendeskSectionId the primary key for the new zendesk section
	 * @return the new zendesk section
	 */
	@Override
	public ZendeskSection create(long zendeskSectionId) {
		ZendeskSection zendeskSection = new ZendeskSectionImpl();

		zendeskSection.setNew(true);
		zendeskSection.setPrimaryKey(zendeskSectionId);

		return zendeskSection;
	}

	/**
	 * Removes the zendesk section with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param zendeskSectionId the primary key of the zendesk section
	 * @return the zendesk section that was removed
	 * @throws NoSuchZendeskSectionException if a zendesk section with the primary key could not be found
	 */
	@Override
	public ZendeskSection remove(long zendeskSectionId)
		throws NoSuchZendeskSectionException {

		return remove((Serializable)zendeskSectionId);
	}

	/**
	 * Removes the zendesk section with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the zendesk section
	 * @return the zendesk section that was removed
	 * @throws NoSuchZendeskSectionException if a zendesk section with the primary key could not be found
	 */
	@Override
	public ZendeskSection remove(Serializable primaryKey)
		throws NoSuchZendeskSectionException {

		Session session = null;

		try {
			session = openSession();

			ZendeskSection zendeskSection = (ZendeskSection)session.get(
				ZendeskSectionImpl.class, primaryKey);

			if (zendeskSection == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchZendeskSectionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(zendeskSection);
		}
		catch (NoSuchZendeskSectionException noSuchEntityException) {
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
	protected ZendeskSection removeImpl(ZendeskSection zendeskSection) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(zendeskSection)) {
				zendeskSection = (ZendeskSection)session.get(
					ZendeskSectionImpl.class,
					zendeskSection.getPrimaryKeyObj());
			}

			if (zendeskSection != null) {
				session.delete(zendeskSection);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (zendeskSection != null) {
			clearCache(zendeskSection);
		}

		return zendeskSection;
	}

	@Override
	public ZendeskSection updateImpl(ZendeskSection zendeskSection) {
		boolean isNew = zendeskSection.isNew();

		if (!(zendeskSection instanceof ZendeskSectionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(zendeskSection.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					zendeskSection);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in zendeskSection proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ZendeskSection implementation " +
					zendeskSection.getClass());
		}

		ZendeskSectionModelImpl zendeskSectionModelImpl =
			(ZendeskSectionModelImpl)zendeskSection;

		if (!zendeskSectionModelImpl.hasSetModifiedDate()) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Date date = new Date();

			if (serviceContext == null) {
				zendeskSection.setModifiedDate(date);
			}
			else {
				zendeskSection.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(zendeskSection);

				zendeskSection.setNew(false);
			}
			else {
				zendeskSection = (ZendeskSection)session.merge(zendeskSection);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!ZendeskSectionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				zendeskSectionModelImpl.getZendeskCategoryId()
			};

			finderCache.removeResult(_finderPathCountByZendeskCategoryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByZendeskCategoryId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((zendeskSectionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByZendeskCategoryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					zendeskSectionModelImpl.getOriginalZendeskCategoryId()
				};

				finderCache.removeResult(
					_finderPathCountByZendeskCategoryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByZendeskCategoryId, args);

				args = new Object[] {
					zendeskSectionModelImpl.getZendeskCategoryId()
				};

				finderCache.removeResult(
					_finderPathCountByZendeskCategoryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByZendeskCategoryId, args);
			}
		}

		entityCache.putResult(
			ZendeskSectionModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskSectionImpl.class, zendeskSection.getPrimaryKey(),
			zendeskSection, false);

		clearUniqueFindersCache(zendeskSectionModelImpl, false);
		cacheUniqueFindersCache(zendeskSectionModelImpl);

		zendeskSection.resetOriginalValues();

		return zendeskSection;
	}

	/**
	 * Returns the zendesk section with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the zendesk section
	 * @return the zendesk section
	 * @throws NoSuchZendeskSectionException if a zendesk section with the primary key could not be found
	 */
	@Override
	public ZendeskSection findByPrimaryKey(Serializable primaryKey)
		throws NoSuchZendeskSectionException {

		ZendeskSection zendeskSection = fetchByPrimaryKey(primaryKey);

		if (zendeskSection == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchZendeskSectionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return zendeskSection;
	}

	/**
	 * Returns the zendesk section with the primary key or throws a <code>NoSuchZendeskSectionException</code> if it could not be found.
	 *
	 * @param zendeskSectionId the primary key of the zendesk section
	 * @return the zendesk section
	 * @throws NoSuchZendeskSectionException if a zendesk section with the primary key could not be found
	 */
	@Override
	public ZendeskSection findByPrimaryKey(long zendeskSectionId)
		throws NoSuchZendeskSectionException {

		return findByPrimaryKey((Serializable)zendeskSectionId);
	}

	/**
	 * Returns the zendesk section with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the zendesk section
	 * @return the zendesk section, or <code>null</code> if a zendesk section with the primary key could not be found
	 */
	@Override
	public ZendeskSection fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			ZendeskSectionModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskSectionImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		ZendeskSection zendeskSection = (ZendeskSection)serializable;

		if (zendeskSection == null) {
			Session session = null;

			try {
				session = openSession();

				zendeskSection = (ZendeskSection)session.get(
					ZendeskSectionImpl.class, primaryKey);

				if (zendeskSection != null) {
					cacheResult(zendeskSection);
				}
				else {
					entityCache.putResult(
						ZendeskSectionModelImpl.ENTITY_CACHE_ENABLED,
						ZendeskSectionImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					ZendeskSectionModelImpl.ENTITY_CACHE_ENABLED,
					ZendeskSectionImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return zendeskSection;
	}

	/**
	 * Returns the zendesk section with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param zendeskSectionId the primary key of the zendesk section
	 * @return the zendesk section, or <code>null</code> if a zendesk section with the primary key could not be found
	 */
	@Override
	public ZendeskSection fetchByPrimaryKey(long zendeskSectionId) {
		return fetchByPrimaryKey((Serializable)zendeskSectionId);
	}

	@Override
	public Map<Serializable, ZendeskSection> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ZendeskSection> map =
			new HashMap<Serializable, ZendeskSection>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ZendeskSection zendeskSection = fetchByPrimaryKey(primaryKey);

			if (zendeskSection != null) {
				map.put(primaryKey, zendeskSection);
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
				ZendeskSectionModelImpl.ENTITY_CACHE_ENABLED,
				ZendeskSectionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (ZendeskSection)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_ZENDESKSECTION_WHERE_PKS_IN);

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

			for (ZendeskSection zendeskSection :
					(List<ZendeskSection>)query.list()) {

				map.put(zendeskSection.getPrimaryKeyObj(), zendeskSection);

				cacheResult(zendeskSection);

				uncachedPrimaryKeys.remove(zendeskSection.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					ZendeskSectionModelImpl.ENTITY_CACHE_ENABLED,
					ZendeskSectionImpl.class, primaryKey, nullModel);
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
	 * Returns all the zendesk sections.
	 *
	 * @return the zendesk sections
	 */
	@Override
	public List<ZendeskSection> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the zendesk sections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ZendeskSectionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of zendesk sections
	 * @param end the upper bound of the range of zendesk sections (not inclusive)
	 * @return the range of zendesk sections
	 */
	@Override
	public List<ZendeskSection> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the zendesk sections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ZendeskSectionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of zendesk sections
	 * @param end the upper bound of the range of zendesk sections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of zendesk sections
	 */
	@Override
	public List<ZendeskSection> findAll(
		int start, int end,
		OrderByComparator<ZendeskSection> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the zendesk sections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ZendeskSectionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of zendesk sections
	 * @param end the upper bound of the range of zendesk sections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of zendesk sections
	 */
	@Override
	public List<ZendeskSection> findAll(
		int start, int end, OrderByComparator<ZendeskSection> orderByComparator,
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

		List<ZendeskSection> list = null;

		if (useFinderCache) {
			list = (List<ZendeskSection>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ZENDESKSECTION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ZENDESKSECTION;

				sql = sql.concat(ZendeskSectionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ZendeskSection>)QueryUtil.list(
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
	 * Removes all the zendesk sections from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ZendeskSection zendeskSection : findAll()) {
			remove(zendeskSection);
		}
	}

	/**
	 * Returns the number of zendesk sections.
	 *
	 * @return the number of zendesk sections
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_ZENDESKSECTION);

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
		return ZendeskSectionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the zendesk section persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			ZendeskSectionModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskSectionModelImpl.FINDER_CACHE_ENABLED,
			ZendeskSectionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			ZendeskSectionModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskSectionModelImpl.FINDER_CACHE_ENABLED,
			ZendeskSectionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			ZendeskSectionModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskSectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByZendeskCategoryId = new FinderPath(
			ZendeskSectionModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskSectionModelImpl.FINDER_CACHE_ENABLED,
			ZendeskSectionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByZendeskCategoryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByZendeskCategoryId = new FinderPath(
			ZendeskSectionModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskSectionModelImpl.FINDER_CACHE_ENABLED,
			ZendeskSectionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByZendeskCategoryId", new String[] {Long.class.getName()},
			ZendeskSectionModelImpl.ZENDESKCATEGORYID_COLUMN_BITMASK);

		_finderPathCountByZendeskCategoryId = new FinderPath(
			ZendeskSectionModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskSectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByZendeskCategoryId", new String[] {Long.class.getName()});

		_finderPathFetchByZCI_DK = new FinderPath(
			ZendeskSectionModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskSectionModelImpl.FINDER_CACHE_ENABLED,
			ZendeskSectionImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByZCI_DK",
			new String[] {Long.class.getName(), String.class.getName()},
			ZendeskSectionModelImpl.ZENDESKCATEGORYID_COLUMN_BITMASK |
			ZendeskSectionModelImpl.DOCUMENTATIONKEY_COLUMN_BITMASK);

		_finderPathCountByZCI_DK = new FinderPath(
			ZendeskSectionModelImpl.ENTITY_CACHE_ENABLED,
			ZendeskSectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByZCI_DK",
			new String[] {Long.class.getName(), String.class.getName()});

		ZendeskSectionUtil.setPersistence(this);
	}

	public void destroy() {
		ZendeskSectionUtil.setPersistence(null);

		entityCache.removeCache(ZendeskSectionImpl.class.getName());

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

	private static final String _SQL_SELECT_ZENDESKSECTION =
		"SELECT zendeskSection FROM ZendeskSection zendeskSection";

	private static final String _SQL_SELECT_ZENDESKSECTION_WHERE_PKS_IN =
		"SELECT zendeskSection FROM ZendeskSection zendeskSection WHERE zendeskSectionId IN (";

	private static final String _SQL_SELECT_ZENDESKSECTION_WHERE =
		"SELECT zendeskSection FROM ZendeskSection zendeskSection WHERE ";

	private static final String _SQL_COUNT_ZENDESKSECTION =
		"SELECT COUNT(zendeskSection) FROM ZendeskSection zendeskSection";

	private static final String _SQL_COUNT_ZENDESKSECTION_WHERE =
		"SELECT COUNT(zendeskSection) FROM ZendeskSection zendeskSection WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "zendeskSection.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ZendeskSection exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ZendeskSection exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ZendeskSectionPersistenceImpl.class);

}