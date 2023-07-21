/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.upgrade.v2_2_1;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Alberto Chaparro
 */
public class UpgradeFragmentEntry extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasIndex("FragmentEntry", "IX_62913C70")) {
			runSQL("drop index IX_62913C70 on FragmentEntry");
		}
	}

}