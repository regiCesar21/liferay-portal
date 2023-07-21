/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.internal.upgrade.v1_2_0;

import com.liferay.depot.internal.upgrade.v1_2_0.util.DepotEntryGroupRelTable;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alejandro Tardín
 */
public class UpgradeDepotEntryGroupRel extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alter(
			DepotEntryGroupRelTable.class,
			new AlterTableAddColumn("uuid_", "VARCHAR(75) null"),
			new AlterTableAddColumn("groupId", "LONG"),
			new AlterTableAddColumn("createDate", "DATE null"),
			new AlterTableAddColumn("modifiedDate", "DATE null"));

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			try (PreparedStatement ps1 = connection.prepareStatement(
					"select depotEntryGroupRelId from DepotEntryGroupRel");
				PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.autoBatch(
						connection.prepareStatement(
							"update DepotEntryGroupRel set groupId = " +
								"toGroupId, uuid_ = ? where " +
									"depotEntryGroupRelId = ?"));
				ResultSet rs = ps1.executeQuery()) {

				while (rs.next()) {
					ps2.setString(1, PortalUUIDUtil.generate());
					ps2.setLong(2, rs.getLong(1));

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

}