/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.workflow.web.internal.portlet.action;

import com.liferay.app.builder.constants.AppBuilderPortletKeys;
import com.liferay.app.builder.rest.dto.v1_0.App;
import com.liferay.app.builder.rest.resource.v1_0.AppResource;
import com.liferay.app.builder.workflow.rest.dto.v1_0.AppWorkflow;
import com.liferay.app.builder.workflow.rest.resource.v1_0.AppWorkflowResource;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Optional;

import javax.portlet.ResourceRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	property = {
		"javax.portlet.name=" + AppBuilderPortletKeys.APPS,
		"mvc.command.name=/app_builder/add_workflow_app"
	},
	service = MVCResourceCommand.class
)
public class AddAppBuilderAppMVCResourceCommand
	extends BaseAppBuilderAppMVCResourceCommand<App> {

	@Override
	protected Optional<App> doTransactionalCommand(
			ResourceRequest resourceRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		AppResource.Builder appResourceBuilder = _appResourceFactory.create();

		AppResource appResource = appResourceBuilder.user(
			themeDisplay.getUser()
		).build();

		App app = appResource.postDataDefinitionApp(
			ParamUtil.getLong(resourceRequest, "dataDefinitionId"),
			App.toDTO(ParamUtil.getString(resourceRequest, "app")));

		AppWorkflowResource.Builder appWorkflowResourceBuilder =
			_appWorkflowResourceFactory.create();

		AppWorkflowResource appWorkflowResource =
			appWorkflowResourceBuilder.user(
				themeDisplay.getUser()
			).build();

		appWorkflowResource.postAppWorkflow(
			app.getId(),
			AppWorkflow.toDTO(
				ParamUtil.getString(resourceRequest, "appWorkflow")));

		return Optional.of(app);
	}

	@Reference
	private AppResource.Factory _appResourceFactory;

	@Reference
	private AppWorkflowResource.Factory _appWorkflowResourceFactory;

}