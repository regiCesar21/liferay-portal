/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.internal.upgrade.v2_0_0;

import com.liferay.dispatch.internal.upgrade.v2_0_0.util.DispatchTriggerTable;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Igor Beslic
 */
public class DispatchTriggerUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_alterTableAddColumn("endDate", "DATE null");

		_alterTableAddColumn("startDate", "DATE null");

		_alterColumnName("typeSettings", "taskSettings TEXT null");

		_alterColumnName("type_", "taskType VARCHAR(75) null");
	}

	private void _alterColumnName(String oldColumnName, String newColumnName)
		throws Exception {

		if (!hasColumn(DispatchTriggerTable.TABLE_NAME, oldColumnName)) {
			if (hasColumn(DispatchTriggerTable.TABLE_NAME, newColumnName)) {
				return;
			}

			throw new UpgradeException(
				String.format(
					"Unable to rename %s to %s in table %s", oldColumnName,
					newColumnName, DispatchTriggerTable.TABLE_NAME));
		}

		alter(
			DispatchTriggerTable.class,
			new AlterColumnName(oldColumnName, newColumnName));
	}

	private void _alterTableAddColumn(String columnName, String columnType)
		throws Exception {

		if (hasColumn(DispatchTriggerTable.TABLE_NAME, columnName)) {
			return;
		}

		alter(
			DispatchTriggerTable.class,
			new AlterTableAddColumn(columnName, columnType));
	}

}