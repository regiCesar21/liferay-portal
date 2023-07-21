/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.security.permission.resource;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class CTCollectionPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, CTCollection ctCollection,
			String actionId)
		throws PortalException {

		return _ctCollectionModelResourcePermission.contains(
			permissionChecker, ctCollection, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long ctCollectionId,
			String actionId)
		throws PortalException {

		return _ctCollectionModelResourcePermission.contains(
			permissionChecker, ctCollectionId, actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.change.tracking.model.CTCollection)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<CTCollection> modelResourcePermission) {

		_ctCollectionModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<CTCollection>
		_ctCollectionModelResourcePermission;

}