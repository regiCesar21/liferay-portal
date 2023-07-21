/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.model;

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Jeyvison Nascimento
 * @author Marcellus Tavares
 */
public class AnalyticsEventsMessageBuilderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCreateEvent() {
		String expectedApplicationId = randomString();
		String expectedEventId = randomString();

		Map<String, String> expectedProperties = HashMapBuilder.put(
			randomString(), randomString()
		).put(
			randomString(), randomString()
		).put(
			randomString(), randomString()
		).build();

		AnalyticsEventsMessage.Event actualEvent = createEvent(
			expectedApplicationId, expectedEventId, expectedProperties);

		assertEvent(
			expectedApplicationId, expectedEventId, expectedProperties,
			actualEvent);
	}

	@Test
	public void testCreateMessage() {

		// Context

		Map<String, String> expectedContext = createContext(
			randomLong(), randomString(), randomString(), randomLong());

		// Events

		List<AnalyticsEventsMessage.Event> expectedEvents = new ArrayList<>();

		String expectedApplicationId = randomString();
		String expectedEventId = randomString();

		expectedEvents.add(
			createEvent(
				expectedApplicationId, expectedEventId,
				HashMapBuilder.put(
					randomString(), randomString()
				).build()));

		// Message

		String expectedDataSourceId = randomString();
		String expectedUserId = randomString();
		String expectedProtocolVersion = randomString();

		AnalyticsEventsMessage actualAnalyticsEventsMessage =
			createAnalyticsEventsMessage(
				expectedDataSourceId, expectedUserId, expectedContext,
				expectedEvents, expectedProtocolVersion);

		Assert.assertEquals(
			expectedDataSourceId,
			actualAnalyticsEventsMessage.getDataSourceId());
		Assert.assertEquals(
			expectedUserId, actualAnalyticsEventsMessage.getUserId());
		Assert.assertEquals(
			expectedContext, actualAnalyticsEventsMessage.getContext());

		List<AnalyticsEventsMessage.Event> actualEvents =
			actualAnalyticsEventsMessage.getEvents();

		Assert.assertEquals(
			expectedEvents.toString(), expectedEvents.size(),
			actualEvents.size());

		int i = 0;

		for (AnalyticsEventsMessage.Event expectedEvent : expectedEvents) {
			assertEvent(
				expectedEvent.getApplicationId(), expectedEvent.getEventId(),
				expectedEvent.getProperties(), actualEvents.get(i++));
		}

		Assert.assertEquals(
			expectedProtocolVersion,
			actualAnalyticsEventsMessage.getProtocolVersion());
	}

	@Test(expected = IllegalStateException.class)
	public void testCreateMessageWithoutEvent() {
		createAnalyticsEventsMessage(
			randomString(), randomString(), new HashMap<>(), new ArrayList<>(),
			randomString());
	}

	protected void assertEvent(
		String expectedApplicationId, String expectedEventId,
		Map<String, String> expectedProperties,
		AnalyticsEventsMessage.Event actualEvent) {

		Assert.assertEquals(
			expectedApplicationId, actualEvent.getApplicationId());
		Assert.assertEquals(expectedEventId, actualEvent.getEventId());
		Assert.assertEquals(expectedProperties, actualEvent.getProperties());
	}

	protected AnalyticsEventsMessage createAnalyticsEventsMessage(
		String dataSourceId, String userId, Map<String, String> context,
		List<AnalyticsEventsMessage.Event> events, String protocolVersion) {

		AnalyticsEventsMessage.Builder messageBuilder =
			AnalyticsEventsMessage.builder(dataSourceId, userId);

		messageBuilder.context(context);

		for (AnalyticsEventsMessage.Event event : events) {
			messageBuilder.event(event);
		}

		messageBuilder.protocolVersion(protocolVersion);

		return messageBuilder.build();
	}

	protected Map<String, String> createContext(
		long instanceId, String languageId, String url, long userId) {

		return HashMapBuilder.put(
			"instanceId", String.valueOf(instanceId)
		).put(
			"languageId", languageId
		).put(
			"url", url
		).put(
			"userId", String.valueOf(userId)
		).build();
	}

	protected AnalyticsEventsMessage.Event createEvent(
		String applicationId, String eventId, Map<String, String> properties) {

		AnalyticsEventsMessage.Event.Builder eventBuilder =
			AnalyticsEventsMessage.Event.builder(applicationId, eventId);

		for (Map.Entry<String, String> entry : properties.entrySet()) {
			eventBuilder.property(entry.getKey(), entry.getValue());
		}

		return eventBuilder.build();
	}

	protected long randomLong() {
		return RandomUtils.nextLong();
	}

	protected String randomString() {
		return RandomStringUtils.random(5);
	}

}