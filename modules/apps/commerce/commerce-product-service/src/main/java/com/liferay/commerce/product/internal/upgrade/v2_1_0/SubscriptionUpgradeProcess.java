/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.upgrade.v2_1_0;

import com.liferay.commerce.product.internal.upgrade.base.BaseCommerceProductServiceUpgradeProcess;
import com.liferay.commerce.product.internal.upgrade.v2_1_0.util.CPDefinitionTable;
import com.liferay.commerce.product.internal.upgrade.v2_1_0.util.CPInstanceTable;

/**
 * @author Luca Pellizzon
 */
public class SubscriptionUpgradeProcess
	extends BaseCommerceProductServiceUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		addColumn(
			CPDefinitionTable.class, CPDefinitionTable.TABLE_NAME,
			"deliverySubscriptionEnabled", "BOOLEAN");
		addColumn(
			CPDefinitionTable.class, CPDefinitionTable.TABLE_NAME,
			"deliverySubscriptionLength", "INTEGER");
		addColumn(
			CPDefinitionTable.class, CPDefinitionTable.TABLE_NAME,
			"deliverySubscriptionType", "VARCHAR(75)");
		addColumn(
			CPDefinitionTable.class, CPDefinitionTable.TABLE_NAME,
			"deliverySubTypeSettings", "TEXT");
		addColumn(
			CPDefinitionTable.class, CPDefinitionTable.TABLE_NAME,
			"deliveryMaxSubscriptionCycles", "LONG");

		addColumn(
			CPInstanceTable.class, CPInstanceTable.TABLE_NAME,
			"deliverySubscriptionEnabled", "BOOLEAN");
		addColumn(
			CPInstanceTable.class, CPInstanceTable.TABLE_NAME,
			"deliverySubscriptionLength", "INTEGER");
		addColumn(
			CPInstanceTable.class, CPInstanceTable.TABLE_NAME,
			"deliverySubscriptionType", "VARCHAR(75)");
		addColumn(
			CPInstanceTable.class, CPInstanceTable.TABLE_NAME,
			"deliverySubTypeSettings", "TEXT");
		addColumn(
			CPInstanceTable.class, CPInstanceTable.TABLE_NAME,
			"deliveryMaxSubscriptionCycles", "LONG");
	}

}