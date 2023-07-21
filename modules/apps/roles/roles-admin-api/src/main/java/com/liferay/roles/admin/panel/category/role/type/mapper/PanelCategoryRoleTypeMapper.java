/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.roles.admin.panel.category.role.type.mapper;

/**
 * Defines a mapping between role types and a panel category. When implemented,
 * the returned role types will be able to define permissions for apps under the
 * returned panel category. This will be visible in the Define Permissions view
 * in the Roles Admin portlet.
 *
 * @author Drew Brokke
 * @review
 */
public interface PanelCategoryRoleTypeMapper {

	public String getPanelCategoryKey();

	public int[] getRoleTypes();

}