/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.service.impl;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.instances.service.base.PortalInstancesLocalServiceBaseImpl;
import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalInstances;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

import java.sql.SQLException;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @see    PortalInstancesLocalServiceBaseImpl
 * @see    com.liferay.portal.instances.service.PortalInstancesLocalServiceUtil
 */
@Component(
	property = "model.class.name=com.liferay.portal.util.PortalInstances",
	service = AopService.class
)
public class PortalInstancesLocalServiceImpl
	extends PortalInstancesLocalServiceBaseImpl {

	@Override
	public void addCompanyId(long companyId) {
		PortalInstances.addCompanyId(companyId);
	}

	@Override
	public long getCompanyId(HttpServletRequest httpServletRequest) {
		return PortalInstances.getCompanyId(httpServletRequest);
	}

	@Override
	public long[] getCompanyIds() {
		return PortalInstances.getCompanyIds();
	}

	@Override
	public long[] getCompanyIdsBySQL() throws SQLException {
		return PortalInstances.getCompanyIdsBySQL();
	}

	@Override
	public long getDefaultCompanyId() {
		return PortalInstances.getDefaultCompanyId();
	}

	@Override
	public String[] getWebIds() {
		return PortalInstances.getWebIds();
	}

	@Override
	public void initializePortalInstance(
			long companyId, String siteInitializerKey,
			ServletContext servletContext)
		throws PortalException {

		Company company = _companyLocalService.getCompany(companyId);

		PortalInstances.initCompany(servletContext, company.getWebId());

		if (Validator.isNull(siteInitializerKey)) {
			return;
		}

		SiteInitializer siteInitializer =
			_siteInitializerRegistry.getSiteInitializer(siteInitializerKey);

		if (siteInitializer == null) {
			throw new PortalException(
				"Invalid site initializer key " + siteInitializerKey);
		}

		Role role = _roleLocalService.fetchRole(
			companyId, RoleConstants.ADMINISTRATOR);

		List<User> users = _userLocalService.getRoleUsers(role.getRoleId());

		User user = users.get(0);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			_permissionCheckerFactory.create(user));

		String name = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(user.getUserId());

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setInitializingPortalInstance(true)) {

			Group group = _groupLocalService.getGroup(
				company.getCompanyId(), GroupConstants.GUEST);

			siteInitializer.initialize(group.getGroupId());
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
			PrincipalThreadLocal.setName(name);
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #initializePortalInstance(long, String, ServletContext)}
	 */
	@Deprecated
	@Override
	public void initializePortalInstance(
		ServletContext servletContext, String webId) {

		PortalInstances.initCompany(servletContext, webId);
	}

	@Override
	public boolean isAutoLoginIgnoreHost(String host) {
		return PortalInstances.isAutoLoginIgnoreHost(host);
	}

	@Override
	public boolean isAutoLoginIgnorePath(String path) {
		return PortalInstances.isAutoLoginIgnorePath(path);
	}

	@Override
	public boolean isCompanyActive(long companyId) {
		return PortalInstances.isCompanyActive(companyId);
	}

	@Override
	public boolean isVirtualHostsIgnoreHost(String host) {
		return PortalInstances.isVirtualHostsIgnoreHost(host);
	}

	@Override
	public boolean isVirtualHostsIgnorePath(String path) {
		return PortalInstances.isVirtualHostsIgnorePath(path);
	}

	@Override
	public void reload(ServletContext servletContext) {
		PortalInstances.reload(servletContext);
	}

	@Override
	public void removeCompany(long companyId) {
		PortalInstances.removeCompany(companyId);
	}

	@Clusterable
	@Override
	public void synchronizePortalInstances() {
		try {
			long[] initializedCompanyIds = _portal.getCompanyIds();

			List<Long> removeableCompanyIds = ListUtil.fromArray(
				initializedCompanyIds);

			List<Company> companies = _companyLocalService.getCompanies();

			for (Company company : companies) {
				long companyId = company.getCompanyId();

				removeableCompanyIds.remove(companyId);

				if (ArrayUtil.contains(initializedCompanyIds, companyId)) {
					continue;
				}

				ServletContext portalContext = ServletContextPool.get(
					_portal.getPathContext());

				PortalInstances.initCompany(portalContext, company.getWebId());
			}

			for (long companyId : removeableCompanyIds) {
				PortalInstances.removeCompany(companyId);
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalInstancesLocalServiceImpl.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CompanyService _companyService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private Portal _portal;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private SiteInitializerRegistry _siteInitializerRegistry;

	@Reference
	private UserLocalService _userLocalService;

}