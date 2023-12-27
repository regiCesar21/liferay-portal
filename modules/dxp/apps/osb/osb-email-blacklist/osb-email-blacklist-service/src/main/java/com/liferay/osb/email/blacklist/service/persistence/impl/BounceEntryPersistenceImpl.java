/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.email.blacklist.service.persistence.impl;

import com.liferay.osb.email.blacklist.exception.NoSuchBounceEntryException;
import com.liferay.osb.email.blacklist.model.BounceEntry;
import com.liferay.osb.email.blacklist.model.impl.BounceEntryImpl;
import com.liferay.osb.email.blacklist.model.impl.BounceEntryModelImpl;
import com.liferay.osb.email.blacklist.service.persistence.BounceEntryPersistence;
import com.liferay.osb.email.blacklist.service.persistence.BounceEntryUtil;
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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.sql.Timestamp;

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
 * The persistence implementation for the bounce entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Jamie Sammons
 * @generated
 */
public class BounceEntryPersistenceImpl
	extends BasePersistenceImpl<BounceEntry> implements BounceEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>BounceEntryUtil</code> to access the bounce entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		BounceEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByLtBounceDate;
	private FinderPath _finderPathWithPaginationCountByLtBounceDate;

	/**
	 * Returns all the bounce entries where bounceDate &lt; &#63;.
	 *
	 * @param bounceDate the bounce date
	 * @return the matching bounce entries
	 */
	@Override
	public List<BounceEntry> findByLtBounceDate(Date bounceDate) {
		return findByLtBounceDate(
			bounceDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the bounce entries where bounceDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BounceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param bounceDate the bounce date
	 * @param start the lower bound of the range of bounce entries
	 * @param end the upper bound of the range of bounce entries (not inclusive)
	 * @return the range of matching bounce entries
	 */
	@Override
	public List<BounceEntry> findByLtBounceDate(
		Date bounceDate, int start, int end) {

		return findByLtBounceDate(bounceDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bounce entries where bounceDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BounceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param bounceDate the bounce date
	 * @param start the lower bound of the range of bounce entries
	 * @param end the upper bound of the range of bounce entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bounce entries
	 */
	@Override
	public List<BounceEntry> findByLtBounceDate(
		Date bounceDate, int start, int end,
		OrderByComparator<BounceEntry> orderByComparator) {

		return findByLtBounceDate(
			bounceDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the bounce entries where bounceDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BounceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param bounceDate the bounce date
	 * @param start the lower bound of the range of bounce entries
	 * @param end the upper bound of the range of bounce entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching bounce entries
	 */
	@Override
	public List<BounceEntry> findByLtBounceDate(
		Date bounceDate, int start, int end,
		OrderByComparator<BounceEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByLtBounceDate;
		finderArgs = new Object[] {
			_getTime(bounceDate), start, end, orderByComparator
		};

		List<BounceEntry> list = null;

		if (useFinderCache) {
			list = (List<BounceEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BounceEntry bounceEntry : list) {
					if (bounceDate.getTime() <= bounceEntry.getBounceDate(
						).getTime()) {

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

			sb.append(_SQL_SELECT_BOUNCEENTRY_WHERE);

			boolean bindBounceDate = false;

			if (bounceDate == null) {
				sb.append(_FINDER_COLUMN_LTBOUNCEDATE_BOUNCEDATE_1);
			}
			else {
				bindBounceDate = true;

				sb.append(_FINDER_COLUMN_LTBOUNCEDATE_BOUNCEDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BounceEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindBounceDate) {
					queryPos.add(new Timestamp(bounceDate.getTime()));
				}

				list = (List<BounceEntry>)QueryUtil.list(
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
	 * Returns the first bounce entry in the ordered set where bounceDate &lt; &#63;.
	 *
	 * @param bounceDate the bounce date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bounce entry
	 * @throws NoSuchBounceEntryException if a matching bounce entry could not be found
	 */
	@Override
	public BounceEntry findByLtBounceDate_First(
			Date bounceDate, OrderByComparator<BounceEntry> orderByComparator)
		throws NoSuchBounceEntryException {

		BounceEntry bounceEntry = fetchByLtBounceDate_First(
			bounceDate, orderByComparator);

		if (bounceEntry != null) {
			return bounceEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("bounceDate<");
		sb.append(bounceDate);

		sb.append("}");

		throw new NoSuchBounceEntryException(sb.toString());
	}

	/**
	 * Returns the first bounce entry in the ordered set where bounceDate &lt; &#63;.
	 *
	 * @param bounceDate the bounce date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bounce entry, or <code>null</code> if a matching bounce entry could not be found
	 */
	@Override
	public BounceEntry fetchByLtBounceDate_First(
		Date bounceDate, OrderByComparator<BounceEntry> orderByComparator) {

		List<BounceEntry> list = findByLtBounceDate(
			bounceDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last bounce entry in the ordered set where bounceDate &lt; &#63;.
	 *
	 * @param bounceDate the bounce date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching bounce entry
	 * @throws NoSuchBounceEntryException if a matching bounce entry could not be found
	 */
	@Override
	public BounceEntry findByLtBounceDate_Last(
			Date bounceDate, OrderByComparator<BounceEntry> orderByComparator)
		throws NoSuchBounceEntryException {

		BounceEntry bounceEntry = fetchByLtBounceDate_Last(
			bounceDate, orderByComparator);

		if (bounceEntry != null) {
			return bounceEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("bounceDate<");
		sb.append(bounceDate);

		sb.append("}");

		throw new NoSuchBounceEntryException(sb.toString());
	}

	/**
	 * Returns the last bounce entry in the ordered set where bounceDate &lt; &#63;.
	 *
	 * @param bounceDate the bounce date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching bounce entry, or <code>null</code> if a matching bounce entry could not be found
	 */
	@Override
	public BounceEntry fetchByLtBounceDate_Last(
		Date bounceDate, OrderByComparator<BounceEntry> orderByComparator) {

		int count = countByLtBounceDate(bounceDate);

		if (count == 0) {
			return null;
		}

		List<BounceEntry> list = findByLtBounceDate(
			bounceDate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the bounce entries before and after the current bounce entry in the ordered set where bounceDate &lt; &#63;.
	 *
	 * @param bounceEntryId the primary key of the current bounce entry
	 * @param bounceDate the bounce date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next bounce entry
	 * @throws NoSuchBounceEntryException if a bounce entry with the primary key could not be found
	 */
	@Override
	public BounceEntry[] findByLtBounceDate_PrevAndNext(
			long bounceEntryId, Date bounceDate,
			OrderByComparator<BounceEntry> orderByComparator)
		throws NoSuchBounceEntryException {

		BounceEntry bounceEntry = findByPrimaryKey(bounceEntryId);

		Session session = null;

		try {
			session = openSession();

			BounceEntry[] array = new BounceEntryImpl[3];

			array[0] = getByLtBounceDate_PrevAndNext(
				session, bounceEntry, bounceDate, orderByComparator, true);

			array[1] = bounceEntry;

			array[2] = getByLtBounceDate_PrevAndNext(
				session, bounceEntry, bounceDate, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected BounceEntry getByLtBounceDate_PrevAndNext(
		Session session, BounceEntry bounceEntry, Date bounceDate,
		OrderByComparator<BounceEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_BOUNCEENTRY_WHERE);

		boolean bindBounceDate = false;

		if (bounceDate == null) {
			sb.append(_FINDER_COLUMN_LTBOUNCEDATE_BOUNCEDATE_1);
		}
		else {
			bindBounceDate = true;

			sb.append(_FINDER_COLUMN_LTBOUNCEDATE_BOUNCEDATE_2);
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
			sb.append(BounceEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindBounceDate) {
			queryPos.add(new Timestamp(bounceDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(bounceEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<BounceEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the bounce entries where bounceDate &lt; &#63; from the database.
	 *
	 * @param bounceDate the bounce date
	 */
	@Override
	public void removeByLtBounceDate(Date bounceDate) {
		for (BounceEntry bounceEntry :
				findByLtBounceDate(
					bounceDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(bounceEntry);
		}
	}

	/**
	 * Returns the number of bounce entries where bounceDate &lt; &#63;.
	 *
	 * @param bounceDate the bounce date
	 * @return the number of matching bounce entries
	 */
	@Override
	public int countByLtBounceDate(Date bounceDate) {
		FinderPath finderPath = _finderPathWithPaginationCountByLtBounceDate;

		Object[] finderArgs = new Object[] {_getTime(bounceDate)};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_BOUNCEENTRY_WHERE);

			boolean bindBounceDate = false;

			if (bounceDate == null) {
				sb.append(_FINDER_COLUMN_LTBOUNCEDATE_BOUNCEDATE_1);
			}
			else {
				bindBounceDate = true;

				sb.append(_FINDER_COLUMN_LTBOUNCEDATE_BOUNCEDATE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindBounceDate) {
					queryPos.add(new Timestamp(bounceDate.getTime()));
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

	private static final String _FINDER_COLUMN_LTBOUNCEDATE_BOUNCEDATE_1 =
		"bounceEntry.bounceDate IS NULL";

	private static final String _FINDER_COLUMN_LTBOUNCEDATE_BOUNCEDATE_2 =
		"bounceEntry.bounceDate < ?";

	private FinderPath _finderPathWithPaginationFindByEA_GtBD;
	private FinderPath _finderPathWithPaginationCountByEA_GtBD;

	/**
	 * Returns all the bounce entries where emailAddress = &#63; and bounceDate &ge; &#63;.
	 *
	 * @param emailAddress the email address
	 * @param bounceDate the bounce date
	 * @return the matching bounce entries
	 */
	@Override
	public List<BounceEntry> findByEA_GtBD(
		String emailAddress, Date bounceDate) {

		return findByEA_GtBD(
			emailAddress, bounceDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the bounce entries where emailAddress = &#63; and bounceDate &ge; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BounceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param emailAddress the email address
	 * @param bounceDate the bounce date
	 * @param start the lower bound of the range of bounce entries
	 * @param end the upper bound of the range of bounce entries (not inclusive)
	 * @return the range of matching bounce entries
	 */
	@Override
	public List<BounceEntry> findByEA_GtBD(
		String emailAddress, Date bounceDate, int start, int end) {

		return findByEA_GtBD(emailAddress, bounceDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bounce entries where emailAddress = &#63; and bounceDate &ge; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BounceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param emailAddress the email address
	 * @param bounceDate the bounce date
	 * @param start the lower bound of the range of bounce entries
	 * @param end the upper bound of the range of bounce entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bounce entries
	 */
	@Override
	public List<BounceEntry> findByEA_GtBD(
		String emailAddress, Date bounceDate, int start, int end,
		OrderByComparator<BounceEntry> orderByComparator) {

		return findByEA_GtBD(
			emailAddress, bounceDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the bounce entries where emailAddress = &#63; and bounceDate &ge; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BounceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param emailAddress the email address
	 * @param bounceDate the bounce date
	 * @param start the lower bound of the range of bounce entries
	 * @param end the upper bound of the range of bounce entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching bounce entries
	 */
	@Override
	public List<BounceEntry> findByEA_GtBD(
		String emailAddress, Date bounceDate, int start, int end,
		OrderByComparator<BounceEntry> orderByComparator,
		boolean useFinderCache) {

		emailAddress = Objects.toString(emailAddress, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByEA_GtBD;
		finderArgs = new Object[] {
			emailAddress, _getTime(bounceDate), start, end, orderByComparator
		};

		List<BounceEntry> list = null;

		if (useFinderCache) {
			list = (List<BounceEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BounceEntry bounceEntry : list) {
					if (!emailAddress.equals(bounceEntry.getEmailAddress()) ||
						(bounceDate.getTime() > bounceEntry.getBounceDate(
						).getTime())) {

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

			sb.append(_SQL_SELECT_BOUNCEENTRY_WHERE);

			boolean bindEmailAddress = false;

			if (emailAddress.isEmpty()) {
				sb.append(_FINDER_COLUMN_EA_GTBD_EMAILADDRESS_3);
			}
			else {
				bindEmailAddress = true;

				sb.append(_FINDER_COLUMN_EA_GTBD_EMAILADDRESS_2);
			}

			boolean bindBounceDate = false;

			if (bounceDate == null) {
				sb.append(_FINDER_COLUMN_EA_GTBD_BOUNCEDATE_1);
			}
			else {
				bindBounceDate = true;

				sb.append(_FINDER_COLUMN_EA_GTBD_BOUNCEDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BounceEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindEmailAddress) {
					queryPos.add(emailAddress);
				}

				if (bindBounceDate) {
					queryPos.add(new Timestamp(bounceDate.getTime()));
				}

				list = (List<BounceEntry>)QueryUtil.list(
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
	 * Returns the first bounce entry in the ordered set where emailAddress = &#63; and bounceDate &ge; &#63;.
	 *
	 * @param emailAddress the email address
	 * @param bounceDate the bounce date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bounce entry
	 * @throws NoSuchBounceEntryException if a matching bounce entry could not be found
	 */
	@Override
	public BounceEntry findByEA_GtBD_First(
			String emailAddress, Date bounceDate,
			OrderByComparator<BounceEntry> orderByComparator)
		throws NoSuchBounceEntryException {

		BounceEntry bounceEntry = fetchByEA_GtBD_First(
			emailAddress, bounceDate, orderByComparator);

		if (bounceEntry != null) {
			return bounceEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("emailAddress=");
		sb.append(emailAddress);

		sb.append(", bounceDate>=");
		sb.append(bounceDate);

		sb.append("}");

		throw new NoSuchBounceEntryException(sb.toString());
	}

	/**
	 * Returns the first bounce entry in the ordered set where emailAddress = &#63; and bounceDate &ge; &#63;.
	 *
	 * @param emailAddress the email address
	 * @param bounceDate the bounce date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bounce entry, or <code>null</code> if a matching bounce entry could not be found
	 */
	@Override
	public BounceEntry fetchByEA_GtBD_First(
		String emailAddress, Date bounceDate,
		OrderByComparator<BounceEntry> orderByComparator) {

		List<BounceEntry> list = findByEA_GtBD(
			emailAddress, bounceDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last bounce entry in the ordered set where emailAddress = &#63; and bounceDate &ge; &#63;.
	 *
	 * @param emailAddress the email address
	 * @param bounceDate the bounce date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching bounce entry
	 * @throws NoSuchBounceEntryException if a matching bounce entry could not be found
	 */
	@Override
	public BounceEntry findByEA_GtBD_Last(
			String emailAddress, Date bounceDate,
			OrderByComparator<BounceEntry> orderByComparator)
		throws NoSuchBounceEntryException {

		BounceEntry bounceEntry = fetchByEA_GtBD_Last(
			emailAddress, bounceDate, orderByComparator);

		if (bounceEntry != null) {
			return bounceEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("emailAddress=");
		sb.append(emailAddress);

		sb.append(", bounceDate>=");
		sb.append(bounceDate);

		sb.append("}");

		throw new NoSuchBounceEntryException(sb.toString());
	}

	/**
	 * Returns the last bounce entry in the ordered set where emailAddress = &#63; and bounceDate &ge; &#63;.
	 *
	 * @param emailAddress the email address
	 * @param bounceDate the bounce date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching bounce entry, or <code>null</code> if a matching bounce entry could not be found
	 */
	@Override
	public BounceEntry fetchByEA_GtBD_Last(
		String emailAddress, Date bounceDate,
		OrderByComparator<BounceEntry> orderByComparator) {

		int count = countByEA_GtBD(emailAddress, bounceDate);

		if (count == 0) {
			return null;
		}

		List<BounceEntry> list = findByEA_GtBD(
			emailAddress, bounceDate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the bounce entries before and after the current bounce entry in the ordered set where emailAddress = &#63; and bounceDate &ge; &#63;.
	 *
	 * @param bounceEntryId the primary key of the current bounce entry
	 * @param emailAddress the email address
	 * @param bounceDate the bounce date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next bounce entry
	 * @throws NoSuchBounceEntryException if a bounce entry with the primary key could not be found
	 */
	@Override
	public BounceEntry[] findByEA_GtBD_PrevAndNext(
			long bounceEntryId, String emailAddress, Date bounceDate,
			OrderByComparator<BounceEntry> orderByComparator)
		throws NoSuchBounceEntryException {

		emailAddress = Objects.toString(emailAddress, "");

		BounceEntry bounceEntry = findByPrimaryKey(bounceEntryId);

		Session session = null;

		try {
			session = openSession();

			BounceEntry[] array = new BounceEntryImpl[3];

			array[0] = getByEA_GtBD_PrevAndNext(
				session, bounceEntry, emailAddress, bounceDate,
				orderByComparator, true);

			array[1] = bounceEntry;

			array[2] = getByEA_GtBD_PrevAndNext(
				session, bounceEntry, emailAddress, bounceDate,
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

	protected BounceEntry getByEA_GtBD_PrevAndNext(
		Session session, BounceEntry bounceEntry, String emailAddress,
		Date bounceDate, OrderByComparator<BounceEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_BOUNCEENTRY_WHERE);

		boolean bindEmailAddress = false;

		if (emailAddress.isEmpty()) {
			sb.append(_FINDER_COLUMN_EA_GTBD_EMAILADDRESS_3);
		}
		else {
			bindEmailAddress = true;

			sb.append(_FINDER_COLUMN_EA_GTBD_EMAILADDRESS_2);
		}

		boolean bindBounceDate = false;

		if (bounceDate == null) {
			sb.append(_FINDER_COLUMN_EA_GTBD_BOUNCEDATE_1);
		}
		else {
			bindBounceDate = true;

			sb.append(_FINDER_COLUMN_EA_GTBD_BOUNCEDATE_2);
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
			sb.append(BounceEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindEmailAddress) {
			queryPos.add(emailAddress);
		}

		if (bindBounceDate) {
			queryPos.add(new Timestamp(bounceDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(bounceEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<BounceEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the bounce entries where emailAddress = &#63; and bounceDate &ge; &#63; from the database.
	 *
	 * @param emailAddress the email address
	 * @param bounceDate the bounce date
	 */
	@Override
	public void removeByEA_GtBD(String emailAddress, Date bounceDate) {
		for (BounceEntry bounceEntry :
				findByEA_GtBD(
					emailAddress, bounceDate, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(bounceEntry);
		}
	}

	/**
	 * Returns the number of bounce entries where emailAddress = &#63; and bounceDate &ge; &#63;.
	 *
	 * @param emailAddress the email address
	 * @param bounceDate the bounce date
	 * @return the number of matching bounce entries
	 */
	@Override
	public int countByEA_GtBD(String emailAddress, Date bounceDate) {
		emailAddress = Objects.toString(emailAddress, "");

		FinderPath finderPath = _finderPathWithPaginationCountByEA_GtBD;

		Object[] finderArgs = new Object[] {emailAddress, _getTime(bounceDate)};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_BOUNCEENTRY_WHERE);

			boolean bindEmailAddress = false;

			if (emailAddress.isEmpty()) {
				sb.append(_FINDER_COLUMN_EA_GTBD_EMAILADDRESS_3);
			}
			else {
				bindEmailAddress = true;

				sb.append(_FINDER_COLUMN_EA_GTBD_EMAILADDRESS_2);
			}

			boolean bindBounceDate = false;

			if (bounceDate == null) {
				sb.append(_FINDER_COLUMN_EA_GTBD_BOUNCEDATE_1);
			}
			else {
				bindBounceDate = true;

				sb.append(_FINDER_COLUMN_EA_GTBD_BOUNCEDATE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindEmailAddress) {
					queryPos.add(emailAddress);
				}

				if (bindBounceDate) {
					queryPos.add(new Timestamp(bounceDate.getTime()));
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

	private static final String _FINDER_COLUMN_EA_GTBD_EMAILADDRESS_2 =
		"bounceEntry.emailAddress = ? AND ";

	private static final String _FINDER_COLUMN_EA_GTBD_EMAILADDRESS_3 =
		"(bounceEntry.emailAddress IS NULL OR bounceEntry.emailAddress = '') AND ";

	private static final String _FINDER_COLUMN_EA_GTBD_BOUNCEDATE_1 =
		"bounceEntry.bounceDate IS NULL";

	private static final String _FINDER_COLUMN_EA_GTBD_BOUNCEDATE_2 =
		"bounceEntry.bounceDate >= ?";

	public BounceEntryPersistenceImpl() {
		setModelClass(BounceEntry.class);
	}

	/**
	 * Caches the bounce entry in the entity cache if it is enabled.
	 *
	 * @param bounceEntry the bounce entry
	 */
	@Override
	public void cacheResult(BounceEntry bounceEntry) {
		entityCache.putResult(
			BounceEntryModelImpl.ENTITY_CACHE_ENABLED, BounceEntryImpl.class,
			bounceEntry.getPrimaryKey(), bounceEntry);

		bounceEntry.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the bounce entries in the entity cache if it is enabled.
	 *
	 * @param bounceEntries the bounce entries
	 */
	@Override
	public void cacheResult(List<BounceEntry> bounceEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (bounceEntries.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (BounceEntry bounceEntry : bounceEntries) {
			if (entityCache.getResult(
					BounceEntryModelImpl.ENTITY_CACHE_ENABLED,
					BounceEntryImpl.class, bounceEntry.getPrimaryKey()) ==
						null) {

				cacheResult(bounceEntry);
			}
			else {
				bounceEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all bounce entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(BounceEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the bounce entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(BounceEntry bounceEntry) {
		entityCache.removeResult(
			BounceEntryModelImpl.ENTITY_CACHE_ENABLED, BounceEntryImpl.class,
			bounceEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<BounceEntry> bounceEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (BounceEntry bounceEntry : bounceEntries) {
			entityCache.removeResult(
				BounceEntryModelImpl.ENTITY_CACHE_ENABLED,
				BounceEntryImpl.class, bounceEntry.getPrimaryKey());
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				BounceEntryModelImpl.ENTITY_CACHE_ENABLED,
				BounceEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new bounce entry with the primary key. Does not add the bounce entry to the database.
	 *
	 * @param bounceEntryId the primary key for the new bounce entry
	 * @return the new bounce entry
	 */
	@Override
	public BounceEntry create(long bounceEntryId) {
		BounceEntry bounceEntry = new BounceEntryImpl();

		bounceEntry.setNew(true);
		bounceEntry.setPrimaryKey(bounceEntryId);

		return bounceEntry;
	}

	/**
	 * Removes the bounce entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param bounceEntryId the primary key of the bounce entry
	 * @return the bounce entry that was removed
	 * @throws NoSuchBounceEntryException if a bounce entry with the primary key could not be found
	 */
	@Override
	public BounceEntry remove(long bounceEntryId)
		throws NoSuchBounceEntryException {

		return remove((Serializable)bounceEntryId);
	}

	/**
	 * Removes the bounce entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the bounce entry
	 * @return the bounce entry that was removed
	 * @throws NoSuchBounceEntryException if a bounce entry with the primary key could not be found
	 */
	@Override
	public BounceEntry remove(Serializable primaryKey)
		throws NoSuchBounceEntryException {

		Session session = null;

		try {
			session = openSession();

			BounceEntry bounceEntry = (BounceEntry)session.get(
				BounceEntryImpl.class, primaryKey);

			if (bounceEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchBounceEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(bounceEntry);
		}
		catch (NoSuchBounceEntryException noSuchEntityException) {
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
	protected BounceEntry removeImpl(BounceEntry bounceEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(bounceEntry)) {
				bounceEntry = (BounceEntry)session.get(
					BounceEntryImpl.class, bounceEntry.getPrimaryKeyObj());
			}

			if (bounceEntry != null) {
				session.delete(bounceEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (bounceEntry != null) {
			clearCache(bounceEntry);
		}

		return bounceEntry;
	}

	@Override
	public BounceEntry updateImpl(BounceEntry bounceEntry) {
		boolean isNew = bounceEntry.isNew();

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(bounceEntry);

				bounceEntry.setNew(false);
			}
			else {
				bounceEntry = (BounceEntry)session.merge(bounceEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!BounceEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			BounceEntryModelImpl.ENTITY_CACHE_ENABLED, BounceEntryImpl.class,
			bounceEntry.getPrimaryKey(), bounceEntry, false);

		bounceEntry.resetOriginalValues();

		return bounceEntry;
	}

	/**
	 * Returns the bounce entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the bounce entry
	 * @return the bounce entry
	 * @throws NoSuchBounceEntryException if a bounce entry with the primary key could not be found
	 */
	@Override
	public BounceEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchBounceEntryException {

		BounceEntry bounceEntry = fetchByPrimaryKey(primaryKey);

		if (bounceEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchBounceEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return bounceEntry;
	}

	/**
	 * Returns the bounce entry with the primary key or throws a <code>NoSuchBounceEntryException</code> if it could not be found.
	 *
	 * @param bounceEntryId the primary key of the bounce entry
	 * @return the bounce entry
	 * @throws NoSuchBounceEntryException if a bounce entry with the primary key could not be found
	 */
	@Override
	public BounceEntry findByPrimaryKey(long bounceEntryId)
		throws NoSuchBounceEntryException {

		return findByPrimaryKey((Serializable)bounceEntryId);
	}

	/**
	 * Returns the bounce entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the bounce entry
	 * @return the bounce entry, or <code>null</code> if a bounce entry with the primary key could not be found
	 */
	@Override
	public BounceEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			BounceEntryModelImpl.ENTITY_CACHE_ENABLED, BounceEntryImpl.class,
			primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		BounceEntry bounceEntry = (BounceEntry)serializable;

		if (bounceEntry == null) {
			Session session = null;

			try {
				session = openSession();

				bounceEntry = (BounceEntry)session.get(
					BounceEntryImpl.class, primaryKey);

				if (bounceEntry != null) {
					cacheResult(bounceEntry);
				}
				else {
					entityCache.putResult(
						BounceEntryModelImpl.ENTITY_CACHE_ENABLED,
						BounceEntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					BounceEntryModelImpl.ENTITY_CACHE_ENABLED,
					BounceEntryImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return bounceEntry;
	}

	/**
	 * Returns the bounce entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param bounceEntryId the primary key of the bounce entry
	 * @return the bounce entry, or <code>null</code> if a bounce entry with the primary key could not be found
	 */
	@Override
	public BounceEntry fetchByPrimaryKey(long bounceEntryId) {
		return fetchByPrimaryKey((Serializable)bounceEntryId);
	}

	@Override
	public Map<Serializable, BounceEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, BounceEntry> map =
			new HashMap<Serializable, BounceEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			BounceEntry bounceEntry = fetchByPrimaryKey(primaryKey);

			if (bounceEntry != null) {
				map.put(primaryKey, bounceEntry);
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
				BounceEntryModelImpl.ENTITY_CACHE_ENABLED,
				BounceEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (BounceEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_BOUNCEENTRY_WHERE_PKS_IN);

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

			for (BounceEntry bounceEntry : (List<BounceEntry>)query.list()) {
				map.put(bounceEntry.getPrimaryKeyObj(), bounceEntry);

				cacheResult(bounceEntry);

				uncachedPrimaryKeys.remove(bounceEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					BounceEntryModelImpl.ENTITY_CACHE_ENABLED,
					BounceEntryImpl.class, primaryKey, nullModel);
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
	 * Returns all the bounce entries.
	 *
	 * @return the bounce entries
	 */
	@Override
	public List<BounceEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the bounce entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BounceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of bounce entries
	 * @param end the upper bound of the range of bounce entries (not inclusive)
	 * @return the range of bounce entries
	 */
	@Override
	public List<BounceEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the bounce entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BounceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of bounce entries
	 * @param end the upper bound of the range of bounce entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of bounce entries
	 */
	@Override
	public List<BounceEntry> findAll(
		int start, int end, OrderByComparator<BounceEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the bounce entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BounceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of bounce entries
	 * @param end the upper bound of the range of bounce entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of bounce entries
	 */
	@Override
	public List<BounceEntry> findAll(
		int start, int end, OrderByComparator<BounceEntry> orderByComparator,
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

		List<BounceEntry> list = null;

		if (useFinderCache) {
			list = (List<BounceEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_BOUNCEENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_BOUNCEENTRY;

				sql = sql.concat(BounceEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<BounceEntry>)QueryUtil.list(
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
	 * Removes all the bounce entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (BounceEntry bounceEntry : findAll()) {
			remove(bounceEntry);
		}
	}

	/**
	 * Returns the number of bounce entries.
	 *
	 * @return the number of bounce entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_BOUNCEENTRY);

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
		return BounceEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the bounce entry persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			BounceEntryModelImpl.ENTITY_CACHE_ENABLED,
			BounceEntryModelImpl.FINDER_CACHE_ENABLED, BounceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			BounceEntryModelImpl.ENTITY_CACHE_ENABLED,
			BounceEntryModelImpl.FINDER_CACHE_ENABLED, BounceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			BounceEntryModelImpl.ENTITY_CACHE_ENABLED,
			BounceEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByLtBounceDate = new FinderPath(
			BounceEntryModelImpl.ENTITY_CACHE_ENABLED,
			BounceEntryModelImpl.FINDER_CACHE_ENABLED, BounceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLtBounceDate",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByLtBounceDate = new FinderPath(
			BounceEntryModelImpl.ENTITY_CACHE_ENABLED,
			BounceEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtBounceDate",
			new String[] {Date.class.getName()});

		_finderPathWithPaginationFindByEA_GtBD = new FinderPath(
			BounceEntryModelImpl.ENTITY_CACHE_ENABLED,
			BounceEntryModelImpl.FINDER_CACHE_ENABLED, BounceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByEA_GtBD",
			new String[] {
				String.class.getName(), Date.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByEA_GtBD = new FinderPath(
			BounceEntryModelImpl.ENTITY_CACHE_ENABLED,
			BounceEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByEA_GtBD",
			new String[] {String.class.getName(), Date.class.getName()});

		BounceEntryUtil.setPersistence(this);
	}

	public void destroy() {
		BounceEntryUtil.setPersistence(null);

		entityCache.removeCache(BounceEntryImpl.class.getName());

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

	private static Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_BOUNCEENTRY =
		"SELECT bounceEntry FROM BounceEntry bounceEntry";

	private static final String _SQL_SELECT_BOUNCEENTRY_WHERE_PKS_IN =
		"SELECT bounceEntry FROM BounceEntry bounceEntry WHERE bounceEntryId IN (";

	private static final String _SQL_SELECT_BOUNCEENTRY_WHERE =
		"SELECT bounceEntry FROM BounceEntry bounceEntry WHERE ";

	private static final String _SQL_COUNT_BOUNCEENTRY =
		"SELECT COUNT(bounceEntry) FROM BounceEntry bounceEntry";

	private static final String _SQL_COUNT_BOUNCEENTRY_WHERE =
		"SELECT COUNT(bounceEntry) FROM BounceEntry bounceEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "bounceEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No BounceEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No BounceEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		BounceEntryPersistenceImpl.class);

}