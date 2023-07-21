/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.frontend;

import com.liferay.commerce.order.content.web.internal.frontend.constants.CommerceOrderDataSetConstants;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.FilterFactory;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"clay.data.provider.key=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PENDING_ORDER_ITEMS,
		"clay.data.provider.key=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PENDING_ORDERS,
		"clay.data.provider.key=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PLACED_ORDER_ITEMS,
		"clay.data.provider.key=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PLACED_ORDERS
	},
	service = FilterFactory.class
)
public class OrderFilterFactoryImpl implements FilterFactory {

	@Override
	public Filter create(HttpServletRequest httpServletRequest) {
		OrderFilterImpl orderFilterImpl = new OrderFilterImpl();

		long commerceOrderId = ParamUtil.getLong(
			httpServletRequest, "commerceOrderId");

		long commerceAccountId = ParamUtil.getLong(
			httpServletRequest, "accountId");

		orderFilterImpl.setAccountId(commerceAccountId);

		orderFilterImpl.setCommerceOrderId(commerceOrderId);

		orderFilterImpl.setKeywords(
			ParamUtil.getString(httpServletRequest, "search"));

		return orderFilterImpl;
	}

}