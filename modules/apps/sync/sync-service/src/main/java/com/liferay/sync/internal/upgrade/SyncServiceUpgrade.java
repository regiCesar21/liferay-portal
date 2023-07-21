/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sync.internal.upgrade;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.document.library.sync.service.DLSyncEventLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.sync.internal.upgrade.v2_0_0.UpgradeCompanyId;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shinn Lok
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class SyncServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("0.0.1", "1.0.0", new DummyUpgradeStep());

		registry.register("1.0.0", "1.0.1", new DummyUpgradeStep());

		registry.register(
			"1.0.1", "1.0.2",
			new com.liferay.sync.internal.upgrade.v1_0_2.UpgradeSchema(),
			new com.liferay.sync.internal.upgrade.v1_0_2.UpgradeSyncDLObject(
				_counterLocalService, _dlSyncEventLocalService,
				_groupLocalService));

		registry.register(
			"1.0.2", "1.0.3",
			new com.liferay.sync.internal.upgrade.v1_0_3.UpgradeSchema());

		registry.register(
			"1.0.3", "1.0.4",
			new com.liferay.sync.internal.upgrade.v1_0_4.UpgradeSchema());

		registry.register("1.0.4", "2.0.0", new UpgradeCompanyId());
	}

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private DLSyncEventLocalService _dlSyncEventLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}