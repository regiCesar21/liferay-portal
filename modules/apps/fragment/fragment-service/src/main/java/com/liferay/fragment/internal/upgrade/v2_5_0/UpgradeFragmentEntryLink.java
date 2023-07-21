/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.upgrade.v2_5_0;

import com.liferay.fragment.internal.upgrade.v2_5_0.util.FragmentEntryLinkTable;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Eudaldo Alonso
 */
public class UpgradeFragmentEntryLink extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("FragmentEntryLink", "plid")) {
			alter(
				FragmentEntryLinkTable.class,
				new AlterTableAddColumn("plid", "LONG"));
		}

		runSQL(
			"update FragmentEntryLink set plid = classPK where classNameId = " +
				PortalUtil.getClassNameId(Layout.class.getName()));
	}

}