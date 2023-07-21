/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.internal.upgrade;

import com.liferay.app.builder.internal.upgrade.v2_2_0.UpgradeSchema;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class AppBuilderServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "2.0.0",
			new com.liferay.app.builder.internal.upgrade.v2_0_0.
				UpgradeAppBuilderApp());

		registry.register(
			"2.0.0", "2.1.0",
			new com.liferay.app.builder.internal.upgrade.v2_1_0.
				UpgradeAppBuilderApp());

		registry.register(
			"2.1.0", "2.2.0",
			new com.liferay.app.builder.internal.upgrade.v2_2_0.
				UpgradeAppBuilderApp(
					_ddlRecordSetLocalService, _ddmStructureLocalService),
			new UpgradeSchema(_counterLocalService));
	}

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

}