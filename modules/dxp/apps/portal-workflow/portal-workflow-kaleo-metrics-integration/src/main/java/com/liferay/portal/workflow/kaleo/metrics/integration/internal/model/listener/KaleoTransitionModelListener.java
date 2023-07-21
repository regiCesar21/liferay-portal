/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.metrics.integration.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.model.KaleoTransition;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskLocalService;
import com.liferay.portal.workflow.metrics.search.index.TransitionWorkflowMetricsIndexer;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ModelListener.class)
public class KaleoTransitionModelListener
	extends BaseKaleoModelListener<KaleoTransition> {

	@Override
	public void onAfterCreate(KaleoTransition kaleoTransition) {
		KaleoDefinitionVersion kaleoDefinitionVersion =
			getKaleoDefinitionVersion(
				kaleoTransition.getKaleoDefinitionVersionId());

		if (Objects.isNull(kaleoDefinitionVersion)) {
			return;
		}

		try {
			_transitionWorkflowMetricsIndexer.addTransition(
				kaleoTransition.getCompanyId(), kaleoTransition.getCreateDate(),
				kaleoTransition.getModifiedDate(), kaleoTransition.getName(),
				_getNodeId(kaleoTransition.getKaleoNodeId()),
				kaleoTransition.getKaleoDefinitionId(),
				kaleoDefinitionVersion.getVersion(),
				_getNodeId(kaleoTransition.getSourceKaleoNodeId()),
				kaleoTransition.getSourceKaleoNodeName(),
				_getNodeId(kaleoTransition.getTargetKaleoNodeId()),
				kaleoTransition.getTargetKaleoNodeName(),
				kaleoTransition.getKaleoTransitionId(),
				kaleoTransition.getUserId());
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Override
	public void onAfterRemove(KaleoTransition kaleoTransition) {
		_transitionWorkflowMetricsIndexer.deleteTransition(
			kaleoTransition.getCompanyId(),
			kaleoTransition.getKaleoTransitionId());
	}

	private long _getNodeId(long kaleoNodeId) throws PortalException {
		KaleoNode kaleoNode = _kaleoNodeLocalService.fetchKaleoNode(
			kaleoNodeId);

		if ((kaleoNode == null) ||
			!Objects.equals(kaleoNode.getType(), NodeType.TASK.name())) {

			return kaleoNodeId;
		}

		KaleoTask kaleoTask = _kaleoTaskLocalService.getKaleoNodeKaleoTask(
			kaleoNode.getKaleoNodeId());

		return kaleoTask.getKaleoTaskId();
	}

	@Reference
	private KaleoNodeLocalService _kaleoNodeLocalService;

	@Reference
	private KaleoTaskLocalService _kaleoTaskLocalService;

	@Reference
	private TransitionWorkflowMetricsIndexer _transitionWorkflowMetricsIndexer;

}