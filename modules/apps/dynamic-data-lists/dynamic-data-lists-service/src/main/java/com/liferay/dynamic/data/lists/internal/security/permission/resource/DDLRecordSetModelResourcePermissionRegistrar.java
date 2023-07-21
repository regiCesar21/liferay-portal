/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.internal.security.permission.resource;

import com.liferay.dynamic.data.lists.constants.DDLConstants;
import com.liferay.dynamic.data.lists.constants.DDLPortletKeys;
import com.liferay.dynamic.data.lists.constants.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class DDLRecordSetModelResourcePermissionRegistrar {

	@Activate
	protected void activate(BundleContext bundleContext) {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("model.class.name", DDLRecordSet.class.getName());

		_serviceRegistration = bundleContext.registerService(
			(Class<ModelResourcePermission<DDLRecordSet>>)
				(Class<?>)ModelResourcePermission.class,
			ModelResourcePermissionFactory.create(
				DDLRecordSet.class, DDLRecordSet::getRecordSetId,
				_ddlRecordSetLocalService::getDDLRecordSet,
				_portletResourcePermission,
				(modelResourcePermission, consumer) -> consumer.accept(
					(permissionChecker, name, recordSet, actionId) -> {
						if (recordSet.getScope() !=
								DDLRecordSetConstants.
									SCOPE_DYNAMIC_DATA_LISTS) {

							return null;
						}

						return _stagingPermission.hasPermission(
							permissionChecker, recordSet.getGroupId(), name,
							recordSet.getRecordSetId(),
							DDLPortletKeys.DYNAMIC_DATA_LISTS, actionId);
					})),
			properties);
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference(target = "(resource.name=" + DDLConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

	private ServiceRegistration<ModelResourcePermission<DDLRecordSet>>
		_serviceRegistration;

	@Reference
	private StagingPermission _stagingPermission;

}