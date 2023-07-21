/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.catalog.web.internal.frontend;

import com.liferay.commerce.catalog.web.internal.frontend.constants.CommerceCatalogDataSetConstants;
import com.liferay.commerce.catalog.web.internal.model.Catalog;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogService;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gianmarco Brunialti Masera
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommerceCatalogDataSetConstants.COMMERCE_DATA_SET_KEY_CATALOGS,
	service = ClayDataSetDataProvider.class
)
public class CommerceCatalogDataSetDataProvider
	implements ClayDataSetDataProvider<Catalog> {

	@Override
	public List<Catalog> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<Catalog> catalogs = new ArrayList<>();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<CommerceCatalog> commerceCatalogs =
			_commerceCatalogService.searchCommerceCatalogs(
				themeDisplay.getCompanyId(), filter.getKeywords(),
				pagination.getStartPosition(), pagination.getEndPosition(),
				new Sort(Field.NAME, false));

		for (CommerceCatalog catalog : commerceCatalogs) {
			catalogs.add(
				new Catalog(
					catalog.getCommerceCatalogId(),
					catalog.getCommerceCurrencyCode(),
					catalog.getCatalogDefaultLanguageId(), catalog.getName(),
					catalog.isSystem()));
		}

		return catalogs;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _commerceCatalogService.searchCommerceCatalogsCount(
			themeDisplay.getCompanyId(), filter.getKeywords());
	}

	@Reference
	private CommerceCatalogService _commerceCatalogService;

}