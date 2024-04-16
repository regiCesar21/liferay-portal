/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.koroneiki.constants;

/**
 * @author Amos Fong
 */
public class ContactRoleConstants {

	public static final String NAME_ADVOCACY_SPECIALIST = "Advocacy Specialist";

	public static final String NAME_CUSTOMER_SUCCESS = "Customer Success";

	public static final String NAME_MEMBER = "Member";

	public static final String NAME_PARTNER_MANAGER = "Partner Manager";

	public static final String NAME_PARTNER_MARKETING_USER =
		"Partner Marketing User";

	public static final String NAME_PARTNER_MEMBER = "Partner Member";

	public static final String NAME_PARTNER_SALES_USER = "Partner Sales User";

	public static final String NAME_PARTNER_TECHNICAL_USER =
		"Partner Technical User";

	public static final String NAME_SALES = "Sales";

	public static final String NAME_SUPPORT_ADMINISTRATOR =
		"Support Administrator";

	public static final String NAME_SUPPORT_CLOSED_WATCHER =
		"Support Closed Watcher";

	public static final String NAME_SUPPORT_REQUESTER = "Support Requester";

	public static final String NAME_SUPPORT_USER = "Support User";

	public static final String[] PARTNER_CONTACT_ROLES = {
		NAME_PARTNER_MANAGER, NAME_PARTNER_MARKETING_USER, NAME_PARTNER_MEMBER,
		NAME_PARTNER_SALES_USER, NAME_PARTNER_TECHNICAL_USER
	};

	public static final String[] SUPPORT_CONTACT_ROLES = {
		NAME_SUPPORT_ADMINISTRATOR, NAME_SUPPORT_REQUESTER, NAME_SUPPORT_USER,
		NAME_SUPPORT_CLOSED_WATCHER
	};

	public static final String[] SUPPORT_TICKET_ROLES = {
		NAME_SUPPORT_ADMINISTRATOR, NAME_SUPPORT_REQUESTER
	};

}