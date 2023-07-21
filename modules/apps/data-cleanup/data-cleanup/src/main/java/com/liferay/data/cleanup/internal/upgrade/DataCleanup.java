/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.upgrade;

import com.liferay.data.cleanup.internal.configuration.DataCleanupConfiguration;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.util.Map;
import java.util.function.Supplier;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(
	configurationPid = "com.liferay.data.cleanup.internal.configuration.DataCleanupConfiguration",
	immediate = true, service = UpgradeStepRegistrator.class
)
public class DataCleanup implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		try {
			_cleanUpModuleData(
				_dataCleanupConfiguration::cleanUpChatModuleData,
				"com.liferay.chat.service", UpgradeChat::new);

			_cleanUpModuleData(
				_dataCleanupConfiguration::cleanUpDictionaryModuleData,
				"com.liferay.dictionary.web", UpgradeDictionary::new);

			_cleanUpModuleData(
				_dataCleanupConfiguration::cleanUpDirectoryModuleData,
				"com.liferay.directory.web", UpgradeDirectory::new);

			_cleanUpModuleData(
				_dataCleanupConfiguration::cleanUpInvitationModuleData,
				"com.liferay.invitation.web", UpgradeInvitation::new);

			_cleanUpModuleData(
				_dataCleanupConfiguration::cleanUpMailReaderModuleData,
				"com.liferay.mail.reader.service", UpgradeMailReader::new);

			_cleanUpModuleData(
				_dataCleanupConfiguration::cleanUpPrivateMessagingModuleData,
				"com.liferay.social.privatemessaging.service",
				() -> new UpgradePrivateMessaging(_mbThreadLocalService));

			_cleanUpModuleData(
				_dataCleanupConfiguration::cleanUpShoppingModuleData,
				"com.liferay.shopping.service",
				() -> new UpgradeShopping(_imageLocalService));

			_cleanUpModuleData(
				_dataCleanupConfiguration::cleanUpTwitterModuleData,
				"com.liferay.twitter.service", UpgradeTwitter::new);
		}
		catch (UpgradeException upgradeException) {
			ReflectionUtil.throwException(upgradeException);
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_dataCleanupConfiguration = ConfigurableUtil.createConfigurable(
			DataCleanupConfiguration.class, properties);
	}

	private void _cleanUpModuleData(
			Supplier<Boolean> booleanSupplier, String servletContextName,
			Supplier<UpgradeProcess> upgradeProcessSupplier)
		throws UpgradeException {

		if (booleanSupplier.get()) {
			Release release = _releaseLocalService.fetchRelease(
				servletContextName);

			if (release != null) {
				UpgradeProcess upgradeProcess = upgradeProcessSupplier.get();

				upgradeProcess.upgrade();

				CacheRegistryUtil.clear();
			}
		}
	}

	private DataCleanupConfiguration _dataCleanupConfiguration;

	@Reference
	private ImageLocalService _imageLocalService;

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

	@Reference
	private ReleaseLocalService _releaseLocalService;

}