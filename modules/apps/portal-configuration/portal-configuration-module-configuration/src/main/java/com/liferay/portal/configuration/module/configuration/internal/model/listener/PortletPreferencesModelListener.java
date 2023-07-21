/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.module.configuration.internal.model.listener;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.internal.ConfigurationOverrideInstance;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.settings.definition.ConfigurationPidMapping;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationEvent;
import org.osgi.service.cm.ConfigurationListener;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Drew Brokke
 */
@Component(service = {ConfigurationListener.class, ModelListener.class})
public class PortletPreferencesModelListener
	extends BaseModelListener<PortletPreferences>
	implements ConfigurationListener {

	@Override
	public void configurationEvent(ConfigurationEvent configurationEvent) {
		String key = configurationEvent.getPid();

		String factoryPid = configurationEvent.getFactoryPid();

		if (factoryPid != null) {
			key = StringUtil.replaceLast(
				factoryPid, ".scoped", StringPool.BLANK);
		}

		ConfigurationOverrideInstance.clearConfigurationOverrideInstance(key);
	}

	@Override
	public void onAfterCreate(PortletPreferences portletPreferences)
		throws ModelListenerException {

		_clearConfigurationOverrideInstance(portletPreferences);
	}

	@Override
	public void onAfterRemove(PortletPreferences portletPreferences)
		throws ModelListenerException {

		_clearConfigurationOverrideInstance(portletPreferences);
	}

	@Override
	public void onAfterUpdate(PortletPreferences portletPreferences)
		throws ModelListenerException {

		_clearConfigurationOverrideInstance(portletPreferences);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_stringConfigurationPidMappingServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, ConfigurationPidMapping.class, null,
				(serviceReference, emitter) -> {
					ConfigurationPidMapping configurationPidMapping =
						bundleContext.getService(serviceReference);

					emitter.emit(configurationPidMapping.getConfigurationPid());
				});
	}

	@Deactivate
	protected void deactivate() {
		_stringConfigurationPidMappingServiceTrackerMap.close();
	}

	private void _clearConfigurationOverrideInstance(
		PortletPreferences portletPreferences) {

		if ((portletPreferences == null) ||
			(portletPreferences.getPortletId() == null)) {

			return;
		}

		ConfigurationPidMapping configurationPidMapping =
			_stringConfigurationPidMappingServiceTrackerMap.getService(
				portletPreferences.getPortletId());

		if (configurationPidMapping == null) {
			return;
		}

		ConfigurationOverrideInstance.clearConfigurationOverrideInstance(
			configurationPidMapping.getConfigurationBeanClass());
	}

	private ServiceTrackerMap<String, ConfigurationPidMapping>
		_stringConfigurationPidMappingServiceTrackerMap;

}