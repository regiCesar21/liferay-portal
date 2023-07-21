/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.web.internal.deploy;

import com.liferay.app.builder.deploy.AppDeployer;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.app.builder.web.internal.portlet.AppPortlet;
import com.liferay.application.list.PanelApp;
import com.liferay.portal.kernel.model.LayoutTypeAccessPolicy;
import com.liferay.portal.kernel.model.LayoutTypeController;

import java.util.Dictionary;
import java.util.Map;

import javax.portlet.Portlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseAppDeployer implements AppDeployer {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected void deactivate() {
		_bundleContext = null;
	}

	protected ServiceRegistration<?> deployLayoutTypeAccessPolicy(
		LayoutTypeAccessPolicy layoutTypeAccessPolicy,
		Dictionary<String, Object> properties) {

		return _bundleContext.registerService(
			LayoutTypeAccessPolicy.class, layoutTypeAccessPolicy, properties);
	}

	protected ServiceRegistration<?> deployLayoutTypeController(
		LayoutTypeController layoutTypeController,
		Dictionary<String, Object> properties) {

		return _bundleContext.registerService(
			LayoutTypeController.class, layoutTypeController, properties);
	}

	protected ServiceRegistration<?> deployPanelApp(
		PanelApp panelApp, Dictionary<String, Object> properties) {

		return _bundleContext.registerService(
			PanelApp.class, panelApp, properties);
	}

	protected ServiceRegistration<?> deployPortlet(
		AppPortlet appPortlet, Map<String, Object> customProperties) {

		return _bundleContext.registerService(
			Portlet.class, appPortlet,
			appPortlet.getProperties(customProperties));
	}

	@Reference
	protected AppBuilderAppLocalService appBuilderAppLocalService;

	private BundleContext _bundleContext;

}