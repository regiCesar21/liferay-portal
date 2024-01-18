/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.data.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderContext;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcellus Tavares
 */
@RunWith(Arquillian.class)
public class DDMRESTDataProviderTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		DDMDataProvider[] ddmDataProviders = registry.getServices(
			"com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider",
			"(ddm.data.provider.type=rest)");

		_ddmDataProvider = ddmDataProviders[0];
	}

	@Test
	public void testGetData() throws Exception {
		List<KeyValuePair> keyValuePairs = _ddmDataProvider.getData(
			new DDMDataProviderContext(
				_createDDMDataProviderDDMFormValues(
					false, "name", _GET_COUNTRIES_URL, "nameCurrentValue")));

		Assert.assertTrue(
			keyValuePairs.containsAll(
				ListUtil.fromArray(
					new KeyValuePair("france", "France"),
					new KeyValuePair("spain", "Spain"),
					new KeyValuePair("united-states", "United States"),
					new KeyValuePair("brazil", "Brazil"))));
	}

	@Test
	public void testGetDataWithCache() throws Exception {
		_ddmDataProvider.getData(
			new DDMDataProviderContext(
				_createDDMDataProviderDDMFormValues(
					true, "name", _GET_COUNTRIES_URL, "nameCurrentValue")));

		Class<?> clazz = _ddmDataProvider.getClass();

		PortalCache<String, List<KeyValuePair>> portalCache =
			PortalCacheHelperUtil.getPortalCache(
				PortalCacheManagerNames.MULTI_VM, clazz.getName());

		Assert.assertNotNull(portalCache.get(_GET_COUNTRIES_URL));

		portalCache.remove(_GET_COUNTRIES_URL);
	}

	@Test
	public void testGetDataWithWebServiceError() throws Exception {
		List<KeyValuePair> keyValuePairs = _ddmDataProvider.getData(
			new DDMDataProviderContext(
				_createDDMDataProviderDDMFormValues(
					false, "", "http://localhost", "nameCurrentValue;name")));

		Assert.assertEquals(keyValuePairs.toString(), 0, keyValuePairs.size());
	}

	private DDMFormValues _createDDMDataProviderDDMFormValues(
		boolean cacheable, String key, String url, String value) {

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			DDMFormFactory.create(_ddmDataProvider.getSettings()));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"cacheable", String.valueOf(cacheable)));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"key", key));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"password", "test"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"url", url));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"username", "test@liferay.com"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"value", value));

		return ddmFormValues;
	}

	private static final String _GET_COUNTRIES_URL =
		"http://localhost:8080/api/jsonws/country/get-countries";

	private DDMDataProvider _ddmDataProvider;

}