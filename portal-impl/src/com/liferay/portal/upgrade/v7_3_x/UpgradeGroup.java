/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_3_x;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.v7_3_x.util.GroupTable;

/**
 * @author Rachael Koestartyo
 */
public class UpgradeGroup extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("Group_", "modifiedDate")) {
			alter(
				GroupTable.class,
				new AlterTableAddColumn("modifiedDate", "DATE"));
		}

		runSQL(
			"update Group_ set modifiedDate = CURRENT_TIMESTAMP where " +
				"modifiedDate is null");
	}

}