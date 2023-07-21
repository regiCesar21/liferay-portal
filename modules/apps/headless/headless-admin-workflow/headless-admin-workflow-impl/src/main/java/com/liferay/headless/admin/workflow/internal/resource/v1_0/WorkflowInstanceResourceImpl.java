/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.workflow.internal.resource.v1_0;

import com.liferay.headless.admin.workflow.dto.v1_0.ChangeTransition;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowInstance;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowInstanceSubmit;
import com.liferay.headless.admin.workflow.internal.dto.v1_0.util.ObjectReviewedUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowInstanceResource;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.io.Serializable;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/workflow-instance.properties",
	scope = ServiceScope.PROTOTYPE, service = WorkflowInstanceResource.class
)
public class WorkflowInstanceResourceImpl
	extends BaseWorkflowInstanceResourceImpl {

	@Override
	public void deleteWorkflowInstance(Long workflowInstanceId)
		throws Exception {

		_workflowInstanceManager.deleteWorkflowInstance(
			contextCompany.getCompanyId(), workflowInstanceId);
	}

	@Override
	public WorkflowInstance getWorkflowInstance(Long workflowInstanceId)
		throws Exception {

		return _toWorkflowInstance(
			_workflowInstanceManager.getWorkflowInstance(
				contextCompany.getCompanyId(), workflowInstanceId));
	}

	@Override
	public Page<WorkflowInstance> getWorkflowInstancesPage(
			String[] assetClassNames, Long[] assetPrimaryKeys,
			Boolean completed, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_workflowInstanceManager.getWorkflowInstances(
					contextCompany.getCompanyId(), contextUser.getUserId(),
					assetClassNames, completed, pagination.getStartPosition(),
					pagination.getEndPosition(), null),
				this::_toWorkflowInstance),
			pagination,
			_workflowInstanceManager.getWorkflowInstanceCount(
				contextCompany.getCompanyId(), contextUser.getUserId(),
				assetClassNames, completed));
	}

	@Override
	public WorkflowInstance postWorkflowInstanceChangeTransition(
			Long workflowInstanceId, ChangeTransition changeTransition)
		throws Exception {

		return _toWorkflowInstance(
			_workflowInstanceManager.signalWorkflowInstance(
				contextCompany.getCompanyId(), contextUser.getUserId(),
				workflowInstanceId, changeTransition.getTransitionName(),
				null));
	}

	@Override
	public WorkflowInstance postWorkflowInstanceSubmit(
			WorkflowInstanceSubmit workflowInstanceSubmit)
		throws Exception {

		return _toWorkflowInstance(
			_workflowInstanceManager.startWorkflowInstance(
				contextCompany.getCompanyId(),
				workflowInstanceSubmit.getSiteId(), contextUser.getUserId(),
				workflowInstanceSubmit.getWorkflowDefinitionName(),
				GetterUtil.getInteger(
					workflowInstanceSubmit.getWorkflowDefinitionVersion()),
				workflowInstanceSubmit.getTransitionName(),
				_toWorkflowContext(workflowInstanceSubmit.getContext())));
	}

	private Map<String, Serializable> _toWorkflowContext(
		Map<String, ?> context) {

		return Stream.of(
			context.entrySet()
		).flatMap(
			Collection::parallelStream
		).filter(
			entry -> entry.getValue() instanceof Serializable
		).collect(
			Collectors.toMap(
				Map.Entry::getKey, entry -> (Serializable)entry.getValue())
		);
	}

	private WorkflowInstance _toWorkflowInstance(
			com.liferay.portal.kernel.workflow.WorkflowInstance
				workflowInstance)
		throws Exception {

		return new WorkflowInstance() {
			{
				completed = workflowInstance.isComplete();
				dateCompletion = workflowInstance.getEndDate();
				dateCreated = workflowInstance.getStartDate();
				id = workflowInstance.getWorkflowInstanceId();
				objectReviewed = ObjectReviewedUtil.toObjectReviewed(
					contextAcceptLanguage.getPreferredLocale(),
					workflowInstance.getWorkflowContext());
				workflowDefinitionName =
					workflowInstance.getWorkflowDefinitionName();
				workflowDefinitionVersion = String.valueOf(
					workflowInstance.getWorkflowDefinitionVersion());
			}
		};
	}

	@Reference
	private WorkflowInstanceManager _workflowInstanceManager;

}