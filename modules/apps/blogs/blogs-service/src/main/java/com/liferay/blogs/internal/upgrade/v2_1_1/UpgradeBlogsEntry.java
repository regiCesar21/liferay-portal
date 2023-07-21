/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.internal.upgrade.v2_1_1;

import com.liferay.blogs.internal.upgrade.v2_1_1.util.BlogsEntryTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Brian I. Kim
 */
public class UpgradeBlogsEntry extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasColumnType(
				getTableName(BlogsEntryTable.class), "title",
				"VARCHAR(150) null")) {

			alter(
				BlogsEntryTable.class,
				new AlterColumnType("title", "VARCHAR(255) null"));
		}
	}

}