/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipment.web.internal.frontend;

import com.liferay.commerce.constants.CommerceShipmentDataSetConstants;
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
 * @author Alec Sloan
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.set.display.name=" + CommerceShipmentDataSetConstants.COMMERCE_DATA_SET_KEY_SHIPMENTS,
	service = ClayDataSetDisplayView.class
)
public class CommerceShipmentClayTableDataSetDisplayView
	extends BaseTableClayDataSetDisplayView {

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.create();

		ClayTableSchemaField shipmentIdField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"shipmentId", "shipment-id");

		shipmentIdField.setContentRenderer("actionLink");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"accountName", "account");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"channelName", "channel");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"address", "shipping-address");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"tracking", "tracking-number");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"createDate", "create-date");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"expectedShipDate", "estimated-shipping-date");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"expectedDeliveryDate", "estimated-delivery-date");

		ClayTableSchemaField statusField =
			clayTableSchemaBuilder.addClayTableSchemaField("status", "status");

		statusField.setContentRenderer("label");

		return clayTableSchemaBuilder.build();
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

}