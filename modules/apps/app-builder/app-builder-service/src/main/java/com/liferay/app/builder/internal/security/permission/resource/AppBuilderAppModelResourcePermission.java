/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.internal.security.permission.resource;

import com.liferay.app.builder.constants.AppBuilderConstants;
import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.app.builder.model.AppBuilderApp",
	service = ModelResourcePermission.class
)
public class AppBuilderAppModelResourcePermission
	implements ModelResourcePermission<AppBuilderApp> {

	@Override
	public void check(
			PermissionChecker permissionChecker, AppBuilderApp appBuilderApp,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, appBuilderApp, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, AppBuilderApp.class.getName(),
				appBuilderApp.getPrimaryKey(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long appBuilderAppId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, appBuilderAppId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, AppBuilderApp.class.getName(),
				appBuilderAppId, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, AppBuilderApp appBuilderApp,
			String actionId)
		throws PortalException {

		if (_portletResourcePermission.contains(
				permissionChecker, appBuilderApp.getGroupId(),
				ActionKeys.MANAGE)) {

			return true;
		}

		return permissionChecker.hasPermission(
			null, AppBuilderApp.class.getName(), appBuilderApp.getPrimaryKey(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long appBuilderAppId,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			_appBuilderAppLocalService.getAppBuilderApp(appBuilderAppId),
			actionId);
	}

	@Override
	public String getModelName() {
		return AppBuilderApp.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	@Reference(
		target = "(resource.name=" + AppBuilderConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}