/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.web.internal.frontend;

import com.liferay.commerce.inventory.web.internal.frontend.constants.CommerceInventoryDataSetConstants;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.BaseTableClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchema;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilder;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilderFactory;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaField;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.set.display.name=" + CommerceInventoryDataSetConstants.COMMERCE_DATA_SET_KEY_INVENTORY_REPLENISHMENT,
	service = ClayDataSetDisplayView.class
)
public class CommerceInventoryReplenishmentClayTableDataSetDisplayView
	extends BaseTableClayDataSetDisplayView {

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.create();

		ClayTableSchemaField warehouseField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"warehouse", "destination");

		warehouseField.setContentRenderer("actionLink");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"date", "expected-delivery-date");

		clayTableSchemaBuilder.addClayTableSchemaField("quantity", "quantity");

		return clayTableSchemaBuilder.build();
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

}