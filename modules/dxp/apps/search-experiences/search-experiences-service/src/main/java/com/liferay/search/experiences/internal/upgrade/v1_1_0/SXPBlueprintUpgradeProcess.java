/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.upgrade.v1_1_0;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Gustavo Lima
 */
public class SXPBlueprintUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_alterTableAddColumn("key_", "VARCHAR(75) null");
		_alterTableAddColumn("version", "VARCHAR(75) null");
	}

	private void _alterTableAddColumn(String columnName, String columnType)
		throws Exception {

		if (hasColumn("SXPBlueprint", columnName)) {
			return;
		}

		runSQL(
			StringBundler.concat(
				"alter table SXPBlueprint add ", columnName, StringPool.SPACE,
				columnType));
	}

}