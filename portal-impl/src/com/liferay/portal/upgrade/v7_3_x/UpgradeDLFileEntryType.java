/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_3_x;

import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alicia Garcia
 */
public class UpgradeDLFileEntryType extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeSchema();

		_populateFields();
	}

	private void _populateFields() throws Exception {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select uuid_, fileEntryTypeId, groupId, fileEntryTypeKey " +
					"from DLFileEntryType where (dataDefinitionId IS NULL OR " +
						"dataDefinitionId = 0)");
			PreparedStatement ps2 = connection.prepareStatement(
				"select structureId FROM DDMStructure where groupId = ? AND " +
					"classNameId = ? AND (structureKey = ? OR structureKey = " +
						"? OR structureKey = ? ) ");
			PreparedStatement ps3 = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"update DLFileEntryType set dataDefinitionId = ? where " +
						"fileEntryTypeId = ? "));
			ResultSet rs1 = ps1.executeQuery()) {

			long classNameId = PortalUtil.getClassNameId(
				DLFileEntryMetadata.class);

			while (rs1.next()) {
				ps2.setLong(1, rs1.getLong(3));
				ps2.setLong(2, classNameId);
				ps2.setString(3, DLUtil.getDDMStructureKey(rs1.getString(1)));
				ps2.setString(
					4, DLUtil.getDeprecatedDDMStructureKey(rs1.getLong(2)));
				ps2.setString(5, rs1.getString(4));

				try (ResultSet rs2 = ps2.executeQuery()) {
					if (rs2.next()) {
						ps3.setLong(1, rs2.getLong(1));
						ps3.setLong(2, rs1.getLong(2));

						ps3.addBatch();
					}
				}
			}

			ps3.executeBatch();
		}
	}

	private void _upgradeSchema() throws Exception {
		if (!hasColumn("DLFileEntryType", "dataDefinitionId")) {
			runSQL("alter table DLFileEntryType add dataDefinitionId LONG ");
		}
	}

}