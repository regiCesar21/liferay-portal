/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.web.internal.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.search.experiences.model.SXPBlueprint;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = SXPBlueprintPermission.class)
public class SXPBlueprintPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, long entryId, String actionKey)
		throws PortalException {

		return _sxpBlueprintModelResourcePermission.contains(
			permissionChecker, entryId, actionKey);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, SXPBlueprint entry,
			String actionKey)
		throws PortalException {

		return _sxpBlueprintModelResourcePermission.contains(
			permissionChecker, entry, actionKey);
	}

	@Reference(
		target = "(model.class.name=com.liferay.search.experiences.model.SXPBlueprint)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<SXPBlueprint> modelResourcePermission) {

		_sxpBlueprintModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<SXPBlueprint>
		_sxpBlueprintModelResourcePermission;

}