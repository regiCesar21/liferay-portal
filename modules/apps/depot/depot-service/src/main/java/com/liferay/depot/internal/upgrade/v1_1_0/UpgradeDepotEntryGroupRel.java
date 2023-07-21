/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.internal.upgrade.v1_1_0;

import com.liferay.depot.internal.upgrade.v1_1_0.util.DepotEntryGroupRelTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Alejandro Tardín
 */
public class UpgradeDepotEntryGroupRel extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alter(
			DepotEntryGroupRelTable.class,
			new AlterTableAddColumn("ddmStructuresAvailable", "BOOLEAN"));
	}

}