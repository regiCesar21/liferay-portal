/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.internal.upgrade;

import com.liferay.commerce.notification.internal.upgrade.v2_0_0.CommerceNotificationTemplateAccountGroupRelUpgradeProcess;
import com.liferay.commerce.notification.internal.upgrade.v2_1_0.CommerceNotificationQueueEntryUpgradeProcess;
import com.liferay.commerce.notification.internal.upgrade.v2_2_0.CommerceNotificationTemplateUpgradeProcess;
import com.liferay.commerce.notification.internal.upgrade.v2_2_1.CommerceNotificationTemplateGroupIdUpgradeProcess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true, service = UpgradeStepRegistrator.class
)
public class CommerceNotificationUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		if (_log.isInfoEnabled()) {
			_log.info("Commerce notification upgrade step registrator started");
		}

		registry.register("1.0.0", "1.1.0", new DummyUpgradeProcess());

		registry.register(
			"1.1.0", "2.0.0",
			new CommerceNotificationTemplateAccountGroupRelUpgradeProcess());

		registry.register(
			"2.0.0", "2.1.0",
			new CommerceNotificationQueueEntryUpgradeProcess());

		registry.register(
			"2.1.0", "2.2.0", new CommerceNotificationTemplateUpgradeProcess());

		registry.register(
			"2.2.0", "2.2.1",
			new CommerceNotificationTemplateGroupIdUpgradeProcess(
				_classNameLocalService, _groupLocalService));

		if (_log.isInfoEnabled()) {
			_log.info(
				"Commerce notification upgrade step registrator finished");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceNotificationUpgradeStepRegistrator.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}