/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.service.impl;

import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.base.BatchEngineImportTaskServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(
	property = {
		"json.web.service.context.name=batchengine",
		"json.web.service.context.path=BatchEngineImportTask"
	},
	service = AopService.class
)
public class BatchEngineImportTaskServiceImpl
	extends BatchEngineImportTaskServiceBaseImpl {

	@Override
	public BatchEngineImportTask addBatchEngineImportTask(
			long companyId, long userId, long batchSize, String callbackURL,
			String className, byte[] content, String contentType,
			String executeStatus, Map<String, String> fieldNameMappingMap,
			String operation, Map<String, Serializable> parameters,
			String taskItemDelegateName)
		throws PortalException {

		_checkPermission(companyId);

		return batchEngineImportTaskLocalService.addBatchEngineImportTask(
			companyId, userId, batchSize, callbackURL, className, content,
			contentType, executeStatus, fieldNameMappingMap, operation,
			parameters, taskItemDelegateName);
	}

	@Override
	public BatchEngineImportTask getBatchEngineImportTask(
			long batchEngineImportTaskId)
		throws PortalException {

		BatchEngineImportTask batchEngineImportTask =
			batchEngineImportTaskLocalService.getBatchEngineImportTask(
				batchEngineImportTaskId);

		_checkPermission(batchEngineImportTask);

		return batchEngineImportTask;
	}

	private void _checkPermission(BatchEngineImportTask batchEngineImportTask)
		throws PrincipalException {

		if (!_hasPermission(batchEngineImportTask, getPermissionChecker())) {
			throw new PrincipalException();
		}
	}

	private void _checkPermission(long companyId) throws PrincipalException {
		PermissionChecker permissionChecker = getPermissionChecker();

		if ((companyId != permissionChecker.getCompanyId()) &&
			!permissionChecker.isOmniadmin()) {

			throw new PrincipalException();
		}
	}

	private boolean _hasPermission(
		BatchEngineImportTask batchEngineImportTask,
		PermissionChecker permissionChecker) {

		if (permissionChecker.isCompanyAdmin(
				batchEngineImportTask.getCompanyId()) ||
			(batchEngineImportTask.getUserId() ==
				permissionChecker.getUserId())) {

			return true;
		}

		return false;
	}

}