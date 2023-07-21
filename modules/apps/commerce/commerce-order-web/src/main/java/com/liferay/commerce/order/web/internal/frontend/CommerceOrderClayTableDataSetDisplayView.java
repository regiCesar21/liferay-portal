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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"clay.data.set.display.name=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_COMPLETED_ORDERS,
		"clay.data.set.display.name=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PENDING_ORDERS,
		"clay.data.set.display.name=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PROCESSING_ORDERS
	},
	service = ClayDataSetDisplayView.class
)
public class CommerceOrderClayTableDataSetDisplayView
	extends BaseTableClayDataSetDisplayView {

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.create();

		ClayTableSchemaField orderIdField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"orderId", "order-id");

		orderIdField.setContentRenderer("actionLink");

		clayTableSchemaBuilder.addClayTableSchemaField("account", "account");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"accountCode", "account-number");

		clayTableSchemaBuilder.addClayTableSchemaField("channel", "channel");

		clayTableSchemaBuilder.addClayTableSchemaField("amount", "amount");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"createDate", "order-date");

		ClayTableSchemaField orderStatusField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"orderStatus", "order-status");

		orderStatusField.setContentRenderer("label");

		ClayTableSchemaField fulfillmentWorkflowField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"fulfillmentWorkflow", "acceptance-workflow-status");

		fulfillmentWorkflowField.setContentRenderer("label");

		return clayTableSchemaBuilder.build();
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

}