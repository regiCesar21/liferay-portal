/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_3_x;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.v7_3_x.util.CompanyTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alberto Chaparro
 */
public class UpgradeSchema extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQLTemplate("update-7.2.0-7.3.0.sql", false);

		_copyCompanyKey();

		alter(CompanyTable.class, new AlterTableDropColumn("key_"));
	}

	private void _copyCompanyKey() throws Exception {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select companyId, key_ from Company");
			ResultSet rs = ps1.executeQuery();
			PreparedStatement ps2 = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"insert into CompanyInfo (companyInfoId, companyId, " +
						"key_) values (?, ?, ?)"))) {

			while (rs.next()) {
				ps2.setLong(1, increment());
				ps2.setLong(2, rs.getLong(1));
				ps2.setString(3, rs.getString(2));

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

}