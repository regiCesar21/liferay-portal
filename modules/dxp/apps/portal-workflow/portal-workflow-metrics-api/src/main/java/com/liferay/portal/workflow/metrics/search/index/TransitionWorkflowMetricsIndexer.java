/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.search.index;

import com.liferay.portal.search.document.Document;

import java.util.Date;

/**
 * @author Rafael Praxedes
 */
public interface TransitionWorkflowMetricsIndexer {

	public Document addTransition(
		long companyId, Date createDate, Date modifiedDate, String name,
		long nodeId, long processId, String processVersion, long sourceNodeId,
		String sourceNodeName, long targetNodeId, String targetNodeName,
		long transitionId, long userId);

	public void deleteTransition(long companyId, long transitionId);

}