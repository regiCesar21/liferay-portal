/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.web.internal.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.redirect.model.RedirectEntry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = {})
public class RedirectEntryPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, RedirectEntry redirectEntry,
			String actionId)
		throws PortalException {

		return _redirectEntryModelResourcePermission.contains(
			permissionChecker, redirectEntry, actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.redirect.model.RedirectEntry)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<RedirectEntry> modelResourcePermission) {

		_redirectEntryModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<RedirectEntry>
		_redirectEntryModelResourcePermission;

}