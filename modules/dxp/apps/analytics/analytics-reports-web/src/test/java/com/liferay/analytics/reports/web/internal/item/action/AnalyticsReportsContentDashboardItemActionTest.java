/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.web.internal.item.action;

import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.Enumeration;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author David Arques
 */
public class AnalyticsReportsContentDashboardItemActionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCreation() {
		String label = RandomTestUtil.randomString();
		String url = RandomTestUtil.randomString();

		ResourceBundle resourceBundle = new ResourceBundle() {

			@Override
			public Enumeration<String> getKeys() {
				return Collections.enumeration(
					Collections.singletonList("view-metrics"));
			}

			@Override
			protected Object handleGetObject(String key) {
				return label;
			}

		};

		AnalyticsReportsContentDashboardItemAction
			analyticsReportsContentDashboardItemAction =
				new AnalyticsReportsContentDashboardItemAction(
					locale -> resourceBundle, url);

		Assert.assertEquals(
			label,
			analyticsReportsContentDashboardItemAction.getLabel(LocaleUtil.US));
		Assert.assertEquals(
			"viewMetrics",
			analyticsReportsContentDashboardItemAction.getName());
		Assert.assertEquals(
			url, analyticsReportsContentDashboardItemAction.getURL());
		Assert.assertEquals(
			ContentDashboardItemAction.Type.VIEW_IN_PANEL,
			analyticsReportsContentDashboardItemAction.getType());
	}

}