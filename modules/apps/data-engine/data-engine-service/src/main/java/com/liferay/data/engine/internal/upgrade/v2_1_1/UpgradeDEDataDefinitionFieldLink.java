/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.internal.upgrade.v2_1_1;

import com.liferay.data.engine.internal.upgrade.v2_1_1.util.DEDataDefinitionFieldLinkTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Eudaldo Alonso
 */
public class UpgradeDEDataDefinitionFieldLink extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasColumnType("DEDataDefinitionFieldLink", "fieldName", "LONG")) {
			alter(
				DEDataDefinitionFieldLinkTable.class,
				new AlterColumnType("fieldName", "VARCHAR(75) null"));
		}
	}

}