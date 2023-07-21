/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v4_6_0;

import com.liferay.commerce.internal.upgrade.base.BaseCommerceServiceUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v4_6_0.util.CommerceShipmentTable;

/**
 * @author Luca Pellizzon
 */
public class ShipmentUpgradeProcess extends BaseCommerceServiceUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		changeColumnType(
			CommerceShipmentTable.class, CommerceShipmentTable.TABLE_NAME,
			"shippingOptionName", "TEXT");
	}

}