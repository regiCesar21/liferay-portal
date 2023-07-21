/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_2_x;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Raymond Augé
 */
public class UpgradeVirtualHost extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("VirtualHost", "defaultVirtualHost")) {
			runSQL("alter table VirtualHost add defaultVirtualHost BOOLEAN");

			runSQL("update VirtualHost set defaultVirtualHost = [$TRUE$]");
		}

		if (!hasColumn("VirtualHost", "languageId")) {
			runSQL("alter table VirtualHost add languageId VARCHAR(75) null");
		}
	}

}