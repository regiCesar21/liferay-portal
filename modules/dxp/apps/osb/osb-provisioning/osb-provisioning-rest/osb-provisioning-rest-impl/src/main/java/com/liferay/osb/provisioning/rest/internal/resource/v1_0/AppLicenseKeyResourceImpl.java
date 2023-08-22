/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.provisioning.rest.internal.resource.v1_0;

import com.liferay.osb.provisioning.license.helper.constants.ProductId;
import com.liferay.osb.provisioning.license.model.LicenseKey;
import com.liferay.osb.provisioning.license.service.LicenseKeyLocalService;
import com.liferay.osb.provisioning.rest.dto.v1_0.AppLicenseKey;
import com.liferay.osb.provisioning.rest.dto.v1_0.util.AppLicenseKeyUtil;
import com.liferay.osb.provisioning.rest.internal.odata.entity.v1_0.AppLicenseKeyEntityModel;
import com.liferay.osb.provisioning.rest.resource.v1_0.AppLicenseKeyResource;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Amos Fong
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/app-license-key.properties",
	scope = ServiceScope.PROTOTYPE, service = AppLicenseKeyResource.class
)
public class AppLicenseKeyResourceImpl
	extends BaseAppLicenseKeyResourceImpl implements EntityModelResource {

	@Override
	public Page<AppLicenseKey> getAppLicenseKeysPage(
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		_checkPermission();

		return SearchUtil.search(
			booleanQuery -> booleanQuery.addTerm(
				"productId", ProductId.PORTAL, false,
				BooleanClauseOccur.MUST_NOT),
			filter, LicenseKey.class, search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> searchContext.setCompanyId(
				contextCompany.getCompanyId()),
			document -> AppLicenseKeyUtil.toAppLicenseKey(
				_licenseKeyLocalService.getLicenseKey(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			sorts);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	private boolean _checkPermission() {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker.isOmniadmin()) {
			return true;
		}

		return false;
	}

	private static final EntityModel _entityModel =
		new AppLicenseKeyEntityModel();

	@Reference
	private LicenseKeyLocalService _licenseKeyLocalService;

}