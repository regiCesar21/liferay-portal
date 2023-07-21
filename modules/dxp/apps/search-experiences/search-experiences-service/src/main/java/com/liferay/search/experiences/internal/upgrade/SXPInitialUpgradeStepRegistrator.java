/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.upgrade;

import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.search.experiences.internal.model.listener.CompanyModelListener;
import com.liferay.search.experiences.internal.search.SXPElementSearchRegistrar;
import com.liferay.search.experiences.internal.upgrade.v1_0_0.SXPElementUpgradeProcess;
import com.liferay.search.experiences.service.SXPElementLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(
	enabled = true, immediate = true, service = UpgradeStepRegistrator.class
)
public class SXPInitialUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialUpgradeSteps(
			new SXPElementUpgradeProcess(
				_companyLocalService, _companyModelListener,
				_sxpElementLocalService));
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CompanyModelListener _companyModelListener;

	@Reference
	private SXPElementLocalService _sxpElementLocalService;

	@Reference
	private SXPElementSearchRegistrar _sxpElementSearchRegistrar;

}