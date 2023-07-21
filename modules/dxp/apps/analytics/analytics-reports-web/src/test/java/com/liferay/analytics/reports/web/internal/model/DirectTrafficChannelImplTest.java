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
public class DirectTrafficChannelImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testToJSONObject() {
		DirectTrafficChannelImpl directTrafficChannelImpl =
			new DirectTrafficChannelImpl(
				RandomTestUtil.randomInt(), RandomTestUtil.randomDouble());

		Assert.assertEquals(
			JSONUtil.put(
				"helpMessage", directTrafficChannelImpl.getHelpMessageKey()
			).put(
				"name", directTrafficChannelImpl.getName()
			).put(
				"share",
				String.format(
					"%.1f", directTrafficChannelImpl.getTrafficShare())
			).put(
				"title", directTrafficChannelImpl.getName()
			).put(
				"value",
				Math.toIntExact(directTrafficChannelImpl.getTrafficAmount())
			).toString(),
			String.valueOf(
				directTrafficChannelImpl.toJSONObject(
					LocaleUtil.US,
					_getResourceBundle(directTrafficChannelImpl))));
	}

	@Test
	public void testToJSONObjectWithError() {
		DirectTrafficChannelImpl directTrafficChannelImpl =
			new DirectTrafficChannelImpl(true);

		Assert.assertEquals(
			JSONUtil.put(
				"helpMessage", directTrafficChannelImpl.getHelpMessageKey()
			).put(
				"name", directTrafficChannelImpl.getName()
			).put(
				"title", directTrafficChannelImpl.getName()
			).toString(),
			String.valueOf(
				directTrafficChannelImpl.toJSONObject(
					LocaleUtil.US,
					_getResourceBundle(directTrafficChannelImpl))));
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