/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.workflow.service.impl;

import com.liferay.app.builder.workflow.exception.DuplicateAppBuilderWorkflowTaskLinkException;
import com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink;
import com.liferay.app.builder.workflow.service.base.AppBuilderWorkflowTaskLinkLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink",
	service = AopService.class
)
public class AppBuilderWorkflowTaskLinkLocalServiceImpl
	extends AppBuilderWorkflowTaskLinkLocalServiceBaseImpl {

	@Override
	public AppBuilderWorkflowTaskLink addAppBuilderWorkflowTaskLink(
			long companyId, long appBuilderAppId, long appBuilderAppVersionId,
			long ddmStructureLayoutId, boolean readOnly,
			String workflowTaskName)
		throws PortalException {

		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink =
			appBuilderWorkflowTaskLinkPersistence.fetchByA_A_D_W(
				appBuilderAppId, appBuilderAppVersionId, ddmStructureLayoutId,
				workflowTaskName);

		if (Objects.nonNull(appBuilderWorkflowTaskLink)) {
			throw new DuplicateAppBuilderWorkflowTaskLinkException();
		}

		appBuilderWorkflowTaskLink =
			appBuilderWorkflowTaskLinkPersistence.create(
				counterLocalService.increment());

		appBuilderWorkflowTaskLink.setCompanyId(companyId);
		appBuilderWorkflowTaskLink.setAppBuilderAppId(appBuilderAppId);
		appBuilderWorkflowTaskLink.setAppBuilderAppVersionId(
			appBuilderAppVersionId);
		appBuilderWorkflowTaskLink.setDdmStructureLayoutId(
			ddmStructureLayoutId);
		appBuilderWorkflowTaskLink.setReadOnly(readOnly);
		appBuilderWorkflowTaskLink.setWorkflowTaskName(workflowTaskName);

		return appBuilderWorkflowTaskLinkPersistence.update(
			appBuilderWorkflowTaskLink);
	}

	@Override
	public void deleteAppBuilderWorkflowTaskLinks(long appBuilderAppId) {
		appBuilderWorkflowTaskLinkPersistence.removeByAppBuilderAppId(
			appBuilderAppId);
	}

	@Override
	public void deleteAppBuilderWorkflowTaskLinks(
		long appBuilderAppId, long appBuilderAppVersionId) {

		appBuilderWorkflowTaskLinkPersistence.removeByA_A(
			appBuilderAppId, appBuilderAppVersionId);
	}

	@Override
	public List<AppBuilderWorkflowTaskLink> getAppBuilderWorkflowTaskLinks(
		long appBuilderAppId) {

		return appBuilderWorkflowTaskLinkPersistence.findByAppBuilderAppId(
			appBuilderAppId);
	}

	@Override
	public List<AppBuilderWorkflowTaskLink> getAppBuilderWorkflowTaskLinks(
		long appBuilderAppId, long appBuilderAppVersionId) {

		return appBuilderWorkflowTaskLinkPersistence.findByA_A(
			appBuilderAppId, appBuilderAppVersionId);
	}

	@Override
	public List<AppBuilderWorkflowTaskLink> getAppBuilderWorkflowTaskLinks(
		long appBuilderAppId, long appBuilderAppVersionId,
		String workflowTaskName) {

		return appBuilderWorkflowTaskLinkPersistence.findByA_A_W(
			appBuilderAppId, appBuilderAppVersionId, workflowTaskName);
	}

}