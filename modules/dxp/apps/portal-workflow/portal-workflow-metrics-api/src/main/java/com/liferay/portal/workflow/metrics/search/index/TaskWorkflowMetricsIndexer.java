/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.search.index;

import com.liferay.portal.search.document.Document;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * @author Rafael Praxedes
 */
public interface TaskWorkflowMetricsIndexer {

	public Document addTask(
		Map<Locale, String> assetTitleMap, Map<Locale, String> assetTypeMap,
		Long[] assigneeIds, String assigneeType, String className, long classPK,
		long companyId, boolean completed, Date completionDate,
		Long completionUserId, Date createDate, boolean instanceCompleted,
		Date instanceCompletionDate, long instanceId, Date modifiedDate,
		String name, long nodeId, long processId, String processVersion,
		long taskId, long userId);

	public Document completeTask(
		long companyId, Date completionDate, long completionUserId,
		long duration, Date modifiedDate, long taskId, long userId);

	public void deleteTask(long companyId, long taskId);

	public Document updateTask(
		Map<Locale, String> assetTitleMap, Map<Locale, String> assetTypeMap,
		Long[] assigneeIds, String assigneeType, long companyId,
		Date modifiedDate, long taskId, long userId);

}