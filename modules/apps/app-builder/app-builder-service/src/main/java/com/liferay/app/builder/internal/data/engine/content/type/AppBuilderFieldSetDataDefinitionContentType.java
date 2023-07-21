/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.internal.data.engine.content.type;

import com.liferay.app.builder.constants.AppBuilderConstants;
import com.liferay.data.engine.content.type.DataDefinitionContentType;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.Portal;

import java.io.Serializable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	immediate = true, property = "content.type=app-builder-fieldset",
	service = DataDefinitionContentType.class
)
public class AppBuilderFieldSetDataDefinitionContentType
	implements DataDefinitionContentType {

	@Override
	public long getClassNameId() {
		return _portal.getClassNameId(AppBuilderFieldSet.class);
	}

	@Override
	public String getContentType() {
		return "app-builder-fieldset";
	}

	@Override
	public String getPortletResourceName() {
		return AppBuilderConstants.RESOURCE_NAME;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long companyId, long groupId,
			String resourceName, long primKey, long userId, String actionId)
		throws PortalException {

		if (_portletResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(), groupId,
				ActionKeys.MANAGE)) {

			return true;
		}

		return DataDefinitionContentType.super.hasPermission(
			permissionChecker, companyId, groupId, resourceName, primKey,
			userId, actionId);
	}

	@Override
	public boolean hasPortletPermission(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException {

		if (_portletResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(), groupId,
				ActionKeys.MANAGE)) {

			return true;
		}

		return _portletResourcePermission.contains(
			permissionChecker, groupId, actionId);
	}

	public class AppBuilderFieldSet implements ClassedModel, Serializable {

		@Override
		public ExpandoBridge getExpandoBridge() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Class<?> getModelClass() {
			return AppBuilderFieldSet.class;
		}

		@Override
		public String getModelClassName() {
			return AppBuilderFieldSet.class.getName();
		}

		@Override
		public Serializable getPrimaryKeyObj() {
			return _appBuilderFieldSetId;
		}

		@Override
		public void setPrimaryKeyObj(Serializable primaryKeyObj) {
			_appBuilderFieldSetId = (long)primaryKeyObj;
		}

		private long _appBuilderFieldSetId;

	}

	@Reference
	private Portal _portal;

	@Reference(
		target = "(resource.name=" + AppBuilderConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}