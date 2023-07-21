/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.upgrade.v2_2_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Samuel Trong Tran
 */
public class UpgradeCTPreferences extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL("alter table CTPreferences add previousCtCollectionId LONG");
	}

}