/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.internal.search.index;

import com.liferay.portal.workflow.metrics.search.index.name.WorkflowMetricsIndexNameBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = "workflow.metrics.index.entity.name=sla-task-result",
	service = WorkflowMetricsIndex.class
)
public class SLATaskResultWorkflowMetricsIndexImpl
	extends BaseWorkflowMetricsIndex implements WorkflowMetricsIndex {

	@Override
	public String getIndexName(long companyId) {
		return _workflowMetricsIndexNameBuilder.getIndexName(companyId);
	}

	@Override
	public String getIndexType() {
		return "WorkflowMetricsSLATaskResultType";
	}

	@Reference(target = "(workflow.metrics.index.entity.name=sla-task-result)")
	private WorkflowMetricsIndexNameBuilder _workflowMetricsIndexNameBuilder;

}