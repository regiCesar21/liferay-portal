/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.service.test;

import com.liferay.app.builder.deploy.AppDeployer;
import com.liferay.app.builder.deploy.AppDeployerTracker;
import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.model.AppBuilderAppVersion;
import com.liferay.app.builder.service.AppBuilderAppDeploymentLocalService;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.app.builder.service.AppBuilderAppVersionLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class AppBuilderAppLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddAppBuilderApp() throws Exception {
		AppBuilderApp appBuilderApp =
			_appBuilderAppLocalService.addAppBuilderApp(
				TestPropsValues.getGroupId(), TestPropsValues.getCompanyId(),
				TestPropsValues.getUserId(), true, RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString());

		AppBuilderAppVersion latestAppBuilderAppVersion =
			_appBuilderAppVersionLocalService.getLatestAppBuilderAppVersion(
				appBuilderApp.getAppBuilderAppId());

		Assert.assertNotNull(latestAppBuilderAppVersion);
		Assert.assertEquals("1.0", latestAppBuilderAppVersion.getVersion());
	}

	@Test
	public void testDeleteAppBuilderApp() throws Exception {
		AppBuilderApp appBuilderApp =
			_appBuilderAppLocalService.addAppBuilderApp(
				TestPropsValues.getGroupId(), TestPropsValues.getCompanyId(),
				TestPropsValues.getUserId(), true, RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString());

		_appBuilderAppDeploymentLocalService.addAppBuilderAppDeployment(
			appBuilderApp.getAppBuilderAppId(), null, "standalone");

		AppDeployer appDeployer = _appDeployerTracker.getAppDeployer(
			"standalone");

		appDeployer.deploy(appBuilderApp.getAppBuilderAppId());

		appBuilderApp = _appBuilderAppLocalService.fetchAppBuilderApp(
			appBuilderApp.getAppBuilderAppId());

		Assert.assertNotNull(appBuilderApp);

		_appBuilderAppLocalService.deleteAppBuilderApp(
			appBuilderApp.getAppBuilderAppId());

		appBuilderApp = _appBuilderAppLocalService.fetchAppBuilderApp(
			appBuilderApp.getAppBuilderAppId());

		Assert.assertNull(appBuilderApp);
	}

	@Test
	public void testUpdateAppBuilderApp() throws Exception {
		AppBuilderApp appBuilderApp =
			_appBuilderAppLocalService.addAppBuilderApp(
				TestPropsValues.getGroupId(), TestPropsValues.getCompanyId(),
				TestPropsValues.getUserId(), true, RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString());

		appBuilderApp = _appBuilderAppLocalService.updateAppBuilderApp(
			appBuilderApp.getUserId(), appBuilderApp.getAppBuilderAppId(),
			appBuilderApp.isActive(), appBuilderApp.getDdmStructureId(),
			RandomTestUtil.nextLong(), appBuilderApp.getDeDataListViewId(),
			appBuilderApp.getNameMap());

		AppBuilderAppVersion latestAppBuilderAppVersion =
			_appBuilderAppVersionLocalService.getLatestAppBuilderAppVersion(
				appBuilderApp.getAppBuilderAppId());

		Assert.assertEquals("2.0", latestAppBuilderAppVersion.getVersion());

		appBuilderApp = _appBuilderAppLocalService.updateAppBuilderApp(
			appBuilderApp.getUserId(), appBuilderApp.getAppBuilderAppId(),
			false, RandomTestUtil.nextLong(),
			appBuilderApp.getDdmStructureLayoutId(), RandomTestUtil.nextLong(),
			RandomTestUtil.randomLocaleStringMap());

		latestAppBuilderAppVersion =
			_appBuilderAppVersionLocalService.getLatestAppBuilderAppVersion(
				appBuilderApp.getAppBuilderAppId());

		Assert.assertEquals("2.0", latestAppBuilderAppVersion.getVersion());
	}

	@Inject
	private AppBuilderAppDeploymentLocalService
		_appBuilderAppDeploymentLocalService;

	@Inject
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	@Inject
	private AppBuilderAppVersionLocalService _appBuilderAppVersionLocalService;

	@Inject
	private AppDeployerTracker _appDeployerTracker;

}