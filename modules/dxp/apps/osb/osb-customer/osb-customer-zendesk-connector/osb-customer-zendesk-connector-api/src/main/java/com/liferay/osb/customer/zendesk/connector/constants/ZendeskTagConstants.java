/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.zendesk.connector.constants;

/**
 * @author Kyle Bischof
 */
public class ZendeskTagConstants {

	public static final String GS_OPPORTUNITY = "gs_opportunity";

	public static final String OSB_CUSTOMER = "osb_customer";

	public static final String OSB_KNOWLEDGE_BASE = "osb_kb";

	public static final String OSB_LIFERAY_EMPLOYEE = "osb_lr_employee";

	public static final String OSB_PARTNER = "osb_partner";

	public static final String PREFIX_OSB = "osb_";

	public static final String PREMIUM_SERVICE = "premium_service";

	public static final String WATCHER = "_watcher";

	public static String getWatcherTag(long externalId) {
		return PREFIX_OSB + String.valueOf(externalId) + WATCHER;
	}

}