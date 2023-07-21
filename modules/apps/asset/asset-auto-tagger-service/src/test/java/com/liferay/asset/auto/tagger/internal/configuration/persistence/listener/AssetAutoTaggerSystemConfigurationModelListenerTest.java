/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.auto.tagger.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Dictionary;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Alicia Garcia
 */
@PrepareForTest(ResourceBundleUtil.class)
@RunWith(PowerMockRunner.class)
public class AssetAutoTaggerSystemConfigurationModelListenerTest {

	@Before
	public void setUp() {
		_setUpAssetAutoTaggerSystemConfigurationModelListener();
		_setUpResourceBundleUtil();
	}

	@Test(expected = ConfigurationModelListenerException.class)
	public void testMaximumNumberOfTagsPerAssetNegative()
		throws ConfigurationModelListenerException {

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("maximumNumberOfTagsPerAsset", -1);

		_assetAutoTaggerSystemConfigurationModelListener.onBeforeSave(
			RandomTestUtil.randomString(), properties);
	}

	private void _setUpAssetAutoTaggerSystemConfigurationModelListener() {
		_assetAutoTaggerSystemConfigurationModelListener =
			new AssetAutoTaggerSystemConfigurationModelListener();
	}

	private void _setUpResourceBundleUtil() {
		PowerMockito.mockStatic(ResourceBundleUtil.class);

		PowerMockito.when(
			ResourceBundleUtil.getBundle(
				Matchers.anyString(), Matchers.any(Locale.class),
				Matchers.any(ClassLoader.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);
	}

	private AssetAutoTaggerSystemConfigurationModelListener
		_assetAutoTaggerSystemConfigurationModelListener;

}