/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pavel Savinov
 */
public class UpgradeLayout extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select plid, typeSettings from Layout where classNameId = ? " +
					"and classPK > 0 and type_ = ? and system_ = ?");
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update Layout set status = ? where plid = ?")) {

			ps1.setLong(1, PortalUtil.getClassNameId(Layout.class));
			ps1.setString(2, LayoutConstants.TYPE_CONTENT);
			ps1.setBoolean(3, true);

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					long plid = rs.getLong("plid");
					String typeSettings = rs.getString("typeSettings");

					UnicodeProperties unicodeProperties =
						new UnicodeProperties();

					unicodeProperties.load(typeSettings);

					boolean published = GetterUtil.getBoolean(
						unicodeProperties.getProperty("published"));

					if (published) {
						ps2.setInt(1, WorkflowConstants.STATUS_APPROVED);
					}
					else {
						ps2.setInt(1, WorkflowConstants.STATUS_DRAFT);
					}

					ps2.setLong(2, plid);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

}