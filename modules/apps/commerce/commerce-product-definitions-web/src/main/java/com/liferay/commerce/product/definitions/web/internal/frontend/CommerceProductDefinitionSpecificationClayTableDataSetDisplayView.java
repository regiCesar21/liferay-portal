/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.definitions.web.internal.frontend;

import com.liferay.commerce.product.definitions.web.internal.frontend.constants.CommerceProductDataSetConstants;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilder;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaField;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.set.display.name=" + CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_DEFINITION_SPECIFICATIONS,
	service = ClayDataSetDisplayView.class
)
public class CommerceProductDefinitionSpecificationClayTableDataSetDisplayView
	extends BaseClayTableDataSetDisplayView {

	@Override
	protected void addActionLinkFields(
		ClayTableSchemaBuilder clayTableSchemaBuilder) {

		ClayTableSchemaField labelField =
			clayTableSchemaBuilder.addClayTableSchemaField("label", "label");

		labelField.setContentRenderer("actionLink");
	}

	@Override
	protected void addFields(ClayTableSchemaBuilder clayTableSchemaBuilder) {
		clayTableSchemaBuilder.addClayTableSchemaField("value", "value");
		clayTableSchemaBuilder.addClayTableSchemaField("group", "group");
		clayTableSchemaBuilder.addClayTableSchemaField("order", "position");
	}

}