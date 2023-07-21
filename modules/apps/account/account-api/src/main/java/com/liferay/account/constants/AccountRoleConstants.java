/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.constants;

import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Objects;

/**
 * @author Drew Brokke
 */
public class AccountRoleConstants {

	public static final String REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR =
		"Account Administrator";

	public static final String REQUIRED_ROLE_NAME_ACCOUNT_MANAGER =
		"Account Manager";

	public static final String REQUIRED_ROLE_NAME_ACCOUNT_MEMBER =
		"Account Member";

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #REQUIRED_ROLE_NAME_ACCOUNT_MANAGER}
	 */
	@Deprecated
	public static final String REQUIRED_ROLE_NAME_ACCOUNT_OWNER =
		"Account Owner";

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR}
	 */
	@Deprecated
	public static final String REQUIRED_ROLE_NAME_ACCOUNT_POWER_USER =
		"Account Power User";

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #REQUIRED_ROLE_NAME_ACCOUNT_MEMBER}
	 */
	@Deprecated
	public static final String REQUIRED_ROLE_NAME_ACCOUNT_USER = "Account User";

	public static final String[] REQUIRED_ROLE_NAMES = {
		REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR,
		REQUIRED_ROLE_NAME_ACCOUNT_MANAGER, REQUIRED_ROLE_NAME_ACCOUNT_MEMBER
	};

	public static boolean isImpliedRole(Role role) {
		if (Objects.equals(REQUIRED_ROLE_NAME_ACCOUNT_MEMBER, role.getName())) {
			return true;
		}

		return false;
	}

	public static boolean isRequiredRole(Role role) {
		return ArrayUtil.contains(REQUIRED_ROLE_NAMES, role.getName());
	}

	public static boolean isSharedRole(Role role) {
		if (role.getType() == RoleConstants.TYPE_ACCOUNT) {
			return true;
		}

		return false;
	}

}