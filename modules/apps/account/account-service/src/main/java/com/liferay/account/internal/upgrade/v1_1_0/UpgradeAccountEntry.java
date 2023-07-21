/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.upgrade.v1_1_0;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.internal.upgrade.v1_1_0.util.AccountEntryTable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Drew Brokke
 */
public class UpgradeAccountEntry extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("AccountEntry", "externalReferenceCode")) {
			alter(
				AccountEntryTable.class,
				new AlterTableAddColumn(
					"externalReferenceCode", "VARCHAR(75)"));
		}

		if (!hasColumn("AccountEntry", "taxIdNumber")) {
			alter(
				AccountEntryTable.class,
				new AlterTableAddColumn("taxIdNumber", "VARCHAR(75)"));
		}

		if (!hasColumn("AccountEntry", "type_")) {
			alter(
				AccountEntryTable.class,
				new AlterTableAddColumn("type_", "VARCHAR(75)"));

			String defaultType = StringUtil.quote(
				AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
				StringPool.APOSTROPHE);

			runSQL("update AccountEntry set type_ = " + defaultType);
		}
	}

}