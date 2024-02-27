/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link BatchEngineImportTaskService}.
 *
 * @author Shuyang Zhou
 * @see BatchEngineImportTaskService
 * @generated
 */
public class BatchEngineImportTaskServiceWrapper
	implements BatchEngineImportTaskService,
			   ServiceWrapper<BatchEngineImportTaskService> {

	public BatchEngineImportTaskServiceWrapper(
		BatchEngineImportTaskService batchEngineImportTaskService) {

		_batchEngineImportTaskService = batchEngineImportTaskService;
	}

	@Override
	public com.liferay.batch.engine.model.BatchEngineImportTask
			addBatchEngineImportTask(
				long companyId, long userId, long batchSize, String callbackURL,
				String className, byte[] content, String contentType,
				String executeStatus,
				java.util.Map<String, String> fieldNameMappingMap,
				String operation,
				java.util.Map<String, java.io.Serializable> parameters,
				String taskItemDelegateName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineImportTaskService.addBatchEngineImportTask(
			companyId, userId, batchSize, callbackURL, className, content,
			contentType, executeStatus, fieldNameMappingMap, operation,
			parameters, taskItemDelegateName);
	}

	@Override
	public com.liferay.batch.engine.model.BatchEngineImportTask
			getBatchEngineImportTask(long batchEngineImportTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineImportTaskService.getBatchEngineImportTask(
			batchEngineImportTaskId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _batchEngineImportTaskService.getOSGiServiceIdentifier();
	}

	@Override
	public BatchEngineImportTaskService getWrappedService() {
		return _batchEngineImportTaskService;
	}

	@Override
	public void setWrappedService(
		BatchEngineImportTaskService batchEngineImportTaskService) {

		_batchEngineImportTaskService = batchEngineImportTaskService;
	}

	private BatchEngineImportTaskService _batchEngineImportTaskService;

}