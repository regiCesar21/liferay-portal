/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.blueprint.workflow;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.BaseWorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	property = "model.class.name=com.liferay.search.experiences.model.SXPBlueprint",
	service = WorkflowHandler.class
)
public class SXPBlueprintWorkflowHandler
	extends BaseWorkflowHandler<SXPBlueprint> {

	@Override
	public String getClassName() {
		return SXPBlueprint.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return ResourceActionsUtil.getModelResource(locale, getClassName());
	}

	@Override
	public SXPBlueprint updateStatus(
			int status, Map<String, Serializable> workflowContext)
		throws PortalException {

		return _sxpBlueprintLocalService.updateStatus(
			GetterUtil.getLong(
				(String)workflowContext.get(WorkflowConstants.CONTEXT_USER_ID)),
			GetterUtil.getLong(
				(String)workflowContext.get(
					WorkflowConstants.CONTEXT_ENTRY_CLASS_PK)),
			status, (ServiceContext)workflowContext.get("serviceContext"));
	}

	@Reference
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

}