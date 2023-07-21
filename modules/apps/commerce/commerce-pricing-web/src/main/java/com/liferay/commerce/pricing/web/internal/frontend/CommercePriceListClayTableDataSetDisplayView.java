/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.web.internal.frontend;

import com.liferay.commerce.pricing.web.internal.frontend.constants.CommercePricingDataSetConstants;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.BaseTableClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchema;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilder;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilderFactory;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaField;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.set.display.name=" + CommercePricingDataSetConstants.COMMERCE_DATA_SET_KEY_PRICE_LISTS,
	service = ClayDataSetDisplayView.class
)
public class CommercePriceListClayTableDataSetDisplayView
	extends BaseTableClayDataSetDisplayView {

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.create();

		ClayTableSchemaField nameField =
			clayTableSchemaBuilder.addClayTableSchemaField("name", "name");

		nameField.setContentRenderer("actionLink");
		nameField.setSortable(true);

		clayTableSchemaBuilder.addClayTableSchemaField("author", "author");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"catalogName", "catalog");

		clayTableSchemaBuilder.addClayTableSchemaField("priority", "priority");

		ClayTableSchemaField dateclayTableSchemaField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"createDate", "create-date");

		dateclayTableSchemaField.setContentRenderer("date");
		dateclayTableSchemaField.setSortable(true);

		ClayTableSchemaField clayTableSchemaField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"workflowStatusInfo", "status");

		clayTableSchemaField.setContentRenderer("status");

		return clayTableSchemaBuilder.build();
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

}