/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.upgrade.v1_0_3;

import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pei-Jung Lan
 */
public class UpgradeRole extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			StringBundler.concat(
				"update Role_ set type_ = ", RoleConstants.TYPE_ORGANIZATION,
				" where name = '",
				AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MANAGER, "'"));

		try (PreparedStatement ps1 = connection.prepareStatement(
				"select distinct Role_.roleId from Role_ where name = '" +
					AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MANAGER +
						"'");
			PreparedStatement ps2 = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"delete from ResourcePermission where roleId = ?"))) {

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					long roleId = rs.getLong("roleId");

					ps2.setLong(1, roleId);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

}