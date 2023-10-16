/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.cache;

import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(immediate = true, service = AsahExperimentCache.class)
public class AsahExperimentCache {

	public String getExperiment(long companyId, String experimentId) {
		return _portalCache.get(_generateCacheKey(companyId, experimentId));
	}

	public void putExperiment(
		long companyId, String experimentId, String experimentJSON) {

		_portalCache.put(
			_generateCacheKey(companyId, experimentId), experimentJSON,
			_SEGMENTS_EXPERIMENT_TIME_TO_LIVE_IN_SECONDS);
	}

	public void removeExperiment(long companyId, String experimentId) {
		_portalCache.remove(_generateCacheKey(companyId, experimentId));
	}

	@Reference(unbind = "-")
	protected void setMultiVMPool(MultiVMPool multiVMPool) {
		_portalCache = (PortalCache<String, String>)multiVMPool.getPortalCache(
			AsahExperimentCache.class.getName());
	}

	private String _generateCacheKey(long companyId, String experimentId) {
		return _CACHE_PREFIX + companyId + experimentId;
	}

	private static final String _CACHE_PREFIX = "experiment-";

	private static final int _SEGMENTS_EXPERIMENT_TIME_TO_LIVE_IN_SECONDS =
		3600;

	private PortalCache<String, String> _portalCache;

}