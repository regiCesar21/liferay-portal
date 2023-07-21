/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_3_x;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.v7_3_x.util.RatingsStatsTable;

import java.sql.PreparedStatement;

/**
 * @author Javier Gamarra
 */
public class UpgradeRatingsStats extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("RatingsStats", "createDate")) {
			alter(
				RatingsStatsTable.class,
				new AlterTableAddColumn("createDate", "DATE null"));
		}

		if (!hasColumn("RatingsStats", "modifiedDate")) {
			alter(
				RatingsStatsTable.class,
				new AlterTableAddColumn("modifiedDate", "DATE null"));
		}

		try (PreparedStatement ps = connection.prepareStatement(
				_getUpdateSQL("createDate", "min"))) {

			ps.executeUpdate();
		}

		try (PreparedStatement ps = connection.prepareStatement(
				_getUpdateSQL("modifiedDate", "max"))) {

			ps.executeUpdate();
		}
	}

	private String _getUpdateSQL(String columnName, String function) {
		return StringBundler.concat(
			"update RatingsStats set ", columnName, " = (select ", function,
			"(", columnName,
			") FROM RatingsEntry WHERE RatingsStats.classNameId = ",
			"RatingsEntry.classNameId and RatingsStats.classPK = ",
			"RatingsEntry.classPK)");
	}

}