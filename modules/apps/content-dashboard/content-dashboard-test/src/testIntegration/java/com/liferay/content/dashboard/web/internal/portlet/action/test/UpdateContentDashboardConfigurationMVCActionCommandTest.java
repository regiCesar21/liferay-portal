/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Arques
 */
@RunWith(Arquillian.class)
@Sync
public class UpdateContentDashboardConfigurationMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testProcessAction() throws PortletException {
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		String[] assetVocabularyNames = {"vocabulary1", "vocabulary2"};

		mockLiferayPortletActionRequest.setParameter(
			"assetVocabularyNames", StringUtil.merge(assetVocabularyNames));

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		PortletPreferences portletPreferences =
			mockLiferayPortletActionRequest.getPreferences();

		Assert.assertArrayEquals(
			assetVocabularyNames,
			portletPreferences.getValues(
				"assetVocabularyNames", new String[0]));

		Assert.assertNull(
			SessionMessages.get(
				mockLiferayPortletActionRequest, "emptyAssetVocabularyNames"));
	}

	@Test
	public void testProcessActionWithEmptyAssetVocabularyNames()
		throws PortletException {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setParameter(
			"assetVocabularyNames", new String[0]);

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		PortletPreferences portletPreferences =
			mockLiferayPortletActionRequest.getPreferences();

		Assert.assertArrayEquals(
			new String[0],
			portletPreferences.getValues(
				"assetVocabularyNames", new String[0]));

		Object emptyAssetVocabularyNames = SessionMessages.get(
			mockLiferayPortletActionRequest, "emptyAssetVocabularyNames");

		Assert.assertNotNull(emptyAssetVocabularyNames);
		Assert.assertTrue((Boolean)emptyAssetVocabularyNames);
	}

	@Inject(
		filter = "mvc.command.name=/update_content_dashboard_configuration",
		type = MVCActionCommand.class
	)
	private MVCActionCommand _mvcActionCommand;

}