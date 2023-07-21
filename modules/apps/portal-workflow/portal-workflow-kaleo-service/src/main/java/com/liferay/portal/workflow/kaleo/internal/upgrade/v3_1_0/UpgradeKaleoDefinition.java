/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.upgrade.v3_1_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v3_1_0.util.KaleoDefinitionTable;

import java.sql.PreparedStatement;

/**
 * @author Inácio Nery
 */
public class UpgradeKaleoDefinition extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("KaleoDefinition", "scope")) {
			alter(
				KaleoDefinitionTable.class,
				new AlterTableAddColumn("scope", "VARCHAR(75) null"));

			try (PreparedStatement ps = connection.prepareStatement(
					"update KaleoDefinition set scope = ?")) {

				ps.setString(1, WorkflowDefinitionConstants.SCOPE_ALL);

				ps.execute();
			}
		}
	}

}