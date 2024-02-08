/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.portal.instances.internal.resource.v1_0;

import com.liferay.headless.portal.instances.dto.v1_0.PortalInstance;
import com.liferay.headless.portal.instances.resource.v1_0.PortalInstanceResource;
import com.liferay.portal.instances.service.PortalInstancesLocalService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alberto Chaparro
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/portal-instance.properties",
	scope = ServiceScope.PROTOTYPE, service = PortalInstanceResource.class
)
public class PortalInstanceResourceImpl extends BasePortalInstanceResourceImpl {

	@Override
	public void deletePortalInstance(String portalInstanceId) throws Exception {
		Company company = _companyLocalService.getCompanyByWebId(
			portalInstanceId);

		_companyLocalService.deleteCompany(company.getCompanyId());

		_portalInstancesLocalService.synchronizePortalInstances();
	}

	@Override
	public PortalInstance getPortalInstance(String portalInstanceId)
		throws Exception {

		return _toPortalInstance(
			_companyLocalService.getCompanyByWebId(portalInstanceId));
	}

	@Override
	public Page<PortalInstance> getPortalInstancesPage(Boolean skipDefault)
		throws Exception {

		skipDefault = GetterUtil.getBoolean(skipDefault);

		List<PortalInstance> portalInstances = new ArrayList<>();

		for (Company company : _companyLocalService.getCompanies(false)) {
			if (skipDefault &&
				(_portalInstancesLocalService.getDefaultCompanyId() ==
					company.getCompanyId())) {

				continue;
			}

			portalInstances.add(_toPortalInstance(company));
		}

		return Page.of(portalInstances);
	}

	@Override
	public PortalInstance patchPortalInstance(
			String portalInstanceId, PortalInstance portalInstance)
		throws Exception {

		Company company = _companyLocalService.getCompanyByWebId(
			portalInstanceId);

		String virtualHostname = GetterUtil.getString(
			portalInstance.getVirtualHost(), company.getVirtualHostname());
		String domain = GetterUtil.getString(
			portalInstance.getDomain(), company.getMx());

		return _toPortalInstance(
			_companyLocalService.updateCompany(
				company.getCompanyId(), virtualHostname, domain,
				company.getMaxUsers(), company.isActive()));
	}

	@Override
	public PortalInstance postPortalInstance(
			String initializerKey, PortalInstance portalInstance)
		throws Exception {

		Company company = _companyLocalService.addCompany(
			portalInstance.getCompanyId(), portalInstance.getPortalInstanceId(),
			portalInstance.getVirtualHost(), portalInstance.getDomain(), false,
			0, true, null, null, null, null, null, null);

		_portalInstancesLocalService.initializePortalInstance(
			company.getCompanyId(),
			portalInstance.getPortalInstanceInitializerKey(), _servletContext);

		_portalInstancesLocalService.synchronizePortalInstances();

		return _toPortalInstance(company);
	}

	@Override
	public void putPortalInstanceActivate(String portalInstanceId)
		throws Exception {

		Company company = _companyLocalService.getCompanyByWebId(
			portalInstanceId);

		_companyLocalService.updateCompany(
			company.getCompanyId(), company.getVirtualHostname(),
			company.getMx(), company.getMaxUsers(), true);
	}

	@Override
	public void putPortalInstanceDeactivate(String portalInstanceId)
		throws Exception {

		Company company = _companyLocalService.getCompanyByWebId(
			portalInstanceId);

		_companyLocalService.updateCompany(
			company.getCompanyId(), company.getVirtualHostname(),
			company.getMx(), company.getMaxUsers(), false);
	}

	private PortalInstance _toPortalInstance(Company company) {
		return new PortalInstance() {
			{
				active = company.isActive();
				companyId = company.getCompanyId();
				domain = company.getMx();
				portalInstanceId = company.getWebId();
				virtualHost = company.getVirtualHostname();
			}
		};
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private PortalInstancesLocalService _portalInstancesLocalService;

	@Reference(
		target = "(&(original.bean=true)(bean.id=javax.servlet.ServletContext))"
	)
	private ServletContext _servletContext;

}