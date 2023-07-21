/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.orm;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 */
public class EntityCacheUtil {

	public static void clearCache() {
		_entityCache.clearCache();
	}

	public static void clearCache(Class<?> clazz) {
		_entityCache.clearCache(clazz);
	}

	public static void clearLocalCache() {
		_entityCache.clearLocalCache();
	}

	public static EntityCache getEntityCache() {
		return _entityCache;
	}

	public static Serializable getLocalCacheResult(
		Class<?> clazz, Serializable primaryKey) {

		return _entityCache.getLocalCacheResult(clazz, primaryKey);
	}

	public static PortalCache<Serializable, Serializable> getPortalCache(
		Class<?> clazz) {

		return _entityCache.getPortalCache(clazz);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getResult(Class, Serializable)}
	 */
	@Deprecated
	public static Serializable getResult(
		boolean entityCacheEnabled, Class<?> clazz, Serializable primaryKey) {

		return _entityCache.getResult(entityCacheEnabled, clazz, primaryKey);
	}

	public static Serializable getResult(
		Class<?> clazz, Serializable primaryKey) {

		return _entityCache.getResult(clazz, primaryKey);
	}

	public static void invalidate() {
		_entityCache.invalidate();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static Serializable loadResult(
		boolean entityCacheEnabled, Class<?> clazz, Serializable primaryKey,
		SessionFactory sessionFactory) {

		return _entityCache.loadResult(
			entityCacheEnabled, clazz, primaryKey, sessionFactory);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #putResult(Class, Serializable, Serializable)}
	 */
	@Deprecated
	public static void putResult(
		boolean entityCacheEnabled, Class<?> clazz, Serializable primaryKey,
		Serializable result) {

		_entityCache.putResult(entityCacheEnabled, clazz, primaryKey, result);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #putResult(Class, Serializable, Serializable, boolean)}
	 */
	@Deprecated
	public static void putResult(
		boolean entityCacheEnabled, Class<?> clazz, Serializable primaryKey,
		Serializable result, boolean quiet) {

		_entityCache.putResult(
			entityCacheEnabled, clazz, primaryKey, result, quiet);
	}

	public static void putResult(
		Class<?> clazz, BaseModel<?> baseModel, boolean quiet,
		boolean updateFinderCache) {

		_entityCache.putResult(clazz, baseModel, quiet, updateFinderCache);
	}

	public static void putResult(
		Class<?> clazz, Serializable primaryKey, Serializable result) {

		_entityCache.putResult(clazz, primaryKey, result);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #putResult(Class, BaseModel, boolean, boolean)}
	 */
	@Deprecated
	public static void putResult(
		Class<?> clazz, Serializable primaryKey, Serializable result,
		boolean quiet) {

		_entityCache.putResult(clazz, primaryKey, result, quiet);
	}

	public static void removeCache(String className) {
		_entityCache.removeCache(className);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #removeResult(Class, Serializable)}
	 */
	@Deprecated
	public static void removeResult(
		boolean entityCacheEnabled, Class<?> clazz, Serializable primaryKey) {

		_entityCache.removeResult(entityCacheEnabled, clazz, primaryKey);
	}

	public static void removeResult(Class<?> clazz, BaseModel<?> baseModel) {
		_entityCache.removeResult(clazz, baseModel);
	}

	public static void removeResult(Class<?> clazz, Serializable primaryKey) {
		_entityCache.removeResult(clazz, primaryKey);
	}

	private static volatile EntityCache _entityCache =
		ServiceProxyFactory.newServiceTrackedInstance(
			EntityCache.class, EntityCacheUtil.class, "_entityCache", false);

}