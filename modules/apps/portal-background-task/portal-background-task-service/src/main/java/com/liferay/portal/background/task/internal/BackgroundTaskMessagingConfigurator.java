/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.background.task.internal;

import com.liferay.portal.background.task.internal.messaging.BackgroundTaskMessageListener;
import com.liferay.portal.background.task.internal.messaging.BackgroundTaskQueuingMessageListener;
import com.liferay.portal.background.task.internal.messaging.RemoveOnCompletionBackgroundTaskStatusMessageListener;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutorRegistry;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusRegistry;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocalManager;
import com.liferay.portal.kernel.lock.LockManager;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Vendel Toreki
 */
@Component(
	immediate = true, service = BackgroundTaskMessagingConfigurator.class
)
public class BackgroundTaskMessagingConfigurator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		Destination backgroundTaskDestination = _registerDestination(
			bundleContext, DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
			DestinationNames.BACKGROUND_TASK, 5, 10);

		backgroundTaskDestination.register(
			new BackgroundTaskMessageListener(
				_backgroundTaskExecutorRegistry, _backgroundTaskLocalService,
				_backgroundTaskStatusRegistry,
				_backgroundTaskThreadLocalManager, _lockManager, _messageBus));

		Destination backgroundTaskStatusDestination = _registerDestination(
			bundleContext, DestinationConfiguration.DESTINATION_TYPE_SERIAL,
			DestinationNames.BACKGROUND_TASK_STATUS, 1, 1);

		backgroundTaskStatusDestination.register(
			new BackgroundTaskQueuingMessageListener(
				_backgroundTaskLocalService, _lockManager));
		backgroundTaskStatusDestination.register(
			new RemoveOnCompletionBackgroundTaskStatusMessageListener(
				_backgroundTaskLocalService));
	}

	@Deactivate
	protected void deactivate() {
		for (ServiceRegistration<Destination> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	private Destination _registerDestination(
		BundleContext bundleContext, String destinationType,
		String destinationName, int workersCoreSize, int workersMaxSize) {

		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(destinationType, destinationName);

		destinationConfiguration.setWorkersCoreSize(workersCoreSize);
		destinationConfiguration.setWorkersMaxSize(workersMaxSize);

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		_serviceRegistrations.add(
			bundleContext.registerService(
				Destination.class, destination,
				MapUtil.singletonDictionary(
					"destination.name", destination.getName())));

		return destination;
	}

	@Reference
	private BackgroundTaskExecutorRegistry _backgroundTaskExecutorRegistry;

	@Reference
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Reference
	private BackgroundTaskStatusRegistry _backgroundTaskStatusRegistry;

	@Reference
	private BackgroundTaskThreadLocalManager _backgroundTaskThreadLocalManager;

	@Reference
	private DestinationFactory _destinationFactory;

	@Reference
	private LockManager _lockManager;

	@Reference
	private MessageBus _messageBus;

	private final List<ServiceRegistration<Destination>> _serviceRegistrations =
		new ArrayList<>();

}