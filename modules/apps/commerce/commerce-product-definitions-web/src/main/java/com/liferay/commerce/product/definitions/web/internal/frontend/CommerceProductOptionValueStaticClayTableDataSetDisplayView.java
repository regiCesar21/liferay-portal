/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.definitions.web.internal.frontend;

import com.liferay.commerce.product.definitions.web.internal.frontend.constants.CommerceProductDataSetConstants;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Beslic
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.set.display.name=" + CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_OPTION_VALUES_STATIC,
	service = ClayDataSetDisplayView.class
)
public class CommerceProductOptionValueStaticClayTableDataSetDisplayView
	extends BaseCommerceProductOptionValueClayTableDataSetDisplayView {

	@Override
	protected void addFields(ClayTableSchemaBuilder clayTableSchemaBuilder) {
		clayTableSchemaBuilder.addClayTableSchemaField("key", "key");
		clayTableSchemaBuilder.addClayTableSchemaField("position", "position");
		clayTableSchemaBuilder.addClayTableSchemaField(
			"deltaPrice", "delta-price");
		clayTableSchemaBuilder.addClayTableSchemaField("sku", "linked-product");
	}

}