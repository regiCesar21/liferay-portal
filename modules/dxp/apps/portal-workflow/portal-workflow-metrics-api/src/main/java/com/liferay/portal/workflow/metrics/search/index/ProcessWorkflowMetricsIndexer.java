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
public interface ProcessWorkflowMetricsIndexer {

	public Document addProcess(
		boolean active, long companyId, Date createDate, String description,
		Date modifiedDate, String name, long processId, String title,
		Map<Locale, String> titleMap, String version);

	public void deleteProcess(long companyId, long processId);

	public Document updateProcess(
		Boolean active, long companyId, String description, Date modifiedDate,
		long processId, String title, Map<Locale, String> titleMap,
		String version);

}