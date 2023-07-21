/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.security.permission.resource;

import com.liferay.layout.security.permission.resource.LayoutContentModelResourcePermission;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = {})
public class ModelResourcePermissionUtil {

	public static boolean contains(
		PermissionChecker permissionChecker, long plid, String actionId) {

		return _modelResourcePermission.contains(
			permissionChecker, plid, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, String className, long classPK,
		String actionId) {

		return _modelResourcePermission.contains(
			permissionChecker, className, classPK, actionId);
	}

	@Reference(unbind = "-")
	protected void setsModelResourcePermission(
		LayoutContentModelResourcePermission modelResourcePermission) {

		_modelResourcePermission = modelResourcePermission;
	}

	private static LayoutContentModelResourcePermission
		_modelResourcePermission;

}