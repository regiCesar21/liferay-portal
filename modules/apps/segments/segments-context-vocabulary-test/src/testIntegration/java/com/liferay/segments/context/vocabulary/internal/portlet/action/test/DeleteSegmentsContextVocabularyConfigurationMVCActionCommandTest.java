/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.context.vocabulary.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Locale;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class DeleteSegmentsContextVocabularyConfigurationMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final TestRule testRule = new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_locale = LocaleThreadLocal.getThemeDisplayLocale();

		LocaleThreadLocal.setThemeDisplayLocale(LocaleUtil.US);
	}

	@AfterClass
	public static void tearDownClass() {
		LocaleThreadLocal.setThemeDisplayLocale(_locale);
	}

	@Test
	public void testProcessAction() throws Exception {
		Configuration configuration = _addConfiguration();

		try {
			MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
				new MockLiferayPortletActionRequest();

			mockLiferayPortletActionRequest.setParameter(
				"pid", configuration.getPid());

			_deleteMVCActionCommand.processAction(
				mockLiferayPortletActionRequest, null);

			configuration = _configurationAdmin.getConfiguration(
				configuration.getPid(), StringPool.QUESTION);

			Assert.assertNull(configuration.getProperties());
		}
		finally {
			configuration.delete();
		}
	}

	private Configuration _addConfiguration() throws Exception {
		Configuration configuration =
			_configurationAdmin.createFactoryConfiguration(
				"com.liferay.segments.context.vocabulary.internal." +
					"configuration.SegmentsContextVocabularyConfiguration",
				StringPool.QUESTION);

		Dictionary<String, Object> configuredProperties = new Hashtable<>();

		configuredProperties.put(
			"assetVocabulary", RandomTestUtil.randomString());
		configuredProperties.put("configuration.cleaner.ignore", "true");
		configuredProperties.put("entityField", RandomTestUtil.randomString());

		configuration.update(configuredProperties);

		return configuration;
	}

	private static Locale _locale;

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	@Inject(
		filter = "mvc.command.name=/delete_segments_context_vocabulary_configuration"
	)
	private MVCActionCommand _deleteMVCActionCommand;

}