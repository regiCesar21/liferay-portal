/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.constants;

import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommerceRegionService;
import com.liferay.petra.string.StringBundler;

/**
 * @author Luca Pellizzon
 */
public class CommerceSAPConstants {

	public static final String CLASS_NAME_COMMERCE_CART_RESOURCE =
		"com.liferay.commerce.frontend.internal.cart.CommerceCartResource";

	public static final String CLASS_NAME_COMMERCE_HEADLESS_CART_ITEM_RESOURCE =
		"com.liferay.headless.commerce.delivery.cart.internal.resource.v1_0." +
			"CartItemResourceImpl";

	public static final String CLASS_NAME_COMMERCE_HEADLESS_CART_RESOURCE =
		"com.liferay.headless.commerce.delivery.cart.internal.resource.v1_0." +
			"CartResourceImpl";

	public static final String CLASS_NAME_COMMERCE_SEARCH_RESOURCE =
		"com.liferay.commerce.frontend.internal.search.CommerceSearchResource";

	public static final String SAP_ENTRY_NAME = "COMMERCE_DEFAULT";

	public static final String[][] SAP_ENTRY_OBJECT_ARRAYS = {
		{
			SAP_ENTRY_NAME,
			StringBundler.concat(
				CommerceAccountService.class.getName(), "#getCommerceAccount\n",
				CommerceCountryService.class.getName(),
				"#getBillingCommerceCountriesByChannelId\n",
				CommerceCountryService.class.getName(),
				"#getCommerceCountries\n",
				CommerceCountryService.class.getName(),
				"#getShippingCommerceCountriesByChannelId\n",
				CommerceOrderItemService.class.getName(),
				"#getCommerceOrderItem\n",
				CommerceOrderItemService.class.getName(),
				"#getCommerceOrderItems\n",
				CommerceOrderItemService.class.getName(),
				"#getCommerceOrderItemsQuantity\n",
				CommerceOrderItemService.class.getName(),
				"#upsertCommerceOrderItem\n",
				CommerceOrderService.class.getName(), "#addCommerceOrder\n",
				CommerceOrderService.class.getName(), "#fetchCommerceOrder\n",
				CommerceOrderService.class.getName(), "#getCommerceOrder\n",
				CommerceRegionService.class.getName(), "#getCommerceRegions\n",
				CLASS_NAME_COMMERCE_CART_RESOURCE, "*\n",
				CLASS_NAME_COMMERCE_HEADLESS_CART_ITEM_RESOURCE,
				"#deleteCartItem\n",
				CLASS_NAME_COMMERCE_HEADLESS_CART_ITEM_RESOURCE,
				"#getCartItem\n",
				CLASS_NAME_COMMERCE_HEADLESS_CART_ITEM_RESOURCE,
				"#patchCartItem\n",
				CLASS_NAME_COMMERCE_HEADLESS_CART_ITEM_RESOURCE,
				"#postCartItem\n", CLASS_NAME_COMMERCE_HEADLESS_CART_RESOURCE,
				"#getCart\n", CLASS_NAME_COMMERCE_HEADLESS_CART_RESOURCE,
				"#getChannelCartsPage\n",
				CLASS_NAME_COMMERCE_HEADLESS_CART_RESOURCE, "#patchCart\n",
				CLASS_NAME_COMMERCE_HEADLESS_CART_RESOURCE,
				"#postChannelCart\n", CLASS_NAME_COMMERCE_SEARCH_RESOURCE)
		}
	};

}