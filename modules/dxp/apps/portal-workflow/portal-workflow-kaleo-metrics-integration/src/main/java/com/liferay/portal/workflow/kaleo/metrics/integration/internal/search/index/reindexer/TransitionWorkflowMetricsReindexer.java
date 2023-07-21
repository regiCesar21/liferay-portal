/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.metrics.integration.internal.search.index.reindexer;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.model.KaleoTransition;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTransitionLocalService;
import com.liferay.portal.workflow.metrics.search.background.task.WorkflowMetricsReindexStatusMessageSender;
import com.liferay.portal.workflow.metrics.search.index.TransitionWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.search.index.reindexer.WorkflowMetricsReindexer;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = "workflow.metrics.index.entity.name=transition",
	service = WorkflowMetricsReindexer.class
)
public class TransitionWorkflowMetricsReindexer
	implements WorkflowMetricsReindexer {

	@Override
	public void reindex(long companyId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_kaleoTransitionLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property companyIdProperty = PropertyFactoryUtil.forName(
					"companyId");

				dynamicQuery.add(companyIdProperty.eq(companyId));
			});

		long total = actionableDynamicQuery.performCount();

		AtomicInteger atomicCounter = new AtomicInteger(0);

		actionableDynamicQuery.setPerformActionMethod(
			(KaleoTransition kaleoTransition) -> {
				KaleoDefinitionVersion kaleoDefinitionVersion =
					_kaleoDefinitionVersionLocalService.
						fetchKaleoDefinitionVersion(
							kaleoTransition.getKaleoDefinitionVersionId());

				if (Objects.isNull(kaleoTransition)) {
					return;
				}

				_transitionWorkflowMetricsIndexer.addTransition(
					kaleoTransition.getCompanyId(),
					kaleoTransition.getCreateDate(),
					kaleoTransition.getModifiedDate(),
					kaleoTransition.getName(),
					_getNodeId(kaleoTransition.getKaleoNodeId()),
					kaleoTransition.getKaleoDefinitionId(),
					kaleoDefinitionVersion.getVersion(),
					_getNodeId(kaleoTransition.getSourceKaleoNodeId()),
					kaleoTransition.getSourceKaleoNodeName(),
					_getNodeId(kaleoTransition.getTargetKaleoNodeId()),
					kaleoTransition.getTargetKaleoNodeName(),
					kaleoTransition.getKaleoTransitionId(),
					kaleoTransition.getUserId());

				_workflowMetricsReindexStatusMessageSender.sendStatusMessage(
					atomicCounter.incrementAndGet(), total, "transition");
			});

		actionableDynamicQuery.performActions();
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
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

	@Reference
	private KaleoNodeLocalService _kaleoNodeLocalService;

	@Reference
	private KaleoTaskLocalService _kaleoTaskLocalService;

	@Reference
	private KaleoTransitionLocalService _kaleoTransitionLocalService;

	@Reference
	private TransitionWorkflowMetricsIndexer _transitionWorkflowMetricsIndexer;

	@Reference
	private WorkflowMetricsReindexStatusMessageSender
		_workflowMetricsReindexStatusMessageSender;

}