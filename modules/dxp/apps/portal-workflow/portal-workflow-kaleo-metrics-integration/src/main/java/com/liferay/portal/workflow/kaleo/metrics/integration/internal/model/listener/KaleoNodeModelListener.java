/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.metrics.integration.internal.model.listener;

import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.metrics.search.index.NodeWorkflowMetricsIndexer;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ModelListener.class)
public class KaleoNodeModelListener extends BaseKaleoModelListener<KaleoNode> {

	@Override
	public void onAfterCreate(KaleoNode kaleoNode) {
		if (!Objects.equals(kaleoNode.getType(), NodeType.STATE.name())) {
			return;
		}

		KaleoDefinitionVersion kaleoDefinitionVersion =
			getKaleoDefinitionVersion(kaleoNode.getKaleoDefinitionVersionId());

		if (Objects.isNull(kaleoDefinitionVersion)) {
			return;
		}

		_nodeWorkflowMetricsIndexer.addNode(
			kaleoNode.getCompanyId(), kaleoNode.getCreateDate(),
			kaleoNode.isInitial(), kaleoNode.getModifiedDate(),
			kaleoNode.getName(), kaleoNode.getKaleoNodeId(),
			kaleoNode.getKaleoDefinitionId(),
			kaleoDefinitionVersion.getVersion(), kaleoNode.isTerminal(),
			kaleoNode.getType());
	}

	@Override
	public void onAfterRemove(KaleoNode kaleoNode) {
		if (!Objects.equals(kaleoNode.getType(), NodeType.STATE.name())) {
			return;
		}

		_nodeWorkflowMetricsIndexer.deleteNode(
			kaleoNode.getCompanyId(), kaleoNode.getKaleoNodeId());
	}

	@Reference
	private NodeWorkflowMetricsIndexer _nodeWorkflowMetricsIndexer;

}