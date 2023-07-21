/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.display.context;

import com.liferay.document.library.kernel.versioning.VersioningStrategy;
import com.liferay.document.library.web.internal.display.context.util.DLRequestHelper;
import com.liferay.document.library.web.internal.helper.DLTrashHelper;
import com.liferay.trash.TrashHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Adolfo Pérez
 */
@Component(service = DLAdminDisplayContextProvider.class)
public class DLAdminDisplayContextProvider {

	public DLAdminDisplayContext getDLAdminDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		DLRequestHelper dlRequestHelper = new DLRequestHelper(
			httpServletRequest);

		return new DLAdminDisplayContext(
			httpServletRequest, dlRequestHelper.getLiferayPortletRequest(),
			dlRequestHelper.getLiferayPortletResponse(), _versioningStrategy,
			_trashHelper);
	}

	public DLAdminManagementToolbarDisplayContext
		getDLAdminManagementToolbarDisplayContext(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			DLAdminDisplayContext dlAdminDisplayContext) {

		DLRequestHelper dlRequestHelper = new DLRequestHelper(
			httpServletRequest);

		return new DLAdminManagementToolbarDisplayContext(
			httpServletRequest, dlRequestHelper.getLiferayPortletRequest(),
			dlRequestHelper.getLiferayPortletResponse(), dlAdminDisplayContext,
			_dlTrashHelper);
	}

	@Reference
	private DLTrashHelper _dlTrashHelper;

	@Reference
	private TrashHelper _trashHelper;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile VersioningStrategy _versioningStrategy;

}