/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.store.gcs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;

import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.store.gcs.configuration.GCSStoreConfiguration;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.ByteArrayInputStream;

import java.util.Collections;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Adam Brandizzi
 */
@PrepareForTest(ConfigurableUtil.class)
@RunWith(PowerMockRunner.class)
public class GCSStoreTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_gcsStoreConfiguration = PowerMockito.mock(GCSStoreConfiguration.class);

		PowerMockito.mockStatic(ConfigurableUtil.class);

		_gcsStore = new GCSStore();

		PowerMockito.when(
			ConfigurableUtil.createConfigurable(
				Mockito.eq(GCSStoreConfiguration.class), Mockito.any(Map.class))
		).thenReturn(
			_gcsStoreConfiguration
		);

		PowerMockito.when(
			_gcsStoreConfiguration.retryDelayMultiplier()
		).thenReturn(
			1.5
		);

		PowerMockito.when(
			_gcsStoreConfiguration.rpcTimeoutMultiplier()
		).thenReturn(
			1.0
		);
	}

	@Test
	public void testActivate1() throws Exception {
		byte[] serviceAccountKeyBytes = _getBytes(
			"dependencies/service-account-key.json");

		_mockServiceAccountKey(new String(serviceAccountKeyBytes));

		_gcsStore.activate(Collections.emptyMap());

		_assertGoogleCredentials(
			ServiceAccountCredentials.fromStream(
				new ByteArrayInputStream(serviceAccountKeyBytes)));
	}

	@Test
	public void testActivate2() throws Exception {
		_mockServiceAccountKey(null);

		_gcsStore.activate(Collections.emptyMap());

		_assertGoogleCredentials(
			ServiceAccountCredentials.getApplicationDefault());
	}

	private void _assertGoogleCredentials(GoogleCredentials googleCredentials) {
		Assert.assertEquals(
			ReflectionTestUtil.getFieldValue(_gcsStore, "_googleCredentials"),
			googleCredentials);
	}

	private byte[] _getBytes(String path) throws Exception {
		Class<? extends GCSStoreTest> clazz = getClass();

		return StreamUtil.toByteArray(clazz.getResourceAsStream(path));
	}

	private void _mockServiceAccountKey(String serviceAccountKey) {
		PowerMockito.when(
			_gcsStoreConfiguration.serviceAccountKey()
		).thenReturn(
			serviceAccountKey
		);
	}

	private GCSStore _gcsStore;
	private GCSStoreConfiguration _gcsStoreConfiguration;

}