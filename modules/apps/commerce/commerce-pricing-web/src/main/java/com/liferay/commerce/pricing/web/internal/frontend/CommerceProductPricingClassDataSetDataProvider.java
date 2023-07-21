/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.web.internal.frontend;

import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.service.CommercePricingClassCPDefinitionRelService;
import com.liferay.commerce.pricing.service.CommercePricingClassService;
import com.liferay.commerce.pricing.web.internal.frontend.constants.CommercePricingDataSetConstants;
import com.liferay.commerce.pricing.web.internal.model.ProductPricingClass;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommercePricingDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_PRICING_CLASSES,
	service = ClayDataSetDataProvider.class
)
public class CommerceProductPricingClassDataSetDataProvider
	implements ClayDataSetDataProvider<ProductPricingClass> {

	@Override
	public List<ProductPricingClass> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<ProductPricingClass> productPricingClasses = new ArrayList<>();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long cpDefinitionId = ParamUtil.getLong(
			httpServletRequest, "cpDefinitionId");

		List<CommercePricingClass> commercePricingClasses =
			_commercePricingClassService.
				searchCommercePricingClassesByCPDefinitionId(
					cpDefinitionId, filter.getKeywords(),
					pagination.getStartPosition(), pagination.getEndPosition());

		for (CommercePricingClass commercePricingClass :
				commercePricingClasses) {

			productPricingClasses.add(
				new ProductPricingClass(
					cpDefinitionId,
					commercePricingClass.getCommercePricingClassId(),
					commercePricingClass.getTitle(themeDisplay.getLocale()),
					_commercePricingClassCPDefinitionRelService.
						getCommercePricingClassCPDefinitionRelsCount(
							commercePricingClass.getCommercePricingClassId())));
		}

		return productPricingClasses;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long cpDefinitionId = ParamUtil.getLong(
			httpServletRequest, "cpDefinitionId");

		return _commercePricingClassService.getCommercePricingClassesCount(
			cpDefinitionId, filter.getKeywords());
	}

	@Reference
	private CommercePricingClassCPDefinitionRelService
		_commercePricingClassCPDefinitionRelService;

	@Reference
	private CommercePricingClassService _commercePricingClassService;

}