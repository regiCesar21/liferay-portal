/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_3_x;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.v7_3_x.util.LayoutTable;

/**
 * @author Eudaldo Alonso
 */
public class UpgradeLayoutStyleBookEntry extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("Layout", "styleBookEntryId")) {
			alter(
				LayoutTable.class,
				new AlterTableAddColumn("styleBookEntryId", "LONG"));
		}

		runSQL("update Layout set styleBookEntryId = 0");
	}

}