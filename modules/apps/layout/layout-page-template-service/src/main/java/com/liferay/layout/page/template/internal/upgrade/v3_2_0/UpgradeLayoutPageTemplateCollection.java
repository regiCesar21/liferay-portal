/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.upgrade.v3_2_0;

import com.liferay.layout.page.template.internal.upgrade.v2_0_0.util.LayoutPageTemplateCollectionTable;
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
public class UpgradeLayoutPageTemplateCollection extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeSchema();
		upgradeLayoutPageTemplateCollectionKey();
	}

	protected void upgradeLayoutPageTemplateCollectionKey()
		throws SQLException {

		try (Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(
				"select layoutPageTemplateCollectionId, name from " +
					"LayoutPageTemplateCollection");
			PreparedStatement ps = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"update LayoutPageTemplateCollection set " +
						"lptCollectionKey = ? where " +
							"layoutPageTemplateCollectionId = ?"))) {

			while (rs.next()) {
				long layoutPageTemplateCollectionId = rs.getLong(
					"layoutPageTemplateCollectionId");

				String name = rs.getString("name");

				ps.setString(1, _generateLayoutPageTemplateCollectionKey(name));

				ps.setLong(2, layoutPageTemplateCollectionId);

				ps.addBatch();
			}

			ps.executeBatch();
		}
	}

	protected void upgradeSchema() throws Exception {
		alter(
			LayoutPageTemplateCollectionTable.class,
			new AlterTableAddColumn("lptCollectionKey", "VARCHAR(75)"));
	}

	private String _generateLayoutPageTemplateCollectionKey(String name) {
		return StringUtil.replace(
			StringUtil.toLowerCase(name.trim()),
			new char[] {CharPool.FORWARD_SLASH, CharPool.SPACE},
			new char[] {CharPool.DASH, CharPool.DASH});
	}

}