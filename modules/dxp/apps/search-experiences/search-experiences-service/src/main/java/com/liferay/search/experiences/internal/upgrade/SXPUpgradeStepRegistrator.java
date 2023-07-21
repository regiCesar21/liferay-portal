/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.upgrade;

import com.liferay.portal.kernel.upgrade.BaseExternalReferenceCodeUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.search.experiences.internal.upgrade.v1_1_0.SXPBlueprintUpgradeProcess;
import com.liferay.search.experiences.internal.upgrade.v1_1_0.SXPElementUpgradeProcess;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(
	enabled = true, immediate = true, service = UpgradeStepRegistrator.class
)
public class SXPUpgradeStepRegistrator implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "1.1.0", new SXPElementUpgradeProcess(),
			new SXPBlueprintUpgradeProcess());

		registry.register(
			"1.1.0", "1.2.0",
			new BaseExternalReferenceCodeUpgradeProcess() {

				@Override
				protected String[][] getTableAndPrimaryKeyColumnNames() {
					return new String[][] {
						{"SXPBlueprint", "sxpBlueprintId"},
						{"SXPElement", "sxpElementId"}
					};
				}

			});

		registry.register(
			"1.2.0", "2.0.0",
			new com.liferay.search.experiences.internal.upgrade.v2_0_0.
				SXPBlueprintUpgradeProcess());
	}

}