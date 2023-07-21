/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.category.property.internal.upgrade.v2_2_0;

import com.liferay.asset.category.property.internal.upgrade.v2_2_0.util.AssetCategoryPropertyTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Jürgen Kappler
 */
public class UpgradeAssetCategoryProperty extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasColumnType(
				"AssetCategoryProperty", "key_", "VARCHAR(75) null")) {

			alter(
				AssetCategoryPropertyTable.class,
				new AlterColumnType("key_", "VARCHAR(255) null"));
		}

		if (hasColumnType(
				"AssetCategoryProperty", "value", "VARCHAR(75) null")) {

			alter(
				AssetCategoryPropertyTable.class,
				new AlterColumnType("value", "VARCHAR(255) null"));
		}
	}

}