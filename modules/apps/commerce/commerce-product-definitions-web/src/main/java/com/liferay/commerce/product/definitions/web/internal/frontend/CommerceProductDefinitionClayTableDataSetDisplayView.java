/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.definitions.web.internal.frontend;

import com.liferay.commerce.product.definitions.web.internal.frontend.constants.CommerceProductDataSetConstants;
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
	property = "clay.data.set.display.name=" + CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_DEFINITIONS,
	service = ClayDataSetDisplayView.class
)
public class CommerceProductDefinitionClayTableDataSetDisplayView
	extends BaseTableClayDataSetDisplayView {

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.create();

		ClayTableSchemaField imageclayTableSchemaField =
			clayTableSchemaBuilder.addClayTableSchemaField("thumbnail", "");

		imageclayTableSchemaField.setContentRenderer("image");

		ClayTableSchemaField nameField =
			clayTableSchemaBuilder.addClayTableSchemaField("name.LANG", "name");

		nameField.setSortable(true);
		nameField.setContentRenderer("actionLink");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"catalog.name", "catalog");
		clayTableSchemaBuilder.addClayTableSchemaField(
			"productTypeI18n", "type");

		ClayTableSchemaField clayTableSchemaField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"workflowStatusInfo", "status");

		clayTableSchemaField.setContentRenderer("status");

		ClayTableSchemaField dateclayTableSchemaField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"modifiedDate", "modified-date");

		dateclayTableSchemaField.setContentRenderer("date");
		dateclayTableSchemaField.setSortable(true);

		return clayTableSchemaBuilder.build();
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

}