/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.orm;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.model.BaseModel;

import java.io.Serializable;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface EntityCache {

	public void clearCache();

	public void clearCache(Class<?> clazz);

	public void clearLocalCache();

	public Serializable getLocalCacheResult(
		Class<?> clazz, Serializable primaryKey);

	public PortalCache<Serializable, Serializable> getPortalCache(
		Class<?> clazz);

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getResult(Class, Serializable)}
	 */
	@Deprecated
	public Serializable getResult(
		boolean entityCacheEnabled, Class<?> clazz, Serializable primaryKey);

	public Serializable getResult(Class<?> clazz, Serializable primaryKey);

	public void invalidate();

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public Serializable loadResult(
		boolean entityCacheEnabled, Class<?> clazz, Serializable primaryKey,
		SessionFactory sessionFactory);

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #putResult(Class, Serializable, Serializable)}
	 */
	@Deprecated
	public void putResult(
		boolean entityCacheEnabled, Class<?> clazz, Serializable primaryKey,
		Serializable result);

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #putResult(Class, Serializable, Serializable, boolean)}
	 */
	@Deprecated
	public void putResult(
		boolean entityCacheEnabled, Class<?> clazz, Serializable primaryKey,
		Serializable result, boolean quiet);

	public void putResult(
		Class<?> clazz, BaseModel<?> baseModel, boolean quiet,
		boolean updateFinderCache);

	public void putResult(
		Class<?> clazz, Serializable primaryKey, Serializable result);

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #putResult(Class, BaseModel, boolean, boolean)}
	 */
	@Deprecated
	public void putResult(
		Class<?> clazz, Serializable primaryKey, Serializable result,
		boolean quiet);

	public void removeCache(String className);

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #removeResult(Class, Serializable)}
	 */
	@Deprecated
	public void removeResult(
		boolean entityCacheEnabled, Class<?> clazz, Serializable primaryKey);

	public void removeResult(Class<?> clazz, BaseModel<?> baseModel);

	public void removeResult(Class<?> clazz, Serializable primaryKey);

}