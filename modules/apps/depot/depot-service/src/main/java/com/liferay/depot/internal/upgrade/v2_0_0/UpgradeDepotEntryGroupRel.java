/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.internal.upgrade.v2_0_0;

import com.liferay.depot.internal.upgrade.v2_0_0.util.DepotEntryGroupRelTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Alicia Garcia
 */
public class UpgradeDepotEntryGroupRel extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alter(
			DepotEntryGroupRelTable.class,
			new AlterTableAddColumn("userId", "LONG"),
			new AlterTableAddColumn("userName", "VARCHAR(75) null"),
			new AlterTableAddColumn("lastPublishDate", "DATE null"));
	}

}