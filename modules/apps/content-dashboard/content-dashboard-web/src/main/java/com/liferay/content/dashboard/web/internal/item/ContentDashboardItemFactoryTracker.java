/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.web.internal.item;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.reflect.GenericUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = ContentDashboardItemFactoryTracker.class)
public class ContentDashboardItemFactoryTracker {

	public Collection<Long> getClassIds() {
		Collection<String> classNames = getClassNames();

		Stream<String> stream = classNames.stream();

		return stream.map(
			_classNameLocalService::getClassNameId
		).collect(
			Collectors.toSet()
		);
	}

	public Collection<String> getClassNames() {
		return Collections.unmodifiableCollection(_serviceTrackerMap.keySet());
	}

	public Optional<ContentDashboardItemFactory<?>>
		getContentDashboardItemFactoryOptional(String className) {

		return Optional.ofNullable(_serviceTrackerMap.getService(className));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap =
			(ServiceTrackerMap)ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, ContentDashboardItemFactory.class, null,
				ServiceReferenceMapperFactory.create(
					bundleContext,
					(contentDashboardItem, emitter) -> emitter.emit(
						GenericUtil.getGenericClassName(
							contentDashboardItem))));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	private volatile ServiceTrackerMap<String, ContentDashboardItemFactory<?>>
		_serviceTrackerMap;

}