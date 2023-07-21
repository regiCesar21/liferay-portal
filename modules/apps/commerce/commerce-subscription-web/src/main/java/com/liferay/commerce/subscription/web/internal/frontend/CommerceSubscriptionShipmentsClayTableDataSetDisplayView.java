/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.subscription.web.internal.frontend;

import com.liferay.commerce.subscription.web.internal.frontend.constants.CommerceSubscriptionDataSetConstants;
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
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.set.display.name=" + CommerceSubscriptionDataSetConstants.COMMERCE_DATA_SET_KEY_SUBSCRIPTION_SHIPMENTS,
	service = ClayDataSetDisplayView.class
)
public class CommerceSubscriptionShipmentsClayTableDataSetDisplayView
	extends BaseTableClayDataSetDisplayView {

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.create();

		clayTableSchemaBuilder.addClayTableSchemaField(
			"createDate", "create-date");

		ClayTableSchemaField shipmentIdField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"shipmentId", "shipment-id");

		shipmentIdField.setContentRenderer("link");

		ClayTableSchemaField statusField =
			clayTableSchemaBuilder.addClayTableSchemaField("status", "status");

		statusField.setContentRenderer("label");

		ClayTableSchemaField orderIdField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"orderId", "order-id");

		orderIdField.setContentRenderer("link");

		clayTableSchemaBuilder.addClayTableSchemaField("receiver", "sent-to");

		ClayTableSchemaField trackingField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"tracking", "tracking");

		trackingField.setContentRenderer("link");

		return clayTableSchemaBuilder.build();
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

}