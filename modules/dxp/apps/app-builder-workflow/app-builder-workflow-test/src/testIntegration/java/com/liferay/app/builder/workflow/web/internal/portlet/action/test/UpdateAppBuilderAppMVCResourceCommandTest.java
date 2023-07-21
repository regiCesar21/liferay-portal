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

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@Ignore
@RunWith(Arquillian.class)
public class UpdateAppBuilderAppMVCResourceCommandTest
	extends BaseAppBuilderPortletTestCase {

	@Test
	public void testUpdateAppWorkflow() throws Exception {
		App app = addApp();

		app = updateApp(app, getAppWorkflow(app));

		Assert.assertEquals("1.0", app.getVersion());

		AppWorkflow appWorkflow = getAppWorkflow(app);

		Assert.assertNotNull(appWorkflow);
		Assert.assertEquals("1.0", appWorkflow.getAppVersion());

		app = updateApp(app, createAppWorkflow());

		Assert.assertEquals("2.0", app.getVersion());

		appWorkflow = getAppWorkflow(app);

		Assert.assertEquals("2.0", appWorkflow.getAppVersion());
	}

}