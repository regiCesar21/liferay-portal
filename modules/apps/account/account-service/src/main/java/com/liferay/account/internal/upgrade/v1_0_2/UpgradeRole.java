/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.upgrade.v1_0_2;

import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;

/**
 * @author Pei-Jung Lan
 */
public class UpgradeRole extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			"delete from Role_ where name = '" +
				AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR +
					"'");

		_updateRole(
			AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_POWER_USER,
			AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR);
		_updateRole(
			AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_OWNER,
			AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MANAGER);
		_updateRole(
			AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_USER,
			AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MEMBER);
	}

	private void _updateRole(String oldName, String newName) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"update Role_ set name = ?, title = NULL where name = ?")) {

			ps.setString(1, newName);
			ps.setString(2, oldName);

			ps.executeUpdate();
		}
	}

}