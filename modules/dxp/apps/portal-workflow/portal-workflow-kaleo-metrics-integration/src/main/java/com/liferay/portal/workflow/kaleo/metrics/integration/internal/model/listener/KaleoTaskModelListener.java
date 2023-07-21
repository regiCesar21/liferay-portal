/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.metrics.integration.internal.model.listener;

import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.metrics.search.index.NodeWorkflowMetricsIndexer;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ModelListener.class)
public class KaleoTaskModelListener extends BaseKaleoModelListener<KaleoTask> {

	@Override
	public void onAfterCreate(KaleoTask kaleoTask) {
		KaleoDefinitionVersion kaleoDefinitionVersion =
			getKaleoDefinitionVersion(kaleoTask.getKaleoDefinitionVersionId());

		if (Objects.isNull(kaleoDefinitionVersion)) {
			return;
		}

		_nodeWorkflowMetricsIndexer.addNode(
			kaleoTask.getCompanyId(), kaleoTask.getCreateDate(), false,
			kaleoTask.getModifiedDate(), kaleoTask.getName(),
			kaleoTask.getKaleoTaskId(), kaleoTask.getKaleoDefinitionId(),
			kaleoDefinitionVersion.getVersion(), false, NodeType.TASK.name());
	}

	@Override
	public void onAfterRemove(KaleoTask kaleoTask) {
		_nodeWorkflowMetricsIndexer.deleteNode(
			kaleoTask.getCompanyId(), kaleoTask.getKaleoTaskId());
	}

	@Reference
	private NodeWorkflowMetricsIndexer _nodeWorkflowMetricsIndexer;

}