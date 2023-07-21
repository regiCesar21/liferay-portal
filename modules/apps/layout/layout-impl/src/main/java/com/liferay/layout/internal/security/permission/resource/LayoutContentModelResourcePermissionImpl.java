/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.security.permission.resource;

import com.liferay.layout.model.LayoutClassedModelUsage;
import com.liferay.layout.security.permission.resource.LayoutContentModelResourcePermission;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	immediate = true, service = LayoutContentModelResourcePermission.class
)
public class LayoutContentModelResourcePermissionImpl
	implements LayoutContentModelResourcePermission {

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long plid, String actionId) {

		List<LayoutClassedModelUsage> layoutClassedModelUsages =
			_layoutClassedModelUsageLocalService.
				getLayoutClassedModelUsagesByPlid(plid);

		for (LayoutClassedModelUsage layoutClassedModelUsage :
				layoutClassedModelUsages) {

			if (contains(
					permissionChecker, layoutClassedModelUsage.getClassName(),
					layoutClassedModelUsage.getClassPK(), actionId)) {

				return true;
			}
		}

		return false;
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, String className, long classPK,
		String actionId) {

		ModelResourcePermission<?> modelResourcePermission =
			_modelResourcePermissionServiceTrackerMap.getService(className);

		if (modelResourcePermission == null) {
			return false;
		}

		try {
			if (modelResourcePermission.contains(
					permissionChecker, classPK, actionId)) {

				return true;
			}
		}
		catch (PortalException portalException) {
			_log.error(
				"An error occurred while checking permissions",
				portalException);
		}

		return false;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_modelResourcePermissionServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext,
				(Class<ModelResourcePermission<?>>)
					(Class<?>)ModelResourcePermission.class,
				"model.class.name");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutContentModelResourcePermissionImpl.class);

	private static ServiceTrackerMap<String, ModelResourcePermission<?>>
		_modelResourcePermissionServiceTrackerMap;

	@Reference
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

}