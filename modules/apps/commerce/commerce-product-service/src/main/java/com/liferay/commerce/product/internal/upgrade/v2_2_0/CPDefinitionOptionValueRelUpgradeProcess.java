/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.upgrade.v2_2_0;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.internal.upgrade.base.BaseCommerceProductServiceUpgradeProcess;
import com.liferay.commerce.product.internal.upgrade.v2_2_0.util.CPDefinitionOptionRelTable;
import com.liferay.commerce.product.internal.upgrade.v2_2_0.util.CPDefinitionOptionValueRelTable;

/**
 * @author Marco Leo
 */
public class CPDefinitionOptionValueRelUpgradeProcess
	extends BaseCommerceProductServiceUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		addColumn(
			CPDefinitionOptionValueRelTable.class,
			CPDefinitionOptionValueRelTable.TABLE_NAME, "CPInstanceUuid",
			"VARCHAR(75)");
		addColumn(
			CPDefinitionOptionValueRelTable.class,
			CPDefinitionOptionValueRelTable.TABLE_NAME, "CProductId", "LONG");
		addColumn(
			CPDefinitionOptionValueRelTable.class,
			CPDefinitionOptionValueRelTable.TABLE_NAME, "quantity", "INTEGER");
		addColumn(
			CPDefinitionOptionValueRelTable.class,
			CPDefinitionOptionValueRelTable.TABLE_NAME, "price",
			"DECIMAL(30, 16)");

		addColumn(
			CPDefinitionOptionRelTable.class,
			CPDefinitionOptionRelTable.TABLE_NAME, "priceType", "VARCHAR(75)");

		runSQL(
			String.format(
				"update %s set priceType = '%s'",
				CPDefinitionOptionRelTable.TABLE_NAME,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC));

		runSQL(
			String.format(
				"update %s set price = 0",
				CPDefinitionOptionValueRelTable.TABLE_NAME));
	}

}