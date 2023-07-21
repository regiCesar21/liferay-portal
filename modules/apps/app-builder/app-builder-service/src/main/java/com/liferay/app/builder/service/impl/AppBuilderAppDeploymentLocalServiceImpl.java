/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.service.impl;

import com.liferay.app.builder.deploy.AppDeployer;
import com.liferay.app.builder.model.AppBuilderAppDeployment;
import com.liferay.app.builder.service.base.AppBuilderAppDeploymentLocalServiceBaseImpl;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.app.builder.model.AppBuilderAppDeployment",
	service = AopService.class
)
public class AppBuilderAppDeploymentLocalServiceImpl
	extends AppBuilderAppDeploymentLocalServiceBaseImpl {

	@Override
	public AppBuilderAppDeployment addAppBuilderAppDeployment(
		long appBuilderAppId, String settings, String type) {

		AppBuilderAppDeployment appBuilderAppDeployment =
			appBuilderAppDeploymentPersistence.create(
				counterLocalService.increment());

		appBuilderAppDeployment.setAppBuilderAppId(appBuilderAppId);
		appBuilderAppDeployment.setSettings(settings);
		appBuilderAppDeployment.setType(type);

		return appBuilderAppDeploymentPersistence.update(
			appBuilderAppDeployment);
	}

	@Override
	public AppBuilderAppDeployment deleteAppBuilderAppDeployment(
			long appBuilderAppDeploymentId)
		throws PortalException {

		AppBuilderAppDeployment appBuilderAppDeployment =
			getAppBuilderAppDeployment(appBuilderAppDeploymentId);

		AppDeployer appDeployer = _serviceTrackerMap.getService(
			appBuilderAppDeployment.getType());

		try {
			if (appDeployer != null) {
				appDeployer.undeploy(
					appBuilderAppDeployment.getAppBuilderAppId());
			}
		}
		catch (PortalException portalException) {
			throw portalException;
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}

		return super.deleteAppBuilderAppDeployment(appBuilderAppDeploymentId);
	}

	@Override
	public AppBuilderAppDeployment getAppBuilderAppDeployment(
			long appBuilderAppId, String type)
		throws PortalException {

		return appBuilderAppDeploymentPersistence.findByA_T(
			appBuilderAppId, type);
	}

	@Override
	public List<AppBuilderAppDeployment> getAppBuilderAppDeployments(
		long appBuilderAppId) {

		return appBuilderAppDeploymentPersistence.findByAppBuilderAppId(
			appBuilderAppId);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, AppDeployer.class, "app.builder.deploy.type");
	}

	@Deactivate
	@Override
	protected void deactivate() {
		super.deactivate();

		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, AppDeployer> _serviceTrackerMap;

}