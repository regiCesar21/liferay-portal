/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.web.internal.deploy;

import com.liferay.app.builder.portlet.tab.AppBuilderAppPortletTab;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = {})
public class AppDeployUtil {

	public static AppBuilderAppPortletTab getAppBuilderAppPortletTab(
		String name) {

		return _appBuilderAppPortletTabServiceTrackerMap.getService(name);
	}

	public static List<ServiceWrapper<MVCResourceCommand>> getServices(
		String name) {

		return _appPortletMVCResourceCommandServiceTrackerMap.getService(name);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_appBuilderAppPortletTabServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, AppBuilderAppPortletTab.class,
				"app.builder.app.tab.name");
		_appPortletMVCResourceCommandServiceTrackerMap =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext, MVCResourceCommand.class,
				"app.builder.app.scope",
				ServiceTrackerCustomizerFactory.
					<MVCResourceCommand>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_appBuilderAppPortletTabServiceTrackerMap.close();
		_appPortletMVCResourceCommandServiceTrackerMap.close();
	}

	private static ServiceTrackerMap<String, AppBuilderAppPortletTab>
		_appBuilderAppPortletTabServiceTrackerMap;
	private static ServiceTrackerMap
		<String, List<ServiceWrapper<MVCResourceCommand>>>
			_appPortletMVCResourceCommandServiceTrackerMap;

}