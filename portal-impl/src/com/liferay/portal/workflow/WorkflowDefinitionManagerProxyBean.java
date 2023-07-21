/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow;

import com.liferay.portal.kernel.messaging.proxy.BaseProxyBean;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;

import java.util.List;

/**
 * @author Micha Kiener
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 * @author Eduardo Lundgren
 */
@OSGiBeanProperties(
	property = "proxy.bean=true", service = WorkflowDefinitionManager.class
)
public class WorkflowDefinitionManagerProxyBean
	extends BaseProxyBean implements WorkflowDefinitionManager {

	@Override
	public List<WorkflowDefinition> getActiveWorkflowDefinitions(
		long companyId, int start, int end,
		OrderByComparator<WorkflowDefinition> orderByComparator) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<WorkflowDefinition> getActiveWorkflowDefinitions(
		long companyId, String name, int start, int end,
		OrderByComparator<WorkflowDefinition> orderByComparator) {

		throw new UnsupportedOperationException();
	}

	@Override
	public WorkflowDefinition getWorkflowDefinition(
		long companyId, String name, int version) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<WorkflowDefinition> getWorkflowDefinitions(
		long companyId, String name, int start, int end,
		OrderByComparator<WorkflowDefinition> orderByComparator) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void undeployWorkflowDefinition(
		long companyId, long userId, String name, int version) {

		throw new UnsupportedOperationException();
	}

	@Override
	public WorkflowDefinition updateActive(
		long companyId, long userId, String name, int version, boolean active) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void validateWorkflowDefinition(byte[] bytes) {
		throw new UnsupportedOperationException();
	}

}