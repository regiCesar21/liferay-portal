/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.upgrade.v3_2_0;

import com.liferay.layout.page.template.internal.upgrade.v3_2_0.util.LayoutPageTemplateEntryTable;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Jürgen Kappler
 */
public class UpgradeLayoutPageTemplateEntry extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeSchema();
		upgradeLayoutPageTemplateEntryKey();
	}

	protected void upgradeLayoutPageTemplateEntryKey() throws SQLException {
		try (Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(
				"select layoutPageTemplateEntryId, name from " +
					"LayoutPageTemplateEntry");
			PreparedStatement ps = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"update LayoutPageTemplateEntry set " +
						"layoutPageTemplateEntryKey = ? where " +
							"layoutPageTemplateEntryId = ?"))) {

			while (rs.next()) {
				long layoutPageTemplateEntryId = rs.getLong(
					"layoutPageTemplateEntryId");

				String name = rs.getString("name");

				ps.setString(1, _generateLayoutPageTemplateEntryKey(name));

				ps.setLong(2, layoutPageTemplateEntryId);

				ps.addBatch();
			}

			ps.executeBatch();
		}
	}

	protected void upgradeSchema() throws Exception {
		alter(
			LayoutPageTemplateEntryTable.class,
			new AlterTableAddColumn(
				"layoutPageTemplateEntryKey", "VARCHAR(75)"));
	}

	private String _generateLayoutPageTemplateEntryKey(String name) {
		String layoutPageTemplateEntryKey = StringUtil.toLowerCase(name.trim());

		return StringUtil.replace(
			layoutPageTemplateEntryKey, CharPool.SPACE, CharPool.DASH);
	}

}