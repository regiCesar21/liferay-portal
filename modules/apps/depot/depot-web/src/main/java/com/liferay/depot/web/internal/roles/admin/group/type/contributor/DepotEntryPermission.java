/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.web.internal.roles.admin.group.type.contributor;

import com.liferay.depot.model.DepotEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = {})
public class DepotEntryPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, long depotEntryId,
			String actionId)
		throws PortalException {

		return _depotEntryModelResourcePermission.contains(
			permissionChecker, depotEntryId, actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.depot.model.DepotEntry)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<DepotEntry> modelResourcePermission) {

		_depotEntryModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<DepotEntry>
		_depotEntryModelResourcePermission;

}