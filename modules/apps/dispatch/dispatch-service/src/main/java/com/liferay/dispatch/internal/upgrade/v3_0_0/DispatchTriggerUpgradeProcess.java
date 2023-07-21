/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.internal.upgrade.v3_0_0;

import com.liferay.dispatch.internal.upgrade.v2_0_0.util.DispatchTriggerTable;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Igor Beslic
 */
public class DispatchTriggerUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String oldColumnName = "taskType";
		String newColumnName = "taskExecutorType";

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
			new AlterColumnName(
				oldColumnName, newColumnName + " VARCHAR(75) null"));
	}

}