/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.internal.upgrade.v1_2_0;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.style.book.internal.upgrade.v1_2_0.util.StyleBookEntryVersionTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Víctor Galán
 */
public class UpgradeStyleBookEntryVersion extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeSchema();
	}

	protected void upgradeSchema() throws Exception {
		alter(
			StyleBookEntryVersionTable.class,
			new AlterTableAddColumn("uuid_", "VARCHAR(75) null"),
			new AlterTableAddColumn("modifiedDate", "DATE null"));

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			try (PreparedStatement ps1 = connection.prepareStatement(
					"select styleBookEntryId from StyleBookEntry");
				PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.autoBatch(
						connection.prepareStatement(
							"update StyleBookEntryVersion set uuid_ = ? " +
								"where styleBookEntryId = ?"));
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