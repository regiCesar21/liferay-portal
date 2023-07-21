/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.internal.upgrade.v2_2_0;

import com.liferay.dynamic.data.lists.internal.upgrade.v2_2_0.util.DDLRecordTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Jeyvison Nascimento
 */
public class UpgradeSchema extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("DDLRecord", "className")) {
			alter(
				DDLRecordTable.class,
				new AlterTableAddColumn("className", "VARCHAR(300) null"));
		}

		if (!hasColumn("DDLRecord", "classPK")) {
			alter(
				DDLRecordTable.class,
				new AlterTableAddColumn("classPK", "LONG"));
		}
	}

}