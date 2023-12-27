/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.loop.service.persistence.impl;

import com.liferay.osb.loop.exception.NoSuchLoopUserNotificationSubscriptionException;
import com.liferay.osb.loop.model.LoopUserNotificationSubscription;
import com.liferay.osb.loop.model.impl.LoopUserNotificationSubscriptionImpl;
import com.liferay.osb.loop.model.impl.LoopUserNotificationSubscriptionModelImpl;
import com.liferay.osb.loop.service.persistence.LoopUserNotificationSubscriptionPersistence;
import com.liferay.osb.loop.service.persistence.LoopUserNotificationSubscriptionUtil;
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
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the loop user notification subscription service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ethan Bustad
 * @generated
 */
public class LoopUserNotificationSubscriptionPersistenceImpl
	extends BasePersistenceImpl<LoopUserNotificationSubscription>
	implements LoopUserNotificationSubscriptionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LoopUserNotificationSubscriptionUtil</code> to access the loop user notification subscription persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LoopUserNotificationSubscriptionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private int _databaseInMaxParameters;
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByLPI_CNI_CP_DT;
	private FinderPath _finderPathCountByLPI_CNI_CP_DT;

	/**
	 * Returns the loop user notification subscription where loopPersonId = &#63; and classNameId = &#63; and classPK = &#63; and deliveryType = &#63; or throws a <code>NoSuchLoopUserNotificationSubscriptionException</code> if it could not be found.
	 *
	 * @param loopPersonId the loop person ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param deliveryType the delivery type
	 * @return the matching loop user notification subscription
	 * @throws NoSuchLoopUserNotificationSubscriptionException if a matching loop user notification subscription could not be found
	 */
	@Override
	public LoopUserNotificationSubscription findByLPI_CNI_CP_DT(
			long loopPersonId, long classNameId, long classPK, int deliveryType)
		throws NoSuchLoopUserNotificationSubscriptionException {

		LoopUserNotificationSubscription loopUserNotificationSubscription =
			fetchByLPI_CNI_CP_DT(
				loopPersonId, classNameId, classPK, deliveryType);

		if (loopUserNotificationSubscription == null) {
			StringBundler sb = new StringBundler(10);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("loopPersonId=");
			sb.append(loopPersonId);

			sb.append(", classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append(", deliveryType=");
			sb.append(deliveryType);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchLoopUserNotificationSubscriptionException(
				sb.toString());
		}

		return loopUserNotificationSubscription;
	}

	/**
	 * Returns the loop user notification subscription where loopPersonId = &#63; and classNameId = &#63; and classPK = &#63; and deliveryType = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param loopPersonId the loop person ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param deliveryType the delivery type
	 * @return the matching loop user notification subscription, or <code>null</code> if a matching loop user notification subscription could not be found
	 */
	@Override
	public LoopUserNotificationSubscription fetchByLPI_CNI_CP_DT(
		long loopPersonId, long classNameId, long classPK, int deliveryType) {

		return fetchByLPI_CNI_CP_DT(
			loopPersonId, classNameId, classPK, deliveryType, true);
	}

	/**
	 * Returns the loop user notification subscription where loopPersonId = &#63; and classNameId = &#63; and classPK = &#63; and deliveryType = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param loopPersonId the loop person ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param deliveryType the delivery type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching loop user notification subscription, or <code>null</code> if a matching loop user notification subscription could not be found
	 */
	@Override
	public LoopUserNotificationSubscription fetchByLPI_CNI_CP_DT(
		long loopPersonId, long classNameId, long classPK, int deliveryType,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				loopPersonId, classNameId, classPK, deliveryType
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByLPI_CNI_CP_DT, finderArgs, this);
		}

		if (result instanceof LoopUserNotificationSubscription) {
			LoopUserNotificationSubscription loopUserNotificationSubscription =
				(LoopUserNotificationSubscription)result;

			if ((loopPersonId !=
					loopUserNotificationSubscription.getLoopPersonId()) ||
				(classNameId !=
					loopUserNotificationSubscription.getClassNameId()) ||
				(classPK != loopUserNotificationSubscription.getClassPK()) ||
				(deliveryType !=
					loopUserNotificationSubscription.getDeliveryType())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_SELECT_LOOPUSERNOTIFICATIONSUBSCRIPTION_WHERE);

			sb.append(_FINDER_COLUMN_LPI_CNI_CP_DT_LOOPPERSONID_2);

			sb.append(_FINDER_COLUMN_LPI_CNI_CP_DT_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_LPI_CNI_CP_DT_CLASSPK_2);

			sb.append(_FINDER_COLUMN_LPI_CNI_CP_DT_DELIVERYTYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(loopPersonId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(deliveryType);

				List<LoopUserNotificationSubscription> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByLPI_CNI_CP_DT, finderArgs, list);
					}
				}
				else {
					LoopUserNotificationSubscription
						loopUserNotificationSubscription = list.get(0);

					result = loopUserNotificationSubscription;

					cacheResult(loopUserNotificationSubscription);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByLPI_CNI_CP_DT, finderArgs);
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
			return (LoopUserNotificationSubscription)result;
		}
	}

	/**
	 * Removes the loop user notification subscription where loopPersonId = &#63; and classNameId = &#63; and classPK = &#63; and deliveryType = &#63; from the database.
	 *
	 * @param loopPersonId the loop person ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param deliveryType the delivery type
	 * @return the loop user notification subscription that was removed
	 */
	@Override
	public LoopUserNotificationSubscription removeByLPI_CNI_CP_DT(
			long loopPersonId, long classNameId, long classPK, int deliveryType)
		throws NoSuchLoopUserNotificationSubscriptionException {

		LoopUserNotificationSubscription loopUserNotificationSubscription =
			findByLPI_CNI_CP_DT(
				loopPersonId, classNameId, classPK, deliveryType);

		return remove(loopUserNotificationSubscription);
	}

	/**
	 * Returns the number of loop user notification subscriptions where loopPersonId = &#63; and classNameId = &#63; and classPK = &#63; and deliveryType = &#63;.
	 *
	 * @param loopPersonId the loop person ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param deliveryType the delivery type
	 * @return the number of matching loop user notification subscriptions
	 */
	@Override
	public int countByLPI_CNI_CP_DT(
		long loopPersonId, long classNameId, long classPK, int deliveryType) {

		FinderPath finderPath = _finderPathCountByLPI_CNI_CP_DT;

		Object[] finderArgs = new Object[] {
			loopPersonId, classNameId, classPK, deliveryType
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_LOOPUSERNOTIFICATIONSUBSCRIPTION_WHERE);

			sb.append(_FINDER_COLUMN_LPI_CNI_CP_DT_LOOPPERSONID_2);

			sb.append(_FINDER_COLUMN_LPI_CNI_CP_DT_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_LPI_CNI_CP_DT_CLASSPK_2);

			sb.append(_FINDER_COLUMN_LPI_CNI_CP_DT_DELIVERYTYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(loopPersonId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(deliveryType);

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

	private static final String _FINDER_COLUMN_LPI_CNI_CP_DT_LOOPPERSONID_2 =
		"loopUserNotificationSubscription.loopPersonId = ? AND ";

	private static final String _FINDER_COLUMN_LPI_CNI_CP_DT_CLASSNAMEID_2 =
		"loopUserNotificationSubscription.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_LPI_CNI_CP_DT_CLASSPK_2 =
		"loopUserNotificationSubscription.classPK = ? AND ";

	private static final String _FINDER_COLUMN_LPI_CNI_CP_DT_DELIVERYTYPE_2 =
		"loopUserNotificationSubscription.deliveryType = ?";

	public LoopUserNotificationSubscriptionPersistenceImpl() {
		setModelClass(LoopUserNotificationSubscription.class);
	}

	/**
	 * Caches the loop user notification subscription in the entity cache if it is enabled.
	 *
	 * @param loopUserNotificationSubscription the loop user notification subscription
	 */
	@Override
	public void cacheResult(
		LoopUserNotificationSubscription loopUserNotificationSubscription) {

		entityCache.putResult(
			LoopUserNotificationSubscriptionModelImpl.ENTITY_CACHE_ENABLED,
			LoopUserNotificationSubscriptionImpl.class,
			loopUserNotificationSubscription.getPrimaryKey(),
			loopUserNotificationSubscription);

		finderCache.putResult(
			_finderPathFetchByLPI_CNI_CP_DT,
			new Object[] {
				loopUserNotificationSubscription.getLoopPersonId(),
				loopUserNotificationSubscription.getClassNameId(),
				loopUserNotificationSubscription.getClassPK(),
				loopUserNotificationSubscription.getDeliveryType()
			},
			loopUserNotificationSubscription);

		loopUserNotificationSubscription.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the loop user notification subscriptions in the entity cache if it is enabled.
	 *
	 * @param loopUserNotificationSubscriptions the loop user notification subscriptions
	 */
	@Override
	public void cacheResult(
		List<LoopUserNotificationSubscription>
			loopUserNotificationSubscriptions) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (loopUserNotificationSubscriptions.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (LoopUserNotificationSubscription loopUserNotificationSubscription :
				loopUserNotificationSubscriptions) {

			if (entityCache.getResult(
					LoopUserNotificationSubscriptionModelImpl.
						ENTITY_CACHE_ENABLED,
					LoopUserNotificationSubscriptionImpl.class,
					loopUserNotificationSubscription.getPrimaryKey()) == null) {

				cacheResult(loopUserNotificationSubscription);
			}
			else {
				loopUserNotificationSubscription.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all loop user notification subscriptions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LoopUserNotificationSubscriptionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the loop user notification subscription.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		LoopUserNotificationSubscription loopUserNotificationSubscription) {

		entityCache.removeResult(
			LoopUserNotificationSubscriptionModelImpl.ENTITY_CACHE_ENABLED,
			LoopUserNotificationSubscriptionImpl.class,
			loopUserNotificationSubscription.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(LoopUserNotificationSubscriptionModelImpl)
				loopUserNotificationSubscription,
			true);
	}

	@Override
	public void clearCache(
		List<LoopUserNotificationSubscription>
			loopUserNotificationSubscriptions) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LoopUserNotificationSubscription loopUserNotificationSubscription :
				loopUserNotificationSubscriptions) {

			entityCache.removeResult(
				LoopUserNotificationSubscriptionModelImpl.ENTITY_CACHE_ENABLED,
				LoopUserNotificationSubscriptionImpl.class,
				loopUserNotificationSubscription.getPrimaryKey());

			clearUniqueFindersCache(
				(LoopUserNotificationSubscriptionModelImpl)
					loopUserNotificationSubscription,
				true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				LoopUserNotificationSubscriptionModelImpl.ENTITY_CACHE_ENABLED,
				LoopUserNotificationSubscriptionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		LoopUserNotificationSubscriptionModelImpl
			loopUserNotificationSubscriptionModelImpl) {

		Object[] args = new Object[] {
			loopUserNotificationSubscriptionModelImpl.getLoopPersonId(),
			loopUserNotificationSubscriptionModelImpl.getClassNameId(),
			loopUserNotificationSubscriptionModelImpl.getClassPK(),
			loopUserNotificationSubscriptionModelImpl.getDeliveryType()
		};

		finderCache.putResult(
			_finderPathCountByLPI_CNI_CP_DT, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByLPI_CNI_CP_DT, args,
			loopUserNotificationSubscriptionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		LoopUserNotificationSubscriptionModelImpl
			loopUserNotificationSubscriptionModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				loopUserNotificationSubscriptionModelImpl.getLoopPersonId(),
				loopUserNotificationSubscriptionModelImpl.getClassNameId(),
				loopUserNotificationSubscriptionModelImpl.getClassPK(),
				loopUserNotificationSubscriptionModelImpl.getDeliveryType()
			};

			finderCache.removeResult(_finderPathCountByLPI_CNI_CP_DT, args);
			finderCache.removeResult(_finderPathFetchByLPI_CNI_CP_DT, args);
		}

		if ((loopUserNotificationSubscriptionModelImpl.getColumnBitmask() &
			 _finderPathFetchByLPI_CNI_CP_DT.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				loopUserNotificationSubscriptionModelImpl.
					getOriginalLoopPersonId(),
				loopUserNotificationSubscriptionModelImpl.
					getOriginalClassNameId(),
				loopUserNotificationSubscriptionModelImpl.getOriginalClassPK(),
				loopUserNotificationSubscriptionModelImpl.
					getOriginalDeliveryType()
			};

			finderCache.removeResult(_finderPathCountByLPI_CNI_CP_DT, args);
			finderCache.removeResult(_finderPathFetchByLPI_CNI_CP_DT, args);
		}
	}

	/**
	 * Creates a new loop user notification subscription with the primary key. Does not add the loop user notification subscription to the database.
	 *
	 * @param loopUserNotificationSubscriptionId the primary key for the new loop user notification subscription
	 * @return the new loop user notification subscription
	 */
	@Override
	public LoopUserNotificationSubscription create(
		long loopUserNotificationSubscriptionId) {

		LoopUserNotificationSubscription loopUserNotificationSubscription =
			new LoopUserNotificationSubscriptionImpl();

		loopUserNotificationSubscription.setNew(true);
		loopUserNotificationSubscription.setPrimaryKey(
			loopUserNotificationSubscriptionId);

		return loopUserNotificationSubscription;
	}

	/**
	 * Removes the loop user notification subscription with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param loopUserNotificationSubscriptionId the primary key of the loop user notification subscription
	 * @return the loop user notification subscription that was removed
	 * @throws NoSuchLoopUserNotificationSubscriptionException if a loop user notification subscription with the primary key could not be found
	 */
	@Override
	public LoopUserNotificationSubscription remove(
			long loopUserNotificationSubscriptionId)
		throws NoSuchLoopUserNotificationSubscriptionException {

		return remove((Serializable)loopUserNotificationSubscriptionId);
	}

	/**
	 * Removes the loop user notification subscription with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the loop user notification subscription
	 * @return the loop user notification subscription that was removed
	 * @throws NoSuchLoopUserNotificationSubscriptionException if a loop user notification subscription with the primary key could not be found
	 */
	@Override
	public LoopUserNotificationSubscription remove(Serializable primaryKey)
		throws NoSuchLoopUserNotificationSubscriptionException {

		Session session = null;

		try {
			session = openSession();

			LoopUserNotificationSubscription loopUserNotificationSubscription =
				(LoopUserNotificationSubscription)session.get(
					LoopUserNotificationSubscriptionImpl.class, primaryKey);

			if (loopUserNotificationSubscription == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLoopUserNotificationSubscriptionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(loopUserNotificationSubscription);
		}
		catch (NoSuchLoopUserNotificationSubscriptionException
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
	protected LoopUserNotificationSubscription removeImpl(
		LoopUserNotificationSubscription loopUserNotificationSubscription) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(loopUserNotificationSubscription)) {
				loopUserNotificationSubscription =
					(LoopUserNotificationSubscription)session.get(
						LoopUserNotificationSubscriptionImpl.class,
						loopUserNotificationSubscription.getPrimaryKeyObj());
			}

			if (loopUserNotificationSubscription != null) {
				session.delete(loopUserNotificationSubscription);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (loopUserNotificationSubscription != null) {
			clearCache(loopUserNotificationSubscription);
		}

		return loopUserNotificationSubscription;
	}

	@Override
	public LoopUserNotificationSubscription updateImpl(
		LoopUserNotificationSubscription loopUserNotificationSubscription) {

		boolean isNew = loopUserNotificationSubscription.isNew();

		if (!(loopUserNotificationSubscription instanceof
				LoopUserNotificationSubscriptionModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					loopUserNotificationSubscription.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					loopUserNotificationSubscription);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in loopUserNotificationSubscription proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LoopUserNotificationSubscription implementation " +
					loopUserNotificationSubscription.getClass());
		}

		LoopUserNotificationSubscriptionModelImpl
			loopUserNotificationSubscriptionModelImpl =
				(LoopUserNotificationSubscriptionModelImpl)
					loopUserNotificationSubscription;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(loopUserNotificationSubscription);

				loopUserNotificationSubscription.setNew(false);
			}
			else {
				loopUserNotificationSubscription =
					(LoopUserNotificationSubscription)session.merge(
						loopUserNotificationSubscription);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LoopUserNotificationSubscriptionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			LoopUserNotificationSubscriptionModelImpl.ENTITY_CACHE_ENABLED,
			LoopUserNotificationSubscriptionImpl.class,
			loopUserNotificationSubscription.getPrimaryKey(),
			loopUserNotificationSubscription, false);

		clearUniqueFindersCache(
			loopUserNotificationSubscriptionModelImpl, false);
		cacheUniqueFindersCache(loopUserNotificationSubscriptionModelImpl);

		loopUserNotificationSubscription.resetOriginalValues();

		return loopUserNotificationSubscription;
	}

	/**
	 * Returns the loop user notification subscription with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the loop user notification subscription
	 * @return the loop user notification subscription
	 * @throws NoSuchLoopUserNotificationSubscriptionException if a loop user notification subscription with the primary key could not be found
	 */
	@Override
	public LoopUserNotificationSubscription findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchLoopUserNotificationSubscriptionException {

		LoopUserNotificationSubscription loopUserNotificationSubscription =
			fetchByPrimaryKey(primaryKey);

		if (loopUserNotificationSubscription == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLoopUserNotificationSubscriptionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return loopUserNotificationSubscription;
	}

	/**
	 * Returns the loop user notification subscription with the primary key or throws a <code>NoSuchLoopUserNotificationSubscriptionException</code> if it could not be found.
	 *
	 * @param loopUserNotificationSubscriptionId the primary key of the loop user notification subscription
	 * @return the loop user notification subscription
	 * @throws NoSuchLoopUserNotificationSubscriptionException if a loop user notification subscription with the primary key could not be found
	 */
	@Override
	public LoopUserNotificationSubscription findByPrimaryKey(
			long loopUserNotificationSubscriptionId)
		throws NoSuchLoopUserNotificationSubscriptionException {

		return findByPrimaryKey(
			(Serializable)loopUserNotificationSubscriptionId);
	}

	/**
	 * Returns the loop user notification subscription with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the loop user notification subscription
	 * @return the loop user notification subscription, or <code>null</code> if a loop user notification subscription with the primary key could not be found
	 */
	@Override
	public LoopUserNotificationSubscription fetchByPrimaryKey(
		Serializable primaryKey) {

		Serializable serializable = entityCache.getResult(
			LoopUserNotificationSubscriptionModelImpl.ENTITY_CACHE_ENABLED,
			LoopUserNotificationSubscriptionImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		LoopUserNotificationSubscription loopUserNotificationSubscription =
			(LoopUserNotificationSubscription)serializable;

		if (loopUserNotificationSubscription == null) {
			Session session = null;

			try {
				session = openSession();

				loopUserNotificationSubscription =
					(LoopUserNotificationSubscription)session.get(
						LoopUserNotificationSubscriptionImpl.class, primaryKey);

				if (loopUserNotificationSubscription != null) {
					cacheResult(loopUserNotificationSubscription);
				}
				else {
					entityCache.putResult(
						LoopUserNotificationSubscriptionModelImpl.
							ENTITY_CACHE_ENABLED,
						LoopUserNotificationSubscriptionImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					LoopUserNotificationSubscriptionModelImpl.
						ENTITY_CACHE_ENABLED,
					LoopUserNotificationSubscriptionImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return loopUserNotificationSubscription;
	}

	/**
	 * Returns the loop user notification subscription with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param loopUserNotificationSubscriptionId the primary key of the loop user notification subscription
	 * @return the loop user notification subscription, or <code>null</code> if a loop user notification subscription with the primary key could not be found
	 */
	@Override
	public LoopUserNotificationSubscription fetchByPrimaryKey(
		long loopUserNotificationSubscriptionId) {

		return fetchByPrimaryKey(
			(Serializable)loopUserNotificationSubscriptionId);
	}

	@Override
	public Map<Serializable, LoopUserNotificationSubscription>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, LoopUserNotificationSubscription> map =
			new HashMap<Serializable, LoopUserNotificationSubscription>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			LoopUserNotificationSubscription loopUserNotificationSubscription =
				fetchByPrimaryKey(primaryKey);

			if (loopUserNotificationSubscription != null) {
				map.put(primaryKey, loopUserNotificationSubscription);
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
				LoopUserNotificationSubscriptionModelImpl.ENTITY_CACHE_ENABLED,
				LoopUserNotificationSubscriptionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(
						primaryKey,
						(LoopUserNotificationSubscription)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_LOOPUSERNOTIFICATIONSUBSCRIPTION_WHERE_PKS_IN);

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

			for (LoopUserNotificationSubscription
					loopUserNotificationSubscription :
						(List<LoopUserNotificationSubscription>)query.list()) {

				map.put(
					loopUserNotificationSubscription.getPrimaryKeyObj(),
					loopUserNotificationSubscription);

				cacheResult(loopUserNotificationSubscription);

				uncachedPrimaryKeys.remove(
					loopUserNotificationSubscription.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					LoopUserNotificationSubscriptionModelImpl.
						ENTITY_CACHE_ENABLED,
					LoopUserNotificationSubscriptionImpl.class, primaryKey,
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
	 * Returns all the loop user notification subscriptions.
	 *
	 * @return the loop user notification subscriptions
	 */
	@Override
	public List<LoopUserNotificationSubscription> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the loop user notification subscriptions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopUserNotificationSubscriptionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop user notification subscriptions
	 * @param end the upper bound of the range of loop user notification subscriptions (not inclusive)
	 * @return the range of loop user notification subscriptions
	 */
	@Override
	public List<LoopUserNotificationSubscription> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the loop user notification subscriptions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopUserNotificationSubscriptionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop user notification subscriptions
	 * @param end the upper bound of the range of loop user notification subscriptions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of loop user notification subscriptions
	 */
	@Override
	public List<LoopUserNotificationSubscription> findAll(
		int start, int end,
		OrderByComparator<LoopUserNotificationSubscription> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the loop user notification subscriptions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoopUserNotificationSubscriptionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of loop user notification subscriptions
	 * @param end the upper bound of the range of loop user notification subscriptions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of loop user notification subscriptions
	 */
	@Override
	public List<LoopUserNotificationSubscription> findAll(
		int start, int end,
		OrderByComparator<LoopUserNotificationSubscription> orderByComparator,
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

		List<LoopUserNotificationSubscription> list = null;

		if (useFinderCache) {
			list =
				(List<LoopUserNotificationSubscription>)finderCache.getResult(
					finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_LOOPUSERNOTIFICATIONSUBSCRIPTION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_LOOPUSERNOTIFICATIONSUBSCRIPTION;

				sql = sql.concat(
					LoopUserNotificationSubscriptionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<LoopUserNotificationSubscription>)QueryUtil.list(
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
	 * Removes all the loop user notification subscriptions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LoopUserNotificationSubscription loopUserNotificationSubscription :
				findAll()) {

			remove(loopUserNotificationSubscription);
		}
	}

	/**
	 * Returns the number of loop user notification subscriptions.
	 *
	 * @return the number of loop user notification subscriptions
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
					_SQL_COUNT_LOOPUSERNOTIFICATIONSUBSCRIPTION);

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
		return LoopUserNotificationSubscriptionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the loop user notification subscription persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get("value.object.finder.cache.list.threshold"));

		_finderPathWithPaginationFindAll = new FinderPath(
			LoopUserNotificationSubscriptionModelImpl.ENTITY_CACHE_ENABLED,
			LoopUserNotificationSubscriptionModelImpl.FINDER_CACHE_ENABLED,
			LoopUserNotificationSubscriptionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			LoopUserNotificationSubscriptionModelImpl.ENTITY_CACHE_ENABLED,
			LoopUserNotificationSubscriptionModelImpl.FINDER_CACHE_ENABLED,
			LoopUserNotificationSubscriptionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			LoopUserNotificationSubscriptionModelImpl.ENTITY_CACHE_ENABLED,
			LoopUserNotificationSubscriptionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByLPI_CNI_CP_DT = new FinderPath(
			LoopUserNotificationSubscriptionModelImpl.ENTITY_CACHE_ENABLED,
			LoopUserNotificationSubscriptionModelImpl.FINDER_CACHE_ENABLED,
			LoopUserNotificationSubscriptionImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByLPI_CNI_CP_DT",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			},
			LoopUserNotificationSubscriptionModelImpl.
				LOOPPERSONID_COLUMN_BITMASK |
			LoopUserNotificationSubscriptionModelImpl.
				CLASSNAMEID_COLUMN_BITMASK |
			LoopUserNotificationSubscriptionModelImpl.CLASSPK_COLUMN_BITMASK |
			LoopUserNotificationSubscriptionModelImpl.
				DELIVERYTYPE_COLUMN_BITMASK);

		_finderPathCountByLPI_CNI_CP_DT = new FinderPath(
			LoopUserNotificationSubscriptionModelImpl.ENTITY_CACHE_ENABLED,
			LoopUserNotificationSubscriptionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLPI_CNI_CP_DT",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			});

		LoopUserNotificationSubscriptionUtil.setPersistence(this);
	}

	public void destroy() {
		LoopUserNotificationSubscriptionUtil.setPersistence(null);

		entityCache.removeCache(
			LoopUserNotificationSubscriptionImpl.class.getName());

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

	private static final String _SQL_SELECT_LOOPUSERNOTIFICATIONSUBSCRIPTION =
		"SELECT loopUserNotificationSubscription FROM LoopUserNotificationSubscription loopUserNotificationSubscription";

	private static final String
		_SQL_SELECT_LOOPUSERNOTIFICATIONSUBSCRIPTION_WHERE_PKS_IN =
			"SELECT loopUserNotificationSubscription FROM LoopUserNotificationSubscription loopUserNotificationSubscription WHERE loopUserNotificationSubscriptionId IN (";

	private static final String
		_SQL_SELECT_LOOPUSERNOTIFICATIONSUBSCRIPTION_WHERE =
			"SELECT loopUserNotificationSubscription FROM LoopUserNotificationSubscription loopUserNotificationSubscription WHERE ";

	private static final String _SQL_COUNT_LOOPUSERNOTIFICATIONSUBSCRIPTION =
		"SELECT COUNT(loopUserNotificationSubscription) FROM LoopUserNotificationSubscription loopUserNotificationSubscription";

	private static final String
		_SQL_COUNT_LOOPUSERNOTIFICATIONSUBSCRIPTION_WHERE =
			"SELECT COUNT(loopUserNotificationSubscription) FROM LoopUserNotificationSubscription loopUserNotificationSubscription WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"loopUserNotificationSubscription.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LoopUserNotificationSubscription exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LoopUserNotificationSubscription exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LoopUserNotificationSubscriptionPersistenceImpl.class);

}