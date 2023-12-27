/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.release.tool.service.persistence.impl;

import com.liferay.osb.customer.release.tool.exception.NoSuchArtifactVersionException;
import com.liferay.osb.customer.release.tool.model.ArtifactVersion;
import com.liferay.osb.customer.release.tool.model.impl.ArtifactVersionImpl;
import com.liferay.osb.customer.release.tool.model.impl.ArtifactVersionModelImpl;
import com.liferay.osb.customer.release.tool.service.persistence.ArtifactVersionPersistence;
import com.liferay.osb.customer.release.tool.service.persistence.ArtifactVersionUtil;
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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
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
 * The persistence implementation for the artifact version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ArtifactVersionPersistenceImpl
	extends BasePersistenceImpl<ArtifactVersion>
	implements ArtifactVersionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ArtifactVersionUtil</code> to access the artifact version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ArtifactVersionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByReleaseAssetCategoryId;
	private FinderPath _finderPathWithoutPaginationFindByReleaseAssetCategoryId;
	private FinderPath _finderPathCountByReleaseAssetCategoryId;

	/**
	 * Returns all the artifact versions where releaseAssetCategoryId = &#63;.
	 *
	 * @param releaseAssetCategoryId the release asset category ID
	 * @return the matching artifact versions
	 */
	@Override
	public List<ArtifactVersion> findByReleaseAssetCategoryId(
		long releaseAssetCategoryId) {

		return findByReleaseAssetCategoryId(
			releaseAssetCategoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the artifact versions where releaseAssetCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ArtifactVersionModelImpl</code>.
	 * </p>
	 *
	 * @param releaseAssetCategoryId the release asset category ID
	 * @param start the lower bound of the range of artifact versions
	 * @param end the upper bound of the range of artifact versions (not inclusive)
	 * @return the range of matching artifact versions
	 */
	@Override
	public List<ArtifactVersion> findByReleaseAssetCategoryId(
		long releaseAssetCategoryId, int start, int end) {

		return findByReleaseAssetCategoryId(
			releaseAssetCategoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the artifact versions where releaseAssetCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ArtifactVersionModelImpl</code>.
	 * </p>
	 *
	 * @param releaseAssetCategoryId the release asset category ID
	 * @param start the lower bound of the range of artifact versions
	 * @param end the upper bound of the range of artifact versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching artifact versions
	 */
	@Override
	public List<ArtifactVersion> findByReleaseAssetCategoryId(
		long releaseAssetCategoryId, int start, int end,
		OrderByComparator<ArtifactVersion> orderByComparator) {

		return findByReleaseAssetCategoryId(
			releaseAssetCategoryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the artifact versions where releaseAssetCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ArtifactVersionModelImpl</code>.
	 * </p>
	 *
	 * @param releaseAssetCategoryId the release asset category ID
	 * @param start the lower bound of the range of artifact versions
	 * @param end the upper bound of the range of artifact versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching artifact versions
	 */
	@Override
	public List<ArtifactVersion> findByReleaseAssetCategoryId(
		long releaseAssetCategoryId, int start, int end,
		OrderByComparator<ArtifactVersion> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByReleaseAssetCategoryId;
				finderArgs = new Object[] {releaseAssetCategoryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByReleaseAssetCategoryId;
			finderArgs = new Object[] {
				releaseAssetCategoryId, start, end, orderByComparator
			};
		}

		List<ArtifactVersion> list = null;

		if (useFinderCache) {
			list = (List<ArtifactVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ArtifactVersion artifactVersion : list) {
					if (releaseAssetCategoryId !=
							artifactVersion.getReleaseAssetCategoryId()) {

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

			sb.append(_SQL_SELECT_ARTIFACTVERSION_WHERE);

			sb.append(
				_FINDER_COLUMN_RELEASEASSETCATEGORYID_RELEASEASSETCATEGORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ArtifactVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(releaseAssetCategoryId);

				list = (List<ArtifactVersion>)QueryUtil.list(
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
	 * Returns the first artifact version in the ordered set where releaseAssetCategoryId = &#63;.
	 *
	 * @param releaseAssetCategoryId the release asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching artifact version
	 * @throws NoSuchArtifactVersionException if a matching artifact version could not be found
	 */
	@Override
	public ArtifactVersion findByReleaseAssetCategoryId_First(
			long releaseAssetCategoryId,
			OrderByComparator<ArtifactVersion> orderByComparator)
		throws NoSuchArtifactVersionException {

		ArtifactVersion artifactVersion = fetchByReleaseAssetCategoryId_First(
			releaseAssetCategoryId, orderByComparator);

		if (artifactVersion != null) {
			return artifactVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("releaseAssetCategoryId=");
		sb.append(releaseAssetCategoryId);

		sb.append("}");

		throw new NoSuchArtifactVersionException(sb.toString());
	}

	/**
	 * Returns the first artifact version in the ordered set where releaseAssetCategoryId = &#63;.
	 *
	 * @param releaseAssetCategoryId the release asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching artifact version, or <code>null</code> if a matching artifact version could not be found
	 */
	@Override
	public ArtifactVersion fetchByReleaseAssetCategoryId_First(
		long releaseAssetCategoryId,
		OrderByComparator<ArtifactVersion> orderByComparator) {

		List<ArtifactVersion> list = findByReleaseAssetCategoryId(
			releaseAssetCategoryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last artifact version in the ordered set where releaseAssetCategoryId = &#63;.
	 *
	 * @param releaseAssetCategoryId the release asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching artifact version
	 * @throws NoSuchArtifactVersionException if a matching artifact version could not be found
	 */
	@Override
	public ArtifactVersion findByReleaseAssetCategoryId_Last(
			long releaseAssetCategoryId,
			OrderByComparator<ArtifactVersion> orderByComparator)
		throws NoSuchArtifactVersionException {

		ArtifactVersion artifactVersion = fetchByReleaseAssetCategoryId_Last(
			releaseAssetCategoryId, orderByComparator);

		if (artifactVersion != null) {
			return artifactVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("releaseAssetCategoryId=");
		sb.append(releaseAssetCategoryId);

		sb.append("}");

		throw new NoSuchArtifactVersionException(sb.toString());
	}

	/**
	 * Returns the last artifact version in the ordered set where releaseAssetCategoryId = &#63;.
	 *
	 * @param releaseAssetCategoryId the release asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching artifact version, or <code>null</code> if a matching artifact version could not be found
	 */
	@Override
	public ArtifactVersion fetchByReleaseAssetCategoryId_Last(
		long releaseAssetCategoryId,
		OrderByComparator<ArtifactVersion> orderByComparator) {

		int count = countByReleaseAssetCategoryId(releaseAssetCategoryId);

		if (count == 0) {
			return null;
		}

		List<ArtifactVersion> list = findByReleaseAssetCategoryId(
			releaseAssetCategoryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the artifact versions before and after the current artifact version in the ordered set where releaseAssetCategoryId = &#63;.
	 *
	 * @param artifactVersionId the primary key of the current artifact version
	 * @param releaseAssetCategoryId the release asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next artifact version
	 * @throws NoSuchArtifactVersionException if a artifact version with the primary key could not be found
	 */
	@Override
	public ArtifactVersion[] findByReleaseAssetCategoryId_PrevAndNext(
			long artifactVersionId, long releaseAssetCategoryId,
			OrderByComparator<ArtifactVersion> orderByComparator)
		throws NoSuchArtifactVersionException {

		ArtifactVersion artifactVersion = findByPrimaryKey(artifactVersionId);

		Session session = null;

		try {
			session = openSession();

			ArtifactVersion[] array = new ArtifactVersionImpl[3];

			array[0] = getByReleaseAssetCategoryId_PrevAndNext(
				session, artifactVersion, releaseAssetCategoryId,
				orderByComparator, true);

			array[1] = artifactVersion;

			array[2] = getByReleaseAssetCategoryId_PrevAndNext(
				session, artifactVersion, releaseAssetCategoryId,
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

	protected ArtifactVersion getByReleaseAssetCategoryId_PrevAndNext(
		Session session, ArtifactVersion artifactVersion,
		long releaseAssetCategoryId,
		OrderByComparator<ArtifactVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_ARTIFACTVERSION_WHERE);

		sb.append(
			_FINDER_COLUMN_RELEASEASSETCATEGORYID_RELEASEASSETCATEGORYID_2);

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
			sb.append(ArtifactVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(releaseAssetCategoryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						artifactVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ArtifactVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the artifact versions where releaseAssetCategoryId = &#63; from the database.
	 *
	 * @param releaseAssetCategoryId the release asset category ID
	 */
	@Override
	public void removeByReleaseAssetCategoryId(long releaseAssetCategoryId) {
		for (ArtifactVersion artifactVersion :
				findByReleaseAssetCategoryId(
					releaseAssetCategoryId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(artifactVersion);
		}
	}

	/**
	 * Returns the number of artifact versions where releaseAssetCategoryId = &#63;.
	 *
	 * @param releaseAssetCategoryId the release asset category ID
	 * @return the number of matching artifact versions
	 */
	@Override
	public int countByReleaseAssetCategoryId(long releaseAssetCategoryId) {
		FinderPath finderPath = _finderPathCountByReleaseAssetCategoryId;

		Object[] finderArgs = new Object[] {releaseAssetCategoryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ARTIFACTVERSION_WHERE);

			sb.append(
				_FINDER_COLUMN_RELEASEASSETCATEGORYID_RELEASEASSETCATEGORYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(releaseAssetCategoryId);

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
		_FINDER_COLUMN_RELEASEASSETCATEGORYID_RELEASEASSETCATEGORYID_2 =
			"artifactVersion.releaseAssetCategoryId = ?";

	private FinderPath _finderPathFetchByRACI_G_N;
	private FinderPath _finderPathCountByRACI_G_N;

	/**
	 * Returns the artifact version where releaseAssetCategoryId = &#63; and group = &#63; and name = &#63; or throws a <code>NoSuchArtifactVersionException</code> if it could not be found.
	 *
	 * @param releaseAssetCategoryId the release asset category ID
	 * @param group the group
	 * @param name the name
	 * @return the matching artifact version
	 * @throws NoSuchArtifactVersionException if a matching artifact version could not be found
	 */
	@Override
	public ArtifactVersion findByRACI_G_N(
			long releaseAssetCategoryId, String group, String name)
		throws NoSuchArtifactVersionException {

		ArtifactVersion artifactVersion = fetchByRACI_G_N(
			releaseAssetCategoryId, group, name);

		if (artifactVersion == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("releaseAssetCategoryId=");
			sb.append(releaseAssetCategoryId);

			sb.append(", group=");
			sb.append(group);

			sb.append(", name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchArtifactVersionException(sb.toString());
		}

		return artifactVersion;
	}

	/**
	 * Returns the artifact version where releaseAssetCategoryId = &#63; and group = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param releaseAssetCategoryId the release asset category ID
	 * @param group the group
	 * @param name the name
	 * @return the matching artifact version, or <code>null</code> if a matching artifact version could not be found
	 */
	@Override
	public ArtifactVersion fetchByRACI_G_N(
		long releaseAssetCategoryId, String group, String name) {

		return fetchByRACI_G_N(releaseAssetCategoryId, group, name, true);
	}

	/**
	 * Returns the artifact version where releaseAssetCategoryId = &#63; and group = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param releaseAssetCategoryId the release asset category ID
	 * @param group the group
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching artifact version, or <code>null</code> if a matching artifact version could not be found
	 */
	@Override
	public ArtifactVersion fetchByRACI_G_N(
		long releaseAssetCategoryId, String group, String name,
		boolean useFinderCache) {

		group = Objects.toString(group, "");
		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {releaseAssetCategoryId, group, name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByRACI_G_N, finderArgs, this);
		}

		if (result instanceof ArtifactVersion) {
			ArtifactVersion artifactVersion = (ArtifactVersion)result;

			if ((releaseAssetCategoryId !=
					artifactVersion.getReleaseAssetCategoryId()) ||
				!Objects.equals(group, artifactVersion.getGroup()) ||
				!Objects.equals(name, artifactVersion.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_ARTIFACTVERSION_WHERE);

			sb.append(_FINDER_COLUMN_RACI_G_N_RELEASEASSETCATEGORYID_2);

			boolean bindGroup = false;

			if (group.isEmpty()) {
				sb.append(_FINDER_COLUMN_RACI_G_N_GROUP_3);
			}
			else {
				bindGroup = true;

				sb.append(_FINDER_COLUMN_RACI_G_N_GROUP_2);
			}

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_RACI_G_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_RACI_G_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(releaseAssetCategoryId);

				if (bindGroup) {
					queryPos.add(group);
				}

				if (bindName) {
					queryPos.add(name);
				}

				List<ArtifactVersion> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByRACI_G_N, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									releaseAssetCategoryId, group, name
								};
							}

							_log.warn(
								"ArtifactVersionPersistenceImpl.fetchByRACI_G_N(long, String, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					ArtifactVersion artifactVersion = list.get(0);

					result = artifactVersion;

					cacheResult(artifactVersion);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByRACI_G_N, finderArgs);
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
			return (ArtifactVersion)result;
		}
	}

	/**
	 * Removes the artifact version where releaseAssetCategoryId = &#63; and group = &#63; and name = &#63; from the database.
	 *
	 * @param releaseAssetCategoryId the release asset category ID
	 * @param group the group
	 * @param name the name
	 * @return the artifact version that was removed
	 */
	@Override
	public ArtifactVersion removeByRACI_G_N(
			long releaseAssetCategoryId, String group, String name)
		throws NoSuchArtifactVersionException {

		ArtifactVersion artifactVersion = findByRACI_G_N(
			releaseAssetCategoryId, group, name);

		return remove(artifactVersion);
	}

	/**
	 * Returns the number of artifact versions where releaseAssetCategoryId = &#63; and group = &#63; and name = &#63;.
	 *
	 * @param releaseAssetCategoryId the release asset category ID
	 * @param group the group
	 * @param name the name
	 * @return the number of matching artifact versions
	 */
	@Override
	public int countByRACI_G_N(
		long releaseAssetCategoryId, String group, String name) {

		group = Objects.toString(group, "");
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByRACI_G_N;

		Object[] finderArgs = new Object[] {
			releaseAssetCategoryId, group, name
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_ARTIFACTVERSION_WHERE);

			sb.append(_FINDER_COLUMN_RACI_G_N_RELEASEASSETCATEGORYID_2);

			boolean bindGroup = false;

			if (group.isEmpty()) {
				sb.append(_FINDER_COLUMN_RACI_G_N_GROUP_3);
			}
			else {
				bindGroup = true;

				sb.append(_FINDER_COLUMN_RACI_G_N_GROUP_2);
			}

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_RACI_G_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_RACI_G_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(releaseAssetCategoryId);

				if (bindGroup) {
					queryPos.add(group);
				}

				if (bindName) {
					queryPos.add(name);
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
		_FINDER_COLUMN_RACI_G_N_RELEASEASSETCATEGORYID_2 =
			"artifactVersion.releaseAssetCategoryId = ? AND ";

	private static final String _FINDER_COLUMN_RACI_G_N_GROUP_2 =
		"artifactVersion.group = ? AND ";

	private static final String _FINDER_COLUMN_RACI_G_N_GROUP_3 =
		"(artifactVersion.group IS NULL OR artifactVersion.group = '') AND ";

	private static final String _FINDER_COLUMN_RACI_G_N_NAME_2 =
		"artifactVersion.name = ?";

	private static final String _FINDER_COLUMN_RACI_G_N_NAME_3 =
		"(artifactVersion.name IS NULL OR artifactVersion.name = '')";

	public ArtifactVersionPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("group", "group_");

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

		setModelClass(ArtifactVersion.class);
	}

	/**
	 * Caches the artifact version in the entity cache if it is enabled.
	 *
	 * @param artifactVersion the artifact version
	 */
	@Override
	public void cacheResult(ArtifactVersion artifactVersion) {
		entityCache.putResult(
			ArtifactVersionModelImpl.ENTITY_CACHE_ENABLED,
			ArtifactVersionImpl.class, artifactVersion.getPrimaryKey(),
			artifactVersion);

		finderCache.putResult(
			_finderPathFetchByRACI_G_N,
			new Object[] {
				artifactVersion.getReleaseAssetCategoryId(),
				artifactVersion.getGroup(), artifactVersion.getName()
			},
			artifactVersion);

		artifactVersion.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the artifact versions in the entity cache if it is enabled.
	 *
	 * @param artifactVersions the artifact versions
	 */
	@Override
	public void cacheResult(List<ArtifactVersion> artifactVersions) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (artifactVersions.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ArtifactVersion artifactVersion : artifactVersions) {
			if (entityCache.getResult(
					ArtifactVersionModelImpl.ENTITY_CACHE_ENABLED,
					ArtifactVersionImpl.class,
					artifactVersion.getPrimaryKey()) == null) {

				cacheResult(artifactVersion);
			}
			else {
				artifactVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all artifact versions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ArtifactVersionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the artifact version.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ArtifactVersion artifactVersion) {
		entityCache.removeResult(
			ArtifactVersionModelImpl.ENTITY_CACHE_ENABLED,
			ArtifactVersionImpl.class, artifactVersion.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(ArtifactVersionModelImpl)artifactVersion, true);
	}

	@Override
	public void clearCache(List<ArtifactVersion> artifactVersions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ArtifactVersion artifactVersion : artifactVersions) {
			entityCache.removeResult(
				ArtifactVersionModelImpl.ENTITY_CACHE_ENABLED,
				ArtifactVersionImpl.class, artifactVersion.getPrimaryKey());

			clearUniqueFindersCache(
				(ArtifactVersionModelImpl)artifactVersion, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				ArtifactVersionModelImpl.ENTITY_CACHE_ENABLED,
				ArtifactVersionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ArtifactVersionModelImpl artifactVersionModelImpl) {

		Object[] args = new Object[] {
			artifactVersionModelImpl.getReleaseAssetCategoryId(),
			artifactVersionModelImpl.getGroup(),
			artifactVersionModelImpl.getName()
		};

		finderCache.putResult(
			_finderPathCountByRACI_G_N, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByRACI_G_N, args, artifactVersionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		ArtifactVersionModelImpl artifactVersionModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				artifactVersionModelImpl.getReleaseAssetCategoryId(),
				artifactVersionModelImpl.getGroup(),
				artifactVersionModelImpl.getName()
			};

			finderCache.removeResult(_finderPathCountByRACI_G_N, args);
			finderCache.removeResult(_finderPathFetchByRACI_G_N, args);
		}

		if ((artifactVersionModelImpl.getColumnBitmask() &
			 _finderPathFetchByRACI_G_N.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				artifactVersionModelImpl.getOriginalReleaseAssetCategoryId(),
				artifactVersionModelImpl.getOriginalGroup(),
				artifactVersionModelImpl.getOriginalName()
			};

			finderCache.removeResult(_finderPathCountByRACI_G_N, args);
			finderCache.removeResult(_finderPathFetchByRACI_G_N, args);
		}
	}

	/**
	 * Creates a new artifact version with the primary key. Does not add the artifact version to the database.
	 *
	 * @param artifactVersionId the primary key for the new artifact version
	 * @return the new artifact version
	 */
	@Override
	public ArtifactVersion create(long artifactVersionId) {
		ArtifactVersion artifactVersion = new ArtifactVersionImpl();

		artifactVersion.setNew(true);
		artifactVersion.setPrimaryKey(artifactVersionId);

		return artifactVersion;
	}

	/**
	 * Removes the artifact version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param artifactVersionId the primary key of the artifact version
	 * @return the artifact version that was removed
	 * @throws NoSuchArtifactVersionException if a artifact version with the primary key could not be found
	 */
	@Override
	public ArtifactVersion remove(long artifactVersionId)
		throws NoSuchArtifactVersionException {

		return remove((Serializable)artifactVersionId);
	}

	/**
	 * Removes the artifact version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the artifact version
	 * @return the artifact version that was removed
	 * @throws NoSuchArtifactVersionException if a artifact version with the primary key could not be found
	 */
	@Override
	public ArtifactVersion remove(Serializable primaryKey)
		throws NoSuchArtifactVersionException {

		Session session = null;

		try {
			session = openSession();

			ArtifactVersion artifactVersion = (ArtifactVersion)session.get(
				ArtifactVersionImpl.class, primaryKey);

			if (artifactVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchArtifactVersionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(artifactVersion);
		}
		catch (NoSuchArtifactVersionException noSuchEntityException) {
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
	protected ArtifactVersion removeImpl(ArtifactVersion artifactVersion) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(artifactVersion)) {
				artifactVersion = (ArtifactVersion)session.get(
					ArtifactVersionImpl.class,
					artifactVersion.getPrimaryKeyObj());
			}

			if (artifactVersion != null) {
				session.delete(artifactVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (artifactVersion != null) {
			clearCache(artifactVersion);
		}

		return artifactVersion;
	}

	@Override
	public ArtifactVersion updateImpl(ArtifactVersion artifactVersion) {
		boolean isNew = artifactVersion.isNew();

		if (!(artifactVersion instanceof ArtifactVersionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(artifactVersion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					artifactVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in artifactVersion proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ArtifactVersion implementation " +
					artifactVersion.getClass());
		}

		ArtifactVersionModelImpl artifactVersionModelImpl =
			(ArtifactVersionModelImpl)artifactVersion;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(artifactVersion);

				artifactVersion.setNew(false);
			}
			else {
				artifactVersion = (ArtifactVersion)session.merge(
					artifactVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!ArtifactVersionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				artifactVersionModelImpl.getReleaseAssetCategoryId()
			};

			finderCache.removeResult(
				_finderPathCountByReleaseAssetCategoryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByReleaseAssetCategoryId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((artifactVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByReleaseAssetCategoryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					artifactVersionModelImpl.getOriginalReleaseAssetCategoryId()
				};

				finderCache.removeResult(
					_finderPathCountByReleaseAssetCategoryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByReleaseAssetCategoryId,
					args);

				args = new Object[] {
					artifactVersionModelImpl.getReleaseAssetCategoryId()
				};

				finderCache.removeResult(
					_finderPathCountByReleaseAssetCategoryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByReleaseAssetCategoryId,
					args);
			}
		}

		entityCache.putResult(
			ArtifactVersionModelImpl.ENTITY_CACHE_ENABLED,
			ArtifactVersionImpl.class, artifactVersion.getPrimaryKey(),
			artifactVersion, false);

		clearUniqueFindersCache(artifactVersionModelImpl, false);
		cacheUniqueFindersCache(artifactVersionModelImpl);

		artifactVersion.resetOriginalValues();

		return artifactVersion;
	}

	/**
	 * Returns the artifact version with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the artifact version
	 * @return the artifact version
	 * @throws NoSuchArtifactVersionException if a artifact version with the primary key could not be found
	 */
	@Override
	public ArtifactVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchArtifactVersionException {

		ArtifactVersion artifactVersion = fetchByPrimaryKey(primaryKey);

		if (artifactVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchArtifactVersionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return artifactVersion;
	}

	/**
	 * Returns the artifact version with the primary key or throws a <code>NoSuchArtifactVersionException</code> if it could not be found.
	 *
	 * @param artifactVersionId the primary key of the artifact version
	 * @return the artifact version
	 * @throws NoSuchArtifactVersionException if a artifact version with the primary key could not be found
	 */
	@Override
	public ArtifactVersion findByPrimaryKey(long artifactVersionId)
		throws NoSuchArtifactVersionException {

		return findByPrimaryKey((Serializable)artifactVersionId);
	}

	/**
	 * Returns the artifact version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the artifact version
	 * @return the artifact version, or <code>null</code> if a artifact version with the primary key could not be found
	 */
	@Override
	public ArtifactVersion fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			ArtifactVersionModelImpl.ENTITY_CACHE_ENABLED,
			ArtifactVersionImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		ArtifactVersion artifactVersion = (ArtifactVersion)serializable;

		if (artifactVersion == null) {
			Session session = null;

			try {
				session = openSession();

				artifactVersion = (ArtifactVersion)session.get(
					ArtifactVersionImpl.class, primaryKey);

				if (artifactVersion != null) {
					cacheResult(artifactVersion);
				}
				else {
					entityCache.putResult(
						ArtifactVersionModelImpl.ENTITY_CACHE_ENABLED,
						ArtifactVersionImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					ArtifactVersionModelImpl.ENTITY_CACHE_ENABLED,
					ArtifactVersionImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return artifactVersion;
	}

	/**
	 * Returns the artifact version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param artifactVersionId the primary key of the artifact version
	 * @return the artifact version, or <code>null</code> if a artifact version with the primary key could not be found
	 */
	@Override
	public ArtifactVersion fetchByPrimaryKey(long artifactVersionId) {
		return fetchByPrimaryKey((Serializable)artifactVersionId);
	}

	@Override
	public Map<Serializable, ArtifactVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ArtifactVersion> map =
			new HashMap<Serializable, ArtifactVersion>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ArtifactVersion artifactVersion = fetchByPrimaryKey(primaryKey);

			if (artifactVersion != null) {
				map.put(primaryKey, artifactVersion);
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
				ArtifactVersionModelImpl.ENTITY_CACHE_ENABLED,
				ArtifactVersionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (ArtifactVersion)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_ARTIFACTVERSION_WHERE_PKS_IN);

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

			for (ArtifactVersion artifactVersion :
					(List<ArtifactVersion>)query.list()) {

				map.put(artifactVersion.getPrimaryKeyObj(), artifactVersion);

				cacheResult(artifactVersion);

				uncachedPrimaryKeys.remove(artifactVersion.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					ArtifactVersionModelImpl.ENTITY_CACHE_ENABLED,
					ArtifactVersionImpl.class, primaryKey, nullModel);
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
	 * Returns all the artifact versions.
	 *
	 * @return the artifact versions
	 */
	@Override
	public List<ArtifactVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the artifact versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ArtifactVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of artifact versions
	 * @param end the upper bound of the range of artifact versions (not inclusive)
	 * @return the range of artifact versions
	 */
	@Override
	public List<ArtifactVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the artifact versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ArtifactVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of artifact versions
	 * @param end the upper bound of the range of artifact versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of artifact versions
	 */
	@Override
	public List<ArtifactVersion> findAll(
		int start, int end,
		OrderByComparator<ArtifactVersion> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the artifact versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ArtifactVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of artifact versions
	 * @param end the upper bound of the range of artifact versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of artifact versions
	 */
	@Override
	public List<ArtifactVersion> findAll(
		int start, int end,
		OrderByComparator<ArtifactVersion> orderByComparator,
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

		List<ArtifactVersion> list = null;

		if (useFinderCache) {
			list = (List<ArtifactVersion>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ARTIFACTVERSION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ARTIFACTVERSION;

				sql = sql.concat(ArtifactVersionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ArtifactVersion>)QueryUtil.list(
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
	 * Removes all the artifact versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ArtifactVersion artifactVersion : findAll()) {
			remove(artifactVersion);
		}
	}

	/**
	 * Returns the number of artifact versions.
	 *
	 * @return the number of artifact versions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_ARTIFACTVERSION);

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
		return ArtifactVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the artifact version persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			ArtifactVersionModelImpl.ENTITY_CACHE_ENABLED,
			ArtifactVersionModelImpl.FINDER_CACHE_ENABLED,
			ArtifactVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			ArtifactVersionModelImpl.ENTITY_CACHE_ENABLED,
			ArtifactVersionModelImpl.FINDER_CACHE_ENABLED,
			ArtifactVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			ArtifactVersionModelImpl.ENTITY_CACHE_ENABLED,
			ArtifactVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByReleaseAssetCategoryId = new FinderPath(
			ArtifactVersionModelImpl.ENTITY_CACHE_ENABLED,
			ArtifactVersionModelImpl.FINDER_CACHE_ENABLED,
			ArtifactVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByReleaseAssetCategoryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByReleaseAssetCategoryId =
			new FinderPath(
				ArtifactVersionModelImpl.ENTITY_CACHE_ENABLED,
				ArtifactVersionModelImpl.FINDER_CACHE_ENABLED,
				ArtifactVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByReleaseAssetCategoryId",
				new String[] {Long.class.getName()},
				ArtifactVersionModelImpl.RELEASEASSETCATEGORYID_COLUMN_BITMASK |
				ArtifactVersionModelImpl.GROUP_COLUMN_BITMASK |
				ArtifactVersionModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByReleaseAssetCategoryId = new FinderPath(
			ArtifactVersionModelImpl.ENTITY_CACHE_ENABLED,
			ArtifactVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByReleaseAssetCategoryId",
			new String[] {Long.class.getName()});

		_finderPathFetchByRACI_G_N = new FinderPath(
			ArtifactVersionModelImpl.ENTITY_CACHE_ENABLED,
			ArtifactVersionModelImpl.FINDER_CACHE_ENABLED,
			ArtifactVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByRACI_G_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			ArtifactVersionModelImpl.RELEASEASSETCATEGORYID_COLUMN_BITMASK |
			ArtifactVersionModelImpl.GROUP_COLUMN_BITMASK |
			ArtifactVersionModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByRACI_G_N = new FinderPath(
			ArtifactVersionModelImpl.ENTITY_CACHE_ENABLED,
			ArtifactVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRACI_G_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			});

		ArtifactVersionUtil.setPersistence(this);
	}

	public void destroy() {
		ArtifactVersionUtil.setPersistence(null);

		entityCache.removeCache(ArtifactVersionImpl.class.getName());

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

	private static final String _SQL_SELECT_ARTIFACTVERSION =
		"SELECT artifactVersion FROM ArtifactVersion artifactVersion";

	private static final String _SQL_SELECT_ARTIFACTVERSION_WHERE_PKS_IN =
		"SELECT artifactVersion FROM ArtifactVersion artifactVersion WHERE artifactVersionId IN (";

	private static final String _SQL_SELECT_ARTIFACTVERSION_WHERE =
		"SELECT artifactVersion FROM ArtifactVersion artifactVersion WHERE ";

	private static final String _SQL_COUNT_ARTIFACTVERSION =
		"SELECT COUNT(artifactVersion) FROM ArtifactVersion artifactVersion";

	private static final String _SQL_COUNT_ARTIFACTVERSION_WHERE =
		"SELECT COUNT(artifactVersion) FROM ArtifactVersion artifactVersion WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "artifactVersion.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ArtifactVersion exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ArtifactVersion exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ArtifactVersionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"group"});

}