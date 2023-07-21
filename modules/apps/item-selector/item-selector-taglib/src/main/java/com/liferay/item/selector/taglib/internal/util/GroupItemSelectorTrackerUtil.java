/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector.taglib.internal.util;

import com.liferay.item.selector.provider.GroupItemSelectorProvider;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Cristina González
 */
@Component(immediate = true, service = {})
public class GroupItemSelectorTrackerUtil {

	public static Optional<GroupItemSelectorProvider>
		getGroupItemSelectorProviderOptional(String groupType) {

		if (_serviceTrackerMap == null) {
			return Optional.empty();
		}

		GroupItemSelectorProvider groupItemSelectorProvider =
			_serviceTrackerMap.getService(groupType);

		if ((groupItemSelectorProvider != null) &&
			groupItemSelectorProvider.isEnabled()) {

			return Optional.of(groupItemSelectorProvider);
		}

		return Optional.empty();
	}

	public static Set<String> getGroupItemSelectorProviderTypes() {
		if (_serviceTrackerMap == null) {
			return Collections.emptySet();
		}

		Collection<GroupItemSelectorProvider> values =
			_serviceTrackerMap.values();

		Stream<GroupItemSelectorProvider> stream = values.stream();

		return stream.filter(
			GroupItemSelectorProvider::isEnabled
		).map(
			GroupItemSelectorProvider::getGroupType
		).collect(
			Collectors.toSet()
		);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, GroupItemSelectorProvider.class, null,
			(serviceReference, emitter) -> {
				GroupItemSelectorProvider groupItemSelectorProvider =
					bundleContext.getService(serviceReference);

				try {
					emitter.emit(groupItemSelectorProvider.getGroupType());
				}
				finally {
					bundleContext.ungetService(serviceReference);
				}
			});
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceTrackerMap != null) {
			_serviceTrackerMap.close();
		}
	}

	private static ServiceTrackerMap<String, GroupItemSelectorProvider>
		_serviceTrackerMap;

}