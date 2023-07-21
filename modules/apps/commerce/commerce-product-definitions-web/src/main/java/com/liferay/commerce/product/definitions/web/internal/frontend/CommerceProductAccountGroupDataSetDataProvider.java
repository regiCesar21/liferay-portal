/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.definitions.web.internal.frontend;

import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.model.CommerceAccountGroupRel;
import com.liferay.commerce.account.service.CommerceAccountGroupRelService;
import com.liferay.commerce.product.definitions.web.internal.frontend.constants.CommerceProductDataSetConstants;
import com.liferay.commerce.product.definitions.web.internal.model.AccountGroup;
import com.liferay.commerce.product.model.CPDefinition;
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
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_ACCOUNT_GROUPS,
	service = ClayDataSetDataProvider.class
)
public class CommerceProductAccountGroupDataSetDataProvider
	implements ClayDataSetDataProvider<AccountGroup> {

	@Override
	public List<AccountGroup> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<AccountGroup> accountGroups = new ArrayList<>();

		long cpDefinitionId = ParamUtil.getLong(
			httpServletRequest, "cpDefinitionId");

		List<CommerceAccountGroupRel> commerceAccountGroupRels =
			_commerceAccountGroupRelService.getCommerceAccountGroupRels(
				CPDefinition.class.getName(), cpDefinitionId,
				pagination.getStartPosition(), pagination.getEndPosition(),
				null);

		for (CommerceAccountGroupRel commerceAccountGroupRel :
				commerceAccountGroupRels) {

			CommerceAccountGroup commerceAccountGroup =
				commerceAccountGroupRel.getCommerceAccountGroup();

			accountGroups.add(
				new AccountGroup(
					commerceAccountGroupRel.getCommerceAccountGroupRelId(),
					commerceAccountGroup.getName()));
		}

		return accountGroups;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long cpDefinitionId = ParamUtil.getLong(
			httpServletRequest, "cpDefinitionId");

		return _commerceAccountGroupRelService.getCommerceAccountGroupRelsCount(
			CPDefinition.class.getName(), cpDefinitionId);
	}

	@Reference
	private CommerceAccountGroupRelService _commerceAccountGroupRelService;

}