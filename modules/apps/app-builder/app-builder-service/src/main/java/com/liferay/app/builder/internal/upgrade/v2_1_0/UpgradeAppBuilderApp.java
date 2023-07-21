/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.internal.upgrade.v2_1_0;

import com.liferay.app.builder.constants.AppBuilderAppConstants;
import com.liferay.app.builder.internal.upgrade.v2_1_0.util.AppBuilderAppTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;

/**
 * @author Rafael Praxedes
 */
public class UpgradeAppBuilderApp extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn(AppBuilderAppTable.TABLE_NAME, "scope")) {
			alter(
				AppBuilderAppTable.class,
				new AlterTableAddColumn("scope", "VARCHAR(75) null"));

			try (PreparedStatement ps = connection.prepareStatement(
					"update AppBuilderApp set scope = ?")) {

				ps.setString(1, AppBuilderAppConstants.SCOPE_STANDARD);

				ps.execute();
			}
		}
	}

}