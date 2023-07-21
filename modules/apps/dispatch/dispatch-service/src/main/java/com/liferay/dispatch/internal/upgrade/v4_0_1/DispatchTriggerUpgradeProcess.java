/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.internal.upgrade.v4_0_1;

import com.liferay.dispatch.internal.upgrade.v4_0_1.util.DispatchTriggerTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Matija Petanjek
 */
public class DispatchTriggerUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alter(
			DispatchTriggerTable.class,
			new AlterColumnType("dispatchTaskExecutorType", "VARCHAR(75) null"),
			new AlterColumnType("dispatchTaskSettings", "TEXT null"));
	}

}