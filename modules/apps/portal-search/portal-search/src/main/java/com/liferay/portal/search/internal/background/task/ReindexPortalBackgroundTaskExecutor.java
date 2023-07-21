/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.background.task;

import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.background.task.ReindexBackgroundTaskConstants;
import com.liferay.portal.kernel.search.background.task.ReindexStatusMessageSenderUtil;
import com.liferay.portal.search.internal.SearchEngineInitializer;

import org.osgi.framework.BundleContext;

/**
 * @author Andrew Betts
 */
public class ReindexPortalBackgroundTaskExecutor
	extends ReindexBackgroundTaskExecutor {

	public ReindexPortalBackgroundTaskExecutor(
		BundleContext bundleContext,
		PortalExecutorManager portalExecutorManager) {

		_bundleContext = bundleContext;
		_portalExecutorManager = portalExecutorManager;
	}

	@Override
	public BackgroundTaskExecutor clone() {
		return new ReindexPortalBackgroundTaskExecutor(
			_bundleContext, _portalExecutorManager);
	}

	@Override
	protected void reindex(String className, long[] companyIds)
		throws Exception {

		for (long companyId : companyIds) {
			ReindexStatusMessageSenderUtil.sendStatusMessage(
				ReindexBackgroundTaskConstants.PORTAL_START, companyId,
				companyIds);

			if (_log.isInfoEnabled()) {
				_log.info("Start reindexing company " + companyId);
			}

			try {
				SearchEngineInitializer searchEngineInitializer =
					new SearchEngineInitializer(
						_bundleContext, companyId, _portalExecutorManager);

				searchEngineInitializer.reindex();
			}
			catch (Exception exception) {
				_log.error(exception, exception);
			}
			finally {
				ReindexStatusMessageSenderUtil.sendStatusMessage(
					ReindexBackgroundTaskConstants.PORTAL_END, companyId,
					companyIds);

				if (_log.isInfoEnabled()) {
					_log.info("Finished reindexing company " + companyId);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReindexPortalBackgroundTaskExecutor.class);

	private final BundleContext _bundleContext;
	private final PortalExecutorManager _portalExecutorManager;

}