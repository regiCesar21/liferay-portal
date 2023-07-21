/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.web.internal.frontend;

import com.liferay.commerce.order.status.CommerceOrderStatus;
import com.liferay.commerce.order.status.CommerceOrderStatusRegistry;
import com.liferay.commerce.order.web.internal.frontend.constants.CommerceOrderDataSetConstants;
import com.liferay.frontend.taglib.clay.data.set.filter.BaseCheckBoxClayDataSetFilter;
import com.liferay.frontend.taglib.clay.data.set.filter.CheckBoxClayDataSetFilterItem;
import com.liferay.frontend.taglib.clay.data.set.filter.ClayDataSetFilter;

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
	property = "clay.data.set.display.name=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_ALL_ORDERS,
	service = ClayDataSetFilter.class
)
public class CommerceOrderStatusClayTableDataSetFilter
	extends BaseCheckBoxClayDataSetFilter {

	@Override
	public List<CheckBoxClayDataSetFilterItem>
		getCheckBoxClayDataSetFilterItems(Locale locale) {

		List<CheckBoxClayDataSetFilterItem> checkBoxClayDataSetFilterItems =
			new ArrayList<>();

		for (CommerceOrderStatus commerceOrderStatus :
				_commerceOrderStatusRegistry.getCommerceOrderStatuses()) {

			checkBoxClayDataSetFilterItems.add(
				new CheckBoxClayDataSetFilterItem(
					commerceOrderStatus.getLabel(locale),
					commerceOrderStatus.getKey()));
		}

		return checkBoxClayDataSetFilterItems;
	}

	@Override
	public String getId() {
		return "orderStatus";
	}

	@Override
	public String getLabel() {
		return "order-status";
	}

	@Reference
	private CommerceOrderStatusRegistry _commerceOrderStatusRegistry;

}