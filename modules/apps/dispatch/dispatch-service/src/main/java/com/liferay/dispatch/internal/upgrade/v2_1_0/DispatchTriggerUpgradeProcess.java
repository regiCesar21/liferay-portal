/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.internal.upgrade.v2_1_0;

import com.liferay.dispatch.internal.upgrade.v2_1_0.util.DispatchTriggerTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Matija Petanjek
 */
public class DispatchTriggerUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasColumn(DispatchTriggerTable.TABLE_NAME, "overlapAllowed")) {
			return;
		}

		alter(
			DispatchTriggerTable.class,
			new AlterTableAddColumn("overlapAllowed", "BOOLEAN"));
	}

}