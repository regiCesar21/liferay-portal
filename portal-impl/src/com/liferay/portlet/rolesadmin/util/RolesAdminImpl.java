/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.rolesadmin.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.roles.admin.kernel.util.RolesAdmin;

/**
 * @author Brian Wing Shun Chan
 */
public class RolesAdminImpl implements RolesAdmin {

	@Override
	public String getIconCssClass(Role role) {
		String iconCssClass = StringPool.BLANK;

		String roleName = role.getName();
		int roleType = role.getType();

		if (roleName.equals(RoleConstants.GUEST)) {
			iconCssClass = "user";
		}
		else if (roleType == RoleConstants.TYPE_ORGANIZATION) {
			iconCssClass = "globe";
		}
		else if (roleType == RoleConstants.TYPE_REGULAR) {
			iconCssClass = "user";
		}
		else if (roleType == RoleConstants.TYPE_SITE) {
			iconCssClass = "globe";
		}
		else if (role.isTeam()) {
			iconCssClass = "community";
		}

		return iconCssClass;
	}

}