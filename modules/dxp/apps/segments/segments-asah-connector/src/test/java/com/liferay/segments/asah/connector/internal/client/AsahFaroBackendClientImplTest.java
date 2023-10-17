/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.client;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.asah.connector.internal.client.model.Experiment;
import com.liferay.segments.asah.connector.internal.client.model.ExperimentSettings;

import javax.ws.rs.core.MultivaluedHashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Sarai Díaz
 */
@RunWith(MockitoJUnitRunner.class)
public class AsahFaroBackendClientImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_jsonWebServiceClient = Mockito.mock(JSONWebServiceClient.class);

		_asahFaroBackendClient = new AsahFaroBackendClientImpl(
			_jsonWebServiceClient);

		ReflectionTestUtil.setFieldValue(
			PrefsPropsUtil.class, "_prefsProps",
			Mockito.mock(PrefsProps.class));
	}

	@Test
	public void testCalculateExperimentEstimatedDaysDuration() {
		String days = "14";

		Mockito.when(
			_jsonWebServiceClient.doPost(
				Mockito.eq(String.class), Mockito.anyString(),
				Mockito.anyString(), Mockito.any(ExperimentSettings.class),
				Mockito.anyMapOf(String.class, String.class))
		).thenReturn(
			days
		);

		Assert.assertEquals(
			Long.valueOf(days),
			_asahFaroBackendClient.calculateExperimentEstimatedDaysDuration(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				new ExperimentSettings()));
	}

	@Test
	public void testCalculateExperimentEstimatedDaysDurationWithEmptyResult() {
		Mockito.when(
			_jsonWebServiceClient.doPost(
				Mockito.eq(String.class), Mockito.anyString(),
				Mockito.anyString(), Mockito.any(ExperimentSettings.class),
				Mockito.anyMapOf(String.class, String.class))
		).thenReturn(
			StringPool.BLANK
		);

		Assert.assertNull(
			_asahFaroBackendClient.calculateExperimentEstimatedDaysDuration(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				new ExperimentSettings()));
	}

	@Test
	public void testCalculateExperimentEstimatedDaysDurationWithInvalidResult() {
		Mockito.when(
			_jsonWebServiceClient.doPost(
				Mockito.eq(String.class), Mockito.anyString(),
				Mockito.anyString(), Mockito.any(ExperimentSettings.class),
				Mockito.anyMapOf(String.class, String.class))
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Assert.assertNull(
			_asahFaroBackendClient.calculateExperimentEstimatedDaysDuration(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				new ExperimentSettings()));
	}

	@Test
	public void testGetExperiment() throws Exception {
		String experimentId = RandomTestUtil.randomString();

		Mockito.when(
			_jsonWebServiceClient.doGet(
				Mockito.anyString(), Mockito.anyString(),
				Mockito.any(MultivaluedHashMap.class),
				Mockito.anyMapOf(String.class, String.class))
		).thenReturn(
			JSONUtil.put(
				"channelId", "637850400632477181"
			).put(
				"goal", JSONUtil.put("metric", "BOUNCE_RATE")
			).put(
				"status", "RUNNING"
			).toString()
		);

		Experiment experiment = _asahFaroBackendClient.getExperiment(
			RandomTestUtil.randomLong(), experimentId);

		Assert.assertEquals(
			"RUNNING", String.valueOf(experiment.getExperimentStatus()));
	}

	private AsahFaroBackendClient _asahFaroBackendClient;
	private JSONWebServiceClient _jsonWebServiceClient;

}