/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v4_6_0;

import com.liferay.commerce.internal.upgrade.base.BaseCommerceServiceUpgradeProcess;
import com.liferay.commerce.internal.upgrade.v4_6_0.util.CommerceSubscriptionEntryTable;

/**
 * @author Luca Pellizzon
 */
public class SubscriptionUpgradeProcess
	extends BaseCommerceServiceUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn(
				CommerceSubscriptionEntryTable.TABLE_NAME,
				"deliverySubscriptionLength")) {

			addColumn(
				CommerceSubscriptionEntryTable.class,
				CommerceSubscriptionEntryTable.TABLE_NAME,
				"deliverySubscriptionLength", "INTEGER");
		}

		if (!hasColumn(
				CommerceSubscriptionEntryTable.TABLE_NAME,
				"deliverySubscriptionType")) {

			addColumn(
				CommerceSubscriptionEntryTable.class,
				CommerceSubscriptionEntryTable.TABLE_NAME,
				"deliverySubscriptionType", "VARCHAR(75)");
		}

		if (!hasColumn(
				CommerceSubscriptionEntryTable.TABLE_NAME,
				"deliverySubTypeSettings")) {

			addColumn(
				CommerceSubscriptionEntryTable.class,
				CommerceSubscriptionEntryTable.TABLE_NAME,
				"deliverySubTypeSettings", "TEXT");
		}

		if (!hasColumn(
				CommerceSubscriptionEntryTable.TABLE_NAME,
				"deliveryCurrentCycle")) {

			addColumn(
				CommerceSubscriptionEntryTable.class,
				CommerceSubscriptionEntryTable.TABLE_NAME,
				"deliveryCurrentCycle", "LONG");
		}

		if (!hasColumn(
				CommerceSubscriptionEntryTable.TABLE_NAME,
				"deliveryMaxSubscriptionCycles")) {

			addColumn(
				CommerceSubscriptionEntryTable.class,
				CommerceSubscriptionEntryTable.TABLE_NAME,
				"deliveryMaxSubscriptionCycles", "LONG");
		}

		if (!hasColumn(
				CommerceSubscriptionEntryTable.TABLE_NAME,
				"deliverySubscriptionStatus")) {

			addColumn(
				CommerceSubscriptionEntryTable.class,
				CommerceSubscriptionEntryTable.TABLE_NAME,
				"deliverySubscriptionStatus", "INTEGER");
		}

		if (!hasColumn(
				CommerceSubscriptionEntryTable.TABLE_NAME,
				"deliveryLastIterationDate")) {

			addColumn(
				CommerceSubscriptionEntryTable.class,
				CommerceSubscriptionEntryTable.TABLE_NAME,
				"deliveryLastIterationDate", "DATE");
		}

		if (!hasColumn(
				CommerceSubscriptionEntryTable.TABLE_NAME,
				"deliveryNextIterationDate")) {

			addColumn(
				CommerceSubscriptionEntryTable.class,
				CommerceSubscriptionEntryTable.TABLE_NAME,
				"deliveryNextIterationDate", "DATE");
		}

		if (!hasColumn(
				CommerceSubscriptionEntryTable.TABLE_NAME,
				"deliveryStartDate")) {

			addColumn(
				CommerceSubscriptionEntryTable.class,
				CommerceSubscriptionEntryTable.TABLE_NAME, "deliveryStartDate",
				"DATE");
		}
	}

}