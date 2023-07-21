/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_3_x;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.v7_3_x.util.LayoutTable;

/**
 * @author Preston Crary
 */
public class UpgradeLayout extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasColumn(LayoutTable.TABLE_NAME, "headId") ||
			hasColumn(LayoutTable.TABLE_NAME, "head")) {

			alter(
				LayoutTable.class, new AlterTableDropColumn("headId"),
				new AlterTableDropColumn("head"));
		}

		if (!hasColumnType(
				LayoutTable.TABLE_NAME, "description", "TEXT null")) {

			alter(
				LayoutTable.class,
				new UpgradeProcess.AlterColumnType("description", "TEXT null"));
		}

		if (!hasColumn(LayoutTable.TABLE_NAME, "masterLayoutPlid")) {
			alter(
				LayoutTable.class,
				new AlterTableAddColumn("masterLayoutPlid", "LONG"));

			runSQL("update Layout set masterLayoutPlid = 0");
		}

		if (!hasColumn(LayoutTable.TABLE_NAME, "status")) {
			alter(
				LayoutTable.class,
				new AlterTableAddColumn("status", "INTEGER"));

			runSQL("update Layout set status = 0");
		}

		if (!hasColumn(LayoutTable.TABLE_NAME, "statusByUserId")) {
			alter(
				LayoutTable.class,
				new AlterTableAddColumn("statusByUserId", "LONG"));
		}

		if (!hasColumn(LayoutTable.TABLE_NAME, "statusByUserName")) {
			alter(
				LayoutTable.class,
				new AlterTableAddColumn(
					"statusByUserName", "VARCHAR(75) null"));
		}

		if (!hasColumn(LayoutTable.TABLE_NAME, "statusDate")) {
			alter(
				LayoutTable.class,
				new AlterTableAddColumn("statusDate", "DATE null"));
		}

		runSQL("DROP_TABLE_IF_EXISTS(LayoutVersion)");

		runSQL("update Layout set classNameId = 0 where classNameId is null");

		if (!hasColumnType(LayoutTable.TABLE_NAME, "title", "TEXT null")) {
			alter(LayoutTable.class, new AlterColumnType("title", "TEXT null"));
		}
	}

}