/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.internal.upgrade.v2_1_0;

import com.liferay.layout.seo.internal.upgrade.v2_0_0.util.LayoutSEOEntryTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Alberto Chaparro
 */
public class UpgradeSchema extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alter(
			LayoutSEOEntryTable.class,
			new AlterTableAddColumn("DDMStorageId", "LONG"),
			new AlterTableAddColumn("openGraphImageAlt", "STRING null"));

		String template = StringUtil.read(
			UpgradeSchema.class.getResourceAsStream("dependencies/update.sql"));

		runSQLTemplateString(template, false);
	}

}