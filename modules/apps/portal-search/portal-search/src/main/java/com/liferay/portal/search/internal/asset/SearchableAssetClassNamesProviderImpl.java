/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.asset;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.asset.SearchableAssetClassNamesProvider;

import java.util.List;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(immediate = true, service = SearchableAssetClassNamesProvider.class)
public class SearchableAssetClassNamesProviderImpl
	implements SearchableAssetClassNamesProvider {

	@Override
	public String[] getClassNames(long companyId) {
		List<AssetRendererFactory<?>> assetRendererFactories =
			assetRendererFactoryRegistry.getAssetRendererFactories(companyId);

		Stream<AssetRendererFactory<?>> stream =
			assetRendererFactories.stream();

		String[] searchEngineHelperEntryClassNames =
			searchEngineHelper.getEntryClassNames();

		return stream.filter(
			AssetRendererFactory::isSearchable
		).map(
			AssetRendererFactory::getClassName
		).filter(
			className -> ArrayUtil.contains(
				searchEngineHelperEntryClassNames, className, false)
		).toArray(
			String[]::new
		);
	}

	@Reference
	protected AssetRendererFactoryRegistry assetRendererFactoryRegistry;

	@Reference
	protected SearchEngineHelper searchEngineHelper;

}