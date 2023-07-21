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
import com.liferay.petra.string.StringPool;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.set.display.name=" + CommercePricingDataSetConstants.COMMERCE_DATA_SET_KEY_PROMOTION_ENTRIES,
	service = ClayDataSetDisplayView.class
)
public class CommercePromotionEntriesClayTableDataSetDisplayView
	extends BaseTableClayDataSetDisplayView {

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.create();

		ClayTableSchemaField imageField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"product.thumbnail", StringPool.BLANK);

		imageField.setContentRenderer("image");

		ClayTableSchemaField skuField =
			clayTableSchemaBuilder.addClayTableSchemaField("sku.name", "sku");

		skuField.setContentRenderer("actionLink");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"product.name.LANG", "name");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"sku.basePromoPriceFormatted", "base-promotion-price");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"priceFormatted", "promotion-price");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"discountLevelsFormatted", "unit-discount");

		ClayTableSchemaField tieredPrice =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"hasTierPrice", "tiered-price");

		tieredPrice.setContentRenderer("boolean");

		return clayTableSchemaBuilder.build();
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

}