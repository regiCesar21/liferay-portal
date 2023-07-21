/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.upgrade.v3_1_0;

import com.liferay.commerce.product.internal.upgrade.base.BaseCommerceProductServiceUpgradeProcess;
import com.liferay.commerce.product.internal.upgrade.v3_1_0.util.CPTaxCategoryTable;

/**
 * @author Danny Situ
 */
public class CPTaxCategoryUpgradeProcess
	extends BaseCommerceProductServiceUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		addColumn(
			CPTaxCategoryTable.class, CPTaxCategoryTable.TABLE_NAME,
			"externalReferenceCode", "VARCHAR(75)");
	}

}