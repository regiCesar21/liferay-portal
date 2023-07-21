/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.web.internal.util;

import com.liferay.configuration.admin.display.ConfigurationVisibilityController;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import java.io.Serializable;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Drew Brokke
 */
public class ConfigurationVisibilityUtil {

	public static boolean isVisible(
		String pid, ExtendedObjectClassDefinition.Scope scope,
		Serializable scopePK) {

		ConfigurationVisibilityController configurationVisibilityController =
			_getConfigurationVisibilityController(pid);

		return configurationVisibilityController.isVisible(scope, scopePK);
	}

	private static ConfigurationVisibilityController
		_getConfigurationVisibilityController(String pid) {

		if (_serviceTrackerMap.containsKey(pid)) {
			return _serviceTrackerMap.getService(pid);
		}

		return _configurationVisibilityController;
	}

	private static final ConfigurationVisibilityController
		_configurationVisibilityController = (scope, scopePK) -> true;
	private static final ServiceTrackerMap
		<String, ConfigurationVisibilityController> _serviceTrackerMap;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ConfigurationVisibilityController.class);

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundle.getBundleContext(), ConfigurationVisibilityController.class,
			"configuration.pid");
	}

}