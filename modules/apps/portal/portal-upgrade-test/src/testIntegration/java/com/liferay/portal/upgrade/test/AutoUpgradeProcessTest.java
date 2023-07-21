/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.util.PropsValues;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class AutoUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_originalUpgradeDatabaseAutoRun = ReflectionTestUtil.getFieldValue(
			PropsValues.class, "UPGRADE_DATABASE_AUTO_RUN");
	}

	@After
	public void tearDown() throws Exception {
		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "UPGRADE_DATABASE_AUTO_RUN",
			_originalUpgradeDatabaseAutoRun);

		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}

		Release release = _releaseLocalService.fetchRelease(
			_SERVLET_CONTEXT_NAME);

		if (release != null) {
			_releaseLocalService.deleteRelease(release);
		}
	}

	@Test
	public void testNewUpgradeProcessWhenAutoUpgradeDisabled()
		throws Exception {

		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "UPGRADE_DATABASE_AUTO_RUN", false);

		Assert.assertEquals(
			"1.0.0", _registerNewUpgradeProcess().getSchemaVersion());
	}

	@Test
	public void testNewUpgradeProcessWhenAutoUpgradeEnabled() throws Exception {
		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "UPGRADE_DATABASE_AUTO_RUN", true);

		Assert.assertEquals(
			"2.0.0", _registerNewUpgradeProcess().getSchemaVersion());
	}

	private Release _registerNewUpgradeProcess() throws Exception {
		_releaseLocalService.addRelease(_SERVLET_CONTEXT_NAME, "1.0.0");

		Bundle bundle = FrameworkUtil.getBundle(AutoUpgradeProcessTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			UpgradeStepRegistrator.class, new TestUpgradeStepRegistrator(),
			null);

		return _releaseLocalService.fetchRelease(_SERVLET_CONTEXT_NAME);
	}

	private static final String _SERVLET_CONTEXT_NAME =
		"com.liferay.portal.upgrade.test";

	private static boolean _originalUpgradeDatabaseAutoRun;

	@Inject
	private static ReleaseLocalService _releaseLocalService;

	private ServiceRegistration<UpgradeStepRegistrator> _serviceRegistration;

	private static class TestUpgradeStepRegistrator
		implements UpgradeStepRegistrator {

		@Override
		public void register(Registry registry) {
			registry.register("1.0.0", "2.0.0", new DummyUpgradeStep());
		}

	}

}