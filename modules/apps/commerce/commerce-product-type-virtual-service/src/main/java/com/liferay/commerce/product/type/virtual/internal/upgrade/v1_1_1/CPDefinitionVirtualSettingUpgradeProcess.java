/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.internal.upgrade.v1_1_1;

import com.liferay.commerce.product.type.virtual.internal.upgrade.v1_1_1.util.CPDefinitionVirtualSettingTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Ivica Cardic
 */
public class CPDefinitionVirtualSettingUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasColumnType(
				getTableName(CPDefinitionVirtualSettingTable.class),
				"sampleUrl", "VARCHAR(75) null")) {

			alter(
				CPDefinitionVirtualSettingTable.class,
				new AlterColumnType("sampleUrl", "VARCHAR(255) null"));
		}

		if (hasColumnType(
				getTableName(CPDefinitionVirtualSettingTable.class), "url",
				"VARCHAR(75) null")) {

			alter(
				CPDefinitionVirtualSettingTable.class,
				new AlterColumnType("url", "VARCHAR(255) null"));
		}
	}

}