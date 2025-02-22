/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.publisher.rest.controller;

import com.liferay.osb.asah.common.model.AnalyticsEvent;
import com.liferay.osb.asah.common.model.AnalyticsEventsMessage;
import com.liferay.osb.asah.common.util.ProjectIdThreadLocal;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author Marcos Martins
 */
public class AnalyticsEventsRestControllerTest {

	@BeforeEach
	public void setUp() {
		ProjectIdThreadLocal.setProjectId("test");
	}

	@Test
	public void testCreateAnalyticsEvent() {
		AnalyticsEventsMessage analyticsEventsMessage =
			new AnalyticsEventsMessage();

		analyticsEventsMessage.setChannelId("1");
		analyticsEventsMessage.setContext(
			Collections.singletonMap("context", "test"));

		AnalyticsEventsMessage.Event event = new AnalyticsEventsMessage.Event();

		event.setApplicationId("Page");
		event.setEventDate(new Date());
		event.setEventId("pageViewed");
		event.setId("1");
		event.setProperties(Collections.singletonMap("property", "test"));

		analyticsEventsMessage.setEvents(Arrays.asList(event));

		analyticsEventsMessage.setEmailAddressHashed(
			DigestUtils.sha256Hex("test@liferay.com"));

		AnalyticsEvent analyticsEvent = ReflectionTestUtils.invokeMethod(
			_analyticsEventsRestController, "_createAnalyticsEvent",
			analyticsEventsMessage, "1", "1", event, "UTC",
			Collections.emptySet());

		Assertions.assertNotNull(analyticsEvent);

		Assertions.assertEquals("Page", analyticsEvent.getApplicationId());
		Assertions.assertEquals("1", analyticsEvent.getChannelId());
		Assertions.assertEquals(
			Collections.singletonMap("context", "test"),
			analyticsEvent.getContext());
		Assertions.assertEquals("1", analyticsEvent.getDataSourceId());
		Assertions.assertEquals(
			DigestUtils.sha256Hex("test@liferay.com"),
			analyticsEvent.getEmailAddressHashed());
		Assertions.assertEquals("pageViewed", analyticsEvent.getEventId());
		Assertions.assertEquals(
			Collections.singletonMap("property", "test"),
			analyticsEvent.getEventProperties());

		analyticsEvent = ReflectionTestUtils.invokeMethod(
			_analyticsEventsRestController, "_createAnalyticsEvent",
			analyticsEventsMessage, "1", "1", event, "UTC",
			Collections.singleton("test@liferay.com"));

		Assertions.assertNotNull(analyticsEvent);

		Assertions.assertNull(analyticsEvent.getEmailAddressHashed());
	}

	private final AnalyticsEventsRestController _analyticsEventsRestController =
		new AnalyticsEventsRestController();

}