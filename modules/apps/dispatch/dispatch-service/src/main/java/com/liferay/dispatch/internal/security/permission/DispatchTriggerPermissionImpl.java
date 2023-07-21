/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.internal.security.permission;

import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.security.permission.DispatchTriggerPermission;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
@Component(service = DispatchTriggerPermission.class)
@Deprecated
public class DispatchTriggerPermissionImpl
	implements DispatchTriggerPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			DispatchTrigger dispatchTrigger, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, dispatchTrigger, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, DispatchTrigger.class.getName(),
				dispatchTrigger.getDispatchTriggerId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long dispatchTriggerId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, dispatchTriggerId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, DispatchTrigger.class.getName(),
				dispatchTriggerId, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			DispatchTrigger dispatchTrigger, String actionId)
		throws PortalException {

		if (contains(
				permissionChecker, dispatchTrigger.getDispatchTriggerId(),
				actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long dispatchTriggerId,
			String actionId)
		throws PortalException {

		DispatchTrigger dispatchTrigger =
			_dispatchTriggerLocalService.fetchDispatchTrigger(
				dispatchTriggerId);

		if (dispatchTrigger == null) {
			return false;
		}

		return _contains(permissionChecker, dispatchTrigger, actionId);
	}

	private boolean _contains(
			PermissionChecker permissionChecker,
			DispatchTrigger dispatchTrigger, String actionId)
		throws PortalException {

		if (permissionChecker.hasOwnerPermission(
				dispatchTrigger.getCompanyId(), DispatchTrigger.class.getName(),
				dispatchTrigger.getDispatchTriggerId(),
				dispatchTrigger.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			0, DispatchTrigger.class.getName(),
			dispatchTrigger.getDispatchTriggerId(), actionId);
	}

	@Reference
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

}