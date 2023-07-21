/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.upgrade.v3_1_3;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Pavel Savinov
 */
public class UpgradeLayoutPageTemplateEntry extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeIndex();
	}

	protected void upgradeIndex() throws Exception {
		if (hasIndex("LayoutPageTemplateEntry", "IX_A075DAA4")) {
			runSQL("drop index IX_A075DAA4 on LayoutPageTemplateEntry");
		}

		if (!hasIndex("LayoutPageTemplateEntry", "IX_C3960EB1")) {
			runSQL(
				"create unique index IX_C3960EB1 on LayoutPageTemplateEntry (" +
					"groupId, name, type_)");
		}
	}

}