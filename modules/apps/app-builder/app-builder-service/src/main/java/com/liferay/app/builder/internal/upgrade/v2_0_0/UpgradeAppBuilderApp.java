/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.internal.upgrade.v2_0_0;

import com.liferay.app.builder.internal.upgrade.v2_0_0.util.AppBuilderAppTable;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Rafael Praxedes
 */
public class UpgradeAppBuilderApp extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("AppBuilderApp", "active_")) {
			alter(
				AppBuilderAppTable.class,
				new AlterTableAddColumn("active_", "BOOLEAN"));

			try (PreparedStatement ps1 = connection.prepareStatement(
					"select appBuilderAppId, status from AppBuilderApp");
				PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection,
						"update AppBuilderApp set active_ = ? where " +
							"appBuilderAppId = ?");
				ResultSet rs = ps1.executeQuery()) {

				while (rs.next()) {
					ps2.setBoolean(
						1, (rs.getInt("status") == 0) ? true : false);
					ps2.setLong(2, rs.getLong("appBuilderAppId"));

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}

		if (hasColumn("AppBuilderApp", "status")) {
			alter(AppBuilderAppTable.class, new AlterTableDropColumn("status"));
		}
	}

}