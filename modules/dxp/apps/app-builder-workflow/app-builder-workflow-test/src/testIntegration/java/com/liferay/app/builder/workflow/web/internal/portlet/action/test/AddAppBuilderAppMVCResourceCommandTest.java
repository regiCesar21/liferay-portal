/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.workflow.web.internal.portlet.action.test;

import com.liferay.app.builder.rest.dto.v1_0.App;
import com.liferay.app.builder.workflow.rest.dto.v1_0.AppWorkflow;
import com.liferay.app.builder.workflow.web.internal.portlet.test.BaseAppBuilderPortletTestCase;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class AddAppBuilderAppMVCResourceCommandTest
	extends BaseAppBuilderPortletTestCase {

	@Ignore
	@Test
	public void testAddAppWorkflow() throws Exception {
		App app = addApp();

		Assert.assertNotNull(app);
		Assert.assertEquals("1.0", app.getVersion());

		AppWorkflow appWorkflow = getAppWorkflow(app);

		Assert.assertNotNull(appWorkflow);
		Assert.assertEquals("1.0", appWorkflow.getAppVersion());
	}

	@Test
	public void testAddAppWorkflowWithDuplicateTasks() throws Exception {
		AppWorkflow appWorkflow = createAppWorkflow();

		appWorkflow.setAppWorkflowTasks(
			ArrayUtil.append(
				appWorkflow.getAppWorkflowTasks(),
				appWorkflow.getAppWorkflowTasks()));

		Assert.assertNull(addApp(appWorkflow));
	}

}