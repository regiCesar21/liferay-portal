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
	property = "clay.data.set.display.name=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_ALL_ORDERS,
	service = ClayDataSetDisplayView.class
)
public class AllCommerceOrderClayTableDataSetDisplayView
	extends BaseTableClayDataSetDisplayView {

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.create();

		ClayTableSchemaField orderIdField =
			clayTableSchemaBuilder.addClayTableSchemaField("id", "order-id");

		orderIdField.setContentRenderer("actionLink");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"account.name", "account");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"accountId", "account-number");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"channel.name", "channel");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"totalFormatted", "amount");

		ClayTableSchemaField dateClayTableSchemaField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"createDate", "create-date");

		dateClayTableSchemaField.setContentRenderer("date");
		dateClayTableSchemaField.setSortable(true);

		ClayTableSchemaField orderStatusField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"orderStatusInfo", "order-status");

		orderStatusField.setContentRenderer("status");

		ClayTableSchemaField fulfillmentWorkflowField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"workflowStatusInfo", "acceptance-workflow-status");

		fulfillmentWorkflowField.setContentRenderer("status");

		return clayTableSchemaBuilder.build();
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

}