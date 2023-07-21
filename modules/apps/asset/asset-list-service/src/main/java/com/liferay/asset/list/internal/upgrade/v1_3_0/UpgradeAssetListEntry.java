/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.internal.upgrade.v1_3_0;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.list.internal.upgrade.v1_3_0.util.AssetListEntryTable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Eudaldo Alonso
 */
public class UpgradeAssetListEntry extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alter(
			AssetListEntryTable.class,
			new AlterTableAddColumn("assetEntrySubtype", "VARCHAR(255) null"),
			new AlterTableAddColumn("assetEntryType", "VARCHAR(255) null"));

		runSQL(
			"create index IX_6BEBFA56 on AssetListEntry (groupId, " +
				"assetEntrySubtype[$COLUMN_LENGTH:255$], " +
					"assetEntryType[$COLUMN_LENGTH:255$], ctCollectionId)");

		runSQL(
			"create index IX_D604A2E on AssetListEntry (groupId, " +
				"assetEntryType[$COLUMN_LENGTH:255$], ctCollectionId)");

		runSQL(
			StringBundler.concat(
				"create index IX_FD621D8E on AssetListEntry (groupId, ",
				"title[$COLUMN_LENGTH:75$], ",
				"assetEntrySubtype[$COLUMN_LENGTH:255$], ",
				"assetEntryType[$COLUMN_LENGTH:255$], ctCollectionId)"));

		runSQL(
			"create index IX_CBD041F6 on AssetListEntry (groupId, " +
				"title[$COLUMN_LENGTH:75$], " +
					"assetEntryType[$COLUMN_LENGTH:255$], ctCollectionId)");

		runSQL(
			"update AssetListEntry set assetEntryType = '" +
				AssetEntry.class.getName() + "'");
	}

}