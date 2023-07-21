/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.workflow.internal.resource.v1_0;

import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowDefinition;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowDefinitionResource;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/workflow-definition.properties",
	scope = ServiceScope.PROTOTYPE, service = WorkflowDefinitionResource.class
)
public class WorkflowDefinitionResourceImpl
	extends BaseWorkflowDefinitionResourceImpl {

	@Override
	public void deleteWorkflowDefinitionUndeploy(String name, String version)
		throws Exception {

		_workflowDefinitionManager.undeployWorkflowDefinition(
			contextCompany.getCompanyId(), contextUser.getUserId(), name,
			GetterUtil.getInteger(version));
	}

	@Override
	public WorkflowDefinition getWorkflowDefinitionByName(String name)
		throws Exception {

		return _toWorkflowDefinition(
			_workflowDefinitionManager.getLatestWorkflowDefinition(
				contextCompany.getCompanyId(), name));
	}

	@Override
	public Page<WorkflowDefinition> getWorkflowDefinitionsPage(
			Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_workflowDefinitionManager.getLatestWorkflowDefinitions(
					contextCompany.getCompanyId(),
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toWorkflowDefinition),
			pagination,
			_workflowDefinitionManager.getLatestWorkflowDefinitionsCount(
				contextCompany.getCompanyId()));
	}

	@Override
	public WorkflowDefinition postWorkflowDefinitionDeploy(
			WorkflowDefinition workflowDefinition)
		throws Exception {

		String content = workflowDefinition.getContent();

		return _toWorkflowDefinition(
			_workflowDefinitionManager.deployWorkflowDefinition(
				contextCompany.getCompanyId(), contextUser.getUserId(),
				workflowDefinition.getTitle(), workflowDefinition.getName(),
				content.getBytes()));
	}

	@Override
	public WorkflowDefinition postWorkflowDefinitionSave(
			WorkflowDefinition workflowDefinition)
		throws Exception {

		String content = workflowDefinition.getContent();

		return _toWorkflowDefinition(
			_workflowDefinitionManager.saveWorkflowDefinition(
				contextCompany.getCompanyId(), contextUser.getUserId(),
				workflowDefinition.getTitle(), workflowDefinition.getName(),
				content.getBytes()));
	}

	@Override
	public WorkflowDefinition postWorkflowDefinitionUpdateActive(
			Boolean active, String name, String version)
		throws Exception {

		return _toWorkflowDefinition(
			_workflowDefinitionManager.updateActive(
				contextCompany.getCompanyId(), contextUser.getUserId(), name,
				GetterUtil.getInteger(version), active));
	}

	private WorkflowDefinition _toWorkflowDefinition(
		com.liferay.portal.kernel.workflow.WorkflowDefinition
			workflowDefinition) {

		return new WorkflowDefinition() {
			{
				active = workflowDefinition.isActive();
				content = workflowDefinition.getContent();
				dateModified = workflowDefinition.getModifiedDate();
				description = workflowDefinition.getDescription();
				name = workflowDefinition.getName();
				title = workflowDefinition.getTitle();
				version = String.valueOf(workflowDefinition.getVersion());
			}
		};
	}

	@Reference(target = "(proxy.bean=false)")
	private WorkflowDefinitionManager _workflowDefinitionManager;

}