/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.search.experiences.internal.model.listener.CompanyModelListener;
import com.liferay.search.experiences.service.SXPElementLocalService;

/**
 * @author Shuyang Zhou
 */
public class SXPElementUpgradeProcess extends UpgradeProcess {

	public SXPElementUpgradeProcess(
		CompanyLocalService companyLocalService,
		CompanyModelListener companyModelListener,
		SXPElementLocalService sxpElementLocalService) {

		_companyLocalService = companyLocalService;
		_companyModelListener = companyModelListener;
		_sxpElementLocalService = sxpElementLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompany(
			company -> _companyModelListener.addSXPElements(
				company, _sxpElementLocalService));
	}

	private final CompanyLocalService _companyLocalService;
	private final CompanyModelListener _companyModelListener;
	private final SXPElementLocalService _sxpElementLocalService;

}