/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.internal.upgrade.v2_1_0;

import com.liferay.commerce.inventory.internal.upgrade.v2_1_0.util.CommerceInventoryBookedQuantityTable;
import com.liferay.commerce.inventory.internal.upgrade.v2_1_0.util.CommerceInventoryReplenishmentItemTable;
import com.liferay.commerce.inventory.internal.upgrade.v2_1_0.util.CommerceInventoryWarehouseItemTable;
import com.liferay.commerce.inventory.internal.upgrade.v2_1_0.util.CommerceInventoryWarehouseTable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Luca Pellizzon
 */
public class MVCCUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasTable(CommerceInventoryBookedQuantityTable.TABLE_NAME)) {
			_addColumn(
				CommerceInventoryBookedQuantityTable.class,
				CommerceInventoryBookedQuantityTable.TABLE_NAME, "mvccVersion",
				"LONG default 0 not null");
		}

		if (hasTable(CommerceInventoryReplenishmentItemTable.TABLE_NAME)) {
			_addColumn(
				CommerceInventoryReplenishmentItemTable.class,
				CommerceInventoryReplenishmentItemTable.TABLE_NAME,
				"mvccVersion", "LONG default 0 not null");
		}

		if (hasTable(CommerceInventoryWarehouseTable.TABLE_NAME)) {
			_addColumn(
				CommerceInventoryWarehouseTable.class,
				CommerceInventoryWarehouseTable.TABLE_NAME, "mvccVersion",
				"LONG default 0 not null");
		}

		if (hasTable(CommerceInventoryWarehouseItemTable.TABLE_NAME)) {
			_addColumn(
				CommerceInventoryWarehouseItemTable.class,
				CommerceInventoryWarehouseItemTable.TABLE_NAME, "mvccVersion",
				"LONG default 0 not null");
		}
	}

	private void _addColumn(
			Class<?> entityClass, String tableName, String columnName,
			String columnType)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info(
				String.format(
					"Adding column %s to table %s", columnName, tableName));
		}

		if (!hasColumn(tableName, columnName)) {
			alter(
				entityClass,
				new AlterTableAddColumn(
					columnName + StringPool.SPACE + columnType));
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info(
					String.format(
						"Column %s already exists on table %s", columnName,
						tableName));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MVCCUpgradeProcess.class);

}