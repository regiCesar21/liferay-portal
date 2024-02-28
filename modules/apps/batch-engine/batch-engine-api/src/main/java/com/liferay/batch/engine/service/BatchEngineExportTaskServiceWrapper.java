/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link BatchEngineExportTaskService}.
 *
 * @author Shuyang Zhou
 * @see BatchEngineExportTaskService
 * @generated
 */
public class BatchEngineExportTaskServiceWrapper
	implements BatchEngineExportTaskService,
			   ServiceWrapper<BatchEngineExportTaskService> {

	public BatchEngineExportTaskServiceWrapper(
		BatchEngineExportTaskService batchEngineExportTaskService) {

		_batchEngineExportTaskService = batchEngineExportTaskService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _batchEngineExportTaskService.getOSGiServiceIdentifier();
	}

	@Override
	public BatchEngineExportTaskService getWrappedService() {
		return _batchEngineExportTaskService;
	}

	@Override
	public void setWrappedService(
		BatchEngineExportTaskService batchEngineExportTaskService) {

		_batchEngineExportTaskService = batchEngineExportTaskService;
	}

	private BatchEngineExportTaskService _batchEngineExportTaskService;

}