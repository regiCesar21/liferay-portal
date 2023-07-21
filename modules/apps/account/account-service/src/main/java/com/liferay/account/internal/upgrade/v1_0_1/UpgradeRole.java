/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.upgrade.v1_0_1;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountRole;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pei-Jung Lan
 */
public class UpgradeRole extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			StringBundler sb = new StringBundler(8);

			sb.append("select distinct Role_.roleId from Role_ inner join ");
			sb.append("AccountRole on AccountRole.roleId = Role_.roleId ");
			sb.append("where AccountRole.accountEntryId = ");
			sb.append(AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT);
			sb.append(" and Role_.classNameId = ");
			sb.append(PortalUtil.getClassNameId(AccountRole.class));
			sb.append(" and Role_.type_ = ");
			sb.append(RoleConstants.TYPE_PROVIDER);

			try (PreparedStatement ps1 = connection.prepareStatement(
					sb.toString());
				PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.autoBatch(
						connection.prepareStatement(
							"update Role_ set type_ = " +
								RoleConstants.TYPE_ACCOUNT +
									" where roleId = ?"))) {

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

}