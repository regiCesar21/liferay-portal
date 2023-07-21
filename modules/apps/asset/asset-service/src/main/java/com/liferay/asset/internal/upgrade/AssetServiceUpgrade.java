/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.internal.upgrade;

import com.liferay.asset.internal.upgrade.v1_0_0.AssetEntryUsageUpgradeProcess;
import com.liferay.asset.internal.upgrade.v2_0_0.UpgradeCompanyId;
import com.liferay.asset.internal.upgrade.v2_0_1.UpgradeAssetEntryUsage;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.portal.kernel.upgrade.UpgradeCTModel;
import com.liferay.portal.kernel.upgrade.UpgradeMVCCVersion;
import com.liferay.portal.kernel.upgrade.UpgradeViewCount;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.view.count.service.ViewCountEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class AssetServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"0.0.1", "1.0.0", new AssetEntryUsageUpgradeProcess());

		registry.register(
			"1.0.0", "1.1.0",
			new UpgradeMVCCVersion() {

				@Override
				protected String[] getModuleTableNames() {
					return new String[] {"AssetEntryUsage"};
				}

			});

		registry.register(
			"1.1.0", "2.0.0", new UpgradeCompanyId(),
			new UpgradeViewCount(
				"AssetEntry", AssetEntry.class, "entryId", "viewCount"));

		registry.register("2.0.0", "2.0.1", new UpgradeAssetEntryUsage());

		registry.register(
			"2.0.1", "2.1.0", new UpgradeCTModel("AssetEntryUsage"));
	}

	/**
	 * See LPS-101084. The ViewCount table needs to exist.
	 */
	@Reference
	private ViewCountEntryLocalService _viewCountEntryLocalService;

}