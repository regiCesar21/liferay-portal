/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.constants;

/**
 * @author Pei-Jung Lan
 */
public class AccountConstants {

	public static final long ACCOUNT_ENTRY_ID_ANY = -1;

	public static final long ACCOUNT_ENTRY_ID_DEFAULT = 0;

	public static final String ACCOUNT_ENTRY_TYPE_BUSINESS = "business";

	public static final String ACCOUNT_ENTRY_TYPE_PERSON = "person";

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #ACCOUNT_ENTRY_TYPE_PERSON}
	 */
	@Deprecated
	public static final String ACCOUNT_ENTRY_TYPE_PERSONAL = "person";

	public static final String[] ACCOUNT_ENTRY_TYPES = {
		ACCOUNT_ENTRY_TYPE_BUSINESS, ACCOUNT_ENTRY_TYPE_PERSON
	};

	public static final String RESOURCE_NAME = "com.liferay.account";

}