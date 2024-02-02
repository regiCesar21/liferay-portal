/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.internal.dao.orm;

import com.liferay.portal.cache.AggregatedPortalCacheListener;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheListener;
import com.liferay.portal.kernel.cache.PortalCacheListenerScope;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  @author David Truong
 *  @author Tina Tian
 */
public class CTAwarePortalCache
	implements PortalCache<Serializable, Serializable> {

	public CTAwarePortalCache(
		MultiVMPool multiVMPool, String portalCacheName, boolean mvcc) {

		_multiVMPool = multiVMPool;
		_portalCacheName = portalCacheName;
		_mvcc = mvcc;

		_productionPortalCache =
			(PortalCache<Serializable, Serializable>)multiVMPool.getPortalCache(
				portalCacheName, false, mvcc);
	}

	public void destroy() {
		_multiVMPool.removePortalCache(
			_productionPortalCache.getPortalCacheName());

		for (PortalCache<Serializable, Serializable> ctPortalCache :
				_ctPortalCaches.values()) {

			_multiVMPool.removePortalCache(ctPortalCache.getPortalCacheName());
		}

		_ctPortalCaches.clear();
	}

	@Override
	public Serializable get(Serializable key) {
		PortalCache<Serializable, Serializable> portalCache =
			_getCTPortalCache();

		return portalCache.get(key);
	}

	@Override
	public List<Serializable> getKeys() {
		throw new UnsupportedOperationException();
	}

	@Override
	public PortalCacheManager<Serializable, Serializable>
		getPortalCacheManager() {

		return _productionPortalCache.getPortalCacheManager();
	}

	@Override
	public String getPortalCacheName() {
		return _portalCacheName;
	}

	public PortalCache<Serializable, Serializable> getProductionPortalCache() {
		return _productionPortalCache;
	}

	@Override
	public boolean isBlocking() {
		return false;
	}

	@Override
	public boolean isMVCC() {
		return _mvcc;
	}

	@Override
	public void put(Serializable key, Serializable value) {
		put(key, value, PortalCache.DEFAULT_TIME_TO_LIVE);
	}

	@Override
	public void put(Serializable key, Serializable value, int timeToLive) {
		PortalCache<Serializable, Serializable> portalCache =
			_getCTPortalCache();

		portalCache.put(key, value, timeToLive);
	}

	@Override
	public void registerPortalCacheListener(
		PortalCacheListener<Serializable, Serializable> portalCacheListener) {

		aggregatedPortalCacheListener.addPortalCacheListener(
			portalCacheListener);
	}

	@Override
	public void registerPortalCacheListener(
		PortalCacheListener<Serializable, Serializable> portalCacheListener,
		PortalCacheListenerScope portalCacheListenerScope) {

		aggregatedPortalCacheListener.addPortalCacheListener(
			portalCacheListener, portalCacheListenerScope);
	}

	@Override
	public void remove(Serializable key) {
		_productionPortalCache.remove(key);

		for (PortalCache<Serializable, Serializable> ctPortalCache :
				_ctPortalCaches.values()) {

			ctPortalCache.remove(key);
		}
	}

	@Override
	public void removeAll() {
		_productionPortalCache.removeAll();

		for (PortalCache<Serializable, Serializable> ctPortalCache :
				_ctPortalCaches.values()) {

			ctPortalCache.removeAll();
		}
	}

	@Override
	public void unregisterPortalCacheListener(
		PortalCacheListener<Serializable, Serializable> portalCacheListener) {

		aggregatedPortalCacheListener.removePortalCacheListener(
			portalCacheListener);
	}

	@Override
	public void unregisterPortalCacheListeners() {
		aggregatedPortalCacheListener.clearAll();
	}

	protected final AggregatedPortalCacheListener<Serializable, Serializable>
		aggregatedPortalCacheListener = new AggregatedPortalCacheListener<>();

	private PortalCache<Serializable, Serializable> _getCTPortalCache() {
		long ctCollectionId = CTCollectionThreadLocal.getCTCollectionId();

		if (ctCollectionId == 0) {
			return _productionPortalCache;
		}

		PortalCache<Serializable, Serializable> ctPortalCache =
			_ctPortalCaches.get(ctCollectionId);

		if (ctPortalCache != null) {
			return ctPortalCache;
		}

		ctPortalCache =
			(PortalCache<Serializable, Serializable>)
				_multiVMPool.getPortalCache(
					_portalCacheName + _CHANGE_TRACKING_POSTFIX +
						ctCollectionId,
					false, _mvcc);

		PortalCache<Serializable, Serializable> previousCTPortalCache =
			_ctPortalCaches.putIfAbsent(ctCollectionId, ctPortalCache);

		if (previousCTPortalCache != null) {
			return previousCTPortalCache;
		}

		return ctPortalCache;
	}

	private static final String _CHANGE_TRACKING_POSTFIX = ".CT#";

	private final Map<Long, PortalCache<Serializable, Serializable>>
		_ctPortalCaches = new ConcurrentHashMap<>();
	private final MultiVMPool _multiVMPool;
	private final boolean _mvcc;
	private final String _portalCacheName;
	private final PortalCache<Serializable, Serializable>
		_productionPortalCache;

}