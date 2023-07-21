/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.catalog.web.internal.frontend;

import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.model.CommerceAccountGroupRel;
import com.liferay.commerce.account.service.CommerceAccountGroupRelService;
import com.liferay.commerce.account.service.CommerceAccountGroupService;
import com.liferay.commerce.catalog.web.internal.frontend.constants.CommerceCatalogDataSetConstants;
import com.liferay.commerce.catalog.web.internal.model.AccountGroup;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ParamUtil;

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
	property = "clay.data.provider.key=" + CommerceCatalogDataSetConstants.COMMERCE_DATA_SET_KEY_CATALOG_ACCOUNT_GROUPS,
	service = ClayDataSetDataProvider.class
)
public class CommerceCatalogAccountGroupsDataSetDataProvider
	implements ClayDataSetDataProvider<AccountGroup> {

	@Override
	public List<AccountGroup> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<AccountGroup> accountGroups = new ArrayList<>();

		long commerceCatalogId = ParamUtil.getLong(
			httpServletRequest, "commerceCatalogId");

		List<CommerceAccountGroupRel> commerceAccountGroups =
			_commerceAccountGroupRelService.getCommerceAccountGroupRels(
				CommerceCatalog.class.getName(), commerceCatalogId,
				pagination.getStartPosition(), pagination.getEndPosition(),
				null);

		for (CommerceAccountGroupRel commerceAccountGroupRel :
				commerceAccountGroups) {

			CommerceAccountGroup commerceAccountGroup =
				_commerceAccountGroupService.getCommerceAccountGroup(
					commerceAccountGroupRel.getCommerceAccountGroupId());

			accountGroups.add(new AccountGroup(commerceAccountGroup.getName()));
		}

		return accountGroups;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long commerceCatalogId = ParamUtil.getLong(
			httpServletRequest, "commerceCatalogId");

		return _commerceAccountGroupRelService.getCommerceAccountGroupRelsCount(
			CommerceCatalog.class.getName(), commerceCatalogId);
	}

	@Reference
	private CommerceAccountGroupRelService _commerceAccountGroupRelService;

	@Reference
	private CommerceAccountGroupService _commerceAccountGroupService;

}