/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.service;

import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.InputStream;
import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for BatchEngineExportTask. This utility wraps
 * <code>com.liferay.batch.engine.service.impl.BatchEngineExportTaskServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Shuyang Zhou
 * @see BatchEngineExportTaskService
 * @generated
 */
public class BatchEngineExportTaskServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.batch.engine.service.impl.BatchEngineExportTaskServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static BatchEngineExportTask addBatchEngineExportTask(
			long companyId, long userId, String callbackURL, String className,
			String contentType, String executeStatus,
			List<String> fieldNamesList, Map<String, Serializable> parameters,
			String taskItemDelegateName)
		throws PortalException {

		return getService().addBatchEngineExportTask(
			companyId, userId, callbackURL, className, contentType,
			executeStatus, fieldNamesList, parameters, taskItemDelegateName);
	}

	public static BatchEngineExportTask getBatchEngineExportTask(
			long batchEngineExportTaskId)
		throws PortalException {

		return getService().getBatchEngineExportTask(batchEngineExportTaskId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static InputStream openContentInputStream(
			long batchEngineExportTaskId)
		throws PortalException {

		return getService().openContentInputStream(batchEngineExportTaskId);
	}

	public static BatchEngineExportTaskService getService() {
		return _service;
	}

	public static void setService(BatchEngineExportTaskService service) {
		_service = service;
	}

	private static volatile BatchEngineExportTaskService _service;

}