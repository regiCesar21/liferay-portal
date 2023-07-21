/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.internal.upgrade.v2_2_0;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Rafael Praxedes
 */
public class UpgradeSchema extends UpgradeProcess {

	public UpgradeSchema(CounterLocalService counterLocalService) {
		_counterLocalService = counterLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			String template = StringUtil.read(
				UpgradeSchema.class.getResourceAsStream(
					"dependencies/update.sql"));

			runSQLTemplateString(template, false);

			StringBundler sb = new StringBundler(5);

			sb.append("insert into AppBuilderAppVersion (uuid_, ");
			sb.append("appBuilderAppVersionId, groupId, companyId, userId, ");
			sb.append("userName, createDate, modifiedDate, appBuilderAppId, ");
			sb.append("ddlRecordSetId, ddmStructureId, ddmStructureLayoutId, ");
			sb.append("version) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			try (PreparedStatement ps1 = connection.prepareStatement(
					"select AppBuilderApp.* from AppBuilderApp");
				ResultSet rs = ps1.executeQuery();
				PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection, sb.toString())) {

				while (rs.next()) {
					ps2.setString(1, PortalUUIDUtil.generate());
					ps2.setLong(2, _counterLocalService.increment());
					ps2.setLong(3, rs.getLong("groupId"));
					ps2.setLong(4, rs.getLong("companyId"));
					ps2.setLong(5, rs.getLong("userId"));
					ps2.setString(6, rs.getString("userName"));
					ps2.setTimestamp(7, rs.getTimestamp("createDate"));
					ps2.setTimestamp(8, rs.getTimestamp("modifiedDate"));
					ps2.setLong(9, rs.getLong("appBuilderAppId"));
					ps2.setLong(10, rs.getLong("ddlRecordSetId"));
					ps2.setLong(11, rs.getLong("ddmStructureId"));
					ps2.setLong(12, rs.getLong("ddmStructureLayoutId"));
					ps2.setString(13, "1.0");

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	private final CounterLocalService _counterLocalService;

}