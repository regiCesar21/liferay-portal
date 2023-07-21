/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.internal.upgrade.v1_1_0;

import com.liferay.commerce.pricing.internal.upgrade.v1_1_0.util.CommercePricingClassTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Riccardo Alberti
 */
public class CommercePricingClassUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasColumn(CommercePricingClassTable.TABLE_NAME, "title")) {
			alter(
				CommercePricingClassTable.class,
				new AlterColumnType("title", "TEXT null"));
		}

		if (hasColumn(CommercePricingClassTable.TABLE_NAME, "description")) {
			alter(
				CommercePricingClassTable.class,
				new AlterColumnType("description", "TEXT null"));
		}
	}

}