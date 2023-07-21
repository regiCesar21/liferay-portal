/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.web.internal.searcher;

import com.liferay.content.dashboard.web.internal.item.ContentDashboardItemFactoryTracker;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchRequestBuilder;

import java.util.Collection;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(service = ContentDashboardSearchRequestBuilderFactory.class)
public class ContentDashboardSearchRequestBuilderFactory {

	public SearchRequestBuilder builder(SearchContext searchContext) {
		Collection<String> classNames =
			_contentDashboardItemFactoryTracker.getClassNames();

		return _searchRequestBuilderFactory.builder(
			searchContext
		).emptySearchEnabled(
			true
		).entryClassNames(
			classNames.toArray(new String[0])
		).fields(
			Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK, Field.UID
		).highlightEnabled(
			false
		);
	}

	@Reference
	private ContentDashboardItemFactoryTracker
		_contentDashboardItemFactoryTracker;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}