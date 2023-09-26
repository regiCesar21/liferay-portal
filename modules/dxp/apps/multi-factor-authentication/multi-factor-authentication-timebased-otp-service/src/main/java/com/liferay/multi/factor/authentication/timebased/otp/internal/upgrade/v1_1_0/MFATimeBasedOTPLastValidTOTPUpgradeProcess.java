/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.timebased.otp.internal.upgrade.v1_1_0;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Manuele Castro
 */
public class MFATimeBasedOTPLastValidTOTPUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_alterTableAddColumn("lastValidTOTP", "VARCHAR(75) null");
	}

	private void _alterTableAddColumn(String columnName, String columnType)
		throws Exception {

		if (hasColumn("MFATimeBasedOTPEntry", columnName)) {
			return;
		}

		runSQL(
			StringBundler.concat(
				"alter table MFATimeBasedOTPEntry add ", columnName,
				StringPool.SPACE, columnType));
	}

}