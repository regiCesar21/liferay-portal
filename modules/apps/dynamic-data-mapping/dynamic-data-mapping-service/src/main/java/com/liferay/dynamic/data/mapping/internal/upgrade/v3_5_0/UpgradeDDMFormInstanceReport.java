/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v3_5_0;

import com.liferay.dynamic.data.mapping.internal.upgrade.v3_5_0.util.DDMFormInstanceReportTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Marcos Martins
 */
public class UpgradeDDMFormInstanceReport extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasTable(DDMFormInstanceReportTable.TABLE_NAME)) {
			runSQL(DDMFormInstanceReportTable.TABLE_SQL_CREATE);
		}
	}

}