/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.definitions.web.internal.frontend;

import com.liferay.frontend.taglib.clay.data.set.view.table.BaseTableClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchema;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilder;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilderFactory;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaField;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Beslic
 */
public abstract class BaseClayTableDataSetDisplayView
	extends BaseTableClayDataSetDisplayView {

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			clayTableSchemaBuilderFactory.create();

		addActionLinkFields(clayTableSchemaBuilder);

		addFields(clayTableSchemaBuilder);

		return clayTableSchemaBuilder.build();
	}

	protected void addActionLinkFields(
		ClayTableSchemaBuilder clayTableSchemaBuilder) {

		ClayTableSchemaField nameField =
			clayTableSchemaBuilder.addClayTableSchemaField("name", "name");

		nameField.setContentRenderer("actionLink");
	}

	protected abstract void addFields(
		ClayTableSchemaBuilder clayTableSchemaBuilder);

	@Reference
	protected ClayTableSchemaBuilderFactory clayTableSchemaBuilderFactory;

}