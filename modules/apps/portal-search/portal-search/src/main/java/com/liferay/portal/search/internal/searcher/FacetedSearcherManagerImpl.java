/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.searcher;

import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcher;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcherManager;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.search.asset.SearchableAssetClassNamesProvider;
import com.liferay.portal.search.internal.expando.ExpandoQueryContributorHelper;
import com.liferay.portal.search.internal.indexer.AddSearchKeywordsQueryContributorHelper;
import com.liferay.portal.search.internal.indexer.PostProcessSearchQueryContributorHelper;
import com.liferay.portal.search.internal.indexer.PreFilterContributorHelper;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = FacetedSearcherManager.class)
public class FacetedSearcherManagerImpl implements FacetedSearcherManager {

	@Override
	public FacetedSearcher createFacetedSearcher() {
		return new FacetedSearcherImpl(
			addSearchKeywordsQueryContributorHelper,
			expandoQueryContributorHelper, indexerRegistry, indexSearcherHelper,
			postProcessSearchQueryContributorHelper, preFilterContributorHelper,
			searchableAssetClassNamesProvider, searchRequestBuilderFactory);
	}

	protected Localization getLocalization() {

		// See LPS-72507

		if (localization != null) {
			return localization;
		}

		return LocalizationUtil.getLocalization();
	}

	@Reference
	protected AddSearchKeywordsQueryContributorHelper
		addSearchKeywordsQueryContributorHelper;

	@Reference
	protected ExpandoQueryContributorHelper expandoQueryContributorHelper;

	@Reference
	protected IndexerRegistry indexerRegistry;

	@Reference
	protected IndexSearcherHelper indexSearcherHelper;

	protected Localization localization;

	@Reference
	protected PostProcessSearchQueryContributorHelper
		postProcessSearchQueryContributorHelper;

	@Reference
	protected PreFilterContributorHelper preFilterContributorHelper;

	@Reference
	protected SearchableAssetClassNamesProvider
		searchableAssetClassNamesProvider;

	@Reference
	protected SearchRequestBuilderFactory searchRequestBuilderFactory;

}