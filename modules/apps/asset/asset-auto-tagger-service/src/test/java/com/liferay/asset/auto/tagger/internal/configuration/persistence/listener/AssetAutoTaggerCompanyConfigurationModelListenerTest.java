/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.auto.tagger.internal.configuration.persistence.listener;

import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfiguration;
import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfigurationFactory;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Dictionary;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Alicia Garcia
 */
@PrepareForTest(ResourceBundleUtil.class)
@RunWith(PowerMockRunner.class)
public class AssetAutoTaggerCompanyConfigurationModelListenerTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		_setUpAssetAutoTaggerCompanyConfigurationModelListener();
		_setUpResourceBundleUtil();
	}

	@Test(expected = ConfigurationModelListenerException.class)
	public void testMaximumNumberOfTagsPerAssetGreaterThanSystem()
		throws ConfigurationModelListenerException {

		_setUpAssetAutoTaggerConfigurationFactory();

		ReflectionTestUtil.setFieldValue(
			_assetAutoTaggerCompanyConfigurationModelListener,
			"_assetAutoTaggerConfigurationFactory",
			_assetAutoTaggerConfigurationFactory);

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("maximumNumberOfTagsPerAsset", 11);

		_assetAutoTaggerCompanyConfigurationModelListener.onBeforeSave(
			RandomTestUtil.randomString(), properties);
	}

	@Test(expected = ConfigurationModelListenerException.class)
	public void testMaximumNumberOfTagsPerAssetNegative()
		throws ConfigurationModelListenerException {

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("maximumNumberOfTagsPerAsset", -1);

		_assetAutoTaggerCompanyConfigurationModelListener.onBeforeSave(
			RandomTestUtil.randomString(), properties);
	}

	private void _setUpAssetAutoTaggerCompanyConfigurationModelListener() {
		_assetAutoTaggerCompanyConfigurationModelListener =
			new AssetAutoTaggerCompanyConfigurationModelListener();
	}

	private void _setUpAssetAutoTaggerConfigurationFactory() {
		AssetAutoTaggerConfiguration assetAutoTaggerConfiguration =
			new AssetAutoTaggerConfiguration() {

				@Override
				public int getMaximumNumberOfTagsPerAsset() {
					return 10;
				}

				@Override
				public boolean isAvailable() {
					return true;
				}

				@Override
				public boolean isEnabled() {
					return true;
				}

			};

		Mockito.doReturn(
			assetAutoTaggerConfiguration
		).when(
			_assetAutoTaggerConfigurationFactory
		).getSystemAssetAutoTaggerConfiguration();
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

	private AssetAutoTaggerCompanyConfigurationModelListener
		_assetAutoTaggerCompanyConfigurationModelListener;

	@Mock
	private AssetAutoTaggerConfigurationFactory
		_assetAutoTaggerConfigurationFactory;

}