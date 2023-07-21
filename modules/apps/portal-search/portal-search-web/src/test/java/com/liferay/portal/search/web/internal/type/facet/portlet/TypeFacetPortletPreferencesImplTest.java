/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.type.facet.portlet;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.internal.asset.AssetRendererFactoryRegistry;
import com.liferay.portal.search.internal.asset.SearchableAssetClassNamesProviderImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Adam Brandizzi
 */
public class TypeFacetPortletPreferencesImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		typeFacetPortletPreferencesImpl = new TypeFacetPortletPreferencesImpl(
			Optional.empty(),
			new SearchableAssetClassNamesProviderImpl() {
				{
					assetRendererFactoryRegistry =
						_assetRendererFactoryRegistry;
					searchEngineHelper = _searchEngineHelper;
				}
			});

		mockAssetRendererFactoryGetClassName(
			assetRendererFactory1, CLASS_NAME_1);

		mockAssetRendererFactoryGetClassName(
			assetRendererFactory2, CLASS_NAME_2);

		mockAssetRendererFactoryIsSearchable(assetRendererFactory1, true);

		mockAssetRendererFactoryIsSearchable(assetRendererFactory2, true);
	}

	@Test
	public void testGetAssetTypes() {
		mockAssetRendererFactoryRegistry(
			assetRendererFactory1, assetRendererFactory2);

		String[] entryClassNames = {CLASS_NAME_1, CLASS_NAME_2};

		mockSearchEngineHelperEntryClassNames(entryClassNames);

		Assert.assertArrayEquals(
			entryClassNames,
			typeFacetPortletPreferencesImpl.getAllAssetTypes(
				RandomTestUtil.randomLong()));
	}

	@Test
	public void testGetAssetTypesNotInRegistry() {
		mockAssetRendererFactoryRegistry(assetRendererFactory2);

		String[] entryClassNames = {CLASS_NAME_1, CLASS_NAME_2};

		mockSearchEngineHelperEntryClassNames(entryClassNames);

		Assert.assertArrayEquals(
			new String[] {CLASS_NAME_2},
			typeFacetPortletPreferencesImpl.getAllAssetTypes(
				RandomTestUtil.randomLong()));
	}

	@Test
	public void testGetAssetTypesNotInSearchEngineHelper() {
		mockAssetRendererFactoryRegistry(
			assetRendererFactory1, assetRendererFactory2);

		String[] entryClassNames = {CLASS_NAME_1};

		mockSearchEngineHelperEntryClassNames(entryClassNames);

		Assert.assertArrayEquals(
			entryClassNames,
			typeFacetPortletPreferencesImpl.getAllAssetTypes(
				RandomTestUtil.randomLong()));
	}

	@Test
	public void testGetAssetTypesNotSearchable() {
		mockAssetRendererFactoryIsSearchable(assetRendererFactory1, false);

		mockAssetRendererFactoryRegistry(
			assetRendererFactory1, assetRendererFactory2);

		String[] entryClassNames = {CLASS_NAME_1, CLASS_NAME_2};

		mockSearchEngineHelperEntryClassNames(entryClassNames);

		Assert.assertArrayEquals(
			new String[] {CLASS_NAME_2},
			typeFacetPortletPreferencesImpl.getAllAssetTypes(
				RandomTestUtil.randomLong()));
	}

	protected void mockAssetRendererFactoryGetClassName(
		AssetRendererFactory<?> assetRendererFactory, String className) {

		Mockito.when(
			assetRendererFactory.getClassName()
		).thenReturn(
			className
		);
	}

	protected void mockAssetRendererFactoryIsSearchable(
		AssetRendererFactory<?> assetRendererFactory, boolean searchable) {

		Mockito.when(
			assetRendererFactory.isSearchable()
		).thenReturn(
			searchable
		);
	}

	protected void mockAssetRendererFactoryRegistry(
		AssetRendererFactory<?>... assetRendererFactories) {

		Mockito.when(
			_assetRendererFactoryRegistry.getAssetRendererFactories(
				Matchers.anyLong())
		).thenReturn(
			Arrays.asList(assetRendererFactories)
		);
	}

	protected void mockSearchEngineHelperEntryClassNames(
		String[] entryClassNames) {

		Mockito.when(
			_searchEngineHelper.getEntryClassNames()
		).thenReturn(
			entryClassNames
		);
	}

	protected static final String CLASS_NAME_1 = "com.liferay.model.Model1";

	protected static final String CLASS_NAME_2 = "com.liferay.model.Model2";

	@Mock
	protected AssetRendererFactory<?> assetRendererFactory1;

	@Mock
	protected AssetRendererFactory<?> assetRendererFactory2;

	protected TypeFacetPortletPreferencesImpl typeFacetPortletPreferencesImpl;

	@Mock
	private AssetRendererFactoryRegistry _assetRendererFactoryRegistry;

	@Mock
	private SearchEngineHelper _searchEngineHelper;

}