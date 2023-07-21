/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.designer.web.internal.messaging;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ModelListener.class)
public class KaleoDefinitionVersionModelListener
	extends BaseModelListener<KaleoDefinitionVersion> {

	@Override
	public void onAfterCreate(KaleoDefinitionVersion kaleoDefinitionVersion)
		throws ModelListenerException {

		try {
			_resourceLocalService.addModelResources(
				kaleoDefinitionVersion, getServiceContext());
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Override
	public void onAfterRemove(KaleoDefinitionVersion kaleoDefinitionVersion)
		throws ModelListenerException {

		try {
			_resourceLocalService.deleteResource(
				kaleoDefinitionVersion, ResourceConstants.SCOPE_COMPANY);
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	protected ServiceContext getServiceContext() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);
		}

		return serviceContext;
	}

	@Reference
	private ResourceLocalService _resourceLocalService;

}