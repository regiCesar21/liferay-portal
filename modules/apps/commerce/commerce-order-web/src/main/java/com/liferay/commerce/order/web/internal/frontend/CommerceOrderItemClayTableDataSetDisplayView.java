/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.web.internal.frontend;

import com.liferay.commerce.order.web.internal.frontend.constants.CommerceOrderDataSetConstants;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.BaseTableClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchema;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilder;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilderFactory;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaField;
import com.liferay.petra.string.StringPool;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.set.display.name=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_ORDER_ITEMS,
	service = ClayDataSetDisplayView.class
)
public class CommerceOrderItemClayTableDataSetDisplayView
	extends BaseTableClayDataSetDisplayView {

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.create();

		ClayTableSchemaField imageField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"image", StringPool.BLANK);

		imageField.setContentRenderer("image");

		ClayTableSchemaField skuField =
			clayTableSchemaBuilder.addClayTableSchemaField("sku", "sku");

		skuField.setContentRenderer("actionLink");

		clayTableSchemaBuilder.addClayTableSchemaField("name", "name");

		clayTableSchemaBuilder.addClayTableSchemaField("options", "options");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"requestedDeliveryDate", "delivery-date");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"deliveryGroup", "delivery-group");

		ClayTableSchemaField priceField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"price", "list-price");

		priceField.setContentRenderer("commerceTableCellSubscription");

		clayTableSchemaBuilder.addClayTableSchemaField("discount", "discount");

		clayTableSchemaBuilder.addClayTableSchemaField("quantity", "quantity");

		clayTableSchemaBuilder.addClayTableSchemaField("total", "total");

		return clayTableSchemaBuilder.build();
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

}