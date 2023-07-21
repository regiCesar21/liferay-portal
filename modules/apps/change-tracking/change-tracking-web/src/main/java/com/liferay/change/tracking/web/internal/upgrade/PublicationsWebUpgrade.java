/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.upgrade;

import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.web.internal.constants.CTPortletKeys;
import com.liferay.change.tracking.web.internal.upgrade.v1_0_2.CleanUpPDFPreviewsUpgradeProcess;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletId;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class PublicationsWebUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("0.0.0", "1.0.2", new DummyUpgradeStep());

		registry.register(
			"1.0.0", "1.0.1",
			new BaseUpgradePortletId() {

				@Override
				protected String[][] getRenamePortletIdsArray() {
					return new String[][] {
						{
							"com_liferay_change_tracking_web_portlet_" +
								"ChangeListsPortlet",
							CTPortletKeys.PUBLICATIONS
						},
						{
							"com_liferay_change_tracking_web_portlet_" +
								"ChangeListsConfigurationPortlet",
							CTPortletKeys.PUBLICATIONS_CONFIGURATION
						}
					};
				}

			});

		registry.register(
			"1.0.1", "1.0.2",
			new CleanUpPDFPreviewsUpgradeProcess(
				_ctCollectionLocalService, _portal));
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private Portal _portal;

}