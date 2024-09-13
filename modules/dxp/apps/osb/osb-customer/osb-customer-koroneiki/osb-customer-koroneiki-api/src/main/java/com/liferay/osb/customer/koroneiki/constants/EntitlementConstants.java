/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.koroneiki.constants;

/**
 * @author Amos Fong
 */
public class EntitlementConstants {

	public static final String NAME_CUSTOMER = "Customer";

	public static final String NAME_CUSTOMER_LIFERAY_PAAS =
		"Customer - Liferay PaaS";

	public static final String NAME_GOLD_SUBSCRIPTION = "Gold Subscription";

	public static final String NAME_LIFERAY_EMPLOYEE = "Liferay Employee";

	public static final String NAME_LIMITED_SUBSCRIPTION =
		"Limited Subscription";

	public static final String NAME_PARTNER = "Partner";

	public static final String NAME_PLATINUM_SUBSCRIPTION =
		"Platinum Subscription";

	public static final String NAME_PREMIUM_SUBSCRIPTION =
		"Premium Subscription";

	public static final String ORGANIZATION_NAME_PREFIX = "Koroneiki ";

	public static final String[] SUPPORT_ENTITLEMENTS = {
		NAME_GOLD_SUBSCRIPTION, NAME_LIMITED_SUBSCRIPTION,
		NAME_PLATINUM_SUBSCRIPTION, NAME_PREMIUM_SUBSCRIPTION
	};

	public static final String[] TICKET_SUPPORT_ENTITLEMENTS = {
		NAME_GOLD_SUBSCRIPTION, NAME_PLATINUM_SUBSCRIPTION,
		NAME_PREMIUM_SUBSCRIPTION
	};

}