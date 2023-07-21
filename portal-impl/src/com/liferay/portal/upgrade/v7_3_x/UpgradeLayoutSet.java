/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_3_x;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.v7_3_x.util.LayoutSetTable;

/**
 * @author Preston Crary
 */
public class UpgradeLayoutSet extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasColumn("LayoutSet", "headId") ||
			hasColumn("LayoutSet", "head")) {

			alter(
				LayoutSetTable.class, new AlterTableDropColumn("headId"),
				new AlterTableDropColumn("head"),
				new AlterTableDropColumn("pageCount"));
		}

		runSQL("DROP_TABLE_IF_EXISTS(LayoutSetVersion)");
	}

}