/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.workflow.web.internal.portlet.action.test;

import com.liferay.app.builder.rest.dto.v1_0.App;
import com.liferay.app.builder.workflow.web.internal.portlet.test.BaseAppBuilderPortletTestCase;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.messaging.proxy.ProxyMessageListener;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;

import org.apache.log4j.Level;

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
public class DeleteAppBuilderAppMVCResourceCommandTest
	extends BaseAppBuilderPortletTestCase {

	@Test
	public void testDeleteAppWorkflow() throws Exception {
		App app = addApp();

		Assert.assertNotNull(app);
		Assert.assertTrue(deleteApp(app));
	}

	@Test
	public void testDeleteAppWorkflowWithIncompleteInstance() throws Exception {
		App app = addApp();

		addDataRecord(app);

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					ProxyMessageListener.class.getName(), Level.OFF)) {

			Assert.assertFalse(deleteApp(app));
		}
	}

}