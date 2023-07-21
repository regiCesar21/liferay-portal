/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.web.internal.security.permisison.resource;

import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = {})
public class DispatchTriggerPermission {

	public static boolean contains(
			PermissionChecker permissionChecker,
			DispatchTrigger dispatchTrigger, String actionId)
		throws PortalException {

		return _dispatchTriggerModelResourcePermission.contains(
			permissionChecker, dispatchTrigger.getDispatchTriggerId(),
			actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long dispatchTriggerId,
			String actionId)
		throws PortalException {

		return _dispatchTriggerModelResourcePermission.contains(
			permissionChecker, dispatchTriggerId, actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.dispatch.model.DispatchTrigger)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<DispatchTrigger> modelResourcePermission) {

		_dispatchTriggerModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<DispatchTrigger>
		_dispatchTriggerModelResourcePermission;

}