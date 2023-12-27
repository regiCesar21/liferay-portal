/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.admin.service.persistence.impl;

import com.liferay.osb.customer.admin.exception.NoSuchProductEntryException;
import com.liferay.osb.customer.admin.model.ProductEntry;
import com.liferay.osb.customer.admin.model.impl.ProductEntryImpl;
import com.liferay.osb.customer.admin.model.impl.ProductEntryModelImpl;
import com.liferay.osb.customer.admin.service.persistence.ProductEntryPersistence;
import com.liferay.osb.customer.admin.service.persistence.ProductEntryUtil;
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
 * The persistence implementation for the product entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ProductEntryPersistenceImpl
	extends BasePersistenceImpl<ProductEntry>
	implements ProductEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ProductEntryUtil</code> to access the product entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ProductEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByKoroneikiProductKey;
	private FinderPath _finderPathCountByKoroneikiProductKey;

	/**
	 * Returns the product entry where koroneikiProductKey = &#63; or throws a <code>NoSuchProductEntryException</code> if it could not be found.
	 *
	 * @param koroneikiProductKey the koroneiki product key
	 * @return the matching product entry
	 * @throws NoSuchProductEntryException if a matching product entry could not be found
	 */
	@Override
	public ProductEntry findByKoroneikiProductKey(String koroneikiProductKey)
		throws NoSuchProductEntryException {

		ProductEntry productEntry = fetchByKoroneikiProductKey(
			koroneikiProductKey);

		if (productEntry == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("koroneikiProductKey=");
			sb.append(koroneikiProductKey);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchProductEntryException(sb.toString());
		}

		return productEntry;
	}

	/**
	 * Returns the product entry where koroneikiProductKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param koroneikiProductKey the koroneiki product key
	 * @return the matching product entry, or <code>null</code> if a matching product entry could not be found
	 */
	@Override
	public ProductEntry fetchByKoroneikiProductKey(String koroneikiProductKey) {
		return fetchByKoroneikiProductKey(koroneikiProductKey, true);
	}

	/**
	 * Returns the product entry where koroneikiProductKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param koroneikiProductKey the koroneiki product key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching product entry, or <code>null</code> if a matching product entry could not be found
	 */
	@Override
	public ProductEntry fetchByKoroneikiProductKey(
		String koroneikiProductKey, boolean useFinderCache) {

		koroneikiProductKey = Objects.toString(koroneikiProductKey, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {koroneikiProductKey};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByKoroneikiProductKey, finderArgs, this);
		}

		if (result instanceof ProductEntry) {
			ProductEntry productEntry = (ProductEntry)result;

			if (!Objects.equals(
					koroneikiProductKey,
					productEntry.getKoroneikiProductKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_PRODUCTENTRY_WHERE);

			boolean bindKoroneikiProductKey = false;

			if (koroneikiProductKey.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_KORONEIKIPRODUCTKEY_KORONEIKIPRODUCTKEY_3);
			}
			else {
				bindKoroneikiProductKey = true;

				sb.append(
					_FINDER_COLUMN_KORONEIKIPRODUCTKEY_KORONEIKIPRODUCTKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindKoroneikiProductKey) {
					queryPos.add(koroneikiProductKey);
				}

				List<ProductEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByKoroneikiProductKey, finderArgs,
							list);
					}
				}
				else {
					ProductEntry productEntry = list.get(0);

					result = productEntry;

					cacheResult(productEntry);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByKoroneikiProductKey, finderArgs);
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
			return (ProductEntry)result;
		}
	}

	/**
	 * Removes the product entry where koroneikiProductKey = &#63; from the database.
	 *
	 * @param koroneikiProductKey the koroneiki product key
	 * @return the product entry that was removed
	 */
	@Override
	public ProductEntry removeByKoroneikiProductKey(String koroneikiProductKey)
		throws NoSuchProductEntryException {

		ProductEntry productEntry = findByKoroneikiProductKey(
			koroneikiProductKey);

		return remove(productEntry);
	}

	/**
	 * Returns the number of product entries where koroneikiProductKey = &#63;.
	 *
	 * @param koroneikiProductKey the koroneiki product key
	 * @return the number of matching product entries
	 */
	@Override
	public int countByKoroneikiProductKey(String koroneikiProductKey) {
		koroneikiProductKey = Objects.toString(koroneikiProductKey, "");

		FinderPath finderPath = _finderPathCountByKoroneikiProductKey;

		Object[] finderArgs = new Object[] {koroneikiProductKey};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_PRODUCTENTRY_WHERE);

			boolean bindKoroneikiProductKey = false;

			if (koroneikiProductKey.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_KORONEIKIPRODUCTKEY_KORONEIKIPRODUCTKEY_3);
			}
			else {
				bindKoroneikiProductKey = true;

				sb.append(
					_FINDER_COLUMN_KORONEIKIPRODUCTKEY_KORONEIKIPRODUCTKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindKoroneikiProductKey) {
					queryPos.add(koroneikiProductKey);
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
		_FINDER_COLUMN_KORONEIKIPRODUCTKEY_KORONEIKIPRODUCTKEY_2 =
			"productEntry.koroneikiProductKey = ?";

	private static final String
		_FINDER_COLUMN_KORONEIKIPRODUCTKEY_KORONEIKIPRODUCTKEY_3 =
			"(productEntry.koroneikiProductKey IS NULL OR productEntry.koroneikiProductKey = '')";

	private FinderPath _finderPathFetchByName;
	private FinderPath _finderPathCountByName;

	/**
	 * Returns the product entry where name = &#63; or throws a <code>NoSuchProductEntryException</code> if it could not be found.
	 *
	 * @param name the name
	 * @return the matching product entry
	 * @throws NoSuchProductEntryException if a matching product entry could not be found
	 */
	@Override
	public ProductEntry findByName(String name)
		throws NoSuchProductEntryException {

		ProductEntry productEntry = fetchByName(name);

		if (productEntry == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchProductEntryException(sb.toString());
		}

		return productEntry;
	}

	/**
	 * Returns the product entry where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @return the matching product entry, or <code>null</code> if a matching product entry could not be found
	 */
	@Override
	public ProductEntry fetchByName(String name) {
		return fetchByName(name, true);
	}

	/**
	 * Returns the product entry where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching product entry, or <code>null</code> if a matching product entry could not be found
	 */
	@Override
	public ProductEntry fetchByName(String name, boolean useFinderCache) {
		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByName, finderArgs, this);
		}

		if (result instanceof ProductEntry) {
			ProductEntry productEntry = (ProductEntry)result;

			if (!Objects.equals(name, productEntry.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_PRODUCTENTRY_WHERE);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_NAME_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_NAME_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(name);
				}

				List<ProductEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByName, finderArgs, list);
					}
				}
				else {
					ProductEntry productEntry = list.get(0);

					result = productEntry;

					cacheResult(productEntry);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByName, finderArgs);
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
			return (ProductEntry)result;
		}
	}

	/**
	 * Removes the product entry where name = &#63; from the database.
	 *
	 * @param name the name
	 * @return the product entry that was removed
	 */
	@Override
	public ProductEntry removeByName(String name)
		throws NoSuchProductEntryException {

		ProductEntry productEntry = findByName(name);

		return remove(productEntry);
	}

	/**
	 * Returns the number of product entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching product entries
	 */
	@Override
	public int countByName(String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByName;

		Object[] finderArgs = new Object[] {name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_PRODUCTENTRY_WHERE);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_NAME_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_NAME_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

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

	private static final String _FINDER_COLUMN_NAME_NAME_2 =
		"productEntry.name = ?";

	private static final String _FINDER_COLUMN_NAME_NAME_3 =
		"(productEntry.name IS NULL OR productEntry.name = '')";

	private FinderPath _finderPathWithPaginationFindByEnvironment;
	private FinderPath _finderPathWithoutPaginationFindByEnvironment;
	private FinderPath _finderPathCountByEnvironment;

	/**
	 * Returns all the product entries where environment = &#63;.
	 *
	 * @param environment the environment
	 * @return the matching product entries
	 */
	@Override
	public List<ProductEntry> findByEnvironment(int environment) {
		return findByEnvironment(
			environment, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the product entries where environment = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ProductEntryModelImpl</code>.
	 * </p>
	 *
	 * @param environment the environment
	 * @param start the lower bound of the range of product entries
	 * @param end the upper bound of the range of product entries (not inclusive)
	 * @return the range of matching product entries
	 */
	@Override
	public List<ProductEntry> findByEnvironment(
		int environment, int start, int end) {

		return findByEnvironment(environment, start, end, null);
	}

	/**
	 * Returns an ordered range of all the product entries where environment = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ProductEntryModelImpl</code>.
	 * </p>
	 *
	 * @param environment the environment
	 * @param start the lower bound of the range of product entries
	 * @param end the upper bound of the range of product entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching product entries
	 */
	@Override
	public List<ProductEntry> findByEnvironment(
		int environment, int start, int end,
		OrderByComparator<ProductEntry> orderByComparator) {

		return findByEnvironment(
			environment, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the product entries where environment = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ProductEntryModelImpl</code>.
	 * </p>
	 *
	 * @param environment the environment
	 * @param start the lower bound of the range of product entries
	 * @param end the upper bound of the range of product entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching product entries
	 */
	@Override
	public List<ProductEntry> findByEnvironment(
		int environment, int start, int end,
		OrderByComparator<ProductEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByEnvironment;
				finderArgs = new Object[] {environment};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByEnvironment;
			finderArgs = new Object[] {
				environment, start, end, orderByComparator
			};
		}

		List<ProductEntry> list = null;

		if (useFinderCache) {
			list = (List<ProductEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ProductEntry productEntry : list) {
					if (environment != productEntry.getEnvironment()) {
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

			sb.append(_SQL_SELECT_PRODUCTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_ENVIRONMENT_ENVIRONMENT_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ProductEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(environment);

				list = (List<ProductEntry>)QueryUtil.list(
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
	 * Returns the first product entry in the ordered set where environment = &#63;.
	 *
	 * @param environment the environment
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching product entry
	 * @throws NoSuchProductEntryException if a matching product entry could not be found
	 */
	@Override
	public ProductEntry findByEnvironment_First(
			int environment, OrderByComparator<ProductEntry> orderByComparator)
		throws NoSuchProductEntryException {

		ProductEntry productEntry = fetchByEnvironment_First(
			environment, orderByComparator);

		if (productEntry != null) {
			return productEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("environment=");
		sb.append(environment);

		sb.append("}");

		throw new NoSuchProductEntryException(sb.toString());
	}

	/**
	 * Returns the first product entry in the ordered set where environment = &#63;.
	 *
	 * @param environment the environment
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching product entry, or <code>null</code> if a matching product entry could not be found
	 */
	@Override
	public ProductEntry fetchByEnvironment_First(
		int environment, OrderByComparator<ProductEntry> orderByComparator) {

		List<ProductEntry> list = findByEnvironment(
			environment, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last product entry in the ordered set where environment = &#63;.
	 *
	 * @param environment the environment
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching product entry
	 * @throws NoSuchProductEntryException if a matching product entry could not be found
	 */
	@Override
	public ProductEntry findByEnvironment_Last(
			int environment, OrderByComparator<ProductEntry> orderByComparator)
		throws NoSuchProductEntryException {

		ProductEntry productEntry = fetchByEnvironment_Last(
			environment, orderByComparator);

		if (productEntry != null) {
			return productEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("environment=");
		sb.append(environment);

		sb.append("}");

		throw new NoSuchProductEntryException(sb.toString());
	}

	/**
	 * Returns the last product entry in the ordered set where environment = &#63;.
	 *
	 * @param environment the environment
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching product entry, or <code>null</code> if a matching product entry could not be found
	 */
	@Override
	public ProductEntry fetchByEnvironment_Last(
		int environment, OrderByComparator<ProductEntry> orderByComparator) {

		int count = countByEnvironment(environment);

		if (count == 0) {
			return null;
		}

		List<ProductEntry> list = findByEnvironment(
			environment, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the product entries before and after the current product entry in the ordered set where environment = &#63;.
	 *
	 * @param productEntryId the primary key of the current product entry
	 * @param environment the environment
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next product entry
	 * @throws NoSuchProductEntryException if a product entry with the primary key could not be found
	 */
	@Override
	public ProductEntry[] findByEnvironment_PrevAndNext(
			long productEntryId, int environment,
			OrderByComparator<ProductEntry> orderByComparator)
		throws NoSuchProductEntryException {

		ProductEntry productEntry = findByPrimaryKey(productEntryId);

		Session session = null;

		try {
			session = openSession();

			ProductEntry[] array = new ProductEntryImpl[3];

			array[0] = getByEnvironment_PrevAndNext(
				session, productEntry, environment, orderByComparator, true);

			array[1] = productEntry;

			array[2] = getByEnvironment_PrevAndNext(
				session, productEntry, environment, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ProductEntry getByEnvironment_PrevAndNext(
		Session session, ProductEntry productEntry, int environment,
		OrderByComparator<ProductEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_PRODUCTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_ENVIRONMENT_ENVIRONMENT_2);

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
			sb.append(ProductEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(environment);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(productEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ProductEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the product entries where environment = &#63; from the database.
	 *
	 * @param environment the environment
	 */
	@Override
	public void removeByEnvironment(int environment) {
		for (ProductEntry productEntry :
				findByEnvironment(
					environment, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(productEntry);
		}
	}

	/**
	 * Returns the number of product entries where environment = &#63;.
	 *
	 * @param environment the environment
	 * @return the number of matching product entries
	 */
	@Override
	public int countByEnvironment(int environment) {
		FinderPath finderPath = _finderPathCountByEnvironment;

		Object[] finderArgs = new Object[] {environment};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_PRODUCTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_ENVIRONMENT_ENVIRONMENT_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(environment);

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

	private static final String _FINDER_COLUMN_ENVIRONMENT_ENVIRONMENT_2 =
		"productEntry.environment = ?";

	private FinderPath _finderPathWithPaginationFindByLicenses;
	private FinderPath _finderPathWithoutPaginationFindByLicenses;
	private FinderPath _finderPathCountByLicenses;

	/**
	 * Returns all the product entries where licenses = &#63;.
	 *
	 * @param licenses the licenses
	 * @return the matching product entries
	 */
	@Override
	public List<ProductEntry> findByLicenses(boolean licenses) {
		return findByLicenses(
			licenses, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the product entries where licenses = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ProductEntryModelImpl</code>.
	 * </p>
	 *
	 * @param licenses the licenses
	 * @param start the lower bound of the range of product entries
	 * @param end the upper bound of the range of product entries (not inclusive)
	 * @return the range of matching product entries
	 */
	@Override
	public List<ProductEntry> findByLicenses(
		boolean licenses, int start, int end) {

		return findByLicenses(licenses, start, end, null);
	}

	/**
	 * Returns an ordered range of all the product entries where licenses = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ProductEntryModelImpl</code>.
	 * </p>
	 *
	 * @param licenses the licenses
	 * @param start the lower bound of the range of product entries
	 * @param end the upper bound of the range of product entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching product entries
	 */
	@Override
	public List<ProductEntry> findByLicenses(
		boolean licenses, int start, int end,
		OrderByComparator<ProductEntry> orderByComparator) {

		return findByLicenses(licenses, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the product entries where licenses = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ProductEntryModelImpl</code>.
	 * </p>
	 *
	 * @param licenses the licenses
	 * @param start the lower bound of the range of product entries
	 * @param end the upper bound of the range of product entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching product entries
	 */
	@Override
	public List<ProductEntry> findByLicenses(
		boolean licenses, int start, int end,
		OrderByComparator<ProductEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByLicenses;
				finderArgs = new Object[] {licenses};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByLicenses;
			finderArgs = new Object[] {licenses, start, end, orderByComparator};
		}

		List<ProductEntry> list = null;

		if (useFinderCache) {
			list = (List<ProductEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ProductEntry productEntry : list) {
					if (licenses != productEntry.isLicenses()) {
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

			sb.append(_SQL_SELECT_PRODUCTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_LICENSES_LICENSES_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ProductEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(licenses);

				list = (List<ProductEntry>)QueryUtil.list(
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
	 * Returns the first product entry in the ordered set where licenses = &#63;.
	 *
	 * @param licenses the licenses
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching product entry
	 * @throws NoSuchProductEntryException if a matching product entry could not be found
	 */
	@Override
	public ProductEntry findByLicenses_First(
			boolean licenses, OrderByComparator<ProductEntry> orderByComparator)
		throws NoSuchProductEntryException {

		ProductEntry productEntry = fetchByLicenses_First(
			licenses, orderByComparator);

		if (productEntry != null) {
			return productEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("licenses=");
		sb.append(licenses);

		sb.append("}");

		throw new NoSuchProductEntryException(sb.toString());
	}

	/**
	 * Returns the first product entry in the ordered set where licenses = &#63;.
	 *
	 * @param licenses the licenses
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching product entry, or <code>null</code> if a matching product entry could not be found
	 */
	@Override
	public ProductEntry fetchByLicenses_First(
		boolean licenses, OrderByComparator<ProductEntry> orderByComparator) {

		List<ProductEntry> list = findByLicenses(
			licenses, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last product entry in the ordered set where licenses = &#63;.
	 *
	 * @param licenses the licenses
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching product entry
	 * @throws NoSuchProductEntryException if a matching product entry could not be found
	 */
	@Override
	public ProductEntry findByLicenses_Last(
			boolean licenses, OrderByComparator<ProductEntry> orderByComparator)
		throws NoSuchProductEntryException {

		ProductEntry productEntry = fetchByLicenses_Last(
			licenses, orderByComparator);

		if (productEntry != null) {
			return productEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("licenses=");
		sb.append(licenses);

		sb.append("}");

		throw new NoSuchProductEntryException(sb.toString());
	}

	/**
	 * Returns the last product entry in the ordered set where licenses = &#63;.
	 *
	 * @param licenses the licenses
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching product entry, or <code>null</code> if a matching product entry could not be found
	 */
	@Override
	public ProductEntry fetchByLicenses_Last(
		boolean licenses, OrderByComparator<ProductEntry> orderByComparator) {

		int count = countByLicenses(licenses);

		if (count == 0) {
			return null;
		}

		List<ProductEntry> list = findByLicenses(
			licenses, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the product entries before and after the current product entry in the ordered set where licenses = &#63;.
	 *
	 * @param productEntryId the primary key of the current product entry
	 * @param licenses the licenses
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next product entry
	 * @throws NoSuchProductEntryException if a product entry with the primary key could not be found
	 */
	@Override
	public ProductEntry[] findByLicenses_PrevAndNext(
			long productEntryId, boolean licenses,
			OrderByComparator<ProductEntry> orderByComparator)
		throws NoSuchProductEntryException {

		ProductEntry productEntry = findByPrimaryKey(productEntryId);

		Session session = null;

		try {
			session = openSession();

			ProductEntry[] array = new ProductEntryImpl[3];

			array[0] = getByLicenses_PrevAndNext(
				session, productEntry, licenses, orderByComparator, true);

			array[1] = productEntry;

			array[2] = getByLicenses_PrevAndNext(
				session, productEntry, licenses, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ProductEntry getByLicenses_PrevAndNext(
		Session session, ProductEntry productEntry, boolean licenses,
		OrderByComparator<ProductEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_PRODUCTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_LICENSES_LICENSES_2);

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
			sb.append(ProductEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(licenses);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(productEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ProductEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the product entries where licenses = &#63; from the database.
	 *
	 * @param licenses the licenses
	 */
	@Override
	public void removeByLicenses(boolean licenses) {
		for (ProductEntry productEntry :
				findByLicenses(
					licenses, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(productEntry);
		}
	}

	/**
	 * Returns the number of product entries where licenses = &#63;.
	 *
	 * @param licenses the licenses
	 * @return the number of matching product entries
	 */
	@Override
	public int countByLicenses(boolean licenses) {
		FinderPath finderPath = _finderPathCountByLicenses;

		Object[] finderArgs = new Object[] {licenses};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_PRODUCTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_LICENSES_LICENSES_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(licenses);

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

	private static final String _FINDER_COLUMN_LICENSES_LICENSES_2 =
		"productEntry.licenses = ?";

	public ProductEntryPersistenceImpl() {
		setModelClass(ProductEntry.class);
	}

	/**
	 * Caches the product entry in the entity cache if it is enabled.
	 *
	 * @param productEntry the product entry
	 */
	@Override
	public void cacheResult(ProductEntry productEntry) {
		entityCache.putResult(
			ProductEntryModelImpl.ENTITY_CACHE_ENABLED, ProductEntryImpl.class,
			productEntry.getPrimaryKey(), productEntry);

		finderCache.putResult(
			_finderPathFetchByKoroneikiProductKey,
			new Object[] {productEntry.getKoroneikiProductKey()}, productEntry);

		finderCache.putResult(
			_finderPathFetchByName, new Object[] {productEntry.getName()},
			productEntry);

		productEntry.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the product entries in the entity cache if it is enabled.
	 *
	 * @param productEntries the product entries
	 */
	@Override
	public void cacheResult(List<ProductEntry> productEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (productEntries.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ProductEntry productEntry : productEntries) {
			if (entityCache.getResult(
					ProductEntryModelImpl.ENTITY_CACHE_ENABLED,
					ProductEntryImpl.class, productEntry.getPrimaryKey()) ==
						null) {

				cacheResult(productEntry);
			}
			else {
				productEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all product entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ProductEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the product entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ProductEntry productEntry) {
		entityCache.removeResult(
			ProductEntryModelImpl.ENTITY_CACHE_ENABLED, ProductEntryImpl.class,
			productEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((ProductEntryModelImpl)productEntry, true);
	}

	@Override
	public void clearCache(List<ProductEntry> productEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ProductEntry productEntry : productEntries) {
			entityCache.removeResult(
				ProductEntryModelImpl.ENTITY_CACHE_ENABLED,
				ProductEntryImpl.class, productEntry.getPrimaryKey());

			clearUniqueFindersCache((ProductEntryModelImpl)productEntry, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				ProductEntryModelImpl.ENTITY_CACHE_ENABLED,
				ProductEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ProductEntryModelImpl productEntryModelImpl) {

		Object[] args = new Object[] {
			productEntryModelImpl.getKoroneikiProductKey()
		};

		finderCache.putResult(
			_finderPathCountByKoroneikiProductKey, args, Long.valueOf(1),
			false);
		finderCache.putResult(
			_finderPathFetchByKoroneikiProductKey, args, productEntryModelImpl,
			false);

		args = new Object[] {productEntryModelImpl.getName()};

		finderCache.putResult(
			_finderPathCountByName, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByName, args, productEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		ProductEntryModelImpl productEntryModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				productEntryModelImpl.getKoroneikiProductKey()
			};

			finderCache.removeResult(
				_finderPathCountByKoroneikiProductKey, args);
			finderCache.removeResult(
				_finderPathFetchByKoroneikiProductKey, args);
		}

		if ((productEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByKoroneikiProductKey.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				productEntryModelImpl.getOriginalKoroneikiProductKey()
			};

			finderCache.removeResult(
				_finderPathCountByKoroneikiProductKey, args);
			finderCache.removeResult(
				_finderPathFetchByKoroneikiProductKey, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {productEntryModelImpl.getName()};

			finderCache.removeResult(_finderPathCountByName, args);
			finderCache.removeResult(_finderPathFetchByName, args);
		}

		if ((productEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByName.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				productEntryModelImpl.getOriginalName()
			};

			finderCache.removeResult(_finderPathCountByName, args);
			finderCache.removeResult(_finderPathFetchByName, args);
		}
	}

	/**
	 * Creates a new product entry with the primary key. Does not add the product entry to the database.
	 *
	 * @param productEntryId the primary key for the new product entry
	 * @return the new product entry
	 */
	@Override
	public ProductEntry create(long productEntryId) {
		ProductEntry productEntry = new ProductEntryImpl();

		productEntry.setNew(true);
		productEntry.setPrimaryKey(productEntryId);

		return productEntry;
	}

	/**
	 * Removes the product entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param productEntryId the primary key of the product entry
	 * @return the product entry that was removed
	 * @throws NoSuchProductEntryException if a product entry with the primary key could not be found
	 */
	@Override
	public ProductEntry remove(long productEntryId)
		throws NoSuchProductEntryException {

		return remove((Serializable)productEntryId);
	}

	/**
	 * Removes the product entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the product entry
	 * @return the product entry that was removed
	 * @throws NoSuchProductEntryException if a product entry with the primary key could not be found
	 */
	@Override
	public ProductEntry remove(Serializable primaryKey)
		throws NoSuchProductEntryException {

		Session session = null;

		try {
			session = openSession();

			ProductEntry productEntry = (ProductEntry)session.get(
				ProductEntryImpl.class, primaryKey);

			if (productEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchProductEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(productEntry);
		}
		catch (NoSuchProductEntryException noSuchEntityException) {
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
	protected ProductEntry removeImpl(ProductEntry productEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(productEntry)) {
				productEntry = (ProductEntry)session.get(
					ProductEntryImpl.class, productEntry.getPrimaryKeyObj());
			}

			if (productEntry != null) {
				session.delete(productEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (productEntry != null) {
			clearCache(productEntry);
		}

		return productEntry;
	}

	@Override
	public ProductEntry updateImpl(ProductEntry productEntry) {
		boolean isNew = productEntry.isNew();

		if (!(productEntry instanceof ProductEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(productEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					productEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in productEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ProductEntry implementation " +
					productEntry.getClass());
		}

		ProductEntryModelImpl productEntryModelImpl =
			(ProductEntryModelImpl)productEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (productEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				productEntry.setCreateDate(date);
			}
			else {
				productEntry.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!productEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				productEntry.setModifiedDate(date);
			}
			else {
				productEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(productEntry);

				productEntry.setNew(false);
			}
			else {
				productEntry = (ProductEntry)session.merge(productEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!ProductEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				productEntryModelImpl.getEnvironment()
			};

			finderCache.removeResult(_finderPathCountByEnvironment, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByEnvironment, args);

			args = new Object[] {productEntryModelImpl.isLicenses()};

			finderCache.removeResult(_finderPathCountByLicenses, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByLicenses, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((productEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByEnvironment.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					productEntryModelImpl.getOriginalEnvironment()
				};

				finderCache.removeResult(_finderPathCountByEnvironment, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByEnvironment, args);

				args = new Object[] {productEntryModelImpl.getEnvironment()};

				finderCache.removeResult(_finderPathCountByEnvironment, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByEnvironment, args);
			}

			if ((productEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByLicenses.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					productEntryModelImpl.getOriginalLicenses()
				};

				finderCache.removeResult(_finderPathCountByLicenses, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByLicenses, args);

				args = new Object[] {productEntryModelImpl.isLicenses()};

				finderCache.removeResult(_finderPathCountByLicenses, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByLicenses, args);
			}
		}

		entityCache.putResult(
			ProductEntryModelImpl.ENTITY_CACHE_ENABLED, ProductEntryImpl.class,
			productEntry.getPrimaryKey(), productEntry, false);

		clearUniqueFindersCache(productEntryModelImpl, false);
		cacheUniqueFindersCache(productEntryModelImpl);

		productEntry.resetOriginalValues();

		return productEntry;
	}

	/**
	 * Returns the product entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the product entry
	 * @return the product entry
	 * @throws NoSuchProductEntryException if a product entry with the primary key could not be found
	 */
	@Override
	public ProductEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchProductEntryException {

		ProductEntry productEntry = fetchByPrimaryKey(primaryKey);

		if (productEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchProductEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return productEntry;
	}

	/**
	 * Returns the product entry with the primary key or throws a <code>NoSuchProductEntryException</code> if it could not be found.
	 *
	 * @param productEntryId the primary key of the product entry
	 * @return the product entry
	 * @throws NoSuchProductEntryException if a product entry with the primary key could not be found
	 */
	@Override
	public ProductEntry findByPrimaryKey(long productEntryId)
		throws NoSuchProductEntryException {

		return findByPrimaryKey((Serializable)productEntryId);
	}

	/**
	 * Returns the product entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the product entry
	 * @return the product entry, or <code>null</code> if a product entry with the primary key could not be found
	 */
	@Override
	public ProductEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			ProductEntryModelImpl.ENTITY_CACHE_ENABLED, ProductEntryImpl.class,
			primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		ProductEntry productEntry = (ProductEntry)serializable;

		if (productEntry == null) {
			Session session = null;

			try {
				session = openSession();

				productEntry = (ProductEntry)session.get(
					ProductEntryImpl.class, primaryKey);

				if (productEntry != null) {
					cacheResult(productEntry);
				}
				else {
					entityCache.putResult(
						ProductEntryModelImpl.ENTITY_CACHE_ENABLED,
						ProductEntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					ProductEntryModelImpl.ENTITY_CACHE_ENABLED,
					ProductEntryImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return productEntry;
	}

	/**
	 * Returns the product entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param productEntryId the primary key of the product entry
	 * @return the product entry, or <code>null</code> if a product entry with the primary key could not be found
	 */
	@Override
	public ProductEntry fetchByPrimaryKey(long productEntryId) {
		return fetchByPrimaryKey((Serializable)productEntryId);
	}

	@Override
	public Map<Serializable, ProductEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ProductEntry> map =
			new HashMap<Serializable, ProductEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ProductEntry productEntry = fetchByPrimaryKey(primaryKey);

			if (productEntry != null) {
				map.put(primaryKey, productEntry);
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
				ProductEntryModelImpl.ENTITY_CACHE_ENABLED,
				ProductEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (ProductEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_PRODUCTENTRY_WHERE_PKS_IN);

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

			for (ProductEntry productEntry : (List<ProductEntry>)query.list()) {
				map.put(productEntry.getPrimaryKeyObj(), productEntry);

				cacheResult(productEntry);

				uncachedPrimaryKeys.remove(productEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					ProductEntryModelImpl.ENTITY_CACHE_ENABLED,
					ProductEntryImpl.class, primaryKey, nullModel);
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
	 * Returns all the product entries.
	 *
	 * @return the product entries
	 */
	@Override
	public List<ProductEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the product entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ProductEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of product entries
	 * @param end the upper bound of the range of product entries (not inclusive)
	 * @return the range of product entries
	 */
	@Override
	public List<ProductEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the product entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ProductEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of product entries
	 * @param end the upper bound of the range of product entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of product entries
	 */
	@Override
	public List<ProductEntry> findAll(
		int start, int end, OrderByComparator<ProductEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the product entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ProductEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of product entries
	 * @param end the upper bound of the range of product entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of product entries
	 */
	@Override
	public List<ProductEntry> findAll(
		int start, int end, OrderByComparator<ProductEntry> orderByComparator,
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

		List<ProductEntry> list = null;

		if (useFinderCache) {
			list = (List<ProductEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_PRODUCTENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_PRODUCTENTRY;

				sql = sql.concat(ProductEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ProductEntry>)QueryUtil.list(
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
	 * Removes all the product entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ProductEntry productEntry : findAll()) {
			remove(productEntry);
		}
	}

	/**
	 * Returns the number of product entries.
	 *
	 * @return the number of product entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_PRODUCTENTRY);

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
		return ProductEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the product entry persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			ProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			ProductEntryModelImpl.FINDER_CACHE_ENABLED, ProductEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			ProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			ProductEntryModelImpl.FINDER_CACHE_ENABLED, ProductEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			ProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			ProductEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByKoroneikiProductKey = new FinderPath(
			ProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			ProductEntryModelImpl.FINDER_CACHE_ENABLED, ProductEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByKoroneikiProductKey",
			new String[] {String.class.getName()},
			ProductEntryModelImpl.KORONEIKIPRODUCTKEY_COLUMN_BITMASK);

		_finderPathCountByKoroneikiProductKey = new FinderPath(
			ProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			ProductEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByKoroneikiProductKey",
			new String[] {String.class.getName()});

		_finderPathFetchByName = new FinderPath(
			ProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			ProductEntryModelImpl.FINDER_CACHE_ENABLED, ProductEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByName",
			new String[] {String.class.getName()},
			ProductEntryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByName = new FinderPath(
			ProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			ProductEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByName",
			new String[] {String.class.getName()});

		_finderPathWithPaginationFindByEnvironment = new FinderPath(
			ProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			ProductEntryModelImpl.FINDER_CACHE_ENABLED, ProductEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByEnvironment",
			new String[] {
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByEnvironment = new FinderPath(
			ProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			ProductEntryModelImpl.FINDER_CACHE_ENABLED, ProductEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByEnvironment",
			new String[] {Integer.class.getName()},
			ProductEntryModelImpl.ENVIRONMENT_COLUMN_BITMASK |
			ProductEntryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByEnvironment = new FinderPath(
			ProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			ProductEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByEnvironment",
			new String[] {Integer.class.getName()});

		_finderPathWithPaginationFindByLicenses = new FinderPath(
			ProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			ProductEntryModelImpl.FINDER_CACHE_ENABLED, ProductEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLicenses",
			new String[] {
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByLicenses = new FinderPath(
			ProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			ProductEntryModelImpl.FINDER_CACHE_ENABLED, ProductEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByLicenses",
			new String[] {Boolean.class.getName()},
			ProductEntryModelImpl.LICENSES_COLUMN_BITMASK |
			ProductEntryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByLicenses = new FinderPath(
			ProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			ProductEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByLicenses",
			new String[] {Boolean.class.getName()});

		ProductEntryUtil.setPersistence(this);
	}

	public void destroy() {
		ProductEntryUtil.setPersistence(null);

		entityCache.removeCache(ProductEntryImpl.class.getName());

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

	private static final String _SQL_SELECT_PRODUCTENTRY =
		"SELECT productEntry FROM ProductEntry productEntry";

	private static final String _SQL_SELECT_PRODUCTENTRY_WHERE_PKS_IN =
		"SELECT productEntry FROM ProductEntry productEntry WHERE productEntryId IN (";

	private static final String _SQL_SELECT_PRODUCTENTRY_WHERE =
		"SELECT productEntry FROM ProductEntry productEntry WHERE ";

	private static final String _SQL_COUNT_PRODUCTENTRY =
		"SELECT COUNT(productEntry) FROM ProductEntry productEntry";

	private static final String _SQL_COUNT_PRODUCTENTRY_WHERE =
		"SELECT COUNT(productEntry) FROM ProductEntry productEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "productEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ProductEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ProductEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ProductEntryPersistenceImpl.class);

}