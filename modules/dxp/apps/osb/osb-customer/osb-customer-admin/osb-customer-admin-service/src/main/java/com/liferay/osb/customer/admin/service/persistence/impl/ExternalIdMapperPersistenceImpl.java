/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.admin.service.persistence.impl;

import com.liferay.osb.customer.admin.exception.NoSuchExternalIdMapperException;
import com.liferay.osb.customer.admin.model.ExternalIdMapper;
import com.liferay.osb.customer.admin.model.impl.ExternalIdMapperImpl;
import com.liferay.osb.customer.admin.model.impl.ExternalIdMapperModelImpl;
import com.liferay.osb.customer.admin.service.persistence.ExternalIdMapperPersistence;
import com.liferay.osb.customer.admin.service.persistence.ExternalIdMapperUtil;
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
 * The persistence implementation for the external ID mapper service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ExternalIdMapperPersistenceImpl
	extends BasePersistenceImpl<ExternalIdMapper>
	implements ExternalIdMapperPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ExternalIdMapperUtil</code> to access the external ID mapper persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ExternalIdMapperImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the external ID mappers where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching external ID mappers
	 */
	@Override
	public List<ExternalIdMapper> findByC_C(long classNameId, long classPK) {
		return findByC_C(
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the external ID mappers where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExternalIdMapperModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of external ID mappers
	 * @param end the upper bound of the range of external ID mappers (not inclusive)
	 * @return the range of matching external ID mappers
	 */
	@Override
	public List<ExternalIdMapper> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the external ID mappers where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExternalIdMapperModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of external ID mappers
	 * @param end the upper bound of the range of external ID mappers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching external ID mappers
	 */
	@Override
	public List<ExternalIdMapper> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<ExternalIdMapper> orderByComparator) {

		return findByC_C(
			classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the external ID mappers where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExternalIdMapperModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of external ID mappers
	 * @param end the upper bound of the range of external ID mappers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching external ID mappers
	 */
	@Override
	public List<ExternalIdMapper> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<ExternalIdMapper> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {classNameId, classPK};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				classNameId, classPK, start, end, orderByComparator
			};
		}

		List<ExternalIdMapper> list = null;

		if (useFinderCache) {
			list = (List<ExternalIdMapper>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ExternalIdMapper externalIdMapper : list) {
					if ((classNameId != externalIdMapper.getClassNameId()) ||
						(classPK != externalIdMapper.getClassPK())) {

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

			sb.append(_SQL_SELECT_EXTERNALIDMAPPER_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ExternalIdMapperModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<ExternalIdMapper>)QueryUtil.list(
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
	 * Returns the first external ID mapper in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching external ID mapper
	 * @throws NoSuchExternalIdMapperException if a matching external ID mapper could not be found
	 */
	@Override
	public ExternalIdMapper findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<ExternalIdMapper> orderByComparator)
		throws NoSuchExternalIdMapperException {

		ExternalIdMapper externalIdMapper = fetchByC_C_First(
			classNameId, classPK, orderByComparator);

		if (externalIdMapper != null) {
			return externalIdMapper;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchExternalIdMapperException(sb.toString());
	}

	/**
	 * Returns the first external ID mapper in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching external ID mapper, or <code>null</code> if a matching external ID mapper could not be found
	 */
	@Override
	public ExternalIdMapper fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<ExternalIdMapper> orderByComparator) {

		List<ExternalIdMapper> list = findByC_C(
			classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last external ID mapper in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching external ID mapper
	 * @throws NoSuchExternalIdMapperException if a matching external ID mapper could not be found
	 */
	@Override
	public ExternalIdMapper findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<ExternalIdMapper> orderByComparator)
		throws NoSuchExternalIdMapperException {

		ExternalIdMapper externalIdMapper = fetchByC_C_Last(
			classNameId, classPK, orderByComparator);

		if (externalIdMapper != null) {
			return externalIdMapper;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchExternalIdMapperException(sb.toString());
	}

	/**
	 * Returns the last external ID mapper in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching external ID mapper, or <code>null</code> if a matching external ID mapper could not be found
	 */
	@Override
	public ExternalIdMapper fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<ExternalIdMapper> orderByComparator) {

		int count = countByC_C(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<ExternalIdMapper> list = findByC_C(
			classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the external ID mappers before and after the current external ID mapper in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param externalIdMapperId the primary key of the current external ID mapper
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next external ID mapper
	 * @throws NoSuchExternalIdMapperException if a external ID mapper with the primary key could not be found
	 */
	@Override
	public ExternalIdMapper[] findByC_C_PrevAndNext(
			long externalIdMapperId, long classNameId, long classPK,
			OrderByComparator<ExternalIdMapper> orderByComparator)
		throws NoSuchExternalIdMapperException {

		ExternalIdMapper externalIdMapper = findByPrimaryKey(
			externalIdMapperId);

		Session session = null;

		try {
			session = openSession();

			ExternalIdMapper[] array = new ExternalIdMapperImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, externalIdMapper, classNameId, classPK,
				orderByComparator, true);

			array[1] = externalIdMapper;

			array[2] = getByC_C_PrevAndNext(
				session, externalIdMapper, classNameId, classPK,
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

	protected ExternalIdMapper getByC_C_PrevAndNext(
		Session session, ExternalIdMapper externalIdMapper, long classNameId,
		long classPK, OrderByComparator<ExternalIdMapper> orderByComparator,
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

		sb.append(_SQL_SELECT_EXTERNALIDMAPPER_WHERE);

		sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

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
			sb.append(ExternalIdMapperModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						externalIdMapper)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ExternalIdMapper> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the external ID mappers where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		for (ExternalIdMapper externalIdMapper :
				findByC_C(
					classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(externalIdMapper);
		}
	}

	/**
	 * Returns the number of external ID mappers where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching external ID mappers
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {classNameId, classPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_EXTERNALIDMAPPER_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"externalIdMapper.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"externalIdMapper.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByC_C_T;
	private FinderPath _finderPathWithoutPaginationFindByC_C_T;
	private FinderPath _finderPathCountByC_C_T;

	/**
	 * Returns all the external ID mappers where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the matching external ID mappers
	 */
	@Override
	public List<ExternalIdMapper> findByC_C_T(
		long classNameId, long classPK, int type) {

		return findByC_C_T(
			classNameId, classPK, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the external ID mappers where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExternalIdMapperModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of external ID mappers
	 * @param end the upper bound of the range of external ID mappers (not inclusive)
	 * @return the range of matching external ID mappers
	 */
	@Override
	public List<ExternalIdMapper> findByC_C_T(
		long classNameId, long classPK, int type, int start, int end) {

		return findByC_C_T(classNameId, classPK, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the external ID mappers where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExternalIdMapperModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of external ID mappers
	 * @param end the upper bound of the range of external ID mappers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching external ID mappers
	 */
	@Override
	public List<ExternalIdMapper> findByC_C_T(
		long classNameId, long classPK, int type, int start, int end,
		OrderByComparator<ExternalIdMapper> orderByComparator) {

		return findByC_C_T(
			classNameId, classPK, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the external ID mappers where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExternalIdMapperModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of external ID mappers
	 * @param end the upper bound of the range of external ID mappers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching external ID mappers
	 */
	@Override
	public List<ExternalIdMapper> findByC_C_T(
		long classNameId, long classPK, int type, int start, int end,
		OrderByComparator<ExternalIdMapper> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C_T;
				finderArgs = new Object[] {classNameId, classPK, type};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C_T;
			finderArgs = new Object[] {
				classNameId, classPK, type, start, end, orderByComparator
			};
		}

		List<ExternalIdMapper> list = null;

		if (useFinderCache) {
			list = (List<ExternalIdMapper>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ExternalIdMapper externalIdMapper : list) {
					if ((classNameId != externalIdMapper.getClassNameId()) ||
						(classPK != externalIdMapper.getClassPK()) ||
						(type != externalIdMapper.getType())) {

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

			sb.append(_SQL_SELECT_EXTERNALIDMAPPER_WHERE);

			sb.append(_FINDER_COLUMN_C_C_T_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_T_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ExternalIdMapperModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(type);

				list = (List<ExternalIdMapper>)QueryUtil.list(
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
	 * Returns the first external ID mapper in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching external ID mapper
	 * @throws NoSuchExternalIdMapperException if a matching external ID mapper could not be found
	 */
	@Override
	public ExternalIdMapper findByC_C_T_First(
			long classNameId, long classPK, int type,
			OrderByComparator<ExternalIdMapper> orderByComparator)
		throws NoSuchExternalIdMapperException {

		ExternalIdMapper externalIdMapper = fetchByC_C_T_First(
			classNameId, classPK, type, orderByComparator);

		if (externalIdMapper != null) {
			return externalIdMapper;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchExternalIdMapperException(sb.toString());
	}

	/**
	 * Returns the first external ID mapper in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching external ID mapper, or <code>null</code> if a matching external ID mapper could not be found
	 */
	@Override
	public ExternalIdMapper fetchByC_C_T_First(
		long classNameId, long classPK, int type,
		OrderByComparator<ExternalIdMapper> orderByComparator) {

		List<ExternalIdMapper> list = findByC_C_T(
			classNameId, classPK, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last external ID mapper in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching external ID mapper
	 * @throws NoSuchExternalIdMapperException if a matching external ID mapper could not be found
	 */
	@Override
	public ExternalIdMapper findByC_C_T_Last(
			long classNameId, long classPK, int type,
			OrderByComparator<ExternalIdMapper> orderByComparator)
		throws NoSuchExternalIdMapperException {

		ExternalIdMapper externalIdMapper = fetchByC_C_T_Last(
			classNameId, classPK, type, orderByComparator);

		if (externalIdMapper != null) {
			return externalIdMapper;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchExternalIdMapperException(sb.toString());
	}

	/**
	 * Returns the last external ID mapper in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching external ID mapper, or <code>null</code> if a matching external ID mapper could not be found
	 */
	@Override
	public ExternalIdMapper fetchByC_C_T_Last(
		long classNameId, long classPK, int type,
		OrderByComparator<ExternalIdMapper> orderByComparator) {

		int count = countByC_C_T(classNameId, classPK, type);

		if (count == 0) {
			return null;
		}

		List<ExternalIdMapper> list = findByC_C_T(
			classNameId, classPK, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the external ID mappers before and after the current external ID mapper in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param externalIdMapperId the primary key of the current external ID mapper
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next external ID mapper
	 * @throws NoSuchExternalIdMapperException if a external ID mapper with the primary key could not be found
	 */
	@Override
	public ExternalIdMapper[] findByC_C_T_PrevAndNext(
			long externalIdMapperId, long classNameId, long classPK, int type,
			OrderByComparator<ExternalIdMapper> orderByComparator)
		throws NoSuchExternalIdMapperException {

		ExternalIdMapper externalIdMapper = findByPrimaryKey(
			externalIdMapperId);

		Session session = null;

		try {
			session = openSession();

			ExternalIdMapper[] array = new ExternalIdMapperImpl[3];

			array[0] = getByC_C_T_PrevAndNext(
				session, externalIdMapper, classNameId, classPK, type,
				orderByComparator, true);

			array[1] = externalIdMapper;

			array[2] = getByC_C_T_PrevAndNext(
				session, externalIdMapper, classNameId, classPK, type,
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

	protected ExternalIdMapper getByC_C_T_PrevAndNext(
		Session session, ExternalIdMapper externalIdMapper, long classNameId,
		long classPK, int type,
		OrderByComparator<ExternalIdMapper> orderByComparator,
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

		sb.append(_SQL_SELECT_EXTERNALIDMAPPER_WHERE);

		sb.append(_FINDER_COLUMN_C_C_T_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_T_CLASSPK_2);

		sb.append(_FINDER_COLUMN_C_C_T_TYPE_2);

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
			sb.append(ExternalIdMapperModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						externalIdMapper)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ExternalIdMapper> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the external ID mappers where classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 */
	@Override
	public void removeByC_C_T(long classNameId, long classPK, int type) {
		for (ExternalIdMapper externalIdMapper :
				findByC_C_T(
					classNameId, classPK, type, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(externalIdMapper);
		}
	}

	/**
	 * Returns the number of external ID mappers where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the number of matching external ID mappers
	 */
	@Override
	public int countByC_C_T(long classNameId, long classPK, int type) {
		FinderPath finderPath = _finderPathCountByC_C_T;

		Object[] finderArgs = new Object[] {classNameId, classPK, type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_EXTERNALIDMAPPER_WHERE);

			sb.append(_FINDER_COLUMN_C_C_T_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_T_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_T_CLASSNAMEID_2 =
		"externalIdMapper.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_T_CLASSPK_2 =
		"externalIdMapper.classPK = ? AND ";

	private static final String _FINDER_COLUMN_C_C_T_TYPE_2 =
		"externalIdMapper.type = ?";

	private FinderPath _finderPathWithPaginationFindByC_T_EI;
	private FinderPath _finderPathWithoutPaginationFindByC_T_EI;
	private FinderPath _finderPathCountByC_T_EI;

	/**
	 * Returns all the external ID mappers where classNameId = &#63; and type = &#63; and externalId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param externalId the external ID
	 * @return the matching external ID mappers
	 */
	@Override
	public List<ExternalIdMapper> findByC_T_EI(
		long classNameId, int type, String externalId) {

		return findByC_T_EI(
			classNameId, type, externalId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the external ID mappers where classNameId = &#63; and type = &#63; and externalId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExternalIdMapperModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param externalId the external ID
	 * @param start the lower bound of the range of external ID mappers
	 * @param end the upper bound of the range of external ID mappers (not inclusive)
	 * @return the range of matching external ID mappers
	 */
	@Override
	public List<ExternalIdMapper> findByC_T_EI(
		long classNameId, int type, String externalId, int start, int end) {

		return findByC_T_EI(classNameId, type, externalId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the external ID mappers where classNameId = &#63; and type = &#63; and externalId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExternalIdMapperModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param externalId the external ID
	 * @param start the lower bound of the range of external ID mappers
	 * @param end the upper bound of the range of external ID mappers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching external ID mappers
	 */
	@Override
	public List<ExternalIdMapper> findByC_T_EI(
		long classNameId, int type, String externalId, int start, int end,
		OrderByComparator<ExternalIdMapper> orderByComparator) {

		return findByC_T_EI(
			classNameId, type, externalId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the external ID mappers where classNameId = &#63; and type = &#63; and externalId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExternalIdMapperModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param externalId the external ID
	 * @param start the lower bound of the range of external ID mappers
	 * @param end the upper bound of the range of external ID mappers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching external ID mappers
	 */
	@Override
	public List<ExternalIdMapper> findByC_T_EI(
		long classNameId, int type, String externalId, int start, int end,
		OrderByComparator<ExternalIdMapper> orderByComparator,
		boolean useFinderCache) {

		externalId = Objects.toString(externalId, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_T_EI;
				finderArgs = new Object[] {classNameId, type, externalId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_T_EI;
			finderArgs = new Object[] {
				classNameId, type, externalId, start, end, orderByComparator
			};
		}

		List<ExternalIdMapper> list = null;

		if (useFinderCache) {
			list = (List<ExternalIdMapper>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ExternalIdMapper externalIdMapper : list) {
					if ((classNameId != externalIdMapper.getClassNameId()) ||
						(type != externalIdMapper.getType()) ||
						!externalId.equals(externalIdMapper.getExternalId())) {

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

			sb.append(_SQL_SELECT_EXTERNALIDMAPPER_WHERE);

			sb.append(_FINDER_COLUMN_C_T_EI_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_T_EI_TYPE_2);

			boolean bindExternalId = false;

			if (externalId.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_T_EI_EXTERNALID_3);
			}
			else {
				bindExternalId = true;

				sb.append(_FINDER_COLUMN_C_T_EI_EXTERNALID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ExternalIdMapperModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(type);

				if (bindExternalId) {
					queryPos.add(externalId);
				}

				list = (List<ExternalIdMapper>)QueryUtil.list(
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
	 * Returns the first external ID mapper in the ordered set where classNameId = &#63; and type = &#63; and externalId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param externalId the external ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching external ID mapper
	 * @throws NoSuchExternalIdMapperException if a matching external ID mapper could not be found
	 */
	@Override
	public ExternalIdMapper findByC_T_EI_First(
			long classNameId, int type, String externalId,
			OrderByComparator<ExternalIdMapper> orderByComparator)
		throws NoSuchExternalIdMapperException {

		ExternalIdMapper externalIdMapper = fetchByC_T_EI_First(
			classNameId, type, externalId, orderByComparator);

		if (externalIdMapper != null) {
			return externalIdMapper;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", type=");
		sb.append(type);

		sb.append(", externalId=");
		sb.append(externalId);

		sb.append("}");

		throw new NoSuchExternalIdMapperException(sb.toString());
	}

	/**
	 * Returns the first external ID mapper in the ordered set where classNameId = &#63; and type = &#63; and externalId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param externalId the external ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching external ID mapper, or <code>null</code> if a matching external ID mapper could not be found
	 */
	@Override
	public ExternalIdMapper fetchByC_T_EI_First(
		long classNameId, int type, String externalId,
		OrderByComparator<ExternalIdMapper> orderByComparator) {

		List<ExternalIdMapper> list = findByC_T_EI(
			classNameId, type, externalId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last external ID mapper in the ordered set where classNameId = &#63; and type = &#63; and externalId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param externalId the external ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching external ID mapper
	 * @throws NoSuchExternalIdMapperException if a matching external ID mapper could not be found
	 */
	@Override
	public ExternalIdMapper findByC_T_EI_Last(
			long classNameId, int type, String externalId,
			OrderByComparator<ExternalIdMapper> orderByComparator)
		throws NoSuchExternalIdMapperException {

		ExternalIdMapper externalIdMapper = fetchByC_T_EI_Last(
			classNameId, type, externalId, orderByComparator);

		if (externalIdMapper != null) {
			return externalIdMapper;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", type=");
		sb.append(type);

		sb.append(", externalId=");
		sb.append(externalId);

		sb.append("}");

		throw new NoSuchExternalIdMapperException(sb.toString());
	}

	/**
	 * Returns the last external ID mapper in the ordered set where classNameId = &#63; and type = &#63; and externalId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param externalId the external ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching external ID mapper, or <code>null</code> if a matching external ID mapper could not be found
	 */
	@Override
	public ExternalIdMapper fetchByC_T_EI_Last(
		long classNameId, int type, String externalId,
		OrderByComparator<ExternalIdMapper> orderByComparator) {

		int count = countByC_T_EI(classNameId, type, externalId);

		if (count == 0) {
			return null;
		}

		List<ExternalIdMapper> list = findByC_T_EI(
			classNameId, type, externalId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the external ID mappers before and after the current external ID mapper in the ordered set where classNameId = &#63; and type = &#63; and externalId = &#63;.
	 *
	 * @param externalIdMapperId the primary key of the current external ID mapper
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param externalId the external ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next external ID mapper
	 * @throws NoSuchExternalIdMapperException if a external ID mapper with the primary key could not be found
	 */
	@Override
	public ExternalIdMapper[] findByC_T_EI_PrevAndNext(
			long externalIdMapperId, long classNameId, int type,
			String externalId,
			OrderByComparator<ExternalIdMapper> orderByComparator)
		throws NoSuchExternalIdMapperException {

		externalId = Objects.toString(externalId, "");

		ExternalIdMapper externalIdMapper = findByPrimaryKey(
			externalIdMapperId);

		Session session = null;

		try {
			session = openSession();

			ExternalIdMapper[] array = new ExternalIdMapperImpl[3];

			array[0] = getByC_T_EI_PrevAndNext(
				session, externalIdMapper, classNameId, type, externalId,
				orderByComparator, true);

			array[1] = externalIdMapper;

			array[2] = getByC_T_EI_PrevAndNext(
				session, externalIdMapper, classNameId, type, externalId,
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

	protected ExternalIdMapper getByC_T_EI_PrevAndNext(
		Session session, ExternalIdMapper externalIdMapper, long classNameId,
		int type, String externalId,
		OrderByComparator<ExternalIdMapper> orderByComparator,
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

		sb.append(_SQL_SELECT_EXTERNALIDMAPPER_WHERE);

		sb.append(_FINDER_COLUMN_C_T_EI_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_T_EI_TYPE_2);

		boolean bindExternalId = false;

		if (externalId.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_T_EI_EXTERNALID_3);
		}
		else {
			bindExternalId = true;

			sb.append(_FINDER_COLUMN_C_T_EI_EXTERNALID_2);
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
			sb.append(ExternalIdMapperModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(type);

		if (bindExternalId) {
			queryPos.add(externalId);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						externalIdMapper)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ExternalIdMapper> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the external ID mappers where classNameId = &#63; and type = &#63; and externalId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param externalId the external ID
	 */
	@Override
	public void removeByC_T_EI(long classNameId, int type, String externalId) {
		for (ExternalIdMapper externalIdMapper :
				findByC_T_EI(
					classNameId, type, externalId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(externalIdMapper);
		}
	}

	/**
	 * Returns the number of external ID mappers where classNameId = &#63; and type = &#63; and externalId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param externalId the external ID
	 * @return the number of matching external ID mappers
	 */
	@Override
	public int countByC_T_EI(long classNameId, int type, String externalId) {
		externalId = Objects.toString(externalId, "");

		FinderPath finderPath = _finderPathCountByC_T_EI;

		Object[] finderArgs = new Object[] {classNameId, type, externalId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_EXTERNALIDMAPPER_WHERE);

			sb.append(_FINDER_COLUMN_C_T_EI_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_T_EI_TYPE_2);

			boolean bindExternalId = false;

			if (externalId.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_T_EI_EXTERNALID_3);
			}
			else {
				bindExternalId = true;

				sb.append(_FINDER_COLUMN_C_T_EI_EXTERNALID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(type);

				if (bindExternalId) {
					queryPos.add(externalId);
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

	private static final String _FINDER_COLUMN_C_T_EI_CLASSNAMEID_2 =
		"externalIdMapper.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_T_EI_TYPE_2 =
		"externalIdMapper.type = ? AND ";

	private static final String _FINDER_COLUMN_C_T_EI_EXTERNALID_2 =
		"externalIdMapper.externalId = ?";

	private static final String _FINDER_COLUMN_C_T_EI_EXTERNALID_3 =
		"(externalIdMapper.externalId IS NULL OR externalIdMapper.externalId = '')";

	public ExternalIdMapperPersistenceImpl() {
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

		setModelClass(ExternalIdMapper.class);
	}

	/**
	 * Caches the external ID mapper in the entity cache if it is enabled.
	 *
	 * @param externalIdMapper the external ID mapper
	 */
	@Override
	public void cacheResult(ExternalIdMapper externalIdMapper) {
		entityCache.putResult(
			ExternalIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			ExternalIdMapperImpl.class, externalIdMapper.getPrimaryKey(),
			externalIdMapper);

		externalIdMapper.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the external ID mappers in the entity cache if it is enabled.
	 *
	 * @param externalIdMappers the external ID mappers
	 */
	@Override
	public void cacheResult(List<ExternalIdMapper> externalIdMappers) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (externalIdMappers.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ExternalIdMapper externalIdMapper : externalIdMappers) {
			if (entityCache.getResult(
					ExternalIdMapperModelImpl.ENTITY_CACHE_ENABLED,
					ExternalIdMapperImpl.class,
					externalIdMapper.getPrimaryKey()) == null) {

				cacheResult(externalIdMapper);
			}
			else {
				externalIdMapper.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all external ID mappers.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ExternalIdMapperImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the external ID mapper.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ExternalIdMapper externalIdMapper) {
		entityCache.removeResult(
			ExternalIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			ExternalIdMapperImpl.class, externalIdMapper.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<ExternalIdMapper> externalIdMappers) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ExternalIdMapper externalIdMapper : externalIdMappers) {
			entityCache.removeResult(
				ExternalIdMapperModelImpl.ENTITY_CACHE_ENABLED,
				ExternalIdMapperImpl.class, externalIdMapper.getPrimaryKey());
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				ExternalIdMapperModelImpl.ENTITY_CACHE_ENABLED,
				ExternalIdMapperImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new external ID mapper with the primary key. Does not add the external ID mapper to the database.
	 *
	 * @param externalIdMapperId the primary key for the new external ID mapper
	 * @return the new external ID mapper
	 */
	@Override
	public ExternalIdMapper create(long externalIdMapperId) {
		ExternalIdMapper externalIdMapper = new ExternalIdMapperImpl();

		externalIdMapper.setNew(true);
		externalIdMapper.setPrimaryKey(externalIdMapperId);

		return externalIdMapper;
	}

	/**
	 * Removes the external ID mapper with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param externalIdMapperId the primary key of the external ID mapper
	 * @return the external ID mapper that was removed
	 * @throws NoSuchExternalIdMapperException if a external ID mapper with the primary key could not be found
	 */
	@Override
	public ExternalIdMapper remove(long externalIdMapperId)
		throws NoSuchExternalIdMapperException {

		return remove((Serializable)externalIdMapperId);
	}

	/**
	 * Removes the external ID mapper with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the external ID mapper
	 * @return the external ID mapper that was removed
	 * @throws NoSuchExternalIdMapperException if a external ID mapper with the primary key could not be found
	 */
	@Override
	public ExternalIdMapper remove(Serializable primaryKey)
		throws NoSuchExternalIdMapperException {

		Session session = null;

		try {
			session = openSession();

			ExternalIdMapper externalIdMapper = (ExternalIdMapper)session.get(
				ExternalIdMapperImpl.class, primaryKey);

			if (externalIdMapper == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchExternalIdMapperException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(externalIdMapper);
		}
		catch (NoSuchExternalIdMapperException noSuchEntityException) {
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
	protected ExternalIdMapper removeImpl(ExternalIdMapper externalIdMapper) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(externalIdMapper)) {
				externalIdMapper = (ExternalIdMapper)session.get(
					ExternalIdMapperImpl.class,
					externalIdMapper.getPrimaryKeyObj());
			}

			if (externalIdMapper != null) {
				session.delete(externalIdMapper);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (externalIdMapper != null) {
			clearCache(externalIdMapper);
		}

		return externalIdMapper;
	}

	@Override
	public ExternalIdMapper updateImpl(ExternalIdMapper externalIdMapper) {
		boolean isNew = externalIdMapper.isNew();

		if (!(externalIdMapper instanceof ExternalIdMapperModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(externalIdMapper.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					externalIdMapper);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in externalIdMapper proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ExternalIdMapper implementation " +
					externalIdMapper.getClass());
		}

		ExternalIdMapperModelImpl externalIdMapperModelImpl =
			(ExternalIdMapperModelImpl)externalIdMapper;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (externalIdMapper.getCreateDate() == null)) {
			if (serviceContext == null) {
				externalIdMapper.setCreateDate(date);
			}
			else {
				externalIdMapper.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!externalIdMapperModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				externalIdMapper.setModifiedDate(date);
			}
			else {
				externalIdMapper.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(externalIdMapper);

				externalIdMapper.setNew(false);
			}
			else {
				externalIdMapper = (ExternalIdMapper)session.merge(
					externalIdMapper);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!ExternalIdMapperModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				externalIdMapperModelImpl.getClassNameId(),
				externalIdMapperModelImpl.getClassPK()
			};

			finderCache.removeResult(_finderPathCountByC_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_C, args);

			args = new Object[] {
				externalIdMapperModelImpl.getClassNameId(),
				externalIdMapperModelImpl.getClassPK(),
				externalIdMapperModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByC_C_T, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_C_T, args);

			args = new Object[] {
				externalIdMapperModelImpl.getClassNameId(),
				externalIdMapperModelImpl.getType(),
				externalIdMapperModelImpl.getExternalId()
			};

			finderCache.removeResult(_finderPathCountByC_T_EI, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_T_EI, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((externalIdMapperModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					externalIdMapperModelImpl.getOriginalClassNameId(),
					externalIdMapperModelImpl.getOriginalClassPK()
				};

				finderCache.removeResult(_finderPathCountByC_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);

				args = new Object[] {
					externalIdMapperModelImpl.getClassNameId(),
					externalIdMapperModelImpl.getClassPK()
				};

				finderCache.removeResult(_finderPathCountByC_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);
			}

			if ((externalIdMapperModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_C_T.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					externalIdMapperModelImpl.getOriginalClassNameId(),
					externalIdMapperModelImpl.getOriginalClassPK(),
					externalIdMapperModelImpl.getOriginalType()
				};

				finderCache.removeResult(_finderPathCountByC_C_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_C_T, args);

				args = new Object[] {
					externalIdMapperModelImpl.getClassNameId(),
					externalIdMapperModelImpl.getClassPK(),
					externalIdMapperModelImpl.getType()
				};

				finderCache.removeResult(_finderPathCountByC_C_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_C_T, args);
			}

			if ((externalIdMapperModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_T_EI.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					externalIdMapperModelImpl.getOriginalClassNameId(),
					externalIdMapperModelImpl.getOriginalType(),
					externalIdMapperModelImpl.getOriginalExternalId()
				};

				finderCache.removeResult(_finderPathCountByC_T_EI, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_T_EI, args);

				args = new Object[] {
					externalIdMapperModelImpl.getClassNameId(),
					externalIdMapperModelImpl.getType(),
					externalIdMapperModelImpl.getExternalId()
				};

				finderCache.removeResult(_finderPathCountByC_T_EI, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_T_EI, args);
			}
		}

		entityCache.putResult(
			ExternalIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			ExternalIdMapperImpl.class, externalIdMapper.getPrimaryKey(),
			externalIdMapper, false);

		externalIdMapper.resetOriginalValues();

		return externalIdMapper;
	}

	/**
	 * Returns the external ID mapper with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the external ID mapper
	 * @return the external ID mapper
	 * @throws NoSuchExternalIdMapperException if a external ID mapper with the primary key could not be found
	 */
	@Override
	public ExternalIdMapper findByPrimaryKey(Serializable primaryKey)
		throws NoSuchExternalIdMapperException {

		ExternalIdMapper externalIdMapper = fetchByPrimaryKey(primaryKey);

		if (externalIdMapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchExternalIdMapperException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return externalIdMapper;
	}

	/**
	 * Returns the external ID mapper with the primary key or throws a <code>NoSuchExternalIdMapperException</code> if it could not be found.
	 *
	 * @param externalIdMapperId the primary key of the external ID mapper
	 * @return the external ID mapper
	 * @throws NoSuchExternalIdMapperException if a external ID mapper with the primary key could not be found
	 */
	@Override
	public ExternalIdMapper findByPrimaryKey(long externalIdMapperId)
		throws NoSuchExternalIdMapperException {

		return findByPrimaryKey((Serializable)externalIdMapperId);
	}

	/**
	 * Returns the external ID mapper with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the external ID mapper
	 * @return the external ID mapper, or <code>null</code> if a external ID mapper with the primary key could not be found
	 */
	@Override
	public ExternalIdMapper fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			ExternalIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			ExternalIdMapperImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		ExternalIdMapper externalIdMapper = (ExternalIdMapper)serializable;

		if (externalIdMapper == null) {
			Session session = null;

			try {
				session = openSession();

				externalIdMapper = (ExternalIdMapper)session.get(
					ExternalIdMapperImpl.class, primaryKey);

				if (externalIdMapper != null) {
					cacheResult(externalIdMapper);
				}
				else {
					entityCache.putResult(
						ExternalIdMapperModelImpl.ENTITY_CACHE_ENABLED,
						ExternalIdMapperImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					ExternalIdMapperModelImpl.ENTITY_CACHE_ENABLED,
					ExternalIdMapperImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return externalIdMapper;
	}

	/**
	 * Returns the external ID mapper with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param externalIdMapperId the primary key of the external ID mapper
	 * @return the external ID mapper, or <code>null</code> if a external ID mapper with the primary key could not be found
	 */
	@Override
	public ExternalIdMapper fetchByPrimaryKey(long externalIdMapperId) {
		return fetchByPrimaryKey((Serializable)externalIdMapperId);
	}

	@Override
	public Map<Serializable, ExternalIdMapper> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ExternalIdMapper> map =
			new HashMap<Serializable, ExternalIdMapper>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ExternalIdMapper externalIdMapper = fetchByPrimaryKey(primaryKey);

			if (externalIdMapper != null) {
				map.put(primaryKey, externalIdMapper);
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
				ExternalIdMapperModelImpl.ENTITY_CACHE_ENABLED,
				ExternalIdMapperImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (ExternalIdMapper)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_EXTERNALIDMAPPER_WHERE_PKS_IN);

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

			for (ExternalIdMapper externalIdMapper :
					(List<ExternalIdMapper>)query.list()) {

				map.put(externalIdMapper.getPrimaryKeyObj(), externalIdMapper);

				cacheResult(externalIdMapper);

				uncachedPrimaryKeys.remove(externalIdMapper.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					ExternalIdMapperModelImpl.ENTITY_CACHE_ENABLED,
					ExternalIdMapperImpl.class, primaryKey, nullModel);
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
	 * Returns all the external ID mappers.
	 *
	 * @return the external ID mappers
	 */
	@Override
	public List<ExternalIdMapper> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the external ID mappers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExternalIdMapperModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of external ID mappers
	 * @param end the upper bound of the range of external ID mappers (not inclusive)
	 * @return the range of external ID mappers
	 */
	@Override
	public List<ExternalIdMapper> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the external ID mappers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExternalIdMapperModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of external ID mappers
	 * @param end the upper bound of the range of external ID mappers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of external ID mappers
	 */
	@Override
	public List<ExternalIdMapper> findAll(
		int start, int end,
		OrderByComparator<ExternalIdMapper> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the external ID mappers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExternalIdMapperModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of external ID mappers
	 * @param end the upper bound of the range of external ID mappers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of external ID mappers
	 */
	@Override
	public List<ExternalIdMapper> findAll(
		int start, int end,
		OrderByComparator<ExternalIdMapper> orderByComparator,
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

		List<ExternalIdMapper> list = null;

		if (useFinderCache) {
			list = (List<ExternalIdMapper>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_EXTERNALIDMAPPER);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_EXTERNALIDMAPPER;

				sql = sql.concat(ExternalIdMapperModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ExternalIdMapper>)QueryUtil.list(
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
	 * Removes all the external ID mappers from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ExternalIdMapper externalIdMapper : findAll()) {
			remove(externalIdMapper);
		}
	}

	/**
	 * Returns the number of external ID mappers.
	 *
	 * @return the number of external ID mappers
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_EXTERNALIDMAPPER);

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
		return ExternalIdMapperModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the external ID mapper persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			ExternalIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			ExternalIdMapperModelImpl.FINDER_CACHE_ENABLED,
			ExternalIdMapperImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			ExternalIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			ExternalIdMapperModelImpl.FINDER_CACHE_ENABLED,
			ExternalIdMapperImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			ExternalIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			ExternalIdMapperModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByC_C = new FinderPath(
			ExternalIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			ExternalIdMapperModelImpl.FINDER_CACHE_ENABLED,
			ExternalIdMapperImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			ExternalIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			ExternalIdMapperModelImpl.FINDER_CACHE_ENABLED,
			ExternalIdMapperImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			ExternalIdMapperModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			ExternalIdMapperModelImpl.CLASSPK_COLUMN_BITMASK);

		_finderPathCountByC_C = new FinderPath(
			ExternalIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			ExternalIdMapperModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByC_C_T = new FinderPath(
			ExternalIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			ExternalIdMapperModelImpl.FINDER_CACHE_ENABLED,
			ExternalIdMapperImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_C_T = new FinderPath(
			ExternalIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			ExternalIdMapperModelImpl.FINDER_CACHE_ENABLED,
			ExternalIdMapperImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			ExternalIdMapperModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			ExternalIdMapperModelImpl.CLASSPK_COLUMN_BITMASK |
			ExternalIdMapperModelImpl.TYPE_COLUMN_BITMASK);

		_finderPathCountByC_C_T = new FinderPath(
			ExternalIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			ExternalIdMapperModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByC_T_EI = new FinderPath(
			ExternalIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			ExternalIdMapperModelImpl.FINDER_CACHE_ENABLED,
			ExternalIdMapperImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_T_EI",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_T_EI = new FinderPath(
			ExternalIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			ExternalIdMapperModelImpl.FINDER_CACHE_ENABLED,
			ExternalIdMapperImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_T_EI",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				String.class.getName()
			},
			ExternalIdMapperModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			ExternalIdMapperModelImpl.TYPE_COLUMN_BITMASK |
			ExternalIdMapperModelImpl.EXTERNALID_COLUMN_BITMASK);

		_finderPathCountByC_T_EI = new FinderPath(
			ExternalIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			ExternalIdMapperModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_T_EI",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				String.class.getName()
			});

		ExternalIdMapperUtil.setPersistence(this);
	}

	public void destroy() {
		ExternalIdMapperUtil.setPersistence(null);

		entityCache.removeCache(ExternalIdMapperImpl.class.getName());

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

	private static final String _SQL_SELECT_EXTERNALIDMAPPER =
		"SELECT externalIdMapper FROM ExternalIdMapper externalIdMapper";

	private static final String _SQL_SELECT_EXTERNALIDMAPPER_WHERE_PKS_IN =
		"SELECT externalIdMapper FROM ExternalIdMapper externalIdMapper WHERE externalIdMapperId IN (";

	private static final String _SQL_SELECT_EXTERNALIDMAPPER_WHERE =
		"SELECT externalIdMapper FROM ExternalIdMapper externalIdMapper WHERE ";

	private static final String _SQL_COUNT_EXTERNALIDMAPPER =
		"SELECT COUNT(externalIdMapper) FROM ExternalIdMapper externalIdMapper";

	private static final String _SQL_COUNT_EXTERNALIDMAPPER_WHERE =
		"SELECT COUNT(externalIdMapper) FROM ExternalIdMapper externalIdMapper WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "externalIdMapper.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ExternalIdMapper exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ExternalIdMapper exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ExternalIdMapperPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type"});

}