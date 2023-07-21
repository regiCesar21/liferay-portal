/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.internal.upgrade.v1_2_0;

import com.liferay.commerce.currency.internal.upgrade.base.BaseCommerceCurrencyUpgradeProcess;
import com.liferay.commerce.currency.internal.upgrade.v1_2_0.util.CommerceCurrencyTable;

/**
 * @author Alec Sloan
 */
public class CommerceCurrencySymbolUpgradeProcess
	extends BaseCommerceCurrencyUpgradeProcess {

	@Override
	public void doUpgrade() throws Exception {
		addColumn(
			CommerceCurrencyTable.class, CommerceCurrencyTable.TABLE_NAME,
			"symbol", "VARCHAR(75)");
	}

}