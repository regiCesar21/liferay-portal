/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.internal.upgrade.v2_0_1;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Alberto Chaparro
 */
public class UpgradeAssetEntryUsage extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasIndex("AssetEntryUsage", "IX_71A0231C")) {
			runSQL("drop index IX_71A0231C on AssetEntryUsage");
		}
	}

}