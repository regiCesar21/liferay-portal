/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.web.internal.model;

import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
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
public class OrganicTrafficChannelImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testToJSONObject() {
		OrganicTrafficChannelImpl organicTrafficChannelImpl =
			new OrganicTrafficChannelImpl(
				RandomTestUtil.randomInt(), RandomTestUtil.randomDouble());

		Assert.assertEquals(
			JSONUtil.put(
				"helpMessage", organicTrafficChannelImpl.getHelpMessageKey()
			).put(
				"name", organicTrafficChannelImpl.getName()
			).put(
				"share",
				String.format(
					"%.1f", organicTrafficChannelImpl.getTrafficShare())
			).put(
				"title", organicTrafficChannelImpl.getName()
			).put(
				"value",
				Math.toIntExact(organicTrafficChannelImpl.getTrafficAmount())
			).toString(),
			String.valueOf(
				organicTrafficChannelImpl.toJSONObject(
					LocaleUtil.US,
					_getResourceBundle(organicTrafficChannelImpl))));
	}

	@Test
	public void testToJSONObjectWithError() {
		OrganicTrafficChannelImpl organicTrafficChannelImpl =
			new OrganicTrafficChannelImpl(true);

		Assert.assertEquals(
			JSONUtil.put(
				"helpMessage", organicTrafficChannelImpl.getHelpMessageKey()
			).put(
				"name", organicTrafficChannelImpl.getName()
			).put(
				"title", organicTrafficChannelImpl.getName()
			).toString(),
			String.valueOf(
				organicTrafficChannelImpl.toJSONObject(
					LocaleUtil.US,
					_getResourceBundle(organicTrafficChannelImpl))));
	}

	private ResourceBundle _getResourceBundle(TrafficChannel trafficChannel) {
		return new ResourceBundle() {

			@Override
			public Enumeration<String> getKeys() {
				return Collections.enumeration(
					Arrays.asList(
						trafficChannel.getName(),
						trafficChannel.getHelpMessageKey()));
			}

			@Override
			protected Object handleGetObject(String key) {
				return key;
			}

		};
	}

}