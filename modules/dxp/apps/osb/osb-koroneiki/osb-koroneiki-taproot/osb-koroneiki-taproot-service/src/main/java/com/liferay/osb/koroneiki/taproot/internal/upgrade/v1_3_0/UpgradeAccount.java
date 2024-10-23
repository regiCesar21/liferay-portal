/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.koroneiki.taproot.internal.upgrade.v1_3_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Felipe Veloso
 */
public class UpgradeAccount extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateAccount();
	}

	protected void updateAccount() throws Exception {
		runSQL("ALTER TABLE Koroneiki_Account MODIFY name VARCHAR(250)");
	}

}