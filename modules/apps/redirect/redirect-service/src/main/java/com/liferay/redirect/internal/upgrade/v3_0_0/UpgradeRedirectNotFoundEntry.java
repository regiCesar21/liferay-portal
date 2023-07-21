/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.internal.upgrade.v3_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.redirect.internal.upgrade.v3_0_0.util.RedirectNotFoundEntryTable;

/**
 * @author Alejandro Tardín
 */
public class UpgradeRedirectNotFoundEntry extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alter(
			RedirectNotFoundEntryTable.class, new AlterTableDropColumn("hits"));
	}

}