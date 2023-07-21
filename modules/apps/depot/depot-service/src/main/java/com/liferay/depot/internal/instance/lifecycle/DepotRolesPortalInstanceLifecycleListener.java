/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.internal.instance.lifecycle;

import com.liferay.depot.constants.DepotRolesConstants;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.NoSuchRoleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class DepotRolesPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company)
		throws PortalException {

		for (String name : _DEPOT_ROLE_NAMES) {
			Role role = _getOrCreateRole(
				company.getCompanyId(), name, _getDescriptionMap(name));

			_resourceLocalService.addResources(
				company.getCompanyId(), 0, 0, Role.class.getName(),
				role.getRoleId(), false, false, false);
		}
	}

	private String _getDescription(Locale locale, String name) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		if (Objects.equals(
				DepotRolesConstants.ASSET_LIBRARY_ADMINISTRATOR, name)) {

			return ResourceBundleUtil.getString(
				resourceBundle,
				"asset-library-administrators-are-super-users-of-their-asset-" +
					"library-but-cannot-make-other-users-into-asset-library-" +
						"administrators");
		}
		else if (Objects.equals(
					DepotRolesConstants.ASSET_LIBRARY_MEMBER, name)) {

			return ResourceBundleUtil.getString(
				resourceBundle,
				"all-users-who-belong-to-an-asset-library-have-this-role-" +
					"within-that-asset-library");
		}
		else if (Objects.equals(
					DepotRolesConstants.ASSET_LIBRARY_OWNER, name)) {

			return ResourceBundleUtil.getString(
				resourceBundle,
				"asset-library-owners-are-super-users-of-their-asset-library-" +
					"and-can-assign-asset-library-roles-to-users");
		}

		return null;
	}

	private Map<Locale, String> _getDescriptionMap(String name) {
		Map<Locale, String> descriptionMap = new HashMap<>();

		for (Locale locale : _language.getAvailableLocales()) {
			String description = _getDescription(locale, name);

			if (description != null) {
				descriptionMap.put(locale, description);
			}
		}

		return descriptionMap;
	}

	private Role _getOrCreateRole(
			long companyId, String name, Map<Locale, String> descriptionMap)
		throws PortalException {

		try {
			Role role = _roleLocalService.getRole(companyId, name);

			if (!Objects.equals(descriptionMap, role.getDescriptionMap())) {
				role.setDescriptionMap(descriptionMap);

				return _roleLocalService.updateRole(role);
			}

			return role;
		}
		catch (NoSuchRoleException noSuchRoleException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchRoleException, noSuchRoleException);
			}

			boolean addResource = PermissionThreadLocal.isAddResource();

			try {
				PermissionThreadLocal.setAddResource(false);

				User user = _userLocalService.getDefaultUser(companyId);

				return _roleLocalService.addRole(
					user.getUserId(), null, 0, name, null, descriptionMap,
					RoleConstants.TYPE_DEPOT, null, null);
			}
			finally {
				PermissionThreadLocal.setAddResource(addResource);
			}
		}
	}

	private static final String[] _DEPOT_ROLE_NAMES = {
		DepotRolesConstants.ASSET_LIBRARY_ADMINISTRATOR,
		DepotRolesConstants.ASSET_LIBRARY_CONNECTED_SITE_MEMBER,
		DepotRolesConstants.ASSET_LIBRARY_CONTENT_REVIEWER,
		DepotRolesConstants.ASSET_LIBRARY_MEMBER,
		DepotRolesConstants.ASSET_LIBRARY_OWNER
	};

	private static final Log _log = LogFactoryUtil.getLog(
		DepotRolesPortalInstanceLifecycleListener.class);

	@Reference
	private Language _language;

	@Reference(target = "(bundle.symbolic.name=com.liferay.depot.service)")
	private ResourceBundleLoader _resourceBundleLoader;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}