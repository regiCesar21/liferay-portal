/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sync.web.internal.upgrade;

import com.liferay.portal.kernel.upgrade.BaseUpgradePortletId;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.sync.constants.SyncPortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shinn Lok
 */
@Component(
	immediate = true,
	service = {SyncWebUpgrade.class, UpgradeStepRegistrator.class}
)
public class SyncWebUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"0.0.0", "1.0.0",
			new BaseUpgradePortletId() {

				@Override
				protected String[][] getRenamePortletIdsArray() {
					return new String[][] {
						{
							"com_liferay_sync_connector_web_portlet_" +
								"SyncAdminPortlet",
							SyncPortletKeys.SYNC_ADMIN_PORTLET
						},
						{
							"com_liferay_sync_connector_web_portlet_" +
								"SyncDevicesPortlet",
							SyncPortletKeys.SYNC_DEVICES_PORTLET
						}
					};
				}

			});
	}

}