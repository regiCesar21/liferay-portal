/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.asset.SearchableAssetClassNamesProvider;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.search.experiences.rest.dto.v1_0.SearchableAssetNameDisplay;
import com.liferay.search.experiences.rest.resource.v1_0.SearchableAssetNameDisplayResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/searchable-asset-name-display.properties",
	scope = ServiceScope.PROTOTYPE,
	service = SearchableAssetNameDisplayResource.class
)
public class SearchableAssetNameDisplayResourceImpl
	extends BaseSearchableAssetNameDisplayResourceImpl {

	@Override
	public Page<SearchableAssetNameDisplay> getSearchableAssetNameLanguagePage(
			String languageId)
		throws Exception {

		return Page.of(
			transformToList(
				_searchableAssetClassNamesProvider.getClassNames(
					contextCompany.getCompanyId()),
				className1 -> new SearchableAssetNameDisplay() {
					{
						className = className1;
						displayName = _getDisplayName(className1, languageId);
					}
				}));
	}

	private String _getDisplayName(String className, String languageId) {
		return ResourceActionsUtil.getModelResource(
			LocaleUtil.fromLanguageId(languageId), className);
	}

	@Reference
	private SearchableAssetClassNamesProvider
		_searchableAssetClassNamesProvider;

}