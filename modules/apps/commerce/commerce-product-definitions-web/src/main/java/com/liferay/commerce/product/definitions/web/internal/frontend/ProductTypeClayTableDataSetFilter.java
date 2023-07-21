/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.definitions.web.internal.frontend;

import com.liferay.commerce.product.definitions.web.internal.frontend.constants.CommerceProductDataSetConstants;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.CPTypeServicesTracker;
import com.liferay.frontend.taglib.clay.data.set.filter.BaseRadioClayDataSetFilter;
import com.liferay.frontend.taglib.clay.data.set.filter.ClayDataSetFilter;
import com.liferay.frontend.taglib.clay.data.set.filter.RadioClayDataSetFilterItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.set.display.name=" + CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_DEFINITIONS,
	service = ClayDataSetFilter.class
)
public class ProductTypeClayTableDataSetFilter
	extends BaseRadioClayDataSetFilter {

	@Override
	public String getId() {
		return "productType";
	}

	@Override
	public String getLabel() {
		return "product-type";
	}

	@Override
	public List<RadioClayDataSetFilterItem> getRadioClayDataSetFilterItems(
		Locale locale) {

		List<RadioClayDataSetFilterItem> radioClayDataSetFilterItems =
			new ArrayList<>();

		for (CPType cpType : _cpTypeServicesTracker.getCPTypes()) {
			radioClayDataSetFilterItems.add(
				new RadioClayDataSetFilterItem(
					cpType.getLabel(locale), cpType.getName()));
		}

		return radioClayDataSetFilterItems;
	}

	@Reference
	private CPTypeServicesTracker _cpTypeServicesTracker;

}