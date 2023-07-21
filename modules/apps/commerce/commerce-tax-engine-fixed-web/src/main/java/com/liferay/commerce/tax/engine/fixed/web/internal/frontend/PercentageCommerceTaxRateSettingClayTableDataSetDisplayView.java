/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.engine.fixed.web.internal.frontend;

import com.liferay.commerce.tax.engine.fixed.web.internal.frontend.constants.CommerceTaxRateSettingDataSetConstants;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.BaseTableClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchema;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilder;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilderFactory;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaField;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.set.display.name=" + CommerceTaxRateSettingDataSetConstants.COMMERCE_DATA_SET_KEY_PERCENTAGE_TAX_RATE_SETTING,
	service = ClayDataSetDisplayView.class
)
public class PercentageCommerceTaxRateSettingClayTableDataSetDisplayView
	extends BaseTableClayDataSetDisplayView {

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.create();

		ClayTableSchemaField taxRateField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"taxRate", "tax-rate");

		taxRateField.setContentRenderer("actionLink");

		clayTableSchemaBuilder.addClayTableSchemaField("country", "country");

		clayTableSchemaBuilder.addClayTableSchemaField("region", "region");

		clayTableSchemaBuilder.addClayTableSchemaField("zip", "zip");

		clayTableSchemaBuilder.addClayTableSchemaField("rate", "rate");

		return clayTableSchemaBuilder.build();
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

}