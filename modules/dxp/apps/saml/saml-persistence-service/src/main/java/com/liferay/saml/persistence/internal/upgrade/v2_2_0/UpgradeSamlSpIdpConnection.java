/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.persistence.internal.upgrade.v2_2_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.saml.persistence.internal.upgrade.v2_2_0.util.SamlSpIdpConnectionTable;

import java.sql.SQLException;

/**
 * @author Stian Sigvartsen
 */
public class UpgradeSamlSpIdpConnection extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			alter(
				SamlSpIdpConnectionTable.class,
				new AlterTableAddColumn("unknownUsersAreStrangers", "BOOLEAN"));
		}
		catch (SQLException sqlException) {
			upgradeTable(
				SamlSpIdpConnectionTable.TABLE_NAME,
				SamlSpIdpConnectionTable.TABLE_COLUMNS,
				SamlSpIdpConnectionTable.TABLE_SQL_CREATE,
				SamlSpIdpConnectionTable.TABLE_SQL_ADD_INDEXES);
		}
	}

}