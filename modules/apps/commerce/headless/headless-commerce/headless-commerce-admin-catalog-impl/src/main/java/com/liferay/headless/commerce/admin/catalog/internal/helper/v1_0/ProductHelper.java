/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.helper.v1_0;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Product;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(enabled = false, immediate = true, service = ProductHelper.class)
public class ProductHelper {

	public Page<Product> getProductsPage(
			long companyId, String search, Filter filter, Pagination pagination,
			Sort[] sorts,
			UnsafeFunction<Document, Product, Exception>
				transformUnsafeFunction,
			Locale preferredLocale)
		throws Exception {

		return SearchUtil.search(
			null, booleanQuery -> booleanQuery.getPreBooleanFilter(), filter,
			CPDefinition.class, search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			new UnsafeConsumer() {

				public void accept(Object object) throws Exception {
					SearchContext searchContext = (SearchContext)object;

					searchContext.setCompanyId(companyId);

					long[] commerceCatalogGroupIds =
						_getCommerceCatalogGroupIds(companyId);

					if ((commerceCatalogGroupIds != null) &&
						(commerceCatalogGroupIds.length > 0)) {

						searchContext.setGroupIds(commerceCatalogGroupIds);
					}

					searchContext.setAttribute(
						Field.STATUS, WorkflowConstants.STATUS_ANY);

					if (preferredLocale != null) {
						searchContext.setLocale(preferredLocale);
					}
				}

			},
			sorts, transformUnsafeFunction);
	}

	private long[] _getCommerceCatalogGroupIds(long companyId)
		throws Exception {

		List<CommerceCatalog> commerceCatalogs =
			_commerceCatalogLocalService.searchCommerceCatalogs(companyId);

		Stream<CommerceCatalog> stream = commerceCatalogs.stream();

		return stream.mapToLong(
			CommerceCatalog::getGroupId
		).toArray();
	}

	@Reference
	private CommerceCatalogLocalService _commerceCatalogLocalService;

}